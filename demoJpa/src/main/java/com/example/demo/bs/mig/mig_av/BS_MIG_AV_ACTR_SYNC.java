package com.example.demo.bs.mig.mig_av;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.db.da.mig_av.DA_MIG_AV_ACTR;
import com.example.demo.db.domain.mig_av.MigAvActr;
import com.example.demo.exception.BizException;
import com.example.demo.sa.mig.mig_av.SA_MIG_AV_ACTR_DTL_GET;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

@Tag(name = "AV", description = "AV정보")
@Slf4j
@RestController
public class BS_MIG_AV_ACTR_SYNC {
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BS_MIG_AV_ACTR_SYNC")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;
	}

	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BS_MIG_AV_ACTR_SYNC")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BS_MIG_AV_ACTR_SYNC", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}

	@Autowired
	DA_MIG_AV_ACTR daMigAvActr;

    @Autowired
	SA_MIG_AV_ACTR_DTL_GET br;
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"AV"}, value = "AVDBS 배우 마이그", notes = "AVDBS 배우 마이그")
	@GetMapping(path= "/api/BS_MIG_AV_ACTR_SYNC")
	public OUT_DS run() throws BizException {
        List<MigAvActr> al = daMigAvActr.findBySyncN();
		for(var i=0;i<al.size();i++){
			MigAvActr m=al.get(i);
            br.run(m.getActrIdx(),false);//상태가 N이면 업데이트
		}
		OUT_DS outDs = new OUT_DS();
		return outDs;		
	}
}
