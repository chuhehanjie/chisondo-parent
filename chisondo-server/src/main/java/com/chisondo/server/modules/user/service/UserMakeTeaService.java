package com.chisondo.server.modules.user.service;

import com.chisondo.server.modules.device.dto.resp.MakeTeaRowRespDTO;
import com.chisondo.server.modules.user.entity.UserMakeTeaEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 12.19
 */
public interface UserMakeTeaService {
	
	UserMakeTeaEntity queryObject(Integer id);
	
	List<UserMakeTeaEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(UserMakeTeaEntity userMakeTea);
	
	void update(UserMakeTeaEntity userMakeTea);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);

    List<MakeTeaRowRespDTO> queryMakeTeaRecordsByDeviceId(Map<String, Object> params);

    int countMakeTeaRecordsByDeviceId(String deviceId);
}
