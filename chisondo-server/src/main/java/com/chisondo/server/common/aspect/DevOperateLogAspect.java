package com.chisondo.server.common.aspect;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.model.http.HttpStatus;
import com.chisondo.server.common.annotation.DevOperateLog;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.http.CommonResp;
import com.chisondo.server.common.utils.Constant;
import com.chisondo.server.common.utils.Keys;
import com.chisondo.server.common.utils.RedisUtils;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.modules.device.entity.ActivedDeviceInfoEntity;
import com.chisondo.server.modules.device.entity.DeviceOperateLogEntity;
import com.chisondo.server.modules.device.service.DeviceOperateLogService;
import com.chisondo.server.modules.user.entity.UserVipEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;


/**
 * 设备操作日志，切面处理类
 * @author ding.zhong
 * @email 258321511@qq.com
 * @date Mar 23.19
 */
@Aspect
@Component
@Slf4j
public class DevOperateLogAspect {

	@Autowired
	private DeviceOperateLogService devOperateLogService;

	@Autowired
	private RedisUtils redisUtils;

	@Pointcut("@annotation(com.chisondo.server.common.annotation.DevOperateLog)")
	public void devOperateLogPointcut() {
		
	}

	@Around("devOperateLogPointcut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long startTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();
		//执行时长(毫秒)
		long endTime = System.currentTimeMillis();
		long callTime = endTime - startTime;
		Method method = this.getCallMethod(point);
		if (!this.isOldDev(point)) {
			// 不是老设备才记录操作日志
			DevOperateLog annotation = method.getAnnotation(DevOperateLog.class);
			DeviceOperateLogEntity devOperateLog = this.buildDevOperLog(startTime, endTime, annotation);
			if (annotation.asyncCall()) {
				ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1,
						new BasicThreadFactory.Builder().namingPattern("scheduled-pool-%d").daemon(true).build());
				scheduledExecutorService.execute(() -> this.saveDevOperateLog(point, result, devOperateLog));
			} else {
				//保存设备操作日志
				this.saveDevOperateLog(point, result, devOperateLog);
			}
			String key = Keys.PREFIX_DEV_CONCURRENT + devOperateLog.getDeviceId();
			this.redisUtils.delete(key);
			log.info("清除设备[{}]并发操作", key);
		}

		log.error("执行业务方法 [{}] 共耗时 {} 毫秒", method.getName() , callTime);
		return result;
	}

	private DeviceOperateLogEntity buildDevOperLog(long startTime, long endTime, DevOperateLog annotation) {
		DeviceOperateLogEntity devOperateLog = new DeviceOperateLogEntity();
		devOperateLog.setStartTime(new Date(startTime));
		devOperateLog.setEndTime(new Date(endTime));
		devOperateLog.setDesc(annotation.value());
		return devOperateLog;
	}

	private boolean isOldDev(ProceedingJoinPoint point) {
		CommonReq req = (CommonReq) point.getArgs()[0];
		return req.isOldDev();
	}

	private Method getCallMethod(ProceedingJoinPoint point) {
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		return method;
	}

	private void saveDevOperateLog(ProceedingJoinPoint joinPoint, Object result, DeviceOperateLogEntity devOperateLog) {
		try {
			CommonReq req = (CommonReq) joinPoint.getArgs()[0];
			CommonResp resp = (CommonResp) result;
			UserVipEntity user = (UserVipEntity) req.getAttrByKey(Keys.USER_INFO);
			String newDeviceId = (String) req.getAttrByKey(Keys.NEW_DEVICE_ID);
			devOperateLog.setDeviceId(newDeviceId);
			devOperateLog.setTeamanId(ValidateUtils.isEmpty(user) ? "" : user.getMemberId() + "");
			devOperateLog.setUserMobileNo(ValidateUtils.isEmpty(user) ? "" :user.getPhone());
			devOperateLog.setOperType(0); // TODO 操作类型未定义
			devOperateLog.setReqContent(this.buildReqContent(req));
			devOperateLog.setResContent(JSONObject.toJSONString(resp));
			devOperateLog.setOperResult(resp.getRetn() == HttpStatus.SC_OK ? Constant.RespResult.SUCCESS : Constant.RespResult.FAILED);
			this.devOperateLogService.save(devOperateLog);
		} catch (Exception e) {
			log.error("保存设备操作日志异常", e);
		}
	}

	private String buildReqContent(CommonReq req) {
		String reqContent = JSONObject.toJSONString(req);
		if (ValidateUtils.isNotEmpty(req.getAttrByKey(Keys.DEV_REQ))) {
			reqContent = reqContent.substring(0, reqContent.length() - 1);
			reqContent += ",\"" + Keys.DEV_REQ + "\":" + JSONObject.toJSONString(req.getAttrByKey(Keys.DEV_REQ)) + "}";
		}
		if (ValidateUtils.isNotEmpty(req.getAttrByKey(Keys.DEV_REQ_2))) {
			reqContent = reqContent.substring(0, reqContent.length() - 1);
			reqContent += ",\"" + Keys.DEV_REQ_2 + "\":" + JSONObject.toJSONString(req.getAttrByKey(Keys.DEV_REQ_2)) + "}";
		}
		return reqContent;
	}
}
