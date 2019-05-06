package com.chisondo.server.modules.sys.service;

import com.chisondo.server.modules.sys.entity.StarbannerEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since May 06.19
 */
public interface StarbannerService {
	
	StarbannerEntity queryObject(Integer id);
	
	List<StarbannerEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(StarbannerEntity starbanner);
	
	void update(StarbannerEntity starbanner);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
