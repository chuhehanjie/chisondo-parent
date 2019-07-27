package com.chisondo.server.modules.device.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.model.http.req.QryDeviceInfoHttpReq;
import com.chisondo.model.http.resp.DevSettingHttpResp;
import com.chisondo.model.http.resp.DevStatusRespDTO;
import com.chisondo.model.http.resp.TeaSpectrumMsgResp;
import com.chisondo.server.common.annotation.DevConcurrentOperation;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.http.CommonResp;
import com.chisondo.server.common.utils.*;
import com.chisondo.server.modules.device.dto.resp.*;
import com.chisondo.server.modules.device.entity.ActivedDeviceInfoEntity;
import com.chisondo.server.modules.device.entity.DeviceStateInfoEntity;
import com.chisondo.server.modules.device.service.ActivedDeviceInfoService;
import com.chisondo.server.modules.device.service.DeviceQueryService;
import com.chisondo.server.modules.device.service.DeviceStateInfoService;
import com.chisondo.server.modules.http2dev.service.DeviceHttpService;
import com.chisondo.server.modules.tea.entity.AppChapuEntity;
import com.chisondo.server.modules.tea.service.AppChapuService;
import com.chisondo.server.modules.user.entity.UserVipEntity;
import com.chisondo.server.modules.user.service.UserMakeTeaService;
import com.chisondo.server.modules.user.service.UserVipService;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("deviceQueryService")
@Slf4j
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

	@Autowired
	private UserVipService userVipService;

	/**
	 * 查询设置内置参数信息
	 * @param req
	 * @return
	 */
	@Override
	@DevConcurrentOperation
	public CommonResp queryDevSettingInfo(CommonReq req) {
		ActivedDeviceInfoEntity deviceInfo = (ActivedDeviceInfoEntity) req.getAttrByKey(Keys.DEVICE_INFO);

		DevSettingHttpResp httpResp = this.deviceHttpService.queryDevSettingInfo(new QryDeviceInfoHttpReq(req.getAttrByKey(Keys.NEW_DEVICE_ID).toString()));
		if (!httpResp.isOK()) {
			return new CommonResp(httpResp.getRetn(), httpResp.getDesc());
		}
		DevSettingRespDTO devSettingResp = this.buildDevSettingResp(deviceInfo, httpResp);
		return CommonResp.ok(devSettingResp);
	}

	private DevSettingRespDTO buildDevSettingResp(ActivedDeviceInfoEntity deviceInfo, DevSettingHttpResp httpResp) {
		DevSettingRespDTO devSettingResp = new DevSettingRespDTO();
		devSettingResp.setDeviceName(deviceInfo.getDeviceName());
		devSettingResp.setDevicePwd(deviceInfo.getPassword());
		if (ValidateUtils.isNotEmpty(httpResp.getMsg())) {
			devSettingResp.setIsOpenSound(ValidateUtils.equals(httpResp.getMsg().getVolflag(), Constant.DevVolumeCtrl.OPEN) ? Constant.DevVolumeFlag.YES : Constant.DevVolumeFlag.NO);
			devSettingResp.setGmsflag(httpResp.getMsg().getGmsflag());
		}
		if (ValidateUtils.isNotEmpty(httpResp.getWashteamsg())) {
			devSettingResp.setWashTea(new DevSettingMsgDTO(httpResp.getWashteamsg().getTemperature(), httpResp.getWashteamsg().getSoak(), httpResp.getWashteamsg().getWaterlevel()));
		}
		if (ValidateUtils.isNotEmpty(httpResp.getBoilwatermsg())) {
			devSettingResp.setWaterHeat(new DevSettingMsgDTO(httpResp.getBoilwatermsg().getTemperature(), httpResp.getBoilwatermsg().getSoak(), httpResp.getBoilwatermsg().getWaterlevel()));
		}
		if (ValidateUtils.isNotEmptyCollection(httpResp.getChapumsg())) {
			List<TeaSpectrumDTO> chapuList = Lists.newArrayList();
			for (TeaSpectrumMsgResp teaSpectrumMsgResp : httpResp.getChapumsg()) {
				TeaSpectrumDTO chapuDTO = new TeaSpectrumDTO(teaSpectrumMsgResp.getChapuid());
				if (chapuList.contains(chapuDTO)) {
					continue;
				}
				AppChapuEntity teaSpectrum = this.appChapuService.queryTeaSpectrumById(teaSpectrumMsgResp.getChapuid());
				if (ValidateUtils.isEmpty(teaSpectrum)) {
					log.error("茶谱[id={}]不存在！", teaSpectrumMsgResp.getChapuid());
					continue;
				}
				chapuDTO.setIndex(teaSpectrumMsgResp.getIndex());
				chapuDTO.setChapuName(teaSpectrum.getName());
				chapuDTO.setMakeTimes(ValidateUtils.isEmpty(teaSpectrum.getMakeTimes()) ? 0 : teaSpectrum.getMakeTimes());
				chapuDTO.setSortId(ValidateUtils.isEmpty(teaSpectrum.getSortId()) ? 0 : teaSpectrum.getSortId());
				chapuDTO.setSortName(teaSpectrum.getSortName());
				chapuDTO.setBrandName(teaSpectrum.getBrand());
				chapuDTO.setChapuImg(CommonUtils.plusFullImgPath(teaSpectrum.getImage()));
				chapuList.add(chapuDTO);
			}
			devSettingResp.setChapuInfo(chapuList);
		}
		String v = CacheDataUtils.getConfigValueByKey("COMPANY_VERSION_" + HttpContextUtils.getHttpServletRequest().getHeader(Keys.REQSRC));
		if (ValidateUtils.isNotEmptyString(v)) {
			JSONObject jsonObject = JSONObject.parseObject(v);
			devSettingResp.setVersion(jsonObject.getString("version"));
			devSettingResp.setNewversion(jsonObject.getString("newversion"));
			devSettingResp.setCompanyCode(jsonObject.getString("companyCode"));
			devSettingResp.setStandyServer(jsonObject.getString("standyServer"));
		}
		return devSettingResp;
	}

	@Override
	public CommonResp queryHisConnectDevOfUser(String userMobile) {
		UserVipEntity user = this.userVipService.getUserByMobile(userMobile);
		if (ValidateUtils.isEmpty(user)) {
			return CommonResp.ok(new DevQueryRespDTO(0, Lists.newArrayList()));
		}
		List<DeviceInfoRespDTO> devInfoList = this.deviceInfoService.queryHisConnectDevOfUser(user.getMemberId());
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
		if (!CommonUtils.isOldDevice(deviceId)) {
			ActivedDeviceInfoEntity deviceInfo = this.deviceInfoService.getNewDeviceByNewDevId(deviceId);
			if (ValidateUtils.isEmpty(deviceInfo)) {
				return CommonResp.ok(new MakeTeaRespDTO(0, Lists.newArrayList()));
			}
			deviceId = deviceInfo.getDeviceId();
		}
		Map<String, Object> params = CommonUtils.getPageParams(jsonObj);
		params.put(Keys.DEVICE_ID, deviceId);
		params.put(Query.PAGE, ValidateUtils.isEmpty(jsonObj.get(Query.PAGE)) ? 1 : jsonObj.get(Query.PAGE));
		params.put(Query.LIMIT, ValidateUtils.isEmpty(jsonObj.get(Query.NUM)) ? 10 : jsonObj.get(Query.NUM));
		int count = this.userMakeTeaService.countMakeTeaRecordsByDeviceId(deviceId);
		List<MakeTeaRowRespDTO> rows = this.userMakeTeaService.queryMakeTeaRecordsByDeviceId(new Query(params));
		int size = rows.size();
		if (ValidateUtils.isNotEmptyCollection(rows)) {
			Map<Long, UserVipEntity> cacheUserMap = Maps.newHashMap();
			Map<Integer, AppChapuEntity> cacheChapuMap = Maps.newHashMap();
			rows.removeIf(makeTeaRow -> {
				UserVipEntity user = cacheUserMap.containsKey(makeTeaRow.getTeamanId()) ? cacheUserMap.get(makeTeaRow.getTeamanId()) : this.userVipService.queryObject(makeTeaRow.getTeamanId());
				if (ValidateUtils.isEmpty(user)) {
					return true;
				}
				if (!cacheUserMap.containsKey(user.getMemberId())) {
					cacheUserMap.put(user.getMemberId(), user);
				}
				makeTeaRow.processMakeTypeAndMakeMode();

				makeTeaRow.setPhoneNum(user.getPhone());
				makeTeaRow.setUserName(ValidateUtils.isEmptyString(user.getVipNickname()) ? user.getVipName() : user.getVipNickname());
				makeTeaRow.setUserImg(CommonUtils.plusFullImgPath(user.getVipHeadImg()));
				if (ValidateUtils.equals(Constant.MakeTeaType.TEA_SPECTRUM, makeTeaRow.getMakeType())) {
					// 茶谱沏茶时才返回茶谱相关信息
					AppChapuEntity teaSpectrum = cacheChapuMap.containsKey(makeTeaRow.getChapuId()) ? cacheChapuMap.get(makeTeaRow.getChapuId()) : this.appChapuService.queryTeaSpectrumById(makeTeaRow.getChapuId());
					if (ValidateUtils.isNotEmpty(teaSpectrum)) {
						if (!cacheChapuMap.containsKey(teaSpectrum.getChapuId())) {
							cacheChapuMap.put(teaSpectrum.getChapuId(), teaSpectrum);
						}
						makeTeaRow.setChapuName(teaSpectrum.getName());
						makeTeaRow.setChapuImage(CommonUtils.plusFullImgPath(teaSpectrum.getImage()));
						makeTeaRow.setTeaSortId(null);
						makeTeaRow.setTeaSortName(null);
						makeTeaRow.setTemperature(null);
						makeTeaRow.setWaterlevel(null);
					}
				} else {
					// 非茶谱沏茶
					makeTeaRow.setChapuId(null);
					makeTeaRow.setChapuIndex(null);
					makeTeaRow.setChapuName(null);
					makeTeaRow.setChapuImage(null);
				}
				return false;
			});
			if (size != rows.size()) {
				int diff = size - rows.size();
				count-=diff;
			}
		}
		MakeTeaRespDTO makeTeaResp = new MakeTeaRespDTO(count, rows);
		return CommonResp.ok(makeTeaResp);
	}

	@Override
	public CommonResp queryDevStateInfo(CommonReq req) {
		String deviceId = (String) req.getAttrByKey(Keys.DEVICE_ID);
		String newDeviceId = (String) req.getAttrByKey(Keys.NEW_DEVICE_ID);
		// 首先从 redis 取
		DevStatusRespDTO devStatusResp = this.redisUtils.get(newDeviceId, DevStatusRespDTO.class);
		if (ValidateUtils.isNotEmpty(devStatusResp)) {
			return CommonResp.ok(devStatusResp);
		} else {
			// 从数据库中取
			DeviceStateInfoEntity devStateInfo = this.deviceStateInfoService.queryObject(deviceId);
			if (ValidateUtils.isEmpty(devStateInfo)) {
				return CommonResp.error("设备状态信息不存在！");
			}
			devStatusResp = CommonUtils.convert2DevStatusDTO(devStateInfo);
			return CommonResp.ok(devStatusResp);
		}
	}

	@Override
	public CommonResp queryDeviceDetail(CommonReq req) {
		String deviceId = (String) req.getAttrByKey(Keys.DEVICE_ID);
		String phoneNum = (String) req.getAttrByKey(Keys.PHONE_NUM);
		UserVipEntity user = this.userVipService.getUserByMobile(phoneNum);
		List<DeviceInfoRespDTO> devInfoList = Lists.newArrayList();
		if (ValidateUtils.isNotEmpty(user)) {
			devInfoList = this.deviceInfoService.queryDeviceDetail(ImmutableMap.of(Keys.DEVICE_ID, deviceId, Keys.USER_ID, user.getMemberId(), "oldDevFlag", CommonUtils.isOldDevice(deviceId)));
		}
		return ValidateUtils.isNotEmptyCollection(devInfoList) ? CommonResp.ok(devInfoList.get(0)) : CommonResp.ok();
	}
}
