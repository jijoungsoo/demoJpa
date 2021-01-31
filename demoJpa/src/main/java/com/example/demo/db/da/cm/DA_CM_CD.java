package com.example.demo.db.da.cm;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.db.domain.cm.CmCd;
import com.example.demo.db.domain.cm.CmCdId;
import com.example.demo.db.repository.cm.CmCdRepository;
import com.example.demo.utils.PjtUtil;
import com.example.demo.db.domain.cm.QCmCd;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Service
public class DA_CM_CD {
	
	@Autowired
	JPAQueryFactory qf;
	
	@Autowired
	CmCdRepository cmCdR;
	
	public List<CmCd> findCmCd(String GRP_CD,String USE_YN) {
		JPAQuery<CmCd> q=qf
        .selectFrom(QCmCd.cmCd)
        .where(QCmCd.cmCd.grpCd.eq(GRP_CD));
		if(!PjtUtil.isEmpty(USE_YN)) {
			q.where(QCmCd.cmCd.useYn.eq(USE_YN));
		}
		List<CmCd> al =  q.orderBy(QCmCd.cmCd.ord.asc())
	                .fetch();
		 return al;
	}
	
	public void createCmCd(
			String GRP_CD
			,String CD_NM
			,String CD
			,String USE_YN
			,String ORD
			,String RMK
			,Long L_SESSION_USER_NO
			) {
		

		cmCdR.save(
				CmCd.builder()
				.grpCd(GRP_CD)
				.cdNm(CD_NM)
				.cd(CD)
				.useYn(USE_YN)
				.ord(Integer.parseInt(ORD))
				.rmk(RMK)
				.crtUsrNo(L_SESSION_USER_NO)
				.updtUsrNo(L_SESSION_USER_NO)
				.updtDtm(new Date())
				.crtDtm(new Date()).build());
	}
	public void updateCmCd(
			String GRP_CD
			,String CD_NM
			,String CD
			,String USE_YN
			,String ORD
			,String RMK
			,Long L_SESSION_USER_NO
			) {
		

		cmCdR.save(
				CmCd.builder()
				.grpCd(GRP_CD)
				.cdNm(CD_NM)
				.cd(CD)
				.useYn(USE_YN)
				.ord(Integer.parseInt(ORD))
				.rmk(RMK)
				.updtUsrNo(L_SESSION_USER_NO)
				.updtDtm(new Date()).build());
	}
	
	public void rmCmCd(
			String GRP_CD
			,String CD
			) {
		CmCdId cmCdId = new CmCdId();
		cmCdId.setCd(CD);
		cmCdId.setGrpCd(GRP_CD);
		cmCdR.deleteById(cmCdId);
	}
}
