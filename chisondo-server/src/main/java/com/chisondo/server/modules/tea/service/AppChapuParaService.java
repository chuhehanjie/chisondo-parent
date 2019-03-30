package com.chisondo.server.modules.tea.service;

import com.chisondo.server.modules.tea.entity.AppChapuParaEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 17.19
 */
public interface AppChapuParaService {
	
	AppChapuParaEntity queryObject(Integer chapuId);
	
	List<AppChapuParaEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(AppChapuParaEntity appChapuPara);
	
	void update(AppChapuParaEntity appChapuPara);
	
	void delete(Integer chapuId);
	
	void deleteBatch(Integer[] chapuIds);
}
