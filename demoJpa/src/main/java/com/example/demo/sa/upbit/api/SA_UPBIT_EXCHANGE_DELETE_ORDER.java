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
public class SA_UPBIT_EXCHANGE_DELETE_ORDER {
    @Autowired
	PjtUtil pjtU;

  
  @Autowired
  HttpUtil httpU;
//주문리스트 조회
  public HashMap<String,Object> run(String UUID,String IDENTIFIER) throws BizException, ClientProtocolException, NoSuchAlgorithmException, URISyntaxException, IOException  {
    HashMap<String, String> params = new HashMap<>();
    if(!pjtU.isEmpty(UUID)){
      params.put("uuid",UUID);
    }

    if(!pjtU.isEmpty(IDENTIFIER)){
      params.put("identifier",IDENTIFIER);
    }
    
    ArrayList<String> queryElements = new ArrayList<>();
    for(Map.Entry<String, String> entity : params.entrySet()) {
        queryElements.add(entity.getKey() + "=" + entity.getValue());
    }

    String queryString = String.join("&", queryElements.toArray(new String[0]));
    String jsonOutString = "";
    try{
      jsonOutString = httpU.httpDelUpbitExchangeApi("https://api.upbit.com/v1/order", queryString);
    } catch(BizException e){
      throw e;
    }
    
    HashMap<String,Object> rtn = new HashMap<String,Object>();
    System.out.println("jsonOutString ="+jsonOutString);
    rtn=pjtU.JsonStringToObject(jsonOutString, HashMap.class);
    return rtn;
          /*
{
  "uuid":"cdd92199-2897-4e14-9448-f923320408ad",
  "side":"bid",
  "ord_type":"limit",
  "price":"100.0",
  "state":"wait",
  "market":"KRW-BTC",
  "created_at":"2018-04-10T15:42:23+09:00",
  "volume":"0.01",
  "remaining_volume":"0.01",
  "reserved_fee":"0.0015",
  "remaining_fee":"0.0015",
  "paid_fee":"0.0",
  "locked":"1.0015",
  "executed_volume":"0.0",
  "trades_count":0
}

Request Parameters
Name	설명	타입
uuid	취소할 주문의 UUID	String
identifier	조회용 사용자 지정값	String

uuid 혹은 identifier 둘 중 하나의 값이 반드시 포함되어야 합니다.


Response
필드	설명	타입
uuid	주문의 고유 아이디	String
side	주문 종류	String
ord_type	주문 방식	String
price	주문 당시 화폐 가격	NumberString
state	주문 상태	String
market	마켓의 유일키	String
created_at	주문 생성 시간	String
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
