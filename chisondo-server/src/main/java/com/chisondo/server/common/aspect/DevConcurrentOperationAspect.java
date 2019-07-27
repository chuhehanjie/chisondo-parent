package com.chisondo.server.common.aspect;

import com.chisondo.server.common.annotation.DevConcurrentOperation;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.utils.CacheDataUtils;
import com.chisondo.server.common.utils.Keys;
import com.chisondo.server.common.utils.RedisUtils;
import com.chisondo.server.common.utils.ValidateUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@Order(0)
public class DevConcurrentOperationAspect {

    @Autowired
    private RedisUtils redisUtils;

    @Before("@annotation(devConcurrentOperation)")
    public void before4SetDataSource(JoinPoint joinPoint, DevConcurrentOperation devConcurrentOperation) throws Throwable {
        String key = this.getKey(joinPoint.getArgs()[0]);
        Boolean existedDevConcurrent = this.redisUtils.get(key, Boolean.class);
        if (ValidateUtils.isNotEmpty(existedDevConcurrent)) {
            log.error("设备[{}]正在工作中!", key);
            throw new CommonException("设备忙!请稍后再试!", 510);
        }
        this.redisUtils.set(key, true, Long.valueOf(CacheDataUtils.getConfigValueByKey(Keys.DEV_CONCURRENT_INTERVAL, "3")));
    }

    private String getKey(Object o) {
        CommonReq commonReq = (CommonReq) o;
        String newDeviceId = (String) commonReq.getAttrByKey(Keys.NEW_DEVICE_ID);
        return Keys.PREFIX_DEV_CONCURRENT + newDeviceId;
    }

    @After("@annotation(devConcurrentOperation)")
    public void after(JoinPoint joinPoint, DevConcurrentOperation devConcurrentOperation) {
        String key = this.getKey(joinPoint.getArgs()[0]);
        this.redisUtils.delete(key);
        log.error("清除设备[{}]并发操作", key);
    }
}