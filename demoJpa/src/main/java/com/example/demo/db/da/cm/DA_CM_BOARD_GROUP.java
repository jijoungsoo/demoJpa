package com.example.demo.db.da.cm;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.demo.db.domain.cm.CmBoardGroup;
import com.example.demo.db.domain.cm.QCmBoardGroup;
import com.example.demo.db.repository.cm.CmBoardGroupRepository;
import com.example.demo.exception.BizException;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DA_CM_BOARD_GROUP {
	
	@Autowired
	JPAQueryFactory qf;
	
	@Autowired
	CmBoardGroupRepository cmBoardGroupR;
	
	public List<CmBoardGroup> findBrdGrp() {
		List<CmBoardGroup> al =  qf
	                .selectFrom(QCmBoardGroup.cmBoardGroup)
	                .orderBy(QCmBoardGroup.cmBoardGroup.grpSeq.asc())
	                .fetch();
		 return al;
	}

	public void crtBrdGrp(
			long GRP_SEQ
			,String GRP_NM
			,String USE_YN
			,String RMK
			,Long L_SESSION_USER_NO
			) {
		
				cmBoardGroupR.save(
					CmBoardGroup.builder()
				.grpSeq(GRP_SEQ)
				.grpNm(GRP_NM)
				.useYn(USE_YN)
				.rmk(RMK)
				.crtUsrNo(L_SESSION_USER_NO)
				.updtUsrNo(L_SESSION_USER_NO)
				.updtDtm(new Date())
				.crtDtm(new Date()).build());
	}
	
	public void updtBrdGrp(
		Long GRP_SEQ
		,String GRP_NM
		,String USE_YN
		,String RMK
			,Long L_SESSION_USER_NO
			) throws BizException {
		
		Optional<CmBoardGroup> c = cmBoardGroupR.findById(GRP_SEQ);
		if(c==null) {
			throw new BizException("["+GRP_SEQ+"] 게시판관리가 존재하지 않습니다.[수정X]");
		}
		CmBoardGroup tmp = c.get();
		tmp.setGrpNm(GRP_NM);
		tmp.setUseYn(USE_YN);
		tmp.setRmk(RMK);
		tmp.setUpdtUsrNo(L_SESSION_USER_NO);
		tmp.setUpdtDtm(new Date());
		cmBoardGroupR.save(tmp);
	}
	
	public void rmBrdGrp(
		Long GRP_SEQ
			) {
				cmBoardGroupR.deleteById(GRP_SEQ);
	}
}
