package com.chisondo.iot.device.handler;
import com.chisondo.model.http.resp.DevSettingMsgResp;
import com.chisondo.model.http.resp.TeaSpectrumMsgResp;
import com.chisondo.model.http.resp.DevParamMsg;

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

import java.nio.charset.Charset;
import java.util.List;

@Slf4j
public class DeviceServerDecoder extends StringDecoder {

    public DeviceServerDecoder(Charset charset) {
        super(charset);
    }

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
        } else if (this.isStartWorkResp(json) || this.isStopWorkResp(json) || this.isLockOrUnlockDevResp(json) || this.isKeepWarmCtrlResp(json)
                || this.isSetChapuParamResp(json) || this.isSetOtherParamResp(json) || this.isErrorResp(json)) {
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
        DevSettingHttpResp devSettingResp = new DevSettingHttpResp();
        devSettingResp.setAction("qrydevparmok");
        devSettingResp.setDeviceID("987654321");
        DevSettingMsgResp msg = new DevSettingMsgResp();
        msg.setState(0);
        msg.setStateinfo(0);
        msg.setVolflag(1);
        msg.setGmsflag(1);
        devSettingResp.setMsg(msg);
        DevParamMsg washteaMsg = new DevParamMsg();
        washteaMsg.setTemperature(65);
        washteaMsg.setSoak(200);
        washteaMsg.setWaterlevel(300);
        devSettingResp.setWashteamsg(washteaMsg);
        DevParamMsg boilwaterMsg = new DevParamMsg();
        boilwaterMsg.setTemperature(70);
        boilwaterMsg.setSoak(250);
        boilwaterMsg.setWaterlevel(350);
        devSettingResp.setBoilwatermsg(boilwaterMsg);
        TeaSpectrumMsgResp chapuMsg = new TeaSpectrumMsgResp();
        chapuMsg.setIndex(2);
        chapuMsg.setChapuid(3);
        chapuMsg.setChapuname("乌龙茶-浓");
        chapuMsg.setMaketimes(1);
        DevParamMsg teaParam = new DevParamMsg();
        teaParam.setTemperature(75);
        teaParam.setSoak(300);
        teaParam.setWaterlevel(400);
        chapuMsg.setTeaparm(teaParam);
        devSettingResp.setChapumsg(chapuMsg);
        devSettingResp.setRetn(200);
        devSettingResp.setDesc("请求成功");
        System.out.println(JSONObject.toJSONString(devSettingResp));
        System.out.println("*************************************************");
        String json2 = "{\"retn\":200, \"desc\":\"success\", \"action\":\"startworkok\",\"deviceID\":\"71823MJ890KB\",\"msg\":{\"state\":0,\"stateinfo\":0,\"errorstatus\":0,\"nowwarm\":65,\"remaintime\":\"20\",\"soak\":100,\"taststatus\":2,\"temperature\":70,\"warmstatus\":1,\"waterlevel\":150,\"workstatus\":1}}";
        //String json2 = "{\"retn\":200, \"desc\":\"success\", \"action\":\"startworkok\",\"deviceID\":\"71823MJ890KB\"}";
        DeviceHttpResp deviceResp = JSONObject.parseObject(json2, DeviceHttpResp.class);
        System.out.println("deviceResp json = " + JSONObject.toJSONString(deviceResp));
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
     * 是否停止沏茶/洗茶/烧水响应
     * @param json
     * @return
     */
    private boolean isStopWorkResp(String json) {
        return json.contains("\"action\":\"stopworkok\"");
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

    /**
     * 是否保温控制响应
     * @param json
     * @return
     */
    private boolean isKeepWarmCtrlResp(String json) {
        return json.contains("\"action\":\"keepwarmok\"");
    }

    /**
     * 是否设置内置茶谱参数响应
     * @param json
     * @return
     */
    private boolean isSetChapuParamResp(String json) {
        return json.contains("\"action\":\"setchapuparmok\"");
    }

    /**
     * 是否设置静音/网络响应
     * @param json
     * @return
     */
    private boolean isSetOtherParamResp(String json) {
        return json.contains("\"action\":\"setotherparmok\"");
    }

    private boolean isErrorResp(String json) {
        return json.contains("\"retn\":500");
    }

}
