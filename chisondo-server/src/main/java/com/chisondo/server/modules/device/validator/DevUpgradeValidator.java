package com.chisondo.server.modules.device.validator;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.utils.Keys;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.common.validator.BusiValidator;
import com.chisondo.server.modules.device.dto.req.DevUpgradeReqDTO;
import com.chisondo.server.modules.sys.entity.CompanyEntity;
import com.chisondo.server.modules.sys.service.CompanyService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 设备升级校验器
 */
@Component("devUpgradeValidator")
public class DevUpgradeValidator implements BusiValidator {

    @Autowired
    private CompanyService companyService;

    @Override
    public void validate(CommonReq req) {
        DevUpgradeReqDTO devUpgradeReqDTO = JSONObject.parseObject(req.getBizBody(), DevUpgradeReqDTO.class);
        if (ValidateUtils.isEmptyString(devUpgradeReqDTO.getCompanyCode())) {
            throw new CommonException("公司编号为空");
        }
        if (ValidateUtils.isEmptyString(devUpgradeReqDTO.getStandyServer())) {
            throw new CommonException("固件服务器地址为空");
        }
        if (ValidateUtils.isEmptyString(devUpgradeReqDTO.getVersion())) {
            throw new CommonException("版本号为空");
        }
        List<CompanyEntity> companyList = this.companyService.queryList(ImmutableMap.of("id", devUpgradeReqDTO.getCompanyCode()));
        if (ValidateUtils.isEmptyCollection(companyList)) {
            throw new CommonException("公司信息不存在");
        }
        req.addAttr(Keys.REQ, devUpgradeReqDTO);
    }
}
