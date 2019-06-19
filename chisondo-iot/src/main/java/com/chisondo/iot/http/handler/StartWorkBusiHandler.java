package com.chisondo.iot.http.handler;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.iot.http.model.DevBusiModel;
import com.chisondo.model.http.req.DeviceHttpReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("busiHandler4startWork")
@Slf4j
public class StartWorkBusiHandler extends DevBusiHandler {
    @Override
    protected void processBusi(DevBusiModel devBusiModel) {
        log.info("发送启动或预约泡茶指令开始......");
        DeviceHttpReq devHttpReq = JSONObject.parseObject(devBusiModel.getJson(), DeviceHttpReq.class);
        devBusiModel.sendReq2Dev(devHttpReq);
        log.info("发送启动或预约泡茶指令结束......");
    }
}
