package com.httpdemo.httpClient.demo;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by win7 on 2017/5/31.
 */
public class PostDemo {
    public static void main(String[] args) throws IOException, URISyntaxException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost();
        request.setURI(new URI("http://tcc.taobao.com/cc/json/mobile_tel_segment.htm"));
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("tel", "17600205063"));
        request.setEntity(new UrlEncodedFormEntity(nvps));
        CloseableHttpResponse response = httpClient.execute(request);
        try {
            if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                System.err.println(EntityUtils.toString(response.getEntity(),"utf-8"));
                EntityUtils.consume(response.getEntity());
            }
        } catch (Exception e) {

        } finally {
            response.close();
        }
    }
}
