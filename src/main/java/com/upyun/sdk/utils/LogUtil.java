package com.upyun.sdk.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;

public class LogUtil {
    public static void exception(Logger logger, Exception e) {
        StringWriter sw = new StringWriter();
        try {
            e.printStackTrace(new PrintWriter(sw));
            logger.error(logger.getName() + " occured " + e.getClass().getSimpleName() + " " + e.getMessage());
            logger.error(sw.toString());
        } finally {
            try {
                sw.close();
            } catch (IOException e1) {
                logger.error(e1);
            }
        }
    }
}
