package com.chisondo.iot.http.handler;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.iot.http.model.DevBusiModel;
import com.chisondo.model.http.req.DeviceHttpReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("busiHandler4setDevParam")
@Slf4j
public class SetDevParamBusiHandler extends DevBusiHandler {
    @Override
    protected void processBusi(DevBusiModel devBusiModel) {
        log.info("发送设置洗茶/烧水参数指令开始......");
        DeviceHttpReq devHttpReq = JSONObject.parseObject(devBusiModel.getJson(), DeviceHttpReq.class);
        devBusiModel.sendReq2Dev(devHttpReq);
        log.info("发送设置洗茶/烧水参数指令结束......");
    }
}
