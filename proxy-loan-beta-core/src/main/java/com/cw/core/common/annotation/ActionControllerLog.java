package com.cw.core.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ActionControllerLog {
    /** 标题 */
    String title()  default "";
    /** 动作的名称 */
    String action() default "";
    /** 是否保存请求的参数 */
    boolean isSaveRequestData() default false;
    /** 渠道 */
    String channel() default "web";
}
