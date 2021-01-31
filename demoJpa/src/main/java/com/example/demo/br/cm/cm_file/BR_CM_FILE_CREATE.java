package com.example.demo.br.cm.cm_file;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.cm.DA_CM_FILE;
import com.example.demo.db.da.cm.DA_CM_SEQ;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Tag(name = "CM_FILE", description = "파일")
public class BR_CM_FILE_CREATE {

	@JsonRootName("IN_DS")
	@Schema(name="IN_DS",title="IN_DS-BR_CM_FILE_CREATE")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA,LSESSION", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;
		
		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CM_FILE_CREATE", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
				
		@JsonProperty("LSESSION")
		@Schema(name = "LSESSION", description = "세션데이터")
		LSESSION_ROW LSESSION;
	}

	@JsonRootName("OUT_DS")
	@Schema(name="OUT_DS",title = "OUT_DS-BR_CM_FILE_CREATE")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA", title="OUT_DATA-BR_CM_FILE_CREATE", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}

	@Schema(name = "IN_DATA_ROW-BR_CM_FILE_CREATE")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("FILE_ID")
		@Schema(name = "FILE_ID", example = "1", description = "프로그램NO")
		String FILE_ID = null;
		@JsonProperty("FILE_GROUP")
		@Schema(name = "FILE_GROUP", example = "CM_001", description = "프로그램ID")
		String FILE_GROUP = null;
		@JsonProperty("ORG_FILE_NM")
		@Schema(name = "ORG_FILE_NM", example = "****", description = "DIR_LINK")
		String ORG_FILE_NM = null;
		@JsonProperty("SVR_DIR_PATH")
		@Schema(name = "SVR_DIR_PATH", example = "PGM_LINK", description = "PGM_LINK")
		String SVR_DIR_PATH = null;
		@JsonProperty("SVR_FILE_NM")
		@Schema(name = "SVR_FILE_NM", example = "admin@gogo.com", description = "CATEGORY")
		String SVR_FILE_NM = null;
		@JsonProperty("FILE_STATUS_CD")
		@Schema(name = "FILE_STATUS_CD", example = "admin@gogo.com", description = "RMK")
		String FILE_STATUS_CD = null;
		@JsonProperty("EXT")
		@Schema(name = "EXT", example = "admin@gogo.com", description = "CRT_DTM")
		String EXT = null;
	}
	
	@Autowired
	DA_CM_FILE daCmFile;

	@Autowired
	DA_CM_SEQ daCmSeq;

	@Operation(summary = "파일정보를 저장한다.", description = "")
	@PostMapping(path= "/api/BR_CM_FILE_CREATE", consumes = "application/json", produces = "application/json")
	public OUT_DS createFile(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.LSESSION==null) {
			throw new BizRuntimeException("세션값이 넘어오지 않았습니다1.");
		}
		String LSESSION_USER_NO =inDS.LSESSION.getUSER_NO();
		if(PjtUtil.isEmpty(LSESSION_USER_NO)) {
			throw new BizRuntimeException("사용자NO가 넘어오지 않았습니다2.");
		}
		Long L_LSESSION_USER_NO = Long.parseLong(LSESSION_USER_NO);
		
		
		for( int i=0;i<inDS.IN_DATA.size();i++) {
			IN_DATA_ROW  rs =inDS.IN_DATA.get(i);
			String  FILE_ID		 	= PjtUtil.strTrim(rs.FILE_ID);
			String  FILE_GROUP 		= PjtUtil.strTrim(rs.FILE_GROUP);
			String  ORG_FILE_NM 	= PjtUtil.strTrim(rs.ORG_FILE_NM);
			String  SVR_DIR_PATH 	= PjtUtil.strTrim(rs.SVR_DIR_PATH);
			String  SVR_FILE_NM 	= PjtUtil.strTrim(rs.SVR_FILE_NM);
			String  FILE_STATUS_CD 	= PjtUtil.strTrim(rs.FILE_STATUS_CD);
			String  EXT 			= PjtUtil.strTrim(rs.EXT);

			daCmFile.createFile(
					 FILE_ID,
					 FILE_GROUP,
					 ORG_FILE_NM,
					 EXT,
					 SVR_FILE_NM,
					 SVR_DIR_PATH,
					 FILE_STATUS_CD,
					 L_LSESSION_USER_NO
					);
		}

		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
}
