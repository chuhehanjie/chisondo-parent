package com.chisondo.iot.http.handler;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.iot.http.model.DevBusiModel;
import com.chisondo.model.http.req.DeviceHttpReq;
import com.chisondo.model.http.req.StopWorkHttpReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("busiHandler4stopWork")
@Slf4j
public class StopWorkBusiHandler extends DevBusiHandler {
    @Override
    protected void processBusi(DevBusiModel devBusiModel) {
        log.info("发送停止沏茶/洗茶/烧水/保温指令开始......");
        StopWorkHttpReq devHttpReq = JSONObject.parseObject(devBusiModel.getJson(), StopWorkHttpReq.class);
        devBusiModel.sendReq2Dev(devHttpReq);
        log.info("发送停止沏茶/洗茶/烧水/保温指令结束......");
    }
}
