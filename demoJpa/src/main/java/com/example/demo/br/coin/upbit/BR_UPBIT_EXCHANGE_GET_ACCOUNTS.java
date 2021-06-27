package com.example.demo.br.coin.upbit;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.demo.anotation.OpService;
import com.example.demo.exception.BizException;
import com.example.demo.sa.upbit.api.SA_UPBIT_EXCHANGE_GET_ACCOUNTS;
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
public class BR_UPBIT_EXCHANGE_GET_ACCOUNTS {

	@Autowired
    PjtUtil pjtU;
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_UPBIT_EXCHANGE_GET_ACCOUNTS")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_UPBIT_EXCHANGE_GET_ACCOUNTS", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
	}

	@ApiModel(value="IN_DATA_ROW-BR_UPBIT_EXCHANGE_GET_ACCOUNTS")
	@Data
	static class IN_DATA_ROW {
	
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_UPBIT_ORDER_BOOK_UNITS_FIND")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_UPBIT_ORDER_BOOK_UNITS_FIND", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}

	@ApiModel(value="OUT_DATA_ROW-BR_UPBIT_ORDER_BOOK_UNITS_FIND")
	@Data
	static class OUT_DATA_ROW {

		@JsonProperty("CURRENCY")
		@Schema(name = "CURRENCY", example = "KRW", description = "화폐를 의미하는 영문 대문자 코드")
		String CURRENCY = null;

		@JsonProperty("BALANCE")
		@Schema(name = "BALANCE", example = "1", description = "주문가능 금액/수량")
		String BALANCE = null;

		@JsonProperty("LOCKED")
		@Schema(name = "LOCKED", example = "1", description = "주문 중 묶여있는 금액/수량")
		String LOCKED = null;		

		@JsonProperty("AVG_BUY_PRICE")
		@Schema(name = "AVG_BUY_PRICE", example = "1", description = "매수평균가")
		String AVG_BUY_PRICE = null;

		@JsonProperty("AVG_BUY_PRICE_MODIFIED")
		@Schema(name = "AVG_BUY_PRICE_MODIFIED", example = "false", description = "매수평균가 수정 여부")
		Boolean AVG_BUY_PRICE_MODIFIED = null;

		@JsonProperty("UNIT_CURRENCY")
		@Schema(name = "UNIT_CURRENCY", example = "KRW", description = "평단가 기준 화폐")
		String UNIT_CURRENCY = null;

		
	}
	
	@Autowired
	SA_UPBIT_EXCHANGE_GET_ACCOUNTS saUpbitExchageGetAccounts;

	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})

	Double asDouble(Object o) {
		Double val = null;
		if (o instanceof Number) {
			val = ((Number) o).doubleValue();
		}
		return val;
	}


	@ApiOperation(tags={"UPBIT"}, value = "UPBIT 전쳬계좌를 조회한다.", notes = "페이징 처리 X")
	//@PostMapping(path= "/api/BR_MIG_AV_ACTR_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		OUT_DS outDs = new OUT_DS();	
		
		try {
			ArrayList<HashMap<String, Object>> al = (ArrayList<HashMap<String,Object>>) saUpbitExchageGetAccounts.run();
			for (int i = 0; i < al.size(); i++) {
				HashMap<String,Object> c = al.get(i);
						
				OUT_DATA_ROW row = new OUT_DATA_ROW();

				row.CURRENCY = c.get("currency").toString();
				row.BALANCE  = c.get("balance").toString();
				row.LOCKED  = c.get("locked").toString();
				row.AVG_BUY_PRICE = c.get("avg_buy_price").toString();
				row.AVG_BUY_PRICE_MODIFIED = Boolean.parseBoolean(c.get("avg_buy_price_modified").toString());
				row.UNIT_CURRENCY =  c.get("unit_currency").toString();
			
				outDs.OUT_DATA.add(row);
			}
		} catch (NoSuchAlgorithmException | URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return outDs;
	}
}
