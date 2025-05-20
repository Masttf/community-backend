package fun.masttf.entity.dto;

import fun.masttf.annotation.VerifyParam;

/*
 * 注册设置
 */
public class SysSetting4Register {
    // 注册欢迎语
    @VerifyParam(required = true)
    private String registerWelcomeInfo;

    public String getRegisterWelcomeInfo() {
        return registerWelcomeInfo;
    }

    public void setRegisterWelcomeInfo(String registerWelcomeInfo) {
        this.registerWelcomeInfo = registerWelcomeInfo;
    }

}
