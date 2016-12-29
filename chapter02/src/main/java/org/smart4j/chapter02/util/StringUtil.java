package org.smart4j.chapter02.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类
 */
public final class StringUtil {

    /**
     * 判断字符串是否为空
     * @param str   源字符串
     * @return      判断结果
     */
    public static boolean isEmpty(String str) {
        if (str != null) {
            str = str.trim();
        }
        return StringUtils.isEmpty(str);
    }

    /**
     * 判断字符串是否非空
     * @param str   源字符串
     * @return      判断结果
     */
    public static boolean isNotEmpty(String str) {
        if (str != null) {
            str = str.trim();
        }
        return !isEmpty(str);
    }
}
