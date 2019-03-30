package com.chisondo.server.modules.device.dto.req;

/**
 * 启动或预约泡茶请求DTO
 */
public class StartOrReserveMakeTeaReqDTO extends DeviceCtrlReqDTO {
    private String startTime; // 启动时间	大于当前时间24小时内,YYYY-MM-DD HH:MM24:SS，当没有该参数时，表示立即启动

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
