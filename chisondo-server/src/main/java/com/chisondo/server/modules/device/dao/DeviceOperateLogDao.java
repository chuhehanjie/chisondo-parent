package com.chisondo.server.modules.device.dao;

import com.chisondo.server.modules.device.entity.DeviceOperateLogEntity;
import com.chisondo.server.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

/**
 * 设备操作日志表
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 17.19
 */
@Mapper
public interface DeviceOperateLogDao extends BaseDao<DeviceOperateLogEntity> {
	
}
