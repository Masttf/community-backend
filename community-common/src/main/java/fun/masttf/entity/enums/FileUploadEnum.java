package fun.masttf.entity.enums;



import fun.masttf.entity.constans.Constans;

public enum FileUploadEnum {
    
    ARTICLE_ATTACHMENT("attachment/", "文章附件", Constans.ATTACHMENT_SUFFIX),
    ARTICLE_COVER("cover/", "文章封面", Constans.IMAGE_SUFFIX),
    AVATAR("avatar/", "头像", Constans.IMAGE_SUFFIX),
    TEMP("temp/", "临时文件", Constans.IMAGE_SUFFIX),
    COMMENT_IMAGE("images/", "图片", Constans.IMAGE_SUFFIX);
    
    private String folder;
    private String desc;
    private String[] suffix;

    private FileUploadEnum(String folder, String desc, String[] suffix) {
        this.folder = folder;
        this.desc = desc;
        this.suffix = suffix;
    }

    public static FileUploadEnum getBySuffix(String suffix) {
        for (FileUploadEnum value : FileUploadEnum.values()) {
            for (String s : value.getSuffix()) {
                //都是小写
                if (s.equals(suffix)) {
                    return value;
                }
            }
        }
        return null;
    }
    public String getFolder() {
        return folder;
    }
    public String getDesc() {
        return desc;
    }
    public String[] getSuffix() {
        return suffix;
    }
}
