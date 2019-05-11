package com.chisondo.server.modules.device.service;

import com.chisondo.model.http.resp.DevStatusReportResp;
import com.chisondo.server.modules.device.dto.req.DeviceBindReqDTO;
import com.chisondo.server.modules.device.entity.DeviceStateInfoEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 17.19
 */
public interface DeviceStateInfoService {
	
	DeviceStateInfoEntity queryObject(String deviceId);
	
	List<DeviceStateInfoEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(DeviceStateInfoEntity deviceStateInfo);
	
	void update(DeviceStateInfoEntity deviceStateInfo);
	
	void delete(Integer deviceId);
	
	void deleteBatch(Integer[] deviceIds);

    void saveOrUpdate(DeviceBindReqDTO devBindReq);

    @Transactional
    void updateDevStatus(DevStatusReportResp devStatusReportResp, String newDeviceId);

    void setDevChapu2Finish(Map<String, Object> params);

    void updateDevStateFromRedis(String deviceId);

    void save2(DevStatusReportResp devStatusReportResp, DeviceStateInfoEntity deviceStateInfo);
}
