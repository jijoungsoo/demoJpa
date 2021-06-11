package com.example.demo.db.da.upbit;

import java.util.Date;
import java.util.Optional;

import com.example.demo.db.domain.upbit.UpbitCandlesDays;
import com.example.demo.db.domain.upbit.UpbitCandlesDaysIdx;
import com.example.demo.db.repository.upbit.UpbitCandlesDaysRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DA_UPBIT_CANDLES_DAYS {
	
	@Autowired
	JPAQueryFactory qf;
		
	@Autowired
	UpbitCandlesDaysRepository upbitCandlesDaysR;
	
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


