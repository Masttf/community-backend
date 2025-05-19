package fun.masttf.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fun.masttf.annotation.GlobalInterceptor;
import fun.masttf.annotation.VerifyParam;
import fun.masttf.config.AdminConfig;
import fun.masttf.entity.constans.Constans;
import fun.masttf.entity.dto.CreateImageCode;
import fun.masttf.entity.dto.SessionAdminUserDto;
import fun.masttf.entity.dto.SysSettingDto;
import fun.masttf.entity.enums.CheckCodeTypeEnum;
import fun.masttf.entity.vo.ResponseVo;
import fun.masttf.exception.BusinessException;
import fun.masttf.utils.SysCacheUtils;

@RestController
public class AccountController extends ABaseController {

    @Autowired
    private AdminConfig adminConfig;
    /*
     * 验证码
     */
    @RequestMapping("/checkCode")
    @GlobalInterceptor(checkParams = true)
    public void checkCode(HttpServletResponse response, HttpSession session) throws IOException {
        CreateImageCode imageCode = new CreateImageCode(130, 40, 5, 10);
        response.setContentType("image/jpeg");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        String code = imageCode.getCode();
        session.setAttribute(CheckCodeTypeEnum.COMMON.getSessionAttribute(), code);
        imageCode.write(response.getOutputStream());
    }


    @RequestMapping("/login")
    @GlobalInterceptor(checkParams = true)
    public ResponseVo<Object> login(HttpSession session,
            HttpServletRequest request,
            @VerifyParam(required = true) String account, 
            @VerifyParam(required = true) String password, 
            @VerifyParam(required = true) String checkCode) {
        try {
            String code = (String) session.getAttribute(CheckCodeTypeEnum.COMMON.getSessionAttribute());
            if (code == null || !code.equals(checkCode)) {
                throw new BusinessException("图片验证码错误");
            }
            if(!adminConfig.getAdminAcount().equals(account) || !adminConfig.getAdminPassword().equals(password)){
                throw new BusinessException("账号或密码错误");
            }
            SessionAdminUserDto admin = new SessionAdminUserDto();
            admin.setAccount(account);
            session.setAttribute(Constans.SESSION_KEY, admin);
            return getSuccessResponseVo(admin);
        } finally {
            // 清除验证码
            session.removeAttribute(CheckCodeTypeEnum.COMMON.getSessionAttribute());
        }
    }

    @RequestMapping("/logout")
    public ResponseVo<Object> logout(HttpSession session) {
        session.invalidate();
        return getSuccessResponseVo(null);
    }

    @RequestMapping("/getSysSetting")
    public ResponseVo<Object> getSysSetting() {
        SysSettingDto sttingDto = SysCacheUtils.getSysSetting();
        return getSuccessResponseVo(sttingDto);
    }
}
