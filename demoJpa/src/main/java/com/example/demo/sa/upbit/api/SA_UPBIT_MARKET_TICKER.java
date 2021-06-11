package com.example.demo.sa.upbit.api;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.example.demo.db.da.upbit.DA_UPBIT_MARKET;
import com.example.demo.db.domain.upbit.UpbitCandlesDays;
import com.example.demo.db.domain.upbit.UpbitCandlesDaysIdx;
import com.example.demo.db.domain.upbit.UpbitMarket;
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
public class SA_UPBIT_MARKET_TICKER {
    @Autowired
	PjtUtil pjtU;

  
  @Autowired
  HttpUtil httpU;

  @Autowired
	DA_UPBIT_MARKET daUpbitMarket;
  

  public void run(String markets) throws BizException  {
    /*
market
      마켓 코드 (ex. KRW-BTC)
      KRW-BTC
    */
    try {
      ArrayList<HashMap<String,Object>> al = this.getTicker(markets);
      for(int i=0;i<al.size();i++){
        updateTicker(al.get(i));
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

  
  private void updateTicker(HashMap<String,Object> m) {
    String MARKET  =   m.get("market").toString();
    String TRADE_DATE	  =   m.get("trade_date").toString();
    String TRADE_TIME  =   m.get("trade_time").toString();
    String TRADE_DATE_KST  =   m.get("trade_date_kst").toString();
    String TRADE_TIME_KST  =   m.get("trade_time_kst").toString();
    Double OPENING_PRICE  =  Double.parseDouble(m.get("opening_price").toString());
    Double HIGH_PRICE  =   Double.parseDouble(m.get("high_price").toString());
    Double LOW_PRICE	  =   Double.parseDouble(m.get("low_price").toString());
    Double TRADE_PRICE  =   Double.parseDouble(m.get("trade_price").toString());
    Double PREV_CLOSING_PRICE  =   Double.parseDouble(m.get("prev_closing_price").toString());
    String CHANGE  =   m.get("change").toString();
    Double CHANGE_PRICE  =   Double.parseDouble(m.get("change_price").toString());
    Double CHANGE_RATE  =   Double.parseDouble(m.get("change_rate").toString());
    Double SIGNED_CHANGE_PRICE  =   Double.parseDouble(m.get("signed_change_price").toString());
    Double SIGNED_CHANGE_RATE  =   Double.parseDouble(m.get("signed_change_rate").toString());
    Double TRADE_VOLUME  =   Double.parseDouble(m.get("trade_volume").toString());
    Double ACC_TRADE_PRICE  =   Double.parseDouble(m.get("acc_trade_price").toString());
    Double ACC_TRADE_PRICE_24H  =   Double.parseDouble(m.get("acc_trade_price_24h").toString());
    Double ACC_TRADE_VOLUME  =   Double.parseDouble(m.get("acc_trade_volume").toString());
    Double ACC_TRADE_VOLUME_24H  =   Double.parseDouble(m.get("acc_trade_volume_24h").toString());
    Double HIGHEST_52_WEEK_PRICE  =   Double.parseDouble(m.get("highest_52_week_price").toString());
    String HIGHEST_52_WEEK_DATE  =   m.get("highest_52_week_date").toString();
    Double LOWEST_52_WEEK_PRICE  =   Double.parseDouble(m.get("lowest_52_week_price").toString());
    String LOWEST_52_WEEK_DATE  =   m.get("lowest_52_week_date").toString();
    Long TIMESTAMP  =  Long.parseLong(m.get("timestamp").toString());
    
    Optional<UpbitMarket> c =daUpbitMarket.findById(MARKET);
    System.out.println(MARKET);
		if(c.isPresent()){
			daUpbitMarket.updtTicker(
          MARKET
        , TRADE_DATE
        , TRADE_TIME
        , TRADE_DATE_KST
        , TRADE_TIME_KST
        , OPENING_PRICE
        , HIGH_PRICE
        , LOW_PRICE
        , TRADE_PRICE
        , PREV_CLOSING_PRICE
        , CHANGE
        , CHANGE_PRICE
        , CHANGE_RATE
        , SIGNED_CHANGE_PRICE
        , SIGNED_CHANGE_RATE
        , TRADE_VOLUME
        , ACC_TRADE_PRICE
        , ACC_TRADE_PRICE_24H
        , ACC_TRADE_VOLUME
        , ACC_TRADE_VOLUME_24H
        , HIGHEST_52_WEEK_PRICE
        , HIGHEST_52_WEEK_DATE
        , LOWEST_52_WEEK_PRICE
        , LOWEST_52_WEEK_DATE
        , TIMESTAMP
      );		
    }
  }
   
    public ArrayList<HashMap<String,Object>> getTicker(String markets) throws URISyntaxException, ClientProtocolException, IOException {
/*
markets*
  반점으로 구분되는 마켓 코드 (ex. KRW-BTC, BTC-ETH)
*/    
        List nameValuePairs = new ArrayList();
        System.out.println("vvvvvvv");
        System.out.println(markets);
        nameValuePairs.add(new BasicNameValuePair("markets", markets));
        String jsonOutString = httpU.httpGetUpbit("https://api.upbit.com/v1/ticker", nameValuePairs);
        ArrayList<HashMap<String,Object>> rtn = new ArrayList<HashMap<String,Object>>();
        System.out.println("jsonOutString ="+jsonOutString);
        rtn=pjtU.JsonStringToObject(jsonOutString, ArrayList.class);
        return rtn;
          /*
[
    {
        "market": "KRW-BTC",
        "trade_date": "20180418",
        "trade_time": "102340",
        "trade_date_kst": "20180418",
        "trade_time_kst": "192340",
        "trade_timestamp": 1524047020000,
        "opening_price": 8450000,
        "high_price": 8679000,
        "low_price": 8445000,
        "trade_price": 8621000,
        "prev_closing_price": 8450000,
        "change": "RISE",
        "change_price": 171000,
        "change_rate": 0.0202366864,
        "signed_change_price": 171000,
        "signed_change_rate": 0.0202366864,
        "trade_volume": 0.02467802,
        "acc_trade_price": 108024804862.58254,
        "acc_trade_price_24h": 232702901371.09309,
        "acc_trade_volume": 12603.53386105,
        "acc_trade_volume_24h": 27181.31137002,
        "highest_52_week_price": 28885000,
        "highest_52_week_date": "2018-01-06",
        "lowest_52_week_price": 4175000,
        "lowest_52_week_date": "2017-09-25",
        "timestamp": 1524047026072
    }
]

market	종목 구분 코드	String
trade_date	최근 거래 일자(UTC)	String
trade_time	최근 거래 시각(UTC)	String
trade_date_kst	최근 거래 일자(KST)	String
trade_time_kst	최근 거래 시각(KST)	String
opening_price	시가	Double
high_price	고가	Double
low_price	저가	Double
trade_price	종가	Double
prev_closing_price	전일 종가	Double
change	EVEN : 보합
RISE : 상승
FALL : 하락	String
change_price	변화액의 절대값	Double
change_rate	변화율의 절대값	Double
signed_change_price	부호가 있는 변화액	Double
signed_change_rate	부호가 있는 변화율	Double
trade_volume	가장 최근 거래량	Double
acc_trade_price	누적 거래대금(UTC 0시 기준)	Double
acc_trade_price_24h	24시간 누적 거래대금	Double
acc_trade_volume	누적 거래량(UTC 0시 기준)	Double
acc_trade_volume_24h	24시간 누적 거래량	Double
highest_52_week_price	52주 신고가	Double
highest_52_week_date	52주 신고가 달성일	String
lowest_52_week_price	52주 신저가	Double
lowest_52_week_date	52주 신저가 달성일	String
timestamp	타임스탬프	Long
*/
    }
}
