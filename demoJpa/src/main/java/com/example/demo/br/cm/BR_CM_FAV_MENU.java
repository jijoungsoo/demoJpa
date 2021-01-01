package com.example.demo.br.cm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.IN_DS;
import com.example.demo.ctrl.OUT_DS;
import com.example.demo.db.da.cm.DA_CM_FAV_MENU;
import com.example.demo.db.da.cm.DA_CM_PGM;
import com.example.demo.db.da.cm.DA_CM_SEQ;
import com.example.demo.db.domain.cm.CmFavMenu;
import com.example.demo.db.domain.cm.CmPgm;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
import com.example.demo.utils.PjtUtil;

@Service
public class BR_CM_FAV_MENU {
	@Autowired
	DA_CM_FAV_MENU daFavM;
	
	@Autowired
	DA_CM_SEQ daCmSeq;

	@OpService
	public OUT_DS  findFavMenu(IN_DS inDS) throws BizException {
		ArrayList<HashMap<String, Object>>  al =daFavM.findFavMenu();
		OUT_DS outDs = new OUT_DS();
		outDs.put("OUT_DATA", al);
		return outDs;
	}
	
	@OpService
	public OUT_DS  findFavMenuByUserNo(IN_DS inDS) throws BizException {
		if(inDS.get("LSESSION")==null) {
			throw new BizRuntimeException("세션값이 넘어오지 않았습니다1.");
		}
		if(inDS.get("LSESSION").size()!=1) {
			throw new BizRuntimeException("세션값이 "+inDS.get("LSESSION").size()+"개 넘어왔습니다.");
		}
		String USER_NO =inDS.get("LSESSION").get(0).get("USER_NO").toString();
		Long L_USER_NO = Long.parseLong(USER_NO);
		if(L_USER_NO==null) {
			throw new BizRuntimeException("사용자NO가 넘어오지 않았습니다2.");
		}
		ArrayList<HashMap<String, Object>>  al =daFavM.findFavMenuByUserNo(L_USER_NO);
		OUT_DS outDs = new OUT_DS();
		outDs.put("OUT_DATA", al);
		return outDs;
	}
	
	@OpService
	public OUT_DS createFavMenu(IN_DS inDS) throws BizException {
		if(inDS.get("LSESSION")==null) {
			throw new BizRuntimeException("세션값이 넘어오지 않았습니다1.");
		}
		if(inDS.get("LSESSION").size()!=1) {
			throw new BizRuntimeException("세션값이 "+inDS.get("LSESSION").size()+"개 넘어왔습니다.");
		}
		if(inDS.get("IN_DATA")==null) {
			throw new BizRuntimeException("IN_DATA값이 넘어오지 않았습니다.");
		}
		if(inDS.get("IN_DATA").size()!=1) {
			throw new BizRuntimeException("IN_DATA값이 "+inDS.get("IN_DATA").size()+"개 넘어왔습니다.");
		}
		String USER_NO =inDS.get("LSESSION").get(0).get("USER_NO").toString();
		Long L_USER_NO = Long.parseLong(USER_NO);
		if(L_USER_NO==null) {
			throw new BizRuntimeException("사용자NO가 넘어오지 않았습니다2.");
		}
		
		String USER_ID = (String) inDS.get("LSESSION").get(0).get("USER_ID");
		if(PjtUtil.isEmpty(USER_ID)) {
			throw new BizRuntimeException("사용자ID가 넘어오지 않았습니다2.");
		}
		
		HashMap<String,Object>  rs =inDS.get("IN_DATA").get(0);
		String  MENU_NO 		= PjtUtil.str(rs.get("MENU_NO"));
		
		if(PjtUtil.isEmpty(MENU_NO)) {
			throw new BizRuntimeException("메뉴번호가 입력되지 않았습니다.");
		}
		long L_FAV_NO =daCmSeq.increate("CM_FAV_MENU_FAV_NO_SEQ");
		
		long L_MENU_NO = Long.parseLong(MENU_NO);
		
		CmFavMenu c=daFavM.findFavMenuByMenuCdAndUserNo(L_USER_NO,L_MENU_NO);
		if(c!=null) {
			//기존에 데이터가 있었다.종료
			throw new BizRuntimeException("이미 즐겨찾기에 추가되어있습니다.");
		} else {
			daFavM.createFavMenu(
					L_FAV_NO
					,L_MENU_NO
					,L_USER_NO
			);	
		}
		
		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
	
	@OpService
	public OUT_DS rmFavMenu(IN_DS inDS) throws BizException {
		for( int i=0;i<inDS.get("IN_DATA").size();i++) {
			HashMap<String,Object>  rs =inDS.get("IN_DATA").get(i);
			String  FAV_NO 		= PjtUtil.str(rs.get("FAV_NO"));
			if(PjtUtil.isEmpty(FAV_NO)) {
				throw new BizRuntimeException("즐겨찾기번호가 입력되지 않았습니다.");
			}
			long L_FAV_NO = Long.parseLong(FAV_NO);
			daFavM.rmFavMenu(
					L_FAV_NO
					);
		}
	
		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
}
