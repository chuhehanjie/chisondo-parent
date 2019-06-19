package com.chisondo.iot.http.handler;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.iot.http.model.DevBusiModel;
import com.chisondo.model.http.req.QryDeviceInfoHttpReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("busiHandler4qryDevChapu")
@Slf4j
@Deprecated
public class QryDevChapuBusiHandler extends DevBusiHandler {
    @Override
    protected void processBusi(DevBusiModel devBusiModel) {
        log.info("发送查询设备茶谱信息指令开始......");
        QryDeviceInfoHttpReq devHttpReq = JSONObject.parseObject(devBusiModel.getJson(), QryDeviceInfoHttpReq.class);
        devBusiModel.sendReq2Dev(devHttpReq);
        log.info("发送查询设备茶谱信息指令结束......");
    }
}
