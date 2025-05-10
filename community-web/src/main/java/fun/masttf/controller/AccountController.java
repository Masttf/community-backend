package fun.masttf.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fun.masttf.entity.constans.Constans;
import fun.masttf.entity.dto.CreateImageCode;
import fun.masttf.entity.enums.ResponseCodeEnum;
import fun.masttf.entity.vo.ResponseVo;
import fun.masttf.exception.BusinessException;
import fun.masttf.service.EmailCodeService;
import fun.masttf.service.UserInfoService;
import fun.masttf.utils.StringTools;

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
    public void checkCode(HttpServletResponse response, HttpSession session, Integer type) throws IOException {
        CreateImageCode imageCode = new CreateImageCode(130, 40, 5, 10);
        response.setContentType("image/jpeg");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        String code = imageCode.getCode();
        // logger.info("验证码:{}", code);
        if (type == null || type == 0) { // 登入注册
            session.setAttribute(Constans.CHECK_CODE_KEY, code);
        } else if (type == 1) { // 获取邮箱
            session.setAttribute(Constans.CHECK_CODE_KEY_EMAIL, code);
        }
        imageCode.write(response.getOutputStream());
    }

    @RequestMapping("/sendEmail")
    public ResponseVo<Object> sendEmail(HttpSession session, String email, String checkCode, Integer type) {
        try {
            if (StringTools.isEmpty(email) || StringTools.isEmpty(checkCode) || type == null) {
                throw new BusinessException(ResponseCodeEnum.CODE_600);
            }
            String code = (String) session.getAttribute(Constans.CHECK_CODE_KEY_EMAIL);
            if (!code.equals(checkCode)) {
                throw new BusinessException("验证码错误");
            }
            emailCodeService.sendEmailCode(email, type);
            return getSuccessResponseVo(null);
        } finally {
            // 清除验证码
            session.removeAttribute(Constans.CHECK_CODE_KEY_EMAIL);
        }
    }

    /*
     * 注册
     */
    @RequestMapping("/register")
    public ResponseVo<Object> register(HttpSession session, String email, String emailCode, String nickName,
            String password, String checkCode) {
        try {
            if (StringTools.isEmpty(email) || StringTools.isEmpty(emailCode) || StringTools.isEmpty(nickName)
                    || StringTools.isEmpty(password) || StringTools.isEmpty(checkCode)) {
                throw new BusinessException(ResponseCodeEnum.CODE_600);
            }
            String code = (String) session.getAttribute(Constans.CHECK_CODE_KEY);
            if (!code.equals(checkCode)) {
                throw new BusinessException("验证码错误");
            }
            userInfoService.register(email, emailCode, nickName, password);
            return getSuccessResponseVo(null);
        } finally {
            // 清除验证码
            session.removeAttribute(Constans.CHECK_CODE_KEY);
        }
    }
}
