package com.chisondo.server.modules.device.validator;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.utils.CommonUtils;
import com.chisondo.server.common.utils.Keys;
import com.chisondo.server.common.utils.ValidateUtils;
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

    @Override
    public void validate(CommonReq req) {
        JSONObject jsonObj = JSONObject.parseObject(req.getBizBody());
        String deviceId = jsonObj.getString("deviceId");
        ActivedDeviceInfoEntity deviceInfo = CommonUtils.isOldDevice(deviceId) ? this.deviceInfoService.getDeviceInfoById(deviceId) :
                this.deviceInfoService.getNewDeviceByNewDevId(deviceId);
        if (ValidateUtils.isEmpty(deviceInfo)) {
            throw new CommonException("设备信息不存在");
        }
        req.addAttr(Keys.DEVICE_INFO, deviceInfo);
        req.addAttr(Keys.DEVICE_ID, deviceInfo.getDeviceId());
        req.addAttr(Keys.NEW_DEVICE_ID, deviceInfo.getNewDeviceId());
    }
}
