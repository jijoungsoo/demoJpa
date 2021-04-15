package com.example.demo.br.av.mig_av;

import java.util.ArrayList;
import java.util.Optional;

import com.example.demo.anotation.OpService;
import com.example.demo.db.da.mig_av.DA_MIG_AV_MV;
import com.example.demo.db.domain.mig_av.MigAvMv;
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
public class BS_MIG_AV_MV_IMG_FIND_BY_DVD_IDX {
	@Autowired
    PjtUtil pjtU;
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BS_MIG_AV_MV_IMG_FIND_BY_DVD_IDX")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		
		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BS_MIG_AV_MV_IMG_FIND_BY_DVD_IDX", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
	}

	@ApiModel(value="IN_DATA_ROW-BS_MIG_AV_MV_IMG_FIND_BY_DVD_IDX")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("DVD_IDX")
		@Schema(name = "DVD_IDX", example = "1", description = "DVD_IDX")
		String DVD_IDX = "";
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BS_MIG_AV_MV_IMG_FIND_BY_DVD_IDX")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BS_MIG_AV_MV_IMG_FIND_BY_DVD_IDX", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}

	@ApiModel(value="OUT_DATA_ROW-BS_MIG_AV_MV_IMG_FIND_BY_DVD_IDX")
	@Data
	static class OUT_DATA_ROW {
		@JsonProperty("DVD_IDX")
		@Schema(name = "DVD_IDX", example = "1", description = "DVD_IDX")
		Long DVD_IDX = null;

		@JsonProperty("IMG_A")
		@Schema(name = "IMG_A", example = "adf.jpg", description = "갤러리")
		String IMG_A = null;

		@JsonProperty("IMG_AS")
		@Schema(name = "IMG_AS", example = "adf.jpg", description = "갤러리")
		String IMG_AS = null;

		@JsonProperty("IMG_LA")
		@Schema(name = "IMG_LA", example = "adf.jpg", description = "갤러리")
		String IMG_LA = null;

		@JsonProperty("IMG_LAS")
		@Schema(name = "IMG_LAS", example = "adf.jpg", description = "갤러리")
		String IMG_LAS = null;

		@JsonProperty("CRT_DTM")
		@Schema(name = "CRT_DTM", example = "202012311640", description = "생성일시")
		String CRT_DTM = null;
	}
	
	@Autowired
	DA_MIG_AV_MV daMigAvMv;
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"AV"}, value = "DVD 이미지 하나를 조회한다.", notes = "페이징 처리")
	//@PostMapping(path= "/api/BS_MIG_AV_MV_IMG_FIND_BY_DVD_IDX", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.IN_DATA==null) {
			throw new BizRuntimeException("[IN_DATA]입력파라미터가 전달되지 않았습니다.");
		}
		if(inDS.IN_DATA.size()!=1) {
			throw new BizRuntimeException("[IN_DATA]입력파라미터의 ["+inDS.IN_DATA.size()+"]행수가 잘못되었습니다.");
		}
		
		IN_DATA_ROW  rs =inDS.IN_DATA.get(0);
		String  DVD_IDX 		= pjtU.str(rs.DVD_IDX);

		if(pjtU.isEmpty(DVD_IDX)){
			throw new BizRuntimeException("DVD_IDX 전달되지 않았습니다.");
		}
		Long L_DVD_IDX  = Long.parseLong(DVD_IDX);
		Optional<MigAvMv>  c = daMigAvMv.findById(L_DVD_IDX);
		OUT_DS outDs = new OUT_DS();
		if (c.isPresent()) { //있다.
            MigAvMv m = c.get();
			OUT_DATA_ROW row = new OUT_DATA_ROW();
			row.DVD_IDX = m.getDvdIdx();
			row.IMG_A  = m.getImgA();
			row.IMG_AS  = m.getImgAs();
			row.IMG_LA  = m.getImgLA();
			row.IMG_LAS  = m.getImgLAs();
			row.CRT_DTM = pjtU.getYyyy_MM_dd_HHMMSS(m.getCrtDtm());
			outDs.OUT_DATA.add(row);
        } 			
		return outDs;          
	}
}
