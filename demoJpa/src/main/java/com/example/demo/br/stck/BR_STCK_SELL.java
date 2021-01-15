package com.example.demo.br.stck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.IN_DS;
import com.example.demo.ctrl.OUT_DS;
import com.example.demo.db.da.cm.DA_CM_SEQ;
import com.example.demo.db.da.stck.DA_STCK_BUY;
import com.example.demo.db.da.stck.DA_STCK_BUY_MAPPER;
import com.example.demo.db.da.stck.DA_STCK_SELL;
import com.example.demo.db.domain.stck.StBuy;
import com.example.demo.db.domain.stck.StSell;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
import com.example.demo.utils.PjtUtil;
import org.springframework.data.domain.Page;


@Service
public class BR_STCK_SELL {
	@Autowired
	DA_STCK_SELL daStckS;
	
	@Autowired
	DA_STCK_BUY_MAPPER daStckBM;
	
	@Autowired
	DA_CM_SEQ daCmSeq;

	@OpService
	public OUT_DS findStckSell(IN_DS inDS) throws BizException {
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
		Page<StSell>  pg = daStckS.findStckSell(p);
		List<StSell> al=pg.toList();
		
		
		ArrayList<HashMap<String, Object>> OUT_DATA = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < al.size(); i++) {
			StSell c = al.get(i);
			HashMap<String, Object> OUT_DATA_ROW = new HashMap<String, Object>();
			OUT_DATA_ROW.put("SELL_SEQ", c.getSellSeq());
			OUT_DATA_ROW.put("BUY_SEQ", c.getBuySeq());
			OUT_DATA_ROW.put("STOCK_CD", c.getStockCd());
			
			OUT_DATA_ROW.put("STOCK_NM", c.getStockNm());
			OUT_DATA_ROW.put("AMT", c.getAmt());
			OUT_DATA_ROW.put("CNT", c.getCnt());
			OUT_DATA_ROW.put("FEE", c.getFee());
			OUT_DATA_ROW.put("TAX", c.getTax());
			OUT_DATA_ROW.put("TOT_AMT", c.getTotAmt());
			OUT_DATA_ROW.put("SELL_DATE", c.getSellDate());
			OUT_DATA_ROW.put("CRT_USR_NO", c.getCrtUsrNo());
			OUT_DATA_ROW.put("UPDT_USR_NO", c.getUpdtUsrNo());
			OUT_DATA_ROW.put("CRT_DTM", PjtUtil.getYyyy_MM_dd_HHMMSS(c.getCrtDtm()));
			OUT_DATA_ROW.put("UPDT_DTM", PjtUtil.getYyyy_MM_dd_HHMMSS(c.getUpdtDtm()));
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
	public OUT_DS createStckSell(IN_DS inDS) throws Exception {
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
			String  BUY_SEQ 		= PjtUtil.str(rs.get("BUY_SEQ"));
			String  STOCK_CD 		= PjtUtil.str(rs.get("STOCK_CD"));
			String  STOCK_NM 		= PjtUtil.str(rs.get("STOCK_NM"));
			String  AMT			 	= PjtUtil.str(rs.get("AMT"));
			String  CNT 			= PjtUtil.str(rs.get("CNT"));
			String  FEE 			= PjtUtil.str(rs.get("FEE"));
			String  TAX 			= PjtUtil.str(rs.get("TAX"));
			String  TOT_AMT 		= PjtUtil.str(rs.get("TOT_AMT"));
			String  SELL_DATE 		= PjtUtil.str(rs.get("SELL_DATE"));
			
			if(PjtUtil.isEmpty(BUY_SEQ)) {
				throw new BizRuntimeException("BUY_SEQ 이전구매시퀀스가 입력되지 않았습니다.");
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
			
			if(PjtUtil.isEmpty(FEE)) {
				throw new BizRuntimeException("수수료가 입력되지 않았습니다.");
			}
			
			if(PjtUtil.isEmpty(TAX)) {
				throw new BizRuntimeException("세금이 입력되지 않았습니다.");
			}
			
			if(PjtUtil.isEmpty(SELL_DATE)) {
				throw new BizRuntimeException("판날이 입력되지 않았습니다.");
			}
			
			if(PjtUtil.isEmpty(TOT_AMT)) {
				throw new BizRuntimeException("총금액이 입력되지 않았습니다.");
			}
			
			long L_SELL_SEQ =daCmSeq.increate("ST_SELL_SELL_SEQ");
			
			long	L_BUY_SEQ 	= Integer.parseInt(BUY_SEQ);
			
			Integer I_AMT 			= Integer.parseInt(AMT);
			Integer I_CNT 			= Integer.parseInt(CNT);
			Integer I_FEE 			= Integer.parseInt(FEE);
			Integer I_TAX 			= Integer.parseInt(TAX);
			Integer I_TOT_AMT 		= Integer.parseInt(TOT_AMT);
						
			daStckS.createStckSell(
					L_SELL_SEQ
					,L_BUY_SEQ
					,STOCK_CD
					,STOCK_NM
					,I_AMT
					,I_CNT
					,I_FEE
					,I_TAX
					,I_TOT_AMT
					,SELL_DATE
					,L_USER_NO
					,L_USER_NO
					);
			
			daStckBM.reSyncStBuy(L_BUY_SEQ,L_USER_NO);
		}
	

		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
		
	@OpService
	public OUT_DS updateStckSell(IN_DS inDS) throws BizException {
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
	
		for( int i=0;i<inDS.get("UPDT_DATA").size();i++) {
			HashMap<String,Object>  rs =inDS.get("UPDT_DATA").get(i);
			String  SELL_SEQ 		= PjtUtil.str(rs.get("SELL_SEQ"));
			String  REF_BUY_SEQ 		= PjtUtil.str(rs.get("REF_BUY_SEQ"));
			String  STOCK_CD 		= PjtUtil.str(rs.get("STOCK_CD"));
			String  STOCK_NM 		= PjtUtil.str(rs.get("STOCK_NM"));
			String  AMT			 	= PjtUtil.str(rs.get("AMT"));
			String  CNT 			= PjtUtil.str(rs.get("CNT"));
			String  FEE 			= PjtUtil.str(rs.get("FEE"));
			String  TAX 			= PjtUtil.str(rs.get("TAX"));
			String  TOT_AMT 		= PjtUtil.str(rs.get("TOT_AMT"));
			String  SELL_DATE 		= PjtUtil.str(rs.get("SELL_DATE"));
			
			if(PjtUtil.isEmpty(SELL_SEQ)) {
				throw new BizRuntimeException("내가 판 주식일련번호가 입력되지 않았습니다.");
			}
			
			if(PjtUtil.isEmpty(REF_BUY_SEQ)) {
				throw new BizRuntimeException("REF_BUY_SEQ 이전구매시퀀스가 입력되지 않았습니다.");
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
			
			if(PjtUtil.isEmpty(FEE)) {
				throw new BizRuntimeException("수수료가 입력되지 않았습니다.");
			}
			
			if(PjtUtil.isEmpty(TOT_AMT)) {
				throw new BizRuntimeException("총금액이 입력되지 않았습니다.");
			}
		
			long L_SELL_SEQ = Long.parseLong(SELL_SEQ);
			long L_REF_BUY_SEQ = Long.parseLong(REF_BUY_SEQ);
			
			Integer I_AMT 			= Integer.parseInt(AMT);
			Integer I_CNT 			= Integer.parseInt(CNT);
			Integer I_FEE 			= Integer.parseInt(FEE);
			Integer I_TAX 			= Integer.parseInt(TAX);
			Integer I_TOT_AMT 		= Integer.parseInt(TOT_AMT);
			
			daStckS.updateStckSell(L_SELL_SEQ
					, L_REF_BUY_SEQ
					,  STOCK_CD
					,  STOCK_NM
					,  I_AMT
					,  I_CNT
					,  I_FEE
					,  I_TAX
					,  I_TOT_AMT
					,  SELL_DATE
					,  L_USER_NO
					);
		}

		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
	
	@OpService
	public OUT_DS rmStckSell(IN_DS inDS) throws Exception {
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
			String  SELL_SEQ 		= PjtUtil.str(rs.get("SELL_SEQ"));
			if(PjtUtil.isEmpty(SELL_SEQ)) {
				throw new BizRuntimeException("["+SELL_SEQ+"]내가 판 주식 일련번호가 입력되지 않았습니다.");
			}
			long L_SELL_SEQ = Long.parseLong(SELL_SEQ);
			Optional<StSell>  c = daStckS.findById(L_SELL_SEQ);
			if(c==null) {
				throw new BizException("["+L_SELL_SEQ+"]내 주식이 존재하지 않습니다.[수정X,삭제X]");
			}
			StSell tmp =c.get();
			long L_BUY_SEQ = tmp.getBuySeq();
			
			
			daStckS.rmStckSell(
					L_SELL_SEQ
					);
			daStckBM.reSyncStBuy(L_BUY_SEQ,L_USER_NO);
			///
		}
	
		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
}
