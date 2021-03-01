package com.example.demo.br.cm.cm_email;

import java.util.ArrayList;
import java.util.Date;

import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.cm.DA_CM_EML_SND;
import com.example.demo.db.da.cm.DA_CM_EML_SND_RCV;
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

@Tag(name = "CM_EML", description = "공통메일")
@Slf4j
@RestController
public class BR_CM_EML_SND_SAVE {

	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_CM_EML_SND_SAVE")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA,RCV_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CM_EML_SND_SAVE", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();

		@JsonProperty("RCV_DATA")
		@Schema(name="RCV_DATA-BR_CM_EML_SND_SAVE", description = "입력 데이터")
		ArrayList<RCV_DATA_ROW> RCV_DATA = new ArrayList<RCV_DATA_ROW>();

		@JsonProperty("LSESSION")
		LSESSION_ROW LSESSION;
	}

	@ApiModel(value="IN_DATA_ROW-BR_CM_EML_SND_SAVE")
	@Data
	static class IN_DATA_ROW {

		@JsonProperty("SND_TYPE_CD")
		@Schema(name = "SND_TYPE_CD", example = "P-예약, D-즉시발행", description = "발송타입")
		String SND_TYPE_CD = null;

		@JsonProperty("TTL")
		@Schema(name = "TTL", example = "제목", description = "TTL")
		String TTL = "";

		@JsonProperty("CNTNT")
		@Schema(name = "CNTNT", example = "내용", description = "CNTNT")
		String CNTNT = "";

		@JsonProperty("SNDR_NM")
		@Schema(name = "SNDR_NM", example = "홍길동", description = "발송자명")
		String SNDR_NM = null;

		@JsonProperty("SNDR_ADDR")
		@Schema(name = "SNDR_ADDR", example = "010-, email", description = "발송주소")
		String SNDR_ADDR = null;


		@JsonProperty("SND_DTM")
		@Schema(name = "SND_DTM", example = "20211212 12:12:32", description = "발송예약일자")
		String SND_DTM = null;
	}

	@ApiModel(value="RCV_DATA_ROW-BR_CM_EML_SND_SAVE")
	@Data
	static class RCV_DATA_ROW {
		@JsonProperty("RCV_NM")
		@Schema(name = "RCV_NM", example = "김길동", description = "받는사람명")
		String RCV_NM = "";

		@JsonProperty("RCV_ADDR")
		@Schema(name = "RCV_ADDR", example = "email, 010-111-111 등 ", description = "받는사람 주소")
		String RCV_ADDR = "";
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_EML_SND_SAVE")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_CM_EML_SND_SAVE", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}

	@ApiModel(value="OUT_DATA_ROW-BR_CM_EML_SND_SAVE")
	@Data
	static class OUT_DATA_ROW {
		
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA", title="OUT_DATA-BR_CM_EML_SND_SAVE", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();

	}
	
	@Autowired
	DA_CM_EML_SND daEmlSnd;

	@Autowired
	DA_CM_EML_SND_RCV daEmlSndRcv;

	@Autowired
	DA_CM_SEQ daCmSeq;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_EML"},value = "이메일을 전송한다..", notes = "")
	@PostMapping(path= "/api/BR_CM_EML_SND_SAVE", consumes = "application/json", produces = "application/json")
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
		String  SND_TYPE_CD 	= PjtUtil.str(rs.SND_TYPE_CD);
		String  TTL				= PjtUtil.str(rs.TTL);
		String  CNTNT				= PjtUtil.str(rs.CNTNT);
		String  SNDR_NM			= PjtUtil.str(rs.SNDR_NM);
		String  SNDR_ADDR		= PjtUtil.str(rs.SNDR_ADDR);
		String  SND_DTM			= PjtUtil.str(rs.SND_DTM);

		Date D_SND_DTM  = null;

		if(PjtUtil.isEmpty(SND_TYPE_CD)) {
			throw new BizRuntimeException("발송타입이 입력되지 않았습니다.");
		}
		if(PjtUtil.isEmpty(SNDR_ADDR)) {
			throw new BizRuntimeException("발송주소가 입력되지 않았습니다.");
		}


		if(PjtUtil.isEmpty(TTL)) {
			throw new BizRuntimeException("TTL가 입력되지 않았습니다.");
		}

		if(PjtUtil.isEmpty(CNTNT)) {
			throw new BizRuntimeException("CNTNT가 입력되지 않았습니다.");
		}
		//날짜변환은 나중에 하자.

		Long L_SND_SEQ =daCmSeq.increate("CM_MSG_SND_SND_SEQ");				
				
		daEmlSnd.crtEmlSnd(					
			 L_SND_SEQ
			, TTL
			, CNTNT
			, SNDR_NM
			, SNDR_ADDR
			, SND_TYPE_CD
			,D_SND_DTM
			,L_SESSION_USER_NO
		);

		for(int i=0;i<inDS.RCV_DATA.size();i++){
			RCV_DATA_ROW  rcv_rs =inDS.RCV_DATA.get(i);
			String  RCV_ADDR 	= PjtUtil.str(rcv_rs.RCV_ADDR);
			String  RCV_NM 	= PjtUtil.str(rcv_rs.RCV_NM);

			if(PjtUtil.isEmpty(RCV_ADDR)) {
				throw new BizRuntimeException("받는 주소가 입력되지 않았습니다.");
			}

			daEmlSndRcv.crtEmlSndRcv(					
				L_SND_SEQ
			   , i
			   , RCV_ADDR
			   , RCV_NM
			   ,L_SESSION_USER_NO
		   );
		
		}


		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
}
