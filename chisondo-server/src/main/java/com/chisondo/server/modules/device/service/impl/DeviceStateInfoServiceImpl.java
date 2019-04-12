package com.chisondo.server.modules.device.service.impl;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.utils.*;
import com.chisondo.server.modules.device.dto.req.DevStatusReportReq;
import com.chisondo.server.modules.device.dto.req.DeviceBindReqDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.bcel.Const;
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
		devStateInfo.setDeviceId(Integer.valueOf(devBindReq.getDeviceId()));
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
	public void updateDevStatus(DevStatusReportReq devStatusReportReq) {
		DeviceStateInfoEntity devStateInfo = this.buildDevStateInfo(devStatusReportReq);
		log.info("devStateInfo JSON = {}", JSONObject.toJSONString(devStateInfo));
		this.update(devStateInfo);
		log.info("updateDevStatus success");
	}

	private DeviceStateInfoEntity buildDevStateInfo(DevStatusReportReq devStatusReportReq) {
        DeviceStateInfoEntity devStateInfo = this.convert2DevStatusInfo(devStatusReportReq);
		devStateInfo.setOnlineState(Constant.OnlineState.YES);
		devStateInfo.setConnectState(Constant.ConnectState.CONNECTED);
		devStateInfo.setUpdateTime(new Date());
		devStateInfo.setLastConnTime(new Date());
		return devStateInfo;
	}

	public DeviceStateInfoEntity convert2DevStatusInfo(DevStatusReportReq devStatusReportReq) {
		DeviceStateInfoEntity devStateInfo = new DeviceStateInfoEntity();
		devStateInfo.setDeviceId(Integer.valueOf(devStatusReportReq.getDeviceID()));
//		devStateInfo.setDeviceStateInfo("");
		devStateInfo.setLastValTime(devStatusReportReq.getTcpValTime());
		devStateInfo.setMakeTemp(devStatusReportReq.getMsg().getTemperature());
		devStateInfo.setTemp(devStatusReportReq.getMsg().getTemperature());
		devStateInfo.setWarm(devStatusReportReq.getMsg().getWarmstatus());
		devStateInfo.setDensity(devStatusReportReq.getMsg().getTaststatus());
		devStateInfo.setWaterlv(devStatusReportReq.getMsg().getWaterlevel());
		devStateInfo.setMakeDura(devStatusReportReq.getMsg().getSoak());
		devStateInfo.setReamin(Integer.valueOf(devStatusReportReq.getMsg().getRemaintime()));
		devStateInfo.setTea(Constant.ErrorStatus.LACK_TEA == devStatusReportReq.getMsg().getErrorstatus() ? 1 : 0);
		devStateInfo.setWater(Constant.ErrorStatus.LACK_WATER == devStatusReportReq.getMsg().getErrorstatus() ? 1 : 0);
		devStateInfo.setWork(devStatusReportReq.getMsg().getWorkstatus());
		return devStateInfo;
	}

	@Override
	public void setDevChapu2Finish(Map<String, Object> params) {
		this.deviceStateInfoDao.setDevChapu2Finish(params);
	}
}
