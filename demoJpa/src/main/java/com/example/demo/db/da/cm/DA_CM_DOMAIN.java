package com.example.demo.db.da.cm;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.db.domain.av.AvMv;
import com.example.demo.db.domain.cm.CmDomain;
import com.example.demo.db.repository.cm.CmDomainRepository;
import com.example.demo.exception.BizException;
import com.example.demo.db.domain.cm.QCmDomain;
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
	public void createDomain(
			long DMN_NO
			,String DMN_CD
			,String DMN_NM
			,String DATA_TYPE
			,String RMK
			,Long L_SESSION_USER_NO
			) {
		
		cmDomainR.save(
				CmDomain.builder()
				.dmnNo(DMN_NO)
				.dmnCd(DMN_CD)
				.dmnNm(DMN_NM)
				.dataType(DATA_TYPE)
				.rmk(RMK)
				.crtUsrNo(L_SESSION_USER_NO)
				.updtUsrNo(L_SESSION_USER_NO)
				.updtDtm(new Date())
				.crtDtm(new Date()).build());
	}
	

	/**
	 * @param DMN_CD  도메인코드
	 * @param PGM_NM  프로그램명
	 * @param RMK      비고
	 * @param CATEGORY  카테고리
	 * @param PGM_LINK  프로그램링크
	 * @throws BizException 
	 */
	public void updateDomain(
			long DMN_NO
			,String DMN_CD
			,String DMN_NM
			,String DATA_TYPE
			,String RMK
			,Long L_SESSION_USER_NO
			) throws BizException {
		
		Optional<CmDomain> c = cmDomainR.findById(DMN_NO);
		if(c==null) {
			throw new BizException("["+DMN_NO+"] 도메인이 존재하지 않습니다.[수정X]");
		}
		CmDomain tmp = c.get();
		tmp.setDmnCd(DMN_CD);
		tmp.setDmnNm(DMN_NM);
		tmp.setDataType(DATA_TYPE);
		tmp.setRmk(RMK);
		tmp.setUpdtUsrNo(L_SESSION_USER_NO);
		tmp.setUpdtDtm(new Date());
		cmDomainR.save(tmp);
	}
	
	public void rmDomain(
			long DMN_NO
			) {
		cmDomainR.deleteById(DMN_NO);
	}
}
