package com.httpdemo.httpClient.demo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by win7 on 2017/5/31.
 */
public class AsyncDemo {
    private static   CloseableHttpAsyncClient httpAsyncClient;
    static {
        ConnectingIOReactor ioReactor = null;
        try {
            ioReactor = new DefaultConnectingIOReactor();
        } catch (IOReactorException e) {
            e.printStackTrace();
        }
        PoolingNHttpClientConnectionManager cm = new PoolingNHttpClientConnectionManager(ioReactor);
        cm.setMaxTotal(1);
         httpAsyncClient = HttpAsyncClients.custom().setConnectionManager(cm).build();
        httpAsyncClient.start();
    }

    public  static  void go(  CloseableHttpAsyncClient httpAsyncClient){
        String[] urisToGet = {
                "http://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=17600205063",
        };
        final CountDownLatch latch = new CountDownLatch(urisToGet.length);
        for (final String uri : urisToGet){
            final HttpGet httpGet = new HttpGet(uri);
            httpAsyncClient.execute(httpGet, new FutureCallback<HttpResponse>() {
                @Override
                public void completed(HttpResponse httpResponse) {
                    latch.countDown();
                    HttpEntity entity =null;
                    try {
                        entity = httpResponse.getEntity();
                        System.err.println(EntityUtils.toString(entity,"utf-8"));
                     //   httpAsyncClient.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            EntityUtils.consume(entity);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void failed(Exception e) {

                }

                @Override
                public void cancelled() {
                    latch.countDown();
                }
            });

        }

    }
    public static void main(String[] args) throws IOException, InterruptedException {
 for (int i = 0;i<5;i++){
     go(httpAsyncClient);

        }


    }
}
