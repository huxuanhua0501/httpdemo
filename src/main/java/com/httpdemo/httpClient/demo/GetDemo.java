package com.httpdemo.httpClient.demo;

import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;

/**
 * Created by win7 on 2017/5/31.
 */
public class GetDemo {
    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet();
        request.setURI(URI.create("http://www.yeetrack.com/?p=760"));
        CloseableHttpResponse response = httpClient.execute(request);
        try {
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                System.err.println(EntityUtils.toString(response.getEntity(), "UTF-8"));
               EntityUtils.consume(response.getEntity());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            response.close();
        }
    }
}
