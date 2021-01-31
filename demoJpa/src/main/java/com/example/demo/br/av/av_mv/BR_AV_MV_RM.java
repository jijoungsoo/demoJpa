package com.example.demo.br.av.av_mv;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.av.DA_AV_MV;
import com.example.demo.db.da.cm.DA_CM_SEQ;
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

@Tag(name = "AV", description = "AV정보")
@Slf4j
@RestController
public class BR_AV_MV_RM {

	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_AV_MV_RM")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_AV_MV_RM", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
		
		@JsonProperty("LSESSION")
		@Schema(name = "LSESSION", description = "세션데이터")
		LSESSION_ROW LSESSION;
	}

	@ApiModel(value="IN_DATA_ROW-BR_AV_MV_RM")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("AV_SEQ")
		@Schema(name = "AV_SEQ", example = "1", description = "av작품Seq")
		String AV_SEQ = "";
	}

	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_AV_ACTR_RM")
	@Data
	static class OUT_DS {
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}
	
	@Autowired
	DA_AV_MV daAvMv;
	
	@Autowired
	DA_CM_SEQ daCmSeq;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"AV"},value = "AV작품을 삭제한다.", notes = "")
	@PostMapping(path= "/api/BR_AV_MV_RM", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws Exception {
		if(inDS.LSESSION==null) {
			throw new BizRuntimeException("세션값이 넘어오지 않았습니다1.");
		}
		
		String SESSION_USER_NO =inDS.LSESSION.getUSER_NO();
		if(PjtUtil.isEmpty(SESSION_USER_NO)) {
			throw new BizRuntimeException("사용자NO가 넘어오지 않았습니다2.");
		}
		Long L_SESSION_USER_NO = Long.parseLong(SESSION_USER_NO);
		
		for( int i=0;i<inDS.IN_DATA.size();i++) {
			IN_DATA_ROW  rs =inDS.IN_DATA.get(i);
			String  AV_SEQ 		= PjtUtil.str(rs.AV_SEQ);
			if(PjtUtil.isEmpty(AV_SEQ)) {
				throw new BizRuntimeException("["+AV_SEQ+"] 작품일련번호가 입력되지 않았습니다.");
			}
			long L_AV_SEQ = Long.parseLong(AV_SEQ);
			Optional<AvMv>  c = daAvMv.findById(L_AV_SEQ);
			if(c==null) {
				throw new BizException("["+L_AV_SEQ+"] 작품일련번호가 존재하지 않습니다.[수정X,삭제X]");
			}
			AvMv tmp =c.get();
			daAvMv.rmAvMv(L_AV_SEQ);
		}
	
		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
}
