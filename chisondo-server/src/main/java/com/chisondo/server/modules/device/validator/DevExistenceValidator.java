package com.chisondo.server.modules.device.validator;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.utils.*;
import com.chisondo.server.common.validator.BusiValidator;
import com.chisondo.server.modules.device.entity.ActivedDeviceInfoEntity;
import com.chisondo.server.modules.device.service.ActivedDeviceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 校验设备是否存在
 */
@Component("devExistenceValidator")
public class DevExistenceValidator implements BusiValidator {

    @Autowired
    private ActivedDeviceInfoService deviceInfoService;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public void validate(CommonReq req) {
        JSONObject jsonObj = JSONObject.parseObject(req.getBizBody());
        String deviceId = jsonObj.getString("deviceId");
        ActivedDeviceInfoEntity deviceInfo = null;
        if (CommonUtils.isOldDevice(deviceId)) {
            deviceInfo = this.deviceInfoService.getDeviceInfoById(deviceId);
        } else {
            deviceInfo = this.redisUtils.get(Keys.PREFIX_NEW_DEVICE + deviceId, ActivedDeviceInfoEntity.class);
            if (ValidateUtils.isEmpty(deviceInfo)) {
                deviceInfo = this.deviceInfoService.getNewDeviceByNewDevId(deviceId);
                if (ValidateUtils.isNotEmpty(deviceInfo)) {
                    // 将设备信息放入 redis 缓存 100 秒
                    this.redisUtils.set(Keys.PREFIX_NEW_DEVICE + deviceId, deviceInfo, 500);
                }
            }
        }
        if (ValidateUtils.isEmpty(deviceInfo)) {
            throw new CommonException("设备信息不存在");
        }
        req.addAttr(Keys.DEVICE_INFO, deviceInfo);
        req.addAttr(Keys.DEVICE_ID, deviceInfo.getDeviceId());
        req.addAttr(Keys.NEW_DEVICE_ID, deviceInfo.getNewDeviceId());
    }
}
