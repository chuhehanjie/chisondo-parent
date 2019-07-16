package com.chisondo.server.modules.device.dao;

import com.chisondo.server.modules.device.entity.DeviceStateInfoEntity;
import com.chisondo.server.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 17.19
 */
@Mapper
public interface DeviceStateInfoDao extends BaseDao<DeviceStateInfoEntity> {

    void setDevChapu2Finish(Map<String, Object> params);

    void updateConnectState(DeviceStateInfoEntity deviceState);
}
