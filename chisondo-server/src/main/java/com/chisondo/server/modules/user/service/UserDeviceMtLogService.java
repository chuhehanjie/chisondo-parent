package com.chisondo.server.modules.user.service;

import com.chisondo.server.modules.user.entity.UserDeviceMtLogEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 12.19
 */
public interface UserDeviceMtLogService {
	
	UserDeviceMtLogEntity queryObject(Integer logId);
	
	List<UserDeviceMtLogEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(UserDeviceMtLogEntity userDeviceMtLog);
	
	void update(UserDeviceMtLogEntity userDeviceMtLog);
	
	void delete(Integer logId);
	
	void deleteBatch(Integer[] logIds);
}
