package com.example.demo.sa.upbit.api;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.example.demo.db.da.upbit.DA_UPBIT_MARKET;
import com.example.demo.db.domain.upbit.UpbitMarket;
import com.example.demo.exception.BizException;
import com.example.demo.utils.HttpUtil;
import com.example.demo.utils.PjtUtil;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SA_UPBIT_MARKET_ALL {
    @Autowired
	PjtUtil pjtU;

  @Autowired
  HttpUtil httpU;

  @Autowired
	DA_UPBIT_MARKET daUpbitMarket;
  

  public void run() throws BizException  {
    try {
      ArrayList<HashMap<String,String>> al = this.getMarketAll();
      for(int i=0;i<al.size();i++){
        updateMarket(al.get(i));
      }
    } catch (ClientProtocolException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (URISyntaxException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } 
  }

  
  private void updateMarket(HashMap<String,String> m) {
              String market  =   m.get("market");
              String kNm  =   m.get("korean_name");
              String eNm      =   m.get("english_name");
              String market_warning      =   m.get("market_warning");
              


              Optional<UpbitMarket> c =daUpbitMarket.findById(market);
              if(c.isPresent()){
                daUpbitMarket.updt(market,kNm,eNm,market_warning);
              } else {
                daUpbitMarket.crt(market,kNm,eNm,market_warning);
              }
  }
   
    public ArrayList<HashMap<String,String>> getMarketAll() throws URISyntaxException, ClientProtocolException, IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        List nameValuePairs = new ArrayList();
        nameValuePairs.add(new BasicNameValuePair("isDetails", "true"));    
        /*
isDetails
boolean
유의종목 필드과 같은 상세 정보 노출 여부(선택 파라미터)
        */
        String jsonOutString=httpU.httpGetUpbit("https://api.upbit.com/v1/market/all", nameValuePairs);
        ArrayList<HashMap<String,String>> rtn = new ArrayList<HashMap<String,String>>();
        rtn=pjtU.JsonStringToObject(jsonOutString, ArrayList.class);
      return rtn;
          /*
          [
            {
                    "market": "KRW-BTC",
                    "korean_name": "비트코인",
                    "english_name": "Bitcoin"
                },
            ]
          */
    }
}
