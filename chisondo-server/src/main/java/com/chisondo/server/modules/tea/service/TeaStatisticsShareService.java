package com.chisondo.server.modules.tea.service;

import com.chisondo.server.modules.tea.entity.TeaStatisticsShareEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 12.19
 */
public interface TeaStatisticsShareService {
	
	TeaStatisticsShareEntity queryObject(Integer rowId);
	
	List<TeaStatisticsShareEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TeaStatisticsShareEntity teaStatisticsShare);
	
	void update(TeaStatisticsShareEntity teaStatisticsShare);
	
	void delete(Integer rowId);
	
	void deleteBatch(Integer[] rowIds);
}
