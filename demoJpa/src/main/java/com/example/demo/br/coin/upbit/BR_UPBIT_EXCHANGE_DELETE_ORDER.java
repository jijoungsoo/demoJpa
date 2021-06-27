package com.example.demo.br.coin.upbit;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.demo.anotation.OpService;
import com.example.demo.br.coin.upbit.BR_UPBIT_EXCHANGE_GET_ORDER.OUT_DATA_TRADE_ROW;
import com.example.demo.exception.BizException;
import com.example.demo.sa.upbit.api.SA_UPBIT_EXCHANGE_DELETE_ORDER;
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
public class BR_UPBIT_EXCHANGE_DELETE_ORDER {

	@Autowired
    PjtUtil pjtU;
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_UPBIT_EXCHANGE_DELETE_ORDER")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_UPBIT_EXCHANGE_DELETE_ORDER", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
	}

	@ApiModel(value="IN_DATA_ROW-BR_UPBIT_EXCHANGE_DELETE_ORDER")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("UUID")
		@Schema(name = "UUID", example = "KRW-ADX", description = "주문 UUID")
		String UUID = null;

		@JsonProperty("IDENTIFIER")
		@Schema(name = "IDENTIFIER", example = "KRW-ADX", description = "조회용 사용자 지정 값")
		String IDENTIFIER =null;
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_UPBIT_EXCHANGE_DELETE_ORDER")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_UPBIT_EXCHANGE_DELETE_ORDER", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}

	@ApiModel(value="OUT_DATA_ROW-BR_UPBIT_EXCHANGE_DELETE_ORDER")
	@Data
	static class OUT_DATA_ROW {

		@JsonProperty("UUID")  //String
		@Schema(name = "UUID", example = "1", description = "주문의 고유 아이디")
		String UUID = null;

		@JsonProperty("SIDE")  //String
		@Schema(name = "SIDE", example = "1", description = "주문 종류")
		String SIDE = null;

		@JsonProperty("ORD_TYPE")
		@Schema(name = "ORD_TYPE", example = "KRW-BTC", description = "주문 방식")
		String ORD_TYPE = null;

		@JsonProperty("PRICE")
		@Schema(name = "PRICE", example = "BTC/KRW", description = "주문 당시 화폐 가격")
		String PRICE = null;

		@JsonProperty("STATE")
		@Schema(name = "STATE", example = "limit", description = "주문 상태")
		String STATE = null;

		@JsonProperty("MARKET")
		@Schema(name = "MARKET", example = "ask,bid", description = "마켓의 유일키")
		String MARKET = null;


		//market.bid ==> 매수 시 제약사항
		@JsonProperty("CREATED_AT")
		@Schema(name = "CREATED_AT", example = "KRW", description = "주문 생성 시간")
		String CREATED_AT = null;

		@JsonProperty("VOLUME")
		@Schema(name = "VOLUME", example = "null", description = "사용자가 입력한 주문 양")
		String VOLUME = null;

		@JsonProperty("REMAINING_VOLUME")
		@Schema(name = "REMAINING_VOLUME", example = "1000", description = "체결 후 남은 주문 양")
		String REMAINING_VOLUME = null;

		//market.ask ==> 매도 시 제약사항
		@JsonProperty("RESERVED_FEE")
		@Schema(name = "RESERVED_FEE", example = "8450000", description = "수수료로 예약된 비용")
		String RESERVED_FEE = null;

		@JsonProperty("REMAINING_FEE")
		@Schema(name = "REMAINING_FEE", example = "8450000", description = "남은 수수료")
		String REMAINING_FEE = null;

		@JsonProperty("PAID_FEE")
		@Schema(name = "PAID_FEE", example = "8450000", description = "사용된 수수료")
		String PAID_FEE = null;

		@JsonProperty("LOCKED")
		@Schema(name = "LOCKED", example = "1524046650532", description = "거래에 사용중인 비용")
		String LOCKED = null;

		@JsonProperty("EXECUTED_VOLUME")
		@Schema(name = "EXECUTED_VOLUME", example = "107184005903.68721", description = "체결된 양")
		String EXECUTED_VOLUME = null;

		//bid_account  ==>  	매수 시 사용하는 화폐의 계좌 상태
		@JsonProperty("TRADE_COUNT")
		@Schema(name = "TRADE_COUNT", example = "107184005903.68721", description = "해당 주문에 걸린 체결 수")
		String TRADE_COUNT = null;

	}

	@Autowired
	SA_UPBIT_EXCHANGE_DELETE_ORDER saUpbitExchangeDeleteOrder;

	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"UPBIT"}, value = "UPBIT 종목을 조회한다.", notes = "페이징 처리 X")
	//@PostMapping(path= "/api/BR_MIG_AV_ACTR_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		String UUID = null;
		String IDENTIFIER = null;
		OUT_DS outDs = new OUT_DS();
		if(inDS.IN_DATA!=null) {
			if(inDS.IN_DATA.size()>0) {
				UUID  =inDS.IN_DATA.get(0).UUID;
				IDENTIFIER  =inDS.IN_DATA.get(0).IDENTIFIER;
			}	
		}

		if(pjtU.isEmpty(UUID)  && pjtU.isEmpty(IDENTIFIER)){
			throw new BizException("파라미터가 전달되지 않았습니다.");
		}

		HashMap<String, Object> h=null;
			try {
				h = saUpbitExchangeDeleteOrder.run(UUID,IDENTIFIER);
			} catch (NoSuchAlgorithmException | URISyntaxException | IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			} catch (BizException e) {
				System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbb");
				// TODO Auto-generated catch block
				//e.printStackTrace();
				throw e;
			}
			if(h!=null) {
				OUT_DATA_ROW row = new OUT_DATA_ROW();
				row.UUID = h.get("uuid").toString();
				row.SIDE = h.get("side").toString();
				row.ORD_TYPE = h.get("ord_type").toString();
				row.PRICE = h.get("price").toString();
				row.STATE = h.get("state").toString();
				row.MARKET = h.get("market").toString();
				row.CREATED_AT = h.get("created_at").toString();
				row.VOLUME = h.get("volume").toString();
				row.REMAINING_VOLUME = h.get("remaining_volume").toString();
				row.RESERVED_FEE = h.get("reserved_fee").toString();
				row.REMAINING_FEE = h.get("remaining_fee").toString();
				row.PAID_FEE = h.get("paid_fee").toString();
				row.LOCKED = h.get("locked").toString();
				row.EXECUTED_VOLUME = h.get("executed_volume").toString();
				if(h.get("trade_count")!=null) {
					row.TRADE_COUNT = h.get("trade_count").toString();
				}	
				outDs.OUT_DATA.add(row);
			}
		

		return outDs;
	}
}
