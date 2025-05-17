package fun.masttf.entity.enums;

public enum AttachmentFileTypeEnum {
    ZIP(0, "压缩文件", new String[]{"zip", "rar", "7z", "tar", "gz"}),
    DOC(1, "Word文档", new String[]{"doc", "docx"}),
    XLS(2, "Excel文档", new String[]{"xls", "xlsx"}),
    PPT(3, "PPT文档", new String[]{"ppt", "pptx"}),
    TXT(4, "文本文件", new String[]{"txt"}),
    PDF(5, "PDF文件", new String[]{"pdf"});

    private Integer type;
    private String desc;
    private String[] suffix;

    private AttachmentFileTypeEnum(Integer type, String desc, String[] suffix) {
        this.type = type;
        this.desc = desc;
        this.suffix = suffix;
    }
    public static AttachmentFileTypeEnum getByType(Integer type) {
        for (AttachmentFileTypeEnum value : AttachmentFileTypeEnum.values()) {
            if (value.getType().equals(type)) {
                return value;
            }
        }
        return null;
    }

    public static AttachmentFileTypeEnum getBySuffix(String suffix) {
        for (AttachmentFileTypeEnum value : AttachmentFileTypeEnum.values()) {
            for (String s : value.getSuffix()) {
                //都是小写
                if (s.equals(suffix)) {
                    return value;
                }
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
    public String[] getSuffix() {
        return suffix;
    }
}
