package com.chisondo.server.modules.user.service;

import com.chisondo.server.modules.user.entity.UserSchemeEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 12.19
 */
public interface UserSchemeService {
	
	UserSchemeEntity queryObject(Integer schemeId);
	
	List<UserSchemeEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(UserSchemeEntity userScheme);
	
	void update(UserSchemeEntity userScheme);
	
	void delete(Integer schemeId);
	
	void deleteBatch(Integer[] schemeIds);
}
