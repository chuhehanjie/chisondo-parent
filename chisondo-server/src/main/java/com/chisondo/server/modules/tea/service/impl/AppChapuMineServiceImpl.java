package com.chisondo.server.modules.tea.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.chisondo.server.modules.tea.dao.AppChapuMineDao;
import com.chisondo.server.modules.tea.entity.AppChapuMineEntity;
import com.chisondo.server.modules.tea.service.AppChapuMineService;



@Service("appChapuMineService")
public class AppChapuMineServiceImpl implements AppChapuMineService {
	@Autowired
	private AppChapuMineDao appChapuMineDao;
	
	@Override
	public AppChapuMineEntity queryObject(Integer userId){
		return appChapuMineDao.queryObject(userId);
	}
	
	@Override
	public List<AppChapuMineEntity> queryList(Map<String, Object> map){
		return appChapuMineDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return appChapuMineDao.queryTotal(map);
	}
	
	@Override
	public void save(AppChapuMineEntity appChapuMine){
		appChapuMineDao.save(appChapuMine);
	}
	
	@Override
	public void update(AppChapuMineEntity appChapuMine){
		appChapuMineDao.update(appChapuMine);
	}
	
	@Override
	public void delete(Integer userId){
		appChapuMineDao.delete(userId);
	}
	
	@Override
	public void deleteBatch(Integer[] userIds){
		appChapuMineDao.deleteBatch(userIds);
	}

	@Override
	public void deleteByCondition(Map<String, Object> params) {
		this.appChapuMineDao.deleteByCondition(params);
	}
}
