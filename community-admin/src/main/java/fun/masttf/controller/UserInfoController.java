package fun.masttf.controller;

import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fun.masttf.annotation.GlobalInterceptor;
import fun.masttf.annotation.VerifyParam;
import fun.masttf.entity.query.UserInfoQuery;
import fun.masttf.entity.vo.ResponseVo;
import fun.masttf.service.UserInfoService;

@RestController
@RequestMapping("/user")
public class UserInfoController extends ABaseController {
    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/loadUserList")
    public ResponseVo<Object> loadUserList(UserInfoQuery query) {
        query.setOrderBy("join_time desc");
        return getSuccessResponseVo(userInfoService.findListByPage(query));
    }

    @RequestMapping("/updateUserStatus")
    @GlobalInterceptor(checkParams = true)
    public ResponseVo<Object> updateUserStatus(@VerifyParam(required = true) String userId,
                                               @VerifyParam(required = true) Integer status) {
        userInfoService.updateUserStatus(userId, status);
        return getSuccessResponseVo(null);
    }

    @RequestMapping("/sendUserMessage")
    @GlobalInterceptor(checkParams = true)
    public ResponseVo<Object> sendUserMessage(@VerifyParam(required = true) String userId,
                                               @VerifyParam(required = true) String message,
                                               @VerifyParam(required = true) Integer integral) {
        userInfoService.sendUserMessage(userId, message, integral);
        return getSuccessResponseVo(null);
    }
}
