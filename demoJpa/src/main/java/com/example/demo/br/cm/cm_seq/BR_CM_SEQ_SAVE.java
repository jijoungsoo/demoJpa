package com.example.demo.br.cm.cm_seq;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.br.cm.cm_seq.BR_CM_SEQ_FIND.OUT_DS;
import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.cm.DA_CM_SEQ;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "CM_SEQ", description = "시퀀스")
@Slf4j
@RestController
public class BR_CM_SEQ_SAVE {

	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_CM_SEQ_SAVE")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CM_SEQ_SAVE", description = "입력 데이터")
		ArrayList<DATA_ROW> IN_DATA = new ArrayList<DATA_ROW>();
		
		@JsonProperty("UPDT_DATA")
		@Schema(name="UPDT_DATA-BR_CM_SEQ_SAVE", description = "수정 데이터")
		ArrayList<DATA_ROW> UPDT_DATA = new ArrayList<DATA_ROW>();
		
		@JsonProperty("LSESSION")
		LSESSION_ROW LSESSION;
	}

	@ApiModel(value="DATA_ROW-BR_CM_SEQ_SAVE")
	@Data
	static class DATA_ROW {
		@JsonProperty("SEQ_NM")
		@Schema(name = "SEQ_NM", example = "CM_MENU_MENU_SEQ", description = "시퀀스명(키)")
		String SEQ_NM = null;
		@JsonProperty("SEQ_NO")
		@Schema(name = "SEQ_NO", example = "1", description = "시퀀스(유지되는숫자)")
		String SEQ_NO = null;
		@JsonProperty("TB_NM")
		@Schema(name = "TB_NM", example = "TB_CM_MENU", description = "시퀀스 적용되는 테이블명")
		String TB_NM = null;
		@JsonProperty("COL_NM")
		@Schema(name = "COL_NM", example = "MENU_SEQ", description = "시퀀스 적용되는 컬럼")
		String COL_NM = null;
		@JsonProperty("INIT_VAL")
		@Schema(name = "INIT_VAL", example = "초기값", description = "초기값")
		String INIT_VAL = null;
		@JsonProperty("ALLOCATION_SIZE")
		@Schema(name = "ALLOCATION_SIZE", example = "1", description = "증가값")
		String ALLOCATION_SIZE = null;
		
		
		@JsonProperty("CRT_DTM")
		@Schema(name = "CRT_DTM", example = "202012311210", description = "CRT_DTM")
		String CRT_DTM = null;
		@JsonProperty("UPDT_DTM")
		@Schema(name = "UPDT_DTM", example = "202012311210", description = "UPDT_DTM")
		String UPDT_DTM = null;
	
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_SEQ_SAVE")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema( name="OUT_DATA-BR_CM_SEQ_SAVE", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}
	
	@Autowired
	DA_CM_SEQ daCmSeq;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_SEQ"},value = "시퀀스 저장.", notes = "")
	@PostMapping(path= "/api/BR_CM_SEQ_SAVE", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		for (int i = 0; i < inDS.IN_DATA.size(); i++) {
			DATA_ROW rs = inDS.IN_DATA.get(i);
			String SEQ_NM = PjtUtil.str(rs.SEQ_NM);
			String SEQ_NO = PjtUtil.str(rs.SEQ_NO);
			String TB_NM = PjtUtil.str(rs.TB_NM);
			String COL_NM = PjtUtil.str(rs.COL_NM);
			String INIT_VAL = PjtUtil.str(rs.INIT_VAL);
			String ALLOCATION_SIZE = PjtUtil.str(rs.ALLOCATION_SIZE);

			if (PjtUtil.isEmpty(SEQ_NM)) {
				throw new BizRuntimeException("시퀀스명이 입력되지 않았습니다.");
			}
			if (PjtUtil.isEmpty(SEQ_NO)) {
				throw new BizRuntimeException("시퀀스번호가 입력되지 않았습니다.");
			}
			if (PjtUtil.isEmpty(TB_NM)) {
				throw new BizRuntimeException("테이블명이 입력되지 않았습니다.");
			}
			if (PjtUtil.isEmpty(COL_NM)) {
				throw new BizRuntimeException("컬럼명이 입력되지 않았습니다.");
			}
			if (PjtUtil.isEmpty(INIT_VAL)) {
				throw new BizRuntimeException("초기값이 입력되지 않았습니다.");
			}
			if (PjtUtil.isEmpty(ALLOCATION_SIZE)) {
				throw new BizRuntimeException("증가값이 입력되지 않았습니다.");
			}

			long L_SEQ_NO = Long.parseLong(SEQ_NO);
			Integer I_INIT_VAL = Integer.parseInt(INIT_VAL);
			Integer I_ALLOCATION_SIZE = Integer.parseInt(ALLOCATION_SIZE);
			
			daCmSeq.saveCmSeq(SEQ_NM, L_SEQ_NO, TB_NM, COL_NM, I_INIT_VAL, I_ALLOCATION_SIZE);
		}

		for (int i = 0; i < inDS.UPDT_DATA.size(); i++) {
			DATA_ROW rs = inDS.UPDT_DATA.get(i);
			String SEQ_NM = PjtUtil.str(rs.SEQ_NM);
			String SEQ_NO = PjtUtil.str(rs.SEQ_NO);
			String TB_NM = PjtUtil.str(rs.TB_NM);
			String COL_NM = PjtUtil.str(rs.COL_NM);
			String INIT_VAL = PjtUtil.str(rs.INIT_VAL);
			String ALLOCATION_SIZE = PjtUtil.str(rs.ALLOCATION_SIZE);
			
			if (PjtUtil.isEmpty(SEQ_NM)) {
				throw new BizRuntimeException("시퀀스명이 입력되지 않았습니다.");
			}
			if (PjtUtil.isEmpty(SEQ_NO)) {
				throw new BizRuntimeException("시퀀스번호가 입력되지 않았습니다.");
			}
			if (PjtUtil.isEmpty(TB_NM)) {
				throw new BizRuntimeException("테이블명이 입력되지 않았습니다.");
			}
			if (PjtUtil.isEmpty(COL_NM)) {
				throw new BizRuntimeException("컬럼명이 입력되지 않았습니다.");
			}
			if (PjtUtil.isEmpty(INIT_VAL)) {
				throw new BizRuntimeException("초기값이 입력되지 않았습니다.");
			}
			if (PjtUtil.isEmpty(ALLOCATION_SIZE)) {
				throw new BizRuntimeException("증가값이 입력되지 않았습니다.");
			}
			
			long L_SEQ_NO = Long.parseLong(SEQ_NO);
			Integer I_INIT_VAL = Integer.parseInt(INIT_VAL);
			Integer I_ALLOCATION_SIZE = Integer.parseInt(ALLOCATION_SIZE);
			
			daCmSeq.saveCmSeq(SEQ_NM, L_SEQ_NO, TB_NM, COL_NM, I_INIT_VAL, I_ALLOCATION_SIZE);
		}
		OUT_DS outDs = new OUT_DS();
		return outDs;
	}
}
