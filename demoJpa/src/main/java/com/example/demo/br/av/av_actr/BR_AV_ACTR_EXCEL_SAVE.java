package com.example.demo.br.av.av_actr;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.av.DA_AV_ACTR;
import com.example.demo.db.da.cm.DA_CM_EXCEL_UPLD;
import com.example.demo.db.domain.cm.CmExcelUpld;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

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
public class BR_AV_ACTR_EXCEL_SAVE {

	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_AV_ACTR_EXCEL_SAVE")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_AV_ACTR_EXCEL_SAVE", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
		
		@JsonProperty("LSESSION")
		LSESSION_ROW LSESSION;
	}

	@ApiModel(value="IN_DATA_ROW-BR_AV_ACTR_EXCEL_SAVE")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("FILE_ID")
		@Schema(name = "FILE_ID", example = "1", description = "배우SEQ")
		String FILE_ID = null;
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_AV_ACTR_EXCEL_SAVE")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA", title="OUT_DATA-UPDT_DATA", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}
	
	@Autowired
	DA_AV_ACTR daAvActr;
	
	@Autowired
	DA_CM_EXCEL_UPLD daCmExcelUpld;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"AV"},value = "AV배우를 EXCEL을 기반으로 저장한다.", notes = "")
	@PostMapping(path= "/api/BR_AV_ACTR_EXCEL_SAVE", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object>  run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.LSESSION==null) {
			throw new BizRuntimeException("세션값이 넘어오지 않았습니다1.");
		}
		String SESSION_USER_NO =inDS.LSESSION.getUSER_NO();
		if(PjtUtil.isEmpty(SESSION_USER_NO)) {
			throw new BizRuntimeException("사용자NO가 넘어오지 않았습니다2.");
		}
		Long L_SESSION_USER_NO = Long.parseLong(SESSION_USER_NO);
		
		if(inDS.IN_DATA.size()!=1) {
			throw new BizRuntimeException("잘못된 파라미터 입니다.");
		}
		String FILE_ID = inDS.IN_DATA.get(0).FILE_ID;
		
		List<CmExcelUpld>  al = daCmExcelUpld.findExcelUpld(FILE_ID);
		
		
		for( int i=0;i<al.size();i++) {
			CmExcelUpld  rs =al.get(i);
			if(rs.getGbn().equals("D")) {  //상세 
				String  ACTR_NM_KR 	= PjtUtil.str(rs.getCol00());
				String  ACTR_NM_JP 	= PjtUtil.str(rs.getCol01());
				String  ACTR_NM_ENG = PjtUtil.str(rs.getCol02());
				String  BIRTH_DT 	= PjtUtil.str(rs.getCol03());
				String  SEX 		= PjtUtil.str(rs.getCol04());
				String  RNK 		= PjtUtil.str(rs.getCol05());		
				String  ORD 		= PjtUtil.str(rs.getCol06());
				String  RMK 		= PjtUtil.str(rs.getCol07());
				String  MSC_YN	 	= PjtUtil.str(rs.getCol08());

				if(PjtUtil.isEmpty(ACTR_NM_KR)) {
					throw new BizRuntimeException("배우명(한글)이 입력되지 않았습니다.");
					/*
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
							.body("배우명(한글)이 입력되지 않았습니다.");
							*/
				}
				if(PjtUtil.isEmpty(SEX)) {
					throw new BizRuntimeException("성별이 입력되지 않았습니다.");
					/*
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
							.body("성별이 입력되지 않았습니다.");
							*/
				}
				if(PjtUtil.isEmpty(MSC_YN)) {
					throw new BizRuntimeException("모자이크 여부가 입력되지 않았습니다.");
					
					/*
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
							.body("모자이크 여부가 입력되지 않았습니다.");
					*/
				}
				Integer I_RNK = Integer.parseInt(RNK);
				Integer I_ORD = Integer.parseInt(ORD);

				daAvActr.saveExcelAvActr(
						 ACTR_NM_KR
						,ACTR_NM_JP
						,ACTR_NM_ENG
						,BIRTH_DT
						,SEX
						,I_RNK
						,I_ORD
						,RMK
						,MSC_YN
						,L_SESSION_USER_NO
						);
			}
			
		}

		OUT_DS outDs = new OUT_DS(); 
		return ResponseEntity.ok(outDs);
	}
}
