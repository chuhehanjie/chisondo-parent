package com.chisondo.server.modules.device.service.impl;

import com.chisondo.server.common.http.CommonResp;
import com.chisondo.server.modules.device.dto.req.SetDevNameReqDTO;
import com.chisondo.server.modules.device.dto.req.SetDevPwdReqDTO;
import com.chisondo.server.modules.device.dto.req.SetDevSoundReqDTO;
import com.chisondo.server.modules.device.dto.resp.DevQueryRespDTO;
import com.chisondo.server.modules.device.dto.resp.DeviceInfoRespDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.chisondo.server.modules.device.dao.ActivedDeviceInfoDao;
import com.chisondo.server.modules.device.entity.ActivedDeviceInfoEntity;
import com.chisondo.server.modules.device.service.ActivedDeviceInfoService;



@Service("deviceInfoService")
public class ActivedDeviceInfoServiceImpl implements ActivedDeviceInfoService {
	@Autowired
	private ActivedDeviceInfoDao deviceInfoDao;
	
	@Override
	public ActivedDeviceInfoEntity queryObject(Integer deviceId){
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
	public CommonResp queryHisConnectDevOfUser(String userMobile) {
		List<DeviceInfoRespDTO> devInfoList = this.deviceInfoDao.queryHisConnectDevOfUserByPhone(userMobile);
		DevQueryRespDTO devQueryResp = new DevQueryRespDTO(devInfoList);
		return CommonResp.ok(devQueryResp);
	}

	@Override
	public ActivedDeviceInfoEntity getDeviceInfoById(String deviceId) {
		return this.queryObject(Integer.valueOf(deviceId));
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
}
