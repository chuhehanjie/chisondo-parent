package com.chisondo.server.modules.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.http.CommonResp;
import com.chisondo.server.common.utils.CommonUtils;
import com.chisondo.server.common.utils.Keys;
import com.chisondo.server.common.utils.Query;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.modules.device.dto.resp.MakeTeaRespDTO;
import com.chisondo.server.modules.device.dto.resp.MakeTeaRowRespDTO;
import com.chisondo.server.modules.user.dao.UserVipDao;
import com.chisondo.server.modules.user.entity.UserVipEntity;
import com.chisondo.server.modules.user.service.UserMakeTeaService;
import com.chisondo.server.modules.user.service.UserVipService;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



@Service("dataVipService")
public class UserVipServiceImpl implements UserVipService {
	@Autowired
	private UserVipDao userVipDao;

	@Autowired
	private UserMakeTeaService userMakeTeaService;
	
	@Override
	public UserVipEntity queryObject(Long memberId){
		
		UserVipEntity user = userVipDao.queryObject(memberId);
		
		return user;
	}
	
	@Override
	public List<UserVipEntity> queryList(Map<String, Object> map){
		List<UserVipEntity> userList = userVipDao.queryList(map);
		return userList;
	}

	@Override
	public UserVipEntity getUserByMobile(String mobile) {
		List<UserVipEntity> userData = this.queryList(ImmutableMap.of(Keys.PHONE, mobile));
		return ValidateUtils.isNotEmptyCollection(userData) ? userData.get(0) : null;
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		int total = userVipDao.queryTotal(map);
		return total;
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
		return this.queryObject(memberId);
	}

	@Override
	public List<UserVipEntity> queryUserListByUserIds(List<Long> userIds) {
		List<UserVipEntity> userList = this.userVipDao.queryUserListByUserIds(userIds);
		return userList;
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
		UserVipEntity user = this.getUserByMobile(phoneNum);
		if (ValidateUtils.isEmpty(user)) {
			return CommonResp.ok(new MakeTeaRespDTO(0, Lists.newArrayList()));
		}
		Map<String, Object> params = CommonUtils.getPageParams(jsonObj);
		params.put(Keys.TEAMAN_ID, user.getMemberId());
		int count = this.userMakeTeaService.queryTotal(params);
		if (count > 0) {
			params.put(Query.PAGE, ValidateUtils.isEmpty(jsonObj.get(Query.PAGE)) ? 1 : jsonObj.get(Query.PAGE));
			params.put(Query.LIMIT, ValidateUtils.isEmpty(jsonObj.get(Query.NUM)) ? 10 : jsonObj.get(Query.NUM));
			List<MakeTeaRowRespDTO> rows = this.userMakeTeaService.queryMakeTeaRecordsByUserId(new Query(params));
			if (ValidateUtils.isNotEmptyCollection(rows)) {
				for (MakeTeaRowRespDTO makeTeaRow : rows) {
					makeTeaRow.setPhoneNum(user.getPhone());
					makeTeaRow.setUserName(user.getVipNickname());
					makeTeaRow.setChapuImage(CommonUtils.plusFullImgPath(makeTeaRow.getChapuImage()));
					makeTeaRow.setUserImg(CommonUtils.plusFullImgPath(makeTeaRow.getUserImg()));
				}
			}
			return CommonResp.ok(new MakeTeaRespDTO(count, rows));
		} else {
			return CommonResp.ok(new MakeTeaRespDTO(count, Lists.newArrayList()));
		}
	}
}
