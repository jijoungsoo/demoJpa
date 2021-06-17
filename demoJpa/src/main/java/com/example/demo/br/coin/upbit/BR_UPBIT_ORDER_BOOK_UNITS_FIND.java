package com.example.demo.br.coin.upbit;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.PAGE_DATA_ROW;
import com.example.demo.db.da.upbit.DA_UPBIT_ORDER_BOOK;
import com.example.demo.db.da.upbit.DA_UPBIT_ORDER_BOOK_UNITS;
import com.example.demo.db.domain.upbit.QUpbitMarket;
import com.example.demo.db.domain.upbit.QUpbitOrderBook;
import com.example.demo.db.domain.upbit.QUpbitOrderBookUnits;
import com.example.demo.db.domain.upbit.QUpbitTradesTicks;
import com.example.demo.exception.BizException;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.querydsl.core.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public class BR_UPBIT_ORDER_BOOK_UNITS_FIND {

	@Autowired
    PjtUtil pjtU;
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_UPBIT_ORDER_BOOK_UNITS_FIND")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_UPBIT_ORDER_BOOK_UNITS_FIND", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
	}

	@ApiModel(value="IN_DATA_ROW-BR_UPBIT_ORDER_BOOK_UNITS_FIND")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("MARKET")
		@Schema(name = "MARKET", example = "1", description = "MARKET")
		String MARKET = "";

		@JsonProperty("TIMESTAMP")
		@Schema(name = "TIMESTAMP", example = "1", description = "TIMESTAMP")
		String TIMESTAMP = null;
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

		@JsonProperty("MARKET")
		@Schema(name = "MARKET", example = "KRW-BTC", description = "MARKET")
		String MARKET = null;

		@JsonProperty("MARKET_CD")
		@Schema(name = "MARKET_CD", example = "1", description = "MARKET_CD")
		String MARKET_CD = null;

		@JsonProperty("MARKET_WARNING")
		@Schema(name = "MARKET_WARNING", example = "1", description = "MARKET_WARNING")
		String MARKET_WARNING = null;		

		@JsonProperty("EN_NM")
		@Schema(name = "EN_NM", example = "KRW-BTC", description = "EN_NM")
		String EN_NM = null;

		@JsonProperty("KR_NM")
		@Schema(name = "KR_NM", example = "KRW-BTC", description = "KR_NM")
		String KR_NM = null;

		@JsonProperty("SEQ")
		@Schema(name = "SEQ", example = "1", description = "SEQ")
		Integer SEQ = null;

		@JsonProperty("TIMESTAMP")
		@Schema(name = "TIMESTAMP", example = "1524046650532", description = "호가 생성 시각")
		Long TIMESTAMP = null;

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

		@JsonProperty("CRT_DTM")
		@Schema(name = "CRT_DTM", example = "202012311640", description = "생성일시")
		String CRT_DTM = null;
		
		@JsonProperty("UPDT_DTM")
		@Schema(name = "UPDT_DTM", example = "202012311640", description = "수정일시")
		String UPDT_DTM = null;
	}
	
	@Autowired
	DA_UPBIT_ORDER_BOOK_UNITS daUpbitOrderBookUnits;

	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"UPBIT"}, value = "UPBIT 종목을 조회한다.", notes = "페이징 처리 X")
	//@PostMapping(path= "/api/BR_MIG_AV_ACTR_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		String MARKET =   inDS.IN_DATA.get(0).MARKET;
		String TIMESTAMP =inDS.IN_DATA.get(0).TIMESTAMP;
		Long L_TIMESTAMP = Long.parseLong(TIMESTAMP);
		
		List<Tuple> al=daUpbitOrderBookUnits.find(MARKET,L_TIMESTAMP);

		OUT_DS outDs = new OUT_DS();
		for (int i = 0; i < al.size(); i++) {
			Tuple c = al.get(i);
					
			OUT_DATA_ROW row = new OUT_DATA_ROW();

			row.MARKET = c.get(QUpbitMarket.upbitMarket.market);
			row.MARKET_CD = c.get(QUpbitMarket.upbitMarket.marketCd);
			row.MARKET_WARNING = c.get(QUpbitMarket.upbitMarket.marketWarning);
			
			
			row.EN_NM = c.get(QUpbitMarket.upbitMarket.enNm);
			row.KR_NM = c.get(QUpbitMarket.upbitMarket.krNm);

			row.SEQ = c.get(QUpbitOrderBookUnits.upbitOrderBookUnits.seq);
			
			row.ASK_PRICE = c.get(QUpbitOrderBookUnits.upbitOrderBookUnits.askPrice);
			row.BID_PRICE = c.get(QUpbitOrderBookUnits.upbitOrderBookUnits.bidPrice);
			row.ASK_SIZE = c.get(QUpbitOrderBookUnits.upbitOrderBookUnits.askSize);
			row.BID_SIZE = c.get(QUpbitOrderBookUnits.upbitOrderBookUnits.bidSize);

			row.TIMESTAMP = c.get(QUpbitOrderBookUnits.upbitOrderBookUnits.timestamp);
			
			row.UPDT_DTM = pjtU.getYyyy_MM_dd_HHMMSS(c.get(QUpbitOrderBookUnits.upbitOrderBookUnits.updtDtm));
			row.CRT_DTM = pjtU.getYyyy_MM_dd_HHMMSS(c.get(QUpbitOrderBookUnits.upbitOrderBookUnits.crtDtm));

			
		
			outDs.OUT_DATA.add(row);
		}
		return outDs;
	}
}
