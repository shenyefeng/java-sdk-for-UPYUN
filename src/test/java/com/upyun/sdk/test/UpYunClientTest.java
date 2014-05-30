package com.upyun.sdk.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.upyun.sdk.UpYunClient;
import com.upyun.sdk.exception.UpYunExcetion;
import com.upyun.sdk.vo.FileVo;

public class UpYunClientTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    UpYunClient client = UpYunClient.newClient("java4upyun", "java4upyun", "upyun@12345");

    // @Test
    // public void testAuth() {
    // String url = "http://v0.api.upyun.com/java4upyun/";
    // Map<String, String> headers = new HashMap<String, String>();
    // String gmtDate = DateUtil.getGMTDate();
    // String sign = Md5.MD5("GET&" + "/java4upyun/" + "&" + gmtDate + "&0&" +
    // Md5.MD5("upyun@12345"));
    // headers.put("Authorization", "UpYun java4upyun:" + sign);
    // headers.put("Date", gmtDate);
    // System.out.println(sign);
    // System.out.println(gmtDate);
    // System.out.println(HttpClientUtils.getByHttp(url, headers));
    // }

    @Test
    public void testUploadFile1() {
        File file = new File("e:/upyuntest/cs-4-3-management-nfs.txt");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            client.uploadFile("test.log", fis, fis.available());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testUploadFile2() {
        File file = new File("e:/upyuntest/cs-4-3-management-nfs.txt");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            client.uploadFile("test22.log", fis, fis.available());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testUploadFile3() {
        File file = new File("e:/upyuntest/ssh_hd.zip");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            client.uploadFile("/测试/ssh_hd.zip", fis, fis.available());
            List<FileVo> list = client.listFile();
            for (FileVo vo : list) {
                System.out.print(vo.getName() + " ");
                System.out.print(vo.getIsFile() + " ");
                System.out.print(vo.getSize() + " ");
                System.out.println(vo.getUpdatedAt());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testListFile() {
        try {
            List<FileVo> list = client.listFile();
            for (FileVo vo : list) {
                System.out.print(vo.getName() + " ");
                System.out.print(vo.getIsFile() + " ");
                System.out.print(vo.getSize() + " ");
                System.out.println(vo.getUpdatedAt());
            }
        } catch (UpYunExcetion e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDownloadFile() {
        try {
//            client.downloadFile("d:/upyuntest/", "test.log");
            client.downloadFile("d:/upyuntest/", "/ssh_hd.zip");
        } catch (UpYunExcetion e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteFile() {
        try {
            client.deleteFile("ssh_hd.zip");
            
            List<FileVo> list = client.listFile();
            for (FileVo vo : list) {
                System.out.print(vo.getName() + " ");
                System.out.print(vo.getIsFile() + " ");
                System.out.print(vo.getSize() + " ");
                System.out.println(vo.getUpdatedAt());
            }
        } catch (UpYunExcetion e) {
            e.printStackTrace();
        }
    }
}
