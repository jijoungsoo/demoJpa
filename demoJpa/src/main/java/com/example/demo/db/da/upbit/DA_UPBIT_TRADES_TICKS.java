package com.example.demo.db.da.upbit;

import java.util.Date;
import java.util.Optional;

import com.example.demo.db.domain.upbit.QUpbitMarket;
import com.example.demo.db.domain.upbit.QUpbitTradesTicks;
import com.example.demo.db.domain.upbit.UpbitTradesTicks;
import com.example.demo.db.domain.upbit.UpbitTradesTicksIdx;
import com.example.demo.db.repository.upbit.UpbitTradesTicksRepository;
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
public class DA_UPBIT_TRADES_TICKS {
	
	@Autowired
	JPAQueryFactory qf;
		
	@Autowired
	UpbitTradesTicksRepository upbitTradeTicksR;

	
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
		QUpbitTradesTicks.upbitTradesTicks.tradeDateUtc,
		QUpbitTradesTicks.upbitTradesTicks.tradeTimeUtc,
		QUpbitTradesTicks.upbitTradesTicks.timestamp,
		QUpbitTradesTicks.upbitTradesTicks.tradePrice,
		QUpbitTradesTicks.upbitTradesTicks.tradeVolume,
		QUpbitTradesTicks.upbitTradesTicks.prevClosingPrice,
		
		QUpbitTradesTicks.upbitTradesTicks.changePrice,
		QUpbitTradesTicks.upbitTradesTicks.askBid,
		QUpbitTradesTicks.upbitTradesTicks.sequentialId,
		QUpbitTradesTicks.upbitTradesTicks.crtDtm,
		QUpbitTradesTicks.upbitTradesTicks.updtDtm
		)			
		.from(QUpbitMarket.upbitMarket)
		.innerJoin(QUpbitTradesTicks.upbitTradesTicks)
		.on(QUpbitMarket.upbitMarket.market.eq(QUpbitTradesTicks.upbitTradesTicks.market))
		.where(builder)
		.orderBy(QUpbitMarket.upbitMarket.krNm.asc()
			,QUpbitTradesTicks.upbitTradesTicks.tradeDateUtc.desc()
			,QUpbitTradesTicks.upbitTradesTicks.tradeTimeUtc.desc()
			,QUpbitTradesTicks.upbitTradesTicks.sequentialId.desc()
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
				,String TRADE_DATE_UTC	
				,String TRADE_TIME_UTC
				,Long TIMESTAMP
				,Double TRADE_PRICE	
				,Double TRADE_VOLUME
				,Double PREV_CLOSING_PRICE
				,Double CHANGE_PRICE
				,String ASK_BID
				,Long SEQUENTIAL_ID
	) {
		
		upbitTradeTicksR.save(
			UpbitTradesTicks.builder()
				.market(MARKET)
				.tradeDateUtc(TRADE_DATE_UTC)
				.tradeTimeUtc(TRADE_TIME_UTC)
				.timestamp(TIMESTAMP)
				.tradePrice(TRADE_PRICE)
				.tradeVolume(TRADE_VOLUME)
				.prevClosingPrice(PREV_CLOSING_PRICE)
				.changePrice(CHANGE_PRICE)
				.askBid(ASK_BID)
				.sequentialId(SEQUENTIAL_ID)
				.updtDtm(new Date())
				.crtDtm(new Date()).build());
	}

	public void updt(String MARKET
	,String TRADE_DATE_UTC	
	,String TRADE_TIME_UTC
	,Long TIMESTAMP
	,Double TRADE_PRICE	
	,Double TRADE_VOLUME
	,Double PREV_CLOSING_PRICE
	,Double CHANGE_PRICE
	,String ASK_BID
	,Long SEQUENTIAL_ID
	) {
		UpbitTradesTicksIdx  t= new UpbitTradesTicksIdx();
		t.setMarket(MARKET);
		t.setSequentialId(SEQUENTIAL_ID);
		t.setTimestamp(TIMESTAMP);
		t.setTradeDateUtc(TRADE_DATE_UTC);
		t.setTradeTimeUtc(TRADE_TIME_UTC);
		Optional<UpbitTradesTicks> c =upbitTradeTicksR.findById(t);
		if(c.isPresent()){
			UpbitTradesTicks m =c.get();
			m.setMarket(MARKET);
			m.setTradeDateUtc(TRADE_DATE_UTC);
			m.setTradeTimeUtc(TRADE_TIME_UTC);
			m.setTimestamp(TIMESTAMP);
			m.setTradePrice(TRADE_PRICE);
			m.setTradeVolume(TRADE_VOLUME);
			m.setPrevClosingPrice(PREV_CLOSING_PRICE);
			m.setChangePrice(CHANGE_PRICE);
			m.setAskBid(ASK_BID);
			upbitTradeTicksR.save(m);
		}
	}

	public Optional<UpbitTradesTicks> findById(UpbitTradesTicksIdx t) {
	
		return upbitTradeTicksR.findById(t);
	}
}


