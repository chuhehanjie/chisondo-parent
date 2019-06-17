package com.chisondo.iot.common.utils;

import com.chisondo.model.http.resp.DevStatusReportResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class HttpUtils {

    @Autowired
    private RestTemplateUtils restTemplateUtils;

    public void sendDevState2Http(String deviceId, boolean isOffline) {
        try {
            String  suffix = isOffline ? "setDevStateOffline" : "updateDevStateFromRedis";
            Map<String, Object> params = new HashMap<>();
            params.put("deviceId", deviceId);
            String result = this.restTemplateUtils.httpPostMediaTypeJson(this.restTemplateUtils.appHttpURL + "/api/rest/" + suffix, String.class, params);
            log.error("result = {}", result);
        } catch (Exception e) {
            log.error("更新设备状态信息失败！", e);
        }
    }

    public void reportDevStatus2App(DevStatusReportResp reportReq) {
        // 发送请求到 HTTP 应用，更新设备状态
        try {
            String result = this.restTemplateUtils.httpPostMediaTypeJson(this.restTemplateUtils.appHttpURL + "/api/rest/currentState", String.class, reportReq);
            log.error("result = {}", result);
        } catch (Exception e) {
            log.error("上报设备状态信息失败！", e);
        }
    }
}
