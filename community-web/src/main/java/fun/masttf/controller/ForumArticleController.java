package fun.masttf.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fun.masttf.annotation.GlobalInterceptor;
import fun.masttf.aspect.VerifyParam;
import fun.masttf.entity.constans.Constans;
import fun.masttf.entity.dto.SessionWebUserDto;
import fun.masttf.entity.enums.ArticleOrderTypeEnum;
import fun.masttf.entity.enums.ArticleStatusEnum;
import fun.masttf.entity.enums.OperRecordOpTypeEnum;
import fun.masttf.entity.enums.ResponseCodeEnum;
import fun.masttf.entity.po.ForumArticle;
import fun.masttf.entity.po.ForumArticleAttachment;
import fun.masttf.entity.po.LikeRecord;
import fun.masttf.entity.query.ForumArticleAttachmentQuery;
import fun.masttf.entity.query.ForumArticleQuery;
import fun.masttf.entity.vo.ForumArticleAttachmentVo;
import fun.masttf.entity.vo.ForumArticleDetailVo;
import fun.masttf.entity.vo.PaginationResultVo;
import fun.masttf.entity.vo.ResponseVo;
import fun.masttf.entity.vo.Web.ForumArticleVo;
import fun.masttf.exception.BusinessException;
import fun.masttf.service.ForumArticleAttachmentService;
import fun.masttf.service.ForumArticleService;
import fun.masttf.service.LikeRecordService;
import fun.masttf.utils.CopyTools;

@RestController
@RequestMapping("/forum")
public class ForumArticleController extends ABaseController {

    @Autowired
    private ForumArticleService forumArticleService;
    @Autowired
    private ForumArticleAttachmentService forumArticleAttachmentService;
    @Autowired
    private LikeRecordService likeRecordService;

    @RequestMapping("/loadArticle")
    public ResponseVo<Object> loadArticle(HttpSession session, Integer boardId, Integer pBoardId, Integer orderType, Integer pageNo) {
        
        ForumArticleQuery articleQuery = new ForumArticleQuery();
        articleQuery.setBoardId((boardId == null || boardId == 0) ? null : boardId);
        articleQuery.setpBoardId(pBoardId);
        articleQuery.setPageNo(pageNo);
        SessionWebUserDto userDto = (SessionWebUserDto) session.getAttribute(Constans.SESSION_KEY);
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
        SessionWebUserDto userDto = (SessionWebUserDto) session.getAttribute(Constans.SESSION_KEY);
        ForumArticle article = forumArticleService.readArticle(articleId);
        Boolean canShowNoAudit = (userDto == null || !article.getUserId().equals(userDto.getUserId()));
        if(article == null || article.getStatus() == ArticleStatusEnum.DEL.getStatus() || (article.getStatus() == ArticleStatusEnum.NO_AUDIT.getStatus() && !canShowNoAudit)) {
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
}
