package com.httpdemo.httpClient.service;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.FormBodyPartBuilder;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.TreeMap;

public class FormHttpDemo {

    public static void main(String[] args) throws IOException {
        String status = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        FormBodyPartBuilder.create();

            HttpPost httppost = new HttpPost("http://192.168.108.179:8044//probe");
//            HttpPost httppost = new HttpPost("http://localhost:8044//probe");
            File file = new File("E:\\iV6zPDpr4r29LfnE3wT8DA.._WiFiProbe_1494471456.log");
            FormBodyPartBuilder filebody = FormBodyPartBuilder.create("file", new FileBody(file,
                    ContentType.MULTIPART_FORM_DATA, file.getName()));
            TreeMap<String, String> queryParmates = new TreeMap<String, String>();
            queryParmates.put("appkey", "12sssss");
            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder
                    .create();
            entityBuilder = entityBuilder
                    .setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            // 添加文件
            //  for (FormBodyPart formPart : formParts) {
            System.err.println(filebody.build().getName());
            entityBuilder = entityBuilder.addPart(filebody.build().getName(),
                    filebody.build().getBody());
            //   }
            // 添加变量信息
            Iterator<String> it = queryParmates.keySet().iterator();
            String key = null;
            while (it.hasNext()) {
                key = it.next();
                entityBuilder.addTextBody(key, queryParmates.get(key),
                        ContentType.TEXT_PLAIN.withCharset("UTF-8"));
            }
            httppost.setEntity(entityBuilder.build());

           CloseableHttpResponse response =  httpclient.execute(httppost);
        try {
            System.err.println(EntityUtils.toString(response.getEntity()));
            EntityUtils.consume(response.getEntity());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();

        }finally {
            response.close();
        }
    }
}