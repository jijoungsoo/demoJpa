package com.example.demo.br.stck;

import java.util.ArrayList;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.stck.DA_STCK_SELL;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@OpService
@Service
@Tag(name = "STCK", description = "주식")
public class BR_STCK_SELL_UPDATE {

	@JsonRootName("IN_DS")
	@Schema(name="IN_DS",title="IN_DS-BR_STCK_SELL_UPDATE")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "UPDT_DATA,LSESSION", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "", description = "출력 데이터명")
		String brRs;
		
		@JsonProperty("UPDT_DATA")
		@Schema(name="UPDT_DATA-BR_STCK_SELL_UPDATE", description = "수정 데이터")
		ArrayList<UPDT_DATA_ROW> UPDT_DATA = new ArrayList<UPDT_DATA_ROW>();
		
		@JsonProperty("LSESSION")
		@Schema(name = "LSESSION", description = "세션데이터")
		LSESSION_ROW LSESSION;
	}

	@JsonRootName("OUT_DS")
	@Schema(name = "OUT_DS-BR_STCK_SELL_UPDATE")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_STCK_SELL_UPDATE", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}

	@Schema(name = "UPDT_DATA_ROW-BR_STCK_SELL_UPDATE")
	@Data
	static class UPDT_DATA_ROW {
		@JsonProperty("SELL_SEQ")
		@Schema(name = "SELL_SEQ", example = "1", description = "프로그램NO")
		String SELL_SEQ = null;
		@JsonProperty("REF_BUY_SEQ")
		@Schema(name = "REF_BUY_SEQ", example = "1", description = "프로그램NO")
		String REF_BUY_SEQ = null;
		@JsonProperty("STOCK_CD")
		@Schema(name = "STOCK_CD", example = "1", description = "프로그램NO")
		String STOCK_CD = null;
		@JsonProperty("STOCK_NM")
		@Schema(name = "STOCK_NM", example = "CM_001", description = "프로그램ID")
		String STOCK_NM = null;
		@JsonProperty("AMT")
		@Schema(name = "AMT", example = "****", description = "DIR_LINK")
		String AMT = null;
		@JsonProperty("CNT")
		@Schema(name = "CNT", example = "PGM_LINK", description = "PGM_LINK")
		String CNT = null;
		@JsonProperty("BAL_CNT")
		@Schema(name = "BAL_CNT", example = "admin@gogo.com", description = "CATEGORY")
		String BAL_CNT = null;
		@JsonProperty("FEE")
		@Schema(name = "FEE", example = "admin@gogo.com", description = "RMK")
		String FEE = null;
		@JsonProperty("TAX")
		@Schema(name = "TAX", example = "admin@gogo.com", description = "RMK")
		String TAX = null;
		@JsonProperty("TOT_AMT")
		@Schema(name = "TOT_AMT", example = "admin@gogo.com", description = "CRT_DTM")
		String TOT_AMT = null;
		@JsonProperty("SELL_DATE")
		@Schema(name = "SELL_DATE", example = "admin@gogo.com", description = "CRT_DTM")
		String SELL_DATE = null;
	}
	
	@Autowired
	DA_STCK_SELL daStckS;
		
	@Operation(summary = "판주식 수정.", description = "")
	//@PostMapping(path= "/api/BR_STCK_SELL_UPDATE", consumes = "application/json", produces = "application/json")
	public OUT_DS run(IN_DS inDS) throws BizException {
		if(inDS.LSESSION==null) {
			throw new BizRuntimeException("세션값이 넘어오지 않았습니다1.");
		}
		String LSESSION_USER_NO =inDS.LSESSION.getUSER_NO();
		if(PjtUtil.isEmpty(LSESSION_USER_NO)) {
			throw new BizRuntimeException("사용자NO가 넘어오지 않았습니다2.");
		}
		Long L_LSESSION_USER_NO = Long.parseLong(LSESSION_USER_NO);
	
		for( int i=0;i<inDS.UPDT_DATA.size();i++) {
			UPDT_DATA_ROW  rs 		= inDS.UPDT_DATA.get(i);
			String  SELL_SEQ 		= PjtUtil.str(rs.SELL_SEQ);
			String  REF_BUY_SEQ 	= PjtUtil.str(rs.REF_BUY_SEQ);
			String  STOCK_CD 		= PjtUtil.str(rs.STOCK_CD);
			String  STOCK_NM 		= PjtUtil.str(rs.STOCK_NM);
			String  AMT			 	= PjtUtil.str(rs.AMT);
			String  CNT 			= PjtUtil.str(rs.CNT);
			String  FEE 			= PjtUtil.str(rs.FEE);
			String  TAX 			= PjtUtil.str(rs.TAX);
			String  TOT_AMT 		= PjtUtil.str(rs.TOT_AMT);
			String  SELL_DATE 		= PjtUtil.str(rs.SELL_DATE);
			
			if(PjtUtil.isEmpty(SELL_SEQ)) {
				throw new BizRuntimeException("내가 판 주식일련번호가 입력되지 않았습니다.");
			}
			
			if(PjtUtil.isEmpty(REF_BUY_SEQ)) {
				throw new BizRuntimeException("REF_BUY_SEQ 이전구매시퀀스가 입력되지 않았습니다.");
			}
			
			if(PjtUtil.isEmpty(STOCK_CD)) {
				throw new BizRuntimeException("주식코드가 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(STOCK_NM)) {
				throw new BizRuntimeException("주식명이 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(AMT)) {
				throw new BizRuntimeException("단가가 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(CNT)) {
				throw new BizRuntimeException("수량이 입력되지 않았습니다.");
			}
			
			if(PjtUtil.isEmpty(FEE)) {
				throw new BizRuntimeException("수수료가 입력되지 않았습니다.");
			}
			
			if(PjtUtil.isEmpty(TOT_AMT)) {
				throw new BizRuntimeException("총금액이 입력되지 않았습니다.");
			}
		
			long L_SELL_SEQ = Long.parseLong(SELL_SEQ);
			long L_REF_BUY_SEQ = Long.parseLong(REF_BUY_SEQ);
			
			Integer I_AMT 			= Integer.parseInt(AMT);
			Integer I_CNT 			= Integer.parseInt(CNT);
			Integer I_FEE 			= Integer.parseInt(FEE);
			Integer I_TAX 			= Integer.parseInt(TAX);
			Integer I_TOT_AMT 		= Integer.parseInt(TOT_AMT);
			
			daStckS.updateStckSell(L_SELL_SEQ
					, L_REF_BUY_SEQ
					,  STOCK_CD
					,  STOCK_NM
					,  I_AMT
					,  I_CNT
					,  I_FEE
					,  I_TAX
					,  I_TOT_AMT
					,  SELL_DATE
					,  L_LSESSION_USER_NO
					);
		}

		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
}
