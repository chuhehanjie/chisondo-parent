package com.chisondo.server;

import com.chisondo.ServerApplication;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.modules.device.entity.ActivedDeviceInfoEntity;
import com.chisondo.server.modules.device.service.ActivedDeviceInfoService;
import com.chisondo.server.modules.tea.entity.AppChapuEntity;
import com.chisondo.server.modules.tea.service.AppChapuService;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerApplication.class)
public class DeviceTest {
    @Autowired
    private ActivedDeviceInfoService activedDeviceInfoService;

    @Autowired
    private AppChapuService appChapuService;

    @Test
    public void queryDeviceList() {
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

    @Test
    public void queryChapuInfo() {
        StopWatch stopWatch = new StopWatch("查询茶谱列表");
        stopWatch.start();
        int [] chapuIds = {3, 4, 5, 6, 7, 8, 9};
        for (int chapuId : chapuIds) {
            AppChapuEntity teaSpectrum = this.appChapuService.queryObject(chapuId);
            System.out.println(teaSpectrum.getName());
        }
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

}
