package com.chisondo.server.modules.sys.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.chisondo.server.modules.sys.dao.CompanyDao;
import com.chisondo.server.modules.sys.entity.CompanyEntity;
import com.chisondo.server.modules.sys.service.CompanyService;



@Service("companyService")
public class CompanyServiceImpl implements CompanyService {
	@Autowired
	private CompanyDao companyDao;
	
	@Override
	public CompanyEntity queryObject(Integer id){
		return companyDao.queryObject(id);
	}
	
	@Override
	public List<CompanyEntity> queryList(Map<String, Object> map){
		return companyDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return companyDao.queryTotal(map);
	}
	
	@Override
	public void save(CompanyEntity company){
		companyDao.save(company);
	}
	
	@Override
	public void update(CompanyEntity company){
		companyDao.update(company);
	}
	
	@Override
	public void delete(Integer id){
		companyDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		companyDao.deleteBatch(ids);
	}
	
}
