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
public class BR_CM_USER_ROLE_CD_RM {
	@Autowired
    PjtUtil pjtU;
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_CM_USER_ROLE_CD_RM")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CM_USER_ROLE_CD_RM", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
		
		@JsonProperty("LSESSION")
		LSESSION_ROW LSESSION;
	}

	@ApiModel(value="IN_DATA_ROW-BR_CM_USER_ROLE_CD_RM")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("ROLE_CD")
		@Schema(name = "ROLE_CD", example = "ROLE_ADMIN", description = "역할코드")
		String ROLE_CD = "";
		
		@JsonProperty("USER_NO")
		@Schema(name = "USER_NO", example = "1", description = "사용자NO")
		String USER_NO ;
	}

	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_USER_ROLE_CD_RM")
	@Data
	static class OUT_DS {
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}
	
	@Autowired
	DA_CM_USER_ROLE_CD daUserRoleCd;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_ROLE_CD"},value = "사용자-역할코드를 삭제한다.", notes = "")
	//@PostMapping(path= "/api/BR_CM_USER_ROLE_CD_RM", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.LSESSION==null) {
			throw new BizRuntimeException("세션값이 넘어오지 않았습니다1.");
		}
		
		String SESSION_USER_NO =inDS.LSESSION.getUSER_NO();
		if(pjtU.isEmpty(SESSION_USER_NO)) {
			throw new BizRuntimeException("세션 사용자NO가 넘어오지 않았습니다2.");
		}

		Long L_SESSION_USER_NO = Long.parseLong(SESSION_USER_NO);
		for( int i=0;i<inDS.IN_DATA.size();i++) {
			IN_DATA_ROW  rs =inDS.IN_DATA.get(i);
			String  ROLE_CD 		= pjtU.str(rs.ROLE_CD);
			String  USER_NO 		= pjtU.str(rs.USER_NO);
			if(pjtU.isEmpty(ROLE_CD)) {
				throw new BizRuntimeException("역할코드가 입력되지 않았습니다.");
			}
			
			if(pjtU.isEmpty(USER_NO)) {
				throw new BizRuntimeException("사용자번호가 입력되지 않았습니다.");
			}

			Long L_USER_NO = Long.parseLong(USER_NO);
			
			daUserRoleCd.rmCmRoleCd(ROLE_CD,L_USER_NO);
		}
	
		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
}
