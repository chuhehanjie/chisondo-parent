package com.chisondo.server;

import com.alibaba.fastjson.JSONObject;
import com.chisondo.server.common.http.CommonReq;
import com.chisondo.server.modules.device.dto.req.DeviceBindReqDTO;
import com.chisondo.server.modules.device.dto.req.UseTeaSpectrumReqDTO;
import com.chisondo.server.modules.device.dto.req.StartOrReserveMakeTeaReqDTO;

public class DeviceReqBuilder {

    public static void main(String[] args) {
        System.out.println(JSONObject.toJSONString(getDevBindReq()));
        System.out.println(JSONObject.toJSONString(getStartOrReserveTeaReq()));
        System.out.println(JSONObject.toJSONString(getMakeTeaByTeaSpectrumReq()));
    }

    public static CommonReq getMakeTeaByTeaSpectrumReq() {
        CommonReq req = new CommonReq();
        req.setAcckey("71823");
        req.setReqsrc(0);
        req.setTimestamp(System.currentTimeMillis() + "");
        UseTeaSpectrumReqDTO makeTeaReq = new UseTeaSpectrumReqDTO();
        makeTeaReq.setDeviceId("17048865");
        makeTeaReq.setPhoneNum("18975841003");
        makeTeaReq.setChapuId(3);
        makeTeaReq.setIndex(1);
        String bizBody = JSONObject.toJSONString(makeTeaReq);
        req.setBizBody(bizBody);
        return req;
    }

    public static CommonReq getStartOrReserveTeaReq() {
        CommonReq req = new CommonReq();
        req.setAcckey("7788520");
        req.setReqsrc(0);
        req.setTimestamp(System.currentTimeMillis() + "");
        StartOrReserveMakeTeaReqDTO teaReqDTO = new StartOrReserveMakeTeaReqDTO();
        teaReqDTO.setDeviceId("17048865");
        teaReqDTO.setPhoneNum("15873230762");
        teaReqDTO.setStartTime("2019-03-18 01:38:30");
        teaReqDTO.setTemperature(68);
        teaReqDTO.setSoak(0);
        teaReqDTO.setWaterlevel(200);
        teaReqDTO.setTeaSortId(2);
        teaReqDTO.setTeaSortName("绿茶");
        String bizBody = JSONObject.toJSONString(teaReqDTO);
        req.setBizBody(bizBody);
        return req;
    }
    public static CommonReq getDevBindReq() {
        CommonReq req = new CommonReq();
        req.setAcckey("123456");
        req.setReqsrc(0);
        req.setTimestamp(System.currentTimeMillis() + "");
        DeviceBindReqDTO devBindReq = new DeviceBindReqDTO();
        devBindReq.setDeviceId("17048865");
        devBindReq.setPhoneNum("18975841003");
        devBindReq.setCompanyId(0);
        devBindReq.setLongitude("113.0795");
        devBindReq.setLatitude("28.2503");
        devBindReq.setProvince("湖南省");
        devBindReq.setCity("长沙市");
        devBindReq.setDistrict("长沙县");
        devBindReq.setDetaddress("中国湖南省长沙市长沙县天华路139");
        devBindReq.setPasswd("7788520");
        String bizBody = JSONObject.toJSONString(devBindReq);
        req.setBizBody(bizBody);
        return req;
    }
}
