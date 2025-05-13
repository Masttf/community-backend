package fun.masttf.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fun.masttf.annotation.GlobalInterceptor;
import fun.masttf.aspect.VerifyParam;
import fun.masttf.entity.dto.SessionWebUserDto;
import fun.masttf.entity.enums.ArticleStatusEnum;
import fun.masttf.entity.enums.CommentOrderTypeEnum;
import fun.masttf.entity.enums.OperRecordOpTypeEnum;
import fun.masttf.entity.enums.PageSize;
import fun.masttf.entity.enums.ResponseCodeEnum;
import fun.masttf.entity.po.ForumComment;
import fun.masttf.entity.po.LikeRecord;
import fun.masttf.entity.query.ForumCommentQuery;
import fun.masttf.entity.vo.ResponseVo;
import fun.masttf.exception.BusinessException;
import fun.masttf.service.ForumCommentService;
import fun.masttf.service.LikeRecordService;
import fun.masttf.utils.SysCacheUtils;

@RestController
@RequestMapping("/comment")
public class ForumCommentController extends ABaseController {
    @Autowired
    private ForumCommentService forumCommentService;
    @Autowired
    private LikeRecordService likeRecordService;

    /*
     * 先查询一级列表，再查询二级列表然后组合，
     * 这里限制了分页，所以二级列表用 sql 中in来查询 不用left join
     * 点赞状态，使用相关子查询，据说会被优化为join
     */
    @RequestMapping("/loadComment")
    @GlobalInterceptor(checkParams = true)
    public ResponseVo<Object> loadComment(HttpSession session,@VerifyParam(required = true) String articleId, Integer pageNo, Integer orderType) {
        if(!SysCacheUtils.getSysSetting().getCommentSetting().getCommentOpen()){
            throw new BusinessException(ResponseCodeEnum.CODE_404);
        }
        ForumCommentQuery query = new ForumCommentQuery();
        query.setArticleId(articleId);
        query.setPageNo(pageNo);
        query.setPageSize(PageSize.SIZE50.getSize());
        query.setpCommentId(0);
        orderType = orderType == null ? CommentOrderTypeEnum.HOT.getType() : orderType;
        query.setOrderBy(CommentOrderTypeEnum.getByType(orderType).getOderSql());
        query.setLoadChildren(true);
        SessionWebUserDto userDto = getUserInfoSession(session);
        if(userDto != null) {
            query.setQueryLikeType(true);
            query.setCurrentUserId(userDto.getUserId());
        } else{
            query.setStatus(ArticleStatusEnum.AUDIT.getStatus());
        }

        return getSuccessResponseVo(forumCommentService.findListByPage(query));
    }

    @RequestMapping("/doLike")
    @GlobalInterceptor(checkLogin = true,checkParams = true)
    public ResponseVo<Object> doLike(HttpSession session,
                    @VerifyParam(required = true) Integer commentId) {
        SessionWebUserDto userDto = getUserInfoSession(session);
        String objectId = String.valueOf(commentId);
        likeRecordService.doLike(objectId, userDto.getUserId(), userDto.getNickName(), OperRecordOpTypeEnum.COMMENT_LIKE);
        LikeRecord likeRecord = likeRecordService.getByObjectIdAndUserIdAndOpType(objectId, userDto.getUserId(), OperRecordOpTypeEnum.COMMENT_LIKE.getType());
        ForumComment comment = forumCommentService.getByCommentId(commentId);
        comment.setLikeType(likeRecord == null ? 0 : 1);
        return getSuccessResponseVo(comment);
    }

    @RequestMapping("/changeTopType")
    @GlobalInterceptor(checkLogin = true,checkParams = true)
    public ResponseVo<Object> changeTopType(HttpSession session,
                    @VerifyParam(required = true) Integer commentId,
                    @VerifyParam(required = true) Integer topType) {
        forumCommentService.changeTopType(getUserInfoSession(session).getUserId(), commentId, topType);
        return getSuccessResponseVo(null);
    }
}
