package com.example.demo.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
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
	
            headers.add("Cookie","AWSALB=lWtH1NZvQAzA5FijUT741VkqZaAwGkbaLUMcmtkNX0LFljPBl97jN/wTY5uwSwR1/eP+RKWUoBP3ulbw+5PS69rtA7ED9UK/vO8MWqKwL9goo70hxuiQ9mAYRVCH; AWSALBCORS=lWtH1NZvQAzA5FijUT741VkqZaAwGkbaLUMcmtkNX0LFljPBl97jN/wTY5uwSwR1/eP+RKWUoBP3ulbw+5PS69rtA7ED9UK/vO8MWqKwL9goo70hxuiQ9mAYRVCH; vc=234552; _ga_6PD00KC58H=GS1.1.1618325005.1.1.1618325155.0; _ga=GA1.2.691413579.1618325156; _gid=GA1.2.1386655495.1618325156;adult_chk=1; user_nickname=dd; member_idx= 11;");
	
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

    public String httpGetAvdbs(String URL){
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
            ////프록시 설정 : START
            //Creating an HttpHost object for proxy
            /*
            HttpHost proxyHost = new HttpHost("198.16.66.101",1723); 
            //HttpHost proxyHost = new HttpHost("138.197.136.46",3128,"http"); 
            //creating a RoutePlanner object
            HttpRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxyHost);
            httpClientBuilder.setRoutePlanner(routePlanner);
            */
            ////프록시 설정 : END

            // 프록시 테스트 --- 프록시서버 ip주소를 못 참음
            // https://www.haiproxy.net 회원가입해서 1시간 테스트 해보자
            //  curl --head http://www.redcap.co.kr/page/CM_IR_0220?pgNm=MAIN --proxy 34.64.169.221:80

			CloseableHttpClient httpClient = httpClientBuilder.build();

            

			HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
			factory.setConnectTimeout(10000); // 타임아웃 설정 5초
			factory.setReadTimeout(10000);// 타임아웃 설정 5초
			RestTemplate restTemplate = new RestTemplate(factory);
	
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			
			UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(URL);
	
			//headers.add("Cookie", "adult_chk=1; user_nickname=dd; member_idx= 11;");
            headers.add("Cookie","AWSALB=lWtH1NZvQAzA5FijUT741VkqZaAwGkbaLUMcmtkNX0LFljPBl97jN/wTY5uwSwR1/eP+RKWUoBP3ulbw+5PS69rtA7ED9UK/vO8MWqKwL9goo70hxuiQ9mAYRVCH; AWSALBCORS=lWtH1NZvQAzA5FijUT741VkqZaAwGkbaLUMcmtkNX0LFljPBl97jN/wTY5uwSwR1/eP+RKWUoBP3ulbw+5PS69rtA7ED9UK/vO8MWqKwL9goo70hxuiQ9mAYRVCH; vc=234552; _ga_6PD00KC58H=GS1.1.1618325005.1.1.1618325155.0; _ga=GA1.2.691413579.1618325156; _gid=GA1.2.1386655495.1618325156;adult_chk=1; user_nickname=dd; member_idx= 11;");
	
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

    
    public String httpGetUpbit(String URL,List nameValuePairs) throws URISyntaxException, ClientProtocolException, IOException{
    	//QUOTATION API

		/*
		Websocket 연결 요청 수 제한
		초당 5회, 분당 100회
		REST API 요청 수 제한
		분당 600회, 초당 10회 (종목, 캔들, 체결, 티커, 호가별)		
		*/
		/*
		[Quotation API 추가 안내 사항]
		. Quotation API의 요청 수 제한은 IP 주소를 기준으로 합니다.
		. 향후 안정적인 서비스 제공을 위하여 API 요청 수는 추가적인 조정이 이루어질 수 있습니다. 요청 수 조정 필요 시 별도 공지를 통해 안내드리겠습니다.
		. 초당 제한 조건과 분당 제한 조건 중 하나의 조건이라도 요청 수를 초과할 경우 요청 수 제한 적용 됩니다.
		. 요청 수 제한 조건에 적용되는 시간 조건은 첫 요청 시간을 기준으로하며, 일정 시간 이후 초기화됩니다.(실패한 요청은 요청 횟수에 포함되지 않습니다.)
		. 다수의 REST API 요청이 필요하신 경우, 웹소켓을 통한 수신 부탁드립니다.

		앞으로 더욱 안정적이고 고도화된 서비스 제공을 위하여 노력하는 업비트 개발자 센터가 될 수 있도록 노력하겠으며,
		추후 요청 수 제한 기준의 변경이 있을 경우, 공지를 통하여 안내해 드릴 수 있도록 하겠습니다.		
		*/
		/*
		[Exchange API 잔여 요청 수 확인 방법]
		업비트 Open API 서비스는 원활한 사용 환경을 위해 초당 / 분당 요청 수를 제한하고 있습니다.
		Open API 호출 시 남아있는 요청 수는 Remaining-Req 응답 해더를 통해 확인 가능합니다.

		Remaining-Req: group=default; min=1799; sec=29
		위와 같은 포멧의 응답 해더를 수신했다면, default 라는 그룹에 대하여 해당 초에 29개의 요청, 남은 1분간 1799개의 요청이 가능하다는 것을 의미합니다.

		주문하기 Open API의 경우,

		Remaining-Req: group=order; min=59; sec=4
		위와 같은 응답이 올 수 있으며, 이는 order 라는 그룹에 대해 해당 초에 4번, 남은 1분은 59번의 주문 요청이 가능하다는 것을 의미합니다.
		*/
		/*
		해당 시간 내 초과된 요청에 대해서 429 Too Many Requests 오류가 발생할 수 있습니다. 하지만 별도의 추가적인 페널티는 부과되지 않습니다.
		*/
        String rtn="";
        String remaining_req="";
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(URL);
        httpGet.addHeader("Content-type", "application/json; charset=UTF-8");
        URIBuilder uriBuilder = new URIBuilder(httpGet.getURI());

        if(nameValuePairs!=null && nameValuePairs.size()>0){
            uriBuilder.addParameters(nameValuePairs);
        }
        URI uri =uriBuilder.build();
        System.out.println("uri = "+uri.toString());
        ((HttpRequestBase) httpGet).setURI(uri);
        try (CloseableHttpResponse response = client.execute(httpGet)){
            Header[] arr_header =response.getHeaders("remaining-req");
            System.out.print("arr_header.length =");
            System.out.println(arr_header.length);
            if(arr_header.length>0){
                remaining_req =arr_header[0].toString();
            }
            System.out.println(response.getStatusLine().toString()); 
            rtn = EntityUtils.toString(response.getEntity(), "UTF-8");
        }
        //remaining-req: group=candles; min=599; sec=9
        //분당 600회, 초당 10회

        
        String [] arr_tmp =remaining_req.split(";");
        String group = arr_tmp[0].replace("group=", "").trim();
        String min = arr_tmp[1].replace("min=", "").trim();
        String sec = arr_tmp[2].replace("sec=", "").trim();

        int i_sec= Integer.parseInt(sec);

        if(i_sec==0){
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e ){
                
            }
        }

        return  rtn;
	}

    public String httpGet2(String URL){
        String tmp;
        CloseableHttpClient t = HttpClients.custom()
        .setMaxConnTotal(3)
        .setMaxConnPerRoute(3)
        .build();
			HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(t);
			factory.setConnectTimeout(10000); // 타임아웃 설정 5초
			factory.setReadTimeout(10000);// 타임아웃 설정 5초
			RestTemplate restTemplate = new RestTemplate(factory);
	
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			
			UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(URL);
            	
			//headers.add("Cookie", "adult_chk=1; user_nickname=dd; member_idx= 11;");
            headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.114 Safari/537.36");
            headers.add("upgrade-insecure-requests", "1");
            headers.add("sec-fetch-user", "?1");
            headers.add("sec-fetch-site", "same-origin");
            headers.add("sec-fetch-mode", "navigate");
            headers.add("sec-fetch-dest", "document");
            headers.add("sec-ch-ua-mobile", "?0");
            headers.add("sec-ch-ua", "\"Google Chrome\";v=\"89\",\"Chromium\";v=\"89\",\";Not A Brand\";v=\"99\"");
            headers.add("pragma", "no-cache");
            headers.add("cache-control", "max-age=0");
            headers.add(":authority", "www.avdbs.com");
            headers.add(":path", "/menu/dvd.php?dvd_idx=790097");
            headers.add(":scheme", "https");
            headers.add("Cookie","AWSALB=lWtH1NZvQAzA5FijUT741VkqZaAwGkbaLUMcmtkNX0LFljPBl97jN/wTY5uwSwR1/eP+RKWUoBP3ulbw+5PS69rtA7ED9UK/vO8MWqKwL9goo70hxuiQ9mAYRVCH; AWSALBCORS=lWtH1NZvQAzA5FijUT741VkqZaAwGkbaLUMcmtkNX0LFljPBl97jN/wTY5uwSwR1/eP+RKWUoBP3ulbw+5PS69rtA7ED9UK/vO8MWqKwL9goo70hxuiQ9mAYRVCH; vc=234552; _ga_6PD00KC58H=GS1.1.1618325005.1.1.1618325155.0; _ga=GA1.2.691413579.1618325156; _gid=GA1.2.1386655495.1618325156");
            
	
			HttpEntity<?> entity = new HttpEntity<>(headers);
			ResponseEntity<String> resultMap = restTemplate.exchange(uriBuilder.build().toString(), HttpMethod.GET,entity, String.class);
			tmp = resultMap.getBody();
            HttpHeaders hds= resultMap.getHeaders();

            Iterator iteratorK = hds.keySet().iterator();
            while (iteratorK.hasNext()) {
                Object key = iteratorK.next();
                List<String> value = hds.get(key);
                
                    System.out.println("[key]:" + key + ", [value]:" + value);
                
            }

			
 
		return  tmp;
	}
     
}
