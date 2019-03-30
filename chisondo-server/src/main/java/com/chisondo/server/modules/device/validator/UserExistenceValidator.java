package com.chisondo.server.modules.device.validator;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.utils.Keys;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.common.validator.BusiValidator;
import com.chisondo.server.modules.device.entity.ActivedDeviceInfoEntity;
import com.chisondo.server.modules.device.service.ActivedDeviceInfoService;
import com.chisondo.server.modules.user.entity.UserVipEntity;
import com.chisondo.server.modules.user.service.UserVipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * 校验用户是否存在
 */
@Component("userExistenceValidator")
public class UserExistenceValidator implements BusiValidator {

    @Autowired
    private UserVipService userVipService;

    @Override
    public void validate(CommonReq req) {
        JSONObject jsonObj = JSONObject.parseObject(req.getBizBody());
        String phoneNum = jsonObj.getString(Keys.PHONE_NUM);
        if (ValidateUtils.isEmptyString(phoneNum)) {
            throw new CommonException("用户号码为空");
        }
        UserVipEntity user = this.userVipService.getUserByMobile(phoneNum);
        if (ValidateUtils.isEmpty(user)) {
            throw new CommonException("用户不存在");
        }
        req.addAttr(Keys.USER_INFO, user);
    }
}
