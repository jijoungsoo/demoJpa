package com.example.demo.br.cm.cm_seq;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.cm.DA_CM_SEQ;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Tag(name = "CM_SEQ", description = "시퀀스")
public class BR_CM_SEQ_RM {

	@JsonRootName("IN_DS")
	@Schema(name="IN_DS",title="IN_DS-BR_CM_SEQ_RM")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "", description = "출력 데이터명")
		String brRs;
		
		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CM_SEQ_RM", description = "출력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
		
		@JsonProperty("LSESSION")
		@Schema(name = "LSESSION", description = "세션데이터")
		LSESSION_ROW LSESSION;
	}

	@JsonRootName("OUT_DS")
	@Schema(name="OUT_DS",title = "OUT_DS-BR_CM_SEQ_RM")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA", title="OUT_DATA-BR_CM_SEQ_RM", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}

	@Schema(name="IN_DATA_ROW",title = "IN_DATA_ROW-BR_CM_SEQ_RM")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("SEQ_NM")
		@Schema(name = "SEQ_NM", example = "1", description = "프로그램NO")
		String SEQ_NM = null;
	}
	
	@Autowired
	DA_CM_SEQ daCmSeq;

	@Operation(summary = "시퀀스 삭제.", description = "")
	@PostMapping(path= "/api/BR_CM_SEQ_RM", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.LSESSION==null) {
			throw new BizRuntimeException("세션값이 넘어오지 않았습니다1.");
		}
		String USER_NO =inDS.LSESSION.getUSER_NO();
		if(PjtUtil.isEmpty(USER_NO)) {
			throw new BizRuntimeException("사용자NO가 넘어오지 않았습니다2.");
		}
		Long L_USER_NO = Long.parseLong(USER_NO);
		
		for (int i = 0; i < inDS.IN_DATA.size(); i++) {
			IN_DATA_ROW rs = inDS.IN_DATA.get(i);
			String SEQ_NM = PjtUtil.str(rs.SEQ_NM);
			if (PjtUtil.isEmpty(SEQ_NM)) {
				throw new BizRuntimeException("시퀀스명이 선택되지 않았습니다.");
			}
			daCmSeq.rmCmSeq(SEQ_NM);
		}

		OUT_DS outDs = new OUT_DS();
		return outDs;
	}
}
