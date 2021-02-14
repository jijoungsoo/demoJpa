package com.example.demo.br.cm.cm_board;

import java.util.ArrayList;

import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.cm.DA_CM_BOARD_GROUP;
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

@Tag(name = "CM_BOARD", description = "공통게시판")
@Slf4j
@RestController
public class BR_CM_BOARD_GROUP_SAVE {

	@JsonRootName("IN_DS")
	@ApiModel(value="OUT_DS-BR_CM_BOARD_GROUP_SAVE")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CM_BOARD_GROUP_SAVE", description = "입력 데이터")
		ArrayList<DATA_ROW> IN_DATA = new ArrayList<DATA_ROW>();
		
		@JsonProperty("UPDT_DATA")
		@Schema(name="IN_DATA-BR_CM_BOARD_GROUP_SAVE", description = "수정 데이터")
		ArrayList<DATA_ROW> UPDT_DATA = new ArrayList<DATA_ROW>();
		
		@JsonProperty("LSESSION")
		LSESSION_ROW LSESSION;		
	}

	@ApiModel(value="DATA_ROW-BR_CM_BOARD_GROUP_SAVE")
	@Data
	static class DATA_ROW {
		@JsonProperty("GRP_SEQ")
		@Schema(name = "GRP_SEQ", example = "1", description = "게시판관리번호")
		String GRP_SEQ = null;
		@JsonProperty("GRP_NM")
		@Schema(name = "GRP_NM", example = "일반게시판", description = "게시판관리명")
		String GRP_NM = null;
		@JsonProperty("RMK")
		@Schema(name = "RMK", example = "비고", description = "비고")
		String RMK = null;
		
		@JsonProperty("USE_YN")
		@Schema(name = "USE_YN", example = "Y,N", description = "사용여부")
		String USE_YN = null;
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_BOARD_GROUP_SAVE")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA", title="OUT_DATA-BR_CM_BOARD_GROUP_SAVE", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}
	
	@Autowired
	DA_CM_BOARD_GROUP daBrdGrp;
	
	@Autowired
	DA_CM_SEQ daCmSeq;
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_BOARD"},value = "게시판관리를 저장한다.", notes = "")
	@PostMapping(path= "/api/BR_CM_BOARD_GROUP_SAVE", consumes = "application/json", produces = "application/json")
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
			String  GRP_NM 		= PjtUtil.str(rs.GRP_NM);
			String  USE_YN 		= PjtUtil.str(rs.USE_YN);
			String  RMK 	    = PjtUtil.str(rs.RMK);
			
			if(PjtUtil.isEmpty(GRP_NM)) {
				throw new BizRuntimeException("게시판관리명을 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(USE_YN)) {
				throw new BizRuntimeException("사용ㅇ여부를 입력되지 않았습니다.");
			}
			
			long L_GRP_SEQ =daCmSeq.increate("CM_BRD_GRP_GRP_SEQ");
						
			daBrdGrp.crtBrdGrp(
				L_GRP_SEQ
					,GRP_NM
					,USE_YN
					,RMK
					,L_SESSION_USER_NO
					);
		}
		
		for( int i=0;i<inDS.UPDT_DATA.size();i++) {
			DATA_ROW  rs =inDS.UPDT_DATA.get(i);
			String  GRP_SEQ 		= PjtUtil.str(rs.GRP_SEQ);
			String  GRP_NM 		= PjtUtil.str(rs.GRP_NM);
			String  USE_YN 		= PjtUtil.str(rs.USE_YN);
			String  RMK 	    = PjtUtil.str(rs.RMK);

			if(PjtUtil.isEmpty(GRP_SEQ)) {
				throw new BizRuntimeException("게시판관리번호가 입력되지 않았습니다.");
			}
			
			if(PjtUtil.isEmpty(GRP_NM)) {
				throw new BizRuntimeException("게시판관리명을 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(USE_YN)) {
				throw new BizRuntimeException("사용ㅇ여부를 입력되지 않았습니다.");
			}

			long L_GRP_SEQ = Long.parseLong(GRP_SEQ);
			daBrdGrp.updtBrdGrp(
				L_GRP_SEQ
					,GRP_NM
					,USE_YN
					,RMK
					,L_SESSION_USER_NO
					);
		}

		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
}
