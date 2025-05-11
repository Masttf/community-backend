package fun.masttf.entity.enums;

public enum ArticleOrderTypeEnum {
    HOT(0, "top_type desc,comment_count desc, good_count desc, read_count desc", "热度"),
    SEND(1, "post_time asc", "发布"),
    NEW(2, "post_time desc", "最新");
    private Integer type;
    private String oderSql;
    private String desc;

    public static ArticleOrderTypeEnum getByType(Integer type) {
        for (ArticleOrderTypeEnum orderType : ArticleOrderTypeEnum.values()) {
            if (orderType.getType().equals(type)) {
                return orderType;
            }
        }
        return null;
    }
    ArticleOrderTypeEnum(Integer type, String oderSql, String desc) {
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
