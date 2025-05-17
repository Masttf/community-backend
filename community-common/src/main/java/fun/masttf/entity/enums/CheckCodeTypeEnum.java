package fun.masttf.entity.enums;

import fun.masttf.entity.constans.Constans;

public enum CheckCodeTypeEnum {
    COMMON(0, Constans.CHECK_CODE_KEY , "登录注册重置密码等"),
    EMAIL(1, Constans.CHECK_CODE_KEY_EMAIL, "获取邮箱");
    private Integer type;
    private String sessionAttribute;
    private String desc;

    private CheckCodeTypeEnum(Integer type, String sessionAttribute, String desc) {
        this.type = type;
        this.sessionAttribute = sessionAttribute;
        this.desc = desc;
    }
    public static CheckCodeTypeEnum getByType(Integer type) {
        for (CheckCodeTypeEnum checkCodeTypeEnum : CheckCodeTypeEnum.values()) {
            if (checkCodeTypeEnum.getType().equals(type)) {
                return checkCodeTypeEnum;
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
    public String getSessionAttribute() {
        return sessionAttribute;
    }
}
