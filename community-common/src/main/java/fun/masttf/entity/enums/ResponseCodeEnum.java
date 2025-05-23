package fun.masttf.entity.enums;

public enum ResponseCodeEnum {
    CODE_200(200, "请求成功"),
    CODE_404(404, "请求地址不存在"),
    CODE_600(600, "请求参数错误"),
    CODE_601(601, "信息已经存在，重复提交"),
    CODE_602(602, "操作次数过多，请稍后再试"),
    CODE_900(900, "http请求超时"),
    CODE_901(901, "登录超时"),
    CODE_500(500, "服务器返回错误，请联系管理员");

    private Integer code;
    private String message;

    private ResponseCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
