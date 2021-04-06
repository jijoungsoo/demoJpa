package com.example.demo.br.cm.cm_board;

import java.util.ArrayList;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.cm.DA_CM_BOARD_GROUP;
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

@Tag(name = "CM_BOARD", description = "공통게시판")
@Slf4j
@OpService
@Service
public class BR_CM_BOARD_GROUP_RM {

	@Autowired
    PjtUtil pjtU;

	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_CM_BOARD_GROUP_RM")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CM_BOARD_GROUP_RM", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
		
		@JsonProperty("LSESSION")
		LSESSION_ROW LSESSION;
	}

	@ApiModel(value="IN_DATA_ROW-BR_CM_BOARD_GROUP_RM")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("GRP_SEQ")
		@Schema(name = "GRP_SEQ", example = "1", description = "게시판관리번호")
		String GRP_SEQ = "";
	}

	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_BOARD_GROUP_RM")
	@Data
	static class OUT_DS {
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}

	@Autowired
	DA_CM_BOARD_GROUP daBrdGrp;
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_BOARD"},value = "공통게시판.", notes = "")
	//@PostMapping(path= "/api/BR_CM_BOARD_GROUP_RM", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.LSESSION==null) {
			throw new BizRuntimeException("세션값이 넘어오지 않았습니다1.");
		}
		
		String USER_NO =inDS.LSESSION.getUSER_NO();
		if(pjtU.isEmpty(USER_NO)) {
			throw new BizRuntimeException("사용자NO가 넘어오지 않았습니다2.");
		}
		Long L_USER_NO = Long.parseLong(USER_NO);
		for( int i=0;i<inDS.IN_DATA.size();i++) {
			IN_DATA_ROW  rs =inDS.IN_DATA.get(i);
			String  GRP_SEQ 		= pjtU.str(rs.GRP_SEQ);
			if(pjtU.isEmpty(GRP_SEQ)) {
				throw new BizRuntimeException("게시판관리번호가 입력되지 않았습니다.");
			}
			long L_GRP_SEQ = Long.parseLong(GRP_SEQ);
			daBrdGrp.rmBrdGrp(
				L_GRP_SEQ
					);
		}
	
		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
}
