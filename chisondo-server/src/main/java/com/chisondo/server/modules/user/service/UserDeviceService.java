package com.chisondo.server.modules.user.service;

import com.chisondo.server.modules.device.dto.req.DeviceBindReqDTO;
import com.chisondo.server.modules.user.entity.UserDeviceEntity;
import com.google.common.collect.ImmutableMap;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 12.19
 */
public interface UserDeviceService {
	
	UserDeviceEntity queryObject(Integer id);
	
	List<UserDeviceEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(UserDeviceEntity userDevice);
	
	void update(UserDeviceEntity userDevice);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);

	void save(DeviceBindReqDTO devBindReq, Long userId);

	void delUserDeviceByParams(Map<String, Object> params);

    void setDefaultDevice(Map<String, Object> params);

    void setNoneDefaultDev(Long userId);

    Map<String, Object> queryAllUsersOfDevice(String deviceId);
}
