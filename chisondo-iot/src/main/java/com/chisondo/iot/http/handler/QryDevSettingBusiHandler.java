package com.chisondo.iot.http.handler;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.iot.http.model.DevBusiModel;
import com.chisondo.model.http.req.QryDeviceInfoHttpReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("busiHandler4qryDevSetting")
@Slf4j
public class QryDevSettingBusiHandler extends DevBusiHandler {
    @Override
    protected void processBusi(DevBusiModel devBusiModel) {
        log.info("查询设备设置参数信息开始......");
        QryDeviceInfoHttpReq devHttpReq = JSONObject.parseObject(devBusiModel.getJson(), QryDeviceInfoHttpReq.class);
        devBusiModel.sendReq2Dev(devHttpReq);
        log.info("查询设备设置参数信息结束......");
    }
}
