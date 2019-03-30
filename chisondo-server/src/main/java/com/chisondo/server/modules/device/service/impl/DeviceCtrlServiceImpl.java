package com.chisondo.server.modules.device.service.impl;
import com.chisondo.model.http.resp.DevParamMsg;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.model.http.req.DeviceHttpReq;
import com.chisondo.model.http.resp.DeviceHttpResp;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.http.CommonResp;
import com.chisondo.server.common.utils.*;
import com.chisondo.server.modules.device.dto.req.*;
import com.chisondo.server.modules.device.dto.resp.DeviceBindRespDTO;
import com.chisondo.server.modules.device.entity.ActivedDeviceInfoEntity;
import com.chisondo.server.modules.device.service.ActivedDeviceInfoService;
import com.chisondo.server.modules.device.service.DeviceCtrlService;
import com.chisondo.server.modules.device.service.DeviceStateInfoService;
import com.chisondo.server.modules.http2dev.service.DeviceHttpService;
import com.chisondo.server.modules.user.entity.UserBookEntity;
import com.chisondo.server.modules.user.entity.UserMakeTeaEntity;
import com.chisondo.server.modules.user.entity.UserVipEntity;
import com.chisondo.server.modules.user.service.UserBookService;
import com.chisondo.server.modules.user.service.UserMakeTeaService;
import com.chisondo.server.modules.user.service.UserVipService;
import com.chisondo.server.modules.user.service.UserDeviceService;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
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

	@Override
	public CommonResp startOrReserveMakeTea(CommonReq req) {
		StartOrReserveMakeTeaReqDTO startOrReserveTeaReq = JSONObject.parseObject(req.getBizBody(), StartOrReserveMakeTeaReqDTO.class);
		UserVipEntity user = (UserVipEntity) req.getAttrByKey(Keys.USER_INFO);
		// 是否启动泡茶
		if (this.isStartTea(startOrReserveTeaReq.getStartTime())) {
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
				UserMakeTeaEntity userMakeTea = this.buildUserMakeTea(startOrReserveTeaReq, user, devHttpResp);
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
		devHttpReq.setDeviceId(startOrReserveTeaReq.getDeviceId());
		DevParamMsg msg = new DevParamMsg();
		msg.setTemperature(startOrReserveTeaReq.getTemperature());
		msg.setSoak(startOrReserveTeaReq.getSoak());
		msg.setWaterlevel(startOrReserveTeaReq.getWaterlevel());
		devHttpReq.setMsg(msg);
		return devHttpReq;
	}

	private UserBookEntity buildUserBook(StartOrReserveMakeTeaReqDTO startOrReserveTeaReq, UserVipEntity user) {
		UserBookEntity userBook = new UserBookEntity();
		userBook.setTeamanId(user.getMemberId().toString());
		userBook.setDeviceId(Integer.valueOf(startOrReserveTeaReq.getDeviceId()));
		userBook.setConfigCmd("AA0B012C0F89025A023CCC"); // TODO 需要确定这个值怎么取
		userBook.setProcessTime(new Date());
		userBook.setLogTime(new Date());
		userBook.setValidFlag(Constant.UserBookStatus.VALID);
		userBook.setChapuId(0);
		userBook.setInformFlag(0);
		userBook.setTeaSortId(startOrReserveTeaReq.getTeaSortId());
		userBook.setTeaSortName(startOrReserveTeaReq.getTeaSortName());
		userBook.setReservNo("MT" + DateUtils.currentDateStr(DateUtils.DATE_TIME_PATTERN2));
		return userBook;
	}

	private UserMakeTeaEntity buildUserMakeTea(StartOrReserveMakeTeaReqDTO startOrReserveTeaReq, UserVipEntity user, DeviceHttpResp devHttpResp) {
		UserMakeTeaEntity userMakeTea = new UserMakeTeaEntity();
		userMakeTea.setTeamanId(user.getMemberId().toString());
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

	private boolean isStartTea(String startTime) {
		return null == startTime || DateUtils.parseDate(startTime, DateUtils.DATE_TIME_PATTERN).getTime() <= DateUtils.currentDate().getTime();
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
	public void makeTeaByTeaSpectrum(MakeTeaByTeaSpectrumReqDTO makeTeaReq) {
		log.info("makeTeaByTeaSpectrum ok");
	}

	@Override
	public CommonResp washTea(CommonReq req) {
		WashTeaReqDTO washTeaReq = JSONObject.parseObject(req.getBizBody(), WashTeaReqDTO.class);
		// TODO 直接调用接口服务
		log.info("washTea ok");
		return CommonResp.ok();
	}

	@Override
	public CommonResp boilWater(BoilWaterReqDTO boilWaterReq) {
		// TODO 直接调用接口服务
		log.info("boilWater ok");
		return CommonResp.ok();
	}

	@Override
	public CommonResp stopWorking(StopWorkReqDTO stopWorkReq) {
		// TODO 直接调用接口服务
		/*老设备调用老流程中接口服务程序，如果是停止沏茶、烧水操作，调用接口4.3.3；停止洗茶调用接口4.3.2；取消使用茶谱沏茶调用接口4.3.5；在调用老接口前先调用4.3.7连接沏茶器接口，获取sessionid，调用完控制接口后再调用4.3.8断开和沏茶器连接；
⑥、新设备更新4.5.3用户泡茶表*/
		log.info("stopWorking ok");
		return CommonResp.ok();
	}

	@Override
	public CommonResp cancelTeaSpectrum(String devieId) {
		//取消使用茶谱沏茶调用接口服务4.1.2.6；
		log.info("cancelTeaSpectrum ok");
		return CommonResp.ok();
	}

	@Override
	public CommonResp keepWarmCtrl(DevCommonReqDTO devCommonReq) {
		/*终端控制指令包括：保温控制；
		②、保温控制调用接口服务4.1.2.7；
		③、根据设备ID判断新老设备，具体判断规则待定；
		④、新设备调用设备控制服务接口4.2.2.4；
		⑤、老设备调用老流程中接口服务程序，保温控制调用接口4.3.6；在调用老接口前先调用4.3.7连接沏茶器接口，获取sessionid，调用完控制接口后再调用4.3.8断开和沏茶器连接；
*/
		log.info("cancelTeaSpectrum ok");
		return CommonResp.ok();
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
		SetDevSoundReqDTO setDevSoundeq = JSONObject.parseObject(req.getBizBody(), SetDevSoundReqDTO.class);
		// TODO 调用设备接口服务
		this.deviceInfoService.updateDevSound(setDevSoundeq);
		return CommonResp.ok();
	}

	@Override
	public CommonResp setDefaultDevice(CommonReq req) {
		JSONObject jsonObj = JSONObject.parseObject(req.getBizBody());
		String deviceId = (String) req.getAttrByKey(Keys.DEVICE_ID);
		int operFlag = jsonObj.getIntValue(Keys.OPER_FLAG);
		this.userDeviceService.setDefaultDevice(ImmutableMap.of(Keys.DEVICE_ID, deviceId, Keys.OPER_FLAG, operFlag));
		return CommonResp.ok();
	}
}
