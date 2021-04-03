package com.example.demo.br.stck;

import java.util.ArrayList;
import java.util.Optional;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.stck.DA_STCK_BUY_MAPPER;
import com.example.demo.db.da.stck.DA_STCK_SELL;
import com.example.demo.db.domain.stck.StSell;
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

@Tag(name = "STCK", description = "주식")
@Slf4j
@OpService
@Service
public class BR_STCK_SELL_RM {

	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_STCK_SELL_RM")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_STCK_SELL_RM", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
		
		@JsonProperty("LSESSION")
		LSESSION_ROW LSESSION;
	}

	@ApiModel(value="IN_DATA_ROW-BR_STCK_SELL_RM")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("SELL_SEQ")
		@Schema(name = "SELL_SEQ", example = "jijs", description = "사용자ID")
		String SELL_SEQ = "";
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_STCK_SELL_RM")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_STCK_SELL_RM", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	
	}

	@Autowired
	DA_STCK_SELL daStckS;
	
	@Autowired
	DA_STCK_BUY_MAPPER daStckBM;
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"STCK"},value = "판주식 삭제.", notes = "")
	//@PostMapping(path= "/api/BR_STCK_SELL_RM", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws Exception {
		if(inDS.LSESSION==null) {
			throw new BizRuntimeException("세션값이 넘어오지 않았습니다1.");
		}
		String LSESSION_USER_NO =inDS.LSESSION.getUSER_NO();
		if(PjtUtil.isEmpty(LSESSION_USER_NO)) {
			throw new BizRuntimeException("사용자NO가 넘어오지 않았습니다2.");
		}
		Long L_LSESSION_USER_NO = Long.parseLong(LSESSION_USER_NO);
		
		for( int i=0;i<inDS.IN_DATA.size();i++) {
			IN_DATA_ROW rs =inDS.IN_DATA.get(i);
			String  SELL_SEQ 		= PjtUtil.str(rs.SELL_SEQ);
			if(PjtUtil.isEmpty(SELL_SEQ)) {
				throw new BizRuntimeException("["+SELL_SEQ+"]내가 판 주식 일련번호가 입력되지 않았습니다.");
			}
			long L_SELL_SEQ = Long.parseLong(SELL_SEQ);
			Optional<StSell>  c = daStckS.findById(L_SELL_SEQ);
			if(c==null) {
				throw new BizException("["+L_SELL_SEQ+"]내 주식이 존재하지 않습니다.[수정X,삭제X]");
			}
			StSell tmp =c.get();
			long L_BUY_SEQ = tmp.getBuySeq();
			
			daStckS.rmStckSell(L_SELL_SEQ);
			daStckBM.reSyncStBuy(L_BUY_SEQ,L_LSESSION_USER_NO);
			///
		}
	
		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
}
