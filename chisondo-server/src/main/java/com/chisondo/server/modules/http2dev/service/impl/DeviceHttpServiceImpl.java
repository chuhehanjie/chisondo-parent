package com.chisondo.server.modules.http2dev.service.impl;

import com.chisondo.model.constant.DevReqURIConstant;
import com.chisondo.model.http.req.*;
import com.chisondo.model.http.resp.*;
import com.chisondo.server.common.utils.Constant;
import com.chisondo.server.common.utils.RedisUtils;
import com.chisondo.server.common.utils.RestTemplateUtils;
import com.chisondo.server.modules.http2dev.service.DeviceHttpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

@Service("http2DevService")
@Slf4j
public class DeviceHttpServiceImpl implements DeviceHttpService {

    @Autowired
    private RestTemplateUtils restTemplateUtils;

    @Autowired
    private RedisUtils redisUtils;

    @Value("${chisondo.server.http2DevURL}")
    private String http2DevURL;

    private DeviceHttpResp deviceControl(String url, Object req, String deviceId) {
        long startTime = System.currentTimeMillis();
        try {
            this.updateDevConnectState(deviceId, Constant.ConnectState.CONNECTED);
//            this.deviceStateInfoService.updateConnectState(deviceId, Constant.ConnectState.CONNECTED);
            DeviceHttpResp resp = this.restTemplateUtils.httpPostMediaTypeJson(url, DeviceHttpResp.class, req);
            log.info("设备 [{}] 操作 [{}] 耗时 [{}] 毫秒", deviceId, resp.getAction(), (System.currentTimeMillis() - startTime));
            return resp;
        } catch (RestClientException e) {
            log.error("设备控制请求异常！", e);
            return new DeviceHttpResp(HttpStatus.SC_INTERNAL_SERVER_ERROR, "设备控制请求异常！");
        } catch (Exception e) {
            log.error("设备控制请求异常！", e);
            return new DeviceHttpResp(HttpStatus.SC_INTERNAL_SERVER_ERROR, "设备控制请求异常！");
        } finally {
            // 将设备连接状态改为未连接
            // this.deviceStateInfoService.updateConnectState(deviceId, Constant.ConnectState.NOT_CONNECTED);
            this.updateDevConnectState(deviceId, Constant.ConnectState.NOT_CONNECTED);
        }
    }

    private void updateDevConnectState(String deviceId, int connectState) {
        DevStatusRespDTO devStatusRespDTO = this.redisUtils.get(deviceId, DevStatusRespDTO.class);
        devStatusRespDTO.setConnStatus(connectState);
        this.redisUtils.set(deviceId, devStatusRespDTO);
    }

    /**
     * 启动泡茶
     * @param req
     * @return
     */
    @Override
    public DeviceHttpResp makeTea(DeviceHttpReq req) {
        req.setAction("startwork");
        req.setActionflag(Constant.DevStartWorkAction.MAKE_TEA);
        return this.deviceControl(this.http2DevURL + DevReqURIConstant.START_WORK, req, req.getDeviceID());
    }

    /**
     * 洗茶控制
     * @param req
     * @return
     */
    @Override
    public DeviceHttpResp washTeaCtrl(DeviceHttpReq req) {
        req.setAction("startwork");
        req.setActionflag(Constant.DevStartWorkAction.WASH_TEA); // 洗茶按键
        return this.deviceControl(this.http2DevURL + DevReqURIConstant.START_WORK, req, req.getDeviceID());
    }

    /**
     * 烧水控制
     * @param req
     * @return
     */
    @Override
    public DeviceHttpResp boilWaterCtrl(DeviceHttpReq req) {
        req.setAction("startwork");
        req.setActionflag(Constant.DevStartWorkAction.BOIL_WATER); // 烧水按键
        return this.deviceControl(this.http2DevURL + DevReqURIConstant.START_WORK, req, req.getDeviceID());
    }

    /**
     * 设置洗茶/烧水参数
     * @param req
     * @return
     */
    @Override
    public DeviceHttpResp setWashTeaOrBoilWaterParam(DeviceHttpReq req) {
        req.setAction("setdevparm");
        return this.deviceControl(this.http2DevURL + DevReqURIConstant.SET_DEV_PARAM, req, req.getDeviceID());
    }

    /**
     * 停止沏茶/烧水/洗茶/保温
     * actionFalg 1-停止沏茶 2-停止烧水 3-停止洗茶 4-停止保温
     * @param req
     * @return
     */
    @Override
    public DeviceHttpResp stopWork(StopWorkHttpReq req) {
        req.setAction("stopwork");
        DeviceHttpResp resp = this.deviceControl(this.http2DevURL + DevReqURIConstant.STOP_WORK, req, req.getDeviceID());
        if (resp.isOK()) {
            // 需要设置当前为停止沏茶标识
            DevStatusRespDTO devStatusRespDTO = this.redisUtils.get(req.getDeviceID(), DevStatusRespDTO.class);
            devStatusRespDTO.setStopAction(true);
            this.redisUtils.set(req.getDeviceID(), devStatusRespDTO);
        }
        return resp;
    }

    /**
     * 开始保温控制
     * @param req
     * @return
     */
    @Override
    public DeviceHttpResp startKeeWarm(DeviceHttpReq req) {
        req.setAction("keepwarm");
        return this.deviceControl(this.http2DevURL + DevReqURIConstant.START_KEEP_WARM, req, req.getDeviceID());
    }

    /**
     * 设置内置茶谱参数
     * @param req
     * @return
     */
    @Override
    public DeviceHttpResp setDevChapuParam(SetDevChapuParamHttpReq req) {
        req.setAction("setchapuparm");
        return this.deviceControl(this.http2DevURL + DevReqURIConstant.SET_DEV_CHAPU_PARAM, req, req.getDeviceID());
    }

    /**
     * 查询设备参数设置信息
     * @param req
     * @return
     */
    @Override
    public DevSettingHttpResp queryDevSettingInfo(QryDeviceInfoHttpReq req) {
        req.setAction("qrydevparm");
        try {
            DevSettingHttpResp resp = this.restTemplateUtils.httpPostMediaTypeJson(this.http2DevURL + DevReqURIConstant.QRY_DEV_PARAM, DevSettingHttpResp.class, req);
            return resp;
        } catch (Exception e) {
            log.error("查询设备参数设置信息异常！", e);
            return new DevSettingHttpResp(HttpStatus.SC_INTERNAL_SERVER_ERROR, "查询设备参数设置信息异常！");
        }
    }

    /**
     * 查询设备茶谱信息
     * 该方法已弃用，调用 queryDevSettingInfo 方法会返回茶谱信息，update by dz 20190616
     * @param req
     * @return
     */
    @Override
    @Deprecated
    public DevChapuHttpResp queryDevChapuInfo(QryDeviceInfoHttpReq req) {
        req.setAction("qrychapuparm");
        try {
            DevChapuHttpResp resp = this.restTemplateUtils.httpPostMediaTypeJson(this.http2DevURL + DevReqURIConstant.QRY_DEV_CHAPU, DevChapuHttpResp.class, req);
            return resp;
        } catch (Exception e) {
            log.error("查询设备茶谱信息异常！", e);
            return new DevChapuHttpResp(HttpStatus.SC_INTERNAL_SERVER_ERROR, "查询设备茶谱信息异常！");
        }
    }



    /**
     * 查询设备状态信息
     * @param req
     * @return
     */
    @Override
    public DevStatusReportResp queryDevStateInfo(QryDeviceInfoHttpReq req) {
        req.setAction("qrydevicestate");
        DevStatusReportResp resp = this.restTemplateUtils.httpPostMediaTypeJson(this.http2DevURL + DevReqURIConstant.QRY_DEV_STATUS, DevStatusReportResp.class, req);
        return resp;
    }

    /**
     * 锁定或解锁设备
     * @param req
     * @return
     */
    @Override
    public DeviceHttpResp lockOrUnlockDev(LockOrUnlockDevHttpReq req) {
        req.setAction("devicelock");
        return this.deviceControl(this.http2DevURL + DevReqURIConstant.LOCK_OR_UNLOCK_DEV, req, req.getDeviceID());
    }

    /**
     * 设置设备声音或网络
     * @param req
     * @return
     */
    @Override
    public DeviceHttpResp setDevSoundOrNetwork(SetDevOtherParamHttpReq req) {
        req.setAction("setotherparm");
        return this.deviceControl(this.http2DevURL + DevReqURIConstant.SET_DEV_OTHER_PARAM, req, req.getDeviceID());
    }

    /**
     * 设置设备声音或网络
     * @param req
     * @return
     */
    @Override
    public DeviceHttpResp devUpgrade(DevUpgradeHttpReq req) {
        req.setAction("OTAparm");
        return this.deviceControl(this.http2DevURL + DevReqURIConstant.DEV_UPGRADE, req, req.getDeviceID());
    }

}
