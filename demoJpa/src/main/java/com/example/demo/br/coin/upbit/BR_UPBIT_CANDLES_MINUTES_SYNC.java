package com.example.demo.br.coin.upbit;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.anotation.OpService;
import com.example.demo.db.da.upbit.DA_UPBIT_MARKET;
import com.example.demo.db.domain.upbit.UpbitMarket;
import com.example.demo.exception.BizException;
import com.example.demo.sa.upbit.api.SA_UPBIT_QUOTATION_CANDLES_MINUTES;
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
public class BR_UPBIT_CANDLES_MINUTES_SYNC {
	@Autowired
    SA_UPBIT_QUOTATION_CANDLES_MINUTES saUpbitCandlesMinutes;
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_UPBIT_CANDLES_MINUTES_SYNC")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_UPBIT_CANDLES_MINUTES_SYNC", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
	}

	@ApiModel(value="IN_DATA_ROW-BR_UPBIT_CANDLES_MINUTES_SYNC")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("SEARCH_NM")
		@Schema(name = "SEARCH_NM", example = "1", description = "이름검색어")
		String SEARCH_NM = "";

		@JsonProperty("MARKET_CD")
		@Schema(name = "MARKET_CD", example = "1", description = "MARKET_CD")
		String MARKET_CD = null;

		@JsonProperty("MARKET_WARNING")
		@Schema(name = "MARKET_WARNING", example = "1", description = "MARKET_WARNING")
		String MARKET_WARNING = null;

		@JsonProperty("UNIT")
		@Schema(name = "UNIT", example = "1, 3, 5, 15, 10, 30, 60, 240", description = "분봉")
		String UNIT = null;


	}

	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_UPBIT_CANDLES_MINUTES_SYNC")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_UPBIT_CANDLES_MINUTES_SYNC", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}

	@Autowired
	DA_UPBIT_MARKET daUpbitMarket;

	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"UPBIT"}, value = "UPBIT 종목을 싱크한다.", notes = "")
	//@PostMapping(path= "/api/BR_MIG_AV_ACTR_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		String SEARCH_NM = null;
		String MARKET_WARNING = null;
		String MARKET_CD = null;
		String UNIT ="1";

		if(inDS.IN_DATA!=null) {
			if(inDS.IN_DATA.size()>0) {
				SEARCH_NM  =inDS.IN_DATA.get(0).SEARCH_NM;
				MARKET_WARNING  =inDS.IN_DATA.get(0).MARKET_WARNING;
				MARKET_CD  =inDS.IN_DATA.get(0).MARKET_CD;
				UNIT  =inDS.IN_DATA.get(0).UNIT;
			}	
		}
		List<UpbitMarket> al= daUpbitMarket.find(SEARCH_NM,MARKET_WARNING,MARKET_CD,"N");
		//QUOTATION API
		//294 개이니까.
		//1분에  2번 바뀐다.
		//모니터링을 모두 하기에는 너무 느리다.
		//전체 모니터링은 선택적으로 해야겠다.
		
		for(int i=0;i<al.size();i++){
			String market=al.get(i).getMarket();
			String to="";
			String count ="10";
			saUpbitCandlesMinutes.run(UNIT,market, to, count);
		}		
		OUT_DS outDs = new OUT_DS();
		return outDs;
	}
}
