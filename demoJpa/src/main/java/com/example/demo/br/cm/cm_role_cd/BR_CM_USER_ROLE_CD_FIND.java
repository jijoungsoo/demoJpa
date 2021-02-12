package com.example.demo.br.cm.cm_role_cd;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.db.da.cm.DA_CM_USER_ROLE_CD;
import com.example.demo.db.domain.cm.CmUserRoleCd;
import com.example.demo.db.domain.cm.QCmUserRoleCd;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
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

@Tag(name = "CM_ROLE_CD", description = "역할코드")
@Slf4j
@RestController
public class BR_CM_USER_ROLE_CD_FIND {

	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_CM_USER_ROLE_CD_FIND")
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
	}



	
	@ApiModel(value="IN_DATA_ROW-BR_CM_USER_ROLE_CD_FIND")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("ROLE_CD")
		@Schema(name = "ROLE_CD", example = "ROLE_CD", description = "역할코드")
		String ROLE_CD = "";
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_USER_ROLE_CD_FIND")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_CM_USER_ROLE_CD_FIND", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}

	@ApiModel(value="OUT_DATA_ROW-BR_CM_USER_ROLE_CD_FIND")
	@Data
	static class OUT_DATA_ROW {
		@JsonProperty("ROLE_CD")
		@Schema(name = "ROLE_CD", example = "ADMIN", description = "역할코드")
		String ROLE_CD = null;
		
		@JsonProperty("USER_NO")
		@Schema(name = "USER_NO", example = "1", description = "사용자NO")
		Long USER_NO = null;

		@JsonProperty("USER_NM")
		@Schema(name = "USER_NM", example = "1", description = "사용자명")
		String USER_NM = null;

		@JsonProperty("USER_ID")
		@Schema(name = "USER_ID", example = "admin", description = "사용자ID")
		String USER_ID = null;
		
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
	DA_CM_USER_ROLE_CD daUserRoleCd;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_ROLE_CD"},value = "역할-사용자맵핑을 조회한다.", notes = "")
	@PostMapping(path= "/api/BR_CM_USER_ROLE_CD_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS  run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.IN_DATA==null) {
			throw new BizRuntimeException("[IN_DATA]입력파라미터가 전달되지 않았습니다.");
		}
		if(inDS.IN_DATA.size()!=1) {
			throw new BizRuntimeException("[IN_DATA]입력파라미터의 ["+inDS.IN_DATA.size()+"]행수가 잘못되었습니다.");
		}
		
		IN_DATA_ROW  rs =inDS.IN_DATA.get(0);
		String  ROLE_CD 		= PjtUtil.str(rs.ROLE_CD);
		List<Tuple>  al2 = daUserRoleCd.findCmUserRoleCd(ROLE_CD);
		OUT_DS outDs = new OUT_DS();
		for(int i=0;i<al2.size();i++) {
			Tuple cm=al2.get(i);
			OUT_DATA_ROW  row = new OUT_DATA_ROW();
			row.ROLE_CD= cm.get(QCmUserRoleCd.cmUserRoleCd.roleCd);
			row.USER_NO= cm.get(QCmUserRoleCd.cmUserRoleCd.userNo);
			row.USER_ID= cm.get(QCmUserRoleCd.cmUserRoleCd.cmUser.userId);
			row.USER_NM= cm.get(QCmUserRoleCd.cmUserRoleCd.cmUser.userNm);
			row.USE_YN= cm.get(QCmUserRoleCd.cmUserRoleCd.useYn);
			row.ORD= cm.get(QCmUserRoleCd.cmUserRoleCd.ord);
			row.RMK= cm.get(QCmUserRoleCd.cmUserRoleCd.rmk);
			row.CRT_DTM=PjtUtil.getYyyy_MM_dd_HHMMSS(cm.get(QCmUserRoleCd.cmUserRoleCd.crtDtm));
			row.UPDT_DTM=PjtUtil.getYyyy_MM_dd_HHMMSS(cm.get(QCmUserRoleCd.cmUserRoleCd.updtDtm));
			outDs.OUT_DATA.add(row);
		}
		return outDs;
	}	
}
