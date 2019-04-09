package com.chisondo.server.modules.user.dao;

import com.chisondo.server.modules.user.entity.UserDeviceEntity;
import com.chisondo.server.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 12.19
 */
@Mapper
public interface UserDeviceDao extends BaseDao<UserDeviceEntity> {

    void delUserDeviceByParams(Map<String, Object> params);

    void setDefaultDevice(Map<String, Object> params);

    void setNoneDefaultDev(String userId);
}
