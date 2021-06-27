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
public class SA_UPBIT_EXCHANGE_GET_ORDERS {
    @Autowired
	PjtUtil pjtU;

  
  @Autowired
  HttpUtil httpU;
//주문리스트 조회
  public ArrayList<HashMap<String,Object>> run(String MARKET, 
  String STATE,
  ArrayList<String> AL_STATES,
  ArrayList<String> AL_UUIDS,
  ArrayList<String> AL_IDENTIFIERS,
  String PAGE,
  String LIMIT,
  String ORDER_BY
  ) throws BizException, ClientProtocolException, NoSuchAlgorithmException, URISyntaxException, IOException  {
    
    HashMap<String, String> params = new HashMap<>();
    //params.put("market",MARKET);
    /*
    params.put("state","done");
    params.put("page",PAGE);
    params.put("limit","100");
    params.put("order_by","desc");
    */
    
    
    if(!pjtU.isEmpty(MARKET)){
      params.put("market",MARKET);
    }
    if(!pjtU.isEmpty(STATE)){   //디폴트   wait 체결대기
      params.put("state",STATE);
    }
    
    if(!pjtU.isEmpty(PAGE)){    //디폴트 1
      params.put("page",PAGE); 
    }

    if(!pjtU.isEmpty(LIMIT)){    //디폴트 100
      params.put("limit",LIMIT); 
    }
    if(!pjtU.isEmpty(ORDER_BY)){    //디폴트 desc
      params.put("order_by",ORDER_BY);
    }

    ArrayList<String> queryElements = new ArrayList<>();
    for(Map.Entry<String, String> entity : params.entrySet()) {
        queryElements.add(entity.getKey() + "=" + entity.getValue());
    }
    if(AL_STATES!=null){
      for(String state : AL_STATES) {
        queryElements.add("states[]=" + state);
      }
    }
    if(AL_UUIDS!=null){
      for(String uuid : AL_UUIDS) {
          queryElements.add("uuids[]=" + uuid);
      }
    }
    if(AL_IDENTIFIERS!=null){
      for(String identifier : AL_IDENTIFIERS) {
        queryElements.add("identifiers[]=" + identifier);
      }
    }

    String queryString = String.join("&", queryElements.toArray(new String[0]));
    System.out.println("queryString ="+queryString);
    String jsonOutString = httpU.httpGetUpbitExchangeApi("https://api.upbit.com/v1/orders", queryString);
    ArrayList<HashMap<String,Object>> rtn = new ArrayList<HashMap<String,Object>>();
    System.out.println("jsonOutString ="+jsonOutString);
    rtn=pjtU.JsonStringToObject(jsonOutString, ArrayList.class);
    return rtn;
          /*
[
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
  }
  # ....
]


Request Parameters
🚧
states 파라미터 변경 안내 (2021. 03. 22 ~)

2021년 3월 22일부터 미체결 주문(wait, watch)과 완료 주문(done, cancel)을 혼합하여 조회하실 수 없습니다.

예시1) done, cancel 주문을 한 번에 조회 => 가능
예시2) wait, done 주문을 한 번에 조회 => 불가능 (각각 API 호출 필요)

자세한 내용은 개발자 센터 공지사항을 참조 부탁드립니다.

Name	설명	타입
market	마켓 아이디	String
uuids	주문 UUID의 목록	Array
identifiers	주문 identifier의 목록	Array
state	주문 상태
- wait : 체결 대기 (default)
- watch : 예약주문 대기
- done : 전체 체결 완료
- cancel : 주문 취소	String
states	주문 상태의 목록

* 미체결 주문(wait, watch)과 완료 주문(done, cancel)은 혼합하여 조회하실 수 없습니다.	Array
page	페이지 수, default: 1	Number
limit	요청 개수, default: 100	Number
order_by	정렬 방식
- asc : 오름차순
- desc : 내림차순 (default)	String



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
*/
    }
}
