package com.example.demo.br.av.mig_av;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.anotation.OpService;
import com.example.demo.db.da.mig_av.DA_MIG_AV_ACTR;
import com.example.demo.exception.BizException;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;

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

@Tag(name = "AV", description = "AV정보")
@Slf4j
@OpService
@Service
public class BS_MIG_AV_ACTR_DEBUT_YYMM {
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BS_MIG_AV_ACTR_DEBUT_YYMM")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BS_MIG_AV_ACTR_DEBUT_YYMM")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BS_MIG_AV_ACTR_DEBUT_YYMM", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}

	@ApiModel(value="OUT_DATA_ROW-BS_MIG_AV_ACTR_DEBUT_YYMM")
	@Data
	static class OUT_DATA_ROW {
		@JsonProperty("CD")
		@Schema(name = "CD", example = "1", description = "CD")
		String CD = null;
		@JsonProperty("CD_NM")
		@Schema(name = "CD_NM", example = "1", description = "CD_NM")
		String CD_NM = null;		
	}
	
	@Autowired
	DA_MIG_AV_ACTR daMigAvActr;
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"AV"}, value = "AV배우 데뷔월을 조회힌다.")
	//@PostMapping(path= "/api/BS_MIG_AV_ACTR_AGE_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		OUT_DS outDs = new OUT_DS();
		List<Tuple> al = daMigAvActr.findActrDebutDtYYMM();

		if(al.size()>0){
			
			Tuple c= al.get(0);
			String yymm=c.get(Expressions.stringPath("debut_yymm"));
			String yymmdd =yymm+"01";
			LocalDate end = LocalDate.parse(yymmdd, DateTimeFormatter.ofPattern("yyyyMMdd"));

			String s = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
			s=s.substring(0,6)+"01";
			LocalDate start = LocalDate.parse(s, DateTimeFormatter.ofPattern("yyyyMMdd"));

			while(start.compareTo(end)==1){				
				OUT_DATA_ROW row = new OUT_DATA_ROW();
				String key =start.format(DateTimeFormatter.ofPattern("yyyyMM"));
				String val="";
				if(key.trim().length()==6){
					val=key.substring(0,4)+"년"+key.substring(4,6)+"월";
				}
				row.CD = key;
				row.CD_NM = val;
				outDs.OUT_DATA.add(row);
				start = start.plusMonths(-1);	//1달씩 빼줌
				
			}
		}

	
		for(int i=0;i<al.size();i++){
			OUT_DATA_ROW row = new OUT_DATA_ROW();
			Tuple c= al.get(i);
			String yymm=c.get(Expressions.stringPath("debut_yymm"));
			String cnt=c.get(Expressions.numberPath(Long.class,"cnt")).toString();
			row.CD = yymm;
			String val="";
			if(yymm.trim().length()==6){
				val=yymm.substring(0,4)+"년"+yymm.substring(4,6)+"월("+cnt+")";
			}
			row.CD_NM = val;
			outDs.OUT_DATA.add(row);
		}
	
		return outDs;
	}
}
