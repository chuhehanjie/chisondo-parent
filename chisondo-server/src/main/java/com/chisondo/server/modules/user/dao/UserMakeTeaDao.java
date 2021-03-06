package com.chisondo.server.modules.user.dao;

import com.chisondo.server.modules.device.dto.resp.MakeTeaRowRespDTO;
import com.chisondo.server.modules.user.entity.UserMakeTeaEntity;
import com.chisondo.server.modules.sys.dao.BaseDao;
import com.google.common.collect.ImmutableMap;
import org.apache.ibatis.annotations.Mapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 12.19
 */
@Mapper
public interface UserMakeTeaDao extends BaseDao<UserMakeTeaEntity> {

    int countMakeTeaRecordsByDeviceId(String deviceId);

    List<MakeTeaRowRespDTO> queryMakeTeaRecordsByDeviceId(Map<String, Object> params);

    int countMakeTeaRecordsByUserMobile(String userMobile);

    List<MakeTeaRowRespDTO> queryMakeTeaRecordsByUserMobile(Map<String, Object> params);


    void updateStatus(Map<String, Object> params);
}
