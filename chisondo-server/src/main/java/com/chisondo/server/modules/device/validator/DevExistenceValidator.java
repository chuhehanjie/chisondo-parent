package com.chisondo.server.modules.device.validator;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.utils.Keys;
import com.chisondo.server.common.utils.SpringContextUtils;
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
        /*if (ValidateUtils.isEmptyString(deviceId)) {
            throw new CommonException("设备ID为空");
        }*/
        ActivedDeviceInfoEntity deviceInfo = this.deviceInfoService.getDeviceInfoById(deviceId);
        if (ValidateUtils.isEmpty(deviceInfo)) {
            throw new CommonException("设备信息不存在");
        }
        req.addAttr("deviceInfo", deviceInfo);
        req.addAttr(Keys.DEVICE_ID, deviceId);
    }
}
