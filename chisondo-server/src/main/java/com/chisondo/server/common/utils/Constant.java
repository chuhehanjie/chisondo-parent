package com.chisondo.server.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 常量
 *
 * @author ding.zhong
 * @email 258321511@qq.com
 * @date Mar 12.19
 */
public class Constant {

    public static final String ROOT_NODE_ID = "-1";

    public static final String DEF_DEV_NAME = "MT123";

    /**
     * 注册用户时的默认密码
     */
    public static String DEF_PWD = "123456";

    public interface ConnectState {
        /**
         * 已连接
         */
        int CONNECTED = 1;
        /**
         * 未连接
         */
        int NOT_CONNECTED = 0;
    }

    public interface OnlineState {
        /**
         * 在线
         */
        int YES = 1;
        /**
         * 不在线
         */
        int NO = 0;
    }

    public interface RegSrc {
        String CHISONDO = "泉笙道";
    }
    public interface RegSrcType {
        String WECHAT = "微信";
    }

    /**
     * 是否为默认设备(一个用户只能有一台默认设备，一台设备可以被多个用户设置为默认设备)
     */
    public interface DevDefaultTag {
        int YES = 1;
        int NO = 0;
    }

    /**
     * 设备私有标记，1标示不允许除自己外其他人进行连接，0表示可以被其他人连接
     */
    public interface DevPrivateTag {
        int YES = 1;
        int NO = 0;
    }

    /**
     * 数据库沏茶类型
     */
    public interface MakeTeaType4Db {
        /**
         * 茶谱沏茶
         */
        int TEA_SPECTRUM = 0;
        /**
         * 快速沏茶（普通沏茶）
         */
        int NORMAL = 1;
        /**
         * 面板操作
         */
        int PANEL = 2;
    }

    /**
     * 接口返回沏茶类型
     */
    public interface MakeTeaType {
        /**
         * 0-普通沏茶
         */
        int NORMAL = 0;
        /**
         * 1-茶谱沏茶
         */
        int TEA_SPECTRUM = 1;
        /**
         * 2-洗茶
         */
        int WASH_TEA = 2;
        /**
         * 3-烧水
         */
        int BOIL_WATER = 3;
    }

    /**
     * 沏茶模式
     */
    public interface MakeTeaMode {
        /**
         * 手机终端操作
         */
        int MOBILE_TERMINAL = 0;
        /**
         * 设备面板
         */
        int DEV_PANNEL = 1;
    }

    public interface RespResult {
        int SUCCESS = 1;
        int FAILED = 0;
    }

    /**
     * 异常状态
     */
    public interface ErrorStatus {
        int NORMAL = 0; // 正常
        int LACK_WATER = 1; // 缺水
        int LACK_TEA = 2; // 缺茶
        int LACK_WATER_TEA = 3; // 缺水缺茶
        int NOT_CONNECTED_WIFI = 4; // 未连接Wi-Fi
    }

    public interface DevStartWorkAction {
        int MAKE_TEA = 1; // 沏茶
        int WASH_TEA = 2; // 洗茶
        int BOIL_WATER = 3; // 洗茶
    }

    /**
     * 用户预约泡茶状态
     */
    public interface UserBookStatus {
        int VALID = 0; // 有效
        int SUCCESS = 1; // 已成功执行
        int CANCELED = 2; // 已取消
        int EXPIRED = 3; // 已过期且未成功执行
    }

    /**
     * 用户泡茶状态
     */
    public interface UserMakeTeaStatus {
        int VALID = 0; // 有效
        int COMPLETED = 1; // 已完成
        int CANCELED = 2; // 已取消
    }


    public interface TemperatureValue {
        int MIN = 60;
        int MAX = 100;
    }

    public interface SoakTime {
        int MIN = 0;
        int MAX = 600;
    }

    /**
     * 老设备操作类型
     */
    public interface OldDeviceOperType {
        int START_OR_RESERVE_MAKE_TEA = 1;
        int WASH_TEA = 2;
        int STOP_WORK = 3;
        int USE_TEA_SPECTRUM = 4;
        int CANCEL_TEA_SPECTRUM = 5;
        int WARM_CONTROL = 6;
        /**
         * 取消预约
         */
        int CANCEL_RESERVATION = 7;
        /**
         * 查询预约
         */
        int QUERY_RESERVATION = 8;
        /**
         * 烧水
         */
        int BOIL_WATER = 9;
    }

    /**
     * 设备提示音标识
     */
    public interface DevVolumeFlag {
        /**
         * 有提示音
         */
        int YES = 0;
        /**
         * 无提示音
         */
        int NO = 1;
    }
    public interface DevVolumeCtrl {
        int OPEN = 1;
        int CLOSE = 2;
    }

    public interface StopWorkOperFlag {
        int STOP_MAKE_TEA = 0;
        int STOP_WASH_TEA = 1;
        int STOP_BOIL_WATER = 2;
        int STOP_KEEP_WARM = 3;
    }

    public interface KeeWarmCtrlOperFalg {
        int STOP_KEEP_WARM = 0;
        int START_KEEP_WARM = 1;
    }

    public interface LockOrUnlockDevOperFalg {
        int LOCK = 0;
        int UNLOCK = 1;
    }

    /**
     * 1-电源键（全部功能）2-启动键（只保留停止沏茶功能）3-启动键（只保留启动键功能）
     */
    public interface LockOrUnlockDevActionFalg {
        int POWER_KEY = 1;
        int ENABLE_KEY_4_STOP_MAKE_TEA = 2;
        int ENABLE_KEY = 3;
    }
}
