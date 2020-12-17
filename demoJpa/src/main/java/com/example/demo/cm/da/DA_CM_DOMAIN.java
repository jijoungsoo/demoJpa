package com.example.demo.cm.da;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.da.service.CmDomainRepository;
import com.example.demo.da.service.CmPgmRepository;
import com.example.demo.domain.CmDomain;
import com.example.demo.domain.QCmDomain;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Service
public class DA_CM_DOMAIN {
	
	@Autowired
	JPAQueryFactory qf;
	
	@Autowired
	CmDomainRepository cmDomainR;
	
	public List<CmDomain> findDomain() {
		List<CmDomain> al =  qf
	                .selectFrom(QCmDomain.cmDomain)
	                .orderBy(QCmDomain.cmDomain.dmnCd.asc())
	                .fetch();
		 return al;
	}
	
	/**
	 * @param DMN_CD  도메인코드
	 * @param PGM_NM  프로그램명
	 * @param RMK      비고
	 * @param CATEGORY  카테고리
	 * @param PGM_LINK  프로그램링크
	 */
	public void saveDomain(
			String DMN_CD
			,String DMN_NM
			,String DATA_TYPE
			,String RMK
			) {
		
		cmDomainR.save(
				CmDomain.builder()
				.dmnCd(DMN_CD)
				.dmnNm(DMN_NM)
				.dataType(DATA_TYPE)
				.rmk(RMK)
				.updtDtm(new Date())
				.crtDtm(new Date()).build());
	}
	
	public void rmDomain(
			String DMN_CD
			) {
		cmDomainR.deleteById(DMN_CD);
	}
}
