package com.chisondo.iot.http.handler;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.iot.http.model.DevBusiModel;
import com.chisondo.model.http.req.DeviceHttpReq;
import com.chisondo.model.http.req.QryDevSettingHttpReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("busiHandler4qrydevparm")
@Slf4j
public class QryDevParamBusiHandler extends DevBusiHandler {
    @Override
    protected void processBusi(DevBusiModel devBusiModel) {
        log.info("查询设置参数信息开始......");
        QryDevSettingHttpReq devHttpReq = JSONObject.parseObject(devBusiModel.getJson(), QryDevSettingHttpReq.class);
        devBusiModel.sendReq2Dev(devHttpReq);
        log.info("查询设置参数信息结束......");
    }
}
