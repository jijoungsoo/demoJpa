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
public class SA_UPBIT_EXCHANGE_POST_WITHDRAWS_COIN  {
    @Autowired
	PjtUtil pjtU;

  
  @Autowired
  HttpUtil httpU;
//코인출금하기
  public HashMap<String,Object> run(String CURRENCY,String AMOUNT,String  ADDRESS 
  ,String SECONDARY_ADDRESS, String TRANSACTION_TYPE  ) throws BizException, ClientProtocolException, NoSuchAlgorithmException, URISyntaxException, IOException  {
    HashMap<String, String> params = new HashMap<>();
    params.put("currency", CURRENCY);
    params.put("amount", AMOUNT);
    params.put("address", ADDRESS);
    params.put("secondary_address", SECONDARY_ADDRESS);
    params.put("transaction_type", TRANSACTION_TYPE);

    String jsonOutString = httpU.httpPostUpbitExchangeApi("https://api.upbit.com/v1/withdraws/coin", params);
    HashMap<String,Object> rtn = new HashMap<String,Object>();
    System.out.println("jsonOutString ="+jsonOutString);
    rtn=pjtU.JsonStringToObject(jsonOutString, HashMap.class);
    return rtn;
          /*
{
  "type": "withdraw",
  "uuid": "9f432943-54e0-40b7-825f-b6fec8b42b79",
  "currency": "BTC",
  "txid": "ebe6937b-130e-4066-8ac6-4b0e67f28adc",
  "state": "processing",
  "created_at": "2018-04-13T11:24:01+09:00",
  "done_at": null,
  "amount": "0.01",
  "fee": "0.0",
  "krw_amount": "80420.0",
  "transaction_type": "default"
}

Request Parameters
Name	설명	타입
currency *	Currency 코드	String
amount *	출금 수량	Number
address *	출금 가능 주소에 등록된 출금 주소	String
secondary_address	2차 출금 주소 (필요한 코인에 한해서)	String
transaction_type	출금 유형
default : 일반출금
internal : 바로출금	String


Response
필드	설명	타입
type	입출금 종류	String
uuid	출금의 고유 아이디	String
currency	화폐를 의미하는 영문 대문자 코드	String
txid	출금의 트랜잭션 아이디	String
state	출금 상태	String
created_at	출금 생성 시간	DateString
done_at	출금 완료 시간	DateString
amount	출금 금액/수량	NumberString
fee	출금 수수료	NumberString
krw_amount	원화 환산 가격	NumberString
transaction_type	출금 유형	String
*/
    }
}
