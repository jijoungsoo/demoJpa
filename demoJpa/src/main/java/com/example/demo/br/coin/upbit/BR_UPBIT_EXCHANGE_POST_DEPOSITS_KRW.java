package com.example.demo.br.coin.upbit;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.demo.anotation.OpService;
import com.example.demo.br.coin.upbit.BR_UPBIT_EXCHANGE_POST_DEPOSITS_GENERATE_COIN_ADDRESS.OUT_DATA_ROW1;
import com.example.demo.br.coin.upbit.BR_UPBIT_EXCHANGE_POST_DEPOSITS_GENERATE_COIN_ADDRESS.OUT_DATA_ROW2;
import com.example.demo.exception.BizException;
import com.example.demo.sa.upbit.api.SA_UPBIT_EXCHANGE_POST_DEPOSITS_KRW;
import com.example.demo.utils.PjtUtil;
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
public class BR_UPBIT_EXCHANGE_POST_DEPOSITS_KRW {

	@Autowired
    PjtUtil pjtU;
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_UPBIT_EXCHANGE_POST_DEPOSITS_KRW")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_UPBIT_EXCHANGE_POST_DEPOSITS_KRW", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
	}

	@ApiModel(value="IN_DATA_ROW-BR_UPBIT_EXCHANGE_POST_DEPOSITS_KRW")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("AMOUNT")
		@Schema(name = "AMOUNT", example = "1", description = "입금 원화 수량")
		String AMOUNT = null;
	}
	
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_UPBIT_EXCHANGE_POST_DEPOSITS_KRW")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_UPBIT_EXCHANGE_POST_DEPOSITS_KRW", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}

	@ApiModel(value="OUT_DATA_ROW-BR_UPBIT_EXCHANGE_POST_DEPOSITS_KRW")
	@Data
	static class OUT_DATA_ROW {
		@JsonProperty("TYPE")  //String
		@Schema(name = "TYPE", example = "1", description = "입출금 종류")
		String TYPE = null;

		@JsonProperty("UUID")  //String
		@Schema(name = "UUID", example = "1", description = "출금의 고유 아이디")
		String UUID = null;

		@JsonProperty("CURRENCY")
		@Schema(name = "CURRENCY", example = "KRW-BTC", description = "화폐를 의미하는 영문 대문자 코드")
		String CURRENCY = null;

		@JsonProperty("TXID")
		@Schema(name = "TXID", example = "BTC/KRW", description = "출금의 트랜잭션 아이디")
		String TXID = null;

		@JsonProperty("STATE")
		@Schema(name = "STATE", example = "limit", description = "출금 상태")
		String STATE = null;

		@JsonProperty("CREATED_AT")
		@Schema(name = "CREATED_AT", example = "ask,bid", description = "출금 생성 시간")
		String CREATED_AT = null;


		//market.bid ==> 매수 시 제약사항
		@JsonProperty("DONE_AT")
		@Schema(name = "DONE_AT", example = "KRW", description = "출금 완료 시간")
		String DONE_AT = null;

		@JsonProperty("AMOUNT")
		@Schema(name = "AMOUNT", example = "null", description = "출금 금액/수량")
		String AMOUNT = null;

		@JsonProperty("FEE")
		@Schema(name = "FEE", example = "1000", description = "출금 수수료")
		String FEE = null;

		//market.ask ==> 매도 시 제약사항
		@JsonProperty("TRANSACTION_TYPE")
		@Schema(name = "TRANSACTION_TYPE", example = "8450000", description = "출금 유형")
		String TRANSACTION_TYPE = null;
	}

	@Autowired
	SA_UPBIT_EXCHANGE_POST_DEPOSITS_KRW saUpbitExchangePostDepositsKrw;

	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"UPBIT"}, value = "UPBIT 종목을 조회한다.", notes = "페이징 처리 X")
	//@PostMapping(path= "/api/BR_MIG_AV_ACTR_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		OUT_DS outDs = new OUT_DS();
		String AMOUNT = null;
		HashMap<String, Object> tmp;
		if(inDS.IN_DATA!=null) {
			if(inDS.IN_DATA.size()>0) {
				AMOUNT  =inDS.IN_DATA.get(0).AMOUNT;
				try {
					tmp = saUpbitExchangePostDepositsKrw.run(AMOUNT);
					if(tmp!=null) {
					
						OUT_DATA_ROW row = new OUT_DATA_ROW();
						row.TYPE = tmp.get("type").toString();
						row.UUID = tmp.get("uuid").toString();				
						row.CURRENCY = tmp.get("currency").toString();
						row.TXID = tmp.get("txid").toString();
						row.STATE = tmp.get("state").toString();
						row.CREATED_AT = tmp.get("created_at").toString();
						row.DONE_AT = tmp.get("done_at").toString();
						row.AMOUNT = tmp.get("amount").toString();
						row.FEE = tmp.get("fee").toString();
						
						row.TRANSACTION_TYPE = tmp.get("transaction_type").toString();				
						outDs.OUT_DATA.add(row);

					}	
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
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
		}

		return outDs;
	}
}
