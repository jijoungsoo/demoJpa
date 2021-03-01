package com.example.demo.db.da.cm;

import java.util.Date;
import java.util.List;

import com.example.demo.db.domain.cm.CmMsgSnd;
import com.example.demo.db.domain.cm.CmMsgSndRcv;
import com.example.demo.db.domain.cm.QCmMsgSnd;
import com.example.demo.db.domain.cm.QCmMsgSndRcv;
import com.example.demo.db.repository.cm.CmMsgSndRcvRepository;
import com.example.demo.db.repository.cm.CmMsgSndRepository;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DA_CM_MSG_SND {
	
	@Autowired
	JPAQueryFactory qf;
	
	@Autowired
	CmMsgSndRepository cmMsgSndR;
	
	public List<Tuple> findMsgSnd() {
		
		NumberPath<Long> cnt = Expressions.numberPath(Long.class, "cnt");
		JPAQuery<Tuple>  c=  qf.select(
			QCmMsgSnd.cmMsgSnd.sndSeq,
			QCmMsgSnd.cmMsgSnd.sndKindCd,
			QCmMsgSnd.cmMsgSnd.msg,
			QCmMsgSnd.cmMsgSnd.sndrNm,
			QCmMsgSnd.cmMsgSnd.sndrTelNo,
			QCmMsgSnd.cmMsgSnd.sndStatusCd,
			QCmMsgSnd.cmMsgSnd.sndTypeCd,
			QCmMsgSnd.cmMsgSnd.sndDtm,
			QCmMsgSnd.cmMsgSnd.sndCmplDtm,
			QCmMsgSnd.cmMsgSnd.crtDtm,
			QCmMsgSnd.cmMsgSnd.crtUsrNo,
			QCmMsgSnd.cmMsgSnd.crtDtm,
			QCmMsgSnd.cmMsgSnd.updtUsrNo
			,ExpressionUtils.as(				
				JPAExpressions.select(QCmMsgSndRcv.cmMsgSndRcv.count())
						.from(QCmMsgSndRcv.cmMsgSndRcv)
						.where(QCmMsgSndRcv.cmMsgSndRcv.orfCmMsgSnd.eq(QCmMsgSnd.cmMsgSnd)),
				"rcv_cnt")
			,ExpressionUtils.as(
				JPAExpressions.select(QCmMsgSndRcv.cmMsgSndRcv.rcvNm)
						.from(QCmMsgSndRcv.cmMsgSndRcv)
						.where(QCmMsgSndRcv.cmMsgSndRcv.orfCmMsgSnd.eq(QCmMsgSnd.cmMsgSnd))
						.orderBy(QCmMsgSndRcv.cmMsgSndRcv.rcvSeq.asc())
						.limit(1)
						,"rcv_nm")			
			,ExpressionUtils.as(
				JPAExpressions.select(QCmMsgSndRcv.cmMsgSndRcv.rcvTelNo)
						.from(QCmMsgSndRcv.cmMsgSndRcv)
						.where(QCmMsgSndRcv.cmMsgSndRcv.orfCmMsgSnd.eq(QCmMsgSnd.cmMsgSnd))
						.orderBy(QCmMsgSndRcv.cmMsgSndRcv.rcvSeq.asc())
						.limit(1)
						,"rcv_tel_no")	
		)
		.from(QCmMsgSnd.cmMsgSnd)
		.orderBy(QCmMsgSnd.cmMsgSnd.sndSeq.desc());
		List<Tuple> al =  c.fetch();
		 return al;
		 
	}

	public void crtMsgSnd(
			Long L_SND_SEQ
			,String SND_KIND_CD
			,String MSG
			,String SNDR_NM
			,String SNDR_TEL_NO
			,String SND_TYPE_CD
			,Date SND_DTM
			,Long L_SESSION_USER_NO
			) {
				cmMsgSndR.save(
					CmMsgSnd.builder()
					.sndSeq(L_SND_SEQ)
					.sndKindCd(SND_KIND_CD)
					.msg(MSG)
					.sndrNm(SNDR_NM)
					.sndrTelNo(SNDR_TEL_NO)
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


	public List<CmMsgSnd> findMsgSndBySndSeq(Long L_SND_SEQ) {
		List<CmMsgSnd> al =  qf
		.selectFrom(QCmMsgSnd.cmMsgSnd)
		.where(QCmMsgSnd.cmMsgSnd.sndSeq.eq(L_SND_SEQ))
		.orderBy(QCmMsgSnd.cmMsgSnd.sndSeq.desc())
		.fetch();
		return al;
	}
}
