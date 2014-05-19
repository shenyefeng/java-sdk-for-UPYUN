package com.upyun.sdk.utils;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.log4j.Logger;

public class HttpClientUtils {
    private static final Logger logger = Logger.getLogger(HttpClientUtils.class);
    private static final String APPLICATION_JSON = "application/json";
    private static int timeout = 50000;
    private static X509TrustManager tm = new X509TrustManager() {
        public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    };

    @SuppressWarnings("deprecation")
    public static HttpClient getInstance() {
        HttpClient client = new DefaultHttpClient();
        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(null, new TrustManager[] { tm }, null);
        } catch (Exception e) {
            LogUtil.exception(logger, e);
        }
        SSLSocketFactory ssf = new SSLSocketFactory(ctx);
        ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        ClientConnectionManager ccm = client.getConnectionManager();
        SchemeRegistry sr = ccm.getSchemeRegistry();
        sr.register(new Scheme("https", ssf, 443));
        client = new DefaultHttpClient(ccm, client.getParams());
        return client;
    }
    
    public static String postByHttp(String url, String inputParam) {
    	return postByHttp(url, inputParam, APPLICATION_JSON);
    }
    
    public static String postByHttp(String url, String inputParam, String contentType) {
        DefaultHttpClient httpclient = new DefaultHttpClient();

        return post(url, inputParam, contentType, httpclient, null);
    }
    
    public static String postByHttp(String url, String inputParam, Header header, String contentType) {
        DefaultHttpClient httpclient = new DefaultHttpClient();

        return post(url, inputParam, contentType, httpclient, header);
    }
    
    public static String postByHttps(String url, String inputParam, String contentType) {
        DefaultHttpClient httpclient = (DefaultHttpClient)getInstance();
        
        return post(url, inputParam, contentType, httpclient, null);
    }
    
    public static String getByHttp(String url, Map<String, String> headers) {
        return getByHttp(url, null, headers);
    }
   
    public static String getByHttp(String url, String inputParam) {
        return getByHttp(url, inputParam, new HashMap<String, String>());
    }
    
    public static String getByHttp(String url, String inputParam, Map<String, String> headers) {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        return get(url, inputParam, httpclient, headers);
    }
    
    public static String getByHttps(String url, String inputParam, Map<String, String> headers) {
        DefaultHttpClient httpclient = (DefaultHttpClient)getInstance();
        
        return get(url, inputParam, httpclient, headers);
    }

    private static String post(String url, String inputParam, String contentType, HttpClient httpclient, Header header) {
        
        String restr = null;
        
        try {
            StringEntity reqEntity = new StringEntity(inputParam);
            reqEntity.setContentType(contentType);
            httpclient.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, timeout);// 连接超时
            httpclient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, timeout); // 读取超时

            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(reqEntity);
            if(header != null) {
            	httpPost.addHeader(header);
            }
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            restr = httpclient.execute(httpPost, responseHandler);
        } catch (Exception e) {
            LogUtil.exception(logger, e);
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        return restr;
    }

    private static String get(String url, String inputParam, HttpClient httpclient, Map<String, String> headers) {
        
        String restr = null;
        
        try {
            httpclient.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, timeout);// 连接超时
            httpclient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, timeout); // 读取超时
            
            HttpGet httpGet;
            if(inputParam != null && inputParam.length() > 0) {
                httpGet = new HttpGet(url + "?" + inputParam);
            } else {
                httpGet = new HttpGet(url);
            }
            
            for(String key : headers.keySet()) {
                httpGet.addHeader(key, headers.get(key));
            }

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            restr = httpclient.execute(httpGet, responseHandler);
        } catch (Exception e) {
            LogUtil.exception(logger, e);
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        return restr;
    }

    public static void setTimeout(int timeout) {
        HttpClientUtils.timeout = timeout;
    }
}
