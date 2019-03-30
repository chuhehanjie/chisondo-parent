package com.chisondo.server.common.utils;

/**
 * Redis所有Keys
 *
 * @author ding.zhong
 * @email 258321511@qq.com
 * @date Mar 12.19
 */
public class RedisKeys {

    public static String getSysConfigKey(String key){
        return "device:config:" + key;
    }
}
