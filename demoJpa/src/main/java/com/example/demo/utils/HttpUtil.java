package com.example.demo.utils;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.SSLContext;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.exception.BizException;
import com.google.gson.Gson;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
	PjtUtil pjtU;
   
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

    
    public String httpGetUpbit(String URL,List nameValuePairs) throws URISyntaxException, ClientProtocolException, IOException, BizException{
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
        HttpRequestBase req =(HttpRequestBase) httpGet;
        req.setURI(uri);
        try (CloseableHttpResponse response = client.execute(httpGet)){
            Header[] arr_header =response.getHeaders("remaining-req");
            System.out.print("arr_header.length =");
            System.out.println(arr_header.length);
            if(arr_header.length>0){
                remaining_req =arr_header[0].toString();
            }
            StatusLine tmp_status= response.getStatusLine();
            int status_code = tmp_status.getStatusCode();
            System.out.println("status_code : "+ status_code);
            

            System.out.println("URL:"+URL);
            System.out.println("request.getURI().getRawQuery():"+req.getURI().getRawQuery());
            rtn = EntityUtils.toString(response.getEntity(), "UTF-8");


            System.out.println("remaining_req:"+remaining_req);
            System.out.println("rtn:"+rtn);

            if(status_code==404){
                HashMap<String,Object> tmp_error=pjtU.JsonStringToObject(rtn, HashMap.class);
                HashMap<String,Object> tmp_error2=(HashMap<String,Object>)tmp_error.get("error");
                throw new BizException("error :"+tmp_error2.get("message"));
            }
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

    public String httpGetUpbitExchangeApi(String URL) throws URISyntaxException, ClientProtocolException, IOException, NoSuchAlgorithmException{
        String rtn="";
        String remaining_req="";
        String accessKey = System.getenv("UPBIT_OPEN_API_ACCESS_KEY");
        String secretKey = System.getenv("UPBIT_OPEN_API_SECRET_KEY");

        System.out.println("전체 OS 환경변수 값 : " + System.getenv());
        System.out.println("accessKey = " +accessKey);
        System.out.println("secretKey = " +secretKey);

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String jwtToken = JWT.create()
                .withClaim("access_key", accessKey)
                .withClaim("nonce", UUID.randomUUID().toString())
                .sign(algorithm);
        String authenticationToken = "Bearer " + jwtToken;

        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(URL);
            request.setHeader("Content-Type", "application/json");
            request.addHeader("Authorization", authenticationToken);

            HttpResponse response = client.execute(request);
            Header[] arr_header =response.getHeaders("remaining-req");
            System.out.print("arr_header.length =");
            System.out.println(arr_header.length);
            if(arr_header.length>0){
                remaining_req =arr_header[0].toString();
            }
            org.apache.http.HttpEntity entity = response.getEntity();
            rtn = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
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

    
    public String httpGetBinianceGetServerTime() throws URISyntaxException, ClientProtocolException, IOException, NoSuchAlgorithmException{
        String ServerTime="";
        try {

            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet("https://api.binance.com/api/v3/time");
            request.setHeader("Content-Type", "application/json");
            HttpResponse response = client.execute(request);
            org.apache.http.HttpEntity entity = response.getEntity();
            String rtn = EntityUtils.toString(entity, "UTF-8");
            HashMap<String,Object> tmp=pjtU.JsonStringToObject(rtn, HashMap.class);
            ServerTime = tmp.get("serverTime").toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return  ServerTime;
	}

    public String httpGetBiniance(String URL, String queryString) throws URISyntaxException, ClientProtocolException, IOException, NoSuchAlgorithmException{
        String rtn="";
        String accessKey = System.getenv("BINANCE-API-KEY");
        String secretKey = System.getenv("BINANCE-API-SECRET");

        //https://github.com/binance-exchange/binance-java-api/blob/master/src/main/java/com/binance/api/client/security/HmacSHA256Signer.java
        String signature = HmacSHA256Signer.sign(queryString, secretKey);

        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            if(!PjtUtil.isEmpty(queryString)){
                URL=URL+"?"+queryString+"&signature="+signature;
            }
            System.out.println(URL);
            HttpGet request = new HttpGet(URL);
            request.setHeader("Content-Type", "application/json");
            request.addHeader("X-MBX-APIKEY", accessKey);

            HttpResponse response = client.execute(request);
            org.apache.http.HttpEntity entity = response.getEntity();
            rtn = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return  rtn;
	}

    
    public String httpGetUpbitExchangeApi(String URL, String queryString) throws URISyntaxException, ClientProtocolException, IOException, NoSuchAlgorithmException{
        String rtn="";
        String remaining_req="";
        String accessKey = System.getenv("UPBIT_OPEN_API_ACCESS_KEY");
        String secretKey = System.getenv("UPBIT_OPEN_API_SECRET_KEY");

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(queryString.getBytes("UTF-8"));
        String queryHash = String.format("%0128x", new BigInteger(1, md.digest()));
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String jwtToken = JWT.create()
                .withClaim("access_key", accessKey)
                .withClaim("nonce", UUID.randomUUID().toString())
                .withClaim("query_hash", queryHash)
                .withClaim("query_hash_alg", "SHA512")
                .sign(algorithm);
        String authenticationToken = "Bearer " + jwtToken;

        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            if(!PjtUtil.isEmpty(queryString)){
                URL=URL+"?"+queryString;
            }
            HttpGet request = new HttpGet(URL);
            request.setHeader("Content-Type", "application/json");
            request.addHeader("Authorization", authenticationToken);

            HttpResponse response = client.execute(request);
            Header[] arr_header =response.getHeaders("remaining-req");
            System.out.print("arr_header.length =");
            System.out.println(arr_header.length);
            if(arr_header.length>0){
                remaining_req =arr_header[0].toString();
            }
            org.apache.http.HttpEntity entity = response.getEntity();
            rtn = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
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

    public String httpDelUpbitExchangeApi(String URL, String queryString) throws URISyntaxException, ClientProtocolException, IOException, NoSuchAlgorithmException, BizException{
        String rtn="";
        String remaining_req="";
        String accessKey = System.getenv("UPBIT_OPEN_API_ACCESS_KEY");
        String secretKey = System.getenv("UPBIT_OPEN_API_SECRET_KEY");

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(queryString.getBytes("UTF-8"));
        String queryHash = String.format("%0128x", new BigInteger(1, md.digest()));
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String jwtToken = JWT.create()
                .withClaim("access_key", accessKey)
                .withClaim("nonce", UUID.randomUUID().toString())
                .withClaim("query_hash", queryHash)
                .withClaim("query_hash_alg", "SHA512")
                .sign(algorithm);
        String authenticationToken = "Bearer " + jwtToken;
        int status_code = 0;
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            if(!PjtUtil.isEmpty(queryString)){
                URL=URL+"?"+queryString;
            }
            HttpDelete request = new HttpDelete(URL);
            request.setHeader("Content-Type", "application/json");
            request.addHeader("Authorization", authenticationToken);

            HttpResponse response = client.execute(request);
            Header[] arr_header =response.getHeaders("remaining-req");
            System.out.print("arr_header.length =");
            System.out.println(arr_header.length);
            if(arr_header.length>0){
                remaining_req =arr_header[0].toString();
            }
            status_code = response.getStatusLine().getStatusCode();
            org.apache.http.HttpEntity entity = response.getEntity();
            rtn = EntityUtils.toString(entity, "UTF-8");
            
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
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
        System.out.print("status code ="+status_code);
        if(status_code>=400 && status_code <500){
            //에러 
            /*
            Object	error
            String	error.name
            String	error.message
            {"error":{"message":"권한이 부족합니다.","name":"out_of_scope"}}
            */
            throw new BizException(rtn);
        }
        return  rtn;
	}
    
    public String httpPostUpbitExchangeApi(String URL,  HashMap<String, String> params) throws URISyntaxException, ClientProtocolException, IOException, NoSuchAlgorithmException, BizException{
        String rtn="";
        String remaining_req="";
        String accessKey = System.getenv("UPBIT_OPEN_API_ACCESS_KEY");
        String secretKey = System.getenv("UPBIT_OPEN_API_SECRET_KEY");

        ArrayList<String> queryElements = new ArrayList<>();
        for(Map.Entry<String, String> entity : params.entrySet()) {
            queryElements.add(entity.getKey() + "=" + entity.getValue());
        }

        String queryString = String.join("&", queryElements.toArray(new String[0]));


        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(queryString.getBytes("UTF-8"));
        String queryHash = String.format("%0128x", new BigInteger(1, md.digest()));
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        String jwtToken = JWT.create()
                .withClaim("access_key", accessKey)
                .withClaim("nonce", UUID.randomUUID().toString())
                .withClaim("query_hash", queryHash)
                .withClaim("query_hash_alg", "SHA512")
                .sign(algorithm);
        String authenticationToken = "Bearer " + jwtToken;

        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            /* post 전송이므로 빠져야한다.
            if(!PjtUtil.isEmpty(queryString)){
                URL=URL+"?"+queryString;
            }
            */
            HttpPost request = new HttpPost(URL);
            request.setHeader("Content-Type", "application/json");
            request.addHeader("Authorization", authenticationToken);
            request.setEntity(new StringEntity(new Gson().toJson(params)));


            System.out.println("accessKey:"+accessKey);
            System.out.println("secretKey:"+secretKey);

            System.out.println("URL:"+URL);
            System.out.println("request.getURI().getRawQuery():"+request.getURI().getRawQuery());

            HttpResponse response = client.execute(request);
            StatusLine tmp_status =response.getStatusLine();
            int status_code = tmp_status.getStatusCode();
            System.out.println("status_code : "+ status_code);

            Header[] arr_header =response.getHeaders("remaining-req");
            org.apache.http.HttpEntity entity = response.getEntity();
            rtn = EntityUtils.toString(entity, "UTF-8");
            System.out.println("rtn:"+rtn);
            if(status_code==404){
                throw new BizException("페이지 주소가 잘못되었다. URL :"+URL);
            }

            if(status_code==400){
                HashMap<String,Object> tmp_error=pjtU.JsonStringToObject(rtn, HashMap.class);
                HashMap<String,Object> tmp_error2=(HashMap<String,Object>)tmp_error.get("error");
                throw new BizException("error :"+tmp_error2.get("message"));
            }

            if(status_code==401){
                HashMap<String,Object> tmp_error=pjtU.JsonStringToObject(rtn, HashMap.class);
                HashMap<String,Object> tmp_error2=(HashMap<String,Object>)tmp_error.get("error");
                throw new BizException("error :"+tmp_error2.get("message"));
            }
            


            
            System.out.print("arr_header.length =");
            System.out.println(arr_header.length);
            if(arr_header.length>0){
                remaining_req =arr_header[0].toString();
            }
            
            System.out.print("remaining_req:");
            System.out.println(remaining_req);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
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
