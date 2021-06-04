package com.example.demo.db.da.upbit;

import java.util.Date;
import java.util.Optional;

import com.example.demo.db.domain.upbit.UpbitCandlesMinutes;
import com.example.demo.db.domain.upbit.UpbitCandlesMinutesIdx;
import com.example.demo.db.repository.upbit.UpbitCandlesMinutesRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DA_UPBIT_CANDLES_MINUTES {
	
	@Autowired
	JPAQueryFactory qf;
		
	@Autowired
	UpbitCandlesMinutesRepository upbitCandlesMinutesR;
	
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


