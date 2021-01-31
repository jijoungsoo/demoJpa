package com.example.demo.db.da.av;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.db.repository.av.AvActrRepository;
import com.example.demo.db.domain.av.AvActr;
import com.example.demo.db.domain.av.QAvActr;
import com.example.demo.exception.BizException;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Service
public class DA_AV_ACTR {
	
	@Autowired
	JPAQueryFactory qf;
	
	
	@Autowired
	AvActrRepository avActrR;
	
	public Page<Tuple> findAvActr(Pageable p) {
		
		JPAQuery<Tuple> c= qf.select(QAvActr.avActr.actrSeq,
				QAvActr.avActr.actrNmKr,
				QAvActr.avActr.actrNmJp,
				QAvActr.avActr.actrNmEng,
				QAvActr.avActr.birthDt,
				QAvActr.avActr.sex,
				QAvActr.avActr.rnk,
				QAvActr.avActr.ord,
				QAvActr.avActr.rmk,
				QAvActr.avActr.mscYn,
				QAvActr.avActr.crtUsrNo,
				QAvActr.avActr.updtUsrNo,
				QAvActr.avActr.crtDtm,
				QAvActr.avActr.updtDtm
				)
				.from(QAvActr.avActr);
		c= c.offset(p.getOffset()); // offset과
		c= c.limit(p.getPageSize()); // Limit 을 지정할 수 있고
		c= c.orderBy(
				QAvActr.avActr.ord.asc(),
				QAvActr.avActr.actrNmKr.asc(),
				QAvActr.avActr.actrSeq.asc()
				);
		QueryResults<Tuple> result= c.fetchResults();
		 return new PageImpl<>(result.getResults(), p, result.getTotal());
	}
	
	public void createAvActr(long L_ACTR_SEQ
			, String ACTR_NM_KR
			, String ACTR_NM_JP
			, String ACTR_NM_ENG
			, String BIRTH_DT
			, String SEX
			, Integer RNK
			, Integer ORD
			, String RMK
			, String MSC_YN
			, long L_USR_NO
			) {
		avActrR.save(
				AvActr.builder()
				.actrSeq(L_ACTR_SEQ)
				.actrNmKr(ACTR_NM_KR)
				.actrNmJp(ACTR_NM_JP)
				.actrNmEng(ACTR_NM_ENG)
				.birthDt(BIRTH_DT)
				.sex(SEX)
				.rnk(RNK)
				.ord(ORD)
				.rmk(RMK)
				.mscYn(MSC_YN)
				.crtUsrNo(L_USR_NO)
				.updtUsrNo(L_USR_NO)
				.updtDtm(new Date())
				.crtDtm(new Date()).build());
	}
	

	public void updateAvActr(long L_ACTR_SEQ
			, String ACTR_NM_KR
			, String ACTR_NM_JP
			, String ACTR_NM_ENG
			, String BIRTH_DT
			, String SEX
			, Integer RNK
			, Integer ORD
			, String RMK
			, String MSC_YN
			, long L_UPDT_USR_NO
			) throws BizException {
		
		Optional<AvActr> c = avActrR.findById(L_ACTR_SEQ);
		if(c==null) {
			throw new BizException("["+L_ACTR_SEQ+"] 배우가  존재하지 않습니다.[수정X]");
		}
		AvActr tmp = c.get();
		tmp.setActrNmKr(ACTR_NM_KR);
		tmp.setActrNmJp(ACTR_NM_JP);
		tmp.setActrNmEng(ACTR_NM_ENG);
		tmp.setBirthDt(BIRTH_DT);
		tmp.setSex(SEX);
		tmp.setRnk(RNK);
		tmp.setOrd(ORD);
		tmp.setRmk(RMK);
		tmp.setMscYn(MSC_YN);
		tmp.setUpdtUsrNo(L_UPDT_USR_NO);
		tmp.setUpdtDtm(new Date());
		avActrR.save(tmp);
		
		
	}
	
	public Optional<AvActr> findById(long l_ACTR_SEQ){
		return avActrR.findById(l_ACTR_SEQ);
	}
	
	
	public void rmAvActr(long l_ACTR_SEQ) {
		avActrR.deleteById(l_ACTR_SEQ);
		
	}
}
