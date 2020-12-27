package com.example.demo.br.cm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.IN_DS;
import com.example.demo.ctrl.OUT_DS;
import com.example.demo.db.da.cm.DA_CM_CD;
import com.example.demo.db.domain.cm.CmCd;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
import com.example.demo.utils.PjtUtil;

@Service
public class BR_CM_CD {
	@Autowired
	DA_CM_CD daCmCd;

	@OpService
	public OUT_DS  findCmCd(IN_DS inDS) throws BizException {
		if(inDS.get("IN_DATA").size()==0) {
			throw new BizRuntimeException("파라미터가 존재하지 않습니다.");
		}
		HashMap<String,Object>  rs =inDS.get("IN_DATA").get(0);
		String  GRP_CD 		= PjtUtil.str(rs.get("GRP_CD"));
		String  USE_YN 		= PjtUtil.str(rs.get("USE_YN"));
		
		List<CmCd>  al =daCmCd.findCmCd(GRP_CD,USE_YN);
		ArrayList<HashMap<String, Object>> OUT_DATA = new ArrayList<HashMap<String,Object>>();
		for(int i=0;i<al.size();i++) {
			CmCd cm=al.get(i);
			HashMap<String, Object>  OUT_DATA_ROW = new HashMap<String, Object>();
			OUT_DATA_ROW.put("GRP_CD", cm.getGrpCd());
			OUT_DATA_ROW.put("CD", cm.getCd());
			OUT_DATA_ROW.put("CD_NM", cm.getCdNm());
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
	public OUT_DS saveCmCd(IN_DS inDS) throws BizException {
		for( int i=0;i<inDS.get("IN_DATA").size();i++) {
			HashMap<String,Object>  rs =inDS.get("IN_DATA").get(i);
			String  GRP_CD 		= PjtUtil.str(rs.get("GRP_CD"));
			String  CD	 		= PjtUtil.str(rs.get("CD"));
			String  CD_NM 		= PjtUtil.str(rs.get("CD_NM"));
			String  USE_YN 		= PjtUtil.str(rs.get("USE_YN"));
			String  ORD 		= PjtUtil.str(rs.get("ORD"));
			String  RMK 		= PjtUtil.str(rs.get("RMK"));
			
			if(PjtUtil.isEmpty(GRP_CD)) {
				throw new BizRuntimeException("공통코드그룹코드가 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(CD)) {
				throw new BizRuntimeException("공통코드가 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(CD_NM)) {
				throw new BizRuntimeException("공통코드명이 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(USE_YN)) {
				throw new BizRuntimeException("사용여부가 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(ORD)) {
				throw new BizRuntimeException("정렬이 입력되지 않았습니다.");
			}
			
			
			daCmCd.saveCmCd(
					GRP_CD
					,CD_NM
					,CD
					,USE_YN
					,ORD
					,RMK
					);
		}
		
		for( int i=0;i<inDS.get("UPDT_DATA").size();i++) {
			HashMap<String,Object>  rs =inDS.get("UPDT_DATA").get(i);
			String  GRP_CD 		= PjtUtil.str(rs.get("GRP_CD"));
			String  CD 		= PjtUtil.str(rs.get("CD"));
			String  CD_NM 		= PjtUtil.str(rs.get("CD_NM"));
			String  USE_YN 		= PjtUtil.str(rs.get("USE_YN"));
			String  ORD 		= PjtUtil.str(rs.get("ORD"));
			String  RMK 		= PjtUtil.str(rs.get("RMK"));
			
			if(PjtUtil.isEmpty(GRP_CD)) {
				throw new BizRuntimeException("공통코드그룹코드가 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(CD)) {
				throw new BizRuntimeException("공통코드가 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(CD_NM)) {
				throw new BizRuntimeException("공통코드명이 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(USE_YN)) {
				throw new BizRuntimeException("사용여부가 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(ORD)) {
				throw new BizRuntimeException("정렬이 입력되지 않았습니다.");
			}
			
			
			daCmCd.saveCmCd(
					GRP_CD
					,CD_NM
					,CD
					,USE_YN
					,ORD
					,RMK
					);
		}

		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
	
	@OpService
	public OUT_DS rmCmCd(IN_DS inDS) throws BizException {
		for( int i=0;i<inDS.get("IN_DATA").size();i++) {
			HashMap<String,Object>  rs =inDS.get("IN_DATA").get(i);
			String  GRP_CD 		= PjtUtil.str(rs.get("GRP_CD"));
			String  CD 		= PjtUtil.str(rs.get("CD"));
			if(PjtUtil.isEmpty(GRP_CD)) {
				throw new BizRuntimeException("공통그룹코드가 입력되지 않았습니다.");
			}
			
			if(PjtUtil.isEmpty(CD)) {
				throw new BizRuntimeException("공통코드가 입력되지 않았습니다.");
			}
			
			daCmCd.rmCmCd(
					GRP_CD
					,CD
					
					);
		}
	
		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
}
