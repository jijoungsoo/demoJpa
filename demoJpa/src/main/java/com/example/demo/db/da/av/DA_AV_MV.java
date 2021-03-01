package com.example.demo.db.da.av;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.db.repository.av.AvMvRepository;
import com.example.demo.db.domain.av.AvMv;
import com.example.demo.db.domain.av.QAvMv;
import com.example.demo.exception.BizException;
import com.example.demo.utils.PjtUtil;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Service
public class DA_AV_MV {
	
	@Autowired
	JPAQueryFactory qf;
	
	
	@Autowired
	AvMvRepository avMvR;
	
	public Page<AvMv> findAvMv(String MSC_CD , String VR_YN, Pageable p) {
		JPAQuery<AvMv> c= qf.selectFrom(QAvMv.avMv);
		c= c.orderBy(
				QAvMv.avMv.avNm.asc(),
				QAvMv.avMv.ord.asc(),
				QAvMv.avMv.avSeq.asc()
				);
		if(!PjtUtil.isEmpty(MSC_CD)){
			c=c.where(QAvMv.avMv.mscCd.eq(MSC_CD));
		}
		if(!PjtUtil.isEmpty(VR_YN)){
			c=c.where(QAvMv.avMv.vrYn.eq(VR_YN));
		}
		if(p!=null) {
			c= c.offset(p.getOffset()); // offset과
			c= c.limit(p.getPageSize()); // Limit 을 지정할 수 있고			
		}
		QueryResults<AvMv> result= c.fetchResults();
		
		if(p==null) {
			p = PageRequest.of(0, (int) result.getTotal());
		}
		return new PageImpl<>(result.getResults(), p, result.getTotal());
	}
	
	public void createAvMv(long L_AV_SEQ
			, String AV_NM
			, String TTL
			, String CNTNT
			, String MSC_CD
			, String VR_YN
			, String ORD
			, String RMK
			, String CPTN_YN
			, String MK_DT
			, long L_USR_NO
			) {
				
		avMvR.save(
				AvMv.builder()
				.avSeq(L_AV_SEQ)
				.avNm(AV_NM)
				.ttl(TTL)
				.cntnt(CNTNT)
				.lkCnt(0)
				.dslkCnt(0)
				.mscCd(MSC_CD)
				.vrYn(VR_YN)
				.ord(ORD)
				.rmk(RMK)
				.cptnYn(CPTN_YN)
				.rmk(RMK)
				.mkDt(MK_DT)
				.crtUsrNo(L_USR_NO)
				.updtUsrNo(L_USR_NO)
				.updtDtm(new Date())
				.crtDtm(new Date()).build());
	}
	

	public void updateAvMv(long L_AV_SEQ
			, String AV_NM
			, String TTL
			, String CNTNT
			, String MSC_CD
			, String VR_YN
			, String ORD
			, String RMK
			, String CPTN_YN
			, String MK_DT
			, long L_USR_NO
			) throws BizException {
		
		Optional<AvMv> c = avMvR.findById(L_AV_SEQ);
		if(c==null) {
			throw new BizException("["+L_AV_SEQ+"] 작품이 존재하지 않습니다.[수정X]");
		}
		AvMv tmp = c.get();
		tmp.setAvNm(AV_NM);
		tmp.setTtl(TTL);
		tmp.setCntnt(CNTNT);
		tmp.setMscCd(MSC_CD);
		tmp.setVrYn(VR_YN);
		tmp.setOrd(ORD);
		tmp.setRmk(RMK);
		tmp.setCptnYn(CPTN_YN);
		tmp.setMkDt(MK_DT);
		tmp.setUpdtUsrNo(L_USR_NO);
		tmp.setUpdtDtm(new Date());
		avMvR.save(tmp);
		
		
	}
	
	public Optional<AvMv> findById(long L_AV_SEQ){
		return avMvR.findById(L_AV_SEQ);
	}
	
	public  List<AvMv>  findByAvNm(String avNm){
		return qf.selectFrom(QAvMv.avMv)
		.where(QAvMv.avMv.avNm.eq(avNm))
		.fetch();
		
	}
	
	
	public void rmAvMv(long L_AV_SEQ) {
		avMvR.deleteById(L_AV_SEQ);
		
	}
}
