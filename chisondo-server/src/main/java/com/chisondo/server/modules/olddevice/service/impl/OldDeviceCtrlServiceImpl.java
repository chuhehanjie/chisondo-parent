package com.chisondo.server.modules.olddevice.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.utils.Constant;
import com.chisondo.server.common.utils.Keys;
import com.chisondo.server.common.utils.ParamValidatorUtils;
import com.chisondo.server.common.utils.RestTemplateUtils;
import com.chisondo.server.modules.device.dto.req.DeviceCtrlReqDTO;
import com.chisondo.server.modules.device.dto.req.WashTeaReqDTO;
import com.chisondo.server.modules.device.entity.ActivedDeviceInfoEntity;
import com.chisondo.server.modules.olddevice.req.ConnectDevReq;
import com.chisondo.server.modules.olddevice.req.MakeTeaReq;
import com.chisondo.server.modules.olddevice.resp.ConnectDevResp;
import com.chisondo.server.modules.olddevice.service.OldDeviceCtrlService;
import com.chisondo.server.modules.user.entity.UserVipEntity;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("oldDeviceCtrlService")
@Slf4j
public class OldDeviceCtrlServiceImpl implements OldDeviceCtrlService {

    @Autowired
    private RestTemplateUtils restTemplateUtils;

    @Value("chisondo.server.oldDevReqURL")
    private String oldDevReqURL;

    @Override
    public JSONObject service(CommonReq req, int operationType) {
        this.validate(req);
        ConnectDevResp connectDevResp = this.connectDevice(req);
        JSONObject result = this.dispatch(connectDevResp.getSessionId(), req, operationType);
        this.disconnectDevice(connectDevResp.getSessionId());
        return result;
    }

    private void disconnectDevice(String sessionId) {
        JSONObject result = this.restTemplateUtils.httpPostMediaTypeJson(this.oldDevReqURL + "disconnectDevice", JSONObject.class, ImmutableMap.of(Keys.SESSION_ID, sessionId));
        log.info("disconnectDevice result = {}", result);
    }

    private void validate(CommonReq req) {
        ParamValidatorUtils.validateByBeanId("devExistenceValidator", req);
        ParamValidatorUtils.validateByBeanId("userExistenceValidator", req);
    }

    private ConnectDevResp connectDevice(CommonReq req) {
        ConnectDevReq connectDevReq = this.buildConnectDevReq(req);
        ConnectDevResp connectDevResp = this.restTemplateUtils.httpPostMediaTypeJson(this.oldDevReqURL + "connectDevice", ConnectDevResp.class, connectDevReq);
        if (!connectDevResp.isOK()) {
            throw new CommonException(connectDevResp.getErrorInfo());
        }
        return connectDevResp;
    }

    private ConnectDevReq buildConnectDevReq(CommonReq req) {
        UserVipEntity user = (UserVipEntity) req.getAttrByKey(Keys.USER_INFO);
        ActivedDeviceInfoEntity deviceInfo = (ActivedDeviceInfoEntity) req.getAttrByKey(Keys.DEVICE_INFO);
        ConnectDevReq connectDevReq = new ConnectDevReq();
        connectDevReq.setUserId(user.getMemberId().toString());
        connectDevReq.setPhoneNum(user.getPhone());
        connectDevReq.setDeviceId(deviceInfo.getDeviceId() + "");
        connectDevReq.setPasswd(deviceInfo.getPassword());
        connectDevReq.setNeedValidate(0);
        return connectDevReq;
    }

    private JSONObject dispatch(String sessionId, CommonReq req, int operationType) {
        String action = "";
        JSONObject result = null;
        if (operationType == Constant.OldDeviceOperType.START_OR_RESERVE_MAKE_TEA) {
            action = "startWorking";
            result = this.startWorking(sessionId, req);
        } else if (operationType == Constant.OldDeviceOperType.WASH_TEA) {
            action = "washTea";
            result = this.washTea(sessionId, req);
        } else if (operationType == Constant.OldDeviceOperType.STOP_MAKE_TEA) {
            action = "stopWorking";
            result = this.stopWorking(sessionId, req);
        } else if (operationType == Constant.OldDeviceOperType.USE_TEA_SPECTRUM) {
            action = "startByChapu";
            result = this.startByChapu(sessionId, req);
        } else if (operationType == Constant.OldDeviceOperType.CANCEL_TEA_SPECTRUM) {
            action = "cancelChapu";
            result = this.cancelChapu(sessionId, req);
        } else if (operationType == Constant.OldDeviceOperType.WARM_CONTROL) {
            action = "setWarmState";
            result = this.setWarmState(sessionId, req);
        }
        log.info("action = {} result = {}", action, result);
        return result;
    }

    /**
     * 启动或预约泡茶
     * @param sessionId
     * @param req
     * @return
     */
    private JSONObject startWorking(String sessionId, CommonReq req) {
        MakeTeaReq makeTeaReq = new MakeTeaReq(sessionId, JSONObject.parseObject(req.getBizBody(), DeviceCtrlReqDTO.class));
        return this.restTemplateUtils.httpPostMediaTypeJson(this.oldDevReqURL + "startWorking", JSONObject.class, makeTeaReq);
    }

    /**
     * 洗茶
     * @param sessionId
     * @param req
     * @return
     */
    private JSONObject washTea(String sessionId, CommonReq req) {
        Map<String, Object> params = ImmutableMap.of(Keys.SESSION_ID, sessionId, Keys.ACTION, 1);
        return this.restTemplateUtils.httpPostMediaTypeJson(this.oldDevReqURL + "washTea", JSONObject.class, params);
    }

    /**
     * 停止泡茶
     * @param sessionId
     * @param req
     * @return
     */
    private JSONObject stopWorking(String sessionId, CommonReq req) {
        Map<String, Object> params = ImmutableMap.of(Keys.SESSION_ID, sessionId, "stopHeatEx", 1, "stopWarmEx", 1);
        return this.restTemplateUtils.httpPostMediaTypeJson(this.oldDevReqURL + "stopWorking", JSONObject.class, params);
    }

    /**
     * 使用茶谱泡茶
     * @param sessionId
     * @param req
     * @return
     */
    private JSONObject startByChapu(String sessionId, CommonReq req) {
        JSONObject jsonObj = JSONObject.parseObject(req.getBizBody());
        Map<String, Object> params = ImmutableMap.of(Keys.SESSION_ID, sessionId, "chapuId", jsonObj.get("chapuId"), "index", jsonObj.get("index"));
        return this.restTemplateUtils.httpPostMediaTypeJson(this.oldDevReqURL + "startByChapu", JSONObject.class, params);
    }

    /**
     * 取消茶谱
     * @param sessionId
     * @param req
     * @return
     */
    private JSONObject cancelChapu(String sessionId, CommonReq req) {
        Map<String, Object> params = ImmutableMap.of(Keys.SESSION_ID, sessionId);
        return this.restTemplateUtils.httpPostMediaTypeJson(this.oldDevReqURL + "cancelChapu", JSONObject.class, params);
    }

    /**
     * 保温控制
     * @param sessionId
     * @param req
     * @return
     */
    private JSONObject setWarmState(String sessionId, CommonReq req) {
        JSONObject jsonObj = JSONObject.parseObject(req.getBizBody());
        Map<String, Object> params = ImmutableMap.of(Keys.SESSION_ID, sessionId, "operType", jsonObj.get("operType"));
        return this.restTemplateUtils.httpPostMediaTypeJson(this.oldDevReqURL + "setWarmState", JSONObject.class, params);
    }
}
