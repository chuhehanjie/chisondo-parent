package com.chisondo.server.modules.user.dao;

import com.chisondo.server.modules.sys.dao.BaseDao;
import com.chisondo.server.modules.user.entity.UserVipEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 会员信息
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 17.19
 */
@Mapper
public interface UserVipDao extends BaseDao<UserVipEntity> {

    List<UserVipEntity> queryUserListByUserIds(List<Long> userIds);

    List<Map<String, Object>> queryAllUsersOfDevice(String deviceId);
}
