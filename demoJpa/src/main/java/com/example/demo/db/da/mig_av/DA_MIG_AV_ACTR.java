package com.example.demo.db.da.mig_av;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.demo.db.domain.mig_av.MigAvActr;
import com.example.demo.db.domain.mig_av.QMigAvActr;
import com.example.demo.db.repository.mig_av.MigAvActrRepository;
import com.example.demo.exception.BizException;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DA_MIG_AV_ACTR {
	
	@Autowired
	JPAQueryFactory qf;
	
	
	@Autowired
	MigAvActrRepository migAvActrR;


	public List<MigAvActr> findMigAvActr() {
		List<MigAvActr> al = qf
		.selectFrom(QMigAvActr.migAvActr)
		.orderBy(QMigAvActr.migAvActr.actrIdx.desc())
		.fetch();
		 return al;
	}
	
	public void crtMigAvActr(long L_ACTR_IDX
			, String IMG_S
			, String DEBUT_DT
			, String NM_KR
			) {
				migAvActrR.save(
				MigAvActr.builder()
				.actrIdx(L_ACTR_IDX)
				.imgS(IMG_S)
				.debutDt(DEBUT_DT)
				.nmKr(NM_KR)
				.sync("N")
				.updtDtm(new Date())
				.crtDtm(new Date()).build());
	}
	public void updtMigAvActrBirth(long L_ACTR_IDX
	, String BRTH
	, String BRA_SIZE
	) throws BizException {
		Optional<MigAvActr> c = migAvActrR.findById(L_ACTR_IDX);
		if(c==null) {
			throw new BizException("["+L_ACTR_IDX+"] 배우가  존재하지 않습니다.[수정X]");
		}
		MigAvActr tmp = c.get();
		tmp.setBrSize(BRA_SIZE);
		tmp.setBrth(BRTH);
		tmp.setSync("Y");
		tmp.setUpdtDtm(new Date());
		migAvActrR.save(tmp);
	}

	
	

	public void updtMigAvActr(long L_ACTR_IDX
			, String NM_EN
			, String NM_CN
			, String IMG
			, String BRTH
			, String HEIGHT
			, String SIZE
			, String BR_SIZE
			, String ACTOR_ONM
			, String ACTOR_DSCR_TITLE
			, String ACTOR_DSCR_DSCR
			) throws BizException {
		
		Optional<MigAvActr> c = migAvActrR.findById(L_ACTR_IDX);
		if(c==null) {
			throw new BizException("["+L_ACTR_IDX+"] 배우가  존재하지 않습니다.[수정X]");
		}
		MigAvActr tmp = c.get();
		tmp.setNmEn(NM_EN);
		tmp.setNmCn(NM_CN);
		tmp.setImg(IMG);
		tmp.setBrth(BRTH);
		tmp.setHeight(HEIGHT);
		tmp.setSize(SIZE);
		tmp.setBrSize(BR_SIZE);
		tmp.setONm(ACTOR_ONM);
		tmp.setDscrTtl(ACTOR_DSCR_TITLE);
		tmp.setDscr(ACTOR_DSCR_DSCR);		
		tmp.setSync("Y");
		tmp.setUpdtDtm(new Date());
		migAvActrR.save(tmp);
	}



	
	public Optional<MigAvActr> findById(long l_ACTR_IDX){
		return migAvActrR.findById(l_ACTR_IDX);
	}

	public List<MigAvActr> findByDebutYYYYMM(String YYYYMM) {
		List<MigAvActr> al = qf
		.selectFrom(QMigAvActr.migAvActr)
		.where(QMigAvActr.migAvActr.debutDt.startsWith(YYYYMM))
		.orderBy(QMigAvActr.migAvActr.debutDt.asc())
		.fetch();
		 return al;
	}

	public List<MigAvActr> findBySyncB() {
		List<MigAvActr> al = qf
		.selectFrom(QMigAvActr.migAvActr)
		.where(QMigAvActr.migAvActr.sync.eq("B"))
		.orderBy(QMigAvActr.migAvActr.actrIdx.asc())
		.fetch();
		 return al;
	}

	public List<MigAvActr> findBySyncN() {
		List<MigAvActr> al = qf
		.selectFrom(QMigAvActr.migAvActr)
		.where(QMigAvActr.migAvActr.sync.eq("N"))
		.orderBy(QMigAvActr.migAvActr.actrIdx.asc())
		.fetch();
		 return al;
	}
}

