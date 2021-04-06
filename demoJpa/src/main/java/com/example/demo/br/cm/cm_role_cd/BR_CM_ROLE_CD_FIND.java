package com.example.demo.br.cm.cm_role_cd;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.anotation.OpService;
import com.example.demo.db.da.cm.DA_CM_ROLE_CD;
import com.example.demo.db.domain.cm.CmRoleCd;
import com.example.demo.exception.BizException;
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

@Tag(name = "CM_ROLE_CD", description = "역할코드")
@Slf4j
@OpService
@Service
public class BR_CM_ROLE_CD_FIND {
	@Autowired
    PjtUtil pjtU;

	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_CM_ROLE_CD_FIND")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CM_ROLE_CD_FIND", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
	}

	
	@ApiModel(value="IN_DATA_ROW-BR_CM_ROLE_CD_FIND")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("USE_YN")
		@Schema(name = "USE_YN", example = "", description = "사용여부")
		String USE_YN = "";
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_ROLE_CD_FIND")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_CM_ROLE_CD_FIND", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}

	@ApiModel(value="OUT_DATA_ROW-BR_CM_ROLE_CD_FIND")
	@Data
	static class OUT_DATA_ROW {
		@JsonProperty("ROLE_CD")
		@Schema(name = "ROLE_CD", example = "ADMIN", description = "역할코드")
		String ROLE_CD = null;
		
		@JsonProperty("ROLE_NM")
		@Schema(name = "ROLE_NM", example = "관리자", description = "역할명")
		String ROLE_NM = null;
		
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
	DA_CM_ROLE_CD daRoleCd;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_ROLE_CD"},value = "역할코드를 조회한다.", notes = "")
	//@PostMapping(path= "/api/BR_CM_ROLE_CD_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS  run(@RequestBody IN_DS inDS) throws BizException {
		String USE_YN=null;

		if(inDS.IN_DATA.size()>0){
			USE_YN = inDS.IN_DATA.get(0).getUSE_YN();
		}

		List<CmRoleCd>  al =daRoleCd.findCmRoleCd(USE_YN);
		OUT_DS outDs = new OUT_DS();
		for(int i=0;i<al.size();i++) {
			CmRoleCd cm=al.get(i);
			OUT_DATA_ROW  row = new OUT_DATA_ROW();
			row.ROLE_CD= cm.getRoleCd();
			row.ROLE_NM= cm.getRoleNm();
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
