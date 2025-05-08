package fun.masttf.controller;

import fun.masttf.entity.vo.ResponseVo;
import fun.masttf.entity.enums.ResponseCodeEnum;
public class ABaseController {
    protected static final String STATUC_SUCCESS = "success";

    protected static final String STATUC_ERROR = "error";

    protected <T> ResponseVo<T> getSuccessResponseVo(T t) {
        ResponseVo<T> responseVo = new ResponseVo<T>();
        responseVo.setStatus(STATUC_SUCCESS);
        responseVo.setCode(ResponseCodeEnum.CODE_200.getCode());
        responseVo.setMsg(ResponseCodeEnum.CODE_200.getMessage());
        responseVo.setData(t);
        return responseVo;
    }
}
