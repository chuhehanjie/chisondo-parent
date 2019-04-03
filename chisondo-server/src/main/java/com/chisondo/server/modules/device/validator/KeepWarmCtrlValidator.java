package com.chisondo.server.modules.device.validator;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.utils.Constant;
import com.chisondo.server.common.utils.Keys;
import com.chisondo.server.common.validator.BusiValidator;
import org.springframework.stereotype.Component;

/**
 * 保温控制校验器
 */
@Component("keepWarmCtrlValidator")
public class KeepWarmCtrlValidator implements BusiValidator {
    @Override
    public void validate(CommonReq req) {
        JSONObject jsonObj = JSONObject.parseObject(req.getBizBody());
        if (null == jsonObj.get(Keys.OPER_FLAG)) {
            throw new CommonException("操作类型为空");
        }
        int operFlag = jsonObj.getIntValue(Keys.OPER_FLAG);
        if (Constant.KeeWarmCtrlOperFalg.START_KEEP_WARM != operFlag && Constant.KeeWarmCtrlOperFalg.STOP_KEEP_WARM != operFlag) {
            throw new CommonException("操作类型无效");
        }
    }
}
