package fun.masttf.entity.enums;

public enum UserIntegralRecordOrderTypeEnum {
    SEND(0, "record_id asc", "发布"),
    NEW(1, "record_id desc", "最新");
    private Integer type;
    private String oderSql;
    private String desc;

    public static UserIntegralRecordOrderTypeEnum getByType(Integer type) {
        for (UserIntegralRecordOrderTypeEnum orderType : UserIntegralRecordOrderTypeEnum.values()) {
            if (orderType.getType().equals(type)) {
                return orderType;
            }
        }
        return null;
    }
    private UserIntegralRecordOrderTypeEnum(Integer type, String oderSql, String desc) {
        this.type = type;
        this.oderSql = oderSql;
        this.desc = desc;
    }
    public Integer getType() {
        return type;
    }
    public String getOderSql() {
        return oderSql;
    }
    public String getDesc() {
        return desc;
    }
}
