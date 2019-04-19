package com.chisondo.server.modules.device.validator;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.utils.Constant;
import com.chisondo.server.common.utils.Keys;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.common.validator.BusiValidator;
import com.chisondo.server.modules.user.entity.UserBookEntity;
import com.chisondo.server.modules.user.entity.UserVipEntity;
import com.chisondo.server.modules.user.service.UserBookService;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 取消预约
 */
@Component("cancelReservationValidator")
public class CancelReservationValidator implements BusiValidator {

    @Autowired
    private UserBookService userBookService;

    @Override
    public void validate(CommonReq req) {
        if (req.isOldDev()) {
            return;
        }
        JSONObject jsonObj = JSONObject.parseObject(req.getBizBody());
        String reservNo = jsonObj.getString("reservNo");
        if (ValidateUtils.isEmptyString(reservNo)) {
            throw new CommonException("预约号为空");
        }
        String deviceId = (String) req.getAttrByKey(Keys.DEVICE_ID);
        UserVipEntity user = (UserVipEntity) req.getAttrByKey(Keys.USER_INFO);
        // 根据设备ID和茶谱ID获取最近的用户沏茶记录
        Map<String, Object> params = Maps.newHashMap();
        params.put(Keys.DEVICE_ID, deviceId);
        params.put(Keys.TEAMAN_ID, user.getMemberId());
        params.put(Keys.RESERV_NO, reservNo);
        params.put(Keys.VALID_FLAG, Constant.UserBookStatus.VALID);
        List<UserBookEntity> userBookList = this.userBookService.queryList(params);
        if (ValidateUtils.isEmptyCollection(userBookList)) {
            throw new CommonException("用户沏茶预约记录不存在");
        }
        req.addAttr(Keys.USER_BOOK_INFO, userBookList.get(0));
    }
}
