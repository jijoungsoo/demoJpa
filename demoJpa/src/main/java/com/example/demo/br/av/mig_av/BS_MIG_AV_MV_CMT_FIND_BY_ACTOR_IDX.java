package com.example.demo.br.av.mig_av;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.anotation.OpService;
import com.example.demo.db.da.mig_av.DA_MIG_AV_MV_CMT;
import com.example.demo.db.domain.mig_av.MigAvMvCmt;
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

@Tag(name = "AV", description = "AV정보")
@Slf4j
@OpService
@Service
public class BS_MIG_AV_MV_CMT_FIND_BY_ACTOR_IDX {
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BS_MIG_AV_MV_CMT_FIND_BY_ACTOR_IDX")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		
		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BS_MIG_AV_MV_CMT_FIND_BY_ACTOR_IDX", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
	}

	@ApiModel(value="IN_DATA_ROW-BS_MIG_AV_MV_CMT_FIND_BY_ACTOR_IDX")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("ACTOR_IDX")
		@Schema(name = "ACTOR_IDX", example = "1", description = "ACTOR_IDX")
		String ACTOR_IDX = "";

		@JsonProperty("DVD_IDX")
		@Schema(name = "DVD_IDX", example = "1", description = "DVD_IDX")
		String DVD_IDX = "";
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BS_MIG_AV_MV_CMT_FIND_BY_ACTOR_IDX")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BS_MIG_AV_MV_CMT_FIND_BY_ACTOR_IDX", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}

	@ApiModel(value="OUT_DATA_ROW-BS_MIG_AV_MV_CMT_FIND_BY_ACTOR_IDX")
	@Data
	static class OUT_DATA_ROW {
		@JsonProperty("ACTOR_IDX")
		@Schema(name = "ACTOR_IDX", example = "1", description = "배우IDX")
		Long ACTOR_IDX = null;

		@JsonProperty("DVD_IDX")
		@Schema(name = "DVD_IDX", example = "1", description = "dvd IDX")
		Long DVD_IDX = null;


		@JsonProperty("CMT_IDX")
		@Schema(name = "CMT_IDX", example = "1", description = "코멘트IDX")
		Long CMT_IDX = null;
		@JsonProperty("CMT")
		@Schema(name = "CMT", example = "무궁화꽃이", description = "코멘트")
		String CMT = null;

		@JsonProperty("WRITER")
		@Schema(name = "WRITER", example = "홍길동", description = "작성자")
		String WRITER = null;

		@JsonProperty("LK_CNT")
		@Schema(name = "LK_CNT", example = "1", description = "좋아요")
		Long LK_CNT = null;

		@JsonProperty("DSLK_CNT")
		@Schema(name = "DSLK_CNT", example = "1", description = "싫어요")
		Long DSLK_CNT = null;
		
		@JsonProperty("CRT_DTM")
		@Schema(name = "CRT_DTM", example = "202012311640", description = "생성일시")
		String CRT_DTM = null;
	}
	
	@Autowired
	DA_MIG_AV_MV_CMT daMigAvMvCmt;

	@Autowired
    PjtUtil pjtU;
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"AV"}, value = "AV배우 하나를 조회한다.", notes = "페이징 처리")
	//@PostMapping(path= "/api/BS_MIG_AV_ACTR_CMT_FIND_BY_ACTOR_IDX", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.IN_DATA==null) {
			throw new BizRuntimeException("[IN_DATA]입력파라미터가 전달되지 않았습니다.");
		}
		if(inDS.IN_DATA.size()!=1) {
			throw new BizRuntimeException("[IN_DATA]입력파라미터의 ["+inDS.IN_DATA.size()+"]행수가 잘못되었습니다.");
		}
		
		IN_DATA_ROW  rs =inDS.IN_DATA.get(0);
		String  ACTOR_IDX 		= pjtU.str(rs.ACTOR_IDX);

		if(pjtU.isEmpty(ACTOR_IDX)){
			throw new BizRuntimeException("ACTOR_IDX가 전달되지 않았습니다.");
		}
		Long L_ACTOR_IDX  = Long.parseLong(ACTOR_IDX);
		List<MigAvMvCmt> al = daMigAvMvCmt.findMigAvMvCmtByActorIdx(L_ACTOR_IDX);

		OUT_DS outDs = new OUT_DS();
		for(int i=0;i<al.size();i++){
			OUT_DATA_ROW row = new OUT_DATA_ROW();
			MigAvMvCmt c= al.get(i);
			row.ACTOR_IDX = c.getActorIdx();
			row.DVD_IDX = c.getDvdIdx();
			row.CMT_IDX = c.getCmtIdx();
			row.CMT = c.getCmt();
			row.LK_CNT = c.getLkCnt();
			row.DSLK_CNT = c.getDslkCnt();
			
			row.WRITER = c.getWriter();
			row.CRT_DTM = pjtU.getYyyy_MM_dd_HHMMSS(c.getCrtDtm());
			outDs.OUT_DATA.add(row);
		}
	
		return outDs;
	}
}
