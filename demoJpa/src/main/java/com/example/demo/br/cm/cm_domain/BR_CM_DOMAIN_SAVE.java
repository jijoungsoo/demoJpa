package com.example.demo.br.cm.cm_domain;

import java.util.ArrayList;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.cm.DA_CM_DOMAIN;
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

@Tag(name = "CM_DOMAIN", description = "공통도메인")
@Slf4j
@OpService
@Service
public class BR_CM_DOMAIN_SAVE {

	@Autowired
    PjtUtil pjtU;

	@JsonRootName("IN_DS")
	@ApiModel(value="OUT_DS-BR_CM_DOMAIN_SAVE")
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
		@Schema(name="IN_DATA-UPDT_DATA", description = "수정 데이터")
		ArrayList<DATA_ROW> UPDT_DATA = new ArrayList<DATA_ROW>();
		
		@JsonProperty("LSESSION")
		LSESSION_ROW LSESSION;		
	}

	@ApiModel(value="DATA_ROW-BR_CM_DOMAIN_SAVE")
	@Data
	static class DATA_ROW {
		@JsonProperty("DMN_NO")
		@Schema(name = "DMN_NO", example = "1", description = "도메인NO")
		Long DMN_NO = null;
		@JsonProperty("DMN_CD")
		@Schema(name = "DMN_CD", example = "USE_YN", description = "도메인CD")
		String DMN_CD = null;
		@JsonProperty("DMN_NM")
		@Schema(name = "DMN_NM", example = "사용여부", description = "도메인명")
		String DMN_NM = null;
		
		@JsonProperty("DATA_TYPE")
		@Schema(name = "DATA_TYPE", example = "VARCHAR(10)", description = "데이터타입")
		String DATA_TYPE = null;
		
		@JsonProperty("RMK")
		@Schema(name = "RMK", example = "비고", description = "비고")
		String RMK = null;		
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_DOMAIN_SAVE")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA", title="OUT_DATA-UPDT_DATA", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}
	
	@Autowired
	DA_CM_DOMAIN daDmn;
	
	@Autowired
	DA_CM_SEQ daCmSeq;
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_DOMAIN"},value = "도메인을 저장한다.", notes = "")
	//@PostMapping(path= "/api/BR_CM_DOMAIN_SAVE", consumes = "application/json", produces = "application/json")
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
			String  DMN_CD 		= pjtU.str(rs.DMN_CD);
			String  DMN_NM 		= pjtU.str(rs.DMN_NM);
			String  DATA_TYPE 	= pjtU.str(rs.DATA_TYPE);
			String  RMK 	    = pjtU.str(rs.RMK);
			
			if(pjtU.isEmpty(DMN_CD)) {
				throw new BizRuntimeException("도메인코드 입력되지 않았습니다.");
			}
			if(pjtU.isEmpty(DMN_NM)) {
				throw new BizRuntimeException("도메인명 입력되지 않았습니다.");
			}
			if(pjtU.isEmpty(DATA_TYPE)) {
				throw new BizRuntimeException("데이터타입 입력되지 않았습니다.");
			}
			
			long L_DMN_NO =daCmSeq.increate("CM_DOMAIN_DMN_NO_SEQ");
						
			daDmn.createDomain(
					L_DMN_NO
					,DMN_CD
					,DMN_NM
					,DATA_TYPE
					,RMK
					,L_SESSION_USER_NO
					);
		}
		
		for( int i=0;i<inDS.UPDT_DATA.size();i++) {
			DATA_ROW  rs =inDS.UPDT_DATA.get(i);
			String  DMN_NO 		= pjtU.str(rs.DMN_NO);
			String  DMN_CD 		= pjtU.str(rs.DMN_CD);
			String  DMN_NM 		= pjtU.str(rs.DMN_NM);
			String  DATA_TYPE 	= pjtU.str(rs.DATA_TYPE);
			String  RMK 	= pjtU.str(rs.RMK);
			
			if(pjtU.isEmpty(DMN_NO)) {
				throw new BizRuntimeException("도메인번호가 입력되지 않았습니다.");
			}
			
			if(pjtU.isEmpty(DMN_CD)) {
				throw new BizRuntimeException("도메인코드 입력되지 않았습니다.");
			}
			if(pjtU.isEmpty(DMN_NM)) {
				throw new BizRuntimeException("도메인명 입력되지 않았습니다.");
			}
			if(pjtU.isEmpty(DATA_TYPE)) {
				throw new BizRuntimeException("데이터타입 입력되지 않았습니다.");
			}
			long L_DMN_NO = Long.parseLong(DMN_NO);
			daDmn.updateDomain(
					 L_DMN_NO
					,DMN_CD
					,DMN_NM
					,DATA_TYPE
					,RMK
					,L_SESSION_USER_NO
					);
		}

		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
}
