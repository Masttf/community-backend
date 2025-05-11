package fun.masttf.controller;

import fun.masttf.entity.vo.ResponseVo;
import fun.masttf.exception.BusinessException;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import fun.masttf.entity.constans.Constans;
import fun.masttf.entity.dto.SessionWebUserDto;
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

    protected <T> ResponseVo<T> getBussinessErrorResponseVo(BusinessException e, T t) {
        ResponseVo<T> responseVo = new ResponseVo<T>();
        responseVo.setStatus(STATUC_ERROR);
        if(e.getCode() == null) {
            responseVo.setCode(ResponseCodeEnum.CODE_600.getCode());
        } else {
            responseVo.setCode(e.getCode());
        }
        responseVo.setMsg(e.getMessage());
        responseVo.setData(t);
        return responseVo;
    }

    protected <T> ResponseVo<T> getServerErrorResponseVo(T t) {
        ResponseVo<T> responseVo = new ResponseVo<T>();
        responseVo.setStatus(STATUC_ERROR);
        responseVo.setCode(ResponseCodeEnum.CODE_500.getCode());
        responseVo.setMsg(ResponseCodeEnum.CODE_500.getMessage());
        responseVo.setData(t);
        return responseVo;
    }

    protected String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            if(ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    protected SessionWebUserDto getUserInfoSession(HttpSession session) {
        return (SessionWebUserDto) session.getAttribute(Constans.SESSION_KEY);
    }
}
