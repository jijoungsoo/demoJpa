package com.example.demo.br.coin.upbit;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.example.demo.anotation.OpService;
import com.example.demo.db.da.upbit.DA_UPBIT_MARKET;
import com.example.demo.db.da.upbit.DA_UPBIT_ORDER_BOOK;
import com.example.demo.db.da.upbit.DA_UPBIT_ORDER_BOOK_UNITS;
import com.example.demo.db.domain.upbit.UpbitMarket;
import com.example.demo.db.domain.upbit.UpbitOrderBook;
import com.example.demo.db.domain.upbit.UpbitOrderBookIdx;
import com.example.demo.db.domain.upbit.UpbitOrderBookUnits;
import com.example.demo.db.domain.upbit.UpbitOrderBookUnitsIdx;
import com.example.demo.exception.BizException;
import com.example.demo.sa.upbit.api.SA_UPBIT_QUOTATION_ORDER_BOOK;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "UPBIT", description = "UPBIT 업비트")
@Slf4j
@OpService
@Service
public class BR_UPBIT_ORDER_BOOK_SYNC {
	@Autowired
    SA_UPBIT_QUOTATION_ORDER_BOOK saUpbitOrderBook;

	
	@Autowired
	DA_UPBIT_MARKET daUpbitMarket;

	@Autowired
	DA_UPBIT_ORDER_BOOK daUpbitOrderBook;

  	@Autowired
  	DA_UPBIT_ORDER_BOOK_UNITS  daUpbitOrderBookUnits;
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_UPBIT_ORDER_BOOK_SYNC")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;
	}

	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_UPBIT_ORDER_BOOK_SYNC")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_UPBIT_ORDER_BOOK_SYNC", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}

	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"UPBIT"}, value = "UPBIT 일별시세를 싱크한다.", notes = "")
	//@PostMapping(path= "/api/BR_MIG_AV_ACTR_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {

		List<UpbitMarket>  al = daUpbitMarket.find(null, null, null,"N"
		/*삭제유무 */
		);
		String MARKETS ="";
		for(int i=0;i<al.size();i++){
			MARKETS=MARKETS+al.get(i).getMarket();
			if((i+1)!=al.size()){
				MARKETS=MARKETS+",";
			}
		}
		this.run(MARKETS);
		OUT_DS outDs = new OUT_DS();
		return outDs;
	}

	
	public void run(String markets) throws BizException  {
		/*
	markets*
	array of strings
	마켓 코드 목록 (ex. KRW-BTC,BTC-ETH)
		*/
		try {
		  ArrayList<HashMap<String,Object>> al = saUpbitOrderBook.run(markets);
		  for(int i=0;i<al.size();i++){
			updateOrderBook(al.get(i));
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
	
	  
	  private void updateOrderBook(HashMap<String,Object> m) {
		String MARKET  =   m.get("market").toString();
		Long TIMESTAMP = Long.parseLong(m.get("timestamp").toString());
		
		Double TOTAL_ASK_SIZE  =  Double.parseDouble(m.get("total_ask_size").toString());
		Double TOTAL_BID_SIZE  =   Double.parseDouble(m.get("total_bid_size").toString());
	
		UpbitOrderBookIdx  t= new UpbitOrderBookIdx();
			t.setMarket(MARKET);
		t.setTimestamp(TIMESTAMP);
		Optional<UpbitOrderBook> c =daUpbitOrderBook.findById(t);
			if(c.isPresent()){
				daUpbitOrderBook.updt(  MARKET
		  , TIMESTAMP	
		  , TOTAL_ASK_SIZE
		  , TOTAL_BID_SIZE      
		  );
			} else {
		  daUpbitOrderBook.crt( MARKET
		  , TIMESTAMP	
		  , TOTAL_ASK_SIZE
		  , TOTAL_BID_SIZE      
		  );
		}
	
		ArrayList<HashMap<String,Object>> tmp2 = (ArrayList<HashMap<String,Object>>)m.get("orderbook_units");
		for(int i=0;i<tmp2.size();i++){
		  HashMap<String,Object> tmp3 = tmp2.get(i);
		  Integer SEQ = i;
		  Double ASK_PRICE =Double.parseDouble(tmp3.get("ask_price").toString());
		  Double BID_PRICE =Double.parseDouble(tmp3.get("bid_price").toString());
		  Double ASK_SIZE =Double.parseDouble(tmp3.get("ask_size").toString());
		  Double BID_SIZE =Double.parseDouble(tmp3.get("bid_size").toString());
	
		  UpbitOrderBookUnitsIdx  d1= new UpbitOrderBookUnitsIdx();
		  d1.setMarket(MARKET);
		  d1.setTimestamp(TIMESTAMP);
		  d1.setSeq(SEQ);
		  Optional<UpbitOrderBookUnits> d2 =daUpbitOrderBookUnits.findById(d1);
		  if(d2.isPresent()){
			daUpbitOrderBookUnits.updt(  MARKET
			, TIMESTAMP	
			, ASK_PRICE
			, BID_PRICE
			, ASK_SIZE
			, BID_SIZE
			, SEQ      
			);
			
		  } else {
			daUpbitOrderBookUnits.crt( MARKET
			, TIMESTAMP	
			, ASK_PRICE
			, BID_PRICE
			, ASK_SIZE
			, BID_SIZE
			, SEQ
			);
		  }
	  
		}
	
	  }
}
