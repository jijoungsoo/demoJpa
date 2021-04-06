package com.example.demo.br.cm.cm_user;

import java.util.ArrayList;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.cm.DA_CM_SEQ;
import com.example.demo.db.da.cm.DA_CM_USER;
import com.example.demo.db.domain.cm.CmUser;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

@Slf4j
@OpService
@Service
@Tag(name = "CM_USER", description = "사용자정보")
public class BR_CM_USER_SAVE {
	@Autowired
    PjtUtil pjtU;

	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_CM_USER_SAVE")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA,UPDT_DATA,LSESSION", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;
		
		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CM_USER_SAVE", description = "입력 데이터")
		ArrayList<DATA_ROW> IN_DATA = new ArrayList<DATA_ROW>();
		
		@JsonProperty("UPDT_DATA")
		@Schema(name="IN_DATA-BR_CM_USER_SAVE", description = "수정 데이터")
		ArrayList<DATA_ROW> UPDT_DATA = new ArrayList<DATA_ROW>();
		
		@JsonProperty("LSESSION")
		LSESSION_ROW LSESSION;
	}

	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_USER_SAVE")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA", title="OUT_DATA-BR_CM_USER_SAVE", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}

	@ApiModel(value="DATA_ROW-BR_CM_USER_SAVE")
	@Data
	static class DATA_ROW {
		@JsonProperty("USER_NO")
		@Schema(name = "USER_NO", example = "1", description = "사용자NO")
		String USER_NO = null;
		
		@JsonProperty("USER_NM")
		@Schema(name = "USER_NM", example = "홍길동", description = "사용자명")
		String USER_NM = null;
		
		@JsonProperty("USER_ID")
		@Schema(name = "USER_ID", example = "admin", description = "사용자ID")
		String USER_ID = null;
		
		@JsonProperty("USER_PWD")
		@Schema(name = "USER_PWD", example = "****", description = "패스워드")
		String USER_PWD = null;
		
		@JsonProperty("EMAIL")
		@Schema(name = "EMAIL", example = "admin@gogo.com", description = "이메일")
		String EMAIL = null;
		
		@JsonProperty("USE_YN")
		@Schema(name = "USE_YN", example = "(Y,N)", description = "사용여부")
		String USE_YN = null;
		
		@JsonProperty("RMK")
		@Schema(name = "RMK", example = "비고", description = "비고")
		String RMK = null;
		
		@JsonProperty("LST_ACC_DTM")
		@Schema(name = "LST_ACC_DTM", example = "202012311640", description = "마지막접속일")
		String LST_ACC_DTM = null;
	}
	
	@Autowired
	DA_CM_USER daCmUser;
	
	@Autowired
	DA_CM_SEQ daCmSeq;
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_USER"},value = "사용자 저장.", notes = "")
	//@PostMapping(path= "/api/BR_CM_USER_SAVE", consumes = "application/json", produces = "application/json")
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
			DATA_ROW  rs =inDS.IN_DATA.get(i);
			String  USER_NO 		= pjtU.str(rs.USER_NO);
			String  USER_NM 		= pjtU.str(rs.USER_NM);
			String  USER_ID		 	= pjtU.str(rs.USER_ID);
			String  USER_PWD 		= pjtU.str(rs.USER_PWD);
			String  EMAIL 			= pjtU.str(rs.EMAIL);
			String  USE_YN 			= pjtU.str(rs.USE_YN);
			String  RMK 			= pjtU.str(rs.RMK);
			
			if(USER_NO.equals("")) {
				if(pjtU.isEmpty(USER_NM)) {
					throw new BizRuntimeException("사용자명이 입력되지 않았습니다.");
				}
				if(pjtU.isEmpty(USER_ID)) {
					throw new BizRuntimeException("사용자ID가 입력되지 않았습니다.");
				}
				if(pjtU.isEmpty(USER_PWD)) {
					throw new BizRuntimeException("패스워드가 입력되지 않았습니다.");
				}
				if(pjtU.isEmpty(EMAIL)) {
					throw new BizRuntimeException("이메일주소가 입력되지 않았습니다.");
				}
				if(pjtU.isEmpty(USE_YN)) {
					throw new BizRuntimeException("사용여부가 입력되지 않았습니다.");
				}
				
				long L_USER_NO =daCmSeq.increate("CM_USER_USER_NO_SEQ");
				//id중복검사
				CmUser tmp =daCmUser.findByUserId(USER_ID);
				if(tmp!=null) {
					throw new BizException("사용자ID가 중복됩니다.");
				}
				BCryptPasswordEncoder passwordEncoding = new BCryptPasswordEncoder();
				String S_USER_PWD =passwordEncoding.encode(USER_PWD);
				
			
				daCmUser.createCmUser(
						L_USER_NO
						,USER_NM
						,USER_ID
						,S_USER_PWD
						,EMAIL
						,USE_YN
						,RMK
						,"JI"
						,"0"
						,""
						,L_LSESSION_USER_NO
						);
			} else {
				if(pjtU.isEmpty(USER_NO)) {
					throw new BizRuntimeException("사용자번호가 입력되지 않았습니다.");
				}			
				if(pjtU.isEmpty(USER_NM)) {
					throw new BizRuntimeException("사용자명이 입력되지 않았습니다.");
				}
				if(pjtU.isEmpty(USER_ID)) {
					throw new BizRuntimeException("사용자ID가 입력되지 않았습니다.");
				}

				if(pjtU.isEmpty(EMAIL)) {
					throw new BizRuntimeException("이메일주소가 입력되지 않았습니다.");
				}
				if(pjtU.isEmpty(USE_YN)) {
					throw new BizRuntimeException("사용여부가 입력되지 않았습니다.");
				}
								
				long L_USER_NO = Long.parseLong(USER_NO);
				daCmUser.updateCmUser(
						L_USER_NO
						,USER_NM
						,USER_ID
						,EMAIL
						,USE_YN
						,RMK
						,L_LSESSION_USER_NO
						);
			}
		}
		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
}
