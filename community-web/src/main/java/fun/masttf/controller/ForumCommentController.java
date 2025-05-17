package fun.masttf.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fun.masttf.annotation.GlobalInterceptor;
import fun.masttf.aspect.VerifyParam;
import fun.masttf.entity.dto.SessionWebUserDto;
import fun.masttf.entity.enums.CommentOrderTypeEnum;
import fun.masttf.entity.enums.CommentStatusEnum;
import fun.masttf.entity.enums.CommentTopTypeEnum;
import fun.masttf.entity.enums.RecordOpTypeEnum;
import fun.masttf.entity.enums.PageSize;
import fun.masttf.entity.enums.ResponseCodeEnum;
import fun.masttf.entity.po.ForumComment;
import fun.masttf.entity.po.LikeRecord;
import fun.masttf.entity.query.ForumCommentQuery;
import fun.masttf.entity.vo.ResponseVo;
import fun.masttf.exception.BusinessException;
import fun.masttf.service.ForumCommentService;
import fun.masttf.service.LikeRecordService;
import fun.masttf.utils.StringTools;
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
            query.setStatus(CommentStatusEnum.AUDIT.getStatus());
        }

        return getSuccessResponseVo(forumCommentService.findListByPage(query));
    }

    @RequestMapping("/doLike")
    @GlobalInterceptor(checkLogin = true,checkParams = true)
    public ResponseVo<Object> doLike(HttpSession session,
                    @VerifyParam(required = true) Integer commentId) {
        SessionWebUserDto userDto = getUserInfoSession(session);
        String objectId = String.valueOf(commentId);
        likeRecordService.doLike(objectId, userDto.getUserId(), userDto.getNickName(), RecordOpTypeEnum.COMMENT_LIKE);
        LikeRecord likeRecord = likeRecordService.getByObjectIdAndUserIdAndOpType(objectId, userDto.getUserId(), RecordOpTypeEnum.COMMENT_LIKE.getType());
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

    @RequestMapping("/postComment")
    @GlobalInterceptor(checkLogin = true,checkParams = true)
    public ResponseVo<Object> postComment(
                    HttpSession session,
                    @VerifyParam(required = true) String articleId,
                    @VerifyParam(min = 5, max = 800) String content,
                    @VerifyParam(required = true) Integer pCommentId,
                    MultipartFile image,
                    String replyUserId) { 
        
        if(!SysCacheUtils.getSysSetting().getCommentSetting().getCommentOpen()){
            throw new BusinessException(ResponseCodeEnum.CODE_404);
        }
        if(image == null && StringTools.isEmpty(content)) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        SessionWebUserDto userDto = getUserInfoSession(session);
        content = StringTools.escapeHtml(content);
        ForumComment comment = new ForumComment();
        comment.setArticleId(articleId);
        comment.setContent(content);
        comment.setpCommentId(pCommentId);
        comment.setUserId(userDto.getUserId());
        comment.setPostTime(new Date());
        comment.setReplyUserId(replyUserId);
        comment.setNickName(userDto.getNickName());
        comment.setUserIpAddress(userDto.getProvice());
        comment.setTopType(CommentTopTypeEnum.NOT_TOP.getType());
        forumCommentService.postComment(comment, image);
        //二级评论返回评论列表
        if(pCommentId != 0){
            ForumCommentQuery commentQuery = new ForumCommentQuery();
            commentQuery.setpCommentId(pCommentId);
            commentQuery.setArticleId(articleId);
            commentQuery.setOrderBy(CommentOrderTypeEnum.SEND.getOderSql());
            List<ForumComment> list = forumCommentService.findListByPage(commentQuery).getList();
            return getSuccessResponseVo(list);
        }

        return getSuccessResponseVo(comment);
    }
    
}
