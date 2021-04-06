package com.example.demo.br.cm.cm_excel_upld;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.cm.DA_CM_EXCEL_UPLD;
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

@Tag(name = "CM_EXCEL_UPLD", description = "엑셀업로드")
@Slf4j
@OpService
@Service
public class BR_CM_EXCEL_UPLD_CREATE {
	@Autowired
    PjtUtil pjtU;
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_CM_EXCEL_UPLD_CREATE")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CM_EXCEL_UPLD_CREATE", description = "입력 데이터")
		ArrayList<DATA_ROW> IN_DATA = new ArrayList<DATA_ROW>();
		
		@JsonProperty("UPDT_DATA")
		@Schema(name="UPDT_DATA-BR_CM_EXCEL_UPLD_CREATE", description = "수정 데이터")
		ArrayList<DATA_ROW> UPDT_DATA = new ArrayList<DATA_ROW>();
		
		@JsonProperty("LSESSION")
		LSESSION_ROW LSESSION;
	}

	@ApiModel(value="DATA_ROW-BR_CM_EXCEL_UPLD_CREATE")
	@Data
	static class DATA_ROW {
		@JsonProperty("EXCEL_UPLD_ID")
		@Schema(name = "EXCEL_UPLD_ID", example = "XXXXXX", description = "엑셀업로드ID")
		String EXCEL_UPLD_ID = null;
		@JsonProperty("EXCEL_SEQ")
		@Schema(name = "EXCEL_SEQ", example = "1", description = "엑셀SEQ")
		String EXCEL_SEQ = null;
		@JsonProperty("GBN")
		@Schema(name = "GBN", example = "(H-해더, D-상세)", description = "구분")
		String GBN = null;
		@JsonProperty("COL00")
		@Schema(name = "COL00", example = "", description = "")
		String COL00 = null;
		@JsonProperty("COL01")
		@Schema(name = "COL01", example = "", description = "")
		String COL01 = null;
		@JsonProperty("COL02")
		@Schema(name = "COL02", example = "", description = "")
		String COL02 = null;
		@JsonProperty("COL03")
		@Schema(name = "COL03", example = "", description = "")
		String COL03 = null;
		@JsonProperty("COL04")
		@Schema(name = "COL04", example = "", description = "")
		String COL04 = null;
		@JsonProperty("COL05")
		@Schema(name = "COL05", example = "", description = "")
		String COL05 = null;
		@JsonProperty("COL06")
		@Schema(name = "COL06", example = "", description = "")
		String COL06 = null;
		@JsonProperty("COL07")
		@Schema(name = "COL07", example = "", description = "")
		String COL07 = null;
		@JsonProperty("COL08")
		@Schema(name = "COL08", example = "", description = "")
		String COL08 = null;
		@JsonProperty("COL09")
		@Schema(name = "COL09", example = "", description = "")
		String COL09 = null;
		@JsonProperty("COL10")
		@Schema(name = "COL10", example = "", description = "")
		String COL10 = null;
		@JsonProperty("COL11")
		@Schema(name = "COL11", example = "", description = "")
		String COL11 = null;
		@JsonProperty("COL12")
		@Schema(name = "COL12", example = "", description = "")
		String COL12 = null;
		@JsonProperty("COL13")
		@Schema(name = "COL13", example = "", description = "")
		String COL13 = null;
		@JsonProperty("COL14")
		@Schema(name = "COL14", example = "", description = "")
		String COL14 = null;
		@JsonProperty("COL15")
		@Schema(name = "COL15", example = "", description = "")
		String COL15 = null;
		@JsonProperty("COL16")
		@Schema(name = "COL16", example = "", description = "")
		String COL16 = null;
		@JsonProperty("COL17")
		@Schema(name = "COL17", example = "", description = "")
		String COL17 = null;
		@JsonProperty("COL18")
		@Schema(name = "COL18", example = "", description = "")
		String COL18 = null;
		@JsonProperty("COL19")
		@Schema(name = "COL19", example = "", description = "")
		String COL19 = null;
		@JsonProperty("COL20")
		@Schema(name = "COL20", example = "", description = "")
		String COL20 = null;
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_EXCEL_UPLD_CREATE")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA", title="OUT_DATA-BR_CM_EXCEL_UPLD_CREATE", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}
	
	@ApiModel(value="OUT_DATA_ROW-BR_CM_EXCEL_UPLD_CREATE")
	@Data
	static class OUT_DATA_ROW {
		@JsonProperty("EXCEL_UPLD_ID")
		@Schema(name = "EXCEL_UPLD_ID", example = "XXXXXXXXX", description = "엑셀업로드ID")
		String EXCEL_UPLD_ID = null;
	}
	
	@Autowired
	DA_CM_EXCEL_UPLD daCmExcelUpld;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_EXCEL_UPLD"},value = "엑셀UPLD_ID로 조회한다.", notes = "")
	//@PostMapping(path= "/api/BR_CM_EXCEL_UPLD_CREATE", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.LSESSION==null) {
			throw new BizRuntimeException("세션값이 넘어오지 않았습니다1.");
		}
		String USER_NO =inDS.LSESSION.getUSER_NO();
		if(pjtU.isEmpty(USER_NO)) {
			throw new BizRuntimeException("사용자NO가 넘어오지 않았습니다2.");
		}
		Long L_USER_NO = Long.parseLong(USER_NO);
		
		LinkedHashSet<String> excelUpldId = new LinkedHashSet<String>();
		
		for( int i=0;i<inDS.IN_DATA.size();i++) {
			DATA_ROW  rs =inDS.IN_DATA.get(i);
			String  EXCEL_UPLD_ID 	= pjtU.strTrim(rs.EXCEL_UPLD_ID);
			String  EXCEL_SEQ 		= pjtU.strTrim(rs.EXCEL_SEQ);
			String  GBN 			= pjtU.strTrim(rs.GBN);
			String  COL00 			= pjtU.strTrim(rs.COL00);
			String  COL01 			= pjtU.strTrim(rs.COL01);
			String  COL02 			= pjtU.strTrim(rs.COL02);
			String  COL03 			= pjtU.strTrim(rs.COL03);		
			String  COL04 			= pjtU.strTrim(rs.COL04);
			String  COL05 			= pjtU.strTrim(rs.COL05);
			String  COL06 			= pjtU.strTrim(rs.COL06);
			String  COL07 			= pjtU.strTrim(rs.COL07);
			String  COL08 			= pjtU.strTrim(rs.COL08);
			String  COL09 			= pjtU.strTrim(rs.COL09);
			String  COL10 			= pjtU.strTrim(rs.COL10);
			String  COL11 			= pjtU.strTrim(rs.COL11);
			String  COL12 			= pjtU.strTrim(rs.COL12);
			String  COL13 			= pjtU.strTrim(rs.COL13);
			String  COL14 			= pjtU.strTrim(rs.COL14);
			String  COL15 			= pjtU.strTrim(rs.COL15);
			String  COL16 			= pjtU.strTrim(rs.COL16);
			String  COL17 			= pjtU.strTrim(rs.COL17);
			String  COL18 			= pjtU.strTrim(rs.COL18);
			String  COL19 			= pjtU.strTrim(rs.COL19);
			String  COL20 			= pjtU.strTrim(rs.COL20);
			
			if(pjtU.isEmpty(EXCEL_UPLD_ID)) {
				throw new BizRuntimeException("엑셀업로드ID가 입력되지 않았습니다.");
			}
			if(pjtU.isEmpty(EXCEL_SEQ)) {
				throw new BizRuntimeException("엑셀 시퀀스가 입력되지 않았습니다.");
			}
			Integer I_EXCEL_SEQ = Integer.parseInt(EXCEL_SEQ);

			daCmExcelUpld.createExcelUpld(
					EXCEL_UPLD_ID,
					I_EXCEL_SEQ,
					GBN, /*H헤더  ,D 데테일*/
					COL00,
					COL01,
					COL02,
					COL03,
					COL04,
					COL05,
					COL06,
					COL07,
					COL08,
					COL09,
					COL10,
					COL11,
					COL12,
					COL13,
					COL14,
					COL15,
					COL16,
					COL17,
					COL18,
					COL19,
					COL20,
					L_USER_NO
					);
			excelUpldId.add(EXCEL_UPLD_ID);
		}
		
		OUT_DS outDs = new OUT_DS();
		Iterator<String> it = excelUpldId.iterator();
		while (it.hasNext()) {
		    OUT_DATA_ROW tmp = new OUT_DATA_ROW();
		    tmp.EXCEL_UPLD_ID=it.next();
		    outDs.OUT_DATA.add(tmp);
		}
		return outDs;
	}
}
