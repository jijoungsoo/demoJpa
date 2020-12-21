package com.example.demo.cm.br;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.cm.anotation.OpService;
import com.example.demo.cm.ctrl.IN_DS;
import com.example.demo.cm.ctrl.OUT_DS;
import com.example.demo.cm.da.DA_CM_GRP_CD;
import com.example.demo.cm.utils.PjtUtil;
import com.example.demo.domain.CmGrpCd;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;

@Service
public class BR_CM_GRP_CD {
	@Autowired
	DA_CM_GRP_CD daGrpCd;

	@OpService
	public OUT_DS  findCmGrpCd(IN_DS inDS) throws BizException {
		List<CmGrpCd>  al =daGrpCd.findCmGrpCd();
		ArrayList<HashMap<String, Object>> OUT_DATA = new ArrayList<HashMap<String,Object>>();
		for(int i=0;i<al.size();i++) {
			CmGrpCd cm=al.get(i);
			HashMap<String, Object>  OUT_DATA_ROW = new HashMap<String, Object>();
			OUT_DATA_ROW.put("GRP_CD", cm.getGrpCd());
			OUT_DATA_ROW.put("GRP_NM", cm.getGrpNm());
			OUT_DATA_ROW.put("USE_YN", cm.getUseYn());
			OUT_DATA_ROW.put("ORD", cm.getOrd());
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
	public OUT_DS saveCmGrpCd(IN_DS inDS) throws BizException {
		for( int i=0;i<inDS.get("IN_DATA").size();i++) {
			HashMap<String,Object>  rs =inDS.get("IN_DATA").get(i);
			String  GRP_CD 		= PjtUtil.str(rs.get("GRP_CD"));
			String  GRP_NM 		= PjtUtil.str(rs.get("GRP_NM"));
			String  USE_YN 		= PjtUtil.str(rs.get("USE_YN"));
			String  ORD 		= PjtUtil.str(rs.get("ORD"));
			String  RMK 		= PjtUtil.str(rs.get("RMK"));
			
			if(PjtUtil.isEmpty(GRP_CD)) {
				throw new BizRuntimeException("공통코드그룹코드가 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(GRP_NM)) {
				throw new BizRuntimeException("공통코드그룹명이 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(USE_YN)) {
				throw new BizRuntimeException("사용여부가 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(ORD)) {
				throw new BizRuntimeException("정렬이 입력되지 않았습니다.");
			}
			
			
			daGrpCd.saveCmGrpCd(
					GRP_CD
					,GRP_NM
					,USE_YN
					,ORD
					,RMK
					);
		}
		
		for( int i=0;i<inDS.get("UPDT_DATA").size();i++) {
			HashMap<String,Object>  rs =inDS.get("UPDT_DATA").get(i);
			String  GRP_CD 		= PjtUtil.str(rs.get("GRP_CD"));
			String  GRP_NM 		= PjtUtil.str(rs.get("GRP_NM"));
			String  USE_YN 		= PjtUtil.str(rs.get("USE_YN"));
			String  ORD 		= PjtUtil.str(rs.get("ORD"));
			String  RMK 		= PjtUtil.str(rs.get("RMK"));
			
			if(PjtUtil.isEmpty(GRP_CD)) {
				throw new BizRuntimeException("공통코드그룹코드가 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(GRP_NM)) {
				throw new BizRuntimeException("공통코드그룹명이 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(USE_YN)) {
				throw new BizRuntimeException("사용여부가 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(ORD)) {
				throw new BizRuntimeException("정렬이 입력되지 않았습니다.");
			}
			
			
			daGrpCd.saveCmGrpCd(
					GRP_CD
					,GRP_NM
					,USE_YN
					,ORD
					,RMK
					);
		}

		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
	
	@OpService
	public OUT_DS rmCmGrpCd(IN_DS inDS) throws BizException {
		for( int i=0;i<inDS.get("IN_DATA").size();i++) {
			HashMap<String,Object>  rs =inDS.get("IN_DATA").get(i);
			String  GRP_CD 		= PjtUtil.str(rs.get("GRP_CD"));
			if(PjtUtil.isEmpty(GRP_CD)) {
				throw new BizRuntimeException("그룹코드가 입력되지 않았습니다.");
			}
			
			daGrpCd.rmCmGrpCd(
					GRP_CD
					);
		}
	
		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
}
