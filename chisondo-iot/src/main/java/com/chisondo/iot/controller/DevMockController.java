package com.chisondo.iot.controller;

import com.chisondo.iot.device.server.DevTcpChannelManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import test.moniTerminal.MockTerminal;

@RestController
public class DevMockController {

    @RequestMapping("/api/rest/enableMockDev")
    public String enableMockDev(@RequestParam String deviceId) {
        if (null == DevTcpChannelManager.getChannelByDeviceId(deviceId)) {
            MockTerminal.mockClientDev(deviceId);
        }
        return "OK";
    }
}
