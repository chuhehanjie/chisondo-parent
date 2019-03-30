package com.chisondo.iot.device.handler;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.iot.common.utils.IOTUtils;
import com.chisondo.iot.device.request.DevStatusReportReq;
import com.chisondo.model.http.req.DeviceHttpReq;
import com.chisondo.model.http.resp.DeviceHttpResp;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.string.StringDecoder;

import java.util.List;

public class DeviceServerDecoder extends StringDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        String json = IOTUtils.convertByteBufToString(msg);
        System.out.println("decoder msg = " + json);
        // 是否设备状态上报请求
        if (this.isDevStatusReportReq(json)) {
            DevStatusReportReq reportReq = JSONObject.parseObject(json, DevStatusReportReq.class);
            out.add(reportReq);
        } else if (this.isQueryDevStatusResp(json) || this.isErrorResp(json)) {
            DeviceHttpResp deviceResp = JSONObject.parseObject(json, DeviceHttpResp.class);
            out.add(deviceResp);
        } else {
            // http request
            DeviceHttpReq req = JSONObject.parseObject(json, DeviceHttpReq.class);
            if (null != req.getAction() && null != req.getDeviceId()) {
                out.add(req);
            } else {
                out.add("test msg");
            }
        }
    }

    /**
     * 是否设备状态上报请求
     * @param json
     * @return
     */
    private boolean isDevStatusReportReq(String json) {
        return json.contains("\"action\":\"statuspush\"");
    }

    /**
     * 是否查询设备状态响应
     * @param json
     * @return
     */
    private boolean isQueryDevStatusResp(String json) {
        return json.contains("\"action\":\"cancelwarmok\"");
    }

    private boolean isErrorResp(String json) {
        return json.contains("\"retn\":500");
    }
}
