package com.example.demo.br.av.mig_av;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.ctrl.PAGE_DATA_ROW;
import com.example.demo.db.da.mig_av.DA_MIG_AV_ACTR;
import com.example.demo.db.domain.av.QAvActr;
import com.example.demo.db.domain.mig_av.MigAvActr;
import com.example.demo.exception.BizException;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.querydsl.core.Tuple;

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
public class BR_MIG_AV_ACTR_FIND {
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_MIG_AV_ACTR_FIND")
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
	@ApiModel(value="OUT_DS-BR_MIG_AV_ACTR_FIND")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_MIG_AV_ACTR_FIND", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
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
	}
	
	@Autowired
	DA_MIG_AV_ACTR daMigAvActr;
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"AV"}, value = "AV배우를 조회한다.", notes = "페이징 처리")
	@PostMapping(path= "/api/BR_MIG_AV_ACTR_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		List<MigAvActr> al=daMigAvActr.findMigAvActr();

		OUT_DS outDs = new OUT_DS();
		for (int i = 0; i < al.size(); i++) {
			MigAvActr c = al.get(i);
					
			OUT_DATA_ROW row = new OUT_DATA_ROW();
			row.ACTOR_IDX = c.getActrIdx();
			row.IMG = c.getImg();
			row.IMG_S = c.getImgS();
			row.DEBUT_DT = c.getDebutDt();
			row.NAME_KR = c.getNmKr();
			row.NAME_EN = c.getNmEn();
			row.NAME_CN = c.getNmCn();
			row.BIRTH = c.getBrth();
			row.HEIGHT = c.getHeight();
			row.SIZE = c.getSize();
			row.BRA_SIZE = c.getBrSize();
			row.O_NM = c.getONm();
			row.DSCR_TTL = c.getDscrTtl();
			row.DSCR = c.getDscr();		
			row.SYNC = c.getSync();
			row.CRT_DTM = PjtUtil.getYyyy_MM_dd_HHMMSS(c.getCrtDtm());
			outDs.OUT_DATA.add(row);
		}
		return outDs;
	}
}
