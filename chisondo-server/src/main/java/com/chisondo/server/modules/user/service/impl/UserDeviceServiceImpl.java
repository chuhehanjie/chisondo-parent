package com.chisondo.server.modules.user.service.impl;

import com.chisondo.server.common.utils.CommonUtils;
import com.chisondo.server.common.utils.Constant;
import com.chisondo.server.common.utils.Keys;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.modules.device.dto.req.DeviceBindReqDTO;
import com.chisondo.server.modules.user.dto.UsedDeviceUserDTO;
import com.chisondo.server.modules.user.entity.UserVipEntity;
import com.chisondo.server.modules.user.service.UserVipService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.chisondo.server.modules.user.dao.UserDeviceDao;
import com.chisondo.server.modules.user.entity.UserDeviceEntity;
import com.chisondo.server.modules.user.service.UserDeviceService;



@Service("userDeviceService")
public class UserDeviceServiceImpl implements UserDeviceService {
	@Autowired
	private UserDeviceDao userDeviceDao;

	@Autowired
	private UserVipService userVipService;
	
	@Override
	public UserDeviceEntity queryObject(Integer id){
		return userDeviceDao.queryObject(id);
	}
	
	@Override
	public List<UserDeviceEntity> queryList(Map<String, Object> map){
		return userDeviceDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return userDeviceDao.queryTotal(map);
	}
	
	@Override
	public void save(UserDeviceEntity userDevice){
		userDeviceDao.save(userDevice);
	}
	
	@Override
	public void update(UserDeviceEntity userDevice){
		userDeviceDao.update(userDevice);
	}
	
	@Override
	public void delete(Integer id){
		userDeviceDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		userDeviceDao.deleteBatch(ids);
	}

	@Override
	public void save(DeviceBindReqDTO devBindReq, Long userId) {
		// 首先将用户其他设备设置为非默认
		this.setNoneDefaultDev(userId);
		UserDeviceEntity userDevice = new UserDeviceEntity();
		userDevice.setTeamanId(userId.toString());
		userDevice.setDeviceId(Integer.valueOf(devBindReq.getDeviceId()));
		// 设置为默认设备
		userDevice.setPrivateTag(Constant.DevPrivateTag.NO);
		userDevice.setDefaultTag(Constant.DevDefaultTag.YES);
		this.save(userDevice);
	}

	@Override
	public void delUserDeviceByParams(Map<String, Object> params) {
		this.userDeviceDao.delUserDeviceByParams(params);
	}

	@Override
	public void setDefaultDevice(Map<String, Object> params) {
		this.userDeviceDao.setDefaultDevice(params);
	}

	@Override
	public void setNoneDefaultDev(Long userId) {
		this.userDeviceDao.setNoneDefaultDev(userId.toString());
	}

	@Override
	public Map<String, Object> queryAllUsersOfDevice(String deviceId) {
		Map<String, Object> resultMap = Maps.newHashMap();
		List<UsedDeviceUserDTO> usedDevUserList = Lists.newArrayList();
		List<Map<String, Object>> resultList = this.userDeviceDao.queryAllUsersOfDevice(deviceId);
		if (ValidateUtils.isNotEmptyCollection(resultList)) {
			for (Map<String, Object> item : resultList) {
				long userId = Long.valueOf(item.get("teaman_id").toString());
				UserVipEntity user = this.userVipService.queryObject(userId);
				if (ValidateUtils.isNotEmpty(user)) {
					UsedDeviceUserDTO usedDevUser = new UsedDeviceUserDTO();
					usedDevUser.setLastUseTime(ValidateUtils.isNotEmpty(item.get("last_link_time")) ? item.get("last_link_time").toString() : null);
					usedDevUser.setUserImg(CommonUtils.plusFullImgPath(user.getVipHeadImg()));
					usedDevUser.setPhoneNum(user.getPhone());
					usedDevUser.setUserName(ValidateUtils.isNotEmptyString(user.getWechatNickname()) ? user.getWechatNickname() : user.getVipNickname());
					usedDevUserList.add(usedDevUser);
				}
			}
		}
		resultMap.put(Keys.COUNT, ValidateUtils.isEmptyCollection(usedDevUserList) ? 0 : resultMap.size());
		resultMap.put(Keys.USER_INFO, usedDevUserList);
		return resultMap;
	}
}
