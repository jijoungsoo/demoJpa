package com.example.demo.br.coin.upbit;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.demo.anotation.OpService;
import com.example.demo.exception.BizException;
import com.example.demo.sa.upbit.api.SA_UPBIT_EXCHANGE_GET_STATUS_WALLET;
import com.example.demo.sa.upbit.api.SA_UPBIT_EXCHANGE_GET_WITHDRAWS;
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
public class BR_UPBIT_EXCHANGE_GET_STATUS_WALLET {

	@Autowired
    PjtUtil pjtU;
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_UPBIT_EXCHANGE_GET_STATUS_WALLET")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_UPBIT_EXCHANGE_GET_STATUS_WALLET", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
	}

	@ApiModel(value="IN_DATA_ROW-BR_UPBIT_EXCHANGE_GET_STATUS_WALLET")
	@Data
	static class IN_DATA_ROW {
		
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_UPBIT_EXCHANGE_GET_STATUS_WALLET")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_UPBIT_EXCHANGE_GET_WITHDRAWS", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}

	@ApiModel(value="OUT_DATA_ROW-BR_UPBIT_EXCHANGE_GET_WITHDRAWS")
	@Data
	static class OUT_DATA_ROW {

		@JsonProperty("CURRENCY")  //String
		@Schema(name = "CURRENCY", example = "1", description = "화폐를 의미하는 영문 대문자 코드")
		String CURRENCY = null;

		/*
		입출금 상태
- working : 입출금 가능
- withdraw_only : 출금만 가능
- deposit_only : 입금만 가능
- paused : 입출금 중단
- unsupported : 입출금 미지원
		
		*/

		@JsonProperty("WALLET_STATE")  //String
		@Schema(name = "WALLET_STATE", example = "1", description = "입출금 상태")
		String WALLET_STATE = null;

		/*
		블록 상태
- normal : 정상
- delayed : 지연
- inactive : 비활성 (점검 등)		
		*/

		@JsonProperty("BLOCK_STATE")
		@Schema(name = "BLOCK_STATE", example = "KRW-BTC", description = "블록 상태")
		String BLOCK_STATE = null;

		@JsonProperty("BLOCK_HEIGHT")
		@Schema(name = "BLOCK_HEIGHT", example = "BTC/KRW", description = "블록 높이")
		String BLOCK_HEIGHT = null;

		@JsonProperty("BLOCK_UPDATED_AT")
		@Schema(name = "BLOCK_UPDATED_AT", example = "limit", description = "블록 갱신 시각")
		String BLOCK_UPDATED_AT = null;
	}
	
	@Autowired
	SA_UPBIT_EXCHANGE_GET_STATUS_WALLET saUpbitExchangeGetStatusWallet;

	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"UPBIT"}, value = "UPBIT 종목을 조회한다.", notes = "페이징 처리 X")
	//@PostMapping(path= "/api/BR_MIG_AV_ACTR_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {

		OUT_DS outDs = new OUT_DS();


		ArrayList<HashMap<String, Object>> al;
			try {
				al = saUpbitExchangeGetStatusWallet.run();
			
			for(int i=0;i<al.size();i++){
				HashMap<String, Object>  tmp = al.get(i);
				OUT_DATA_ROW row = new OUT_DATA_ROW();
				row.CURRENCY = tmp.get("currency").toString();
				row.WALLET_STATE = tmp.get("wallet_state").toString();
				row.BLOCK_STATE = tmp.get("block_state").toString();
				if(tmp.get("block_height")!=null){
					row.BLOCK_HEIGHT = tmp.get("block_height").toString();
				}
				
				row.BLOCK_UPDATED_AT = tmp.get("block_updated_at").toString();				
				outDs.OUT_DATA.add(row);
			}
		} catch (NoSuchAlgorithmException | URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return outDs;
	}
}
