package com.chisondo.server.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.http.CommonResp;
import com.chisondo.server.modules.device.dto.req.DevStatusReportReq;
import com.chisondo.server.modules.device.dto.resp.DevStatusRespDTO;
import com.chisondo.server.modules.device.entity.DeviceStateInfoEntity;
import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ding.zhong
 * @since Mar 26.18
 */
public final class CommonUtils {
    public static Map<String, Object> buildMapByKeyValue(String [] keys, Object ... values) {
        Map<String, Object> resultMap = new HashMap<>();
        for (int i = 0; i < keys.length; i++) {
            resultMap.put(keys[i], values[i]);
        }
        return resultMap;
    }

    public static String getCompanyNameById(Integer id) {
        // TODO 先写死，后续配成字典
        if (id == 1) {
            return "湘丰集团";
        } else if (id == 2) {
            return "静硒园";
        }
        return "泉笙道";
    }

    /**
     * 判断是否为老设备
     * @param deviceId
     * @return
     */
    public static boolean isOldDevice(String deviceId) {
        // TODO 具体判断规则待定
        return false;
    }

   /* public static String getJSONFromRequest(HttpServletRequest req) throws IOException {
        InputStream inputStream = req.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int flag = 0;
        while ((flag = inputStream.read(bytes)) > 0){
            byteArrayOutputStream.write(bytes,0,flag);
        }
        return new String(byteArrayOutputStream.toByteArray(),req.getCharacterEncoding());
    }*/

    public static void outJSONResponse(HttpServletResponse response, Object obj) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(JSONObject.toJSONString(obj));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(out);
        }
    }

    public static JSONObject convertReq2JSONObj(CommonReq req) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("acckey", req.getAcckey());
        jsonObj.put("reqsrc", req.getReqsrc());
        jsonObj.put("timestamp", req.getTimestamp());
        jsonObj.put("bizBody", req.getBizBody());
        return jsonObj;
    }

    public static JSONObject convertResp2JSONObj(CommonResp resp) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("retn", resp.getRetn());
        jsonObj.put("desc", resp.getDesc());
        jsonObj.put("bizBody", resp.getBizBody());
        return jsonObj;
    }

    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "chris");
        jsonObject.put("job", "java");
        System.out.println(jsonObject);
    }

    public static Map<String, Object> getPageParams(JSONObject jsonObj) {
        Map<String, Object> params = Maps.newHashMap();
        params.put(Keys.PAGE, ValidateUtils.isNotEmpty(jsonObj.get(Keys.PAGE)) ? jsonObj.get(Keys.PAGE) : "1");
        params.put(Keys.LIMIT, ValidateUtils.isNotEmpty(jsonObj.get(Keys.LIMIT)) ? jsonObj.get(Keys.LIMIT) : "10");
        return params;
    }

    public static DevStatusRespDTO convert2DevStatusInfo(DevStatusReportReq devStatusReportReq, DeviceStateInfoEntity devStateInfo) {
        DevStatusRespDTO devStatusResp = new DevStatusRespDTO();
        devStatusResp.setTemp(devStatusReportReq.getMsg().getTemperature());
        devStatusResp.setWarm(devStatusReportReq.getMsg().getWarmstatus());
        devStatusResp.setDensity(devStatusReportReq.getMsg().getTaststatus());
        devStatusResp.setWaterlv(devStatusReportReq.getMsg().getWaterlevel());
        devStatusResp.setMakeDura(devStatusReportReq.getMsg().getSoak());
        devStatusResp.setReamin(Integer.valueOf(devStatusReportReq.getMsg().getRemaintime()));
        devStatusResp.setTea(Constant.ErrorStatus.LACK_TEA == devStatusReportReq.getMsg().getErrorstatus() ? 1 : 0);
        devStatusResp.setWater(Constant.ErrorStatus.LACK_WATER == devStatusReportReq.getMsg().getErrorstatus() ? 1 : 0);
        devStatusResp.setWork(devStatusReportReq.getMsg().getWorkstatus());
        devStatusResp.setMakeTemp(devStateInfo.getMakeTemp());
        devStatusResp.setChapuId(devStateInfo.getChapuId());
        devStatusResp.setChapuName(devStateInfo.getChapuName());
        devStatusResp.setChapuImage(devStateInfo.getChapuImage());
        devStatusResp.setChapuMakeTimes(devStateInfo.getChapuMakeTimes());
        devStatusResp.setTeaSortId(devStateInfo.getTeaSortId());
        devStatusResp.setTeaSortName(devStateInfo.getTeaSortName());
        devStatusResp.setIndex(devStateInfo.getIndex());
        devStatusResp.setMakeType(devStateInfo.getMakeType());
        return devStatusResp;
    }

    public static DevStatusRespDTO convert2DevStatusInfo(DeviceStateInfoEntity devStateInfo) {
        DevStatusRespDTO devStatusResp = new DevStatusRespDTO();
        devStatusResp.setMakeTemp(devStateInfo.getMakeTemp());
        devStatusResp.setTemp(devStateInfo.getTemp());
        devStatusResp.setWarm(devStateInfo.getWarm());
        devStatusResp.setDensity(devStateInfo.getDensity());
        devStatusResp.setWaterlv(devStateInfo.getWaterlv());
        devStatusResp.setMakeDura(devStateInfo.getMakeDura());
        devStatusResp.setReamin(devStateInfo.getReamin());
        devStatusResp.setTea(devStateInfo.getTea());
        devStatusResp.setWater(devStateInfo.getWater());
        devStatusResp.setWork(devStateInfo.getWork());
        devStatusResp.setChapuId(devStateInfo.getChapuId());
        devStatusResp.setChapuName(devStateInfo.getChapuName());
        devStatusResp.setChapuImage(devStateInfo.getChapuImage());
        devStatusResp.setChapuMakeTimes(devStateInfo.getChapuMakeTimes());
        devStatusResp.setTeaSortId(devStateInfo.getTeaSortId());
        devStatusResp.setTeaSortName(devStateInfo.getTeaSortName());
        devStatusResp.setIndex(devStateInfo.getIndex());
        devStatusResp.setMakeType(devStateInfo.getMakeType());
        devStatusResp.setOnlineStatus(devStateInfo.getOnlineState());
        devStatusResp.setConnStatus(devStateInfo.getConnectState());
        return devStatusResp;
    }

    public static CommonResp buildOldDevResp(JSONObject result) {
        return new CommonResp(result.getIntValue("retn"), result.getString("desc"));
    }
}
