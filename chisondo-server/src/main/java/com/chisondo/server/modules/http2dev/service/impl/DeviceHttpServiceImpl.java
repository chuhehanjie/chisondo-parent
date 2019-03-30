package com.chisondo.server.modules.http2dev.service.impl;

import com.chisondo.model.http.req.DeviceHttpReq;
import com.chisondo.model.http.req.QryDevSettingHttpReq;
import com.chisondo.model.http.resp.DevSettingHttpResp;
import com.chisondo.model.http.resp.DeviceHttpResp;
import com.chisondo.server.common.utils.Constant;
import com.chisondo.server.common.utils.RestTemplateUtils;
import com.chisondo.server.modules.http2dev.service.DeviceHttpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

@Service("http2DevService")
@Slf4j
public class DeviceHttpServiceImpl implements DeviceHttpService {

    @Autowired
    private RestTemplateUtils restTemplateUtils;

    @Value("chisondo.server.http2DevURL")
    private String http2DevURL;


    private DeviceHttpResp deviceControl(String url, DeviceHttpReq req) {
        try {
            DeviceHttpResp resp = this.restTemplateUtils.httpPostMediaTypeJson(url, DeviceHttpResp.class, req);
            return resp;
        } catch (RestClientException e) {
            log.error("设备控制请求异常！", e);
            return new DeviceHttpResp(HttpStatus.SC_INTERNAL_SERVER_ERROR, "设备控制请求异常！");
        } catch (Exception e) {
            log.error("设备控制请求异常！", e);
            return new DeviceHttpResp(HttpStatus.SC_INTERNAL_SERVER_ERROR, "设备控制请求异常！");
        }
    }

    @Override
    public DeviceHttpResp makeTea(DeviceHttpReq req) {
        req.setAction("startwork");
        req.setActionflag(Constant.DevStartWorkAction.MAKE_TEA);
        return this.deviceControl(this.http2DevURL + "makeTea", req);
    }

    @Override
    public DevSettingHttpResp queryDevSettingInfo(QryDevSettingHttpReq req) {
        req.setAction("qrydevparm");
        DevSettingHttpResp resp = this.restTemplateUtils.httpPostMediaTypeJson(this.http2DevURL + "qryDevSetting", DevSettingHttpResp.class, req);
        return resp;
    }

}
