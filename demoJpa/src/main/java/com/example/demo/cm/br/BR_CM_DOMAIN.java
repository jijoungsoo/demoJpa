package com.example.demo.cm.br;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.cm.anotation.OpService;
import com.example.demo.cm.ctrl.IN_DS;
import com.example.demo.cm.ctrl.OUT_DS;
import com.example.demo.cm.da.DA_CM_DOMAIN;
import com.example.demo.cm.utils.PjtUtil;
import com.example.demo.domain.CmDomain;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;

@Service
public class BR_CM_DOMAIN {
	@Autowired
	DA_CM_DOMAIN daDmn;

	@OpService
	public OUT_DS findDomain(IN_DS inDS) throws BizException {
		List<CmDomain>  al =daDmn.findDomain();
		ArrayList<HashMap<String, Object>> OUT_DATA = new ArrayList<HashMap<String,Object>>();
		for(int i=0;i<al.size();i++) {
			CmDomain cm=al.get(i);
			HashMap<String, Object>  OUT_DATA_ROW = new HashMap<String, Object>();
			OUT_DATA_ROW.put("DMN_CD", cm.getDmnCd());
			OUT_DATA_ROW.put("DMN_NM", cm.getDmnNm());
			OUT_DATA_ROW.put("DATA_TYPE", cm.getDataType());
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
	public OUT_DS saveDomain(IN_DS inDS) throws BizException {
		for( int i=0;i<inDS.get("IN_DATA").size();i++) {
			HashMap<String,Object>  rs =inDS.get("IN_DATA").get(i);
			String  DMN_CD 		= PjtUtil.str(rs.get("DMN_CD"));
			String  DMN_NM 		= PjtUtil.str(rs.get("DMN_NM"));
			String  DATA_TYPE 	= PjtUtil.str(rs.get("DATA_TYPE"));
			String  RMK 	= PjtUtil.str(rs.get("RMK"));
			
			if(PjtUtil.isEmpty(DMN_CD)) {
				throw new BizRuntimeException("도메인코드 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(DMN_NM)) {
				throw new BizRuntimeException("도메인명 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(DATA_TYPE)) {
				throw new BizRuntimeException("데이터타입 입력되지 않았습니다.");
			}
			
			daDmn.saveDomain(
					DMN_CD
					,DMN_NM
					,DATA_TYPE
					,RMK
					);
		}
		
		for( int i=0;i<inDS.get("UPDT_DATA").size();i++) {
			HashMap<String,Object>  rs =inDS.get("IN_DATA").get(i);
			String  DMN_CD 		= PjtUtil.str(rs.get("DMN_CD"));
			String  DMN_NM 		= PjtUtil.str(rs.get("DMN_NM"));
			String  DATA_TYPE 	= PjtUtil.str(rs.get("DATA_TYPE"));
			String  RMK 	= PjtUtil.str(rs.get("RMK"));
			
			if(PjtUtil.isEmpty(DMN_CD)) {
				throw new BizRuntimeException("도메인코드 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(DMN_NM)) {
				throw new BizRuntimeException("도메인명 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(DATA_TYPE)) {
				throw new BizRuntimeException("데이터타입 입력되지 않았습니다.");
			}
			
			daDmn.saveDomain(
					DMN_CD
					,DMN_NM
					,DATA_TYPE
					,RMK
					);
		}

		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
	
	@OpService
	public OUT_DS rmDomain(IN_DS inDS) throws BizException {
		for( int i=0;i<inDS.get("IN_DATA").size();i++) {
			HashMap<String,Object>  rs =inDS.get("IN_DATA").get(i);
			String  DMN_CD 		= PjtUtil.str(rs.get("DMN_CD"));
			if(PjtUtil.isEmpty(DMN_CD)) {
				throw new BizRuntimeException("도메인코드 입력되지 않았습니다.");
			}
			
			daDmn.rmDomain(
					DMN_CD
					);
		}
	
		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
}
