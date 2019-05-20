package com.chisondo.server.modules.tea.service.impl;

import com.chisondo.server.modules.tea.dao.AppTeaSortDao;
import com.chisondo.server.modules.tea.dto.TeaSortRowDTO;
import com.chisondo.server.modules.tea.entity.AppTeaSortEntity;
import com.chisondo.server.modules.tea.service.AppTeaSortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



@Service("appTeaSortService")
public class AppTeaSortServiceImpl implements AppTeaSortService {
	@Autowired
	private AppTeaSortDao appTeaSortDao;

	@Override
	public List<TeaSortRowDTO> queryAllTeaSorts() {
		return this.queryAllTeaSortsByLocal();
	}


	public List<TeaSortRowDTO> queryAllTeaSortsByLocal() {
		return this.appTeaSortDao.queryAllTeaSorts();
	}

	@Override
	public AppTeaSortEntity queryObject(Integer sortId){
		return appTeaSortDao.queryObject(sortId);
	}

	@Override
	public List<AppTeaSortEntity> queryList(Map<String, Object> map){
		return appTeaSortDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return appTeaSortDao.queryTotal(map);
	}

	@Override
	public void save(AppTeaSortEntity appTeaSort){
		appTeaSortDao.save(appTeaSort);
	}

	@Override
	public void update(AppTeaSortEntity appTeaSort){
		appTeaSortDao.update(appTeaSort);
	}

	@Override
	public void delete(Integer sortId){
		appTeaSortDao.delete(sortId);
	}

	@Override
	public void deleteBatch(Integer[] sortIds){
		appTeaSortDao.deleteBatch(sortIds);
	}
	
}
