package com.example.demo.sa.binance.api;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
public class SA_BINANCE_GET_SAPI_V1_CAPITAL_CONFIG_GETALL {
    @Autowired
	PjtUtil pjtU;

  
  @Autowired
  HttpUtil httpU;
//출금 리스트 조회
  public ArrayList<HashMap<String,Object>> run() throws BizException, ClientProtocolException, NoSuchAlgorithmException, URISyntaxException, IOException  {
    HashMap<String, String> params = new HashMap<>();
    Date date = new Date();
    
    String timestamp=String.valueOf(date.getTime());  /*1970년대 이후를 밀리센컨드로 나타냄 */
    //String timestamp=httpU.httpGetBinianceGetServerTime();
    params.put("timestamp",timestamp);
    ArrayList<String> queryElements = new ArrayList<>();
    for(Map.Entry<String, String> entity : params.entrySet()) {
        queryElements.add(entity.getKey() + "=" + entity.getValue());
    }
    String queryString = String.join("&", queryElements.toArray(new String[0]));
    String jsonOutString = httpU.httpGetBiniance("https://api.binance.com/sapi/v1/capital/config/getall",queryString);

    ArrayList<HashMap<String,Object>> rtn = new ArrayList<HashMap<String,Object>>();
    System.out.println("jsonOutString ="+jsonOutString);
    rtn=pjtU.JsonStringToObject(jsonOutString, ArrayList.class);
    return rtn;
          /*
[
  {
    "access_key": "xxxxxxxxxxxxxxxxxxxxxxxx",
    "expire_at": "2021-03-09T12:39:39+00:00"
  }
]
*/
    }
}
