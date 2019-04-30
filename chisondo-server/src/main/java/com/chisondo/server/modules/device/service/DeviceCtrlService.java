package com.chisondo.server.modules.device.service;

import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.http.CommonResp;
import com.chisondo.server.modules.device.dto.resp.DeviceBindRespDTO;
import com.chisondo.server.modules.user.entity.UserBookEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
	CommonResp makeTeaByTeaSpectrum(CommonReq req);

	/**
	 * 洗茶控制
	 * @param req
	 * @return
	 */
	@Transactional
    CommonResp washTea(CommonReq req);

	@Transactional
	CommonResp boilWater(CommonReq req);

	@Transactional
	CommonResp stopWorking(CommonReq req);

	@Transactional
	CommonResp cancelTeaSpectrum(CommonReq req);

	@Transactional
	CommonResp keepWarmCtrl(CommonReq req);

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


    CommonResp changeDevTeaSpectrum(CommonReq req);

    @Transactional
    CommonResp cancelReservation(CommonReq req);

    @Transactional
    void processReverseMakeTea(List<UserBookEntity> userBookList);

    @Transactional
    void updateMakeType4Dev(int makeType, String deviceId);
}
