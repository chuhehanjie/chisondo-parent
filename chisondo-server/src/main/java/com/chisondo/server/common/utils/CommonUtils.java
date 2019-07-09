package com.chisondo.server.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.model.http.HttpStatus;
import com.chisondo.model.http.resp.DevStatusReportResp;
import com.chisondo.model.http.resp.DeviceHttpResp;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.common.http.CommonResp;
import com.chisondo.model.http.resp.DevStatusRespDTO;
import com.chisondo.server.modules.device.dto.resp.DeviceInfoRespDTO;
import com.chisondo.server.modules.device.entity.DeviceStateInfoEntity;
import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

    public static DevStatusRespDTO convert2DevStatusDTO(DevStatusReportResp devStatusReportResp, DeviceStateInfoEntity devStateInfo) {
        DevStatusRespDTO devStatusResp = new DevStatusRespDTO();
        devStatusResp.setDeviceId(devStatusReportResp.getDeviceID());
        devStatusResp.setTemp(devStatusReportResp.getMsg().getTemperature());
        devStatusResp.setWarm(devStatusReportResp.getMsg().getWarmstatus());
        devStatusResp.setDensity(devStatusReportResp.getMsg().getTaststatus());
        devStatusResp.setWaterlv(devStatusReportResp.getMsg().getWaterlevel());
        devStatusResp.setMakeDura(devStatusReportResp.getMsg().getSoak());
        devStatusResp.setReamin(Integer.valueOf(devStatusReportResp.getMsg().getRemaintime()));
        devStatusResp.setTea(Constant.ErrorStatus.LACK_TEA == devStatusReportResp.getMsg().getErrorstatus() ? 1 : 0);
        devStatusResp.setWater(Constant.ErrorStatus.LACK_WATER == devStatusReportResp.getMsg().getErrorstatus() ? 1 : 0);
        devStatusResp.setWork(devStatusReportResp.getMsg().getWorkstatus());
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

    public static DevStatusRespDTO convert2DevStatusDTO(DeviceStateInfoEntity devStateInfo) {
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

    public static DeviceStateInfoEntity convert2DevStatusEntity(DevStatusReportResp devStatusReportResp, DeviceStateInfoEntity existedDevState) {
        DeviceStateInfoEntity devStateInfo = ValidateUtils.isNotEmpty(existedDevState) ? existedDevState : new DeviceStateInfoEntity();
        devStateInfo.setDeviceId(devStatusReportResp.getDbDeviceId());
        devStateInfo.setNewDeviceId(devStatusReportResp.getDeviceID());
        if (ValidateUtils.isNotEmpty(devStatusReportResp.getMsg().getChapuId())) {
            devStateInfo.setChapuId(devStatusReportResp.getMsg().getChapuId());
        }
        if (ValidateUtils.isNotEmpty(devStatusReportResp.getMsg().getStep())) {
            devStateInfo.setIndex(devStatusReportResp.getMsg().getStep());
        }
//		devStateInfo.setDeviceStateInfo("");
        devStateInfo.setLastValTime(devStatusReportResp.getTcpValTime());
        devStateInfo.setMakeTemp(devStatusReportResp.getMsg().getTemperature());
        devStateInfo.setTemp(devStatusReportResp.getMsg().getTemperature());
        devStateInfo.setWarm(devStatusReportResp.getMsg().getWarmstatus());
        devStateInfo.setDensity(devStatusReportResp.getMsg().getTaststatus());
        devStateInfo.setWaterlv(devStatusReportResp.getMsg().getWaterlevel());
        devStateInfo.setMakeDura(devStatusReportResp.getMsg().getSoak());
        devStateInfo.setReamin(Integer.valueOf(devStatusReportResp.getMsg().getRemaintime()));
        devStateInfo.setTea(Constant.ErrorStatus.LACK_TEA == devStatusReportResp.getMsg().getErrorstatus() ? 1 : 0);
        devStateInfo.setWater(Constant.ErrorStatus.LACK_WATER == devStatusReportResp.getMsg().getErrorstatus() ? 1 : 0);
        devStateInfo.setWork(devStatusReportResp.getMsg().getWorkstatus());
        return devStateInfo;
    }

    public static DeviceStateInfoEntity convert2DevStatusEntity(DeviceHttpResp devStatusReportResp, String deviceId) {
        DeviceStateInfoEntity devStateInfo = new DeviceStateInfoEntity();
        devStateInfo.setDeviceId(deviceId);
        devStateInfo.setLastConnTime(DateUtils.currentDate()); // TODO 上次连接时间应该取上条记录的更新时间
        devStateInfo.setUpdateTime(DateUtils.currentDate());
        devStateInfo.setNewDeviceId(devStatusReportResp.getDeviceID());
        devStateInfo.setMakeTemp(devStatusReportResp.getMsg().getTemperature());
        devStateInfo.setTemp(devStatusReportResp.getMsg().getTemperature());
        devStateInfo.setWarm(devStatusReportResp.getMsg().getWarmstatus());
        devStateInfo.setDensity(devStatusReportResp.getMsg().getTaststatus());
        devStateInfo.setWaterlv(devStatusReportResp.getMsg().getWaterlevel());
        devStateInfo.setMakeDura(devStatusReportResp.getMsg().getSoak());
        devStateInfo.setReamin(Integer.valueOf(devStatusReportResp.getMsg().getRemaintime()));
        devStateInfo.setTea(Constant.ErrorStatus.LACK_TEA == devStatusReportResp.getMsg().getErrorstatus() ? 1 : 0);
        devStateInfo.setWater(Constant.ErrorStatus.LACK_WATER == devStatusReportResp.getMsg().getErrorstatus() ? 1 : 0);
        devStateInfo.setWork(devStatusReportResp.getMsg().getWorkstatus());
        return devStateInfo;
    }

    public static CommonResp buildOldDevResp(JSONObject result) {
        return new CommonResp(0 == result.getIntValue("STATE") ? HttpStatus.SC_OK : HttpStatus.SC_INTERNAL_SERVER_ERROR, result.getString("STATE_INFO"));
    }

    public static String plusFullImgPath(String srcImg) {
        String imgPrefix = CacheDataUtils.getImgPathPrefix();
        if (ValidateUtils.isNotEmptyString(srcImg)) {
            if (!srcImg.startsWith("http://") && !srcImg.startsWith("https://")) {
                return imgPrefix + srcImg;
            }
        }
        return srcImg;
    }

    public static void debugLog(Logger log, String msg) {
        if (log.isDebugEnabled()) {
            log.debug(msg);
        }
    }

    public static void processDevTypeAndOnlineStatus(List<DeviceInfoRespDTO> deviceDetails) {
        if (ValidateUtils.isNotEmptyCollection(deviceDetails)) {
            deviceDetails.forEach(deviceDetail -> {
                if (ValidateUtils.equals(Constant.OnlineState.YES, deviceDetail.getOnlineStatus())) {
                    // 设备在线，且设备类型为 WIFI
                    if (ValidateUtils.equals(1, deviceDetail.getDeviceType())) {
                        // 老设备则表示 WIFI 在线
                        deviceDetail.setOnlineStatus(1);
                        deviceDetail.setDeviceType(0); // WIFI 设备
                    } else  {
                        // 2G 和 WIFI 都在线
                        deviceDetail.setOnlineStatus(2);
                        deviceDetail.setDeviceType(1); // 2G 设备
                        deviceDetail.setWiFiSSID("");
                    }
                } else {
                    deviceDetail.setWiFiSSID("");
                }
            });
        }
    }

    /**
     * 获取随机验证码
     *
     * @return
     */
    public static String getValidationCode(int length) {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(rand.nextInt(10));
        }
        return sb.toString();
    }

    public static boolean isOldDevice(String deviceId) {
        return deviceId.length() <= 8;
    }

    public static String convertDateStr(String dateStr) {
        return ValidateUtils.isNotEmptyString(dateStr) && dateStr.length() > 19 ? dateStr.substring(0, 19) : dateStr;
    }

    public static DeviceStateInfoEntity buildDevStateByDevStatusResp(DevStatusRespDTO devStatusResp, String deviceId) {
        DeviceStateInfoEntity deviceState = new DeviceStateInfoEntity();
        deviceState.setDeviceId(deviceId);
        deviceState.setUpdateTime(DateUtils.currentDate());
        deviceState.setLastConnTime(DateUtils.currentDate());
        deviceState.setMakeTemp(devStatusResp.getMakeTemp());
        deviceState.setTemp(devStatusResp.getTemp());
        deviceState.setWarm(devStatusResp.getWarm());
        deviceState.setDensity(devStatusResp.getDensity());
        deviceState.setWaterlv(devStatusResp.getWaterlv());
        deviceState.setMakeDura(devStatusResp.getMakeDura());
        deviceState.setReamin(devStatusResp.getReamin());
        deviceState.setTea(devStatusResp.getTea());
        deviceState.setWater(devStatusResp.getWater());
        deviceState.setWork(devStatusResp.getWork());
        return deviceState;
    }
}
