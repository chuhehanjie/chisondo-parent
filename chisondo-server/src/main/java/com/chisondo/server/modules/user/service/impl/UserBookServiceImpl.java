package com.chisondo.server.modules.user.service.impl;

import com.chisondo.model.http.req.DeviceHttpReq;
import com.chisondo.model.http.resp.DeviceHttpResp;
import com.chisondo.server.common.http.CommonResp;
import com.chisondo.server.modules.http2dev.service.DeviceHttpService;
import com.chisondo.server.modules.user.entity.UserMakeTeaEntity;
import com.chisondo.server.modules.user.service.UserMakeTeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.chisondo.server.modules.user.dao.UserBookDao;
import com.chisondo.server.modules.user.entity.UserBookEntity;
import com.chisondo.server.modules.user.service.UserBookService;



@Service("userBookService")
public class UserBookServiceImpl implements UserBookService {
	@Autowired
	private UserBookDao userBookDao;

	@Override
	public UserBookEntity queryObject(Integer id){
		return userBookDao.queryObject(id);
	}
	
	@Override
	public List<UserBookEntity> queryList(Map<String, Object> map){
		return userBookDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return userBookDao.queryTotal(map);
	}
	
	@Override
	public void save(UserBookEntity userBook){
		userBookDao.save(userBook);
	}
	
	@Override
	public void update(UserBookEntity userBook){
		userBookDao.update(userBook);
	}
	
	@Override
	public void delete(Integer id){
		userBookDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		userBookDao.deleteBatch(ids);
	}

}
