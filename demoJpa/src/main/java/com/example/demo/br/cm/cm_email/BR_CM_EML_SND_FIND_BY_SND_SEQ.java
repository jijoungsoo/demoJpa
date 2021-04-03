package com.example.demo.br.cm.cm_email;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.cm.DA_CM_EML_SND;
import com.example.demo.db.da.cm.DA_CM_EML_SND_RCV;
import com.example.demo.db.domain.cm.CmEmlSnd;
import com.example.demo.db.domain.cm.CmEmlSndRcv;
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

@Tag(name = "CM_EML", description = "공통메일")
@Slf4j
@OpService
@Service
public class BR_CM_EML_SND_FIND_BY_SND_SEQ {

	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_CM_EML_SND_FIND_BY_SND_SEQ")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CM_EML_SND_FIND_BY_SND_SEQ", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();

		
		@JsonProperty("LSESSION")
		LSESSION_ROW LSESSION;

	}

	@ApiModel(value="IN_DATA_ROW-BR_CM_EML_SND_FIND_BY_SND_SEQ")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("SND_SEQ")
		@Schema(name = "SND_SEQ", example = "1 ", description = "SND_SEQ")
		String SND_SEQ = "";
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_EML_SND_FIND_BY_SND_SEQ")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_CM_EML_SND_FIND_BY_SND_SEQ", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();

		@JsonProperty("RCV_DATA")
		@Schema(name="RCV_DATA-BR_CM_EML_SND_FIND_BY_SND_SEQ", description = "출력 데이터")
		ArrayList<RCV_DATA_ROW> RCV_DATA = new ArrayList<RCV_DATA_ROW>();
	}

	@ApiModel(value="OUT_DATA_ROW-BR_CM_EML_SND_FIND_BY_SND_SEQ")
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

	
	@ApiModel(value="RCV_DATA_ROW-BR_CM_EML_SND_FIND_BY_SND_SEQ")
	@Data
	static class RCV_DATA_ROW {
		@JsonProperty("SND_SEQ")
		@Schema(name = "SND_SEQ", example = "1", description = "SND_SEQ")
		String SND_SEQ = null;

		@JsonProperty("RCV_SEQ")
		@Schema(name = "RCV_SEQ", example = "1", description = "RCV_SEQ")
		String RCV_SEQ = null;
		
		@JsonProperty("RCV_NM")
		@Schema(name = "RCV_NM", example = "홍길동", description = "수신자명")
		String RCV_NM = null;

		@JsonProperty("RCV_ADDR")
		@Schema(name = "RCV_ADDR", example = "hotmail.com , 010-9999", description = "수신자주소")
		String RCV_ADDR = null;

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

	@Autowired
	DA_CM_EML_SND_RCV daEmlSndRcv;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_EML"},value = "이메일 전송상세를 조회한다.", notes = "")
	//@PostMapping(path= "/api/BR_CM_EML_SND_FIND_BY_SND_SEQ", consumes = "application/json", produces = "application/json")
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
		if(PjtUtil.isEmpty(inDS.IN_DATA.get(0).SND_SEQ)){
			throw new BizRuntimeException("SND_SEQ가  넘어오지 않았습니다.");
		}
		String SND_SEQ  =  inDS.IN_DATA.get(0).SND_SEQ;
		Long L_SND_SEQ  = Long.parseLong(SND_SEQ);
		
		List<CmEmlSnd> al = daEmlSnd.findEmlSndBySndSeq(L_SND_SEQ);
		OUT_DS outDs = new OUT_DS();
		
		for(int i=0;i<al.size();i++) {
			CmEmlSnd cm=al.get(i);
			OUT_DATA_ROW  row = new OUT_DATA_ROW();
			row.SND_SEQ= String.valueOf(cm.getSndSeq());
			row.TTL= cm.getTtl();
			row.CNTNT= cm.getCntnt();
			row.SNDR_NM= cm.getSndrNm();
			row.SNDR_ADDR= cm.getSndrAddr();
			row.SND_STATUS_CD= cm.getSndStatusCd();
			row.SND_TYPE_CD= cm.getSndTypeCd();
			row.SND_DTM= PjtUtil.getYyyy_MM_dd_HHMMSS(cm.getSndDtm());			
			row.SND_CMPL_DTM= PjtUtil.getYyyy_MM_dd_HHMMSS(cm.getSndCmplDtm());			

			row.UPDT_USR_NO= String.valueOf(cm.getUpdtUsrNo());
			row.UPDT_DTM=PjtUtil.getYyyy_MM_dd_HHMMSS(cm.getUpdtDtm());
			row.CRT_USR_NO= String.valueOf(cm.getCrtUsrNo());
			row.CRT_DTM=PjtUtil.getYyyy_MM_dd_HHMMSS(cm.getCrtDtm());
			outDs.OUT_DATA.add(row);
		}

		List<CmEmlSndRcv> al2 = daEmlSndRcv.findEmlSndRcvBySndSeq(L_SND_SEQ);
		for(int i=0;i<al2.size();i++) {
			CmEmlSndRcv cm=al2.get(i);
			RCV_DATA_ROW  row = new RCV_DATA_ROW();
			row.SND_SEQ= String.valueOf(cm.getSndSeq());
			row.RCV_SEQ= String.valueOf(cm.getRcvSeq());
			row.RCV_NM= cm.getRcvNm();
			row.RCV_ADDR= cm.getRcvAddr();
			outDs.RCV_DATA.add(row);
		}
		return outDs;
	}
}
