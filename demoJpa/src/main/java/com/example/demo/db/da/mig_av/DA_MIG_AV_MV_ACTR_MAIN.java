package com.example.demo.db.da.mig_av;

import java.util.Date;
import java.util.Optional;

import com.example.demo.db.domain.mig_av.MigAvMvActr;
import com.example.demo.db.domain.mig_av.MigAvMvActrMain;
import com.example.demo.db.domain.mig_av.MigAvMvActrMainIdx;
import com.example.demo.db.repository.mig_av.MigAvMvActrMainRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DA_MIG_AV_MV_ACTR_MAIN {
	
	@Autowired
	JPAQueryFactory qf;
		
	@Autowired
	MigAvMvActrMainRepository migAvMvActrMainR;
	
	public void crtMigAvMvActrMain(Long L_MV_IDX
				,Long L_ACTOR_IDX
	) {
		
		migAvMvActrMainR.save(
				MigAvMvActrMain.builder()
				.dvdIdx(L_MV_IDX)
				.actrIdx(L_ACTOR_IDX)
				.crtDtm(new Date()).build());
	}
	
	public Optional<MigAvMvActrMain> findById(MigAvMvActrMainIdx migAvMvActrMainIdx){
		return migAvMvActrMainR.findById(migAvMvActrMainIdx);
	}
}


