package com.example.demo.br.cm.cm_msg;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.cm.DA_CM_MSG_TMPL;
import com.example.demo.db.domain.cm.CmMsgTmpl;
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
public class BR_CM_MSG_TMPL_FIND_BY_TMPL_SEQ {
	@Autowired
    PjtUtil pjtU;

	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_CM_MSG_TMPL_FIND_BY_TMPL_SEQ")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CM_MSG_TMPL_FIND_BY_TMPL_SEQ", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();

		@JsonProperty("LSESSION")
		LSESSION_ROW LSESSION;		

	}

	@ApiModel(value="IN_DATA_ROW-BR_CM_MSG_TMPL_FIND_BY_TMPL_SEQ")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("TMPL_SEQ")
		@Schema(name = "TMPL_SEQ", example = "EMAIL , KAKAO ", description = "TMPL_SEQ")
		String TMPL_SEQ = "";
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_MSG_TMPL_FIND_BY_TMPL_SEQ")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_CM_MSG_TMPL_FIND_BY_TMPL_SEQ", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}

	@ApiModel(value="OUT_DATA_ROW-BR_CM_MSG_TMPL_FIND_BY_TMPL_SEQ")
	@Data
	static class OUT_DATA_ROW {
		
		@JsonProperty("TMPL_SEQ")
		@Schema(name = "TMPL_SEQ", example = "1", description = "TMPL_SEQ")
		String TMPL_SEQ = null;

		@JsonProperty("MSG_TMPL_KIND_CD")
		@Schema(name = "MSG_TMPL_KIND_CD", example = "EMAIL, KAKAO", description = "발송구분")
		String MSG_TMPL_KIND_CD = null;

		@JsonProperty("MSG_TMPL_STATUS_CD")
		@Schema(name = "MSG_TMPL_STATUS_CD", example = "1", description = "템플릿상태")
		String MSG_TMPL_STATUS_CD = "";


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
					
		@JsonProperty("CRT_USR_NO")
		@Schema(name = "CRT_USR_NO", example = "1", description = "생성자NO")
		String CRT_USR_NO = null;
		
		@JsonProperty("UPDT_USR_NO")
		@Schema(name = "UPDT_USR_NO", example = "1", description = "수정자NO")
		String UPDT_USR_NO = null;
		
		@JsonProperty("CRT_DTM")
		@Schema(name = "CRT_DTM", example = "202012311640", description = "생성일시")
		String CRT_DTM = null;
		
		@JsonProperty("UPDT_DTM")
		@Schema(name = "UPDT_DTM", example = "202012311640", description = "수정일시")
		String UPDT_DTM = null;
	}
	
	@Autowired
	DA_CM_MSG_TMPL daMsgTmpl;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_MSG"},value = "메시지 템플릿 한건을 조회한다.", notes = "")
	//@PostMapping(path= "/api/BR_CM_MSG_TMPL_FIND_BY_TMPL_SEQ", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.LSESSION==null) {
			throw new BizRuntimeException("세션값이 넘어오지 않았습니다1.");
		}
		String SESSION_USER_NO =inDS.LSESSION.getUSER_NO();
		if(pjtU.isEmpty(SESSION_USER_NO)) {
			throw new BizRuntimeException("사용자NO가 넘어오지 않았습니다2.");
		}
		Long L_SESSION_USER_NO = Long.parseLong(SESSION_USER_NO);

		if(inDS.IN_DATA==null) {
			throw new BizRuntimeException("IN_DATA가 넘어오지 않았습니다1.");
		}
		if(inDS.IN_DATA.size()==0) {
			throw new BizRuntimeException("IN_DATA가 넘어오지 않았습니다2.");
		}
		if(pjtU.isEmpty(inDS.IN_DATA.get(0).TMPL_SEQ)){
			throw new BizRuntimeException("TMPL_SEQ가  넘어오지 않았습니다2.");
		}
		String TMPL_SEQ  =  inDS.IN_DATA.get(0).TMPL_SEQ;

		Long L_TMPL_SEQ = Long.parseLong(TMPL_SEQ);

		List<CmMsgTmpl>  al =daMsgTmpl.findMsgTmplByTmplSeq(L_TMPL_SEQ);
		OUT_DS outDs = new OUT_DS();
		for(int i=0;i<al.size();i++) {
			CmMsgTmpl cm=al.get(i);
			OUT_DATA_ROW  row = new OUT_DATA_ROW();
			row.TMPL_SEQ= String.valueOf(cm.getTmplSeq());
			row.MSG_TMPL_KIND_CD= cm.getMsgTmplKindCd();
			row.MSG_TMPL_STATUS_CD= cm.getMsgTmplStatusCd();
			row.TTL= cm.getTtl();
			row.MSG= cm.getMsg();
			row.TMPL_PATH= cm.getTmplPath();
			row.RMK= cm.getRmk();
			row.CRT_USR_NO= String.valueOf(cm.getCrtUsrNo());
			row.UPDT_USR_NO= String.valueOf(cm.getUpdtUsrNo());
			row.CRT_DTM=pjtU.getYyyy_MM_dd_HHMMSS(cm.getCrtDtm());
			row.UPDT_DTM=pjtU.getYyyy_MM_dd_HHMMSS(cm.getUpdtDtm());
			outDs.OUT_DATA.add(row);
		}
		return outDs;
	}
}
