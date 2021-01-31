package com.example.demo.br.cm.cm_fav_menu;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.db.da.cm.DA_CM_FAV_MENU;
import com.example.demo.db.domain.cm.QCmFavMenu;
import com.example.demo.db.domain.cm.QCmMenu;
import com.example.demo.db.domain.cm.QCmPgm;
import com.example.demo.db.domain.cm.QCmUser;
import com.example.demo.exception.BizException;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.querydsl.core.Tuple;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Tag(name = "CM_MENU", description = "메뉴")
public class BR_CM_FAV_MENU_FIND {

	@JsonRootName("IN_DS")
	@Schema(name="IN_DS",title="IN_DS-BR_CM_FAV_MENU_FIND")
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
	@Schema(name="OUT_DS",title = "OUT_DS-BR_CM_FAV_MENU_FIND")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA", title="OUT_DATA-BR_CM_FAV_MENU_FIND", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}
	
	@Schema(name="DATA_ROW", title = "DATA_ROW-BR_CM_FAV_MENU_FIND")
	@Data
	static class OUT_DATA_ROW {
		@JsonProperty("FAV_NO")
		@Schema(name = "FAV_NO", example = "1", description = "사용자NO")
		String FAV_NO = null;
		@JsonProperty("MENU_NO")
		@Schema(name = "MENU_NO", example = "1", description = "사용자NO")
		String MENU_NO = null;
		@JsonProperty("USER_NO")
		@Schema(name = "USER_NO", example = "jijs", description = "사용자ID")
		String USER_NO = null;
		@JsonProperty("USER_NM")
		@Schema(name = "USER_NM", example = "****", description = "사용자패스워드")
		String USER_NM = null;
		@JsonProperty("USER_ID")
		@Schema(name = "USER_ID", example = "****", description = "사용자패스워드")
		String USER_ID = null;
		@JsonProperty("MENU_NM")
		@Schema(name = "MENU_NM", example = "****", description = "사용자패스워드")
		String MENU_NM = null;
		@JsonProperty("PGM_ID")
		@Schema(name = "PGM_ID", example = "****", description = "사용자패스워드")
		String PGM_ID = null;
		@JsonProperty("PGM_NM")
		@Schema(name = "PGM_NM", example = "****", description = "사용자패스워드")
		String PGM_NM = null;
		@JsonProperty("PGM_LINK")
		@Schema(name = "PGM_LINK", example = "****", description = "사용자패스워드")
		String PGM_LINK = null;
		@JsonProperty("DIR_LINK")
		@Schema(name = "DIR_LINK", example = "****", description = "사용자패스워드")
		String DIR_LINK = null;
		@JsonProperty("CRT_DTM")
		@Schema(name = "CRT_DTM", example = "****", description = "사용자패스워드")
		String CRT_DTM = null;
		@JsonProperty("UPDT_DTM")
		@Schema(name = "UPDT_DTM", example = "****", description = "사용자패스워드")
		String UPDT_DTM = null;
	
	}
	
	@Autowired
	DA_CM_FAV_MENU daFavM;

	@Operation(summary = "즐겨찾기 메뉴 조회한다.", description = "")
	@PostMapping(path= "/api/BR_CM_FAV_MENU_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS  run(@RequestBody IN_DS inDS) throws BizException {
		List<com.querydsl.core.Tuple>   al =daFavM.findFavMenu();
		OUT_DS outDs = new OUT_DS();
		 for (Tuple row : al) {
			 OUT_DATA_ROW  out_data_row = new OUT_DATA_ROW();
			 out_data_row.FAV_NO= String.valueOf(row.get(QCmFavMenu.cmFavMenu.favNo));
			 out_data_row.MENU_NO= String.valueOf(row.get(QCmFavMenu.cmFavMenu.menuNo));
			 out_data_row.USER_NO= String.valueOf(row.get(QCmFavMenu.cmFavMenu.userNo));
			 out_data_row.USER_NM= row.get(QCmUser.cmUser.userNm);
			 out_data_row.USER_ID= row.get(QCmUser.cmUser.userId);
			 out_data_row.MENU_NM= row.get(QCmMenu.cmMenu.menuNm);
			 out_data_row.PGM_ID= row.get(QCmMenu.cmMenu.pgmId);
			 out_data_row.PGM_NM= row.get(QCmPgm.cmPgm.pgmNm);
			 out_data_row.PGM_LINK= row.get(QCmPgm.cmPgm.pgmLink);
			 out_data_row.DIR_LINK= row.get(QCmPgm.cmPgm.dirLink);
			 out_data_row.CRT_DTM= PjtUtil.getYyyy_MM_dd_HHMMSS(row.get(QCmFavMenu.cmFavMenu.crtDtm));
			 out_data_row.UPDT_DTM= PjtUtil.getYyyy_MM_dd_HHMMSS(row.get(QCmFavMenu.cmFavMenu.updtDtm));
			 outDs.OUT_DATA.add(out_data_row);
		 }
		return outDs;
	}
}
