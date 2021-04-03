package com.example.demo.br.cm.cm_menu;

import java.util.ArrayList;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.cm.DA_CM_MENU;
import com.example.demo.db.da.cm.DA_CM_SEQ;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
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

@Tag(name = "CM_MENU", description = "메뉴")
@Slf4j
@OpService
@Service
public class BR_CM_MENU_SAVE {

	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_CM_MENU_SAVE")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CM_MENU_SAVE", description = "입력 데이터")
		ArrayList<DATA_ROW> IN_DATA = new ArrayList<DATA_ROW>();
		
		@JsonProperty("UPDT_DATA")
		@Schema(name="IN_DATA-BR_CM_MENU_SAVE", description = "수정 데이터")
		ArrayList<DATA_ROW> UPDT_DATA = new ArrayList<DATA_ROW>();
		
		@JsonProperty("LSESSION")
		LSESSION_ROW LSESSION;
	}

	@ApiModel(value="DATA_ROW-BR_CM_MENU_SAVE")
	@Data
	static class DATA_ROW {
		@JsonProperty("MENU_NO")
		@Schema(name = "MENU_NO", example = "1", description = "메뉴번호")
		Long MENU_NO = null;
		@JsonProperty("MENU_CD")
		@Schema(name = "MENU_CD", example = "MN_0100", description = "메뉴코드")
		String MENU_CD = null;
		@JsonProperty("MENU_NM")
		@Schema(name = "MENU_NM", example = "공통", description = "메뉴명")
		String MENU_NM = null;
		@JsonProperty("PRNT_MENU_CD")
		@Schema(name = "PRNT_MENU_CD", example = "PRNT_MENU_CD", description = "상위메뉴코드")
		String PRNT_MENU_CD = null;
		@JsonProperty("ORD")
		@Schema(name = "ORD", example = "001", description = "정렬")
		String ORD = null;
		@JsonProperty("PGM_ID")
		@Schema(name = "PGM_ID", example = "CM_01000", description = "프로그램ID")
		String PGM_ID = null;
		@JsonProperty("MENU_LVL")
		@Schema(name = "MENU_LVL", example = "1", description = "메뉴레벨")
		Integer MENU_LVL = null;
		@JsonProperty("MENU_KIND")
		@Schema(name = "MENU_KIND", example = "S", description = "메뉴종료")
		String MENU_KIND = null;
		@JsonProperty("MENU_PATH")
		@Schema(name = "MENU_PATH", example = "????", description = "메뉴경로")
		String MENU_PATH = null;
		@JsonProperty("RMK")
		@Schema(name = "RMK", example = "비고", description = "비고")
		String RMK = null;
	}

	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_MENU_SAVE")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA", title="OUT_DATA-BR_CM_MENU_SAVE", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}
	
	@Autowired
	DA_CM_MENU daMenu;
	
	@Autowired
	DA_CM_SEQ daCmSeq;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_MENU"},value = "메뉴 저장.", notes = "")
	//@PostMapping(path= "/api/BR_CM_MENU_SAVE", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.LSESSION==null) {
			throw new BizRuntimeException("세션값이 넘어오지 않았습니다1.");
		}
		String SESSION_USER_NO =inDS.LSESSION.getUSER_NO();
		if(PjtUtil.isEmpty(SESSION_USER_NO)) {
			throw new BizRuntimeException("사용자NO가 넘어오지 않았습니다2.");
		}
		Long L_SESSION_USER_NO = Long.parseLong(SESSION_USER_NO);
		
		System.out.println(inDS);
		for( int i=0;i<inDS.IN_DATA.size();i++) {
			DATA_ROW  rs =inDS.IN_DATA.get(i);
			String  MENU_CD 		= PjtUtil.str(rs.MENU_CD);
			String  MENU_NM 		= PjtUtil.str(rs.MENU_NM);
			String  PRNT_MENU_CD 	= PjtUtil.str(rs.PRNT_MENU_CD);
			String  ORD 			= PjtUtil.str(rs.ORD);
			String  PGM_ID 			= PjtUtil.str(rs.PGM_ID);
			String  MENU_LVL 		= PjtUtil.str(rs.MENU_LVL);
			String  MENU_KIND 		= PjtUtil.str(rs.MENU_KIND);
			String  MENU_PATH 		= PjtUtil.str(rs.MENU_PATH);
			String  RMK 			= PjtUtil.str(rs.RMK);
			
			if(PjtUtil.isEmpty(MENU_CD)) {
				throw new BizRuntimeException("메뉴코드가 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(MENU_NM)) {
				throw new BizRuntimeException("메뉴명이 입력되지 않았습니다.");
			}

			if(PjtUtil.isEmpty(ORD)) {
				throw new BizRuntimeException("정렬이 입력되지 않았습니다.");
			}
			
			long L_MENU_NO =daCmSeq.increate("CM_MENU_MENU_NO_SEQ");
						
			daMenu.createMenu(
					L_MENU_NO
					,MENU_CD
					,MENU_NM
					,PRNT_MENU_CD
					,ORD
					,PGM_ID
					,MENU_KIND
					,MENU_LVL
					,MENU_PATH
					,RMK
					,L_SESSION_USER_NO
					);
		}
		
		for( int i=0;i<inDS.UPDT_DATA.size();i++) {
			DATA_ROW  rs =inDS.UPDT_DATA.get(i);
			String  MENU_NO 		= PjtUtil.str(rs.MENU_NO);
			String  MENU_CD 		= PjtUtil.str(rs.MENU_CD);
			String  MENU_NM 		= PjtUtil.str(rs.MENU_NM);
			String  PRNT_MENU_CD 	= PjtUtil.str(rs.PRNT_MENU_CD);
			String  ORD 			= PjtUtil.str(rs.ORD);
			String  PGM_ID 			= PjtUtil.str(rs.PGM_ID);
			String  MENU_LVL 		= PjtUtil.str(rs.MENU_LVL);
			String  MENU_KIND 		= PjtUtil.str(rs.MENU_KIND);
			String  MENU_PATH 		= PjtUtil.str(rs.MENU_PATH);
			String  RMK 			= PjtUtil.str(rs.RMK);
			
			if(PjtUtil.isEmpty(MENU_NO)) {
				throw new BizRuntimeException("메뉴번호가 입력되지 않았습니다.");
			}
			
			if(PjtUtil.isEmpty(MENU_CD)) {
				throw new BizRuntimeException("메뉴코드가 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(MENU_NM)) {
				throw new BizRuntimeException("메뉴명이 입력되지 않았습니다.");
			}
			
			if(PjtUtil.isEmpty(ORD)) {
				throw new BizRuntimeException("정렬이 입력되지 않았습니다.");
			}
			long L_MENU_NO = Long.parseLong(MENU_NO);
			daMenu.updateMenu(
					L_MENU_NO
					,MENU_CD
					,MENU_NM
					,PRNT_MENU_CD
					,ORD
					,PGM_ID
					,MENU_KIND
					,MENU_LVL
					,MENU_PATH
					,RMK
					,L_SESSION_USER_NO
					);
		}
		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
}
