package com.chisondo.server.modules.device.controller;

import com.chisondo.server.common.core.AbstractController;
import com.chisondo.server.common.http.CommonResp;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.modules.device.dto.req.DevStatusReportReq;
import com.chisondo.server.modules.device.service.ActivedDeviceInfoService;
import com.chisondo.server.modules.device.service.DeviceStateInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 设备状态上报 controller
 * @author ding.zhong
 * @email 258321511@qq.com
 * @date Mar 26.19
 */
@RestController
@Slf4j
public class DeviceStatusReportController extends AbstractController {

	@Autowired
	private DeviceStateInfoService deviceStateInfoService;

	@Autowired
	private ActivedDeviceInfoService deviceInfoService;
	/**
	 * 设备状态上报
	 * 由 设备 ->> 设备TCP报务 ->> 应用HTTP接收并更新设备状态
	 */
	@RequestMapping("/api/rest/currentState")
	public CommonResp updateDevStatus(@RequestBody  DevStatusReportReq devStatusReportReq) {
		if (ValidateUtils.isEmpty(this.deviceInfoService.getDeviceInfoById(devStatusReportReq.getDeviceID()))) {
			return CommonResp.error("设备[" + devStatusReportReq.getDeviceID() + "]不存在！");
		} else {
			// TODO 直接从 redis 查询设备状态
			this.deviceStateInfoService.updateDevStatus(devStatusReportReq);
			return CommonResp.ok();
		}
	}

}
