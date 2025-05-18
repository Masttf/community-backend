package fun.masttf.entity.enums;

public enum UserOpFrequencyTypeEnum {
    NO_CHECK(0, "不限制"),
    POST_ARTICLE(1, "发帖"),
    POST_COMMENT(2, "评论"),
    DO_LIKE(3, "点赞"),
    IMAGE_UPLOAD(4, "图片上传");
    private Integer opType;
    private String desc;

    private UserOpFrequencyTypeEnum(Integer opType, String desc) {
        this.opType = opType;
        this.desc = desc;
    }

    public static UserOpFrequencyTypeEnum getByType(Integer type) {
        for (UserOpFrequencyTypeEnum orderType : UserOpFrequencyTypeEnum.values()) {
            if (orderType.getOpType().equals(type)) {
                return orderType;
            }
        }
        return null;
    }

    public Integer getOpType() {
        return opType;
    }
    public String getDesc() {
        return desc;
    }
}
