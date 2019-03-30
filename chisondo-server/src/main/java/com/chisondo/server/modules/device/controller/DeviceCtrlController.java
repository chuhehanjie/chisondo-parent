package com.chisondo.server.modules.device.controller;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.annotation.DevOperateLog;
import com.chisondo.server.common.annotation.ParamValidator;
import com.chisondo.server.common.core.AbstractController;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.http.CommonResp;
import com.chisondo.server.common.utils.CommonUtils;
import com.chisondo.server.common.utils.Constant;
import com.chisondo.server.common.utils.Keys;
import com.chisondo.server.modules.device.dto.req.*;
import com.chisondo.server.modules.device.dto.resp.DeviceBindRespDTO;
import com.chisondo.server.modules.device.service.DeviceCtrlService;
import com.chisondo.server.modules.device.validator.*;
import com.chisondo.server.modules.olddevice.service.OldDeviceCtrlService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 设备控制 controller
 * @author ding.zhong
 * @email 258321511@qq.com
 * @date Mar 12.19
 */
@RestController
public class DeviceCtrlController extends AbstractController {

	@Autowired
	private DeviceCtrlService deviceCtrlService;

	@Autowired
	private OldDeviceCtrlService oldDevCtrlService;

	/**
	 * 启动或预约泡茶
	 * 设置参数并启动泡茶，或提前预约在指定的时间开始沏茶，按茶类沏茶或自己设置参数沏茶，非茶谱沏茶
	 */
	@RequestMapping("/api/rest/startWorking")
	@ParamValidator({UserDevRelaValidator.class, StartOrReserveMakeTeaValidator.class})
	@DevOperateLog("启动或预约泡茶")
	public CommonResp startOrReserveMakeTea(@RequestBody CommonReq req){
		if (req.isOldDev()) {
			// TODO 走老设备流程
			JSONObject result = this.oldDevCtrlService.service(req, Constant.OldDeviceOperType.START_OR_RESERVE_MAKE_TEA);
			CommonResp resp = CommonUtils.buildOldDevResp(result);
			resp.setBizBody(JSONObject.toJSONString(ImmutableMap.of("bizBody", result.get(Keys.RESERV_NO))));
			return resp;
		}
        return this.deviceCtrlService.startOrReserveMakeTea(req);
	}

	/**
	 * 洗茶控制
	 * 开始洗茶或设置液晶屏版 洗茶按钮参数
	 * @param req
	 * @return
	 */
	@RequestMapping("/api/rest/washTea")
	@ParamValidator({UserDevRelaValidator.class})
	public CommonResp washTea(@RequestBody CommonReq req){
		if (req.isOldDev()) {
			JSONObject result = this.oldDevCtrlService.service(req, Constant.OldDeviceOperType.WASH_TEA);
			return CommonUtils.buildOldDevResp(result);
		}
		/*
		deviceId	Y	String	设备ID
		phoneNum	Y	String	手机号码	设备绑定的手机号码
		isSave	N	int	是否更新液晶屏洗茶按钮参数。	0-执行洗茶功能 1-执行洗茶并修改液晶屏洗茶按钮参数 2-只修改液晶屏洗茶按钮参数，不执行洗茶操作（为空则默认：0）
		temperature	Y	Integer	设定温度	60~100,0-停止加热
		soak	Y	Integer	设定时间（根据出汤量不同时间的最小值不同）	0~600秒,0 - 不浸泡，1~600  浸泡时间(单位:秒)
		waterlevel	Y	Integer	出水量（分8个档次）	 150 200 250 300   350 400  450 550 (单位：毫升ml)
		teaSortId	N	Integer	茶类ID
		teaSortName	N	String	茶类名称
		响应参数
		输出参数	是否必填	参数类型	说明	备注
		retn	Y	Integer	返回码
		Desc	Y	String	返回描述
		*/
		return this.deviceCtrlService.washTea(req);
	}

	/**
	 * 烧水控制
	 * 开始烧水 或设置液晶屏版 烧水按钮参数
	 * @param req
	 * @return
	 */
	@RequestMapping("/api/rest/boilWater")

	public CommonResp boilWater(@RequestBody CommonReq req){
		BoilWaterReqDTO boilWaterReq = JSONObject.parseObject(req.getBizBody(), BoilWaterReqDTO.class);
		/*
		deviceId	Y	String	设备ID
		phoneNum	Y	String	手机号码	设备绑定的手机号码
		isSave	N	int	是否更新液晶屏烧水按钮参数。	0-执行烧水功能 1-执行烧水并修改液晶屏烧水按钮参数 2-只修改液晶屏烧水按钮参数，不执行烧水操作（为空则默认：0）
		temperature	Y	Integer	设定温度	60~100,0-停止加热
		soak	N	Integer	设定时间（根据出汤量不同时间的最小值不同）	0~600秒,0-不浸泡（保留参数，可不传，默认为0 直接出水）
		waterlevel	Y	Integer	出水量（分8个档次）	 150 200 250 300   350 400  450 550 (单位：毫升ml)

		*/
		return this.deviceCtrlService.boilWater(boilWaterReq);
	}

	/**
	 * 停止沏茶/洗茶/烧水操作
	 * @param req
	 * @return
	 */
	@RequestMapping("/api/rest/stopWorking")
	@DevOperateLog("停止沏茶/洗茶/烧水操作")
	public CommonResp stopWorking(@RequestBody CommonReq req){
		StopWorkReqDTO stopWorkReq = JSONObject.parseObject(req.getBizBody(), StopWorkReqDTO.class);
		/*
		deviceId	Y	String	设备ID
		phoneNum	Y	String	手机号码	设备绑定的手机号码
		isSave	N	int	是否更新液晶屏烧水按钮参数。	0-执行烧水功能 1-执行烧水并修改液晶屏烧水按钮参数 2-只修改液晶屏烧水按钮参数，不执行烧水操作（为空则默认：0）
		temperature	Y	Integer	设定温度	60~100,0-停止加热
		soak	N	Integer	设定时间（根据出汤量不同时间的最小值不同）	0~600秒,0-不浸泡（保留参数，可不传，默认为0 直接出水）
		waterlevel	Y	Integer	出水量（分8个档次）	 150 200 250 300   350 400  450 550 (单位：毫升ml)

		*/
		return this.deviceCtrlService.stopWorking(stopWorkReq);
	}

	/**
	 * 取消使用茶谱
	 * 在开始使用茶谱泡茶后中断当前茶谱的使用，用户需要重新选择茶谱执行沏茶
	 * @param req
	 * @return
	 */
	@RequestMapping("/api/rest/cancelChapu")
	public CommonResp cancelTeaSpectrum(@RequestBody CommonReq req){
		JSONObject jsonObj = JSONObject.parseObject(req.getBizBody());
		String devieId = jsonObj.getString(Keys.DEVICE_ID);

		return this.deviceCtrlService.cancelTeaSpectrum(devieId);
	}
	/**
	 * 保温控制
	 * 启动保温或停止保温
	 * @param req
	 * @return
	 */
	@RequestMapping("/api/rest/setWarmState")
	public CommonResp keepWarmCtrl(@RequestBody CommonReq req){
		DevCommonReqDTO devCommonReq = JSONObject.parseObject(req.getBizBody(), DevCommonReqDTO.class);
		return this.deviceCtrlService.keepWarmCtrl(devCommonReq);
	}

	/**
	 * 使用茶谱沏茶
	 */
	@RequestMapping("/api/rest/startByChapu")
	public CommonResp makeTeaByTeaSpectrum(@RequestBody CommonReq req){
        MakeTeaByTeaSpectrumReqDTO makeTeaReq = JSONObject.parseObject(req.getBizBody(), MakeTeaByTeaSpectrumReqDTO.class);
        this.deviceCtrlService.makeTeaByTeaSpectrum(makeTeaReq);
        return CommonResp.ok();
	}

	/**
	 * 绑定沏茶器（添加设备）
	 * @param req
	 * @return
	 */
	@RequestMapping("/api/rest/connectDevice")
	@DevOperateLog("绑定沏茶器")
	@ParamValidator({BindDeviceValidator.class})
	public CommonResp bindDevice(@RequestBody CommonReq req) {
        DeviceBindRespDTO devBindResp = this.deviceCtrlService.bindDevice(req);
		return CommonResp.ok(devBindResp);
	}

	/**
	 * 删除设备连接记录
	 * 删除一条用户连接设备的历史记录
	 * @param req
	 * @return
	 */
	@RequestMapping("/api/rest/delDevConnRecord")
	@DevOperateLog("删除设备连接记录")
	@ParamValidator({DevExistenceValidator.class, UserExistenceValidator.class})
	public CommonResp delDevConnectRecord(@RequestBody CommonReq req) {
		this.deviceCtrlService.delDevConnectRecord(req);
		return CommonResp.ok();
	}
	/**
	 * 设置沏茶器的密码
	 * @param req
	 * @return
	 */
	@RequestMapping("/api/rest/setDevicePassword")
	@DevOperateLog(value = "设置沏茶器密码", asyncCall = true)
	@ParamValidator({SetDevPwdValidator.class})
	public CommonResp setDevicePassword(@RequestBody CommonReq req) {
		return this.deviceCtrlService.setDevicePassword(req);
	}


	/**
	 * 设置沏茶器名称/描述
	 * @param req
	 * @return
	 */
	@RequestMapping("/api/rest/setDeviceName")
	@DevOperateLog("设置沏茶器名称/描述")
	@ParamValidator({UserDevRelaValidator.class, SetDevNameValidator.class})
	public CommonResp setDeviceNameOrDesc(@RequestBody CommonReq req) {
		return this.deviceCtrlService.setDeviceNameOrDesc(req);
	}

	/**
	 * 打开或关闭设备“滴声”提示音
	 * @param req
	 * @return
	 */
	@RequestMapping("/api/rest/setDeviceSound")
	@DevOperateLog("设置沏茶器名称/描述")
	@ParamValidator({UserDevRelaValidator.class})
	public CommonResp setDeviceSound(@RequestBody CommonReq req) {
		return this.deviceCtrlService.setDeviceSound(req);
	}

	/**
	 * 设置默认设备
	 * @param req
	 * @return
	 */
	@RequestMapping("/api/rest/setDefaultDevice")
	@DevOperateLog("设置沏茶器名称/描述")
	@ParamValidator({UserDevRelaValidator.class})
	public CommonResp setDefaultDevice(@RequestBody CommonReq req) {
		return this.deviceCtrlService.setDefaultDevice(req);
	}
}
