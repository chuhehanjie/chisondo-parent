package com.chisondo.server.common.utils;

import com.chisondo.server.common.exception.CommonException;
import com.chisondo.server.common.exception.CommonException;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * @author ding.zhong
 * @since Apr 20.18
 */
public final class ValidateUtils {
    public static boolean isEmptyCollection(Collection c) {
        return c == null || c.isEmpty();
    }

    public static boolean isNotEmptyCollection(Collection c) {
        return !isEmptyCollection(c);
    }

    public static boolean isEmptyArray(Object[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isNotEmptyArray(Object[] array) {
        return !isEmptyArray(array);
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }
    public static boolean isEmpty(Object obj) {
        return obj == null ? true : (obj.getClass().isArray() ? Array.getLength(obj) == 0 : (obj instanceof CharSequence ? ((CharSequence) obj).length() == 0 : (obj instanceof Collection ? ((Collection) obj).isEmpty() : (obj instanceof Map ? ((Map) obj).isEmpty() : false))));
    }

    public static boolean isEmptyString(String str) {
        return str == null || "".equals(str);
    }

    public static boolean isNotEmptyString(String str) {
        return !isEmptyString(str);
    }

    public static boolean equals(Object o1, Object o2) {
        return ObjectUtils.nullSafeEquals(o1, o2);
    }

    public static boolean notEquals(Object o1, Object o2) {
        return !equals(o1, o2);
    }



    /**
     * spring mvc validated params
     * @param result
     * @throws CommonException
     */
    public static void validatedParams(BindingResult result)throws CommonException {
        if(result!=null && result.getAllErrors()!=null && result.getAllErrors().size()>0){
            throw new CommonException(result.getAllErrors().get(0).getDefaultMessage());
        }
    }
}
