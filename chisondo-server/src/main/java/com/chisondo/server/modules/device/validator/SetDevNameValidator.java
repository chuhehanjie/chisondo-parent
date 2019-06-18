package com.chisondo.server.modules.device.validator;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.utils.Keys;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.common.validator.BusiValidator;
import com.chisondo.server.modules.device.dto.req.SetDevNameReqDTO;
import org.springframework.stereotype.Component;

/**
 * 设置设备名称或描述校验器
 */
@Component
public class SetDevNameValidator implements BusiValidator {

    @Override
    public void validate(CommonReq req) {
        SetDevNameReqDTO setDevNameReq = JSONObject.parseObject(req.getBizBody(), SetDevNameReqDTO.class);
        if (ValidateUtils.isEmptyString(setDevNameReq.getDeviceName())) {
            throw new CommonException("设备名称为空");
        }
        setDevNameReq.setDeviceId((String) req.getAttrByKey(Keys.DEVICE_ID));
        req.addAttr("setDevNameReq", setDevNameReq);
    }
}
