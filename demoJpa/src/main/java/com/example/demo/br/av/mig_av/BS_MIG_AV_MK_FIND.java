package com.example.demo.br.av.mig_av;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.PAGE_DATA_ROW;
import com.example.demo.db.da.mig_av.DA_MIG_AV_GEN;
import com.example.demo.db.da.mig_av.DA_MIG_AV_MK;
import com.example.demo.db.domain.mig_av.MigAvMk;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

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
public class BS_MIG_AV_MK_FIND {

	@Autowired
    PjtUtil pjtU;
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BS_MIG_AV_MK_FIND")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		
		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BS_MIG_AV_MK_FIND", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();

		
		@JsonProperty("PAGE_DATA")
		PAGE_DATA_ROW PAGE_DATA = new PAGE_DATA_ROW();
	}

	@ApiModel(value="IN_DATA_ROW-BS_MIG_AV_MK_FIND")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("SEARCH_NM")
		@Schema(name = "SEARCH_NM", example = "1", description = "검색어")
		String SEARCH_NM = "";
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BS_MIG_AV_MK_FIND")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BS_MIG_AV_MK_FIND", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
		
		@JsonProperty("PAGE_DATA")
		@Schema(name="PAGE_DATA-BS_MIG_AV_MK_FIND", description = "페이지 데이터")
		PAGE_DATA_ROW PAGE_DATA;
	}

	@ApiModel(value="OUT_DATA_ROW-BS_MIG_AV_MK_FIND")
	@Data
	static class OUT_DATA_ROW {
		@JsonProperty("MK_ID")
		@Schema(name = "MK_ID", example = "1", description = "메이커ID")
		Long MK_ID = null;

		@JsonProperty("NM")
		@Schema(name = "NM", example = "adf.jpg", description = "메이커명")
		String NM = null;


		@JsonProperty("IMG_L")
		@Schema(name = "IMG_L", example = "adf.jpg", description = "IMG_L")
		String IMG_L = null;

		@JsonProperty("IMG")
		@Schema(name = "IMG", example = "adf.jpg", description = "IMG")
		String IMG = null;

		@JsonProperty("TTL")
		@Schema(name = "TTL", example = "20200302", description = "내용")
		String TTL = null;

		@JsonProperty("CRT_DTM")
		@Schema(name = "CRT_DTM", example = "202012311640", description = "생성일시")
		String CRT_DTM = null;
	}

	
	@ApiModel(value="OUT_DATA_GEN_ROW-BS_MIG_AV_MK_FIND")
	@Data
	static class OUT_DATA_GEN_ROW {
		@JsonProperty("CATE_NO")
		@Schema(name = "CATE_NO", example = "", description = "CATE_NO")
		Long CATE_NO = null;

		@JsonProperty("CATE_NM")
		@Schema(name = "CATE_NM", example = "", description = "CATE_NM")
		String CATE_NM = null;


		@JsonProperty("CNT")
		@Schema(name = "CNT", example = "", description = "CNT")
		String CNT = null;
	}
	
	@Autowired
	DA_MIG_AV_MK daMigAvMk;

	@Autowired
	DA_MIG_AV_GEN daMigAvGen;
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"AV"}, value = "AV메이커를 조회한다.", notes = "페이징 처리")
	//@PostMapping(path= "/api/BS_MIG_AV_MK_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
	

		if(inDS.PAGE_DATA==null) {
			throw new BizRuntimeException("[PAGE_DATA]입력파라미터가 전달되지 않았습니다.");
		}
		Pageable p = inDS.PAGE_DATA.getPageable();

		Page<MigAvMk> pg = daMigAvMk.findMigAvMk(p);
		List<MigAvMk> al=pg.toList();
		
		OUT_DS outDs = new OUT_DS();
		for (int i = 0; i < al.size(); i++) {
			MigAvMk c = al.get(i);
					
			OUT_DATA_ROW row = new OUT_DATA_ROW();
			row.MK_ID = c.getMkId();
			row.NM = c.getNm();
			row.IMG_L = c.getImgL();
			row.IMG = c.getImg();
			row.TTL = c.getTtl();
			row.CRT_DTM = pjtU.getYyyy_MM_dd_HHMMSS(c.getCrtDtm());
			outDs.OUT_DATA.add(row);
		}
		
		PAGE_DATA_ROW page_data = new PAGE_DATA_ROW(pg);
		outDs.PAGE_DATA=page_data;


//		daMigAvGen.findById(L_CATE_NO)


		return outDs;
	}
}
