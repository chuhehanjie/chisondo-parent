package com.chisondo.server.modules.device.service.impl;
import com.chisondo.model.constant.DevConstant;
import com.chisondo.model.http.req.SetDevChapuParamHttpReq;
import com.chisondo.model.http.req.SetDevOtherParamHttpReq;
import com.chisondo.model.http.req.StopWorkHttpReq;
import com.chisondo.model.http.resp.DevParamMsg;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.model.http.req.DeviceHttpReq;
import com.chisondo.model.http.resp.DeviceHttpResp;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.http.CommonResp;
import com.chisondo.server.common.utils.*;
import com.chisondo.server.modules.device.dto.req.*;
import com.chisondo.server.modules.device.dto.resp.DeviceBindRespDTO;
import com.chisondo.server.modules.device.entity.ActivedDeviceInfoEntity;
import com.chisondo.server.modules.device.entity.DeviceOperateLogEntity;
import com.chisondo.server.modules.device.service.ActivedDeviceInfoService;
import com.chisondo.server.modules.device.service.DeviceCtrlService;
import com.chisondo.server.modules.device.service.DeviceOperateLogService;
import com.chisondo.server.modules.device.service.DeviceStateInfoService;
import com.chisondo.server.modules.http2dev.service.DeviceHttpService;
import com.chisondo.server.modules.tea.entity.AppChapuEntity;
import com.chisondo.server.modules.tea.entity.AppChapuParaEntity;
import com.chisondo.server.modules.tea.service.AppChapuParaService;
import com.chisondo.server.modules.user.entity.UserBookEntity;
import com.chisondo.server.modules.user.entity.UserMakeTeaEntity;
import com.chisondo.server.modules.user.entity.UserVipEntity;
import com.chisondo.server.modules.user.service.UserBookService;
import com.chisondo.server.modules.user.service.UserMakeTeaService;
import com.chisondo.server.modules.user.service.UserVipService;
import com.chisondo.server.modules.user.service.UserDeviceService;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 设备控制服务实现类
 * @author ding.zhong
 * @email 258321511@qq.com
 * @date Mar 12.19
 */
@Slf4j
@Service("deviceCtrlService")
public class DeviceCtrlServiceImpl implements DeviceCtrlService {

	@Autowired
	private UserVipService userVipService;

	@Autowired
	private ActivedDeviceInfoService deviceInfoService;

	@Autowired
	private UserDeviceService userDeviceService;

	@Autowired
	private DeviceStateInfoService deviceStateInfoService;

	@Autowired
	private UserMakeTeaService userMakeTeaService;

	@Autowired
	private UserBookService userBookService;

	@Autowired
	private DeviceHttpService deviceHttpService;

	@Autowired
	private AppChapuParaService appChapuParaService;

	@Autowired
	private DeviceOperateLogService devOperateLogService;

	@Override
	public CommonResp startOrReserveMakeTea(CommonReq req) {
		StartOrReserveMakeTeaReqDTO startOrReserveTeaReq = (StartOrReserveMakeTeaReqDTO) req.getAttrByKey(Keys.REQ);
		UserVipEntity user = (UserVipEntity) req.getAttrByKey(Keys.USER_INFO);
		boolean isReserveMakeTea = (boolean) req.getAttrByKey("isReserveMakeTea");
		// 是否预约泡茶
		if (isReserveMakeTea) {
			UserBookEntity userBook = this.buildUserBook(startOrReserveTeaReq, user);
			this.userBookService.save(userBook);
			return CommonResp.ok(ImmutableMap.of(Keys.RESERV_NO, userBook.getReservNo()));
		} else {
			// TODO 调用新设备接口服务 小程序http -->> 设备 http -->> 设备 tcp -->> 沏茶器
			/*新设备非预约泡茶直接入4.5.3用户泡茶表；预约泡茶入4.5.4用户预约泡茶表，后端由定时程序根据预约时间自动启动泡茶信息，修改4.5.4表记录状态，
			并将泡茶信息插入4.5.3用户泡茶表；*/
			DeviceHttpReq devHttpReq = this.buildDevHttpReq(startOrReserveTeaReq);

			DeviceHttpResp devHttpResp = this.deviceHttpService.makeTea(devHttpReq);
			if (devHttpResp.isOK()) {
				UserMakeTeaEntity userMakeTea = this.buildUserMakeTea(startOrReserveTeaReq, user.getMemberId().toString(), devHttpResp);
				this.userMakeTeaService.save(userMakeTea);
				return CommonResp.ok();
			} else {
				// 设备接口服务返回失败
				return CommonResp.error(devHttpResp.getRetn(), devHttpResp.getDesc());
			}
		}
	}

	private DeviceHttpReq buildDevHttpReq(StartOrReserveMakeTeaReqDTO startOrReserveTeaReq) {
		DeviceHttpReq devHttpReq = new DeviceHttpReq();
		devHttpReq.setDeviceID(startOrReserveTeaReq.getDeviceId());
		devHttpReq.setMsg(new DevParamMsg(startOrReserveTeaReq.getTemperature(), startOrReserveTeaReq.getSoak(), startOrReserveTeaReq.getWaterlevel()));
		return devHttpReq;
	}

	private DeviceHttpReq buildDevHttpReq(BoilWaterReqDTO boilWaterReq) {
		DeviceHttpReq devHttpReq = new DeviceHttpReq();
		devHttpReq.setDeviceID(boilWaterReq.getDeviceId());
		devHttpReq.setMsg(new DevParamMsg(boilWaterReq.getTemperature(), boilWaterReq.getSoak(), boilWaterReq.getWaterlevel()));
		return devHttpReq;
	}

	private UserBookEntity buildUserBook(StartOrReserveMakeTeaReqDTO startOrReserveTeaReq, UserVipEntity user) {
		UserBookEntity userBook = new UserBookEntity();
		userBook.setTeamanId(user.getMemberId().toString());
		userBook.setDeviceId(Integer.valueOf(startOrReserveTeaReq.getDeviceId()));
		userBook.setConfigCmd("AA0B012C0F89025A023CCC"); // TODO 需要确定这个值怎么取
		userBook.setProcessTime(DateUtils.parseDate(startOrReserveTeaReq.getStartTime(), DateUtils.DATE_TIME_PATTERN3));
		userBook.setLogTime(new Date());
		userBook.setValidFlag(Constant.UserBookStatus.VALID);
		userBook.setChapuId(0);
		userBook.setInformFlag(0);
		userBook.setTeaSortId(startOrReserveTeaReq.getTeaSortId());
		userBook.setTeaSortName(startOrReserveTeaReq.getTeaSortName());
		userBook.setReservNo(UUID.randomUUID().toString());
		userBook.setReserveParam(JSONObject.toJSONString(startOrReserveTeaReq));
		return userBook;
	}

	private UserMakeTeaEntity buildUserMakeTea(StartOrReserveMakeTeaReqDTO startOrReserveTeaReq, String userId, DeviceHttpResp devHttpResp) {
		UserMakeTeaEntity userMakeTea = new UserMakeTeaEntity();
		userMakeTea.setTeamanId(userId);
		userMakeTea.setDeviceId(Integer.valueOf(startOrReserveTeaReq.getDeviceId()));
		userMakeTea.setChapuId(0);
		userMakeTea.setMaxNum(0);
		userMakeTea.setMakeIndex(0);
		userMakeTea.setAddTime(new Date());
		userMakeTea.setStatus(devHttpResp.getMsg().getState()); // TODO 先使用接口返回的状态值
		userMakeTea.setTemperature(startOrReserveTeaReq.getTemperature());
		userMakeTea.setWarm(0);
		userMakeTea.setSoak(startOrReserveTeaReq.getSoak());
		userMakeTea.setTeaSortId(startOrReserveTeaReq.getTeaSortId());
		userMakeTea.setTeaSortName(startOrReserveTeaReq.getTeaSortName());
		userMakeTea.setMakeType(Constant.MakeTeaType.TEA_SPECTRUM);
		userMakeTea.setBarcode("");
		userMakeTea.setDensity(0);
		return userMakeTea;
	}

	@Override
	public DeviceBindRespDTO bindDevice(CommonReq req) {
		DeviceBindReqDTO devBindReq = (DeviceBindReqDTO) req.getAttrByKey("devBindReq");
		ActivedDeviceInfoEntity deviceInfo = (ActivedDeviceInfoEntity) req.getAttrByKey("devBindReq");
		Long userId = this.getUserId(devBindReq);

		// 保存用户与设备之间的关系
		this.userDeviceService.save(devBindReq, userId);

		// 更新设备状态信息
		this.deviceStateInfoService.saveOrUpdate(devBindReq);

		DeviceBindRespDTO devBindResp = new DeviceBindRespDTO();
		devBindResp.setDeviceId(deviceInfo.getDeviceId());
		devBindResp.setDeviceName(deviceInfo.getDeviceName());
		devBindResp.setCompanyId(deviceInfo.getCompanyId());
		devBindResp.setCompanyName(CommonUtils.getCompanyNameById(devBindReq.getCompanyId()));
		devBindResp.setDeviceDesc(deviceInfo.getDevDesc());
		return devBindResp;
	}

	private Long getUserId(DeviceBindReqDTO devBindReq) {
		Long userId = null;
		UserVipEntity user = this.userVipService.getUserByMobile(devBindReq.getPhoneNum());
		// 校验手机号是否存在
		if (ValidateUtils.isEmpty(user)) {
			// 手机号不存在则注册用户
			UserVipEntity userVip = new UserVipEntity();
			userVip.setPhone(devBindReq.getPhoneNum());
			userVip.setVipPwd(MD5Utils.encode(Constant.DEF_PWD, userVip.getPhone()));
			userVip.setRegSrcType(Constant.RegSrcType.WECHAT);
			userVip.setRegSrc(Constant.RegSrc.CHISONDO);
			userVip.setUseTag(0); // 可用标识 0：可用 1：不可用
			userVip.setIsTalent(0);
			this.userVipService.save(userVip);
			userId = userVip.getMemberId();
		} else {
			userId = user.getMemberId();
		}
		return userId;
	}


	@Override
	public CommonResp washTea(CommonReq req) {
		WashTeaReqDTO washTeaReq = JSONObject.parseObject(req.getBizBody(), WashTeaReqDTO.class);
		DeviceHttpReq devHttpReq = this.buildWashTeaHttpReq(washTeaReq);
		DeviceHttpResp devHttpResp = this.deviceHttpService.washTeaCtrl(devHttpReq);
		log.info("调用洗茶 HTTP 服务响应：{}", devHttpResp);
		return new CommonResp(devHttpResp.getRetn(), devHttpResp.getDesc());
	}

	@Override
	public CommonResp boilWater(CommonReq req) {
		BoilWaterReqDTO boilWaterReq = JSONObject.parseObject(req.getBizBody(), BoilWaterReqDTO.class);
		DeviceHttpReq devHttpReq = this.buildDevHttpReq(boilWaterReq);
		DeviceHttpResp devHttpResp = this.deviceHttpService.boilWaterCtrl(devHttpReq);
		log.info("调用烧水 HTTP 服务响应：{}", devHttpResp);
		return new CommonResp(devHttpResp.getRetn(), devHttpResp.getDesc());
	}

	@Override
	public CommonResp stopWorking(CommonReq req) {
		StopWorkReqDTO stopWorkReq = JSONObject.parseObject(req.getBizBody(), StopWorkReqDTO.class);
		/*老设备调用老流程中接口服务程序，如果是停止沏茶、烧水操作，调用接口4.3.3；停止洗茶调用接口4.3.2；取消使用茶谱沏茶调用接口4.3.5；在调用老接口前先调用4.3.7连接沏茶器接口，获取sessionid，调用完控制接口后再调用4.3.8断开和沏茶器连接；
⑥、新设备更新4.5.3用户泡茶表*/
		StopWorkHttpReq devHttpReq = this.buildStopWorkReq(stopWorkReq);
		DeviceHttpResp devHttpResp = this.deviceHttpService.stopWork(devHttpReq);
		// 更新用户泡茶表状态
		this.userMakeTeaService.updateStatus(stopWorkReq.getDeviceId(), Constant.UserMakeTeaStatus.CANCELED);
		log.info("调用停止沏茶/洗茶/烧水 HTTP 服务响应：{}", devHttpResp);
		return new CommonResp(devHttpResp.getRetn(), devHttpResp.getDesc());
	}

	@Override
	public CommonResp makeTeaByTeaSpectrum(CommonReq req) {
		UseTeaSpectrumReqDTO useTeaSpectrumReq = JSONObject.parseObject(req.getBizBody(), UseTeaSpectrumReqDTO.class);
		log.info("makeTeaByTeaSpectrum ok");
		UserMakeTeaEntity useMakeTea = (UserMakeTeaEntity) req.getAttrByKey(Keys.USER_MAKTE_TEA_INFO);
		useMakeTea.setStatus(Constant.UserMakeTeaStatus.COMPLETED); // TODO 状态值待确认
		useMakeTea.setMakeIndex(null == useTeaSpectrumReq.getIndex() ? 0 : useTeaSpectrumReq.getIndex());
		useMakeTea.setLastTime(new Date());
		this.userMakeTeaService.update(useMakeTea);
		// TODO 待确认是否调用新设备接口服务 +
//		this.deviceHttpService.stopWork()
		return CommonResp.ok();
	}

	@Override
	public CommonResp cancelTeaSpectrum(CommonReq req) {
		/*StopWorkHttpReq devHttpReq = new StopWorkHttpReq(DevConstant.StopWorkActionFlag.STOP_MAKE_TEA, deviceId);
		DeviceHttpResp devHttpResp = this.deviceHttpService.stopWork(devHttpReq);
		log.info("调用取消使用茶谱沏茶 HTTP 服务响应：{}", devHttpResp);*/
		// 更新用户泡茶表状态
		UserMakeTeaEntity useMakeTea = (UserMakeTeaEntity) req.getAttrByKey(Keys.USER_MAKTE_TEA_INFO);
		useMakeTea.setStatus(Constant.UserMakeTeaStatus.CANCELED);
		useMakeTea.setCancelTime(new Date());
		this.userMakeTeaService.update(useMakeTea);
		return CommonResp.ok();
	}

	@Override
	public CommonResp keepWarmCtrl(CommonReq req) {
		/*
		  ①、终端控制指令包括：保温控制；
		  ②、保温控制调用接口服务4.1.2.7；
		  ③、根据设备ID判断新老设备，具体判断规则待定；
		  ④、新设备调用设备控制服务接口4.2.2.4；
		  ⑤、老设备调用老流程中接口服务程序，保温控制调用接口4.3.6；在调用老接口前先调用4.3.7连接沏茶器接口，获取sessionid，调用完控制接口后再调用4.3.8断开和沏茶器连接；
		  ⑥、新设备更新4.5.3用户泡茶表；（不需要更新）
		  ⑦、操作信息统一入4.5.6沏茶器操作日志表，该日志表保存近10天的操作日志记录。
		*/
		String deviceId = (String) req.getAttrByKey(Keys.DEVICE_ID);
		DeviceHttpReq devHttpReq = new DeviceHttpReq(deviceId);
		DeviceHttpResp devHttpResp = this.deviceHttpService.startKeeWarm(devHttpReq);
		log.info("调用保温控制 HTTP 服务响应：{}", devHttpResp);
		return new CommonResp(devHttpResp.getRetn(), devHttpResp.getDesc());
	}

	@Override
	public void delDevConnectRecord(CommonReq req) {
		UserVipEntity user = (UserVipEntity) req.getAttrByKey(Keys.USER_INFO);
		ActivedDeviceInfoEntity devInfo = (ActivedDeviceInfoEntity) req.getAttrByKey(Keys.DEVICE_INFO);
		this.userDeviceService.delUserDeviceByParams(ImmutableMap.of(Keys.TEAMAN_ID, user.getMemberId(), Keys.DEVICE_ID, devInfo.getDeviceId()));
	}

	@Override
	public CommonResp setDevicePassword(CommonReq req) {
		SetDevPwdReqDTO setDevPwdReq = (SetDevPwdReqDTO) req.getAttrByKey("setDevPwdReq");
		this.deviceInfoService.updateDevPwd(setDevPwdReq);
		return CommonResp.ok();
	}

	@Override
	public CommonResp setDeviceNameOrDesc(CommonReq req) {
		SetDevNameReqDTO setDevNameReq = (SetDevNameReqDTO) req.getAttrByKey("setDevNameReq");
		this.deviceInfoService.updateDevNameOrDesc(setDevNameReq);
		return CommonResp.ok();
	}

	@Override
	public CommonResp setDeviceSound(CommonReq req) {
		SetDevSoundReqDTO setDevSoundReq = JSONObject.parseObject(req.getBizBody(), SetDevSoundReqDTO.class);
		SetDevOtherParamHttpReq devHttpReq = new SetDevOtherParamHttpReq();
		devHttpReq.setDeviceID(setDevSoundReq.getDeviceId());
		devHttpReq.setVolflag(setDevSoundReq.getOperFlag() == 0 ? Constant.DevVolumeCtrl.OPEN : Constant.DevVolumeCtrl.CLOSE);
		devHttpReq.setGmsflag(setDevSoundReq.getGmsflag());
		devHttpReq.setMsg(new DevParamMsg()); // TODO 待确认 设置声音和网络不应该传MSG
		DeviceHttpResp devHttpResp = this.deviceHttpService.setDevSoundOrNetwork(devHttpReq);
		log.info("调用设置设备声音或网络 HTTP 服务响应：{}", devHttpResp);
		this.deviceInfoService.updateDevSound(setDevSoundReq);
		return new CommonResp(devHttpResp.getRetn(), devHttpResp.getDesc());
	}

	@Override
	public CommonResp setDefaultDevice(CommonReq req) {
		JSONObject jsonObj = JSONObject.parseObject(req.getBizBody());
		String deviceId = (String) req.getAttrByKey(Keys.DEVICE_ID);
		int operFlag = jsonObj.getIntValue(Keys.OPER_FLAG);
		this.userDeviceService.setDefaultDevice(ImmutableMap.of(Keys.DEVICE_ID, deviceId, Keys.OPER_FLAG, operFlag));
		return CommonResp.ok();
	}

	/**
	 * 更换液晶屏茶谱
	 * @param req
	 * @return
	 */
    @Override
    public CommonResp changeDevTeaSpectrum(CommonReq req) {
	    ChgDevTeaSpectrumReqDTO chgDevTeaSpectrumReq = JSONObject.parseObject(req.getBizBody(), ChgDevTeaSpectrumReqDTO.class);
        AppChapuEntity teaSpectrum = (AppChapuEntity) req.getAttrByKey(Keys.TEA_SPECTRUM_INFO);
		AppChapuParaEntity teaSpectrumParam = this.appChapuParaService.queryObject(teaSpectrum.getChapuId());
		if (ValidateUtils.isEmpty(teaSpectrumParam)) {
			throw new CommonException("茶谱参数未设置");
		}
		SetDevChapuParamHttpReq devHttpReq = new SetDevChapuParamHttpReq();
		devHttpReq.setDeviceID(chgDevTeaSpectrumReq.getDeviceId());
		devHttpReq.setIndex(chgDevTeaSpectrumReq.getIndex());
		devHttpReq.setMaketimes(teaSpectrum.getMakeTimes());
		DevParamMsg teaparam = new DevParamMsg(teaSpectrumParam.getTemp(), teaSpectrumParam.getDura(), 200); // TODO 待确认 出水量怎么传
		devHttpReq.setTeaparm(teaparam);
		devHttpReq.setChapuid(teaSpectrum.getChapuId());
		devHttpReq.setChapuname(teaSpectrum.getName());
		DeviceHttpResp devHttpResp = this.deviceHttpService.setDevChapuParam(devHttpReq);
		log.info("调用更换液晶屏茶谱 HTTP 服务响应：{}", devHttpResp);
		return new CommonResp(devHttpResp.getRetn(), devHttpResp.getDesc());
    }

	@Override
	public CommonResp cancelReservation(CommonReq req) {
    	UserBookEntity userBook = (UserBookEntity) req.getAttrByKey(Keys.USER_BOOK_INFO);
    	// 设置状态为取消
    	userBook.setValidFlag(Constant.UserBookStatus.CANCELED);
    	this.userBookService.update(userBook);
		return CommonResp.ok();
	}

	private DeviceHttpReq buildWashTeaHttpReq(WashTeaReqDTO washTeaReq) {
		DeviceHttpReq devHttpReq = new DeviceHttpReq();
		devHttpReq.setDeviceID(washTeaReq.getDeviceId());
		devHttpReq.setMsg(new DevParamMsg(washTeaReq.getTemperature(), washTeaReq.getSoak(), washTeaReq.getWaterlevel()));
		return devHttpReq;
	}

	private StopWorkHttpReq buildStopWorkReq(StopWorkReqDTO stopWorkReq) {
		StopWorkHttpReq req = new StopWorkHttpReq();
		req.setActionflag(this.convertActionFlag(stopWorkReq.getOperFlag()));
		req.setDeviceID(stopWorkReq.getDeviceId());
		return req;
	}

	private Integer convertActionFlag(int operFlag) {
		if (operFlag == Constant.StopWorkOperFlag.STOP_MAKE_TEA) {
			return DevConstant.StopWorkActionFlag.STOP_MAKE_TEA;
		} else if (operFlag == Constant.StopWorkOperFlag.STOP_BOIL_WATER) {
			return DevConstant.StopWorkActionFlag.STOP_BOIL_WATER;
		} else if (operFlag == Constant.StopWorkOperFlag.STOP_WASH_TEA) {
			return DevConstant.StopWorkActionFlag.STOP_WASH_TEA;
		} else {
			return DevConstant.StopWorkActionFlag.STOP_WARM;
		}
	}

	/**
	 * 处理预约沏茶
	 * @param userBookList
	 */
	@Override
	public void processReverseMakeTea(List<UserBookEntity> userBookList) {
		for (UserBookEntity userBook : userBookList) {
			StartOrReserveMakeTeaReqDTO startOrReserveTeaReq = JSONObject.parseObject(userBook.getReserveParam(), StartOrReserveMakeTeaReqDTO.class);
			DeviceHttpReq devHttpReq = this.buildDevHttpReq(startOrReserveTeaReq);
			DeviceHttpResp devHttpResp = this.deviceHttpService.makeTea(devHttpReq);
			long startTime = System.currentTimeMillis();
			if (devHttpResp.isOK()) {
				UserMakeTeaEntity userMakeTea = this.buildUserMakeTea(startOrReserveTeaReq, userBook.getTeamanId(), devHttpResp);
				this.userMakeTeaService.save(userMakeTea);
			} else {
				log.error("发送沏茶请求到设备失败，原因：{}", devHttpResp.getDesc());
			}
			long endTime = System.currentTimeMillis();
			DeviceOperateLogEntity devOperateLog = new DeviceOperateLogEntity();
			devOperateLog.setDeviceId(userBook.getDeviceId().toString());
			devOperateLog.setTeamanId(userBook.getTeamanId());
			devOperateLog.setUserMobileNo(startOrReserveTeaReq.getPhoneNum());
			devOperateLog.setOperType(0);
			devOperateLog.setReqContent(JSONObject.toJSONString(devHttpReq));
			devOperateLog.setResContent(JSONObject.toJSONString(devHttpResp));
			devOperateLog.setStartTime(new Date(startTime));
			devOperateLog.setEndTime(new Date(endTime));
			devOperateLog.setOperResult(devHttpResp.isOK() ? Constant.RespResult.SUCCESS : Constant.RespResult.FAILED);
			devOperateLog.setDesc("处理用户预约沏茶");
		}
	}
}
