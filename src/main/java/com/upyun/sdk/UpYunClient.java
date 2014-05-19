package com.upyun.sdk;

import java.io.FileInputStream;
import java.util.Map;

import com.upyun.sdk.utils.PropertyUtil;

public class UpYunClient {
    
    private Signature sign;
    private String autoUrl;
    
    private UpYunClient() {
        
    }
    
    public UpYunClient newClient(String space, String operator, String password) {
        sign =  new Signature();
        sign.setSpace(space);
        sign.setOperator(operator);
        sign.setPassword(password);
        
        autoUrl = PropertyUtil.getProperty("auto_url");
        return new UpYunClient();
    }
    
    public void uploadFile(String file, FileInputStream stream, Integer fileLength) {
        sign.setUri(sign.getSpace() + "/" + file);
        sign.setContentLength(fileLength);
        autoUrl = autoUrl + sign.getUri();
        Map<String, String> headers = sign.getHeaders();
    }
}
