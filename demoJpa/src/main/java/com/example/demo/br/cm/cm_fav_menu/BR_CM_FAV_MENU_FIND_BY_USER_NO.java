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
import com.example.demo.exception.BizRuntimeException;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.querydsl.core.Tuple;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Tag(name = "CM_MENU", description = "메뉴")
public class BR_CM_FAV_MENU_FIND_BY_USER_NO {

	@JsonRootName("IN_DS")
	@Schema(name="IN_DS",title="IN_DS-BR_CM_FAV_MENU_FIND_BY_USER_NO")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;
		
		@JsonProperty("LSESSION")
		@Schema(name = "LSESSION",title="LSESSION-BR_CM_FAV_MENU_FIND_BY_USER_NO", description = "세션데이터")
		ArrayList<LSESSION> LSESSION;
	}
	
	static class LSESSION{
		@JsonProperty("USER_NO")
		@Schema(name = "USER_NO", example = "1", description = "사용자NO")
		String USER_NO;
	}

	@JsonRootName("OUT_DS")
	@Schema(name="OUT_DS",title = "OUT_DS-BR_CM_LOGIN_LOAD_USER_BY_USER_NAME")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA",title="OUT_DATA-BR_CM_LOGIN_LOAD_USER_BY_USER_NAME", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}

	@Schema(name="OUT_DATA_ROW",title = "OUT_DATA_ROW-BR_CM_LOGIN_LOAD_USER_BY_USER_NAME")
	@Data
	static class OUT_DATA_ROW {
		@JsonProperty("FAV_NO")
		@Schema(name = "FAV_NO",example = "1", description = "즐겨찾기 NO")
		Long FAV_NO = null;
		@JsonProperty("MENU_NO")
		@Schema(name = "MENU_NO",example = "1",description = "메뉴 NO")
		Long MENU_NO = null;
		@JsonProperty("USER_NO")
		@Schema(name = "USER_NO",example = "1",description = "소유자 NO")
		Long USER_NO = null;
		@JsonProperty("USER_ID")
		@Schema(name = "USER_ID",example = "jijs",description = "사용자ID")
		String USER_ID = null;
		@JsonProperty("MENU_NM")
		@Schema(name = "MENU_NM",example = "admin@gogo.com", description = "메뉴명")
		String MENU_NM = null;
		@JsonProperty("PGM_ID")
		@Schema(name = "PGM_ID",example = "admin@gogo.com", description = "프로그램ID")
		String PGM_ID = null;
		@JsonProperty("PGM_LINK")
		@Schema(name = "PGM_LINK",example = "admin@gogo.com", description = "프로그램링크")
		String PGM_LINK = null;
		@JsonProperty("DIR_LINK")
		@Schema(name = "DIR_LINK",example = "admin@gogo.com", description = "경로링크")
		String DIR_LINK = null;
	}
	
	@Autowired
	DA_CM_FAV_MENU daFavM;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation",content = {
			@Content(mediaType = "application/json",schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@Operation(summary = "즐겨찾기매뉴를 조회한다(by USER_NO)", description = "")
	@PostMapping(path= "/api/BR_CM_FAV_MENU_FIND_BY_USER_NO", consumes = "application/json", produces = "application/json")
	public OUT_DS  run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.LSESSION==null) {
			throw new BizRuntimeException("세션값이 넘어오지 않았습니다1.");
		}
		if(inDS.LSESSION.size()!=1) {
			String tmp =String.valueOf(inDS.LSESSION.size());
			throw new BizRuntimeException("세션값이 "+tmp+"개 넘어왔습니다.");
		}
		String USER_NO =inDS.LSESSION.get(0).USER_NO;
		Long L_USER_NO = Long.parseLong(USER_NO);
		if(L_USER_NO==null) {
			throw new BizRuntimeException("사용자NO가 넘어오지 않았습니다2.");
		}
		List<com.querydsl.core.Tuple>   tmp =daFavM.findFavMenuByUserNo(L_USER_NO);
		OUT_DS outDs = new OUT_DS();
		 for (Tuple row : tmp) {
			 OUT_DATA_ROW  data_row = new OUT_DATA_ROW();
			 data_row.FAV_NO=row.get(QCmFavMenu.cmFavMenu.favNo);
			 data_row.MENU_NO=row.get(QCmFavMenu.cmFavMenu.menuNo);
			 data_row.USER_NO=row.get(QCmFavMenu.cmFavMenu.userNo);
			 data_row.USER_ID=row.get(QCmUser.cmUser.userId);
			 data_row.MENU_NM=row.get(QCmMenu.cmMenu.menuNm);
			 data_row.PGM_ID=row.get(QCmMenu.cmMenu.pgmId);
			 data_row.PGM_LINK=row.get(QCmPgm.cmPgm.pgmLink);
			 data_row.DIR_LINK=row.get(QCmPgm.cmPgm.dirLink);
			 outDs.OUT_DATA.add(data_row);
		 }
		return outDs;
	}
}
