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
public class SA_UPBIT_EXCHANGE_GET_DEPOSIT  {
    @Autowired
	PjtUtil pjtU;

  
  @Autowired
  HttpUtil httpU;
//코인출금하기
  public HashMap<String,Object> run(String  UUID
  , String TXID,String CURRENCY  ) throws BizException, ClientProtocolException, NoSuchAlgorithmException, URISyntaxException, IOException  {
    HashMap<String, String> params = new HashMap<>();
    params.put("uuid",UUID);
    params.put("txid",TXID);
    params.put("currency",CURRENCY);
    ArrayList<String> queryElements = new ArrayList<>();
    for(Map.Entry<String, String> entity : params.entrySet()) {
        queryElements.add(entity.getKey() + "=" + entity.getValue());
    }
    
    String queryString = String.join("&", queryElements.toArray(new String[0]));

    String jsonOutString = httpU.httpGetUpbitExchangeApi("https://api.upbit.com/v1/deposit", queryString);
    HashMap<String,Object> rtn = new HashMap<String,Object>();
    System.out.println("jsonOutString ="+jsonOutString);
    rtn=pjtU.JsonStringToObject(jsonOutString, HashMap.class);
    return rtn;
          /*
{ 
  "type": "deposit",
  "uuid": "94332e99-3a87-4a35-ad98-28b0c969f830",
  "currency": "KRW",
  "txid": "9e37c537-6849-4c8b-a134-57313f5dfc5a",
  "state": "ACCEPTED",
  "created_at": "2017-12-08T15:38:02+09:00",
  "done_at": "2017-12-08T15:38:02+09:00",
  "amount": "100000.0",
  "fee": "0.0",
  "transaction_type": "default"
}

Request Parameters
Name	설명	타입
uuid	입금 UUID	String
txid	입금 TXID	String
currency	Currency 코드	String
Response
필드	설명	타입
type	입출금 종류	String
uuid	입금에 대한 고유 아이디	String
currency	화폐를 의미하는 영문 대문자 코드	String
txid	입금의 트랜잭션 아이디	String
state	입금 상태	String
created_at	입금 생성 시간	DateString
done_at	입금 완료 시간	DateString
amount	입금 수량	NumberString
fee	입금 수수료	NumberString
transaction_type	입금 유형
default : 일반입금
internal : 바로입금	String
*/
    }
}
