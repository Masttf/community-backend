package fun.masttf.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fun.masttf.annotation.GlobalInterceptor;
import fun.masttf.aspect.VerifyParam;
import fun.masttf.config.WebConfig;
import fun.masttf.entity.constans.Constans;
import fun.masttf.entity.dto.SessionWebUserDto;
import fun.masttf.entity.enums.ArticleOrderTypeEnum;
import fun.masttf.entity.enums.ArticleAttachmentTypeEnum;
import fun.masttf.entity.enums.ArticleEditorTypeEnum;
import fun.masttf.entity.enums.ArticleStatusEnum;
import fun.masttf.entity.enums.RecordOpTypeEnum;
import fun.masttf.entity.enums.ResponseCodeEnum;
import fun.masttf.entity.po.ForumArticle;
import fun.masttf.entity.po.ForumArticleAttachment;
import fun.masttf.entity.po.ForumArticleAttachmentDownload;
import fun.masttf.entity.po.LikeRecord;
import fun.masttf.entity.po.UserInfo;
import fun.masttf.entity.query.ForumArticleAttachmentQuery;
import fun.masttf.entity.query.ForumArticleQuery;
import fun.masttf.entity.vo.ForumArticleAttachmentVo;
import fun.masttf.entity.vo.ForumArticleDetailVo;
import fun.masttf.entity.vo.ForumArticleVo;
import fun.masttf.entity.vo.PaginationResultVo;
import fun.masttf.entity.vo.ResponseVo;
import fun.masttf.entity.vo.UserDownloadInfoVo;
import fun.masttf.exception.BusinessException;
import fun.masttf.service.ForumArticleAttachmentDownloadService;
import fun.masttf.service.ForumArticleAttachmentService;
import fun.masttf.service.ForumArticleService;
import fun.masttf.service.ForumBoardService;
import fun.masttf.service.LikeRecordService;
import fun.masttf.service.UserInfoService;
import fun.masttf.utils.CopyTools;
import fun.masttf.utils.StringTools;


@RestController
@RequestMapping("/forum")
public class ForumArticleController extends ABaseController {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ForumArticleController.class);
    @Autowired
    private ForumArticleService forumArticleService;
    @Autowired
    private ForumArticleAttachmentService forumArticleAttachmentService;
    @Autowired
    private LikeRecordService likeRecordService;
    @Autowired
    private ForumArticleAttachmentDownloadService forumArticleAttachmentDownloadService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private WebConfig webConfig;
    @Autowired
    private ForumBoardService forumBoardService;
    
    @RequestMapping("/loadArticle")
    @GlobalInterceptor(checkParams = true)
    public ResponseVo<Object> loadArticle(HttpSession session, Integer boardId, Integer pBoardId, Integer orderType, Integer pageNo) {
        
        ForumArticleQuery articleQuery = new ForumArticleQuery();
        articleQuery.setBoardId((boardId == null || boardId == 0) ? null : boardId);
        articleQuery.setpBoardId(pBoardId);
        articleQuery.setPageNo(pageNo);
        SessionWebUserDto userDto = getUserInfoSession(session);
        if(userDto != null) {
            articleQuery.setCurrentUserId(userDto.getUserId());
        }else{
            articleQuery.setStatus(ArticleStatusEnum.AUDIT.getStatus());
        }
        ArticleOrderTypeEnum orderTypeEnum = ArticleOrderTypeEnum.getByType(orderType);
        orderTypeEnum = orderTypeEnum == null ? ArticleOrderTypeEnum.HOT : orderTypeEnum;
        articleQuery.setOrderBy(orderTypeEnum.getOderSql());
        PaginationResultVo<ForumArticle> result = forumArticleService.findListByPage(articleQuery);
        return getSuccessResponseVo(convert2PaginationVo(result, ForumArticleVo.class));
    }
    
    @RequestMapping("/getArticleDetail")
    @GlobalInterceptor(checkParams = true)
    public ResponseVo<Object> getArticleDetail(HttpSession session,@VerifyParam(required = true) String articleId) {
        SessionWebUserDto userDto = getUserInfoSession(session);
        ForumArticle article = forumArticleService.readArticle(articleId);
        if(article == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_404);
        }
        //先判断空避免空指针
        Boolean canShowNoAudit = (userDto != null && article.getUserId().equals(userDto.getUserId()));
        if(article.getStatus() == ArticleStatusEnum.DEL.getStatus() || (article.getStatus() == ArticleStatusEnum.NO_AUDIT.getStatus() && !canShowNoAudit)) {
            throw new BusinessException(ResponseCodeEnum.CODE_404);
        }
        ForumArticleDetailVo detailVo = new ForumArticleDetailVo();
        detailVo.setArticle(CopyTools.copy(article,ForumArticleVo.class));
        
        //附件
        if(article.getAttachmentType() == ArticleAttachmentTypeEnum.HAVE_ATTACHMENT.getType()) {
            ForumArticleAttachmentQuery attachmentQuery = new ForumArticleAttachmentQuery();
            attachmentQuery.setArticleId(article.getArticleId());
            List<ForumArticleAttachment> attachmentList = forumArticleAttachmentService.findListByQuery(attachmentQuery);
            if(!attachmentList.isEmpty()){
                detailVo.setArticleAttachments(CopyTools.copy(attachmentList.get(0), ForumArticleAttachmentVo.class));
            }
        }

        if(userDto != null) {
            LikeRecord likeRecord = likeRecordService.getByObjectIdAndUserIdAndOpType(articleId, userDto.getUserId(), RecordOpTypeEnum.ARTICLE_LIKE.getType());
            if(likeRecord != null) {
                detailVo.setHaveLike(true);
            }
        }
        return getSuccessResponseVo(detailVo);
    }
    @RequestMapping("/doLike")
    @GlobalInterceptor(checkLogin = true, checkParams = true)
    public ResponseVo<Object> doLike(HttpSession session,@VerifyParam(required = true) String articleId) {
        SessionWebUserDto userDto = (SessionWebUserDto) session.getAttribute(Constans.SESSION_KEY);
        likeRecordService.doLike(articleId, userDto.getUserId(), userDto.getNickName(), RecordOpTypeEnum.ARTICLE_LIKE);
        return getSuccessResponseVo(null);
    }

    @RequestMapping("/getUserDownloadInfo")
    @GlobalInterceptor(checkLogin = true, checkParams = true)
    public ResponseVo<Object> getUserDownloadInfo(HttpSession session, @VerifyParam(required = true) String fileId) {
        SessionWebUserDto userDto = getUserInfoSession(session);
        UserInfo userInfo = userInfoService.getByUserId(userDto.getUserId());
        UserDownloadInfoVo downloadInfo = new UserDownloadInfoVo();
        downloadInfo.setUserIntegral(userInfo.getCurrentIntegral());
        ForumArticleAttachmentDownload attachment =  forumArticleAttachmentDownloadService.getByFileIdAndUserId(fileId, userDto.getUserId());
        if(attachment != null) {
            downloadInfo.setHaveDownload(true);
        }else{
            downloadInfo.setHaveDownload(false);
        }
        return getSuccessResponseVo(downloadInfo);
    }
    
    @RequestMapping("/attachmentDownload")
    @GlobalInterceptor(checkLogin = true, checkParams = true)
    public void attachmentDownload(HttpSession session,
                            HttpServletRequest request,
                            HttpServletResponse response,
                            @VerifyParam(required = true) String fileId) {
        ForumArticleAttachment attachment = forumArticleAttachmentService.downloadAttachment(fileId, getUserInfoSession(session));
        InputStream in = null;
        OutputStream out = null;
        String downloadFileName = attachment.getFileName();
        String filePath = webConfig.getProjectFolder() + Constans.FILE_FOLDER_FILE + Constans.FILE_FOLDER_ATTACHMENT + attachment.getFilePath();
        File file = new File(filePath);
        try {
            in = new FileInputStream(file);
            out = response.getOutputStream();
            response.setContentType("application/x-msdownload;charset=UTF-8");
            //解决中文乱码
            if(request.getHeader("User-Agent").toLowerCase().indexOf("msie") > 0) { //IE浏览器
                downloadFileName = URLEncoder.encode(downloadFileName, "UTF-8");
            } else {
                downloadFileName = new String(downloadFileName.getBytes("UTF-8"), "ISO8859-1");
            }
            response.setHeader("Content-Disposition", "attachment;filename=\"" + downloadFileName + "\"");
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (Exception e) {
            logger.error("下载附件失败", e);
            throw new BusinessException("下载附件失败");
        } finally {
            try {
                if(in != null) {
                    in.close();
                }
                if(out != null) {
                    out.close();
                }
            } catch (Exception e) {
                logger.error("关闭流失败", e);
            }
        }
    }

    @RequestMapping("/loadBoard4Post")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVo<Object> loadBoard4Post(HttpSession session) {
        SessionWebUserDto userDto = getUserInfoSession(session);
        Integer postType = null;
        if(!userDto.getIsAdmin()) {
            postType = 1;
        }
        return getSuccessResponseVo(forumBoardService.getBoardTree(postType));
    }


    @RequestMapping("/postArticle")
    @GlobalInterceptor(checkLogin = true, checkParams = true)
    public ResponseVo<Object> postArticle(
                            HttpSession session,
                            MultipartFile cover,
                            MultipartFile attachment,
                            Integer integral,
                            @VerifyParam(required = true, max = 150) String title,
                            @VerifyParam(required = true) Integer pBoardId,
                            Integer boardId,
                            @VerifyParam(max = 200) String summary,
                            @VerifyParam(required = true) String content,
                            String markdownContent,
                            @VerifyParam(required = true) Integer editorType) {
        
        ArticleEditorTypeEnum editorTypeEnum = ArticleEditorTypeEnum.getByType(editorType);
        if(editorTypeEnum == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        if(ArticleEditorTypeEnum.MARKDOWN.getType().equals(editorType) && StringTools.isEmpty(markdownContent)) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        
        title = StringTools.escapeHtml(title);
        SessionWebUserDto userDto = getUserInfoSession(session);
        
        ForumArticle article = new ForumArticle();
        article.setTitle(title);
        article.setpBoardId(pBoardId);
        article.setBoardId(boardId);
        article.setSummary(summary);
        article.setContent(content);
        article.setMarkdownContent(markdownContent);
        article.setEditorType(editorType);

        article.setUserId(userDto.getUserId());
        article.setNickName(userDto.getNickName());
        article.setUserIpAddress(userDto.getProvice());

        ForumArticleAttachment articleAttachment = new ForumArticleAttachment();
        articleAttachment.setIntegral(integral == null ? 0 : integral);

        forumArticleService.postArticle(userDto.getIsAdmin(),article, articleAttachment, cover, attachment);
        Map<String, String> map = new HashMap<>();
        map.put("articleId", article.getArticleId());
        return getSuccessResponseVo(map);
    }

    @RequestMapping("/articleDetail4Update")
    @GlobalInterceptor(checkLogin = true, checkParams = true)
    public ResponseVo<Object> articleDetail4Update(HttpSession session, @VerifyParam(required = true) String articleId) {
        SessionWebUserDto userDto = getUserInfoSession(session);
        ForumArticle article = forumArticleService.readArticle(articleId);
        if(article == null || article.getStatus() == ArticleStatusEnum.DEL.getStatus() || !article.getUserId().equals(userDto.getUserId())) {
            throw new BusinessException(ResponseCodeEnum.CODE_404);
        }
        ForumArticleDetailVo detailVo = new ForumArticleDetailVo();
        detailVo.setArticle(CopyTools.copy(article,ForumArticleVo.class));
        //附件
        if(article.getAttachmentType() == ArticleAttachmentTypeEnum.HAVE_ATTACHMENT.getType()) {
            ForumArticleAttachmentQuery attachmentQuery = new ForumArticleAttachmentQuery();
            attachmentQuery.setArticleId(article.getArticleId());
            List<ForumArticleAttachment> attachmentList = forumArticleAttachmentService.findListByQuery(attachmentQuery);
            if(!attachmentList.isEmpty()){
                detailVo.setArticleAttachments(CopyTools.copy(attachmentList.get(0), ForumArticleAttachmentVo.class));
            }
        }
        return getSuccessResponseVo(detailVo);
    }
    @RequestMapping("/updateArticle")
    @GlobalInterceptor(checkLogin = true, checkParams = true)
    public ResponseVo<Object> updateArticle(
                            HttpSession session,
                            MultipartFile cover,
                            MultipartFile attachment,
                            Integer integral,
                            @VerifyParam(required = true) String articleId,
                            @VerifyParam(required = true, max = 150) String title,
                            @VerifyParam(required = true) Integer pBoardId,
                            Integer boardId,
                            @VerifyParam(max = 200) String summary,
                            @VerifyParam(required = true) String content,
                            String markdownContent,
                            @VerifyParam(required = true) Integer haveAttachment,
                            @VerifyParam(required = true) Integer editorType) {
        
        ArticleEditorTypeEnum editorTypeEnum = ArticleEditorTypeEnum.getByType(editorType);
        if(editorTypeEnum == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        ArticleAttachmentTypeEnum attachmentTypeEnum = ArticleAttachmentTypeEnum.getByType(haveAttachment);
        if(attachmentTypeEnum == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        if(ArticleEditorTypeEnum.MARKDOWN.getType().equals(editorType) && StringTools.isEmpty(markdownContent)) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        
        title = StringTools.escapeHtml(title);
        SessionWebUserDto userDto = getUserInfoSession(session);
        
        ForumArticle article = new ForumArticle();
        article.setArticleId(articleId);
        article.setTitle(title);
        article.setpBoardId(pBoardId);
        article.setBoardId(boardId);
        article.setSummary(summary);
        article.setContent(content);
        article.setMarkdownContent(markdownContent);
        article.setEditorType(editorType);
        article.setAttachmentType(haveAttachment);

        article.setUserId(userDto.getUserId());
        article.setNickName(userDto.getNickName());
        article.setUserIpAddress(userDto.getProvice());

        ForumArticleAttachment articleAttachment = new ForumArticleAttachment();
        articleAttachment.setIntegral(integral == null ? 0 : integral);

        forumArticleService.updateArticle(userDto.getIsAdmin(),article, articleAttachment, cover, attachment);
        Map<String, String> map = new HashMap<>();
        map.put("articleId", article.getArticleId());
        return getSuccessResponseVo(map);
    }

    @RequestMapping("/search")
    @GlobalInterceptor(checkParams = true)
    public ResponseVo<Object> search(HttpSession session, @VerifyParam(required = true) String keyword, Integer pageNo) {
        ForumArticleQuery articleQuery = new ForumArticleQuery();
        articleQuery.setTitleFuzzy(keyword);
        articleQuery.setPageNo(pageNo);
        SessionWebUserDto userDto = getUserInfoSession(session);
        if(userDto != null) {
            articleQuery.setCurrentUserId(userDto.getUserId());
        }else{
            articleQuery.setStatus(ArticleStatusEnum.AUDIT.getStatus());
        }
        PaginationResultVo<ForumArticle> paginationResultVo = forumArticleService.findListByPage(articleQuery);
        return getSuccessResponseVo(convert2PaginationVo(paginationResultVo, ForumArticleVo.class));
    }

    @RequestMapping("/deleteArticle")
    @GlobalInterceptor(checkLogin = true, checkParams = true)
    public ResponseVo<Object> deleteArticle(HttpSession session, @VerifyParam(required = true) String articleId) {
        SessionWebUserDto userDto = getUserInfoSession(session);
        ForumArticle article = forumArticleService.readArticle(articleId);
        if(article == null ||article.getStatus().equals(ArticleStatusEnum.DEL.getStatus()) || !article.getUserId().equals(userDto.getUserId())) {
            throw new BusinessException(ResponseCodeEnum.CODE_404);
        }
        ForumArticle articleTemp = new ForumArticle();
        articleTemp.setStatus(ArticleStatusEnum.DEL.getStatus());
        forumArticleService.updateByArticleId(articleTemp, articleId);
        return getSuccessResponseVo(null);
    }
}
