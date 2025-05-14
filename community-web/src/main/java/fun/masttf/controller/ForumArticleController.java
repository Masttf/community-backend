package fun.masttf.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fun.masttf.annotation.GlobalInterceptor;
import fun.masttf.aspect.VerifyParam;
import fun.masttf.config.WebConfig;
import fun.masttf.entity.constans.Constans;
import fun.masttf.entity.dto.SessionWebUserDto;
import fun.masttf.entity.enums.ArticleOrderTypeEnum;
import fun.masttf.entity.enums.ArticleOrCommentStatusEnum;
import fun.masttf.entity.enums.OperRecordOpTypeEnum;
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
import fun.masttf.service.LikeRecordService;
import fun.masttf.service.UserInfoService;
import fun.masttf.utils.CopyTools;


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
    
    @RequestMapping("/loadArticle")
    public ResponseVo<Object> loadArticle(HttpSession session, Integer boardId, Integer pBoardId, Integer orderType, Integer pageNo) {
        
        ForumArticleQuery articleQuery = new ForumArticleQuery();
        articleQuery.setBoardId((boardId == null || boardId == 0) ? null : boardId);
        articleQuery.setpBoardId(pBoardId);
        articleQuery.setPageNo(pageNo);
        SessionWebUserDto userDto = getUserInfoSession(session);
        if(userDto != null) {
            articleQuery.setCurrentUserId(userDto.getUserId());
        }else{
            articleQuery.setStatus(ArticleOrCommentStatusEnum.AUDIT.getStatus());
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
        Boolean canShowNoAudit = (userDto == null || !article.getUserId().equals(userDto.getUserId()));
        if(article == null || article.getStatus() == ArticleOrCommentStatusEnum.DEL.getStatus() || (article.getStatus() == ArticleOrCommentStatusEnum.NO_AUDIT.getStatus() && !canShowNoAudit)) {
            throw new BusinessException(ResponseCodeEnum.CODE_404);
        }
        ForumArticleDetailVo detailVo = new ForumArticleDetailVo();
        detailVo.setArticle(CopyTools.copy(article,ForumArticleVo.class));
        
        //附件
        if(article.getAttachmentType() == Constans.ONE) {
            ForumArticleAttachmentQuery attachmentQuery = new ForumArticleAttachmentQuery();
            attachmentQuery.setArticleId(article.getArticleId());
            List<ForumArticleAttachment> attachmentList = forumArticleAttachmentService.findListByQuery(attachmentQuery);
            if(!attachmentList.isEmpty()){
                detailVo.setArticleAttachments(CopyTools.copyList(attachmentList, ForumArticleAttachmentVo.class));
            }
        }

        if(userDto != null) {
            LikeRecord likeRecord = likeRecordService.getByObjectIdAndUserIdAndOpType(articleId, userDto.getUserId(), OperRecordOpTypeEnum.ARTICLE_LIKE.getType());
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
        likeRecordService.doLike(articleId, userDto.getUserId(), userDto.getNickName(), OperRecordOpTypeEnum.ARTICLE_LIKE);
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
}
