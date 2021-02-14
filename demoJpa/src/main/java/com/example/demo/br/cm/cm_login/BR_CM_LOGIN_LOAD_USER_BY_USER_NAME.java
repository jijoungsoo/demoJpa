package com.example.demo.br.cm.cm_login;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.db.da.cm.DA_CM_LOGIN;
import com.example.demo.db.domain.cm.CmUser;
import com.example.demo.exception.BizException;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "CM_LOGIN", description = "로그인")
@Slf4j
@RestController
public class BR_CM_LOGIN_LOAD_USER_BY_USER_NAME {
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_CM_LOGIN_LOAD_USER_BY_USER_NAME")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CM_LOGIN_LOAD_USER_BY_USER_NAME", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
	}

	@ApiModel(value="IN_DATA_ROW-BR_CM_LOGIN_LOAD_USER_BY_USER_NAME")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("USER_ID")
		@Schema(name = "USER_ID", example = "jijs", description = "사용자ID")
		String USER_ID = "";
	}

	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_LOGIN_LOAD_USER_BY_USER_NAME")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_CM_LOGIN_LOAD_USER_BY_USER_NAME", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}

	@ApiModel(value="OUT_DATA_ROW-BR_CM_LOGIN_LOAD_USER_BY_USER_NAME")
	@Data
	static class OUT_DATA_ROW {
		@JsonProperty("userNo")
		@Schema(name = "userNo", example = "1", description = "사용자NO")
		Long USER_NO = null;
		@JsonProperty("userId")
		@Schema(name = "userId", example = "jijs", description = "사용자ID")
		String USER_ID = null;
		@JsonProperty("userPwd")
		@Schema(name = "userPwd", example = "****", description = "사용자패스워드")
		String USER_PWD = null;
		@JsonProperty("userNm")
		@Schema(name = "userNm", example = "홍길동", description = "사용자명")
		String USER_NM = null;
		@JsonProperty("email")
		@Schema(name = "email", example = "admin@gogo.com", description = "이메일")
		String EMAIL = null;

		@JsonProperty("auth")
		@Schema(name = "auth", example = "ROLE_ADMIN,ROLE_PUBLIC", description = "권한")
		String AUTH = null;
	}

	@Autowired
	private DA_CM_LOGIN daLogin;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_LOGIN"},value = "스피링시큐리티 로그인 조회.", notes = "")
	@PostMapping(path = "/api/BR_CM_LOGIN_LOAD_USER_BY_USER_NAME", consumes = "application/json", produces = "application/json")
	public @ResponseBody OUT_DS run(@RequestBody IN_DS inDs) throws BizException {
		if (inDs.IN_DATA.size() != 1) {
			throw new BizException("입력파라미터가 잘못되었습니다.");
		}
		IN_DATA_ROW rs = inDs.IN_DATA.get(0);

		if (rs.USER_ID == null) {
			throw new BizException("사용자ID가 입력되지 않았습니다.");
		}
		CmUser c = daLogin.findByUserId(rs.USER_ID);

		OUT_DS outDs = new OUT_DS();
		if (c != null) {
			List<String> auth_al = daLogin.findAuthByUserNo( c.getUserNo());
			OUT_DATA_ROW row = new OUT_DATA_ROW();
			row.USER_NO = c.getUserNo();
			row.USER_ID = c.getUserId();
			row.USER_PWD = c.getUserPwd();
			row.USER_NM = c.getUserNm();
			row.EMAIL = c.getEmail();
			if(auth_al.size()>0){
				row.AUTH  = String.join(",", auth_al);
			}
			outDs.OUT_DATA.add(row);
			
		}
		return outDs;
	}
}