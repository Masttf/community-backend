package fun.masttf.aspect;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import fun.masttf.annotation.GlobalInterceptor;
import fun.masttf.entity.constans.Constans;
import fun.masttf.entity.dto.SessionWebUserDto;
import fun.masttf.entity.dto.SysSettingDto;
import fun.masttf.entity.enums.DateTimePatternEnum;
import fun.masttf.entity.enums.ResponseCodeEnum;
import fun.masttf.entity.enums.UserOpFrequencyTypeEnum;
import fun.masttf.entity.query.ForumArticleQuery;
import fun.masttf.entity.query.ForumCommentQuery;
import fun.masttf.entity.query.LikeRecordQuery;
import fun.masttf.entity.vo.ResponseVo;
import fun.masttf.exception.BusinessException;
import fun.masttf.service.ForumArticleService;
import fun.masttf.service.ForumCommentService;
import fun.masttf.service.LikeRecordService;
import fun.masttf.utils.DateUtils;
import fun.masttf.utils.StringTools;
import fun.masttf.utils.SysCacheUtils;
import fun.masttf.utils.VerifyUtils;

@Component
@Aspect
public class OperactionAspect {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(OperactionAspect.class);
    
    private static final String[] TYPE_BASE = {"java.lang.String", "java.lang.Integer", "java.lang.Long"};
    
    @Autowired
    private ForumArticleService forumArticleService;
    @Autowired
    private ForumCommentService forumCommentService;
    @Autowired
    private LikeRecordService likeRecordService;

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
            checkFrequency(interceptor.checkFrequency());
            Object result = point.proceed();

            if(result instanceof ResponseVo) {
                ResponseVo<?> response = (ResponseVo<?>) result;
                if(response.getStatus().equals(Constans.STATUS_SUCCESS)){
                    addOpCount(interceptor.checkFrequency());
                }
            }
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
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            // 如果不在Web请求上下文中，抛出异常
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        HttpServletRequest request = requestAttributes.getRequest();
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
                checkObjValue(parameter, value);
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
    private void checkFrequency(UserOpFrequencyTypeEnum frequencyType) {
        if (frequencyType == null || frequencyType.equals(UserOpFrequencyTypeEnum.NO_CHECK)) {
            return;
        }
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            // 如果不在Web请求上下文中，抛出异常
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        HttpServletRequest request = requestAttributes.getRequest();
        HttpSession session = request.getSession();
        SessionWebUserDto userDto = (SessionWebUserDto) session.getAttribute(Constans.SESSION_KEY);
        String curDate = DateUtils.format(new java.util.Date(), DateTimePatternEnum.YYYY_MM_DD.getPattern());
        String frequencyKey = Constans.SESSION_FREQUENCY_KEY + curDate + frequencyType.getOpType();
        Integer count = (Integer) session.getAttribute(frequencyKey);
        SysSettingDto setting = SysCacheUtils.getSysSetting();

        switch (frequencyType) {
            case POST_ARTICLE:
                if(count == null) {
                    ForumArticleQuery query = new ForumArticleQuery();
                    query.setUserId(userDto.getUserId());
                    query.setPostTimeStart(curDate);
                    query.setPostTimeEnd(curDate);
                    count = forumArticleService.findCountByQuery(query);
                }
                if(count > setting.getPostSetting().getPostDayCountThreshold()){
                    throw new BusinessException(ResponseCodeEnum.CODE_602);
                }
                break;
            case POST_COMMENT:
                if(count == null) {
                    ForumCommentQuery query = new ForumCommentQuery();
                    query.setUserId(userDto.getUserId());
                    query.setPostTimeStart(curDate);
                    query.setPostTimeEnd(curDate);
                    count = forumCommentService.findCountByQuery(query);
                }
                if(count > setting.getCommentSetting().getCommentDayCountThreshold()){
                    throw new BusinessException(ResponseCodeEnum.CODE_602);
                }
                break;
            case DO_LIKE:
                if(count == null) {
                    LikeRecordQuery query = new LikeRecordQuery();
                    query.setUserId(userDto.getUserId());
                    query.setCreateTimeStart(curDate);
                    query.setCreateTimeEnd(curDate);
                    count = likeRecordService.findCountByQuery(query);
                }
                if(count > setting.getLikeSetting().getLikeDayCountThresold()){
                    throw new BusinessException(ResponseCodeEnum.CODE_602);
                }
                break;
            case IMAGE_UPLOAD:
                if(count == null) {
                    count = 0;
                }
                if(count > setting.getPostSetting().getDayImageUploadCount()){
                    throw new BusinessException(ResponseCodeEnum.CODE_602);
                }
                break;
            default:
                break;
        }
        session.setAttribute(frequencyKey, count);
    }

    private void addOpCount(UserOpFrequencyTypeEnum frequencyType) {
        if (frequencyType == null || frequencyType.equals(UserOpFrequencyTypeEnum.NO_CHECK)) {
            return;
        }
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            // 如果不在Web请求上下文中，抛出异常
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        HttpServletRequest request = requestAttributes.getRequest();
        HttpSession session = request.getSession();
        String curDate = DateUtils.format(new java.util.Date(), DateTimePatternEnum.YYYY_MM_DD.getPattern());
        String frequencyKey = Constans.SESSION_FREQUENCY_KEY + curDate + frequencyType.getOpType();
        Integer count = (Integer) session.getAttribute(frequencyKey);
        session.setAttribute(frequencyKey, count + 1);
    }
}
