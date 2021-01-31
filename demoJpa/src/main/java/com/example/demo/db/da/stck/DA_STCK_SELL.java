package com.example.demo.db.da.stck;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.db.repository.stck.StSellRepository;
import com.example.demo.db.domain.stck.QStBuy;
import com.example.demo.db.domain.stck.QStSell;
import com.example.demo.db.domain.stck.StSell;
import com.example.demo.exception.BizException;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Service
public class DA_STCK_SELL {
	
	@Autowired
	JPAQueryFactory qf;
	
	
	@Autowired
	StSellRepository stSellR;
	
	public Page<Tuple> findStckSell(Pageable p) {
		
		JPAQuery<Tuple> c= qf.select(QStSell.stSell.sellSeq,
				QStSell.stSell.buySeq,
				QStSell.stSell.stockCd,
				QStSell.stSell.stockNm,
				QStSell.stSell.amt,
				QStBuy.stBuy.amt,
				QStSell.stSell.cnt,
				QStSell.stSell.fee,
				QStSell.stSell.tax,
				QStSell.stSell.totAmt,
				QStSell.stSell.sellDate,
				QStSell.stSell.crtUsrNo,
				QStSell.stSell.updtUsrNo,
				QStSell.stSell.crtDtm,
				QStSell.stSell.updtDtm
				)
				.from(QStSell.stSell)
				.leftJoin(QStBuy.stBuy)
				.on(QStSell.stSell.buySeq.eq(QStBuy.stBuy.buySeq));
				//.where(QStSell.stSell.delYn.eq("N"));
		c= c.offset(p.getOffset()); // offset과
		c= c.limit(p.getPageSize()); // Limit 을 지정할 수 있고
		c= c.orderBy(QStSell.stSell.sellDate.asc());
		QueryResults<Tuple> result= c.fetchResults();
		/*
		 * QueryResults<StckMarcap>
		 * JPAQuery<StckMarcap>
		 * https://joont92.github.io/jpa/QueryDSL/
		 * 여기서 보면 전체 조회수를 알아야할때 사용하는게 fetchResults라 말하고 있다.
		 * */
		 return new PageImpl<>(result.getResults(), p, result.getTotal());
	}
	
	public void createStckSell(long L_SELL_SEQ
			, long L_REF_BUY_SEQ
			, String STOCK_CD
			, String STOCK_NM
			, Integer I_AMT
			, Integer I_CNT
			, Integer I_FEE
			, Integer I_TAX
			, Integer I_TOT_AMT
			, String SELL_DATE
			, long L_CRT_USR_NO
			, long L_UPDT_USR_NO
			) {
		// TODO Auto-generated method stub
		
		stSellR.save(
				StSell.builder()
				.sellSeq(L_SELL_SEQ)
				.buySeq(L_REF_BUY_SEQ)
				.stockCd(STOCK_CD)
				.stockNm(STOCK_NM)
				.amt(I_AMT)
				.cnt(I_CNT)
				.fee(I_FEE)
				.tax(I_TAX)
				.delYn("N")
				.totAmt(I_TOT_AMT)
				.sellDate(SELL_DATE)
				.crtUsrNo(L_CRT_USR_NO)
				.updtUsrNo(L_UPDT_USR_NO)
				.updtDtm(new Date())
				.crtDtm(new Date()).build());
	}
	

	public void updateStckSell(long L_SELL_SEQ
			, long L_BUY_SEQ
			, String STOCK_CD
			, String STOCK_NM
			, Integer I_AMT
			, Integer I_CNT
			, Integer I_FEE
			, Integer I_TAX
			, Integer I_TOT_AMT
			, String SELL_DATE
			, long L_UPDT_USR_NO
			) throws BizException {
		
		Optional<StSell> c = stSellR.findById(L_SELL_SEQ);
		if(c==null) {
			throw new BizException("["+STOCK_CD+"]["+STOCK_NM+"] 내 주식이 존재하지 않습니다.[수정X]");
		}
		StSell tmp = c.get();
		tmp.setBuySeq(L_BUY_SEQ);
		tmp.setStockCd(STOCK_CD);
		tmp.setStockNm(STOCK_NM);
		tmp.setAmt(I_AMT);
		tmp.setCnt(I_CNT);
		tmp.setFee(I_FEE);
		tmp.setTax(I_TAX);
		tmp.setTotAmt(I_TOT_AMT);
		tmp.setSellDate(SELL_DATE);
		tmp.setUpdtUsrNo(L_UPDT_USR_NO);
		tmp.setUpdtDtm(new Date());
		stSellR.save(tmp);
		
		
	}
	
	public Optional<StSell> findById(long L_SELL_SEQ){
		return stSellR.findById(L_SELL_SEQ);
	}
	
	public void rmStckSell(long L_SELL_SEQ) throws BizException {
		Optional<StSell> c = stSellR.findById(L_SELL_SEQ);
		if(c==null) {
			throw new BizException("["+L_SELL_SEQ+"]내 주식이 존재하지 않습니다.[수정X]");
		}
		stSellR.deleteById(L_SELL_SEQ);
	}
}
