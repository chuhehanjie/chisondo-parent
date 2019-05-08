package com.chisondo.server.modules.user.service.impl;

import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.utils.Constant;
import com.chisondo.server.common.utils.Keys;
import com.chisondo.server.common.utils.Query;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.modules.device.dto.resp.MakeTeaRowRespDTO;
import com.chisondo.server.modules.user.dao.UserMakeTeaDao;
import com.chisondo.server.modules.user.entity.UserMakeTeaEntity;
import com.chisondo.server.modules.user.service.UserMakeTeaService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;



@Service("userMakeTeaService")
public class UserMakeTeaServiceImpl implements UserMakeTeaService {
	@Autowired
	private UserMakeTeaDao userMakeTeaDao;
	
	@Override
	public UserMakeTeaEntity queryObject(Integer id){
		return userMakeTeaDao.queryObject(id);
	}
	
	@Override
	public List<UserMakeTeaEntity> queryList(Map<String, Object> map){
		return userMakeTeaDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return userMakeTeaDao.queryTotal(map);
	}
	
	@Override
	public void save(UserMakeTeaEntity userMakeTea){
		userMakeTeaDao.save(userMakeTea);
	}
	
	@Override
	public void update(UserMakeTeaEntity userMakeTea){
		userMakeTeaDao.update(userMakeTea);
	}
	
	@Override
	public void delete(Integer id){
		userMakeTeaDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		userMakeTeaDao.deleteBatch(ids);
	}

	@Override
	public int countMakeTeaRecordsByDeviceId(String deviceId) {
		return this.userMakeTeaDao.countMakeTeaRecordsByDeviceId(deviceId);
	}

	@Override
	public List<MakeTeaRowRespDTO> queryMakeTeaRecordsByDeviceId(Map<String, Object> params) {
		return this.userMakeTeaDao.queryMakeTeaRecordsByDeviceId(params);
	}

	@Override
	public int countMakeTeaRecordsByUserMobile(String userMobile) {
		return this.userMakeTeaDao.countMakeTeaRecordsByUserMobile(userMobile);
	}

	@Override
	public List<MakeTeaRowRespDTO> queryMakeTeaRecordsByUserMobile(Map<String, Object> params) {
		return this.userMakeTeaDao.queryMakeTeaRecordsByUserMobile(params);
	}

}
