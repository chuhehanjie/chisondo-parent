package com.chisondo.server.datasources.annotation;

import java.lang.annotation.*;

/**
 * 多数据源注解
 * @author ding.zhong
 * @email 258321511@qq.com
 * @date Mar 12.19
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    String name() default "";
}
