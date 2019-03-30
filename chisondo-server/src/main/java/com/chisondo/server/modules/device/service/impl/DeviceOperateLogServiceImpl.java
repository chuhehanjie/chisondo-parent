package com.chisondo.server.modules.device.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.chisondo.server.modules.device.dao.DeviceOperateLogDao;
import com.chisondo.server.modules.device.entity.DeviceOperateLogEntity;
import com.chisondo.server.modules.device.service.DeviceOperateLogService;



@Service("deviceOperateLogService")
public class DeviceOperateLogServiceImpl implements DeviceOperateLogService {
	@Autowired
	private DeviceOperateLogDao deviceOperateLogDao;
	
	@Override
	public DeviceOperateLogEntity queryObject(Long id){
		return deviceOperateLogDao.queryObject(id);
	}
	
	@Override
	public List<DeviceOperateLogEntity> queryList(Map<String, Object> map){
		return deviceOperateLogDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return deviceOperateLogDao.queryTotal(map);
	}
	
	@Override
	public void save(DeviceOperateLogEntity deviceOperateLog){
		deviceOperateLogDao.save(deviceOperateLog);
	}
	
	@Override
	public void update(DeviceOperateLogEntity deviceOperateLog){
		deviceOperateLogDao.update(deviceOperateLog);
	}
	
	@Override
	public void delete(Long id){
		deviceOperateLogDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		deviceOperateLogDao.deleteBatch(ids);
	}
	
}
