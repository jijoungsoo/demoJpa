package com.example.demo.br.coin.upbit;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.demo.anotation.OpService;
import com.example.demo.exception.BizException;
import com.example.demo.sa.upbit.api.SA_UPBIT_QUOTATION_ORDER_BOOK;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

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
public class BR_UPBIT_QUOTATION_ORDER_BOOK {

	@Autowired
    PjtUtil pjtU;
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_UPBIT_QUOTATION_ORDER_BOOK")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_UPBIT_QUOTATION_ORDER_BOOK", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
	}

	@ApiModel(value="IN_DATA_ROW-BR_UPBIT_QUOTATION_ORDER_BOOK")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("MARKET")
		@Schema(name = "MARKET", example = "1", description = "MARKET")
		String MARKET = "";
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_UPBIT_QUOTATION_ORDER_BOOK")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_UPBIT_QUOTATION_ORDER_BOOK", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();

		@JsonProperty("OUT_DATA_ORDER_BOOK")
		@Schema(name="OUT_DATA-BR_UPBIT_QUOTATION_ORDER_BOOK", description = "출력 데이터")
		ArrayList<OUT_DATA_ORDER_BOOK_ROW> OUT_DATA_ORDER_BOOK = new ArrayList<OUT_DATA_ORDER_BOOK_ROW>();
	}

	@ApiModel(value="OUT_DATA_ROW-BR_UPBIT_QUOTATION_ORDER_BOOK")
	@Data
	static class OUT_DATA_ROW {

		@JsonProperty("MARKET")
		@Schema(name = "MARKET", example = "KRW-BTC", description = "MARKET")
		String MARKET = null;

		@JsonProperty("TIMESTAMP")
		@Schema(name = "TIMESTAMP", example = "1524046650532", description = "호가 생성 시각")
		Long TIMESTAMP = null;

		@JsonProperty("TOTAL_ASK_SIZE")
		@Schema(name = "TOTAL_ASK_SIZE", example = "8450000", description = "호가 매도 총 잔량")
		Double TOTAL_ASK_SIZE = null;

		@JsonProperty("TOTAL_BID_SIZE")
		@Schema(name = "TOTAL_BID_SIZE", example = "8450000", description = "호가 매수 총 잔량")
		Double TOTAL_BID_SIZE = null;
	}

	@ApiModel(value="OUT_DATA_ORDER_BOOK_ROW-BR_UPBIT_QUOTATION_ORDER_BOOK")
	@Data
	static class OUT_DATA_ORDER_BOOK_ROW {
		@JsonProperty("MARKET")
		@Schema(name = "MARKET", example = "KRW-BTC", description = "MARKET")
		String MARKET = null;

		@JsonProperty("ASK_PRICE")
		@Schema(name = "ASK_PRICE", example = "8450000", description = "매도호가")
		Double ASK_PRICE = null;

		@JsonProperty("BID_PRICE")
		@Schema(name = "BID_PRICE", example = "8450000", description = "매수호가")
		Double BID_PRICE = null;

		@JsonProperty("ASK_SIZE")
		@Schema(name = "ASK_SIZE", example = "8450000", description = "매도 잔량")
		Double ASK_SIZE = null;

		@JsonProperty("BID_SIZE")
		@Schema(name = "BID_SIZE", example = "8450000", description = "매수 잔량")
		Double BID_SIZE = null;
	}
	@Autowired
    SA_UPBIT_QUOTATION_ORDER_BOOK saUpbitOrderBook;

	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"UPBIT"}, value = "UPBIT 종목을 조회한다.", notes = "페이징 처리 X")
	//@PostMapping(path= "/api/BR_MIG_AV_ACTR_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		String MARKET =   inDS.IN_DATA.get(0).MARKET;
		OUT_DS outDs = new OUT_DS();
		ArrayList<HashMap<String, Object>> al;
		try {
			al = saUpbitOrderBook.run(MARKET);
			
		
			for (int i = 0; i < al.size(); i++) {
				HashMap<String,Object> c = al.get(i);
						
				OUT_DATA_ROW row = new OUT_DATA_ROW();

				row.MARKET = c.get("market").toString();
				row.TIMESTAMP = Long.parseLong(c.get("timestamp").toString());
				row.TOTAL_ASK_SIZE = Double.parseDouble(c.get("total_ask_size").toString());
				row.TOTAL_BID_SIZE = Double.parseDouble(c.get("total_bid_size").toString());
				outDs.OUT_DATA.add(row);

				Object orderbook_units = c.get("orderbook_units");
				if(orderbook_units!=null){
					ArrayList<HashMap<String, Object>> al_tmp = (ArrayList<HashMap<String, Object>>)orderbook_units;
					for(int j=0;j<al_tmp.size();j++){
						HashMap<String, Object> d = al_tmp.get(j);
						OUT_DATA_ORDER_BOOK_ROW  row_tmp = new OUT_DATA_ORDER_BOOK_ROW();
						row_tmp.MARKET = c.get("market").toString();
						row_tmp.ASK_PRICE = Double.parseDouble(d.get("ask_price").toString());
						row_tmp.BID_PRICE = Double.parseDouble(d.get("bid_price").toString());
						row_tmp.ASK_SIZE = Double.parseDouble(d.get("ask_size").toString());
						row_tmp.BID_SIZE = Double.parseDouble(d.get("bid_size").toString());
						outDs.OUT_DATA_ORDER_BOOK.add(row_tmp);
					}
				}
			}
		} catch (URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return outDs;
	}
}
