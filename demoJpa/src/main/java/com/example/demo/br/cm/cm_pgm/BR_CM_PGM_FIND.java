package com.example.demo.br.cm.cm_pgm;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.anotation.OpService;
import com.example.demo.db.da.cm.DA_CM_PGM;
import com.example.demo.db.domain.cm.CmPgm;
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

@Tag(name = "CM_PGM", description = "프로그램")
@Slf4j
@OpService
@Service
public class BR_CM_PGM_FIND {
	@Autowired
    PjtUtil pjtU;
	
	@JsonRootName("IN_DS")
	@ApiModel(value="OUT_DS-BR_CM_PGM_FIND")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CM_PGM_FIND", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
	}

	
	@ApiModel(value="IN_DATA_ROW-BR_CM_PGM_FIND")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("CATEGORY")
		@Schema(name = "CATEGORY", example = "CM", description = "카테고리")
		String CATEGORY = "";
	}
	

	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_PGM_FIND")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA", title="OUT_DATA-BR_CM_PGM_FIND", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}

	@ApiModel(value="OUT_DATA_ROW-BR_CM_PGM_FIND")
	@Data
	static class OUT_DATA_ROW {
		@JsonProperty("PGM_NO")
		@Schema(name = "PGM_NO", example = "1", description = "프로그램NO")
		Long PGM_NO = null;
		@JsonProperty("PGM_ID")
		@Schema(name = "PGM_ID", example = "CM_001", description = "프로그램ID")
		String PGM_ID = null;
		@JsonProperty("DIR_LINK")
		@Schema(name = "DIR_LINK", example = "****", description = "DIR_LINK")
		String DIR_LINK = null;
		@JsonProperty("PGM_LINK")
		@Schema(name = "PGM_LINK", example = "PGM_LINK", description = "PGM_LINK")
		String PGM_LINK = null;
		@JsonProperty("PGM_NM")
		@Schema(name = "PGM_NM", example = "admin@gogo.com", description = "PGM_NM")
		String PGM_NM = null;
		@JsonProperty("CATEGORY")
		@Schema(name = "CATEGORY", example = "admin@gogo.com", description = "CATEGORY")
		String CATEGORY = null;
		@JsonProperty("RMK")
		@Schema(name = "RMK", example = "admin@gogo.com", description = "RMK")
		String RMK = null;
		@JsonProperty("ORD")
		@Schema(name = "ORD", example = "admin@gogo.com", description = "ORD")
		String ORD = null;
		@JsonProperty("CRT_DTM")
		@Schema(name = "CRT_DTM", example = "admin@gogo.com", description = "CRT_DTM")
		String CRT_DTM = null;
		@JsonProperty("UPDT_DTM")
		@Schema(name = "UPDT_DTM", example = "admin@gogo.com", description = "UPDT_DTM")
		String UPDT_DTM = null;
	}
	
	@Autowired
	DA_CM_PGM daPgm;
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_PGM"},value = "프로그램 조회.", notes = "")
	//@PostMapping(path= "/api/BR_CM_PGM_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS  run(@RequestBody IN_DS inDS) throws BizException {
		String  CATEGORY = null;
		if(inDS.IN_DATA!=null) {
			if(inDS.IN_DATA.size()>0) {
				IN_DATA_ROW  rs =inDS.IN_DATA.get(0);
				CATEGORY 		= pjtU.str(rs.CATEGORY);
			}
		}
		
		List<CmPgm>  al =daPgm.findPgm(CATEGORY);
		OUT_DS outDs = new OUT_DS();
		for(int i=0;i<al.size();i++) {
			CmPgm cm=al.get(i);
			OUT_DATA_ROW  row = new OUT_DATA_ROW();
			row.PGM_NO=cm.getPgmNo();
			row.PGM_ID=cm.getPgmId();
			row.DIR_LINK=cm.getDirLink();
			row.PGM_LINK=cm.getPgmLink();
			row.PGM_NM=cm.getPgmNm();
			row.CATEGORY=cm.getCategory();
			row.RMK=cm.getRmk();
			row.ORD=cm.getOrd();
			row.CRT_DTM=pjtU.getYyyy_MM_dd_HHMMSS(cm.getCrtDtm());
			row.UPDT_DTM=pjtU.getYyyy_MM_dd_HHMMSS(cm.getUpdtDtm());
			outDs.OUT_DATA.add(row);
		}		
		return outDs;
	}
}
