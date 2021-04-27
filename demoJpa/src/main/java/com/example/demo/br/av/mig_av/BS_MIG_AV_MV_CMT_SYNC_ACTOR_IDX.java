package com.example.demo.br.av.mig_av;

import java.util.ArrayList;

import com.example.demo.anotation.OpService;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
import com.example.demo.sa.mig.mig_av.SA_MIG_AV_ACTR_CMT_SYNC;
import com.example.demo.sa.mig.mig_av.SA_MIG_AV_MV_CMT_SYNC;
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
public class BS_MIG_AV_MV_CMT_SYNC_ACTOR_IDX {
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BS_MIG_AV_MV_CMT_SYNC_ACTOR_IDX")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		
		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BS_MIG_AV_MV_CMT_SYNC_ACTOR_IDX", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
	}

	@ApiModel(value="IN_DATA_ROW-BS_MIG_AV_MV_CMT_SYNC_ACTOR_IDX")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("ACTOR_IDX")
		@Schema(name = "ACTOR_IDX", example = "1", description = "ACTOR_IDX")
		String ACTOR_IDX = "";

		@JsonProperty("DVD_IDX")
		@Schema(name = "DVD_IDX", example = "1", description = "DVD_IDX")
		String DVD_IDX = "";


		@JsonProperty("SYNC_YN")
		@Schema(name = "SYNC_YN", example = "Y, N", description = "전체싱크여부")
		String SYNC_YN = "";
		
	}

	@Autowired
    PjtUtil pjtU;
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BS_MIG_AV_MV_CMT_SYNC_ACTOR_IDX")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BS_MIG_AV_MV_CMT_SYNC_ACTOR_IDX", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}

	@Autowired
	SA_MIG_AV_MV_CMT_SYNC saMigAvMvCmtSync;
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"AV"}, value = "AV배우 코멘트를 싱크한다.")
	//@PostMapping(path= "/api/BS_MIG_AV_ACTR_CMT_SYNC_ACTOR_IDX", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.IN_DATA==null) {
			throw new BizRuntimeException("[IN_DATA]입력파라미터가 전달되지 않았습니다.");
		}
		if(inDS.IN_DATA.size()!=1) {
			throw new BizRuntimeException("[IN_DATA]입력파라미터의 ["+inDS.IN_DATA.size()+"]행수가 잘못되었습니다.");
		}
		
		IN_DATA_ROW  rs =inDS.IN_DATA.get(0);
		String  ACTOR_IDX 		= pjtU.str(rs.ACTOR_IDX);
		String  SYNC_YN 		= pjtU.str(rs.SYNC_YN);

		if(pjtU.isEmpty(ACTOR_IDX)){
			throw new BizRuntimeException("ACTOR_IDX가 전달되지 않았습니다.");
		}
		Long L_ACTOR_IDX  = Long.parseLong(ACTOR_IDX);

		try {
			saMigAvMvCmtSync.run(L_ACTOR_IDX,SYNC_YN);
		} catch (Exception e) {
			e.printStackTrace();
		}

		OUT_DS outDs = new OUT_DS();
		return outDs;
	}
}
