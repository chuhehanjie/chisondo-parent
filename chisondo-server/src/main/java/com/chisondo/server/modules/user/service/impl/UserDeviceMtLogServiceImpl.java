package com.chisondo.server.modules.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.chisondo.server.modules.user.dao.UserDeviceMtLogDao;
import com.chisondo.server.modules.user.entity.UserDeviceMtLogEntity;
import com.chisondo.server.modules.user.service.UserDeviceMtLogService;



@Service("userDeviceMtLogService")
public class UserDeviceMtLogServiceImpl implements UserDeviceMtLogService {
	@Autowired
	private UserDeviceMtLogDao userDeviceMtLogDao;
	
	@Override
	public UserDeviceMtLogEntity queryObject(Integer logId){
		return userDeviceMtLogDao.queryObject(logId);
	}
	
	@Override
	public List<UserDeviceMtLogEntity> queryList(Map<String, Object> map){
		return userDeviceMtLogDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return userDeviceMtLogDao.queryTotal(map);
	}
	
	@Override
	public void save(UserDeviceMtLogEntity userDeviceMtLog){
		userDeviceMtLogDao.save(userDeviceMtLog);
	}
	
	@Override
	public void update(UserDeviceMtLogEntity userDeviceMtLog){
		userDeviceMtLogDao.update(userDeviceMtLog);
	}
	
	@Override
	public void delete(Integer logId){
		userDeviceMtLogDao.delete(logId);
	}
	
	@Override
	public void deleteBatch(Integer[] logIds){
		userDeviceMtLogDao.deleteBatch(logIds);
	}
	
}
