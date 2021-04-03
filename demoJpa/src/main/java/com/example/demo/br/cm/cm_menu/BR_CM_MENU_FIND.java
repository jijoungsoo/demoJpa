package com.example.demo.br.cm.cm_menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.demo.anotation.OpService;
import com.example.demo.db.da.cm.DA_CM_MENU_MAPPER;
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
public class BR_CM_MENU_FIND {

	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_CM_MENU_FIND")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		
		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CM_MENU_FIND", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
	}
	
	
	@ApiModel(value="IN_DATA_ROW-BR_CM_MENU_FIND")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("MENU_KIND")
		@Schema(name = "MENU_KIND", example = "M-메뉴, S-화면", description = "메뉴종류")
		String MENU_KIND = "";

		@JsonProperty("PRNT_MENU_CD")
		@Schema(name = "PRNT_MENU_CD", example = "CM_0100", description = "부모메뉴코드")
		String PRNT_MENU_CD = "";
	}

	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_MENU_FIND")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA", title="OUT_DATA-BR_CM_MENU_FIND", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}

	@ApiModel(value="OUT_DATA_ROW-BR_CM_MENU_FIND")
	@Data
	static class OUT_DATA_ROW {
		@JsonProperty("MENU_NO")
		@Schema(name = "MENU_NO", example = "1", description = "MENU_NO")
		String MENU_NO = null;
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
		String MENU_PATH = null
		;
		@JsonProperty("CRT_DTM")
		@Schema(name = "CRT_DTM", example = "202012311640", description = "생성일시")
		String CRT_DTM = null;
		
		@JsonProperty("UPDT_DTM")
		@Schema(name = "UPDT_DTM", example = "202012311640", description = "수정일시")
		String UPDT_DTM = null;
	}
	@Autowired
	DA_CM_MENU_MAPPER daMenuM;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_MENU"},value = "메뉴 조회.", notes = "")
	//@PostMapping(path= "/api/BR_CM_MENU_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS  run(@RequestBody IN_DS inDS) throws Exception {
		String PRNT_MENU_CD  =  null;
		String MENU_KIND  =  null;
		if(inDS.IN_DATA!=null) {
			if(inDS.IN_DATA.size()>0){
				IN_DATA_ROW  rs =inDS.IN_DATA.get(0);
				PRNT_MENU_CD 	=rs.PRNT_MENU_CD;
				MENU_KIND 		=rs.MENU_KIND;
			}
		}
		List<Map>  al =daMenuM.findMenu(PRNT_MENU_CD,MENU_KIND);
		OUT_DS outDs = new OUT_DS();
		for(int i=0;i<al.size();i++) {
			Map cm=al.get(i);
			OUT_DATA_ROW  row = new OUT_DATA_ROW();
			row.MENU_NO= PjtUtil.str(cm.get("menu_no"));
			row.MENU_CD= PjtUtil.str(cm.get("menu_cd"));
			row.MENU_NM= PjtUtil.str(cm.get("menu_nm"));
			row.PRNT_MENU_CD= PjtUtil.str(cm.get("prnt_menu_cd"));
			row.ORD= PjtUtil.str(cm.get("ord"));
			row.PGM_ID= PjtUtil.str(cm.get("pgm_id"));
			row.MENU_KIND= PjtUtil.str(cm.get("menu_kind"));
			row.RMK= PjtUtil.str(cm.get("rmk"));
			row.MENU_LVL= PjtUtil.str(cm.get("menu_lvl"));
			row.MENU_PATH= PjtUtil.str(cm.get("menu_path"));
			row.CRT_DTM=PjtUtil.getYyyy_MM_dd_HHMMSS((java.util.Date)cm.get("crt_dtm"));
			row.UPDT_DTM=PjtUtil.getYyyy_MM_dd_HHMMSS((java.util.Date)cm.get("updt_dtm"));
			outDs.OUT_DATA.add(row);
		}
		return outDs;
	}
}
