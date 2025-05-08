package fun.masttf.controller;

import fun.masttf.entity.vo.ResponseVo;
import fun.masttf.entity.enums.ResponseCodeEnum;
import fun.masttf.exception.BusinessException;

import java.net.BindException;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

public class AGlobalExceptionHandlerController extends ABaseController {
    private static final Logger logger = LoggerFactory.getLogger(AGlobalExceptionHandlerController.class);

    @ExceptionHandler(value = Exception.class)
    Object handleException(Exception e, HttpServletRequest request) {
        logger.error("请求地址：{}，异常信息：{}", request.getRequestURI(), e);
        ResponseVo<Object> ajaxResponse = new ResponseVo<>();
        // 404
        if (e instanceof NoHandlerFoundException) {
            ajaxResponse.setStatus(STATUC_ERROR);
            ajaxResponse.setCode(ResponseCodeEnum.CODE_404.getCode());
            ajaxResponse.setMsg(ResponseCodeEnum.CODE_404.getMessage());
        } else if (e instanceof BusinessException) {
            // 业务异常
            BusinessException biz = (BusinessException) e;
            ajaxResponse.setStatus(STATUC_ERROR);
            ajaxResponse.setCode(biz.getCode());
            ajaxResponse.setMsg(biz.getMessage());
        } else if (e instanceof BindException || e instanceof MethodArgumentTypeMismatchException) {
            // 参数类型异常
            ajaxResponse.setStatus(STATUC_ERROR);
            ajaxResponse.setCode(ResponseCodeEnum.CODE_600.getCode());
            ajaxResponse.setMsg(ResponseCodeEnum.CODE_600.getMessage());
        } else if (e instanceof DuplicateKeyException) {
            // 主键冲突异常
            ajaxResponse.setStatus(STATUC_ERROR);
            ajaxResponse.setCode(ResponseCodeEnum.CODE_601.getCode());
            ajaxResponse.setMsg(ResponseCodeEnum.CODE_601.getMessage());
        } else {
            // 其他异常
            ajaxResponse.setStatus(STATUC_ERROR);
            ajaxResponse.setCode(ResponseCodeEnum.CODE_500.getCode());
            ajaxResponse.setMsg(ResponseCodeEnum.CODE_500.getMessage());
        }
        return ajaxResponse;
    }
}
