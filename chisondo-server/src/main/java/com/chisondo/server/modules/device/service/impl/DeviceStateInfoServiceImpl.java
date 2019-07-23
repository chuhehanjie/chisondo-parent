package com.chisondo.server.modules.device.service.impl;

import com.chisondo.model.constant.DeviceConstant;
import com.chisondo.model.http.resp.DevStatusReportResp;
import com.chisondo.model.http.resp.DevStatusRespDTO;
import com.chisondo.server.common.utils.*;
import com.chisondo.server.modules.device.dao.DeviceStateInfoDao;
import com.chisondo.server.modules.device.dto.req.DeviceBindReqDTO;
import com.chisondo.server.modules.device.entity.ActivedDeviceInfoEntity;
import com.chisondo.server.modules.device.entity.DeviceStateInfoEntity;
import com.chisondo.server.modules.device.service.ActivedDeviceInfoService;
import com.chisondo.server.modules.device.service.DeviceStateInfoService;
import com.chisondo.server.modules.device.utils.DevWorkRemainTimeUtils;
import com.chisondo.server.modules.tea.entity.AppChapuEntity;
import com.chisondo.server.modules.tea.service.AppChapuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("deviceStateInfoService")
@Slf4j
public class DeviceStateInfoServiceImpl implements DeviceStateInfoService {
	@Autowired
	private DeviceStateInfoDao deviceStateInfoDao;

	@Autowired
	private RedisUtils redisUtils;

	@Autowired
	private ActivedDeviceInfoService deviceInfoService;

	@Autowired
	private AppChapuService chapuService;
	
	@Override
	public DeviceStateInfoEntity queryObject(String deviceId){
		return deviceStateInfoDao.queryObject(deviceId);
	}
	
	@Override
	public List<DeviceStateInfoEntity> queryList(Map<String, Object> map){
		return deviceStateInfoDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return deviceStateInfoDao.queryTotal(map);
	}
	
	@Override
	public void save(DeviceStateInfoEntity deviceStateInfo){
		deviceStateInfoDao.save(deviceStateInfo);
	}
	
	@Override
	public void update(DeviceStateInfoEntity deviceStateInfo){
		deviceStateInfoDao.update(deviceStateInfo);
	}
	
	@Override
	public void delete(Integer deviceId){
		deviceStateInfoDao.delete(deviceId);
	}
	
	@Override
	public void deleteBatch(Integer[] deviceIds){
		deviceStateInfoDao.deleteBatch(deviceIds);
	}

	@Override
	public void saveOrUpdate(DeviceBindReqDTO devBindReq) {
		DeviceStateInfoEntity devStateInfo = this.queryObject(devBindReq.getDeviceId());
		if (ValidateUtils.isEmpty(devStateInfo)) {
			devStateInfo = new DeviceStateInfoEntity();
			this.setDevStateAttrs(devBindReq, devStateInfo);
			this.save(devStateInfo);
		} else {
			this.setDevStateAttrs(devBindReq, devStateInfo);
			this.update(devStateInfo);
		}
	}

	private void setDevStateAttrs(DeviceBindReqDTO devBindReq, DeviceStateInfoEntity devStateInfo) {
		devStateInfo.setDeviceId(devBindReq.getDeviceId());
		devStateInfo.setUpdateTime(DateUtils.currentDate());
		if (ValidateUtils.isEmptyString(devStateInfo.getDeviceStateInfo())) {
			devStateInfo.setDeviceStateInfo("DEV" + devBindReq.getDeviceId());
		}
		if (ValidateUtils.isEmpty(devStateInfo.getOnlineState())) {
			devStateInfo.setOnlineState(Constant.OnlineState.NO);
		}
		if (ValidateUtils.isEmpty(devStateInfo.getConnectState())) {
			devStateInfo.setConnectState(Constant.ConnectState.NOT_CONNECTED);
		}
		if (ValidateUtils.isEmptyString(devStateInfo.getClientIpAddress())) {
			devStateInfo.setClientIpAddress(HttpContextUtils.getHttpServletRequest().getRemoteAddr());
		}
		if (ValidateUtils.isNotEmptyString(devBindReq.getLongitude())) {
			devStateInfo.setLongitude(Double.valueOf(devBindReq.getLongitude()));
		}
		if (ValidateUtils.isNotEmptyString(devBindReq.getLatitude())) {
			devStateInfo.setLatitude(Double.valueOf(devBindReq.getLatitude()));
		}
		if (ValidateUtils.isNotEmptyString(devBindReq.getProvince())) {
			devStateInfo.setProvince(devBindReq.getProvince());
		}
		if (ValidateUtils.isNotEmptyString(devBindReq.getCity())) {
			devStateInfo.setCity(devBindReq.getCity());
		}
		if (ValidateUtils.isNotEmptyString(devBindReq.getDistrict())) {
			devStateInfo.setDistrict(devBindReq.getDistrict());
		}
		if (ValidateUtils.isNotEmptyString(devBindReq.getDetaddress())) {
			devStateInfo.setAddress(devBindReq.getDetaddress());
		}
	}

	@Override
	public void updateDevStatus(DevStatusReportResp devStatusReportResp) {
		//  根据设备ID查询设备状态信息是否存在
		DeviceStateInfoEntity existedDevState = this.queryObject(devStatusReportResp.getDbDeviceId());
		DevStatusRespDTO devStatusResp = this.redisUtils.get(devStatusReportResp.getDeviceID(), DevStatusRespDTO.class);
		//log.info("从 redis 中取设备[{}]状态信息 = {}", devStatusResp.getDeviceId(), JSONObject.toJSONString(devStatusResp));
		DeviceStateInfoEntity devStateInfo = this.buildDevStateInfo(devStatusResp, existedDevState, devStatusReportResp);
		if (ValidateUtils.isEmpty(existedDevState)) {
			devStateInfo.setDeviceStateInfo(devStateInfo.getDeviceId() + "STATE");
			devStateInfo.setClientIpAddress(devStatusReportResp.getClientIP());
			if (ValidateUtils.isEmpty(devStateInfo.getConnectState())) {
				devStateInfo.setConnectState(Constant.ConnectState.NOT_CONNECTED);
			}
			// 不存在则添加
			this.save(devStateInfo);
			log.info("add device state success");
		} else {
			this.update(devStateInfo);
			log.info("updateDevStatus success");
		}
		// 把设备状态信息保存到 redis 中
		this.save2Redis(devStatusResp, devStateInfo);
	}

	/**
	 * 处理设备工作剩余时间
	 *//*
	private void processDevWorkRemainTime(final DevStatusRespDTO devStatusResp, final DeviceStateInfoEntity devStateInfo) {
		ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(2,
				new BasicThreadFactory.Builder().namingPattern("scheduled-pool2-%d").daemon(true).build());
		scheduledExecutorService.execute(() -> {
			log.info("开始倒计时处理，设备ID = {}, remain = {}", devStatusResp.getDeviceId(), devStatusResp.getReamin());
			int remainTime = devStatusResp.getReamin() - 1;
			try {
				for (int i = remainTime; i >= 0; i--) {
					DevStatusRespDTO tempDevStatusResp = this.redisUtils.get(devStatusResp.getDeviceId(), DevStatusRespDTO.class);
					if (ValidateUtils.isNotEmpty(tempDevStatusResp.getReamin())) {
						if (ValidateUtils.equals(0, tempDevStatusResp.getReamin())) {
							break;
						}
						if (tempDevStatusResp.getReamin() != remainTime) {
							remainTime = tempDevStatusResp.getReamin();
							i = remainTime;
							continue;
						}
					}
					Thread.sleep(990);
				}
			} catch (InterruptedException e) {
				log.error("更新设备工作剩余时间失败！", e);
			}
			DevStatusRespDTO tempDevStatusResp = this.redisUtils.get(devStatusResp.getDeviceId(), DevStatusRespDTO.class);
			tempDevStatusResp.setCountdownFlag(false);
			tempDevStatusResp.setReamin(0);
			this.redisUtils.set(devStatusResp.getDeviceId(), tempDevStatusResp);
		});
	}*/

	private DeviceStateInfoEntity buildDevStateInfo(DevStatusRespDTO devStatusRespDTO, DeviceStateInfoEntity existedDevState, DevStatusReportResp devStatusReportResp) {
		DeviceStateInfoEntity devStateInfo = ValidateUtils.isNotEmpty(existedDevState) ? existedDevState : new DeviceStateInfoEntity();
		devStateInfo.setOnlineState(Constant.OnlineState.YES);
//		devStateInfo.setConnectState(Constant.ConnectState.CONNECTED);
		devStateInfo.setLastConnTime(devStateInfo.getUpdateTime());
		devStateInfo.setUpdateTime(DateUtils.currentDate());
		devStateInfo.setDeviceId(devStatusReportResp.getDbDeviceId());
		devStateInfo.setNewDeviceId(devStatusRespDTO.getDeviceId());
		this.processMakeTeaByChapu(devStatusRespDTO, devStateInfo);
		if (ValidateUtils.isNotEmpty(devStatusRespDTO.getIndex())) {
			devStateInfo.setIndex(devStatusRespDTO.getIndex());
		}
//		devStateInfo.setDeviceStateInfo("");
		devStateInfo.setLastValTime(devStatusReportResp.getTcpValTime());
		devStateInfo.setMakeTemp(devStatusRespDTO.getTemp());
		devStateInfo.setTemp(devStatusRespDTO.getTemp());
		devStateInfo.setWarm(devStatusRespDTO.getWarm());
		devStateInfo.setDensity(devStatusRespDTO.getDensity());
		devStateInfo.setWaterlv(devStatusRespDTO.getWaterlv());
		devStateInfo.setMakeDura(devStatusRespDTO.getMakeDura());
		devStateInfo.setReamin(devStatusRespDTO.getReamin());
		devStateInfo.setTea(devStatusRespDTO.getTea());
		devStateInfo.setWater(devStatusRespDTO.getWater());
		return devStateInfo;
	}

	public static void main(String[] args) {
		DeviceStateInfoEntity devStateInfo = new DeviceStateInfoEntity();
		System.out.println(ValidateUtils.equals(devStateInfo.getMakeTeaByChapuFlag(), Constant.MakeTeaType.TEA_SPECTRUM));
	}

	private void processMakeTeaByChapu(DevStatusRespDTO devStatusRespDTO, DeviceStateInfoEntity devStateInfo) {
		if (ValidateUtils.equals(devStateInfo.getMakeTeaByChapuFlag(), Constant.MakeTeaType.TEA_SPECTRUM)) {
			log.info("设备还在茶谱沏茶中");
			if (ValidateUtils.equals(DeviceConstant.WorkStatus.IDLE, devStatusRespDTO.getWork())) {
				// 茶谱沏茶中，但上报的状态为空闲，则还是要设置为茶谱沏茶
				devStateInfo.setMakeType(DeviceConstant.MakeType.TEA_SPECTRUM);
				devStatusRespDTO.setMakeType(DeviceConstant.MakeType.TEA_SPECTRUM);
				devStateInfo.setMakeTeaByChapuFlag(Constant.MakeTeaType.TEA_SPECTRUM);
			}
		}
		if (ValidateUtils.equals(DeviceConstant.WorkStatus.MAKING_TEA, devStatusRespDTO.getWork())) {
			// 3，当设备上报状态workstatus​为1（沏茶）时，若同时上报了茶谱ID，则显示上报的这个茶谱ID的茶谱沏茶。
			if (ValidateUtils.isNotEmpty(devStatusRespDTO.getChapuId()) && ValidateUtils.notEquals(devStateInfo.getChapuId(), devStatusRespDTO.getChapuId())) {
				log.info("更换茶谱，旧茶谱ID = {}, 新茶谱ID = {}", devStateInfo.getChapuId(), devStatusRespDTO.getChapuId());
				AppChapuEntity teaSpectrum = this.chapuService.queryObject(devStatusRespDTO.getChapuId());
				if (ValidateUtils.isEmpty(teaSpectrum)) {
					log.error("茶谱[{}]不存在！", devStatusRespDTO.getChapuId());
				} else {
					devStateInfo.setChapuId(teaSpectrum.getChapuId());
					devStateInfo.setChapuName(teaSpectrum.getName());
					devStateInfo.setChapuImage(CommonUtils.plusFullImgPath(teaSpectrum.getImage()));
					devStateInfo.setChapuMakeTimes(teaSpectrum.getMakeTimes());
					devStateInfo.setIndex(devStatusRespDTO.getIndex());
					devStatusRespDTO.setChapuName(devStateInfo.getChapuName());
					devStatusRespDTO.setChapuImage(devStateInfo.getChapuImage());
					devStatusRespDTO.setChapuMakeTimes(devStateInfo.getChapuMakeTimes());
				}
			} else if (ValidateUtils.equals(0, devStatusRespDTO.getChapuId())) {
				log.info("上报的茶谱ID为0，需要结束茶谱");
				// 4，​当设备上报状态workstatus​为1（沏茶）时，若同时上报的茶谱ID为0，则结束茶谱沏茶，返回普通沏茶状态。
				CommonUtils.set2NormalMakeTea(devStatusRespDTO, devStateInfo);
			}
		} else if (ValidateUtils.equals(DeviceConstant.WorkStatus.BOILING_WATER, devStatusRespDTO.getWork())) {
			log.info("烧水操作，需要结束茶谱");
			// 2，当设备上报状态workstatus​为3（烧水）时，结束茶谱沏茶，返回普通沏茶。
			CommonUtils.set2NormalMakeTea(devStatusRespDTO, devStateInfo);
		}
		// 设备启动按键 需要设置 makeType 为茶谱沏茶
		if (ValidateUtils.equals(DeviceConstant.DevReportActionFlag.ENABLE_BUTTON, devStatusRespDTO.getActionFlag())) {
			devStateInfo.setMakeType(DeviceConstant.MakeType.TEA_SPECTRUM);
			devStatusRespDTO.setMakeType(DeviceConstant.MakeType.TEA_SPECTRUM);
		}
		devStateInfo.setWork(devStatusRespDTO.getWork());
	}

	@Override
	public void setDevChapu2Finish(Map<String, Object> params) {
		this.deviceStateInfoDao.setDevChapu2Finish(params);
	}

	@Override
	@Deprecated
	public void updateDevStateFromRedis(String newDevId) {
		ActivedDeviceInfoEntity deviceInfo = this.deviceInfoService.getNewDeviceByNewDevId(newDevId);
		if (ValidateUtils.isEmpty(deviceInfo)) {
			log.error("新设备[{}]信息不存在！", newDevId);
			return;
		}
		DevStatusRespDTO devStatusRespDTO = this.redisUtils.get(newDevId, DevStatusRespDTO.class);
		DeviceStateInfoEntity deviceState = new DeviceStateInfoEntity();
		deviceState.setDeviceId(deviceInfo.getDeviceId());
		deviceState.setUpdateTime(DateUtils.currentDate());
		deviceState.setLastConnTime(DateUtils.currentDate());
		deviceState.setMakeTemp(devStatusRespDTO.getMakeTemp());
		deviceState.setTemp(devStatusRespDTO.getTemp());
		deviceState.setWarm(devStatusRespDTO.getWarm());
		deviceState.setDensity(devStatusRespDTO.getDensity());
		deviceState.setWaterlv(devStatusRespDTO.getWaterlv());
		deviceState.setMakeDura(devStatusRespDTO.getMakeDura());
		deviceState.setReamin(devStatusRespDTO.getReamin());
		deviceState.setTea(devStatusRespDTO.getTea());
		deviceState.setWater(devStatusRespDTO.getWater());
		deviceState.setWork(devStatusRespDTO.getWork());
		if (DevWorkRemainTimeUtils.hasWorkRemainTime(devStatusRespDTO)) {
			DevWorkRemainTimeUtils.asyncProcessWorkRemainTime(devStatusRespDTO, deviceState);
		} else {
			this.update(deviceState);
		}
		log.error("更新设备[{}]状态信息成功！新设备ID = {}", deviceInfo.getDeviceId(), deviceInfo.getNewDeviceId());
	}

	@Override
	public void save2(DevStatusReportResp devStatusReportResp, DeviceStateInfoEntity devStateInfo) {
		this.save(devStateInfo);
		DevStatusRespDTO devStatusResp = this.redisUtils.get(devStatusReportResp.getDeviceID(), DevStatusRespDTO.class);
		this.save2Redis(devStatusResp, devStateInfo);
	}

	private void save2Redis(DevStatusRespDTO devStatusRespDTO, DeviceStateInfoEntity devStateInfo) {
		devStatusRespDTO.setOnlineStatus(Constant.OnlineState.YES);
		devStatusRespDTO.setTeaSortId(devStateInfo.getTeaSortId());
		devStatusRespDTO.setTeaSortName(devStateInfo.getTeaSortName());
//		devStatusResp.setConnStatus(Constant.ConnectState.CONNECTED);
		if (DevWorkRemainTimeUtils.hasWorkRemainTime(devStatusRespDTO)) {
			DevWorkRemainTimeUtils.asyncProcessWorkRemainTime(devStatusRespDTO, devStateInfo);
		} else {
			this.redisUtils.set(devStatusRespDTO.getDeviceId(), devStatusRespDTO);
		}
	}

	@Override
	public void updateConnectState(String deviceId, int connectState) {
		DeviceStateInfoEntity deviceState = new DeviceStateInfoEntity();
		deviceState.setDeviceId(deviceId);
		deviceState.setConnectState(connectState);
		this.deviceStateInfoDao.updateConnectState(deviceState);
	}
}
