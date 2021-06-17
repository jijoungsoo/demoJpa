package com.example.demo.db.da.upbit;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.demo.db.domain.upbit.QUpbitMarket;
import com.example.demo.db.domain.upbit.QUpbitOrderBookUnits;
import com.example.demo.db.domain.upbit.UpbitOrderBookUnits;
import com.example.demo.db.domain.upbit.UpbitOrderBookUnitsIdx;
import com.example.demo.db.repository.upbit.UpbitOrderBookUnitsRepository;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DA_UPBIT_ORDER_BOOK_UNITS {
	
	@Autowired
	JPAQueryFactory qf;
		
	@Autowired
	UpbitOrderBookUnitsRepository upbitOrderBookUnitsR;

	
	public List<Tuple> find(String MARKET,Long TIMESTAMP) {
		return qf
		.select(QUpbitMarket.upbitMarket.market,
		QUpbitMarket.upbitMarket.marketCd,
		QUpbitMarket.upbitMarket.marketWarning,
		QUpbitMarket.upbitMarket.enNm,
		QUpbitMarket.upbitMarket.krNm,
		QUpbitOrderBookUnits.upbitOrderBookUnits.seq,
		QUpbitOrderBookUnits.upbitOrderBookUnits.askPrice,
		QUpbitOrderBookUnits.upbitOrderBookUnits.bidPrice,
		QUpbitOrderBookUnits.upbitOrderBookUnits.askSize,
		QUpbitOrderBookUnits.upbitOrderBookUnits.bidSize,
		QUpbitOrderBookUnits.upbitOrderBookUnits.timestamp,
		QUpbitOrderBookUnits.upbitOrderBookUnits.crtDtm,
		QUpbitOrderBookUnits.upbitOrderBookUnits.updtDtm)
		.from(QUpbitMarket.upbitMarket)
		.innerJoin(QUpbitOrderBookUnits.upbitOrderBookUnits)	
		.on(QUpbitMarket.upbitMarket.market.eq(QUpbitOrderBookUnits.upbitOrderBookUnits.market))
		.where(QUpbitOrderBookUnits.upbitOrderBookUnits.market.eq(MARKET))
		.where(QUpbitOrderBookUnits.upbitOrderBookUnits.timestamp.eq(TIMESTAMP))
		.orderBy(QUpbitOrderBookUnits.upbitOrderBookUnits.market.asc()
			,QUpbitOrderBookUnits.upbitOrderBookUnits.seq.asc()
		).fetch();
		
		

	}
	
	public void crt(String MARKET
				,Long TIMESTAMP
				,Double ASK_PRICE	
				,Double BID_PRICE
				,Double ASK_SIZE	
				,Double BID_SIZE
				,Integer SEQ
	) {
		
		upbitOrderBookUnitsR.save(
			UpbitOrderBookUnits.builder()
				.market(MARKET)
				.timestamp(TIMESTAMP)
				.askPrice(ASK_PRICE)
				.bidPrice(BID_PRICE)
				.askSize(ASK_SIZE)
				.bidSize(BID_SIZE)
				.seq(SEQ)
				.updtDtm(new Date())
				.crtDtm(new Date()).build());
	}

	public void updt(String MARKET
	,Long TIMESTAMP
	,Double ASK_PRICE	
	,Double BID_PRICE
	,Double ASK_SIZE	
	,Double BID_SIZE
	,Integer SEQ
	) {
		UpbitOrderBookUnitsIdx  t= new UpbitOrderBookUnitsIdx();
		t.setMarket(MARKET);
		t.setTimestamp(TIMESTAMP);
		t.setSeq(SEQ);
		Optional<UpbitOrderBookUnits> c =upbitOrderBookUnitsR.findById(t);
		if(c.isPresent()){
			UpbitOrderBookUnits m =c.get();
			m.setMarket(MARKET);
			m.setTimestamp(TIMESTAMP);
			m.setAskPrice(ASK_PRICE);
			m.setBidPrice(BID_PRICE);
			m.setAskSize(ASK_SIZE);
			m.setBidSize(BID_SIZE);
			m.setSeq(SEQ);
			upbitOrderBookUnitsR.save(m);
		}
	}

	public Optional<UpbitOrderBookUnits> findById(UpbitOrderBookUnitsIdx t) {
	
		return upbitOrderBookUnitsR.findById(t);
	}
}


