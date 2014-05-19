package com.upyun.sdk;

import java.io.FileInputStream;

public class UpYunClient {
    
    private Signature sign;
    public UpYunClient newClient(String space, String operator, String password) {
        sign =  new Signature();
        sign.setSpace(space);
        sign.setOperator(operator);
        sign.setPassword(password);
        
        return new UpYunClient();
    }
    
    public void uploadFile(String file, FileInputStream stream) {
        
    }
}
