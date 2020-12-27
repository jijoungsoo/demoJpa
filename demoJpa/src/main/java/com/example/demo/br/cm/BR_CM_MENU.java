package com.example.demo.br.cm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.IN_DS;
import com.example.demo.ctrl.OUT_DS;
import com.example.demo.db.da.cm.DA_CM_MENU;
import com.example.demo.db.da.cm.DA_CM_SEQ;
import com.example.demo.db.domain.cm.CmMenu;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
import com.example.demo.utils.PjtUtil;

@Service
public class BR_CM_MENU {
	@Autowired
	DA_CM_MENU daMenu;
	
	@Autowired
	DA_CM_SEQ daCmSeq;

	@OpService
	public OUT_DS  findMenu(IN_DS inDS) throws BizException {
		List<CmMenu>  al =daMenu.findMenu();
		ArrayList<HashMap<String, Object>> OUT_DATA = new ArrayList<HashMap<String,Object>>();
		for(int i=0;i<al.size();i++) {
			CmMenu cm=al.get(i);
			HashMap<String, Object>  OUT_DATA_ROW = new HashMap<String, Object>();
			OUT_DATA_ROW.put("MENU_NO", cm.getMenuNo());
			OUT_DATA_ROW.put("MENU_CD", cm.getMenuCd());
			OUT_DATA_ROW.put("MENU_NM", cm.getMenuNm());
			OUT_DATA_ROW.put("PRNT_MENU_CD", cm.getPrntMenuCd());
			OUT_DATA_ROW.put("ORD", cm.getOrd());
			OUT_DATA_ROW.put("PGM_ID", cm.getPgmId());
			OUT_DATA_ROW.put("MENU_KIND", cm.getMenuKind());
			OUT_DATA_ROW.put("RMK", cm.getRmk());
			OUT_DATA_ROW.put("MENU_LVL", cm.getMenuLvl());
			OUT_DATA_ROW.put("MENU_PATH", cm.getMenuPath());
			OUT_DATA_ROW.put("CRT_DTM",PjtUtil.getYyyy_MM_dd_HHMMSS(cm.getCrtDtm()));
			OUT_DATA_ROW.put("UPDT_DTM",PjtUtil.getYyyy_MM_dd_HHMMSS(cm.getUpdtDtm()));
			OUT_DATA.add(OUT_DATA_ROW);
		}
		OUT_DS outDs = new OUT_DS();
		outDs.put("OUT_DATA", OUT_DATA);
		return outDs;
	}
	@OpService
	public OUT_DS findMenuRoot(IN_DS inDs) throws BizException {
		List<CmMenu> al= daMenu.findMenuRoot();//파라미터 사용안함
		ArrayList<HashMap<String, Object>> OUT_DATA = new ArrayList<HashMap<String,Object>>();
		for(int i=0;i<al.size();i++) {
			CmMenu cm=al.get(i);
			int MENU_LVL=0;
			HashMap<String, Object>  OUT_DATA_ROW = new HashMap<String, Object>();
			OUT_DATA_ROW.put("PGM_ID", cm.getPgmId());
			OUT_DATA_ROW.put("PRNT_MENU_CD", cm.getPrntMenuCd());
			OUT_DATA_ROW.put("ORD", cm.getOrd());
			OUT_DATA_ROW.put("MENU_NO", cm.getMenuNo());
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
		List<CmMenu> subAl= daMenu.findSubMenuRoot(MENU_CD);
		MENU_LVL=MENU_LVL+1;
		if(subAl.size()>0) {
			prntMap.put("_children", OUT_DATA);	
		}
		for(int j=0;j<subAl.size();j++) {
			CmMenu subC=subAl.get(j);
			HashMap<String, Object>  OUT_CHILD_DATA_ROW = new HashMap<String, Object>();
			OUT_CHILD_DATA_ROW.put("PGM_ID", subC.getPgmId());
			OUT_CHILD_DATA_ROW.put("PRNT_MENU_CD", subC.getPrntMenuCd());
			OUT_CHILD_DATA_ROW.put("MENU_NO", subC.getMenuNo());
			OUT_CHILD_DATA_ROW.put("MENU_CD", subC.getMenuCd());
			OUT_CHILD_DATA_ROW.put("MENU_NM", subC.getMenuNm());
			OUT_CHILD_DATA_ROW.put("MENU_PATH", MENU_PATH+">>"+subC.getMenuNm());
			OUT_CHILD_DATA_ROW.put("MENU_LVL", MENU_LVL);
			OUT_CHILD_DATA_ROW.put("ORD", subC.getOrd());
			OUT_CHILD_DATA_ROW.put("MENU_KIND", subC.getMenuKind());
			HashMap<String,Object> tmp = new HashMap<String,Object>();
			tmp.put("expanded", true);
			OUT_CHILD_DATA_ROW.put("_attributes", tmp);
			OUT_DATA.add(OUT_CHILD_DATA_ROW);
			this.findSubMenuRoot(OUT_CHILD_DATA_ROW, subC.getMenuCd(),(MENU_PATH+">>"+subC.getMenuNm()),MENU_LVL);
		}
	}
	
	@OpService
	public OUT_DS saveMenu(IN_DS inDS) throws BizException {
		for( int i=0;i<inDS.get("IN_DATA").size();i++) {
			HashMap<String,Object>  rs =inDS.get("IN_DATA").get(i);
			String  MENU_CD 		= PjtUtil.str(rs.get("MENU_CD"));
			String  MENU_NM 		= PjtUtil.str(rs.get("MENU_NM"));
			String  PRNT_MENU_CD 	= PjtUtil.str(rs.get("PRNT_MENU_CD"));
			String  ORD 			= PjtUtil.str(rs.get("ORD"));
			String  PGM_ID 			= PjtUtil.str(rs.get("PGM_ID"));
			String  MENU_LVL 		= PjtUtil.str(rs.get("MENU_LVL"));
			String  MENU_KIND 		= PjtUtil.str(rs.get("MENU_KIND"));
			String  MENU_PATH 		= PjtUtil.str(rs.get("MENU_PATH"));
			String  RMK 			= PjtUtil.str(rs.get("RMK"));
			
			if(PjtUtil.isEmpty(MENU_CD)) {
				throw new BizRuntimeException("메뉴코드가 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(MENU_NM)) {
				throw new BizRuntimeException("메뉴명이 입력되지 않았습니다.");
			}

			if(PjtUtil.isEmpty(ORD)) {
				throw new BizRuntimeException("정렬이 입력되지 않았습니다.");
			}
			
			long L_MENU_NO =daCmSeq.increate("CM_MENU_MENU_NO_SEQ");
						
			daMenu.createMenu(
					L_MENU_NO
					,MENU_CD
					,MENU_NM
					,PRNT_MENU_CD
					,ORD
					,PGM_ID
					,MENU_KIND
					,MENU_LVL
					,MENU_PATH
					,RMK
					);
		}
		
		for( int i=0;i<inDS.get("UPDT_DATA").size();i++) {
			HashMap<String,Object>  rs =inDS.get("UPDT_DATA").get(i);
			String  MENU_NO 		= PjtUtil.str(rs.get("MENU_NO"));
			String  MENU_CD 		= PjtUtil.str(rs.get("MENU_CD"));
			String  MENU_NM 		= PjtUtil.str(rs.get("MENU_NM"));
			String  PRNT_MENU_CD 	= PjtUtil.str(rs.get("PRNT_MENU_CD"));
			String  ORD 			= PjtUtil.str(rs.get("ORD"));
			String  PGM_ID 			= PjtUtil.str(rs.get("PGM_ID"));
			String  MENU_KIND 		= PjtUtil.str(rs.get("MENU_KIND"));
			String  MENU_LVL 		= PjtUtil.str(rs.get("MENU_LVL"));
			String  MENU_PATH 		= PjtUtil.str(rs.get("MENU_PATH"));
			String  RMK 			= PjtUtil.str(rs.get("RMK"));
			
			if(PjtUtil.isEmpty(MENU_NO)) {
				throw new BizRuntimeException("메뉴번호가 입력되지 않았습니다.");
			}
			
			if(PjtUtil.isEmpty(MENU_CD)) {
				throw new BizRuntimeException("메뉴코드가 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(MENU_NM)) {
				throw new BizRuntimeException("메뉴명이 입력되지 않았습니다.");
			}
			
			if(PjtUtil.isEmpty(ORD)) {
				throw new BizRuntimeException("정렬이 입력되지 않았습니다.");
			}
			long L_MENU_NO = Long.parseLong(MENU_NO);
			daMenu.updateMenu(
					L_MENU_NO
					,MENU_CD
					,MENU_NM
					,PRNT_MENU_CD
					,ORD
					,PGM_ID
					,MENU_KIND
					,MENU_LVL
					,MENU_PATH
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
			String  MENU_NO 		= PjtUtil.str(rs.get("MENU_NO"));
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
