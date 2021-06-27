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
public class SA_UPBIT_EXCHANGE_POST_DEPOSITS_GENERATE_COIN_ADDRESS {
    @Autowired
	PjtUtil pjtU;

  
  @Autowired
  HttpUtil httpU;
//주문리스트 조회
  public HashMap<String,Object> run(String CURRENCY) throws BizException, ClientProtocolException, NoSuchAlgorithmException, URISyntaxException, IOException  {
    HashMap<String, String> params = new HashMap<>();
    params.put("currency", CURRENCY);

    String jsonOutString = httpU.httpPostUpbitExchangeApi("https://api.upbit.com/v1/deposits/generate_coin_address", params);
    HashMap<String,Object> rtn = new HashMap<String,Object>();
    System.out.println("jsonOutString ="+jsonOutString);
    rtn=pjtU.JsonStringToObject(jsonOutString, HashMap.class);
    return rtn;
          /*
{
  "success": true,
  "message": "BTC 입금주소를 생성중입니다."
}

Request Parameters
Name	설명	타입
currency *	Currency 코드	String
Response1
필드	설명	타입
success	요청 성공 여부	Boolean
message	요청 결과에 대한 메세지	String
Response2
필드	설명	타입
currency	화폐를 의미하는 영문 대문자 코드	String
deposit_address	입금 주소	String
secondary_address	2차 입금 주소	String
📘
입금 주소 생성 요청 API 유의사항

입금 주소의 생성은 서버에서 비동기적으로 이뤄집니다.

비동기적 생성 특성상 요청과 동시에 입금 주소가 발급되지 않을 수 있습니다.

주소 발급 요청 시 결과로 Response1이 반환되며 주소 발급 완료 이전까지 계속 Response1이 반환됩니다.

주소가 발급된 이후부터는 새로운 주소가 발급되는 것이 아닌 이전에 발급된 주소가 Response2 형태로 반환됩니다.

정상적으로 주소가 생성되지 않는다면 일정 시간 이후 해당 API를 다시 호출해주시길 부탁드립니다.
*/
    }
}
