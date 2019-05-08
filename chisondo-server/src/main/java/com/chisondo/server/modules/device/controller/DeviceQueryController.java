package com.chisondo.server.modules.device.controller;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.annotation.ParamValidator;
import com.chisondo.server.common.core.AbstractController;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.http.CommonResp;
import com.chisondo.server.common.utils.Keys;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.modules.device.dto.resp.DevSettingRespDTO;
import com.chisondo.server.modules.device.dto.resp.DevStatusRespDTO;
import com.chisondo.server.modules.device.entity.ActivedDeviceInfoEntity;
import com.chisondo.server.modules.device.service.DeviceQueryService;
import com.chisondo.server.modules.device.validator.DevExistenceValidator;
import com.chisondo.server.modules.device.validator.QueryParamValidator;
import com.chisondo.server.modules.olddevice.service.OldDeviceCtrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 设备查询 controller
 * @author ding.zhong
 * @email 258321511@qq.com
 * @date Mar 12.19
 */
@RestController
public class DeviceQueryController extends AbstractController {

	@Autowired
	private DeviceQueryService deviceQueryService;

	@Autowired
	private OldDeviceCtrlService oldDeviceCtrlService;

	/**
	 * 查询设备实时状态
	 */
	@RequestMapping("/api/rest/qryDevStatus")
	@ParamValidator({DevExistenceValidator.class})
	public CommonResp queryDeviceStatus(@RequestBody CommonReq req) {
		String pwd =  JSONObject.parseObject(req.getBizBody()).getString("pwd");
		if (ValidateUtils.isNotEmptyString(pwd)) {
			// 密码不为空，则需要校验密码
			ActivedDeviceInfoEntity deviceInfo = (ActivedDeviceInfoEntity) req.getAttrByKey(Keys.DEVICE_INFO);
			if (ValidateUtils.notEquals(pwd, deviceInfo.getPassword())) {
				return CommonResp.error("设备密码不正确");
			}
		}
		if (req.isOldDev()) {
			String deviceId = (String) req.getAttrByKey(Keys.DEVICE_ID);
			JSONObject result = this.oldDeviceCtrlService.queryDevStatus(deviceId);
			DevStatusRespDTO devStatusResp = this.buildDevStatusResp(result);
			CommonResp resp = new CommonResp(result.getInteger("STATE"), result.getString("STATE_INFO"), JSONObject.toJSONString(devStatusResp));
			return resp;
		}
		return this.deviceQueryService.queryDevStateInfo(req);
	}

	private DevStatusRespDTO buildDevStatusResp(JSONObject result) {
		DevStatusRespDTO devStatusResp = new DevStatusRespDTO();
		devStatusResp.setConnStatus(result.getInteger("connStatus"));
		devStatusResp.setOnlineStatus(result.getInteger("onlineStatus"));
		devStatusResp.setMakeTemp(result.getInteger("makeTemp"));
		devStatusResp.setTemp(result.getInteger("temp"));
		devStatusResp.setWarm(result.getInteger("warm"));
		devStatusResp.setDensity(result.getInteger("density"));
		devStatusResp.setWaterlv(0);
		devStatusResp.setMakeDura(result.getInteger("makeDura"));
		devStatusResp.setReamin(result.getInteger("reamin"));
		devStatusResp.setTea(result.getInteger("tea"));
		devStatusResp.setWater(result.getInteger("water"));
		devStatusResp.setWork(result.getInteger("work"));
		devStatusResp.setMakeType(result.getInteger("makeType"));
		devStatusResp.setTeaSortId(result.getInteger("teaSortId"));
		devStatusResp.setTeaSortName(result.getString("teaSortName"));
		devStatusResp.setChapuId(result.getInteger(Keys.CHAPU_ID));
		devStatusResp.setChapuName(result.getString("chapuName"));
		devStatusResp.setReservLeftTime(result.getInteger("reservLeftTime"));
		devStatusResp.setChapuImage(result.getString("chapuImage"));
		devStatusResp.setChapuMakeTimes(result.getInteger("chapuMakeTimes"));
		devStatusResp.setIndex(result.getInteger("index"));
		devStatusResp.setUseNum(result.getInteger("useNum"));
		return devStatusResp;
	}

	/**
	 * 查询设备设置信息
	 * @param req
	 * @return
	 */
	@RequestMapping("/api/rest/qryDeviceSetInfo")
	@ParamValidator({DevExistenceValidator.class})
	public CommonResp queryDevSettingInfo(@RequestBody CommonReq req) {
		if (req.isOldDev()) {
			ActivedDeviceInfoEntity deviceInfo = (ActivedDeviceInfoEntity) req.getAttrByKey(Keys.DEVICE_INFO);
			DevSettingRespDTO devSettingResp = new DevSettingRespDTO(deviceInfo.getDeviceName(), deviceInfo.getPassword());
			return CommonResp.ok(devSettingResp);
		}
		return this.deviceQueryService.queryDevSettingInfo(req);
	}

	/**
	 * 查询用户历史连接设备
	 * @param req
	 * @return
	 */
	@RequestMapping("/api/rest/getDeviceConnectedBefore")
	public CommonResp queryHisConnectDevOfUser(@RequestBody CommonReq req) {
		JSONObject jsonObj = JSONObject.parseObject(req.getBizBody());
		if (ValidateUtils.isEmpty(jsonObj) || ValidateUtils.isEmptyString(jsonObj.getString(Keys.PHONE_NUM))) {
			throw new CommonException("手机号为空");
		}
		return this.deviceQueryService.queryHisConnectDevOfUser(jsonObj.getString(Keys.PHONE_NUM));
	}

	/**
	 * 查询设备的沏茶记录
	 * @param req
	 * @return
	 */
	@RequestMapping("/api/rest/qryDeviceTeaRecord")
	public CommonResp queryMakeTeaRecordsOfDev(@RequestBody CommonReq req) {
		/*输入参数	是否必填	参数类型	说明	备注
		phoneNum	Y	String	手机号码
		num	N	int	每页条数
		page	N	int	页码*/
		return this.deviceQueryService.queryMakeTeaRecordsOfDev(req);
	}

	/**
	 * 查询设备详细信息
	 * @param req
	 * @return
	 */
	@RequestMapping("/api/rest/qryDeviceInfo")
	@ParamValidator(value = {QueryParamValidator.class}, isQuery = true)
	public CommonResp queryDeviceDetail(@RequestBody CommonReq req) {
		return this.deviceQueryService.queryDeviceDetail(req);
	}
}
