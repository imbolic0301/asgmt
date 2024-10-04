package com.partimestudy.global.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Argument Resolver 가 연결된 파라미터에 선언한다.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResolvedParam {
    // 해당 값이 선택 입력인지의 여부를 관리한다.
    boolean isOptional() default false;
}
