package com.chisondo.server.modules.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.http.CommonResp;
import com.chisondo.server.common.utils.Keys;
import com.chisondo.server.common.utils.Query;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.datasources.DataSourceNames;
import com.chisondo.server.datasources.annotation.DataSource;
import com.chisondo.server.modules.user.entity.UserBookEntity;
import com.chisondo.server.modules.user.service.UserBookService;
import com.chisondo.server.modules.user.service.UserDeviceService;
import com.chisondo.server.modules.user.service.UserVipService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

	/**
	 * 查询用户预约信息
	 */
	@PostMapping("/api/rest/queryReservation")
	public CommonResp queryUserReservation(@RequestBody CommonReq req){
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
