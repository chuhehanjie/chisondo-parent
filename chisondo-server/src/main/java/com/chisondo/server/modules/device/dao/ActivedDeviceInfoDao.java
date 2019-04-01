package com.chisondo.server.modules.device.dao;

import com.chisondo.server.modules.device.dto.req.SetDevNameReqDTO;
import com.chisondo.server.modules.device.dto.req.SetDevPwdReqDTO;
import com.chisondo.server.modules.device.dto.req.SetDevSoundReqDTO;
import com.chisondo.server.modules.device.dto.resp.DeviceInfoRespDTO;
import com.chisondo.server.modules.device.entity.ActivedDeviceInfoEntity;
import com.chisondo.server.modules.sys.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ding.zhong
 * @email 258321511@qq.com
 * @since Mar 17.19
 */
@Mapper
public interface ActivedDeviceInfoDao extends BaseDao<ActivedDeviceInfoEntity> {

    List<DeviceInfoRespDTO> queryHisConnectDevOfUserByPhone(String userMobile);

    void updateDevPwd(SetDevPwdReqDTO setDevPwdReq);

    void updateDevNameOrDesc(SetDevNameReqDTO setDevNameReq);

    void updateDevSound(SetDevSoundReqDTO setDevSoundReq);

    List<DeviceInfoRespDTO> queryDeviceDetail(Map<String, Object> params);
}
