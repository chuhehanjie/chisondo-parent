package com.chisondo.server.modules.tea.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.chisondo.server.modules.tea.dao.TeaStatisticsDao;
import com.chisondo.server.modules.tea.entity.TeaStatisticsEntity;
import com.chisondo.server.modules.tea.service.TeaStatisticsService;



@Service("teaStatisticsService")
public class TeaStatisticsServiceImpl implements TeaStatisticsService {
	@Autowired
	private TeaStatisticsDao teaStatisticsDao;
	
	@Override
	public TeaStatisticsEntity queryObject(String userId){
		return teaStatisticsDao.queryObject(userId);
	}
	
	@Override
	public List<TeaStatisticsEntity> queryList(Map<String, Object> map){
		return teaStatisticsDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return teaStatisticsDao.queryTotal(map);
	}
	
	@Override
	public void save(TeaStatisticsEntity teaStatistics){
		teaStatisticsDao.save(teaStatistics);
	}
	
	@Override
	public void update(TeaStatisticsEntity teaStatistics){
		teaStatisticsDao.update(teaStatistics);
	}
	
	@Override
	public void delete(String userId){
		teaStatisticsDao.delete(userId);
	}
	
	@Override
	public void deleteBatch(String[] userIds){
		teaStatisticsDao.deleteBatch(userIds);
	}
	
}
