package fun.masttf.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import fun.masttf.config.AdminConfig;
import fun.masttf.entity.constans.Constans;
import fun.masttf.entity.dto.SessionAdminUserDto;
import fun.masttf.entity.enums.ResponseCodeEnum;
import fun.masttf.exception.BusinessException;

@Component
public class AppInterceptor implements HandlerInterceptor {
    @Autowired
    private AdminConfig adminConfig;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler == null) return false;
        if(!(handler instanceof HandlerMethod)) return true;
        /*
         * 全局拦截，避免方法未设置拦截器，导致权限，日志等操作没有记录如果不强制要求设置GlobalInterceptor注解，可去掉次拦截器
         */
        if(request.getRequestURI().indexOf("checkCode") != -1 || request.getRequestURI().indexOf("login") != -1){
            return true;
        }
        checkLogin();
        return true;
    }
    private void checkLogin() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            // 如果不在Web请求上下文中，抛出异常
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        HttpServletRequest request = requestAttributes.getRequest();
        HttpSession session = request.getSession();
        SessionAdminUserDto admin = (SessionAdminUserDto) session.getAttribute(Constans.SESSION_KEY);
        if(admin == null && adminConfig.isDev()){
            admin = new SessionAdminUserDto();
            admin.setAccount("adminUser");
            session.setAttribute(Constans.SESSION_KEY, admin);
        }
        if(admin == null){
            throw new BusinessException(ResponseCodeEnum.CODE_901);
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 处理请求后逻辑
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 处理请求完成逻辑
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
