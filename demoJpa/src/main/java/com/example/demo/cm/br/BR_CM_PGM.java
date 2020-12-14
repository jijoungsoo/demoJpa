package com.example.demo.cm.br;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
	@OpService
	public OUT_DS makePgm(IN_DS inDS) throws BizException {

		return null;
		//return daPgm.makePgm();
	}
}
