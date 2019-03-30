package com.chisondo.server.modules.tea.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.chisondo.server.modules.tea.dao.AppChapuParaDao;
import com.chisondo.server.modules.tea.entity.AppChapuParaEntity;
import com.chisondo.server.modules.tea.service.AppChapuParaService;



@Service("appChapuParaService")
public class AppChapuParaServiceImpl implements AppChapuParaService {
	@Autowired
	private AppChapuParaDao appChapuParaDao;
	
	@Override
	public AppChapuParaEntity queryObject(Integer chapuId){
		return appChapuParaDao.queryObject(chapuId);
	}
	
	@Override
	public List<AppChapuParaEntity> queryList(Map<String, Object> map){
		return appChapuParaDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return appChapuParaDao.queryTotal(map);
	}
	
	@Override
	public void save(AppChapuParaEntity appChapuPara){
		appChapuParaDao.save(appChapuPara);
	}
	
	@Override
	public void update(AppChapuParaEntity appChapuPara){
		appChapuParaDao.update(appChapuPara);
	}
	
	@Override
	public void delete(Integer chapuId){
		appChapuParaDao.delete(chapuId);
	}
	
	@Override
	public void deleteBatch(Integer[] chapuIds){
		appChapuParaDao.deleteBatch(chapuIds);
	}
	
}
