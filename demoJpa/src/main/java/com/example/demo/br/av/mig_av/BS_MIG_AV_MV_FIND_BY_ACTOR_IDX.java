package com.example.demo.br.av.mig_av;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.anotation.OpService;
import com.example.demo.db.da.mig_av.DA_MIG_AV_MV;
import com.example.demo.db.domain.mig_av.MigAvMv;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
import com.example.demo.sa.mig.mig_av.SA_MIG_AV_MV_DTL_GET;
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
public class BS_MIG_AV_MV_FIND_BY_ACTOR_IDX {

	@Autowired
    PjtUtil pjtU;
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BS_MIG_AV_MV_FIND_BY_ACTOR_IDX")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		
		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BS_MIG_AV_MV_FIND_BY_ACTOR_IDX", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
	}

	@ApiModel(value="IN_DATA_ROW-BS_MIG_AV_MV_FIND_BY_ACTOR_IDX")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("ACTOR_IDX")
		@Schema(name = "ACTOR_IDX", example = "1", description = "ACTOR_IDX")
		String ACTOR_IDX = "";
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BS_MIG_AV_MV_FIND_BY_ACTOR_IDX")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BS_MIG_AV_MV_FIND_BY_ACTOR_IDX", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}

	@ApiModel(value="OUT_DATA_ROW-BS_MIG_AV_MV_FIND_BY_ACTOR_IDX")
	@Data
	static class OUT_DATA_ROW {
		@JsonProperty("MAIN_ACTOR_IDX")
		@Schema(name = "MAIN_ACTOR_IDX", example = "1", description = "메인 배우IDX")
		Long MAIN_ACTOR_IDX = null;

		@JsonProperty("DVD_IDX")
		@Schema(name = "DVD_IDX", example = "1", description = "DVD_IDX")
		Long DVD_IDX = null;


		@JsonProperty("MV_NM")
		@Schema(name = "MV_NM", example = "AV111", description = "품번")
		String MV_NM = null;


		@JsonProperty("BEST_YN")
		@Schema(name = "BEST_YN", example = "Y", description = "베스트 작품")
		String BEST_YN = null;
		
		@JsonProperty("TTL_KR")
		@Schema(name = "TTL_KR", example = "야호", description = "작품 타이틀 설명")
		String TTL_KR = null;

		@JsonProperty("OPEN_DT")
		@Schema(name = "OPEN_DT", example = "야호", description = "20201231")
		String OPEN_DT = null;		
	}
	
	@Autowired
	DA_MIG_AV_MV daMigAvMv;

	@Autowired
	SA_MIG_AV_MV_DTL_GET saMigAvMvDtlGet;
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"AV"}, value = "AV배우 MV 조회한다.", notes = "페이징 처리")
	//@PostMapping(path= "/api/BS_MIG_AV_MV_FIND_BY_ACTOR_IDX", consumes = "application/json", produces = "application/json")
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
		List<MigAvMv> al= daMigAvMv.findMigAvMvByActorIdx(L_ACTOR_IDX);

		OUT_DS outDs = new OUT_DS();
		for(int i=0;i<al.size();i++){
			MigAvMv m = al.get(i);
			MigAvMv c =saMigAvMvDtlGet.run(m.getDvdIdx());

			OUT_DATA_ROW row = new OUT_DATA_ROW();
			row.MAIN_ACTOR_IDX = c.getMnActrIdx();
			row.BEST_YN = c.getBestYn();
			row.TTL_KR = c.getTtlKr();
			row.MV_NM = c.getMvNm();
			row.DVD_IDX = c.getDvdIdx();
			row.OPEN_DT = c.getOpenDt();
			outDs.OUT_DATA.add(row);
		}
		return outDs;
	}
}
