package com.chisondo.iot.http.handler;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.iot.http.model.DevBusiModel;
import com.chisondo.model.http.req.SetDevChapuParamHttpReq;
import com.chisondo.model.http.req.SetDevOtherParamHttpReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("busiHandler4setDevOtherParam")
@Slf4j
public class SetDevOtherParamBusiHandler extends DevBusiHandler {
    @Override
    protected void processBusi(DevBusiModel devBusiModel) {
        log.info("设置设备静音/网络业务开始......");
        SetDevOtherParamHttpReq devHttpReq = JSONObject.parseObject(devBusiModel.getJson(), SetDevOtherParamHttpReq.class);
        devBusiModel.sendReq2Dev(devHttpReq);
        log.info("设置设备静音/网络业务结束......");
    }
}
