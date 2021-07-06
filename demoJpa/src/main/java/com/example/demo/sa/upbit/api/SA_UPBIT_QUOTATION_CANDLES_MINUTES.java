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
import com.example.demo.utils.HttpUtil;
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
public class SA_UPBIT_QUOTATION_CANDLES_MINUTES {
    @Autowired
	PjtUtil pjtU;

  
  @Autowired
  HttpUtil httpU;

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
      ArrayList<HashMap<String,Object>> al = this.getCandlesMinutes(unit,market, to ,  count);
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

  
  private void updateMarket(HashMap<String,Object> m) {
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
    Integer UNIT  =   Integer.parseInt(m.get("unit").toString());

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
   
    public ArrayList<HashMap<String,Object>> getCandlesMinutes(String unit,String market,String to ,String count) throws URISyntaxException, ClientProtocolException, IOException, BizException {
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
    
        List nameValuePairs = new ArrayList();
        nameValuePairs.add(new BasicNameValuePair("market", market));
        nameValuePairs.add(new BasicNameValuePair("to", to));
        nameValuePairs.add(new BasicNameValuePair("count", count));
        String jsonOutString = httpU.httpGetUpbit("https://api.upbit.com/v1/candles/minutes/"+unit, nameValuePairs);
        ArrayList<HashMap<String,Object>> rtn = new ArrayList<HashMap<String,Object>>();
        System.out.println("jsonOutString ="+jsonOutString);
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
