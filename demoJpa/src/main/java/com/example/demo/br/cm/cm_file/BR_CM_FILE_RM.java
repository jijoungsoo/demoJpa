package com.example.demo.br.cm.cm_file;

import java.util.ArrayList;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.cm.DA_CM_FILE;
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

@Tag(name = "CM_FILE", description = "파일")
@Slf4j
@OpService
@Service
public class BR_CM_FILE_RM {

	@Autowired
    PjtUtil pjtU;

	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_CM_FILE_RM")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA,LSESSION", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;
		
		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CM_FILE_RM", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
				
		@JsonProperty("LSESSION")
		LSESSION_ROW LSESSION;
	}

	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_FILE_RM")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA", title="OUT_DATA-BR_CM_FILE_CREATE", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}

	@ApiModel(value="IN_DATA_ROW-BR_CM_FILE_RM")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("FILE_ID")
		@Schema(name = "FILE_ID", example = "1", description = "프로그램NO")
		String FILE_ID = null;
		
		@JsonProperty("SVR_DIR_PATH")
		@Schema(name = "SVR_DIR_PATH", example = "/sorce/xxx/", description = "서버경로")
		String SVR_DIR_PATH = null;
	}
	
	@Autowired
	DA_CM_FILE daCmFile;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_FILE"},value = "파일정보를 삭제한다.", notes = "")
	//@PostMapping(path= "/api/BR_CM_FILE_RM", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.LSESSION==null) {
			throw new BizRuntimeException("세션값이 넘어오지 않았습니다1.");
		}
		String LSESSION_USER_NO =inDS.LSESSION.getUSER_NO();
		if(pjtU.isEmpty(LSESSION_USER_NO)) {
			throw new BizRuntimeException("사용자NO가 넘어오지 않았습니다2.");
		}
		Long L_LSESSION_USER_NO = Long.parseLong(LSESSION_USER_NO);
		
		
		for( int i=0;i<inDS.IN_DATA.size();i++) {
			IN_DATA_ROW  rs =inDS.IN_DATA.get(i);
			String  FILE_ID		 	= pjtU.strTrim(rs.FILE_ID);
			String  SVR_DIR_PATH	= pjtU.strTrim(rs.SVR_DIR_PATH);
			
			daCmFile.rmFile(
					 FILE_ID,
					 SVR_DIR_PATH,
					 L_LSESSION_USER_NO
					);
		}

		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
}
