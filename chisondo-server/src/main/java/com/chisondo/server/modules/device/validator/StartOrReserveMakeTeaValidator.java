package com.chisondo.server.modules.device.validator;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.utils.*;
import com.chisondo.server.common.validator.BusiValidator;
import com.chisondo.server.modules.device.dto.req.StartOrReserveMakeTeaReqDTO;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.stereotype.Component;

import java.util.Date;

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
        boolean isReserveMakeTea = false;
        if (ValidateUtils.isNotEmpty(startOrReserveTeaReq.getStartTime())) {
            Date startTime = DateUtils.parseDate(startOrReserveTeaReq.getStartTime(), DateUtils.DATE_TIME_PATTERN);
            Date currentDate = DateUtils.currentDate();
            if (startTime.compareTo(currentDate) < 0) {
                throw new CommonException("开始时间小于当前时间");
            }
            // 校验启动时间
            if (DateUtils.getBetweenMinutes(startTime, currentDate) < 5) {
                throw new CommonException("开始时间必须大于当前时间 5 分钟");
            }
            isReserveMakeTea = true;
        }
        ParamValidatorUtils.validateByBeanId("devCtrlParamValidator", req);
        req.addAttr(Keys.REQ, startOrReserveTeaReq);
        req.addAttr("isReserveMakeTea", isReserveMakeTea);

    }
}
