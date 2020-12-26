package com.example.demo.db.da.cm;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.db.domain.cm.CmPgm;
import com.example.demo.db.repository.cm.CmPgmRepository;
import com.example.demo.db.domain.cm.QCmPgm;
import com.example.demo.exception.BizException;
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
	public void createPgm(
			String PGM_ID
			,String PGM_NM
			,String RMK
			,String CATEGORY
			,String DIR_LINK
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
	
	/**
	 * @param PGM_ID  프로그램 ID
	 * @param PGM_NM  프로그램명
	 * @param RMK      비고
	 * @param CATEGORY  카테고리
	 * @param PGM_LINK  프로그램링크
	 * @throws BizException 
	 */
	public void updatePgm(
			String PGM_ID
			,String PGM_NM
			,String RMK
			,String CATEGORY
			,String DIR_LINK
			,String PGM_LINK
			
			) throws BizException {
		Optional<CmPgm> c = cmPgmR.findById(PGM_ID);
		if(c==null) {
			throw new BizException("["+PGM_ID+"] 프로그램이 존재하지 않습니다.[수정X]");
		}
		CmPgm tmp = c.get();
		tmp.setPgmNm(PGM_NM);
		tmp.setRmk(RMK);
		tmp.setCategory(CATEGORY);
		tmp.setDirLink(DIR_LINK);
		tmp.setPgmLink(PGM_LINK);
		tmp.setUpdtDtm(new Date());
		cmPgmR.save(tmp);
	}
	
	public void rmPgm(
			String PGM_ID
			) {
		cmPgmR.deleteById(PGM_ID);
	}
}
