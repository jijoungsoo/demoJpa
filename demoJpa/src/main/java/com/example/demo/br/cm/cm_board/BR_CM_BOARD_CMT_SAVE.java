package com.example.demo.br.cm.cm_board;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.cm.DA_CM_BOARD;
import com.example.demo.db.da.cm.DA_CM_BOARD_CMT;
import com.example.demo.db.da.cm.DA_CM_SEQ;
import com.example.demo.db.domain.cm.CmBoard;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
public class BR_CM_BOARD_CMT_SAVE {

	@JsonRootName("IN_DS")
	@ApiModel(value="OUT_DS-BR_CM_BOARD_CMT_SAVE")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CM_BOARD_CMT_SAVE", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
		
		@JsonProperty("LSESSION")
		LSESSION_ROW LSESSION;		
	}

	@ApiModel(value="IN_DATA_ROW-BR_CM_BOARD_CMT_SAVE")
	@Data
	static class IN_DATA_ROW {

		@JsonProperty("BRD_SEQ")
		@Schema(name = "BRD_SEQ", example = "1", description = "BRD_SEQ")
		String BRD_SEQ = null;

		@JsonProperty("CMT_SEQ")
		@Schema(name = "CMT_SEQ", example = "1", description = "댓글SEQ")
		String CMT_SEQ = null;

		@JsonProperty("PRNT_CMT_SEQ")
		@Schema(name = "PRNT_CMT_SEQ", example = "1", description = "댓글일때 부모 SEQ")
		String PRNT_CMT_SEQ = null;

		@JsonProperty("CNTNT")
		@Schema(name = "CNTNT", example = "내용HTML", description = "내용")
		String CNTNT = null;
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_BOARD_CMT_SAVE")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA", title="OUT_DATA-BR_CM_BOARD_CMT_SAVE", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}
	
	@Autowired
	DA_CM_BOARD daBrd;

	@Autowired
	DA_CM_BOARD_CMT daBrdCmt;
	
	@Autowired
	DA_CM_SEQ daCmSeq;
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_BOARD"},value = "댓글을 저장한다.", notes = "")
	//@PostMapping(path= "/api/BR_CM_BOARD_CMT_SAVE", consumes = "application/json", produces = "application/json")
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
			IN_DATA_ROW  rs 	=inDS.IN_DATA.get(i);			
			String  CNTNT 		= PjtUtil.str(rs.CNTNT);
			String  CMT_SEQ 	= PjtUtil.str(rs.CMT_SEQ);
			String  BRD_SEQ 	= PjtUtil.str(rs.BRD_SEQ);
			String  PRNT_CMT_SEQ= PjtUtil.str(rs.PRNT_CMT_SEQ);
			Document doc = Jsoup.parse(CNTNT);
			String  CNTNT_TEXT 		= doc.text();

			if(PjtUtil.isEmpty(CNTNT)) {
				throw new BizRuntimeException("내용이 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(BRD_SEQ)) {
				throw new BizRuntimeException("게시물번호가 입력되지 않았습니다.");
			}

			Long L_BRD_SEQ = Long.parseLong(BRD_SEQ);
			List<CmBoard> al = daBrd.findBrdByBrdSeq(L_BRD_SEQ);
			if(al.size()==0) {
				throw new BizException("["+BRD_SEQ+"] 게시물이 존재하지 않습니다.[수정X]");
			}

			if(PjtUtil.isEmpty(CMT_SEQ)==true){
				Long L_CMT_SEQ =daCmSeq.increate("CM_BRD_CMT_CMT_SEQ");				

				Long L_PRNT_CMT_SEQ = 0L;//부모댓글이 있을때 부모 SEQ 없으면 0
				if(PjtUtil.isEmpty(PRNT_CMT_SEQ)==false){
					L_PRNT_CMT_SEQ = Long.parseLong(PRNT_CMT_SEQ);  
				}
							
				daBrdCmt.crtBrdCmt(					
						 L_BRD_SEQ
						, L_CMT_SEQ
						, L_PRNT_CMT_SEQ
						, CNTNT
						, CNTNT_TEXT
						, L_SESSION_USER_NO
						);
			} else {
				Long L_CMT_SEQ_SEQ = Long.parseLong(CMT_SEQ);
				daBrdCmt.updtBrdCmt(					
						  L_CMT_SEQ_SEQ
						, CNTNT
						, CNTNT_TEXT
						, L_SESSION_USER_NO
						);

			}
			
		}
		
		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
}
