package com.chisondo.server.modules.device.validator;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.utils.CacheDataUtils;
import com.chisondo.server.common.utils.Constant;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.common.validator.BusiValidator;
import com.chisondo.server.modules.device.dto.req.StartOrReserveMakeTeaReqDTO;
import org.springframework.stereotype.Component;

/**
 * 启动或预约泡茶校验器
 */
@Component("startOrReserveMakeTeaValidator")
public class StartOrReserveMakeTeaValidator implements BusiValidator {

    @Override
    public void validate(CommonReq req) {
        StartOrReserveMakeTeaReqDTO startOrReserveTeaReq = JSONObject.parseObject(req.getBizBody(), StartOrReserveMakeTeaReqDTO.class);
        if (ValidateUtils.isEmpty(startOrReserveTeaReq)) {
            throw new CommonException("请求体为空");
        }
        if (ValidateUtils.isEmpty(startOrReserveTeaReq.getTeaSortId()) ||
                ValidateUtils.isEmptyString(startOrReserveTeaReq.getTeaSortName())) {
            throw new CommonException("茶类ID或茶类名称为空");
        }
        if (startOrReserveTeaReq.getTemperature() < Constant.TemperatureValue.MIN
                || startOrReserveTeaReq.getTemperature() > Constant.TemperatureValue.MAX) {
            throw new CommonException(String.format("温度值必须在%d至%d之间", Constant.TemperatureValue.MIN, Constant.TemperatureValue.MAX));
        }
        if (startOrReserveTeaReq.getSoak() < Constant.SoakTime.MIN
                || startOrReserveTeaReq.getSoak() > Constant.SoakTime.MAX) {
            throw new CommonException(String.format("浸泡时间必须在%d至%d之间", Constant.SoakTime.MIN, Constant.SoakTime.MAX));
        }

        if (!CacheDataUtils.getWaterLevels().contains(startOrReserveTeaReq.getWaterlevel())) {
            throw new CommonException(String.format("出水量必须在%s档次之间", CacheDataUtils.getWaterLevels()));
        }

    }
}
