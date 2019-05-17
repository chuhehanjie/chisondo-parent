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
	CommonResp startOrReserveMakeTea(CommonReq req);

	DeviceBindRespDTO bindDevice(CommonReq devBindReq);

	CommonResp makeTeaByTeaSpectrum(CommonReq req);

	/**
	 * 洗茶控制
	 * @param req
	 * @return
	 */
    CommonResp washTea(CommonReq req);

	CommonResp boilWater(CommonReq req);

	CommonResp stopWorking(CommonReq req);

	CommonResp cancelTeaSpectrum(CommonReq req);

	CommonResp keepWarmCtrl(CommonReq req);

	void delDevConnectRecord(CommonReq devCommonReq);

    CommonResp setDevicePassword(CommonReq req);

    CommonResp setDeviceNameOrDesc(CommonReq req);

    CommonResp setDeviceSound(CommonReq req);

	@Transactional
	CommonResp setDefaultDevice(CommonReq req);


    CommonResp changeDevTeaSpectrum(CommonReq req);

    CommonResp cancelReservation(CommonReq req);

    void processReverseMakeTea(List<UserBookEntity> userBookList);

    void updateMakeType4Dev(int makeType, String deviceId);
}
