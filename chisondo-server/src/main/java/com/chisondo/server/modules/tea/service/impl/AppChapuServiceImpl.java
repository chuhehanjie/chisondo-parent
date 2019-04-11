package com.chisondo.server.modules.tea.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.http.CommonResp;
import com.chisondo.server.common.utils.*;
import com.chisondo.server.datasources.DataSourceNames;
import com.chisondo.server.datasources.DynamicDataSource;
import com.chisondo.server.modules.device.service.DeviceStateInfoService;
import com.chisondo.server.modules.tea.constant.TeaSpectrumConstant;
import com.chisondo.server.modules.tea.dto.*;
import com.chisondo.server.modules.tea.entity.AppChapuParaEntity;
import com.chisondo.server.modules.tea.service.AppChapuMineService;
import com.chisondo.server.modules.tea.service.AppChapuParaService;
import com.chisondo.server.modules.user.entity.UserDeviceEntity;
import com.chisondo.server.modules.user.entity.UserVipEntity;
import com.chisondo.server.modules.user.service.UserDeviceService;
import com.chisondo.server.modules.user.service.UserVipService;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.chisondo.server.modules.tea.dao.AppChapuDao;
import com.chisondo.server.modules.tea.entity.AppChapuEntity;
import com.chisondo.server.modules.tea.service.AppChapuService;


@Service("appChapuService")
public class AppChapuServiceImpl implements AppChapuService {
	@Autowired
	private AppChapuDao appChapuDao;

	@Autowired
	private UserVipService userVipService;

	@Autowired
	private AppChapuParaService appChapuParaService;

	@Autowired
	private AppChapuMineService appChapuMineService;

	@Autowired
	private DeviceStateInfoService deviceStateInfoService;

	@Autowired
	private UserDeviceService userDeviceService;
	
	@Override
	public AppChapuEntity queryObject(Integer chapuId){
		DynamicDataSource.setDataSource(DataSourceNames.SECOND);
		return appChapuDao.queryObject(chapuId);
	}
	
	@Override
	public List<AppChapuEntity> queryList(Map<String, Object> map){
		DynamicDataSource.setDataSource(DataSourceNames.SECOND);
		return appChapuDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return appChapuDao.queryTotal(map);
	}
	
	@Override
	public void save(AppChapuEntity appChapu){
		appChapuDao.save(appChapu);
	}
	
	@Override
	public void update(AppChapuEntity appChapu){
		appChapuDao.update(appChapu);
	}
	
	@Override
	public void delete(Integer chapuId){
		appChapuDao.delete(chapuId);
	}
	
	@Override
	public void deleteBatch(Integer[] chapuIds){
		appChapuDao.deleteBatch(chapuIds);
	}

	@Override
	public AppChapuEntity queryTeaSpectrumById(Integer id) {
		DynamicDataSource.setDataSource(DataSourceNames.SECOND);
		return this.appChapuDao.queryTeaSpectrumById(id);
	}

	@Override
	public QryTeaSpectrumDetailDTO queryTeaSpectrumDetailById(Integer chapuId) {
		QryTeaSpectrumDetailDTO teaSpectrumDetail = this.appChapuDao.queryTeaSpectrumDetailById(chapuId);
		if (ValidateUtils.isNotEmpty(teaSpectrumDetail)) {
			List<AppChapuParaEntity> teaSpectrumParams = this.appChapuParaService.queryList(ImmutableMap.of(Keys.CHAPU_ID, chapuId));
			teaSpectrumDetail.setParameter(this.convertEntities2DTOs(teaSpectrumParams));
			teaSpectrumDetail.setChapuImg(CommonUtils.plusFullImgPath(teaSpectrumDetail.getChapuImg()));
			UserVipEntity user = this.userVipService.queryUserByMemberId(teaSpectrumDetail.getUserId());
			if (ValidateUtils.isNotEmpty(user)) {
				this.doSetUserRelaAttrs(teaSpectrumDetail, user);
			}
			// 查询茶谱参数列表
			return teaSpectrumDetail;
		}
		return null;
	}

	/**
	 * 设置用户关联属性
	 * @param teaSpectrumDetail
	 * @param user
	 */
	private void doSetUserRelaAttrs(QryTeaSpectrumDetailDTO teaSpectrumDetail, UserVipEntity user) {
		teaSpectrumDetail.setPhoneNum(user.getPhone());
		teaSpectrumDetail.setAvatar(CommonUtils.plusFullImgPath(user.getVipHeadImg()));
		teaSpectrumDetail.setNickname(user.getVipNickname());
	}

	private List<QryTeaSpectrumParamDTO> convertEntities2DTOs(List<AppChapuParaEntity> teaSpectrumParams) {
		List<QryTeaSpectrumParamDTO> teaSpectrumParamDTOs = Lists.newArrayList();
		if (ValidateUtils.isNotEmptyCollection(teaSpectrumParams)) {
			teaSpectrumParams.forEach(tsp -> {
				QryTeaSpectrumParamDTO teaSpectrumParamDTO = new QryTeaSpectrumParamDTO();
				teaSpectrumParamDTO.setWater(200); // TODO 待确认 出水量如何取值
				teaSpectrumParamDTO.setTemp(tsp.getTemp());
				teaSpectrumParamDTO.setDura(tsp.getDura());
				teaSpectrumParamDTOs.add(teaSpectrumParamDTO);
			});
		}
		return teaSpectrumParamDTOs;
	}

	@Override
	public List<QryTeaSpectrumDetailDTO> queryTeaSpectrumListByCondition(CommonReq req) {
		QryTeaSpectrumReqDTO qryTeaSpectrumReq = JSONObject.parseObject(req.getBizBody(), QryTeaSpectrumReqDTO.class);
		Map<String, Object> params = this.buildQryParams(qryTeaSpectrumReq);
		List<QryTeaSpectrumDetailDTO> detailList = this.appChapuDao.queryTeaSpectrumListByCondition(new Query(params));
		this.setUserRelaAttrs(detailList, null);
		return detailList;
	}

	/**
	 * 根据关键字搜索茶谱
	 * @param req
	 * @return
	 */
	@Override
	public List<QryTeaSpectrumDetailDTO> searchTeaSpectrum(CommonReq req) {
		JSONObject jsonObj = JSONObject.parseObject(req.getBizBody());
		Map<String, Object> params = this.buildQryParams(jsonObj);
		// 如果关键字是手机号，则根据茶谱创建人手机号查询对应的茶谱
		if (RegexUtils.isMobile(jsonObj.getString("keyword"))) {
			DynamicDataSource.setDataSource(DataSourceNames.FIRST);
			UserVipEntity user = this.userVipService.getUserByMobile(jsonObj.getString("keyword"));
			if (ValidateUtils.isNotEmpty(user)) {
				params.put(Keys.USER_ID, user.getMemberId());
				params.remove("keyword");
			}
			DynamicDataSource.setDataSource(DataSourceNames.SECOND);
		}
		// TODO 待确认 关键字查询的范围
		List<QryTeaSpectrumDetailDTO> detailList = this.appChapuDao.queryTeaSpectrumListByCondition(new Query(params));
		this.setUserRelaAttrs(detailList, null);
		return detailList;
	}

	/**
	 * 查询我的茶谱
	 * @param req
	 * @return
	 */
	@Override
	public List<QryTeaSpectrumDetailDTO> queryMyTeaSpectrum(CommonReq req) {
		QryMyTeaSpectrumReqDTO qryMyTeaSpectrumReq = (QryMyTeaSpectrumReqDTO) req.getAttrByKey(Keys.REQ);
		DynamicDataSource.setDataSource(DataSourceNames.FIRST);
		UserVipEntity user = this.userVipService.getUserByMobile(qryMyTeaSpectrumReq.getPhoneNum());
		if (ValidateUtils.isNotEmpty(user)) {
			DynamicDataSource.setDataSource(DataSourceNames.SECOND);
			qryMyTeaSpectrumReq.setUserId(user.getMemberId());
			Map<String, Object> params = this.buildQryParams(qryMyTeaSpectrumReq);
			List<QryTeaSpectrumDetailDTO> detailList = this.appChapuDao.queryTeaSpectrumListByCondition(new Query(params));
			this.setUserRelaAttrs(detailList, user);
			return detailList;
		}
		return Collections.EMPTY_LIST;
	}

	private void setUserRelaAttrs(List<QryTeaSpectrumDetailDTO> detailList, UserVipEntity userParam) {
		if (ValidateUtils.isNotEmptyCollection(detailList)) {
			// 按 userId 分组
			Map<Long, List<QryTeaSpectrumDetailDTO>> groupMap = Maps.newHashMap();
            for (QryTeaSpectrumDetailDTO detailItem : detailList) {
                detailItem.setAvatar(CommonUtils.plusFullImgPath(detailItem.getAvatar()));
                detailItem.setChapuImg(CommonUtils.plusFullImgPath(detailItem.getChapuImg()));
                if (groupMap.containsKey(detailItem.getUserId())) {
                    groupMap.get(detailItem.getUserId()).add(detailItem);
                } else {
                    List<QryTeaSpectrumDetailDTO> values = new ArrayList<>();
                    values.add(detailItem);
                    groupMap.put(detailItem.getUserId(), values);
                }
            }
			List<Long> userIds = ValidateUtils.isEmpty(userParam) ? this.getUserIds(groupMap) : ImmutableList.of(userParam.getMemberId());
			final List<UserVipEntity> userList = ValidateUtils.isEmpty(userParam) ? this.userVipService.queryUserListByUserIds(userIds) : ImmutableList.of(userParam);
			if (ValidateUtils.isNotEmptyCollection(userList)) {
				groupMap.forEach((k, v) -> {
					final UserVipEntity user = this.getUserById(k, userList);
					if (ValidateUtils.isNotEmpty(user)) {
						v.forEach(detail -> {
							this.doSetUserRelaAttrs(detail, user);
						});
					}
				});
			}
		}
	}

    private List<Long> getUserIds(Map<Long, List<QryTeaSpectrumDetailDTO>> groupMap) {
		List<Long> userIds = Lists.newArrayList();
		groupMap.forEach((k, v) -> {
            userIds.add(k);
        });
		return userIds;
	}

	private UserVipEntity getUserById(Long userId, List<UserVipEntity> userList) {
		for (UserVipEntity user : userList) {
			if (ValidateUtils.equals(userId, user.getMemberId())) {
				return user;
			}
		}
		return null;
	}

	private Map<String,Object> buildQryParams(QryTeaSpectrumReqDTO qryTeaSpectrumReq) {
		Map<String, Object> params = Maps.newHashMap();
		params.put(Query.LIMIT, ValidateUtils.isEmpty(qryTeaSpectrumReq.getNum()) ? 10 : qryTeaSpectrumReq.getNum());
		params.put(Query.PAGE, ValidateUtils.isEmpty(qryTeaSpectrumReq.getPage()) ? 1 : qryTeaSpectrumReq.getPage());
		if (ValidateUtils.isNotEmpty(qryTeaSpectrumReq.getSortId()) && ValidateUtils.notEquals(TeaSpectrumConstant.QUERY_ALL, qryTeaSpectrumReq.getSortId())) {
			params.put("sortId", qryTeaSpectrumReq.getSortId());
		}
		if (ValidateUtils.isNotEmpty(qryTeaSpectrumReq.getStandard()) && ValidateUtils.notEquals(TeaSpectrumConstant.QUERY_ALL, qryTeaSpectrumReq.getStandard())) {
			if (ValidateUtils.notEquals(TeaSpectrumConstant.StandardFlag.STANDARD, qryTeaSpectrumReq.getStandard()) &&
					ValidateUtils.notEquals(TeaSpectrumConstant.StandardFlag.NORMAL, qryTeaSpectrumReq.getStandard())) {
				throw new CommonException("无效的标准茶谱标识参数，只能是[0、1、2]");
			}
			params.put("standard", qryTeaSpectrumReq.getStandard());
		}
		if (ValidateUtils.isNotEmpty(qryTeaSpectrumReq.getAuth()) && ValidateUtils.notEquals(TeaSpectrumConstant.QUERY_ALL, qryTeaSpectrumReq.getAuth())) {
			if (ValidateUtils.notEquals(TeaSpectrumConstant.AuthFlag.YES, qryTeaSpectrumReq.getAuth()) &&
					ValidateUtils.notEquals(TeaSpectrumConstant.AuthFlag.NO, qryTeaSpectrumReq.getAuth())) {
				throw new CommonException("无效的鉴定标识参数，只能是[0、1、2]");
			}
			params.put("auth", qryTeaSpectrumReq.getAuth());
		}
		if (ValidateUtils.notEquals(TeaSpectrumConstant.OrderByFlag.NONE, qryTeaSpectrumReq.getOrder())) {
			if (ValidateUtils.equals(TeaSpectrumConstant.OrderByFlag.MAKE_TEA_TIMES, qryTeaSpectrumReq.getOrder())) {
				params.put(Query.SIDX, "make_times");
				params.put(Query.ORDER, "desc");
			} else if (ValidateUtils.equals(TeaSpectrumConstant.OrderByFlag.PUBLISH_TIME, qryTeaSpectrumReq.getOrder())) {
				params.put(Query.SIDX, "public_time");
				params.put(Query.ORDER, "desc");
			} else {
				throw new CommonException("无效的排序标识参数，只能是[0、1、2]");
			}
		}
		return params;
	}

	private Map<String,Object> buildQryParams(JSONObject jsonObj) {
		if (ValidateUtils.isEmptyString(jsonObj.getString("keyword"))) {
			throw new CommonException("关键字为空");
		}
		Map<String, Object> params = Maps.newHashMap();
		params.put(Query.LIMIT, ValidateUtils.isEmpty(jsonObj.get(Query.NUM)) ? 10 : jsonObj.get(Query.NUM));
		params.put(Query.PAGE, ValidateUtils.isEmpty(jsonObj.get(Query.PAGE)) ? 1 : jsonObj.get(Query.PAGE));
		params.put("keyword", jsonObj.getString("keyword"));
		return params;
	}

	private Map<String,Object> buildQryParams(QryMyTeaSpectrumReqDTO qryMyTeaSpectrumReq) {
		Map<String, Object> params = Maps.newHashMap();
		params.put(Query.LIMIT, ValidateUtils.isEmpty(qryMyTeaSpectrumReq.getNum()) ? 10 : qryMyTeaSpectrumReq.getNum());
		params.put(Query.PAGE, ValidateUtils.isEmpty(qryMyTeaSpectrumReq.getPage()) ? 1 : qryMyTeaSpectrumReq.getPage());
		params.put(Keys.USER_ID, qryMyTeaSpectrumReq.getUserId());
		params.put("qryMyChapuFlag", true);
		if (ValidateUtils.isNotEmpty(qryMyTeaSpectrumReq.getType())) {
			List<Integer> types = Lists.newArrayList();
			if (ValidateUtils.equals(TeaSpectrumConstant.MyChapuType.CREATED, qryMyTeaSpectrumReq.getType())) {
				types.add(TeaSpectrumConstant.MyChapuFlag.CREATED);
			} else if (ValidateUtils.equals(TeaSpectrumConstant.MyChapuType.SAVED, qryMyTeaSpectrumReq.getType())) {
				// TODO 待确认 是否要添加多个类型
				types.add(TeaSpectrumConstant.MyChapuFlag.FAVORITE);
				types.add(TeaSpectrumConstant.MyChapuFlag.EDITED);
				types.add(TeaSpectrumConstant.MyChapuFlag.LIKED);
				types.add(TeaSpectrumConstant.MyChapuFlag.COMMENTED);
			} else if (ValidateUtils.equals(TeaSpectrumConstant.MyChapuType.USED, qryMyTeaSpectrumReq.getType())) {
				types.add(TeaSpectrumConstant.MyChapuFlag.USED);
			}
			params.put("types", types);
		}
		return params;
	}

	@Override
	public CommonResp delOrFinishTeaSpectrum(CommonReq req) {
		/*
		 删除我的茶谱、结束茶谱泡茶调用接口服务程序4.1.3.2，
		 如果是删除我的茶谱，根据手机号码获取用户id，根据茶谱id和用户id信息删除4.5.12我的茶谱表中用户和茶谱的绑定关系；
 		 如果是结束泡茶，根据手机号码获取用户id，根据用户id获取用户绑定的所有设备id，更新4.5.5沏茶器运行状态信息表中，
 		 对应当前用户的所有设备，并且茶谱id为需要结束的茶谱的记录，将index字段设置为999；
		 操作信息统一入4.5.6沏茶器操作日志表，该日志表保存近10天的操作日志记录。
		*/
		DelOrFinishTeaSpectrumReqDTO delOrFinishTeaSpectrumReq = (DelOrFinishTeaSpectrumReqDTO) req.getAttrByKey(Keys.REQ);
		UserVipEntity user = (UserVipEntity) req.getAttrByKey(Keys.USER_INFO);
		if (ValidateUtils.equals(TeaSpectrumConstant.MyChapuOperFlag.DELETE, delOrFinishTeaSpectrumReq.getOperFlag())) {
			// 删除我的茶谱
			Map<String, Object> params = ImmutableMap.of(Keys.CHAPU_ID, delOrFinishTeaSpectrumReq.getChapuId(), Keys.USER_ID, user.getMemberId());
			this.appChapuMineService.deleteByCondition(params);
		} else {
			// 结束我的茶谱
			this.doFinishTeaSpectrum(user.getMemberId(), delOrFinishTeaSpectrumReq.getChapuId());
		}
		return CommonResp.ok();
	}

	private void doFinishTeaSpectrum(Long memberId, Integer chapuId) {
		DynamicDataSource.setDataSource(DataSourceNames.FIRST);
		List<UserDeviceEntity> userDeviceRelas = this.userDeviceService.queryList(ImmutableMap.of(Keys.TEAMAN_ID, memberId));
		if (ValidateUtils.isNotEmptyCollection(userDeviceRelas)) {
			List<Integer> deviceIds = userDeviceRelas.stream().map(item -> item.getDeviceId()).collect(Collectors.toList());
			Map<String, Object> params = ImmutableMap.of(Keys.CHAPU_ID, chapuId, "deviceIds", deviceIds);
			this.deviceStateInfoService.setDevChapu2Finish(params);
		}
	}
}
