package com.example.demo.sa.upbit.api;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.demo.exception.BizException;
import com.example.demo.utils.HttpUtil;
import com.example.demo.utils.PjtUtil;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SA_UPBIT_EXCHANGE_GET_ORDER {
    @Autowired
	PjtUtil pjtU;

  
  @Autowired
  HttpUtil httpU;
//개별주문조회
  public HashMap<String,Object> run(String UUID) throws BizException, ClientProtocolException, NoSuchAlgorithmException, URISyntaxException, IOException  {
    HashMap<String, String> params = new HashMap<>();
    params.put("uuid",UUID);
    ArrayList<String> queryElements = new ArrayList<>();
    for(Map.Entry<String, String> entity : params.entrySet()) {
        queryElements.add(entity.getKey() + "=" + entity.getValue());
    }
    String queryString = String.join("&", queryElements.toArray(new String[0]));

    String jsonOutString = httpU.httpGetUpbitExchangeApi("https://api.upbit.com/v1/order", queryString);
    HashMap<String,Object> rtn = new HashMap<String,Object>();
    System.out.println("jsonOutString ="+jsonOutString);
    rtn=pjtU.JsonStringToObject(jsonOutString, HashMap.class);
    return rtn;
          /*
{
  "uuid": "9ca023a5-851b-4fec-9f0a-48cd83c2eaae",
  "side": "ask",
  "ord_type": "limit",
  "price": "4280000.0",
  "state": "done",
  "market": "KRW-BTC",
  "created_at": "2019-01-04T13:48:09+09:00",
  "volume": "1.0",
  "remaining_volume": "0.0",
  "reserved_fee": "0.0",
  "remaining_fee": "0.0",
  "paid_fee": "2140.0",
  "locked": "0.0",
  "executed_volume": "1.0",
  "trades_count": 1,
  "trades": [
    {
      "market": "KRW-BTC",
      "uuid": "9e8f8eba-7050-4837-8969-cfc272cbe083",
      "price": "4280000.0",
      "volume": "1.0",
      "funds": "4280000.0",
      "side": "ask"
    }
  ]
}
Request Parameters
Name	설명	타입
uuid	주문 UUID	String
identifier	조회용 사용자 지정 값	String

Response
필드	설명	타입
uuid	주문의 고유 아이디	String
side	주문 종류	String
ord_type	주문 방식	String
price	주문 당시 화폐 가격	NumberString
state	주문 상태	String
market	마켓의 유일키	String
created_at	주문 생성 시간	DateString
volume	사용자가 입력한 주문 양	NumberString
remaining_volume	체결 후 남은 주문 양	NumberString
reserved_fee	수수료로 예약된 비용	NumberString
remaining_fee	남은 수수료	NumberString
paid_fee	사용된 수수료	NumberString
locked	거래에 사용중인 비용	NumberString
executed_volume	체결된 양	NumberString
trade_count	해당 주문에 걸린 체결 수	Integer
trades	체결	Array[Object]
trades.market	마켓의 유일 키	String
trades.uuid	체결의 고유 아이디	String
trades.price	체결 가격	NumberString
trades.volume	체결 양	NumberString
trades.funds	체결된 총 가격	NumberString
trades.side	체결 종류	String
trades.created_at	체결 시각	DateString
*/
    }
}
