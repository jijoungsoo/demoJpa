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
public class SA_UPBIT_EXCHANGE_GET_WITHDRAWS {
    @Autowired
	PjtUtil pjtU;

  
  @Autowired
  HttpUtil httpU;
//출금 리스트 조회
  public ArrayList<HashMap<String,Object>> run(String CURRENCY,String STATE, ArrayList<String> UUIDS, 
  ArrayList<String>  TXIDS, String LIMIT, String PAGE, String ORDER_BY  ) throws BizException, ClientProtocolException, NoSuchAlgorithmException, URISyntaxException, IOException  {
    HashMap<String, String> params = new HashMap<>();
    params.put("currency",CURRENCY);
    params.put("state",STATE);
    params.put("limit",LIMIT);
    params.put("page",PAGE);
    params.put("order_by",ORDER_BY);
    ArrayList<String> queryElements = new ArrayList<>();
    for(Map.Entry<String, String> entity : params.entrySet()) {
        queryElements.add(entity.getKey() + "=" + entity.getValue());
    }
    for(String uuid : UUIDS) {
      queryElements.add("uuids[]=" + uuid);
    }

    for(String txid : TXIDS) {
      queryElements.add("txids[]=" +txid );
    }

    String queryString = String.join("&", queryElements.toArray(new String[0]));

    String jsonOutString = httpU.httpGetUpbitExchangeApi("https://api.upbit.com/v1/withdraws", queryString);
    ArrayList<HashMap<String,Object>> rtn = new ArrayList<HashMap<String,Object>>();
    System.out.println("jsonOutString ="+jsonOutString);
    rtn=pjtU.JsonStringToObject(jsonOutString, ArrayList.class);
    return rtn;
          /*
[
  {
    "type": "withdraw",
    "uuid": "35a4f1dc-1db5-4d6b-89b5-7ec137875956",
    "currency": "XRP",
    "txid": "98c15999f0bdc4ae0e8a-ed35868bb0c204fe6ec29e4058a3451e-88636d1040f4baddf943274ce37cf9cc",
    "state": "DONE",
    "created_at": "2019-02-28T15:17:51+09:00",
    "done_at": "2019-02-28T15:22:12+09:00",
    "amount": "1.00",
    "fee": "0.0",
    "transaction_type": "default"
  }
  # ....
]
QUERY PARAMS

currency
string
Currency 코드

state
string
출금 상태

uuids
array of strings
출금 UUID의 목록

Request Parameters
Name	설명	타입
currency	Currency 코드	String
state	출금 상태
- submitting : 처리 중
- submitted : 처리 완료
- almost_accepted : 출금대기중
- rejected : 거부
- accepted : 승인됨
- processing : 처리 중
- done : 완료
- canceled : 취소됨	String
uuids	출금 UUID의 목록	Array
txids	출금 TXID의 목록	Array
limit	개수 제한 (default: 100, max: 100)	Number
page	페이지 수, default: 1	Number
order_by	정렬
- asc : 오름차순
- desc : 내림차순 (default)	String
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
