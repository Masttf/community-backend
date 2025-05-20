package fun.masttf.entity.dto;

import fun.masttf.annotation.VerifyParam;

/*
 * 发送邮件设置
 */
public class SysSetting4EmailDto {
    /*
     * 邮件标题
     */
    @VerifyParam(required = true)
    private String emailTittle;
    /*
     * 邮件内容
     */
    @VerifyParam(required = true)
    private String emailContent;

    public String getEmailTittle() {
        return emailTittle;
    }

    public void setEmailTittle(String emailTittle) {
        this.emailTittle = emailTittle;
    }

    public String getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }

}
