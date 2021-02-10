package com.example.demo.db.da.cm;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.db.domain.cm.CmCd;
import com.example.demo.db.domain.cm.CmCdId;
import com.example.demo.db.domain.cm.CmGrpCd;
import com.example.demo.db.repository.cm.CmGrpCdRepository;
import com.example.demo.exception.BizException;
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
			) throws BizException {
		
		
		Optional<CmGrpCd> c= cmGrpCdR.findById(GRP_CD);
		
		if(c==null) {
			throw new BizException("["+GRP_CD+"그룹코드]가 존재하지 않습니다.[수정X]");
		}

		CmGrpCd tmp = c.get();
		tmp.setGrpCd(GRP_CD);
		tmp.setGrpNm(GRP_NM);
		tmp.setUseYn(USE_YN);
		tmp.setOrd(Integer.parseInt(ORD));
		tmp.setRmk(RMK);
		tmp.setUpdtUsrNo(L_SESSION_USER_NO);
		tmp.setUpdtDtm(new Date());
		
		cmGrpCdR.save(tmp);
	}
	
	public void rmCmGrpCd(
			String GRP_CD
			) {
		cmGrpCdR.deleteById(GRP_CD);
	}
}
