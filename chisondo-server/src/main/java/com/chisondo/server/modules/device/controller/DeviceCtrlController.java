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
	@ParamValidator({UserDevRelaValidator.class, DevCtrlParamValidator.class})
	@DevOperateLog("洗茶控制")
	public CommonResp washTea(@RequestBody CommonReq req){
		if (req.isOldDev()) {
			JSONObject result = this.oldDevCtrlService.service(req, Constant.OldDeviceOperType.WASH_TEA);
			return CommonUtils.buildOldDevResp(result);
		}
		return this.deviceCtrlService.washTea(req);
	}

	/**
	 * 烧水控制
	 * 开始烧水 或设置液晶屏版 烧水按钮参数
	 * @param req
	 * @return
	 */
	@RequestMapping("/api/rest/boilWater")
	@ParamValidator({UserDevRelaValidator.class, DevCtrlParamValidator.class})
	@DevOperateLog("烧水控制")
	public CommonResp boilWater(@RequestBody CommonReq req){
		// TODO 老设备未找到烧水控制API 需确认(当前调用开始沏茶接口)
		if (req.isOldDev()) {
			JSONObject result = this.oldDevCtrlService.service(req, Constant.OldDeviceOperType.START_OR_RESERVE_MAKE_TEA);
			return CommonUtils.buildOldDevResp(result);
		}
		return this.deviceCtrlService.boilWater(req);
	}

	/**
	 * 停止沏茶/洗茶/烧水操作
	 * @param req
	 * @return
	 */
	@RequestMapping("/api/rest/stopWorking")
	@ParamValidator({DevExistenceValidator.class, StopWorkValidator.class})
	@DevOperateLog("停止沏茶/洗茶/烧水操作")
	public CommonResp stopWorking(@RequestBody CommonReq req){
		if (req.isOldDev()) {
			JSONObject result = this.oldDevCtrlService.service(req, Constant.OldDeviceOperType.STOP_WORK);
			return CommonUtils.buildOldDevResp(result);
		}
		return this.deviceCtrlService.stopWorking(req);
	}

	/**
	 * 使用茶谱沏茶
	 */
	@RequestMapping("/api/rest/startByChapu")
	@ParamValidator({UserDevRelaValidator.class, UseTeaSpectrumValidator.class})
	@DevOperateLog("使用茶谱沏茶")
	public CommonResp makeTeaByTeaSpectrum(@RequestBody CommonReq req){
		if (req.isOldDev()) {
			JSONObject result = this.oldDevCtrlService.service(req, Constant.OldDeviceOperType.USE_TEA_SPECTRUM);
			return CommonUtils.buildOldDevResp(result);
		}
		return this.deviceCtrlService.makeTeaByTeaSpectrum(req);
	}

	/**
	 * 取消使用茶谱
	 * 在开始使用茶谱泡茶后中断当前茶谱的使用，用户需要重新选择茶谱执行沏茶
	 * @param req
	 * @return
	 */
	@RequestMapping("/api/rest/cancelChapu")
	@ParamValidator({DevExistenceValidator.class, CancelTeaSpectrumValidator.class})
	@DevOperateLog("取消使用茶谱沏茶")
	public CommonResp cancelTeaSpectrum(@RequestBody CommonReq req){
		if (req.isOldDev()) {
			JSONObject result = this.oldDevCtrlService.service(req, Constant.OldDeviceOperType.CANCEL_TEA_SPECTRUM);
			return CommonUtils.buildOldDevResp(result);
		}
		return this.deviceCtrlService.cancelTeaSpectrum(req);
	}
	/**
	 * 保温控制
	 * 启动保温或停止保温
	 * @param req
	 * @return
	 */
	@RequestMapping("/api/rest/setWarmState")
	@ParamValidator({UserDevRelaValidator.class, KeepWarmCtrlValidator.class})
	@DevOperateLog("保温控制")
	public CommonResp keepWarmCtrl(@RequestBody CommonReq req){
		if (req.isOldDev()) {
			JSONObject result = this.oldDevCtrlService.service(req, Constant.OldDeviceOperType.WARM_CONTROL);
			return CommonUtils.buildOldDevResp(result);
		}
		return this.deviceCtrlService.keepWarmCtrl(req);
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
