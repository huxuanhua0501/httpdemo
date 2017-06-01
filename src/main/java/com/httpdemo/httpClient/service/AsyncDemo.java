package com.httpdemo.httpClient.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by win7 on 2017/5/31.
 */
public class AsyncDemo {
    public static void main(String[] args) throws Exception {
        ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor();
        PoolingNHttpClientConnectionManager cm = new PoolingNHttpClientConnectionManager(ioReactor);
        cm.setMaxTotal(10000);
        CloseableHttpAsyncClient httpAsyncClient = HttpAsyncClients.custom().setConnectionManager(cm).build();
        httpAsyncClient.start();
        String[] urisToGet = {
                "http://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=17600205063",
                "http://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=17600205063",

        };

        final CountDownLatch latch = new CountDownLatch(urisToGet.length);
        for (final String uri: urisToGet) {
            final HttpGet httpget = new HttpGet(uri);
            httpAsyncClient.execute(httpget, new FutureCallback<HttpResponse>() {

                public void completed(final HttpResponse response) {
                    latch.countDown();
                    try {
                        System.err.println(EntityUtils.toString(response.getEntity()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println(httpget.getRequestLine() + "->" + response.getStatusLine());
                }

                public void failed(final Exception ex) {
                    latch.countDown();
                    System.out.println(httpget.getRequestLine() + "->" + ex);
                }

                public void cancelled() {
                    latch.countDown();
                    System.out.println(httpget.getRequestLine() + " cancelled");
                }

            });
        }
        latch.await();
        httpAsyncClient.close();
    }
}
