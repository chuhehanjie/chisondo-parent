package com.chisondo.server.modules.device.dto.resp;

import java.io.Serializable;

/**
 * 启动或预约泡茶响应DTO
 */
public class StartOrReserveTeaRespDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 预约号
     */
    private String reservNo;

    public String getReservNo() {
        return reservNo;
    }

    public void setReservNo(String reservNo) {
        this.reservNo = reservNo;
    }
}
