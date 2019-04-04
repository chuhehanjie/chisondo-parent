package com.chisondo.server.modules.user.service.impl;

import com.chisondo.server.common.utils.Keys;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.datasources.DataSourceNames;
import com.chisondo.server.datasources.DynamicDataSource;
import com.chisondo.server.datasources.aspect.DataSourceAspect;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.chisondo.server.modules.user.dao.UserVipDao;
import com.chisondo.server.modules.user.entity.UserVipEntity;
import com.chisondo.server.modules.user.service.UserVipService;



@Service("dataVipService")
public class UserVipServiceImpl implements UserVipService {
	@Autowired
	private UserVipDao userVipDao;
	
	@Override
	public UserVipEntity queryObject(Long memberId){
		return userVipDao.queryObject(memberId);
	}
	
	@Override
	public List<UserVipEntity> queryList(Map<String, Object> map){
		return userVipDao.queryList(map);
	}

	@Override
	public UserVipEntity getUserByMobile(String mobile) {
		List<UserVipEntity> userData = this.queryList(ImmutableMap.of(Keys.PHONE, mobile));
		return ValidateUtils.isNotEmptyCollection(userData) ? userData.get(0) : null;
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return userVipDao.queryTotal(map);
	}
	
	@Override
	public void save(UserVipEntity dataVip){
		userVipDao.save(dataVip);
	}
	
	@Override
	public void update(UserVipEntity dataVip){
		userVipDao.update(dataVip);
	}
	
	@Override
	public void delete(Long memberId){
		userVipDao.delete(memberId);
	}
	
	@Override
	public void deleteBatch(Long[] memberIds){
		userVipDao.deleteBatch(memberIds);
	}

	@Override
	public UserVipEntity queryUserByMemberId(Long memberId) {
		DynamicDataSource.setDataSource(DataSourceNames.FIRST);
		return this.userVipDao.queryObject(memberId);
	}
}
