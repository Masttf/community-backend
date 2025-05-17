package fun.masttf.entity.enums;

public enum UserSexEnum {
    WOMAN(0, "女"),
    MAN(1, "男");
    private Integer sex;
    private String desc;

    private UserSexEnum(Integer sex, String desc) {
        this.sex = sex;
        this.desc = desc;
    }

    public static UserSexEnum getBySex(Integer sex) {
        for (UserSexEnum value : UserSexEnum.values()){
            if (value.getSex().equals(sex)){
                return value;
            }
        }
        return null;
    }
    public Integer getSex() {
        return sex;
    }

    public String getDesc() {
        return desc;
    }

    
}
