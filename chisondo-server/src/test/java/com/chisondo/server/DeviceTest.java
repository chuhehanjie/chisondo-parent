package com.chisondo.server;

import com.chisondo.ServerApplication;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.datasources.DataSourceNames;
import com.chisondo.server.datasources.DynamicDataSource;
import com.chisondo.server.modules.device.entity.ActivedDeviceInfoEntity;
import com.chisondo.server.modules.device.service.ActivedDeviceInfoService;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerApplication.class)
public class DeviceTest {
    @Autowired
    private ActivedDeviceInfoService activedDeviceInfoService;

    @Test
    public void queryDeviceList() {
        DynamicDataSource.setDataSource(DataSourceNames.FIRST);
        List<ActivedDeviceInfoEntity> deviceList = this.activedDeviceInfoService.queryList(Maps.newHashMap());
        if (ValidateUtils.isNotEmptyCollection(deviceList)) {
            System.out.println("deviceList size = " + deviceList.size());
            Map<String, String> map = Maps.newHashMap();
            deviceList.forEach(device -> {
                map.put(device.getDeviceId().hashCode() + "", device.getDeviceId());
            });
            System.out.println("map size = " + map.size());
        }
    }

}
