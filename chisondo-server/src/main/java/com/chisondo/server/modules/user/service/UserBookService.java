package com.chisondo.server.modules.user.service;

import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.modules.user.entity.UserBookEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 12.19
 */
public interface UserBookService {
	
	UserBookEntity queryObject(Integer id);
	
	List<UserBookEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(UserBookEntity userBook);
	
	void update(UserBookEntity userBook);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);

    Map<String,Object> queryUserReservation(CommonReq req);
}
