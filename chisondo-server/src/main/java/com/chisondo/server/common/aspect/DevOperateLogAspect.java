package com.chisondo.server.common.aspect;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.annotation.DevOperateLog;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.http.CommonResp;
import com.chisondo.server.common.utils.Constant;
import com.chisondo.server.common.utils.Keys;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.datasources.DataSourceNames;
import com.chisondo.server.datasources.DynamicDataSource;
import com.chisondo.server.modules.device.entity.ActivedDeviceInfoEntity;
import com.chisondo.server.modules.device.entity.DeviceOperateLogEntity;
import com.chisondo.server.modules.device.service.DeviceOperateLogService;
import com.chisondo.server.modules.user.entity.UserVipEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.http.HttpStatus;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
		log.error("执行业务方法 [{}] 共耗时 {} 毫秒",method.getName() , callTime);
		if (!this.isOldDev(point)) {
			// 不是老设备才记录操作日志
			DevOperateLog devOperateLog = method.getAnnotation(DevOperateLog.class);
			if (devOperateLog.asyncCall()) {
				ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1,
						new BasicThreadFactory.Builder().namingPattern("scheduled-pool-%d").daemon(true).build());
				scheduledExecutorService.execute(() -> this.saveDevOperateLog(point, result, startTime, endTime, devOperateLog.value()));
			} else {
				//保存设备操作日志
				this.saveDevOperateLog(point, result, startTime, endTime, devOperateLog.value());
			}
		}
		return result;
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

	private void saveDevOperateLog(ProceedingJoinPoint joinPoint, Object result, long startTime, long endTime, String methodDesc) {
		CommonReq req = (CommonReq) joinPoint.getArgs()[0];
		CommonResp resp = (CommonResp) result;
		UserVipEntity user = (UserVipEntity) req.getAttrByKey(Keys.USER_INFO);
		ActivedDeviceInfoEntity deviceInfo = (ActivedDeviceInfoEntity) req.getAttrByKey(Keys.DEVICE_INFO);
		DeviceOperateLogEntity devOperateLog = new DeviceOperateLogEntity();
		devOperateLog.setDeviceId(ValidateUtils.isEmpty(deviceInfo) ? "" : deviceInfo.getDeviceId().toString());
		devOperateLog.setTeamanId(user.getMemberId() + "");
		devOperateLog.setUserMobileNo(user.getPhone());
		devOperateLog.setOperType(0); // TODO 操作类型未定义
		devOperateLog.setReqContent(JSONObject.toJSONString(req));
		devOperateLog.setResContent(JSONObject.toJSONString(resp));
		devOperateLog.setStartTime(new Date(startTime));
		devOperateLog.setEndTime(new Date(endTime));
		devOperateLog.setDesc(methodDesc);
		devOperateLog.setOperResult(resp.getRetn() == HttpStatus.SC_OK ? Constant.RespResult.SUCCESS : Constant.RespResult.FAILED);
		DynamicDataSource.setDataSource(DataSourceNames.FIRST);
		this.devOperateLogService.save(devOperateLog);
	}
}
