package com.js.util;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

public class HttpUtil {

    // 池化管理
    private static PoolingHttpClientConnectionManager poolConnManager = null;


    private static CloseableHttpClient client;

    static {
        try {
            System.out.println("初始化HttpClient~~~开始");
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
            // 配置同时支持 HTTP 和 HTPPS
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    .register("https", sslsf).build();
            // 初始化连接管理器
            poolConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            poolConnManager.setMaxTotal(640);// 同时最多连接数
            // 设置最大路由
            poolConnManager.setDefaultMaxPerRoute(320);
            client = getConnection(getDefaultTimeout());
            System.out.println("初始化HttpClient~~~结束");
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public static int getDefaultTimeout() {
        return 60000;
    }

    public static CloseableHttpClient getConnection(int timeout) {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout).setSocketTimeout(timeout).build();
        return HttpClients.custom()
                // 设置连接池管理
                .setConnectionManager(poolConnManager)
                .setDefaultRequestConfig(config)
                // 设置重试次数
                .setRetryHandler(new DefaultHttpRequestRetryHandler(2, false)).build();
    }


    public static String doGetString(String url, Map<String, String> headerMap) {
        HttpGet request = new HttpGet(url);
        request.setHeader("Accept", "application/json");
        if (headerMap != null) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                request.setHeader(entry.getKey(), entry.getValue());
            }
        }
        try {
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                return result;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
