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
import fun.masttf.entity.enums.PageSize;
import fun.masttf.entity.enums.ResponseCodeEnum;
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
}
