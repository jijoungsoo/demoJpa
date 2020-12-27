package com.example.demo.br.cm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.IN_DS;
import com.example.demo.ctrl.OUT_DS;
import com.example.demo.db.da.cm.DA_CM_MAIN;
import com.example.demo.db.domain.cm.CmMenu;
import com.example.demo.db.domain.cm.CmPgm;
import com.example.demo.exception.BizException;
import com.example.demo.utils.PjtUtil;

@Service
public class BR_CM_MAIN {
	@Autowired
	DA_CM_MAIN daMain;

	
	@OpService
	public OUT_DS findMainMenuRoot(IN_DS inDs) throws BizException {
		List<CmMenu> al= daMain.findMainMenu();//파라미터 사용안함
		ArrayList<HashMap<String, Object>> OUT_DATA = new ArrayList<HashMap<String,Object>>();
		for(int i=0;i<al.size();i++) {
			CmMenu cm=al.get(i);
			int MENU_LVL=0;
			HashMap<String, Object>  OUT_DATA_ROW = new HashMap<String, Object>();
			OUT_DATA_ROW.put("PGM_ID", cm.getPgmId());
			OUT_DATA_ROW.put("PRNT_MENU_CD", cm.getPrntMenuCd());
			OUT_DATA_ROW.put("ORD", cm.getOrd());
			OUT_DATA_ROW.put("MENU_CD", cm.getMenuCd());
			OUT_DATA_ROW.put("MENU_NM", cm.getMenuNm());
			OUT_DATA_ROW.put("MENU_PATH", cm.getMenuNm());
			OUT_DATA_ROW.put("MENU_LVL", MENU_LVL);
			OUT_DATA_ROW.put("MENU_KIND", cm.getMenuKind());
			HashMap<String,Object> tmp = new HashMap<String,Object>();
			tmp.put("expanded", true);
			OUT_DATA_ROW.put("_attributes", tmp);
			
			OUT_DATA.add(OUT_DATA_ROW);
			this.findSubMenuRoot(OUT_DATA_ROW,cm.getMenuCd(),cm.getMenuNm(),MENU_LVL);
		}
		OUT_DS outDs = new OUT_DS();
		outDs.put("OUT_DATA", OUT_DATA);
		return outDs;
	}
	public void findSubMenuRoot(HashMap<String, Object> prntMap, String MENU_CD,String MENU_PATH,int MENU_LVL)  {
		ArrayList<HashMap<String, Object>> OUT_DATA = new ArrayList<HashMap<String,Object>>();
		List<CmMenu> subAl= daMain.findSubMenu(MENU_CD);
		MENU_LVL=MENU_LVL+1;
		if(subAl.size()>0) {
			prntMap.put("_children", OUT_DATA);	
		}
		for(int j=0;j<subAl.size();j++) {
			CmMenu subC=subAl.get(j);
			HashMap<String, Object>  OUT_CHILD_DATA_ROW = new HashMap<String, Object>();
			OUT_CHILD_DATA_ROW.put("PGM_ID", subC.getPgmId());
			OUT_CHILD_DATA_ROW.put("PRNT_MENU_CD", subC.getPrntMenuCd());
			OUT_CHILD_DATA_ROW.put("MENU_CD", subC.getMenuCd());
			OUT_CHILD_DATA_ROW.put("MENU_NM", subC.getMenuNm());
			OUT_CHILD_DATA_ROW.put("MENU_PATH", MENU_PATH+">>"+subC.getMenuNm());
			OUT_CHILD_DATA_ROW.put("MENU_LVL", MENU_LVL);
			OUT_CHILD_DATA_ROW.put("MENU_KIND", subC.getMenuKind());
			HashMap<String,Object> tmp = new HashMap<String,Object>();
			tmp.put("expanded", true);
			OUT_CHILD_DATA_ROW.put("_attributes", tmp);
			OUT_DATA.add(OUT_CHILD_DATA_ROW);
			this.findSubMenuRoot(OUT_CHILD_DATA_ROW, subC.getMenuCd(),(MENU_PATH+">>"+subC.getMenuNm()),MENU_LVL);
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
