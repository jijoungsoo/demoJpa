package com.example.demo.br.cm.cm_main;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.anotation.OpService;
import com.example.demo.db.da.cm.DA_CM_MAIN;
import com.example.demo.db.domain.cm.CmPgm;
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

@Tag(name = "CM_MAIN", description = "메인로딩")
@Slf4j
@OpService
@Service
public class BR_CM_MAIN_PGM_FIND_BY_PGM_ID {

	@Autowired
    PjtUtil pjtU;

	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_CM_MAIN_PGM_FIND_BY_PGM_ID")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;
	    
		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;
	
		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CM_MAIN_PGM_FIND_BY_PGM_ID", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
	}

	@ApiModel(value="IN_DATA_ROW-BR_CM_MAIN_PGM_FIND_BY_PGM_ID")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("PGM_ID")
		@Schema(name = "PGM_ID", example = "1", description = "프로그램ID")
		String PGM_ID = "";
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_MAIN_PGM_FIND_BY_PGM_ID")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_CM_MAIN_PGM_FIND_BY_PGM_ID", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}
	
	@ApiModel(value="OUT_DATA_ROW-BR_CM_MAIN_PGM_FIND_BY_PGM_ID")
	@Data
	static class OUT_DATA_ROW {
		@JsonProperty("PGM_ID")
		@Schema(name = "PGM_ID", example = "CM_01000", description = "프로그램ID")
		String PGM_ID = null;
		@JsonProperty("DIR_LINK")
		@Schema(name = "DIR_LINK", example = "/web/gogo/", description = "프로그램경로")
		String DIR_LINK = null;
		@JsonProperty("PGM_LINK")
		@Schema(name = "PGM_LINK", example = "/web/gogo/", description = "프로그램경로")
		String PGM_LINK = null;
		@JsonProperty("PGM_NM")
		@Schema(name = "PGM_NM", example = "공통코드", description = "프로그램명")
		String PGM_NM = null;
		@JsonProperty("CRT_DTM")
		@Schema(name = "CRT_DTM", example = "202012311355", description = "생성일자")
		String CRT_DTM = null;
		@JsonProperty("UPDT_DTM")
		@Schema(name = "UPDT_DTM", example = "202012311355", description = "수정일자")
		String UPDT_DTM = null;
		@JsonProperty("RMK")
		@Schema(name = "RMK", example = "비고", description = "비고")
		String RMK = null;
	}
	
	@Autowired
	DA_CM_MAIN daMain;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_MAIN"},value = "페이지 프로그램 조회.", notes = "")
	//@PostMapping(path= "/api/BR_CM_MAIN_PGM_FIND_BY_PGM_ID", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.IN_DATA==null) {
			throw new BizRuntimeException("[IN_DATA]입력파라미터가 전달되지 않았습니다.");
		}
		if(inDS.IN_DATA.size()!=1) {
			throw new BizRuntimeException("[IN_DATA]입력파라미터의 ["+inDS.IN_DATA.size()+"]행수가 잘못되었습니다.");
		}
		
		IN_DATA_ROW  rs =inDS.IN_DATA.get(0);
		String  PGM_ID 		= pjtU.str(rs.PGM_ID);
		
		List<CmPgm> al= daMain.findPgmByPgmId(PGM_ID);//파라미터 사용안함
		ArrayList<OUT_DATA_ROW>  OUT_DATA =  new ArrayList<OUT_DATA_ROW>();
		for(int i=0;i<al.size();i++) {
			CmPgm cm=al.get(i);
			OUT_DATA_ROW  row = new OUT_DATA_ROW();
			row.PGM_ID=cm.getPgmId();
			row.DIR_LINK=cm.getDirLink();
			row.PGM_LINK=cm.getPgmLink();
			row.PGM_NM=cm.getPgmNm();
			row.RMK=cm.getRmk();
			row.CRT_DTM=pjtU.getYyyyMMddHHMMSS(cm.getCrtDtm());
			row.UPDT_DTM=pjtU.getYyyyMMddHHMMSS(cm.getUpdtDtm());
			OUT_DATA.add(row);
		}
		OUT_DS outDs = new OUT_DS();
		outDs.OUT_DATA= OUT_DATA;
		return outDs;
	}
}
