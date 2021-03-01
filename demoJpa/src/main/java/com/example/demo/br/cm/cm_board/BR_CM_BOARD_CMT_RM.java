package com.example.demo.br.cm.cm_board;

import java.util.ArrayList;

import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.cm.DA_CM_BOARD_CMT;
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

@Tag(name = "CM_BOARD", description = "공통게시판")
@Slf4j
@RestController
public class BR_CM_BOARD_CMT_RM {

	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_CM_BOARD_CMT_RM")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CM_BOARD_CMT_RM", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
		
		@JsonProperty("LSESSION")
		LSESSION_ROW LSESSION;
	}

	@ApiModel(value="IN_DATA_ROW-BR_CM_BOARD_CMT_RM")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("CMT_SEQ")
		@Schema(name = "CMT_SEQ", example = "1", description = "댓글SEQ")
		String CMT_SEQ = "";
	}

	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_BOARD_CMT_RM")
	@Data
	static class OUT_DS {
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}

	@Autowired
	DA_CM_BOARD_CMT daBrdCmt;
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_BOARD"},value = "댓글 삭제", notes = "")
	@PostMapping(path= "/api/BR_CM_BOARD_CMT_RM", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.LSESSION==null) {
			throw new BizRuntimeException("세션값이 넘어오지 않았습니다1.");
		}
		
		String USER_NO =inDS.LSESSION.getUSER_NO();
		if(PjtUtil.isEmpty(USER_NO)) {
			throw new BizRuntimeException("사용자NO가 넘어오지 않았습니다2.");
		}
		Long L_USER_NO = Long.parseLong(USER_NO);
		for( int i=0;i<inDS.IN_DATA.size();i++) {
			IN_DATA_ROW  rs =inDS.IN_DATA.get(i);
			String  CMT_SEQ 		= PjtUtil.str(rs.CMT_SEQ);
			if(PjtUtil.isEmpty(CMT_SEQ)) {
				throw new BizRuntimeException("CMT_SEQ가 입력되지 않았습니다.");
			}
			long L_CMT_SEQ = Long.parseLong(CMT_SEQ);
			daBrdCmt.rmBrdCmt(
				L_CMT_SEQ
					);
		}
	
		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
}
