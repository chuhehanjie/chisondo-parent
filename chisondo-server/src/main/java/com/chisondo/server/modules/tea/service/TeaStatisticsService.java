package com.chisondo.server.modules.tea.service;

import com.chisondo.server.modules.tea.entity.TeaStatisticsEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 12.19
 */
public interface TeaStatisticsService {
	
	TeaStatisticsEntity queryObject(String userId);
	
	List<TeaStatisticsEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TeaStatisticsEntity teaStatistics);
	
	void update(TeaStatisticsEntity teaStatistics);
	
	void delete(String userId);
	
	void deleteBatch(String[] userIds);
}
