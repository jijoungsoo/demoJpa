package com.example.demo.br.cm.cm_fav_menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.cm.DA_CM_FAV_MENU_MAPPER;
import com.example.demo.exception.BizRuntimeException;
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

@Tag(name = "CM_MENU", description = "메뉴")
@Slf4j
@OpService
@Service
public class BR_CM_FAV_MENU_FIND_BY_USER_NO {

	@Autowired
    PjtUtil pjtU;

	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_CM_FAV_MENU_FIND_BY_USER_NO")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;
		
		@JsonProperty("LSESSION")
		LSESSION_ROW LSESSION;
	}
	

	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_FAV_MENU_FIND_BY_USER_NO")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_CM_LOGIN_LOAD_USER_BY_USER_NAME", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}

	@ApiModel(value="OUT_DATA_ROW-BR_CM_FAV_MENU_FIND_BY_USER_NO")
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
		@Schema(name = "MENU_NM",example = "MENU_1000", description = "메뉴명")
		String MENU_NM = null;
		@JsonProperty("PGM_ID")
		@Schema(name = "PGM_ID",example = "PGM_0001", description = "프로그램ID")
		String PGM_ID = null;
		@JsonProperty("PGM_LINK")
		@Schema(name = "PGM_LINK",example = "????", description = "프로그램링크")
		String PGM_LINK = null;
		@JsonProperty("DIR_LINK")
		@Schema(name = "DIR_LINK",example = "????", description = "경로링크")
		String DIR_LINK = null;
	}
	
	@Autowired
	DA_CM_FAV_MENU_MAPPER daFavM;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation",content = {
			@Content(mediaType = "application/json",schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_MENU"},value = "즐겨찾기매뉴를 조회한다(by USER_NO).", notes = "")
	//@PostMapping(path= "/api/BR_CM_FAV_MENU_FIND_BY_USER_NO", consumes = "application/json", produces = "application/json")
	public OUT_DS  run(@RequestBody IN_DS inDS) throws Exception {
		if(inDS.LSESSION==null) {
			throw new BizRuntimeException("세션값이 넘어오지 않았습니다1.");
		}
		String SESSION_USER_NO =inDS.LSESSION.getUSER_NO();
		if(pjtU.isEmpty(SESSION_USER_NO)) {
			throw new BizRuntimeException("사용자NO가 넘어오지 않았습니다2.");
		}
		Long L_SESSION_USER_NO = Long.parseLong(SESSION_USER_NO);
		
		List<Map>   tmp =daFavM.findFavMenuByUserNo(L_SESSION_USER_NO);
		OUT_DS outDs = new OUT_DS();
		 for (Map row : tmp) {
			 OUT_DATA_ROW  data_row = new OUT_DATA_ROW();
			 data_row.FAV_NO =Long.parseLong(pjtU.str(row.get("fav_no")));
			 data_row.MENU_NO=Long.parseLong(pjtU.str(row.get("menu_no")));
			 data_row.USER_NO=Long.parseLong(pjtU.str(row.get("user_no")));
			 data_row.USER_ID=pjtU.str(row.get("user_id"));
			 data_row.MENU_NM=pjtU.str(row.get("menu_nm"));
			 data_row.PGM_ID=pjtU.str(row.get("pgm_id"));
			 data_row.PGM_LINK=pjtU.str(row.get("pgm_link"));
			 data_row.DIR_LINK=pjtU.str(row.get("dir_link"));
			 outDs.OUT_DATA.add(data_row);
		 }
		return outDs;
	}
}
