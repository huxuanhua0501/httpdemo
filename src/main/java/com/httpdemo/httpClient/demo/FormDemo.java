package com.httpdemo.httpClient.demo;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.FormBodyPartBuilder;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


/**
 * Created by win7 on 2017/5/31.
 */
public class FormDemo {
    public static void main(String[] args) throws URISyntaxException, IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost();
        post.setURI(new URI("http://192.168.108.179:8044//probe"));
        File file = new File("E:\\iV6zPDpr4r29LfnE3wT8DA.._WiFiProbe_1494471456.log");
        FormBodyPartBuilder formBody = FormBodyPartBuilder.create("file", new FileBody(file, ContentType.MULTIPART_FORM_DATA, file.getName()));
        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
        entityBuilder = entityBuilder.addPart(formBody.build().getName(), formBody.build().getBody());
        post.setEntity(entityBuilder.build());
        CloseableHttpResponse response = httpClient.execute(post);

        try {
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                System.err.println(EntityUtils.toString(response.getEntity(), "utf-8"));
            }
            EntityUtils.consume(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            response.close();
        }


    }
}
