package com.example.demo.br.av.av_mv;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ctrl.PAGE_DATA_ROW;
import com.example.demo.db.da.av.DA_AV_MV;
import com.example.demo.db.domain.av.AvMv;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;


@Tag(name = "AV", description = "AV정보")
@Slf4j
@RestController
public class BR_AV_MV_FIND {
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_AV_MV_FIND")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_AV_MV_FIND", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
		
		@JsonProperty("PAGE_DATA")
		PAGE_DATA_ROW PAGE_DATA = new PAGE_DATA_ROW();
	}

	@ApiModel(value="IN_DATA_ROW-BR_AV_MV_FIND")
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
	@ApiModel(value="OUT_DS-BR_AV_MV_FIND")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_AV_MV_FIND", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
		
		@JsonProperty("PAGE_DATA")
		@Schema(name="PAGE_DATA-BR_AV_MV_FIND", description = "페이지 데이터")
		PAGE_DATA_ROW PAGE_DATA;
	}

	@ApiModel(value="OUT_DATA_ROW-BR_AV_MV_FIND")
	@Data
	static class OUT_DATA_ROW {
		@JsonProperty("AV_SEQ")
		@Schema(name = "AV_SEQ", example = "1", description = "사용자NO")
		Long AV_SEQ = null;
		
		@JsonProperty("AV_NM")
		@Schema(name = "AV_NM", example = "jijs", description = "사용자ID")
		String AV_NM = null;
		
		@JsonProperty("TTL")
		@Schema(name = "TTL", example = "제목입니다.", description = "제목")
		String TTL = null;
		
		@JsonProperty("CNTNT")
		@Schema(name = "CNTNT", example = "내용입니다.", description = "내용")
		String CNTNT = null;
		
		@JsonProperty("LK_CNT")
		@Schema(name = "LK_CNT", example = "1", description = "좋아요 카운트")
		String LK_CNT = null;

		@JsonProperty("DSLK_CNT")
		@Schema(name = "DSLK_CNT", example = "1", description = "싫어요 카운트")
		String DSLK_CNT = null;
		
		@JsonProperty("MSC_CD")
		@Schema(name = "MSC_CD", example = "(U-유출,M-모자이크,A-모자이크AI,N-노모)", description = "모자이크 코드")
		String MSC_CD = null;
		
		@JsonProperty("ORD")
		@Schema(name = "ORD", example = "001", description = "정렬")
		String ORD = null;
		
		@JsonProperty("RMK")
		@Schema(name = "RMK", example = "비고", description = "비고")
		String RMK = null;

		@JsonProperty("VR_YN")
		@Schema(name = "VR_YN", example = "(Y-vr,N-vr아님)", description = "VR여부")
		String VR_YN = null;
		
		@JsonProperty("CPTN_YN")
		@Schema(name = "CPTN_YN", example = "(Y-자막있음,N-자막없음)", description = "자막YN")
		String CPTN_YN = null;
		
		@JsonProperty("MK_DT")
		@Schema(name = "MK_DT", example = "20201231", description = "작품발매일")
		String MK_DT = null;
		
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
	DA_AV_MV daAvMv;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"AV"},value = "AV작품을 조회한다.", notes = "페이징 처리")
	@PostMapping(path= "/api/BR_AV_MV_FIND", consumes = "application/json", produces = "application/json")
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



		if(inDS.PAGE_DATA==null) {
			throw new BizRuntimeException("[PAGE_DATA]입력파라미터가 전달되지 않았습니다.");
		}
		Pageable p = inDS.PAGE_DATA.getPageable();
		
		Page<AvMv>  pg = daAvMv.findAvMv(MSC_CD,VR_YN,p);
		List<AvMv> al=pg.toList();
		
		OUT_DS outDs = new OUT_DS();
		for (int i = 0; i < al.size(); i++) {
			AvMv c = al.get(i);
					
			OUT_DATA_ROW row = new OUT_DATA_ROW();
			row.AV_SEQ=c.getAvSeq();
			row.AV_NM=c.getAvNm();
			row.TTL=c.getTtl();
			row.CNTNT=c.getCntnt();
			row.LK_CNT=String.valueOf(c.getLkCnt());
			row.DSLK_CNT=String.valueOf(c.getDslkCnt());
			row.MSC_CD=c.getMscCd();
			row.VR_YN=c.getVrYn();
			row.ORD=c.getOrd();
			row.RMK=c.getRmk();
			row.CPTN_YN=c.getCptnYn();
			row.MK_DT=c.getMkDt();
						
			row.CRT_USR_NO=String.valueOf(c.getCrtUsrNo());
			row.UPDT_USR_NO=String.valueOf(c.getUpdtUsrNo());
			row.CRT_DTM=PjtUtil.getYyyy_MM_dd_HHMMSS(c.getCrtDtm());
			row.UPDT_DTM=PjtUtil.getYyyy_MM_dd_HHMMSS(c.getUpdtDtm());
			outDs.OUT_DATA.add(row);
		}
		
		PAGE_DATA_ROW page_data = new PAGE_DATA_ROW(pg);
		outDs.PAGE_DATA=page_data;
		return outDs;
	}
}
