package fun.masttf.entity.enums;

public enum MessageStatusEnum {
    NO_READ(1, "未读"),
    READ(2, "已读");

    private Integer status;
    private String desc;

    private MessageStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

}
