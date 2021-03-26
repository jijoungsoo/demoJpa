package com.example.demo.db.da.mig_av;

import java.util.Date;
import java.util.Optional;

import com.example.demo.db.domain.mig_av.MigAvMk;
import com.example.demo.db.domain.mig_av.QMigAvMk;
import com.example.demo.db.repository.mig_av.MigAvMkRepository;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DA_MIG_AV_MK {
	
	@Autowired
	JPAQueryFactory qf;
		
	@Autowired
	MigAvMkRepository migAvMkR;

	public Page<MigAvMk> findMigAvMk(Pageable p) {
		QueryResults<MigAvMk>  r= qf
		.selectFrom(QMigAvMk.migAvMk)
		.orderBy(QMigAvMk.migAvMk.mkId.desc())
		.offset(p.getOffset()) // offset과
		.limit(p.getPageSize()) // offset과
		.fetchResults(); 

		return new PageImpl<>(r.getResults(), p, r.getTotal());
	}

	public void crtMigAvMk(long L_MK_ID
	, String NM
	, String IMG
	, String IMG_L
	, String TTL
	) {
		migAvMkR.save(
				MigAvMk.builder()
				.mkId(L_MK_ID)
				.nm(NM)
				.img(IMG)
				.imgL(IMG_L)
				.ttl(TTL)
				.updtDtm(new Date())
				.crtDtm(new Date()).build());
	}
	
	public Optional<MigAvMk> findById(Long L_MK_ID){
		return migAvMkR.findById(L_MK_ID);
	}
}


