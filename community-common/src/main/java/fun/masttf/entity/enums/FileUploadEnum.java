package fun.masttf.entity.enums;



import fun.masttf.entity.constans.Constans;

public enum FileUploadEnum {
    
    ARTICLE_ATTACHMENT(Constans.FILE_FOLDER_ATTACHMENT, "文章附件", Constans.ATTACHMENT_SUFFIX),
    ARTICLE_COVER(Constans.FILE_FOLDER_IMAGES, "文章封面", Constans.IMAGE_SUFFIX),
    AVATAR(Constans.FILE_FOLDER_AVATAR, "头像", Constans.IMAGE_SUFFIX),
    TEMP(Constans.FILE_FOLDER_TEMP, "临时文件", Constans.IMAGE_SUFFIX),
    COMMENT_IMAGE(Constans.FILE_FOLDER_IMAGES, "图片", Constans.IMAGE_SUFFIX);
    
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
