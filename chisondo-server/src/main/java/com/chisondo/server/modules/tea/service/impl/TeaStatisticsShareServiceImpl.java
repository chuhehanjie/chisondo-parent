package com.chisondo.server.modules.tea.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.chisondo.server.modules.tea.dao.TeaStatisticsShareDao;
import com.chisondo.server.modules.tea.entity.TeaStatisticsShareEntity;
import com.chisondo.server.modules.tea.service.TeaStatisticsShareService;



@Service("teaStatisticsShareService")
public class TeaStatisticsShareServiceImpl implements TeaStatisticsShareService {
	@Autowired
	private TeaStatisticsShareDao teaStatisticsShareDao;
	
	@Override
	public TeaStatisticsShareEntity queryObject(Integer rowId){
		return teaStatisticsShareDao.queryObject(rowId);
	}
	
	@Override
	public List<TeaStatisticsShareEntity> queryList(Map<String, Object> map){
		return teaStatisticsShareDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return teaStatisticsShareDao.queryTotal(map);
	}
	
	@Override
	public void save(TeaStatisticsShareEntity teaStatisticsShare){
		teaStatisticsShareDao.save(teaStatisticsShare);
	}
	
	@Override
	public void update(TeaStatisticsShareEntity teaStatisticsShare){
		teaStatisticsShareDao.update(teaStatisticsShare);
	}
	
	@Override
	public void delete(Integer rowId){
		teaStatisticsShareDao.delete(rowId);
	}
	
	@Override
	public void deleteBatch(Integer[] rowIds){
		teaStatisticsShareDao.deleteBatch(rowIds);
	}
	
}
