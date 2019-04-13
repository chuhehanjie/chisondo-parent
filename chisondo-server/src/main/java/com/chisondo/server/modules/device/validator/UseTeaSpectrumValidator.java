package com.chisondo.server.modules.device.validator;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.utils.Keys;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.common.validator.BusiValidator;
import com.chisondo.server.datasources.DataSourceNames;
import com.chisondo.server.datasources.DynamicDataSource;
import com.chisondo.server.modules.device.dto.req.UseTeaSpectrumReqDTO;
import com.chisondo.server.modules.tea.entity.AppChapuEntity;
import com.chisondo.server.modules.tea.entity.AppChapuParaEntity;
import com.chisondo.server.modules.tea.service.AppChapuParaService;
import com.chisondo.server.modules.tea.service.AppChapuService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 使用茶谱沏茶校验器
 */
@Component("useTeaSpectrumValidator")
public class UseTeaSpectrumValidator implements BusiValidator {

    @Autowired
    private AppChapuService appChapuService;

    @Autowired
    private AppChapuParaService appChapuParaService;
    @Override
    public void validate(CommonReq req) {
        UseTeaSpectrumReqDTO useTeaSpectrumReq = JSONObject.parseObject(req.getBizBody(), UseTeaSpectrumReqDTO.class);
        if (ValidateUtils.isEmpty(useTeaSpectrumReq.getChapuId())) {
            throw new CommonException("茶谱ID为空");
        }
        DynamicDataSource.setDataSource(DataSourceNames.SECOND);
        AppChapuEntity teaSpectrum = this.appChapuService.queryObject(useTeaSpectrumReq.getChapuId());
        if (ValidateUtils.isEmpty(teaSpectrum)) {
            throw new CommonException("茶谱信息不存在");
        }
        int index = (ValidateUtils.isEmpty(useTeaSpectrumReq.getIndex()) || 0 == useTeaSpectrumReq.getIndex()) ? 1 : useTeaSpectrumReq.getIndex();
        List<AppChapuParaEntity> teaSpectrumParams = this.appChapuParaService.queryList(ImmutableMap.of(Keys.CHAPU_ID, useTeaSpectrumReq.getChapuId(), "number", index));
        if (ValidateUtils.isEmptyCollection(teaSpectrumParams)) {
            throw new CommonException("茶谱参数不存在");
        }
        req.addAttr(Keys.REQ, useTeaSpectrumReq);
        req.addAttr(Keys.TEA_SPECTRUM_INFO, teaSpectrum);
        req.addAttr(Keys.TEA_SPECTRUM_PARAM_INFO, teaSpectrumParams.get(0));
        // 使用茶谱泡茶，应该校验茶谱存在不存在，而不是校验用户沏茶记录是否存在
        /*UserVipEntity user = (UserVipEntity) req.getAttrByKey(Keys.USER_INFO);
        // 根据设备ID和茶谱ID获取最近的用户沏茶记录
        Map<String, Object> params = Maps.newHashMap();
        params.put(Keys.DEVICE_ID, useTeaSpectrumReq.getDeviceId());
        params.put(Keys.TEAMAN_ID, user.getMemberId());
        params.put(Keys.CHAPU_ID, useTeaSpectrumReq.getChapuId());
        params.put(Query.SIDX, "add_time");
        params.put(Query.ORDER, "desc");
        params.put(Query.OFFSET, 0);
        params.put(Query.LIMIT, 1);
        List<UserMakeTeaEntity> userMakeTeaList = this.userMakeTeaService.queryList(params);
        if (ValidateUtils.isEmptyCollection(userMakeTeaList)) {
            throw new CommonException("用户沏茶记录不存在");
        }
        req.addAttr(Keys.USER_MAKTE_TEA_INFO, userMakeTeaList.get(0));*/
    }
}
