package com.example.demo.br;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.da.DA_LOGIN;
import com.example.demo.da.service.CmUserRepository;
import com.example.demo.domain.CmUser;
import com.example.demo.domain.QCmUser;
import com.example.demo.exception.BizException;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Service
public class BR_LOGIN {
	@Autowired
	private DA_LOGIN daLogin;

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
	public CmUser loadUserByusername(String userId) throws BizException {
		CmUser c =daLogin.findByUserId(userId);
		return c;
		
	}
	
	
}
