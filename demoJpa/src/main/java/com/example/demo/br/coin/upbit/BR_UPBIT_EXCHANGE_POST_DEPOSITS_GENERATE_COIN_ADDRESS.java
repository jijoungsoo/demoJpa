package com.example.demo.br.coin.upbit;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.demo.anotation.OpService;
import com.example.demo.exception.BizException;
import com.example.demo.sa.upbit.api.SA_UPBIT_EXCHANGE_POST_DEPOSITS_GENERATE_COIN_ADDRESS;
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
public class BR_UPBIT_EXCHANGE_POST_DEPOSITS_GENERATE_COIN_ADDRESS {

	@Autowired
    PjtUtil pjtU;
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_UPBIT_EXCHANGE_POST_DEPOSITS_GENERATE_COIN_ADDRESS")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_UPBIT_EXCHANGE_POST_DEPOSITS_GENERATE_COIN_ADDRESS", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
	}

	@ApiModel(value="IN_DATA_ROW-BR_UPBIT_EXCHANGE_POST_DEPOSITS_GENERATE_COIN_ADDRESS")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("CURRENCY")
		@Schema(name = "CURRENCY", example = "KRW-ADX", description = "CURRENCY코드")
		String CURRENCY = null;
	}
	
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_UPBIT_EXCHANGE_POST_DEPOSITS_GENERATE_COIN_ADDRESS")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_UPBIT_EXCHANGE_GET_DEPOSITS_COIN_ADDRESSES", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW1> OUT_DATA1 = new ArrayList<OUT_DATA_ROW1>();
		ArrayList<OUT_DATA_ROW2> OUT_DATA2 = new ArrayList<OUT_DATA_ROW2>();
	}

	@ApiModel(value="OUT_DATA_ROW-BR_UPBIT_EXCHANGE_POST_DEPOSITS_GENERATE_COIN_ADDRESS")
	@Data
	static class OUT_DATA_ROW1 {
		@JsonProperty("SUCCESS")
		@Schema(name = "SUCCESS", example = "KRW-BTC", description = "요청 성공 여부")
		String SUCCESS = null;

		@JsonProperty("MESSAGE")
		@Schema(name = "MESSAGE", example = "BTC/KRW", description = "요청 결과에 대한 메세지")
		String MESSAGE = null;
	}

	@ApiModel(value="OUT_DATA_ROW-BR_UPBIT_EXCHANGE_POST_DEPOSITS_GENERATE_COIN_ADDRESS")
	@Data
	static class OUT_DATA_ROW2 {
		@JsonProperty("CURRENCY")
		@Schema(name = "CURRENCY", example = "KRW-BTC", description = "화폐를 의미하는 영문 대문자 코드")
		String CURRENCY = null;

		@JsonProperty("DEPOSIT_ADDRESS")
		@Schema(name = "DEPOSIT_ADDRESS", example = "BTC/KRW", description = "입금 주소")
		String DEPOSIT_ADDRESS = null;

		@JsonProperty("SECONDARY_ADDRESS")
		@Schema(name = "SECONDARY_ADDRESS", example = "BTC/KRW", description = "2차 입금 주소")
		String SECONDARY_ADDRESS = null;

		/*
		입금 주소 생성 요청 API 유의사항
		입금 주소의 생성은 서버에서 비동기적으로 이뤄집니다.
		비동기적 생성 특성상 요청과 동시에 입금 주소가 발급되지 않을 수 있습니다.
		주소 발급 요청 시 결과로 Response1이 반환되며 주소 발급 완료 이전까지 계속 Response1이 반환됩니다.
		주소가 발급된 이후부터는 새로운 주소가 발급되는 것이 아닌 이전에 발급된 주소가 Response2 형태로 반환됩니다.
		정상적으로 주소가 생성되지 않는다면 일정 시간 이후 해당 API를 다시 호출해주시길 부탁드립니다.		
		*/
	}
	
	@Autowired
	SA_UPBIT_EXCHANGE_POST_DEPOSITS_GENERATE_COIN_ADDRESS saUpbitExchangePostDepositsGenerateCoinAddress;

	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"UPBIT"}, value = "UPBIT 종목을 조회한다.", notes = "페이징 처리 X")
	//@PostMapping(path= "/api/BR_MIG_AV_ACTR_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		OUT_DS outDs = new OUT_DS();
		String CURRENCY = null;
		HashMap<String, Object> h;
		if(inDS.IN_DATA!=null) {
			if(inDS.IN_DATA.size()>0) {
				CURRENCY  =inDS.IN_DATA.get(0).CURRENCY;
				try {
					h = saUpbitExchangePostDepositsGenerateCoinAddress.run(CURRENCY);
					if(h!=null) {
					
						if(h.get("success")!=null){
							OUT_DATA_ROW1 row = new OUT_DATA_ROW1();
							row.SUCCESS = h.get("success").toString();
							row.MESSAGE = h.get("message").toString();
							outDs.OUT_DATA1.add(row);
						} else {
							OUT_DATA_ROW2 row = new OUT_DATA_ROW2();
							row.CURRENCY = h.get("currency").toString();
							row.DEPOSIT_ADDRESS = h.get("deposit_address").toString();
							row.SECONDARY_ADDRESS = h.get("secondary_address").toString();
							outDs.OUT_DATA2.add(row);
						}				
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
