package com.chisondo.server.modules.device.validator;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.utils.Constant;
import com.chisondo.server.common.validator.BusiValidator;
import com.chisondo.server.modules.device.dto.req.StopWorkReqDTO;
import org.springframework.stereotype.Component;

/**
 * 停止设备工作校验器
 */
@Component("stopWorkValidator")
public class StopWorkValidator implements BusiValidator {

    @Override
    public void validate(CommonReq req) {
        StopWorkReqDTO stopWorkReqDTO = JSONObject.parseObject(req.getBizBody(), StopWorkReqDTO.class);
        if (stopWorkReqDTO.getOperFlag() != Constant.StopWorkOperFlag.STOP_MAKE_TEA && stopWorkReqDTO.getOperFlag() != Constant.StopWorkOperFlag.STOP_WASH_TEA
                && stopWorkReqDTO.getOperFlag() != Constant.StopWorkOperFlag.STOP_BOIL_WATER) {
            throw new CommonException("操作类型不正确");
        }
    }
}
