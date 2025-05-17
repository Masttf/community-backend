package fun.masttf.controller;

import javax.servlet.http.HttpSession;

import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fun.masttf.annotation.GlobalInterceptor;
import fun.masttf.aspect.VerifyParam;
import fun.masttf.entity.dto.SessionWebUserDto;
import fun.masttf.entity.enums.ArticleOrderTypeEnum;
import fun.masttf.entity.enums.ArticleStatusEnum;
import fun.masttf.entity.enums.ResponseCodeEnum;
import fun.masttf.entity.enums.UserIntegralRecordOrderTypeEnum;
import fun.masttf.entity.enums.UserSexEnum;
import fun.masttf.entity.enums.UserStatusEnum;
import fun.masttf.entity.po.ForumArticle;
import fun.masttf.entity.po.UserInfo;
import fun.masttf.entity.po.UserIntegralRecord;
import fun.masttf.entity.query.ForumArticleQuery;
import fun.masttf.entity.query.LikeRecordQuery;
import fun.masttf.entity.query.UserIntegralRecordQuery;
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
    public ResponseVo<Object> loadUserArticle(HttpSession session,@VerifyParam(required = true) String userId, @VerifyParam(required = true) Integer type, @VerifyParam(required = true) Integer pageNo) {
        UserInfo userInfo = userInfoService.getByUserId(userId);
        if(userInfo == null || userInfo.getStatus().equals(UserStatusEnum.DISABLE.getStatus())) {
            throw new BusinessException(ResponseCodeEnum.CODE_404);
        }
        ForumArticleQuery articleQuery = new ForumArticleQuery();
        articleQuery.setOrderBy(ArticleOrderTypeEnum.NEW.getOderSql());
        articleQuery.setPageNo(pageNo);
        if(type == 0){
            articleQuery.setUserId(userId);
        }else if(type == 1){
            articleQuery.setCommentUserId(userId);
        }else if(type == 2){
            articleQuery.setLikeUserId(userId);
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
            @VerifyParam(required = true) String nickName,
            @VerifyParam(required = true) Integer Sex,
            @VerifyParam(max = 100) String personDesc,
            MultipartFile avatar){
        UserSexEnum sexEnum = UserSexEnum.getBySex(Sex);
        if(sexEnum == null){
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        SessionWebUserDto userDto = getUserInfoSession(session);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userDto.getUserId());
        userInfo.setPersonDescription(personDesc);
        userInfo.setSex(Sex);
        if(!userDto.getNickName().equals(nickName)){
            userInfo.setNickName(nickName);
        }
        userInfoService.updateUserInfo(userInfo, avatar);
        return getSuccessResponseVo(null);
    }

    @RequestMapping("/loadUserIntegralRecord")
    @GlobalInterceptor(checkLogin = true, checkParams = true)
    public ResponseVo<Object> loadUserIntegralRecord(HttpSession session, @VerifyParam(required = true) Integer pageNo,
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
}
