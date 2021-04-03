package com.example.demo.br.cm.cm_msg;

import java.util.ArrayList;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.cm.DA_CM_MSG_TMPL;
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

@Tag(name = "CM_MSG", description = "공통메시지")
@Slf4j
@OpService
@Service
public class BR_CM_MSG_TMPL_CHG {

	@JsonRootName("IN_DS")
	@ApiModel(value="OUT_DS-BR_CM_MSG_TMPL_CHG")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CM_MSG_TMPL_CHG", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
		
		@JsonProperty("LSESSION")
		LSESSION_ROW LSESSION;		
	}

	@ApiModel(value="IN_DATA_ROW-BR_CM_MSG_TMPL_CHG")
	@Data
	static class IN_DATA_ROW {

		@JsonProperty("TMPL_SEQ")
		@Schema(name = "TMPL_SEQ", example = "1", description = "TMPL_SEQ")
		String TMPL_SEQ = null;

		@JsonProperty("MSG_TMPL_STATUS_CD")
		@Schema(name = "MSG_TMPL_STATUS_CD", example = "T , U , D", description = "템플릿상태")
		String MSG_TMPL_STATUS_CD = null;

		
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_MSG_TMPL_CHG")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA", title="OUT_DATA-BR_CM_MSG_TMPL_CHG", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}

	@Autowired
	DA_CM_MSG_TMPL daMsgTmpl;
	
	@Autowired
	DA_CM_SEQ daCmSeq;
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_MSG"},value = "메시지를 템플릿을 사용,삭제로 변경 한다.", notes = "")
	//@PostMapping(path= "/api/BR_CM_MSG_TMPL_CHG", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.LSESSION==null) {
			throw new BizRuntimeException("세션값이 넘어오지 않았습니다1.");
		}
		String SESSION_USER_NO =inDS.LSESSION.getUSER_NO();
		if(PjtUtil.isEmpty(SESSION_USER_NO)) {
			throw new BizRuntimeException("사용자NO가 넘어오지 않았습니다2.");
		}
		Long L_SESSION_USER_NO = Long.parseLong(SESSION_USER_NO);

		if(inDS.IN_DATA==null) {
			throw new BizRuntimeException("IN_DATA가 넘어오지 않았습니다1.");
		}
		if(inDS.IN_DATA.size()==0) {
			throw new BizRuntimeException("IN_DATA가 넘어오지 않았습니다2.");
		}
		if(PjtUtil.isEmpty(inDS.IN_DATA.get(0).TMPL_SEQ)){
			throw new BizRuntimeException("TMPL_SEQ가  넘어오지 않았습니다2.");
		}
		String TMPL_SEQ  =  inDS.IN_DATA.get(0).TMPL_SEQ;
		Long L_TMPL_SEQ = Long.parseLong(TMPL_SEQ);
		String MSG_TMPL_STATUS_CD  =  inDS.IN_DATA.get(0).MSG_TMPL_STATUS_CD;
									
		daMsgTmpl.chgMsgTmpl(L_TMPL_SEQ,MSG_TMPL_STATUS_CD,L_SESSION_USER_NO);
		
		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
}
