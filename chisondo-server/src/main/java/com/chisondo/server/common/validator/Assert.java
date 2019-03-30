package com.chisondo.server.common.validator;

import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.exception.CommonException;
import org.apache.commons.lang.StringUtils;

/**
 * 数据校验
 * @author ding.zhong
 * @email 258321511@qq.com
 * @date Mar 12.19
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new CommonException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new CommonException(message);
        }
    }
}
