package com.chisondo.iot.device.handler;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.iot.common.constant.Constant;
import com.chisondo.iot.common.utils.IOTUtils;
import com.chisondo.iot.device.request.DevStatusReportReq;
import com.chisondo.model.http.resp.DevSettingHttpResp;
import com.chisondo.model.http.resp.DevStatusReportResp;
import com.chisondo.model.http.req.DeviceHttpReq;
import com.chisondo.model.http.resp.DeviceHttpResp;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class DeviceServerDecoder extends StringDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        String json = IOTUtils.convertByteBufToString(msg);
        log.debug("decoder msg = " + json);
        json = this.filterCloseSymbol(json);
        if (this.isDevStatusReportReq(json)) {
            // 设备状态上报请求
            DevStatusReportReq reportReq = JSONObject.parseObject(json, DevStatusReportReq.class);
            out.add(reportReq);
        } else if (this.isQueryDevStatusResp(json)) {
            // 查询设备状态响应
            DevStatusReportResp reportResp = JSONObject.parseObject(json, DevStatusReportResp.class);
            out.add(reportResp);
        } else if (this.isQueryDevSettingResp(json)) {
            // 查询设备设置参数响应
            DevSettingHttpResp devSettingResp = JSONObject.parseObject(json, DevSettingHttpResp.class);
            out.add(devSettingResp);
        } else if (this.isStartWorkResp(json) || this.isLockOrUnlockDevResp(json) || this.isErrorResp(json)) {
            DeviceHttpResp deviceResp = JSONObject.parseObject(json, DeviceHttpResp.class);
            out.add(deviceResp);
        } else {
            // http request
            DeviceHttpReq req = JSONObject.parseObject(json, DeviceHttpReq.class);
            if (null != req.getAction() && null != req.getDeviceID()) {
                out.add(req);
            } else {
                out.add("test msg");
            }
        }
    }

    private String filterCloseSymbol(String json) {
        return json.endsWith(Constant.CLOSE_SYMBOL) ? json.replace(Constant.CLOSE_SYMBOL, "") : json;
    }

    public static void main(String[] args) {
        String json = "{\"action\":\"statuspush\",\"actionFlag\":1,\"deviceID\":\"18170964\",\"msg\":{\"errorstatus\":0,\"nowwarm\":65,\"remaintime\":\"580\",\"soak\":100,\"taststatus\":2,\"temperature\":70,\"warmstatus\":1,\"waterlevel\":150,\"workstatus\":1},\"oK\":false,\"retn\":0}\\n";
        if (json.endsWith(Constant.CLOSE_SYMBOL)) {
            json = json.replace(Constant.CLOSE_SYMBOL, "");
        }
        DevStatusReportReq reportReq = JSONObject.parseObject(json, DevStatusReportReq.class);
        System.out.println(reportReq.getDeviceID());
    }

    /**
     * 是否启动沏茶/洗茶/烧水响应
     * @param json
     * @return
     */
    private boolean isStartWorkResp(String json) {
        return json.contains("\"action\":\"startworkok\"");
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
     * 是否查询设备状态信息响应
     * @param json
     * @return
     */
    private boolean isQueryDevStatusResp(String json) {
        return json.contains("\"action\":\"qrydevicestateok\"");
    }

    /**
     * 是否查询设备设置参数响应
     * @param json
     * @return
     */
    private boolean isQueryDevSettingResp(String json) {
        return json.contains("\"action\":\"qrydevparmok\"");
    }

    /**
     * 是否锁定或解锁设备响应
     * @param json
     * @return
     */
    private boolean isLockOrUnlockDevResp(String json) {
        return json.contains("\"action\":\"devicelockok\"");
    }

    private boolean isErrorResp(String json) {
        return json.contains("\"retn\":500");
    }
}
