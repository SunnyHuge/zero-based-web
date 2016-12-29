package org.smart4j.chapter02.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件工具类.
 */
public final class PropsUtil {
    // 日志管理
    private static final Logger logger = LoggerFactory.getLogger(PropsUtil.class);

    public static Properties loadProps(String fileName) {
        Properties props = null;
        InputStream is = null;

        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if (is == null) {
                throw new FileNotFoundException("file " + fileName + " is not found");
            }
            props = new Properties();
            props.load(is);
        } catch (Exception e) {
            logger.error("load properties file failure", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    logger.error("close input stream failure ", e);
                }
            }
        }
        return props;
    }

    /**
     * 从配置文件中获取字符型属性值  (默认值为空）
     * @param props         配置文件属性
     * @param key           属性名
     * @return              key对应value
     */
    public static String getString(Properties props, String key) {
        return getString(props, key, "");
    }

    /**
     * 从配置文件中获取字符型属性值
     * @param props         配置文件属性
     * @param key           属性名
     * @param defaultValue   默认值
     * @return              key对应value
     */
    public static String getString(Properties props, String key, String defaultValue) {
        String value = defaultValue;
        if (props.contains(key)) {
            value = props.getProperty(key);
        }
        return value;
    }

    /**
     * 从配置文件中获取数值型属性值  (默认值为0）
     * @param props         配置文件属性
     * @param key           属性名
     * @return              key对应value
     */
    public static int getInt(Properties props, String key) {
        return getInt(props, key, 0);
    }

    /**
     * 从配置文件中获取数值型属性值
     * @param props         配置文件属性
     * @param key           属性名
     * @param defaultValue   默认值
     * @return              key对应value
     */
    public static int getInt(Properties props, String key, int defaultValue) {
        int value = defaultValue;
        if (props.contains(key)) {
            value = CastUtil.castInt(props.getProperty(key));
        }
        return value;
    }

    /**
     * 从配置文件中获取布尔型属性值  (默认值为false
     * @param props         配置文件属性
     * @param key           属性名
     * @return              key对应value
     */
    public static boolean getBoolean(Properties props, String key) {
        return getBoolean(props, key, false);
    }

    /**
     * 从配置文件中获取布尔型属性值
     * @param props         配置文件属性
     * @param key           属性名
     * @param defaultValue   默认值
     * @return              key对应value
     */
    public static boolean getBoolean(Properties props, String key, boolean defaultValue) {
        boolean value = defaultValue;
        if (props.contains(key)) {
            value = CastUtil.castBoolean(props.getProperty(key));
        }
        return value;
    }
}
