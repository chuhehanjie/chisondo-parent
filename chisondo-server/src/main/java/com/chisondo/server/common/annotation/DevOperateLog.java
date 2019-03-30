package com.chisondo.server.common.annotation;

import java.lang.annotation.*;

/**
 * 设备操作日志注解
 * @author ding.zhong
 * @email 258321511@qq.com
 * @date Mar 23.19
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DevOperateLog {

	String value() default "";

	boolean asyncCall() default false;
}
