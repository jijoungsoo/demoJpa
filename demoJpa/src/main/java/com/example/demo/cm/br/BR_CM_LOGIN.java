package com.example.demo.cm.br;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.cm.anotation.OpService;
import com.example.demo.cm.ctrl.IN_DS;
import com.example.demo.cm.ctrl.OUT_DS;
import com.example.demo.cm.da.DA_CM_LOGIN;
import com.example.demo.domain.CmUser;
import com.example.demo.exception.BizException;

@Service
public class BR_CM_LOGIN {
	@Autowired
	private DA_CM_LOGIN daLogin;

	/*
	public CmUser Login(String userId,String userPwd) throws BizException {
		CmUser c =daLogin.findByUserId(userId);
		if(c==null) {
			throw new BizException("사용자ID가 유효하지 않습니다.");
		}
		BCryptPasswordEncoder passwordEncoding = new BCryptPasswordEncoder();
		if(!passwordEncoding.matches(userPwd, c.getUserPwd())) {   //매치함수를 써서 확인한다.
			throw new BizException("사용자암호가 유효하지 않습니다.");
		}
		//로그인 처리는 demoWeb에서하고  여기서는 로그인 처리를 한다. 접속일자 갱신
		c.setLstAccDtm(new Date()); //이렇게만 해도 시간이 없데이트 된다. 좋다.
		
		return c;		
	}
	*/
	


	
	/*spring security 기본 구현 관련 */
	@OpService
	public OUT_DS loadUserByusername(IN_DS inDs) throws BizException {
		System.out.println(inDs);
		
		
		
		if(inDs.get("IN_DATA").size()!=1) {
			throw new BizException("입력파라미터가 잘못되었습니다.");
		}
		HashMap<String,Object>  rs =inDs.get("IN_DATA").get(0);
		
		if(rs.get("USER_ID")==null) {
			throw new BizException("사용자ID가 입력되지 않았습니다.");
		}
		CmUser c =daLogin.findByUserId((String) rs.get("USER_ID"));
		
		OUT_DS outDs = new OUT_DS();
		ArrayList<HashMap<String, Object>> OUT_DATA = new ArrayList<HashMap<String,Object>>();
		outDs.put("OUT_DATA",OUT_DATA);
		if(c!=null) {
			HashMap<String, Object> OUT_DATA_ROW = new HashMap<String,Object>();
			OUT_DATA_ROW.put("userId", c.getUserId());
			OUT_DATA_ROW.put("userPwd", c.getUserPwd());
			OUT_DATA_ROW.put("userNm", c.getUserNm());
			OUT_DATA_ROW.put("email", c.getEmail());
			OUT_DATA.add(OUT_DATA_ROW);
		}
		return outDs;
		
	}
	
	
}
