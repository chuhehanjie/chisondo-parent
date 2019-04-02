package com.chisondo.server.modules.http2dev.service;

import com.chisondo.model.http.req.DeviceHttpReq;
import com.chisondo.model.http.req.QryDeviceInfoHttpReq;
import com.chisondo.model.http.req.SetDevChapuParamHttpReq;
import com.chisondo.model.http.req.StopWorkHttpReq;
import com.chisondo.model.http.resp.DevSettingHttpResp;
import com.chisondo.model.http.resp.DevStatusReportResp;
import com.chisondo.model.http.resp.DeviceHttpResp;

public interface DeviceHttpService {
    DeviceHttpResp makeTea(DeviceHttpReq req);

    DeviceHttpResp washTeaCtrl(DeviceHttpReq req);

    DeviceHttpResp boilWaterCtrl(DeviceHttpReq req);

    DeviceHttpResp stopWork(StopWorkHttpReq req);

    DeviceHttpResp startKeeWarm(DeviceHttpReq req);

    DevSettingHttpResp queryDevSettingInfo(QryDeviceInfoHttpReq req);

    DevStatusReportResp queryDevStateInfo(QryDeviceInfoHttpReq req);

    DeviceHttpResp setDevChapuParam(SetDevChapuParamHttpReq setDevChapuParamHttpReq);
}
