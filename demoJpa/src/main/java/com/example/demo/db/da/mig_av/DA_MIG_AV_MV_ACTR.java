package com.example.demo.db.da.mig_av;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import com.example.demo.db.domain.mig_av.MigAvMvActr;
import com.example.demo.db.domain.mig_av.MigAvMvActrIdx;
import com.example.demo.db.domain.mig_av.QMigAvMvActr;
import com.example.demo.db.repository.mig_av.MigAvMvActrRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DA_MIG_AV_MV_ACTR {
	
	@Autowired
	JPAQueryFactory qf;
		
	@Autowired
	MigAvMvActrRepository migAvMvActrR;
	
	public void crtMigAvMvActr(Long L_MV_IDX
				,Long L_ACTOR_IDX
	) {
		migAvMvActrR.save(
				MigAvMvActr.builder()
				.dvdIdx(L_MV_IDX)
				.actrIdx(L_ACTOR_IDX)
				.crtDtm(new Date()).build());
	}

	@Transactional 
	public void rmMigAvMvActr(Long L_MV_IDX) {
		qf.delete(QMigAvMvActr.migAvMvActr)
		.where(QMigAvMvActr.migAvMvActr.dvdIdx.eq(L_MV_IDX))
		.execute();
	}
	
	public Optional<MigAvMvActr> findById(MigAvMvActrIdx migAvMvActrIdx){
		return migAvMvActrR.findById(migAvMvActrIdx);
	}
}


