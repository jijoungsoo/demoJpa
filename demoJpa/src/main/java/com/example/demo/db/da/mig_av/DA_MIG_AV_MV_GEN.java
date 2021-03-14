package com.example.demo.db.da.mig_av;

import java.util.Date;
import java.util.Optional;

import com.example.demo.db.domain.mig_av.MigAvMvActr;
import com.example.demo.db.domain.mig_av.MigAvMvGen;
import com.example.demo.db.domain.mig_av.MigAvMvGenIdx;
import com.example.demo.db.repository.mig_av.MigAvMvGenRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DA_MIG_AV_MV_GEN {
	
	@Autowired
	JPAQueryFactory qf;
		
	@Autowired
	MigAvMvGenRepository migAvMvGenR;
	
	public void crtMigAvMvGen(Long L_MV_IDX
				,Long L_CATE_NO
				,Long L_MENU_NO
	) {
		
		migAvMvGenR.save(
				MigAvMvGen.builder()
				.dvdIdx(L_MV_IDX)
				.cateNo(L_CATE_NO)
				.menuNo(L_MENU_NO)
				.crtDtm(new Date()).build());
	}
	
	public Optional<MigAvMvGen> findById(MigAvMvGenIdx migAvMvGenIdx){
		return migAvMvGenR.findById(migAvMvGenIdx);
	}
}


