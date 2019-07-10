package com.chisondo.server.modules.device.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.model.http.resp.DevStatusReportResp;
import com.chisondo.model.http.resp.DevStatusRespDTO;
import com.chisondo.server.common.utils.*;
import com.chisondo.server.modules.device.dao.DeviceStateInfoDao;
import com.chisondo.server.modules.device.dto.req.DeviceBindReqDTO;
import com.chisondo.server.modules.device.entity.ActivedDeviceInfoEntity;
import com.chisondo.server.modules.device.entity.DeviceStateInfoEntity;
import com.chisondo.server.modules.device.service.ActivedDeviceInfoService;
import com.chisondo.server.modules.device.service.DeviceStateInfoService;
import com.chisondo.server.modules.tea.constant.TeaSpectrumConstant;
import com.chisondo.server.modules.tea.entity.AppChapuEntity;
import com.chisondo.server.modules.tea.service.AppChapuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;


@Service("deviceStateInfoService")
@Slf4j
public class DeviceStateInfoServiceImpl implements DeviceStateInfoService {
	@Autowired
	private DeviceStateInfoDao deviceStateInfoDao;

	@Autowired
	private RedisUtils redisUtils;

	@Autowired
	private ActivedDeviceInfoService deviceInfoService;

	@Autowired
	private AppChapuService chapuService;
	
	@Override
	public DeviceStateInfoEntity queryObject(String deviceId){
		return deviceStateInfoDao.queryObject(deviceId);
	}
	
	@Override
	public List<DeviceStateInfoEntity> queryList(Map<String, Object> map){
		return deviceStateInfoDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return deviceStateInfoDao.queryTotal(map);
	}
	
	@Override
	public void save(DeviceStateInfoEntity deviceStateInfo){
		deviceStateInfoDao.save(deviceStateInfo);
	}
	
	@Override
	public void update(DeviceStateInfoEntity deviceStateInfo){
		deviceStateInfoDao.update(deviceStateInfo);
	}
	
	@Override
	public void delete(Integer deviceId){
		deviceStateInfoDao.delete(deviceId);
	}
	
	@Override
	public void deleteBatch(Integer[] deviceIds){
		deviceStateInfoDao.deleteBatch(deviceIds);
	}

	@Override
	public void saveOrUpdate(DeviceBindReqDTO devBindReq) {
		DeviceStateInfoEntity devStateInfo = this.queryObject(devBindReq.getDeviceId());
		if (ValidateUtils.isEmpty(devStateInfo)) {
			devStateInfo = new DeviceStateInfoEntity();
			this.setDevStateAttrs(devBindReq, devStateInfo);
			this.save(devStateInfo);
		} else {
			this.setDevStateAttrs(devBindReq, devStateInfo);
			this.update(devStateInfo);
		}
	}

	private void setDevStateAttrs(DeviceBindReqDTO devBindReq, DeviceStateInfoEntity devStateInfo) {
		devStateInfo.setDeviceId(devBindReq.getDeviceId());
		devStateInfo.setUpdateTime(DateUtils.currentDate());
		if (ValidateUtils.isEmptyString(devStateInfo.getDeviceStateInfo())) {
			devStateInfo.setDeviceStateInfo("DEV" + devBindReq.getDeviceId());
		}
		if (ValidateUtils.isEmpty(devStateInfo.getOnlineState())) {
			devStateInfo.setOnlineState(Constant.OnlineState.NO);
		}
		if (ValidateUtils.isEmpty(devStateInfo.getConnectState())) {
			devStateInfo.setConnectState(Constant.ConnectState.NOT_CONNECTED);
		}
		if (ValidateUtils.isEmptyString(devStateInfo.getClientIpAddress())) {
			devStateInfo.setClientIpAddress(HttpContextUtils.getHttpServletRequest().getRemoteAddr());
		}
		if (ValidateUtils.isNotEmptyString(devBindReq.getLongitude())) {
			devStateInfo.setLongitude(Double.valueOf(devBindReq.getLongitude()));
		}
		if (ValidateUtils.isNotEmptyString(devBindReq.getLatitude())) {
			devStateInfo.setLatitude(Double.valueOf(devBindReq.getLatitude()));
		}
		if (ValidateUtils.isNotEmptyString(devBindReq.getProvince())) {
			devStateInfo.setProvince(devBindReq.getProvince());
		}
		if (ValidateUtils.isNotEmptyString(devBindReq.getCity())) {
			devStateInfo.setCity(devBindReq.getCity());
		}
		if (ValidateUtils.isNotEmptyString(devBindReq.getDistrict())) {
			devStateInfo.setDistrict(devBindReq.getDistrict());
		}
		if (ValidateUtils.isNotEmptyString(devBindReq.getDetaddress())) {
			devStateInfo.setAddress(devBindReq.getDetaddress());
		}
	}

	@Override
	public void updateDevStatus(DevStatusReportResp devStatusReportResp) {
		//  根据设备ID查询设备状态信息是否存在
		DeviceStateInfoEntity existedDevState = this.queryObject(devStatusReportResp.getDbDeviceId());
		DeviceStateInfoEntity devStateInfo = this.buildDevStateInfo(devStatusReportResp, existedDevState);
//		log.info("devStateInfo JSON = {}", JSONObject.toJSONString(devStateInfo));
		if (ValidateUtils.isEmpty(existedDevState)) {
			devStateInfo.setDeviceStateInfo(devStateInfo.getDeviceId() + "STATE");
			devStateInfo.setClientIpAddress(devStatusReportResp.getClientIP());
			if (ValidateUtils.isEmpty(devStateInfo.getConnectState())) {
				devStateInfo.setConnectState(Constant.ConnectState.NOT_CONNECTED);
			}
			// 不存在则添加
			this.save(devStateInfo);
			log.info("add device state success");
		} else {
			this.update(devStateInfo);
			log.info("updateDevStatus success");
		}
		// 把设备状态信息保存到 redis 中
		this.save2Redis(devStatusReportResp, devStateInfo);
	}

	/**
	 * 处理设备工作剩余时间
	 * @param devStatusResp
	 * @param devStateInfo
	 */
	private void processDevWorkRemainTime(final DevStatusRespDTO devStatusResp, final DeviceStateInfoEntity devStateInfo) {
		ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(2,
				new BasicThreadFactory.Builder().namingPattern("scheduled-pool2-%d").daemon(true).build());
		scheduledExecutorService.execute(() -> {
			log.info("开始倒计时处理，设备ID = {}, remain = {}", devStatusResp.getDeviceId(), devStatusResp.getReamin());
			boolean needUpdate = true;
			int remainTime = devStatusResp.getReamin() - 1;
			for (int i = remainTime; i >= 0; i--) {
				try {
					DevStatusRespDTO tempDevStatusResp = this.redisUtils.get(devStatusResp.getDeviceId(), DevStatusRespDTO.class);
					if (ValidateUtils.isNotEmpty(tempDevStatusResp) && ValidateUtils.equals(tempDevStatusResp.getReamin(), 0)) {
						needUpdate = false;
						break;
					}
					tempDevStatusResp.setReamin(i);
					this.redisUtils.set(devStatusResp.getDeviceId(), tempDevStatusResp);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					log.error("更新设备工作剩余时间失败！", e);
				}
			}
			DevStatusRespDTO tempDevStatusResp = this.redisUtils.get(devStatusResp.getDeviceId(), DevStatusRespDTO.class);
			tempDevStatusResp.setCountdownFlag(false);
			tempDevStatusResp.setReamin(0);
			this.redisUtils.set(devStatusResp.getDeviceId(), tempDevStatusResp);
		});
	}

	private DeviceStateInfoEntity buildDevStateInfo(DevStatusReportResp devStatusReportResp, DeviceStateInfoEntity existedDevState) {
		DeviceStateInfoEntity devStateInfo = ValidateUtils.isNotEmpty(existedDevState) ? existedDevState : new DeviceStateInfoEntity();
		devStateInfo.setOnlineState(Constant.OnlineState.YES);
//		devStateInfo.setConnectState(Constant.ConnectState.CONNECTED);
		devStateInfo.setLastConnTime(devStateInfo.getUpdateTime());
		devStateInfo.setUpdateTime(DateUtils.currentDate());
		devStateInfo.setDeviceId(devStatusReportResp.getDbDeviceId());
		devStateInfo.setNewDeviceId(devStatusReportResp.getDeviceID());
		if (ValidateUtils.isNotEmpty(devStatusReportResp.getMsg().getChapuId())) {
			if (ValidateUtils.notEquals(devStatusReportResp.getMsg().getChapuId(), devStateInfo.getChapuId())) {
				AppChapuEntity teaSpectrum = this.chapuService.queryObject(devStatusReportResp.getMsg().getChapuId());
				devStateInfo.setChapuName(teaSpectrum.getName());
				devStateInfo.setChapuImage(CommonUtils.plusFullImgPath(teaSpectrum.getImage()));
				devStateInfo.setChapuMakeTimes(teaSpectrum.getMakeTimes());
			}
			devStateInfo.setChapuId(devStatusReportResp.getMsg().getChapuId());
		}
		if (ValidateUtils.isNotEmpty(devStatusReportResp.getMsg().getStep())) {
			devStateInfo.setIndex(devStatusReportResp.getMsg().getStep());
		}
//		devStateInfo.setDeviceStateInfo("");
		devStateInfo.setLastValTime(devStatusReportResp.getTcpValTime());
		devStateInfo.setMakeTemp(devStatusReportResp.getMsg().getTemperature());
		devStateInfo.setTemp(devStatusReportResp.getMsg().getTemperature());
		devStateInfo.setWarm(devStatusReportResp.getMsg().getWarmstatus());
		devStateInfo.setDensity(devStatusReportResp.getMsg().getTaststatus());
		devStateInfo.setWaterlv(devStatusReportResp.getMsg().getWaterlevel());
		devStateInfo.setMakeDura(devStatusReportResp.getMsg().getSoak());
		devStateInfo.setReamin(Integer.valueOf(devStatusReportResp.getMsg().getRemaintime()));
		devStateInfo.setTea(Constant.ErrorStatus.LACK_TEA == devStatusReportResp.getMsg().getErrorstatus() ? 1 : 0);
		devStateInfo.setWater(Constant.ErrorStatus.LACK_WATER == devStatusReportResp.getMsg().getErrorstatus() ? 1 : 0);
		devStateInfo.setWork(devStatusReportResp.getMsg().getWorkstatus());
		return devStateInfo;
	}

	@Override
	public void setDevChapu2Finish(Map<String, Object> params) {
		this.deviceStateInfoDao.setDevChapu2Finish(params);
	}

	@Override
	@Deprecated
	public void updateDevStateFromRedis(String newDevId) {
		ActivedDeviceInfoEntity deviceInfo = this.deviceInfoService.getNewDeviceByNewDevId(newDevId);
		if (ValidateUtils.isEmpty(deviceInfo)) {
			log.error("新设备[{}]信息不存在！", newDevId);
			return;
		}
		DevStatusRespDTO devStatusResp = this.redisUtils.get(newDevId, DevStatusRespDTO.class);
		DeviceStateInfoEntity deviceState = new DeviceStateInfoEntity();
		deviceState.setDeviceId(deviceInfo.getDeviceId());
		deviceState.setUpdateTime(DateUtils.currentDate());
		deviceState.setLastConnTime(DateUtils.currentDate());
		deviceState.setMakeTemp(devStatusResp.getMakeTemp());
		deviceState.setTemp(devStatusResp.getTemp());
		deviceState.setWarm(devStatusResp.getWarm());
		deviceState.setDensity(devStatusResp.getDensity());
		deviceState.setWaterlv(devStatusResp.getWaterlv());
		deviceState.setMakeDura(devStatusResp.getMakeDura());
		deviceState.setReamin(devStatusResp.getReamin());
		deviceState.setTea(devStatusResp.getTea());
		deviceState.setWater(devStatusResp.getWater());
		deviceState.setWork(devStatusResp.getWork());
		if (this.hasWorkRemainTime(devStatusResp)) {
			this.processDevWorkRemainTime(devStatusResp, deviceState);
		} else {
			this.update(deviceState);
		}
		log.error("更新设备[{}]状态信息成功！新设备ID = {}", deviceInfo.getDeviceId(), deviceInfo.getNewDeviceId());
	}

	private boolean hasWorkRemainTime(DevStatusRespDTO devStatusResp) {
		return ValidateUtils.isNotEmpty(devStatusResp.getReamin()) && devStatusResp.getReamin() > 0 && !devStatusResp.isCountdownFlag();
	}

	@Override
	public void save2(DevStatusReportResp devStatusReportResp, DeviceStateInfoEntity devStateInfo) {
		this.save(devStateInfo);
		this.save2Redis(devStatusReportResp, devStateInfo);
	}

	private void save2Redis(DevStatusReportResp devStatusReportResp, DeviceStateInfoEntity devStateInfo) {
		DevStatusRespDTO devStatusResp = this.redisUtils.get(devStatusReportResp.getDeviceID(), DevStatusRespDTO.class);
		devStatusResp.setOnlineStatus(Constant.OnlineState.YES);
		devStatusResp.setMakeTemp(devStateInfo.getMakeTemp());
		devStatusResp.setChapuId(devStateInfo.getChapuId());
		devStatusResp.setChapuName(devStateInfo.getChapuName());
		devStatusResp.setChapuImage(devStateInfo.getChapuImage());
		devStatusResp.setChapuMakeTimes(devStateInfo.getChapuMakeTimes());
		devStatusResp.setTeaSortId(devStateInfo.getTeaSortId());
		devStatusResp.setTeaSortName(devStateInfo.getTeaSortName());
		devStatusResp.setIndex(devStateInfo.getIndex());
		devStatusResp.setMakeType(devStateInfo.getMakeType());
//		devStatusResp.setConnStatus(Constant.ConnectState.CONNECTED);
		if (this.hasWorkRemainTime(devStatusResp)) {
			devStatusResp.setCountdownFlag(true);
			this.redisUtils.set(devStatusReportResp.getDeviceID(), devStatusResp);
			this.processDevWorkRemainTime(devStatusResp, devStateInfo);
		} else {
			this.redisUtils.set(devStatusReportResp.getDeviceID(), devStatusResp);
		}
	}

	@Override
	public void updateConnectState(String deviceId, int connectState) {
		DeviceStateInfoEntity deviceState = new DeviceStateInfoEntity();
		deviceState.setDeviceId(deviceId);
		deviceState.setConnectState(connectState);
		this.update(deviceState);
	}
}
