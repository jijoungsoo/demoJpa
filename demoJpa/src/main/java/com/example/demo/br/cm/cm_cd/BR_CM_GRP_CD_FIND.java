package com.example.demo.br.cm.cm_cd;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.anotation.OpService;
import com.example.demo.db.da.cm.DA_CM_GRP_CD;
import com.example.demo.db.domain.cm.CmGrpCd;
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

@Tag(name = "CM_CD", description = "공통코드")
@Slf4j
@OpService
@Service
public class BR_CM_GRP_CD_FIND {

	@Autowired
    PjtUtil pjtU;

	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_CM_GRP_CD_FIND")
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
	@ApiModel(value="OUT_DS-BR_CM_GRP_CD_FIND")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_CM_GRP_CD_FIND", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}

	@ApiModel(value="OUT_DATA_ROW-BR_CM_GRP_CD_FIND")
	@Data
	static class OUT_DATA_ROW {
		@JsonProperty("GRP_CD")
		@Schema(name = "GRP_CD", example = "1", description = "사용자NO")
		String GRP_CD = null;
		@JsonProperty("GRP_NM")
		@Schema(name = "GRP_NM", example = "jijs", description = "사용자ID")
		String GRP_NM = null;
		@JsonProperty("CD_NM")
		@Schema(name = "CD_NM", example = "****", description = "사용자패스워드")
		String CD_NM = null;
		@JsonProperty("USE_YN")
		@Schema(name = "USE_YN", example = "홍길동", description = "사용자명")
		String USE_YN = null;
		
		@JsonProperty("ORD")
		@Schema(name = "ORD", example = "홍길동", description = "사용자명")
		String ORD = null;
		
		@JsonProperty("RMK")
		@Schema(name = "RMK", example = "홍길동", description = "사용자명")
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
	DA_CM_GRP_CD daGrpCd;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_CD"},value = "공통그룹코드 조회한다.", notes = "")
	//@PostMapping(path= "/api/BR_CM_GRP_CD_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS  run(@RequestBody IN_DS inDS) throws BizException {
		List<CmGrpCd>  al =daGrpCd.findCmGrpCd();
		OUT_DS outDs = new OUT_DS();
		for(int i=0;i<al.size();i++) {
			CmGrpCd cm=al.get(i);
			OUT_DATA_ROW  row = new OUT_DATA_ROW();
			row.GRP_CD= cm.getGrpCd();
			row.GRP_NM= cm.getGrpNm();
			row.USE_YN= cm.getUseYn();
			row.ORD= String.valueOf(cm.getOrd());
			row.RMK= cm.getRmk();
			row.CRT_DTM=pjtU.getYyyy_MM_dd_HHMMSS(cm.getCrtDtm());
			row.UPDT_DTM=pjtU.getYyyy_MM_dd_HHMMSS(cm.getUpdtDtm());
			outDs.OUT_DATA.add(row);

		}

		return outDs;
	}	
}
