package com.chisondo.server.modules.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.chisondo.server.modules.user.dao.TeamanAdminUserDao;
import com.chisondo.server.modules.user.entity.TeamanAdminUserEntity;
import com.chisondo.server.modules.user.service.TeamanAdminUserService;



@Service("teamanAdminUserService")
public class TeamanAdminUserServiceImpl implements TeamanAdminUserService {
	@Autowired
	private TeamanAdminUserDao teamanAdminUserDao;
	
	@Override
	public TeamanAdminUserEntity queryObject(Integer id){
		return teamanAdminUserDao.queryObject(id);
	}
	
	@Override
	public List<TeamanAdminUserEntity> queryList(Map<String, Object> map){
		return teamanAdminUserDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return teamanAdminUserDao.queryTotal(map);
	}
	
	@Override
	public void save(TeamanAdminUserEntity teamanAdminUser){
		teamanAdminUserDao.save(teamanAdminUser);
	}
	
	@Override
	public void update(TeamanAdminUserEntity teamanAdminUser){
		teamanAdminUserDao.update(teamanAdminUser);
	}
	
	@Override
	public void delete(Integer id){
		teamanAdminUserDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		teamanAdminUserDao.deleteBatch(ids);
	}
	
}
