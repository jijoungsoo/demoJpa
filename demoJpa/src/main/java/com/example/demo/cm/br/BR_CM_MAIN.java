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
	DA_CM_MAIN daMenu;

	@OpService
	public OUT_DS findMainMenu(IN_DS inDs) throws BizException {
		List<CmMenu> al= daMenu.findMainMenu();//파라미터 사용안함
		ArrayList<HashMap<String, Object>> OUT_DATA = new ArrayList<HashMap<String,Object>>();
		ArrayList<HashMap<String, Object>> OUT_CHILD_DATA = new ArrayList<HashMap<String,Object>>();
		for(int i=0;i<al.size();i++) {
			CmMenu cm=al.get(i);
			HashMap<String, Object>  OUT_DATA_ROW = new HashMap<String, Object>();
			
			OUT_DATA_ROW.put("fst_ord", cm.getFstOrd());
			OUT_DATA_ROW.put("menu_cd", cm.getMenuCd());
			OUT_DATA_ROW.put("menu_nm", cm.getMenuNm());
			OUT_DATA_ROW.put("menu_kind", cm.getMenuKind());
			List<CmMenu> subAl= daMenu.findSubMenu(cm.getMenuCd());
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
	public OUT_DS findMainPgm(IN_DS inDs) throws BizException {
		List<CmPgm> al= daMenu.findMainPgm();//파라미터 사용안함
		ArrayList<HashMap<String, Object>> OUT_DATA = new ArrayList<HashMap<String,Object>>();
		for(int i=0;i<al.size();i++) {
			CmPgm cm=al.get(i);
			HashMap<String, Object>  OUT_DATA_ROW = new HashMap<String, Object>();
			OUT_DATA_ROW.put("PGM_ID", cm.getPgmId());
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
