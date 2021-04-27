package com.example.demo.db.da.mig_av;

import java.util.Date;
import java.util.List;

import com.example.demo.db.domain.mig_av.MigAvMvCmt;
import com.example.demo.db.domain.mig_av.QMigAvMvCmt;
import com.example.demo.db.repository.mig_av.MigAvMvCmtRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DA_MIG_AV_MV_CMT {
	
	@Autowired
	JPAQueryFactory qf;
	
	
	@Autowired
	MigAvMvCmtRepository migAvMvCmtR;

	


	public List<MigAvMvCmt> findMigAvMvCmtByActorIdx(Long L_ACTOR_IDX) {
		return  qf
		.selectFrom(QMigAvMvCmt.migAvMvCmt)
		.where(QMigAvMvCmt.migAvMvCmt.actorIdx.eq(L_ACTOR_IDX))
		.orderBy(QMigAvMvCmt.migAvMvCmt.cmtIdx.desc())
		.fetch();
	}
	
	public void crtMigAvMvCmt(
			long L_CMT_IDX
			,long L_DVD_IDX
		    ,long L_ACTR_IDX
			, String CMT
			, String WRITER
			, Long L_LK_CNT
			, Long L_DSLK_CNT
			) {
				migAvMvCmtR.save(
			    MigAvMvCmt.builder()
				.cmtIdx(L_CMT_IDX)
				.dvdIdx(L_DVD_IDX)
				.actorIdx(L_ACTR_IDX)
				.cmt(CMT)
				.writer(WRITER)
				.lkCnt(L_LK_CNT)
				.dslkCnt(L_DSLK_CNT)
				.crtDtm(new Date()).build());
	}
}

