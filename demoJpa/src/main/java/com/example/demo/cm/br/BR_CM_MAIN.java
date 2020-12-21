package com.example.demo.cm.br;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.cm.anotation.OpService;
import com.example.demo.cm.ctrl.IN_DS;
import com.example.demo.cm.ctrl.OUT_DS;
import com.example.demo.cm.da.DA_CM_MAIN;
import com.example.demo.cm.utils.PjtUtil;
import com.example.demo.domain.CmMenu;
import com.example.demo.domain.CmPgm;
import com.example.demo.exception.BizException;

@Service
public class BR_CM_MAIN {
	@Autowired
	DA_CM_MAIN daMain;

	@OpService
	public OUT_DS findMainMenu(IN_DS inDs) throws BizException {
		List<CmMenu> al= daMain.findMainMenu();//파라미터 사용안함
		ArrayList<HashMap<String, Object>> OUT_DATA = new ArrayList<HashMap<String,Object>>();
		ArrayList<HashMap<String, Object>> OUT_CHILD_DATA = new ArrayList<HashMap<String,Object>>();
		for(int i=0;i<al.size();i++) {
			CmMenu cm=al.get(i);
			HashMap<String, Object>  OUT_DATA_ROW = new HashMap<String, Object>();
			
			OUT_DATA_ROW.put("fst_ord", cm.getFstOrd());
			OUT_DATA_ROW.put("menu_cd", cm.getMenuCd());
			OUT_DATA_ROW.put("menu_nm", cm.getMenuNm());
			OUT_DATA_ROW.put("menu_kind", cm.getMenuKind());
			List<CmMenu> subAl= daMain.findSubMenu(cm.getMenuCd());
			for(int j=0;j<subAl.size();j++) {
				CmMenu subC=subAl.get(j);
				HashMap<String, Object>  OUT_CHILD_DATA_ROW = new HashMap<String, Object>();
				OUT_CHILD_DATA_ROW.put("pgm_id", subC.getPgmId());
				OUT_CHILD_DATA_ROW.put("prnt_menu_cd", subC.getPrntMenuCd());
				OUT_CHILD_DATA_ROW.put("menu_cd", subC.getMenuCd());
				OUT_CHILD_DATA_ROW.put("menu_nm", subC.getMenuNm());
				OUT_CHILD_DATA_ROW.put("sed_ord", subC.getSedOrd());
				OUT_CHILD_DATA_ROW.put("menu_kind", subC.getMenuKind());
				OUT_CHILD_DATA.add(OUT_CHILD_DATA_ROW);
			}
			OUT_DATA.add(OUT_DATA_ROW);
		}
		OUT_DS outDs = new OUT_DS();
		outDs.put("OUT_DATA", OUT_DATA);
		outDs.put("OUT_CHILD_DATA", OUT_CHILD_DATA);
		
		return outDs;
	}
	@OpService
	public OUT_DS findMainMenu2(IN_DS inDs) throws BizException {
		List<CmMenu> al= daMain.findMainMenu();//파라미터 사용안함
		ArrayList<HashMap<String, Object>> OUT_DATA = new ArrayList<HashMap<String,Object>>();
		for(int i=0;i<al.size();i++) {
			CmMenu cm=al.get(i);
			int MENU_LVL=0;
			HashMap<String, Object>  OUT_DATA_ROW = new HashMap<String, Object>();
			OUT_DATA_ROW.put("PGM_ID", cm.getPgmId());
			OUT_DATA_ROW.put("PRNT_MENU_CD", cm.getPrntMenuCd());
			OUT_DATA_ROW.put("FST_ORD", cm.getFstOrd());
			OUT_DATA_ROW.put("MENU_CD", cm.getMenuCd());
			OUT_DATA_ROW.put("MENU_NM", cm.getMenuNm());
			OUT_DATA_ROW.put("MENU_PATH", cm.getMenuNm());
			OUT_DATA_ROW.put("MENU_LVL", MENU_LVL);
			OUT_DATA_ROW.put("MENU_KIND", cm.getMenuKind());
			OUT_DATA.add(OUT_DATA_ROW);
			this.findSubMenu2(OUT_DATA_ROW,cm.getMenuCd(),cm.getMenuNm(),MENU_LVL);
		}
		OUT_DS outDs = new OUT_DS();
		outDs.put("OUT_DATA", OUT_DATA);
		return outDs;
	}
	public void findSubMenu2(HashMap<String, Object> prntMap, String MENU_CD,String MENU_PATH,int MENU_LVL)  {
		ArrayList<HashMap<String, Object>> OUT_DATA = new ArrayList<HashMap<String,Object>>();
		List<CmMenu> subAl= daMain.findSubMenu(MENU_CD);
		MENU_LVL=MENU_LVL+1;
		for(int j=0;j<subAl.size();j++) {
			CmMenu subC=subAl.get(j);
			HashMap<String, Object>  OUT_CHILD_DATA_ROW = new HashMap<String, Object>();
			OUT_CHILD_DATA_ROW.put("PGM_ID", subC.getPgmId());
			OUT_CHILD_DATA_ROW.put("PRNT_MENU_CD", subC.getPrntMenuCd());
			OUT_CHILD_DATA_ROW.put("MENU_CD", subC.getMenuCd());
			OUT_CHILD_DATA_ROW.put("MENU_NM", subC.getMenuNm());
			OUT_CHILD_DATA_ROW.put("MENU_PATH", MENU_PATH+">>"+subC.getMenuNm());
			OUT_CHILD_DATA_ROW.put("MENU_LVL", MENU_LVL);
			OUT_CHILD_DATA_ROW.put("SED_ORD", subC.getSedOrd());
			OUT_CHILD_DATA_ROW.put("MENU_KIND", subC.getMenuKind());
			OUT_DATA.add(OUT_CHILD_DATA_ROW);
			this.findSubMenu2(OUT_CHILD_DATA_ROW, subC.getMenuCd(),(MENU_PATH+">>"+subC.getMenuNm()),MENU_LVL);
		}
		prntMap.put("child", OUT_DATA);
	}
	
	@OpService
	public OUT_DS findMainMenuTree(IN_DS inDs) throws BizException {
		List<CmMenu> al= daMain.findMainMenu();//파라미터 사용안함
		ArrayList<HashMap<String, Object>> OUT_DATA = new ArrayList<HashMap<String,Object>>();
		ArrayList<HashMap<String, Object>> OUT_CHILD_DATA = new ArrayList<HashMap<String,Object>>();
		for(int i=0;i<al.size();i++) {
			CmMenu cm=al.get(i);
			HashMap<String, Object>  OUT_DATA_ROW = new HashMap<String, Object>();
			
			OUT_DATA_ROW.put("fst_ord", cm.getFstOrd());
			OUT_DATA_ROW.put("menu_cd", cm.getMenuCd());
			OUT_DATA_ROW.put("menu_nm", cm.getMenuNm());
			OUT_DATA_ROW.put("menu_kind", cm.getMenuKind());
			this.findSubMenuTree(OUT_DATA_ROW,cm.getMenuCd()); //반복쪽으로 재귀호출
			OUT_DATA.add(OUT_DATA_ROW);
		}
		OUT_DS outDs = new OUT_DS();
		outDs.put("OUT_DATA", OUT_DATA);
		outDs.put("OUT_CHILD_DATA", OUT_CHILD_DATA);
		
		return outDs;
	}
	
	private void findSubMenuTree(HashMap<String, Object> prntMap,String MENU_CD){
		ArrayList<HashMap<String, Object>> OUT_CHILD_DATA = new ArrayList<HashMap<String,Object>>();
		List<CmMenu> subAl= daMain.findSubMenu(MENU_CD);
		for(int j=0;j<subAl.size();j++) {
			CmMenu subC=subAl.get(j);
			HashMap<String, Object>  OUT_CHILD_DATA_ROW = new HashMap<String, Object>();
			OUT_CHILD_DATA_ROW.put("pgm_id", subC.getPgmId());
			OUT_CHILD_DATA_ROW.put("prnt_menu_cd", subC.getPrntMenuCd());
			OUT_CHILD_DATA_ROW.put("menu_cd", subC.getMenuCd());
			OUT_CHILD_DATA_ROW.put("menu_nm", subC.getMenuNm());
			OUT_CHILD_DATA_ROW.put("sed_ord", subC.getSedOrd());
			OUT_CHILD_DATA_ROW.put("menu_kind", subC.getMenuKind());
			OUT_CHILD_DATA.add(OUT_CHILD_DATA_ROW);
			this.findSubMenuTree(OUT_CHILD_DATA_ROW,subC.getMenuCd()); //반복쪽으로 재귀호출
		}
		if(OUT_CHILD_DATA.size()>0) {
			prntMap.put("child",OUT_CHILD_DATA);
		}
	}
	
	
	@OpService
	public OUT_DS findMainPgm(IN_DS inDs) throws BizException {
		List<CmPgm> al= daMain.findMainPgm();//파라미터 사용안함
		ArrayList<HashMap<String, Object>> OUT_DATA = new ArrayList<HashMap<String,Object>>();
		for(int i=0;i<al.size();i++) {
			CmPgm cm=al.get(i);
			HashMap<String, Object>  OUT_DATA_ROW = new HashMap<String, Object>();
			OUT_DATA_ROW.put("PGM_ID", cm.getPgmId());
			OUT_DATA_ROW.put("DIR_LINK", cm.getDirLink());
			OUT_DATA_ROW.put("PGM_LINK", cm.getPgmLink());
			OUT_DATA_ROW.put("PGM_NM", cm.getPgmNm());
			OUT_DATA_ROW.put("RMK", cm.getRmk());
			OUT_DATA_ROW.put("CRT_DTM",PjtUtil.getYyyyMMddHHMMSS(cm.getCrtDtm()));
			OUT_DATA_ROW.put("UPDT_DTM",PjtUtil.getYyyyMMddHHMMSS(cm.getUpdtDtm()));
			OUT_DATA.add(OUT_DATA_ROW);
		}
		OUT_DS outDs = new OUT_DS();
		outDs.put("OUT_DATA", OUT_DATA);
		return outDs;
	}
	
	
}
