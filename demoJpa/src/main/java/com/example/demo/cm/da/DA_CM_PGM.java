package com.example.demo.cm.da;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.da.service.CmPgmRepository;
import com.example.demo.domain.CmPgm;
import com.example.demo.domain.QCmPgm;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Service
public class DA_CM_PGM {
	
	@Autowired
	JPAQueryFactory qf;
	
	@Autowired
	CmPgmRepository cmPgmR;
	
	public List<CmPgm> findPgm() {
		List<CmPgm> al =  qf
	                .selectFrom(QCmPgm.cmPgm)
	                .orderBy(QCmPgm.cmPgm.pgmId.asc())
	                .fetch();
		 return al;
	}
	
	/**
	 * @param PGM_ID  프로그램 ID
	 * @param PGM_NM  프로그램명
	 * @param RMK      비고
	 * @param CATEGORY  카테고리
	 * @param PGM_LINK  프로그램링크
	 */
	public void insertPgm(
			String PGM_ID
			,String PGM_NM
			,String RMK
			,String CATEGORY
			,String PGM_LINK
			) {
		
		cmPgmR.save(
				CmPgm.builder()
				.pgmId(PGM_ID)
				.pgmNm(PGM_NM)
				.rmk(RMK)
				.category(CATEGORY)
				.pgmLink(PGM_LINK)
				.updtDtm(new Date())
				.crtDtm(new Date()).build());
	}
}
