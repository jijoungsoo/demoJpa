package com.example.demo.db.da.cm;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.demo.db.domain.cm.CmBoard;
import com.example.demo.db.domain.cm.QCmBoard;
import com.example.demo.db.repository.cm.CmBoardRepository;
import com.example.demo.exception.BizException;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DA_CM_BOARD {
	
	@Autowired
	JPAQueryFactory qf;
	
	@Autowired
	CmBoardRepository cmBoardR;
	
	public List<CmBoard> findBrd() {
		List<CmBoard> al =  qf
	                .selectFrom(QCmBoard.cmBoard)
	                .orderBy(QCmBoard.cmBoard.brdSeq.desc())
	                .fetch();
		 return al;
	}

	public void crtBrd(
			long L_GRP_SEQ
			,long L_BRD_SEQ
			,long L_PRNT_BRD_SEQ
			,String TTL
			,String TTL_TEXT
			,String CNTNT
			,String CNTNT_TEXT
			,Long L_SESSION_USER_NO
			) {
				Long L_ROOT_BRD_SEQ  =0L;
				Long L_BRD_RPLY_ORD  =0L;
				Long L_BRD_DEPTH     =0L;
		
				cmBoardR.save(
					CmBoard.builder()
				.grpSeq(L_GRP_SEQ)
				.brdSeq(L_BRD_SEQ)
				.rootBrdSeq(L_PRNT_BRD_SEQ)
				.prntBrdSeq(L_ROOT_BRD_SEQ)
				.brdRplyOrd(L_BRD_RPLY_ORD)
				.brdDpth(L_BRD_DEPTH)
				.ttl(TTL)
				.ttlText(TTL_TEXT)
				.cntnt(CNTNT)
				.delYn("N")
				.cntntText(CNTNT_TEXT)
				.crtUsrNo(L_SESSION_USER_NO)
				.updtUsrNo(L_SESSION_USER_NO)
				.updtDtm(new Date())
				.crtDtm(new Date()).build());
	}
	
	public void updtBrd(
		long GRP_SEQ
		,long BRD_SEQ
		,String TTL
		,String TTL_TEXT
		,String CNTNT
		,String CNTNT_TEXT
		,Long L_SESSION_USER_NO
			) throws BizException {
		
		Optional<CmBoard> c = cmBoardR.findById(BRD_SEQ);
		if(c==null) {
			throw new BizException("["+GRP_SEQ+"] 게시물SEQ가 존재하지 않습니다.[수정X]");
		}
		CmBoard tmp = c.get();
		tmp.setTtl(TTL);
		tmp.setTtlText(TTL_TEXT);
		tmp.setCntnt(CNTNT);
		tmp.setCntntText(CNTNT_TEXT);
		tmp.setUpdtUsrNo(L_SESSION_USER_NO);
		tmp.setUpdtDtm(new Date());
		cmBoardR.save(tmp);
	}
	
	public void rmBrd(
		Long BRD_SEQ
			) {
				cmBoardR.deleteById(BRD_SEQ);
	}
}
