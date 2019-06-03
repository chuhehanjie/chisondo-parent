package com.chisondo.server.modules.device.validator;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.utils.Constant;
import com.chisondo.server.common.utils.Keys;
import com.chisondo.server.common.validator.BusiValidator;
import org.springframework.stereotype.Component;

/**
 * 锁定或解锁设备校验器
 */
@Component("lockOrUnlockDevValidator")
public class LockOrUnlockDevValidator implements BusiValidator {
    @Override
    public void validate(CommonReq req) {
        JSONObject jsonObj = JSONObject.parseObject(req.getBizBody());
        if (null == jsonObj.get(Keys.OPER_FLAG)) {
            throw new CommonException("操作类型为空");
        }
        if (null == jsonObj.get(Keys.ACTION_FLAG)) {
            throw new CommonException("功能标示为空");
        }
        int operFlag = jsonObj.getIntValue(Keys.OPER_FLAG);
        if (Constant.LockOrUnlockDevOperFalg.LOCK != operFlag && Constant.LockOrUnlockDevOperFalg.UNLOCK != operFlag) {
            throw new CommonException("操作类型无效");
        }
        int actionFlag = jsonObj.getIntValue(Keys.ACTION_FLAG);
        if (Constant.LockOrUnlockDevActionFalg.POWER_KEY != actionFlag && Constant.LockOrUnlockDevActionFalg.ENABLE_KEY_4_STOP_MAKE_TEA != actionFlag
                && Constant.LockOrUnlockDevActionFalg.ENABLE_KEY != actionFlag) {
            throw new CommonException("功能标示无效");
        }
        req.addAttr(Keys.REQ, jsonObj);
    }
}
