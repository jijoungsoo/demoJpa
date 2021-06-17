package com.example.demo.sa.upbit.api;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.example.demo.db.da.upbit.DA_UPBIT_TRADES_TICKS;
import com.example.demo.db.domain.upbit.UpbitTradesTicks;
import com.example.demo.db.domain.upbit.UpbitTradesTicksIdx;
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
public class SA_UPBIT_QUOTATION_TRADES_TICKS {
    @Autowired
	PjtUtil pjtU;

  
  @Autowired
  HttpUtil httpU;

  @Autowired
	DA_UPBIT_TRADES_TICKS daUpbitTradesTicks;
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
      ArrayList<HashMap<String,Object>> al = this.getTradesTicks(market, to ,  count);
      for(int i=0;i<al.size();i++){
        updateTradesTicks(al.get(i));
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

  
  private void updateTradesTicks(HashMap<String,Object> m) {
    String MARKET  =   m.get("market").toString();
    String TRADE_DATE_UTC	  =   m.get("trade_date_utc").toString();
    String TRADE_TIME_UTC  =   m.get("trade_time_utc").toString();
    Double TRADE_PRICE  =  Double.parseDouble(m.get("trade_price").toString());
    Double TRADE_VOLUME  =   Double.parseDouble(m.get("trade_volume").toString());
    Double PREV_CLOSING_PRICE	  =   Double.parseDouble(m.get("prev_closing_price").toString());
    Double CHANGE_PRICE  =   Double.parseDouble(m.get("change_price").toString());
    String ASK_BID  =   m.get("ask_bid").toString();
    Long TIMESTAMP  = 0L;
    if(m.get("timestamp") != null){
      TIMESTAMP = Long.parseLong(m.get("timestamp").toString());
    }
    Long SEQUENTIAL_ID  =   Long.parseLong(m.get("sequential_id").toString());
    UpbitTradesTicksIdx  t= new UpbitTradesTicksIdx();
		t.setMarket(MARKET);
		t.setSequentialId(SEQUENTIAL_ID);
    t.setTimestamp(TIMESTAMP);
    t.setTradeDateUtc(TRADE_DATE_UTC);
    t.setTradeTimeUtc(TRADE_TIME_UTC);
    Optional<UpbitTradesTicks> c =daUpbitTradesTicks.findById(t);
		if(c.isPresent()){
			daUpbitTradesTicks.updt(  MARKET
      , TRADE_DATE_UTC	
      , TRADE_TIME_UTC
      , TIMESTAMP
      , TRADE_PRICE	
      , TRADE_VOLUME
      , PREV_CLOSING_PRICE
      , CHANGE_PRICE
      , ASK_BID
      , SEQUENTIAL_ID
      );
		} else {
      daUpbitTradesTicks.crt( MARKET
      , TRADE_DATE_UTC	
      , TRADE_TIME_UTC
      , TIMESTAMP
      , TRADE_PRICE	
      , TRADE_VOLUME
      , PREV_CLOSING_PRICE
      , CHANGE_PRICE
      , ASK_BID
      , SEQUENTIAL_ID
      );
    }
  }
   
    public ArrayList<HashMap<String,Object>> getTradesTicks(String market,String to ,String count) throws URISyntaxException, ClientProtocolException, IOException {
/*
market*
string
마켓 코드 (ex. KRW-BTC)

to
string
마지막 체결 시각. 형식 : [HHmmss 또는 HH:mm:ss]. 비워서 요청시 가장 최근 데이터

count
int32
체결 개수

1
cursor
string
페이지네이션 커서 (sequentialId)

daysAgo
int32
최근 체결 날짜 기준 7일 이내의 이전 데이터 조회 가능. 비워서 요청 시 가장 최근 체결 날짜 반환. (범위: 1 ~ 7))
*/    
        List nameValuePairs = new ArrayList();
        nameValuePairs.add(new BasicNameValuePair("market", market));
        nameValuePairs.add(new BasicNameValuePair("to", to));
        nameValuePairs.add(new BasicNameValuePair("count", count));
        nameValuePairs.add(new BasicNameValuePair("cursor", ""));
        nameValuePairs.add(new BasicNameValuePair("daysAgo", ""));
        String jsonOutString = httpU.httpGetUpbit("https://api.upbit.com/v1/trades/ticks", nameValuePairs);
        ArrayList<HashMap<String,Object>> rtn = new ArrayList<HashMap<String,Object>>();
        System.out.println("jsonOutString ="+jsonOutString);
        rtn=pjtU.JsonStringToObject(jsonOutString, ArrayList.class);
        return rtn;
          /*
[
    {
        "market": "KRW-BTC",
        "trade_date_utc": "2018-04-18",
        "trade_time_utc": "10:19:58",
        "timestamp": 1524046798000,
        "trade_price": 8616000,
        "trade_volume": 0.03060688,
        "prev_closing_price": 8450000,
        "chane_price": 166000,
        "ask_bid": "ASK"
    }
]

trade_date_utc	체결 일자(UTC 기준)	String
trade_time_utc	체결 시각(UTC 기준)	String
timestamp	체결 타임스탬프	Long
trade_price	체결 가격	Double
trade_volume	체결량	Double
prev_closing_price	전일 종가	Double
change_price	변화량	Double
ask_bid	매도/매수	String
sequential_id	체결 번호(Unique)	Long
sequential_id 필드는 체결의 유일성 판단을 위한 근거로 쓰일 수 있습니다. 하지만 체결의 순서를 보장하지는 못합니다.
*/
    }
}
