package com.chisondo.server.modules.tea.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.chisondo.server.modules.tea.dao.TeamanUserDao;
import com.chisondo.server.modules.tea.entity.TeamanUserEntity;
import com.chisondo.server.modules.tea.service.TeamanUserService;



@Service("teamanUserService")
public class TeamanUserServiceImpl implements TeamanUserService {
	@Autowired
	private TeamanUserDao teamanUserDao;
	
	@Override
	public TeamanUserEntity queryObject(Integer id){
		return teamanUserDao.queryObject(id);
	}
	
	@Override
	public List<TeamanUserEntity> queryList(Map<String, Object> map){
		return teamanUserDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return teamanUserDao.queryTotal(map);
	}
	
	@Override
	public void save(TeamanUserEntity teamanUser){
		teamanUserDao.save(teamanUser);
	}
	
	@Override
	public void update(TeamanUserEntity teamanUser){
		teamanUserDao.update(teamanUser);
	}
	
	@Override
	public void delete(Integer id){
		teamanUserDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		teamanUserDao.deleteBatch(ids);
	}
	
}
