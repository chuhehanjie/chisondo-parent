package com.chisondo.server.modules.device.validator;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.utils.Keys;
import com.chisondo.server.common.utils.ParamValidatorUtils;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.common.validator.BusiValidator;
import com.chisondo.server.modules.device.dto.req.SetDevPwdReqDTO;
import com.chisondo.server.modules.device.entity.ActivedDeviceInfoEntity;
import com.chisondo.server.modules.user.entity.UserDeviceEntity;
import com.chisondo.server.modules.user.entity.UserVipEntity;
import com.chisondo.server.modules.user.service.UserDeviceService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 设置设备密码校验器
 */
@Component
public class SetDevPwdValidator implements BusiValidator {

    @Override
    public void validate(CommonReq req) {
        ParamValidatorUtils.validateByBeanId("userDevRelaValidator", req);
        SetDevPwdReqDTO setDevPwdReq = JSONObject.parseObject(req.getBizBody(), SetDevPwdReqDTO.class);
        if (ValidateUtils.isEmptyString(setDevPwdReq.getNewPwd())) {
            throw new CommonException("新密码为空");
        }
        // 旧密码不为空则需要核对旧密码
        ActivedDeviceInfoEntity devInfo = (ActivedDeviceInfoEntity) req.getAttrByKey(Keys.DEVICE_INFO);
        if (ValidateUtils.isNotEmptyString(devInfo.getPassword()) && ValidateUtils.notEquals(devInfo.getPassword(), setDevPwdReq.getOldPwd())) {
            throw new CommonException("旧密码不正确");
        }
        setDevPwdReq.setDeviceId(devInfo.getDeviceId());
        req.addAttr("setDevPwdReq", setDevPwdReq);
    }
}
