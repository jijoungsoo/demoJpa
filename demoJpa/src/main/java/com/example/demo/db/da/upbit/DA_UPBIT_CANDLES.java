package com.example.demo.db.da.upbit;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import com.example.demo.db.domain.upbit.QUpbitCandlesMinutes;
import com.example.demo.db.domain.upbit.QUpbitMarket;
import com.example.demo.db.domain.upbit.UpbitCandlesMinutes;
import com.example.demo.db.domain.upbit.UpbitCandlesMinutesIdx;
import com.example.demo.db.repository.upbit.UpbitCandlesMinutesRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.JoinFlag.Position;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.sql.JPASQLQuery;
import com.querydsl.sql.SQLTemplates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class DA_UPBIT_CANDLES {
	
	@Autowired
	JPAQueryFactory qf;

	@Autowired
    private EntityManager em;
		
	@Autowired
	UpbitCandlesMinutesRepository upbitCandlesMinutesR;

	
	public List<Tuple> find(String SEARCH_NM,String MARKET_WARNING,String MARKET_CD) {
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

		SQLTemplates templates = com.querydsl.sql.PostgreSQLTemplates.builder().build();
		
		JPASQLQuery<?> query = new JPASQLQuery<Void>(em, templates);
		JPASQLQuery<?> query1 = new JPASQLQuery<Void>(em, templates);

		JPASQLQuery<Tuple>  q=query
		.select(QUpbitMarket.upbitMarket.market,
		QUpbitMarket.upbitMarket.marketCd,
		QUpbitMarket.upbitMarket.marketWarning,
		QUpbitMarket.upbitMarket.enNm,
		QUpbitMarket.upbitMarket.krNm,
		QUpbitCandlesMinutes.upbitCandlesMinutes.candleDateTimeUtc,
		/*QUpbitCandlesMinutes.upbitCandlesMinutes.candleDateTimeKst, 버그가 있어 아래로*/
		Expressions.stringPath(QUpbitCandlesMinutes.upbitCandlesMinutes,"candle_date_time_kst").as("candle_date_time_kst"),
		QUpbitCandlesMinutes.upbitCandlesMinutes.openingPrice,
		QUpbitCandlesMinutes.upbitCandlesMinutes.highPrice,
		QUpbitCandlesMinutes.upbitCandlesMinutes.lowPrice,
		QUpbitCandlesMinutes.upbitCandlesMinutes.tradePrice,
		QUpbitCandlesMinutes.upbitCandlesMinutes.timestamp,
		QUpbitCandlesMinutes.upbitCandlesMinutes.candleAccTradePrice,
		QUpbitCandlesMinutes.upbitCandlesMinutes.candleAccTradeVolume,
		QUpbitCandlesMinutes.upbitCandlesMinutes.unit,
		QUpbitCandlesMinutes.upbitCandlesMinutes.crtDtm,
		QUpbitCandlesMinutes.upbitCandlesMinutes.updtDtm
		)			
		.from(QUpbitMarket.upbitMarket)
		.leftJoin(QUpbitCandlesMinutes.upbitCandlesMinutes)
		.on(QUpbitMarket.upbitMarket.market.eq(QUpbitCandlesMinutes.upbitCandlesMinutes.market))
		.innerJoin(
			query1.select(
				ExpressionUtils.as(QUpbitCandlesMinutes.upbitCandlesMinutes.market, "market")
				,ExpressionUtils.as(QUpbitCandlesMinutes.upbitCandlesMinutes.candleDateTimeUtc.max(), "max_candle_date_time_utc")
			)
			.from(QUpbitCandlesMinutes.upbitCandlesMinutes)                
			.groupBy(QUpbitCandlesMinutes.upbitCandlesMinutes.market),Expressions.stringPath("cc")
			/*최근 1분 것은 누적거래금액과   누적 거래량이 집계가 잘 안된다. --지금 시점이기때문에
			비교를 하려면 1분전것 부터 해야한다. */
		)
		.addJoinFlag(" on cc.market = upbitCandlesMinutes.market"
			+" and cc.max_candle_date_time_utc = upbitCandlesMinutes.candle_date_time_utc",
			Position.BEFORE_CONDITION)
		.where(builder);
		List<Tuple>  r=q.orderBy(QUpbitMarket.upbitMarket.krNm.asc()
		,QUpbitCandlesMinutes.upbitCandlesMinutes.candleDateTimeUtc.desc()
		,QUpbitCandlesMinutes.upbitCandlesMinutes.timestamp.desc()
		).fetch();
		

		 return r;
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
				,Integer UNIT
	) {
		
		upbitCandlesMinutesR.save(
			UpbitCandlesMinutes.builder()
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
				.unit(UNIT)
				.updtDtm(new Date())
				.crtDtm(new Date()).build());
	}
	
	public Optional<UpbitCandlesMinutes> findById(UpbitCandlesMinutesIdx u){
		return upbitCandlesMinutesR.findById(u);
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
	,Integer UNIT
	) {
		UpbitCandlesMinutesIdx u = new UpbitCandlesMinutesIdx();
		u.setMarket(MARKET);
		u.setCandleDateTimeUtc(CANDLE_DATE_TIME_UTC);
		Optional<UpbitCandlesMinutes> c =upbitCandlesMinutesR.findById(u);
		if(c.isPresent()){
			UpbitCandlesMinutes m =c.get();
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
			m.setUnit(UNIT);
			upbitCandlesMinutesR.save(m);
		}
	}
}


