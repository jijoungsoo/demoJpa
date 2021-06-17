package com.example.demo.br.coin.upbit;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.anotation.OpService;
import com.example.demo.db.da.upbit.DA_UPBIT_MARKET;
import com.example.demo.db.domain.upbit.UpbitMarket;
import com.example.demo.exception.BizException;
import com.example.demo.sa.upbit.api.SA_UPBIT_QUOTATION_CANDLES_MONTHS;
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
public class BR_UPBIT_CANDLES_MONTHS_SYNC {
	@Autowired
    SA_UPBIT_QUOTATION_CANDLES_MONTHS saUpbitCandlesMonths;
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_UPBIT_CANDLES_MONTHS_SYNC")
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
	@ApiModel(value="OUT_DS-BR_UPBIT_CANDLES_MONTHS_SYNC")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_UPBIT_CANDLES_MONTHS_SYNC", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}

	@Autowired
	DA_UPBIT_MARKET daUpbitMarket;

	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"UPBIT"}, value = "UPBIT 일별시세를 싱크한다.", notes = "")
	//@PostMapping(path= "/api/BR_MIG_AV_ACTR_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {

		List<UpbitMarket> al= daUpbitMarket.find(null,null,null);
		for(int i=0;i<al.size();i++){
			String market=al.get(i).getMarket();
			String to="";
			String count ="30";
			saUpbitCandlesMonths.run(market, to, count);
		}		
		OUT_DS outDs = new OUT_DS();
		return outDs;
	}
}
