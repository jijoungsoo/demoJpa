package com.example.demo.br.cm.cm_msg;

import java.util.ArrayList;
import java.util.Date;

import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.cm.DA_CM_MSG_SND;
import com.example.demo.db.da.cm.DA_CM_MSG_SND_RCV;
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
public class BR_CM_MSG_SND_SAVE {

	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_CM_MSG_SND_SAVE")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA,RCV_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CM_MSG_SND_SAVE", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();

		@JsonProperty("RCV_DATA")
		@Schema(name="RCV_DATA-BR_CM_MSG_SND_SAVE", description = "입력 데이터")
		ArrayList<RCV_DATA_ROW> RCV_DATA = new ArrayList<RCV_DATA_ROW>();

		@JsonProperty("LSESSION")
		LSESSION_ROW LSESSION;
	}

	@ApiModel(value="IN_DATA_ROW-BR_CM_MSG_SND_SAVE")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("SND_KIND_CD")
		@Schema(name = "SND_KIND_CD", example = "EMAIL, KAKAO", description = "발송구분")
		String SND_KIND_CD = null;

		@JsonProperty("SND_TYPE_CD")
		@Schema(name = "SND_TYPE_CD", example = "P-예약, D-즉시발행", description = "발송타입")
		String SND_TYPE_CD = null;

		@JsonProperty("MSG")
		@Schema(name = "MSG", example = "내용", description = "MSG")
		String MSG = "";

		@JsonProperty("SNDR_NM")
		@Schema(name = "SNDR_NM", example = "홍길동", description = "발송자명")
		String SNDR_NM = null;

		@JsonProperty("SNDR_TEL_NO")
		@Schema(name = "SNDR_TEL_NO", example = "010-", description = "발송 전화번호")
		String SNDR_TEL_NO = null;


		@JsonProperty("SND_DTM")
		@Schema(name = "SND_DTM", example = "20211212 12:12:32", description = "발송예약일자")
		String SND_DTM = null;
	}

	@ApiModel(value="RCV_DATA_ROW-BR_CM_MSG_SND_SAVE")
	@Data
	static class RCV_DATA_ROW {
		@JsonProperty("RCV_NM")
		@Schema(name = "RCV_NM", example = "김길동", description = "받는사람명")
		String RCV_NM = "";

		@JsonProperty("RCV_TEL_NO")
		@Schema(name = "RCV_TEL_NO", example = "010-111-111 등 ", description = "받는사람 전화번호")
		String RCV_TEL_NO = "";
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_MSG_SND_SAVE")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_CM_MSG_SND_SAVE", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}

	@ApiModel(value="OUT_DATA_ROW-BR_CM_MSG_SND_SAVE")
	@Data
	static class OUT_DATA_ROW {
		
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA", title="OUT_DATA-BR_CM_MSG_SND_SAVE", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();

	}
	
	@Autowired
	DA_CM_MSG_SND daMsgSnd;

	
	@Autowired
	DA_CM_MSG_SND_RCV daMsgSndRcv;

	@Autowired
	DA_CM_SEQ daCmSeq;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_MSG"},value = "메시지 전송를 전송한다..", notes = "")
	@PostMapping(path= "/api/BR_CM_MSG_SND_SAVE", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		
		if(inDS.LSESSION==null) {
			throw new BizRuntimeException("세션값이 넘어오지 않았습니다1.");
		}
		String SESSION_USER_NO =inDS.LSESSION.getUSER_NO();
		if(PjtUtil.isEmpty(SESSION_USER_NO)) {
			throw new BizRuntimeException("사용자NO가 넘어오지 않았습니다2.");
		}
		Long L_SESSION_USER_NO = Long.parseLong(SESSION_USER_NO);

		if(inDS.IN_DATA==null){
			throw new BizRuntimeException("IN_DATA가 넘어오지 않았습니다1.");
		}
		if(inDS.IN_DATA.size()==0){
			throw new BizRuntimeException("IN_DATA가 넘어오지 않았습니다2.");
		}

		
		if(inDS.RCV_DATA==null){
			throw new BizRuntimeException("RCV_DATA 넘어오지 않았습니다1.");
		}
		if(inDS.RCV_DATA.size()==0){
			throw new BizRuntimeException("RCV_DATA 넘어오지 않았습니다2.");
		}

		IN_DATA_ROW  rs 		=inDS.IN_DATA.get(0);			
		String  SND_KIND_CD 	= PjtUtil.str(rs.SND_KIND_CD);
		String  MSG_STATUS_CD	= "B"; //발송전 무조건  -- 배치에서 메일을 보낼때 A로 변경됨
		String  SND_TYPE_CD 	= PjtUtil.str(rs.SND_TYPE_CD);
		String  MSG				= PjtUtil.str(rs.MSG);
		String  SNDR_NM			= PjtUtil.str(rs.SNDR_NM);
		String  SNDR_TEL_NO		= PjtUtil.str(rs.SNDR_TEL_NO);
		String  SND_DTM			= PjtUtil.str(rs.SND_DTM);

		Date D_SND_DTM  = null;
			
		if(PjtUtil.isEmpty(SND_KIND_CD)) {
			throw new BizRuntimeException("발송구분이 입력되지 않았습니다.");
		}

		if(PjtUtil.isEmpty(SNDR_NM)) {
			throw new BizRuntimeException("보내는 사람명이 입력되지 않았습니다.");
		}
		if(PjtUtil.isEmpty(SNDR_TEL_NO)) {
			throw new BizRuntimeException("보내는 전화번호가 입력되지 않았습니다.");
		}

		if(PjtUtil.isEmpty(SNDR_TEL_NO)) {
			throw new BizRuntimeException("보내는 전화번호가 입력되지 않았습니다.");
		}

		if(PjtUtil.isEmpty(MSG)) {
			throw new BizRuntimeException("메시지가 입력되지 않았습니다.");
		}
		//날짜변환은 나중에 하자.

		Long L_SND_SEQ =daCmSeq.increate("CM_MSG_SND_SND_SEQ");				
				
		daMsgSnd.crtMsgSnd(					
			 L_SND_SEQ
			, SND_KIND_CD
			, MSG
			, SNDR_NM
			, SNDR_TEL_NO
			, SND_TYPE_CD
			,D_SND_DTM
			,L_SESSION_USER_NO
		);

		for(int i=0;i<inDS.RCV_DATA.size();i++){
			RCV_DATA_ROW  rcv_rs =inDS.RCV_DATA.get(i);
			String  RCV_TEL_NO 	= PjtUtil.str(rcv_rs.RCV_TEL_NO);
			String  RCV_NM 	= PjtUtil.str(rcv_rs.RCV_NM);

			if(PjtUtil.isEmpty(RCV_TEL_NO)) {
				throw new BizRuntimeException("받는 주소가 입력되지 않았습니다.");
			}

			daMsgSndRcv.crtMsgSndRcv(					
				L_SND_SEQ
			   , i
			   , RCV_TEL_NO
			   , RCV_NM
			   ,L_SESSION_USER_NO
		   );
		
		}


		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
}
