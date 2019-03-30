package com.chisondo.server.common.utils;

import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.validator.BusiValidator;

public final class ParamValidatorUtils {

    public static void validateByBeanId(String beanId, CommonReq req) {
        BusiValidator busiValidator = (BusiValidator) SpringContextUtils.getBean(beanId);
        busiValidator.validate(req);
    }
}
