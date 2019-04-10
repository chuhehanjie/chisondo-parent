package com.chisondo.server.modules.device.validator;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.utils.Keys;
import com.chisondo.server.common.utils.ParamValidatorUtils;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.common.validator.BusiValidator;
import com.chisondo.server.datasources.DataSourceNames;
import com.chisondo.server.datasources.DynamicDataSource;
import com.chisondo.server.modules.tea.constant.TeaSpectrumConstant;
import com.chisondo.server.modules.tea.dto.DelOrFinishTeaSpectrumReqDTO;
import com.chisondo.server.modules.tea.entity.AppChapuEntity;
import com.chisondo.server.modules.tea.service.AppChapuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

/**
 * 删除或结束茶谱校验器
 */
@Component("delOrFinishTeaSpectrumValidator")
public class DelOrFinishTeaSpectrumValidator implements BusiValidator {

    @Autowired
    private AppChapuService appChapuService;

    @Override
    public void validate(CommonReq req) {
        DelOrFinishTeaSpectrumReqDTO delOrFinishTeaSpectrumReq = JSONObject.parseObject(req.getBizBody(), DelOrFinishTeaSpectrumReqDTO.class);
        if (ValidateUtils.isEmpty(delOrFinishTeaSpectrumReq.getChapuId())) {
            throw new CommonException("茶谱ID为空");
        }
        if (ValidateUtils.isEmpty(delOrFinishTeaSpectrumReq.getOperFlag())) {
            throw new CommonException("操作标识为空");
        }
        if (ValidateUtils.notEquals(TeaSpectrumConstant.MyChapuOperFlag.DELETE, delOrFinishTeaSpectrumReq.getOperFlag()) &&
                ValidateUtils.notEquals(TeaSpectrumConstant.MyChapuOperFlag.FINISH, delOrFinishTeaSpectrumReq.getOperFlag())) {
            throw new CommonException("无效的操作标识，只能是[0,1]");
        }
        DynamicDataSource.setDataSource(DataSourceNames.SECOND);
        AppChapuEntity teaSpectrum = this.appChapuService.queryObject(delOrFinishTeaSpectrumReq.getChapuId());
        if (ValidateUtils.isEmpty(teaSpectrum)) {
            throw new CommonException("茶谱信息不存在");
        }
        req.addAttr(Keys.REQ, delOrFinishTeaSpectrumReq);
        req.addAttr("teaSpectrumInfo", teaSpectrum);
    }
}
