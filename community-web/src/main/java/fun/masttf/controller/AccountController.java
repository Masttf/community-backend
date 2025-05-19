package fun.masttf.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fun.masttf.annotation.GlobalInterceptor;
import fun.masttf.annotation.VerifyParam;
import fun.masttf.entity.constans.Constans;
import fun.masttf.entity.dto.CreateImageCode;
import fun.masttf.entity.dto.SessionWebUserDto;
import fun.masttf.entity.dto.SysSetting4CommentDto;
import fun.masttf.entity.dto.SysSettingDto;
import fun.masttf.entity.enums.CheckCodeTypeEnum;
import fun.masttf.entity.enums.EmailTypeEnum;
import fun.masttf.entity.enums.ResponseCodeEnum;
import fun.masttf.entity.enums.VerifyRegexEnum;
import fun.masttf.entity.vo.ResponseVo;
import fun.masttf.exception.BusinessException;
import fun.masttf.service.EmailCodeService;
import fun.masttf.service.UserInfoService;
import fun.masttf.utils.SysCacheUtils;

@RestController
public class AccountController extends ABaseController {
    // private static final org.slf4j.Logger logger =
    // org.slf4j.LoggerFactory.getLogger(AccountController.class);

    @Autowired
    @Qualifier("emailCodeService")
    private EmailCodeService emailCodeService;

    @Autowired
    @Qualifier("userInfoService")
    private UserInfoService userInfoService;

    /*
     * 验证码
     */
    @RequestMapping("/checkCode")
    @GlobalInterceptor(checkParams = true)
    public void checkCode(HttpServletResponse response, HttpSession session, @VerifyParam(required = true) Integer type) throws IOException {
        CheckCodeTypeEnum checkCodeTypeEnum = CheckCodeTypeEnum.getByType(type);
        if (checkCodeTypeEnum == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        CreateImageCode imageCode = new CreateImageCode(130, 40, 5, 10);
        response.setContentType("image/jpeg");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        String code = imageCode.getCode();
        session.setAttribute(checkCodeTypeEnum.getSessionAttribute(), code);
        imageCode.write(response.getOutputStream());
    }

    @RequestMapping("/sendEmail")
    @GlobalInterceptor(checkParams = true)
    public ResponseVo<Object> sendEmail(HttpSession session, 
                    @VerifyParam(required = true) String email, 
                    @VerifyParam(required = true) String checkCode, 
                    @VerifyParam(required = true) Integer type) {
        try {
            String code = (String) session.getAttribute(CheckCodeTypeEnum.EMAIL.getSessionAttribute());
            if (!code.equals(checkCode)) {
                throw new BusinessException("图片验证码错误");
            }
            EmailTypeEnum emailTypeEnum = EmailTypeEnum.getByType(type);
            if (emailTypeEnum == null) {
                throw new BusinessException(ResponseCodeEnum.CODE_600);
            }
            emailCodeService.sendEmailCode(email, type);
            return getSuccessResponseVo(null);
        } finally {
            // 清除验证码
            session.removeAttribute(CheckCodeTypeEnum.EMAIL.getSessionAttribute());
        }
    }

    /*
     * 注册
     */
    @RequestMapping("/register")
    @GlobalInterceptor(checkParams = true)
    public ResponseVo<Object> register(HttpSession session,
            @VerifyParam(required = true, regex = VerifyRegexEnum.EMAIL) String email, 
            @VerifyParam(required = true) String emailCode, 
            @VerifyParam(required = true, max = 20) String nickName,
            @VerifyParam(required = true, min = 8, max = 18, regex = VerifyRegexEnum.PASSWORD) String password, 
            @VerifyParam(required = true) String checkCode) {
        try {
            String code = (String) session.getAttribute(CheckCodeTypeEnum.COMMON.getSessionAttribute());
            if (code == null || !code.equals(checkCode)) {
                throw new BusinessException("图片验证码错误");
            }
            userInfoService.register(email, emailCode, nickName, password);
            return getSuccessResponseVo(null);
        } finally {
            // 清除验证码
            session.removeAttribute(CheckCodeTypeEnum.COMMON.getSessionAttribute());
        }
    }

    @RequestMapping("/login")
    @GlobalInterceptor(checkParams = true)
    public ResponseVo<Object> login(HttpSession session,
            HttpServletRequest request,
            @VerifyParam(required = true) String email, 
            @VerifyParam(required = true) String password, 
            @VerifyParam(required = true) String checkCode) {
        try {
            String code = (String) session.getAttribute(CheckCodeTypeEnum.COMMON.getSessionAttribute());
            if (code == null || !code.equals(checkCode)) {
                throw new BusinessException("图片验证码错误");
            }
            SessionWebUserDto sessionWebUserDto = userInfoService.login(email, password, getIpAddr(request));
            session.setAttribute(Constans.SESSION_KEY, sessionWebUserDto);
            return getSuccessResponseVo(sessionWebUserDto);
        } finally {
            // 清除验证码
            session.removeAttribute(CheckCodeTypeEnum.COMMON.getSessionAttribute());
        }
    }

    /*
     * 获取登录用户信息
     */
    @RequestMapping("/getUserInfo")
    public ResponseVo<Object> getUserInfo(HttpSession session) {
        return getSuccessResponseVo(getUserInfoSession(session));
    }

    @RequestMapping("/logout")
    public ResponseVo<Object> logout(HttpSession session) {
        session.invalidate();
        return getSuccessResponseVo(null);
    }

    @RequestMapping("/getSysSetting")
    public ResponseVo<Object> getSysSetting() {
        SysSettingDto sttingDto = SysCacheUtils.getSysSetting();
        SysSetting4CommentDto commentDto = sttingDto.getCommentSetting();
        Map<String, Object> result = new HashMap<>();
        result.put("commentOpen", commentDto.getCommentOpen());
        return getSuccessResponseVo(result);
    }

    @RequestMapping("/resetPwd")
    @GlobalInterceptor(checkParams = true)
    public ResponseVo<Object> resetPwd(HttpSession session,
            @VerifyParam(required = true) String email, 
            @VerifyParam(required = true) String emailCode, 
            @VerifyParam(required = true, min = 8, max = 18, regex = VerifyRegexEnum.PASSWORD) String password, 
            @VerifyParam(required = true) String checkCode) {
        try {
            String code = (String) session.getAttribute(CheckCodeTypeEnum.COMMON.getSessionAttribute());
            if (code == null || !code.equals(checkCode)) {
                throw new BusinessException("图片验证码错误");
            }
            userInfoService.resetPwd(email, emailCode, password);
            return getSuccessResponseVo(null);
        } finally {
            // 清除验证码
            session.removeAttribute(CheckCodeTypeEnum.COMMON.getSessionAttribute());
        }
    }
}
