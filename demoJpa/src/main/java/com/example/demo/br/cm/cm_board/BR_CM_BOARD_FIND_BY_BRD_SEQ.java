package com.example.demo.br.cm.cm_board;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.anotation.OpService;
import com.example.demo.db.da.cm.DA_CM_BOARD;
import com.example.demo.db.domain.cm.CmBoard;
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
public class BR_CM_BOARD_FIND_BY_BRD_SEQ {

	@Autowired
    PjtUtil pjtU;

	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_CM_BOARD_FIND_BY_BRD_SEQ")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CM_BOARD_FIND_BY_BRD_SEQ", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
	}

	@ApiModel(value="IN_DATA_ROW-BR_CM_BOARD_FIND_BY_BRD_SEQ")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("BRD_SEQ")
		@Schema(name = "BRD_SEQ", example = "1", description = "게시물번호")
		String BRD_SEQ = "";
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_BOARD_FIND_BY_BRD_SEQ")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_CM_BOARD_FIND_BY_BRD_SEQ", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}

	@ApiModel(value="OUT_DATA_ROW-BR_CM_BOARD_FIND_BY_BRD_SEQ")
	@Data
	static class OUT_DATA_ROW {
		@JsonProperty("GRP_SEQ")
		@Schema(name = "GRP_SEQ", example = "1", description = "게시판관리번호")
		Long GRP_SEQ = null;

		@JsonProperty("BRD_SEQ")
		@Schema(name = "BRD_SEQ", example = "1", description = "BRD_SEQ")
		Long BRD_SEQ = null;

		@JsonProperty("PRNT_BRD_SEQ")
		@Schema(name = "PRNT_BRD_SEQ", example = "1", description = "PRNT_BRD_SEQ")
		Long PRNT_BRD_SEQ = null;


		@JsonProperty("ROOT_BRD_SEQ")
		@Schema(name = "ROOT_BRD_SEQ", example = "1", description = "현재 ROOT BRD_SEQ")
		Long ROOT_BRD_SEQ = null;

		@JsonProperty("BRD_RPLY_ORD")
		@Schema(name = "BRD_RPLY_ORD", example = "1", description = "댓글정렬")
		Long BRD_RPLY_ORD = null;

		@JsonProperty("BRD_DEPTH")
		@Schema(name = "BRD_DEPTH", example = "1", description = "댓글깊이")
		String BRD_DEPTH = null;

		@JsonProperty("TTL")
		@Schema(name = "TTL", example = "안녕하세요", description = "제목")
		String TTL = null;

		@JsonProperty("CNTNT")
		@Schema(name = "CNTNT", example = "내용HTML", description = "내용")
		String CNTNT = null;

		@JsonProperty("DEL_YN")
		@Schema(name = "DEL_YN", example = "Y,N", description = "삭제여부")
		String DEL_YN = null;
				
		@JsonProperty("CRT_USR_NO")
		@Schema(name = "CRT_USR_NO", example = "1", description = "생성자NO")
		String CRT_USR_NO = null;
		
		@JsonProperty("UPDT_USR_NO")
		@Schema(name = "UPDT_USR_NO", example = "1", description = "수정자NO")
		String UPDT_USR_NO = null;
		
		@JsonProperty("CRT_DTM")
		@Schema(name = "CRT_DTM", example = "202012311640", description = "생성일시")
		String CRT_DTM = null;
		
		@JsonProperty("UPDT_DTM")
		@Schema(name = "UPDT_DTM", example = "202012311640", description = "수정일시")
		String UPDT_DTM = null;
	}
	
	@Autowired
	DA_CM_BOARD daBrd;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_BOARD"},value = "게시물을 조회한다.", notes = "")
	//@PostMapping(path= "/api/BR_CM_BOARD_FIND_BY_BRD_SEQ", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.IN_DATA==null) {
			throw new BizRuntimeException("[IN_DATA]입력파라미터가 전달되지 않았습니다.");
		}
		if(inDS.IN_DATA.size()!=1) {
			throw new BizRuntimeException("[IN_DATA]입력파라미터의 ["+inDS.IN_DATA.size()+"]행수가 잘못되었습니다.");
		}
		
		IN_DATA_ROW  rs =inDS.IN_DATA.get(0);
		String  BRD_SEQ 		= pjtU.str(rs.BRD_SEQ);

		if(pjtU.isEmpty(BRD_SEQ)) {
			throw new BizRuntimeException("BRD_SEQ가 넘어오지 않았습니다.");
		}
		Long L_BRD_SEQ = Long.parseLong(BRD_SEQ);		

		List<CmBoard>  al =daBrd.findBrdByBrdSeq(L_BRD_SEQ);
		OUT_DS outDs = new OUT_DS();
		for(int i=0;i<al.size();i++) {
			CmBoard cm=al.get(i);
			OUT_DATA_ROW  row = new OUT_DATA_ROW();
			row.GRP_SEQ= cm.getGrpSeq();
			row.BRD_SEQ= cm.getBrdSeq();
			row.PRNT_BRD_SEQ= cm.getPrntBrdSeq();
			row.ROOT_BRD_SEQ= cm.getRootBrdSeq();
			row.BRD_RPLY_ORD= cm.getBrdRplyOrd();
			row.TTL= cm.getTtl();
			row.CNTNT= cm.getCntnt();
			row.DEL_YN= cm.getDelYn();
			row.CRT_DTM=pjtU.getYyyy_MM_dd_HHMMSS(cm.getCrtDtm());
			row.UPDT_DTM=pjtU.getYyyy_MM_dd_HHMMSS(cm.getUpdtDtm());
			outDs.OUT_DATA.add(row);
		}
		return outDs;
	}
}
