package com.chisondo.server.modules.device.validator;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.utils.Keys;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.common.validator.BusiValidator;
import com.chisondo.server.datasources.DataSourceNames;
import com.chisondo.server.datasources.DynamicDataSource;
import com.chisondo.server.modules.tea.constant.TeaSpectrumConstant;
import com.chisondo.server.modules.tea.dto.DelOrFinishTeaSpectrumReqDTO;
import com.chisondo.server.modules.tea.dto.QryTeaSpectrumParamDTO;
import com.chisondo.server.modules.tea.dto.SaveTeaSpectrumReqDTO;
import com.chisondo.server.modules.tea.entity.AppChapuEntity;
import com.chisondo.server.modules.tea.entity.AppTeaSortEntity;
import com.chisondo.server.modules.tea.service.AppChapuService;
import com.chisondo.server.modules.tea.service.AppTeaSortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 新建或修改茶谱校验器
 */
@Component("saveTeaSpectrumValidator")
public class SaveTeaSpectrumValidator implements BusiValidator {

    @Autowired
    private AppTeaSortService appTeaSortService;

    @Autowired
    private AppChapuService appChapuService;

    @Override
    public void validate(CommonReq req) {
        SaveTeaSpectrumReqDTO saveTeaSpectrumReq = JSONObject.parseObject(req.getBizBody(), SaveTeaSpectrumReqDTO.class);
        if (ValidateUtils.isEmpty(saveTeaSpectrumReq.getOperFlag())) {
            throw new CommonException("操作标识为空");
        }
        if (ValidateUtils.notEquals(TeaSpectrumConstant.MyChapuOperFlag.CREATE, saveTeaSpectrumReq.getOperFlag()) &&
                ValidateUtils.notEquals(TeaSpectrumConstant.MyChapuOperFlag.MODIFY, saveTeaSpectrumReq.getOperFlag()) &&
                ValidateUtils.notEquals(TeaSpectrumConstant.MyChapuOperFlag.SAVE, saveTeaSpectrumReq.getOperFlag())) {
            throw new CommonException("无效的操作标识，只能是[0,1,2]");
        }
        if (ValidateUtils.isEmpty(saveTeaSpectrumReq.getSortId())) {
            throw new CommonException("茶类ID为空");
        }
        if (ValidateUtils.isEmptyString(saveTeaSpectrumReq.getSortName())) {
            throw new CommonException("茶类名称为空");
        }
        if (ValidateUtils.isEmptyString(saveTeaSpectrumReq.getBrandName())) {
            throw new CommonException("茶品牌名称为空");
        }
        this.validateMakeTeaParam(saveTeaSpectrumReq);
        DynamicDataSource.setDataSource(DataSourceNames.SECOND);
        if (ValidateUtils.equals(TeaSpectrumConstant.MyChapuOperFlag.MODIFY, saveTeaSpectrumReq.getOperFlag())) {
            if (ValidateUtils.isEmpty(saveTeaSpectrumReq.getChapuId())) {
                throw new CommonException("茶谱ID为空");
            }
            AppChapuEntity teaSpectrum = this.appChapuService.queryObject(saveTeaSpectrumReq.getChapuId());
            if (ValidateUtils.isEmpty(teaSpectrum)) {
                throw new CommonException("茶谱信息不存在");
            }
            // 如果是标准茶谱，则不允许修改
            if (teaSpectrum.getTemp() == 0) {
                throw new CommonException("标准茶谱，不允许修改");
            }
            req.addAttr(Keys.TEA_SPECTRUM_INFO, teaSpectrum);
        }
        if (ValidateUtils.isEmptyString(saveTeaSpectrumReq.getChapuName())) {
            throw new CommonException("茶谱名称为空");
        }
        AppTeaSortEntity teaSort = this.appTeaSortService.queryObject(saveTeaSpectrumReq.getSortId());
        if (ValidateUtils.isEmpty(teaSort)) {
            throw new CommonException("茶类信息不存在");
        }
        req.addAttr(Keys.REQ, saveTeaSpectrumReq);
        req.addAttr("teaSortInfo", teaSort);
    }

    private void validateMakeTeaParam(SaveTeaSpectrumReqDTO saveTeaSpectrumReq) {
        if (ValidateUtils.isEmpty(saveTeaSpectrumReq.getAmount())) {
            throw new CommonException("投茶量为空");
        }
        if (saveTeaSpectrumReq.getAmount() < 0 || saveTeaSpectrumReq.getAmount() > 30) {
            throw new CommonException("投茶量只能在 0 至 30 克之间");
        }
        if (ValidateUtils.isEmpty(saveTeaSpectrumReq.getAwake())) {
            throw new CommonException("醒茶参数为空");
        }
        if (saveTeaSpectrumReq.getAwake() != 0 && saveTeaSpectrumReq.getAwake() != 1) {
            throw new CommonException("无效的醒茶参数，只能是[0, 1]");
        }
        if (ValidateUtils.isEmpty(saveTeaSpectrumReq.getMakeTimes())) {
            throw new CommonException("茶谱总泡数为空");
        }
        if (ValidateUtils.isEmptyCollection(saveTeaSpectrumReq.getParameter())) {
            throw new CommonException("沏茶参数为空");
        }
        for (QryTeaSpectrumParamDTO makeTeaParam : saveTeaSpectrumReq.getParameter()) {
            if (ValidateUtils.isEmpty(makeTeaParam.getWater())) {
                throw new CommonException("出水量为空");
            }
            if (ValidateUtils.isEmpty(makeTeaParam.getTemp()) || makeTeaParam.getTemp() < 60 || makeTeaParam.getTemp() > 100) {
                throw new CommonException("水温只能在 60 至 100 度之间");
            }
            if (ValidateUtils.isEmpty(makeTeaParam.getDura()) || makeTeaParam.getDura() < 0 || makeTeaParam.getDura() > 600) {
                throw new CommonException("时长只能在 0 至 600 秒之间");
            }
        }
    }
}
