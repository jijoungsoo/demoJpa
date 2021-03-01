package com.example.demo.db.da.cm;

import java.util.Date;
import java.util.List;

import com.example.demo.db.domain.cm.CmEmlSnd;
import com.example.demo.db.domain.cm.CmEmlSndRcv;
import com.example.demo.db.domain.cm.QCmEmlSnd;
import com.example.demo.db.domain.cm.QCmEmlSndRcv;
import com.example.demo.db.repository.cm.CmEmlSndRcvRepository;
import com.example.demo.db.repository.cm.CmEmlSndRepository;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DA_CM_EML_SND {
	
	@Autowired
	JPAQueryFactory qf;
	
	@Autowired
	CmEmlSndRepository cmEmlSndR;
		
	public List<Tuple> findEmlSnd() {		
		NumberPath<Long> cnt = Expressions.numberPath(Long.class, "cnt");
		JPAQuery<Tuple>  c=  qf.select(
			QCmEmlSnd.cmEmlSnd.sndSeq,
			QCmEmlSnd.cmEmlSnd.ttl,
			QCmEmlSnd.cmEmlSnd.cntnt,
			QCmEmlSnd.cmEmlSnd.sndrNm,
			QCmEmlSnd.cmEmlSnd.sndrAddr,
			QCmEmlSnd.cmEmlSnd.sndStatusCd,
			QCmEmlSnd.cmEmlSnd.sndTypeCd,
			QCmEmlSnd.cmEmlSnd.sndDtm,
			QCmEmlSnd.cmEmlSnd.sndCmplDtm,
			QCmEmlSnd.cmEmlSnd.crtDtm,
			QCmEmlSnd.cmEmlSnd.crtUsrNo,
			QCmEmlSnd.cmEmlSnd.crtDtm,
			QCmEmlSnd.cmEmlSnd.updtUsrNo
			,ExpressionUtils.as(				
				JPAExpressions.select(QCmEmlSndRcv.cmEmlSndRcv.count())
						.from(QCmEmlSndRcv.cmEmlSndRcv)
						.where(QCmEmlSndRcv.cmEmlSndRcv.orfCmEmlSnd.eq(QCmEmlSnd.cmEmlSnd)),
				"rcv_cnt")
			,ExpressionUtils.as(
				JPAExpressions.select(QCmEmlSndRcv.cmEmlSndRcv.rcvNm.min())
						.from(QCmEmlSndRcv.cmEmlSndRcv)
						.where(QCmEmlSndRcv.cmEmlSndRcv.orfCmEmlSnd.eq(QCmEmlSnd.cmEmlSnd))
						/*.orderBy(QCmEmlSndRcv.cmEmlSndRcv.rcvSeq.asc())
						.limit(1)*/
						,"rcv_nm")
			,ExpressionUtils.as(			
				JPAExpressions.select(QCmEmlSndRcv.cmEmlSndRcv.rcvAddr.min())
						.from(QCmEmlSndRcv.cmEmlSndRcv)
						.where(QCmEmlSndRcv.cmEmlSndRcv.orfCmEmlSnd.eq(QCmEmlSnd.cmEmlSnd))
						/*
						.orderBy(QCmEmlSndRcv.cmEmlSndRcv.rcvSeq.asc())
						.limit(1)*/
						,"rcv_addr")
						
		)
		.from(QCmEmlSnd.cmEmlSnd)
		.orderBy(QCmEmlSnd.cmEmlSnd.sndSeq.desc());
		List<Tuple> al =  c.fetch();
		/*문제점 querydsl 하위 쿼리에  order by  limit 1이 안먹음 */
		 return al;
		 
	}


	public void crtEmlSnd(
			Long L_SND_SEQ
			,String TTL
			,String CNTNT
			,String SNDR_NM
			,String SNDR_ADDR
			,String SND_TYPE_CD
			,Date SND_DTM
			,Long L_SESSION_USER_NO
			) {
				cmEmlSndR.save(
					CmEmlSnd.builder()
					.sndSeq(L_SND_SEQ)
					.ttl(TTL)
					.cntnt(CNTNT)
					.sndrNm(SNDR_NM)
					.sndrAddr(SNDR_ADDR)
					.sndStatusCd("B")		//B 발송전  --배치에서 보내고 나서 발송 후A
					.sndTypeCd(SND_TYPE_CD)
					.sndDtm(SND_DTM)
					.sndTypeCd(SND_TYPE_CD)
					.crtUsrNo(L_SESSION_USER_NO)
					.updtUsrNo(L_SESSION_USER_NO)
					.updtDtm(new Date())
					.crtDtm(new Date()).build()
					);
	}


	public List<CmEmlSnd> findEmlSndBySndSeq(Long L_SND_SEQ) {
		List<CmEmlSnd> al =  qf
		.selectFrom(QCmEmlSnd.cmEmlSnd)
		.where(QCmEmlSnd.cmEmlSnd.sndSeq.eq(L_SND_SEQ))
		.orderBy(QCmEmlSnd.cmEmlSnd.sndSeq.desc())
		.fetch();
		return al;
	}
}
