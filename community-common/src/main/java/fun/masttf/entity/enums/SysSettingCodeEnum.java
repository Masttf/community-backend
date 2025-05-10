package fun.masttf.entity.enums;

public enum SysSettingCodeEnum {
    AUDIT("audit", "fun.masttf.entity.dto.SysSetting4AuditDto", "auditSetting", "审核设置"),
    EMAIL("email", "fun.masttf.entity.dto.SysSetting4EmailDto", "emailSetting", "邮件设置"),
    LIKE("like", "fun.masttf.entity.dto.SysSetting4LikeDto", "likeSetting", "点赞设置"),
    REGISTER("register", "fun.masttf.entity.dto.SysSetting4Register", "registerSetting", "注册设置"),
    POST("post", "fun.masttf.entity.dto.SysSetting4PostDto", "postSetting", "发帖设置"),
    COMMENT("comment", "fun.masttf.entity.dto.SysSetting4CommentDto", "commentSetting", "评论设置");

    private String code;
    private String classz;
    private String propName;
    private String desc;

    private SysSettingCodeEnum(String code, String classz, String propName, String desc) {
        this.code = code;
        this.classz = classz;
        this.propName = propName;
        this.desc = desc;
    }

    public static SysSettingCodeEnum getByCode(String code) {
        for (SysSettingCodeEnum setting : SysSettingCodeEnum.values()) {
            if (setting.getCode().equals(code)) {
                return setting;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getClassz() {
        return classz;
    }

    public String getPropName() {
        return propName;
    }

    public String getDesc() {
        return desc;
    }

}
