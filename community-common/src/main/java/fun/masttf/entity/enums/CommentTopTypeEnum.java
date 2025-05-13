package fun.masttf.entity.enums;

public enum CommentTopTypeEnum {
    NOT_TOP(0, "未置顶"),
    TOP(1, "置顶");
    private Integer type;
    private String desc;

    CommentTopTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }
    public static CommentTopTypeEnum getByType(Integer type) {
        for (CommentTopTypeEnum value : CommentTopTypeEnum.values()) {
            if (value.getType().equals(type)) {
                return value;
            }
        }
        return NOT_TOP;
    }
    public Integer getType() {
        return type;
    }
    public String getDesc() {
        return desc;
    }
}
