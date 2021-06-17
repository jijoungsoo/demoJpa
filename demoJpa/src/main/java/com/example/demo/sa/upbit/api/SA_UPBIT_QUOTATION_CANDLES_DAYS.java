package com.example.demo.sa.upbit.api;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.example.demo.db.da.upbit.DA_UPBIT_CANDLES_DAYS;
import com.example.demo.db.domain.upbit.UpbitCandlesDays;
import com.example.demo.db.domain.upbit.UpbitCandlesDaysIdx;
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
public class SA_UPBIT_QUOTATION_CANDLES_DAYS {
    @Autowired
	PjtUtil pjtU;

  
  @Autowired
  HttpUtil httpU;

  @Autowired
	DA_UPBIT_CANDLES_DAYS daUpbitCandlesDays;
  

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
      ArrayList<HashMap<String,Object>> al = this.getCandlesDays(market, to ,  count);
      for(int i=0;i<al.size();i++){
        updateCandlesDays(al.get(i));
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

  
  private void updateCandlesDays(HashMap<String,Object> m) {
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
    Double PREV_CLOSING_PRICE  =   Double.parseDouble(m.get("prev_closing_price").toString());
    Double CHANGE_PRICE  =   0D;
    if(m.get("change_price")!=null){
      CHANGE_PRICE  =   Double.parseDouble(m.get("change_price").toString());
    }
    Double CHANGE_RATE  =   0D;
    if(m.get("change_rate")!=null){
       CHANGE_RATE  =   Double.parseDouble(m.get("change_rate").toString());
    }
    
    Double CONVERTED_TRADE_PRICE  =   0D;
    if(m.get("converted_trade_price")!=null){
       CONVERTED_TRADE_PRICE  =   Double.parseDouble(m.get("converted_trade_price").toString());  
    }
    /*
    convertingPriceUnit 파라미터의 경우, 원화 마켓이 아닌 다른 마켓(ex. BTC, ETH)의 일봉 요청시 종가를 명시된 파라미터 값으로 환산해 converted_trade_price 필드에 추가하여 반환합니다.
    현재는 원화(KRW) 로 변환하는 기능만 제공하며 추후 기능을 확장할 수 있습니다.
    */

    UpbitCandlesDaysIdx u = new UpbitCandlesDaysIdx();
		u.setMarket(MARKET);
		u.setCandleDateTimeUtc(CANDLE_DATE_TIME_UTC);
    Optional<UpbitCandlesDays> c =daUpbitCandlesDays.findById(u);
		if(c.isPresent()){
			daUpbitCandlesDays.updt( MARKET
      , CANDLE_DATE_TIME_UTC	
      , CANDLE_DATE_TIME_KST
      , OPENING_PRICE
      , HIGH_PRICE
      , LOW_PRICE	
      , TRADE_PRICE
      , TIMESTAMP
      , CANDLE_ACC_TRADE_PRICE
      , CANDLE_ACC_TRADE_VOLUME
      , PREV_CLOSING_PRICE
      , CHANGE_PRICE
      , CHANGE_RATE
      , CONVERTED_TRADE_PRICE
      );
		} else {
      daUpbitCandlesDays.crt( MARKET
      , CANDLE_DATE_TIME_UTC	
      , CANDLE_DATE_TIME_KST
      , OPENING_PRICE
      , HIGH_PRICE
      , LOW_PRICE	
      , TRADE_PRICE
      , TIMESTAMP
      , CANDLE_ACC_TRADE_PRICE
      , CANDLE_ACC_TRADE_VOLUME
      , PREV_CLOSING_PRICE
      , CHANGE_PRICE
      , CHANGE_RATE
      , CONVERTED_TRADE_PRICE
      );
    }
  }
   
    public ArrayList<HashMap<String,Object>> getCandlesDays(String market,String to ,String count) throws URISyntaxException, ClientProtocolException, IOException {
/*
market*
  마켓 코드 (ex. KRW-BTC)
to
  마지막 캔들 시각 (exclusive). 포맷 : yyyy-MM-dd'T'HH:mm:ss'Z' or yyyy-MM-dd HH:mm:ss. 비워서 요청시 가장 최근 캔들
count
  캔들 개수
convertingPriceUnit
  종가 환산 화폐 단위 (생략 가능, KRW로 명시할 시 원화 환산 가격을 반환.)
*/    
        List nameValuePairs = new ArrayList();
        nameValuePairs.add(new BasicNameValuePair("market", market));
        nameValuePairs.add(new BasicNameValuePair("to", to));
        nameValuePairs.add(new BasicNameValuePair("count", count));
        nameValuePairs.add(new BasicNameValuePair("convertingPriceUnit", "USDT"));        
        String jsonOutString = httpU.httpGetUpbit("https://api.upbit.com/v1/candles/days", nameValuePairs);
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
        "prev_closing_price": 8450000,
        "change_price": 176000,
        "change_rate": 0.0208284024
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
prev_closing_price	전일 종가(UTC 0시 기준)	Double
change_price	전일 종가 대비 변화 금액	Double
change_rate	전일 종가 대비 변화량	Double
converted_trade_price	종가 환산 화폐 단위로 환산된 가격(요청에 convertingPriceUnit 파라미터 없을 시 해당 필드 포함되지 않음.)	Double
convertingPriceUnit 파라미터의 경우, 원화 마켓이 아닌 다른 마켓(ex. BTC, ETH)의 일봉 요청시 종가를 명시된 파라미터 값으로 환산해 converted_trade_price 필드에 추가하여 반환합니다.
현재는 원화(KRW) 로 변환하는 기능만 제공하며 추후 기능을 확장할 수 있습니다.
*/
    }
}
