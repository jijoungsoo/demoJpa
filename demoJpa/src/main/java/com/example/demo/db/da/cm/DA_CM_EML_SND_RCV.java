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
public class DA_CM_EML_SND_RCV {
	
	@Autowired
	JPAQueryFactory qf;

	@Autowired
	CmEmlSndRcvRepository cmEmlSndRcvR;
	

	public void crtEmlSndRcv(
			Long L_SND_SEQ
			,int I_RCV_SEQ
			,String RCV_ADDR
			,String RCV_NM
			,Long L_SESSION_USER_NO
			) {
				cmEmlSndRcvR.save(
					CmEmlSndRcv.builder()
					.sndSeq(L_SND_SEQ)
					.rcvSeq(I_RCV_SEQ)
					.rcvAddr(RCV_ADDR)
					.rcvStatusCd("S")  //S-발송 , C 완료
					.rcvNm(RCV_NM)
					.crtUsrNo(L_SESSION_USER_NO)
					.updtUsrNo(L_SESSION_USER_NO)
					.updtDtm(new Date())
					.crtDtm(new Date()).build()
					);
	}

	public List<CmEmlSndRcv> findEmlSndRcvBySndSeq(Long L_SND_SEQ) {
		List<CmEmlSndRcv> al =  qf
		.selectFrom(QCmEmlSndRcv.cmEmlSndRcv)
		.where(QCmEmlSndRcv.cmEmlSndRcv.sndSeq.eq(L_SND_SEQ))
		.orderBy(QCmEmlSndRcv.cmEmlSndRcv.rcvSeq.desc())
		.fetch();
		return al;
	}
}
