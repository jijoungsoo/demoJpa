package com.example.demo.br.cm.cm_domain;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.anotation.OpService;
import com.example.demo.db.da.cm.DA_CM_DOMAIN;
import com.example.demo.db.domain.cm.CmDomain;
import com.example.demo.exception.BizException;
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

@Tag(name = "CM_DOMAIN", description = "공통도메인")
@Slf4j
@OpService
@Service
public class BR_CM_DOMAIN_FIND {

	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_CM_DOMAIN_FIND")
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
	@ApiModel(value="OUT_DS-BR_CM_DOMAIN_FIND")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_CM_DOMAIN_FIND", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}

	@ApiModel(value="OUT_DATA_ROW-BR_CM_DOMAIN_FIND")
	@Data
	static class OUT_DATA_ROW {
		@JsonProperty("DMN_NO")
		@Schema(name = "DMN_NO", example = "1", description = "도메인NO")
		Long DMN_NO = null;
		@JsonProperty("DMN_CD")
		@Schema(name = "DMN_CD", example = "USE_YN", description = "도메인CD")
		String DMN_CD = null;
		@JsonProperty("DMN_NM")
		@Schema(name = "DMN_NM", example = "사용여부", description = "도메인명")
		String DMN_NM = null;
		
		@JsonProperty("DATA_TYPE")
		@Schema(name = "DATA_TYPE", example = "VARCHAR(10)", description = "데이터타입")
		String DATA_TYPE = null;
		
		@JsonProperty("RMK")
		@Schema(name = "RMK", example = "비고", description = "비고")
		String RMK = null;		
		
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
	DA_CM_DOMAIN daDmn;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_DOMAIN"},value = "도메인을 조회한다.", notes = "")
	//@PostMapping(path= "/api/BR_CM_DOMAIN_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		List<CmDomain>  al =daDmn.findDomain();
		OUT_DS outDs = new OUT_DS();
		for(int i=0;i<al.size();i++) {
			CmDomain cm=al.get(i);
			OUT_DATA_ROW  row = new OUT_DATA_ROW();
			row.DMN_NO= cm.getDmnNo();
			row.DMN_CD= cm.getDmnCd();
			row.DMN_NM= cm.getDmnNm();
			row.DATA_TYPE= cm.getDataType();
			row.RMK= cm.getRmk();
			row.CRT_DTM=PjtUtil.getYyyy_MM_dd_HHMMSS(cm.getCrtDtm());
			row.UPDT_DTM=PjtUtil.getYyyy_MM_dd_HHMMSS(cm.getUpdtDtm());
			outDs.OUT_DATA.add(row);
		}
		return outDs;
	}
}
