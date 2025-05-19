package fun.masttf.entity.enums;

public enum LoadUserArticleTypeEnum {
    POST_ARTICLE(0, "发布文章"),
    COMMENT_ARTICLE(1, "评论文章"),
    LIKE_ARTICLE(2, "点赞文章");
    private Integer type;
    private String desc;
    private LoadUserArticleTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static LoadUserArticleTypeEnum getByType(Integer type) {
        for (LoadUserArticleTypeEnum value : LoadUserArticleTypeEnum.values()) {
            if (value.getType().equals(type)) {
                return value;
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
