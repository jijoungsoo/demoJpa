package com.example.demo.br.av.mig_av;

import java.util.ArrayList;
import java.util.Optional;

import com.example.demo.anotation.OpService;
import com.example.demo.db.da.mig_av.DA_MIG_AV_ACTR_IMG;
import com.example.demo.db.domain.mig_av.MigAvActrImg;
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

@Tag(name = "AV", description = "AV정보")
@Slf4j
@OpService
@Service
public class BS_MIG_AV_ACTR_IMG_FIND_BY_IMG_SEQ {
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BS_MIG_AV_ACTR_IMG_FIND_BY_IMG_SEQ")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		
		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BS_MIG_AV_ACTR_IMG_FIND_BY_IMG_SEQ", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
	}

	@ApiModel(value="IN_DATA_ROW-BS_MIG_AV_ACTR_IMG_FIND_BY_IMG_SEQ")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("IMG_SEQ")
		@Schema(name = "IMG_SEQ", example = "1", description = "IMG_SEQ")
		String IMG_SEQ = "";
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BS_MIG_AV_ACTR_IMG_FIND_BY_IMG_SEQ")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BS_MIG_AV_ACTR_IMG_FIND_BY_IMG_SEQ", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}

	@ApiModel(value="OUT_DATA_ROW-BS_MIG_AV_ACTR_IMG_FIND_BY_IMG_SEQ")
	@Data
	static class OUT_DATA_ROW {
		@JsonProperty("IMG_SEQ")
		@Schema(name = "IMG_SEQ", example = "1", description = "IMG_SEQ")
		Long IMG_SEQ = null;

		@JsonProperty("IMG")
		@Schema(name = "IMG", example = "adf.jpg", description = "갤러리")
		String IMG = null;

		@JsonProperty("IMG_L")
		@Schema(name = "IMG_L", example = "adf.jpg", description = "갤러리")
		String IMG_L = null;

		@JsonProperty("IMG_S")
		@Schema(name = "IMG_S", example = "adf.jpg", description = "갤러리")
		String IMG_S = null;

		@JsonProperty("IMG_LS")
		@Schema(name = "IMG_LS", example = "adf.jpg", description = "갤러리")
		String IMG_LS = null;

		@JsonProperty("CRT_DTM")
		@Schema(name = "CRT_DTM", example = "202012311640", description = "생성일시")
		String CRT_DTM = null;
	}
	
	@Autowired
	DA_MIG_AV_ACTR_IMG daMigAvActrImg;
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"AV"}, value = "AV배우 이미지 하나를 조회한다.", notes = "페이징 처리")
	//@PostMapping(path= "/api/BS_MIG_AV_ACTR_IMG_FIND_BY_IMG_SEQ", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.IN_DATA==null) {
			throw new BizRuntimeException("[IN_DATA]입력파라미터가 전달되지 않았습니다.");
		}
		if(inDS.IN_DATA.size()!=1) {
			throw new BizRuntimeException("[IN_DATA]입력파라미터의 ["+inDS.IN_DATA.size()+"]행수가 잘못되었습니다.");
		}
		
		IN_DATA_ROW  rs =inDS.IN_DATA.get(0);
		String  IMG_SEQ 		= PjtUtil.str(rs.IMG_SEQ);

		if(PjtUtil.isEmpty(IMG_SEQ)){
			throw new BizRuntimeException("IMG_SEQ 전달되지 않았습니다.");
		}
		Long L_IMG_SEQ  = Long.parseLong(IMG_SEQ);
		Optional<MigAvActrImg>  c = daMigAvActrImg.findById(L_IMG_SEQ);
		OUT_DS outDs = new OUT_DS();
		if (c.isPresent()) { //있다.
            MigAvActrImg m = c.get();
			OUT_DATA_ROW row = new OUT_DATA_ROW();
			row.IMG_SEQ = m.getImgSeq();
			row.IMG  = m.getImg();
			row.IMG_S  = m.getImgS();
			row.IMG_L  = m.getImgL();
			row.IMG_LS  = m.getImgLs();
			row.CRT_DTM = PjtUtil.getYyyy_MM_dd_HHMMSS(m.getCrtDtm());
			outDs.OUT_DATA.add(row);
        } 			
		return outDs;          
	}
}
