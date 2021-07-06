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
public class SA_UPBIT_EXCHANGE_POST_ORDERS {
    @Autowired
	PjtUtil pjtU;

  
  @Autowired
  HttpUtil httpU;
//주문리스트 조회
  public HashMap<String,Object> run(String MARKET,String SIDE,String  VOLUME ,String PRICE, String ORD_TYPE ) throws BizException, ClientProtocolException, NoSuchAlgorithmException, URISyntaxException, IOException  {
    HashMap<String, String> params = new HashMap<>();
    params.put("market", MARKET);
    params.put("side", SIDE);

    if(!pjtU.isEmpty(VOLUME)){
      params.put("volume", VOLUME);
    }

    if(!pjtU.isEmpty(PRICE)){
      params.put("price", PRICE);
    }
        
    params.put("ord_type", ORD_TYPE);

    String jsonOutString = httpU.httpPostUpbitExchangeApi("https://api.upbit.com/v1/orders", params);
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
  "avg_price":"0.0",
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
market *	마켓 ID (필수)	String
side *	주문 종류 (필수)
- bid : 매수
- ask : 매도	String
volume *	주문량 (지정가, 시장가 매도 시 필수)	NumberString
price *	주문 가격. (지정가, 시장가 매수 시 필수)
ex) KRW-BTC 마켓에서 1BTC당 1,000 KRW로 거래할 경우, 값은 1000 이 된다.
ex) KRW-BTC 마켓에서 1BTC당 매도 1호가가 500 KRW 인 경우, 시장가 매수 시 값을 1000으로 세팅하면 2BTC가 매수된다.
(수수료가 존재하거나 매도 1호가의 수량에 따라 상이할 수 있음)	NumberString
ord_type *	주문 타입 (필수)
- limit : 지정가 주문
- price : 시장가 주문(매수)
- market : 시장가 주문(매도)	String
identifier	조회용 사용자 지정값 (선택)	String (Uniq 값 사용)
🚧
원화 마켓 가격 단위를 확인하세요.

원화 마켓에서 주문을 요청 할 경우, 원화 마켓 주문 가격 단위 를 확인하여 값을 입력해주세요.

🚧
identifier 파라미터 사용

identifier는 서비스에서 발급하는 uuid가 아닌 이용자가 직접 발급하는 키값으로, 주문을 조회하기 위해 할당하는 값입니다. 해당 값은 사용자의 전체 주문 내 유일한 값을 전달해야하며, 비록 주문 요청시 오류가 발생하더라도 같은 값으로 다시 요청을 보낼 수 없습니다.

주문의 성공 / 실패 여부와 관계없이 중복해서 들어온 identifier 값에서는 중복 오류가 발생하니, 매 요청시 새로운 값을 생성해주세요.

🚧
시장가 주문

시장가 주문은 ord_type 필드를 price or market 으로 설정해야됩니다.
매수 주문의 경우 ord_type을 price로 설정하고 volume을 null 혹은 제외해야됩니다.
매도 주문의 경우 ord_type을 market로 설정하고 price을 null 혹은 제외해야됩니다.






Response
필드	설명	타입
uuid	주문의 고유 아이디	String
side	주문 종류	String
ord_type	주문 방식	String
price	주문 당시 화폐 가격	NumberString
avg_price	체결 가격의 평균가	NumberString
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
