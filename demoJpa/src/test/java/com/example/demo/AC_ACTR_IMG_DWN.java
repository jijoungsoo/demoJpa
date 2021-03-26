package com.example.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;

public class AC_ACTR_IMG_DWN {
	@Test
    void download() {
        try {
            String url = "https://i2.avdbs.com/actor/a02/2426_n.jpg";
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(url);
			//String uri = request.getURI().toString();
			//System.out.println("uri=>"+uri);
			//String auth = request.getURI().getAuthority();
			//System.out.println("auth=>"+auth);
			//String fr = request.getURI().getFragment();
			//System.out.println("fr=>"+fr);
			//String host = request.getURI().getHost();
			//System.out.println("host=>"+host);
			String path = request.getURI().getPath();
			System.out.println("path=>"+path);  ////actor/a02/2426_n.jpg
			//String qu = request.getURI().getQuery();
			//System.out.println("qu=>"+qu);
 
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
 
            int responseCode = response.getStatusLine().getStatusCode();
 
            System.out.println("Request Url: " + request.getURI());
            System.out.println("Response Code: " + responseCode);
 
            String filePath2 = "d:\\avdbs.com\\file.zip";
            InputStream is = entity.getContent();

			String filePath = "d:/avdbs.com"+path;
			String p = filePath.substring(0,filePath.lastIndexOf("/"));
			String OsDirPath = p.replace("/", Matcher.quoteReplacement(File.separator));
			System.out.println(OsDirPath);
			File d = new File(OsDirPath);
			if(!d.exists()){	//폴더가 없으면 생성 상위 폴더 까지 전부
				Boolean b =d.mkdirs();
				System.out.println(b);
			}  			

			String OsFilePath = filePath.replace("/", Matcher.quoteReplacement(File.separator));
			//String lastPath = OsFilePath.replaceAll(Matcher.quoteReplacement(File.separator), "/");
			System.out.println(OsFilePath);
			
			File f = new File(OsFilePath);
            FileOutputStream fos = new FileOutputStream(f);
 
            int inByte;
            while ((inByte = is.read()) != -1) {
                fos.write(inByte);
            }
 
            is.close();
            fos.close();
 
            client.close();
			System.out.println(OsDirPath);
			System.out.println(OsFilePath);
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
