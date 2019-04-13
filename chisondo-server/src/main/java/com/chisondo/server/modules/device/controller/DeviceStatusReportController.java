package com.chisondo.server.modules.device.controller;
import java.util.Date;

import com.chisondo.model.http.resp.DevStatusReportResp;
import com.chisondo.server.common.core.AbstractController;
import com.chisondo.server.common.http.CommonResp;
import com.chisondo.server.common.utils.Constant;
import com.chisondo.server.common.utils.DateUtils;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.modules.device.entity.ActivedDeviceInfoEntity;
import com.chisondo.server.modules.device.entity.DeviceStateInfoEntity;
import com.chisondo.server.modules.device.service.ActivedDeviceInfoService;
import com.chisondo.server.modules.device.service.DeviceStateInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.bcel.Const;
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
	public CommonResp updateDevStatus(@RequestBody DevStatusReportResp devStatusReportResp) {
		if (ValidateUtils.isEmpty(this.deviceInfoService.getDeviceInfoById(devStatusReportResp.getDeviceID()))) {
			// 新设备入库
			this.addNewDevice(devStatusReportResp);
			this.addDeviceStateInfo(devStatusReportResp);
		} else {
			// TODO 直接从 redis 查询设备状态
			this.deviceStateInfoService.updateDevStatus(devStatusReportResp);
		}
		return CommonResp.ok();
	}

	private void addDeviceStateInfo(@RequestBody DevStatusReportResp devStatusReportResp) {
		DeviceStateInfoEntity deviceStateInfo = new DeviceStateInfoEntity();
		deviceStateInfo.setDeviceId(devStatusReportResp.getDeviceID());
		deviceStateInfo.setOnlineState(Constant.OnlineState.YES);
		deviceStateInfo.setDeviceStateInfo("TEST10086"); // TODO 待确认 设备状态信息值先写死
		deviceStateInfo.setConnectState(Constant.ConnectState.CONNECTED);
		deviceStateInfo.setUpdateTime(DateUtils.currentDate());
		deviceStateInfo.setLastValTime(devStatusReportResp.getTcpValTime());
		deviceStateInfo.setClientIpAddress(devStatusReportResp.getClientIP());
		deviceStateInfo.setTemp(devStatusReportResp.getMsg().getNowwarm()); // 即时温度值
		deviceStateInfo.setWarm(devStatusReportResp.getMsg().getWarmstatus());
		deviceStateInfo.setWaterlv(devStatusReportResp.getMsg().getWaterlevel());
		deviceStateInfo.setMakeDura(devStatusReportResp.getMsg().getSoak()); // 浸泡时间
		deviceStateInfo.setReamin(Integer.valueOf(devStatusReportResp.getMsg().getRemaintime()));
		deviceStateInfo.setTea(ValidateUtils.equals(Constant.ErrorStatus.LACK_TEA, devStatusReportResp.getMsg().getErrorstatus()) ? 1 : null);
		deviceStateInfo.setWater(ValidateUtils.equals(Constant.ErrorStatus.LACK_WATER, devStatusReportResp.getMsg().getErrorstatus()) ? Constant.ErrorStatus.LACK_WATER : null);
		deviceStateInfo.setWork(devStatusReportResp.getMsg().getWorkstatus());
		deviceStateInfo.setDensity(null);
		deviceStateInfo.setLastConnTime(null);
		deviceStateInfo.setLongitude(0.0D);
		deviceStateInfo.setLatitude(0.0D);
		deviceStateInfo.setProvince("");
		deviceStateInfo.setCity("");
		deviceStateInfo.setDistrict("");
		deviceStateInfo.setAddress("");
		deviceStateInfo.setMakeTemp(null);
		deviceStateInfo.setMakeType(null);
		deviceStateInfo.setTeaSortId(null);
		deviceStateInfo.setTeaSortName(null);
		deviceStateInfo.setChapuId(null);
		deviceStateInfo.setChapuName(null);
		deviceStateInfo.setChapuImage(null);
		deviceStateInfo.setChapuMakeTimes(0);
		deviceStateInfo.setIndex(-1);
		this.deviceStateInfoService.save(deviceStateInfo);
	}

	private void addNewDevice(@RequestBody DevStatusReportResp devStatusReportResp) {
		ActivedDeviceInfoEntity deviceInfo = new ActivedDeviceInfoEntity();
		deviceInfo.setDeviceId(devStatusReportResp.getDeviceID());
		deviceInfo.setDeviceName(Constant.DEF_DEV_NAME);
		deviceInfo.setDeviceTypeId(3); // 3 表示新设备
		deviceInfo.setActivedTime(DateUtils.currentDate());
		deviceInfo.setPassword(Constant.DEF_PWD);
		deviceInfo.setDevColor(1); // 默认颜色
			/*deviceInfo.setTermId("");
			deviceInfo.setDevDesc("");
			deviceInfo.setAdminName("");
			deviceInfo.setAdminPhone("");*/
		deviceInfo.setRestId(0);
		deviceInfo.setLockPanel(0);
		deviceInfo.setCompanyId(-1);
		deviceInfo.setVolFlag(null);
		deviceInfo.setGmsFlag(null);
		deviceInfoService.save(deviceInfo);
	}

}
