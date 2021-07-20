package com.example.demo.br.coin.upbit;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.demo.anotation.OpService;
import com.example.demo.exception.BizException;
import com.example.demo.sa.upbit.api.SA_UPBIT_EXCHANGE_GET_DEPOSITS_COIN_ADDRESS;
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
public class BR_UPBIT_EXCHANGE_GET_DEPOSITS_COIN_ADDRESS {

	@Autowired
    PjtUtil pjtU;
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_UPBIT_EXCHANGE_GET_DEPOSITS_COIN_ADDRESS")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_UPBIT_EXCHANGE_GET_DEPOSITS_COIN_ADDRESS", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
	}

	@ApiModel(value="IN_DATA_ROW-BR_UPBIT_EXCHANGE_GET_DEPOSITS_COIN_ADDRESS")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("CURRENCY")
		@Schema(name = "CURRENCY", example = "KRW-BTC", description = "화폐를 의미하는 영문 대문자 코드")
		String CURRENCY = null;
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_UPBIT_EXCHANGE_GET_DEPOSITS_COIN_ADDRESS")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_UPBIT_EXCHANGE_GET_DEPOSITS_COIN_ADDRESS", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}

	@ApiModel(value="OUT_DATA_ROW-BR_UPBIT_EXCHANGE_GET_DEPOSITS_COIN_ADDRESS")
	@Data
	static class OUT_DATA_ROW {
		@JsonProperty("CURRENCY")
		@Schema(name = "CURRENCY", example = "KRW-BTC", description = "화폐를 의미하는 영문 대문자 코드")
		String CURRENCY = null;

		@JsonProperty("DEPOSIT_ADDRESS")
		@Schema(name = "DEPOSIT_ADDRESS", example = "BTC/KRW", description = "입금 주소")
		String DEPOSIT_ADDRESS = null;

		@JsonProperty("SECONDARY_ADDRESS")
		@Schema(name = "SECONDARY_ADDRESS", example = "limit", description = "2차 입금 주소")
		String SECONDARY_ADDRESS = null;
	}
	
	@Autowired
	SA_UPBIT_EXCHANGE_GET_DEPOSITS_COIN_ADDRESS saUpbitExchangeGetDepositsCoinAddress;

	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"UPBIT"}, value = "UPBIT 종목을 조회한다.", notes = "페이징 처리 X")
	//@PostMapping(path= "/api/BR_MIG_AV_ACTR_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		OUT_DS outDs = new OUT_DS();


		String CURRENCY = null;
		if(inDS.IN_DATA!=null) {
			if(inDS.IN_DATA.size()>0) {
				CURRENCY  =inDS.IN_DATA.get(0).CURRENCY;
				HashMap<String, Object> tmp;
			try {
					tmp = saUpbitExchangeGetDepositsCoinAddress.run(CURRENCY);
					if(tmp!=null){
						OUT_DATA_ROW row = new OUT_DATA_ROW();
						row.CURRENCY = tmp.get("currency").toString();
						row.DEPOSIT_ADDRESS = tmp.get("deposit_address").toString();
						if(tmp.get("secondary_address")!=null) {
							row.SECONDARY_ADDRESS = tmp.get("secondary_address").toString();
						}				
						outDs.OUT_DATA.add(row);
					}
			
				} catch (NoSuchAlgorithmException | URISyntaxException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		

		return outDs;
	}
}
