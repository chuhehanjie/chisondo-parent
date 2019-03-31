package com.chisondo.server.modules.http2dev.service.impl;

import com.chisondo.model.http.req.DeviceHttpReq;
import com.chisondo.model.http.req.QryDeviceInfoHttpReq;
import com.chisondo.model.http.req.StopWorkHttpReq;
import com.chisondo.model.http.resp.DevSettingHttpResp;
import com.chisondo.model.http.resp.DevStatusReportResp;
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

    @Value("${chisondo.server.http2DevURL}")
    private String http2DevURL;



    private DeviceHttpResp deviceControl(String url, Object req) {
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

    /**
     * 启动泡茶
     * @param req
     * @return
     */
    @Override
    public DeviceHttpResp makeTea(DeviceHttpReq req) {
        req.setAction("startwork");
        req.setActionflag(Constant.DevStartWorkAction.MAKE_TEA);
        return this.deviceControl(this.http2DevURL + "startWork", req);
    }

    /**
     * 洗茶控制
     * @param req
     * @return
     */
    @Override
    public DeviceHttpResp washTeaCtrl(DeviceHttpReq req) {
        req.setAction("setdevparm");
        req.setActionflag(1); // 洗茶按键
        return this.deviceControl(this.http2DevURL + "startWork", req);
    }

    /**
     * 烧水控制
     * @param req
     * @return
     */
    @Override
    public DeviceHttpResp boilWaterCtrl(DeviceHttpReq req) {
        req.setAction("setdevparm");
        req.setActionflag(2); // 烧水按键
        return this.deviceControl(this.http2DevURL + "startWork", req);
    }

    /**
     * 停止沏茶/烧水/洗茶/保温
     * actionFalg 1-停止沏茶 2-停止烧水 3-停止洗茶 4-停止保温
     * @param req
     * @return
     */
    @Override
    public DeviceHttpResp stopWork(StopWorkHttpReq req) {
        req.setAction("stopwork");
        return this.deviceControl(this.http2DevURL + "stopWork", req);
    }

    /**
     * 开始保温控制
     * @param req
     * @return
     */
    @Override
    public DeviceHttpResp startKeeWarm(DeviceHttpReq req) {
        req.setAction("keepwarm");
        return this.deviceControl(this.http2DevURL + "startKeepWarm", req);
    }

    @Override
    public DevSettingHttpResp queryDevSettingInfo(QryDeviceInfoHttpReq req) {
        req.setAction("qrydevparm");
        DevSettingHttpResp resp = this.restTemplateUtils.httpPostMediaTypeJson(this.http2DevURL + "qryDevSetting", DevSettingHttpResp.class, req);
        return resp;
    }

    /**
     * 查询设备状态信息
     * @param req
     * @return
     */
    @Override
    public DevStatusReportResp queryDevStateInfo(QryDeviceInfoHttpReq req) {
        req.setAction("qrydevicestate");
        DevStatusReportResp resp = this.restTemplateUtils.httpPostMediaTypeJson(this.http2DevURL + "qryDevStatus", DevStatusReportResp.class, req);
        return resp;
    }

}
