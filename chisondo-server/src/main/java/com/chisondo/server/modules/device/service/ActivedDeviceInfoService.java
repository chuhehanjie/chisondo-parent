package com.chisondo.server.modules.device.service;

import com.chisondo.server.modules.device.dto.req.SetDevNameReqDTO;
import com.chisondo.server.modules.device.dto.req.SetDevPwdReqDTO;
import com.chisondo.server.modules.device.dto.req.SetDevSoundReqDTO;
import com.chisondo.server.modules.device.dto.resp.DeviceInfoRespDTO;
import com.chisondo.server.modules.device.entity.ActivedDeviceInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 17.19
 */
public interface ActivedDeviceInfoService {
	
	ActivedDeviceInfoEntity queryObject(String deviceId);
	
	List<ActivedDeviceInfoEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ActivedDeviceInfoEntity activedDeviceInfo);
	
	void update(ActivedDeviceInfoEntity activedDeviceInfo);
	
	void delete(Integer deviceId);
	
	void deleteBatch(Integer[] deviceIds);

    List<DeviceInfoRespDTO> queryHisConnectDevOfUser(Long userId);

    ActivedDeviceInfoEntity getDeviceInfoById(String deviceId);

    void updateDevPwd(SetDevPwdReqDTO setDevPwdReq);

    void updateDevNameOrDesc(SetDevNameReqDTO setDevNameReq);

    void updateDevSound(SetDevSoundReqDTO setDevSoundReq);

	List<DeviceInfoRespDTO> queryDeviceDetail(Map<String, Object> params);

    ActivedDeviceInfoEntity getNewDeviceByNewDevId(String deviceId);
}
