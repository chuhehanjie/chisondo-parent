package com.chisondo.server.modules.device.validator;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.utils.Keys;
import com.chisondo.server.common.utils.ParamValidatorUtils;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.common.validator.BusiValidator;
import org.springframework.stereotype.Component;

/**
 * 查询预约信息校验器
 */
@Component("queryReservationValidator")
public class QueryReservationValidator implements BusiValidator {

    @Override
    public void validate(CommonReq req) {
        JSONObject jsonObj = JSONObject.parseObject(req.getBizBody());
        if (ValidateUtils.isNotEmptyString(jsonObj.getString(Keys.PHONE_NUM))) {
            ParamValidatorUtils.validateByBeanId("userDevRelaValidator", req);
        } else {
            ParamValidatorUtils.validateByBeanId("devExistenceValidator", req);
        }
        req.addAttr(Keys.REQ, jsonObj);
    }
}
