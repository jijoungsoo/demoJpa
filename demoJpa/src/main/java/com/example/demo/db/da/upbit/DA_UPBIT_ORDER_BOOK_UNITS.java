package com.example.demo.db.da.upbit;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.demo.db.domain.upbit.QUpbitOrderBookUnits;
import com.example.demo.db.domain.upbit.UpbitOrderBookUnits;
import com.example.demo.db.domain.upbit.UpbitOrderBookUnitsIdx;
import com.example.demo.db.repository.upbit.UpbitOrderBookUnitsRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DA_UPBIT_ORDER_BOOK_UNITS {
	
	@Autowired
	JPAQueryFactory qf;
		
	@Autowired
	UpbitOrderBookUnitsRepository upbitOrderBookUnitsR;

	
	public List<UpbitOrderBookUnits> find(String MARKET,Long TIMESTAMP) {
	

		return qf
		.selectFrom(QUpbitOrderBookUnits.upbitOrderBookUnits)	
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
	) {
		
		upbitOrderBookUnitsR.save(
			UpbitOrderBookUnits.builder()
				.market(MARKET)
				.timestamp(TIMESTAMP)
				.askPrice(ASK_PRICE)
				.bidPrice(BID_PRICE)
				.askSize(ASK_SIZE)
				.bidSize(BID_SIZE)
				.updtDtm(new Date())
				.crtDtm(new Date()).build());
	}

	public void updt(String MARKET
	,Long TIMESTAMP
	,Double ASK_PRICE	
	,Double BID_PRICE
	,Double ASK_SIZE	
	,Double BID_SIZE
	) {
		UpbitOrderBookUnitsIdx  t= new UpbitOrderBookUnitsIdx();
		t.setMarket(MARKET);
		t.setTimestamp(TIMESTAMP);
		Optional<UpbitOrderBookUnits> c =upbitOrderBookUnitsR.findById(t);
		if(c.isPresent()){
			UpbitOrderBookUnits m =c.get();
			m.setMarket(MARKET);
			m.setTimestamp(TIMESTAMP);
			m.setAskPrice(ASK_PRICE);
			m.setBidPrice(BID_PRICE);
			m.setAskSize(ASK_SIZE);
			m.setBidSize(BID_SIZE);
			upbitOrderBookUnitsR.save(m);
		}
	}

	public Optional<UpbitOrderBookUnits> findById(UpbitOrderBookUnitsIdx t) {
	
		return upbitOrderBookUnitsR.findById(t);
	}
}


