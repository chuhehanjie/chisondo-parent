package com.chisondo.server.modules.tea.service;

import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.http.CommonResp;
import com.chisondo.server.modules.tea.dto.QryTeaSpectrumDetailDTO;
import com.chisondo.server.modules.tea.entity.AppChapuEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 17.19
 */
public interface AppChapuService {
	
	AppChapuEntity queryObject(Integer chapuId);
	
	List<AppChapuEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(AppChapuEntity appChapu);
	
	void update(AppChapuEntity appChapu);
	
	void delete(Integer chapuId);
	
	void deleteBatch(Integer[] chapuIds);

	AppChapuEntity queryTeaSpectrumById(Integer id);

    QryTeaSpectrumDetailDTO queryTeaSpectrumDetailById(Integer chapuId);

	List<QryTeaSpectrumDetailDTO> queryTeaSpectrumListByCondition(CommonReq req);

    List<QryTeaSpectrumDetailDTO> searchTeaSpectrum(CommonReq req);

    List<QryTeaSpectrumDetailDTO> queryMyTeaSpectrum(CommonReq req);

    CommonResp delOrFinishTeaSpectrum(CommonReq req);
}
