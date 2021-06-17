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
public class SA_UPBIT_EXCHANGE_GET_ORDERS_CHANCE {
    @Autowired
	PjtUtil pjtU;

  
  @Autowired
  HttpUtil httpU;
//주문가능정보
  public HashMap<String,Object> run(String market) throws BizException, ClientProtocolException, NoSuchAlgorithmException, URISyntaxException, IOException  {
    HashMap<String, String> params = new HashMap<>();
    params.put("market",market);
    ArrayList<String> queryElements = new ArrayList<>();
    for(Map.Entry<String, String> entity : params.entrySet()) {
        queryElements.add(entity.getKey() + "=" + entity.getValue());
    }
    String queryString = String.join("&", queryElements.toArray(new String[0]));

    String jsonOutString = httpU.httpGetUpbitExchangeApi("https://api.upbit.com/v1/orders/chance", queryString);
    HashMap<String,Object> rtn = new HashMap<String,Object>();
    System.out.println("jsonOutString ="+jsonOutString);
    rtn=pjtU.JsonStringToObject(jsonOutString, HashMap.class);
    return rtn;
          /*
{
  "bid_fee": "0.0015",
  "ask_fee": "0.0015",
  "market": {
    "id": "KRW-BTC",
    "name": "BTC/KRW",
    "order_types": [
      "limit"
    ],
    "order_sides": [
      "ask",
      "bid"
    ],
    "bid": {
      "currency": "KRW",
      "price_unit": null,
      "min_total": 1000
    },
    "ask": {
      "currency": "BTC",
      "price_unit": null,
      "min_total": 1000
    },
    "max_total": "100000000.0",
    "state": "active",
  },
  "bid_account": {
    "currency": "KRW",
    "balance": "0.0",
    "locked": "0.0",
    "avg_buy_price": "0",
    "avg_buy_price_modified": false,
    "unit_currency": "KRW",
  },
  "ask_account": {
    "currency": "BTC",
    "balance": "10.0",
    "locked": "0.0",
    "avg_buy_price": "8042000",
    "avg_buy_price_modified": false,
    "unit_currency": "KRW",
  }
}

ask_fee	매도 수수료 비율	NumberString
market	마켓에 대한 정보	Object
market.id	마켓의 유일 키	String
market.name	마켓 이름	String
market.order_types	지원 주문 방식	Array[String]
market.order_sides	지원 주문 종류	Array[String]
market.bid	매수 시 제약사항	Object
market.bid.currency	화폐를 의미하는 영문 대문자 코드	String
market.bit.price_unit	주문금액 단위	String
market.bid.min_total	최소 매도/매수 금액	Number
market.ask	매도 시 제약사항	Object
market.ask.currency	화폐를 의미하는 영문 대문자 코드	String
market.ask.price_unit	주문금액 단위	String
market.ask.min_total	최소 매도/매수 금액	Number
market.max_total	최대 매도/매수 금액	NumberString
market.state	마켓 운영 상태	String
bid_account	매수 시 사용하는 화폐의 계좌 상태	Object
bid_account.currency	화폐를 의미하는 영문 대문자 코드	String
bid_account.balance	주문가능 금액/수량	NumberString
bid_account.locked	주문 중 묶여있는 금액/수량	NumberString
bid_account.avg_buy_price	매수평균가	NumberString
bid_account.avg_buy_price_modified	매수평균가 수정 여부	Boolean
bid_account.unit_currency	평단가 기준 화폐	String
ask_account	매도 시 사용하는 화폐의 계좌 상태	Object
ask_account.currency	화폐를 의미하는 영문 대문자 코드	String
ask_account.balance	주문가능 금액/수량	NumberString
ask_account.locked	주문 중 묶여있는 금액/수량	NumberString
ask_account.avg_buy_price	매수평균가	NumberString
ask_account.avg_buy_price_modified	매수평균가 수정 여부	Boolean
ask_account.unit_currency	평단가 기준 화폐	String
*/
    }
}
