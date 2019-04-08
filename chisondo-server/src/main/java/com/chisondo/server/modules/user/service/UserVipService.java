package com.chisondo.server.modules.user.service;

import com.chisondo.server.modules.user.entity.UserVipEntity;

import java.util.List;
import java.util.Map;

/**
 * 会员信息
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 17.19
 */
public interface UserVipService {
	
	UserVipEntity queryObject(Long memberId);
	
	List<UserVipEntity> queryList(Map<String, Object> map);

    UserVipEntity getUserByMobile(String mobile);

    int queryTotal(Map<String, Object> map);
	
	void save(UserVipEntity dataVip);
	
	void update(UserVipEntity dataVip);
	
	void delete(Long memberId);
	
	void deleteBatch(Long[] memberIds);

    UserVipEntity queryUserByMemberId(Long userId);

    List<UserVipEntity> queryUserListByUserIds(List<Long> userIds);

    Map<String,Object> queryAllUsersOfDevice(String string);
}
