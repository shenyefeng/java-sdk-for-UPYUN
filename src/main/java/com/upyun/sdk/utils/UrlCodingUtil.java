package com.upyun.sdk.utils;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;

public class UrlCodingUtil {

    private static Logger logger = Logger.getLogger(UrlCodingUtil.class);
    /***
     * encode by Base64
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static String encodeBase64(byte[] input) {
        String result = null;
        try {
            Class clazz = Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
            Method mainMethod = clazz.getMethod("encode", byte[].class);
            mainMethod.setAccessible(true);
            Object retObj = mainMethod.invoke(null, new Object[] { input });
            result = (String) retObj;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return result;
    }

    /***
     * decode by Base64
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static String decodeBase64(String input) {
        String result = null;
        try {
            Class clazz = Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
            Method mainMethod = clazz.getMethod("decode", String.class);
            mainMethod.setAccessible(true);
            Object retObj = mainMethod.invoke(null, input);
            if (retObj != null) {
                result = new String((byte[]) retObj);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return result;
    }
}
