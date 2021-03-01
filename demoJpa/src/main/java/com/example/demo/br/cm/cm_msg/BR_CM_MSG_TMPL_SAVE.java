package com.example.demo.br.cm.cm_msg;

import java.util.ArrayList;

import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.cm.DA_CM_MSG_TMPL;
import com.example.demo.db.da.cm.DA_CM_SEQ;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

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

@Tag(name = "CM_MSG", description = "공통메시지")
@Slf4j
@RestController
public class BR_CM_MSG_TMPL_SAVE {

	@JsonRootName("IN_DS")
	@ApiModel(value="OUT_DS-BR_CM_MSG_TMPL_SAVE")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CM_MSG_TMPL_SAVE", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
		
		@JsonProperty("LSESSION")
		LSESSION_ROW LSESSION;		
	}

	@ApiModel(value="IN_DATA_ROW-BR_CM_MSG_TMPL_SAVE")
	@Data
	static class IN_DATA_ROW {

		@JsonProperty("TMPL_SEQ")
		@Schema(name = "TMPL_SEQ", example = "1", description = "TMPL_SEQ")
		String TMPL_SEQ = null;

		@JsonProperty("SND_KIND_CD")
		@Schema(name = "SND_KIND_CD", example = "EMAIL, KAKAO", description = "발송구분")
		String SND_KIND_CD = null;

		@JsonProperty("TTL")
		@Schema(name = "TTL", example = "안녕하세요", description = "제목")
		String TTL = null;

		@JsonProperty("MSG")
		@Schema(name = "MSG", example = "MSG", description = "MSG")
		String MSG = null;

		@JsonProperty("TMPL_PATH")
		@Schema(name = "TMPL_PATH", example = "T0001", description = "템플릿경로")
		String TMPL_PATH = null;

		@JsonProperty("RMK")
		@Schema(name = "RMK", example = "비고", description = "비고")
		String RMK = null;
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_MSG_TMPL_SAVE")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA", title="OUT_DATA-BR_CM_MSG_TMPL_SAVE", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}

	@Autowired
	DA_CM_MSG_TMPL daMsgTmpl;
	
	@Autowired
	DA_CM_SEQ daCmSeq;
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_MSG"},value = "메시지를 템플릿을 저정한다.", notes = "")
	@PostMapping(path= "/api/BR_CM_MSG_TMPL_SAVE", consumes = "application/json", produces = "application/json")
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
			IN_DATA_ROW  rs 		=inDS.IN_DATA.get(i);			
			String  TMPL_SEQ 		= PjtUtil.str(rs.TMPL_SEQ);
			String  SND_KIND_CD		= PjtUtil.str(rs.SND_KIND_CD);
			String  TTL 			= PjtUtil.str(rs.TTL);
			String  MSG				= PjtUtil.str(rs.MSG);
			String  TMPL_PATH		= PjtUtil.str(rs.TMPL_PATH);
			String  RMK				= PjtUtil.str(rs.RMK);
			
			if(PjtUtil.isEmpty(SND_KIND_CD)) {
				throw new BizRuntimeException("발송구분이 입력되지 않았습니다.");
			}

			if(PjtUtil.isEmpty(TTL)) {
				throw new BizRuntimeException("제목이 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(MSG)) {
				throw new BizRuntimeException("메시지가 입력되지 않았습니다.");
			}

			if(PjtUtil.isEmpty(TMPL_SEQ)==true){
				Long L_TMPL_SEQ =daCmSeq.increate("CM_MSG_TMPL_TMPL_SEQ");				
						
				daMsgTmpl.crtMsgTmpl(					
					 L_TMPL_SEQ
					, SND_KIND_CD
					, TTL
					, MSG
					, TMPL_PATH
					, RMK
					, L_SESSION_USER_NO
						);
			} else {
				Long L_TMPL_SEQ = Long.parseLong(TMPL_SEQ);
				daMsgTmpl.updtMsgTmpl(				
					L_TMPL_SEQ
					, TTL
					, MSG
					, TMPL_PATH
					, RMK
					, L_SESSION_USER_NO
						);
			}
		}
		
		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
}
