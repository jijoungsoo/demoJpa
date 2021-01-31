package com.example.demo.br.stck;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ctrl.PAGE_DATA_ROW;
import com.example.demo.db.da.stck.DA_STCK_SELL;
import com.example.demo.db.domain.stck.QStBuy;
import com.example.demo.db.domain.stck.QStSell;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.querydsl.core.Tuple;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;


@Slf4j
@RestController
@Tag(name = "STCK", description = "주식")
public class BR_STCK_SELL_FIND {

	@JsonRootName("IN_DS")
	@Schema(name="IN_DS-BR_AV_ACTR_FIND")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_AV_ACTR_FIND", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
		
		@JsonProperty("PAGE_DATA")
		@Schema(name="PAGE_DATA-BR_AV_MV_FIND", description = "페이지 데이터")
		PAGE_DATA_ROW PAGE_DATA;
	}

	@Schema(name = "IN_DATA_ROW-BR_AV_ACTR_FIND")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("USER_ID")
		@Schema(name = "USER_ID", example = "jijs", description = "사용자ID")
		String USER_ID = "";
	}
	
	@JsonRootName("OUT_DS")
	@Schema(name = "OUT_DS-BR_AV_ACTR_FIND")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_AV_ACTR_FIND", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
		
		@JsonProperty("PAGE_DATA")
		@Schema(name="PAGE_DATA-BR_AV_MV_FIND", description = "페이지 데이터")
		PAGE_DATA_ROW PAGE_DATA;
	}

	@Schema(name = "OUT_DATA_ROW-BR_AV_ACTR_FIND")
	@Data
	static class OUT_DATA_ROW {
		@JsonProperty("SELL_SEQ")
		@Schema(name = "SELL_SEQ", example = "1", description = "사용자NO")
		String SELL_SEQ = null;
		@JsonProperty("STOCK_CD")
		@Schema(name = "STOCK_CD", example = "jijs", description = "사용자ID")
		String STOCK_CD = null;
		@JsonProperty("STOCK_NM")
		@Schema(name = "STOCK_NM", example = "****", description = "사용자패스워드")
		String STOCK_NM = null;
		@JsonProperty("AMT")
		@Schema(name = "AMT", example = "홍길동", description = "사용자명")
		String AMT = null;
		@JsonProperty("BUY_AMT")
		@Schema(name = "BUY_AMT", example = "admin@gogo.com", description = "이메일")
		String BUY_AMT = null;
		@JsonProperty("DIFF_AMT")
		@Schema(name = "DIFF_AMT", example = "admin@gogo.com", description = "이메일")
		String DIFF_AMT = null;
		@JsonProperty("TOT_BNFT_AMT")
		@Schema(name = "TOT_BNFT_AMT", example = "admin@gogo.com", description = "이메일")
		String TOT_BNFT_AMT = null;
		@JsonProperty("BNFT_RT")
		@Schema(name = "BNFT_RT", example = "admin@gogo.com", description = "이메일")
		String BNFT_RT = null;
		@JsonProperty("CNT")
		@Schema(name = "CNT", example = "admin@gogo.com", description = "이메일")
		String CNT = null;
		@JsonProperty("FEE")
		@Schema(name = "FEE", example = "admin@gogo.com", description = "이메일")
		String FEE = null;
		@JsonProperty("TAX")
		@Schema(name = "TAX", example = "admin@gogo.com", description = "이메일")
		String TAX = null;
		@JsonProperty("TOT_BUY_AMT")
		@Schema(name = "TOT_BUY_AMT", example = "admin@gogo.com", description = "이메일")
		String TOT_BUY_AMT = null;
		@JsonProperty("TOT_AMT")
		@Schema(name = "TOT_AMT", example = "admin@gogo.com", description = "이메일")
		String TOT_AMT = null;
		@JsonProperty("BUY_DATE")
		@Schema(name = "BUY_DATE", example = "admin@gogo.com", description = "이메일")
		String SELL_DATE = null;
		@JsonProperty("SELL_DATE")
		@Schema(name = "SELL_DATE", example = "admin@gogo.com", description = "이메일")
		String CRT_USR_NO = null;
		@JsonProperty("UPDT_USR_NO")
		@Schema(name = "UPDT_USR_NO", example = "admin@gogo.com", description = "이메일")
		String UPDT_USR_NO = null;
		@JsonProperty("CRT_DTM")
		@Schema(name = "CRT_DTM", example = "admin@gogo.com", description = "이메일")
		String CRT_DTM = null;
		@JsonProperty("UPDT_DTM")
		@Schema(name = "UPDT_DTM", example = "admin@gogo.com", description = "이메일")
		String UPDT_DTM = null;
	}
	
	@Autowired
	DA_STCK_SELL daStckS;
	
	@Operation(summary = "판주식 조회.", description = "")
	@PostMapping(path= "/api/BR_STCK_SELL_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS  run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.IN_DATA==null) {
			throw new BizRuntimeException("[IN_DATA]입력파라미터가 전달되지 않았습니다.");
		}

		if(inDS.PAGE_DATA==null) {
			throw new BizRuntimeException("[PAGE_DATA]입력파라미터가 전달되지 않았습니다.");
		}
		
		PAGE_DATA_ROW rs_page =inDS.PAGE_DATA;
		Pageable p = rs_page.getPageable();
		Page<Tuple>  pg = daStckS.findStckSell(p);
		List<Tuple> al=pg.toList();
		
		OUT_DS outDs = new OUT_DS();
		for (int i = 0; i < al.size(); i++) {
			Tuple c = al.get(i);
					
			OUT_DATA_ROW row = new OUT_DATA_ROW();
			row.SELL_SEQ= c.get(QStSell.stSell.sellSeq).toString();
			row.STOCK_CD= c.get(QStSell.stSell.stockCd);
			
			row.STOCK_NM= c.get(QStSell.stSell.stockNm);
			row.AMT= c.get(QStSell.stSell.amt).toString();
			row.BUY_AMT= c.get(QStBuy.stBuy.amt).toString();	
			
			row.DIFF_AMT=String.valueOf(c.get(QStSell.stSell.amt)-c.get(QStBuy.stBuy.amt));
			
			row.TOT_BNFT_AMT=
					String.valueOf(
					c.get(QStSell.stSell.totAmt)-(c.get(QStBuy.stBuy.amt) * c.get(QStSell.stSell.cnt))
					);
			row.BNFT_RT= 
					String.format("%.2f", ((double)(c.get(QStSell.stSell.amt)-c.get(QStBuy.stBuy.amt))
					/c.get(QStSell.stSell.amt)*100)
					);
			
			row.CNT= c.get(QStSell.stSell.cnt).toString();
			row.FEE= c.get(QStSell.stSell.fee).toString();
			row.TAX= c.get(QStSell.stSell.tax).toString();
			row.TOT_BUY_AMT=
					String.valueOf(
					c.get(QStBuy.stBuy.amt) * c.get(QStSell.stSell.cnt)
					);
			row.TOT_AMT= c.get(QStSell.stSell.totAmt).toString();
			row.SELL_DATE= c.get(QStSell.stSell.sellDate);
			row.CRT_USR_NO= c.get(QStSell.stSell.crtUsrNo).toString();
			row.UPDT_USR_NO= c.get(QStSell.stSell.updtUsrNo).toString();
			row.CRT_DTM= PjtUtil.getYyyy_MM_dd_HHMMSS(c.get(QStSell.stSell.crtDtm));
			row.UPDT_DTM= PjtUtil.getYyyy_MM_dd_HHMMSS(c.get(QStSell.stSell.updtDtm));
			outDs.OUT_DATA.add(row);
		}
	
		PAGE_DATA_ROW page_data = new PAGE_DATA_ROW(pg);
		outDs.PAGE_DATA=page_data;
		return outDs;
	}
}
