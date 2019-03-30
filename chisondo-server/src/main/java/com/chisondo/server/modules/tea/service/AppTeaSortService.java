package com.chisondo.server.modules.tea.service;

import com.chisondo.server.datasources.DataSourceNames;
import com.chisondo.server.datasources.annotation.DataSource;
import com.chisondo.server.modules.tea.dto.TeaSortRowDTO;
import com.chisondo.server.modules.tea.entity.AppTeaSortEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 17.19
 */
public interface AppTeaSortService {

    List<TeaSortRowDTO> queryAllTeaSorts();

    AppTeaSortEntity queryObject(Integer sortId);
	
	List<AppTeaSortEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(AppTeaSortEntity appTeaSort);
	
	void update(AppTeaSortEntity appTeaSort);
	
	void delete(Integer sortId);
	
	void deleteBatch(Integer[] sortIds);
}
