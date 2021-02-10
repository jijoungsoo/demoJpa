package com.example.demo.db.da.cm;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.db.domain.cm.CmUser;
import com.example.demo.db.repository.cm.CmUserRepository;
import com.example.demo.db.domain.cm.QCmUser;
import com.example.demo.exception.BizException;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class DA_CM_USER {
	
	@Autowired
	JPAQueryFactory qf;
	
	@Autowired
	CmUserRepository cmUserR;
	
	public List<CmUser> findCmUser() {
		List<CmUser> al =  qf
	                .selectFrom(QCmUser.cmUser)
	                .orderBy(QCmUser.cmUser.crtDtm.desc())
	                .fetch();
		 return al;
	}
	
	public CmUser findByUserId(String USER_ID) {
		CmUser c = cmUserR.findByUserId(USER_ID);
		return c;
	}
	
	/**
	 * @param GRP_CD  그룹코드
	 * @param GRP_NM  그룹코드명
	 * @param USE_YN  사용여부
	 * @param ORD 	  순서
	 * @param RMK	  비고
	 */
	public void createCmUser(
			long USER_NO
			,String USER_NM
			,String USER_ID
			,String USER_PWD
			,String EMAIL
			,String USE_YN
			,String RMK
			,Long L_LSESSION_USER_NO
			) {

		cmUserR.save(
				CmUser.builder()
				.userNo(USER_NO)
				.userNm(USER_NM)
				.userId(USER_ID)
				.userPwd(USER_PWD)
				.email(EMAIL)
				.useYn(USE_YN)
				.rmk(RMK)
				.lstAccDtm(new Date())
				.crtUsrNo(L_LSESSION_USER_NO)
				.updtUsrNo(L_LSESSION_USER_NO)
				.updtDtm(new Date())
				.crtDtm(new Date()).build());
	}
	
	public void updateCmUser(
			long USER_NO
			,String USER_NM
			,String USER_ID
			,String EMAIL
			,String USE_YN
			,String RMK
			,Long L_LSESSION_USER_NO
			) throws BizException {
		 Optional<CmUser> c = cmUserR.findById(USER_NO);
		 if(c==null) {
			 throw new BizException("사용자번호["+USER_NO+"] 수정할 대상이 존재하지 않습니다.");
		 }
		 CmUser tmp =c.get();
	
		tmp.setUserNm(USER_NM);
		tmp.setUserId(USER_ID);
		tmp.setEmail(EMAIL);
		tmp.setUseYn(USE_YN);
		tmp.setRmk(RMK);
		tmp.setUpdtUsrNo(L_LSESSION_USER_NO);
		tmp.setUpdtDtm(new Date());
		
		cmUserR.save(tmp);
	}
	
	public void rmCmUser(
			long USER_NO
			) {
		cmUserR.deleteById(USER_NO);
	}

	public void changeUserPwd(long l_USER_NO, String USER_PWD) {
		Optional<CmUser> c = cmUserR.findById(l_USER_NO);
		CmUser tmp= c.get();
		log.info("l_USER_NO=>"+l_USER_NO);
		log.info("USER_PWD=>"+USER_PWD);
		
		
		tmp.setUserPwd(USER_PWD);
		cmUserR.save(tmp);
		//
	}
}
