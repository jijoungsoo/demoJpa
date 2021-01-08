package com.example.demo.br.kiw;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.IN_DS;
import com.example.demo.ctrl.OUT_DS;
import com.example.demo.db.da.cm.DA_CM_SEQ;
import com.example.demo.db.da.kiw.DA_KIW_STOCK_MST;
import com.example.demo.db.da.kiw.DA_KIW_STOCK_MST_MAPPER;
import com.example.demo.db.da.stck.DA_STCK_MARCAP;
import com.example.demo.db.domain.cm.CmSeq;
import com.example.demo.db.domain.kiw.KiwStockMst;
import com.example.demo.db.domain.kiw.QKiwStockMst;
import com.example.demo.db.domain.marcap.StckMarcap;
import com.example.demo.db.repository.stck.StckMarcapRepository;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
import com.example.demo.utils.PjtUtil;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;


@Service
public class BR_KIW_STOCK_MST {
	@Autowired
	DA_KIW_STOCK_MST_MAPPER daStM;


	@OpService
	public OUT_DS findKiwMst(IN_DS inDS) throws BizException {
		if(inDS.get("IN_DATA")==null) {
			throw new BizRuntimeException("[IN_DATA]입력파라미터가 전달되지 않았습니다.");
		}
		if(inDS.get("IN_DATA").size()!=1) {
			throw new BizRuntimeException("[IN_DATA]입력파라미터의 ["+inDS.get("IN_DATA").size()+"]행수가 잘못되었습니다.");
		}
		
		HashMap<String,Object>  rs =inDS.get("IN_DATA").get(0);
		String  MARKET_CD 		= (String) rs.get("MARKET_CD");
		String  STOCK_CD 		= (String) rs.get("STOCK_CD");
		String  STOCK_NM 		= (String) rs.get("STOCK_NM");
		
		
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

		//코틀린에서 아래와 같이 제공한다고 한다.
		// PageRequest p = PageRequest.of(page: 1, size: 10);
		Pageable p = PageRequest.of(I_PAGE_NUM, I_PAGE_SIZE);
		
		List<Map> al=null;
		try {
			al = daStM.findKiwMst(MARKET_CD,STOCK_CD,STOCK_NM,I_PAGE_NUM*I_PAGE_SIZE,I_PAGE_SIZE);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int totalCnt=0;
		try {
			totalCnt = daStM.findKiwMstTotalCnt(MARKET_CD,STOCK_CD,STOCK_NM);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Page<Map>  pg = new PageImpl<>(al, p, totalCnt);
			
			ArrayList<HashMap<String, Object>> OUT_DATA = new ArrayList<HashMap<String, Object>>();
			for (int i = 0; i < al.size(); i++) {
				Map c = al.get(i);
				HashMap<String, Object> OUT_DATA_ROW = new HashMap<String, Object>();
				OUT_DATA_ROW.put("STOCK_CD", c.get("stock_cd"));
				OUT_DATA_ROW.put("LIST_MARKET_NM", c.get("list_market_nm"));
				OUT_DATA_ROW.put("MARKET_CNT", c.get("market_cnt"));
				OUT_DATA_ROW.put("OPEN_DT", c.get("open_dt"));
				OUT_DATA_ROW.put("STOCK_NM", c.get("stock_nm"));
				OUT_DATA_ROW.put("CLS_AMT", c.get("cls_amt"));
				OUT_DATA_ROW.put("STOCK_CNT", c.get("stock_cnt"));
				OUT_DATA_ROW.put("CONSTRUCTION", c.get("construction"));
				OUT_DATA_ROW.put("STOCK_STATE", c.get("stock_state"));
				OUT_DATA_ROW.put("CRT_DTM", c.get("crt_dtm"));
				OUT_DATA_ROW.put("UPDT_DTM", c.get("updt_dtm"));
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
}
