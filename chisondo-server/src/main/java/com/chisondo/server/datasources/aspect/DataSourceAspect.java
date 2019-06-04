package com.chisondo.server.datasources.aspect;

import com.chisondo.server.datasources.DataSourceNames;
import com.chisondo.server.datasources.DynamicDataSource;
import com.chisondo.server.datasources.DataSourceNames;
import com.chisondo.server.datasources.DynamicDataSource;
import com.chisondo.server.datasources.annotation.DataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 多数据源，切面处理类
 * @author ding.zhong
 * @email 258321511@qq.com
 * @date Mar 12.19
 */
@Aspect
@Component
public class DataSourceAspect implements Ordered {
    protected Logger logger = LoggerFactory.getLogger(getClass());

   /* @Pointcut("@annotation(com.chisondo.server.datasources.annotation.DataSource)")
    public void dataSourcePointCut() {

    }*/

    @Pointcut("execution(* com.chisondo.server.modules.user.dao.UserVipDao.*(..))")
    public void setThirdDataSource() {
    }

    @Pointcut("execution(* com.chisondo.server.modules.tea.dao.*.*(..)) || execution(* com.chisondo.server.modules.sys.dao.StarbannerDao.*(..))")
    public void setSecondDataSource() {
    }

    /*@Before("execution(* com.chisondo.server.modules.user.dao.UserVipDao.*(..))")
    public void setThirdDataSource() {
        DynamicDataSource.setDataSource(DataSourceNames.THIRD);
        logger.error("dataSource切换到：third");
    }

    @After("execution(* com.chisondo.server.modules.user.dao.UserVipDao.*(..))")
    public void clearThirdDataSource() {
        DynamicDataSource.clearDataSource();
        logger.error("清除数据源：third");
    }*/

    @Around("setSecondDataSource()")
    public Object around4SecondDS(ProceedingJoinPoint point) throws Throwable {
        try {
            DynamicDataSource.setDataSource(DataSourceNames.SECOND);
            logger.debug("dataSource切换到：second");
            return point.proceed();
        } finally {
            DynamicDataSource.clearDataSource();
            logger.debug("clean second datasource");
        }
    }

    @Around("setThirdDataSource()")
    public Object around4ThirdDS(ProceedingJoinPoint point) throws Throwable {
        try {
            DynamicDataSource.setDataSource(DataSourceNames.THIRD);
            logger.debug("dataSource切换到：third");
            return point.proceed();
        } finally {
            DynamicDataSource.clearDataSource();
            logger.debug("clean third datasource");
        }
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
