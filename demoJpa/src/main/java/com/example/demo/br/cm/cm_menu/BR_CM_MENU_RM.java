package com.example.demo.br.cm.cm_menu;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.db.da.cm.DA_CM_MENU;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
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
public class BR_CM_MENU_RM {

	@JsonRootName("IN_DS")
	@Schema(name="IN_DS",title="IN_DS-BR_CM_MENU_RM")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CM_MENU_SAVE", description = "입력 데이터")
		ArrayList<IN_DATA> IN_DATA = new ArrayList<IN_DATA>();
	}

	@Schema(name="DATA_ROW", title = "IN_DATA_ROW-BR_CM_MENU_RM")
	@Data
	static class IN_DATA {
		@JsonProperty("MENU_NO")
		@Schema(name = "MENU_NO", example = "1", description = "메뉴번호")
		Long MENU_NO = null;
	}
	
	@JsonRootName("OUT_DS")
	@Schema(name="OUT_DS",title = "OUT_DS-BR_CM_MENU_SAVE")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA", title="OUT_DATA-BR_CM_MENU_SAVE", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}
	
	@Autowired
	DA_CM_MENU daMenu;
	
	@Operation(summary = "메뉴 삭제.", description = "")
	@PostMapping(path= "/api/BR_CM_MENU_RM", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		for( int i=0;i<inDS.IN_DATA.size();i++) {
			IN_DATA  rs =inDS.IN_DATA.get(i);
			String  MENU_NO 		= PjtUtil.str(rs.MENU_NO);
			if(PjtUtil.isEmpty(MENU_NO)) {
				throw new BizRuntimeException("["+MENU_NO+"]메뉴번호가 입력되지 않았습니다.");
			}
			long L_MENU_NO = Long.parseLong(MENU_NO);
			daMenu.rmMenu(
					L_MENU_NO
					);
		}
	
		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
}
