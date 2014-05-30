package com.upyun.sdk.utils;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

public class FileUtil {
    private static final Logger logger = Logger.getLogger(FileUtil.class);
    private static int BUFFER_SIZE = 1024;
    
    public static void saveToFile(String fileName, InputStream in) {
        FileOutputStream fos = null;
        BufferedInputStream bis = null;

        byte[] buf = new byte[BUFFER_SIZE];
        int size = 0;
        bis = new BufferedInputStream(in);

        try {
            fos = new FileOutputStream(fileName);
            while ((size = bis.read(buf)) != -1) {
                fos.write(buf, 0, size);
            }
        } catch (Exception e) {
            LogUtil.exception(logger, e);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                LogUtil.exception(logger, e);
            }
            try {
                if (bis != null) {
                    bis.close();
                }
            } catch (IOException e) {
                LogUtil.exception(logger, e);
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                LogUtil.exception(logger, e);
            }
        }
    }
}
