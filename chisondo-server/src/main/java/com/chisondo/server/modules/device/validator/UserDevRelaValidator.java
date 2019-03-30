package com.chisondo.server.modules.device.validator;

import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.utils.Keys;
import com.chisondo.server.common.utils.ParamValidatorUtils;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.common.validator.BusiValidator;
import com.chisondo.server.modules.user.entity.UserDeviceEntity;
import com.chisondo.server.modules.user.entity.UserVipEntity;
import com.chisondo.server.modules.user.service.UserDeviceService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户与设备关联校验器
 */
@Component("userDevRelaValidator")
public class UserDevRelaValidator implements BusiValidator {

    @Autowired
    private UserDeviceService userDeviceService;

    @Override
    public void validate(CommonReq req) {
        ParamValidatorUtils.validateByBeanId("devExistenceValidator", req);
        ParamValidatorUtils.validateByBeanId("userExistenceValidator", req);
        UserVipEntity user = (UserVipEntity) req.getAttrByKey(Keys.USER_INFO);
        String deviceId = (String) req.getAttrByKey(Keys.DEVICE_ID);
        List<UserDeviceEntity> userDeviceRela = this.userDeviceService.queryList(ImmutableMap.of(Keys.DEVICE_ID, deviceId, Keys.TEAMAN_ID, user.getMemberId()));
        if (ValidateUtils.isEmptyCollection(userDeviceRela)) {
            throw new CommonException("用户与设备未绑定");
        }
    }
}
