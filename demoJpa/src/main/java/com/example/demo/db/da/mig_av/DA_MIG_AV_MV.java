package com.example.demo.db.da.mig_av;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.demo.db.domain.mig_av.MigAvMv;
import com.example.demo.db.domain.mig_av.QMigAvActr;
import com.example.demo.db.domain.mig_av.QMigAvActrCmt;
import com.example.demo.db.domain.mig_av.QMigAvMv;
import com.example.demo.db.domain.mig_av.QMigAvMvActrMain;
import com.example.demo.db.domain.mig_av.QMigAvMvCmt;
import com.example.demo.db.domain.mig_av.QMigAvMvGen;
import com.example.demo.db.repository.mig_av.MigAvMvRepository;
import com.example.demo.exception.BizException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class DA_MIG_AV_MV {
	
	@Autowired
	JPAQueryFactory qf;
		
	@Autowired
	MigAvMvRepository migAvMvR;

	public Page<Tuple> findMigAvMv(Pageable p,String SEARCH_AGE, String SEARCH_NM) {

		StringExpression age_sub =Expressions.stringTemplate("CAST( (cast(substring(to_char(now(), 'YYYYMMDD'),0,5) as integer)-cast(substring({0},0,5) as  integer)+1 ) as  text)",QMigAvActr.migAvActr.brth); //2020만 남음
		StringExpression age =new CaseBuilder()
		.when(QMigAvActr.migAvActr.brth.between("19000101", "23001231"))
		.then(age_sub)
		.otherwise(QMigAvActr.migAvActr.brth);

		StringPath age_alis = Expressions.stringPath("age");
		
		
		BooleanBuilder builder = new BooleanBuilder();

		if (!ObjectUtils.isEmpty(SEARCH_AGE)) {
            builder.and(age.eq(SEARCH_AGE));
        }

		if (!ObjectUtils.isEmpty(SEARCH_NM)) {
			BooleanBuilder tmp = new BooleanBuilder();
            tmp.or(QMigAvMv.migAvMv.mvNm.containsIgnoreCase(SEARCH_NM));
			tmp.or(QMigAvMv.migAvMv.ttlKr.containsIgnoreCase(SEARCH_NM));
			tmp.or(QMigAvMv.migAvMv.series.containsIgnoreCase(SEARCH_NM));
			tmp.or(QMigAvMv.migAvMv.drctr.containsIgnoreCase(SEARCH_NM));
			tmp.or(QMigAvMv.migAvMv.stryKr.containsIgnoreCase(SEARCH_NM));
			tmp.or(QMigAvMv.migAvMv.actrNm.containsIgnoreCase(SEARCH_NM));
			tmp.or(QMigAvMv.migAvMv.dvdIdx.like("%"+SEARCH_NM+"%"));
			builder.and(tmp);
        }

		QueryResults<Tuple>  r= qf
		.select(QMigAvMv.migAvMv.dvdIdx,
		QMigAvMv.migAvMv.delYn,
		QMigAvMv.migAvMv.imgAs,
		QMigAvMv.migAvMv.imgNs,
		QMigAvMv.migAvMv.mvNm,
		QMigAvMv.migAvMv.ttlKr,
		QMigAvMv.migAvMv.mvNm,
		QMigAvMv.migAvMv.mnActrIdx,
		QMigAvMv.migAvMv.openDt,
		QMigAvMv.migAvMv.cmpNm,
		QMigAvMv.migAvMv.lbl,
		QMigAvMv.migAvMv.sync,
		QMigAvMv.migAvMv.bestYn,
		QMigAvMv.migAvMv.series,
		QMigAvMv.migAvMv.drctr,
		QMigAvMv.migAvMv.rnTm,
		QMigAvMv.migAvMv.stryKr,
		QMigAvMv.migAvMv.genLst,
		QMigAvMv.migAvMv.crtDtm,
		QMigAvActr.migAvActr.nmKr,
		age.as(age_alis),
		ExpressionUtils.as(				
		JPAExpressions.select(QMigAvMv.migAvMv.dvdIdx.count())
				.from(QMigAvMv.migAvMv)
				.where(QMigAvMv.migAvMv.mnActrIdx.eq(QMigAvActr.migAvActr.actrIdx)),
		"dvd_cnt"),
		ExpressionUtils.as(				
		JPAExpressions.select(QMigAvActrCmt.migAvActrCmt.lkCnt.sum())
				.from(QMigAvActrCmt.migAvActrCmt)
				.where(QMigAvActrCmt.migAvActrCmt.actorIdx.eq(QMigAvActr.migAvActr.actrIdx)),
		"actor_lk_cnt"),
		ExpressionUtils.as(				
		JPAExpressions.select(QMigAvActrCmt.migAvActrCmt.dslkCnt.sum())
				.from(QMigAvActrCmt.migAvActrCmt)
				.where(QMigAvActrCmt.migAvActrCmt.actorIdx.eq(QMigAvActr.migAvActr.actrIdx)),
		"actor_dslk_cnt"),
		ExpressionUtils.as(				
		JPAExpressions.select(QMigAvActrCmt.migAvActrCmt.count())
				.from(QMigAvActrCmt.migAvActrCmt)
				.where(QMigAvActrCmt.migAvActrCmt.actorIdx.eq(QMigAvActr.migAvActr.actrIdx)),
		"actor_cmt_cnt"),
		ExpressionUtils.as(				
		JPAExpressions.select(QMigAvMvCmt.migAvMvCmt.count())
				.from(QMigAvMvCmt.migAvMvCmt)
				.where(QMigAvMvCmt.migAvMvCmt.dvdIdx.eq(QMigAvMv.migAvMv.dvdIdx)),
		"mv_cmt_cnt")
		)		
		.from(QMigAvMv.migAvMv)
		.leftJoin(QMigAvActr.migAvActr)
		.on(QMigAvMv.migAvMv.mnActrIdx.eq(QMigAvActr.migAvActr.actrIdx))
		.where(builder)
		.orderBy(QMigAvMv.migAvMv.dvdIdx.asc())
		.offset(p.getOffset()) // offset과
		.limit(p.getPageSize()) // offset과
		.fetchResults(); 

		return new PageImpl<>(r.getResults(), p, r.getTotal());
	}

	public List<MigAvMv> findMigAvMvN() {
		List<MigAvMv> al = qf
		.selectFrom(QMigAvMv.migAvMv)
		.where(QMigAvMv.migAvMv.sync.eq("N"))
		.orderBy(QMigAvMv.migAvMv.dvdIdx.asc())
		.fetch();
		 return al;
	}

	public List<MigAvMv> findMigAvBestMvByActorIdx(Long ACTOR_IDX) {
		List<MigAvMv> al = qf
		.selectFrom(QMigAvMv.migAvMv)
		.where(QMigAvMv.migAvMv.mnActrIdx.eq(ACTOR_IDX))
		.where(QMigAvMv.migAvMv.bestYn.eq("Y"))
		.orderBy(QMigAvMv.migAvMv.dvdIdx.desc())
		.fetch();
		 return al;
	}

	
	public Long getMaxDvdIdxByActorIdx(Long ACTOR_IDX) {

		List<Long> al = qf
		.select(QMigAvMv.migAvMv.dvdIdx.max())
		.from(QMigAvMv.migAvMv)
		.where(QMigAvMv.migAvMv.mnActrIdx.eq(ACTOR_IDX))
		.fetch();

		Long rtn =0L;
		if(al.size()>0){
			rtn = al.get(0);
		}

		 return rtn;
	}



	public List<MigAvMv> findMigAvMvByActorIdx(Long ACTOR_IDX,Long L_CATE_NO) {
		
		BooleanBuilder builder = new BooleanBuilder();

		if (!ObjectUtils.isEmpty(ACTOR_IDX)) {
            builder.and(QMigAvMvActrMain.migAvMvActrMain.actrIdx.eq(ACTOR_IDX));
        }

		if (!ObjectUtils.isEmpty(L_CATE_NO)) {
			builder.and(JPAExpressions.selectOne()
            .from(QMigAvMvGen.migAvMvGen)
            .where(QMigAvMvGen.migAvMvGen.dvdIdx.eq(QMigAvMv.migAvMv.dvdIdx))
			.where(QMigAvMvGen.migAvMvGen.cateNo.eq(L_CATE_NO))
            .exists());			
        }



		List<MigAvMv> al = qf
		.selectFrom(QMigAvMv.migAvMv)
		.innerJoin(QMigAvMvActrMain.migAvMvActrMain)
		.on(QMigAvMv.migAvMv.dvdIdx.eq(QMigAvMvActrMain.migAvMvActrMain.dvdIdx))
		.where(builder)
		.orderBy(QMigAvMv.migAvMv.dvdIdx.desc())
		.fetch();
		 return al;
	}


	public void updtMigAvMv(long L_DVD_IDX
	, String MV_NM
	, String TITLE_KR
	, Long MAIN_ACTR_IDX
	, String OPEN_DT
	, String ACTR_NM
	, String COMP_NM
	, String LABEL
	, String SERIES
	, String DIRECTOR
	, String RUN_TIME
	, String STORY_KR
	) throws BizException {
		Optional<MigAvMv> c = migAvMvR.findById(L_DVD_IDX);
		if(c.isPresent()){
			MigAvMv tmp = c.get();
			tmp.setMvNm(MV_NM);
			tmp.setTtlKr(TITLE_KR);
			tmp.setMnActrIdx(MAIN_ACTR_IDX);
			tmp.setOpenDt(OPEN_DT);
			tmp.setActrNm(ACTR_NM);
			tmp.setCmpNm(COMP_NM);
			tmp.setLbl(LABEL);
			tmp.setSeries(SERIES);
			tmp.setDrctr(DIRECTOR);
			tmp.setRnTm(RUN_TIME);
			tmp.setStryKr(STORY_KR);
			tmp.setSync("Y");
			tmp.setUpdtDtm(new Date());
			migAvMvR.save(tmp);

		}
		
	}

	
	public void updtMigAvMvExtra(long L_DVD_IDX
	, String FILE_PATH
	, String DEL_YN
	, String RMK
	) throws BizException {
		Optional<MigAvMv> c = migAvMvR.findById(L_DVD_IDX);
		if(c.isPresent()){
			MigAvMv tmp = c.get();
			tmp.setFilePath(FILE_PATH);
			tmp.setDelYn(DEL_YN);	
			tmp.setRmk(RMK);	
			tmp.setUpdtDtm(new Date());
			migAvMvR.save(tmp);
		}
		
	}

	public void updtMigAvMvSync(long L_DVD_IDX,String SYNC_YN) throws BizException {
		Optional<MigAvMv> c = migAvMvR.findById(L_DVD_IDX);
		if(c.isPresent()){
			MigAvMv tmp = c.get();
			tmp.setSync(SYNC_YN);
			tmp.setUpdtDtm(new Date());
			migAvMvR.save(tmp);
		}
		
	}



	public void updtMigAvMvImg(long L_DVD_IDX
		,String MV_NM
		,String TTL_KR
		,String SAMPLE_YN
		,String IMG_A
		,String IMG_AS
		,String IMG_N
		,String IMG_NS
		,String IMG_LA
		,String IMG_LAS
		,String IMG_LN
		,String IMG_LNS
	) throws BizException {
		Optional<MigAvMv> c = migAvMvR.findById(L_DVD_IDX);
		if(c.isPresent()){
			MigAvMv tmp = c.get();
			tmp.setMvNm(MV_NM);
			tmp.setTtlKr(TTL_KR);
			tmp.setSampleYn(SAMPLE_YN);
			tmp.setImgA(IMG_A);
			tmp.setImgAs(IMG_AS);
			tmp.setImgN(IMG_N);
			tmp.setImgNs(IMG_NS);
			tmp.setImgLA(IMG_LA);
			tmp.setImgLAs(IMG_LAS);
			tmp.setImgLN(IMG_LN);
			tmp.setImgLNs(IMG_LNS);
			tmp.setUpdtDtm(new Date());
			migAvMvR.save(tmp);
		}
	}
	
	public void crtMigAvMv(Long L_DVD_IDX
				,Long L_MN_ACTOR_IDX
				,String MV_NM
				,String TTL_KR
				,String SAMPLE_YN
				,String IMG_A
				,String IMG_AS
				,String IMG_N
				,String IMG_NS
				,String IMG_LA
				,String IMG_LAS
				,String IMG_LN
				,String IMG_LNS
	) {
		migAvMvR.save(
				MigAvMv.builder()
				.dvdIdx(L_DVD_IDX)
				.mnActrIdx(L_MN_ACTOR_IDX)
				.mvNm(MV_NM)
				.ttlKr(TTL_KR)
				.bestYn("N")
				.sync("N")
				.sampleYn(SAMPLE_YN)
				.imgA(IMG_A)
				.imgAs(IMG_AS)
				.imgN(IMG_N)
				.imgNs(IMG_NS)
				.imgLA(IMG_LA)
				.imgLAs(IMG_LAS)
				.imgLN(IMG_LN)
				.imgLNs(IMG_LNS)
				.updtDtm(new Date())
				.crtDtm(new Date()).build());
	}

	public void setBestYn(Long L_DVD_IDX
				,Long L_MN_ACTOR_IDX
	) {
		Optional<MigAvMv> c = findById(L_DVD_IDX);
		if(c.isPresent()){
			MigAvMv tmp = c.get();
			tmp.setMnActrIdx(L_MN_ACTOR_IDX);
			tmp.setBestYn("Y");
			tmp.setUpdtDtm(new Date());
			migAvMvR.save(tmp);	
		}
		
	}
	
	public Optional<MigAvMv> findById(Long L_DVD_IDX){
		return migAvMvR.findById(L_DVD_IDX);
	}
}


