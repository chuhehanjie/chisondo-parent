package com.chisondo.server.modules.tea.service.impl;

import com.chisondo.server.common.utils.Keys;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.datasources.DataSourceNames;
import com.chisondo.server.datasources.DynamicDataSource;
import com.chisondo.server.modules.tea.dto.QryTeaSpectrumDetailDTO;
import com.chisondo.server.modules.tea.dto.QryTeaSpectrumParamDTO;
import com.chisondo.server.modules.tea.entity.AppChapuParaEntity;
import com.chisondo.server.modules.tea.service.AppChapuParaService;
import com.chisondo.server.modules.user.entity.UserVipEntity;
import com.chisondo.server.modules.user.service.UserVipService;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
				teaSpectrumDetail.setPhoneNum(user.getPhone());
				teaSpectrumDetail.setAvatar(user.getVipHeadImg());
				teaSpectrumDetail.setNickname(user.getVipNickname());
			}
			// 查询茶谱参数列表
			return teaSpectrumDetail;
		}
		return null;
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
}
