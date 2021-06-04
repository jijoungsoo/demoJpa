package com.example.demo.sa.upbit.api;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.example.demo.db.da.upbit.DA_UPBIT_MARKET;
import com.example.demo.db.domain.upbit.UpbitMarket;
import com.example.demo.exception.BizException;
import com.example.demo.utils.PjtUtil;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SA_UBIT_MARKET_ALL {
    @Autowired
	PjtUtil pjtU;

  @Autowired
	DA_UPBIT_MARKET daUpbitMarket;
  

  public void run() throws BizException  {
    try {
      ArrayList<HashMap<String,String>> al = this.getMarketAll();
      for(int i=0;i<al.size();i++){
        updateMarket(al.get(i));
      }
    } catch (ClientProtocolException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (URISyntaxException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } 
  }

  
  private void updateMarket(HashMap<String,String> m) {
              String market  =   m.get("market");
              String kNm  =   m.get("korean_name");
              String eNm      =   m.get("english_name");
              String market_warning      =   m.get("market_warning");
              


              Optional<UpbitMarket> c =daUpbitMarket.findById(market);
		if(c.isPresent()){
			daUpbitMarket.updt(market,kNm,eNm,market_warning);
		} else {
      daUpbitMarket.crt(market,kNm,eNm,market_warning);
    }
  }
   
    public ArrayList<HashMap<String,String>> getMarketAll() throws URISyntaxException, ClientProtocolException, IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        List nameValuePairs = new ArrayList();
        nameValuePairs.add(new BasicNameValuePair("isDetails", "false"));
        /*
isDetails
boolean
유의종목 필드과 같은 상세 정보 노출 여부(선택 파라미터)
        */

        HttpGet httpGet = new HttpGet("https://api.upbit.com/v1/market/all");
        URI uri = new URIBuilder(httpGet.getURI())
          .addParameters(nameValuePairs)
          .build();
       ((HttpRequestBase) httpGet).setURI(uri);
        CloseableHttpResponse response = client.execute(httpGet);
        client.close();

          //HTTP Response객체의 status 상태를 화면에 출력,  200 OK이면 정상
          System.out.println(response.getStatusLine().toString()); 
            
          InputStream is = null;
          BufferedInputStream bis = null;
          //response객체의 body중 컨텐츠 부분을 획득
          is = response.getEntity().getContent(); 
          bis = new BufferedInputStream(is);
          StringBuilder sb = new StringBuilder();
          byte[] buffer = new byte[1024];
          while ((bis.read(buffer)) != -1) {
              //byte[]배열을 utf-8형식으로 인코딩하여 String 객체를 만들어내는곳
              String str = new String(buffer, "utf-8"); 
              //System.out.println(str);
              sb.append(str);
          }
            String  jsonOutString = sb.toString();
            ArrayList<HashMap<String,String>> rtn = new ArrayList<HashMap<String,String>>();
            rtn=pjtU.JsonStringToObject(jsonOutString, ArrayList.class);
          return rtn;

          /*
          [
            {
                    "market": "KRW-BTC",
                    "korean_name": "비트코인",
                    "english_name": "Bitcoin"
                },
            ]
          */
    }
}
