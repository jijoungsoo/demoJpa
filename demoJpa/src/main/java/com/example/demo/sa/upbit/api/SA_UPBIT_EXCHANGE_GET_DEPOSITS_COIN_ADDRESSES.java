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
public class SA_UPBIT_EXCHANGE_GET_DEPOSITS_COIN_ADDRESSES {
    @Autowired
	PjtUtil pjtU;

  
  @Autowired
  HttpUtil httpU;
//출금 리스트 조회
  public ArrayList<HashMap<String,Object>> run() throws BizException, ClientProtocolException, NoSuchAlgorithmException, URISyntaxException, IOException  {
    String jsonOutString = httpU.httpGetUpbitExchangeApi("https://api.upbit.com/v1/deposits/coin_addresses");
    ArrayList<HashMap<String,Object>> rtn = new ArrayList<HashMap<String,Object>>();
    System.out.println("jsonOutString ="+jsonOutString);
    rtn=pjtU.JsonStringToObject(jsonOutString, ArrayList.class);
    return rtn;
          /*
[
  {
    "currency": "BTC",
    "deposit_address": "3EusRwybuZUhVDeHL7gh3HSLmbhLcy7NqD",
    "secondary_address": null
  },
  {
    "currency": "ETH",
    "deposit_address": "0x0d73e0a482b8cf568976d2e8688f4a899d29301c",
    "secondary_address": null
  },
  {
    "currency": "XRP",
    "deposit_address": "rN9qNpgnBaZwqCg8CvUZRPqCcPPY7wfWep",
    "secondary_address": "3057887915"
  }
]

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
