package com.example.demo.br;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.da.DA_MAIN;
import com.example.demo.domain.CmMenu;
import com.example.demo.domain.CmPgm;
import com.example.demo.domain.CmUser;
import com.example.demo.exception.BizException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Service
public class BR_MAIN {
	@Autowired
	DA_MAIN daMenu;

	public String findMainMenu() throws BizException {
		List<CmMenu> al= daMenu.findMainMenu();
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>();
		for(int i=0;i<al.size();i++) {
			CmMenu cm=al.get(i);
			HashMap<String, Object>  hm = new HashMap<String, Object>();
			ArrayList<HashMap<String, Object>> child = new ArrayList<HashMap<String,Object>>();
			hm.put("child", child);
			hm.put("fst_ord", cm.getFstOrd());
			hm.put("menu_cd", cm.getMenuCd());
			hm.put("menu_nm", cm.getMenuNm());
			hm.put("menu_kind", cm.getMenuKind());
			List<CmMenu> subAl= daMenu.findSubMenu(cm.getMenuCd());
			for(int j=0;j<subAl.size();j++) {
				CmMenu subC=subAl.get(j);
				HashMap<String, Object>  subHm = new HashMap<String, Object>();
				subHm.put("pgm_id", subC.getPgmId());
				subHm.put("menu_cd", subC.getMenuCd());
				subHm.put("menu_nm", subC.getMenuNm());
				subHm.put("sed_ord", subC.getSedOrd());
				subHm.put("menu_kind", subC.getMenuKind());
				child.add(subHm);
			}
			list.add(hm);
		}
		ObjectMapper om = new ObjectMapper();
		om.enable(SerializationFeature.INDENT_OUTPUT);
		String jsonOutString = null;
		try {
			 jsonOutString = om.writeValueAsString(list);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jsonOutString;
	}
	
	public String findMainPgm() throws BizException {
		List<CmPgm> al= daMenu.findMainPgm();
		ObjectMapper om = new ObjectMapper();
		om.enable(SerializationFeature.INDENT_OUTPUT);
		String jsonOutString = null;
		try {
			 jsonOutString = om.writeValueAsString(al);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonOutString;
	}
	
	
}
