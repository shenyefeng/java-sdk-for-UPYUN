package com.upyun.sdk;

import java.util.Date;

public class Signature {
    private String method;
    private String space;
    private String uri;
    private String gmtDate;
    private int contentLength;
    private String password;
    private String operator;

    private String sign;
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

    public String getSign() {
        return sign;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
