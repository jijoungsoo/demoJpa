package com.example.demo.br.cm.cm_cd;

import java.util.ArrayList;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.cm.DA_CM_GRP_CD;
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
public class BR_CM_GRP_CD_SAVE {

	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_CM_GRP_CD_SAVE")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CM_GRP_CD_SAVE", description = "입력 데이터")
		ArrayList<DATA_ROW> IN_DATA = new ArrayList<DATA_ROW>();
		
		@JsonProperty("UPDT_DATA")
		@Schema(name="UPDT_DATA-BR_CM_GRP_CD_SAVE", description = "수정 데이터")
		ArrayList<DATA_ROW> UPDT_DATA = new ArrayList<DATA_ROW>();
		
		@JsonProperty("LSESSION")
		LSESSION_ROW LSESSION;
	}

	@ApiModel(value="DATA_ROW-BR_CM_GRP_CD_SAVE")
	@Data
	static class DATA_ROW {
		@JsonProperty("GRP_CD")
		@Schema(name = "GRP_CD", example = "1", description = "사용자NO")
		String GRP_CD = null;
		@JsonProperty("GRP_NM")
		@Schema(name = "GRP_NM", example = "jijs", description = "사용자ID")
		String GRP_NM = null;
		@JsonProperty("CD_NM")
		@Schema(name = "CD_NM", example = "****", description = "사용자패스워드")
		String CD_NM = null;
		@JsonProperty("USE_YN")
		@Schema(name = "USE_YN", example = "홍길동", description = "사용자명")
		String USE_YN = null;
		
		@JsonProperty("ORD")
		@Schema(name = "ORD", example = "홍길동", description = "사용자명")
		String ORD = null;
		
		@JsonProperty("RMK")
		@Schema(name = "RMK", example = "홍길동", description = "사용자명")
		String RMK = null;
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_GRP_CD_SAVE")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA", title="OUT_DATA-UPDT_DATA", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}
	
	@Autowired
	DA_CM_GRP_CD daGrpCd;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_CD"},value = "공통그룹코드 저장한다.", notes = "")
	//@PostMapping(path= "/api/BR_CM_GRP_CD_SAVE", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.LSESSION==null) {
			throw new BizRuntimeException("세션값이 넘어오지 않았습니다1.");
		}
		String SESSION_USER_NO =inDS.LSESSION.getUSER_NO();
		if(PjtUtil.isEmpty(SESSION_USER_NO)) {
			throw new BizRuntimeException("사용자NO가 넘어오지 않았습니다2.");
		}
		Long L_SESSION_USER_NO = Long.parseLong(SESSION_USER_NO);
		
		for( int i=0;i<inDS.IN_DATA.size();i++) {
			DATA_ROW  rs =inDS.IN_DATA.get(i);
			String  GRP_CD 		= PjtUtil.str(rs.GRP_CD);
			String  GRP_NM 		= PjtUtil.str(rs.GRP_NM);
			String  USE_YN 		= PjtUtil.str(rs.USE_YN);
			String  ORD 		= PjtUtil.str(rs.ORD);
			String  RMK 		= PjtUtil.str(rs.RMK);
			
			if(PjtUtil.isEmpty(GRP_CD)) {
				throw new BizRuntimeException("공통코드그룹코드가 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(GRP_NM)) {
				throw new BizRuntimeException("공통코드그룹명이 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(USE_YN)) {
				throw new BizRuntimeException("사용여부가 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(ORD)) {
				throw new BizRuntimeException("정렬이 입력되지 않았습니다.");
			}
			
			
			daGrpCd.createCmGrpCd(
					GRP_CD
					,GRP_NM
					,USE_YN
					,ORD
					,RMK
					,L_SESSION_USER_NO
					);
		}
		
		for( int i=0;i<inDS.UPDT_DATA.size();i++) {
			DATA_ROW  rs =inDS.UPDT_DATA.get(i);
			String  GRP_CD 		= PjtUtil.str(rs.GRP_CD);
			String  GRP_NM 		= PjtUtil.str(rs.GRP_NM);
			String  USE_YN 		= PjtUtil.str(rs.USE_YN);
			String  ORD 		= PjtUtil.str(rs.ORD);
			String  RMK 		= PjtUtil.str(rs.RMK);
			
			if(PjtUtil.isEmpty(GRP_CD)) {
				throw new BizRuntimeException("공통코드그룹코드가 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(GRP_NM)) {
				throw new BizRuntimeException("공통코드그룹명이 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(USE_YN)) {
				throw new BizRuntimeException("사용여부가 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(ORD)) {
				throw new BizRuntimeException("정렬이 입력되지 않았습니다.");
			}
			
			
			daGrpCd.updateCmGrpCd(
					GRP_CD
					,GRP_NM
					,USE_YN
					,ORD
					,RMK
					,L_SESSION_USER_NO
					);
		}

		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
	
}
