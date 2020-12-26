package com.example.demo.db.da.cm;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.db.domain.cm.CmSeq;
import com.example.demo.db.repository.cm.CmSeqRepository;
import com.example.demo.db.domain.cm.QCmSeq;
import com.example.demo.exception.BizException;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Service
public class DA_CM_SEQ {
	
	@Autowired
	JPAQueryFactory qf;
	
	@Autowired
	CmSeqRepository cmSeqR;
	
	public List<CmSeq> findCmSeq() {
		List<CmSeq> al =  qf
	                .selectFrom(QCmSeq.cmSeq)
	                .orderBy(QCmSeq.cmSeq.crtDtm.desc())
	                .fetch();
		 return al;
	}
	
	public long increate(String SEQ_NM) throws BizException {
		
		Optional<CmSeq> c= cmSeqR.findById(SEQ_NM);
		if(c==null) {
			throw new BizException("시퀀스를 찾지 못햇습니다.");
		}
		long seqNo= c.get().getSeqNo();
		int allocationSize= c.get().getAllocationSize();
		seqNo=seqNo+allocationSize;
		c.get().setSeqNo(seqNo);
		return seqNo;
	}
	
	/**
	 * @param SEQ_NM  그룹코드
	 * @param SEQ_NO  그룹코드명
	 * @param TB_NM  사용여부
	 * @param COL_NM 	  순서
	 * @param INIT_VAL	  비고
	 * @param ALLOCATION_SIZE	  비고
	 */
	public void saveCmSeq(
			String SEQ_NM
			,long SEQ_NO
			,String TB_NM
			,String COL_NM
			,int INIT_VAL
			,int ALLOCATION_SIZE
			) {
		
		cmSeqR.save(
				CmSeq.builder()
				.seqNm(SEQ_NM)
				.seqNo(SEQ_NO)
				.tbNm(TB_NM)
				.colNm(COL_NM)
				.initVal(INIT_VAL)
				.allocationSize(ALLOCATION_SIZE)
				.updtDtm(new Date())
				.crtDtm(new Date()).build());
	}
	
	
	public void rmCmSeq(
			String SEQ_NM
			) {
		cmSeqR.deleteById(SEQ_NM);
	}
}
