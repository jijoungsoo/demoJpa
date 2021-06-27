package com.example.demo.sa.upbit.api;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.demo.exception.BizException;
import com.example.demo.utils.HttpUtil;
import com.example.demo.utils.PjtUtil;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SA_UPBIT_EXCHANGE_GET_ACCOUNTS {
    @Autowired
	PjtUtil pjtU;

  
  @Autowired
  HttpUtil httpU;
//전체계좌조회
  public ArrayList<HashMap<String, Object>> run() throws BizException, ClientProtocolException, URISyntaxException, IOException, NoSuchAlgorithmException  {
      String jsonOutString = httpU.httpGetUpbitExchangeApi("https://api.upbit.com/v1/accounts");
      ArrayList<HashMap<String,Object>> rtn = new ArrayList<HashMap<String,Object>>();
      System.out.println("jsonOutString ="+jsonOutString);
      rtn=pjtU.JsonStringToObject(jsonOutString, ArrayList.class);
      return rtn;
          /*
[
  {
    "currency":"KRW",
    "balance":"1000000.0",
    "locked":"0.0",
    "avg_buy_price":"0",
    "avg_buy_price_modified":false,
    "unit_currency": "KRW",
  },
  {
    "currency":"BTC",
    "balance":"2.0",
    "locked":"0.0",
    "avg_buy_price":"101000",
    "avg_buy_price_modified":false,
    "unit_currency": "KRW",
  }
]

currency	화폐를 의미하는 영문 대문자 코드	String
balance	주문가능 금액/수량	NumberString
locked	주문 중 묶여있는 금액/수량	NumberString
avg_buy_price	매수평균가	NumberString
avg_buy_price_modified	매수평균가 수정 여부	Boolean
unit_currency	평단가 기준 화폐	String
*/
    }
}
