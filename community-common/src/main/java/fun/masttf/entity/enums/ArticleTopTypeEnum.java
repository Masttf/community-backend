package fun.masttf.entity.enums;

public enum ArticleTopTypeEnum {
    NOT_TOP(0, "未置顶"),
    TOP(1, "置顶");
    private Integer type;
    private String desc;

    private ArticleTopTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }
    public static ArticleTopTypeEnum getByType(Integer type) {
        for (ArticleTopTypeEnum value : ArticleTopTypeEnum.values()) {
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
