package com.httpdemo.httpClient.service;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Created by win7 on 2017/5/31.
 */
public class GetHttpDemo {
    public static void main(String[] args) {
        try {
            // 根据地址获取请求
            HttpGet request = new HttpGet("http://www.yeetrack.com/?p=760");//这里发送get请求
            // 获取当前客户端对象
            CloseableHttpClient httpClient = HttpClients.createDefault();
            // 通过请求对象获取响应对象
//            HttpResponse response = httpClient.execute(request);
            CloseableHttpResponse response = httpClient.execute(request);
            // 判断网络连接状态码是否正常(0--200都数正常)
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                System.err.println(EntityUtils.toString(response.getEntity(), "utf-8"));
            }
            EntityUtils.consume(response.getEntity());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //....result是用户信息,站内业务以及具体的json转换这里也不写了...
    }
}
