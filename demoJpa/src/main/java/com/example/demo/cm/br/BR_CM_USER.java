package com.example.demo.cm.br;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.cm.anotation.OpService;
import com.example.demo.cm.ctrl.IN_DS;
import com.example.demo.cm.ctrl.OUT_DS;
import com.example.demo.cm.utils.PjtUtil;
import com.example.demo.db.da.cm.DA_CM_SEQ;
import com.example.demo.db.da.cm.DA_CM_USER;
import com.example.demo.db.domain.cm.CmUser;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class BR_CM_USER {
	@Autowired
	DA_CM_USER daCmUser;
	
	@Autowired
	DA_CM_SEQ daCmSeq;

	@OpService
	public OUT_DS  findCmUser(IN_DS inDS) throws BizException {
		List<CmUser>  al =daCmUser.findCmUser();
		ArrayList<HashMap<String, Object>> OUT_DATA = new ArrayList<HashMap<String,Object>>();
		for(int i=0;i<al.size();i++) {
			CmUser cm=al.get(i);
			HashMap<String, Object>  OUT_DATA_ROW = new HashMap<String, Object>();
			OUT_DATA_ROW.put("USER_NO", cm.getUserNo());
			OUT_DATA_ROW.put("USER_NM", cm.getUserNm());
			OUT_DATA_ROW.put("USER_ID", cm.getUserId());
			OUT_DATA_ROW.put("USER_PWD", cm.getUserPwd());
			OUT_DATA_ROW.put("EMAIL", cm.getEmail());
			OUT_DATA_ROW.put("USE_YN", cm.getUseYn());
			OUT_DATA_ROW.put("RMK", cm.getRmk());			
			OUT_DATA_ROW.put("LST_ACC_DTM", PjtUtil.getYyyy_MM_dd_HHMMSS(cm.getLstAccDtm()));
			OUT_DATA_ROW.put("CRT_DTM",PjtUtil.getYyyy_MM_dd_HHMMSS(cm.getCrtDtm()));
			OUT_DATA_ROW.put("UPDT_DTM",PjtUtil.getYyyy_MM_dd_HHMMSS(cm.getUpdtDtm()));
			OUT_DATA.add(OUT_DATA_ROW);
		}
		OUT_DS outDs = new OUT_DS();
		outDs.put("OUT_DATA", OUT_DATA);
		return outDs;
	}
	@OpService
	public OUT_DS saveCmUser(IN_DS inDS) throws BizException {
		for( int i=0;i<inDS.get("IN_DATA").size();i++) {
			HashMap<String,Object>  rs =inDS.get("IN_DATA").get(i);
			String  USER_NO 		= PjtUtil.str(rs.get("USER_NO"));
			String  USER_NM 		= PjtUtil.str(rs.get("USER_NM"));
			String  USER_ID		 	= PjtUtil.str(rs.get("USER_ID"));
			String  USER_PWD 		= PjtUtil.str(rs.get("USER_PWD"));
			String  EMAIL 			= PjtUtil.str(rs.get("EMAIL"));
			String  USE_YN 			= PjtUtil.str(rs.get("USE_YN"));
			String  RMK 			= PjtUtil.str(rs.get("RMK"));
			
			if(USER_NO.equals("")) {
				if(PjtUtil.isEmpty(USER_NM)) {
					throw new BizRuntimeException("사용자명이 입력되지 않았습니다.");
				}
				if(PjtUtil.isEmpty(USER_ID)) {
					throw new BizRuntimeException("사용자ID가 입력되지 않았습니다.");
				}
				if(PjtUtil.isEmpty(USER_PWD)) {
					throw new BizRuntimeException("패스워드가 입력되지 않았습니다.");
				}
				if(PjtUtil.isEmpty(EMAIL)) {
					throw new BizRuntimeException("이메일주소가 입력되지 않았습니다.");
				}
				if(PjtUtil.isEmpty(USE_YN)) {
					throw new BizRuntimeException("사용여부가 입력되지 않았습니다.");
				}
				if(PjtUtil.isEmpty(RMK)) {
					throw new BizRuntimeException("비고가 입력되지 않았습니다.");
				}
				
				long L_USER_NO =daCmSeq.increate("CM_USER_USER_NO_SEQ");
				//id중복검사
				CmUser tmp =daCmUser.findByUserId(USER_ID);
				if(tmp!=null) {
					throw new BizException("사용자ID가 중복됩니다.");
				}
				BCryptPasswordEncoder passwordEncoding = new BCryptPasswordEncoder();
				String S_USER_PWD =passwordEncoding.encode(USER_PWD);
				
			
				daCmUser.createCmUser(
						L_USER_NO
						,USER_NM
						,USER_ID
						,S_USER_PWD
						,EMAIL
						,USE_YN
						,RMK
						);
			} else {
				if(PjtUtil.isEmpty(USER_NO)) {
					throw new BizRuntimeException("사용자번호가 입력되지 않았습니다.");
				}			
				if(PjtUtil.isEmpty(USER_NM)) {
					throw new BizRuntimeException("사용자명이 입력되지 않았습니다.");
				}
				if(PjtUtil.isEmpty(USER_ID)) {
					throw new BizRuntimeException("사용자ID가 입력되지 않았습니다.");
				}

				if(PjtUtil.isEmpty(EMAIL)) {
					throw new BizRuntimeException("이메일주소가 입력되지 않았습니다.");
				}
				if(PjtUtil.isEmpty(USE_YN)) {
					throw new BizRuntimeException("사용여부가 입력되지 않았습니다.");
				}
				if(PjtUtil.isEmpty(RMK)) {
					throw new BizRuntimeException("비고가 입력되지 않았습니다.");
				}
				
				long L_USER_NO = Long.parseLong(USER_NO);
				daCmUser.updateCmUser(
						L_USER_NO
						,USER_NM
						,USER_ID
						,EMAIL
						,USE_YN
						,RMK
						);
			}
		}
		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
	
	@OpService
	public OUT_DS rmCmUser(IN_DS inDS) throws BizException {
		for( int i=0;i<inDS.get("IN_DATA").size();i++) {
			HashMap<String,Object>  rs =inDS.get("IN_DATA").get(i);
			String  USER_NO 		= PjtUtil.str(rs.get("USER_NO"));
			if(PjtUtil.isEmpty(USER_NO)) {
				throw new BizRuntimeException("사용자가 선택되지 않았습니다.");
			}
			long L_USER_NO = Long.parseLong(USER_NO);
			daCmUser.rmCmUser(
					L_USER_NO
					);
		}
	
		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
	
	
	@OpService
	public OUT_DS changeUserPwd(IN_DS inDS) throws BizException {
		for( int i=0;i<inDS.get("IN_DATA").size();i++) {
			HashMap<String,Object>  rs =inDS.get("IN_DATA").get(i);
			String  USER_NO 		= PjtUtil.str(rs.get("USER_NO"));
			String  USER_PWD 		= PjtUtil.str(rs.get("USER_PWD"));
			String  RE_USER_PWD 	= PjtUtil.str(rs.get("RE_USER_PWD"));
			if(PjtUtil.isEmpty(USER_NO)) {
				throw new BizRuntimeException("사용자가 선택되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(USER_PWD)) {
				throw new BizRuntimeException("비밀번호가 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(RE_USER_PWD)) {
				throw new BizRuntimeException("비밀번호확인이 입력되지 않았습니다.");
			}
			if(USER_PWD.equals(RE_USER_PWD)==false) {
				throw new BizRuntimeException("비밀번호와 비밀번호확인이 입력값이 다릅니다.");
			}
			
			BCryptPasswordEncoder passwordEncoding = new BCryptPasswordEncoder();
			String S_USER_PWD =passwordEncoding.encode(USER_PWD);
			
			
			long L_USER_NO = Long.parseLong(USER_NO);
			daCmUser.changeUserPwd(
					L_USER_NO
					,S_USER_PWD
					);
		}
	
		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
}
