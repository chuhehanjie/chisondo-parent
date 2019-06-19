package com.chisondo.iot.http.handler;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.iot.http.model.DevBusiModel;
import com.chisondo.model.http.req.DeviceHttpReq;
import com.chisondo.model.http.req.LockOrUnlockDevHttpReq;
import com.chisondo.model.http.req.SetDevChapuParamHttpReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("busiHandler4lockOrUnlockDev")
@Slf4j
public class LockOrUnlockDevBusiHandler extends DevBusiHandler {
    @Override
    protected void processBusi(DevBusiModel devBusiModel) {
        log.info("发送锁定或解锁设备指令开始......");
        LockOrUnlockDevHttpReq devHttpReq = JSONObject.parseObject(devBusiModel.getJson(), LockOrUnlockDevHttpReq.class);
        devBusiModel.sendReq2Dev(devHttpReq);
        log.info("发送锁定或解锁设备指令结束......");
    }
}
