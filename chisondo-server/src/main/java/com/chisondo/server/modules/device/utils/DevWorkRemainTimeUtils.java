package com.chisondo.server.modules.device.utils;

import com.chisondo.model.constant.DeviceConstant;
import com.chisondo.model.http.resp.DevStatusRespDTO;
import com.chisondo.model.http.resp.DeviceHttpResp;
import com.chisondo.model.http.resp.DeviceMsgResp;
import com.chisondo.server.common.utils.CommonUtils;
import com.chisondo.server.common.utils.RedisUtils;
import com.chisondo.server.common.utils.SpringContextUtils;
import com.chisondo.server.common.utils.ValidateUtils;
import com.chisondo.server.modules.device.entity.DeviceStateInfoEntity;
import com.chisondo.server.modules.device.service.DeviceStateInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

@Slf4j
public final class DevWorkRemainTimeUtils {


    public static boolean hasWorkRemainTime(DevStatusRespDTO devStatusResp) {
        return ValidateUtils.isNotEmpty(devStatusResp.getReamin()) && devStatusResp.getReamin() > 0 && !devStatusResp.isCountdownFlag();
    }

    /**
     * 处理设备工作剩余时间
     * 倒计时处理改为由设备上报时统一处理 update by dz 20190710
     * @param deviceHttpResp
     * @param deviceId
     */
    public static void processDevWorkingRemainTime(final DeviceHttpResp deviceHttpResp, String deviceId) {
        if (ValidateUtils.isEmpty(deviceHttpResp.getMsg())) {
            log.error("设备[{}]MSG为空", deviceHttpResp.getDeviceID());
            return;
        }
        DeviceStateInfoEntity devStateInfo = CommonUtils.convert2DevStatusEntity(deviceHttpResp, deviceId);
        DevStatusRespDTO devStatusRespDTO = getRedisUtils().get(deviceHttpResp.getDeviceID(), DevStatusRespDTO.class);
        updateDevState2Redis(devStatusRespDTO, deviceHttpResp);
        if (hasWorkRemainTime(devStatusRespDTO)) {
            asyncProcessWorkRemainTime(devStatusRespDTO, devStateInfo);
        } else {
            getRedisUtils().set(devStatusRespDTO.getDeviceId(), devStatusRespDTO);
            getDevStateInfoService().update(devStateInfo);
        }
    }

    private static void updateDevState2Redis(DevStatusRespDTO devStatusRespDTO, DeviceHttpResp deviceHttpResp) {
        DeviceMsgResp devMsg = deviceHttpResp.getMsg();
        if (null != devMsg.getTemperature()) {
            devStatusRespDTO.setTemp(devMsg.getTemperature());
            devStatusRespDTO.setMakeTemp(devMsg.getTemperature());
        }
        if (null != devMsg.getChapuId()) {
            devStatusRespDTO.setChapuId(devMsg.getChapuId());
        }
        if (null != devMsg.getStep()) {
            devStatusRespDTO.setIndex(devMsg.getStep());
        }
        if (null != devMsg.getWarmstatus()) {
            devStatusRespDTO.setWarm(devMsg.getWarmstatus());
        }
        if (null != devMsg.getTaststatus()) {
            devStatusRespDTO.setDensity(devMsg.getTaststatus());
        }
        if (null != devMsg.getWaterlevel()) {
            devStatusRespDTO.setWaterlv(devMsg.getWaterlevel());
        }
        if (null != devMsg.getSoak()) {
            devStatusRespDTO.setMakeDura(devMsg.getSoak());
        }
        // 需要将 remain 时间多加 2 秒，因为设备已经在倒计时了，而服务端会有延时
        devStatusRespDTO.setReamin(ValidateUtils.isEmpty(devMsg.getRemaintime()) ? null : (devMsg.getRemaintime() > 0 ? devMsg.getRemaintime() + 2 : 0));
        devStatusRespDTO.setTea(2 == devMsg.getErrorstatus() ? 1 : 0);
        devStatusRespDTO.setWater(1 == devMsg.getErrorstatus() ? 1 : 0);
        devStatusRespDTO.setWork(devMsg.getWorkstatus());
    }

    /**
     * 异步处理工作剩余时间
     * @param devStatusRespDTO
     * @param devStateInfo
     */
    public static void asyncProcessWorkRemainTime(DevStatusRespDTO devStatusRespDTO, DeviceStateInfoEntity devStateInfo) {
        log.info("开始倒计时处理，设备ID = {}, action = {}, remain = {}", devStatusRespDTO.getDeviceId(), devStatusRespDTO.getActionFlag(), devStatusRespDTO.getReamin());
        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(2,
                new BasicThreadFactory.Builder().namingPattern("scheduled-pool2-%d").daemon(true).build());
        scheduledExecutorService.execute(() -> {
            boolean stopChapuFlag = true;
            boolean onlyOnce = true;
            int remainTime = devStatusRespDTO.getReamin() - 1;
            String deviceId = devStatusRespDTO.getDeviceId();
            devStatusRespDTO.setCountdownFlag(true);
            getRedisUtils().set(deviceId, devStatusRespDTO);
            try {
                for (int i = remainTime; i >= 0; i--) {
                    DevStatusRespDTO tempDevStatusResp = getRedisUtils().get(deviceId, DevStatusRespDTO.class);
                    if (ValidateUtils.equals(tempDevStatusResp.getReamin(), 0)) {
                        getRedisUtils().set(deviceId, tempDevStatusResp);
                        break;
                    }
                    // actionFlag 为 4 时，表示结束茶谱，此时倒计时时间以传入的为准
                    if (ValidateUtils.equals(DeviceConstant.DevReportActionFlag.ENABLE_BUTTON, tempDevStatusResp.getActionFlag()) && stopChapuFlag) {
                        i = tempDevStatusResp.getReamin();
                        stopChapuFlag = false;
                        log.info("启动按键了， remain = {}", i);
                    }
                    if (tempDevStatusResp.isStopMakeTea() && onlyOnce) {
                        i = tempDevStatusResp.getReamin();
                        onlyOnce = false;
                        log.info("停止沏茶了， remain = {}", i);
                    }
                    tempDevStatusResp.setReamin(i);
                    getRedisUtils().set(deviceId, tempDevStatusResp);
                    log.info("剩余时间 = {}", i);
                    Thread.sleep(990);
                }
            } catch (InterruptedException e) {
                log.error("更新设备工作剩余时间失败！", e);
            } finally {
                DevStatusRespDTO tempDevStatusResp = getRedisUtils().get(deviceId, DevStatusRespDTO.class);
                tempDevStatusResp.setCountdownFlag(false);
                tempDevStatusResp.setStopMakeTea(false);
                getRedisUtils().set(deviceId, tempDevStatusResp);
                log.error("设备[{}]倒计时结束！", deviceId);
            }
        });
    }

    private static RedisUtils getRedisUtils() {
        return (RedisUtils) SpringContextUtils.getBean("redisUtils");
    }

    private static DeviceStateInfoService getDevStateInfoService() {
        return (DeviceStateInfoService) SpringContextUtils.getBean("deviceStateInfoService");
    }
}
