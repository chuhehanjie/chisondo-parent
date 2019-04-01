package com.chisondo.server.modules.device.validator;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.utils.Keys;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.common.validator.BusiValidator;

public class QueryParamValidator implements BusiValidator {
    @Override
    public void validate(CommonReq req) {
        JSONObject jsonObj = JSONObject.parseObject(req.getBizBody());
        String deviceId = jsonObj.getString("deviceId");
        if (ValidateUtils.isEmptyString(deviceId)) {
            throw new CommonException("设备ID为空");
        }
        String phoneNum = jsonObj.getString(Keys.PHONE_NUM);
        if (ValidateUtils.isEmptyString(phoneNum)) {
            throw new CommonException("用户号码为空");
        }
        req.addAttr(Keys.DEVICE_ID, deviceId);
        req.addAttr(Keys.PHONE_NUM, phoneNum);
    }
}
