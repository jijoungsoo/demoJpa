package com.example.demo.br.coin.upbit;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.demo.anotation.OpService;
import com.example.demo.exception.BizException;
import com.example.demo.sa.upbit.api.SA_UPBIT_EXCHANGE_GET_WITHDRAWS_CHANCE;
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
public class BR_UPBIT_EXCHANGE_GET_WITHDRAWS_CHANCE {

	@Autowired
    PjtUtil pjtU;
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_UPBIT_EXCHANGE_GET_WITHDRAWS_CHANCE")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_UPBIT_EXCHANGE_GET_WITHDRAWS_CHANCE", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
	}

	@ApiModel(value="IN_DATA_ROW-BR_UPBIT_EXCHANGE_GET_WITHDRAWS_CHANCE")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("CURRENCY")
		@Schema(name = "CURRENCY", example = "KRW-ADX", description = "CURRENCY코드")
		String CURRENCY = null;
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_UPBIT_EXCHANGE_GET_WITHDRAWS_CHANCE")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA_MEMBER_LEVEL")
		@Schema(name="OUT_DATA_MEMBER_LEVEL-BR_UPBIT_EXCHANGE_GET_WITHDRAWS_CHANCE", description = "출력 데이터")
		ArrayList<OUT_DATA_MEMBER_LEVEL_ROW> OUT_DATA_MEMBER_LEVEL = new ArrayList<OUT_DATA_MEMBER_LEVEL_ROW>();

		@JsonProperty("OUT_DATA_CURRENCY")
		@Schema(name="OUT_DATA_CURRENCY-BR_UPBIT_EXCHANGE_GET_WITHDRAWS_CHANCE", description = "출력 데이터")
		ArrayList<OUT_DATA_CURRENCY_ROW> OUT_DATA_CURRENCY = new ArrayList<OUT_DATA_CURRENCY_ROW>();

		@JsonProperty("OUT_DATA_ACCOUNT")
		@Schema(name="OUT_DATA_ACCOUNT-BR_UPBIT_EXCHANGE_GET_WITHDRAWS_CHANCE", description = "출력 데이터")
		ArrayList<OUT_DATA_ACCOUNT_ROW> OUT_DATA_ACCOUNT = new ArrayList<OUT_DATA_ACCOUNT_ROW>();

		@JsonProperty("OUT_DATA_WITHDRAW_LIMIT")
		@Schema(name="OUT_DATA_WITHDRAW_LIMIT-BR_UPBIT_EXCHANGE_GET_WITHDRAWS_CHANCE", description = "출력 데이터")
		ArrayList<OUT_DATA_WITHDRAW_LIMIT_ROW> OUT_DATA_WITHDRAW_LIMIT = new ArrayList<OUT_DATA_WITHDRAW_LIMIT_ROW>();
	}

	@ApiModel(value="OUT_DATA_MEMBER_LEVEL_ROW-BR_UPBIT_EXCHANGE_GET_WITHDRAWS_CHANCE")
	@Data
	static class OUT_DATA_MEMBER_LEVEL_ROW {

		@JsonProperty("SECURITY_LEVEL")  //String
		@Schema(name = "SECURITY_LEVEL", example = "1", description = "사용자의 보안등급")
		String SECURITY_LEVEL = null;

		@JsonProperty("FEE_LEVEL")  //String
		@Schema(name = "FEE_LEVEL", example = "1", description = "사용자의 수수료등급")
		String FEE_LEVEL = null;

		@JsonProperty("EMAIL_VERIFIED")
		@Schema(name = "EMAIL_VERIFIED", example = "Boolean", description = "사용자의 이메일 인증 여부")
		String EMAIL_VERIFIED = null;

		@JsonProperty("IDENTITY_AUTH_VERIFIED")
		@Schema(name = "IDENTITY_AUTH_VERIFIED", example = "Boolean", description = "사용자의 실명 인증 여부")
		String IDENTITY_AUTH_VERIFIED = null;

		@JsonProperty("BANK_ACCOUNT_VERIFIED")
		@Schema(name = "BANK_ACCOUNT_VERIFIED", example = "Boolean", description = "사용자의 계좌 인증 여부")
		String BANK_ACCOUNT_VERIFIED = null;

		@JsonProperty("KAKAO_PAY_AUTH_VERIFIED")
		@Schema(name = "KAKAO_PAY_AUTH_VERIFIED", example = "Boolean", description = "사용자의 카카오페이 인증 여부")
		String KAKAO_PAY_AUTH_VERIFIED = null;

		@JsonProperty("LOCKED")
		@Schema(name = "LOCKED", example = "Boolean", description = "사용자의 계정 보호 상태")
		String LOCKED = null;

		@JsonProperty("WALLET_LOCKED")
		@Schema(name = "WALLET_LOCKED", example = "Boolean", description = "사용자의 출금 보호 상태")
		String WALLET_LOCKED = null;
	}

	@ApiModel(value="OUT_DATA_CURRENCY_ROW-BR_UPBIT_EXCHANGE_GET_WITHDRAW_CHANCE")
	@Data
	static class OUT_DATA_CURRENCY_ROW {
		@JsonProperty("CODE")  //String
		@Schema(name = "CODE", example = "1", description = "화폐를 의미하는 영문 대문자 코드")
		String CODE = null;

		@JsonProperty("WITHDRAW_FEE")  //String
		@Schema(name = "WITHDRAW_FEE", example = "1", description = "해당 화폐의 출금 수수료")
		String WITHDRAW_FEE = null;

		@JsonProperty("IS_COIN")
		@Schema(name = "IS_COIN", example = "Boolean", description = "화폐의 코인 여부")
		String IS_COIN = null;

		@JsonProperty("WALLET_STATE")
		@Schema(name = "WALLET_STATE", example = "string", description = "해당 화폐의 지갑 상태")
		String WALLET_STATE = null;

		@JsonProperty("WALLET_SUPPORT")
		@Schema(name = "WALLET_SUPPORT", example = "Boolean", description = "해당 화폐가 지원하는 입출금 정보")
		String WALLET_SUPPORT = null;
	}


	
	@ApiModel(value="OUT_DATA_ACCOUNT_ROW-BR_UPBIT_EXCHANGE_GET_WITHDRAWS_CHANCE")
	@Data
	static class OUT_DATA_ACCOUNT_ROW {
		@JsonProperty("CURRENCY")  //String
		@Schema(name = "CURRENCY", example = "1", description = "화폐를 의미하는 영문 대문자 코드")
		String CURRENCY = null;

		@JsonProperty("BALANCE")  //String
		@Schema(name = "BALANCE", example = "1", description = "주문가능 금액/수량")
		String BALANCE = null;

		@JsonProperty("IS_COIN")
		@Schema(name = "IS_COIN", example = "Boolean", description = "화폐의 코인 여부")
		String IS_COIN = null;

		@JsonProperty("LOCKED")
		@Schema(name = "LOCKED", example = "string", description = "주문 중 묶여있는 금액/수량")
		String LOCKED = null;

		@JsonProperty("AVG_BUY_PRICE")
		@Schema(name = "AVG_BUY_PRICE", example = "Boolean", description = "매수평균가")
		String AVG_BUY_PRICE = null;

		@JsonProperty("AVG_BUY_PRICE_MODIFIED")
		@Schema(name = "AVG_BUY_PRICE_MODIFIED", example = "Boolean", description = "매수평균가 수정 여부")
		String AVG_BUY_PRICE_MODIFIED = null;

		@JsonProperty("UNIT_CURRENCY")
		@Schema(name = "UNIT_CURRENCY", example = "Boolean", description = "평단가 기준 화폐")
		String UNIT_CURRENCY = null;
	}


	
	@ApiModel(value="OUT_DATA_WITHDRAW_LIMIT_ROW-BR_UPBIT_EXCHANGE_GET_WITHDRAWS_CHANCE")
	@Data
	static class OUT_DATA_WITHDRAW_LIMIT_ROW {
		@JsonProperty("CURRENCY")  //String
		@Schema(name = "CURRENCY", example = "1", description = "화폐를 의미하는 영문 대문자 코드")
		String CURRENCY = null;

		@JsonProperty("MINIMUM")  //String
		@Schema(name = "MINIMUM", example = "1", description = "출금 최소 금액/수량")
		String MINIMUM = null;

		@JsonProperty("ONETIME")
		@Schema(name = "ONETIME", example = "Boolean", description = "1회 출금 한도")
		String ONETIME = null;

		@JsonProperty("DAILY")
		@Schema(name = "DAILY", example = "string", description = "1일 출금 한도")
		String DAILY = null;

		@JsonProperty("REMAINING_DAILY")
		@Schema(name = "REMAINING_DAILY", example = "Boolean", description = "1일 잔여 출금 한도")
		String REMAINING_DAILY = null;

		@JsonProperty("REMAINING_DAILY_KRW")
		@Schema(name = "REMAINING_DAILY_KRW", example = "Boolean", description = "통합 1일 잔여 출금 한도")
		String REMAINING_DAILY_KRW = null;

		@JsonProperty("FIXED")
		@Schema(name = "FIXED", example = "Boolean", description = "출금 금액/수량 소수점 자리 수")
		String FIXED = null;

		
		@JsonProperty("CAN_WITHDRAW")
		@Schema(name = "CAN_WITHDRAW", example = "Boolean", description = "출금 지원 여부")
		String CAN_WITHDRAW = null;
	}
	
	@Autowired
	SA_UPBIT_EXCHANGE_GET_WITHDRAWS_CHANCE saUpbitExchangeGetWithdrawsChance;

	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"UPBIT"}, value = "UPBIT 종목을 조회한다.", notes = "페이징 처리 X")
	//@PostMapping(path= "/api/BR_MIG_AV_ACTR_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		String CURRENCY = null;
		OUT_DS outDs = new OUT_DS();
		if(inDS.IN_DATA!=null) {
			if(inDS.IN_DATA.size()>0) {
				CURRENCY  =inDS.IN_DATA.get(0).CURRENCY;
			}	
		}

		
		try {
			HashMap<String,Object>  tmp = saUpbitExchangeGetWithdrawsChance.run(CURRENCY);
			if(tmp!=null){				
				HashMap<String,Object>  member_level = (HashMap<String, Object>) tmp.get("member_level");
				HashMap<String,Object>  currency = (HashMap<String, Object>) tmp.get("currency");
				HashMap<String,Object>  account = (HashMap<String, Object>) tmp.get("account");
				HashMap<String,Object>  withdraw_limit = (HashMap<String, Object>) tmp.get("withdraw_limit");
				if(member_level!=null) {
					OUT_DATA_MEMBER_LEVEL_ROW row = new OUT_DATA_MEMBER_LEVEL_ROW();
					row.SECURITY_LEVEL =member_level.get("security_level").toString();
					row.FEE_LEVEL =member_level.get("fee_level").toString();
					row.EMAIL_VERIFIED =member_level.get("email_verified").toString();
					row.IDENTITY_AUTH_VERIFIED =member_level.get("identity_auth_verified").toString();
					row.BANK_ACCOUNT_VERIFIED =member_level.get("bank_account_verified").toString();
					row.KAKAO_PAY_AUTH_VERIFIED =member_level.get("kakao_pay_auth_verified").toString();
					row.LOCKED =member_level.get("locked").toString();
					row.WALLET_LOCKED =member_level.get("wallet_locked").toString();
					outDs.OUT_DATA_MEMBER_LEVEL.add(row);
				}

				if(currency!=null) {
					OUT_DATA_CURRENCY_ROW row = new OUT_DATA_CURRENCY_ROW();
					row.CODE =currency.get("code").toString();
					row.WITHDRAW_FEE =currency.get("withdraw_fee").toString();
					row.IS_COIN =currency.get("is_coin").toString();
					row.WALLET_STATE =currency.get("wallet_state").toString();
					row.WALLET_SUPPORT =currency.get("wallet_support").toString();
					outDs.OUT_DATA_CURRENCY.add(row);
				}

				if(account!=null) {
					OUT_DATA_ACCOUNT_ROW row = new OUT_DATA_ACCOUNT_ROW();
					row.CURRENCY =account.get("currency").toString();
					row.BALANCE =account.get("balance").toString();
					row.LOCKED =account.get("locked").toString();
					row.AVG_BUY_PRICE =account.get("avg_buy_price").toString();
					row.AVG_BUY_PRICE_MODIFIED =account.get("avg_buy_price_modified").toString();
					row.UNIT_CURRENCY =account.get("unit_currency").toString();
					outDs.OUT_DATA_ACCOUNT.add(row);
				}

				if(withdraw_limit!=null) {
					OUT_DATA_WITHDRAW_LIMIT_ROW row = new OUT_DATA_WITHDRAW_LIMIT_ROW();
					row.CURRENCY =withdraw_limit.get("currency").toString();
					row.MINIMUM =withdraw_limit.get("minimum").toString();
					if(withdraw_limit.get("onetime")!=null){
						row.ONETIME =withdraw_limit.get("onetime").toString();
					}
					if(withdraw_limit.get("daily")!=null){
						row.DAILY =withdraw_limit.get("daily").toString();
					}
					
					row.REMAINING_DAILY =withdraw_limit.get("remaining_daily").toString();
					row.REMAINING_DAILY_KRW =withdraw_limit.get("remaining_daily_krw").toString();
					row.FIXED =withdraw_limit.get("fixed").toString();
					row.CAN_WITHDRAW =withdraw_limit.get("can_withdraw").toString();
					outDs.OUT_DATA_WITHDRAW_LIMIT.add(row);
				}
			}
		} catch (NoSuchAlgorithmException | URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return outDs;
	}
}
