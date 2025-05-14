package fun.masttf.controller;

import fun.masttf.entity.vo.PaginationResultVo;
import fun.masttf.entity.vo.ResponseVo;
import fun.masttf.exception.BusinessException;
import fun.masttf.utils.CopyTools;
import fun.masttf.utils.JsonUtils;
import fun.masttf.utils.OKHttpUtils;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import fun.masttf.entity.constans.Constans;
import fun.masttf.entity.dto.SessionWebUserDto;
import fun.masttf.entity.enums.ResponseCodeEnum;
public class ABaseController {
    protected static final String STATUC_SUCCESS = "success";

    protected static final String STATUC_ERROR = "error";

    private static final Logger logger = LoggerFactory.getLogger(ABaseController.class);

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
    public String getIpProvince(String ip) {
		try {
			String url = "http://whois.pconline.com.cn/ipJson.jsp?json=true&ip=" + ip;
			String responseJson = OKHttpUtils.getRequest(url);
			if(responseJson == null) {
				return Constans.NO_ADDRESS;
			}
			Map<String, String> addressInfo = JsonUtils.convertJson2Obj(responseJson, Map.class);
			return addressInfo.get("pro");
		} catch (Exception e) {
			logger.error("获取ip地址失败", e);
		}
		return Constans.NO_ADDRESS;
	}

    protected SessionWebUserDto getUserInfoSession(HttpSession session) {
        return (SessionWebUserDto) session.getAttribute(Constans.SESSION_KEY);
    }

    protected <S, T> PaginationResultVo<T> convert2PaginationVo(PaginationResultVo<S> result, Class<T> clazz) {
        PaginationResultVo<T> resultVo = new PaginationResultVo<T>();
        resultVo.setList(CopyTools.copyList(result.getList(), clazz));
        resultVo.setPageNo(result.getPageNo());
        resultVo.setPageSize(result.getPageSize());
        resultVo.setPageTotal(result.getPageTotal());
        resultVo.setTotalCount(result.getTotalCount());
        return resultVo;
    }
}
