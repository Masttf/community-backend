package fun.masttf.entity.enums;

public enum UserStatusEnum {

    DISABLE(0, "禁用"),
    NORMAL(1, "正常");

    private Integer status;
    private String msg;

    private UserStatusEnum(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public static UserStatusEnum getByStatus(Integer status) {
        for (UserStatusEnum userStatusEnum : UserStatusEnum.values()) {
            if (userStatusEnum.getStatus().equals(status)) {
                return userStatusEnum;
            }
        }
        return null;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
