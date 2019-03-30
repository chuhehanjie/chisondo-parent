package com.chisondo.server.modules.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.chisondo.server.modules.user.dao.UserSchemeDao;
import com.chisondo.server.modules.user.entity.UserSchemeEntity;
import com.chisondo.server.modules.user.service.UserSchemeService;



@Service("userSchemeService")
public class UserSchemeServiceImpl implements UserSchemeService {
	@Autowired
	private UserSchemeDao userSchemeDao;
	
	@Override
	public UserSchemeEntity queryObject(Integer schemeId){
		return userSchemeDao.queryObject(schemeId);
	}
	
	@Override
	public List<UserSchemeEntity> queryList(Map<String, Object> map){
		return userSchemeDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return userSchemeDao.queryTotal(map);
	}
	
	@Override
	public void save(UserSchemeEntity userScheme){
		userSchemeDao.save(userScheme);
	}
	
	@Override
	public void update(UserSchemeEntity userScheme){
		userSchemeDao.update(userScheme);
	}
	
	@Override
	public void delete(Integer schemeId){
		userSchemeDao.delete(schemeId);
	}
	
	@Override
	public void deleteBatch(Integer[] schemeIds){
		userSchemeDao.deleteBatch(schemeIds);
	}
	
}
