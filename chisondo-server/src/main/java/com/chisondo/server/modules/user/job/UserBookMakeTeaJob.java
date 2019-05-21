package com.chisondo.server.modules.user.job;

import com.chisondo.server.common.utils.*;
import com.chisondo.server.modules.device.service.DeviceCtrlService;
import com.chisondo.server.modules.user.entity.UserBookEntity;
import com.chisondo.server.modules.user.service.UserBookService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class UserBookMakeTeaJob {

    @Autowired
    private UserBookService userBookService;

    @Autowired
    private DeviceCtrlService deviceCtrlService;

//    @Scheduled(cron = "0 0 0/1 * * ?")//一小时一次
    @Scheduled(cron = "0 0/1 * * * ?") // 每3分钟一次
    public void exectue(){
        if (log.isDebugEnabled()) {
            log.debug("查询用户沏茶预约记录开始");
        }
        // 查询当前要处理的沏茶预约数据
        Map<String, Object> params = Maps.newHashMap();
        params.put(Keys.VALID_FLAG, Constant.UserBookStatus.VALID);
        params.put(Query.PAGE, 1);
        params.put(Query.LIMIT, 10);
        params.put("notNull4ReservNo", true);
//        params.put("reserveTime", DateUtils.currentDate());
        List<UserBookEntity> userBookList = this.userBookService.queryList(new Query(params));
        if (ValidateUtils.isNotEmptyCollection(userBookList)) {
            this.deviceCtrlService.processReverseMakeTea(userBookList);
        }
        if (log.isDebugEnabled()) {
            log.debug("查询用户沏茶预约记录结束，共处理[{}]条记录", userBookList.size());
        }

    }
}
