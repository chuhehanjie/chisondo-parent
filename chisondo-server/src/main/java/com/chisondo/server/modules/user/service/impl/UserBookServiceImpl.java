package com.chisondo.server.modules.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.utils.*;
import com.chisondo.server.modules.device.dto.req.StartOrReserveMakeTeaReqDTO;
import com.chisondo.server.modules.tea.entity.AppChapuEntity;
import com.chisondo.server.modules.user.dao.UserBookDao;
import com.chisondo.server.modules.user.dto.UserMakeTeaReservationDTO;
import com.chisondo.server.modules.user.entity.UserBookEntity;
import com.chisondo.server.modules.user.entity.UserVipEntity;
import com.chisondo.server.modules.user.service.UserBookService;
import com.chisondo.server.modules.user.service.UserVipService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



@Service("userBookService")
public class UserBookServiceImpl implements UserBookService {
	@Autowired
	private UserBookDao userBookDao;

	@Autowired
	private UserVipService userVipService;

	@Override
	public UserBookEntity queryObject(Integer id){
		return userBookDao.queryObject(id);
	}
	
	@Override
	public List<UserBookEntity> queryList(Map<String, Object> map){
		return userBookDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return userBookDao.queryTotal(map);
	}
	
	@Override
	public void save(UserBookEntity userBook){
		userBookDao.save(userBook);
	}
	
	@Override
	public void update(UserBookEntity userBook){
		userBookDao.update(userBook);
	}
	
	@Override
	public void delete(Integer id){
		userBookDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		userBookDao.deleteBatch(ids);
	}

	@Override
	public Map<String, Object> queryUserReservation(CommonReq req) {
		Map<String, Object> resultMap = Maps.newHashMap();
		Map<String, Object> qryParams = this.buildQryParams(req);
		UserVipEntity user = (UserVipEntity) req.getAttrByKey(Keys.USER_INFO);
		if (ValidateUtils.isNotEmpty(user)) {
			qryParams.put(Keys.TEAMAN_ID, user.getMemberId());
		}
		int count = this.queryTotal(qryParams);
		resultMap.put(Keys.COUNT, count);
		if (count > 0) {
			List<UserMakeTeaReservationDTO> userMakeTeaResvList = Lists.newArrayList();
			List<UserBookEntity> userBookList = this.queryList(new Query(qryParams));
			if (ValidateUtils.isNotEmpty(user)) {
				userBookList.forEach(userBook -> {
					UserMakeTeaReservationDTO userMakeTeaResv = this.buildUserMakeTeaResv(user, userBook);
					userMakeTeaResvList.add(userMakeTeaResv);
				});
			} else {
				for (UserBookEntity userBook : userBookList) {
					UserVipEntity userVip = this.userVipService.queryObject(Long.valueOf(userBook.getTeamanId()));
					UserMakeTeaReservationDTO userMakeTeaResv = this.buildUserMakeTeaResv(userVip, userBook);
					userMakeTeaResvList.add(userMakeTeaResv);
				}
			}
			resultMap.put(Keys.RESERV_INFO, userMakeTeaResvList);
		} else {
			resultMap.put(Keys.RESERV_INFO, Lists.newArrayList());
		}
		return resultMap;
	}

	private Map<String, Object> buildQryParams(CommonReq req) {
		JSONObject jsonObj = (JSONObject) req.getAttrByKey(Keys.REQ);
		Map<String, Object> qryParams = Maps.newHashMap();
		qryParams.put(Query.LIMIT, ValidateUtils.isEmpty(jsonObj.get(Query.NUM)) ? 10 : jsonObj.get(Query.NUM));
		qryParams.put(Query.PAGE, ValidateUtils.isEmpty(jsonObj.get(Query.PAGE)) ? 1 : jsonObj.get(Query.PAGE));
		qryParams.put(Keys.DEVICE_ID, req.getAttrByKey(Keys.DEVICE_ID));
		return qryParams;
	}

	private UserMakeTeaReservationDTO buildUserMakeTeaResv(UserVipEntity user, UserBookEntity userBook) {
		UserMakeTeaReservationDTO userMakeTeaResv = new UserMakeTeaReservationDTO();
		userMakeTeaResv.setReservNo(userBook.getReservNo());
		userMakeTeaResv.setPhoneNum(user.getPhone());
		userMakeTeaResv.setReservTime(DateUtils.format(userBook.getLogTime(), DateUtils.DATE_TIME_PATTERN));
		userMakeTeaResv.setStartTime(ValidateUtils.isNotEmpty(userBook.getProcessTime()) ? DateUtils.format(userBook.getProcessTime(), DateUtils.DATE_TIME_PATTERN) : null);
		userMakeTeaResv.setChapuId(userBook.getChapuId());
		AppChapuEntity teaSpectrum = CacheDataUtils.getChapuById(userBook.getChapuId());
		userMakeTeaResv.setChapuName(ValidateUtils.isEmpty(teaSpectrum) ? userBook.getChapuName() : teaSpectrum.getName());
		userMakeTeaResv.setChapuImage(ValidateUtils.isEmpty(teaSpectrum) ? null : CommonUtils.plusFullImgPath(teaSpectrum.getImage()));
		userMakeTeaResv.setTeaSortId(userBook.getTeaSortId());
		userMakeTeaResv.setTeaSortName(userBook.getTeaSortName());
		userMakeTeaResv.setValid(userBook.getValidFlag());
		if (ValidateUtils.isNotEmptyString(userBook.getReserveParam())) {
            StartOrReserveMakeTeaReqDTO startOrReserveTeaReq = JSONObject.parseObject(userBook.getReserveParam(), StartOrReserveMakeTeaReqDTO.class);
            userMakeTeaResv.setSoak(startOrReserveTeaReq.getSoak());
            userMakeTeaResv.setTemperature(startOrReserveTeaReq.getTemperature());
            userMakeTeaResv.setWarm(startOrReserveTeaReq.getWarm());
            userMakeTeaResv.setWaterlv(startOrReserveTeaReq.getWaterlevel());
        }
		return userMakeTeaResv;
	}
}
