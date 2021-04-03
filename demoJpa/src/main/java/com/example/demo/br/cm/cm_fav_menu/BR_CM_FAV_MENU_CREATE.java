package com.example.demo.br.cm.cm_fav_menu;

import java.util.ArrayList;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.cm.DA_CM_FAV_MENU;
import com.example.demo.db.da.cm.DA_CM_SEQ;
import com.example.demo.db.domain.cm.CmFavMenu;
import com.example.demo.exception.BizException;
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
public class BR_CM_FAV_MENU_CREATE {

	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_CM_FAV_MENU_CREATE")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CM_FAV_MENU_CREATE", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
		
		@JsonProperty("LSESSION")
		LSESSION_ROW LSESSION;
	}

	@ApiModel(value="IN_DATA_ROW-BR_CM_FAV_MENU_CREATE")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("MENU_NO")
		@Schema(name = "MENU_NO", example = "1", description = "메뉴번호")
		String MENU_NO = null;
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_FAV_MENU_CREATE")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA", title="OUT_DATA-BR_CM_FAV_MENU_CREATE", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}
	
	@Autowired
	DA_CM_FAV_MENU daFavM;
	
	@Autowired
	DA_CM_SEQ daCmSeq;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_MENU"},value = "즐겨찾기 메뉴를 저장한다.", notes = "")
	//@PostMapping(path= "/api/BR_CM_FAV_MENU_CREATE", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.LSESSION==null) {
			throw new BizRuntimeException("세션값이 넘어오지 않았습니다1.");
		}
		String SESSION_USER_NO =inDS.LSESSION.getUSER_NO();
		if(PjtUtil.isEmpty(SESSION_USER_NO)) {
			throw new BizRuntimeException("사용자NO가 넘어오지 않았습니다2.");
		}
		Long L_SESSION_USER_NO = Long.parseLong(SESSION_USER_NO);
				
		IN_DATA_ROW  rs =inDS.IN_DATA.get(0);
		String  MENU_NO 		= PjtUtil.str(rs.MENU_NO);
		
		if(PjtUtil.isEmpty(MENU_NO)) {
			throw new BizRuntimeException("메뉴번호가 입력되지 않았습니다.");
		}
		long L_FAV_NO =daCmSeq.increate("CM_FAV_MENU_FAV_NO_SEQ");
		
		long L_MENU_NO = Long.parseLong(MENU_NO);
		
		CmFavMenu c=daFavM.findFavMenuByMenuCdAndUserNo(L_SESSION_USER_NO,L_MENU_NO);
		if(c!=null) {
			//기존에 데이터가 있었다.종료
			throw new BizRuntimeException("이미 즐겨찾기에 추가되어있습니다.");
		} else {
			daFavM.createFavMenu(
					L_FAV_NO
					,L_MENU_NO
					,L_SESSION_USER_NO
			);	
		}
		
		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
}
