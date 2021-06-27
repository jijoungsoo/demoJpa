package com.example.demo.br.coin.upbit;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.demo.anotation.OpService;
import com.example.demo.exception.BizException;
import com.example.demo.sa.upbit.api.SA_UPBIT_EXCHANGE_GET_ORDERS;
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
public class BR_UPBIT_EXCHANGE_GET_ORDERS {

	@Autowired
    PjtUtil pjtU;
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_UPBIT_EXCHANGE_GET_ORDERS")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_UPBIT_EXCHANGE_GET_ORDERS", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
	}

	@ApiModel(value="IN_DATA_ROW-BR_UPBIT_EXCHANGE_GET_ORDERS")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("MARKET")
		@Schema(name = "MARKET", example = "KRW-ADX", description = "종목명")
		String MARKET = null;

		@JsonProperty("STATE")
		@Schema(name = "STATE", example = "KRW-ADX", description = "종목상태")
		String STATE = null;

		@JsonProperty("AL_UUIDS")
		@Schema(name = "AL_UUIDS", example = "KRW-ADX", description = "AL_UUIDS")
		String AL_UUIDS = null;

		@JsonProperty("AL_IDENTIFIERS")
		@Schema(name = "AL_IDENTIFIERS", example = "KRW-ADX", description = "AL_IDENTIFIERS")
		String AL_IDENTIFIERS =null;

		
		
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_UPBIT_EXCHANGE_GET_ORDERS")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_UPBIT_EXCHANGE_GET_ORDERS", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}

	@ApiModel(value="OUT_DATA_ROW-BR_UPBIT_EXCHANGE_GET_ORDERS")
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
	SA_UPBIT_EXCHANGE_GET_ORDERS saUpbitExchangeGetOrders;

	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"UPBIT"}, value = "UPBIT 종목을 조회한다.", notes = "페이징 처리 X")
	//@PostMapping(path= "/api/BR_MIG_AV_ACTR_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		String MARKET = null;
		String STATE ="done";
		ArrayList<String> AL_STATES  =null;
		ArrayList<String> AL_UUIDS  =null;
		ArrayList<String> AL_IDENTIFIERS =null;
		OUT_DS outDs = new OUT_DS();
		if(inDS.IN_DATA!=null) {
			if(inDS.IN_DATA.size()>0) {
				MARKET  =inDS.IN_DATA.get(0).MARKET;
				STATE  =inDS.IN_DATA.get(0).STATE;
				String tmp_al_uuids   =inDS.IN_DATA.get(0).AL_UUIDS;
				if(!pjtU.isEmpty(tmp_al_uuids)){
					String[] arr_tmp = tmp_al_uuids.split(",");
					if(arr_tmp.length>0){
						AL_UUIDS = new ArrayList<String>();
					}					
				}
				String tmp_al_identifiers   =inDS.IN_DATA.get(0).AL_IDENTIFIERS;
				if(!pjtU.isEmpty(tmp_al_identifiers)){
					String[] arr_tmp = tmp_al_identifiers.split(",");
					if(arr_tmp.length>0){
						AL_IDENTIFIERS = new ArrayList<String>();
					}					
				}
			}	
		}

		ArrayList<HashMap<String, Object>> al;
			try {
				al = saUpbitExchangeGetOrders.run( MARKET, 
				STATE
				/*
					주문 상태
					- wait : 체결 대기 (default)
					- watch : 예약주문 대기
					- done : 전체 체결 완료
					- cancel : 주문 취소
				*/
				,
				null /*AL_STATES
						쓰지말자
						주문 상태의 목록
						* 미체결 주문(wait, watch)과 완료 주문(done, cancel)은 혼합하여 조회하실 수 없습니다.
				*/,
				AL_UUIDS,
				AL_IDENTIFIERS,
				"1" /*page 페이지 */,
				"100" /*limit 리미트 1~100 */,
				"desc"  /*order by 정렬  */
				
				);
			
			for(int i=0;i<al.size();i++){
				HashMap<String, Object>  tmp = al.get(i);
				OUT_DATA_ROW row = new OUT_DATA_ROW();
				row.UUID = tmp.get("uuid").toString();
				row.SIDE = tmp.get("side").toString();
				row.ORD_TYPE = tmp.get("ord_type").toString();
				row.PRICE = tmp.get("price").toString();
				row.STATE = tmp.get("state").toString();
				row.MARKET = tmp.get("market").toString();
				row.CREATED_AT = tmp.get("created_at").toString();
				row.VOLUME = tmp.get("volume").toString();
				row.REMAINING_VOLUME = tmp.get("remaining_volume").toString();
				row.RESERVED_FEE = tmp.get("reserved_fee").toString();
				row.REMAINING_FEE = tmp.get("remaining_fee").toString();
				row.PAID_FEE = tmp.get("paid_fee").toString();
				row.LOCKED = tmp.get("locked").toString();
				row.EXECUTED_VOLUME = tmp.get("executed_volume").toString();
				if(tmp.get("trade_count")!=null) {
					row.TRADE_COUNT = tmp.get("trade_count").toString();
				}				
				outDs.OUT_DATA.add(row);
			}
		} catch (NoSuchAlgorithmException | URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return outDs;
	}
}
