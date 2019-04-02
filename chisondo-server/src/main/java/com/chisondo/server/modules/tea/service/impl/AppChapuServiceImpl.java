package com.chisondo.server.modules.tea.service.impl;

import com.chisondo.server.datasources.DataSourceNames;
import com.chisondo.server.datasources.DynamicDataSource;
import com.chisondo.server.datasources.annotation.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.chisondo.server.modules.tea.dao.AppChapuDao;
import com.chisondo.server.modules.tea.entity.AppChapuEntity;
import com.chisondo.server.modules.tea.service.AppChapuService;



@Service("appChapuService")
public class AppChapuServiceImpl implements AppChapuService {
	@Autowired
	private AppChapuDao appChapuDao;
	
	@Override
	public AppChapuEntity queryObject(Integer chapuId){
		DynamicDataSource.setDataSource(DataSourceNames.SECOND);
		return appChapuDao.queryObject(chapuId);
	}
	
	@Override
	public List<AppChapuEntity> queryList(Map<String, Object> map){
		DynamicDataSource.setDataSource(DataSourceNames.SECOND);
		return appChapuDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return appChapuDao.queryTotal(map);
	}
	
	@Override
	public void save(AppChapuEntity appChapu){
		appChapuDao.save(appChapu);
	}
	
	@Override
	public void update(AppChapuEntity appChapu){
		appChapuDao.update(appChapu);
	}
	
	@Override
	public void delete(Integer chapuId){
		appChapuDao.delete(chapuId);
	}
	
	@Override
	public void deleteBatch(Integer[] chapuIds){
		appChapuDao.deleteBatch(chapuIds);
	}

	@Override
	public AppChapuEntity queryTeaSpectrumById(Integer id) {
		DynamicDataSource.setDataSource(DataSourceNames.SECOND);
		return this.appChapuDao.queryTeaSpectrumById(id);
	}
}
