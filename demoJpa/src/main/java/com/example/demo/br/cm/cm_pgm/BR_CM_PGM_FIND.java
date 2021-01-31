package com.example.demo.br.cm.cm_pgm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.db.da.cm.DA_CM_PGM;
import com.example.demo.db.domain.cm.CmPgm;
import com.example.demo.exception.BizException;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Tag(name = "CM_PGM", description = "프로그램")
public class BR_CM_PGM_FIND {
	
	@JsonRootName("IN_DS")
	@Schema(name="IN_DS",title="IN_DS-BR_CM_PGM_FIND")
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
	@Schema(name="OUT_DS",title = "OUT_DS-BR_CM_PGM_FIND")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA", title="OUT_DATA-BR_CM_PGM_FIND", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}

	@Schema(name="OUT_DATA_ROW",title = "OUT_DATA_ROW-BR_CM_PGM_FIND")
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

	@Operation(summary = "프로그램 조회.", description = "")
	@PostMapping(path= "/api/BR_CM_PGM_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS  run(@RequestBody IN_DS inDS) throws BizException {
		List<CmPgm>  al =daPgm.findPgm();
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
			row.CRT_DTM=PjtUtil.getYyyy_MM_dd_HHMMSS(cm.getCrtDtm());
			row.UPDT_DTM=PjtUtil.getYyyy_MM_dd_HHMMSS(cm.getUpdtDtm());
			outDs.OUT_DATA.add(row);
		}		
		return outDs;
	}
}
