package com.example.demo.cm.br;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.cm.anotation.OpService;
import com.example.demo.cm.ctrl.IN_DS;
import com.example.demo.cm.ctrl.OUT_DS;
import com.example.demo.cm.da.DA_CM_MENU;
import com.example.demo.cm.utils.PjtUtil;
import com.example.demo.domain.CmMenu;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;

@Service
public class BR_CM_MENU {
	@Autowired
	DA_CM_MENU daMenu;

	@OpService
	public OUT_DS  findMenu(IN_DS inDS) throws BizException {
		List<CmMenu>  al =daMenu.findMenu();
		ArrayList<HashMap<String, Object>> OUT_DATA = new ArrayList<HashMap<String,Object>>();
		for(int i=0;i<al.size();i++) {
			CmMenu cm=al.get(i);
			HashMap<String, Object>  OUT_DATA_ROW = new HashMap<String, Object>();
			OUT_DATA_ROW.put("MENU_CD", cm.getMenuCd());
			OUT_DATA_ROW.put("MENU_NM", cm.getMenuNm());
			OUT_DATA_ROW.put("PRNT_MENU_CD", cm.getPrntMenuCd());
			OUT_DATA_ROW.put("FST_ORD", cm.getFstOrd());
			OUT_DATA_ROW.put("SED_ORD", cm.getSedOrd());
			OUT_DATA_ROW.put("PGM_ID", cm.getPgmId());
			OUT_DATA_ROW.put("MENU_KIND", cm.getMenuKind());
			OUT_DATA_ROW.put("RMK", cm.getRmk());
			OUT_DATA_ROW.put("CRT_DTM",PjtUtil.getYyyy_MM_dd_HHMMSS(cm.getCrtDtm()));
			OUT_DATA_ROW.put("UPDT_DTM",PjtUtil.getYyyy_MM_dd_HHMMSS(cm.getUpdtDtm()));
			OUT_DATA.add(OUT_DATA_ROW);
		}
		OUT_DS outDs = new OUT_DS();
		outDs.put("OUT_DATA", OUT_DATA);
		return outDs;
	}
	@OpService
	public OUT_DS  findMenu2(IN_DS inDS) throws BizException {
		List<CmMenu>  al =daMenu.findMenuRoot();
		ArrayList<HashMap<String, Object>> OUT_DATA = new ArrayList<HashMap<String,Object>>();
		for(int i=0;i<al.size();i++) {
			CmMenu cm=al.get(i);
			int MENU_LVL=0;
			HashMap<String, Object>  OUT_DATA_ROW = new HashMap<String, Object>();
			OUT_DATA_ROW.put("MENU_CD", cm.getMenuCd());
			OUT_DATA_ROW.put("MENU_NM", cm.getMenuNm());
			OUT_DATA_ROW.put("MENU_PATH", cm.getMenuNm());
			OUT_DATA_ROW.put("MENU_LVL", MENU_LVL);
			OUT_DATA_ROW.put("PRNT_MENU_CD", cm.getPrntMenuCd());
			OUT_DATA_ROW.put("FST_ORD", cm.getFstOrd());
			OUT_DATA_ROW.put("SED_ORD", cm.getSedOrd());
			OUT_DATA_ROW.put("PGM_ID", cm.getPgmId());
			OUT_DATA_ROW.put("MENU_KIND", cm.getMenuKind());
			OUT_DATA_ROW.put("RMK", cm.getRmk());
			OUT_DATA_ROW.put("CRT_DTM",PjtUtil.getYyyy_MM_dd_HHMMSS(cm.getCrtDtm()));
			OUT_DATA_ROW.put("UPDT_DTM",PjtUtil.getYyyy_MM_dd_HHMMSS(cm.getUpdtDtm()));
			OUT_DATA.add(OUT_DATA_ROW);
			this.findSubMenu2(OUT_DATA ,cm.getMenuCd(),cm.getMenuNm(),MENU_LVL);
		}
		OUT_DS outDs = new OUT_DS();
		outDs.put("OUT_DATA", OUT_DATA);
		return outDs;
	}
	
	private void  findSubMenu2(ArrayList<HashMap<String, Object>> prntMap, String MENU_CD,String MENU_PATH,int MENU_LVL) throws BizException {
		List<CmMenu>  al =daMenu.findSubMenu(MENU_CD);
		MENU_LVL=MENU_LVL+1;
		for(int i=0;i<al.size();i++) {
			CmMenu cm=al.get(i);
			HashMap<String, Object>  OUT_DATA_ROW = new HashMap<String, Object>();
			OUT_DATA_ROW.put("MENU_CD", cm.getMenuCd());
			OUT_DATA_ROW.put("MENU_NM", cm.getMenuNm());
			OUT_DATA_ROW.put("MENU_PATH", MENU_PATH+">>"+cm.getMenuNm());
			OUT_DATA_ROW.put("MENU_LVL", MENU_LVL);
			OUT_DATA_ROW.put("PRNT_MENU_CD", cm.getPrntMenuCd());
			OUT_DATA_ROW.put("FST_ORD", cm.getFstOrd());
			OUT_DATA_ROW.put("SED_ORD", cm.getSedOrd());
			OUT_DATA_ROW.put("PGM_ID", cm.getPgmId());
			OUT_DATA_ROW.put("MENU_KIND", cm.getMenuKind());
			OUT_DATA_ROW.put("RMK", cm.getRmk());
			OUT_DATA_ROW.put("CRT_DTM",PjtUtil.getYyyy_MM_dd_HHMMSS(cm.getCrtDtm()));
			OUT_DATA_ROW.put("UPDT_DTM",PjtUtil.getYyyy_MM_dd_HHMMSS(cm.getUpdtDtm()));
			prntMap.add(OUT_DATA_ROW);
			this.findSubMenu2(prntMap, cm.getMenuCd(),(MENU_PATH+">>"+cm.getMenuNm()),MENU_LVL);
		}
	}
	
	@OpService
	public OUT_DS saveMenu(IN_DS inDS) throws BizException {
		for( int i=0;i<inDS.get("IN_DATA").size();i++) {
			HashMap<String,Object>  rs =inDS.get("IN_DATA").get(i);
			String  MENU_CD 		= PjtUtil.str(rs.get("MENU_CD"));
			String  MENU_NM 		= PjtUtil.str(rs.get("MENU_NM"));
			String  PRNT_MENU_CD 	= PjtUtil.str(rs.get("PRNT_MENU_CD"));
			String  FST_ORD 		= PjtUtil.str(rs.get("FST_ORD"));
			String  SED_ORD 		= PjtUtil.str(rs.get("SED_ORD"));
			String  PGM_ID 			= PjtUtil.str(rs.get("PGM_ID"));
			String  MENU_KIND 		= PjtUtil.str(rs.get("MENU_KIND"));
			String  RMK 			= PjtUtil.str(rs.get("RMK"));
			
			if(PjtUtil.isEmpty(MENU_CD)) {
				throw new BizRuntimeException("메뉴코드가 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(MENU_NM)) {
				throw new BizRuntimeException("메뉴명이 입력되지 않았습니다.");
			}

			if(PjtUtil.isEmpty(FST_ORD)) {
				throw new BizRuntimeException("첫번째정렬이 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(SED_ORD)) {
				throw new BizRuntimeException("두번째정렬이 입력되지 않았습니다.");
			}
						
			daMenu.createMenu(
					MENU_CD
					,MENU_NM
					,PRNT_MENU_CD
					,FST_ORD
					,SED_ORD
					,PGM_ID
					,MENU_KIND
					,RMK
					);
		}
		
		for( int i=0;i<inDS.get("UPDT_DATA").size();i++) {
			HashMap<String,Object>  rs =inDS.get("UPDT_DATA").get(i);
			String  MENU_CD 		= PjtUtil.str(rs.get("MENU_CD"));
			String  MENU_NM 		= PjtUtil.str(rs.get("MENU_NM"));
			String  PRNT_MENU_CD 	= PjtUtil.str(rs.get("PRNT_MENU_CD"));
			String  FST_ORD 		= PjtUtil.str(rs.get("FST_ORD"));
			String  SED_ORD 		= PjtUtil.str(rs.get("SED_ORD"));
			String  PGM_ID 			= PjtUtil.str(rs.get("PGM_ID"));
			String  MENU_KIND 		= PjtUtil.str(rs.get("MENU_KIND"));
			String  RMK 			= PjtUtil.str(rs.get("RMK"));
			
			if(PjtUtil.isEmpty(MENU_CD)) {
				throw new BizRuntimeException("메뉴코드가 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(MENU_NM)) {
				throw new BizRuntimeException("메뉴명이 입력되지 않았습니다.");
			}
			
			if(PjtUtil.isEmpty(FST_ORD)) {
				throw new BizRuntimeException("첫번째정렬이 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(SED_ORD)) {
				throw new BizRuntimeException("두번째정렬이 입력되지 않았습니다.");
			}
			daMenu.updateMenu(
					MENU_CD
					,MENU_NM
					,PRNT_MENU_CD
					,FST_ORD
					,SED_ORD
					,PGM_ID
					,MENU_KIND
					,RMK
					);
		}

		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
	
	@OpService
	public OUT_DS rmMenu(IN_DS inDS) throws BizException {
		for( int i=0;i<inDS.get("IN_DATA").size();i++) {
			HashMap<String,Object>  rs =inDS.get("IN_DATA").get(i);
			String  MENU_CD 		= PjtUtil.str(rs.get("MENU_CD"));
			if(PjtUtil.isEmpty(MENU_CD)) {
				throw new BizRuntimeException("메뉴코드가 입력되지 않았습니다.");
			}
			
			daMenu.rmMenu(
					MENU_CD
					);
		}
	
		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
}
