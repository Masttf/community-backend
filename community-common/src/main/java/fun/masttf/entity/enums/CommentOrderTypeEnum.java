package fun.masttf.entity.enums;

public enum CommentOrderTypeEnum {
    HOT(0, "top_type desc, good_count desc, comment_id desc", "热度"),
    NEW(1, "top_type desc, comment_id desc", "最新"),
    SEND(2, "top_type desc, comment_id asc", "发布时间");
    private Integer type;
    private String oderSql;
    private String desc;
    public static CommentOrderTypeEnum getByType(Integer type) {
        for (CommentOrderTypeEnum orderType : CommentOrderTypeEnum.values()) {
            if (orderType.getType().equals(type)) {
                return orderType;
            }
        }
        return null;
    }
    CommentOrderTypeEnum(Integer type, String oderSql, String desc) {
        this.type = type;
        this.oderSql = oderSql;
        this.desc = desc;
    }
    public Integer getType() {
        return type;
    }
    public String getOderSql() {
        return oderSql;
    }
    public String getDesc() {
        return desc;
    }
}
