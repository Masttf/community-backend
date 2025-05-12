package fun.masttf.aspect;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.tomcat.util.bcel.Const;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import fun.masttf.annotation.GlobalInterceptor;
import fun.masttf.entity.constans.Constans;
import fun.masttf.entity.dto.SessionWebUserDto;
import fun.masttf.entity.enums.ResponseCodeEnum;
import fun.masttf.exception.BusinessException;
import fun.masttf.utils.StringTools;
import fun.masttf.utils.VerifyUtils;

@Component
@Aspect
public class OperactionAspect {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OperactionAspect.class);
    
    private static final String[] TYPE_BASE = {"java.lang.String", "java.lang.Integer", "java.lang.Long"};
    @Pointcut("@annotation(fun.masttf.annotation.GlobalInterceptor)")
    public void requestInterceptor() {
        // Pointcut for methods annotated with @GlobalInterceptor
    }

    @Around("requestInterceptor()")
    public Object interceptorDO(ProceedingJoinPoint point) {
        try{
            Object target = point.getTarget();
            Object[] args = point.getArgs();
            String methodName = point.getSignature().getName();
            Class<?>[] parameterTypes = ((MethodSignature)point.getSignature()).getMethod().getParameterTypes();
            Method method = target.getClass().getMethod(methodName, parameterTypes);
            GlobalInterceptor interceptor = method.getAnnotation(GlobalInterceptor.class);
            if(interceptor == null){
                return null;
            }
            if(interceptor.checkLogin()){
                checkLogin();
            }
            if(interceptor.checkParams()){
                validateParams(method, args);
            }
            Object result = point.proceed();
            return result;
        }catch(BusinessException e){
            logger.error("OperactionAspect: 错误", e);
            throw e;
        }catch (Exception e) {
            logger.error("OperactionAspect: 错误", e);
            throw new BusinessException(ResponseCodeEnum.CODE_500);
        }catch (Throwable e) {
            logger.error("OperactionAspect: 错误", e);
            throw new BusinessException(ResponseCodeEnum.CODE_500);
        }
    }

    private void checkLogin() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        SessionWebUserDto userDto = (SessionWebUserDto) session.getAttribute(Constans.SESSION_KEY);
        if(userDto == null){
            throw new BusinessException(ResponseCodeEnum.CODE_901);
        }

    }
    private void validateParams(Method m,Object[] args) {
        Parameter[] parameters = m.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Object value = args[i];
            // logger.info("参数类型:{}", JsonUtils.convertObj2Json(value));
            VerifyParam verifyParam = parameter.getAnnotation(VerifyParam.class);
            if(verifyParam == null) continue;
            if(ArrayUtils.contains(TYPE_BASE, parameter.getType().getName())){
                checkValue(value, verifyParam);
            }else{

            }
        }
    }
    private void checkObjValue(Parameter parameter, Object value) {

    }
    private void checkValue(Object value, VerifyParam verifyParam) {
        Boolean isEmpty = value == null || StringTools.isEmpty(value.toString());
        Integer length = value == null ? 0 : value.toString().length();
        // 检查值是不是空
        if (isEmpty && verifyParam.required()) {
            // logger.error("参数不能为空");
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        //检查长度
        if(!isEmpty && ((verifyParam.max() != -1 && length > verifyParam.max()) || (verifyParam.min() != -1 && length < verifyParam.min()))){
            // logger.error("参数长度不符合要求");
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }

        //检查正则
        if(!isEmpty && !StringTools.isEmpty(verifyParam.regex().getRegex()) && !VerifyUtils.verify(verifyParam.regex(), String.valueOf(value))){
            // logger.error("参数不符合正则要求");
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
    }
}
