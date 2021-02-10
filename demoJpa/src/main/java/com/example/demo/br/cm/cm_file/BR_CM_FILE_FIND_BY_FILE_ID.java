package com.example.demo.br.cm.cm_file;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.cm.DA_CM_FILE;
import com.example.demo.db.domain.cm.CmFile;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

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
@RestController
public class BR_CM_FILE_FIND_BY_FILE_ID {

	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_CM_FILE_FIND_BY_FILE_ID")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA,LSESSION", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;
		
		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CM_FILE_FIND", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
				
		@JsonProperty("LSESSION")
		@Schema(name = "LSESSION", description = "세션데이터")
		LSESSION_ROW LSESSION;
	}
	
	@ApiModel(value="IN_DATA_ROW-BR_CM_FILE_FIND_BY_FILE_ID")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("FILE_ID")
		@Schema(name = "FILE_ID", example = "1", description = "프로그램NO")
		String FILE_ID = null;
	}

	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_FILE_FIND_BY_FILE_ID")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA", title="OUT_DATA-BR_CM_FILE_CREATE", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}

	@ApiModel(value="OUT_DATA_ROW-BR_CM_FILE_FIND_BY_FILE_ID")
	@Data
	static class OUT_DATA_ROW {
		@JsonProperty("FILE_ID")
		@Schema(name = "FILE_ID", example = "XXXXX", description = "파일ID")
		String FILE_ID = null;
		@JsonProperty("FILE_GROUP")
		@Schema(name = "FILE_GROUP", example = "XXX", description = "파일그룹ID")
		String FILE_GROUP = null;
		@JsonProperty("ORG_FILE_NM")
		@Schema(name = "ORG_FILE_NM", example = "abc.jpg", description = "원래파일명")
		String ORG_FILE_NM = null;
		@JsonProperty("SVR_DIR_PATH")
		@Schema(name = "SVR_DIR_PATH", example = "/sorce/xxx/", description = "서버경로")
		String SVR_DIR_PATH = null;
		@JsonProperty("SVR_FILE_NM")
		@Schema(name = "SVR_FILE_NM", example = "abcdef", description = "서버파일명")
		String SVR_FILE_NM = null;
		@JsonProperty("FILE_STATUS_CD")
		@Schema(name = "FILE_STATUS_CD", example = "(T-임시,M-매핑,D-삭제", description = "파일상태")
		String FILE_STATUS_CD = null;
		@JsonProperty("EXT")
		@Schema(name = "EXT", example = "JPG", description = "확장자")
		String EXT = null;

		@JsonProperty("FILE_SIZE")
		@Schema(name = "FILE_SIZE", example = "123123", description = "파일사이즈")
		Long FILE_SIZE = null;
		
		@JsonProperty("CONTENT_TYPE")
		@Schema(name = "CONTENT_TYPE", example = "123123", description = "컨텐츠타입")
		String CONTENT_TYPE = null;
		
		
		@JsonProperty("CRT_USR_NO")
		@Schema(name = "CRT_USR_NO", example = "1", description = "생성자NO")
		String CRT_USR_NO = null;
		@JsonProperty("CRT_DTM")
		@Schema(name = "CRT_DTM", example = "202012311650", description = "생성일")
		String CRT_DTM = null;
		
	}
	
	@Autowired
	DA_CM_FILE daCmFile;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_FILE"},value = "파일을 조회한다.(by FILE_ID)", notes = "")
	@PostMapping(path= "/api/BR_CM_FILE_FIND_BY_FILE_ID", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.LSESSION==null) {
			throw new BizRuntimeException("세션값이 넘어오지 않았습니다1.");
		}
		String LSESSION_USER_NO =inDS.LSESSION.getUSER_NO();
		if(PjtUtil.isEmpty(LSESSION_USER_NO)) {
			throw new BizRuntimeException("사용자NO가 넘어오지 않았습니다2.");
		}
		Long L_LSESSION_USER_NO = Long.parseLong(LSESSION_USER_NO);

		if (inDS.IN_DATA.size() != 1) {
			throw new BizException("입력파라미터가 잘못되었습니다.");
		}
		IN_DATA_ROW rs = inDS.IN_DATA.get(0);

		if (rs.FILE_ID == null) {
			throw new BizException("파일ID가 입력되지 않았습니다.");
		}
		
		String FILE_ID = rs.FILE_ID;
		Optional<CmFile>  c =daCmFile.findFileListByFileId(FILE_ID);
		OUT_DS outDs = new OUT_DS();
		if(c!=null) {
			CmFile cm=c.get();
			OUT_DATA_ROW  row = new OUT_DATA_ROW();
			row.FILE_ID= cm.getFileId().toString();
			row.FILE_GROUP= cm.getFileGroup();
			row.ORG_FILE_NM= cm.getOrgFileNm();
			row.SVR_DIR_PATH= cm.getSvrDirPath();
			row.SVR_FILE_NM= cm.getSvrFileNm();
			row.FILE_SIZE= cm.getFileSize();
			row.CONTENT_TYPE = cm.getContentType();
			row.FILE_STATUS_CD =cm.getFileStatusCd(); 
			row.EXT= cm.getExt();
			row.CRT_USR_NO= String.valueOf(cm.getCrtUsrNo());
			row.CRT_DTM=PjtUtil.getYyyy_MM_dd_HHMMSS(cm.getCrtDtm());
			outDs.OUT_DATA.add(row);
		}
		return outDs;
	}
}
