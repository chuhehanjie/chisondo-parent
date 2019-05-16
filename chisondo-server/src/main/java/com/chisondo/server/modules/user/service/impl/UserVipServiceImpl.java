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
import com.google.common.collect.Lists;
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
		DynamicDataSource.setDataSource(DataSourceNames.THIRD);
		UserVipEntity user = userVipDao.queryObject(memberId);
		DynamicDataSource.setDataSource(DataSourceNames.FIRST);
		return user;
	}
	
	@Override
	public List<UserVipEntity> queryList(Map<String, Object> map){
		DynamicDataSource.setDataSource(DataSourceNames.THIRD);
		List<UserVipEntity> userList = userVipDao.queryList(map);
		DynamicDataSource.setDataSource(DataSourceNames.FIRST);
		return userList;
	}

	@Override
	public UserVipEntity getUserByMobile(String mobile) {
		List<UserVipEntity> userData = this.queryList(ImmutableMap.of(Keys.PHONE, mobile));
		return ValidateUtils.isNotEmptyCollection(userData) ? userData.get(0) : null;
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		DynamicDataSource.setDataSource(DataSourceNames.THIRD);
		int total = userVipDao.queryTotal(map);
		DynamicDataSource.setDataSource(DataSourceNames.FIRST);
		return total;
	}
	
	@Override
	public void save(UserVipEntity dataVip){
		DynamicDataSource.setDataSource(DataSourceNames.THIRD);
		userVipDao.save(dataVip);
		DynamicDataSource.setDataSource(DataSourceNames.FIRST);
	}
	
	@Override
	public void update(UserVipEntity dataVip){
		DynamicDataSource.setDataSource(DataSourceNames.THIRD);
		userVipDao.update(dataVip);
		DynamicDataSource.setDataSource(DataSourceNames.FIRST);
	}
	
	@Override
	public void delete(Long memberId){
		DynamicDataSource.setDataSource(DataSourceNames.THIRD);
		userVipDao.delete(memberId);
		DynamicDataSource.setDataSource(DataSourceNames.FIRST);
	}
	
	@Override
	public void deleteBatch(Long[] memberIds){
		DynamicDataSource.setDataSource(DataSourceNames.THIRD);
		userVipDao.deleteBatch(memberIds);
		DynamicDataSource.setDataSource(DataSourceNames.FIRST);
	}

	@Override
	public UserVipEntity queryUserByMemberId(Long memberId) {
		return this.queryObject(memberId);
	}

	@Override
	public List<UserVipEntity> queryUserListByUserIds(List<Long> userIds) {
		DynamicDataSource.setDataSource(DataSourceNames.THIRD);
		List<UserVipEntity> userList = this.userVipDao.queryUserListByUserIds(userIds);
		DynamicDataSource.setDataSource(DataSourceNames.FIRST);
		return userList;
	}

	@Override
	public Map<String, Object> queryAllUsersOfDevice(String deviceId) {
		Map<String, Object> resultMap = Maps.newHashMap();
		List<UsedDeviceUserDTO> usedDevUserList = Lists.newArrayList();
		List<Map<String, Object>> resultList = this.userVipDao.queryAllUsersOfDevice(deviceId);
		if (ValidateUtils.isNotEmptyCollection(resultList)) {
			DynamicDataSource.setDataSource(DataSourceNames.THIRD);
			for (Map<String, Object> item : resultList) {
				long userId = Long.valueOf(item.get("teaman_id").toString());
				UserVipEntity user = this.userVipDao.queryObject(userId);
				if (ValidateUtils.isNotEmpty(user)) {
					UsedDeviceUserDTO usedDevUser = new UsedDeviceUserDTO();
					usedDevUser.setLastUseTime(ValidateUtils.isNotEmpty(item.get("last_link_time")) ? item.get("last_link_time").toString() : null);
					usedDevUser.setUserImg(CommonUtils.plusFullImgPath(user.getVipHeadImg()));
					usedDevUser.setPhoneNum(user.getPhone());
					usedDevUser.setUserName(ValidateUtils.isNotEmptyString(user.getWechatNickname()) ? user.getWechatNickname() : user.getVipNickname());
					usedDevUserList.add(usedDevUser);
				}
			}
			DynamicDataSource.setDataSource(DataSourceNames.FIRST);

		}
		resultMap.put(Keys.COUNT, ValidateUtils.isEmptyCollection(usedDevUserList) ? 0 : resultMap.size());
		resultMap.put(Keys.USER_INFO, usedDevUserList);
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
