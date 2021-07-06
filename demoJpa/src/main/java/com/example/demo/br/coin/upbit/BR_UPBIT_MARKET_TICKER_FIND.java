package com.example.demo.br.coin.upbit;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.anotation.OpService;
import com.example.demo.db.da.upbit.DA_UPBIT_MARKET;
import com.example.demo.db.domain.upbit.QUpbitCandlesMinutes;
import com.example.demo.db.domain.upbit.QUpbitMarket;
import com.example.demo.db.domain.upbit.UpbitMarket;
import com.example.demo.exception.BizException;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;

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
public class BR_UPBIT_MARKET_TICKER_FIND {

	@Autowired
    PjtUtil pjtU;
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_UPBIT_MARKET_TICKER_FIND")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_UPBIT_MARKET_TICKER_FIND", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
	}

	@ApiModel(value="IN_DATA_ROW-BR_UPBIT_MARKET_TICKER_FIND")
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
	@ApiModel(value="OUT_DS-BR_UPBIT_MARKET_TICKER_FIND")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_UPBIT_MARKET_TICKER_FIND", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}

	@ApiModel(value="OUT_DATA_ROW-BR_UPBIT_MARKET_TICKER_FIND")
	@Data
	static class OUT_DATA_ROW {

		@JsonProperty("MARKET")
		@Schema(name = "MARKET", example = "KRW-BTC", description = "종목명")
		String MARKET = null;

		@JsonProperty("MARKET_CD")
		@Schema(name = "MARKET_CD", example = "1", description = "통화코드")
		String MARKET_CD = null;

		@JsonProperty("MARKET_WARNING")
		@Schema(name = "MARKET_WARNING", example = "1", description = "유의 종목 여부")
		String MARKET_WARNING = null;

		

		@JsonProperty("EN_NM")
		@Schema(name = "EN_NM", example = "KRW-BTC", description = "영문명")
		String EN_NM = null;

		@JsonProperty("KR_NM")
		@Schema(name = "KR_NM", example = "KRW-BTC", description = "한글명")
		String KR_NM = null;

		@JsonProperty("TRADE_DATE")
		@Schema(name = "TRADE_DATE", example = "20201231", description = "최근 거래 일자(UTC)")
		String TRADE_DATE = null;
		@JsonProperty("TRADE_TIME")
		@Schema(name = "TRADE_TIME", example = "231245", description = "최근 거래 시각(UTC)")
		String TRADE_TIME = null;
		@JsonProperty("TRADE_DATE_KST")
		@Schema(name = "TRADE_DATE_KST", example = "20201231", description = "최근 거래 일자(KST)")
		String TRADE_DATE_KST = null;
		@JsonProperty("TRADE_TIME_KST")
		@Schema(name = "TRADE_TIME_KST", example = "231245", description = "최근 거래 시각(KST)")
		String TRADE_TIME_KST = null;

		@JsonProperty("OPENING_PRICE")
		@Schema(name = "OPENING_PRICE", example = "8450000", description = "시가")
		Double OPENING_PRICE = null;

		@JsonProperty("HIGH_PRICE")
		@Schema(name = "HIGH_PRICE", example = "8450000", description = "고가")
		Double HIGH_PRICE = null;

		@JsonProperty("LOW_PRICE")
		@Schema(name = "LOW_PRICE", example = "8450000", description = "저가")
		Double LOW_PRICE = null;

		@JsonProperty("TRADE_PRICE")
		@Schema(name = "TRADE_PRICE", example = "8450000", description = "종가")
		Double TRADE_PRICE = null;

		@JsonProperty("PREV_CLOSING_PRICE")
		@Schema(name = "PREV_CLOSING_PRICE", example = "8450000", description = "전일 종가")
		Double PREV_CLOSING_PRICE = null;

		@JsonProperty("CHANGE")
		@Schema(name = "CHANGE", example = "8450000", description = "CHANGE")
		String CHANGE = null;

		@JsonProperty("CHANGE_PRICE")
		@Schema(name = "CHANGE_PRICE", example = "8450000", description = "변화액의 절대값")
		Double CHANGE_PRICE = null;


		@JsonProperty("CHANGE_RATE")
		@Schema(name = "CHANGE_RATE", example = "8450000", description = "변화율의 절대값")
		Double CHANGE_RATE = null;


		@JsonProperty("SIGNED_CHANGE_PRICE")
		@Schema(name = "SIGNED_CHANGE_PRICE", example = "8450000", description = "부호가 있는 변화액")
		Double SIGNED_CHANGE_PRICE = null;

		@JsonProperty("SIGNED_CHANGE_RATE")
		@Schema(name = "SIGNED_CHANGE_RATE", example = "8450000", description = "부호가 있는 변화율")
		Double SIGNED_CHANGE_RATE = null;

		@JsonProperty("TRADE_VOLUME")
		@Schema(name = "TRADE_VOLUME", example = "8450000", description = "가장 최근 거래량")
		Double TRADE_VOLUME = null;

		@JsonProperty("ACC_TRADE_PRICE")
		@Schema(name = "ACC_TRADE_PRICE", example = "8450000", description = "누적 거래대금(UTC 0시 기준)")
		Double ACC_TRADE_PRICE = null;

		@JsonProperty("ACC_TRADE_PRICE_24H")
		@Schema(name = "ACC_TRADE_PRICE_24H", example = "8450000", description = "24시간 누적 거래대금")
		Double ACC_TRADE_PRICE_24H = null;

		@JsonProperty("ACC_TRADE_VOLUME")
		@Schema(name = "ACC_TRADE_VOLUME", example = "8450000", description = "누적 거래량(UTC 0시 기준)")
		Double ACC_TRADE_VOLUME = null;

		@JsonProperty("ACC_TRADE_VOLUME_24H")
		@Schema(name = "ACC_TRADE_VOLUME_24H", example = "8450000", description = "24시간 누적 거래량")
		Double ACC_TRADE_VOLUME_24H = null;

		@JsonProperty("HIGHEST_52_WEEK_PRICE")
		@Schema(name = "HIGHEST_52_WEEK_PRICE", example = "8450000", description = "52주 신고가")
		Double HIGHEST_52_WEEK_PRICE = null;

		@JsonProperty("HIGHEST_52_WEEK_DATE")
		@Schema(name = "HIGHEST_52_WEEK_DATE", example = "8450000", description = "52주 신고가 달성일")
		String HIGHEST_52_WEEK_DATE = null;

		@JsonProperty("LOWEST_52_WEEK_PRICE")
		@Schema(name = "LOWEST_52_WEEK_PRICE", example = "8450000", description = "52주 신저가")
		Double LOWEST_52_WEEK_PRICE = null;

		@JsonProperty("LOWEST_52_WEEK_DATE")
		@Schema(name = "LOWEST_52_WEEK_DATE", example = "8450000", description = "52주 신저가 달성일")
		String LOWEST_52_WEEK_DATE = null;

		@JsonProperty("TIMESTAMP")
		@Schema(name = "TIMESTAMP", example = "1524046650532", description = "TIMESTAMP")
		Long TIMESTAMP = null;


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
		
		List<UpbitMarket> al=daUpbitMarket.find(SEARCH_NM,MARKET_WARNING,MARKET_CD,"N"
		/*삭제유무 */
		);
		OUT_DS outDs = new OUT_DS();
		for (int i = 0; i < al.size(); i++) {
			UpbitMarket c = al.get(i);
					
			OUT_DATA_ROW row = new OUT_DATA_ROW();

			row.MARKET = c.getMarket();
			row.MARKET_CD = c.getMarketCd();
			row.MARKET_WARNING = c.getMarketWarning();
			
			row.EN_NM = c.getEnNm();
			row.KR_NM = c.getKrNm();
			row.TRADE_DATE = c.getTradeDate();
			row.TRADE_TIME = c.getTradeTime();
			row.TRADE_DATE_KST = c.getTradeDateKst();
			row.TRADE_TIME_KST = c.getTradeTimeKst();

			row.OPENING_PRICE =  c.getOpeningPrice();
			row.HIGH_PRICE =  c.getHighPrice();
			row.LOW_PRICE =  c.getLowPrice();
			row.TRADE_PRICE =  c.getTradePrice();

			row.PREV_CLOSING_PRICE =  c.getPrevClosingPrice();
			row.CHANGE =  c.getChange();
			row.CHANGE_PRICE =  c.getChangePrice();
			row.CHANGE_RATE =  c.getChangeRate();
			row.SIGNED_CHANGE_PRICE =  c.getSignedChangePrice();
			row.SIGNED_CHANGE_RATE =  c.getSignedChangeRate();


			row.TRADE_VOLUME =  c.getTradeVolume();
			row.ACC_TRADE_PRICE	= c.getAccTradePrice();
			row.ACC_TRADE_PRICE_24H	= c.getAccTradePrice24h();
			row.ACC_TRADE_VOLUME = c.getAccTradeVolume();
			row.ACC_TRADE_VOLUME_24H= c.getAccTradeVolume24h();
			row.HIGHEST_52_WEEK_PRICE= c.getHighest52WeekPrice();
			row.HIGHEST_52_WEEK_DATE=c.getHighest52WeekDate();
			row.LOWEST_52_WEEK_PRICE = c.getLowest52WeekPrice();
			row.LOWEST_52_WEEK_DATE = c.getLowest52WeekDate();
			row.TIMESTAMP = c.getTimestamp();

			row.UPDT_DTM = pjtU.getYyyy_MM_dd_HHMMSS(c.getUpdtDtm());
			row.CRT_DTM = pjtU.getYyyy_MM_dd_HHMMSS(c.getCrtDtm());

			
		
			outDs.OUT_DATA.add(row);
		}
		return outDs;
	}
}
