package com.example.demo.br.coin.upbit;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.demo.anotation.OpService;
import com.example.demo.exception.BizException;
import com.example.demo.sa.upbit.api.SA_UPBIT_EXCHANGE_GET_ORDERS_CHANCE;
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
public class BR_UPBIT_EXCHANGE_GET_ORDERS_CHANCE {

	public static com.example.demo.br.coin.upbit.BR_UPBIT_EXCHANGE_GET_ORDERS_CHANCE.OUT_DS OUT_DS;
	@Autowired
    PjtUtil pjtU;
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_UPBIT_EXCHANGE_GET_ORDERS_CHANCE")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_UPBIT_EXCHANGE_GET_ORDERS_CHANCE", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
	}

	@ApiModel(value="IN_DATA_ROW-BR_UPBIT_EXCHANGE_GET_ORDERS_CHANCE")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("MARKET")
		@Schema(name = "MARKET", example = "KRW-ADX", description = "종목명")
		String MARKET = "";
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_UPBIT_EXCHANGE_GET_ORDERS_CHANCE")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_UPBIT_EXCHANGE_GET_ORDERS_CHANCE", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}

	@ApiModel(value="OUT_DATA_ROW-BR_UPBIT_CANDLES_WEEKS_FIND")
	@Data
	static class OUT_DATA_ROW {

		@JsonProperty("BID_FEE")  //NumberString
		@Schema(name = "BID_FEE", example = "1", description = "매수 수수료 비율")
		String BID_FEE = null;

		@JsonProperty("ASK_FEE")  //NumberString
		@Schema(name = "ASK_FEE", example = "1", description = "매도 수수료 비율")
		String ASK_FEE = null;

		@JsonProperty("MARKET__ID")
		@Schema(name = "MARKET__ID", example = "KRW-BTC", description = "마켓의 유일 키")
		String MARKET__ID = null;

		@JsonProperty("MARKET__NAME")
		@Schema(name = "MARKET__NAME", example = "BTC/KRW", description = "마켓 이름")
		String MARKET__NAME = null;

		

		@JsonProperty("MARKET__ORDER_TYPES")
		@Schema(name = "MARKET__ORDER_TYPES", example = "limit", description = "지원 주문 방식")
		String MARKET__ORDER_TYPES = null;

		@JsonProperty("MARKET__ORDER_SIDES")
		@Schema(name = "MARKET__ORDER_SIDES", example = "ask,bid", description = "지원 주문 종류")
		String MARKET__ORDER_SIDES = null;


		//market.bid ==> 매수 시 제약사항
		@JsonProperty("MARKET__BID__CURRENCY")
		@Schema(name = "MARKET__BID__CURRENCY", example = "KRW", description = "화폐를 의미하는 영문 대문자 코드")
		String MARKET__BID__CURRENCY = null;

		@JsonProperty("MARKET__BID__PRICE_UNIT")
		@Schema(name = "MARKET__BID__PRICE_UNIT", example = "null", description = "주문금액 단위")
		String MARKET__BID__PRICE_UNIT = null;

		@JsonProperty("MARKET__BID__MIN_TOTAL")
		@Schema(name = "MARKET__BID__MIN_TOTAL", example = "1000", description = "최소 매도/매수 금액")
		String MARKET__BID__MIN_TOTAL = null;

		//market.ask ==> 매도 시 제약사항
		@JsonProperty("MARKET__ASK__CURRENCY")
		@Schema(name = "MARKET__ASK__CURRENCY", example = "8450000", description = "화폐를 의미하는 영문 대문자 코드")
		String MARKET__ASK__CURRENCY = null;

		@JsonProperty("MARKET__ASK__PRICE_UNIT")
		@Schema(name = "MARKET__ASK__PRICE_UNIT", example = "8450000", description = "주문금액 단위")
		String MARKET__ASK__PRICE_UNIT = null;


		@JsonProperty("MARKET__ASK__MIN_TOTAL")
		@Schema(name = "MARKET__ASK__MIN_TOTAL", example = "8450000", description = "최소 매도/매수 금액")
		String MARKET__ASK__MIN_TOTAL = null;

		@JsonProperty("MARKET__MAX_TOTAL")
		@Schema(name = "MARKET__MAX_TOTAL", example = "1524046650532", description = "최대 매도/매수 금액")
		String MARKET__MAX_TOTAL = null;

		@JsonProperty("MARKET__STATE")
		@Schema(name = "MARKET__STATE", example = "107184005903.68721", description = "마켓 운영 상태")
		String MARKET__STATE = null;

		//bid_account  ==>  	매수 시 사용하는 화폐의 계좌 상태
		@JsonProperty("BID_ACCOUNT__CURRENCY")
		@Schema(name = "BID_ACCOUNT__CURRENCY", example = "107184005903.68721", description = "화폐를 의미하는 영문 대문자 코드")
		String BID_ACCOUNT__CURRENCY = null;

		@JsonProperty("BID_ACCOUNT__BALANCE")
		@Schema(name = "BID_ACCOUNT__BALANCE", example = "0.0208284024", description = "주문가능 금액/수량")
		String BID_ACCOUNT__BALANCE = null;

		@JsonProperty("BID_ACCOUNT__LOCKED")
		@Schema(name = "BID_ACCOUNT__LOCKED", example = "202012311640", description = "주문 중 묶여있는 금액/수량")
		String BID_ACCOUNT__LOCKED = null;
		
		@JsonProperty("BID_ACCOUNT__AVG_BUY_PRICE")
		@Schema(name = "BID_ACCOUNT__AVG_BUY_PRICE", example = "202012311640", description = "매수평균가")
		String BID_ACCOUNT__AVG_BUY_PRICE = null;

		@JsonProperty("BID_ACCOUNT__AVG_BUY_PRICE_MODIFIED")
		@Schema(name = "BID_ACCOUNT__AVG_BUY_PRICE_MODIFIED", example = "202012311640", description = "매수평균가 수정 여부")
		String BID_ACCOUNT__AVG_BUY_PRICE_MODIFIED = null;

		@JsonProperty("BID_ACCOUNT__UNIT_CURRENCY")
		@Schema(name = "BID_ACCOUNT__UNIT_CURRENCY", example = "202012311640", description = "평단가 기준 화폐")
		String BID_ACCOUNT__UNIT_CURRENCY = null;

//ask_account==>  매도 시 사용하는 화폐의 계좌 상태
		@JsonProperty("ASK_ACCOUNT__CURRENCY")
		@Schema(name = "ASK_ACCOUNT__CURRENCY", example = "202012311640", description = "화폐를 의미하는 영문 대문자 코드")
		String ASK_ACCOUNT__CURRENCY = null;

		@JsonProperty("ASK_ACCOUNT__BALANCE")
		@Schema(name = "ASK_ACCOUNT__BALANCE", example = "202012311640", description = "주문가능 금액/수량")
		String ASK_ACCOUNT__BALANCE = null;

		
		@JsonProperty("ASK_ACCOUNT__LOCKED")
		@Schema(name = "ASK_ACCOUNT__LOCKED", example = "202012311640", description = "주문 중 묶여있는 금액/수량")
		String ASK_ACCOUNT__LOCKED = null;


		@JsonProperty("ASK_ACCOUNT__AVG_BUY_PRICE")
		@Schema(name = "ASK_ACCOUNT__AVG_BUY_PRICE", example = "202012311640", description = "매수평균가")
		String ASK_ACCOUNT__AVG_BUY_PRICE = null;

		@JsonProperty("ASK_ACCOUNT__AVG_BUY_PRICE_MODIFIED")
		@Schema(name = "ASK_ACCOUNT__AVG_BUY_PRICE_MODIFIED", example = "202012311640", description = "매수평균가 수정 여부")
		String ASK_ACCOUNT__AVG_BUY_PRICE_MODIFIED = null;

		@JsonProperty("ASK_ACCOUNT__UNIT_CURRENCY")
		@Schema(name = "ASK_ACCOUNT__UNIT_CURRENCY", example = "202012311640", description = "평단가 기준 화폐")
		String ASK_ACCOUNT__UNIT_CURRENCY = null;


	}
	
	@Autowired
	SA_UPBIT_EXCHANGE_GET_ORDERS_CHANCE saUpbitExchangeGetOrdersChance;

	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"UPBIT"}, value = "UPBIT 종목을 조회한다.", notes = "페이징 처리 X")
	//@PostMapping(path= "/api/BR_MIG_AV_ACTR_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		OUT_DS outDs = new OUT_DS();
		String MARKET = null;
		if(inDS.IN_DATA!=null) {
			if(inDS.IN_DATA.size()>0) {
				MARKET  =inDS.IN_DATA.get(0).MARKET;
			}	
		}

		HashMap<String, Object> h;
		try {
			h = saUpbitExchangeGetOrdersChance.run(MARKET);
			
			if(h!=null){
				OUT_DATA_ROW row = new OUT_DATA_ROW();
				if(h.get("bid_fee")!=null){
					row.BID_FEE = h.get("bid_fee").toString();
				}
				if(h.get("ask_fee")!=null){
					row.ASK_FEE = h.get("ask_fee").toString();
				}


				
				
				
				HashMap market = (HashMap) h.get("market");

				if(h.get("market")!=null){
					row.MARKET__ID = market.get("id").toString();
					row.MARKET__NAME = market.get("name").toString();
	
					ArrayList<String> order_types = (ArrayList<String>)market.get("order_types");
					ArrayList<String> order_sides = (ArrayList<String>)market.get("order_sides");
	
					row.MARKET__ORDER_TYPES = String.join(",", order_types) ;
					row.MARKET__ORDER_SIDES = String.join(",", order_sides) ;
	
					HashMap bid = (HashMap) market.get("bid");
	
					row.MARKET__BID__CURRENCY = bid.get("currency").toString();
					if(bid.get("price_unit")!=null) {
						row.MARKET__BID__PRICE_UNIT = bid.get("price_unit").toString();
					}
					
					row.MARKET__BID__MIN_TOTAL = bid.get("min_total").toString();

					HashMap ask = (HashMap) market.get("ask");
					row.MARKET__ASK__CURRENCY = ask.get("currency").toString();
					if(ask.get("price_unit")!=null) {
						row.MARKET__ASK__PRICE_UNIT = ask.get("price_unit").toString();
					}
					row.MARKET__ASK__MIN_TOTAL = ask.get("min_total").toString();
					row.MARKET__MAX_TOTAL = market.get("max_total").toString();
					row.MARKET__STATE = market.get("state").toString();
	
					
				}
			
				HashMap bid_account = (HashMap) h.get("bid_account");
				row.BID_ACCOUNT__CURRENCY = bid_account.get("currency").toString();
				row.BID_ACCOUNT__BALANCE = bid_account.get("balance").toString();
				row.BID_ACCOUNT__LOCKED = bid_account.get("locked").toString();
				row.BID_ACCOUNT__AVG_BUY_PRICE = bid_account.get("avg_buy_price").toString();
				row.BID_ACCOUNT__AVG_BUY_PRICE_MODIFIED = bid_account.get("avg_buy_price_modified").toString();
				row.BID_ACCOUNT__UNIT_CURRENCY = bid_account.get("unit_currency").toString();

				HashMap ask_account = (HashMap) h.get("ask_account");

				row.ASK_ACCOUNT__CURRENCY = ask_account.get("currency").toString();
				row.ASK_ACCOUNT__BALANCE = ask_account.get("balance").toString();
				row.ASK_ACCOUNT__LOCKED = ask_account.get("locked").toString();
				row.ASK_ACCOUNT__AVG_BUY_PRICE = ask_account.get("avg_buy_price").toString();
				row.ASK_ACCOUNT__AVG_BUY_PRICE_MODIFIED = ask_account.get("avg_buy_price_modified").toString();
				row.ASK_ACCOUNT__UNIT_CURRENCY = ask_account.get("unit_currency").toString();

				outDs.OUT_DATA.add(row);
			}

		} catch (NoSuchAlgorithmException | URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return outDs;
	}
}
