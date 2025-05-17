package fun.masttf.entity.enums;

public enum BoardPostTypeEnum {
    ADMIN_POST(0, "只允许管理员发帖"),
    POST(1, "任何人可以发帖");
    private Integer type;
    private String desc;
    private BoardPostTypeEnum(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }
    public Integer getType() {
        return type;
    }
    public String getDesc() {
        return desc;
    }
    
}
