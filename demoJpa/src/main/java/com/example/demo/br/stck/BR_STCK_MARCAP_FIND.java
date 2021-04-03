package com.example.demo.br.stck;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.PAGE_DATA_ROW;
import com.example.demo.db.da.stck.DA_STCK_MARCAP;
import com.example.demo.db.domain.marcap.StckMarcap;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@OpService
@Service
@Tag(name = "STCK", description = "주식")
public class BR_STCK_MARCAP_FIND {

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
		@JsonProperty("STOCK_DT_ST")
		@Schema(name = "STOCK_DT_ST", example = "jijs", description = "사용자ID")
		String STOCK_DT_ST = "";
		
		@JsonProperty("STOCK_DT_ED")
		@Schema(name = "STOCK_DT_ED", example = "jijs", description = "사용자ID")
		String STOCK_DT_ED = "";
		
		@JsonProperty("STOCK_CD")
		@Schema(name = "STOCK_CD", example = "jijs", description = "사용자ID")
		String STOCK_CD = "";
		
		
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
		@JsonProperty("STOCK_DT")
		@Schema(name = "STOCK_DT", example = "1", description = "사용자NO")
		String STOCK_DT = null;
		@JsonProperty("STOCK_CD")
		@Schema(name = "STOCK_CD", example = "jijs", description = "사용자ID")
		String STOCK_CD = null;
		@JsonProperty("STOCK_NM")
		@Schema(name = "STOCK_NM", example = "****", description = "사용자패스워드")
		String STOCK_NM = null;
		@JsonProperty("CLS_AMT")
		@Schema(name = "CLS_AMT", example = "홍길동", description = "사용자명")
		String CLS_AMT = null;
		@JsonProperty("CHANGES_AMT")
		@Schema(name = "CHANGES_AMT", example = "admin@gogo.com", description = "이메일")
		String CHANGES_AMT = null;
		@JsonProperty("CHANGES_RT")
		@Schema(name = "CHANGES_RT", example = "admin@gogo.com", description = "이메일")
		String CHANGES_RT = null;
		@JsonProperty("TRADE_QTY")
		@Schema(name = "TRADE_QTY", example = "admin@gogo.com", description = "이메일")
		String TRADE_QTY = null;
		@JsonProperty("TRADE_AMT")
		@Schema(name = "TRADE_AMT", example = "admin@gogo.com", description = "이메일")
		String TRADE_AMT = null;
		@JsonProperty("START_AMT")
		@Schema(name = "START_AMT", example = "admin@gogo.com", description = "이메일")
		String START_AMT = null;
		@JsonProperty("HIGH_AMT")
		@Schema(name = "HIGH_AMT", example = "admin@gogo.com", description = "이메일")
		String HIGH_AMT = null;
		@JsonProperty("LOW_AMT")
		@Schema(name = "LOW_AMT", example = "admin@gogo.com", description = "이메일")
		String LOW_AMT = null;
		@JsonProperty("TOTAL_MRKT_AMT")
		@Schema(name = "TOTAL_MRKT_AMT", example = "admin@gogo.com", description = "이메일")
		String TOTAL_MRKT_AMT = null;
		@JsonProperty("TOTAL_MRKT_AMT_RT")
		@Schema(name = "TOTAL_MRKT_AMT_RT", example = "admin@gogo.com", description = "이메일")
		String TOTAL_MRKT_AMT_RT = null;
		@JsonProperty("STOCK_CNT")
		@Schema(name = "STOCK_CNT", example = "admin@gogo.com", description = "이메일")
		String STOCK_CNT = null;

		@JsonProperty("RNK")
		@Schema(name = "RNK", example = "admin@gogo.com", description = "이메일")
		String RNK = null;
		@JsonProperty("CRT_DTM")
		@Schema(name = "CRT_DTM", example = "admin@gogo.com", description = "이메일")
		String CRT_DTM = null;
		@JsonProperty("UPDT_DTM")
		@Schema(name = "UPDT_DTM", example = "admin@gogo.com", description = "이메일")
		String UPDT_DTM = null;
	}
	
	@Autowired
	DA_STCK_MARCAP daStckM;

	@Operation(summary = "marcap 주식정보 조회.", description = "")
	//@PostMapping(path= "/api/BR_STCK_MARCAP_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS run(IN_DS inDS) throws BizException {
		if(inDS.IN_DATA==null) {
			throw new BizRuntimeException("[IN_DATA]입력파라미터가 전달되지 않았습니다.");
		}
		
		if(inDS.IN_DATA.size()!=1) {
			throw new BizRuntimeException("[IN_DATA]입력파라미터가 "+inDS.IN_DATA.size()+" 개 전달되었습니다.");
		}
		
		IN_DATA_ROW  rs =inDS.IN_DATA.get(0);
		String  STOCK_DT_ST 		= PjtUtil.str(rs.STOCK_DT_ST);
		String  STOCK_DT_ED 		= PjtUtil.str(rs.STOCK_DT_ED);
		String  STOCK_CD 			= PjtUtil.str(rs.STOCK_CD);
		
		if(PjtUtil.isEmpty(STOCK_DT_ST)) {
			throw new BizRuntimeException("시작일자가 입력되지 않았습니다.");
		}
		if(STOCK_DT_ST.length()!=8) {
			throw new BizRuntimeException("["+STOCK_DT_ST+"]시작일자가 잘못입력되었습니다.");
		}
		
		if(STOCK_DT_ED.length()!=8) {
			throw new BizRuntimeException("["+STOCK_DT_ED+"]종료일자가 잘못입력되었습니다.");
		}
		
		if(inDS.PAGE_DATA==null) {
			throw new BizRuntimeException("[PAGE_DATA]입력파라미터가 전달되지 않았습니다.");
		}
		
		PAGE_DATA_ROW rs_page =inDS.PAGE_DATA;
		Pageable p = rs_page.getPageable();
		Page<StckMarcap>  pg = daStckM.findStckMarcap(STOCK_DT_ST,STOCK_DT_ED,STOCK_CD,p);
		List<StckMarcap> al=pg.toList();
		OUT_DS outDs = new OUT_DS();
		for (int i = 0; i < al.size(); i++) {
			StckMarcap c = al.get(i);
			OUT_DATA_ROW row = new OUT_DATA_ROW();
			row.STOCK_CD= c.getStockCd();
			row.STOCK_DT= c.getStockDt();
			row.STOCK_NM= c.getStockNm();
			row.CLS_AMT= c.getClsAmt().toString();
			row.CHANGES_AMT= c.getChangesAmt().toString();
			row.CHANGES_RT= c.getChangesRt().toString();
			
			row.TRADE_QTY= c.getTradeQty().toString();
			row.TRADE_AMT= c.getTradeAmt().toString();
			row.START_AMT= c.getStartAmt().toString();
			row.HIGH_AMT= c.getHighAmt().toString();
			row.LOW_AMT= c.getLowAmt().toString();
			row.TOTAL_MRKT_AMT= c.getTotalMrktAmt().toString();
			row.TOTAL_MRKT_AMT_RT= c.getTotalMrktAmtRt().toString();
			row.STOCK_CNT= c.getStockCnt().toString();
			row.RNK= c.getRnk().toString();
			
			row.CRT_DTM= PjtUtil.getYyyyMMddHHMMSS(c.getCrtDtm());
			row.UPDT_DTM= PjtUtil.getYyyyMMddHHMMSS(c.getUpdtDtm());
			outDs.OUT_DATA.add(row);
		}
		
		PAGE_DATA_ROW page_data = new PAGE_DATA_ROW(pg);
		outDs.PAGE_DATA=page_data;
		return outDs;
	}
}
