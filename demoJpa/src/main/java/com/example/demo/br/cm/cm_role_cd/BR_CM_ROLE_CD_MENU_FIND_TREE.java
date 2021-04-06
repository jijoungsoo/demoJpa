package com.example.demo.br.cm.cm_role_cd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.anotation.OpService;
import com.example.demo.db.da.cm.DA_CM_MENU_ROLE_CD_MAPPER;
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

@Tag(name = "CM_ROLE_CD", description = "역할코드")
@Slf4j
@OpService
@Service
public class BR_CM_ROLE_CD_MENU_FIND_TREE {
	@Autowired
    PjtUtil pjtU;

	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_CM_ROLE_CD_MENU_FIND_TREE")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CM_ROLE_CD_MENU_FIND_TREE", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
	}

	
	@ApiModel(value="IN_DATA_ROW-BR_CM_ROLE_CD_MENU_FIND_TREE")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("ROLE_CD")
		@Schema(name = "ROLE_CD", example = "ROLE_ADMIN", description = "역할코드")
		String ROLE_CD = "";
	}

	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_ROLE_CD_MENU_FIND_TREE")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA", title="OUT_DATA-BR_CM_MENU_FIND_TREE", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}

	@ApiModel(value="OUT_DATA_ROW-BR_CM_ROLE_CD_MENU_FIND_TREE")
	@Data
	static class OUT_DATA_ROW {
		@JsonProperty("PGM_ID")
		@Schema(name = "PGM_ID", example = "CM_01000", description = "프로그램ID")
		String PGM_ID = null;
		@JsonProperty("PRNT_MENU_CD")
		@Schema(name = "PRNT_MENU_CD", example = "PRNT_MENU_CD", description = "상위메뉴코드")
		String PRNT_MENU_CD = null;

		@JsonProperty("ROLE_CD")
		@Schema(name = "ROLE_CD", example = "ROLE_ADMIN", description = "역할코드")
		String ROLE_CD = null;

		@JsonProperty("ROLE_YN")
		@Schema(name = "ROLE_YN", example = "Y-부여, N-NO", description = "권한부여")
		String ROLE_YN = null;


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
	DA_CM_MENU_ROLE_CD_MAPPER daMenuRoleCdM;


	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_ROLE_CD"},value = "역할메뉴를 조회힌다.", notes = "")
	//@PostMapping(path= "/api/BR_CM_ROLE_CD_MENU_FIND_TREE", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws Exception {

		if(inDS.IN_DATA==null) {
			throw new BizRuntimeException("IN_DATA 값이 NULL  않았습니다1.");
		}

		if(inDS.IN_DATA.size()!=1) {
			throw new BizRuntimeException("IN_DATA 값이 넘어오지 않았습니다1.");
		}

		if(pjtU.isEmpty(inDS.IN_DATA.get(0).ROLE_CD)) {
			throw new BizRuntimeException("ROLE_CD 값이 넘어오지 않았습니다1.");
		}
		
		OUT_DS  outDs =  new OUT_DS();
		ArrayList<OUT_DATA_ROW>  OUT_DATA =  new ArrayList<OUT_DATA_ROW>();
		String ROLE_CD =inDS.IN_DATA.get(0).ROLE_CD;
		List<Map> al= daMenuRoleCdM.findMenuRoot(ROLE_CD);//파라미터 사용안함

		for(int i=0;i<al.size();i++) {
			Map cm=al.get(i);
			int MENU_LVL=0;
			OUT_DATA_ROW  row = new OUT_DATA_ROW();
			row.PGM_ID		= pjtU.str(cm.get("pgm_id"));
			row.PRNT_MENU_CD= pjtU.str(cm.get("prnt_menu_cd"));
			row.ROLE_CD     = pjtU.str(cm.get("role_cd"));
			row.ROLE_YN     = pjtU.str(cm.get("role_yn"));
			row.ORD			= pjtU.str(cm.get("ord"));
			row.MENU_NO		= Long.parseLong(pjtU.str(cm.get("menu_no")));
			row.MENU_CD		= pjtU.str(cm.get("menu_cd"));
			row.MENU_NM		= pjtU.str(cm.get("menu_nm"));
			row.MENU_PATH	= pjtU.str(cm.get("menu_path"));
			row.MENU_LVL= MENU_LVL;
			row.MENU_KIND	= pjtU.str(cm.get("menu_kind"));
			HashMap<String,Object> tmp = new HashMap<String,Object>();
			tmp.put("expanded", true);
			row._attributes=tmp;
			
			OUT_DATA.add(row);
			this.findSubMenuRoot(row,ROLE_CD,row.MENU_CD,row.MENU_NM,MENU_LVL);
		}
		outDs.OUT_DATA= OUT_DATA;
		return outDs;
	}
	public void findSubMenuRoot(OUT_DATA_ROW prntMap,String ROLE_CD, String MENU_CD,String MENU_PATH,int MENU_LVL)
			throws Exception {
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
		List<Map> subAl= daMenuRoleCdM.findSubMenuRoot(ROLE_CD,MENU_CD);
		MENU_LVL=MENU_LVL+1;
		if(subAl.size()>0) {
			prntMap._children=OUT_DATA;	
		}
		for(int j=0;j<subAl.size();j++) {
			Map subC=subAl.get(j);
			OUT_DATA_ROW  child_row = new OUT_DATA_ROW();
			child_row.PGM_ID		=pjtU.str(subC.get("pgm_id"));
			child_row.PRNT_MENU_CD	=pjtU.str(subC.get("prnt_menu_cd"));
			child_row.ROLE_CD	=pjtU.str(subC.get("role_cd"));
			child_row.ROLE_YN     = pjtU.str(subC.get("role_yn"));
			child_row.MENU_NO		=Long.parseLong(pjtU.str(subC.get("menu_no")));
			child_row.MENU_CD		=pjtU.str(subC.get("menu_cd"));
			child_row.MENU_NM		=pjtU.str(subC.get("menu_nm"));
			child_row.MENU_PATH=MENU_PATH+">>"+pjtU.str(subC.get("menu_nm"));
			child_row.MENU_LVL		=MENU_LVL;
			child_row.MENU_KIND		=pjtU.str(subC.get("menu_kind"));
			HashMap<String,Object> tmp = new HashMap<String,Object>();
			tmp.put("expanded", true);
			child_row._attributes= tmp;
			OUT_DATA.add(child_row);
			this.findSubMenuRoot(child_row, ROLE_CD, child_row.MENU_CD,(MENU_PATH+">>"+child_row.MENU_NM),MENU_LVL);
		}
	}
}
