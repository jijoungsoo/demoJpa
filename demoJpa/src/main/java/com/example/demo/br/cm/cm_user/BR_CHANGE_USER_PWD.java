package com.example.demo.br.cm.cm_user;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.cm.DA_CM_USER;
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

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
@RestController
@Tag(name = "CM_USER", description = "사용자정보")
public class BR_CHANGE_USER_PWD {

	@JsonRootName("IN_DS")
	@Schema(name="IN_DS",title="IN_DS-BR_CHANGE_USER_PWD")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CHANGE_USER_PWD", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
		
		@JsonProperty("LSESSION")
		@Schema(name = "LSESSION",title="LSESSION-BR_CHANGE_USER_PWD", description = "세션데이터")
		LSESSION_ROW LSESSION;
	}

	@Schema(name="DATA_ROW", title = "DATA_ROW-BR_CHANGE_USER_PWD")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("USER_NO")
		@Schema(name = "USER_NO", example = "1", description = "사용자NO")
		String USER_NO = null;
		@JsonProperty("USER_PWD")
		@Schema(name = "USER_PWD", example = "1", description = "사용자NO")
		String USER_PWD = null;
		@JsonProperty("RE_USER_PWD")
		@Schema(name = "RE_USER_PWD", example = "1", description = "사용자NO")
		String RE_USER_PWD = null;
	}
	
	@JsonRootName("OUT_DS")
	@Schema(name="OUT_DS",title = "OUT_DS-BR_CHANGE_USER_PWD")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA", title="OUT_DATA-BR_CHANGE_USER_PWD", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}
	
	@Autowired
	DA_CM_USER daCmUser;
	
	@Operation(summary = "사용자 패스워드 변경.", description = "")
	@PostMapping(path= "/api/BR_CHANGE_USER_PWD", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		for( int i=0;i<inDS.IN_DATA.size();i++) {
			IN_DATA_ROW  rs =inDS.IN_DATA.get(i);
			String  USER_NO 		= PjtUtil.str(rs.USER_NO);
			String  USER_PWD 		= PjtUtil.str(rs.USER_PWD);
			String  RE_USER_PWD 	= PjtUtil.str(rs.RE_USER_PWD);
			if(PjtUtil.isEmpty(USER_NO)) {
				throw new BizRuntimeException("사용자가 선택되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(USER_PWD)) {
				throw new BizRuntimeException("비밀번호가 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(RE_USER_PWD)) {
				throw new BizRuntimeException("비밀번호확인이 입력되지 않았습니다.");
			}
			if(USER_PWD.equals(RE_USER_PWD)==false) {
				throw new BizRuntimeException("비밀번호와 비밀번호확인이 입력값이 다릅니다.");
			}
			
			BCryptPasswordEncoder passwordEncoding = new BCryptPasswordEncoder();
			String S_USER_PWD =passwordEncoding.encode(USER_PWD);
			
			
			long L_USER_NO = Long.parseLong(USER_NO);
			daCmUser.changeUserPwd(
					L_USER_NO
					,S_USER_PWD
					);
		}
	
		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
}
