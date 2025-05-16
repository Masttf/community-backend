package fun.masttf.entity.enums;

public enum ArticleCountTypeEnum {
    READ_COUNT(0, "阅读数"),
    GOOD_COUNT(1, "点赞数"),
    COMMENT_COUNT(2, "评论数"),;

    private Integer type;
    private String desc;

    private ArticleCountTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static ArticleCountTypeEnum getByType(Integer type) {
        for (ArticleCountTypeEnum updateArticleCountType : ArticleCountTypeEnum.values()) {
            if (updateArticleCountType.getType().equals(type)) {
                return updateArticleCountType;
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
