package com.example.demo.db.da.upbit;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.demo.db.domain.upbit.QUpbitMarket;
import com.example.demo.db.domain.upbit.UpbitMarket;
import com.example.demo.db.repository.upbit.UpbitMarketRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class DA_UPBIT_MARKET {
	
	@Autowired
	JPAQueryFactory qf;
		
	@Autowired
	UpbitMarketRepository upbitMarketR;

	public List<UpbitMarket> find(String SEARCH_NM,String MARKET_WARNING,String MARKET_CD,String DEL_YN) {
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
		if (!ObjectUtils.isEmpty(DEL_YN)) {
            builder.and(QUpbitMarket.upbitMarket.delYn.eq(DEL_YN));
        }

		JPAQuery<UpbitMarket> c =  qf
		.selectFrom(QUpbitMarket.upbitMarket)
		.where(builder)
		.orderBy(QUpbitMarket.upbitMarket.market.asc());
		

		List<UpbitMarket> al =  c.fetch();
		return al;	
	}
	
	public void crt(String MARKET
				,String KR_NM
				,String EN_NM
				,String MARKET_WARNING
	) {
		String MARKET_CD = MARKET.substring(0,MARKET.indexOf("-"));
		
		upbitMarketR.save(
				UpbitMarket.builder()
				.market(MARKET)
				.marketCd(MARKET_CD)
				.krNm(KR_NM)
				.enNm(EN_NM)
				.marketWarning(MARKET_WARNING)
				.delYn("N")
				.updtDtm(new Date())
				.crtDtm(new Date()).build());
	}
	
	public Optional<UpbitMarket> findById(String MARKET){
		return upbitMarketR.findById(MARKET);
	}

	
	public Long updtDelY() {
		
		long c =  qf
		.update(QUpbitMarket.upbitMarket)
		.set(QUpbitMarket.upbitMarket.delYn,"Y")
		.execute();
		return c;
	}

	public void updt(String MARKET
		,String KR_NM
		,String EN_NM
		,String MARKET_WARNING
	) {
		String MARKET_CD = MARKET.substring(0,MARKET.indexOf("-"));
		Optional<UpbitMarket> c =upbitMarketR.findById(MARKET);
		if(c.isPresent()){
			UpbitMarket m =c.get();
			m.setMarketCd(MARKET_CD);
			m.setKrNm(KR_NM);
			m.setEnNm(EN_NM);
			m.setMarketWarning(MARKET_WARNING);
			m.setDelYn("N");
			m.setUpdtDtm(new Date());
			upbitMarketR.save(m);
		}
	}

	public void updtTicker(
		String MARKET
		,String TRADE_DATE
		,String TRADE_TIME
		,String TRADE_DATE_KST
		,String TRADE_TIME_KST
		,Double OPENING_PRICE
		,Double HIGH_PRICE
		,Double LOW_PRICE
		,Double TRADE_PRICE
		,Double PREV_CLOSING_PRICE
		,String CHANGE
		,Double CHANGE_PRICE
		,Double CHANGE_RATE
		,Double SIGNED_CHANGE_PRICE
		,Double SIGNED_CHANGE_RATE
		,Double TRADE_VOLUME
		,Double ACC_TRADE_PRICE
		,Double ACC_TRADE_PRICE_24H
		,Double ACC_TRADE_VOLUME
		,Double ACC_TRADE_VOLUME_24H
		,Double HIGHEST_52_WEEK_PRICE
		,String HIGHEST_52_WEEK_DATE
		,Double LOWEST_52_WEEK_PRICE
		,String LOWEST_52_WEEK_DATE
		,Long TIMESTAMP) {
	String MARKET_CD = MARKET.substring(0,MARKET.indexOf("-"));
	Optional<UpbitMarket> c =upbitMarketR.findById(MARKET);
	if(c.isPresent()){
		UpbitMarket m =c.get();
		m.setMarketCd(MARKET_CD);
		m.setTradeDate(TRADE_DATE);
		m.setTradeTime(TRADE_TIME);		
		m.setTradeDateKst(TRADE_DATE_KST);
		m.setTradeTimeKst(TRADE_TIME_KST);
		m.setOpeningPrice(OPENING_PRICE);
		m.setHighPrice(HIGH_PRICE);
		m.setLowPrice(LOW_PRICE);
		m.setTradePrice(TRADE_PRICE);
		m.setPrevClosingPrice(PREV_CLOSING_PRICE);
		m.setChange(CHANGE);		
		m.setChangePrice(CHANGE_PRICE);
		m.setChangeRate(CHANGE_RATE);
		m.setSignedChangePrice(SIGNED_CHANGE_PRICE);
		m.setSignedChangeRate(SIGNED_CHANGE_RATE);
		m.setTradeVolume(TRADE_VOLUME);
		m.setAccTradePrice(ACC_TRADE_PRICE);
		m.setAccTradePrice24h(ACC_TRADE_PRICE_24H);
		m.setAccTradeVolume(ACC_TRADE_VOLUME);
		m.setAccTradeVolume24h(ACC_TRADE_VOLUME_24H);
		m.setHighest52WeekPrice(HIGHEST_52_WEEK_PRICE);
		m.setHighest52WeekDate(HIGHEST_52_WEEK_DATE);
		m.setLowest52WeekPrice(LOWEST_52_WEEK_PRICE);
		m.setLowest52WeekDate(LOWEST_52_WEEK_DATE);
		m.setTimestamp(TIMESTAMP);
		m.setUpdtDtm(new Date());
		upbitMarketR.save(m);
	}
}
}


