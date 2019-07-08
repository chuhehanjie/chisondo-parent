package com.chisondo.iot.http.handler;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.iot.http.model.DevBusiModel;
import com.chisondo.model.http.req.DevUpgradeHttpReq;
import com.chisondo.model.http.req.LockOrUnlockDevHttpReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("busiHandler4devUpgrade")
@Slf4j
public class DevUpgradeBusiHandler extends DevBusiHandler {
    @Override
    protected void processBusi(DevBusiModel devBusiModel) {
        log.info("发送固件升级指令开始......");
        DevUpgradeHttpReq devHttpReq = JSONObject.parseObject(devBusiModel.getJson(), DevUpgradeHttpReq.class);
        devBusiModel.sendReq2Dev(devHttpReq);
        log.info("发送固件升级指令结束......");
    }
}
