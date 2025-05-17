package fun.masttf.entity.enums;

public enum EmailTypeEnum {
    REGISTER(0, "注册"),
    RESET_PASSWORD(1, "找回密码");
    private Integer type;
    private String desc;

    private EmailTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }
    public static EmailTypeEnum getByType(Integer type) {
        for (EmailTypeEnum emailTypeEnum : EmailTypeEnum.values()) {
            if (emailTypeEnum.getType().equals(type)) {
                return emailTypeEnum;
            }
        }
        return null;
    }
    public Integer getType() {
        return type;
    }
    public String getDesc() {
        return desc;
    }

}
