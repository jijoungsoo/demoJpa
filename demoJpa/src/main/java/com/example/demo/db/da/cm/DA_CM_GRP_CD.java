package com.example.demo.db.da.cm;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.db.domain.cm.CmGrpCd;
import com.example.demo.db.repository.cm.CmGrpCdRepository;
import com.example.demo.db.domain.cm.QCmGrpCd;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Service
public class DA_CM_GRP_CD {
	
	@Autowired
	JPAQueryFactory qf;
	
	@Autowired
	CmGrpCdRepository cmGrpCdR;
	
	public List<CmGrpCd> findCmGrpCd() {
		List<CmGrpCd> al =  qf
	                .selectFrom(QCmGrpCd.cmGrpCd)
	                .orderBy(QCmGrpCd.cmGrpCd.ord.asc())
	                .fetch();
		 return al;
	}
	
	public void createCmGrpCd(
			String GRP_CD
			,String GRP_NM
			,String USE_YN
			,String ORD
			,String RMK
			,Long L_SESSION_USER_NO
			) {

		cmGrpCdR.save(
				CmGrpCd.builder()
				.grpCd(GRP_CD)
				.grpNm(GRP_NM)
				.useYn(USE_YN)
				.ord(Integer.parseInt(ORD))
				.rmk(RMK)
				.crtUsrNo(L_SESSION_USER_NO)
				.updtUsrNo(L_SESSION_USER_NO)
				.updtDtm(new Date())
				.crtDtm(new Date()).build());
	}
	

	public void updateCmGrpCd(
			String GRP_CD
			,String GRP_NM
			,String USE_YN
			,String ORD
			,String RMK
			,Long L_SESSION_USER_NO
			) {

		cmGrpCdR.save(
				CmGrpCd.builder()
				.grpCd(GRP_CD)
				.grpNm(GRP_NM)
				.useYn(USE_YN)
				.ord(Integer.parseInt(ORD))
				.rmk(RMK)
				.updtUsrNo(L_SESSION_USER_NO)
				.updtDtm(new Date()).build());
	}
	
	public void rmCmGrpCd(
			String GRP_CD
			) {
		cmGrpCdR.deleteById(GRP_CD);
	}
}
