package com.example.demo.br.cm.cm_seq;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.cm.DA_CM_SEQ;
import com.example.demo.db.domain.cm.CmSeq;
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

@Tag(name = "CM_SEQ", description = "시퀀스")
@Slf4j
@OpService
@Service
public class BR_CM_SEQ_FIND {

	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_CM_SEQ_FIND")
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
	@ApiModel(value="OUT_DS-BR_CM_SEQ_FIND")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_CM_SEQ_FIND", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}

	@ApiModel(value = "OUT_DATA_ROW-BR_CM_SEQ_FIND")
	@Data
	static class OUT_DATA_ROW {
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
	
	@Autowired
	DA_CM_SEQ daCmSeq;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_SEQ"},value = "시퀀스 조회", notes = "")
	//@PostMapping(path= "/api/BR_CM_SEQ_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.LSESSION==null) {
			throw new BizRuntimeException("세션값이 넘어오지 않았습니다1.");
		}
		String USER_NO =inDS.LSESSION.getUSER_NO();
		if(PjtUtil.isEmpty(USER_NO)) {
			throw new BizRuntimeException("사용자NO가 넘어오지 않았습니다2.");
		}
		Long L_USER_NO = Long.parseLong(USER_NO);
		
		List<CmSeq> al = daCmSeq.findCmSeq();
		OUT_DS outDs = new OUT_DS();
		for (int i = 0; i < al.size(); i++) {
			CmSeq cm = al.get(i);
			OUT_DATA_ROW row = new OUT_DATA_ROW();
			row.SEQ_NM= cm.getSeqNm();
			row.SEQ_NO= String.valueOf(cm.getSeqNo());
			row.TB_NM= cm.getTbNm();
			row.COL_NM= cm.getColNm();
			row.INIT_VAL= String.valueOf(cm.getInitVal());
			row.ALLOCATION_SIZE= String.valueOf(cm.getAllocationSize());
			row.CRT_DTM= PjtUtil.getYyyyMMddHHMMSS(cm.getCrtDtm());
			row.UPDT_DTM= PjtUtil.getYyyyMMddHHMMSS(cm.getUpdtDtm());
			outDs.OUT_DATA.add(row);
		}
		
		return outDs;
	}

}
