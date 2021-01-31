package com.example.demo.br.cm.cm_menu;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.db.da.cm.DA_CM_MENU;
import com.example.demo.db.domain.cm.CmMenu;
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
@Tag(name = "CM_MENU", description = "메뉴")
public class BR_CM_MENU_FIND {

	@JsonRootName("IN_DS")
	@Schema(name="IN_DS",title="IN_DS-BR_CM_MENU_FIND")
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
	@Schema(name="OUT_DS",title = "OUT_DS-BR_CM_MENU_FIND")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA", title="OUT_DATA-BR_CM_MENU_FIND", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}

	@Schema(name="OUT_DATA_ROW",title = "OUT_DATA_ROW-BR_CM_MENU_FIND")
	@Data
	static class OUT_DATA_ROW {
		@JsonProperty("MENU_NO")
		@Schema(name = "MENU_NO", example = "1", description = "MENU_NO")
		Long MENU_NO = null;
		@JsonProperty("MENU_CD")
		@Schema(name = "MENU_CD", example = "CM_001", description = "MENU_CD")
		String MENU_CD = null;
		@JsonProperty("MENU_NM")
		@Schema(name = "MENU_NM", example = "****", description = "MENU_NM")
		String MENU_NM = null;
		@JsonProperty("PRNT_MENU_CD")
		@Schema(name = "PRNT_MENU_CD", example = "PRNT_MENU_CD", description = "PRNT_MENU_CD")
		String PRNT_MENU_CD = null;
		@JsonProperty("ORD")
		@Schema(name = "ORD", example = "admin@gogo.com", description = "ORD")
		String ORD = null;
		@JsonProperty("PGM_ID")
		@Schema(name = "PGM_ID", example = "admin@gogo.com", description = "PGM_ID")
		String PGM_ID = null;
		@JsonProperty("MENU_KIND")
		@Schema(name = "MENU_KIND", example = "admin@gogo.com", description = "MENU_KIND")
		String MENU_KIND = null;
		@JsonProperty("RMK")
		@Schema(name = "RMK", example = "admin@gogo.com", description = "RMK")
		String RMK = null;
		@JsonProperty("MENU_LVL")
		@Schema(name = "MENU_LVL", example = "admin@gogo.com", description = "MENU_LVL")
		String MENU_LVL = null;
		@JsonProperty("MENU_PATH")
		@Schema(name = "MENU_PATH", example = "admin@gogo.com", description = "MENU_PATH")
		String MENU_PATH = null;
		@JsonProperty("CRT_DTM")
		@Schema(name = "CRT_DTM", example = "admin@gogo.com", description = "CRT_DTM")
		String CRT_DTM = null;
		@JsonProperty("UPDT_DTM")
		@Schema(name = "UPDT_DTM", example = "admin@gogo.com", description = "UPDT_DTM")
		String UPDT_DTM = null;
	}
	@Autowired
	DA_CM_MENU daMenu;

	@Operation(summary = "메뉴 조회.", description = "")
	@PostMapping(path= "/api/BR_CM_MENU_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS  run(@RequestBody IN_DS inDS) throws BizException {
		List<CmMenu>  al =daMenu.findMenu();
		OUT_DS outDs = new OUT_DS();
		for(int i=0;i<al.size();i++) {
			CmMenu cm=al.get(i);
			OUT_DATA_ROW  row = new OUT_DATA_ROW();
			row.MENU_NO= cm.getMenuNo();
			row.MENU_CD= cm.getMenuCd();
			row.MENU_NM= cm.getMenuNm();
			row.PRNT_MENU_CD= cm.getPrntMenuCd();
			row.ORD= cm.getOrd();
			row.PGM_ID= cm.getPgmId();
			row.MENU_KIND= cm.getMenuKind();
			row.RMK= cm.getRmk();
			row.MENU_LVL= cm.getMenuLvl();
			row.MENU_PATH= cm.getMenuPath();
			row.CRT_DTM=PjtUtil.getYyyy_MM_dd_HHMMSS(cm.getCrtDtm());
			row.UPDT_DTM=PjtUtil.getYyyy_MM_dd_HHMMSS(cm.getUpdtDtm());
			outDs.OUT_DATA.add(row);
		}
		return outDs;
	}
}
