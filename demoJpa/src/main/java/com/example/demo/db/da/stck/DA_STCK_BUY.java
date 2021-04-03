package com.example.demo.db.da.stck;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import com.example.demo.db.domain.marcap.QStckMarcap;
import com.example.demo.db.domain.stck.QStBuy;
import com.example.demo.db.domain.stck.StBuy;
import com.example.demo.db.repository.stck.StBuyRepository;
import com.example.demo.exception.BizException;
import com.querydsl.core.JoinFlag.Position;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.sql.JPASQLQuery;
import com.querydsl.sql.Configuration;
import com.querydsl.sql.SQLTemplates;
import com.querydsl.sql.spring.SpringExceptionTranslator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class DA_STCK_BUY {
	
	@Autowired
	JPAQueryFactory qf;

	@Autowired
    private EntityManager em;
	
	@Autowired
	StBuyRepository stBuyR;

	public Page<Tuple> findStckBuy(Pageable p) {

		/*
		https://www.inflearn.com/questions/14139
		QueryDSL은 결국 JPQL로 실행되기 때문에, JPQL의 제약에 묶이게 됩니다.
		현재 하이버네이트 구현체는 FROM이나 JOIN 절에서 서브쿼리를 만들 수 없습니다.(SELECT, WHERE 절은 가능합니다.)
		그래서 아쉽게도 작성해주신 SQL은 JOIN 절에 서브쿼리가 들어가기 때문에 JPQL이나 QueryDSL로 작성하는 것이 불가능합니다.
		해결방안은 다음과 같습니다.
		1. 쿼리를 2번 나누어 실행한다. (서브쿼리 부분을 별도의 JPQL이나 QueryDSL로 실행하고 결과를 받아서, 그 결과를 파라미터로 넘기는 JPQL이나 QueryDSL을 다시 실행합니다. 예를 들어서 처음 쿼리로 [2019, 11], [2020,20]이라는 결과를 받고, 그 다음에 이 값을 파라미터로 넘기는 쿼리를 실행해서 최종 결과를 받습니다.)
		2. 네이티브 쿼리를 사용한다.
		3. 다음과 같이 다른 방법으로 푼다. (WHERE 절에서는 서브쿼리가 가능하므로)
		select name, value from test_entity
		where concat(year, value) in (
		 select concat(year, max(value))
		 from test_entity
		  group by year
		);
		그런데 이 경우 where in 절에 파라미터를 2개 동시에 넘길 수 없어서, 어쩔 수 없이 문자를 더했습니다. 결국 인덱스를 사용할 수 없기 때문에 데이터가 많은 경우 성능에 치명적입니다. 그래서 네이티브 쿼리를 직접 사용하거나, QueryDSL이나 JPQL을 사용하면 쿼리를 2번 나누어 실행하는 방법중 하나를 선택하시는 것을 권장합니다.
		참고로 MULTIPLE COLUMN SUBQUERY를 지원하는 데이터베이스의 경우 where (year, value) in 처럼 in 절 파라미터를 concat 하지 않고도 여러게 동시에 매칭할 수 있는데, 데이터베이스에 따라 지원 여부가 다르므로, 이 방법으로 풀려면 네이티브 쿼리로 푸는 것이 맞습니다.
		혹시 더 좋은 방법을 발견하시면 저에게도 알려주세요^^
		감사합니다.
		*/
		/*
		left outer join 을 줄수없으니까.
		selcect 의 하위 쿼리로 줘야한다.		
		*/

		//웨에는 안된다고 했는데 이건 썼다.
		//https://stackoverflow.com/questions/55580301/querydsl-sql-left-join-a-subquery

	
		/*
		https://www.inflearn.com/questions/14139
		QueryDSL은 결국 JPQL로 실행되기 때문에, JPQL의 제약에 묶이게 됩니다.
		현재 하이버네이트 구현체는 FROM이나 JOIN 절에서 서브쿼리를 만들 수 없습니다.(SELECT, WHERE 절은 가능합니다.)
		그래서 아쉽게도 작성해주신 SQL은 JOIN 절에 서브쿼리가 들어가기 때문에 JPQL이나 QueryDSL로 작성하는 것이 불가능합니다.
		해결방안은 다음과 같습니다.
		1. 쿼리를 2번 나누어 실행한다. (서브쿼리 부분을 별도의 JPQL이나 QueryDSL로 실행하고 결과를 받아서, 그 결과를 파라미터로 넘기는 JPQL이나 QueryDSL을 다시 실행합니다. 예를 들어서 처음 쿼리로 [2019, 11], [2020,20]이라는 결과를 받고, 그 다음에 이 값을 파라미터로 넘기는 쿼리를 실행해서 최종 결과를 받습니다.)
		2. 네이티브 쿼리를 사용한다.
		3. 다음과 같이 다른 방법으로 푼다. (WHERE 절에서는 서브쿼리가 가능하므로)
		select name, value from test_entity
		where concat(year, value) in (
		 select concat(year, max(value))
		 from test_entity
		  group by year
		);
		그런데 이 경우 where in 절에 파라미터를 2개 동시에 넘길 수 없어서, 어쩔 수 없이 문자를 더했습니다. 결국 인덱스를 사용할 수 없기 때문에 데이터가 많은 경우 성능에 치명적입니다. 그래서 네이티브 쿼리를 직접 사용하거나, QueryDSL이나 JPQL을 사용하면 쿼리를 2번 나누어 실행하는 방법중 하나를 선택하시는 것을 권장합니다.
		참고로 MULTIPLE COLUMN SUBQUERY를 지원하는 데이터베이스의 경우 where (year, value) in 처럼 in 절 파라미터를 concat 하지 않고도 여러게 동시에 매칭할 수 있는데, 데이터베이스에 따라 지원 여부가 다르므로, 이 방법으로 풀려면 네이티브 쿼리로 푸는 것이 맞습니다.
		혹시 더 좋은 방법을 발견하시면 저에게도 알려주세요^^
		감사합니다.
		*/
		/*
		left outer join 을 줄수없으니까.
		selcect 의 하위 쿼리로 줘야한다.		
		*/


		//이건 설정하는법  QuerySQL 이 거 꼭 설정해야한다.
		///https://adunhansa.tistory.com/225
		///http://www.querydsl.com/static/querydsl/latest/reference/html/ch02s03.html#d0e1067

		//웨에는 안된다고 했는데 이건 썼다.
		//https://stackoverflow.com/questions/55580301/querydsl-sql-left-join-a-subquery


		//이게 JPAQuery, JPASqlQuery,  SqlQuery의 차이를 잘 설명하고 있다.
		//https://fordeveloper2.tistory.com/9310

		SQLTemplates templates = com.querydsl.sql.PostgreSQLTemplates.builder().build();
		JPASQLQuery<?> query = new JPASQLQuery<Void>(em, templates);
	
		
		Expression<String> STOCK_CD = ExpressionUtils.as(QStckMarcap.stckMarcap.stockCd, "stock_cd");
		Expression<String> MAX_STOCK_DT = ExpressionUtils.as(QStckMarcap.stckMarcap.stockDt.max(), "max_stock_dt");


		QStBuy  t = QStBuy.stBuy;


		Long TOTAL_CNT  = query.select(QStBuy.stBuy.count())
		.from(QStBuy.stBuy)                
		.where(QStBuy.stBuy.delYn.eq("N"))
		.fetchOne();
		System.out.println("total_cnt=>"+TOTAL_CNT);

		StringPath mm = Expressions.stringPath("bb");
		JPASQLQuery<?> query2 = new JPASQLQuery<Void>(em, templates);
		JPASQLQuery<?> query3 = new JPASQLQuery<Void>(em, templates);		
		JPASQLQuery<Tuple> payoutsBbf  = 
		query3.select(
			Expressions.stringPath(mm,"stock_cd")
			,Expressions.stringPath(mm,"max_stock_dt")
			,QStckMarcap.stckMarcap.clsAmt.as("cls_amt")
		)		
		.from(
				query2.select(
					STOCK_CD
					,MAX_STOCK_DT
					)
					.from(QStckMarcap.stckMarcap)                
					.groupBy(QStckMarcap.stckMarcap.stockCd)
				
		,mm
		)
		.innerJoin(QStckMarcap.stckMarcap)
		.addJoinFlag(" on stckMarcap.stock_cd = bb.stock_cd", Position.BEFORE_CONDITION)
		.addJoinFlag(" and stckMarcap.stock_dt = bb.max_stock_dt", Position.BEFORE_CONDITION)
		;


		JPASQLQuery<?> query4 = new JPASQLQuery<Void>(em, templates);
		StringPath cc = Expressions.stringPath("cc");
		JPASQLQuery<Tuple>  c= query4.select(
				t.amt,
				t.balCnt,
				t.buyDate,
				t.buySeq,
				t.cnt,
				t.crtDtm,
				t.crtUsrNo,
				t.fee,
				t.rmk,
				t.stockCd,
				t.stockNm,
				t.totAmt,
				t.updtDtm,
				t.updtUsrNo,
				Expressions.stringPath(cc,"cls_amt").as("cls_amt")
				)
				.from(t)
				.leftJoin(payoutsBbf,cc)
				.addJoinFlag(" on cc.stock_cd = stBuy.stock_cd", Position.BEFORE_CONDITION)
				.where(t.delYn.eq("N"));
		c= c.offset(p.getOffset()); // offset과
		c= c.limit(p.getPageSize()); // Limit 을 지정할 수 있고
		c= c.orderBy(t.buyDate.asc(),t.buySeq.asc());
		List<Tuple> result= c.fetch();
		//QueryResult는 전체 카운트 구하는데 에러가 발생됨.
		//그래서 page 부분은 별도로 나눴음.
		return new PageImpl<>(result, p, TOTAL_CNT);

	}
	
	
	public Configuration querydslConfiguration() {		 
		SQLTemplates templates = com.querydsl.sql.PostgreSQLTemplates.builder().build();
		Configuration configuration = new Configuration(templates);
		configuration.setExceptionTranslator(new SpringExceptionTranslator());
		return configuration;
	}


	public void createStckBuy(long L_BUY_SEQ
			, String STOCK_CD
			, String STOCK_NM
			, Integer I_AMT
			, Integer I_CNT
			, Integer I_BAL_CNT			
			, Integer I_FEE
			, Integer I_TOT_AMT
			, String BUY_DATE
			, long L_CRT_USR_NO
			, long L_UPDT_USR_NO
			) {
		// TODO Auto-generated method stub
		
		stBuyR.save(
				StBuy.builder()
				.buySeq(L_BUY_SEQ)
				.stockCd(STOCK_CD)
				.stockNm(STOCK_NM)
				.amt(I_AMT)
				.cnt(I_CNT)
				.balCnt(I_BAL_CNT)				
				.fee(I_FEE)
				.totAmt(I_TOT_AMT)
				.buyDate(BUY_DATE)
				.delYn("N")
				.crtUsrNo(L_CRT_USR_NO)
				.updtUsrNo(L_UPDT_USR_NO)
				.updtDtm(new Date())
				.crtDtm(new Date()).build());
	}
	

	public void updateStckBuy(long L_BUY_SEQ
			, String STOCK_CD
			, String STOCK_NM
			, Integer I_AMT
			, Integer I_CNT
			, Integer I_BAL_CNT
			, Integer I_FEE
			, Integer I_TOT_AMT
			, String BUY_DATE
			, long L_UPDT_USR_NO
			) throws BizException {
		
		Optional<StBuy> c = stBuyR.findById(L_BUY_SEQ);
		if(c==null) {
			throw new BizException("["+STOCK_CD+"]["+STOCK_NM+"] 내 주식이 존재하지 않습니다.[수정X]");
		}
		StBuy tmp = c.get();
		tmp.setStockCd(STOCK_CD);
		tmp.setStockNm(STOCK_NM);
		tmp.setAmt(I_AMT);
		tmp.setCnt(I_CNT);
		tmp.setBalCnt(I_BAL_CNT);
		tmp.setFee(I_FEE);
		tmp.setTotAmt(I_TOT_AMT);
		tmp.setBuyDate(BUY_DATE);
		tmp.setUpdtUsrNo(L_UPDT_USR_NO);
		tmp.setUpdtDtm(new Date());
		stBuyR.save(tmp);
	}

	public void rmStckBuy(long L_BUY_SEQ) throws BizException {
		Optional<StBuy> c = stBuyR.findById(L_BUY_SEQ);
		if(c==null) {
			throw new BizException("["+L_BUY_SEQ+"]내 주식이 존재하지 않습니다.[수정X]");
		}
		StBuy tmp = c.get();
		tmp.setDelYn("Y");
		tmp.setUpdtDtm(new Date());
		stBuyR.save(tmp);
	}
}
