package com.example.demo.db.da.cm;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.demo.db.domain.cm.CmPgm;
import com.example.demo.db.domain.cm.QCmPgm;
import com.example.demo.db.repository.cm.CmPgmRepository;
import com.example.demo.exception.BizException;
import com.example.demo.utils.PjtUtil;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DA_CM_PGM {
	
	@Autowired
	JPAQueryFactory qf;
	

	
	@Autowired
	CmPgmRepository cmPgmR;
	
	public List<CmPgm> findPgm(String CATEGORY) {
		JPAQuery<CmPgm> c = qf
		.selectFrom(QCmPgm.cmPgm)
		.orderBy(QCmPgm.cmPgm.pgmId.asc());
		if(!PjtUtil.isEmpty(CATEGORY)){
			c=c.where(QCmPgm.cmPgm.category.eq(CATEGORY));
		}
		List<CmPgm> al =  c.fetch();
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
			 Long PGM_NO
			,String PGM_ID
			,String PGM_NM
			,String RMK
			,String ORD
			,String CATEGORY
			,String DIR_LINK
			,String PGM_LINK
			
			) {
		
		cmPgmR.save(
				CmPgm.builder()
				.pgmNo(PGM_NO)
				.pgmId(PGM_ID)
				.pgmNm(PGM_NM)
				.rmk(RMK)
				.ord(ORD)
				.category(CATEGORY)
				.dirLink(DIR_LINK)
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
			Long PGM_NO
			,String PGM_ID
			,String PGM_NM
			,String RMK
			,String ORD
			,String CATEGORY
			,String DIR_LINK
			,String PGM_LINK
			
			) throws BizException {
		Optional<CmPgm> c = cmPgmR.findById(PGM_NO);
		if(c==null) {
			throw new BizException("["+PGM_NO+"]["+PGM_ID+"] 프로그램이 존재하지 않습니다.[수정X]");
		}
		CmPgm tmp = c.get();
		tmp.setPgmId(PGM_ID);
		tmp.setPgmNm(PGM_NM);
		tmp.setRmk(RMK);
		tmp.setOrd(ORD);
		tmp.setCategory(CATEGORY);
		tmp.setDirLink(DIR_LINK);
		tmp.setPgmLink(PGM_LINK);
		tmp.setUpdtDtm(new Date());
		cmPgmR.save(tmp);
	}
	
	public void rmPgm(
			Long PGM_NO
			) {
		cmPgmR.deleteById(PGM_NO);
	}
}
