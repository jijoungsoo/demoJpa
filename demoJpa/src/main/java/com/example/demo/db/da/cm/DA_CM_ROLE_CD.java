package com.example.demo.db.da.cm;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.demo.db.domain.cm.CmRoleCd;
import com.example.demo.db.domain.cm.QCmRoleCd;
import com.example.demo.db.repository.cm.CmRoleCdRepository;
import com.example.demo.exception.BizException;
import com.example.demo.utils.PjtUtil;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DA_CM_ROLE_CD {

	@Autowired
    PjtUtil pjtU;
	
	
	@Autowired
	JPAQueryFactory qf;
	
	@Autowired
	CmRoleCdRepository cmRoleR;
	
	public List<CmRoleCd> findCmRoleCd(String USE_YN) {

		JPAQuery<CmRoleCd>  c = qf.selectFrom(QCmRoleCd.cmRoleCd);
		if(USE_YN!=null && !pjtU.isEmpty(USE_YN)) {
			c.where(QCmRoleCd.cmRoleCd.useYn.eq(USE_YN));
		}
	                
		List<CmRoleCd> al =  c
					.orderBy(QCmRoleCd.cmRoleCd.roleCd.asc())
	                .fetch();
		 return al;
		 
	}
	
	public void createCmRoleCd(
			String ROLE_CD
			,String ROLE_NM
			,String USE_YN
			,String ORD
			,String RMK
			,Long L_SESSION_USER_NO
			) {

		cmRoleR.save(
				CmRoleCd.builder()
				.roleCd(ROLE_CD)
				.roleNm(ROLE_NM)
				.useYn(USE_YN)
				.ord(ORD)
				.rmk(RMK)
				.crtUsrNo(L_SESSION_USER_NO)
				.updtUsrNo(L_SESSION_USER_NO)
				.updtDtm(new Date())
				.crtDtm(new Date()).build());
	}
	

	public void updateCmRoleCd(
			 String ROLE_CD
			,String ROLE_NM
			,String USE_YN
			,String ORD
			,String RMK
			,Long L_SESSION_USER_NO
			) throws BizException {
		
		
		Optional<CmRoleCd> c= cmRoleR.findById(ROLE_CD);
		
		if(c==null) {
			throw new BizException("["+ROLE_CD+"역할코드]가 존재하지 않습니다.[수정X]");
		}

		CmRoleCd tmp = c.get();
		tmp.setRoleNm(ROLE_NM);
		tmp.setUseYn(USE_YN);
		tmp.setOrd(ORD);
		tmp.setRmk(RMK);
		tmp.setUpdtUsrNo(L_SESSION_USER_NO);
		tmp.setUpdtDtm(new Date());
		
		cmRoleR.save(tmp);
	}
	
	public void rmCmRoleCd(
			String ROLE_CD
			) {
		cmRoleR.deleteById(ROLE_CD);
	}

}
