package com.example.demo.db.da.upbit;

import java.util.Date;
import java.util.Optional;

import com.example.demo.db.domain.upbit.QUpbitCandlesWeeks;
import com.example.demo.db.domain.upbit.QUpbitMarket;
import com.example.demo.db.domain.upbit.UpbitCandlesWeeks;
import com.example.demo.db.domain.upbit.UpbitCandlesWeeksIdx;
import com.example.demo.db.repository.upbit.UpbitCandlesWeeksRepository;
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
public class DA_UPBIT_CANDLES_WEEKS {
	
	@Autowired
	JPAQueryFactory qf;
		
	@Autowired
	UpbitCandlesWeeksRepository upbitCandlesWeeksR;

	
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
		QUpbitCandlesWeeks.upbitCandlesWeeks.candleDateTimeUtc,
		QUpbitCandlesWeeks.upbitCandlesWeeks.candleDateTimeKst,
		QUpbitCandlesWeeks.upbitCandlesWeeks.openingPrice,
		QUpbitCandlesWeeks.upbitCandlesWeeks.highPrice,
		QUpbitCandlesWeeks.upbitCandlesWeeks.lowPrice,
		QUpbitCandlesWeeks.upbitCandlesWeeks.tradePrice,
		QUpbitCandlesWeeks.upbitCandlesWeeks.timestamp,
		QUpbitCandlesWeeks.upbitCandlesWeeks.candleAccTradePrice,
		QUpbitCandlesWeeks.upbitCandlesWeeks.candleAccTradeVolume,
		QUpbitCandlesWeeks.upbitCandlesWeeks.firstDayOfPeriod,
		QUpbitCandlesWeeks.upbitCandlesWeeks.crtDtm,
		QUpbitCandlesWeeks.upbitCandlesWeeks.updtDtm
		)			
		.from(QUpbitMarket.upbitMarket)
		.innerJoin(QUpbitCandlesWeeks.upbitCandlesWeeks)
		.on(QUpbitMarket.upbitMarket.market.eq(QUpbitCandlesWeeks.upbitCandlesWeeks.market))
		.where(builder)
		.orderBy(QUpbitMarket.upbitMarket.krNm.asc()
			,QUpbitCandlesWeeks.upbitCandlesWeeks.candleDateTimeUtc.desc()
			,QUpbitCandlesWeeks.upbitCandlesWeeks.timestamp.desc()
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
				,String CANDLE_DATE_TIME_UTC	
				,String CANDLE_DATE_TIME_KST
				,Double OPENING_PRICE
				,Double HIGH_PRICE
				,Double LOW_PRICE	
				,Double TRADE_PRICE
				,Long TIMESTAMP
				,Double CANDLE_ACC_TRADE_PRICE
				,Double CANDLE_ACC_TRADE_VOLUME
				,String FIRST_DAY_OF_PERIOD
	) {
		
		upbitCandlesWeeksR.save(
			UpbitCandlesWeeks.builder()
				.market(MARKET)
				.candleDateTimeUtc(CANDLE_DATE_TIME_UTC)
				.candleDateTimeKst(CANDLE_DATE_TIME_KST)
				.openingPrice(OPENING_PRICE)
				.highPrice(HIGH_PRICE)
				.lowPrice(LOW_PRICE)
				.tradePrice(TRADE_PRICE)
				.timestamp(TIMESTAMP)
				.candleAccTradePrice(CANDLE_ACC_TRADE_PRICE)
				.candleAccTradeVolume(CANDLE_ACC_TRADE_VOLUME)
				.firstDayOfPeriod(FIRST_DAY_OF_PERIOD)
				.updtDtm(new Date())
				.crtDtm(new Date()).build());
	}
	
	public Optional<UpbitCandlesWeeks> findById(UpbitCandlesWeeksIdx u){
		return upbitCandlesWeeksR.findById(u);
	}

	public void updt(String MARKET
	,String CANDLE_DATE_TIME_UTC	
	,String CANDLE_DATE_TIME_KST
	,Double OPENING_PRICE
	,Double HIGH_PRICE
	,Double LOW_PRICE	
	,Double TRADE_PRICE
	,Long TIMESTAMP
	,Double CANDLE_ACC_TRADE_PRICE
	,Double CANDLE_ACC_TRADE_VOLUME
	,String FIRST_DAY_OF_PERIOD
	) {
		UpbitCandlesWeeksIdx u = new UpbitCandlesWeeksIdx();
		u.setMarket(MARKET);
		u.setCandleDateTimeUtc(CANDLE_DATE_TIME_UTC);
		Optional<UpbitCandlesWeeks> c =upbitCandlesWeeksR.findById(u);
		if(c.isPresent()){
			UpbitCandlesWeeks m =c.get();
			m.setMarket(MARKET);
			m.setCandleDateTimeUtc(CANDLE_DATE_TIME_UTC);
			m.setCandleDateTimeKst(CANDLE_DATE_TIME_KST);
			m.setOpeningPrice(OPENING_PRICE);
			m.setHighPrice(HIGH_PRICE);
			m.setLowPrice(LOW_PRICE);
			m.setTradePrice(TRADE_PRICE);
			m.setTimestamp(TIMESTAMP);
			m.setCandleAccTradePrice(CANDLE_ACC_TRADE_PRICE);
			m.setCandleAccTradeVolume(CANDLE_ACC_TRADE_VOLUME);
			m.setFirstDayOfPeriod(FIRST_DAY_OF_PERIOD);
			upbitCandlesWeeksR.save(m);
		}
	}
}


