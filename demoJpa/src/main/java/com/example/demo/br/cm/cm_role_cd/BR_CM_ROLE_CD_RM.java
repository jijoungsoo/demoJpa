package com.example.demo.br.cm.cm_role_cd;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.cm.DA_CM_ROLE_CD;
import com.example.demo.db.da.cm.DA_CM_USER_ROLE_CD;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.querydsl.core.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
public class BR_CM_ROLE_CD_RM {
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_CM_ROLE_CD_RM")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CM_ROLE_CD_RM", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
		
		@JsonProperty("LSESSION")
		LSESSION_ROW LSESSION;
	}

	@ApiModel(value="IN_DATA_ROW-BR_CM_ROLE_CD_RM")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("ROLE_CD")
		@Schema(name = "ROLE_CD", example = "ROLE_ADMIN", description = "역할코드")
		String ROLE_CD = "";
	}

	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_ROLE_CD_RM")
	@Data
	static class OUT_DS {
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}
	
	@Autowired
	DA_CM_USER_ROLE_CD daUserRoleCd;

	@Autowired
	DA_CM_ROLE_CD daRoleCd;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_ROLE_CD"},value = "역할코드를 삭제한다.", notes = "")
	@PostMapping(path= "/api/BR_CM_ROLE_CD_RM", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.LSESSION==null) {
			throw new BizRuntimeException("세션값이 넘어오지 않았습니다1.");
		}
		
		String SESSION_USER_NO =inDS.LSESSION.getUSER_NO();
		if(PjtUtil.isEmpty(SESSION_USER_NO)) {
			throw new BizRuntimeException("세션 사용자NO가 넘어오지 않았습니다2.");
		}

		Long L_SESSION_USER_NO = Long.parseLong(SESSION_USER_NO);
		for( int i=0;i<inDS.IN_DATA.size();i++) {
			IN_DATA_ROW  rs =inDS.IN_DATA.get(i);
			String  ROLE_CD 		= PjtUtil.str(rs.ROLE_CD);
			if(PjtUtil.isEmpty(ROLE_CD)) {
				throw new BizRuntimeException("역할코드가 입력되지 않았습니다.");
			}
			
			//삭제하기 전에 사용자-맴핑이 있는지 보고 그게 있으면 에러
			
			List<Tuple>  al = daUserRoleCd.findCmUserRoleCd(ROLE_CD);
			if(al.size()>0){
				throw new BizRuntimeException("역할코드에 사용자 맴핑이 남아있습니다.  역할-사용자를 먼저 삭제해주세요.");
			}
			
			daRoleCd.rmCmRoleCd(ROLE_CD);
		}
	
		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
}
