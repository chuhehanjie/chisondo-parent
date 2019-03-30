package com.chisondo.server.modules.device.service;

import com.chisondo.server.modules.device.entity.DeviceOperateLogEntity;

import java.util.List;
import java.util.Map;

/**
 * 设备操作日志表
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 17.19
 */
public interface DeviceOperateLogService {
	
	DeviceOperateLogEntity queryObject(Long id);
	
	List<DeviceOperateLogEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(DeviceOperateLogEntity deviceOperateLog);
	
	void update(DeviceOperateLogEntity deviceOperateLog);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
