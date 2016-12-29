package org.smart4j.chapter02.util;

/**
 * 类型转换工具类
 */
public final class CastUtil {

    /**
     * 将Object类型转为String    (默认为空)
     * @param obj               转换对象
     * @return                  转换后的String
     */
    public static String castString(Object obj) {
        return CastUtil.castString(obj, "");
    }

    /**
     * 将Object类型转为String
     * @param obj              转换对象
     * @param defaultValue      默认值
     * @return                 转换后的String
     */
    public static String castString(Object obj, String defaultValue) {
        return obj != null ? String.valueOf(obj) : defaultValue;
    }

    /**
     * 将Object类型转为double    (默认为0)
     * @param obj               转换对象
     * @return                  转换后的double
     */
    public static double castDouble(Object obj) {
        return CastUtil.castDouble(obj, 0);
    }

    /**
     * 将Object类型转为double
     * @param obj               转换对象
     * @param defaultValue       默认值
     * @return                  转换后的double
     */
    public static double castDouble(Object obj, double defaultValue) {
        double doubleValue = defaultValue;
        if (obj != null) {
            String strValue = String.valueOf(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    doubleValue = Double.valueOf(strValue);
                } catch (NumberFormatException e) {
                    doubleValue = defaultValue;
                }
            }
        }
        return doubleValue;
    }

    /**
     * 将Object类型转为long   (默认为0)
     * @param obj           转换对象
     * @return              转换后的long
     */
    public static long castLong(Object obj) {
        return CastUtil.castLong(obj, 0l);
    }

    /**
     * 将Object类型转为long
     * @param obj               转换对象
     * @param defaultValue       默认值
     * @return                  转换后的long
     */
    public static long castLong(Object obj, long defaultValue) {
        long longValue = defaultValue;
        if (obj != null) {
            String strValue = String.valueOf(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    longValue = Long.valueOf(strValue);
                } catch (NumberFormatException e) {
                    longValue = defaultValue;
                }
            }
        }
        return longValue;
    }

    /**
     * 将Object类型转为Int   (默认为0)
     * @param obj           转换对象
     * @return              转换后的String
     */
    public static int castInt(Object obj) {
        return CastUtil.castInt(obj, 0);
    }

    /**
     * 将Object类型转为String
     * @param obj               转换对象
     * @param defaultValue       默认值
     * @return                  转换后的String
     */
    public static int castInt(Object obj, int defaultValue) {
        int intValue = defaultValue;
        if (obj != null) {
            String strValue = String.valueOf(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    intValue = Integer.valueOf(strValue);
                } catch (NumberFormatException e) {
                    intValue = defaultValue;
                }
            }
        }
        return intValue;
    }

    /**
     * 将Object类型转为boolean   (默认为0)
     * @param obj               转换对象
     * @return                  转换后的boolean
     */
    public static boolean castBoolean(Object obj) {
        return CastUtil.castBoolean(obj, false);
    }

    /**
     * 将Object类型转为boolean
     * @param obj               转换对象
     * @param defaultValue       默认值
     * @return                  转换后的boolean
     */
    public static boolean castBoolean(Object obj, boolean defaultValue) {
        boolean booleanValue = defaultValue;
        if (obj != null) {
            String strValue = String.valueOf(obj);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    booleanValue = Boolean.valueOf(strValue);
                } catch (NumberFormatException e) {
                    booleanValue = defaultValue;
                }
            }
        }
        return booleanValue;
    }
}
