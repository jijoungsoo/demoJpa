package com.example.demo.br.cm.cm_excel_upld;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.cm.DA_CM_EXCEL_UPLD;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Tag(name = "CM_EXCEL_UPLD", description = "엑셀업로드")
public class BR_CM_EXCEL_UPLD_CREATE {
	@JsonRootName("IN_DS")
	@Schema(name="IN_DS",title="IN_DS-UPDT_DATA")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-UPDT_DATA", description = "입력 데이터")
		ArrayList<DATA_ROW> IN_DATA = new ArrayList<DATA_ROW>();
		
		@JsonProperty("UPDT_DATA")
		@Schema(name="IN_DATA-UPDT_DATA", description = "수정 데이터")
		ArrayList<DATA_ROW> UPDT_DATA = new ArrayList<DATA_ROW>();
		
		@JsonProperty("LSESSION")
		@Schema(name = "LSESSION",title="LSESSION-UPDT_DATA", description = "세션데이터")
		LSESSION_ROW LSESSION;
	}

	@Schema(name="DATA_ROW", title = "DATA_ROW-UPDT_DATA")
	@Data
	static class DATA_ROW {
		@JsonProperty("EXCEL_UPLD_ID")
		@Schema(name = "EXCEL_UPLD_ID", example = "1", description = "사용자NO")
		Long EXCEL_UPLD_ID = null;
		@JsonProperty("EXCEL_SEQ")
		@Schema(name = "EXCEL_SEQ", example = "jijs", description = "사용자ID")
		String EXCEL_SEQ = null;
		@JsonProperty("GBN")
		@Schema(name = "GBN", example = "****", description = "사용자패스워드")
		String GBN = null;
		@JsonProperty("COL00")
		@Schema(name = "COL00", example = "홍길동", description = "사용자명")
		String COL00 = null;
		@JsonProperty("COL01")
		@Schema(name = "COL01", example = "admin@gogo.com", description = "이메일")
		String COL01 = null;
		@JsonProperty("COL02")
		@Schema(name = "COL02", example = "admin@gogo.com", description = "이메일")
		String COL02 = null;
		@JsonProperty("COL03")
		@Schema(name = "COL03", example = "admin@gogo.com", description = "이메일")
		String COL03 = null;
		@JsonProperty("COL04")
		@Schema(name = "COL04", example = "admin@gogo.com", description = "이메일")
		String COL04 = null;
		@JsonProperty("COL05")
		@Schema(name = "COL05", example = "admin@gogo.com", description = "이메일")
		String COL05 = null;
		@JsonProperty("COL06")
		@Schema(name = "COL06", example = "admin@gogo.com", description = "이메일")
		String COL06 = null;
		@JsonProperty("COL07")
		@Schema(name = "COL07", example = "admin@gogo.com", description = "이메일")
		String COL07 = null;
		@JsonProperty("COL08")
		@Schema(name = "COL08", example = "admin@gogo.com", description = "이메일")
		String COL08 = null;
		@JsonProperty("COL09")
		@Schema(name = "COL09", example = "admin@gogo.com", description = "이메일")
		String COL09 = null;
		@JsonProperty("COL10")
		@Schema(name = "COL10", example = "admin@gogo.com", description = "이메일")
		String COL10 = null;
		@JsonProperty("COL11")
		@Schema(name = "COL11", example = "admin@gogo.com", description = "이메일")
		String COL11 = null;
		@JsonProperty("COL12")
		@Schema(name = "COL12", example = "admin@gogo.com", description = "이메일")
		String COL12 = null;
		@JsonProperty("COL13")
		@Schema(name = "COL13", example = "admin@gogo.com", description = "이메일")
		String COL13 = null;
		@JsonProperty("COL14")
		@Schema(name = "COL14", example = "admin@gogo.com", description = "이메일")
		String COL14 = null;
		@JsonProperty("COL15")
		@Schema(name = "COL15", example = "admin@gogo.com", description = "이메일")
		String COL15 = null;
		@JsonProperty("COL16")
		@Schema(name = "COL16", example = "admin@gogo.com", description = "이메일")
		String COL16 = null;
		@JsonProperty("COL17")
		@Schema(name = "COL17", example = "admin@gogo.com", description = "이메일")
		String COL17 = null;
		@JsonProperty("COL18")
		@Schema(name = "COL18", example = "admin@gogo.com", description = "이메일")
		String COL18 = null;
		@JsonProperty("COL19")
		@Schema(name = "COL19", example = "admin@gogo.com", description = "이메일")
		String COL19 = null;
		@JsonProperty("COL20")
		@Schema(name = "COL20", example = "admin@gogo.com", description = "이메일")
		String COL20 = null;
	}
	
	@JsonRootName("OUT_DS")
	@Schema(name="OUT_DS",title = "OUT_DS-UPDT_DATA")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA", title="OUT_DATA-UPDT_DATA", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}
	
	@Autowired
	DA_CM_EXCEL_UPLD daCmExcelUpld;

	@Operation(summary = "엑셀UPLD_ID로 조회한다.", description = "")
	@PostMapping(path= "/api/BR_CM_EXCEL_UPLD_CREATE", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.LSESSION==null) {
			throw new BizRuntimeException("세션값이 넘어오지 않았습니다1.");
		}
		String USER_NO =inDS.LSESSION.getUSER_NO();
		if(PjtUtil.isEmpty(USER_NO)) {
			throw new BizRuntimeException("사용자NO가 넘어오지 않았습니다2.");
		}
		Long L_USER_NO = Long.parseLong(USER_NO);
		
		for( int i=0;i<inDS.IN_DATA.size();i++) {
			DATA_ROW  rs =inDS.IN_DATA.get(i);
			String  EXCEL_UPLD_ID 	= PjtUtil.strTrim(rs.EXCEL_UPLD_ID);
			String  EXCEL_SEQ 		= PjtUtil.strTrim(rs.EXCEL_SEQ);
			String  GBN 			= PjtUtil.strTrim(rs.GBN);
			String  COL00 			= PjtUtil.strTrim(rs.COL00);
			String  COL01 			= PjtUtil.strTrim(rs.COL01);
			String  COL02 			= PjtUtil.strTrim(rs.COL02);
			String  COL03 			= PjtUtil.strTrim(rs.COL03);		
			String  COL04 			= PjtUtil.strTrim(rs.COL04);
			String  COL05 			= PjtUtil.strTrim(rs.COL05);
			String  COL06 			= PjtUtil.strTrim(rs.COL06);
			String  COL07 			= PjtUtil.strTrim(rs.COL07);
			String  COL08 			= PjtUtil.strTrim(rs.COL08);
			String  COL09 			= PjtUtil.strTrim(rs.COL09);
			String  COL10 			= PjtUtil.strTrim(rs.COL10);
			String  COL11 			= PjtUtil.strTrim(rs.COL11);
			String  COL12 			= PjtUtil.strTrim(rs.COL12);
			String  COL13 			= PjtUtil.strTrim(rs.COL13);
			String  COL14 			= PjtUtil.strTrim(rs.COL14);
			String  COL15 			= PjtUtil.strTrim(rs.COL15);
			String  COL16 			= PjtUtil.strTrim(rs.COL16);
			String  COL17 			= PjtUtil.strTrim(rs.COL17);
			String  COL18 			= PjtUtil.strTrim(rs.COL18);
			String  COL19 			= PjtUtil.strTrim(rs.COL19);
			String  COL20 			= PjtUtil.strTrim(rs.COL20);
			
			if(PjtUtil.isEmpty(EXCEL_UPLD_ID)) {
				throw new BizRuntimeException("엑셀업로드ID가 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(EXCEL_SEQ)) {
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
		}

		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
}
