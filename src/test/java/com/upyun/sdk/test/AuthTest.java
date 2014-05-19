package com.upyun.sdk.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.upyun.sdk.utils.DateUtil;
import com.upyun.sdk.utils.HttpClientUtils;
import com.upyun.sdk.utils.Md5;

public class AuthTest {

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

    @Test
    public void testAuth() {
        String url = "http://v0.api.upyun.com/java4upyun/";
        Map<String, String> headers = new HashMap<String, String>();
        String gmtDate = DateUtil.getGMTDate();
        String sign = Md5.MD5("GET&" + "/java4upyun/" + "&" + gmtDate + "&0&" + Md5.MD5("ph5022188"));
        headers.put("Authorization", "UpYun java4upyun:" + sign);
        headers.put("Date", gmtDate);
        System.out.println(sign);
        System.out.println(gmtDate);
        System.out.println(HttpClientUtils.getByHttp(url, headers));
    }

    @Test
    public void testUploadFile() {
        
    }
}
