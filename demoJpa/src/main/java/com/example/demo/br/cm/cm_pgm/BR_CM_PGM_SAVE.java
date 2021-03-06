package com.example.demo.br.cm.cm_pgm;

import java.util.ArrayList;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.cm.DA_CM_PGM;
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

@Tag(name = "CM_PGM", description = "프로그램")
@Slf4j
@OpService
@Service
public class BR_CM_PGM_SAVE {
	@Autowired
    PjtUtil pjtU;

	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_CM_PGM_SAVE")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CM_PGM_SAVE", description = "입력 데이터")
		ArrayList<DATA_ROW> IN_DATA = new ArrayList<DATA_ROW>();
		
		@JsonProperty("UPDT_DATA")
		@Schema(name="UPDT_DATA-BR_CM_PGM_SAVE", description = "수정 데이터")
		ArrayList<DATA_ROW> UPDT_DATA = new ArrayList<DATA_ROW>();
		
		@JsonProperty("LSESSION")
		LSESSION_ROW LSESSION;
	}

	@ApiModel(value="DATA_ROW-BR_CM_PGM_SAVE")
	@Data
	static class DATA_ROW {
		@JsonProperty("PGM_NO")
		@Schema(name = "PGM_NO", example = "1", description = "프로그램NO")
		Long PGM_NO = null;
		@JsonProperty("PGM_ID")
		@Schema(name = "PGM_ID", example = "CM_001", description = "프로그램ID")
		String PGM_ID = null;
		@JsonProperty("DIR_LINK")
		@Schema(name = "DIR_LINK", example = "****", description = "DIR_LINK")
		String DIR_LINK = null;
		@JsonProperty("PGM_LINK")
		@Schema(name = "PGM_LINK", example = "PGM_LINK", description = "PGM_LINK")
		String PGM_LINK = null;
		@JsonProperty("PGM_NM")
		@Schema(name = "PGM_NM", example = "admin@gogo.com", description = "PGM_NM")
		String PGM_NM = null;
		@JsonProperty("CATEGORY")
		@Schema(name = "CATEGORY", example = "admin@gogo.com", description = "CATEGORY")
		String CATEGORY = null;
		@JsonProperty("RMK")
		@Schema(name = "RMK", example = "admin@gogo.com", description = "RMK")
		String RMK = null;
		@JsonProperty("ORD")
		@Schema(name = "ORD", example = "admin@gogo.com", description = "ORD")
		String ORD = null;
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_PGM_SAVE")
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

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_PGM"},value = "프로그램 저장.", notes = "")
	//@PostMapping(path= "/api/BR_CM_PGM_SAVE", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.LSESSION==null) {
			throw new BizRuntimeException("세션값이 넘어오지 않았습니다1.");
		}
		String USER_NO =inDS.LSESSION.getUSER_NO();
		if(pjtU.isEmpty(USER_NO)) {
			throw new BizRuntimeException("사용자NO가 넘어오지 않았습니다2.");
		}
		Long L_USER_NO = Long.parseLong(USER_NO);
		
		for( int i=0;i<inDS.IN_DATA.size();i++) {
			DATA_ROW  rs =inDS.IN_DATA.get(i);
			String  PGM_ID 		= pjtU.str(rs.PGM_ID);
			String  PGM_NM 		= pjtU.str(rs.PGM_NM);
			String  RMK 		= pjtU.str(rs.RMK);
			String  ORD 		= pjtU.str(rs.ORD);
			String  DIR_LINK 	= pjtU.str(rs.DIR_LINK);
			String  PGM_LINK 	= pjtU.str(rs.PGM_LINK);			
			String  CATEGORY 	= pjtU.str(rs.CATEGORY);
			
			
			if(pjtU.isEmpty(PGM_ID)) {
				throw new BizRuntimeException("프로그램ID가 입력되지 않았습니다.");
			}
			if(pjtU.isEmpty(PGM_NM)) {
				throw new BizRuntimeException("프로그램명이 입력되지 않았습니다.");
			}
			if(pjtU.isEmpty(DIR_LINK)) {
				throw new BizRuntimeException("디렉토리링크가 입력되지 않았습니다.");
			}
			if(pjtU.isEmpty(PGM_LINK)) {
				throw new BizRuntimeException("프로그램링크가 입력되지 않았습니다.");
			}
			
			if(pjtU.isEmpty(ORD)) {
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
			String  PGM_NO 		= pjtU.str(rs.PGM_NO);
			String  PGM_ID 		= pjtU.str(rs.PGM_ID);
			String  PGM_NM 		= pjtU.str(rs.PGM_NM);
			String  RMK 		= pjtU.str(rs.RMK);
			String  ORD 		= pjtU.str(rs.ORD);
			String  DIR_LINK 	= pjtU.str(rs.DIR_LINK);
			String  PGM_LINK 	= pjtU.str(rs.PGM_LINK);			
			String  CATEGORY 	= pjtU.str(rs.CATEGORY);
			
			if(pjtU.isEmpty(PGM_NO)) {
				throw new BizRuntimeException("프로그램번호가 입력되지 않았습니다.");
			}
			if(pjtU.isEmpty(PGM_ID)) {
				throw new BizRuntimeException("프로그램ID가 입력되지 않았습니다.");
			}
			if(pjtU.isEmpty(PGM_NM)) {
				throw new BizRuntimeException("프로그램명이 입력되지 않았습니다.");
			}
			if(pjtU.isEmpty(DIR_LINK)) {
				throw new BizRuntimeException("디렉토리링크가 입력되지 않았습니다.");
			}
			
			if(pjtU.isEmpty(PGM_LINK)) {
				throw new BizRuntimeException("프로그램링크가 입력되지 않았습니다.");
			}
			if(pjtU.isEmpty(ORD)) {
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
