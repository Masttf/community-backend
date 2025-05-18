package fun.masttf.entity.enums;

public enum MessageOrderTypeEnum {
    SEND(0, "message_id asc", "发布"),
    NEW(1, "message_id desc", "最新");
    private Integer type;
    private String oderSql;
    private String desc;

    public static MessageOrderTypeEnum getByType(Integer type) {
        for (MessageOrderTypeEnum orderType : MessageOrderTypeEnum.values()) {
            if (orderType.getType().equals(type)) {
                return orderType;
            }
        }
        return null;
    }
    private MessageOrderTypeEnum(Integer type, String oderSql, String desc) {
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
