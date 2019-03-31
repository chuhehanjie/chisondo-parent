package com.chisondo.server.modules.device.validator;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.utils.Keys;
import com.chisondo.server.common.utils.Query;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.common.validator.BusiValidator;
import com.chisondo.server.modules.device.dto.req.UseTeaSpectrumReqDTO;
import com.chisondo.server.modules.tea.entity.AppChapuEntity;
import com.chisondo.server.modules.tea.service.AppChapuService;
import com.chisondo.server.modules.user.entity.UserMakeTeaEntity;
import com.chisondo.server.modules.user.entity.UserVipEntity;
import com.chisondo.server.modules.user.service.UserMakeTeaService;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 使用茶谱沏茶校验器
 */
@Component("useTeaSpectrumValidator")
public class UseTeaSpectrumValidator implements BusiValidator {

    @Autowired
    private AppChapuService appChapuService;

    @Autowired
    private UserMakeTeaService userMakeTeaService;
    @Override
    public void validate(CommonReq req) {
        UseTeaSpectrumReqDTO useTeaSpectrumReq = JSONObject.parseObject(req.getBizBody(), UseTeaSpectrumReqDTO.class);
        if (ValidateUtils.isEmpty(useTeaSpectrumReq.getChapuId())) {
            throw new CommonException("茶谱ID为空");
        }
        UserVipEntity user = (UserVipEntity) req.getAttrByKey(Keys.USER_INFO);
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
        req.addAttr(Keys.USER_MAKTE_TEA_INFO, userMakeTeaList.get(0));
    }
}
