package com.example.demo.br.stck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.IN_DS;
import com.example.demo.ctrl.OUT_DS;
import com.example.demo.db.da.cm.DA_CM_SEQ;
import com.example.demo.db.da.stck.DA_STCK_BUY;
import com.example.demo.db.domain.kiw.QKiwStockMst;
import com.example.demo.db.domain.stck.QStBuy;
import com.example.demo.db.domain.stck.StBuy;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
import com.example.demo.utils.PjtUtil;
import com.querydsl.core.Tuple;

import org.springframework.data.domain.Page;


@Service
public class BR_STCK_BUY {
	@Autowired
	DA_STCK_BUY daStckB;
	
	@Autowired
	DA_CM_SEQ daCmSeq;

	@OpService
	public OUT_DS findStckBuy(IN_DS inDS) throws BizException {
		if(inDS.get("IN_DATA")==null) {
			throw new BizRuntimeException("[IN_DATA]입력파라미터가 전달되지 않았습니다.");
		}
		if(inDS.get("IN_DATA").size()!=1) {
			throw new BizRuntimeException("[IN_DATA]입력파라미터의 ["+inDS.get("IN_DATA").size()+"]행수가 잘못되었습니다.");
		}
		
		
		HashMap<String,Object>  rs =inDS.get("IN_DATA").get(0);
		if(inDS.get("PAGE_DATA")==null) {
			throw new BizRuntimeException("[PAGE_DATA]입력파라미터가 전달되지 않았습니다.");
		}
		if(inDS.get("PAGE_DATA").size()!=1) {
			throw new BizRuntimeException("[PAGE_DATA]입력파라미터의 ["+inDS.get("PAGE_DATA").size()+"]행수가 잘못되었습니다.");
		}

		HashMap<String,Object>  rs_page =inDS.get("PAGE_DATA").get(0);
		String  PAGE_NUM 		= PjtUtil.str(rs_page.get("PAGE_NUM"));
		String  PAGE_SIZE 		= PjtUtil.str(rs_page.get("PAGE_SIZE"));
		
		if(PjtUtil.isEmpty(PAGE_NUM)) {
			throw new BizRuntimeException("페이지번호 잘못입력되었습니다.1");
		}
		
		if(PjtUtil.isEmpty(PAGE_SIZE)) {
			throw new BizRuntimeException("페이지사이즈가 잘못입력되었습니다.");
		}
		int I_PAGE_NUM = 1;
		try {
			I_PAGE_NUM = Integer.parseInt(PAGE_NUM);
		} catch (NumberFormatException e) {
			throw new BizRuntimeException("페이지번호 잘못된 타입이 입력되었습니다.");
		}
		
		int I_PAGE_SIZE = 10;
		try {
			I_PAGE_SIZE= Integer.parseInt(PAGE_SIZE);
		} catch (NumberFormatException e) {
			throw new BizRuntimeException("페이지사이즈가 잘못된 타입이 입력되었습니다.");
		}

		Pageable p = PageRequest.of(I_PAGE_NUM, I_PAGE_SIZE);
		Page<Tuple>  pg = daStckB.findStckBuy(p);
		List<Tuple> al=pg.toList();
		
		
		ArrayList<HashMap<String, Object>> OUT_DATA = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < al.size(); i++) {
			Tuple c = al.get(i);
			HashMap<String, Object> OUT_DATA_ROW = new HashMap<String, Object>();
			
			OUT_DATA_ROW.put("BUY_SEQ", c.get(QStBuy.stBuy.buySeq));
			OUT_DATA_ROW.put("STOCK_CD", c.get(QStBuy.stBuy.stockCd));
			OUT_DATA_ROW.put("STOCK_NM", c.get(QStBuy.stBuy.stockNm));
			OUT_DATA_ROW.put("BUY_AMT", c.get(QStBuy.stBuy.amt));
			OUT_DATA_ROW.put("CURR_AMT", c.get(QKiwStockMst.kiwStockMst.clsAmt));
			OUT_DATA_ROW.put("DIFF_AMT", 
					c.get(QKiwStockMst.kiwStockMst.clsAmt)-c.get(QStBuy.stBuy.amt)
					);
			OUT_DATA_ROW.put("BNFT_RT", 
					String.format("%.2f", ((double)(c.get(QKiwStockMst.kiwStockMst.clsAmt)-c.get(QStBuy.stBuy.amt))
					/c.get(QStBuy.stBuy.amt)*100)
					));
			OUT_DATA_ROW.put("CNT", c.get(QStBuy.stBuy.cnt));
			OUT_DATA_ROW.put("BAL_CNT", c.get(QStBuy.stBuy.balCnt));
			OUT_DATA_ROW.put("TOT_BAL_AMT", c.get(QStBuy.stBuy.balCnt)*c.get(QStBuy.stBuy.amt));
			OUT_DATA_ROW.put("TOT_BAL_CURR_AMT", c.get(QStBuy.stBuy.balCnt)*c.get(QKiwStockMst.kiwStockMst.clsAmt));
			OUT_DATA_ROW.put("TOT_DIFF_BAL_AMT", 
					(c.get(QStBuy.stBuy.balCnt)*c.get(QKiwStockMst.kiwStockMst.clsAmt))
					-(c.get(QStBuy.stBuy.balCnt)*c.get(QStBuy.stBuy.amt))
					);
			if(c.get(QStBuy.stBuy.balCnt)>0) {
				OUT_DATA_ROW.put("TOT_BNFT_RT", 
						String.format("%.2f", ((double)((c.get(QKiwStockMst.kiwStockMst.clsAmt)*c.get(QStBuy.stBuy.balCnt))-(c.get(QStBuy.stBuy.amt)*c.get(QStBuy.stBuy.balCnt)))
						/(c.get(QStBuy.stBuy.amt)*c.get(QStBuy.stBuy.balCnt))*100)
						));	
			} else {
				OUT_DATA_ROW.put("TOT_BNFT_RT", 0);
			}
			
			OUT_DATA_ROW.put("FEE", c.get(QStBuy.stBuy.fee));
			OUT_DATA_ROW.put("RMK", c.get(QStBuy.stBuy.rmk));
			OUT_DATA_ROW.put("TOT_AMT", c.get(QStBuy.stBuy.totAmt));
			OUT_DATA_ROW.put("BUY_DATE", c.get(QStBuy.stBuy.buyDate));
			OUT_DATA_ROW.put("CRT_USR_NO", c.get(QStBuy.stBuy.crtUsrNo));
			OUT_DATA_ROW.put("UPDT_USR_NO", c.get(QStBuy.stBuy.updtUsrNo));
			OUT_DATA_ROW.put("CRT_DTM", PjtUtil.getYyyy_MM_dd_HHMMSS(c.get(QStBuy.stBuy.crtDtm)));
			OUT_DATA_ROW.put("UPDT_DTM", PjtUtil.getYyyy_MM_dd_HHMMSS(c.get(QStBuy.stBuy.updtDtm)));
			OUT_DATA.add(OUT_DATA_ROW);
		}
		OUT_DS outDs = new OUT_DS();
		outDs.put("OUT_DATA", OUT_DATA);
		
		ArrayList<HashMap<String, Object>> PAGE_DATA = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> PAGE_DATA_ROW = new HashMap<String, Object>();
		PAGE_DATA_ROW.put("PAGE_NUM", pg.getNumber());
		PAGE_DATA_ROW.put("TOTAL_SIZE", pg.getTotalElements());
		PAGE_DATA_ROW.put("TOTAL_PAGE", pg.getTotalPages());
		PAGE_DATA_ROW.put("PAGE_SIZE", pg.getSize());
		PAGE_DATA.add(PAGE_DATA_ROW);
		outDs.put("PAGE_DATA", PAGE_DATA);
		return outDs;
	}
		
	@OpService
	public OUT_DS saveStckBuy(IN_DS inDS) throws BizException {
		if(inDS.get("LSESSION")==null) {
			throw new BizRuntimeException("세션값이 넘어오지 않았습니다1.");
		}
		if(inDS.get("LSESSION").size()!=1) {
			throw new BizRuntimeException("세션값이 "+inDS.get("LSESSION").size()+"개 넘어왔습니다.");
		}
		String USER_NO =inDS.get("LSESSION").get(0).get("USER_NO").toString();
		Long L_USER_NO = Long.parseLong(USER_NO);
		if(L_USER_NO==null) {
			throw new BizRuntimeException("사용자NO가 넘어오지 않았습니다2.");
		}
		
		for( int i=0;i<inDS.get("IN_DATA").size();i++) {
			HashMap<String,Object>  rs =inDS.get("IN_DATA").get(i);
			String  STOCK_CD 		= PjtUtil.str(rs.get("STOCK_CD"));
			String  STOCK_NM 		= PjtUtil.str(rs.get("STOCK_NM"));
			String  AMT			 	= PjtUtil.str(rs.get("AMT"));
			String  CNT 			= PjtUtil.str(rs.get("CNT"));
			String  BAL_CNT			= PjtUtil.str(rs.get("BAL_CNT"));
			String  FEE 			= PjtUtil.str(rs.get("FEE"));
			String  TOT_AMT 		= PjtUtil.str(rs.get("TOT_AMT"));
			String  BUY_DATE 		= PjtUtil.str(rs.get("BUY_DATE"));
			
			
			if(PjtUtil.isEmpty(STOCK_CD)) {
				throw new BizRuntimeException("주식코드가 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(STOCK_NM)) {
				throw new BizRuntimeException("주식명이 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(AMT)) {
				throw new BizRuntimeException("단가가 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(CNT)) {
				throw new BizRuntimeException("수량이 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(BAL_CNT)) {
				throw new BizRuntimeException("잔고 수량이 입력되지 않았습니다.");
			}
			
			
			if(PjtUtil.isEmpty(FEE)) {
				throw new BizRuntimeException("수수료가 입력되지 않았습니다.");
			}
			
			if(PjtUtil.isEmpty(TOT_AMT)) {
				throw new BizRuntimeException("총금액이 입력되지 않았습니다.");
			}
			
			long L_BUY_SEQ =daCmSeq.increate("ST_BUY_BUY_SEQ");
			
			Integer I_AMT 			= Integer.parseInt(AMT);
			Integer I_CNT 			= Integer.parseInt(CNT);
			Integer I_BAL_CNT		= Integer.parseInt(BAL_CNT);
			Integer I_FEE 			= Integer.parseInt(FEE);
			Integer I_TOT_AMT 		= Integer.parseInt(TOT_AMT);
						
			daStckB.createStckBuy(
					L_BUY_SEQ
					,STOCK_CD
					,STOCK_NM
					,I_AMT
					,I_CNT
					,I_BAL_CNT
					,I_FEE
					,I_TOT_AMT
					,BUY_DATE
					,L_USER_NO
					,L_USER_NO
					);
		}
		
		for( int i=0;i<inDS.get("UPDT_DATA").size();i++) {
			HashMap<String,Object>  rs =inDS.get("UPDT_DATA").get(i);
			String  BUY_SEQ 		= PjtUtil.str(rs.get("BUY_SEQ"));
			String  STOCK_CD 		= PjtUtil.str(rs.get("STOCK_CD"));
			String  STOCK_NM 		= PjtUtil.str(rs.get("STOCK_NM"));
			String  AMT			 	= PjtUtil.str(rs.get("AMT"));
			String  CNT 			= PjtUtil.str(rs.get("CNT"));
			String  BAL_CNT			= PjtUtil.str(rs.get("BAL_CNT"));
			String  FEE 			= PjtUtil.str(rs.get("FEE"));
			String  TOT_AMT 		= PjtUtil.str(rs.get("TOT_AMT"));
			String  BUY_DATE 		= PjtUtil.str(rs.get("BUY_DATE"));
			
			if(PjtUtil.isEmpty(BUY_SEQ)) {
				throw new BizRuntimeException("내가산주식일련번호가 입력되지 않았습니다.");
			}
			
			if(PjtUtil.isEmpty(STOCK_CD)) {
				throw new BizRuntimeException("주식코드가 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(STOCK_NM)) {
				throw new BizRuntimeException("주식명이 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(AMT)) {
				throw new BizRuntimeException("단가가 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(CNT)) {
				throw new BizRuntimeException("수량이 입력되지 않았습니다.");
			}
			
			if(PjtUtil.isEmpty(BAL_CNT)) {
				throw new BizRuntimeException("잔고 수량이 입력되지 않았습니다.");
			}
			
			if(PjtUtil.isEmpty(FEE)) {
				throw new BizRuntimeException("수수료가 입력되지 않았습니다.");
			}
			
			if(PjtUtil.isEmpty(TOT_AMT)) {
				throw new BizRuntimeException("총금액이 입력되지 않았습니다.");
			}
		
			long L_BUY_SEQ = Long.parseLong(BUY_SEQ);
			
			Integer I_AMT 			= Integer.parseInt(AMT);
			Integer I_CNT 			= Integer.parseInt(CNT);
			Integer I_BAL_CNT 		= Integer.parseInt(BAL_CNT);
			Integer I_FEE 			= Integer.parseInt(FEE);
			Integer I_TOT_AMT 		= Integer.parseInt(TOT_AMT);
			
			daStckB.updateStckBuy(L_BUY_SEQ
					,  STOCK_CD
					,  STOCK_NM
					,  I_AMT
					,  I_CNT
					,  I_BAL_CNT
					,  I_FEE
					,  I_TOT_AMT
					,  BUY_DATE
					,  L_USER_NO
					);
		}

		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
	
	@OpService
	public OUT_DS rmStckBuy(IN_DS inDS) throws BizException {
		for( int i=0;i<inDS.get("IN_DATA").size();i++) {
			HashMap<String,Object>  rs =inDS.get("IN_DATA").get(i);
			String  BUY_SEQ 		= PjtUtil.str(rs.get("BUY_SEQ"));
			if(PjtUtil.isEmpty(BUY_SEQ)) {
				throw new BizRuntimeException("["+BUY_SEQ+"]내가산 주식 일련번호가 입력되지 않았습니다.");
			}
			long L_BUY_SEQ = Long.parseLong(BUY_SEQ);
			daStckB.rmStckBuy(
					L_BUY_SEQ
					);
		}
	
		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
}
