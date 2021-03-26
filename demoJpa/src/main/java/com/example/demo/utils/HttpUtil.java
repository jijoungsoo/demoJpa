package com.example.demo.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpUtil {
    public void download() {
        try {
            String url = "https://i2.avdbs.com/actor/a02/2426_n.jpg";
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);
 
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
 
            int responseCode = response.getStatusLine().getStatusCode();
 
            System.out.println("Request Url: " + request.getURI());
            System.out.println("Response Code: " + responseCode);
 
            InputStream is = entity.getContent();
 
            String filePath = "c:\\demo\\file.zip";
            FileOutputStream fos = new FileOutputStream(new File(filePath));
 
            int inByte;
            while ((inByte = is.read()) != -1) {
                fos.write(inByte);
            }
 
            is.close();
            fos.close();
 
            client.close();
            System.out.println("File Download Completed!!!");
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     
}
