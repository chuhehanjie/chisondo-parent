package com.chisondo.server.modules.http2dev.service;

import com.chisondo.model.http.req.DeviceHttpReq;
import com.chisondo.model.http.req.QryDevSettingHttpReq;
import com.chisondo.model.http.resp.DevSettingHttpResp;
import com.chisondo.model.http.resp.DeviceHttpResp;

public interface DeviceHttpService {
    DeviceHttpResp makeTea(DeviceHttpReq req);

    DevSettingHttpResp queryDevSettingInfo(QryDevSettingHttpReq req);
}
