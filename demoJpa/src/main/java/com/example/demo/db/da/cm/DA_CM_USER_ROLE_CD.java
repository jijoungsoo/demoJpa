package com.example.demo.db.da.cm;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.demo.db.domain.cm.CmUserNoRoleCd;
import com.example.demo.db.domain.cm.CmUserRoleCd;
import com.example.demo.db.domain.cm.QCmUserRoleCd;
import com.example.demo.db.repository.cm.CmUserRoleCdRepository;
import com.example.demo.exception.BizException;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DA_CM_USER_ROLE_CD {
	
	@Autowired
	JPAQueryFactory qf;
	
	@Autowired
	CmUserRoleCdRepository cmUserRoleR;
	
	public List<Tuple>  findCmUserRoleCd(String ROLE_CD) {
		List<Tuple>  al =  qf
		.select(QCmUserRoleCd.cmUserRoleCd.refCmUser.userNm,
		QCmUserRoleCd.cmUserRoleCd.roleCd,
		QCmUserRoleCd.cmUserRoleCd.userNo,
		QCmUserRoleCd.cmUserRoleCd.refCmUser.userId,
		QCmUserRoleCd.cmUserRoleCd.refCmUser.userNm,
		QCmUserRoleCd.cmUserRoleCd.useYn,
		QCmUserRoleCd.cmUserRoleCd.ord,
		QCmUserRoleCd.cmUserRoleCd.rmk,			
		QCmUserRoleCd.cmUserRoleCd.crtDtm,
		QCmUserRoleCd.cmUserRoleCd.updtDtm,
		QCmUserRoleCd.cmUserRoleCd.updtUsrNo,
		QCmUserRoleCd.cmUserRoleCd.crtUsrNo
		)
		.from(QCmUserRoleCd.cmUserRoleCd)
					.where(QCmUserRoleCd.cmUserRoleCd.roleCd.eq(ROLE_CD))
					.orderBy(QCmUserRoleCd.cmUserRoleCd.userNo.asc())
					.fetch();
			return al;
	}
	
	
	public void createCmUserRoleCd(
			String ROLE_CD
			,Long USER_NO
			,String USE_YN
			,String ORD
			,String RMK
			,Long L_SESSION_USER_NO
			) {

			cmUserRoleR.save(
				CmUserRoleCd.builder()
				.roleCd(ROLE_CD)
				.userNo(USER_NO)
				.useYn(USE_YN)
				.ord(ORD)
				.rmk(RMK)
				.crtUsrNo(L_SESSION_USER_NO)
				.updtUsrNo(L_SESSION_USER_NO)
				.updtDtm(new Date())
				.crtDtm(new Date()).build());
	}
	

	public void updateCmUserRoleCd(
			 String ROLE_CD
			,Long USER_NO
			,String USE_YN
			,String ORD
			,String RMK
			,Long L_SESSION_USER_NO
			) throws BizException {
		
		
		CmUserNoRoleCd key = new CmUserNoRoleCd();
		key.setRoleCd(ROLE_CD);
		key.setUserNo(USER_NO);
		Optional<CmUserRoleCd> c= cmUserRoleR.findById(key);
		
		if(c==null) {
			throw new BizException("["+ROLE_CD+"-"+USER_NO+"맴핑]이 존재하지 않습니다.[수정X]");
		}

		CmUserRoleCd tmp = c.get();
		tmp.setUseYn(USE_YN);
		tmp.setOrd(ORD);
		tmp.setRmk(RMK);
		tmp.setUpdtUsrNo(L_SESSION_USER_NO);
		tmp.setUpdtDtm(new Date());
		
		cmUserRoleR.save(tmp);
	}
	
	public void rmCmRoleCd(
			String ROLE_CD
			,Long USER_NO
			) {
		CmUserNoRoleCd key = new CmUserNoRoleCd();
		key.setRoleCd(ROLE_CD);
		key.setUserNo(USER_NO);
		cmUserRoleR.deleteById(key);
	}
}
