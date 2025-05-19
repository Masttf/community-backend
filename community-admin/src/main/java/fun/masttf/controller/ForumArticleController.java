package fun.masttf.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fun.masttf.annotation.GlobalInterceptor;
import fun.masttf.annotation.VerifyParam;
import fun.masttf.config.AdminConfig;
import fun.masttf.entity.constans.Constans;
import fun.masttf.entity.enums.ArticleOrderTypeEnum;
import fun.masttf.entity.enums.ArticleTopTypeEnum;
import fun.masttf.entity.enums.CommentOrderTypeEnum;
import fun.masttf.entity.enums.ResponseCodeEnum;
import fun.masttf.entity.po.ForumArticle;
import fun.masttf.entity.po.ForumArticleAttachment;
import fun.masttf.entity.query.ForumArticleAttachmentQuery;
import fun.masttf.entity.query.ForumArticleQuery;
import fun.masttf.entity.query.ForumCommentQuery;
import fun.masttf.entity.vo.ResponseVo;
import fun.masttf.exception.BusinessException;
import fun.masttf.service.ForumArticleAttachmentService;
import fun.masttf.service.ForumArticleService;
import fun.masttf.service.ForumCommentService;

@RestController
@RequestMapping("/forum")
public class ForumArticleController extends ABaseController {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ForumArticleController.class);

    @Autowired
    private ForumArticleService forumArticleService;
    @Autowired
    private ForumArticleAttachmentService forumArticleAttachmentService;
    @Autowired
    private AdminConfig adminConfig;
    @Autowired
    private ForumCommentService forumCommentService;
    @RequestMapping("/loadArticle")
    public ResponseVo<Object> loadArticle(ForumArticleQuery query, Integer pageNo) {
        query.setOrderBy(ArticleOrderTypeEnum.NEW.getOderSql());
        query.setPageNo(pageNo);
        return getSuccessResponseVo(forumArticleService.findListByPage(query));
    }

    @RequestMapping("/delArticle")
    @GlobalInterceptor(checkParams = true)
    public ResponseVo<Object> delArticle(@VerifyParam(required = true) String articleIds) {
        forumArticleService.deleteArticle(articleIds);
        return getSuccessResponseVo(null);
    }

    @RequestMapping("/updateBoard")
    @GlobalInterceptor(checkParams = true)
    public ResponseVo<Object> updateBoard(@VerifyParam(required = true) String articleId,
                                          @VerifyParam(required = true) Integer pBoardId,
                                          @VerifyParam(required = true) Integer boardId) {   
        boardId = boardId == null ? 0 : boardId;
        forumArticleService.updateBoard(articleId, pBoardId, boardId);
        return getSuccessResponseVo(null);
    }

    @RequestMapping("/getAttachment")
    @GlobalInterceptor(checkParams = true)
    public ResponseVo<Object> getAttachment(@VerifyParam(required = true) String articleId) {   
        ForumArticleAttachmentQuery query = new ForumArticleAttachmentQuery();
        query.setArticleId(articleId);
        List<ForumArticleAttachment> list = forumArticleAttachmentService.findListByQuery(query);
        if(list.isEmpty()){
            throw new BusinessException("没有附件");
        }
        return getSuccessResponseVo(list.get(0));
    }

    @RequestMapping("/attachmentDownload")
    @GlobalInterceptor(checkParams = true)
    public void attachmentDownload(HttpServletRequest request,
                            HttpServletResponse response,
                            @VerifyParam(required = true) String fileId) {
        ForumArticleAttachment attachment = forumArticleAttachmentService.getByFileId(fileId);
        InputStream in = null;
        OutputStream out = null;
        String downloadFileName = attachment.getFileName();
        String filePath = adminConfig.getProjectFolder() + Constans.FILE_FOLDER_FILE + Constans.FILE_FOLDER_ATTACHMENT + attachment.getFilePath();
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

    @RequestMapping("/topArticle")
    @GlobalInterceptor(checkParams = true)
    public ResponseVo<Object> topArticle(@VerifyParam(required = true) String articleId, Integer topType) {   
        ArticleTopTypeEnum type = ArticleTopTypeEnum.getByType(topType);
        if(type == null){
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        ForumArticle updateInfo = new ForumArticle();
        updateInfo.setTopType(type.getType());
        forumArticleService.updateByArticleId(updateInfo, articleId);
        return getSuccessResponseVo(null);
    }

    @RequestMapping("/auditArticle")
    @GlobalInterceptor(checkParams = true)
    public ResponseVo<Object> auditArticle(@VerifyParam(required = true) String articleIds) {   
        forumArticleService.auditArticle(articleIds);
        return getSuccessResponseVo(null);
    }

    @RequestMapping("/loadComment")
    public ResponseVo<Object> loadComment(ForumCommentQuery query, Integer pageNo) {   
        query.setLoadChildren(true);
        query.setPageNo(pageNo);
        query.setOrderBy(CommentOrderTypeEnum.NEW.getOderSql());
        return getSuccessResponseVo(forumCommentService.findListByPage(query));
    }

    @RequestMapping("/loadComment4Article")
    public ResponseVo<Object> loadComment4Article(ForumCommentQuery query) {   
        query.setLoadChildren(true);
        query.setOrderBy(CommentOrderTypeEnum.NEW.getOderSql());
        //只查询一级评论
        query.setpCommentId(0);
        return getSuccessResponseVo(forumCommentService.findListByQuery(query));
    }

    @RequestMapping("/delComment")
    public ResponseVo<Object> delComment(String commentIds) {   
        forumCommentService.deleteComment(commentIds);
        return getSuccessResponseVo(null);
    }
     
}
