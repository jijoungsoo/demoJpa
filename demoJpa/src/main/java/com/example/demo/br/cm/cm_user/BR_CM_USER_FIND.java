package com.example.demo.br.cm.cm_user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.cm.DA_CM_USER;
import com.example.demo.db.domain.cm.CmUser;
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
@Tag(name = "CM_USER", description = "사용자정보")
public class BR_CM_USER_FIND {

	@JsonRootName("IN_DS")
	@Schema(name="IN_DS",title="IN_DS-BR_CM_PGM_FIND")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;
		
		@JsonProperty("LSESSION")
		@Schema(name = "LSESSION", description = "세션데이터")
		LSESSION_ROW LSESSION;
	}

	@JsonRootName("OUT_DS")
	@Schema(name="OUT_DS",title = "OUT_DS-BR_CM_PGM_FIND")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA", title="OUT_DATA-BR_CM_PGM_FIND", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}

	@Schema(name="OUT_DATA_ROW",title = "OUT_DATA_ROW-BR_CM_PGM_FIND")
	@Data
	static class OUT_DATA_ROW {
		@JsonProperty("USER_NO")
		@Schema(name = "USER_NO", example = "1", description = "프로그램NO")
		String USER_NO = null;
		@JsonProperty("USER_NM")
		@Schema(name = "USER_NM", example = "CM_001", description = "프로그램ID")
		String USER_NM = null;
		@JsonProperty("USER_ID")
		@Schema(name = "USER_ID", example = "****", description = "DIR_LINK")
		String USER_ID = null;
		@JsonProperty("USER_PWD")
		@Schema(name = "USER_PWD", example = "PGM_LINK", description = "PGM_LINK")
		String USER_PWD = null;
		@JsonProperty("EMAIL")
		@Schema(name = "EMAIL", example = "admin@gogo.com", description = "CATEGORY")
		String EMAIL = null;
		@JsonProperty("USE_YN")
		@Schema(name = "USE_YN", example = "admin@gogo.com", description = "RMK")
		String USE_YN = null;
		@JsonProperty("RMK")
		@Schema(name = "RMK", example = "admin@gogo.com", description = "CRT_DTM")
		String RMK = null;
		@JsonProperty("LST_ACC_DTM")
		@Schema(name = "LST_ACC_DTM", example = "admin@gogo.com", description = "UPDT_DTM")
		String LST_ACC_DTM = null;
		@JsonProperty("CRT_DTM")
		@Schema(name = "CRT_DTM", example = "admin@gogo.com", description = "UPDT_DTM")
		String CRT_DTM = null;
		@JsonProperty("UPDT_DTM")
		@Schema(name = "UPDT_DTM", example = "admin@gogo.com", description = "UPDT_DTM")
		String UPDT_DTM = null;
	}
	
	@Autowired
	DA_CM_USER daCmUser;

	@Operation(summary = "사용자 조회.", description = "")
	@PostMapping(path= "/api/BR_CM_USER_FIND", consumes = "application/json", produces = "application/json")
	public OUT_DS  run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.LSESSION==null) {
			throw new BizRuntimeException("세션값이 넘어오지 않았습니다1.");
		}
		String USER_NO =inDS.LSESSION.getUSER_NO();
		if(PjtUtil.isEmpty(USER_NO)) {
			throw new BizRuntimeException("사용자NO가 넘어오지 않았습니다2.");
		}
		Long L_USER_NO = Long.parseLong(USER_NO);
		
		List<CmUser>  al =daCmUser.findCmUser();
		OUT_DS outDs = new OUT_DS();
		for(int i=0;i<al.size();i++) {
			CmUser cm=al.get(i);
			OUT_DATA_ROW  row = new OUT_DATA_ROW();
			row.USER_NO=String.valueOf(cm.getUserNo());
			row.USER_NM=cm.getUserNm();
			row.USER_ID=cm.getUserId();
			row.USER_PWD=cm.getUserPwd();
			row.EMAIL=cm.getEmail();
			row.USE_YN=cm.getUseYn();
			row.RMK=cm.getRmk();			
			row.LST_ACC_DTM=PjtUtil.getYyyy_MM_dd_HHMMSS(cm.getLstAccDtm());
			row.CRT_DTM=PjtUtil.getYyyy_MM_dd_HHMMSS(cm.getCrtDtm());
			row.UPDT_DTM=PjtUtil.getYyyy_MM_dd_HHMMSS(cm.getUpdtDtm());
			outDs.OUT_DATA.add(row);
		}
		return outDs;
	}
}
