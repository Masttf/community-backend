package fun.masttf.entity.enums;

public enum ArticleOrCommentStatusEnum {
    DEL(-1, "已删除"),
    NO_AUDIT(0, "未审核"),
    AUDIT(1, "已审核"),;

    private Integer status;
    private String desc;
    private ArticleOrCommentStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }
    public static ArticleOrCommentStatusEnum getByStatus(Integer status) {
        for (ArticleOrCommentStatusEnum articleStatus : ArticleOrCommentStatusEnum.values()) {
            if (articleStatus.getStatus().equals(status)) {
                return articleStatus;
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
