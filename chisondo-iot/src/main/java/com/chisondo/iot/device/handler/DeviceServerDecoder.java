package com.chisondo.iot.device.handler;
import com.alibaba.fastjson.JSONException;
import com.chisondo.model.http.resp.*;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.iot.common.constant.Constant;
import com.chisondo.iot.common.utils.IOTUtils;
import com.chisondo.iot.device.request.DevStatusReportReq;
import com.chisondo.model.http.req.DeviceHttpReq;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class DeviceServerDecoder extends StringDecoder {

    public DeviceServerDecoder(Charset charset) {
        super(charset);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        String json = IOTUtils.convertByteBufToString(msg);
        log.error("decoder msg 接收设备响应 = " + json);
        if (StringUtils.isEmpty(json)) {
            return;
        }
        json = this.filterCloseSymbol(json);
        if (this.isDeviceIdEmpty(json)) {
            log.error("设备ID为空！");
            return;
        }
        this.decodeResponse(out, json);
    }

    private void decodeResponse(List<Object> out, String json) {
        try {
            if (this.isDevStatusReportReq(json)) {
                // 设备状态上报请求
                DevStatusReportReq reportReq = JSONObject.parseObject(json, DevStatusReportReq.class);
                out.add(reportReq);
            } else if (this.isQueryDevStatusResp(json)) {
                // 查询设备状态响应
                DevStatusReportResp reportResp = JSONObject.parseObject(json, DevStatusReportResp.class);
                out.add(reportResp);
            } else if (this.isQueryDevChapuInfoResp(json)) {
                // 查询设备茶谱信息响应
                DevChapuHttpResp reportResp = JSONObject.parseObject(json, DevChapuHttpResp.class);
                out.add(reportResp);
            } else if (this.isQueryDevSettingResp(json)) {
                // 查询设备设置参数响应
                DevSettingHttpResp devSettingResp = JSONObject.parseObject(json, DevSettingHttpResp.class);
                out.add(devSettingResp);
            } else if (this.isStartWorkResp(json) || this.isStopWorkResp(json) || this.isLockOrUnlockDevResp(json) || this.isKeepWarmCtrlResp(json)
                    || this.isSetChapuParamResp(json) || this.isSetOtherParamResp(json) || this.isSetParamResp(json) || this.isErrorResp(json)) {
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
        } catch (JSONException e) {
            log.error("JSON格式错误！", e);
        }
    }

    private boolean isDeviceIdEmpty(String json) {
        return !json.contains("deviceID") || json.contains("\"deviceID\":\"\"");
    }

    private String filterCloseSymbol(String json) {
        String resultJSON = json.endsWith(Constant.CLOSE_SYMBOL) ? json.replace(Constant.CLOSE_SYMBOL, "") : json;
        return resultJSON.replaceAll("\n", "").replaceAll("\t", "");
    }

    public static void main(String[] args) {
        /*String json = "{\"statuspush\",\"actionFlag\":1,\"deviceID\":\"18170964\",\"msg\":{\"errorstatus\":0,\"nowwarm\":65,\"remaintime\":\"580\",\"soak\":100,\"taststatus\":2,\"temperature\":70,\"warmstatus\":1,\"waterlevel\":150,\"workstatus\":1},\"oK\":false,\"retn\":0}\\n";
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
        devSettingResp.setChapumsg(Arrays.asList(chapuMsg));
        devSettingResp.setRetn(200);
        devSettingResp.setDesc("请求成功");
        System.out.println(JSONObject.toJSONString(devSettingResp));
        System.out.println("*************************************************");
        String json2 = "{\"retn\":200, \"desc\":\"success\", \"startworkok\",\"deviceID\":\"71823MJ890KB\",\"msg\":{\"state\":0,\"stateinfo\":0,\"errorstatus\":0,\"nowwarm\":65,\"remaintime\":\"20\",\"soak\":100,\"taststatus\":2,\"temperature\":70,\"warmstatus\":1,\"waterlevel\":150,\"workstatus\":1}}";
        //String json2 = "{\"retn\":200, \"desc\":\"success\", \"startworkok\",\"deviceID\":\"71823MJ890KB\"}";
        DeviceHttpResp deviceResp = JSONObject.parseObject(json2, DeviceHttpResp.class);
        System.out.println("deviceResp json = " + JSONObject.toJSONString(deviceResp));*/
        String json = "{\n" +
                "\t\t\"statuspush\",\n" +
                "\t\"actionFlag\":\t1,\n" +
                "\t\"deviceID\":\t\"533969898238259\",\n" +
                "\t\"msg\":\t{\n" +
                "\t\t\"errorstatus\":\t0,\n" +
                "\t\t\"nowwarm\":\t27,\n" +
                "\t\t\"remaintime\":\t0,\n" +
                "\t\t\"soak\":\t60,\n" +
                "\t\t\"taststatus\":\t2,\n" +
                "\t\t\"temperature\":\t99,\n" +
                "\t\t\"warmstatus\":\t0,\n" +
                "\t\t\"waterlevel\":\t400,\n" +
                "\t\t\"workstatus\":\t0\n" +
                "\t}\n" +
                "}\n";
        DeviceServerDecoder app = new DeviceServerDecoder(CharsetUtil.UTF_8);
        json = app.filterCloseSymbol(json);
        app.decodeResponse(new ArrayList<>(), json);
    }

    /**
     * 是否启动沏茶/洗茶/烧水响应
     * @param json
     * @return
     */
    private boolean isStartWorkResp(String json) {
        return json.contains("\"startworkok\"");
    }

    /**
     * 是否停止沏茶/洗茶/烧水响应
     * @param json
     * @return
     */
    private boolean isStopWorkResp(String json) {
        return json.contains("\"stopworkok\"");
    }

    /**
     * 是否设备状态上报请求
     * @param json
     * @return
     */
    private boolean isDevStatusReportReq(String json) {
        return json.contains("\"statuspush\"");
    }

    /**
     * 是否查询设备状态信息响应
     * @param json
     * @return
     */
    private boolean isQueryDevStatusResp(String json) {
        return json.contains("\"qrydevicestateok\"");
    }

    /**
     * 是否查询设备茶谱信息响应
     * @param json
     * @return
     */
    @Deprecated
    private boolean isQueryDevChapuInfoResp(String json) {
        return json.contains("\"qrychapuparmok\"");
    }

    /**
     * 是否查询设备设置参数响应
     * @param json
     * @return
     */
    private boolean isQueryDevSettingResp(String json) {
        return json.contains("\"qrydevparmok\"");
    }

    /**
     * 是否锁定或解锁设备响应
     * @param json
     * @return
     */
    private boolean isLockOrUnlockDevResp(String json) {
        return json.contains("\"devicelockok\"");
    }

    /**
     * 是否保温控制响应
     * @param json
     * @return
     */
    private boolean isKeepWarmCtrlResp(String json) {
        return json.contains("\"keepwarmok\"");
    }

    /**
     * 是否设置内置茶谱参数响应
     * @param json
     * @return
     */
    private boolean isSetChapuParamResp(String json) {
        return json.contains("\"setchapuparmok\"");
    }

    /**
     * 是否设置静音/网络响应
     * @param json
     * @return
     */
    private boolean isSetOtherParamResp(String json) {
        return json.contains("\"setotherparmok\"");
    }
 /**
     * 是否设置洗茶/烧水参数响应
     * @param json
     * @return
     */
    private boolean isSetParamResp(String json) {
        return json.contains("\"setdevparmok\"");
    }

    private boolean isErrorResp(String json) {
        return json.contains("\"retn\":500");
    }

}
