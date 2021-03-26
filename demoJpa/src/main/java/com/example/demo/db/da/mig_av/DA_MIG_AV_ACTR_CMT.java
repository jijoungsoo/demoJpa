package com.example.demo.db.da.mig_av;

import java.util.Date;
import java.util.List;

import com.example.demo.db.domain.mig_av.MigAvActrCmt;
import com.example.demo.db.domain.mig_av.QMigAvActrCmt;
import com.example.demo.db.repository.mig_av.MigAvActrCmtRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DA_MIG_AV_ACTR_CMT {
	
	@Autowired
	JPAQueryFactory qf;
	
	
	@Autowired
	MigAvActrCmtRepository migAvActrCmtR;

	


	public List<MigAvActrCmt> findMigAvActrCmtByActorIdx(Long L_ACTOR_IDX) {
		return  qf
		.selectFrom(QMigAvActrCmt.migAvActrCmt)
		.where(QMigAvActrCmt.migAvActrCmt.actorIdx.eq(L_ACTOR_IDX))
		.orderBy(QMigAvActrCmt.migAvActrCmt.cmtIdx.desc())
		.fetch();
	}
	
	public void crtMigAvActrCmt(
			long L_CMT_IDX
		    ,long L_ACTR_IDX
			, String CMT
			, String WRITER
			, Long L_LK_CNT
			, Long L_DSLK_CNT
			) {
				migAvActrCmtR.save(
			    MigAvActrCmt.builder()
				.cmtIdx(L_CMT_IDX)
				.actorIdx(L_ACTR_IDX)
				.cmt(CMT)
				.writer(WRITER)
				.lkCnt(L_LK_CNT)
				.dslkCnt(L_DSLK_CNT)
				.crtDtm(new Date()).build());
	}
}

