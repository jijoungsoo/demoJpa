package com.example.demo.br.coin.upbit;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.PAGE_DATA_ROW;
import com.example.demo.db.da.upbit.DA_UPBIT_MARKET;
import com.example.demo.db.domain.mig_av.QMigAvActr;
import com.example.demo.db.domain.upbit.UpbitMarket;
import com.example.demo.exception.BizException;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
public class BR_UPBIT_MARKET_FIND {

	@Autowired
    PjtUtil pjtU;
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_UPBIT_MARKET_FIND")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_UPBIT_MARKET_FIND", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
	}

	@ApiModel(value="IN_DATA_ROW-BR_UPBIT_MARKET_FIND")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("SEARCH_NM")
		@Schema(name = "SEARCH_NM", example = "1", description = "이름검색어")
		String SEARCH_NM = "";

		@JsonProperty("MARKET_WARNING")
		@Schema(name = "MARKET_WARNING", example = "NONE (해당 사항 없음), CAUTION(투자유의)", description = "유의 종목 여부")
		String MARKET_WARNING = "";

		@JsonProperty("MARKET_CD")
		@Schema(name = "MARKET_CD", example = "1", description = "MARKET_CD")
		String MARKET_CD = null;

		
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_UPBIT_MARKET_FIND")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_UPBIT_MARKET_FIND", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}

	@ApiModel(value="OUT_DATA_ROW-BR_UPBIT_MARKET_FIND")
	@Data
	static class OUT_DATA_ROW {
		@JsonProperty("MARKET")
		@Schema(name = "MARKET", example = "1", description = "MARKET")
		String MARKET = null;

		@JsonProperty("MARKET_CD")
		@Schema(name = "MARKET_CD", example = "1", description = "MARKET_CD")
		String MARKET_CD = null;

		@JsonProperty("KR_NM")
		@Schema(name = "KR_NM", example = "도지", description = "KR_NM")
		String KR_NM = null;
		@JsonProperty("EN_NM")
		@Schema(name = "EN_NM", example = "DOGE", description = "EN_NM")
		String EN_NM = null;
		@JsonProperty("MARKET_WARNING")
		@Schema(name = "MARKET_WARNING", example = "NONE (해당 사항 없음), CAUTION(투자유의)", description = "MARKET_WARNING")
		String MARKET_WARNING = null;


		@JsonProperty("CRT_DTM")
		@Schema(name = "CRT_DTM", example = "202012311640", description = "생성일시")
		String CRT_DTM = null;
		
		@JsonProperty("UPDT_DTM")
		@Schema(name = "UPDT_DTM", example = "202012311640", description = "수정일시")
		String UPDT_DTM = null;
	}
	
	@Autowired
	DA_UPBIT_MARKET daUpbitMarket;

	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"UPBIT"}, value = "UPBIT 종목을 조회한다.", notes = "페이징 처리 X")
	//@PostMapping(path= "/api/BR_MIG_AV_ACTR_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		String SEARCH_NM = null;
		String MARKET_WARNING = null;
		String MARKET_CD = null;

		if(inDS.IN_DATA!=null) {
			if(inDS.IN_DATA.size()>0) {
				SEARCH_NM  =inDS.IN_DATA.get(0).SEARCH_NM;
				MARKET_WARNING  =inDS.IN_DATA.get(0).MARKET_WARNING;
				MARKET_CD  =inDS.IN_DATA.get(0).MARKET_CD;
			}	
		}
		
		List<UpbitMarket> al=daUpbitMarket.find(SEARCH_NM,MARKET_WARNING,MARKET_CD);
		OUT_DS outDs = new OUT_DS();
		for (int i = 0; i < al.size(); i++) {
			UpbitMarket c = al.get(i);
					
			OUT_DATA_ROW row = new OUT_DATA_ROW();
			row.MARKET = c.getMarket();
			row.MARKET_CD = c.getMarketCd();
			row.KR_NM = c.getKrNm();
			row.EN_NM = c.getEnNm();
			row.MARKET_WARNING = c.getMarketWarning();
			row.UPDT_DTM = pjtU.getYyyy_MM_dd_HHMMSS(c.getUpdtDtm());
			row.CRT_DTM = pjtU.getYyyy_MM_dd_HHMMSS(c.getCrtDtm());
			outDs.OUT_DATA.add(row);
		}
		return outDs;
	}
}
