package com.example.demo.db.da.cm;

import java.util.Date;
import java.util.List;

import com.example.demo.db.domain.cm.CmMsgSndRcv;
import com.example.demo.db.domain.cm.QCmMsgSndRcv;
import com.example.demo.db.repository.cm.CmMsgSndRcvRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DA_CM_MSG_SND_RCV {
	
	@Autowired
	JPAQueryFactory qf;

	@Autowired
	CmMsgSndRcvRepository cmMsgSndRcvR;
	
	public void crtMsgSndRcv(
			Long L_SND_SEQ
			,int I_RCV_SEQ
			,String RCV_TEL_NO
			,String RCV_NM
			,Long L_SESSION_USER_NO
			) {
				cmMsgSndRcvR.save(
					CmMsgSndRcv.builder()
					.sndSeq(L_SND_SEQ)
					.rcvSeq(I_RCV_SEQ)
					.rcvTelNo(RCV_TEL_NO)
					.rcvStatusCd("S")  //S-발송 , C 완료
					.rcvNm(RCV_NM)
					.crtUsrNo(L_SESSION_USER_NO)
					.updtUsrNo(L_SESSION_USER_NO)
					.updtDtm(new Date())
					.crtDtm(new Date()).build()
					);
	}

	public List<CmMsgSndRcv> findMsgSndRcvBySndSeq(Long L_SND_SEQ) {
		List<CmMsgSndRcv> al =  qf
		.selectFrom(QCmMsgSndRcv.cmMsgSndRcv)
		.where(QCmMsgSndRcv.cmMsgSndRcv.sndSeq.eq(L_SND_SEQ))
		.orderBy(QCmMsgSndRcv.cmMsgSndRcv.rcvSeq.desc())
		.fetch();
		return al;
	}
}
