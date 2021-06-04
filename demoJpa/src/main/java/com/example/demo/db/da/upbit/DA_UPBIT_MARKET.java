package com.example.demo.db.da.upbit;

import java.util.Date;
import java.util.Optional;

import com.example.demo.db.domain.upbit.UpbitMarket;
import com.example.demo.db.repository.upbit.UpbitMarketRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DA_UPBIT_MARKET {
	
	@Autowired
	JPAQueryFactory qf;
		
	@Autowired
	UpbitMarketRepository upbitMarketR;
	
	public void crt(String MARKET
				,String KR_NM
				,String EN_NM
				,String MARKET_WARNING
	) {
		
		upbitMarketR.save(
				UpbitMarket.builder()
				.market(MARKET)
				.kNm(KR_NM)
				.eNm(EN_NM)
				.marketWarning(MARKET_WARNING)
				.crtDtm(new Date()).build());
	}
	
	public Optional<UpbitMarket> findById(String MARKET){
		return upbitMarketR.findById(MARKET);
	}

	public void updt(String MARKET
		,String KR_NM
		,String EN_NM
		,String MARKET_WARNING
	) {
		Optional<UpbitMarket> c =upbitMarketR.findById(MARKET);
		if(c.isPresent()){
			UpbitMarket m =c.get();
			m.setMarket(MARKET);
			m.setKNm(KR_NM);
			m.setENm(EN_NM);
			m.setMarketWarning(MARKET_WARNING);
			upbitMarketR.save(m);
		}
	}
}


