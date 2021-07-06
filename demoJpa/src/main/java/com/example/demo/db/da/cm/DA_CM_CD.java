package com.example.demo.db.da.cm;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.demo.db.domain.cm.CmCd;
import com.example.demo.db.domain.cm.CmCdId;
import com.example.demo.db.domain.cm.QCmCd;
import com.example.demo.db.repository.cm.CmCdRepository;
import com.example.demo.exception.BizException;
import com.example.demo.utils.PjtUtil;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DA_CM_CD {

	@Autowired
    PjtUtil pjtU;
	
	@Autowired
	JPAQueryFactory qf;
	
	@Autowired
	CmCdRepository cmCdR;
	
	public List<CmCd> findCmCd(String GRP_CD,String USE_YN
	,String ATTR1
	,String ATTR2
	,String ATTR3
	,String ATTR4
	) {
		JPAQuery<CmCd> q=qf
        .selectFrom(QCmCd.cmCd)
        .where(QCmCd.cmCd.grpCd.eq(GRP_CD));
		if(!pjtU.isEmpty(USE_YN)) {
			q.where(QCmCd.cmCd.useYn.eq(USE_YN));
		}
		if(!pjtU.isEmpty(ATTR1)) {
			q.where(QCmCd.cmCd.attr1.eq(ATTR1));
		}
		if(!pjtU.isEmpty(ATTR2)) {
			q.where(QCmCd.cmCd.attr2.eq(ATTR2));
		}
		if(!pjtU.isEmpty(ATTR3)) {
			q.where(QCmCd.cmCd.attr3.eq(ATTR3));
		}
		if(!pjtU.isEmpty(ATTR4)) {
			q.where(QCmCd.cmCd.attr4.eq(ATTR4));
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
			,String ATTR1
			,String ATTR2
			,String ATTR3
			,String ATTR4
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
				.attr1(ATTR1)
				.attr2(ATTR2)
				.attr3(ATTR3)
				.attr4(ATTR4)
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
			,String ATTR1
			,String ATTR2
			,String ATTR3
			,String ATTR4
			,Long L_SESSION_USER_NO
			) throws BizException {
		CmCdId cm_cd_id = new CmCdId();
		cm_cd_id.setGrpCd(GRP_CD);
		cm_cd_id.setCd(CD);
		Optional<CmCd> c= cmCdR.findById(cm_cd_id);
		
		if(c==null) {
			throw new BizException("["+GRP_CD+"그룹코드 "+CD+"코드]가 존재하지 않습니다.[수정X]");
		}
		CmCd tmp = c.get();
		tmp.setGrpCd(GRP_CD);
		tmp.setCdNm(CD_NM);
		tmp.setUseYn(USE_YN);
		tmp.setOrd(Integer.parseInt(ORD));
		tmp.setRmk(RMK);
		tmp.setAttr1(ATTR1);
		tmp.setAttr2(ATTR2);
		tmp.setAttr3(ATTR3);
		tmp.setAttr4(ATTR4);
		tmp.setUpdtUsrNo(L_SESSION_USER_NO);
		tmp.setUpdtDtm(new Date());
		
		cmCdR.save(tmp);
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
