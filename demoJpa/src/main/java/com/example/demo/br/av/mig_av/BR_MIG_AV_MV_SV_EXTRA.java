package com.example.demo.br.av.mig_av;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.ctrl.PAGE_DATA_ROW;
import com.example.demo.db.da.mig_av.DA_MIG_AV_MV;
import com.example.demo.db.domain.mig_av.QMigAvActr;
import com.example.demo.db.domain.mig_av.QMigAvMv;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
public class BR_MIG_AV_MV_SV_EXTRA {

	@Autowired
    PjtUtil pjtU;
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_MIG_AV_MV_SV_FILE_PATH_DEL_YN")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_MIG_AV_MV_SV_FILE_PATH_DEL_YN", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();

		@JsonProperty("LSESSION")
		LSESSION_ROW LSESSION;
	}

	@ApiModel(value="IN_DATA_ROW-BR_MIG_AV_MV_SV_FILE_PATH_DEL_YN")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("DVD_IDX")
		@Schema(name = "DVD_IDX", example = "1", description = "DVD_IDX")
		String DVD_IDX = "";

		@JsonProperty("FILE_PATH")
		@Schema(name = "FILE_PATH", example = "1", description = "FILE_PATH")
		String FILE_PATH = "";

		@JsonProperty("DEL_YN")
		@Schema(name = "DEL_YN", example = "1", description = "DEL_YN")
		String DEL_YN = "";

		@JsonProperty("RMK")
		@Schema(name = "RMK", example = "1", description = "RMK")
		String RMK = "";

	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_MIG_AV_MV_SV_FILE_PATH_DEL_YN")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_MIG_AV_MV_SV_FILE_PATH_DEL_YN", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}
	
	@Autowired
	DA_MIG_AV_MV daMigAvMv;
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"AV"}, value = "AV작품을 조회한다.", notes = "페이징 처리")
	//@PostMapping(path= "/api/BR_MIG_AV_MV_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.LSESSION==null) {
			throw new BizRuntimeException("세션값이 넘어오지 않았습니다1.");
		}
		String SESSION_USER_NO =inDS.LSESSION.getUSER_NO();
		if(pjtU.isEmpty(SESSION_USER_NO)) {
			throw new BizRuntimeException("사용자NO가 넘어오지 않았습니다2.");
		}

		if(inDS.IN_DATA==null) {
			throw new BizRuntimeException("파라미터가 넘어오지 않았습니다.");
		}

		if(inDS.IN_DATA.size()!=1) {
			throw new BizRuntimeException("파라미터가 하나이상 넘어왔습니다.");
		}

		if(PjtUtil.isEmpty(inDS.IN_DATA.get(0).DVD_IDX)) {
			throw new BizRuntimeException("DVD_IDX 파라미터가 넘어오지 않았습니다.");
		}

		Long L_DVD_IDX = Long.parseLong(inDS.IN_DATA.get(0).DVD_IDX);
		String FILE_PATH = inDS.IN_DATA.get(0).FILE_PATH;
		String DEL_YN = inDS.IN_DATA.get(0).DEL_YN;
		String RMK = inDS.IN_DATA.get(0).RMK;
		
		daMigAvMv.updtMigAvMvExtra(L_DVD_IDX,FILE_PATH,DEL_YN,RMK);
		
		OUT_DS outDs = new OUT_DS();
		return outDs;
	}
}
