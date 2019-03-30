package com.chisondo.server.modules.user.service;

import com.chisondo.server.modules.user.entity.TeamanAdminUserEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 12.19
 */
public interface TeamanAdminUserService {
	
	TeamanAdminUserEntity queryObject(Integer id);
	
	List<TeamanAdminUserEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TeamanAdminUserEntity teamanAdminUser);
	
	void update(TeamanAdminUserEntity teamanAdminUser);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
