package com.example.demo.br.cm.cm_email;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.db.da.cm.DA_CM_EML_SND;
import com.example.demo.db.domain.cm.CmEmlSnd;
import com.example.demo.db.domain.cm.QCmEmlSnd;
import com.example.demo.exception.BizException;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.Expressions;

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

@Tag(name = "CM_EML", description = "공통메일")
@Slf4j
@RestController
public class BR_CM_EML_SND_FIND {

	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_CM_EML_SND_FIND")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CM_EML_SND_FIND", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();

	}

	@ApiModel(value="IN_DATA_ROW-BR_CM_EML_SND_FIND")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("MSG_TMPL_KIND_CD")
		@Schema(name = "MSG_TMPL_KIND_CD", example = "EMAIL , KAKAO ", description = "템플릿구분")
		String MSG_TMPL_KIND_CD = "";

		@JsonProperty("MSG_TMPL_STATUS_CD")
		@Schema(name = "MSG_TMPL_STATUS_CD", example = "1", description = "템플릿상태")
		String MSG_TMPL_STATUS_CD = "";
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_EML_SND_FIND")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_CM_EML_SND_FIND", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}

	@ApiModel(value="OUT_DATA_ROW-BR_CM_EML_SND_FIND")
	@Data
	static class OUT_DATA_ROW {
		
		@JsonProperty("SND_SEQ")
		@Schema(name = "SND_SEQ", example = "1", description = "SND_SEQ")
		String SND_SEQ = null;

		@JsonProperty("TTL")
		@Schema(name = "TTL", example = "1", description = "TTL")
		String TTL = "";

		@JsonProperty("CNTNT")
		@Schema(name = "CNTNT", example = "1", description = "CNTNT")
		String CNTNT = "";

		@JsonProperty("SNDR_NM")
		@Schema(name = "SNDR_NM", example = "홍길동", description = "발송자명")
		String SNDR_NM = null;

		@JsonProperty("SNDR_ADDR")
		@Schema(name = "SNDR_ADDR", example = "010-, email", description = "발송주소")
		String SNDR_ADDR = null;

		@JsonProperty("SND_STATUS_CD")
		@Schema(name = "SND_STATUS_CD", example = "T0001", description = "발송 상태")
		String SND_STATUS_CD = null;

		@JsonProperty("SND_TYPE_CD")
		@Schema(name = "SND_TYPE_CD", example = "비고", description = "발송타입")
		String SND_TYPE_CD = null;

		@JsonProperty("RCV_CNT")
		@Schema(name = "RCV_CNT", example = "3", description = "수신자수")
		String RCV_CNT = null;

		@JsonProperty("RCV_NM")
		@Schema(name = "RCV_NM", example = "홍길동", description = "수신자명")
		String RCV_NM = null;


		@JsonProperty("RCV_ADDR")
		@Schema(name = "RCV_ADDR", example = "gmail.com, 010-23213", description = "수신자주소")
		String RCV_ADDR = null;
		
		@JsonProperty("SND_DTM")
		@Schema(name = "SND_DTM", example = "20211212 12:12:32", description = "발송예약일자")
		String SND_DTM = null;
	
		@JsonProperty("SND_CMPL_DTM")
		@Schema(name = "SND_CMPL_DTM", example = "20211212 12:12:32", description = "발송완료일자")
		String SND_CMPL_DTM = null;

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
	DA_CM_EML_SND daEmlSnd;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_MSG"},value = "이메일 전송내역을 조회한다.", notes = "")
	@PostMapping(path= "/api/BR_CM_EML_SND_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		
		List<Tuple>  al =daEmlSnd.findEmlSnd();
		OUT_DS outDs = new OUT_DS();
		
		for(int i=0;i<al.size();i++) {
			Tuple cm=al.get(i);
			OUT_DATA_ROW  row = new OUT_DATA_ROW();
			row.SND_SEQ= String.valueOf(cm.get(QCmEmlSnd.cmEmlSnd.sndSeq));
			row.TTL= cm.get(QCmEmlSnd.cmEmlSnd.ttl);
			row.CNTNT= cm.get(QCmEmlSnd.cmEmlSnd.cntnt);
			row.SNDR_NM= cm.get(QCmEmlSnd.cmEmlSnd.sndrNm);
			row.SNDR_ADDR= cm.get(QCmEmlSnd.cmEmlSnd.sndrAddr);
			row.SND_STATUS_CD= cm.get(QCmEmlSnd.cmEmlSnd.sndStatusCd);
			row.SND_TYPE_CD= cm.get(QCmEmlSnd.cmEmlSnd.sndTypeCd);
			row.RCV_CNT = String.valueOf(cm.get(Expressions.stringPath("rcv_cnt")));
			row.RCV_NM = cm.get(Expressions.stringPath("rcv_nm"));
			row.RCV_ADDR = cm.get(Expressions.stringPath("rcv_addr"));
			row.SND_DTM= PjtUtil.getYyyy_MM_dd_HHMMSS(cm.get(QCmEmlSnd.cmEmlSnd.sndDtm));			
			row.SND_CMPL_DTM= PjtUtil.getYyyy_MM_dd_HHMMSS(cm.get(QCmEmlSnd.cmEmlSnd.sndCmplDtm));			

			row.UPDT_USR_NO= String.valueOf(cm.get(QCmEmlSnd.cmEmlSnd.updtUsrNo));
			row.UPDT_DTM=PjtUtil.getYyyy_MM_dd_HHMMSS(cm.get(QCmEmlSnd.cmEmlSnd.updtDtm));
			row.CRT_USR_NO= String.valueOf(cm.get(QCmEmlSnd.cmEmlSnd.updtUsrNo));
			row.CRT_DTM=PjtUtil.getYyyy_MM_dd_HHMMSS(cm.get(QCmEmlSnd.cmEmlSnd.crtDtm));
			outDs.OUT_DATA.add(row);
		}
		return outDs;
	}
}
