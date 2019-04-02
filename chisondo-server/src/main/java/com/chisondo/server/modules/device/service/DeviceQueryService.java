package com.chisondo.server.modules.device.service;

import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.http.CommonResp;

public interface DeviceQueryService {
	
	CommonResp queryDevSettingInfo(CommonReq req);

    CommonResp queryHisConnectDevOfUser(String userMobile);

	/**
	 * 查询设备沏茶记录
	 * @param req
	 * @return
	 */
	CommonResp queryMakeTeaRecordsOfDev(CommonReq req);

	CommonResp queryMakeTeaRecordsOfUser(CommonReq req);

	/**
	 * 查询设备状态信息
	 * @param req
	 * @return
	 */
    CommonResp queryDevStateInfo(CommonReq req);

    CommonResp queryDeviceDetail(CommonReq req);
}
