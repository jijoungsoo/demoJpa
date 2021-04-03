package com.example.demo.br.cm.cm_main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.demo.anotation.OpService;
import com.example.demo.db.da.cm.DA_CM_MAIN;
import com.example.demo.db.domain.cm.CmMenu;
import com.example.demo.exception.BizException;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@OpService
@Service
@Tag(name = "CM_MAIN", description = "메인로딩")
public class BR_CM_MAIN_FIND_TREE {
	
	@JsonRootName("IN_DS")
	@Schema(name = "IN_DS-BR_CM_MAIN_FIND_TREE")
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
	@Schema(name="OUT_DS-BR_CM_MAIN_FIND_TREE")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_CM_MAIN_FIND_TREE", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}
	
	@Schema(name = "OUT_DATA_ROW-BR_CM_MAIN_FIND_TREE")
	@Data
	static class OUT_DATA_ROW {
		@JsonProperty("PGM_ID")
		@Schema(name = "PGM_ID", example = "CM_01000", description = "프로그램ID")
		String PGM_ID = null;
		@JsonProperty("PRNT_MENU_CD")
		@Schema(name = "PRNT_MENU_CD", example = "PRNT_MENU_CD", description = "상위메뉴코드")
		String PRNT_MENU_CD = null;
		@JsonProperty("ORD")
		@Schema(name = "ORD", example = "001", description = "정렬")
		String ORD = null;
		@JsonProperty("MENU_NO")
		@Schema(name = "MENU_NO", example = "1", description = "메뉴번호")
		Long MENU_NO = null;
		@JsonProperty("MENU_CD")
		@Schema(name = "MENU_CD", example = "MN_0100", description = "메뉴코드")
		String MENU_CD = null;
		@JsonProperty("MENU_NM")
		@Schema(name = "MENU_NM", example = "공통", description = "메뉴명")
		String MENU_NM = null;
		@JsonProperty("MENU_PATH")
		@Schema(name = "MENU_PATH", example = "????", description = "메뉴경로")
		String MENU_PATH = null;
		@JsonProperty("MENU_LVL")
		@Schema(name = "MENU_LVL", example = "1", description = "메뉴레벨")
		Integer MENU_LVL = null;
		@JsonProperty("MENU_KIND")
		@Schema(name = "MENU_KIND", example = "S", description = "메뉴종료")
		String MENU_KIND = null;
		@JsonProperty("_attributes")
		@Schema(name = "_attributes", example = "expanded=true", description = "expanded (true ,false)")
		HashMap<String,Object> _attributes = null;
		@JsonProperty("_children")
		@Schema(name = "_children", example = "expanded=true", description = "expanded (true ,false)")
		ArrayList<OUT_DATA_ROW> _children = null;
		
	}
	
	@Autowired
	DA_CM_MAIN daMain;
	
	@Operation(summary = "메인 TREE 메뉴 조회.", description = "")
	@ApiResponses(value = { 
		@ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(
					implementation = OUT_DS.class)) }) 
	})
	//@PostMapping(path= "/api/BR_CM_MAIN_FIND_TREE", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDs) throws BizException {
		OUT_DS  outDs =  new OUT_DS();
		ArrayList<OUT_DATA_ROW>  OUT_DATA =  new ArrayList<OUT_DATA_ROW>();
		List<CmMenu> al= daMain.findMainMenu();//파라미터 사용안함
		for(int i=0;i<al.size();i++) {
			CmMenu cm=al.get(i);
			int MENU_LVL=0;
			OUT_DATA_ROW  row = new OUT_DATA_ROW();
			row.PGM_ID= cm.getPgmId();
			row.PRNT_MENU_CD= cm.getPrntMenuCd();
			row.ORD= cm.getOrd();
			row.MENU_NO= cm.getMenuNo();
			row.MENU_CD= cm.getMenuCd();
			row.MENU_NM= cm.getMenuNm();
			row.MENU_PATH= cm.getMenuNm();
			row.MENU_LVL= MENU_LVL;
			row.MENU_KIND= cm.getMenuKind();
			HashMap<String,Object> tmp = new HashMap<String,Object>();
			tmp.put("expanded", true);
			row._attributes=tmp;
			
			OUT_DATA.add(row);
			this.findSubMenuRoot(row,cm.getMenuCd(),cm.getMenuNm(),MENU_LVL);
		}
		outDs.OUT_DATA= OUT_DATA;
		return outDs;
	}
	public void findSubMenuRoot(OUT_DATA_ROW prntMap, String MENU_CD,String MENU_PATH,int MENU_LVL)  {
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
		List<CmMenu> subAl= daMain.findSubMenu(MENU_CD);
		MENU_LVL=MENU_LVL+1;
		if(subAl.size()>0) {
			prntMap._children=OUT_DATA;	
		}
		for(int j=0;j<subAl.size();j++) {
			CmMenu subC=subAl.get(j);
			OUT_DATA_ROW  child_row = new OUT_DATA_ROW();
			child_row.PGM_ID=subC.getPgmId();
			child_row.PRNT_MENU_CD=subC.getPrntMenuCd();
			child_row.MENU_NO=subC.getMenuNo();
			child_row.MENU_CD=subC.getMenuCd();
			child_row.MENU_NM=subC.getMenuNm();
			child_row.MENU_PATH=MENU_PATH+">>"+subC.getMenuNm();
			child_row.MENU_LVL=MENU_LVL;
			child_row.MENU_KIND=subC.getMenuKind();
			HashMap<String,Object> tmp = new HashMap<String,Object>();
			tmp.put("expanded", true);
			child_row._attributes= tmp;
			OUT_DATA.add(child_row);
			this.findSubMenuRoot(child_row, subC.getMenuCd(),(MENU_PATH+">>"+subC.getMenuNm()),MENU_LVL);
		}
	}
}
