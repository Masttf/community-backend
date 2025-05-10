package fun.masttf.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface GlobalInterceptor {
    /*
     * 是否需要登录
     */
    boolean checkLogin() default false;

    /*
     * 是否需要校验参数
     */
    boolean checkParams() default false;

    /*
     * 校验频次
     */
}
