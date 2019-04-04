package com.chisondo.server.modules.device.service.impl;
import com.chisondo.model.http.req.QryDeviceInfoHttpReq;
import com.chisondo.model.http.resp.DevSettingHttpResp;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.modules.device.dto.resp.*;
import com.chisondo.server.modules.device.entity.ActivedDeviceInfoEntity;
import com.chisondo.server.modules.tea.entity.AppChapuEntity;
import com.chisondo.server.modules.tea.service.AppChapuService;
import com.google.common.collect.ImmutableList;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.http.CommonResp;
import com.chisondo.server.common.utils.*;
import com.chisondo.server.modules.device.dto.req.DevStatusReportReq;
import com.chisondo.server.modules.device.entity.DeviceStateInfoEntity;
import com.chisondo.server.modules.device.service.ActivedDeviceInfoService;
import com.chisondo.server.modules.device.service.DeviceQueryService;
import com.chisondo.server.modules.device.service.DeviceStateInfoService;
import com.chisondo.server.modules.http2dev.service.DeviceHttpService;
import com.chisondo.server.modules.user.service.UserMakeTeaService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("deviceQueryService")
public class DeviceQueryServiceImpl implements DeviceQueryService {
	@Autowired
	private ActivedDeviceInfoService deviceInfoService;

	@Autowired
	private UserMakeTeaService userMakeTeaService;

	@Autowired
	private DeviceStateInfoService deviceStateInfoService;

	@Autowired
	private RedisUtils redisUtils;

	@Autowired
	private DeviceHttpService deviceHttpService;

	@Autowired
	private AppChapuService appChapuService;

	/**
	 * 查询设置内置参数信息
	 * @param req
	 * @return
	 */
	@Override
	public CommonResp queryDevSettingInfo(CommonReq req) {
		ActivedDeviceInfoEntity deviceInfo = (ActivedDeviceInfoEntity) req.getAttrByKey(Keys.DEVICE_INFO);

		DevSettingHttpResp httpResp = this.deviceHttpService.queryDevSettingInfo(new QryDeviceInfoHttpReq(req.getAttrByKey(Keys.DEVICE_ID).toString()));

		DevSettingRespDTO devSettingResp = this.buildDevSettingResp(deviceInfo, httpResp);
		return CommonResp.ok(devSettingResp);
	}

	private DevSettingRespDTO buildDevSettingResp(ActivedDeviceInfoEntity deviceInfo, DevSettingHttpResp httpResp) {
		DevSettingRespDTO devSettingResp = new DevSettingRespDTO();
		devSettingResp.setDeviceName(deviceInfo.getDeviceName());
		devSettingResp.setDevicePwd(deviceInfo.getPassword());
		devSettingResp.setIsOpenSound(ValidateUtils.equals(deviceInfo.getVolFlag(), Constant.DevVolumeCtrl.OPEN) ? Constant.DevVolumeFlag.YES : Constant.DevVolumeFlag.NO);
		devSettingResp.setWaterHeat(new WaterHeatInfo(httpResp.getWashteamsg().getTemperature(), httpResp.getWashteamsg().getSoak(), httpResp.getWashteamsg().getWaterlevel()));
		TeaSpectrumInfo chapuInfo = new TeaSpectrumInfo();
		chapuInfo.setIndex(httpResp.getChapumsg().getIndex());
		chapuInfo.setChapuId(httpResp.getChapumsg().getChapuid());
		chapuInfo.setChapuName(httpResp.getChapumsg().getChapuname());
		chapuInfo.setMakeTimes(httpResp.getChapumsg().getMaketimes());
		//  根据茶谱ID获取茶类信息
		AppChapuEntity teaSpectrum = this.appChapuService.queryTeaSpectrumById(chapuInfo.getChapuId());
		if (ValidateUtils.isNotEmpty(teaSpectrum)) {
			chapuInfo.setSortId(teaSpectrum.getSortId());
			chapuInfo.setSortName(teaSpectrum.getSortName());
			chapuInfo.setBrandName(teaSpectrum.getBrand());
			chapuInfo.setChapuImg(teaSpectrum.getImage());
		}
		devSettingResp.setChapuInfo(ImmutableList.of(chapuInfo));
		return devSettingResp;
	}

	@Override
	public CommonResp queryHisConnectDevOfUser(String userMobile) {
		List<DeviceInfoRespDTO> devInfoList = this.deviceInfoService.queryHisConnectDevOfUser(userMobile);
		DevQueryRespDTO devQueryResp = new DevQueryRespDTO(devInfoList);
		return CommonResp.ok(devQueryResp);
	}

	@Override
	public CommonResp queryMakeTeaRecordsOfDev(CommonReq req) {
		JSONObject jsonObj = JSONObject.parseObject(req.getBizBody());
		String deviceId = jsonObj.getString(Keys.DEVICE_ID);
		if (ValidateUtils.isEmptyString(deviceId)) {
			throw new CommonException("设备ID为空");
		}
		Map<String, Object> params = CommonUtils.getPageParams(jsonObj);
		params.put(Keys.DEVICE_ID, deviceId);
		params.put(Query.PAGE, ValidateUtils.isEmpty(jsonObj.get(Query.PAGE)) ? 1 : jsonObj.get(Query.PAGE));
		params.put(Query.LIMIT, ValidateUtils.isEmpty(jsonObj.get(Query.NUM)) ? 10 : jsonObj.get(Query.NUM));
		int count = this.userMakeTeaService.countMakeTeaRecordsByDeviceId(deviceId);
		List<MakeTeaRowRespDTO> rows = this.userMakeTeaService.queryMakeTeaRecordsByDeviceId(new Query(params));
		MakeTeaRespDTO makeTeaResp = new MakeTeaRespDTO(count, rows);
		return CommonResp.ok(makeTeaResp);
	}

	/**
	 * 查询用户的沏茶记录
	 * @param req
	 * @return
	 */
	@Override
	public CommonResp queryMakeTeaRecordsOfUser(CommonReq req) {
		JSONObject jsonObj = JSONObject.parseObject(req.getBizBody());
		String phoneNum = jsonObj.getString(Keys.PHONE_NUM);
		if (ValidateUtils.isEmptyString(phoneNum)) {
			throw new CommonException("用户手机号为空");
		}
		Map<String, Object> params = CommonUtils.getPageParams(jsonObj);
		params.put(Keys.USER_MOBILE, phoneNum);
		params.put(Query.PAGE, ValidateUtils.isEmpty(jsonObj.get(Query.PAGE)) ? 1 : jsonObj.get(Query.PAGE));
		params.put(Query.LIMIT, ValidateUtils.isEmpty(jsonObj.get(Query.NUM)) ? 10 : jsonObj.get(Query.NUM));
		int count = this.userMakeTeaService.countMakeTeaRecordsByUserMobile(phoneNum);
		List<MakeTeaRowRespDTO> rows = this.userMakeTeaService.queryMakeTeaRecordsByUserMobile(new Query(params));
		MakeTeaRespDTO makeTeaResp = new MakeTeaRespDTO(count, rows);
		return CommonResp.ok(makeTeaResp);
	}

	@Override
	public CommonResp queryDevStateInfo(CommonReq req) {
		String deviceId = (String) req.getAttrByKey(Keys.DEVICE_ID);
		// 首先从 redis 取
		String devStateInfoStr = this.redisUtils.get(deviceId);
		if (ValidateUtils.isNotEmptyString(devStateInfoStr)) {
			DevStatusReportReq devStatusReportReq = JSONObject.parseObject(devStateInfoStr, DevStatusReportReq.class);
			DeviceStateInfoEntity devStateInfo = this.deviceStateInfoService.queryObject(deviceId);
			DevStatusRespDTO devStatusResp = CommonUtils.convert2DevStatusInfo(devStatusReportReq, devStateInfo);
			devStatusResp.setOnlineStatus(Constant.OnlineState.YES);
			devStatusResp.setConnStatus(Constant.ConnectState.CONNECTED);
			return CommonResp.ok(devStatusResp);
		} else {
			// 从数据库中取
			DeviceStateInfoEntity devStateInfo = this.deviceStateInfoService.queryObject(deviceId);
			DevStatusRespDTO devStatusResp = CommonUtils.convert2DevStatusInfo(devStateInfo);
			return CommonResp.ok(devStatusResp);
		}
	}

	@Override
	public CommonResp queryDeviceDetail(CommonReq req) {
		String deviceId = (String) req.getAttrByKey(Keys.DEVICE_ID);
		String phoneNum = (String) req.getAttrByKey(Keys.PHONE_NUM);
		List<DeviceInfoRespDTO> devInfoList = this.deviceInfoService.queryDeviceDetail(ImmutableMap.of(Keys.DEVICE_ID, deviceId, Keys.USER_MOBILE, phoneNum));
		return CommonResp.ok(devInfoList);
	}
}
