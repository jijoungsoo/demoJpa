package com.example.demo.br.cm.cm_sess_log;

import java.util.ArrayList;

import com.example.demo.anotation.OpService;
import com.example.demo.db.da.cm.DA_CM_SEQ;
import com.example.demo.db.da.cm.DA_CM_SESS_LOG;
import com.example.demo.exception.BizException;
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
public class BR_CM_SESSION_LOG_CRT {

	@Autowired
    PjtUtil pjtU;

	
	@Autowired
	DA_CM_SEQ daCmSeq;

	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_CM_SESSION_LOG_CRT")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CM_SESSION_LOG_CRT", description = "입력 데이터")
		ArrayList<DATA_ROW> IN_DATA = new ArrayList<DATA_ROW>();
	}

	@ApiModel(value="DATA_ROW-BR_CM_SESSION_LOG_CRT")
	@Data
	static class DATA_ROW {
		@JsonProperty("USER_NO")
		@Schema(name = "USER_NO", example = "USER_NO", description = "USER_ID")
		String USER_NO = null;
		@JsonProperty("USER_ID")
		@Schema(name = "USER_ID", example = "USER_ID", description = "USER_ID")
		String USER_ID = null;
		@JsonProperty("LOG_TYPE")
		@Schema(name = "USER_ID", example = "(LOGIN,LOGOUT)", description = "LOG_TYPE")
		String LOG_TYPE = null;

		@JsonProperty("IPADDR")
		@Schema(name = "IPADDR", example = "IPADDR", description = "IPADDR")
		String IPADDR = null;
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_SESSION_LOG_LOGIN")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA", title="OUT_DATA-UPDT_DATA", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}
	
	@Autowired
	DA_CM_SESS_LOG daCmSessLog;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"BR_CM_SESSION_LOG_LOGIN"},value = "세션로그 로그인정보를 기록한다.", notes = "")
	//@PostMapping(path= "/api/BR_CM_CD_SAVE", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		OUT_DS outDs = new OUT_DS(); 
		if(inDS.IN_DATA.size()!=1){
			return outDs;
		}
		DATA_ROW  rs =inDS.IN_DATA.get(0);
		String  USER_NO = pjtU.str(rs.USER_NO);
		String  USER_ID	= pjtU.str(rs.USER_ID);
		String  LOG_TYPE	= pjtU.str(rs.LOG_TYPE);
		String  IPADDR	= pjtU.str(rs.IPADDR);

		Long L_USER_NO = Long.parseLong(USER_NO);
		
		long L_SESS_LOG_SEQ =daCmSeq.increate("CM_SESS_LOG_SESS_LOG_SEQ");

		daCmSessLog.crt(
				 L_SESS_LOG_SEQ
				,L_USER_NO
				,USER_ID
				,LOG_TYPE
				,IPADDR
				);
		return outDs;
	}
	
}
