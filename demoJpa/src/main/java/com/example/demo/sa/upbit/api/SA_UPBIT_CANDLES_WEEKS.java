package com.example.demo.sa.upbit.api;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.example.demo.db.da.upbit.DA_UPBIT_CANDLES_WEEKS;
import com.example.demo.db.domain.upbit.UpbitCandlesWeeks;
import com.example.demo.db.domain.upbit.UpbitCandlesWeeksIdx;
import com.example.demo.exception.BizException;
import com.example.demo.utils.HttpUtil;
import com.example.demo.utils.PjtUtil;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SA_UPBIT_CANDLES_WEEKS {
    @Autowired
	PjtUtil pjtU;

  
  @Autowired
  HttpUtil httpU;

  @Autowired
	DA_UPBIT_CANDLES_WEEKS daUpbitCandlesWeeks;
  

  public void run(String market,String to , String count) throws BizException  {
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
      ArrayList<HashMap<String,Object>> al = this.getCandlesWeeks(market, to ,  count);
      for(int i=0;i<al.size();i++){
        updateCandlesWeeks(al.get(i));
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

  
  private void updateCandlesWeeks(HashMap<String,Object> m) {
    String MARKET  =   m.get("market").toString();
    String CANDLE_DATE_TIME_UTC	  =   m.get("candle_date_time_utc").toString();
    String CANDLE_DATE_TIME_KST  =   m.get("candle_date_time_kst").toString();
    Double OPENING_PRICE  =  Double.parseDouble(m.get("opening_price").toString());
    Double HIGH_PRICE  =   Double.parseDouble(m.get("high_price").toString());
    Double LOW_PRICE	  =   Double.parseDouble(m.get("low_price").toString());
    Double TRADE_PRICE  =   Double.parseDouble(m.get("trade_price").toString());
    Long TIMESTAMP  =  Long.parseLong(m.get("timestamp").toString());
    Double CANDLE_ACC_TRADE_PRICE  =   Double.parseDouble(m.get("candle_acc_trade_price").toString());
    Double CANDLE_ACC_TRADE_VOLUME  =   Double.parseDouble(m.get("candle_acc_trade_volume").toString());
    String FIRST_DAY_OF_PERIOD  =   m.get("first_day_of_period").toString();
    
    UpbitCandlesWeeksIdx u = new UpbitCandlesWeeksIdx();
		u.setMarket(MARKET);
		u.setCandleDateTimeUtc(CANDLE_DATE_TIME_UTC);
    Optional<UpbitCandlesWeeks> c =daUpbitCandlesWeeks.findById(u);
		if(c.isPresent()){
			daUpbitCandlesWeeks.updt( MARKET
      , CANDLE_DATE_TIME_UTC	
      , CANDLE_DATE_TIME_KST
      , OPENING_PRICE
      , HIGH_PRICE
      , LOW_PRICE	
      , TRADE_PRICE
      , TIMESTAMP
      , CANDLE_ACC_TRADE_PRICE
      , CANDLE_ACC_TRADE_VOLUME
      , FIRST_DAY_OF_PERIOD
      );
		} else {
      daUpbitCandlesWeeks.crt( MARKET
      , CANDLE_DATE_TIME_UTC	
      , CANDLE_DATE_TIME_KST
      , OPENING_PRICE
      , HIGH_PRICE
      , LOW_PRICE	
      , TRADE_PRICE
      , TIMESTAMP
      , CANDLE_ACC_TRADE_PRICE
      , CANDLE_ACC_TRADE_VOLUME
      , FIRST_DAY_OF_PERIOD
      );
    }
  }
   
    public ArrayList<HashMap<String,Object>> getCandlesWeeks(String market,String to ,String count) throws URISyntaxException, ClientProtocolException, IOException {
/*
market*
  마켓 코드 (ex. KRW-BTC)
to
  마지막 캔들 시각 (exclusive). 포맷 : yyyy-MM-dd'T'HH:mm:ss'Z' or yyyy-MM-dd HH:mm:ss. 비워서 요청시 가장 최근 캔들
count
  캔들 개수
*/    
        List nameValuePairs = new ArrayList();
        nameValuePairs.add(new BasicNameValuePair("market", market));
        nameValuePairs.add(new BasicNameValuePair("to", to));
        nameValuePairs.add(new BasicNameValuePair("count", count));
        String jsonOutString = httpU.httpGetUpbit("https://api.upbit.com/v1/candles/weeks", nameValuePairs);
        ArrayList<HashMap<String,Object>> rtn = new ArrayList<HashMap<String,Object>>();
        System.out.println("jsonOutString ="+jsonOutString);
        rtn=pjtU.JsonStringToObject(jsonOutString, ArrayList.class);
        return rtn;
          /*
[
    {
        "market": "KRW-BTC",
        "candle_date_time_utc": "2018-04-18T00:00:00",
        "candle_date_time_kst": "2018-04-18T09:00:00",
        "opening_price": 8450000,
        "high_price": 8679000,
        "low_price": 8445000,
        "trade_price": 8626000,
        "timestamp": 1524046650532,
        "candle_acc_trade_price": 107184005903.68721,
        "candle_acc_trade_volume": 12505.93101659,
        "first_day_of_period": "2018-04-16"
    }
]

market	마켓명	String
candle_date_time_utc	캔들 기준 시각(UTC 기준)	String
candle_date_time_kst	캔들 기준 시각(KST 기준)	String
opening_price	시가	Double
high_price	고가	Double
low_price	저가	Double
trade_price	종가	Double
timestamp	마지막 틱이 저장된 시각	Long
candle_acc_trade_price	누적 거래 금액	Double
candle_acc_trade_volume	누적 거래량	Double
first_day_of_period	캔들 기간의 가장 첫 날	String
*/
    }
}
