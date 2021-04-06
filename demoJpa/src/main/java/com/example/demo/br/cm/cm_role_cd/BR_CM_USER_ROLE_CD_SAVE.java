package com.example.demo.br.cm.cm_role_cd;

import java.util.ArrayList;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.cm.DA_CM_USER_ROLE_CD;
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

@Tag(name = "CM_ROLE_CD", description = "역할코드")
@Slf4j
@OpService
@Service
public class BR_CM_USER_ROLE_CD_SAVE {
	@Autowired
    PjtUtil pjtU;

	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_CM_USER_ROLE_CD_SAVE")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CM_USER_ROLE_CD_SAVE", description = "입력 데이터")
		ArrayList<DATA_ROW> IN_DATA = new ArrayList<DATA_ROW>();
		
		@JsonProperty("UPDT_DATA")
		@Schema(name="IN_DATA-BR_CM_USER_ROLE_CD_SAVE", description = "수정 데이터")
		ArrayList<DATA_ROW> UPDT_DATA = new ArrayList<DATA_ROW>();
		
		@JsonProperty("LSESSION")
		LSESSION_ROW LSESSION;
	}

	@ApiModel(value="DATA_ROW-BR_CM_USER_ROLE_CD_SAVE")
	@Data
	static class DATA_ROW {
		@JsonProperty("ROLE_CD")
		@Schema(name = "ROLE_CD", example = "ADMIN", description = "역할코드")
		String ROLE_CD = null;
		
		@JsonProperty("USER_NO")
		@Schema(name = "USER_NO", example = "1", description = "사용자NO")
		String USER_NO = null;

			
		@JsonProperty("USE_YN")
		@Schema(name = "USE_YN", example = "(Y-사용,N-미사용)", description = "사용여부")
		String USE_YN = null;
	
		@JsonProperty("ORD")
		@Schema(name = "ORD", example = "001", description = "정렬")
		String ORD = null;
		
		@JsonProperty("RMK")
		@Schema(name = "RMK", example = "비고", description = "비고")
		String RMK = null;
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_USER_ROLE_CD_SAVE")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA", title="OUT_DATA-BR_CM_USER_ROLE_CD_SAVE", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}
	
	@Autowired
	DA_CM_USER_ROLE_CD daCmUserRoleCd;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_ROLE_CD"},value = "역할-사용자맵핑을 저장한다.", notes = "")
	//@PostMapping(path= "/api/BR_CM_USER_ROLE_CD_SAVE", consumes = "application/json", produces = "application/json")
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
			String  ROLE_CD 	= pjtU.str(rs.ROLE_CD);
			String  USER_NO 	= pjtU.str(rs.USER_NO);
			String  USE_YN 		= pjtU.str(rs.USE_YN);
			String  ORD 		= pjtU.str(rs.ORD);
			String  RMK 		= pjtU.str(rs.RMK);
			
			if(pjtU.isEmpty(ROLE_CD)) {
				throw new BizRuntimeException("역할코드가 입력되지 않았습니다.");
			}
			if(pjtU.isEmpty(USER_NO)) {
				throw new BizRuntimeException("사용자가 입력되지 않았습니다.");
			}
			if(pjtU.isEmpty(USE_YN)) {
				throw new BizRuntimeException("사용여부가 입력되지 않았습니다.");
			}
			if(pjtU.isEmpty(ORD)) {
				throw new BizRuntimeException("정렬이 입력되지 않았습니다.");
			}
			Long L_USER_NO  = Long.parseLong(USER_NO);
			
			
			daCmUserRoleCd.createCmUserRoleCd(
					ROLE_CD
					,L_USER_NO
					,USE_YN
					,ORD
					,RMK
					,L_SESSION_USER_NO
					);
		}
		
		for( int i=0;i<inDS.UPDT_DATA.size();i++) {
			DATA_ROW  rs =inDS.UPDT_DATA.get(i);
			String  ROLE_CD 	= pjtU.str(rs.ROLE_CD);
			String  USER_NO 	= pjtU.str(rs.USER_NO);
			String  USE_YN 		= pjtU.str(rs.USE_YN);
			String  ORD 		= pjtU.str(rs.ORD);
			String  RMK 		= pjtU.str(rs.RMK);
			
			if(pjtU.isEmpty(ROLE_CD)) {
				throw new BizRuntimeException("역할코드가 입력되지 않았습니다.");
			}
			if(pjtU.isEmpty(USER_NO)) {
				throw new BizRuntimeException("사용자가 입력되지 않았습니다.");
			}
			if(pjtU.isEmpty(USE_YN)) {
				throw new BizRuntimeException("사용여부가 입력되지 않았습니다.");
			}
			if(pjtU.isEmpty(ORD)) {
				throw new BizRuntimeException("정렬이 입력되지 않았습니다.");
			}
			
			Long L_USER_NO  = Long.parseLong(USER_NO);
			
			daCmUserRoleCd.createCmUserRoleCd(
					ROLE_CD
					,L_USER_NO
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
