package com.example.demo.db.da.stck;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.db.repository.stck.StBuyRepository;
import com.example.demo.db.domain.kiw.QKiwStockMst;
import com.example.demo.db.domain.stck.QStBuy;
import com.example.demo.db.domain.stck.StBuy;
import com.example.demo.exception.BizException;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Service
public class DA_STCK_BUY {
	
	@Autowired
	JPAQueryFactory qf;
	
	
	@Autowired
	StBuyRepository stBuyR;
	
	public Page<Tuple> findStckBuy(Pageable p) {
		JPAQuery<Tuple> c= qf.select(
				QStBuy.stBuy.amt,
				QStBuy.stBuy.balCnt,
				QStBuy.stBuy.buyDate,
				QStBuy.stBuy.buySeq,
				QStBuy.stBuy.cnt,
				QStBuy.stBuy.crtDtm,
				QStBuy.stBuy.crtUsrNo,
				QStBuy.stBuy.fee,
				QStBuy.stBuy.rmk,
				QStBuy.stBuy.stockCd,
				QStBuy.stBuy.stockNm,
				QStBuy.stBuy.totAmt,
				QStBuy.stBuy.updtDtm,
				QStBuy.stBuy.updtUsrNo,
				QKiwStockMst.kiwStockMst.clsAmt
				)
				.from(QStBuy.stBuy)
				.leftJoin(QKiwStockMst.kiwStockMst)
				.on(QKiwStockMst.kiwStockMst.stockCd.eq(QStBuy.stBuy.stockCd))
				.where(QStBuy.stBuy.delYn.eq("N"));
		c= c.offset(p.getOffset()); // offset과
		c= c.limit(p.getPageSize()); // Limit 을 지정할 수 있고
		c= c.orderBy(QStBuy.stBuy.buyDate.asc(),QStBuy.stBuy.buySeq.asc());
		QueryResults<Tuple> result= c.fetchResults();
		/*
		 * QueryResults<StckMarcap>
		 * JPAQuery<StckMarcap>
		 * https://joont92.github.io/jpa/QueryDSL/
		 * 여기서 보면 전체 조회수를 알아야할때 사용하는게 fetchResults라 말하고 있다.
		 * */
		 return new PageImpl<>(result.getResults(), p, result.getTotal());
	}
	
	
	public void createStckBuy(long L_BUY_SEQ
			, String STOCK_CD
			, String STOCK_NM
			, Integer I_AMT
			, Integer I_CNT
			, Integer I_BAL_CNT			
			, Integer I_FEE
			, Integer I_TOT_AMT
			, String BUY_DATE
			, long L_CRT_USR_NO
			, long L_UPDT_USR_NO
			) {
		// TODO Auto-generated method stub
		
		stBuyR.save(
				StBuy.builder()
				.buySeq(L_BUY_SEQ)
				.stockCd(STOCK_CD)
				.stockNm(STOCK_NM)
				.amt(I_AMT)
				.cnt(I_CNT)
				.balCnt(I_BAL_CNT)				
				.fee(I_FEE)
				.totAmt(I_TOT_AMT)
				.buyDate(BUY_DATE)
				.delYn("N")
				.crtUsrNo(L_CRT_USR_NO)
				.updtUsrNo(L_UPDT_USR_NO)
				.updtDtm(new Date())
				.crtDtm(new Date()).build());
	}
	

	public void updateStckBuy(long L_BUY_SEQ
			, String STOCK_CD
			, String STOCK_NM
			, Integer I_AMT
			, Integer I_CNT
			, Integer I_BAL_CNT
			, Integer I_FEE
			, Integer I_TOT_AMT
			, String BUY_DATE
			, long L_UPDT_USR_NO
			) throws BizException {
		
		Optional<StBuy> c = stBuyR.findById(L_BUY_SEQ);
		if(c==null) {
			throw new BizException("["+STOCK_CD+"]["+STOCK_NM+"] 내 주식이 존재하지 않습니다.[수정X]");
		}
		StBuy tmp = c.get();
		tmp.setStockCd(STOCK_CD);
		tmp.setStockNm(STOCK_NM);
		tmp.setAmt(I_AMT);
		tmp.setCnt(I_CNT);
		tmp.setBalCnt(I_BAL_CNT);
		tmp.setFee(I_FEE);
		tmp.setTotAmt(I_TOT_AMT);
		tmp.setBuyDate(BUY_DATE);
		tmp.setUpdtUsrNo(L_UPDT_USR_NO);
		tmp.setUpdtDtm(new Date());
		stBuyR.save(tmp);
	}

	public void rmStckBuy(long L_BUY_SEQ) throws BizException {
		Optional<StBuy> c = stBuyR.findById(L_BUY_SEQ);
		if(c==null) {
			throw new BizException("["+L_BUY_SEQ+"]내 주식이 존재하지 않습니다.[수정X]");
		}
		StBuy tmp = c.get();
		tmp.setDelYn("Y");
		tmp.setUpdtDtm(new Date());
		stBuyR.save(tmp);
	}
}
