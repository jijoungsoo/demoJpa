package com.example.demo.br.kiw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ctrl.PAGE_DATA_ROW;
import com.example.demo.db.da.kiw.DA_KIW_STOCK_MST_MAPPER;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;


@Slf4j
@RestController
@Tag(name = "STCK", description = "주식")
public class BR_KIW_MST_FIND {

	@JsonRootName("IN_DS")
	@Schema(name="IN_DS-BR_AV_MV_FIND")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_AV_MV_FIND", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
		
		@JsonProperty("PAGE_DATA")
		@Schema(name="PAGE_DATA-BR_AV_MV_FIND", description = "페이지 데이터")
		PAGE_DATA_ROW PAGE_DATA = new PAGE_DATA_ROW();
	}

	@Schema(name = "IN_DATA_ROW-BR_AV_MV_FIND")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("MARKET_CD")
		@Schema(name = "MARKET_CD", example = "jijs", description = "사용자ID")
		String MARKET_CD = "";
		@JsonProperty("STOCK_CD")
		@Schema(name = "STOCK_CD", example = "jijs", description = "사용자ID")
		String STOCK_CD = "";
		@JsonProperty("STOCK_NM")
		@Schema(name = "STOCK_NM", example = "jijs", description = "사용자ID")
		String STOCK_NM = "";
	}
	
	@JsonRootName("OUT_DS")
	@Schema(name = "OUT_DS-BR_AV_MV_FIND")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_AV_MV_FIND", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
		
		@JsonProperty("PAGE_DATA")
		@Schema(name="PAGE_DATA-BR_AV_MV_FIND", description = "페이지 데이터")
		PAGE_DATA_ROW PAGE_DATA;
	}

	@Schema(name = "OUT_DATA_ROW-BR_AV_ACTR_FIND")
	@Data
	static class OUT_DATA_ROW {
		@JsonProperty("STOCK_CD")
		@Schema(name = "STOCK_CD", example = "1", description = "사용자NO")
		String STOCK_CD = null;
		@JsonProperty("LIST_MARKET_NM")
		@Schema(name = "LIST_MARKET_NM", example = "jijs", description = "사용자ID")
		String LIST_MARKET_NM = null;
		@JsonProperty("MARKET_CNT")
		@Schema(name = "MARKET_CNT", example = "****", description = "사용자패스워드")
		String MARKET_CNT = null;
		@JsonProperty("OPEN_DT")
		@Schema(name = "OPEN_DT", example = "홍길동", description = "사용자명")
		String OPEN_DT = null;
		@JsonProperty("STOCK_NM")
		@Schema(name = "STOCK_NM", example = "admin@gogo.com", description = "이메일")
		String STOCK_NM = null;
		@JsonProperty("CLS_AMT")
		@Schema(name = "CLS_AMT", example = "admin@gogo.com", description = "이메일")
		String CLS_AMT = null;
		@JsonProperty("STOCK_CNT")
		@Schema(name = "STOCK_CNT", example = "admin@gogo.com", description = "이메일")
		String STOCK_CNT = null;
		@JsonProperty("CONSTRUCTION")
		@Schema(name = "CONSTRUCTION", example = "admin@gogo.com", description = "이메일")
		String CONSTRUCTION = null;
		@JsonProperty("STOCK_STATE")
		@Schema(name = "STOCK_STATE", example = "admin@gogo.com", description = "이메일")
		String STOCK_STATE = null;
		@JsonProperty("CRT_DTM")
		@Schema(name = "CRT_DTM", example = "admin@gogo.com", description = "이메일")
		String CRT_DTM = null;
		@JsonProperty("UPDT_DTM")
		@Schema(name = "UPDT_DTM", example = "admin@gogo.com", description = "이메일")
		String UPDT_DTM = null;
	}
	
	@Autowired
	DA_KIW_STOCK_MST_MAPPER daStM;


	@Operation(summary = "주식 KIW 마스터 조회.", description = "")
	@PostMapping(path= "/api/BR_KIW_MST_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS run(IN_DS inDS) throws BizException {
		if(inDS.IN_DATA==null) {
			throw new BizRuntimeException("[IN_DATA]입력파라미터가 전달되지 않았습니다.");
		}
		if(inDS.IN_DATA.size()!=1) {
			throw new BizRuntimeException("[IN_DATA]입력파라미터의 ["+inDS.IN_DATA.size()+"]행수가 잘못되었습니다.");
		}
		
		IN_DATA_ROW  rs =inDS.IN_DATA.get(0);
		String  MARKET_CD 		= rs.MARKET_CD;
		String  STOCK_CD 		= rs.STOCK_CD;
		String  STOCK_NM 		= rs.STOCK_NM;
		
		if(inDS.PAGE_DATA==null) {
			throw new BizRuntimeException("[PAGE_DATA]입력파라미터가 전달되지 않았습니다.");
		}		
		Pageable p = inDS.PAGE_DATA.getPageable();
		List<Map> al=null;
		try {
			al = daStM.findKiwMst(MARKET_CD,STOCK_CD,STOCK_NM,p.getPageNumber()*p.getPageSize(),p.getPageSize());
		} catch (Exception e) {
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
		OUT_DS outDs = new OUT_DS();
		for (int i = 0; i < al.size(); i++) {
			Map c = al.get(i);
			OUT_DATA_ROW row = new OUT_DATA_ROW();
			row.STOCK_CD= c.get("stock_cd").toString();
			row.LIST_MARKET_NM= c.get("list_market_nm").toString();
			row.MARKET_CNT= c.get("market_cnt").toString();
			row.OPEN_DT= c.get("open_dt").toString();
			row.STOCK_NM= c.get("stock_nm").toString();
			row.CLS_AMT= c.get("cls_amt").toString();
			row.STOCK_CNT= c.get("stock_cnt").toString();
			row.CONSTRUCTION= c.get("construction").toString();
			row.STOCK_STATE= c.get("stock_state").toString();
			row.CRT_DTM= c.get("crt_dtm").toString();
			row.UPDT_DTM= c.get("updt_dtm").toString();
			outDs.OUT_DATA.add(row);
		}

		PAGE_DATA_ROW page_data = new PAGE_DATA_ROW(pg);
		outDs.PAGE_DATA=page_data;
		return outDs;
	}
}
