package com.chisondo.server.modules.sys.service;

import com.chisondo.server.modules.sys.entity.CompanyEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 17.19
 */
public interface CompanyService {
	
	CompanyEntity queryObject(Integer id);
	
	List<CompanyEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(CompanyEntity company);
	
	void update(CompanyEntity company);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
