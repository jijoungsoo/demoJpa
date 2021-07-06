package com.example.demo.sa.upbit.api;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.demo.exception.BizException;
import com.example.demo.utils.HttpUtil;
import com.example.demo.utils.PjtUtil;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SA_UPBIT_QUOTATION_ORDER_BOOK {
    @Autowired
	PjtUtil pjtU;

  
  @Autowired
  HttpUtil httpU;

  public ArrayList<HashMap<String,Object>> run(String markets) throws BizException, ClientProtocolException, URISyntaxException, IOException  {
    /*
    markets*
    array of strings
    마켓 코드 목록 (ex. KRW-BTC,BTC-ETH)
    */    
    List nameValuePairs = new ArrayList();
    nameValuePairs.add(new BasicNameValuePair("markets", markets));
    String jsonOutString = httpU.httpGetUpbit("https://api.upbit.com/v1/orderbook", nameValuePairs);
    ArrayList<HashMap<String,Object>> rtn = new ArrayList<HashMap<String,Object>>();
    System.out.println("jsonOutString ="+jsonOutString);
    rtn=pjtU.JsonStringToObject(jsonOutString, ArrayList.class);
    return rtn;
  /*
[
{
"market": "KRW-BTC",
"timestamp": 1529910247984,
"total_ask_size": 8.83621228,
"total_bid_size": 2.43976741,
"orderbook_units": [
{
  "ask_price": 6956000,
  "bid_price": 6954000,
  "ask_size": 0.24078656,
  "bid_size": 0.00718341
},
{
  "ask_price": 6958000,
  "bid_price": 6953000,
  "ask_size": 1.12919,
  "bid_size": 0.11500074
},
{
  "ask_price": 6960000,
  "bid_price": 6952000,
  "ask_size": 0.08614137,
  "bid_size": 0.19019028
},
{
  "ask_price": 6962000,
  "bid_price": 6950000,
  "ask_size": 0.0837203,
  "bid_size": 0.28201649
},
{
  "ask_price": 6964000,
  "bid_price": 6949000,
  "ask_size": 0.501885,
  "bid_size": 0.01822085
},
{
  "ask_price": 6965000,
  "bid_price": 6946000,
  "ask_size": 1.12517189,
  "bid_size": 0.0002
},
{
  "ask_price": 6968000,
  "bid_price": 6945000,
  "ask_size": 2.89900477,
  "bid_size": 0.03597913
},
{
  "ask_price": 6970000,
  "bid_price": 6944000,
  "ask_size": 0.2044231,
  "bid_size": 0.39291445
},
{
  "ask_price": 6972000,
  "bid_price": 6939000,
  "ask_size": 2.55280097,
  "bid_size": 0.12963816
},
{
  "ask_price": 6974000,
  "bid_price": 6937000,
  "ask_size": 0.01308832,
  "bid_size": 1.2684239
}
]
}
]

market	마켓 코드	String
timestamp	호가 생성 시각	Long
total_ask_size	호가 매도 총 잔량	Double
total_bid_size	호가 매수 총 잔량	Double
orderbook_units	호가	List of Objects
ask_price	매도호가	Double
bid_price	매수호가	Double
ask_size	매도 잔량	Double
bid_size	매수 잔량	Double
orderbook_unit 리스트에는 15호가 정보가 들어가며 차례대로 1호가, 2호가 ... 15호가의 정보를 담고 있습니다.
*/
  }
}
