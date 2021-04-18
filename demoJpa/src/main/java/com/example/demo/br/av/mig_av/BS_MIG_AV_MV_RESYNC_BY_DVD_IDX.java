package com.example.demo.br.av.mig_av;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.anotation.OpService;
import com.example.demo.db.da.mig_av.DA_MIG_AV_MV;
import com.example.demo.db.da.mig_av.DA_MIG_AV_MV_GEN;
import com.example.demo.db.domain.mig_av.MigAvMv;
import com.example.demo.db.domain.mig_av.QMigAvGen;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
import com.example.demo.sa.mig.mig_av.SA_MIG_AV_MV_DTL_GET;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;

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
public class BS_MIG_AV_MV_RESYNC_BY_DVD_IDX {
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BS_MIG_AV_MV_RESYNC_BY_DVD_IDX")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;
		
		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BS_MIG_AV_MV_RESYNC_BY_DVD_IDX", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
	}
	
	@ApiModel(value="IN_DATA_ROW-BS_MIG_AV_MV_RESYNC_BY_DVD_IDX")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("DVD_IDX")
		@Schema(name = "DVD_IDX", example = "1", description = "DVD_IDX")
		String DVD_IDX = "";
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BS_MIG_AV_MV_RESYNC_BY_DVD_IDX")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_MIG_AV_MV_FIND_BY_DVD_IDX", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();

	}

	@Autowired
    PjtUtil pjtU;
	
	@Autowired
	DA_MIG_AV_MV daMigAvMv;

	@Autowired
	SA_MIG_AV_MV_DTL_GET saMigAvMvDtlGet;

	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"AV"}, value = "AV작품을 다시 싱크한다.", notes = "페이징 처리")
	//@PostMapping(path= "/api/BR_MIG_AV_MV_FIND_BY_DVD_IDX", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.IN_DATA==null) {
			throw new BizRuntimeException("[IN_DATA]입력파라미터가 전달되지 않았습니다.");
		}
		if(inDS.IN_DATA.size()!=1) {
			throw new BizRuntimeException("[IN_DATA]입력파라미터의 ["+inDS.IN_DATA.size()+"]행수가 잘못되었습니다.");
		}
		
		IN_DATA_ROW  rs =inDS.IN_DATA.get(0);
		String  DVD_IDX 		= pjtU.str(rs.DVD_IDX);

		if(pjtU.isEmpty(DVD_IDX)) {
			throw new BizRuntimeException("DVD_IDX가 넘어오지 않았습니다.");
		}
		Long L_DVD_IDX = Long.parseLong(DVD_IDX);	

		Optional<MigAvMv> c = daMigAvMv.findById(L_DVD_IDX);
		if(c.isPresent()){
			daMigAvMv.updtMigAvMvSync(L_DVD_IDX,"N");
        }
		saMigAvMvDtlGet.run(L_DVD_IDX);		
		OUT_DS outDs = new OUT_DS();
		return outDs;
	}
}
