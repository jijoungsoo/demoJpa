package com.example.demo.db.da.upbit;

import java.util.Date;
import java.util.Optional;

import com.example.demo.db.domain.upbit.QUpbitMarket;
import com.example.demo.db.domain.upbit.QUpbitOrderBook;
import com.example.demo.db.domain.upbit.QUpbitTradesTicks;
import com.example.demo.db.domain.upbit.UpbitOrderBook;
import com.example.demo.db.domain.upbit.UpbitOrderBookIdx;
import com.example.demo.db.domain.upbit.UpbitTradesTicks;
import com.example.demo.db.domain.upbit.UpbitTradesTicksIdx;
import com.example.demo.db.repository.upbit.UpbitOrderBookRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class DA_UPBIT_ORDER_BOOK {
	
	@Autowired
	JPAQueryFactory qf;
		
	@Autowired
	UpbitOrderBookRepository upbitOrderBookR;

	
	public Page<Tuple> find(Pageable p,String SEARCH_NM,String MARKET_WARNING,String MARKET_CD) {
		BooleanBuilder builder = new BooleanBuilder();

		if (!ObjectUtils.isEmpty(SEARCH_NM)) {
			BooleanBuilder tmp = new BooleanBuilder();
            tmp.or(QUpbitMarket.upbitMarket.market.containsIgnoreCase(SEARCH_NM));
			tmp.or(QUpbitMarket.upbitMarket.krNm.containsIgnoreCase(SEARCH_NM));
			tmp.or(QUpbitMarket.upbitMarket.enNm.containsIgnoreCase(SEARCH_NM));
			builder.and(tmp);
		}

		if (!ObjectUtils.isEmpty(MARKET_WARNING)) {
            builder.and(QUpbitMarket.upbitMarket.marketWarning.eq(MARKET_WARNING));
        }

		if (!ObjectUtils.isEmpty(MARKET_CD)) {
            builder.and(QUpbitMarket.upbitMarket.marketCd.eq(MARKET_CD));
        }

		JPAQuery<Tuple> c= qf
		.select(QUpbitMarket.upbitMarket.market,
		QUpbitMarket.upbitMarket.marketCd,
		QUpbitMarket.upbitMarket.marketWarning,
		QUpbitMarket.upbitMarket.enNm,
		QUpbitMarket.upbitMarket.krNm,
		QUpbitOrderBook.upbitOrderBook.totalAskSize,
		QUpbitOrderBook.upbitOrderBook.totalBidSize,
		QUpbitOrderBook.upbitOrderBook.timestamp
		
		)			
		.from(QUpbitMarket.upbitMarket)
		.innerJoin(QUpbitOrderBook.upbitOrderBook)
		.on(QUpbitMarket.upbitMarket.market.eq(QUpbitOrderBook.upbitOrderBook.market))
		.where(builder)
		.orderBy(QUpbitMarket.upbitMarket.krNm.asc()
			,QUpbitOrderBook.upbitOrderBook.timestamp.desc()
		);
		

		if(p!=null) {
			c= c.offset(p.getOffset()); // offset과
			c= c.limit(p.getPageSize()); // Limit 을 지정할 수 있고			
		}
		QueryResults<Tuple> result= c.fetchResults();
		if(p==null) {
			p = PageRequest.of(0, (int) result.getTotal());
		}
		
		return new PageImpl<>(result.getResults(), p, result.getTotal());
	}
	
	public void crt(String MARKET
				,Long TIMESTAMP
				,Double TOTAL_ASK_SIZE	
				,Double TOTAL_BID_SIZE
	) {
		
		upbitOrderBookR.save(
			UpbitOrderBook.builder()
				.market(MARKET)
				.market(MARKET)
				.timestamp(TIMESTAMP)
				.totalAskSize(TOTAL_ASK_SIZE)
				.totalBidSize(TOTAL_BID_SIZE)
				.updtDtm(new Date())
				.crtDtm(new Date()).build());
	}

	public void updt(String MARKET
	,Long TIMESTAMP
	,Double TOTAL_ASK_SIZE	
	,Double TOTAL_BID_SIZE
	) {
		UpbitOrderBookIdx  t= new UpbitOrderBookIdx();
		t.setMarket(MARKET);
		t.setTimestamp(TIMESTAMP);
		Optional<UpbitOrderBook> c =upbitOrderBookR.findById(t);
		if(c.isPresent()){
			UpbitOrderBook m =c.get();
			m.setMarket(MARKET);
			m.setTimestamp(TIMESTAMP);
			m.setTotalAskSize(TOTAL_ASK_SIZE);
			m.setTotalBidSize(TOTAL_BID_SIZE);
			upbitOrderBookR.save(m);
		}
	}

	public Optional<UpbitOrderBook> findById(UpbitOrderBookIdx t) {
	
		return upbitOrderBookR.findById(t);
	}
}


