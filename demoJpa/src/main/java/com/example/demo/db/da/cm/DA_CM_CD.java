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
	
	/**
	 * @param GRP_CD  그룹코드
	 * @param GRP_NM  그룹코드명
	 * @param USE_YN  사용여부
	 * @param ORD 	  순서
	 * @param RMK	  비고
	 */
	public void saveCmCd(
			String GRP_CD
			,String CD_NM
			,String CD
			,String USE_YN
			,String ORD
			,String RMK
			) {
		

		cmCdR.save(
				CmCd.builder()
				.grpCd(GRP_CD)
				.cdNm(CD_NM)
				.cd(CD)
				.useYn(USE_YN)
				.ord(Integer.parseInt(ORD))
				.rmk(RMK)
				.updtDtm(new Date())
				.crtDtm(new Date()).build());
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
