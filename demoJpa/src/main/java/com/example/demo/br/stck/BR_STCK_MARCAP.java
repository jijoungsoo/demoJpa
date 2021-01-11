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
import com.example.demo.db.da.stck.DA_STCK_MARCAP;
import com.example.demo.db.domain.marcap.StckMarcap;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
import com.example.demo.utils.PjtUtil;
import org.springframework.data.domain.Page;


@Service
public class BR_STCK_MARCAP {
	@Autowired
	DA_STCK_MARCAP daStckM;


	@OpService
	public OUT_DS findStckMarcap(IN_DS inDS) throws BizException {
		if(inDS.get("IN_DATA")==null) {
			throw new BizRuntimeException("[IN_DATA]입력파라미터가 전달되지 않았습니다.");
		}
		if(inDS.get("IN_DATA").size()!=1) {
			throw new BizRuntimeException("[IN_DATA]입력파라미터의 ["+inDS.get("IN_DATA").size()+"]행수가 잘못되었습니다.");
		}
		
		
		HashMap<String,Object>  rs =inDS.get("IN_DATA").get(0);
		String  STOCK_DT_ST 		= PjtUtil.str(rs.get("STOCK_DT_ST"));
		String  STOCK_DT_ED 		= PjtUtil.str(rs.get("STOCK_DT_ED"));
		String  STOCK_CD 			= PjtUtil.str(rs.get("STOCK_CD"));
		
		if(PjtUtil.isEmpty(STOCK_DT_ST)) {
			throw new BizRuntimeException("시작일자가 입력되지 않았습니다.");
		}
		if(STOCK_DT_ST.length()!=8) {
			throw new BizRuntimeException("["+STOCK_DT_ST+"]시작일자가 잘못입력되었습니다.");
		}
		
		if(STOCK_DT_ED.length()!=8) {
			throw new BizRuntimeException("["+STOCK_DT_ED+"]종료일자가 잘못입력되었습니다.");
		}
		

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
		Page<StckMarcap>  pg = daStckM.findStckMarcap(STOCK_DT_ST,STOCK_DT_ED,STOCK_CD,p);
		List<StckMarcap> al=pg.toList();
		
		/*2020-12-27 메모리 누수로 가 생각했다.
		 * 동일 br을 2번 호출했을 때 문제가 되었다.
		 * 그런데 디버거를 걸고 보니까.  demoJpa는 아예 들어오지도 못했고
		 * demoWeb에서 컨트롤도 타지 못했다.
		 * 그말은 demoWeb에서 br호출하는 컨트롤도 시스템이 먹통이되서 처리 못했다는 거다.
		 * 이런거는 사실 각 업무단에서 제어하는 방법 밖에 없는것 같다.
		 * rows가 5000개 정도 반화할때 문제가 된다.
		 * 실제로 보니까. db는 생각보다 빨리 리턴했는데
		 * json object 변환에서 시간이 많이 걸렸다.
		 * 그리고 결과론 적으로 이걸 모두 감싸고 있는 최상위 demoWeb에서 호출 api 컨트롤이
		 * 두번째 시도시 구동이 되지 않았다. 
		 * 대량의 데이터의 경우 페이징 처리를 하도록 해야한다.
		 * 1000개씩 했을때 문제가 없었다.
		 * 1년이 365일이므로  400개 이상은 에러가 나도록 하자ㅏ.
		 * 이것을 DA에 강제하는 법이 있을까?/!!
		 */
		
		ArrayList<HashMap<String, Object>> OUT_DATA = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < al.size(); i++) {
			StckMarcap c = al.get(i);
			HashMap<String, Object> OUT_DATA_ROW = new HashMap<String, Object>();
			OUT_DATA_ROW.put("STOCK_CD", c.getStockCd());
			OUT_DATA_ROW.put("STOCK_DT", c.getStockDt());
			OUT_DATA_ROW.put("STOCK_NM", c.getStockNm());
			OUT_DATA_ROW.put("CLS_AMT", c.getClsAmt());
			OUT_DATA_ROW.put("CHANGES_AMT", c.getChangesAmt());
			OUT_DATA_ROW.put("CHANGES_RT", c.getChangesRt());
			
			OUT_DATA_ROW.put("TRADE_QTY", c.getTradeQty());
			OUT_DATA_ROW.put("TRADE_AMT", c.getTradeAmt());
			OUT_DATA_ROW.put("START_AMT", c.getStartAmt());
			OUT_DATA_ROW.put("HIGH_AMT", c.getHighAmt());
			OUT_DATA_ROW.put("LOW_AMT", c.getLowAmt());
			OUT_DATA_ROW.put("TOTAL_MRKT_AMT", c.getTotalMrktAmt());
			OUT_DATA_ROW.put("TOTAL_MRKT_AMT_RT", c.getTotalMrktAmtrt());
			OUT_DATA_ROW.put("STOCK_CNT", c.getStockCnt());
			OUT_DATA_ROW.put("FRGN_CNT", c.getFrgnCnt());
			OUT_DATA_ROW.put("FRGN_RT", c.getFrgnRt());
			OUT_DATA_ROW.put("RNK", c.getRnk());
			
			OUT_DATA_ROW.put("CRT_DTM", PjtUtil.getYyyyMMddHHMMSS(c.getCrtDtm()));
			OUT_DATA_ROW.put("UPDT_DTM", PjtUtil.getYyyyMMddHHMMSS(c.getUpdtDtm()));
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
