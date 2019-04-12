package com.chisondo.server.modules.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.http.CommonResp;
import com.chisondo.server.common.utils.CommonUtils;
import com.chisondo.server.common.utils.Keys;
import com.chisondo.server.common.utils.Query;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.datasources.DataSourceNames;
import com.chisondo.server.datasources.DynamicDataSource;
import com.chisondo.server.modules.device.dto.resp.MakeTeaRespDTO;
import com.chisondo.server.modules.device.dto.resp.MakeTeaRowRespDTO;
import com.chisondo.server.modules.user.dto.UsedDeviceUserDTO;
import com.chisondo.server.modules.user.service.UserMakeTeaService;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.chisondo.server.modules.user.dao.UserVipDao;
import com.chisondo.server.modules.user.entity.UserVipEntity;
import com.chisondo.server.modules.user.service.UserVipService;



@Service("dataVipService")
public class UserVipServiceImpl implements UserVipService {
	@Autowired
	private UserVipDao userVipDao;

	@Autowired
	private UserMakeTeaService userMakeTeaService;
	
	@Override
	public UserVipEntity queryObject(Long memberId){
		return userVipDao.queryObject(memberId);
	}
	
	@Override
	public List<UserVipEntity> queryList(Map<String, Object> map){
		return userVipDao.queryList(map);
	}

	@Override
	public UserVipEntity getUserByMobile(String mobile) {
		List<UserVipEntity> userData = this.queryList(ImmutableMap.of(Keys.PHONE, mobile));
		return ValidateUtils.isNotEmptyCollection(userData) ? userData.get(0) : null;
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return userVipDao.queryTotal(map);
	}
	
	@Override
	public void save(UserVipEntity dataVip){
		userVipDao.save(dataVip);
	}
	
	@Override
	public void update(UserVipEntity dataVip){
		userVipDao.update(dataVip);
	}
	
	@Override
	public void delete(Long memberId){
		userVipDao.delete(memberId);
	}
	
	@Override
	public void deleteBatch(Long[] memberIds){
		userVipDao.deleteBatch(memberIds);
	}

	@Override
	public UserVipEntity queryUserByMemberId(Long memberId) {
		DynamicDataSource.setDataSource(DataSourceNames.FIRST);
		return this.userVipDao.queryObject(memberId);
	}

	@Override
	public List<UserVipEntity> queryUserListByUserIds(List<Long> userIds) {
		DynamicDataSource.setDataSource(DataSourceNames.FIRST);
		return this.userVipDao.queryUserListByUserIds(userIds);
	}

	@Override
	public Map<String, Object> queryAllUsersOfDevice(String deviceId) {
		Map<String, Object> resultMap = Maps.newHashMap();
		List<UsedDeviceUserDTO> usedDeviceUsers = this.userVipDao.queryAllUsersOfDevice(deviceId);
		if (ValidateUtils.isNotEmptyCollection(usedDeviceUsers)) {
			for (UsedDeviceUserDTO usedDeviceUser : usedDeviceUsers) {
				usedDeviceUser.setUserImg(CommonUtils.plusFullImgPath(usedDeviceUser.getUserImg()));
			}
		}
		resultMap.put(Keys.COUNT, ValidateUtils.isEmptyCollection(usedDeviceUsers) ? 0 : usedDeviceUsers.size());
		resultMap.put(Keys.USER_INFO, usedDeviceUsers);
		return resultMap;
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
		if (ValidateUtils.isNotEmptyCollection(rows)) {
			for (MakeTeaRowRespDTO makeTeaRow : rows) {
				makeTeaRow.setChapuImage(CommonUtils.plusFullImgPath(makeTeaRow.getChapuImage()));
				makeTeaRow.setUserImg(CommonUtils.plusFullImgPath(makeTeaRow.getUserImg()));
			}
		}
		MakeTeaRespDTO makeTeaResp = new MakeTeaRespDTO(count, rows);
		return CommonResp.ok(makeTeaResp);
	}
}
