package fun.masttf.entity.enums;

public enum VerifyRegexEnum {
    NO("", "不校验"),
    IP("([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5]))){3}", "IP地址"),
    POSITIVE_INTEGER("^[0-9]*[1-9][0-9]*$", "正整数"),
    NUMBER_LETTER_UNDERLINE("^\\w+$", "数字、字母、下划线"),
    EMAIL("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$", "邮箱"),
    PHONE("(1[0-9])\\d{9}", "手机号"),
    COMMON("^[a-zA-Z0-9_\\u4e00-\\u9fa5]+$", "数字、字母、中文、下划线"),
    PASSWORD("^(?=.*\\d)(?=.*[a-zA-Z])[\\da-zA-Z~!@#$%^&*_]{8,}$", "密码(数字、字母、特殊字符,8-18位以上) 至少包含一位数字 至少包含一个字母"),
    ACCOUNT("^[0-9a-zA-Z_]{1,}$", "字母开头，由数字字母或者下划线组成"),
    MONEY("^[0-9]+(.[0-9]{1,2})?$", "金额(最多两位小数)");
    private String regex;
    private String desc;
    VerifyRegexEnum(String regex, String desc) {
        this.regex = regex;
        this.desc = desc;
    }
    public String getRegex() {
        return regex;
    }
    public String getDesc() {
        return desc;
    }
    
}
