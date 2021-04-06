package com.example.demo.db.da.mig_av;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.demo.db.domain.mig_av.MigAvActr;
import com.example.demo.db.domain.mig_av.QMigAvActr;
import com.example.demo.db.domain.mig_av.QMigAvActrCmt;
import com.example.demo.db.domain.mig_av.QMigAvMv;
import com.example.demo.db.repository.mig_av.MigAvActrRepository;
import com.example.demo.exception.BizException;
import com.example.demo.utils.PjtUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class DA_MIG_AV_ACTR {

	@Autowired
    PjtUtil pjtU;
	
	@Autowired
	JPAQueryFactory qf;
	
	
	@Autowired
	MigAvActrRepository migAvActrR;


	public MigAvActr findMigAvActrById(Long L_ACTOR_IDX) {
		MigAvActr c = qf
		.selectFrom(QMigAvActr.migAvActr)
		.where(QMigAvActr.migAvActr.actrIdx.eq(L_ACTOR_IDX))
		.fetchOne();
		return  c;
	}
	public List<String> findActrAge() {
		StringExpression age =Expressions.stringTemplate("CAST( (cast(substring(to_char(now(), 'YYYYMMDD'),0,5) as integer)-cast(substring({0},0,5) as  integer)+1 ) as  text)",QMigAvActr.migAvActr.brth); //2020만 남음		

		StringPath age2 = Expressions.stringPath("age");



		List<String> al=qf
		.select(new CaseBuilder()
		.when(QMigAvActr.migAvActr.brth.between("19000101", "23001231"))
		.then(age)
		.otherwise(QMigAvActr.migAvActr.brth).as(age2)
		)
		.distinct()
		.from(QMigAvActr.migAvActr)	
		.orderBy(age2.asc())
		.fetch();
		return al;
	}


	public Page<Tuple> findMigAvActr(String SEARCH_AGE,String SEARCH_NM, Pageable p) {
		//StringTemplate age = Expressions.stringTemplate("(SUBSTR(to_char(now(), 'YYYYMMDD'),0,5)::INTEGER-SUBSTR({0},0,5)::inTEGER)", QMigAvActr.migAvActr.brth); 
		//결국엔  ::integer가 먹지 않아서    cast로 변경하니 되었다.

		//StringTemplate now = Expressions.stringTemplate("'aaa'");    ---aaa 가 age 컬럼으로 잘 나타남

		//StringTemplate now = Expressions.stringTemplate("now()");    --날짜가 찍힐것으로 생각했으나 오류가 나타난다
		//DateTemplate now = Expressions.dateTemplate(Date.class, "now()");  정상적으로 잘된다. -- 다만 밖에서 담을때 날짜형으로 담아야한다.
		//StringTemplate now =Expressions.stringTemplate("to_char(now(), 'YYYYMMDD')");  정상동작 O
		//StringTemplate now =Expressions.stringTemplate("(SUBSTR(to_char(now(), 'YYYYMMDD'),0,5)");  //오류 남 여러개를 사용하면될지 알았는데 안되었다.
		//StringExpression now =Expressions.stringTemplate("to_char(now(), 'YYYYMMDD')").substring(0,4); //2020만 남음
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
            tmp.or(QMigAvActr.migAvActr.nmKr.contains(SEARCH_NM));
			tmp.or(QMigAvActr.migAvActr.nmEn.contains(SEARCH_NM));
			builder.and(tmp);
	//		builder.and(QMigAvActr.migAvActr.nmCn.contains(SEARCH_NM));
        }

		JPAQuery<Tuple>  q=qf
		.select(QMigAvActr.migAvActr.actrIdx,
			QMigAvActr.migAvActr.img,
			QMigAvActr.migAvActr.imgS,
			QMigAvActr.migAvActr.debutDt,
			QMigAvActr.migAvActr.nmKr,
			QMigAvActr.migAvActr.nmEn,
			QMigAvActr.migAvActr.nmCn,
			QMigAvActr.migAvActr.brth,
			QMigAvActr.migAvActr.height,
			QMigAvActr.migAvActr.size,
			QMigAvActr.migAvActr.brSize,
			QMigAvActr.migAvActr.oNm,
			QMigAvActr.migAvActr.dscrTtl,
			QMigAvActr.migAvActr.dscr,
			QMigAvActr.migAvActr.sync,
			QMigAvActr.migAvActr.crtDtm,
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
			JPAExpressions.select(QMigAvMv.migAvMv.dvdIdx.count())
					.from(QMigAvMv.migAvMv)
					.where(QMigAvMv.migAvMv.mnActrIdx.eq(QMigAvActr.migAvActr.actrIdx))
					.where(QMigAvMv.migAvMv.bestYn.eq("Y")),
			"best_dvd_cnt"))		
		.from(QMigAvActr.migAvActr)
		.where(builder);
/*
		if(!PjtUtil.isEmpty(SEARCH_AGE)){
			q = q.where(age.eq(SEARCH_AGE));
		}
		*/

		//QueryResults<Tuple>  r= q.where(o)

		QueryResults<Tuple>  r=q.orderBy(QMigAvActr.migAvActr.actrIdx.desc())
		.offset(p.getOffset()) // offset과
		.limit(p.getPageSize()) // offset과
		.fetchResults(); 

		 return new PageImpl<>(r.getResults(), p, r.getTotal());
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

	public void updtMigAvActrImgL(long L_ACTR_IDX
	, String IMG_L
	) throws BizException {
		Optional<MigAvActr> c = migAvActrR.findById(L_ACTR_IDX);
		if(c==null) {
			throw new BizException("["+L_ACTR_IDX+"] 배우가  존재하지 않습니다.[수정X]");
		}
		MigAvActr tmp = c.get();
		tmp.setImgL(IMG_L);
		tmp.setUpdtDtm(new Date());
		migAvActrR.save(tmp);
	}

	public void updtMigAvActrImgLS(long L_ACTR_IDX
	, String IMG_LS
	) throws BizException {
		Optional<MigAvActr> c = migAvActrR.findById(L_ACTR_IDX);
		if(c==null) {
			throw new BizException("["+L_ACTR_IDX+"] 배우가  존재하지 않습니다.[수정X]");
		}
		MigAvActr tmp = c.get();
		tmp.setImgLs(IMG_LS);
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

