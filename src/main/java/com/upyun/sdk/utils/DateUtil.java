package com.upyun.sdk.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.log4j.Logger;

public class DateUtil {
    private static Logger logger = Logger.getLogger(DateUtil.class);
    
    public static Date toDate(String date) {
        return toDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date toDate(String date, String pattern) {
        Date result = null;
        try {
            result = new SimpleDateFormat(pattern).parse(date);
        } catch (ParseException e) {
            logger.warn(e);
        }
        return result;
    }

    public static String getGMTDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(new Date());
    }
    
    public static String getGMTDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(date);
    }
}
