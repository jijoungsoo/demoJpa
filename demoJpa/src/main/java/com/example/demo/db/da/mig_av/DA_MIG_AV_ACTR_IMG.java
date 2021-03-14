package com.example.demo.db.da.mig_av;

import java.util.Date;
import java.util.Optional;

import com.example.demo.db.domain.mig_av.MigAvActrImg;
import com.example.demo.db.repository.mig_av.MigAvActrImgRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DA_MIG_AV_ACTR_IMG {
	
	@Autowired
	JPAQueryFactory qf;
	
	
	@Autowired
	MigAvActrImgRepository migAvActrImgR;
	
	public void crtMigAvActrImg(String IMG
			, Long L_ACTR_IDX
			) {
				migAvActrImgR.save(
				MigAvActrImg.builder()
				.actrIdx(L_ACTR_IDX)
				.img(IMG)
				.crtDtm(new Date()).build());
	}
	
	public Optional<MigAvActrImg> findById(String IMG){
		return migAvActrImgR.findById(IMG);
	}
}

