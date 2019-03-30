package com.chisondo.server;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.ServerApplication;
import com.chisondo.server.modules.user.entity.UserDeviceEntity;
import com.chisondo.server.modules.user.entity.UserSchemeEntity;
import com.chisondo.server.modules.user.service.UserDeviceService;
import com.chisondo.server.modules.user.service.UserSchemeService;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerApplication.class)
public class UserTest {
    @Autowired
    private UserSchemeService userSchemeService;

    @Autowired
    private UserDeviceService userDeviceService;

    @Test
    public void queryUserSchemeList() {
        List<UserSchemeEntity> schemeEntities = this.userSchemeService.queryList(Maps.newHashMap());
        System.out.println("test result：" + JSONObject.toJSONString(schemeEntities));
    }

    @Test
    public void queryUserDeviceList() {
        List<UserDeviceEntity> userDeviceList = this.userDeviceService.queryList(Maps.newHashMap());
        System.out.println("查询用户与设备列表：" + JSONObject.toJSONString(userDeviceList));
    }

}
