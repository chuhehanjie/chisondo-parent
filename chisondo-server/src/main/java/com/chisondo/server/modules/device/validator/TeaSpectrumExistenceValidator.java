package com.chisondo.server.modules.device.validator;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.utils.Keys;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.common.validator.BusiValidator;
import com.chisondo.server.modules.tea.entity.AppChapuEntity;
import com.chisondo.server.modules.tea.service.AppChapuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 校验茶谱是否存在
 */
@Component("teaSpectrumExistenceValidator")
public class TeaSpectrumExistenceValidator implements BusiValidator {

    @Autowired
    private AppChapuService appChapuService;

    @Override
    public void validate(CommonReq req) {
        JSONObject jsonObj = JSONObject.parseObject(req.getBizBody());
        if (ValidateUtils.isEmpty(jsonObj.get(Keys.CHAPU_ID))) {
            throw new CommonException("茶谱ID为空");
        }
        AppChapuEntity teaSpectrum = this.appChapuService.queryObject(Integer.valueOf(jsonObj.get(Keys.CHAPU_ID).toString()));
        if (ValidateUtils.isEmpty(teaSpectrum)) {
            throw new CommonException("茶谱信息不存在");
        }
        req.addAttr(Keys.TEA_SPECTRUM_INFO, teaSpectrum);
    }
}
