package com.upyun.sdk.exception;

public class UpYunExcetion extends Exception {
    private static final long serialVersionUID = -3760536881151347016L;
    
    private String code;
    public UpYunExcetion(String code, String message) {
        super(message);
        this.code = code;
    }
    
    public UpYunExcetion(int code, String message) {
        super(message);
        this.code = code + "";
    }

    public String getCode() {
        return code;
    }
}
