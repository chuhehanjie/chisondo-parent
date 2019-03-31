package com.chisondo.iot.http.handler;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.iot.http.model.DevBusiModel;
import com.chisondo.model.http.req.QryDeviceInfoHttpReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("busiHandler4qryDevStatus")
@Slf4j
public class QryDevStatusBusiHandler extends DevBusiHandler {
    @Override
    protected void processBusi(DevBusiModel devBusiModel) {
        log.info("查询设备状态信息开始......");
        QryDeviceInfoHttpReq devHttpReq = JSONObject.parseObject(devBusiModel.getJson(), QryDeviceInfoHttpReq.class);
        devBusiModel.sendReq2Dev(devHttpReq);
        log.info("查询设备状态信息结束......");
    }
}
