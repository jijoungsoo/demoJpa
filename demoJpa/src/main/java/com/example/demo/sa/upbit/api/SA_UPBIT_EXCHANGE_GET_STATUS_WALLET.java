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
public class SA_UPBIT_EXCHANGE_GET_STATUS_WALLET {
    @Autowired
	PjtUtil pjtU;

  
  @Autowired
  HttpUtil httpU;
//출금 리스트 조회
  public ArrayList<HashMap<String,Object>> run() throws BizException, ClientProtocolException, NoSuchAlgorithmException, URISyntaxException, IOException  {
    HashMap<String, String> params = new HashMap<>();
    ArrayList<String> queryElements = new ArrayList<>();
    for(Map.Entry<String, String> entity : params.entrySet()) {
        queryElements.add(entity.getKey() + "=" + entity.getValue());
    }

    String jsonOutString = httpU.httpGetUpbitExchangeApi("https://api.upbit.com/v1/status/wallet");
    ArrayList<HashMap<String,Object>> rtn = new ArrayList<HashMap<String,Object>>();
    System.out.println("jsonOutString ="+jsonOutString);
    rtn=pjtU.JsonStringToObject(jsonOutString, ArrayList.class);
    return rtn;
          /*
[
  {
    "currency": "BTC",
    "wallet_state": "working",
    "block_state": "normal",
    "block_height": 637432,
    "block_updated_at": "2020-07-03T02:04:45.523+00:00"
  }
]


🚧
입출금 현황 데이터는 실제 서비스 상태와 다를 수 있습니다.

입출금 현황 API에서 제공하는 입출금 상태, 블록 상태 정보는 수 분 정도 지연되어 반영될 수 있습니다. 본 API는 참고용으로만 사용하시길 바라며 실제 입출금을 수행하기 전에는 반드시 업비트 공지사항 및 입출금 현황 페이지를 참고해주시기 바랍니다.

Response
필드	설명	타입
currency	화폐를 의미하는 영문 대문자 코드	String
wallet_state	입출금 상태
- working : 입출금 가능
- withdraw_only : 출금만 가능
- deposit_only : 입금만 가능
- paused : 입출금 중단
- unsupported : 입출금 미지원	String
block_state	블록 상태
- normal : 정상
- delayed : 지연
- inactive : 비활성 (점검 등)	String
block_height	블록 높이	Integer
block_updated_at	블록 갱신 시각	DateString
*/
    }
}
