package com.chisondo.server.modules.device.validator;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.model.http.resp.DevParamMsg;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.utils.CacheDataUtils;
import com.chisondo.server.common.utils.Constant;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.common.validator.BusiValidator;
import com.chisondo.server.modules.device.dto.req.StartOrReserveMakeTeaReqDTO;
import org.springframework.stereotype.Component;

/**
 * 设备控制参数校验器
 */
@Component("devCtrlParamValidator")
public class DevCtrlParamValidator implements BusiValidator {

    @Override
    public void validate(CommonReq req) {
        DevParamMsg devParamMsg = JSONObject.parseObject(req.getBizBody(), DevParamMsg.class);
        if (devParamMsg.getTemperature() < Constant.TemperatureValue.MIN
                || devParamMsg.getTemperature() > Constant.TemperatureValue.MAX) {
            throw new CommonException(String.format("温度值必须在%d至%d之间", Constant.TemperatureValue.MIN, Constant.TemperatureValue.MAX));
        }
        if (devParamMsg.getSoak() < Constant.SoakTime.MIN
                || devParamMsg.getSoak() > Constant.SoakTime.MAX) {
            throw new CommonException(String.format("浸泡时间必须在%d至%d之间", Constant.SoakTime.MIN, Constant.SoakTime.MAX));
        }
        // 出水量不需要校验 update by dz 20190522
        /*if (!CacheDataUtils.getWaterLevels().contains(devParamMsg.getWaterlevel())) {
            throw new CommonException(String.format("出水量必须在%s档次之间", CacheDataUtils.getWaterLevels()));
        }*/

    }
}
