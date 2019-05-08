package com.chisondo.server.modules.device.validator;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.utils.Constant;
import com.chisondo.server.common.utils.Keys;
import com.chisondo.server.common.utils.ParamValidatorUtils;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.common.validator.BusiValidator;
import com.chisondo.server.modules.device.dto.req.DeviceBindReqDTO;
import com.chisondo.server.modules.device.entity.ActivedDeviceInfoEntity;
import com.chisondo.server.modules.device.service.ActivedDeviceInfoService;
import com.chisondo.server.modules.device.service.DeviceStateInfoService;
import com.chisondo.server.modules.user.service.UserDeviceService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 绑定设备校验器
 */
@Component("bindDeviceValidator")
public class BindDeviceValidator implements BusiValidator {

    @Autowired
    private UserDeviceService userDeviceService;

    @Autowired
    private DeviceStateInfoService deviceStateInfoService;

    @Override
    public void validate(CommonReq req) {
        DeviceBindReqDTO devBindReq = JSONObject.parseObject(req.getBizBody(), DeviceBindReqDTO.class);
        this.validate(devBindReq);

        ParamValidatorUtils.validateByBeanId("devExistenceValidator", req);

        ActivedDeviceInfoEntity deviceInfo = (ActivedDeviceInfoEntity) req.getAttrByKey(Keys.DEVICE_INFO);
        if (ValidateUtils.notEquals(deviceInfo.getPassword(), devBindReq.getPasswd())) {
            throw new CommonException("设备密码错误");
        }
        // 校验设备是否已经被占用(即状态为连接中)
        if (this.isDeviceConnected(devBindReq.getDeviceId())) {
            throw new CommonException("设备在连接中");
        }

        // 校验设备是否有其他用户关联，且只允许关联一个用户 privateTag = 1
        if (this.isDeviceNotAllowConnect(devBindReq.getDeviceId())) {
            throw new CommonException("设备已被占用");
        }
        devBindReq.setDeviceId(deviceInfo.getDeviceId());
        req.addAttr(Keys.REQ, devBindReq);
    }

    private void validate(DeviceBindReqDTO devBindReq) {
        if (ValidateUtils.isEmptyString(devBindReq.getDeviceId())) {
            throw new CommonException("设备ID为空");
        }
        if (ValidateUtils.isEmptyString(devBindReq.getPhoneNum())) {
            throw new CommonException("用户手机号为空");
        }
        if (ValidateUtils.isEmpty(devBindReq.getCompanyId())) {
            throw new CommonException("公司ID为空");
        }
        if (ValidateUtils.isEmptyString(devBindReq.getLongitude())) {
            throw new CommonException("经度为空");
        }
        if (ValidateUtils.isEmptyString(devBindReq.getLatitude())) {
            throw new CommonException("纬度为空");
        }
        if (ValidateUtils.isEmptyString(devBindReq.getProvince())) {
            throw new CommonException("省为空");
        }
        if (ValidateUtils.isEmptyString(devBindReq.getCity())) {
            throw new CommonException("市为空");
        }
        if (ValidateUtils.isEmptyString(devBindReq.getDistrict())) {
            throw new CommonException("区为空");
        }
        if (ValidateUtils.isEmptyString(devBindReq.getDetaddress())) {
            throw new CommonException("详细地址为空");
        }
        if (ValidateUtils.isEmptyString(devBindReq.getPasswd())) {
            throw new CommonException("密码为空");
        }
    }

    private boolean isDeviceNotAllowConnect(String deviceId) {
        if (ValidateUtils.isNotEmptyCollection(this.userDeviceService.queryList(ImmutableMap.of(Keys.DEVICE_ID, deviceId,Keys.PRIVATE_TAG, Constant.DevPrivateTag.YES)))) {
            return true;
        }
        return false;
    }

    private boolean isDeviceConnected(String deviceId) {
        if (ValidateUtils.isNotEmptyCollection(this.deviceStateInfoService.queryList(
                ImmutableMap.of(Keys.DEVICE_ID, deviceId, Keys.CONNECT_STATE, Constant.ConnectState.CONNECTED)))) {
            return true;
        }
        return false;
    }

}
