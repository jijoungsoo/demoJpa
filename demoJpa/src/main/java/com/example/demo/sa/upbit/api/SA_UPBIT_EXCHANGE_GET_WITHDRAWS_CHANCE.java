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
public class SA_UPBIT_EXCHANGE_GET_WITHDRAWS_CHANCE {
    @Autowired
	PjtUtil pjtU;

  
  @Autowired
  HttpUtil httpU;
//출금가능정보
  public HashMap<String,Object> run(String CURRENCY) throws BizException, ClientProtocolException, NoSuchAlgorithmException, URISyntaxException, IOException  {
    HashMap<String, String> params = new HashMap<>();
    params.put("currency",CURRENCY);
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
  "member_level": {
    "security_level": 3,
    "fee_level": 0,
    "email_verified": true,
    "identity_auth_verified": true,
    "bank_account_verified": true,
    "kakao_pay_auth_verified": false,
    "locked": false,
    "wallet_locked": false
  },
  "currency": {
    "code": "BTC",
    "withdraw_fee": "0.0005",
    "is_coin": true,
    "wallet_state": "working",
    "wallet_support": [
      "deposit",
      "withdraw"
    ]
  },
  "account": {
    "currency": "BTC",
    "balance": "10.0",
    "locked": "0.0",
    "avg_buy_price": "8042000",
    "avg_buy_price_modified": false,
    "unit_currency": "KRW",
  },
  "withdraw_limit": {
    "currency": "BTC",
    "minimum": null,
    "onetime": null,
    "daily": "10.0",
    "remaining_daily": "10.0",
    "remaining_daily_krw": "0.0",
    "fixed": null,
    "can_withdraw": true
  }
}





Response
필드	설명	타입
member_level	사용자의 보안등급 정보	Object
member_level.security_level	사용자의 보안등급	Integer
member_level.fee_level	사용자의 수수료등급	Integer
member_level.email_verified	사용자의 이메일 인증 여부	Boolean
member_level.identity_auth_verified	사용자의 실명 인증 여부	Boolean
member_level.bank_account_verified	사용자의 계좌 인증 여부	Boolean
member_level.kakao_pay_auth_verified	사용자의 카카오페이 인증 여부	Boolean
member_level.locked	사용자의 계정 보호 상태	Boolean
member_level.wallet_locked	사용자의 출금 보호 상태	Boolean
currency	화폐 정보	Object
currency.code	화폐를 의미하는 영문 대문자 코드	String
currency.withdraw_fee	해당 화폐의 출금 수수료	NumberString
currency.is_coin	화폐의 코인 여부	Boolean
currency.wallet_state	해당 화폐의 지갑 상태	String
currency.wallet_support	해당 화폐가 지원하는 입출금 정보	Array[String]
account	사용자의 계좌 정보	Object
account.currency	화폐를 의미하는 영문 대문자 코드	String
account.balance	주문가능 금액/수량	NumberString
account.locked	주문 중 묶여있는 금액/수량	NumberString
account.avg_buy_price	매수평균가	NumberString
account.avg_buy_price_modified	매수평균가 수정 여부	Boolean
account.unit_currency	평단가 기준 화폐	String
withdraw_limit	출금 제약 정보	Object
withdraw_limit.currency	화폐를 의미하는 영문 대문자 코드	String
withdraw_limit.minimum	출금 최소 금액/수량	NumberString
withdraw_limit.onetime	1회 출금 한도	NumberString
withdraw_limit.daily	1일 출금 한도	NumberString
withdraw_limit.remaining_daily	1일 잔여 출금 한도	NumberString
withdraw_limit.remaining_daily_krw	통합 1일 잔여 출금 한도	NumberString
withdraw_limit.fixed	출금 금액/수량 소수점 자리 수	Integer
withdraw_limit.can_withdraw	출금 지원 여부	Boolean
*/
    }
}
