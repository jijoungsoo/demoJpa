package com.example.demo.db.da.mig_av;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.demo.db.domain.mig_av.MigAvMvGen;
import com.example.demo.db.domain.mig_av.MigAvMvGenIdx;
import com.example.demo.db.domain.mig_av.QMigAvGen;
import com.example.demo.db.domain.mig_av.QMigAvMv;
import com.example.demo.db.domain.mig_av.QMigAvMvGen;
import com.example.demo.db.repository.mig_av.MigAvMvGenRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;



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

	public List<Tuple> findGenByActrIdx(Long ACTOR_IDX,Long CATE_NO){

		
		BooleanBuilder builder = new BooleanBuilder();

		if (!ObjectUtils.isEmpty(ACTOR_IDX)) {
            builder.and(QMigAvMv.migAvMv.mnActrIdx.eq(ACTOR_IDX));
        }

		if (!ObjectUtils.isEmpty(CATE_NO)) {
			builder.and(QMigAvGen.migAvGen.cateNo.eq(CATE_NO));
        }


		List<Tuple>  al=qf.select(QMigAvGen.migAvGen.cateNo,
		QMigAvGen.migAvGen.cateNm,
		QMigAvGen.migAvGen.menuNo,
		QMigAvGen.migAvGen.count().as("cnt")
		)
		.from(QMigAvGen.migAvGen)
		.innerJoin(QMigAvMvGen.migAvMvGen)
		.on(QMigAvGen.migAvGen.cateNo.eq(QMigAvMvGen.migAvMvGen.cateNo))
		.on(QMigAvGen.migAvGen.menuNo.eq(QMigAvMvGen.migAvMvGen.menuNo))
		.innerJoin(QMigAvMv.migAvMv)
		.on(QMigAvMv.migAvMv.dvdIdx.eq(QMigAvMvGen.migAvMvGen.dvdIdx))
		.where(builder)
		.groupBy(QMigAvGen.migAvGen.cateNo,
		QMigAvGen.migAvGen.cateNm,
		QMigAvGen.migAvGen.menuNo)
		.orderBy(QMigAvGen.migAvGen.count().asc())
		.fetch();
		return al;
	}

	public List<Tuple> findGenByDvdIdx(Long DVD_IDX){
		List<Tuple>  al=qf.select(QMigAvGen.migAvGen.cateNo,
		QMigAvGen.migAvGen.cateNm,
		QMigAvGen.migAvGen.menuNo
		)
		.from(QMigAvGen.migAvGen)
		.innerJoin(QMigAvMvGen.migAvMvGen)
		.on(QMigAvGen.migAvGen.cateNo.eq(QMigAvMvGen.migAvMvGen.cateNo))
		.on(QMigAvGen.migAvGen.menuNo.eq(QMigAvMvGen.migAvMvGen.menuNo))
		.where(QMigAvMvGen.migAvMvGen.dvdIdx.eq(DVD_IDX))
		.orderBy(QMigAvGen.migAvGen.cateNm.asc())
		.fetch();
		return al;
	}
}


