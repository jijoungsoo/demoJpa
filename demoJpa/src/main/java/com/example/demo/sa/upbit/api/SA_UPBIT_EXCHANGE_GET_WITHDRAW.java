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
public class SA_UPBIT_EXCHANGE_GET_WITHDRAW {
    @Autowired
	PjtUtil pjtU;

  
  @Autowired
  HttpUtil httpU;
//개별출금조회
  public HashMap<String,Object> run(String uuid, String txid, String currency) throws BizException, ClientProtocolException, NoSuchAlgorithmException, URISyntaxException, IOException  {
    HashMap<String, String> params = new HashMap<>();
    params.put("uuid",uuid);
    params.put("txid",txid);
    params.put("currency",currency);
    ArrayList<String> queryElements = new ArrayList<>();
    for(Map.Entry<String, String> entity : params.entrySet()) {
        queryElements.add(entity.getKey() + "=" + entity.getValue());
    }
    String queryString = String.join("&", queryElements.toArray(new String[0]));

    String jsonOutString = httpU.httpGetUpbitExchangeApi("https://api.upbit.com/v1/withdraw", queryString);
    HashMap<String,Object> rtn = new HashMap<String,Object>();
    System.out.println("jsonOutString ="+jsonOutString);
    rtn=pjtU.JsonStringToObject(jsonOutString, HashMap.class);
    return rtn;
          /*
{
  "type": "withdraw",
  "uuid": "9f432943-54e0-40b7-825f-b6fec8b42b79",
  "currency": "BTC",
  "txid": null,
  "state": "processing",
  "created_at": "2018-04-13T11:24:01+09:00",
  "done_at": null,
  "amount": "0.01",
  "fee": "0.0",
  "transaction_type": "default"
}
QUERY PARAMS

uuid
string
출금 UUID

txid
string
출금 TXID

currency
string
Currency 코드




Request Parameters
Name	설명	타입
uuid	출금 UUID	String
txid	출금 TXID	String
currency	Currency 코드	String



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
transaction_type	출금 유형
default : 일반출금
internal : 바로출금	String
*/
    }
}
