package com.chisondo.server.modules.olddevice.service;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.http.CommonReq;

public interface OldDeviceCtrlService {
    JSONObject service(CommonReq req, int operationType);
}
