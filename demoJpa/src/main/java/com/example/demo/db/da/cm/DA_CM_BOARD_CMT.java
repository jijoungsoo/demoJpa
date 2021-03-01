package com.example.demo.db.da.cm;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.demo.db.domain.cm.CmBoardCmt;
import com.example.demo.db.domain.cm.QCmBoardCmt;
import com.example.demo.db.repository.cm.CmBoardCmtRepository;
import com.example.demo.exception.BizException;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DA_CM_BOARD_CMT {
	
	@Autowired
	JPAQueryFactory qf;
	
	@Autowired
	CmBoardCmtRepository cmBoardCmtR;
	
	public List<CmBoardCmt> findBrdCmt(Long BRD_SEQ) {
		List<CmBoardCmt> al =  qf
	                .selectFrom(QCmBoardCmt.cmBoardCmt)
					.where(QCmBoardCmt.cmBoardCmt.brdSeq.eq(BRD_SEQ))
	                .orderBy(QCmBoardCmt.cmBoardCmt.cmtSeq.desc())
	                .fetch();
		 return al;
	}

	public List<CmBoardCmt> findBrdCmtByCmtSeq(Long L_CMT_SEQ) {
		List<CmBoardCmt> al =  qf
	                .selectFrom(QCmBoardCmt.cmBoardCmt)
					.where(QCmBoardCmt.cmBoardCmt.cmtSeq.eq(L_CMT_SEQ))
	                .orderBy(QCmBoardCmt.cmBoardCmt.cmtSeq.desc())
	                .fetch();
		 return al;
	}

	public void crtBrdCmt(
			 long L_BRD_SEQ
			,long L_CMT_SEQ
			,long L_PRNT_CMT_SEQ
			,String CNTNT
			,String CNTNT_TEXT
			,Long L_SESSION_USER_NO
			) {
				Long L_ROOT_CMT_SEQ  =0L;
				Long L_CMT_RPLY_ORD  =0L;
				Long L_CMT_DEPTH     =0L;
		
				cmBoardCmtR.save(
					CmBoardCmt.builder()				
				.brdSeq(L_BRD_SEQ)
				.cmtSeq(L_CMT_SEQ)
				.rootCmtSeq(L_ROOT_CMT_SEQ)
				.prntCmtSeq(L_PRNT_CMT_SEQ)
				.cmtRplyOrd(L_CMT_RPLY_ORD)
				.cmtDpth(L_CMT_DEPTH)
				.cntnt(CNTNT)
				.delYn("N")
				.cntntText(CNTNT_TEXT)
				.crtUsrNo(L_SESSION_USER_NO)
				.updtUsrNo(L_SESSION_USER_NO)
				.updtDtm(new Date())
				.crtDtm(new Date()).build());
	}
	
	public void updtBrdCmt(
		long CMT_SEQ
		,String CNTNT
		,String CNTNT_TEXT
		,Long L_SESSION_USER_NO
			) throws BizException {
		
		Optional<CmBoardCmt> c = cmBoardCmtR.findById(CMT_SEQ);
		if(c==null) {
			throw new BizException("["+CMT_SEQ+"] 댓글SEQ가 존재하지 않습니다.[수정X]");
		}
		CmBoardCmt tmp = c.get();
		tmp.setCntnt(CNTNT);
		tmp.setCntntText(CNTNT_TEXT);
		tmp.setUpdtUsrNo(L_SESSION_USER_NO);
		tmp.setUpdtDtm(new Date());
		cmBoardCmtR.save(tmp);
	}
	
	public void rmBrdCmt(
		Long CMT_SEQ
			) {
				cmBoardCmtR.deleteById(CMT_SEQ);
	}
}
