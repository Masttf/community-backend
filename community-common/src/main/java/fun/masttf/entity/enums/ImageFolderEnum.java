package fun.masttf.entity.enums;

public enum ImageFolderEnum {
    AVATAR("avatar/", "头像"),
    IMAGE("images/", "其他图片");
    private String folder;
    private String desc;

    ImageFolderEnum(String folder, String desc) {
        this.folder = folder;
        this.desc = desc;
    }
    public String getFolder() {
        return folder;
    }
    public String getDesc() {
        return desc;
    }
}
