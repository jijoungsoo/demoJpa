package com.example.demo.br.av.av_mv;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.anotation.OpService;
import com.example.demo.db.da.av.DA_AV_MV;
import com.example.demo.db.domain.av.AvMv;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
public class BR_AV_MV_EXCEL_DWNLD {
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_AV_MV_EXCEL_DWNLD")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_AV_MV_EXCEL_DWNLD", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
	}
	
	@ApiModel(value="IN_DATA_ROW-BR_AV_MV_EXCEL_DWNLD")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("MSC_CD")
		@Schema(name = "MSC_CD", example = "(U-유출,M-모자이크,A-모자이크AI,N-노모)", description = "모자이크 코드")
		String MSC_CD = null;
		
		@JsonProperty("VR_YN")
		@Schema(name = "VR_YN", example = "(Y-vr,N-vr아님)", description = "VR여부")
		String VR_YN = null;
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_AV_MV_EXCEL_DWNLD")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_AV_MV_EXCEL_DWNLD", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}

	@ApiModel(value="OUT_DATA_ROW-BR_AV_MV_EXCEL_DWNLD")
	@Data
	static class OUT_DATA_ROW {
		@JsonProperty("AV_NM")
		@Schema(name = "AV_NM", example = "AV-001", description = "AV작품명")
		String AV_NM = null;
		
		@JsonProperty("TTL")
		@Schema(name = "TTL", example = "제목입니다.", description = "제목")
		String TTL = null;
		
		@JsonProperty("CNTNT")
		@Schema(name = "CNTNT", example = "내용입니다.", description = "내용")
		String CNTNT = null;
		
		@JsonProperty("MSC_CD")
		@Schema(name = "MSC_CD", example = "(U-유출,M-모자이크,A-모자이크AI,N-노모)", description = "모자이크 코드")
		String MSC_CD = null;

		@JsonProperty("VR_YN")
		@Schema(name = "VR_YN", example = "(Y-VR,N-NO-VR)", description = "VR여부")
		String VR_YN = null;
		
		@JsonProperty("ORD")
		@Schema(name = "ORD", example = "001", description = "정렬")
		String ORD = null;
		
		@JsonProperty("RMK")
		@Schema(name = "RMK", example = "비고", description = "비고")
		String RMK = null;
		
		@JsonProperty("CPTN_YN")
		@Schema(name = "CPTN_YN", example = "(Y-자막있음,N-자막없음)", description = "자막YN")
		String CPTN_YN = null;
		
		@JsonProperty("MK_DT")
		@Schema(name = "MK_DT", example = "20201231", description = "작품발매일")
		String MK_DT = null;
	}
	
	@Autowired
	DA_AV_MV daAvMv;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"AV"},value = "AV작품을 엑셀다운로드한다..", notes = "")
	//@PostMapping(path= "/api/BR_AV_MV_EXCEL_DWNLD", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		
		if(inDS.IN_DATA==null) {
			throw new BizRuntimeException("[IN_DATA]입력파라미터가 전달되지 않았습니다.");
		}
		if(inDS.IN_DATA.size()!=1) {
			throw new BizRuntimeException("[IN_DATA]입력파라미터의 ["+inDS.IN_DATA.size()+"]행수가 잘못되었습니다.");
		}
		
		IN_DATA_ROW  rs =inDS.IN_DATA.get(0);
		String  MSC_CD 		= PjtUtil.str(rs.MSC_CD);
		String  VR_YN 		= PjtUtil.str(rs.VR_YN);

		Page<AvMv>  pg = daAvMv.findAvMv(MSC_CD,VR_YN,null);
		List<AvMv> al=pg.toList();
		OUT_DS outDs = new OUT_DS();
		OUT_DATA_ROW row = new OUT_DATA_ROW();
		row.AV_NM="AV작품명";
		row.TTL="제목";
		row.CNTNT="내용";
		row.MSC_CD="모자이크 코드";
		row.VR_YN="VR여부";
		row.ORD="정렬";
		row.RMK="비고";
		row.CPTN_YN="자막YN";
		row.MK_DT="작품발매일";
		outDs.OUT_DATA.add(row);
		for (int i = 0; i < al.size(); i++) {
			AvMv c = al.get(i);
			row = new OUT_DATA_ROW();
			row.AV_NM=c.getAvNm();
			row.TTL=c.getTtl();
			row.CNTNT=c.getCntnt();
			row.MSC_CD=c.getMscCd();
			row.VR_YN=c.getVrYn();
			row.ORD=c.getOrd();
			row.RMK=c.getRmk();
			row.CPTN_YN=c.getCptnYn();
			row.MK_DT=c.getMkDt();
			outDs.OUT_DATA.add(row);
		}
		return outDs;
	}
}
