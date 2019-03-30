package com.chisondo.server.modules.device.service;

import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.http.CommonResp;
import com.chisondo.server.modules.device.dto.req.*;
import com.chisondo.server.modules.device.dto.resp.DeviceBindRespDTO;
import org.springframework.transaction.annotation.Transactional;

/**
 * 设备控制服务接口
 * @author ding.zhong
 * @email 258321511@qq.com
 * @date Mar 12.19
 */
public interface DeviceCtrlService {

	/**
	 * 开启或预约泡茶
	 * @param req
	 * @return
	 */
	@Transactional
	CommonResp startOrReserveMakeTea(CommonReq req);

	@Transactional
	DeviceBindRespDTO bindDevice(CommonReq devBindReq);

	@Transactional
    void makeTeaByTeaSpectrum(MakeTeaByTeaSpectrumReqDTO makeTeaReq);

	/**
	 * 洗茶控制
	 * @param req
	 * @return
	 */
	@Transactional
    CommonResp washTea(CommonReq req);

	@Transactional
	CommonResp boilWater(BoilWaterReqDTO boilWaterReq);

	@Transactional
	CommonResp stopWorking(StopWorkReqDTO stopWorkReq);

	@Transactional
	CommonResp cancelTeaSpectrum(String devieId);

	@Transactional
	CommonResp keepWarmCtrl(DevCommonReqDTO keepWarmCtrlReq);

	@Transactional
	void delDevConnectRecord(CommonReq devCommonReq);

	@Transactional
    CommonResp setDevicePassword(CommonReq req);

	@Transactional
    CommonResp setDeviceNameOrDesc(CommonReq req);

	@Transactional
    CommonResp setDeviceSound(CommonReq req);

	@Transactional
	CommonResp setDefaultDevice(CommonReq req);
}
