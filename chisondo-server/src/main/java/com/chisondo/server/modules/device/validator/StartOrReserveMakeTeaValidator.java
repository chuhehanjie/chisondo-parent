package com.chisondo.server.modules.device.validator;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.utils.DateUtils;
import com.chisondo.server.common.utils.Keys;
import com.chisondo.server.common.utils.ParamValidatorUtils;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.common.validator.BusiValidator;
import com.chisondo.server.modules.device.dto.req.StartOrReserveMakeTeaReqDTO;
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
        startOrReserveTeaReq.setDeviceId((String) req.getAttrByKey(Keys.DEVICE_ID));
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
            if (DateUtils.getBetweenMinutes(startTime, currentDate) < 10) {
                throw new CommonException("开始时间必须大于当前时间 10 分钟");
            }
            if (DateUtils.getBetweenHours(startTime, currentDate) > 24) {
                throw new CommonException("开始时间必须在 24 小时内");
            }

            isReserveMakeTea = true;
        }
        if (ValidateUtils.isEmpty(startOrReserveTeaReq.getWarm())) {
            throw new CommonException("温度值为空");
        }
        if (ValidateUtils.isNotEmpty(startOrReserveTeaReq.getSoup())) {
            if (startOrReserveTeaReq.getSoup() < 150 || startOrReserveTeaReq.getSoup() > 500) {
                throw new CommonException("出汤量必须在150-500之间");
            }
        }

        ParamValidatorUtils.validateByBeanId("devCtrlParamValidator", req);
        req.addAttr(Keys.REQ, startOrReserveTeaReq);
        req.addAttr("isReserveMakeTea", isReserveMakeTea);

    }
}
