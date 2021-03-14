package com.example.demo.br.cm.cm_oauth;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.cm.DA_CM_OAUTH_LOG;
import com.example.demo.db.domain.cm.CmOauthLog;
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

@Tag(name = "CM_USER", description = "사용자정보")
@Slf4j
@RestController
public class BR_CM_OAUTH_LOG_FIND {

	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_CM_OAUTH_LOG_FIND")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;
		
		@JsonProperty("LSESSION")
		LSESSION_ROW LSESSION;
	}

	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_OAUTH_LOG_FIND")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA", title="OUT_DATA-BR_CM_OAUTH_LOG_FIND", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}

	@ApiModel(value="OUT_DATA_ROW-BR_CM_OAUTH_LOG_FIND")
	@Data
	static class OUT_DATA_ROW {
		@JsonProperty("SEQ_NO")
		@Schema(name = "SEQ_NO", example = "1", description = "SEQ_NO")
		Long SEQ_NO = null;
		
		@JsonProperty("GBN_ID")
		@Schema(name = "GBN_ID", example = "google", description = "구분ID")
		String GBN_ID = null;
		
		@JsonProperty("AUTH_ID")
		@Schema(name = "AUTH_ID", example = "4616656", description = "인증ID")
		String AUTH_ID = null;
		
		@JsonProperty("NCK_NM")
		@Schema(name = "NCK_NM", example = "가자", description = "닉네임")
		String NCK_NM = null;
		
		@JsonProperty("PRF_IMG")
		@Schema(name = "PRF_IMG", example = "abc.jpg", description = "프로필이미지")
		String PRF_IMG = null;

		@JsonProperty("THUMB_IMG")
		@Schema(name = "THUMB_IMG", example = "abc.jpg", description = "썸네일이미지")
		String THUMB_IMG = null;

		@JsonProperty("EML")
		@Schema(name = "EML", example = "abc@sadf.cocm", description = "이메일")
		String EML = null;

		@JsonProperty("BRTH_DAY")
		@Schema(name = "BRTH_DAY", example = "1231(MMDD)", description = "생일")
		String BRTH_DAY = null;

		@JsonProperty("GNDR")
		@Schema(name = "GNDR", example = "MALE,FEMALE", description = "GNDR")
		String GNDR = null;		
		
		@JsonProperty("CRT_DTM")
		@Schema(name = "CRT_DTM", example = "202012311640", description = "생성일시")
		String CRT_DTM = null;		
	}
	
	@Autowired
	DA_CM_OAUTH_LOG daCmOathLog;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_USER"},value = "사용자 조회.", notes = "")
	@PostMapping(path= "/api/BR_CM_OAUTH_LOG_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS  run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.LSESSION==null) {
			throw new BizRuntimeException("세션값이 넘어오지 않았습니다1.");
		}
		
		List<CmOauthLog>  al =daCmOathLog.findCmOauthLogCmCd();
		OUT_DS outDs = new OUT_DS();
		for(int i=0;i<al.size();i++) {
			CmOauthLog cm=al.get(i);
			OUT_DATA_ROW  row = new OUT_DATA_ROW();
			row.SEQ_NO=cm.getSeqNo();
			row.GBN_ID=cm.getGbnId();
			row.AUTH_ID=cm.getAuthId();
			row.NCK_NM=cm.getNckNm();
			row.PRF_IMG=cm.getPrfImg();
			row.THUMB_IMG=cm.getThmbImg();
			row.EML=cm.getEml();			
			row.BRTH_DAY=cm.getBrthday();		
			row.GNDR=cm.getGndr();		
			row.CRT_DTM=PjtUtil.getYyyy_MM_dd_HHMMSS(cm.getCrtDtm());
			outDs.OUT_DATA.add(row);
		}
		return outDs;
	}
}
