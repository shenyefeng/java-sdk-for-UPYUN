package com.upyun.sdk;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.upyun.sdk.utils.DateUtil;
import com.upyun.sdk.utils.Md5;

public class Signature {
    private String method;
    private String space;
    private String uri;
    private String gmtDate;
    private int contentLength;
    private String password;
    private String operator;

    private Map<String, String> headers;
    private Date signDate;
    
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getGmtDate() {
        return gmtDate;
    }

    public void setGmtDate(String gmtDate) {
        this.gmtDate = gmtDate;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
    
    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public Map<String, String> getHeaders() {
        headers = new HashMap<String, String>();
        headers.put("Authorization", "UpYun " + getSpace() + ":" + generateSign());
        headers.put("Date", getGmtDate());
        return headers;
    }

    private String generateSign() {
        setSignDate(new Date());
        setGmtDate(DateUtil.getGMTDate(getSignDate()));
         return Md5.MD5(getMethod() + "&" + getUri() + "&" + getGmtDate() + "&" + getContentLength() + "&" + Md5.MD5(getPassword()));
    }
}
