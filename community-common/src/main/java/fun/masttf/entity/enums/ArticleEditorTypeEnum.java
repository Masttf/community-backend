package fun.masttf.entity.enums;

public enum ArticleEditorTypeEnum {
    RICH(0, "富文本"),
    MARKDOWN(1, "Markdown");
    private Integer type;
    private String desc;

    ArticleEditorTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static ArticleEditorTypeEnum getByType(Integer type) {
        for (ArticleEditorTypeEnum editorTypeEnum : ArticleEditorTypeEnum.values()) {
            if (editorTypeEnum.getType().equals(type)) {
                return editorTypeEnum;
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
