package com.example.demo.br.coin.upbit;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.demo.anotation.OpService;
import com.example.demo.exception.BizException;
import com.example.demo.sa.binance.api.SA_BINANCE_GET_SAPI_V1_CAPITAL_CONFIG_GETALL;
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
public class BR_BINANCE_GET_SAPI_V1_CAPITAL_CONFIG_GETALL {

	@Autowired
    PjtUtil pjtU;
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_BINANCE_GET_SAPI_V1_CAPITAL_CONFIG_GETALL")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_BINANCE_GET_SAPI_V1_CAPITAL_CONFIG_GETALL", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
	}

	@ApiModel(value="IN_DATA_ROW-BR_BINANCE_GET_SAPI_V1_CAPITAL_CONFIG_GETALL")
	@Data
	static class IN_DATA_ROW {
		
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_BINANCE_GET_SAPI_V1_CAPITAL_CONFIG_GETALL")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_BINANCE_GET_SAPI_V1_CAPITAL_CONFIG_GETALL", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}

	@ApiModel(value="OUT_DATA_ROW-BR_BINANCE_GET_SAPI_V1_CAPITAL_CONFIG_GETALL")
	@Data
	static class OUT_DATA_ROW {

		@JsonProperty("COIN")  //String
		@Schema(name = "COIN", example = "1", description = "COIN")
		String COIN = null;

		@JsonProperty("DEPOSIT_ALL_ENABLE")  //String
		@Schema(name = "DEPOSIT_ALL_ENABLE", example = "1", description = "DEPOSIT_ALL_ENABLE")
		String DEPOSIT_ALL_ENABLE = null;

		@JsonProperty("FREE")  //String
		@Schema(name = "FREE", example = "1", description = "FREE")
		String FREE = null;

		@JsonProperty("FREEZE")  //String
		@Schema(name = "FREEZE", example = "1", description = "FREEZE")
		String FREEZE = null;

		@JsonProperty("IPOABLE")  //String
		@Schema(name = "IPOABLE", example = "1", description = "IPOABLE")
		String IPOABLE = null;

		@JsonProperty("IPOING")  //String
		@Schema(name = "IPOING", example = "1", description = "IPOING")
		String IPOING = null;

		@JsonProperty("IS_LEGAL_MONEY")  //String
		@Schema(name = "IS_LEGAL_MONEY", example = "1", description = "IS_LEGAL_MONEY")
		String IS_LEGAL_MONEY = null;

		@JsonProperty("LOCKED")  //String
		@Schema(name = "LOCKED", example = "1", description = "LOCKED")
		String LOCKED = null;

		@JsonProperty("NAME")  //String
		@Schema(name = "NAME", example = "1", description = "NAME")
		String NAME = null;

		@JsonProperty("STORAGE")  //String
		@Schema(name = "STORAGE", example = "1", description = "STORAGE")
		String STORAGE = null;

		@JsonProperty("TRADING")  //String
		@Schema(name = "TRADING", example = "1", description = "TRADING")
		String TRADING = null;

		
		@JsonProperty("WITHDRAW_ALL_ENABLE")  //String
		@Schema(name = "WITHDRAW_ALL_ENABLE", example = "1", description = "WITHDRAW_ALL_ENABLE")
		String WITHDRAW_ALL_ENABLE = null;

		@JsonProperty("WITHDRAWING")  //String
		@Schema(name = "WITHDRAWING", example = "1", description = "WITHDRAWING")
		String WITHDRAWING = null;

		ArrayList<OUT_DATA_SUB_ROW> NETWORK_LIST = new ArrayList<OUT_DATA_SUB_ROW>();
	}

	@ApiModel(value="OUT_DATA2_ROW-BR_BINANCE_GET_SAPI_V1_CAPITAL_CONFIG_GETALL")
	@Data
	static class OUT_DATA_SUB_ROW {

		@JsonProperty("ADDRESS_REGEX")  //String
		@Schema(name = "ADDRESS_REGEX", example = "1", description = "ADDRESS_REGEX")
		String ADDRESS_REGEX = null;

		@JsonProperty("COIN")  //String
		@Schema(name = "COIN", example = "1", description = "COIN")
		String COIN = null;

		@JsonProperty("DEPOSIT_DESC")  //String
		@Schema(name = "DEPOSIT_DESC", example = "1", description = "DEPOSIT_DESC")
		String DEPOSIT_DESC = null;

		@JsonProperty("DEPOSIT_ENABLE")  //String
		@Schema(name = "DEPOSIT_ENABLE", example = "1", description = "DEPOSIT_ENABLE")
		String DEPOSIT_ENABLE = null;

		@JsonProperty("IS_DEFAULT")  //String
		@Schema(name = "IS_DEFAULT", example = "1", description = "IS_DEFAULT")
		String IS_DEFAULT = null;

		@JsonProperty("MEMO_REGEX")  //String
		@Schema(name = "MEMO_REGEX", example = "1", description = "MEMO_REGEX")
		String MEMO_REGEX = null;

		@JsonProperty("MIN_CONFIRM")  //String
		@Schema(name = "MIN_CONFIRM", example = "1", description = "MIN_CONFIRM")
		String MIN_CONFIRM = null;

		@JsonProperty("NAME")  //String
		@Schema(name = "NAME", example = "1", description = "NAME")
		String NAME = null;

		@JsonProperty("NETWORK")  //String
		@Schema(name = "NETWORK", example = "1", description = "NETWORK")
		String NETWORK = null;

		@JsonProperty("RESET_ADDRESS_STATUS")  //String
		@Schema(name = "RESET_ADDRESS_STATUS", example = "1", description = "RESET_ADDRESS_STATUS")
		String RESET_ADDRESS_STATUS = null;

		@JsonProperty("SPECIAL_TIPS")  //String
		@Schema(name = "SPECIAL_TIPS", example = "1", description = "SPECIAL_TIPS")
		String SPECIAL_TIPS = null;

		
		@JsonProperty("UNLOCK_CONFIRM")  //String
		@Schema(name = "UNLOCK_CONFIRM", example = "1", description = "UNLOCK_CONFIRM")
		String UNLOCK_CONFIRM = null;


		@JsonProperty("INSERT_TIME")  //String
		@Schema(name = "INSERT_TIME", example = "1", description = "INSERT_TIME")
		String INSERT_TIME = null;

		@JsonProperty("UPDATE_TIME")  //String
		@Schema(name = "UPDATE_TIME", example = "1", description = "UPDATE_TIME")
		String UPDATE_TIME = null;

		@JsonProperty("WITHDRAW_DESC")  //String
		@Schema(name = "WITHDRAW_DESC", example = "1", description = "WITHDRAW_DESC")
		String WITHDRAW_DESC = null;

		@JsonProperty("WITHDRAW_ENABLE")  //String
		@Schema(name = "WITHDRAW_ENABLE", example = "1", description = "WITHDRAW_ENABLE")
		String WITHDRAW_ENABLE = null;

		@JsonProperty("WITHDRAW_FEE")  //String
		@Schema(name = "WITHDRAW_FEE", example = "1", description = "WITHDRAW_FEE")
		String WITHDRAW_FEE = null;

		@JsonProperty("WITHDRAW_INTEGER_MULTIPLE")  //String
		@Schema(name = "WITHDRAW_INTEGER_MULTIPLE", example = "1", description = "WITHDRAW_INTEGER_MULTIPLE")
		String WITHDRAW_INTEGER_MULTIPLE = null;

		
		@JsonProperty("WITHDRAW_MIN")  //String
		@Schema(name = "WITHDRAW_MIN", example = "1", description = "WITHDRAW_MIN")
		String WITHDRAW_MIN = null;
	}
	
	@Autowired
	SA_BINANCE_GET_SAPI_V1_CAPITAL_CONFIG_GETALL saBinanceGetSApiV1CapitalConfigGetAll;

	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"UPBIT"}, value = "UPBIT 종목을 조회한다.", notes = "페이징 처리 X")
	//@PostMapping(path= "/api/BR_MIG_AV_ACTR_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {

		OUT_DS outDs = new OUT_DS();


		ArrayList<HashMap<String, Object>> al;
			try {
				al = saBinanceGetSApiV1CapitalConfigGetAll.run();
			
			for(int i=0;i<al.size();i++){
				HashMap<String, Object>  tmp = al.get(i);
				OUT_DATA_ROW row = new OUT_DATA_ROW();

				row.COIN = tmp.get("coin").toString();
				row.DEPOSIT_ALL_ENABLE = tmp.get("depositAllEnable").toString();
				row.FREE = tmp.get("free").toString();
				row.FREEZE = tmp.get("freeze").toString();
				row.IPOABLE = tmp.get("ipoable").toString();
				row.IPOING = tmp.get("ipoing").toString();
				row.IS_LEGAL_MONEY = tmp.get("isLegalMoney").toString();
				row.LOCKED = tmp.get("locked").toString();
				row.NAME = tmp.get("name").toString();
				row.STORAGE = tmp.get("storage").toString();
				row.TRADING = tmp.get("trading").toString();
				row.WITHDRAW_ALL_ENABLE = tmp.get("withdrawAllEnable").toString();
				row.WITHDRAWING = tmp.get("withdrawing").toString();
				if(tmp.get("networkList") !=null){
					ArrayList<HashMap<String, Object>> al_sub = (ArrayList<HashMap<String, Object>>)tmp.get("networkList");
					for(int j=0;j<al_sub.size();j++){
						HashMap<String, Object>  tmp_sub = al_sub.get(j);
						OUT_DATA_SUB_ROW sub_row = new OUT_DATA_SUB_ROW();
						sub_row.ADDRESS_REGEX =  tmp_sub.get("addressRegex").toString();
						sub_row.COIN =  tmp_sub.get("coin").toString();
						if(tmp_sub.get("depositDesc")!=null){
							sub_row.DEPOSIT_DESC =  tmp_sub.get("depositDesc").toString();
						}
						sub_row.DEPOSIT_ENABLE =  tmp_sub.get("depositEnable").toString();
						sub_row.IS_DEFAULT =  tmp_sub.get("isDefault").toString();
						sub_row.MEMO_REGEX =  tmp_sub.get("memoRegex").toString();
						sub_row.MIN_CONFIRM =  tmp_sub.get("minConfirm").toString();
						sub_row.NAME =  tmp_sub.get("name").toString();
						sub_row.NETWORK =  tmp_sub.get("network").toString();
						sub_row.RESET_ADDRESS_STATUS =  tmp_sub.get("resetAddressStatus").toString();
						if(tmp_sub.get("specialTips")!=null){
							sub_row.SPECIAL_TIPS =  tmp_sub.get("specialTips").toString();
						}
						
						sub_row.UNLOCK_CONFIRM =  tmp_sub.get("unLockConfirm").toString();
						
						if(tmp_sub.get("insertTime")!=null){
							sub_row.INSERT_TIME =  tmp_sub.get("insertTime").toString();
						}
						if(tmp_sub.get("updateTime")!=null){
							sub_row.UPDATE_TIME =  tmp_sub.get("updateTime").toString();
						}
						if(tmp_sub.get("withdrawDesc")!=null){
							sub_row.WITHDRAW_DESC =  tmp_sub.get("withdrawDesc").toString();
						}
						sub_row.WITHDRAW_ENABLE =  tmp_sub.get("withdrawEnable").toString();
						sub_row.WITHDRAW_FEE =  tmp_sub.get("withdrawFee").toString();
						sub_row.WITHDRAW_INTEGER_MULTIPLE =  tmp_sub.get("withdrawIntegerMultiple").toString();
						sub_row.WITHDRAW_MIN =  tmp_sub.get("withdrawMin").toString();
						row.NETWORK_LIST.add(sub_row);
					}
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
