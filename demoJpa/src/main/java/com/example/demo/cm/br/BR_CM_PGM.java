package com.example.demo.cm.br;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.cm.anotation.OpService;
import com.example.demo.cm.ctrl.IN_DS;
import com.example.demo.cm.ctrl.OUT_DS;
import com.example.demo.cm.da.DA_CM_PGM;
import com.example.demo.cm.utils.PjtUtil;
import com.example.demo.domain.CmPgm;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;

@Service
public class BR_CM_PGM {
	@Autowired
	DA_CM_PGM daPgm;

	@OpService
	public OUT_DS  findPgm(IN_DS inDS) throws BizException {
		List<CmPgm>  al =daPgm.findPgm();
		ArrayList<HashMap<String, Object>> OUT_DATA = new ArrayList<HashMap<String,Object>>();
		for(int i=0;i<al.size();i++) {
			CmPgm cm=al.get(i);
			HashMap<String, Object>  OUT_DATA_ROW = new HashMap<String, Object>();
			OUT_DATA_ROW.put("PGM_ID", cm.getPgmId());
			OUT_DATA_ROW.put("DIR_LINK", cm.getDirLink());
			OUT_DATA_ROW.put("PGM_LINK", cm.getPgmLink());
			OUT_DATA_ROW.put("PGM_NM", cm.getPgmNm());
			OUT_DATA_ROW.put("CATEGORY", cm.getCategory());
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
	public OUT_DS savePgm(IN_DS inDS) throws BizException {
		for( int i=0;i<inDS.get("IN_DATA").size();i++) {
			HashMap<String,Object>  rs =inDS.get("IN_DATA").get(i);
			String  PGM_ID 		= PjtUtil.str(rs.get("PGM_ID"));
			String  PGM_NM 		= PjtUtil.str(rs.get("PGM_NM"));
			String  RMK 		= PjtUtil.str(rs.get("RMK"));
			String  DIR_LINK 	= PjtUtil.str(rs.get("DIR_LINK"));
			String  PGM_LINK 	= PjtUtil.str(rs.get("PGM_LINK"));			
			String  CATEGORY 	= PjtUtil.str(rs.get("CATEGORY"));
			/*
			 * null이어도 ""로 들어와서 처리된다.
			 * 위에 str은 의미 없는데
			 * 밑에 isEmpty를 해줄려고 했다.
			 * 
			 * */
			
			if(PjtUtil.isEmpty(PGM_ID)) {
				throw new BizRuntimeException("프로그램ID가 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(PGM_NM)) {
				throw new BizRuntimeException("프로그램명이 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(DIR_LINK)) {
				throw new BizRuntimeException("디렉토리링크가 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(PGM_LINK)) {
				throw new BizRuntimeException("프로그램링크가 입력되지 않았습니다.");
			}
			
			daPgm.createPgm(
					PGM_ID
					,PGM_NM
					,RMK
					,CATEGORY
					,DIR_LINK
					,PGM_LINK
					);
		}
		
		for( int i=0;i<inDS.get("UPDT_DATA").size();i++) {
			HashMap<String,Object>  rs =inDS.get("UPDT_DATA").get(i);
			String  PGM_ID 		= PjtUtil.str(rs.get("PGM_ID"));
			String  PGM_NM 		= PjtUtil.str(rs.get("PGM_NM"));
			String  RMK 		= PjtUtil.str(rs.get("RMK"));
			String  DIR_LINK 	= PjtUtil.str(rs.get("DIR_LINK"));
			String  PGM_LINK 	= PjtUtil.str(rs.get("PGM_LINK"));			
			String  CATEGORY 	= PjtUtil.str(rs.get("CATEGORY"));
			/*
			 * null이어도 ""로 들어와서 처리된다.
			 * 위에 str은 의미 없는데
			 * 밑에 isEmpty를 해줄려고 했다.
			 * 
			 * */
			
			if(PjtUtil.isEmpty(PGM_ID)) {
				throw new BizRuntimeException("프로그램ID가 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(PGM_NM)) {
				throw new BizRuntimeException("프로그램명이 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(DIR_LINK)) {
				throw new BizRuntimeException("디렉토리링크가 입력되지 않았습니다.");
			}
			
			if(PjtUtil.isEmpty(PGM_LINK)) {
				throw new BizRuntimeException("프로그램링크가 입력되지 않았습니다.");
			}
			
			daPgm.updatePgm(
					PGM_ID
					,PGM_NM
					,RMK
					,CATEGORY
					,DIR_LINK
					,PGM_LINK
					);
		}

		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
	
	@OpService
	public OUT_DS rmPgm(IN_DS inDS) throws BizException {
		for( int i=0;i<inDS.get("IN_DATA").size();i++) {
			HashMap<String,Object>  rs =inDS.get("IN_DATA").get(i);
			String  PGM_ID 		= PjtUtil.str(rs.get("PGM_ID"));
			if(PjtUtil.isEmpty(PGM_ID)) {
				throw new BizRuntimeException("프로그램ID가 입력되지 않았습니다.");
			}
			
			daPgm.rmPgm(
					PGM_ID
					);
		}
	
		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
}
