package com.example.demo.br.cm.cm_cd;

import java.util.ArrayList;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.cm.DA_CM_CD;
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

@Tag(name = "CM_CD", description = "공통코드")
@Slf4j
@OpService
@Service
public class BR_CM_CD_SAVE {

	@Autowired
    PjtUtil pjtU;

	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_CM_CD_SAVE")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CM_CD_SAVE", description = "입력 데이터")
		ArrayList<DATA_ROW> IN_DATA = new ArrayList<DATA_ROW>();
		
		@JsonProperty("UPDT_DATA")
		@Schema(name="IN_DATA-BR_CM_CD_SAVE", description = "수정 데이터")
		ArrayList<DATA_ROW> UPDT_DATA = new ArrayList<DATA_ROW>();
		
		@JsonProperty("LSESSION")
		LSESSION_ROW LSESSION;
	}

	@ApiModel(value="DATA_ROW-BR_CM_CD_SAVE")
	@Data
	static class DATA_ROW {
		@JsonProperty("GRP_CD")
		@Schema(name = "GRP_CD", example = "CATEGORY_CD", description = "공통그룹코드")
		String GRP_CD = null;
		@JsonProperty("CD")
		@Schema(name = "CD", example = "(C-공통,S-주식)", description = "공통코드")
		String CD = null;
		@JsonProperty("CD_NM")
		@Schema(name = "CD_NM", example = "사용여부", description = "공통코드명")
		String CD_NM = null;
		@JsonProperty("USE_YN")
		@Schema(name = "USE_YN", example = "(Y-사용,N-미사용)", description = "사용여부")
		String USE_YN = null;
		
		@JsonProperty("ORD")
		@Schema(name = "ORD", example = "001", description = "정렬")
		String ORD = null;
		
		@JsonProperty("RMK")
		@Schema(name = "RMK", example = "비고", description = "비고")
		String RMK = null;

			
		@JsonProperty("ATTR1")
		@Schema(name = "ATTR1", example = "홍길동", description = "사용자명")
		String ATTR1 = null;

		@JsonProperty("ATTR2")
		@Schema(name = "ATTR2", example = "홍길동", description = "사용자명")
		String ATTR2 = null;

		@JsonProperty("ATTR3")
		@Schema(name = "ATTR3", example = "홍길동", description = "사용자명")
		String ATTR3 = null;

		@JsonProperty("ATTR4")
		@Schema(name = "ATTR4", example = "홍길동", description = "사용자명")
		String ATTR4 = null;
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_CD_SAVE")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA", title="OUT_DATA-UPDT_DATA", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}
	
	@Autowired
	DA_CM_CD daCmCd;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_CD"},value = "공통코드 저장한다.", notes = "")
	//@PostMapping(path= "/api/BR_CM_CD_SAVE", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.LSESSION==null) {
			throw new BizRuntimeException("세션값이 넘어오지 않았습니다1.");
		}
		String SESSION_USER_NO =inDS.LSESSION.getUSER_NO();
		if(pjtU.isEmpty(SESSION_USER_NO)) {
			throw new BizRuntimeException("사용자NO가 넘어오지 않았습니다2.");
		}
		Long L_SESSION_USER_NO = Long.parseLong(SESSION_USER_NO);
		
		for( int i=0;i<inDS.IN_DATA.size();i++) {
			DATA_ROW  rs =inDS.IN_DATA.get(i);
			String  GRP_CD 		= pjtU.str(rs.GRP_CD);
			String  CD	 		= pjtU.str(rs.CD);
			String  CD_NM 		= pjtU.str(rs.CD_NM);
			String  USE_YN 		= pjtU.str(rs.USE_YN);
			String  ORD 		= pjtU.str(rs.ORD);
			String  RMK 		= pjtU.str(rs.RMK);
			String  ATTR1 		= pjtU.str(rs.ATTR1);
			String  ATTR2 		= pjtU.str(rs.ATTR2);
			String  ATTR3 		= pjtU.str(rs.ATTR3);
			String  ATTR4 		= pjtU.str(rs.ATTR4);

			
			if(pjtU.isEmpty(GRP_CD)) {
				throw new BizRuntimeException("공통코드그룹코드가 입력되지 않았습니다.");
			}
			if(pjtU.isEmpty(CD)) {
				throw new BizRuntimeException("공통코드가 입력되지 않았습니다.");
			}
			if(pjtU.isEmpty(CD_NM)) {
				throw new BizRuntimeException("공통코드명이 입력되지 않았습니다.");
			}
			if(pjtU.isEmpty(USE_YN)) {
				throw new BizRuntimeException("사용여부가 입력되지 않았습니다.");
			}
			if(pjtU.isEmpty(ORD)) {
				throw new BizRuntimeException("정렬이 입력되지 않았습니다.");
			}
			
			
			daCmCd.createCmCd(
					GRP_CD
					,CD_NM
					,CD
					,USE_YN
					,ORD
					,RMK
					,ATTR1
					,ATTR2
					,ATTR3
					,ATTR4
					,L_SESSION_USER_NO
					);
		}
		
		for( int i=0;i<inDS.UPDT_DATA.size();i++) {
			DATA_ROW  rs =inDS.UPDT_DATA.get(i);
			String  GRP_CD 		= pjtU.str(rs.GRP_CD);
			String  CD 			= pjtU.str(rs.CD);
			String  CD_NM 		= pjtU.str(rs.CD_NM);
			String  USE_YN 		= pjtU.str(rs.USE_YN);
			String  ORD 		= pjtU.str(rs.ORD);
			String  RMK 		= pjtU.str(rs.RMK);
			String  ATTR1 		= pjtU.str(rs.ATTR1);
			String  ATTR2 		= pjtU.str(rs.ATTR2);
			String  ATTR3 		= pjtU.str(rs.ATTR3);
			String  ATTR4 		= pjtU.str(rs.ATTR4);
			
			if(pjtU.isEmpty(GRP_CD)) {
				throw new BizRuntimeException("공통코드그룹코드가 입력되지 않았습니다.");
			}
			if(pjtU.isEmpty(CD)) {
				throw new BizRuntimeException("공통코드가 입력되지 않았습니다.");
			}
			if(pjtU.isEmpty(CD_NM)) {
				throw new BizRuntimeException("공통코드명이 입력되지 않았습니다.");
			}
			if(pjtU.isEmpty(USE_YN)) {
				throw new BizRuntimeException("사용여부가 입력되지 않았습니다.");
			}
			if(pjtU.isEmpty(ORD)) {
				throw new BizRuntimeException("정렬이 입력되지 않았습니다.");
			}
			
			
			daCmCd.updateCmCd(
					GRP_CD
					,CD_NM
					,CD
					,USE_YN
					,ORD
					,RMK
					,ATTR1
					,ATTR2
					,ATTR3
					,ATTR4
					,L_SESSION_USER_NO
					);
		}

		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
	
}
