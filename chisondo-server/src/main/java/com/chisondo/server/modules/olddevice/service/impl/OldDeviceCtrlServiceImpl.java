package com.chisondo.server.modules.olddevice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.utils.*;
import com.chisondo.server.datasources.DataSourceNames;
import com.chisondo.server.datasources.DynamicDataSource;
import com.chisondo.server.modules.device.dto.req.DeviceCtrlReqDTO;
import com.chisondo.server.modules.device.dto.req.StopWorkReqDTO;
import com.chisondo.server.modules.device.entity.ActivedDeviceInfoEntity;
import com.chisondo.server.modules.olddevice.req.ConnectDevReq;
import com.chisondo.server.modules.olddevice.req.MakeTeaReq;
import com.chisondo.server.modules.olddevice.resp.ConnectDevResp;
import com.chisondo.server.modules.olddevice.service.OldDeviceCtrlService;
import com.chisondo.server.modules.user.entity.UserVipEntity;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.ws.Response;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service("oldDeviceCtrlService")
@Slf4j
public class OldDeviceCtrlServiceImpl implements OldDeviceCtrlService {

    @Autowired
    private RestTemplateUtils restTemplateUtils;

    @Value("${chisondo.server.oldDevReqURL}")
    private String oldDevReqURL;

    @Override
    public JSONObject service(CommonReq req, int operationType) {
        this.validate(req);
        ConnectDevResp connectDevResp = this.connectDevice(req);
        if (!connectDevResp.isOK()) {
            return new JSONObject(ImmutableMap.of("STATE", connectDevResp.getErrCode(), "STATE_INFO", connectDevResp.getErrorInfo()));
        }
        JSONObject result = this.dispatch(connectDevResp.getSessionId(), req, operationType);
        this.disconnectDevice(connectDevResp.getSessionId(), req);
        return result;
    }

    private void disconnectDevice(String sessionId, CommonReq req) {
        JSONObject result = this.restTemplateUtils.httpPostMediaTypeJson(this.oldDevReqURL + "disconnectDevice", JSONObject.class, ImmutableMap.of(Keys.SESSION_ID, sessionId), this.buildHeaderMap(req));
        log.info("disconnectDevice result = {}", result);
    }

    private void validate(CommonReq req) {
        if (ValidateUtils.isEmpty(req.getAttrByKey(Keys.DEVICE_INFO)) && ValidateUtils.isEmpty(req.getAttrByKey(Keys.USER_INFO))) {
            DynamicDataSource.setDataSource(DataSourceNames.FIRST);
            ParamValidatorUtils.validateByBeanId("devExistenceValidator", req);
            ParamValidatorUtils.validateByBeanId("userExistenceValidator", req);
        }
    }

    private ConnectDevResp connectDevice(CommonReq req) {
        ConnectDevReq connectDevReq = this.buildConnectDevReq(req);
        ResponseEntity<ConnectDevResp> result = this.restTemplateUtils.postForEntity(this.oldDevReqURL + "connectDevice", ConnectDevResp.class, connectDevReq);
        if (result.getHeaders().containsKey(Keys.SET_COOKIE)) {
            String cookie = this.parseCookie(result.getHeaders().get(Keys.SET_COOKIE).get(0));
            req.addAttr(Keys.COOKIE, cookie);
        }
        /*if (!connectDevResp.isOK()) {
            throw new CommonException(ValidateUtils.isNotEmptyString(connectDevResp.getSTATE_INFO()) ? connectDevResp.getSTATE_INFO() : connectDevResp.getErrorInfo());
        }
        return connectDevResp;*/
        return result.getBody();
    }

    private String parseCookie(String header) {
        return header.split(";")[0];
    }

    private ConnectDevReq buildConnectDevReq(CommonReq req) {
        ActivedDeviceInfoEntity deviceInfo = (ActivedDeviceInfoEntity) req.getAttrByKey(Keys.DEVICE_INFO);
        UserVipEntity user = (UserVipEntity) req.getAttrByKey(Keys.USER_INFO);
        ConnectDevReq connectDevReq = new ConnectDevReq();
        if (ValidateUtils.isNotEmpty(user)) {
            connectDevReq.setUserId(user.getMemberId().toString());
            connectDevReq.setPhoneNum(user.getPhone());
        }
        connectDevReq.setDeviceId(Integer.valueOf(deviceInfo.getDeviceId()));
        connectDevReq.setPasswd(deviceInfo.getPassword());
        connectDevReq.setNeedValidate(0);
        return connectDevReq;
    }

    private JSONObject dispatch(String sessionId, CommonReq req, int operationType) {
        String action = "";
        JSONObject result = null;
        if (operationType == Constant.OldDeviceOperType.START_OR_RESERVE_MAKE_TEA || operationType == Constant.OldDeviceOperType.BOIL_WATER ) {
            action = "startWorking";
            result = this.startWorking(sessionId, req);
        } else if (operationType == Constant.OldDeviceOperType.WASH_TEA) {
            action = "washTea";
            result = this.washTea(sessionId, req);
        } else if (operationType == Constant.OldDeviceOperType.STOP_WORK) {
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
        } else if (operationType == Constant.OldDeviceOperType.CANCEL_RESERVATION) {
            action = "cancelReservation";
            result = this.cancelReservation(sessionId, req);
        } else if (operationType == Constant.OldDeviceOperType.QUERY_RESERVATION) {
            action = "queryReservation";
            result = this.queryReservation(sessionId, req);
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
        return this.restTemplateUtils.httpPostMediaTypeJson(this.oldDevReqURL + "startWorking", JSONObject.class, makeTeaReq, this.buildHeaderMap(req));
    }

    private Map<String, String> buildHeaderMap(CommonReq req) {
        if (ValidateUtils.isNotEmpty(req.getAttrByKey(Keys.COOKIE))) {
            log.error("cookie = {}", req.getAttrByKey(Keys.COOKIE));
            return ImmutableMap.of(Keys.COOKIE, req.getAttrByKey(Keys.COOKIE).toString());
        }
        return Collections.EMPTY_MAP;
    }

    /**
     * 洗茶
     * @param sessionId
     * @param req
     * @return
     */
    private JSONObject washTea(String sessionId, CommonReq req) {
        Map<String, Object> params = ImmutableMap.of(Keys.SESSION_ID, sessionId, Keys.ACTION, 1);
        return this.restTemplateUtils.httpPostMediaTypeJson(this.oldDevReqURL + "washTea", JSONObject.class, params, this.buildHeaderMap(req));
    }

    /**
     * 停止洗茶
     * @param sessionId
     * @param req
     * @return
     */
    private JSONObject stopWashTea(String sessionId, CommonReq req) {
        Map<String, Object> params = ImmutableMap.of(Keys.SESSION_ID, sessionId, Keys.ACTION, 0);
        return this.restTemplateUtils.httpPostMediaTypeJson(this.oldDevReqURL + "washTea", JSONObject.class, params, this.buildHeaderMap(req));
    }

    /**
     * 停止泡茶
     * @param sessionId
     * @param req
     * @return
     */
    private JSONObject stopWorking(String sessionId, CommonReq req) {
        StopWorkReqDTO stopWorkReq = JSONObject.parseObject(req.getBizBody(), StopWorkReqDTO.class);
        //  停止洗茶操作
        if (stopWorkReq.getOperFlag() == Constant.StopWorkOperFlag.STOP_WASH_TEA) {
            return this.stopWashTea(sessionId, req);
        } else {
            Map<String, Object> params = Maps.newHashMap();
            params.put(Keys.SESSION_ID, sessionId);
            if (stopWorkReq.getOperFlag() == Constant.StopWorkOperFlag.STOP_KEEP_WARM) {
                params.put("stopWarm", true);
                params.put("stopWarmEx", 1);
            } else {
                params.put("stopHeat", true);
                params.put("stopHeatEx", 1);
            }
            return this.restTemplateUtils.httpPostMediaTypeJson(this.oldDevReqURL + "stopWorking", JSONObject.class, params, this.buildHeaderMap(req));
        }
    }

    /**
     * 使用茶谱泡茶
     * @param sessionId
     * @param req
     * @return
     */
    private JSONObject startByChapu(String sessionId, CommonReq req) {
        JSONObject jsonObj = JSONObject.parseObject(req.getBizBody());
        Map<String, Object> params = ImmutableMap.of(Keys.SESSION_ID, sessionId, Keys.CHAPU_ID, jsonObj.get(Keys.CHAPU_ID), "index", jsonObj.get("index"));
        return this.restTemplateUtils.httpPostMediaTypeJson(this.oldDevReqURL + "startByChapu", JSONObject.class, params, this.buildHeaderMap(req));
    }

    /**
     * 取消茶谱
     * @param sessionId
     * @param req
     * @return
     */
    private JSONObject cancelChapu(String sessionId, CommonReq req) {
        Map<String, Object> params = ImmutableMap.of(Keys.SESSION_ID, sessionId);
        return this.restTemplateUtils.httpPostMediaTypeJson(this.oldDevReqURL + "cancelChapu", JSONObject.class, params, this.buildHeaderMap(req));
    }

    /**
     * 保温控制
     * @param sessionId
     * @param req
     * @return
     */
    private JSONObject setWarmState(String sessionId, CommonReq req) {
        JSONObject jsonObj = JSONObject.parseObject(req.getBizBody());
        Map<String, Object> params = ImmutableMap.of(Keys.SESSION_ID, sessionId, Keys.OPER_FLAG, jsonObj.get(Keys.OPER_FLAG));
        return this.restTemplateUtils.httpPostMediaTypeJson(this.oldDevReqURL + "setWarmState", JSONObject.class, params, this.buildHeaderMap(req));
    }

    /**
     * 取消预约
     * @param sessionId
     * @param req
     * @return
     */
    private JSONObject cancelReservation(String sessionId, CommonReq req) {
        JSONObject jsonObj = JSONObject.parseObject(req.getBizBody());
        Map<String, Object> params = ImmutableMap.of(Keys.SESSION_ID, sessionId, Keys.RESERV_NO, jsonObj.get(Keys.RESERV_NO));
        return this.restTemplateUtils.httpPostMediaTypeJson(this.oldDevReqURL + "cancelReservation", JSONObject.class, params, this.buildHeaderMap(req));
    }

    /**
     * 查询设备状态
     * @param deviceId
     * @return
     */
    @Override
    public JSONObject queryDevStatus(String deviceId) {
        Map<String, Object> params = ImmutableMap.of(Keys.DEVICE_ID, Integer.valueOf(deviceId));
        return this.restTemplateUtils.httpPostMediaTypeJson(this.oldDevReqURL + "qryDevStatus", JSONObject.class, params);
    }

    /**
     * 查询预约信息
     * @return
     */
    private JSONObject queryReservation(String sessionId, CommonReq req) {
        Map<String, Object> params = this.buildQryParams(req);
        params.put(Keys.SESSION_ID, sessionId);
        return this.restTemplateUtils.httpPostMediaTypeJson(this.oldDevReqURL + "queryReservation", JSONObject.class, params);
    }

    private Map<String, Object> buildQryParams(CommonReq req) {
        UserVipEntity user = (UserVipEntity) req.getAttrByKey(Keys.USER_INFO);
        JSONObject jsonObj = JSONObject.parseObject(req.getBizBody());
        Map<String, Object> params = Maps.newHashMap();
        params.put(Keys.DEVICE_ID, Integer.valueOf(jsonObj.getString(Keys.DEVICE_ID)));
        params.put(Query.NUM, jsonObj.get(Query.NUM));
        params.put(Query.PAGE, jsonObj.get(Query.PAGE));
        if (ValidateUtils.isNotEmpty(user)) {
            params.put(Keys.USER_ID, user.getMemberId().toString());
        }
        return params;
    }

    @Override
    public JSONObject queryReservation(CommonReq req) {
        Map<String, Object> params = this.buildQryParams(req);
        return this.restTemplateUtils.httpPostMediaTypeJson(this.oldDevReqURL + "queryReservation", JSONObject.class, params);
    }

}
