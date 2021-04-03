package com.example.demo.br.av.av_actr;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.PAGE_DATA_ROW;
import com.example.demo.db.da.av.DA_AV_ACTR;
import com.example.demo.db.domain.av.QAvActr;
import com.example.demo.exception.BizException;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.querydsl.core.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
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
//@RestController
@OpService
@Service
public class BR_AV_ACTR_FIND {
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_AV_ACTR_FIND")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;
		
		@JsonProperty("PAGE_DATA")
		PAGE_DATA_ROW PAGE_DATA;
	}

	@ApiModel(value="IN_DATA_ROW-BR_AV_ACTR_FIND")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("USER_ID")
		@Schema(name = "USER_ID", example = "jijs", description = "사용자ID")
		String USER_ID = "";
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_AV_ACTR_FIND")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_AV_ACTR_FIND", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
		
		@JsonProperty("PAGE_DATA")
		@Schema(name="PAGE_DATA-BR_AV_MV_FIND", description = "페이지 데이터")
		PAGE_DATA_ROW PAGE_DATA;
	}

	@ApiModel(value="OUT_DATA_ROW-BR_AV_ACTR_FIND")
	@Data
	static class OUT_DATA_ROW {
		@JsonProperty("ACTR_SEQ")
		@Schema(name = "ACTR_SEQ", example = "1", description = "사용자NO")
		Long ACTR_SEQ = null;
		@JsonProperty("ACTR_NM_KR")
		@Schema(name = "ACTR_NM_KR", example = "jijs", description = "사용자ID")
		String ACTR_NM_KR = null;
		@JsonProperty("ACTR_NM_JP")
		@Schema(name = "ACTR_NM_JP", example = "****", description = "사용자패스워드")
		String ACTR_NM_JP = null;
		@JsonProperty("ACTR_NM_ENG")
		@Schema(name = "ACTR_NM_ENG", example = "홍길동", description = "사용자명")
		String ACTR_NM_ENG = null;
		@JsonProperty("BIRTH_DT")
		@Schema(name = "BIRTH_DT", example = "admin@gogo.com", description = "이메일")
		String BIRTH_DT = null;
		@JsonProperty("AGE")
		@Schema(name = "AGE", example = "admin@gogo.com", description = "이메일")
		String AGE = null;
		@JsonProperty("SEX")
		@Schema(name = "SEX", example = "admin@gogo.com", description = "이메일")
		String SEX = null;
		@JsonProperty("RNK")
		@Schema(name = "RNK", example = "admin@gogo.com", description = "이메일")
		String RNK = null;
		@JsonProperty("RMK")
		@Schema(name = "RMK", example = "admin@gogo.com", description = "이메일")
		String RMK = null;
		@JsonProperty("MSC_YN")
		@Schema(name = "MSC_YN", example = "admin@gogo.com", description = "이메일")
		String MSC_YN = null;
		@JsonProperty("ORD")
		@Schema(name = "ORD", example = "admin@gogo.com", description = "이메일")
		String ORD = null;
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
	DA_AV_ACTR daAvActr;
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"AV"}, value = "AV배우를 조회한다.", notes = "페이징 처리")
	//@PostMapping(path= "/api/BR_AV_ACTR_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		PAGE_DATA_ROW rs_page =inDS.PAGE_DATA;
		Pageable p = rs_page.getPageable();
		
		Page<Tuple>  pg = daAvActr.findAvActr(p);
		List<Tuple> al=pg.toList();
		
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		
		OUT_DS outDs = new OUT_DS();
		for (int i = 0; i < al.size(); i++) {
			Tuple c = al.get(i);
					
			OUT_DATA_ROW row = new OUT_DATA_ROW();
			row.ACTR_SEQ=c.get(QAvActr.avActr.actrSeq);
			row.ACTR_NM_KR=c.get(QAvActr.avActr.actrNmKr);
			row.ACTR_NM_JP=c.get(QAvActr.avActr.actrNmJp);
			row.ACTR_NM_ENG=c.get(QAvActr.avActr.actrNmEng);
			row.BIRTH_DT=c.get(QAvActr.avActr.birthDt);
			String BIRTH_YEAR= c.get(QAvActr.avActr.birthDt);
			if(BIRTH_YEAR!=null) {
				int I_BIRTH_YEAR = Integer.parseInt(BIRTH_YEAR.substring(0,4));
				int age = year-I_BIRTH_YEAR;
				row.AGE=String.valueOf(age);
			} else {
				row.AGE="";
			}
			
			row.SEX=c.get(QAvActr.avActr.sex);
			row.RNK=c.get(QAvActr.avActr.rnk).toString();
			row.RMK=c.get(QAvActr.avActr.rmk);
			row.MSC_YN=c.get(QAvActr.avActr.mscYn);
			row.ORD=c.get(QAvActr.avActr.ord).toString();
			row.CRT_USR_NO=c.get(QAvActr.avActr.crtUsrNo).toString();
			row.UPDT_USR_NO=c.get(QAvActr.avActr.updtUsrNo).toString();
			row.CRT_DTM=PjtUtil.getYyyy_MM_dd_HHMMSS(c.get(QAvActr.avActr.crtDtm));
			row.UPDT_DTM=PjtUtil.getYyyy_MM_dd_HHMMSS(c.get(QAvActr.avActr.updtDtm));
			outDs.OUT_DATA.add(row);
		}
		
		PAGE_DATA_ROW page_data = new PAGE_DATA_ROW(pg);
		outDs.PAGE_DATA=page_data;
		return outDs;
	}
}
