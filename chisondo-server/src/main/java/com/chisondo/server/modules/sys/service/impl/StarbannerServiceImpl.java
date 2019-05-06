package com.chisondo.server.modules.sys.service.impl;

import com.chisondo.server.modules.sys.dao.StarbannerDao;
import com.chisondo.server.modules.sys.entity.StarbannerEntity;
import com.chisondo.server.modules.sys.service.StarbannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("starbannerService")
public class StarbannerServiceImpl implements StarbannerService {
	@Autowired
	private StarbannerDao starbannerDao;
	
	@Override
	public StarbannerEntity queryObject(Integer bannerId){
		return starbannerDao.queryObject(bannerId);
	}
	
	@Override
	public List<StarbannerEntity> queryList(Map<String, Object> map){
		return starbannerDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return starbannerDao.queryTotal(map);
	}
	
	@Override
	public void save(StarbannerEntity starbanner){
		starbannerDao.save(starbanner);
	}
	
	@Override
	public void update(StarbannerEntity starbanner){
		starbannerDao.update(starbanner);
	}
	
	@Override
	public void delete(Integer bannerId){
		starbannerDao.delete(bannerId);
	}
	
	@Override
	public void deleteBatch(Integer[] bannerIds){
		starbannerDao.deleteBatch(bannerIds);
	}
	
}
