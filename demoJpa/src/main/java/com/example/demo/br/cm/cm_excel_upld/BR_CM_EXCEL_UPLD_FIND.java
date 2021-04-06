package com.example.demo.br.cm.cm_excel_upld;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.ctrl.PAGE_DATA_ROW;
import com.example.demo.db.da.cm.DA_CM_EXCEL_UPLD;
import com.example.demo.db.domain.cm.CmExcelUpld;
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
public class BR_CM_EXCEL_UPLD_FIND {

	@Autowired
    PjtUtil pjtU;

	@JsonRootName("IN_DS")
	@ApiModel(value="OUT_DS-BR_CM_EXCEL_UPLD_FIND")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CM_EXCEL_UPLD_FIND", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
		

		@JsonProperty("LSESSION")
		LSESSION_ROW LSESSION;
	}

	@ApiModel(value="IN_DATA_ROW-BR_CM_EXCEL_UPLD_FIND")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("EXCEL_UPLD_ID")
		@Schema(name = "EXCEL_UPLD_ID", example = "XXXXXX", description = "엑셀업로드ID")
		String EXCEL_UPLD_ID = "";
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_EXCEL_UPLD_FIND")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_CM_EXCEL_UPLD_FIND", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
		
		@JsonProperty("PAGE_DATA")
		PAGE_DATA_ROW PAGE_DATA;
	}

	@Schema(name = "OUT_DATA_ROW-BR_CM_EXCEL_UPLD_FIND")
	@Data
	static class OUT_DATA_ROW {
		@JsonProperty("EXCEL_UPLD_ID")
		@Schema(name = "EXCEL_UPLD_ID", example = "XXXXX", description = "업로드ID")
		String EXCEL_UPLD_ID = null;
		@JsonProperty("EXCEL_SEQ")
		@Schema(name = "EXCEL_SEQ", example = "1", description = "엑셀SEQ")
		String EXCEL_SEQ = null;
		@JsonProperty("GBN")
		@Schema(name = "GBN", example = "(H-해더,D-디테일)", description = "구분")
		String GBN = null;
		@JsonProperty("COL00")
		@Schema(name = "COL00", example = "", description = "COL00")
		String COL00 = null;
		@JsonProperty("COL01")
		@Schema(name = "COL01", example = "", description = "COL01")
		String COL01 = null;
		@JsonProperty("COL02")
		@Schema(name = "COL02", example = "", description = "COL02")
		String COL02 = null;
		@JsonProperty("COL03")
		@Schema(name = "COL03", example = "", description = "COL03")
		String COL03 = null;
		@JsonProperty("COL04")
		@Schema(name = "COL04", example = "", description = "COL04")
		String COL04 = null;
		@JsonProperty("COL05")
		@Schema(name = "COL05", example = "", description = "COL05")
		String COL05 = null;
		@JsonProperty("COL06")
		@Schema(name = "COL06", example = "", description = "COL06")
		String COL06 = null;
		@JsonProperty("COL07")
		@Schema(name = "COL07", example = "", description = "COL07")
		String COL07 = null;
		@JsonProperty("COL08")
		@Schema(name = "COL08", example = "", description = "COL08")
		String COL08 = null;
		@JsonProperty("COL09")
		@Schema(name = "COL09", example = "", description = "COL09")
		String COL09 = null;
		@JsonProperty("COL10")
		@Schema(name = "COL10", example = "", description = "COL10")
		String COL10 = null;
		@JsonProperty("COL11")
		@Schema(name = "COL11", example = "", description = "COL11")
		String COL11 = null;
		@JsonProperty("COL12")
		@Schema(name = "COL12", example = "", description = "COL12")
		String COL12 = null;
		@JsonProperty("COL13")
		@Schema(name = "COL13", example = "", description = "COL13")
		String COL13 = null;
		@JsonProperty("COL14")
		@Schema(name = "COL14", example = "", description = "COL14")
		String COL14 = null;
		@JsonProperty("COL15")
		@Schema(name = "COL15", example = "", description = "COL15")
		String COL15 = null;
		@JsonProperty("COL16")
		@Schema(name = "COL16", example = "", description = "COL16")
		String COL16 = null;
		@JsonProperty("COL17")
		@Schema(name = "COL17", example = "", description = "COL17")
		String COL17 = null;
		@JsonProperty("COL18")
		@Schema(name = "COL18", example = "", description = "COL19")
		String COL18 = null;
		@JsonProperty("COL19")
		@Schema(name = "COL19", example = "", description = "COL19")
		String COL19 = null;
		@JsonProperty("COL20")
		@Schema(name = "COL20", example = "", description = "COL20")
		String COL20 = null;
				
		@JsonProperty("CRT_USR_NO")
		@Schema(name = "CRT_USR_NO", example = "1", description = "생성자NO")
		String CRT_USR_NO = null;
				
		@JsonProperty("CRT_DTM")
		@Schema(name = "CRT_DTM", example = "202012311640", description = "생성일시")
		String CRT_DTM = null;
		
	}
	@Autowired
	DA_CM_EXCEL_UPLD daCmExcelUpld;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_EXCEL_UPLD"},value = "엑셀UPLD_ID로 조회한다.", notes = "")
	//@PostMapping(path= "/api/BR_CM_EXCEL_UPLD_FIND_BY_EXCEL_UPLD_ID", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.LSESSION==null) {
			throw new BizRuntimeException("세션값이 넘어오지 않았습니다1.");
		}
		
		String USER_NO =inDS.LSESSION.getUSER_NO();
		if(pjtU.isEmpty(USER_NO)) {
			throw new BizRuntimeException("사용자NO가 넘어오지 않았습니다2.");
		}
		Long L_USER_NO = Long.parseLong(USER_NO);
		
		if(inDS.IN_DATA==null) {
			throw new BizRuntimeException("[IN_DATA]입력파라미터가 전달되지 않았습니다.");
		}
		if(inDS.IN_DATA.size()!=1) {
			throw new BizRuntimeException("[IN_DATA]입력파라미터의 ["+inDS.IN_DATA.size()+"]행수가 잘못되었습니다.");
		}

		
		String excelUpldSeq =inDS.IN_DATA.get(0).EXCEL_UPLD_ID;
		List<CmExcelUpld>  al =daCmExcelUpld.findExcelUpld(excelUpldSeq);
		OUT_DS outDs = new OUT_DS();
		for(int i=0;i<al.size();i++) {
			CmExcelUpld cm=al.get(i);
			OUT_DATA_ROW  row = new OUT_DATA_ROW();
			row.EXCEL_UPLD_ID= cm.getExcelUpldId();
			row.EXCEL_SEQ= String.valueOf(cm.getExcelSeq());
			row.GBN= cm.getGbn();
			row.COL00= cm.getCol00();
			row.COL01= cm.getCol01();
			row.COL02= cm.getCol02();
			row.COL03= cm.getCol03();
			row.COL04= cm.getCol04();
			row.COL05= cm.getCol05();
			row.COL06= cm.getCol06();
			row.COL07= cm.getCol07();
			row.COL08= cm.getCol08();
			row.COL09= cm.getCol09();
			row.COL10= cm.getCol10();
			row.COL11= cm.getCol11();
			row.COL12= cm.getCol12();
			row.COL13= cm.getCol13();
			row.COL14= cm.getCol14();
			row.COL15= cm.getCol15();
			row.COL16= cm.getCol16();
			row.COL17= cm.getCol17();
			row.COL18= cm.getCol18();
			row.COL19= cm.getCol19();
			row.COL20= cm.getCol20();
			row.CRT_USR_NO= String.valueOf(cm.getCrtUsrNo());
			row.CRT_DTM=pjtU.getYyyy_MM_dd_HHMMSS(cm.getCrtDtm());
			outDs.OUT_DATA.add(row);
		}
		return outDs;
	}
}
