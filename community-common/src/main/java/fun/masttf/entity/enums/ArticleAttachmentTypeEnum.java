package fun.masttf.entity.enums;

public enum ArticleAttachmentTypeEnum {
    NO_ATTACHMENT(0, "无附件"),
    HAVE_ATTACHMENT(1, "有附件");
    private Integer type;
    private String desc;
    private ArticleAttachmentTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static ArticleAttachmentTypeEnum getByType(Integer type) {
        for (ArticleAttachmentTypeEnum value : ArticleAttachmentTypeEnum.values()) {
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
