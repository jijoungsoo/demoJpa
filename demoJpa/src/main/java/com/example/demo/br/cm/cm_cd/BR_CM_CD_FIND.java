package com.example.demo.br.cm.cm_cd;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.PAGE_DATA_ROW;
import com.example.demo.db.da.cm.DA_CM_CD;
import com.example.demo.db.domain.cm.CmCd;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import org.springframework.beans.factory.annotation.Autowired;
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

@Tag(name = "CM_MENU", description = "메뉴")
@Slf4j
@OpService
@Service
public class BR_CM_CD_FIND {

	@Autowired
    PjtUtil pjtU;

	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_CM_CD_FIND")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CM_CD_FIND", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
		
		@JsonProperty("PAGE_DATA")
		@Schema(name="PAGE_DATA-BR_CM_CD_FIND", description = "페이지 데이터")
		PAGE_DATA_ROW PAGE_DATA;
	}

	@ApiModel(value="IN_DATA_ROW-BR_CM_CD_FIND")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("GRP_CD")
		@Schema(name = "GRP_CD", example = "CATEGORY_CD", description = "공통그룹코드")
		String GRP_CD = "";
		
		@JsonProperty("USE_YN")
		@Schema(name = "USE_YN", example = "(Y-사용,N-미사용)", description = "사용여부")
		String USE_YN = "";
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_CD_FIND")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_CM_CD_FIND", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
		
		@JsonProperty("PAGE_DATA")
		@Schema(name="PAGE_DATA-BR_CM_CD_FIND", description = "페이지 데이터")
		PAGE_DATA_ROW PAGE_DATA;
	}

	@ApiModel(value="OUT_DATA_ROW-BR_CM_CD_FIND")
	@Data
	static class OUT_DATA_ROW {
		@JsonProperty("GRP_CD")
		@Schema(name = "GRP_CD", example = "CATEGORY_CD", description = "공통그룹코드")
		String GRP_CD = null;
		@JsonProperty("CD")
		@Schema(name = "CD", example = "(C-공통,S-주식)", description = "공통코드")
		String CD = null;
		@JsonProperty("CD_NM")
		@Schema(name = "CD_NM", example = "사용여부", description = "공통코드명")
		String CD_NM = null;
		@JsonProperty("USE_YN")
		@Schema(name = "USE_YN", example = "(Y-사용,N-미사용)", description = "사용여부")
		String USE_YN = null;
		
		@JsonProperty("ORD")
		@Schema(name = "ORD", example = "001", description = "정렬")
		String ORD = null;
		
		@JsonProperty("RMK")
		@Schema(name = "RMK", example = "비고", description = "비고")
		String RMK = null;

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
	DA_CM_CD daCmCd;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_CD"},value = "공통코드를 조회한다.", notes = "")
	//@PostMapping(path= "/api/BR_CM_CD_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS  run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.IN_DATA==null) {
			throw new BizRuntimeException("[IN_DATA]입력파라미터가 전달되지 않았습니다.");
		}
		if(inDS.IN_DATA.size()!=1) {
			throw new BizRuntimeException("[IN_DATA]입력파라미터의 ["+inDS.IN_DATA.size()+"]행수가 잘못되었습니다.");
		}
		
		IN_DATA_ROW  rs =inDS.IN_DATA.get(0);
		String  GRP_CD 		= pjtU.str(rs.GRP_CD);
		String  USE_YN 		= pjtU.str(rs.USE_YN);
		
		List<CmCd>  al =daCmCd.findCmCd(GRP_CD,USE_YN);
		OUT_DS outDs = new OUT_DS();
		for(int i=0;i<al.size();i++) {
			CmCd cm=al.get(i);
			OUT_DATA_ROW  row = new OUT_DATA_ROW();
			row.GRP_CD= cm.getGrpCd();
			row.CD= cm.getCd();
			row.CD_NM= cm.getCdNm();
			row.USE_YN= cm.getUseYn();
			row.ORD= String.valueOf(cm.getOrd());
			row.RMK= cm.getRmk();
			row.CRT_DTM=pjtU.getYyyy_MM_dd_HHMMSS(cm.getCrtDtm());
			row.UPDT_DTM=pjtU.getYyyy_MM_dd_HHMMSS(cm.getUpdtDtm());
			outDs.OUT_DATA.add(row);
		}

		return outDs;
	}
}
