package fun.masttf.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import fun.masttf.entity.enums.VerifyRegexEnum;

import java.lang.annotation.RetentionPolicy;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface VerifyParam {
    boolean required() default false; // 是否必填

    int max() default -1;
    int min() default -1;

    VerifyRegexEnum regex() default VerifyRegexEnum.NO; // 正则表达式
}
