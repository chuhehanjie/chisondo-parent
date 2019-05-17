package com.chisondo.server.modules.device.service.impl;

import com.chisondo.server.common.utils.CommonUtils;
import com.chisondo.server.common.utils.Constant;
import com.chisondo.server.common.utils.Keys;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.modules.device.dto.req.SetDevNameReqDTO;
import com.chisondo.server.modules.device.dto.req.SetDevPwdReqDTO;
import com.chisondo.server.modules.device.dto.req.SetDevSoundReqDTO;
import com.chisondo.server.modules.device.dto.resp.DeviceInfoRespDTO;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.chisondo.server.modules.device.dao.ActivedDeviceInfoDao;
import com.chisondo.server.modules.device.entity.ActivedDeviceInfoEntity;
import com.chisondo.server.modules.device.service.ActivedDeviceInfoService;

import javax.validation.Valid;


@Service("deviceInfoService")
public class ActivedDeviceInfoServiceImpl implements ActivedDeviceInfoService {
	@Autowired
	private ActivedDeviceInfoDao deviceInfoDao;
	
	@Override
	public ActivedDeviceInfoEntity queryObject(String deviceId){
		return deviceInfoDao.queryObject(deviceId);
	}
	
	@Override
	public List<ActivedDeviceInfoEntity> queryList(Map<String, Object> map){
		return deviceInfoDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return deviceInfoDao.queryTotal(map);
	}
	
	@Override
	public void save(ActivedDeviceInfoEntity activedDeviceInfo){
		deviceInfoDao.save(activedDeviceInfo);
	}
	
	@Override
	public void update(ActivedDeviceInfoEntity activedDeviceInfo){
		deviceInfoDao.update(activedDeviceInfo);
	}
	
	@Override
	public void delete(Integer deviceId){
		deviceInfoDao.delete(deviceId);
	}
	
	@Override
	public void deleteBatch(Integer[] deviceIds){
		deviceInfoDao.deleteBatch(deviceIds);
	}

	@Override
	public List<DeviceInfoRespDTO> queryHisConnectDevOfUser(Long userId) {
		List<DeviceInfoRespDTO> devInfoList = this.deviceInfoDao.queryHisConnectDevOfUserByUserId(userId);
		CommonUtils.processDevTypeAndOnlineStatus(devInfoList);
		return devInfoList;
	}

	@Override
	public ActivedDeviceInfoEntity getDeviceInfoById(String deviceId) {
		return this.queryObject(deviceId);
	}

	@Override
	public void updateDevPwd(SetDevPwdReqDTO setDevPwdReq) {
		this.deviceInfoDao.updateDevPwd(setDevPwdReq);
	}

	@Override
	public void updateDevNameOrDesc(SetDevNameReqDTO setDevNameReq) {
		this.deviceInfoDao.updateDevNameOrDesc(setDevNameReq);
	}

	@Override
	public void updateDevSound(SetDevSoundReqDTO setDevSoundReq) {
		this.deviceInfoDao.updateDevSound(setDevSoundReq);
	}

	@Override
	public List<DeviceInfoRespDTO> queryDeviceDetail(Map<String, Object> params) {
		List<DeviceInfoRespDTO> deviceDetails = this.deviceInfoDao.queryDeviceDetail(params);
		CommonUtils.processDevTypeAndOnlineStatus(deviceDetails);
		return deviceDetails;
	}

	@Override
	public ActivedDeviceInfoEntity getNewDeviceByNewDevId(String deviceId) {
		List<ActivedDeviceInfoEntity> deviceList = this.queryList(ImmutableMap.of(Keys.NEW_DEVICE_ID, deviceId));
		return ValidateUtils.isNotEmptyCollection(deviceList) ? deviceList.get(0) : null;
	}
}
