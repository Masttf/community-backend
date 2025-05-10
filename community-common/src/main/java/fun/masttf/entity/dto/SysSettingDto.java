package fun.masttf.entity.dto;

public class SysSettingDto {
    private SysSetting4AuditDto auditSetting;
    private SysSetting4EmailDto emailSetting;
    private SysSetting4LikeDto likeSetting;
    private SysSetting4Register registerSetting;
    private SysSetting4PostDto postSetting;
    private SysSetting4CommentDto commentSetting;

    public SysSetting4AuditDto getAuditSetting() {
        return auditSetting;
    }

    public void setAuditSetting(SysSetting4AuditDto auditSetting) {
        this.auditSetting = auditSetting;
    }

    public SysSetting4EmailDto getEmailSetting() {
        return emailSetting;
    }

    public void setEmailSetting(SysSetting4EmailDto emailSetting) {
        this.emailSetting = emailSetting;
    }

    public SysSetting4LikeDto getLikeSetting() {
        return likeSetting;
    }

    public void setLikeSetting(SysSetting4LikeDto likeSetting) {
        this.likeSetting = likeSetting;
    }

    public SysSetting4Register getRegisterSetting() {
        return registerSetting;
    }

    public void setRegisterSetting(SysSetting4Register registerSetting) {
        this.registerSetting = registerSetting;
    }

    public SysSetting4PostDto getPostSetting() {
        return postSetting;
    }

    public void setPostSetting(SysSetting4PostDto postSetting) {
        this.postSetting = postSetting;
    }

    public SysSetting4CommentDto getCommentSetting() {
        return commentSetting;
    }

    public void setCommentSetting(SysSetting4CommentDto commentSetting) {
        this.commentSetting = commentSetting;
    }

}
