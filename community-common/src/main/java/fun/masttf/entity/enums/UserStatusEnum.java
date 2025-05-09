package fun.masttf.entity.enums;

public enum UserStatusEnum {
    /**
     * 正常
     */
    NORMAL(0, "正常"),

    /**
     * 禁用
     */
    DISABLE(1, "禁用");

    private Integer status;
    private String msg;

    UserStatusEnum(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
