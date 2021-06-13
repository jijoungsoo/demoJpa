package com.example.demo.db.da.upbit;

import java.util.Date;
import java.util.Optional;

import com.example.demo.db.domain.upbit.QUpbitCandlesDays;
import com.example.demo.db.domain.upbit.QUpbitMarket;
import com.example.demo.db.domain.upbit.UpbitCandlesDays;
import com.example.demo.db.domain.upbit.UpbitCandlesDaysIdx;
import com.example.demo.db.repository.upbit.UpbitCandlesDaysRepository;
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
public class DA_UPBIT_CANDLES_DAYS {
	
	@Autowired
	JPAQueryFactory qf;
		
	@Autowired
	UpbitCandlesDaysRepository upbitCandlesDaysR;

	
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
		QUpbitCandlesDays.upbitCandlesDays.candleDateTimeUtc,
		QUpbitCandlesDays.upbitCandlesDays.candleDateTimeKst,
		QUpbitCandlesDays.upbitCandlesDays.openingPrice,
		QUpbitCandlesDays.upbitCandlesDays.highPrice,
		QUpbitCandlesDays.upbitCandlesDays.lowPrice,
		QUpbitCandlesDays.upbitCandlesDays.tradePrice,
		QUpbitCandlesDays.upbitCandlesDays.timestamp,
		QUpbitCandlesDays.upbitCandlesDays.candleAccTradePrice,
		QUpbitCandlesDays.upbitCandlesDays.candleAccTradeVolume,
		QUpbitCandlesDays.upbitCandlesDays.prevClosingPrice,
		QUpbitCandlesDays.upbitCandlesDays.changePrice,
		QUpbitCandlesDays.upbitCandlesDays.changeRate,
		QUpbitCandlesDays.upbitCandlesDays.convertedTradePrice,
		QUpbitCandlesDays.upbitCandlesDays.crtDtm,
		QUpbitCandlesDays.upbitCandlesDays.updtDtm
		)			
		.from(QUpbitMarket.upbitMarket)
		.innerJoin(QUpbitCandlesDays.upbitCandlesDays)
		.on(QUpbitMarket.upbitMarket.market.eq(QUpbitCandlesDays.upbitCandlesDays.market))
		.where(builder)
		.orderBy(QUpbitMarket.upbitMarket.krNm.asc()
			,QUpbitCandlesDays.upbitCandlesDays.candleDateTimeUtc.desc()
			,QUpbitCandlesDays.upbitCandlesDays.timestamp.desc()
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
				,Double PREV_CLOSING_PRICE
				,Double CHANGE_PRICE
				,Double CHANGE_RATE
				,Double CONVERTED_TRADE_PRICE
	) {
		
		upbitCandlesDaysR.save(
			UpbitCandlesDays.builder()
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
				.prevClosingPrice(PREV_CLOSING_PRICE)
				.changePrice(CHANGE_PRICE)
				.changeRate(CHANGE_RATE)
				.convertedTradePrice(CONVERTED_TRADE_PRICE)
				.updtDtm(new Date())
				.crtDtm(new Date()).build());
	}
	
	public Optional<UpbitCandlesDays> findById(UpbitCandlesDaysIdx u){
		return upbitCandlesDaysR.findById(u);
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
	,Double PREV_CLOSING_PRICE
	,Double CHANGE_PRICE
	,Double CHANGE_RATE
	,Double CONVERTED_TRADE_PRICE
	) {
		UpbitCandlesDaysIdx u = new UpbitCandlesDaysIdx();
		u.setMarket(MARKET);
		u.setCandleDateTimeUtc(CANDLE_DATE_TIME_UTC);
		Optional<UpbitCandlesDays> c =upbitCandlesDaysR.findById(u);
		if(c.isPresent()){
			UpbitCandlesDays m =c.get();
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
			m.setPrevClosingPrice(PREV_CLOSING_PRICE);
			m.setChangePrice(CHANGE_PRICE);
			m.setChangeRate(CHANGE_RATE);
			m.setConvertedTradePrice(CONVERTED_TRADE_PRICE);
			upbitCandlesDaysR.save(m);
		}
	}
}


