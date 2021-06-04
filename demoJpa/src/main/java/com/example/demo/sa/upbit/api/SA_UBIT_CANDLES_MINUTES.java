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

import com.example.demo.db.da.upbit.DA_UPBIT_CANDLES_MINUTES;
import com.example.demo.db.domain.upbit.UpbitCandlesMinutes;
import com.example.demo.db.domain.upbit.UpbitCandlesMinutesIdx;
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
public class SA_UBIT_CANDLES_MINUTES {
    @Autowired
	PjtUtil pjtU;

  @Autowired
	DA_UPBIT_CANDLES_MINUTES daUpbitCandlesMinutes;
  

  public void run(String unit,String market,String to , String count) throws BizException  {
    /*
market
      마켓 코드 (ex. KRW-BTC)
      KRW-BTC
to
      마지막 캔들 시각 (exclusive). 포맷 : yyyy-MM-dd'T'HH:mm:ss'Z' or yyyy-MM-dd HH:mm:ss. 비워서 요청시 가장 최근 캔들
count
      캔들 개수(최대 200개까지 요청 가능)
    */
    try {
      ArrayList<HashMap<String,String>> al = this.getCandlesMinutes(market, to ,  count);
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
    String MARKET  =   m.get("market");
    String CANDLE_DATE_TIME_UTC	  =   m.get("candle_date_time_utc");
    String CANDLE_DATE_TIME_KST  =   m.get("candle_date_time_kst");
    Double OPENING_PRICE  =  Double.parseDouble(m.get("opening_price"));
    Double HIGH_PRICE  =   Double.parseDouble(m.get("high_price"));
    Double LOW_PRICE	  =   Double.parseDouble(m.get("low_price"));
    Double TRADE_PRICE  =   Double.parseDouble(m.get("trade_price"));
    Long TIMESTAMP  =  Long.parseLong(m.get("timestamp"));
    Double CANDLE_ACC_TRADE_PRICE  =   Double.parseDouble(m.get("candle_acc_trade_price"));
    Double CANDLE_ACC_TRADE_VOLUME  =   Double.parseDouble(m.get("candle_acc_trade_volume"));
    Integer UNIT  =   Integer.parseInt(m.get("unit"));

    UpbitCandlesMinutesIdx u = new UpbitCandlesMinutesIdx();
		u.setMarket(MARKET);
		u.setCandleDateTimeUtc(CANDLE_DATE_TIME_UTC);
    Optional<UpbitCandlesMinutes> c =daUpbitCandlesMinutes.findById(u);
		if(c.isPresent()){
			daUpbitCandlesMinutes.updt( MARKET
      , CANDLE_DATE_TIME_UTC	
      , CANDLE_DATE_TIME_KST
      , OPENING_PRICE
      , HIGH_PRICE
      , LOW_PRICE	
      , TRADE_PRICE
      , TIMESTAMP
      , CANDLE_ACC_TRADE_PRICE
      , CANDLE_ACC_TRADE_VOLUME
      , UNIT);
		} else {
      daUpbitCandlesMinutes.crt( MARKET
      , CANDLE_DATE_TIME_UTC	
      , CANDLE_DATE_TIME_KST
      , OPENING_PRICE
      , HIGH_PRICE
      , LOW_PRICE	
      , TRADE_PRICE
      , TIMESTAMP
      , CANDLE_ACC_TRADE_PRICE
      , CANDLE_ACC_TRADE_VOLUME
      , UNIT);
/*
      String MARKET
				,String CANDLE_DATE_TIME_UTC	
				,String CANDLE_DATE_TIME_KST
				,Double OPENING_PRICE
				,Double HIGH_PRICE
				,Double LOW_PRICE	
				,Double TRADE_PRICE
				,Long TIMESTAMP
				,Double CANDLE_ACC_TRADE_PRICE
				,Double CANDLE_ACC_TRADE_VOLUME
				,Integer UNIT
*/

    }
  }
   
    public ArrayList<HashMap<String,String>> getCandlesMinutes(String market,String to , String count) throws URISyntaxException, ClientProtocolException, IOException {
/*
unit*
      분 단위. 가능한 값 : 1, 3, 5, 15, 10, 30, 60, 240
market
      마켓 코드 (ex. KRW-BTC)
      KRW-BTC
to
      마지막 캔들 시각 (exclusive). 포맷 : yyyy-MM-dd'T'HH:mm:ss'Z' or yyyy-MM-dd HH:mm:ss. 비워서 요청시 가장 최근 캔들
count
      캔들 개수(최대 200개까지 요청 가능)
*/
        CloseableHttpClient client = HttpClients.createDefault();
        List nameValuePairs = new ArrayList();
        nameValuePairs.add(new BasicNameValuePair("market", market));
        nameValuePairs.add(new BasicNameValuePair("to", to));
        nameValuePairs.add(new BasicNameValuePair("count", count));
        //HttpGet httpGet = new HttpGet("https://api.upbit.com/v1/candles/minutes/"+unit);
        HttpGet httpGet = new HttpGet("https://api.upbit.com/v1/candles/minutes/1");
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
        "candle_date_time_utc": "2018-04-18T10:16:00",
        "candle_date_time_kst": "2018-04-18T19:16:00",
        "opening_price": 8615000,
        "high_price": 8618000,
        "low_price": 8611000,
        "trade_price": 8616000,
        "timestamp": 1524046594584,
        "candle_acc_trade_price": 60018891.90054,
        "candle_acc_trade_volume": 6.96780929,
        "unit": 1
    }
]

market	마켓명	String
candle_date_time_utc	캔들 기준 시각(UTC 기준)	String
candle_date_time_kst	캔들 기준 시각(KST 기준)	String
opening_price	시가	Double
high_price	고가	Double
low_price	저가	Double
trade_price	종가	Double
timestamp	해당 캔들에서 마지막 틱이 저장된 시각	Long
candle_acc_trade_price	누적 거래 금액	Double
candle_acc_trade_volume	누적 거래량	Double
unit	분 단위(유닛)	Integer


          */
    }
}
