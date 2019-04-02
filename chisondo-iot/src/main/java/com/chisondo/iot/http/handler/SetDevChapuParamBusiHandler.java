package com.chisondo.iot.http.handler;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.iot.http.model.DevBusiModel;
import com.chisondo.model.http.req.SetDevChapuParamHttpReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("busiHandler4setDevChapuParam")
@Slf4j
public class SetDevChapuParamBusiHandler extends DevBusiHandler {
    @Override
    protected void processBusi(DevBusiModel devBusiModel) {
        log.info("设置设备内置参数业务开始......");
        SetDevChapuParamHttpReq devHttpReq = JSONObject.parseObject(devBusiModel.getJson(), SetDevChapuParamHttpReq.class);
        devBusiModel.sendReq2Dev(devHttpReq);
        log.info("设置设备内置参数业务结束......");
    }
}
