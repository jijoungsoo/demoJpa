package com.example.demo.br.cm.cm_pgm;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.cm.DA_CM_PGM;
import com.example.demo.db.da.cm.DA_CM_SEQ;
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
@Tag(name = "CM_PGM", description = "프로그램")
public class BR_CM_PGM_SAVE {

	@JsonRootName("IN_DS")
	@Schema(name="IN_DS",title="IN_DS-UPDT_DATA")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-UPDT_DATA", description = "입력 데이터")
		ArrayList<DATA_ROW> IN_DATA = new ArrayList<DATA_ROW>();
		
		@JsonProperty("UPDT_DATA")
		@Schema(name="UPDT_DATA-UPDT_DATA", description = "수정 데이터")
		ArrayList<DATA_ROW> UPDT_DATA = new ArrayList<DATA_ROW>();
		
		@JsonProperty("LSESSION")
		@Schema(name = "LSESSION",title="LSESSION-UPDT_DATA", description = "세션데이터")
		LSESSION_ROW LSESSION;
	}

	@Schema(name="DATA_ROW", title = "DATA_ROW-UPDT_DATA")
	@Data
	static class DATA_ROW {
		@JsonProperty("PGM_NO")
		@Schema(name = "PGM_NO", example = "1", description = "사용자NO")
		String PGM_NO = null;
		@JsonProperty("PGM_ID")
		@Schema(name = "PGM_ID", example = "1", description = "사용자NO")
		String PGM_ID = null;
		@JsonProperty("PGM_NM")
		@Schema(name = "PGM_NM", example = "1", description = "사용자NO")
		String PGM_NM = null;
		@JsonProperty("RMK")
		@Schema(name = "RMK", example = "jijs", description = "사용자ID")
		String RMK = null;
		@JsonProperty("ORD")
		@Schema(name = "ORD", example = "****", description = "사용자패스워드")
		String ORD = null;
		@JsonProperty("DIR_LINK")
		@Schema(name = "DIR_LINK", example = "****", description = "사용자패스워드")
		String DIR_LINK = null;
		@JsonProperty("PGM_LINK")
		@Schema(name = "PGM_LINK", example = "****", description = "사용자패스워드")
		String PGM_LINK = null;
		@JsonProperty("CATEGORY")
		@Schema(name = "CATEGORY", example = "****", description = "사용자패스워드")
		String CATEGORY = null;
	
	}
	
	@JsonRootName("OUT_DS")
	@Schema(name="OUT_DS",title = "OUT_DS-UPDT_DATA")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA", title="OUT_DATA-UPDT_DATA", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}
	
	@Autowired
	DA_CM_PGM daPgm;
	
	@Autowired
	DA_CM_SEQ daCmSeq;

	@Operation(summary = "프로그램 조회.", description = "")
	@PostMapping(path= "/api/BR_CM_PGM_SAVE", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.LSESSION==null) {
			throw new BizRuntimeException("세션값이 넘어오지 않았습니다1.");
		}
		String USER_NO =inDS.LSESSION.getUSER_NO();
		if(PjtUtil.isEmpty(USER_NO)) {
			throw new BizRuntimeException("사용자NO가 넘어오지 않았습니다2.");
		}
		Long L_USER_NO = Long.parseLong(USER_NO);
		
		for( int i=0;i<inDS.IN_DATA.size();i++) {
			DATA_ROW  rs =inDS.IN_DATA.get(i);
			String  PGM_ID 		= PjtUtil.str(rs.PGM_ID);
			String  PGM_NM 		= PjtUtil.str(rs.PGM_NM);
			String  RMK 		= PjtUtil.str(rs.RMK);
			String  ORD 		= PjtUtil.str(rs.ORD);
			String  DIR_LINK 	= PjtUtil.str(rs.DIR_LINK);
			String  PGM_LINK 	= PjtUtil.str(rs.PGM_LINK);			
			String  CATEGORY 	= PjtUtil.str(rs.CATEGORY);
			
			
			if(PjtUtil.isEmpty(PGM_ID)) {
				throw new BizRuntimeException("프로그램ID가 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(PGM_NM)) {
				throw new BizRuntimeException("프로그램명이 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(DIR_LINK)) {
				throw new BizRuntimeException("디렉토리링크가 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(PGM_LINK)) {
				throw new BizRuntimeException("프로그램링크가 입력되지 않았습니다.");
			}
			
			if(PjtUtil.isEmpty(ORD)) {
				throw new BizRuntimeException("정렬을 입력되지 않았습니다.");
			}
			
			long L_PGM_NO =daCmSeq.increate("CM_PGM_PGM_NO_SEQ");
			
			daPgm.createPgm(
					L_PGM_NO
					,PGM_ID
					,PGM_NM
					,RMK
					,ORD
					,CATEGORY
					,DIR_LINK
					,PGM_LINK
					);
		}
		
		for( int i=0;i<inDS.UPDT_DATA.size();i++) {
			DATA_ROW  rs =inDS.UPDT_DATA.get(i);
			String  PGM_NO 		= PjtUtil.str(rs.PGM_NO);
			String  PGM_ID 		= PjtUtil.str(rs.PGM_ID);
			String  PGM_NM 		= PjtUtil.str(rs.PGM_NM);
			String  RMK 		= PjtUtil.str(rs.RMK);
			String  ORD 		= PjtUtil.str(rs.ORD);
			String  DIR_LINK 	= PjtUtil.str(rs.DIR_LINK);
			String  PGM_LINK 	= PjtUtil.str(rs.PGM_LINK);			
			String  CATEGORY 	= PjtUtil.str(rs.CATEGORY);
			
			if(PjtUtil.isEmpty(PGM_NO)) {
				throw new BizRuntimeException("프로그램번호가 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(PGM_ID)) {
				throw new BizRuntimeException("프로그램ID가 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(PGM_NM)) {
				throw new BizRuntimeException("프로그램명이 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(DIR_LINK)) {
				throw new BizRuntimeException("디렉토리링크가 입력되지 않았습니다.");
			}
			
			if(PjtUtil.isEmpty(PGM_LINK)) {
				throw new BizRuntimeException("프로그램링크가 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(ORD)) {
				ORD="0000";
			}
			long L_PGM_NO = Long.parseLong(PGM_NO);
			
			daPgm.updatePgm(
					L_PGM_NO
					,PGM_ID
					,PGM_NM
					,RMK
					,ORD
					,CATEGORY
					,DIR_LINK
					,PGM_LINK
					);
		}

		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
}
