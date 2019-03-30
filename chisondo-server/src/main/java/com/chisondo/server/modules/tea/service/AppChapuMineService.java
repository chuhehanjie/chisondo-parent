package com.chisondo.server.modules.tea.service;

import com.chisondo.server.modules.tea.entity.AppChapuMineEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 17.19
 */
public interface AppChapuMineService {
	
	AppChapuMineEntity queryObject(Integer userId);
	
	List<AppChapuMineEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(AppChapuMineEntity appChapuMine);
	
	void update(AppChapuMineEntity appChapuMine);
	
	void delete(Integer userId);
	
	void deleteBatch(Integer[] userIds);
}
