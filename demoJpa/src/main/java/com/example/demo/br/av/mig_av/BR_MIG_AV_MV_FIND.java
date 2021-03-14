package com.example.demo.br.av.mig_av;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.db.da.mig_av.DA_MIG_AV_MV;
import com.example.demo.db.domain.mig_av.MigAvMv;
import com.example.demo.exception.BizException;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
public class BR_MIG_AV_MV_FIND {
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_MIG_AV_MV_FIND")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_MIG_AV_MV_FIND")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_MIG_AV_MV_FIND", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}

	@ApiModel(value="OUT_DATA_ROW-BR_MIG_AV_MV_FIND")
	@Data
	static class OUT_DATA_ROW {
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
		@JsonProperty("TITLE_KR")
		@Schema(name = "TITLE_KR", example = "타이틀", description = "TITLE_KR")
		String TITLE_KR = null;
		@JsonProperty("MAIN_ACTOR_NM")
		@Schema(name = "MAIN_ACTOR_NM", example = "마사카", description = "메인배우")
		String MAIN_ACTOR_NM = null;
		@JsonProperty("MAIN_ACTOR_IDX")
		@Schema(name = "MAIN_ACTOR_IDX", example = "1", description = "MAIN_ACTOR_IDX")
		String MAIN_ACTOR_IDX = null;

		@JsonProperty("ACTR_NM")
		@Schema(name = "ACTR_NM", example = "3 4 5", description = "배우들")
		String ACTR_NM = null;

		@JsonProperty("OPEN_DT")
		@Schema(name = "OPEN_DT", example = "20201231", description = "출시일")
		String OPEN_DT = null;
		@JsonProperty("COMP_NM")
		@Schema(name = "COMP_NM", example = "정말", description = "발매회사")
		String COMP_NM = null;
		
		@JsonProperty("LABEL")
		@Schema(name = "LABEL", example = "LABEL", description = "LABEL")
		String LABEL = null;

		@JsonProperty("SYNC")
		@Schema(name = "SYNC", example = "Y,N", description = "SYNC")
		String SYNC = null;


		@JsonProperty("BEST_YN")
		@Schema(name = "BEST_YN", example = "Y", description = "베스트 작품 여부")
		String BEST_YN = null;

		
		@JsonProperty("SERIES")
		@Schema(name = "SERIES", example = "가자", description = "시리즈")
		String SERIES = null;

		
		@JsonProperty("DIRECTOR")
		@Schema(name = "DIRECTOR", example = "김독", description = "감독")
		String DIRECTOR = null;

		
		@JsonProperty("RUN_TIME")
		@Schema(name = "RUN_TIME", example = "1시간30분", description = "런타임")
		String RUN_TIME = null;

		@JsonProperty("STORY_KR")
		@Schema(name = "STORY_KR", example = "그녀는 야이야", description = "스토리")
		String STORY_KR = null;

		@JsonProperty("GEN_LIST")
		@Schema(name = "GEN_LIST", example = "장르", description = "장르")
		String GEN_LIST = null;
		
		@JsonProperty("CRT_DTM")
		@Schema(name = "CRT_DTM", example = "202012311640", description = "생성일시")
		String CRT_DTM = null;
	}
	
	@Autowired
	DA_MIG_AV_MV daMigAvMv;
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"AV"}, value = "AV작품을 조회한다.", notes = "페이징 처리")
	@PostMapping(path= "/api/BR_MIG_AV_MV_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		List<MigAvMv> al=daMigAvMv.findMigAvMv();

		OUT_DS outDs = new OUT_DS();
		for (int i = 0; i < al.size(); i++) {
			MigAvMv c = al.get(i);
					
			OUT_DATA_ROW row = new OUT_DATA_ROW();
			row.DVD_IDX = c.getDvdIdx();
			row.IMG_A = c.getImgA();
			row.IMG_AS = c.getImgAs();
			row.IMG_N = c.getImgN();
			row.IMG_NS = c.getImgNs();
			row.MV_NM = c.getMvNm();
			row.TITLE_KR = c.getTtlKr();
			row.MAIN_ACTOR_NM = c.getMnActrNm();
			row.MAIN_ACTOR_IDX = String.valueOf(c.getMnActrIdx()) ;
			row.ACTR_NM = c.getActrNm();
			row.OPEN_DT = c.getOpenDt();
			row.COMP_NM = c.getCmpNm();
			row.LABEL = c.getLbl();
			row.SYNC = c.getSync();
			row.BEST_YN = c.getBestYn();
			row.SERIES = c.getSeries();
			row.DIRECTOR = c.getDrctr();
			row.RUN_TIME = c.getRnTm();
			row.STORY_KR = c.getStryKr();
			row.GEN_LIST = c.getGenLst();
			row.CRT_DTM = PjtUtil.getYyyy_MM_dd_HHMMSS(c.getCrtDtm());
			outDs.OUT_DATA.add(row);
		}
		return outDs;
	}
}
