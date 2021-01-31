package com.example.demo.br.stck;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.cm.DA_CM_SEQ;
import com.example.demo.db.da.stck.DA_STCK_BUY;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@Tag(name = "STCK", description = "주식")
public class BR_STCK_BUY_SAVE {

	@JsonRootName("IN_DS")
	@Schema(name="IN_DS",title="IN_DS-BR_STCK_BUY_SAVE")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA,UPDT_DATA,LSESSION", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "", description = "출력 데이터명")
		String brRs;
		
		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_STCK_BUY_SAVE", description = "입력 데이터")
		ArrayList<DATA_ROW> IN_DATA = new ArrayList<DATA_ROW>();
		
		@JsonProperty("UPDT_DATA")
		@Schema(name="IN_DATA-BR_STCK_BUY_SAVE", description = "수정 데이터")
		ArrayList<DATA_ROW> UPDT_DATA = new ArrayList<DATA_ROW>();
		
		@JsonProperty("LSESSION")
		@Schema(name = "LSESSION", description = "세션데이터")
		LSESSION_ROW LSESSION;
	}

	@JsonRootName("OUT_DS")
	@Schema(name="OUT_DS",title = "OUT_DS-BR_STCK_BUY_SAVE")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA", title="OUT_DATA-BR_STCK_BUY_SAVE", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}

	@Schema(name = "DATA_ROW-BR_STCK_BUY_SAVE")
	@Data
	static class DATA_ROW {
		@JsonProperty("BUY_SEQ")
		@Schema(name = "BUY_SEQ", example = "1", description = "프로그램NO")
		String BUY_SEQ = null;
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
		@JsonProperty("TOT_AMT")
		@Schema(name = "TOT_AMT", example = "admin@gogo.com", description = "CRT_DTM")
		String TOT_AMT = null;
		@JsonProperty("BUY_DATE")
		@Schema(name = "BUY_DATE", example = "admin@gogo.com", description = "CRT_DTM")
		String BUY_DATE = null;
	}
	
	@Autowired
	DA_STCK_BUY daStckB;
	
	@Autowired
	DA_CM_SEQ daCmSeq;

	@Operation(summary = "산주식 저장.", description = "")
	@PostMapping(path= "/api/BR_STCK_BUY_SAVE", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.LSESSION==null) {
			throw new BizRuntimeException("세션값이 넘어오지 않았습니다1.");
		}
		String LSESSION_USER_NO =inDS.LSESSION.getUSER_NO();
		if(PjtUtil.isEmpty(LSESSION_USER_NO)) {
			throw new BizRuntimeException("사용자NO가 넘어오지 않았습니다2.");
		}
		Long L_LSESSION_USER_NO = Long.parseLong(LSESSION_USER_NO);
		
		for( int i=0;i<inDS.IN_DATA.size();i++) {
			DATA_ROW  rs =inDS.IN_DATA.get(i);
			String  STOCK_CD 		= PjtUtil.str(rs.STOCK_CD);
			String  STOCK_NM 		= PjtUtil.str(rs.STOCK_NM);
			String  AMT			 	= PjtUtil.str(rs.AMT);
			String  CNT 			= PjtUtil.str(rs.CNT);
			String  BAL_CNT			= PjtUtil.str(rs.BAL_CNT);
			String  FEE 			= PjtUtil.str(rs.FEE);
			String  TOT_AMT 		= PjtUtil.str(rs.TOT_AMT);
			String  BUY_DATE 		= PjtUtil.str(rs.BUY_DATE);
			
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
			if(PjtUtil.isEmpty(BAL_CNT)) {
				throw new BizRuntimeException("잔고 수량이 입력되지 않았습니다.");
			}
			
			
			if(PjtUtil.isEmpty(FEE)) {
				throw new BizRuntimeException("수수료가 입력되지 않았습니다.");
			}
			
			if(PjtUtil.isEmpty(TOT_AMT)) {
				throw new BizRuntimeException("총금액이 입력되지 않았습니다.");
			}
			
			long L_BUY_SEQ =daCmSeq.increate("ST_BUY_BUY_SEQ");
			
			Integer I_AMT 			= Integer.parseInt(AMT);
			Integer I_CNT 			= Integer.parseInt(CNT);
			Integer I_BAL_CNT		= Integer.parseInt(BAL_CNT);
			Integer I_FEE 			= Integer.parseInt(FEE);
			Integer I_TOT_AMT 		= Integer.parseInt(TOT_AMT);
						
			daStckB.createStckBuy(
					L_BUY_SEQ
					,STOCK_CD
					,STOCK_NM
					,I_AMT
					,I_CNT
					,I_BAL_CNT
					,I_FEE
					,I_TOT_AMT
					,BUY_DATE
					,L_LSESSION_USER_NO
					,L_LSESSION_USER_NO
					);
		}
		
		for( int i=0;i<inDS.UPDT_DATA.size();i++) {
			DATA_ROW  rs =inDS.UPDT_DATA.get(i);
			String  BUY_SEQ 		= PjtUtil.str(rs.BUY_SEQ);
			String  STOCK_CD 		= PjtUtil.str(rs.STOCK_CD);
			String  STOCK_NM 		= PjtUtil.str(rs.STOCK_NM);
			String  AMT			 	= PjtUtil.str(rs.AMT);
			String  CNT 			= PjtUtil.str(rs.CNT);
			String  BAL_CNT			= PjtUtil.str(rs.BAL_CNT);
			String  FEE 			= PjtUtil.str(rs.FEE);
			String  TOT_AMT 		= PjtUtil.str(rs.TOT_AMT);
			String  BUY_DATE 		= PjtUtil.str(rs.BUY_DATE);
			
			if(PjtUtil.isEmpty(BUY_SEQ)) {
				throw new BizRuntimeException("내가산주식일련번호가 입력되지 않았습니다.");
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
			
			if(PjtUtil.isEmpty(BAL_CNT)) {
				throw new BizRuntimeException("잔고 수량이 입력되지 않았습니다.");
			}
			
			if(PjtUtil.isEmpty(FEE)) {
				throw new BizRuntimeException("수수료가 입력되지 않았습니다.");
			}
			
			if(PjtUtil.isEmpty(TOT_AMT)) {
				throw new BizRuntimeException("총금액이 입력되지 않았습니다.");
			}
		
			long L_BUY_SEQ = Long.parseLong(BUY_SEQ);
			
			Integer I_AMT 			= Integer.parseInt(AMT);
			Integer I_CNT 			= Integer.parseInt(CNT);
			Integer I_BAL_CNT 		= Integer.parseInt(BAL_CNT);
			Integer I_FEE 			= Integer.parseInt(FEE);
			Integer I_TOT_AMT 		= Integer.parseInt(TOT_AMT);
			
			daStckB.updateStckBuy(L_BUY_SEQ
					,  STOCK_CD
					,  STOCK_NM
					,  I_AMT
					,  I_CNT
					,  I_BAL_CNT
					,  I_FEE
					,  I_TOT_AMT
					,  BUY_DATE
					,  L_LSESSION_USER_NO
					);
		}

		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
}
