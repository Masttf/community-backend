package fun.masttf.entity.enums;

public enum CommentStatusEnum {
    DEL(-1, "已删除"),
    NO_AUDIT(0, "未审核"),
    AUDIT(1, "已审核"),;

    private Integer status;
    private String desc;
    private CommentStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
    public static CommentStatusEnum getByStatus(Integer status) {
        for (CommentStatusEnum commentStatus : CommentStatusEnum.values()) {
            if (commentStatus.getStatus().equals(status)) {
                return commentStatus;
            }
        }
        return null;
    }
    public Integer getStatus() {
        return status;
    }
    public String getDesc() {
        return desc;
    }

    
}
