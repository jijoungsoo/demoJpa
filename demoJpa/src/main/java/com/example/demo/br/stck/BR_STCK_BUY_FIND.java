package com.example.demo.br.stck;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.PAGE_DATA_ROW;
import com.example.demo.db.da.stck.DA_STCK_BUY;
import com.example.demo.db.domain.stck.QStBuy;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "STCK", description = "주식")
@Slf4j
@OpService
@Service
public class BR_STCK_BUY_FIND {

	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_STCK_BUY_FIND")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;
		
		@JsonProperty("PAGE_DATA")
		PAGE_DATA_ROW PAGE_DATA;
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_STCK_BUY_FIND")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_AV_ACTR_FIND", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
		
		@JsonProperty("PAGE_DATA")
		@Schema(name="PAGE_DATA-BR_AV_MV_FIND", description = "페이지 데이터")
		PAGE_DATA_ROW PAGE_DATA;
	}

	@ApiModel(value="OUT_DATA_ROW-BR_STCK_BUY_FIND")
	@Data
	static class OUT_DATA_ROW {
		@JsonProperty("BUY_SEQ")
		@Schema(name = "BUY_SEQ", example = "1", description = "주식팔자SEQ")
		String BUY_SEQ = null;
		@JsonProperty("STOCK_CD")
		@Schema(name = "STOCK_CD", example = "00005", description = "종목코드")
		String STOCK_CD = null;
		@JsonProperty("STOCK_NM")
		@Schema(name = "STOCK_NM", example = "삼성전자", description = "종목명")
		String STOCK_NM = null;
		@JsonProperty("BUY_AMT")
		@Schema(name = "BUY_AMT", example = "1000", description = "구매단가")
		String BUY_AMT = null;
		@JsonProperty("CURR_AMT")
		@Schema(name = "CURR_AMT", example = "800", description = "현재단가")
		String CURR_AMT = null;
		@JsonProperty("DIFF_AMT")
		@Schema(name = "DIFF_AMT", example = "(800-1000)", description = "현재단가-구매단가")
		String DIFF_AMT = null;
		@JsonProperty("BNFT_RT")
		@Schema(name = "BNFT_RT", example = "0.2", description = "이익율(현재단가-구매단가)/구매단가")
		String BNFT_RT = null;
		@JsonProperty("CNT")
		@Schema(name = "CNT", example = "admin@gogo.com", description = "이메일")
		String CNT = null;
		@JsonProperty("BAL_CNT")
		@Schema(name = "BAL_CNT", example = "3", description = "구매수량")
		String BAL_CNT = null;
		@JsonProperty("TOT_BAL_AMT")
		@Schema(name = "TOT_BAL_AMT", example = "10000", description = "총구매금액(구매수량*구매단가)")
		String TOT_BAL_AMT = null;
		@JsonProperty("TOT_BAL_CURR_AMT")
		@Schema(name = "TOT_BAL_CURR_AMT", example = "800", description = "총현재금액(구매수량*현재단가)")
		String TOT_BAL_CURR_AMT = null;
		@JsonProperty("TOT_DIFF_BAL_AMT")
		@Schema(name = "TOT_DIFF_BAL_AMT", example = "(10000-800)", description = "(총현재금액-총구매금액)")
		String TOT_DIFF_BAL_AMT = null;
		
		@JsonProperty("FEE")
		@Schema(name = "FEE", example = "10", description = "수수료")
		String FEE = null;
		
		@JsonProperty("RMK")
		@Schema(name = "RMK", example = "비고", description = "비고")
		String RMK = null;
		
		@JsonProperty("TOT_AMT")
		@Schema(name = "TOT_AMT", example = "2000", description = "전체구매금액")
		String TOT_AMT = null;
		@JsonProperty("BUY_DATE")
		@Schema(name = "BUY_DATE", example = "20201231", description = "구매일자")
		String BUY_DATE = null;
		
		@JsonProperty("CRT_USR_NO")
		@Schema(name = "CRT_USR_NO", example = "1", description = "생성자NO")
		String CRT_USR_NO = null;
		
		@JsonProperty("UPDT_USR_NO")
		@Schema(name = "UPDT_USR_NO", example = "1", description = "수정자NO")
		String UPDT_USR_NO = null;
		
		@JsonProperty("CRT_DTM")
		@Schema(name = "CRT_DTM", example = "202012311640", description = "생성일시")
		String CRT_DTM = null;
		
		@JsonProperty("UPDT_DTM")
		@Schema(name = "UPDT_DTM", example = "202012311640", description = "수정일시")
		String UPDT_DTM = null;
	}
	
	@Autowired
	DA_STCK_BUY daStckB;

	@Autowired
	PjtUtil pjtU;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"STCK"},value = "산주식 조회", notes = "")
	//@PostMapping(path= "/api/BR_STCK_BUY_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.PAGE_DATA==null) {
			throw new BizRuntimeException("[PAGE_DATA]입력파라미터가 전달되지 않았습니다.");
		}
		
		PAGE_DATA_ROW rs_page =inDS.PAGE_DATA;
		Pageable p = rs_page.getPageable();
		Page<Tuple>  pg = daStckB.findStckBuy(p);
		List<Tuple> al=pg.toList();
		
		
		OUT_DS outDs = new OUT_DS();
		for (int i = 0; i < al.size(); i++) {
			Tuple c = al.get(i);
			OUT_DATA_ROW row = new OUT_DATA_ROW();
			
			row.BUY_SEQ= c.get(QStBuy.stBuy.buySeq).toString();
			row.STOCK_CD= c.get(QStBuy.stBuy.stockCd);
			row.STOCK_NM= c.get(QStBuy.stBuy.stockNm);
			row.BUY_AMT= c.get(QStBuy.stBuy.amt).toString();
			row.CURR_AMT= c.get(Expressions.numberPath(Integer.class,"cls_amt")).toString();
			row.DIFF_AMT= 
					String.valueOf(
					c.get(Expressions.numberPath(Integer.class,"cls_amt"))-c.get(QStBuy.stBuy.amt)
					);
			row.BNFT_RT= 
					String.format("%.2f", ((double)(c.get(Expressions.numberPath(Integer.class,"cls_amt"))-c.get(QStBuy.stBuy.amt))
					/c.get(QStBuy.stBuy.amt)*100)
					);
			row.CNT= c.get(QStBuy.stBuy.cnt).toString();
			row.BAL_CNT= c.get(QStBuy.stBuy.balCnt).toString();
			row.TOT_BAL_AMT=
					String.valueOf(
							c.get(QStBuy.stBuy.balCnt)*c.get(QStBuy.stBuy.amt)
					);
			row.TOT_BAL_CURR_AMT= 
					String.valueOf(
					c.get(QStBuy.stBuy.balCnt)*c.get(Expressions.numberPath(Integer.class,"cls_amt"))
					);
			row.TOT_DIFF_BAL_AMT=
					String.valueOf(
					(c.get(QStBuy.stBuy.balCnt)*c.get(Expressions.numberPath(Integer.class,"cls_amt")))
					-(c.get(QStBuy.stBuy.balCnt)*c.get(QStBuy.stBuy.amt))
					);
			row.FEE= c.get(QStBuy.stBuy.fee).toString();
			row.RMK= c.get(QStBuy.stBuy.rmk);
			row.TOT_AMT= c.get(QStBuy.stBuy.totAmt).toString();
			row.BUY_DATE= c.get(QStBuy.stBuy.buyDate);
			row.CRT_USR_NO= c.get(QStBuy.stBuy.crtUsrNo).toString();
			row.UPDT_USR_NO= c.get(QStBuy.stBuy.updtUsrNo).toString();
			row.CRT_DTM= pjtU.getYyyy_MM_dd_HHMMSS(c.get(QStBuy.stBuy.crtDtm));
			row.UPDT_DTM= pjtU.getYyyy_MM_dd_HHMMSS(c.get(QStBuy.stBuy.updtDtm));
			outDs.OUT_DATA.add(row);
		}
		
		PAGE_DATA_ROW page_data = new PAGE_DATA_ROW(pg);
		outDs.PAGE_DATA=page_data;
		return outDs;
	}
}
