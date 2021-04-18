package com.example.demo.br.av.mig_av;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.PAGE_DATA_ROW;
import com.example.demo.db.da.mig_av.DA_MIG_AV_ACTR;
import com.example.demo.db.domain.mig_av.QMigAvActr;
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
public class BR_MIG_AV_ACTR_FIND {

	@Autowired
    PjtUtil pjtU;
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_MIG_AV_ACTR_FIND")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_MIG_AV_ACTR_FIND", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();

		@JsonProperty("PAGE_DATA")
		PAGE_DATA_ROW PAGE_DATA;
	}

	@ApiModel(value="IN_DATA_ROW-BR_MIG_AV_ACTR_FIND")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("AGE")
		@Schema(name = "AGE", example = "1", description = "AGE")
		String AGE = "";

		@JsonProperty("SEARCH_NM")
		@Schema(name = "SEARCH_NM", example = "1", description = "이름검색어")
		String SEARCH_NM = "";


		@JsonProperty("DEBUT_YYMM")
		@Schema(name = "DEBUT_YYMM", example = "1", description = "데뷔월")
		String DEBUT_YYMM = "";
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_MIG_AV_ACTR_FIND")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_MIG_AV_ACTR_FIND", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();

		@JsonProperty("PAGE_DATA")
		@Schema(name="PAGE_DATA-BR_MIG_AV_ACTR_FIND", description = "페이지 데이터")
		PAGE_DATA_ROW PAGE_DATA;
	}

	@ApiModel(value="OUT_DATA_ROW-BR_AV_ACTR_FIND")
	@Data
	static class OUT_DATA_ROW {
		@JsonProperty("ACTOR_IDX")
		@Schema(name = "ACTOR_IDX", example = "1", description = "배우IDX")
		Long ACTOR_IDX = null;
		@JsonProperty("IMG")
		@Schema(name = "IMG", example = "adf.jpg", description = "프로필")
		String IMG = null;
		@JsonProperty("IMG_S")
		@Schema(name = "IMG_S", example = "adf.jpg", description = "작은프로필")
		String IMG_S = null;
		@JsonProperty("DEBUT_DT")
		@Schema(name = "DEBUT_DT", example = "20200302", description = "데뷔일")
		String DEBUT_DT = null;
		@JsonProperty("NAME_KR")
		@Schema(name = "NAME_KR", example = "마사카", description = "이름(한글)")
		String NAME_KR = null;
		@JsonProperty("NAME_EN")
		@Schema(name = "NAME_EN", example = "masaka", description = "이름(영어)")
		String NAME_EN = null;
		@JsonProperty("NAME_CN")
		@Schema(name = "NAME_CN", example = "중국어", description = "이름(중국어)")
		String NAME_CN = null;
		@JsonProperty("BIRTH")
		@Schema(name = "BIRTH", example = "0316", description = "월일")
		String BIRTH = null;
		@JsonProperty("HEIGHT")
		@Schema(name = "HEIGHT", example = "173", description = "키")
		String HEIGHT = null;
		@JsonProperty("SIZE")
		@Schema(name = "SIZE", example = "3 4 5", description = "쓰리사이즈")
		String SIZE = null;
		@JsonProperty("BRA_SIZE")
		@Schema(name = "BRA_SIZE", example = "F", description = "브라사이즈")
		String BRA_SIZE = null;
		@JsonProperty("O_NM")
		@Schema(name = "O_NM", example = "혼또", description = "다른이름")
		String O_NM = null;
		
		@JsonProperty("DSCR_TTL")
		@Schema(name = "DSCR_TTL", example = "야호", description = "프로필상세 타이틀")
		String DSCR_TTL = null;

		@JsonProperty("DSCR")
		@Schema(name = "DSCR", example = "야호", description = "프로필 상세")
		String DSCR = null;


		@JsonProperty("SYNC")
		@Schema(name = "SYNC", example = "Y", description = "SYNC")
		String SYNC = null;
		
		@JsonProperty("CRT_DTM")
		@Schema(name = "CRT_DTM", example = "202012311640", description = "생성일시")
		String CRT_DTM = null;

		@JsonProperty("DVD_CNT")
		@Schema(name = "DVD_CNT", example = "1", description = "비디오수")
		Long DVD_CNT = null;

		@JsonProperty("BEST_DVD_CNT")
		@Schema(name = "BEST_DVD_CNT", example = "1", description = "베스트비디오수")
		Long BEST_DVD_CNT = null;

		@JsonProperty("ACTOR_CMT_CNT")
		@Schema(name = "ACTOR_CMT_CNT", example = "1", description = "베우댓글수")
		Long ACTOR_CMT_CNT = null;


		@JsonProperty("ACTOR_DSLK_CNT")
		@Schema(name = "ACTOR_DSLK_CNT", example = "1", description = "베스트비디오수")
		Long ACTOR_DSLK_CNT = null;

		@JsonProperty("ACTOR_LK_CNT")
		@Schema(name = "ACTOR_LK_CNT", example = "1", description = "베우댓글수")
		Long ACTOR_LK_CNT = null;

		

		@JsonProperty("AGE")
		@Schema(name = "AGE", example = "1", description = "나이")
		String AGE = null;

		
	}
	
	@Autowired
	DA_MIG_AV_ACTR daMigAvActr;

	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"AV"}, value = "AV배우를 조회한다.", notes = "페이징 처리")
	//@PostMapping(path= "/api/BR_MIG_AV_ACTR_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		PAGE_DATA_ROW rs_page =inDS.PAGE_DATA;
		String SEARCH_AGE = null;
		String SEARCH_NM = null;
		String DEBUT_YYMM = null;

		Pageable p = rs_page.getPageable();

		if(inDS.IN_DATA!=null) {
			if(inDS.IN_DATA.size()>0) {
				SEARCH_AGE =inDS.IN_DATA.get(0).AGE;
				SEARCH_NM  =inDS.IN_DATA.get(0).SEARCH_NM;
				DEBUT_YYMM  =inDS.IN_DATA.get(0).DEBUT_YYMM;
			}	
		}
		
		Page<Tuple>  pg=daMigAvActr.findMigAvActr(DEBUT_YYMM,SEARCH_AGE,SEARCH_NM,p);
		List<Tuple> al=pg.toList();
		OUT_DS outDs = new OUT_DS();
		for (int i = 0; i < al.size(); i++) {
			Tuple c = al.get(i);
					
			OUT_DATA_ROW row = new OUT_DATA_ROW();
			row.ACTOR_IDX = c.get(QMigAvActr.migAvActr.actrIdx);
			row.IMG = c.get(QMigAvActr.migAvActr.img);
			row.IMG_S = c.get(QMigAvActr.migAvActr.imgS);
			row.DEBUT_DT = c.get(QMigAvActr.migAvActr.debutDt);
			row.NAME_KR = c.get(QMigAvActr.migAvActr.nmKr);
			row.NAME_EN = c.get(QMigAvActr.migAvActr.nmEn);
			row.NAME_CN = c.get(QMigAvActr.migAvActr.nmCn);
			row.BIRTH = c.get(QMigAvActr.migAvActr.brth);
			row.HEIGHT = c.get(QMigAvActr.migAvActr.height);
			row.SIZE = c.get(QMigAvActr.migAvActr.size);
			row.BRA_SIZE = c.get(QMigAvActr.migAvActr.brSize);
			row.O_NM = c.get(QMigAvActr.migAvActr.oNm);
			row.DSCR_TTL = c.get(QMigAvActr.migAvActr.dscrTtl);
			row.DSCR = c.get(QMigAvActr.migAvActr.dscr);
			row.SYNC = c.get(QMigAvActr.migAvActr.sync);
			row.CRT_DTM = pjtU.getYyyy_MM_dd_HHMMSS( c.get(QMigAvActr.migAvActr.crtDtm));
			row.DVD_CNT = c.get(Expressions.numberPath(Long.class,"dvd_cnt"));
			row.BEST_DVD_CNT = c.get(Expressions.numberPath(Long.class,"best_dvd_cnt"));
			row.ACTOR_CMT_CNT = c.get(Expressions.numberPath(Long.class,"actor_cmt_cnt"));
			row.ACTOR_LK_CNT = c.get(Expressions.numberPath(Long.class,"actor_lk_cnt"));
			row.ACTOR_DSLK_CNT = c.get(Expressions.numberPath(Long.class,"actor_dslk_cnt"));
			
			//날짜형식이 나타나면 날짜로 어떠헥 해야한다.
			//row.AGE = PjtUtil.getYyyy_MM_dd_HHMMSS(c.get(Expressions.datePath(Date.class,"age")));
			//문자열이라면 String으로 받으면됨
			row.AGE = c.get(Expressions.stringPath("age"));
			outDs.OUT_DATA.add(row);
		}
		PAGE_DATA_ROW page_data = new PAGE_DATA_ROW(pg);
		outDs.PAGE_DATA=page_data;
		return outDs;
	}
}
