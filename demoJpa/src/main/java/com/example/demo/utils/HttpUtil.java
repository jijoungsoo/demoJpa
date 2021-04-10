package com.example.demo.utils;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class HttpUtil {
   
    public String httpGetAjax(String URL){
        String tmp = null;
		HttpClientBuilder httpClientBuilder = HttpClients.custom();
        SSLContext sslcontext;
        try {
            sslcontext = SSLContexts.custom()
            .setProtocol("SSL")
   // .loadTrustMaterial(null, new TrustSelfSignedStrategy())
            .loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString) throws CertificateException {
                    return true;
                }
            }).build();
			httpClientBuilder.setSSLHostnameVerifier(new NoopHostnameVerifier()).setSSLContext(sslcontext);
			CloseableHttpClient httpClient = httpClientBuilder.build();

			HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
			factory.setConnectTimeout(10000); // 타임아웃 설정 5초
			factory.setReadTimeout(10000);// 타임아웃 설정 5초
			RestTemplate restTemplate = new RestTemplate(factory);
	
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			
			UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(URL);
	
			headers.add("Cookie", "adult_chk=1; user_nickname=dd; member_idx= 11;");
	
			HttpEntity<?> entity = new HttpEntity<>(headers);
            headers.add("x-pjax", "true");            // ajax로 읽어오는 내용인데 이거 그냥 주석하자!! 전체 읽어와도 다를건 크게 없다.
    	    headers.add("x-requested-with", "XMLHttpRequest");
			ResponseEntity<String> resultMap = restTemplate.exchange(uriBuilder.build().toString(), HttpMethod.GET,entity, String.class);
			tmp = resultMap.getBody();
            try {
                httpClient.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

			
        } catch (KeyManagementException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (KeyStoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		return  tmp;
    }

    public String httpGet(String URL){
		String tmp = null;
		HttpClientBuilder httpClientBuilder = HttpClients.custom();
        SSLContext sslcontext;
        try {
            sslcontext = SSLContexts.custom()
            .setProtocol("SSL")
   // .loadTrustMaterial(null, new TrustSelfSignedStrategy())
            .loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString) throws CertificateException {
                    return true;
                }
            }).build();
			httpClientBuilder.setSSLHostnameVerifier(new NoopHostnameVerifier()).setSSLContext(sslcontext);
			CloseableHttpClient httpClient = httpClientBuilder.build();

			HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
			factory.setConnectTimeout(10000); // 타임아웃 설정 5초
			factory.setReadTimeout(10000);// 타임아웃 설정 5초
			RestTemplate restTemplate = new RestTemplate(factory);
	
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			
			UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(URL);
	
			headers.add("Cookie", "adult_chk=1; user_nickname=dd; member_idx= 11;");
	
			HttpEntity<?> entity = new HttpEntity<>(headers);
			ResponseEntity<String> resultMap = restTemplate.exchange(uriBuilder.build().toString(), HttpMethod.GET,entity, String.class);
			tmp = resultMap.getBody();
            try {
                httpClient.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

			
        } catch (KeyManagementException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (KeyStoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		return  tmp;
	}
     
}
