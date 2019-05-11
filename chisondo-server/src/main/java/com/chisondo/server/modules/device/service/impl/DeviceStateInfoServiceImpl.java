package com.chisondo.server.modules.device.service.impl;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.model.http.resp.DevStatusReportResp;
import com.chisondo.server.common.utils.*;
import com.chisondo.server.modules.device.dto.req.DeviceBindReqDTO;
import com.chisondo.model.http.resp.DevStatusRespDTO;
import com.chisondo.server.modules.device.entity.ActivedDeviceInfoEntity;
import com.chisondo.server.modules.device.service.ActivedDeviceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.chisondo.server.modules.device.dao.DeviceStateInfoDao;
import com.chisondo.server.modules.device.entity.DeviceStateInfoEntity;
import com.chisondo.server.modules.device.service.DeviceStateInfoService;



@Service("deviceStateInfoService")
@Slf4j
public class DeviceStateInfoServiceImpl implements DeviceStateInfoService {
	@Autowired
	private DeviceStateInfoDao deviceStateInfoDao;

	@Autowired
	private RedisUtils redisUtils;

	@Autowired
	private ActivedDeviceInfoService deviceInfoService;
	
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
		devStateInfo.setDeviceStateInfo("DEV" + devBindReq.getDeviceId());
		devStateInfo.setOnlineState(Constant.OnlineState.NO);
		devStateInfo.setConnectState(Constant.ConnectState.NOT_CONNECTED);
		devStateInfo.setClientIpAddress(HttpContextUtils.getHttpServletRequest().getRemoteAddr());
		devStateInfo.setUpdateTime(DateUtils.currentDate());
		devStateInfo.setLongitude(Double.valueOf(devBindReq.getLongitude()));
		devStateInfo.setLatitude(Double.valueOf(devBindReq.getLatitude()));
		devStateInfo.setProvince(devBindReq.getProvince());
		devStateInfo.setCity(devBindReq.getCity());
		devStateInfo.setDistrict(devBindReq.getDistrict());
		devStateInfo.setAddress(devBindReq.getDetaddress());
	}

	@Override
	public void updateDevStatus(DevStatusReportResp devStatusReportResp, String newDeviceId) {
		DeviceStateInfoEntity devStateInfo = this.buildDevStateInfo(devStatusReportResp);
		log.info("devStateInfo JSON = {}", JSONObject.toJSONString(devStateInfo));
		//  根据设备ID查询设备状态信息是否存在
		DeviceStateInfoEntity existedDevState = this.queryObject(devStateInfo.getDeviceId());
		if (ValidateUtils.isEmpty(existedDevState)) {
			devStateInfo.setDeviceStateInfo(devStateInfo.getDeviceId() + "STATE");
			devStateInfo.setClientIpAddress(devStatusReportResp.getClientIP());
			// 不存在则添加
			this.save(devStateInfo);
			log.info("add device state success");
		} else {
			this.update(devStateInfo);
			log.info("updateDevStatus success");
		}
		// 把设备状态信息保存到 redis 中
		devStateInfo.setNewDeviceId(newDeviceId);
		this.save2Redis(devStatusReportResp, devStateInfo);
	}

	/**
	 * 处理设备工作剩余时间
	 * @param devStatusResp
	 * @param devStateInfo
	 */
	private void processDevWorkingRemainTime(final DevStatusRespDTO devStatusResp, final DeviceStateInfoEntity devStateInfo) {
		new Thread(() -> {
			boolean needUpdate = true;
			int remainTime = devStatusResp.getReamin() - 1;
			for (int i = remainTime; i >= 0; i--) {
				try {
					DevStatusRespDTO tempDevStatusResp = this.redisUtils.get(devStatusResp.getDeviceId(), DevStatusRespDTO.class);
					if (tempDevStatusResp.getReamin() == 0) {
						needUpdate = false;
						break;
					}
					devStatusResp.setReamin(i);
					this.redisUtils.set(devStatusResp.getDeviceId(), devStatusResp);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					log.error("更新设备工作剩余时间失败！", e);
				}
			}
			if (!needUpdate) {
				devStateInfo.setReamin(0);
				this.update(devStateInfo);
			}
		}).start();
	}

	private DeviceStateInfoEntity buildDevStateInfo(DevStatusReportResp devStatusReportResp) {
        DeviceStateInfoEntity devStateInfo = this.convert2DevStatusInfo(devStatusReportResp);
		devStateInfo.setOnlineState(Constant.OnlineState.YES);
		devStateInfo.setConnectState(Constant.ConnectState.CONNECTED);
		devStateInfo.setUpdateTime(new Date());
		devStateInfo.setLastConnTime(new Date());
		return devStateInfo;
	}

	public DeviceStateInfoEntity convert2DevStatusInfo(DevStatusReportResp devStatusReportResp) {
		DeviceStateInfoEntity devStateInfo = new DeviceStateInfoEntity();
		devStateInfo.setDeviceId(devStatusReportResp.getDeviceID());
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
	public void updateDevStateFromRedis(String deviceId) {
		ActivedDeviceInfoEntity deviceInfo = this.deviceInfoService.getNewDeviceByNewDevId(deviceId);
		if (ValidateUtils.isEmpty(deviceInfo)) {
			log.error("新设备[{}]信息不存在！", deviceId);
			return;
		}
		DevStatusRespDTO devStatusResp = this.redisUtils.get(deviceId, DevStatusRespDTO.class);
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
		if (devStatusResp.getReamin() > 0) {
			this.processDevWorkingRemainTime(devStatusResp, deviceState);
		} else {
			this.update(deviceState);
		}
		log.error("更新设备[{}]状态信息成功！新设备ID = {}", deviceInfo.getDeviceId(), deviceInfo.getNewDeviceId());
	}

	@Override
	public void save2(DevStatusReportResp devStatusReportResp, DeviceStateInfoEntity devStateInfo) {
		this.save(devStateInfo);
		this.save2Redis(devStatusReportResp, devStateInfo);
	}

	private void save2Redis(DevStatusReportResp devStatusReportResp, DeviceStateInfoEntity devStateInfo) {
		DevStatusRespDTO devStatusResp = CommonUtils.convert2DevStatusInfo(devStatusReportResp, devStateInfo);
		devStatusResp.setOnlineStatus(Constant.OnlineState.YES);
		devStatusResp.setConnStatus(Constant.ConnectState.CONNECTED);
		this.redisUtils.set(devStateInfo.getNewDeviceId(), devStatusResp);
		if (devStatusResp.getReamin() > 0) {
			this.processDevWorkingRemainTime(devStatusResp, devStateInfo);
		}
	}
}
