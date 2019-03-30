package com.chisondo.server.modules.device.controller;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.annotation.ParamValidator;
import com.chisondo.server.common.core.AbstractController;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.http.CommonResp;
import com.chisondo.server.common.utils.Keys;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.modules.device.service.ActivedDeviceInfoService;
import com.chisondo.server.modules.device.service.DeviceQueryService;
import com.chisondo.server.modules.device.service.DeviceStateInfoService;
import com.chisondo.server.modules.device.validator.DevExistenceValidator;
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
	private ActivedDeviceInfoService deviceInfoService;

	@Autowired
	private DeviceQueryService deviceQueryService;

	/**
	 * 查询设备实时状态
	 */
	@RequestMapping("/api/rest/qryDevStatus")
	@ParamValidator({DevExistenceValidator.class})
	public CommonResp queryDeviceStatus(@RequestBody CommonReq req) {
		return this.deviceQueryService.queryDevStateInfo(req);
	}

	/**
	 * 查询设备设置信息
	 * @param req
	 * @return
	 */
	@RequestMapping("/api/rest/qryDeviceSetInfo")
	@ParamValidator({DevExistenceValidator.class})
	public CommonResp queryDevSettingInfo(@RequestBody CommonReq req) {
		/*retn	Y	Integer	返回码
		desc	Y	String	返回描述
		deviceName	Y	String	设备名称
		devicePwd	Y	String	连接密码
		isOpenSound	Y	Integer	是否静音	0-有提示音；1-无提音
		waterHeat	Y	Array	烧水参数
		temperature	Y	Integer	设定温度
		soak	Y	Integer	设定时间	烧水默认 0
		waterlevel	Y	Integer	出水量	分8档
		chapuInfo	N	Array	茶谱信息
		index	Y	Integer	面板位置	液晶屏中的茶谱顺序
		chapuId	Y	Integer	茶谱ID
		chapuName	Y	String	茶谱名称
		chapuImg	N	String	茶谱图标	可为空，显示默认图标
		sortId	Y	int	茶类ID	参考“获取茶类”接口
		sortName	Y	String	茶类名称
		makeTimes	Y	int	泡数	茶谱总泡数
		brandName	Y	String	茶品牌名称	茶叶所属品牌*/

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
		return this.deviceInfoService.queryHisConnectDevOfUser(jsonObj.getString(Keys.PHONE_NUM));
	}

	/**
	 * 查询设备的沏茶记录
	 * @param req
	 * @return
	 */
	@RequestMapping("/api/rest/qryDeviceTeaRecord")
	@ParamValidator({DevExistenceValidator.class})
	public CommonResp queryMakeTeaRecordsOfDev(@RequestBody CommonReq req) {
		/*输入参数	是否必填	参数类型	说明	备注
		phoneNum	Y	String	手机号码
		num	N	int	每页条数
		page	N	int	页码*/
		return this.deviceQueryService.queryMakeTeaRecordsOfDev(req);
	}
}
