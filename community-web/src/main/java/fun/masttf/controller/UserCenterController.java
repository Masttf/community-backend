package fun.masttf.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fun.masttf.annotation.GlobalInterceptor;
import fun.masttf.annotation.VerifyParam;
import fun.masttf.entity.dto.SessionWebUserDto;
import fun.masttf.entity.enums.ArticleOrderTypeEnum;
import fun.masttf.entity.enums.ArticleStatusEnum;
import fun.masttf.entity.enums.LoadUserArticleTypeEnum;
import fun.masttf.entity.enums.MessageOrderTypeEnum;
import fun.masttf.entity.enums.MessageTypeEnum;
import fun.masttf.entity.enums.ResponseCodeEnum;
import fun.masttf.entity.enums.UserIntegralRecordOrderTypeEnum;
import fun.masttf.entity.enums.UserSexEnum;
import fun.masttf.entity.enums.UserStatusEnum;
import fun.masttf.entity.po.ForumArticle;
import fun.masttf.entity.po.UserInfo;
import fun.masttf.entity.po.UserIntegralRecord;
import fun.masttf.entity.po.UserMessage;
import fun.masttf.entity.query.ForumArticleQuery;
import fun.masttf.entity.query.LikeRecordQuery;
import fun.masttf.entity.query.UserIntegralRecordQuery;
import fun.masttf.entity.query.UserMessageQuery;
import fun.masttf.entity.vo.ForumArticleVo;
import fun.masttf.entity.vo.PaginationResultVo;
import fun.masttf.entity.vo.ResponseVo;
import fun.masttf.entity.vo.UserInfoVo;
import fun.masttf.entity.vo.UserIntegralRecordVo;
import fun.masttf.exception.BusinessException;
import fun.masttf.service.ForumArticleService;
import fun.masttf.service.LikeRecordService;
import fun.masttf.service.UserInfoService;
import fun.masttf.service.UserIntegralRecordService;
import fun.masttf.service.UserMessageService;
import fun.masttf.utils.CopyTools;

@RestController
@RequestMapping("/user")
public class UserCenterController extends ABaseController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private ForumArticleService forumArticleService;
    @Autowired
    private LikeRecordService likeRecordService;
    @Autowired
    private UserIntegralRecordService userIntegralRecordService;
    @Autowired
    private UserMessageService userMessageService;

    @RequestMapping("/getUserInfo")
    @GlobalInterceptor(checkParams = true)
    public ResponseVo<Object> getUserInfo(@VerifyParam(required = true) String userId) {
        UserInfo userInfo = userInfoService.getByUserId(userId);
        if(userInfo == null || userInfo.getStatus().equals(UserStatusEnum.DISABLE.getStatus())) {
            throw new BusinessException(ResponseCodeEnum.CODE_404);
        }
        ForumArticleQuery forumArticleQuery = new ForumArticleQuery();
        forumArticleQuery.setUserId(userId);
        forumArticleQuery.setStatus(ArticleStatusEnum.AUDIT.getStatus());
        Integer postCount = forumArticleService.findCountByQuery(forumArticleQuery);

        LikeRecordQuery likeRecordQuery = new LikeRecordQuery();
        likeRecordQuery.setAuthorUserId(userId);
        Integer likeCount = likeRecordService.findCountByQuery(likeRecordQuery);
        UserInfoVo userInfoVo = CopyTools.copy(userInfo, UserInfoVo.class);
        userInfoVo.setPostCount(postCount);
        userInfoVo.setLikeCount(likeCount);
        return getSuccessResponseVo(userInfoVo);
    }

    @RequestMapping("/loadUserArticle")
    @GlobalInterceptor(checkParams = true)
    public ResponseVo<Object> loadUserArticle(HttpSession session,@VerifyParam(required = true) String userId, Integer type, Integer pageNo) {
        LoadUserArticleTypeEnum loadUserArticleTypeEnum = LoadUserArticleTypeEnum.getByType(type);
        if(loadUserArticleTypeEnum == null){
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        UserInfo userInfo = userInfoService.getByUserId(userId);
        if(userInfo == null || userInfo.getStatus().equals(UserStatusEnum.DISABLE.getStatus())) {
            throw new BusinessException(ResponseCodeEnum.CODE_404);
        }
        ForumArticleQuery articleQuery = new ForumArticleQuery();
        articleQuery.setOrderBy(ArticleOrderTypeEnum.NEW.getOderSql());
        articleQuery.setPageNo(pageNo);
        switch (loadUserArticleTypeEnum){
            case POST_ARTICLE:
                articleQuery.setUserId(userId);
                break;
            case COMMENT_ARTICLE:
                articleQuery.setCommentUserId(userId);
                break;
            case LIKE_ARTICLE:
                articleQuery.setLikeUserId(userId);
                break;
        }
        SessionWebUserDto userDto = getUserInfoSession(session);
        if(userDto != null){
            articleQuery.setCurrentUserId(userDto.getUserId());
        }else{
            articleQuery.setStatus(ArticleStatusEnum.AUDIT.getStatus());
        }
        PaginationResultVo<ForumArticle> paginationResultVo = forumArticleService.findListByPage(articleQuery);
        return getSuccessResponseVo(convert2PaginationVo(paginationResultVo, ForumArticleVo.class));
    }

    @RequestMapping("/updateUserInfo")
    @GlobalInterceptor(checkLogin = true, checkParams = true)
    public ResponseVo<Object> updateUserInfo(HttpSession session,
            Integer sex,
            @VerifyParam(max = 100) String personDesc,
            MultipartFile avatar){
        if(sex != null){
            UserSexEnum sexEnum = UserSexEnum.getBySex(sex);
            if(sexEnum == null){
                throw new BusinessException(ResponseCodeEnum.CODE_600);
            }
        }
        SessionWebUserDto userDto = getUserInfoSession(session);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userDto.getUserId());
        userInfo.setPersonDescription(personDesc);
        userInfo.setSex(sex);
        userInfoService.updateUserInfo(userInfo, avatar);
        return getSuccessResponseVo(null);
    }

    @RequestMapping("/loadUserIntegralRecord")
    @GlobalInterceptor(checkLogin = true, checkParams = true)
    public ResponseVo<Object> loadUserIntegralRecord(HttpSession session, Integer pageNo,
            String startTime, String endTime) {
        SessionWebUserDto userDto = getUserInfoSession(session);
        UserIntegralRecordQuery userIntegralRecordQuery = new UserIntegralRecordQuery();
        userIntegralRecordQuery.setUserId(userDto.getUserId());
        userIntegralRecordQuery.setPageNo(pageNo);
        userIntegralRecordQuery.setCreateTimeStart(startTime);
        userIntegralRecordQuery.setCreateTimeEnd(endTime);
        userIntegralRecordQuery.setOrderBy(UserIntegralRecordOrderTypeEnum.NEW.getOderSql());
        PaginationResultVo<UserIntegralRecord> paginationResultVo = userIntegralRecordService.findListByPage(userIntegralRecordQuery);
        return getSuccessResponseVo(convert2PaginationVo(paginationResultVo, UserIntegralRecordVo.class));
    }

    /*
     * 获取未读消息数量
     */
    @RequestMapping("/getUserMessageCount")
    @GlobalInterceptor(checkLogin = true, checkParams = true)
    public ResponseVo<Object> getUserMessageCount(HttpSession session) {
        SessionWebUserDto userDto = getUserInfoSession(session);
        return getSuccessResponseVo(userMessageService.getUserMessageCount(userDto.getUserId()));
    }

    @RequestMapping("/loadMessageList")
    @GlobalInterceptor(checkLogin = true, checkParams = true)
    public ResponseVo<Object> loadMessageList(HttpSession session,@VerifyParam(required = true) String code, Integer PageNo) {
        MessageTypeEnum messageTypeEnum = MessageTypeEnum.getByCode(code);
        if(messageTypeEnum == null){
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        SessionWebUserDto userDto = getUserInfoSession(session);
        UserMessageQuery userMessageQuery = new UserMessageQuery();
        userMessageQuery.setPageNo(PageNo);
        userMessageQuery.setReceivedUserId(userDto.getUserId());
        userMessageQuery.setMessageType(messageTypeEnum.getType());
        userMessageQuery.setOrderBy(MessageOrderTypeEnum.NEW.getOderSql());
        PaginationResultVo<UserMessage> paginationResultVo = userMessageService.findListByPage(userMessageQuery);
        if(PageNo == null || PageNo == 1){
            userMessageService.readMessageByType(userDto.getUserId(), messageTypeEnum.getType());
        }
        return getSuccessResponseVo(paginationResultVo);
    }
}
