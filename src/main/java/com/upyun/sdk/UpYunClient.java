package com.upyun.sdk;

import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.upyun.sdk.enums.HttpMethodEnum;
import com.upyun.sdk.exception.UpYunExcetion;
import com.upyun.sdk.utils.FileUtil;
import com.upyun.sdk.utils.HttpClientUtils;
import com.upyun.sdk.utils.LogUtil;
import com.upyun.sdk.utils.PropertyUtil;
import com.upyun.sdk.utils.UrlCodingUtil;
import com.upyun.sdk.vo.FileVo;

public class UpYunClient {
    private static final Logger logger = Logger.getLogger(UpYunClient.class);

    private Signature sign;
    private String autoUrl;

    private UpYunClient(String space, String operator, String password) {
        sign = new Signature();
        sign.setSpace(space);
        sign.setOperator(operator);
        sign.setPassword(password);

        autoUrl = PropertyUtil.getProperty("auto_url");
    }

    public static UpYunClient newClient(String space, String operator, String password) {
        return new UpYunClient(space, operator, password);
    }

    public void uploadFile(String fileName, FileInputStream instream, Integer fileLength) throws UpYunExcetion {
        try {
            StringBuffer url = new StringBuffer();
            for (String str : fileName.split("/")) {
                url.append(UrlCodingUtil.encodeBase64(str.getBytes("utf-8")) + "/");
            }
            url = url.delete(url.length() - 1, url.length());
            sign.setUri(url.toString());
        } catch (UnsupportedEncodingException e) {
            LogUtil.exception(logger, e);
        }
        sign.setContentLength(fileLength);
        sign.setMethod(HttpMethodEnum.PUT.name());
        String url = autoUrl + sign.getUri();
        Map<String, String> headers = sign.getHeaders();
        headers.put("mkdir", "true");

        HttpResponse httpResponse = HttpClientUtils.putByHttp(url, headers, instream, fileLength);
        if (httpResponse.getStatusLine().getStatusCode() != 200) {
            throw new UpYunExcetion(httpResponse.getStatusLine().getStatusCode(), httpResponse.getStatusLine().getReasonPhrase());
        }
    }

    public void downloadFile(String path, String fileName) throws UpYunExcetion {
        try {
            StringBuffer url = new StringBuffer();
            for (String str : fileName.split("/")) {
                url.append(UrlCodingUtil.encodeBase64(str.getBytes("utf-8")) + "/");
            }
            url = url.delete(url.length() - 1, url.length());
            sign.setUri(url.toString());
        } catch (UnsupportedEncodingException e) {
            LogUtil.exception(logger, e);
        }
        sign.setContentLength(0);
        sign.setMethod(HttpMethodEnum.GET.name());
        String url = autoUrl + sign.getUri();
        Map<String, String> headers = sign.getHeaders();

        HttpResponse httpResponse = HttpClientUtils.getByHttp(url, headers);
        if (httpResponse.getStatusLine().getStatusCode() != 200) {
            throw new UpYunExcetion(httpResponse.getStatusLine().getStatusCode(), httpResponse.getStatusLine().getReasonPhrase());
        }

        HttpEntity entity = httpResponse.getEntity();

        try {
            FileUtil.saveToFile(path + "/" + fileName, entity.getContent());
        } catch (Exception e) {
            LogUtil.exception(logger, e);
        }
    }
    
    public void delete(String fileNameOrDirectory) throws UpYunExcetion {
        try {
            StringBuffer url = new StringBuffer();
            for (String str : fileNameOrDirectory.split("/")) {
                url.append(UrlCodingUtil.encodeBase64(str.getBytes("utf-8")) + "/");
            }
            url = url.delete(url.length() - 1, url.length());
            sign.setUri(url.toString());
        } catch (UnsupportedEncodingException e) {
            LogUtil.exception(logger, e);
        }
        sign.setContentLength(0);
        sign.setMethod(HttpMethodEnum.DELETE.name());
        String url = autoUrl + sign.getUri();
        Map<String, String> headers = sign.getHeaders();

        HttpResponse httpResponse = HttpClientUtils.deleteByHttp(url, headers);
        if (httpResponse.getStatusLine().getStatusCode() != 200) {
            throw new UpYunExcetion(httpResponse.getStatusLine().getStatusCode(), httpResponse.getStatusLine().getReasonPhrase());
        }
    }
    
    public List<FileVo> listFile() throws UpYunExcetion {
        sign.setUri("");
        sign.setContentLength(0);
        sign.setMethod(HttpMethodEnum.GET.name());
        String url = autoUrl + sign.getUri();
        Map<String, String> headers = sign.getHeaders();

        HttpResponse httpResponse = HttpClientUtils.getByHttp(url, headers);
        String resultStr = null;
        if (httpResponse.getStatusLine().getStatusCode() != 200) {
            throw new UpYunExcetion(httpResponse.getStatusLine().getStatusCode(), httpResponse.getStatusLine().getReasonPhrase());
        } else {
            try {
                resultStr = EntityUtils.toString(httpResponse.getEntity());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String[] lines = resultStr.split("\n");
        String[] columns;
        List<FileVo> fileVoList = new ArrayList<FileVo>();
        FileVo fileVo;
        for (String line : lines) {
            columns = line.split("\t");
            fileVo = new FileVo();
            fileVo.setName(UrlCodingUtil.decodeBase64(columns[0]));
            fileVo.setIsFile(columns[1]);
            fileVo.setSize(Long.valueOf(columns[2]));
            fileVo.setUpdatedAt(new Date(Long.valueOf(columns[3]) * 1000));
            fileVoList.add(fileVo);
        }

        return fileVoList;
    }
}
