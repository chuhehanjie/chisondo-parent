package com.chisondo.server.modules.tea.service;

import com.chisondo.server.modules.tea.entity.TeamanUserEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 17.19
 */
public interface TeamanUserService {
	
	TeamanUserEntity queryObject(Integer id);
	
	List<TeamanUserEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TeamanUserEntity teamanUser);
	
	void update(TeamanUserEntity teamanUser);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
