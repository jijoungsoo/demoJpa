package com.example.demo.br.cm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.IN_DS;
import com.example.demo.ctrl.OUT_DS;
import com.example.demo.db.da.cm.DA_CM_SEQ;
import com.example.demo.db.domain.cm.CmSeq;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
import com.example.demo.utils.PjtUtil;

@Service
public class BR_CM_SEQ {
	@Autowired
	DA_CM_SEQ daCmSeq;

	@OpService
	public OUT_DS findCmSeq(IN_DS inDS) throws BizException {
		List<CmSeq> al = daCmSeq.findCmSeq();
		ArrayList<HashMap<String, Object>> OUT_DATA = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < al.size(); i++) {
			CmSeq cm = al.get(i);
			HashMap<String, Object> OUT_DATA_ROW = new HashMap<String, Object>();
			OUT_DATA_ROW.put("SEQ_NM", cm.getSeqNm());
			OUT_DATA_ROW.put("SEQ_NO", cm.getSeqNo());
			OUT_DATA_ROW.put("TB_NM", cm.getTbNm());
			OUT_DATA_ROW.put("COL_NM", cm.getColNm());
			OUT_DATA_ROW.put("INIT_VAL", cm.getInitVal());
			OUT_DATA_ROW.put("ALLOCATION_SIZE", cm.getAllocationSize());
			OUT_DATA_ROW.put("CRT_DTM", PjtUtil.getYyyyMMddHHMMSS(cm.getCrtDtm()));
			OUT_DATA_ROW.put("UPDT_DTM", PjtUtil.getYyyyMMddHHMMSS(cm.getUpdtDtm()));
			OUT_DATA.add(OUT_DATA_ROW);
		}
		OUT_DS outDs = new OUT_DS();
		outDs.put("OUT_DATA", OUT_DATA);
		return outDs;
	}

	@OpService
	public OUT_DS saveCmSeq(IN_DS inDS) throws BizException {
		for (int i = 0; i < inDS.get("IN_DATA").size(); i++) {
			HashMap<String, Object> rs = inDS.get("IN_DATA").get(i);
			String SEQ_NM = PjtUtil.str(rs.get("SEQ_NM"));
			String SEQ_NO = PjtUtil.str(rs.get("SEQ_NO"));
			String TB_NM = PjtUtil.str(rs.get("TB_NM"));
			String COL_NM = PjtUtil.str(rs.get("COL_NM"));
			String INIT_VAL = PjtUtil.str(rs.get("INIT_VAL"));
			String ALLOCATION_SIZE = PjtUtil.str(rs.get("ALLOCATION_SIZE"));

			if (PjtUtil.isEmpty(SEQ_NM)) {
				throw new BizRuntimeException("시퀀스명이 입력되지 않았습니다.");
			}
			if (PjtUtil.isEmpty(SEQ_NO)) {
				throw new BizRuntimeException("시퀀스번호가 입력되지 않았습니다.");
			}
			if (PjtUtil.isEmpty(TB_NM)) {
				throw new BizRuntimeException("테이블명이 입력되지 않았습니다.");
			}
			if (PjtUtil.isEmpty(COL_NM)) {
				throw new BizRuntimeException("컬럼명이 입력되지 않았습니다.");
			}
			if (PjtUtil.isEmpty(INIT_VAL)) {
				throw new BizRuntimeException("초기값이 입력되지 않았습니다.");
			}
			if (PjtUtil.isEmpty(ALLOCATION_SIZE)) {
				throw new BizRuntimeException("증가값이 입력되지 않았습니다.");
			}

			long L_SEQ_NO = Long.parseLong(SEQ_NO);
			Integer I_INIT_VAL = Integer.parseInt(INIT_VAL);
			Integer I_ALLOCATION_SIZE = Integer.parseInt(ALLOCATION_SIZE);
			
			daCmSeq.saveCmSeq(SEQ_NM, L_SEQ_NO, TB_NM, COL_NM, I_INIT_VAL, I_ALLOCATION_SIZE);
		}

		for (int i = 0; i < inDS.get("UPDT_DATA").size(); i++) {
			HashMap<String, Object> rs = inDS.get("UPDT_DATA").get(i);
			String SEQ_NM = PjtUtil.str(rs.get("SEQ_NM"));
			String SEQ_NO = PjtUtil.str(rs.get("SEQ_NO"));
			String TB_NM = PjtUtil.str(rs.get("TB_NM"));
			String COL_NM = PjtUtil.str(rs.get("COL_NM"));
			String INIT_VAL = PjtUtil.str(rs.get("INIT_VAL"));
			String ALLOCATION_SIZE = PjtUtil.str(rs.get("ALLOCATION_SIZE"));
			
			if (PjtUtil.isEmpty(SEQ_NM)) {
				throw new BizRuntimeException("시퀀스명이 입력되지 않았습니다.");
			}
			if (PjtUtil.isEmpty(SEQ_NO)) {
				throw new BizRuntimeException("시퀀스번호가 입력되지 않았습니다.");
			}
			if (PjtUtil.isEmpty(TB_NM)) {
				throw new BizRuntimeException("테이블명이 입력되지 않았습니다.");
			}
			if (PjtUtil.isEmpty(COL_NM)) {
				throw new BizRuntimeException("컬럼명이 입력되지 않았습니다.");
			}
			if (PjtUtil.isEmpty(INIT_VAL)) {
				throw new BizRuntimeException("초기값이 입력되지 않았습니다.");
			}
			if (PjtUtil.isEmpty(ALLOCATION_SIZE)) {
				throw new BizRuntimeException("증가값이 입력되지 않았습니다.");
			}
			
			long L_SEQ_NO = Long.parseLong(SEQ_NO);
			Integer I_INIT_VAL = Integer.parseInt(INIT_VAL);
			Integer I_ALLOCATION_SIZE = Integer.parseInt(ALLOCATION_SIZE);
			
			daCmSeq.saveCmSeq(SEQ_NM, L_SEQ_NO, TB_NM, COL_NM, I_INIT_VAL, I_ALLOCATION_SIZE);
		}
		OUT_DS outDs = new OUT_DS();
		return outDs;
	}

	@OpService
	public OUT_DS rmCmSeq(IN_DS inDS) throws BizException {
		for (int i = 0; i < inDS.get("IN_DATA").size(); i++) {
			HashMap<String, Object> rs = inDS.get("IN_DATA").get(i);
			String SEQ_NM = PjtUtil.str(rs.get("SEQ_NM"));
			if (PjtUtil.isEmpty(SEQ_NM)) {
				throw new BizRuntimeException("시퀀스명이 선택되지 않았습니다.");
			}
			daCmSeq.rmCmSeq(SEQ_NM);
		}

		OUT_DS outDs = new OUT_DS();
		return outDs;
	}
}
