package com.chisondo.server.modules.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.annotation.ParamValidator;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.http.CommonResp;
import com.chisondo.server.common.utils.*;
import com.chisondo.server.modules.device.validator.QueryReservationValidator;
import com.chisondo.server.modules.olddevice.service.OldDeviceCtrlService;
import com.chisondo.server.modules.user.dto.UserMakeTeaReservationDTO;
import com.chisondo.server.modules.user.service.UserBookService;
import com.chisondo.server.modules.user.service.UserVipService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * 用户查询 controller
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Apr 07.19
 */
@RestController
public class UserQueryController {

	@Autowired
	private UserBookService userBookService;

	@Autowired
	private UserVipService userVipService;

	@Autowired
	private OldDeviceCtrlService oldDevCtrlService;

	/**
	 * 查询用户预约信息
	 */
	@PostMapping("/api/rest/queryReservation")
	@ParamValidator({QueryReservationValidator.class})
	public CommonResp queryUserReservation(@RequestBody CommonReq req){
		if (req.isOldDev()) {
			JSONObject result = this.oldDevCtrlService.queryReservation(req);
			Map<String, Object> resultMap = Maps.newHashMap();
			resultMap.put(Keys.COUNT, result.containsKey("count") ? result.get("count") : 0);
			/*List<UserMakeTeaReservationDTO> makeTeaReservationList = Lists.newArrayList();
			UserMakeTeaReservationDTO item = new UserMakeTeaReservationDTO();
			item.setReservNo("");
			item.setPhoneNum("");
			item.setReservTime("");
			item.setStartTime("");
			item.setChapuId(0);
			item.setChapuName("");
			item.setChapuImage("");
			item.setTeaSortId(0);
			item.setTeaSortName("");
			item.setMakeDura(0);
			item.setWaterlv(0);
			item.setMakeTemp(0);
			item.setValid(0);
			makeTeaReservationList.add(item);*/
			resultMap.put(Keys.RESERV_INFO, result.get("reservInfo"));
			return CommonResp.ok(resultMap);
		}
		JSONObject jsonObj = JSONObject.parseObject(req.getBizBody());
		this.validate(jsonObj);
		Map<String, Object> params = this.buildQryParams(jsonObj);
		Map<String, Object> resultMap = this.userBookService.queryUserReservation(params);
		return CommonResp.ok(resultMap);
	}

	/**
	 * 查询使用过该设备的所有用户
	 */
	@PostMapping("/api/rest/getDeviceConnectedUser")
	public CommonResp queryAllUsersOfDevice(@RequestBody CommonReq req){
		JSONObject jsonObj = JSONObject.parseObject(req.getBizBody());
		this.validate(jsonObj);
		Map<String, Object> resultMap = this.userVipService.queryAllUsersOfDevice(jsonObj.getString(Keys.DEVICE_ID));
		return CommonResp.ok(resultMap);
	}

	/**
	 * 查询用户的沏茶记录
	 * @param req
	 * @return
	 */
	@RequestMapping("/api/rest/qryUserTeaRecord")
	public CommonResp queryMakeTeaRecordsOfUser(@RequestBody CommonReq req) {
		return this.userVipService.queryMakeTeaRecordsOfUser(req);
	}

	private Map<String,Object> buildQryParams(JSONObject jsonObj) {
		Map<String, Object> params = Maps.newHashMap();
		params.put(Query.LIMIT, ValidateUtils.isEmpty(jsonObj.get(Query.NUM)) ? 10 : jsonObj.get(Query.NUM));
		params.put(Query.PAGE, ValidateUtils.isEmpty(jsonObj.get(Query.PAGE)) ? 1 : jsonObj.get(Query.PAGE));
		params.put(Keys.DEVICE_ID, jsonObj.getString(Keys.DEVICE_ID));
		String phoneNum = jsonObj.getString(Keys.PHONE_NUM);
		if (ValidateUtils.isNotEmptyString(phoneNum)) {
			params.put(Keys.PHONE_NUM, phoneNum);
		}
		return params;
	}

	private void validate(JSONObject jsonObj) {
		if (ValidateUtils.isEmpty(jsonObj)) {
			throw new CommonException("请求体为空");
		}
		if (ValidateUtils.isEmptyString(jsonObj.getString(Keys.DEVICE_ID))) {
			throw new CommonException("设备ID为空");
		}
	}

}
