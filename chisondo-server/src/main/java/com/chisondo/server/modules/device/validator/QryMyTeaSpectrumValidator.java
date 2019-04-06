package com.chisondo.server.modules.device.validator;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.utils.Keys;
import com.chisondo.server.common.utils.RegexUtils;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.common.validator.BusiValidator;
import com.chisondo.server.modules.tea.constant.TeaSpectrumConstant;
import com.chisondo.server.modules.tea.dto.QryMyTeaSpectrumReqDTO;
import com.chisondo.server.modules.user.entity.UserVipEntity;
import com.chisondo.server.modules.user.service.UserVipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 查询我的茶谱校验器
 */
@Component("qryMyTeaSpectrumValidator")
public class QryMyTeaSpectrumValidator implements BusiValidator {

    @Autowired
    private UserVipService userVipService;

    @Override
    public void validate(CommonReq req) {
        QryMyTeaSpectrumReqDTO qryMyTeaSpectrumReq = JSONObject.parseObject(req.getBizBody(), QryMyTeaSpectrumReqDTO.class);
        String phoneNum = qryMyTeaSpectrumReq.getPhoneNum();
        if (ValidateUtils.isEmptyString(phoneNum)) {
            throw new CommonException("用户号码为空");
        }
        if (!RegexUtils.isMobile(phoneNum)) {
            throw new CommonException("用户号码格式不正确");
        }
        if (ValidateUtils.isNotEmpty(qryMyTeaSpectrumReq.getType())) {
            if (ValidateUtils.notEquals(TeaSpectrumConstant.MyChapuType.CREATED, qryMyTeaSpectrumReq.getType()) &&
                    ValidateUtils.notEquals(TeaSpectrumConstant.MyChapuType.SAVED, qryMyTeaSpectrumReq.getType()) &&
                    ValidateUtils.notEquals(TeaSpectrumConstant.MyChapuType.USED, qryMyTeaSpectrumReq.getType())) {
                throw new CommonException("无效的类型参数，只能是[0、1、2]");
            }
        }
        req.addAttr(Keys.REQ, qryMyTeaSpectrumReq);
    }
}
