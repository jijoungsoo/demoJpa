package com.example.demo.br.av.mig_av;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.PAGE_DATA_ROW;
import com.example.demo.db.da.mig_av.DA_MIG_AV_MV;
import com.example.demo.db.domain.mig_av.QMigAvActr;
import com.example.demo.db.domain.mig_av.QMigAvMv;
import com.example.demo.exception.BizException;
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

@Tag(name = "AV", description = "AV정보")
@Slf4j
@OpService
@Service
public class BR_MIG_AV_MV_FIND {

	@Autowired
    PjtUtil pjtU;
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_MIG_AV_MV_FIND")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_MIG_AV_MV_FIND", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();

		
		@JsonProperty("PAGE_DATA")
		PAGE_DATA_ROW PAGE_DATA;
	}

	@ApiModel(value="IN_DATA_ROW-BR_MIG_AV_MV_FIND")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("AGE")
		@Schema(name = "AGE", example = "1", description = "AGE")
		String AGE = "";

		@JsonProperty("SEARCH_NM")
		@Schema(name = "SEARCH_NM", example = "1", description = "검색어")
		String SEARCH_NM = "";
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_MIG_AV_MV_FIND")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_MIG_AV_MV_FIND", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();

		@JsonProperty("PAGE_DATA")
		@Schema(name="PAGE_DATA-BR_MIG_AV_ACTR_FIND", description = "페이지 데이터")
		PAGE_DATA_ROW PAGE_DATA;
	}

	@ApiModel(value="OUT_DATA_ROW-BR_MIG_AV_MV_FIND")
	@Data
	static class OUT_DATA_ROW {
		@JsonProperty("DEL_YN")
		@Schema(name = "DEL_YN", example = "adf.jpg", description = "DEL_YN")
		String DEL_YN = null;

		@JsonProperty("DVD_IDX")
		@Schema(name = "DVD_IDX", example = "1", description = "DVD IDX")
		Long DVD_IDX = null;
		@JsonProperty("IMG_A")
		@Schema(name = "IMG_A", example = "adf.jpg", description = "IMG_A")
		String IMG_A = null;
		@JsonProperty("IMG_AS")
		@Schema(name = "IMG_AS", example = "adf.jpg", description = "IMG_AS")
		String IMG_AS = null;
		@JsonProperty("IMG_N")
		@Schema(name = "IMG_N", example = "adf.jpg", description = "IMG_N")
		String IMG_N = null;
		@JsonProperty("IMG_NS")
		@Schema(name = "IMG_NS", example = "adf.jpg", description = "IMG_NS")
		String IMG_NS = null;
		@JsonProperty("MV_NM")
		@Schema(name = "MV_NM", example = "masaka", description = "MV_NM")
		String MV_NM = null;
		@JsonProperty("TTL_KR")
		@Schema(name = "TTL_KR", example = "타이틀", description = "TTL_KR")
		String TTL_KR = null;
		@JsonProperty("MAIN_ACTOR_NM")
		@Schema(name = "MAIN_ACTOR_NM", example = "마사카", description = "메인배우")
		String MAIN_ACTOR_NM = null;
		@JsonProperty("MAIN_ACTOR_IDX")
		@Schema(name = "MAIN_ACTOR_IDX", example = "1", description = "MAIN_ACTOR_IDX")
		Long MAIN_ACTOR_IDX = null;
		

		@JsonProperty("ACTR_NM")
		@Schema(name = "ACTR_NM", example = "3 4 5", description = "배우들")
		String ACTR_NM = null;

		@JsonProperty("OPEN_DT")
		@Schema(name = "OPEN_DT", example = "20201231", description = "출시일")
		String OPEN_DT = null;
		@JsonProperty("CMP_NM")
		@Schema(name = "CMP_NM", example = "정말", description = "발매회사")
		String CMP_NM = null;
		
		@JsonProperty("LBL")
		@Schema(name = "LBL", example = "LABEL", description = "LBL")
		String LBL = null;

		@JsonProperty("SYNC")
		@Schema(name = "SYNC", example = "Y,N", description = "SYNC")
		String SYNC = null;


		@JsonProperty("BEST_YN")
		@Schema(name = "BEST_YN", example = "Y", description = "베스트 작품 여부")
		String BEST_YN = null;

		
		@JsonProperty("SERIES")
		@Schema(name = "SERIES", example = "가자", description = "시리즈")
		String SERIES = null;

		
		@JsonProperty("DRCTR")
		@Schema(name = "DRCTR", example = "김독", description = "감독")
		String DRCTR = null;

		
		@JsonProperty("RN_TM")
		@Schema(name = "RN_TM", example = "1시간30분", description = "런타임")
		String RN_TM = null;

		@JsonProperty("STRY_KR")
		@Schema(name = "STRY_KR", example = "그녀는 야이야", description = "스토리")
		String STRY_KR = null;

		@JsonProperty("GEN_LST")
		@Schema(name = "GEN_LST", example = "장르", description = "장르")
		String GEN_LST = null;

		@JsonProperty("AGE")
		@Schema(name = "AGE", example = "1", description = "나이")
		String AGE = null;
		
		@JsonProperty("CRT_DTM")
		@Schema(name = "CRT_DTM", example = "202012311640", description = "생성일시")
		String CRT_DTM = null;

		@JsonProperty("DVD_CNT")
		@Schema(name = "DVD_CNT", example = "1", description = "비디오수")
		Long DVD_CNT = null;

		@JsonProperty("ACTOR_CMT_CNT")
		@Schema(name = "ACTOR_CMT_CNT", example = "1", description = "베우댓글수")
		Long ACTOR_CMT_CNT = null;

		@JsonProperty("MV_CMT_CNT")
		@Schema(name = "MV_CMT_CNT", example = "1", description = "작품댓글수")
		Long MV_CMT_CNT = null;


		@JsonProperty("ACTOR_DSLK_CNT")
		@Schema(name = "ACTOR_DSLK_CNT", example = "1", description = "베스트비디오수")
		Long ACTOR_DSLK_CNT = null;

		@JsonProperty("ACTOR_LK_CNT")
		@Schema(name = "ACTOR_LK_CNT", example = "1", description = "베우댓글수")
		Long ACTOR_LK_CNT = null;

	}
	
	@Autowired
	DA_MIG_AV_MV daMigAvMv;
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"AV"}, value = "AV작품을 조회한다.", notes = "페이징 처리")
	//@PostMapping(path= "/api/BR_MIG_AV_MV_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		PAGE_DATA_ROW rs_page =inDS.PAGE_DATA;
		Pageable p = rs_page.getPageable();

		String SEARCH_AGE = null;
		String SEARCH_NM = null;
		if(inDS.IN_DATA!=null) {
			if(inDS.IN_DATA.size()>0) {
				SEARCH_AGE  =inDS.IN_DATA.get(0).AGE;
				SEARCH_NM  =inDS.IN_DATA.get(0).SEARCH_NM;
			}	
		}
		
		Page<Tuple> pg=daMigAvMv.findMigAvMv(p,SEARCH_AGE,SEARCH_NM);
		List<Tuple> al=pg.toList();

		OUT_DS outDs = new OUT_DS();
		for (int i = 0; i < al.size(); i++) {
			Tuple c = al.get(i);
					
			OUT_DATA_ROW row = new OUT_DATA_ROW();
			row.DEL_YN = c.get(QMigAvMv.migAvMv.delYn);
			row.DVD_IDX = c.get(QMigAvMv.migAvMv.dvdIdx);
			row.IMG_AS = c.get(QMigAvMv.migAvMv.imgAs);
			row.IMG_NS = c.get(QMigAvMv.migAvMv.imgNs);
			row.MV_NM = c.get(QMigAvMv.migAvMv.mvNm);
			row.TTL_KR = c.get(QMigAvMv.migAvMv.ttlKr);
			row.MAIN_ACTOR_IDX = c.get(QMigAvMv.migAvMv.mnActrIdx);
			row.MAIN_ACTOR_NM  = c.get(QMigAvActr.migAvActr.nmKr);
			row.ACTR_NM = c.get(QMigAvMv.migAvMv.actrNm);
			row.OPEN_DT = c.get(QMigAvMv.migAvMv.openDt);
			row.CMP_NM = c.get(QMigAvMv.migAvMv.cmpNm);
			row.LBL = c.get(QMigAvMv.migAvMv.lbl);
			row.SYNC = c.get(QMigAvMv.migAvMv.sync);
			row.BEST_YN = c.get(QMigAvMv.migAvMv.bestYn);
			row.SERIES = c.get(QMigAvMv.migAvMv.series);
			row.DRCTR = c.get(QMigAvMv.migAvMv.drctr);
			row.RN_TM = c.get(QMigAvMv.migAvMv.rnTm);
			row.STRY_KR = c.get(QMigAvMv.migAvMv.stryKr);
			row.GEN_LST =  c.get(QMigAvMv.migAvMv.genLst);
			row.CRT_DTM = pjtU.getYyyy_MM_dd_HHMMSS(c.get(QMigAvMv.migAvMv.crtDtm));
			row.AGE = c.get(Expressions.stringPath("age"));
			row.DVD_CNT = c.get(Expressions.numberPath(Long.class,"dvd_cnt"));
			row.ACTOR_CMT_CNT = c.get(Expressions.numberPath(Long.class,"actor_cmt_cnt"));
			row.MV_CMT_CNT = c.get(Expressions.numberPath(Long.class,"mv_cmt_cnt"));
			row.ACTOR_LK_CNT = c.get(Expressions.numberPath(Long.class,"actor_lk_cnt"));
			row.ACTOR_DSLK_CNT = c.get(Expressions.numberPath(Long.class,"actor_dslk_cnt"));
			outDs.OUT_DATA.add(row);
		}
		PAGE_DATA_ROW page_data = new PAGE_DATA_ROW(pg);
		outDs.PAGE_DATA=page_data;
		return outDs;
	}
}
