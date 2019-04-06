package com.chisondo.server.modules.tea.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.utils.Keys;
import com.chisondo.server.common.utils.Query;
import com.chisondo.server.common.utils.RegexUtils;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.datasources.DataSourceNames;
import com.chisondo.server.datasources.DynamicDataSource;
import com.chisondo.server.modules.tea.constant.TeaSpectrumConstant;
import com.chisondo.server.modules.tea.dto.QryMyTeaSpectrumReqDTO;
import com.chisondo.server.modules.tea.dto.QryTeaSpectrumDetailDTO;
import com.chisondo.server.modules.tea.dto.QryTeaSpectrumParamDTO;
import com.chisondo.server.modules.tea.dto.QryTeaSpectrumReqDTO;
import com.chisondo.server.modules.tea.entity.AppChapuParaEntity;
import com.chisondo.server.modules.tea.service.AppChapuParaService;
import com.chisondo.server.modules.user.entity.UserVipEntity;
import com.chisondo.server.modules.user.service.UserVipService;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		teaSpectrumDetail.setAvatar(user.getVipHeadImg());
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
				params.put("userId", user.getMemberId());
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
			Map<Long, List<QryTeaSpectrumDetailDTO>> groupMap = detailList.stream().collect(Collectors.groupingBy(QryTeaSpectrumDetailDTO::getUserId));
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
		params.put("userId", qryMyTeaSpectrumReq.getUserId());
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
}
