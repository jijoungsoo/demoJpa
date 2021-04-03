package com.example.demo.br.av.av_actr;

import java.util.ArrayList;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.av.DA_AV_ACTR;
import com.example.demo.db.da.cm.DA_CM_SEQ;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
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

@Tag(name = "AV", description = "AV정보")
@Slf4j
@OpService
@Service
public class BR_AV_ACTR_SAVE {

	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_AV_ACTR_SAVE")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-UPDT_DATA", description = "입력 데이터")
		ArrayList<DATA_ROW> IN_DATA = new ArrayList<DATA_ROW>();
		
		@JsonProperty("UPDT_DATA")
		@Schema(name="IN_DATA-UPDT_DATA", description = "수정 데이터")
		ArrayList<DATA_ROW> UPDT_DATA = new ArrayList<DATA_ROW>();
		
		@JsonProperty("LSESSION")
		@Schema(name = "LSESSION",title="LSESSION-UPDT_DATA", description = "세션데이터")
		LSESSION_ROW LSESSION;
	}

	@ApiModel(value="DATA_ROW-BR_AV_ACTR_SAVE")
	@Data
	static class DATA_ROW {
		@JsonProperty("ACTR_SEQ")
		@Schema(name = "ACTR_SEQ", example = "1", description = "배우SEQ")
		Long ACTR_SEQ = null;
		@JsonProperty("ACTR_NM_KR")
		@Schema(name = "ACTR_NM_KR", example = "배우명KR", description = "배우명KR")
		String ACTR_NM_KR = null;
		@JsonProperty("ACTR_NM_JP")
		@Schema(name = "ACTR_NM_JP", example = "배우명JP", description = "배우명JP")
		String ACTR_NM_JP = null;
		@JsonProperty("ACTR_NM_ENG")
		@Schema(name = "ACTR_NM_ENG", example = "배우명ENG", description = "배우명ENG")
		String ACTR_NM_ENG = null;
		@JsonProperty("BIRTH_DT")
		@Schema(name = "BIRTH_DT", example = "20201231", description = "BIRTH_DT")
		String BIRTH_DT = null;
		@JsonProperty("AGE")
		@Schema(name = "AGE", example = "20", description = "AGE")
		String AGE = null;
		@JsonProperty("SEX")
		@Schema(name = "SEX", example = "(F=여자,M-남자)", description = "SEX")
		String SEX = null;
		@JsonProperty("RNK")
		@Schema(name = "RNK", example = "1", description = "배우순위")
		String RNK = null;
		@JsonProperty("RMK")
		@Schema(name = "RMK", example = "비고", description = "비고")
		String RMK = null;
		@JsonProperty("MSC_YN")
		@Schema(name = "MSC_YN", example = "(Y-모자이크있음, N-모자이크 없음)", description = "모자이크작품존재여부")
		String MSC_YN = null;
		@JsonProperty("ORD")
		@Schema(name = "ORD", example = "001", description = "정렬")
		String ORD = null;
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_AV_ACTR_SAVE")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA", title="OUT_DATA-UPDT_DATA", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}
	
	@Autowired
	DA_AV_ACTR daAvActr;
	
	@Autowired
	DA_CM_SEQ daCmSeq;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"AV"},value = "AV배우를 저장한다.", notes = "")
	//@PostMapping(path= "/api/BR_AV_ACTR_SAVE", consumes = "application/json", produces = "application/json")
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
			DATA_ROW  rs =inDS.IN_DATA.get(i);
			String  ACTR_NM_KR 	= PjtUtil.str(rs.ACTR_NM_KR);
			String  ACTR_NM_JP 	= PjtUtil.str(rs.ACTR_NM_JP);
			String  ACTR_NM_ENG = PjtUtil.str(rs.ACTR_NM_ENG);
			String  BIRTH_DT 	= PjtUtil.str(rs.BIRTH_DT);
			String  SEX 		= PjtUtil.str(rs.SEX);
			String  RNK 		= PjtUtil.str(rs.RNK);
			String  ORD 		= PjtUtil.str(rs.ORD);
			String  RMK 		= PjtUtil.str(rs.RMK);
			String  MSC_YN 		= PjtUtil.str(rs.MSC_YN);
			
			
			if(PjtUtil.isEmpty(ACTR_NM_KR)) {
				throw new BizRuntimeException("배우명(한굴)이 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(SEX)) {
				throw new BizRuntimeException("성별이 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(MSC_YN)) {
				throw new BizRuntimeException("모자이크 여부가 입력되지 않았습니다.");
			}			
			
			long L_ACTR_SEQ =daCmSeq.increate("AV_ACTR_ACTR_SEQ");
			Integer I_RNK = Integer.parseInt(RNK);
			Integer I_ORD = Integer.parseInt(ORD);
			daAvActr.createAvActr(
					L_ACTR_SEQ
					,ACTR_NM_KR
					,ACTR_NM_JP
					,ACTR_NM_ENG
					,BIRTH_DT
					,SEX
					,I_RNK
					,I_ORD
					,RMK
					,MSC_YN
					,L_SESSION_USER_NO
					);
		}
		
		for( int i=0;i<inDS.UPDT_DATA.size();i++) {
			DATA_ROW  rs =inDS.UPDT_DATA.get(i);
			String  ACTR_SEQ 	= PjtUtil.str(rs.ACTR_SEQ);
			String  ACTR_NM_KR 	= PjtUtil.str(rs.ACTR_NM_KR);
			String  ACTR_NM_JP 	= PjtUtil.str(rs.ACTR_NM_JP);
			String  ACTR_NM_ENG = PjtUtil.str(rs.ACTR_NM_ENG);
			String  BIRTH_DT 	= PjtUtil.str(rs.BIRTH_DT);
			String  SEX 		= PjtUtil.str(rs.SEX);
			String  RNK 		= PjtUtil.str(rs.RNK);		
			String  ORD 		= PjtUtil.str(rs.ORD);
			String  RMK 		= PjtUtil.str(rs.RMK);
			String  MSC_YN	 	= PjtUtil.str(rs.MSC_YN);
	
			if(PjtUtil.isEmpty(ACTR_SEQ)) {
				throw new BizRuntimeException("배우일련번호가 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(ACTR_NM_KR)) {
				throw new BizRuntimeException("배우명(한글)이 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(SEX)) {
				throw new BizRuntimeException("성별이 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(MSC_YN)) {
				throw new BizRuntimeException("모자이크 여부가 입력되지 않았습니다.");
			}
			
			long L_ACTR_SEQ = Long.parseLong(ACTR_SEQ);
			Integer I_RNK = Integer.parseInt(RNK);
			Integer I_ORD = Integer.parseInt(ORD);

			daAvActr.updateAvActr(
					L_ACTR_SEQ
					,ACTR_NM_KR
					,ACTR_NM_JP
					,ACTR_NM_ENG
					,BIRTH_DT
					,SEX
					,I_RNK
					,I_ORD
					,RMK
					,MSC_YN
					,L_SESSION_USER_NO
					);
		}

		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
}
