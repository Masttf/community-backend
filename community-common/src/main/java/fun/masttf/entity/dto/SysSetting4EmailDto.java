package fun.masttf.entity.dto;

/*
 * 发送邮件设置
 */
public class SysSetting4EmailDto {
    /*
     * 邮件标题
     */
    private String emailTittle;
    /*
     * 邮件内容
     */
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
