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
public class SA_UPBIT_EXCHANGE_GET_DEPOSITS_COIN_ADDRESS {
    @Autowired
	PjtUtil pjtU;

  
  @Autowired
  HttpUtil httpU;
//출금 리스트 조회
  public HashMap<String,Object> run(String CURRENCY) throws BizException, ClientProtocolException, NoSuchAlgorithmException, URISyntaxException, IOException  {
    HashMap<String, String> params = new HashMap<>();
    params.put("currency", CURRENCY);
    ArrayList<String> queryElements = new ArrayList<>();
    for(Map.Entry<String, String> entity : params.entrySet()) {
        queryElements.add(entity.getKey() + "=" + entity.getValue());
    }

    String queryString = String.join("&", queryElements.toArray(new String[0]));

    String jsonOutString = httpU.httpGetUpbitExchangeApi("https://api.upbit.com/v1/deposits/coin_address", queryString);
    HashMap<String,Object> rtn = new HashMap<String,Object>();
    System.out.println("jsonOutString ="+jsonOutString);
    rtn=pjtU.JsonStringToObject(jsonOutString, HashMap.class);
    return rtn;
          /*
{
  "currency": "BTC",
  "deposit_address": "3EusRwybuZUhVDeHL7gh3HSLmbhLcy7NqD",
  "secondary_address": null
}


QUERY PARAMS

currency*
string
Currency symbol

HEADERS

Authorization*
string
Authorization token (JWT)


200
RESPONSE
200

Response schema type: object

String	currency
String	deposit_address
secondary_address
Response
필드	설명	타입
currency	화폐를 의미하는 영문 대문자 코드	String
deposit_address	입금 주소	String
secondary_address	2차 입금 주소	String
📘
입금 주소 조회 요청 API 유의사항

입금 주소 생성 요청 이후 아직 발급되지 않은 상태일 경우 deposit_address가 null일 수 있습니다.
*/
    }
}
