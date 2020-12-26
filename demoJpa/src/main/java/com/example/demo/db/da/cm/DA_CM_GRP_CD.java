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
	
	/**
	 * @param GRP_CD  그룹코드
	 * @param GRP_NM  그룹코드명
	 * @param USE_YN  사용여부
	 * @param ORD 	  순서
	 * @param RMK	  비고
	 */
	public void saveCmGrpCd(
			String GRP_CD
			,String GRP_NM
			,String USE_YN
			,String ORD
			,String RMK
			) {

		cmGrpCdR.save(
				CmGrpCd.builder()
				.grpCd(GRP_CD)
				.grpNm(GRP_NM)
				.useYn(USE_YN)
				.ord(Integer.parseInt(ORD))
				.rmk(RMK)
				.updtDtm(new Date())
				.crtDtm(new Date()).build());
	}
	
	public void rmCmGrpCd(
			String GRP_CD
			) {
		cmGrpCdR.deleteById(GRP_CD);
	}
}
