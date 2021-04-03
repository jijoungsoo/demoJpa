package com.example.demo.br.cm.cm_login;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.example.demo.anotation.OpService;
import com.example.demo.db.da.cm.DA_CM_LOGIN;
import com.example.demo.db.da.cm.DA_CM_OAUTH_LOG;
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
@Transactional
@OpService
@Service
@Tag(name = "CM_USER", description = "사용자정보")
public class BR_CM_LOGIN_SNS {

	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_CM_LOGIN_SNS")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA,UPDT_DATA,LSESSION", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;
		
		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_CM_LOGIN_SNS", description = "입력 데이터")
		ArrayList<DATA_ROW> IN_DATA = new ArrayList<DATA_ROW>();
		
		@JsonProperty("UPDT_DATA")
		@Schema(name="IN_DATA-BR_CM_LOGIN_SNS", description = "수정 데이터")
		ArrayList<DATA_ROW> UPDT_DATA = new ArrayList<DATA_ROW>();
	}

	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_CM_LOGIN_SNS")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA", title="OUT_DATA-BR_CM_LOGIN_SNS", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}


	@ApiModel(value="DATA_ROW-BR_CM_LOGIN_SNS")
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
		
		@JsonProperty("EMAIL")
		@Schema(name = "EMAIL", example = "admin@gogo.com", description = "이메일")
		String EMAIL = null;
		
		@JsonProperty("USE_YN")
		@Schema(name = "USE_YN", example = "(Y,N)", description = "사용여부")
		String USE_YN = null;
		
		@JsonProperty("RMK")
		@Schema(name = "RMK", example = "비고", description = "비고")
		String RMK = null;

		@JsonProperty("SNS_GUBUN")
		@Schema(name = "SNS_GUBUN", example = "kakao, google", description = "SNS로그인 구분")
		String SNS_GUBUN = null;

		@JsonProperty("SNS_ID")
		@Schema(name = "SNS_ID", example = "1", description = "SNS로그인 실별자")
		String SNS_ID = null;

		@JsonProperty("BRTHDAY")
		@Schema(name = "BRTHDAY", example = "0512", description = "생일 MMDD")
		String BRTHDAY = null;

		@JsonProperty("GNDR")
		@Schema(name = "GNDR", example = "MALE_FEMALE", description = "성별")
		String GNDR = null;

		
		@JsonProperty("PRF_IMG")
		@Schema(name = "PRF_IMG", example = "aa.jpg", description = "프로필 이미지")
		String PRF_IMG = null;

		@JsonProperty("THMB_IMG")
		@Schema(name = "THMB_IMG", example = "aa.jpg", description = "프로필 썸네일 이미지 ")
		String THMB_IMG = null;

	}

	
	@ApiModel(value="OUT_DATA_ROW-BR_CM_LOGIN_SNS")
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
	DA_CM_USER daCmUser;
	
	@Autowired
	DA_CM_SEQ daCmSeq;

	@Autowired
	DA_CM_OAUTH_LOG daCmOauthLog;

	@Autowired
	private DA_CM_LOGIN daLogin;
	
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"CM_USER"},value = "SNS 사용자 로그인.", notes = "")
	//@PostMapping(path= "/api/BR_CM_LOGIN_SNS", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
	

		if(inDS.IN_DATA.size()!=1){
			throw new BizRuntimeException("입력 정보는 하나만 넘어와야합니다.");
		}
		
		
		DATA_ROW  rs =inDS.IN_DATA.get(0);
		String  USER_NO 		= PjtUtil.str(rs.USER_NO);
		String  USER_NM 		= PjtUtil.str(rs.USER_NM);
		String  USER_ID		 	= PjtUtil.str(rs.USER_ID);
		String  EMAIL 			= PjtUtil.str(rs.EMAIL);
		String  USE_YN 			= PjtUtil.str(rs.USE_YN);
		String  RMK 			= PjtUtil.str(rs.RMK);
		String  SNS_GUBUN		= PjtUtil.str(rs.SNS_GUBUN);
		String  SNS_ID			= PjtUtil.str(rs.SNS_ID);
		String  BRTHDAY			= PjtUtil.str(rs.BRTHDAY);
		String  GNDR			= PjtUtil.str(rs.GNDR);
		String  PRF_IMG			= PjtUtil.str(rs.PRF_IMG);
		String  THMB_IMG     	= PjtUtil.str(rs.THMB_IMG);


		if(PjtUtil.isEmpty(SNS_GUBUN)) {
			throw new BizRuntimeException("SNS구분이 입력되지 않았습니다.");
		}

		if(PjtUtil.isEmpty(SNS_ID)) {
			throw new BizRuntimeException("SNS id가 입력되지 않았습니다.");
		}

		List<CmUser>  al = daCmUser.findCmUserBySnsId(SNS_GUBUN, SNS_ID);

		
		if(al.size()>1) {
			throw new BizRuntimeException("SNS 로그인 대상이 1개 이상입니다. 관리자에게 문의하세요.");
		}

		if(al.size()==0){
			if(PjtUtil.isEmpty(USER_NM)) {
				throw new BizRuntimeException("사용자명이 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(USER_ID)) {
				throw new BizRuntimeException("사용자ID가 입력되지 않았습니다.");
			}

			if(PjtUtil.isEmpty(EMAIL)) {
				throw new BizRuntimeException("이메일주소가 입력되지 않았습니다.");
			}
			
			long L_USER_NO =daCmSeq.increate("CM_USER_USER_NO_SEQ");
			//id중복검사
			CmUser tmp =daCmUser.findByUserId(USER_ID);
			if(tmp!=null) {
				throw new BizException("사용자ID가 중복됩니다.");
			}
			BCryptPasswordEncoder passwordEncoding = new BCryptPasswordEncoder();
			String S_USER_PWD =passwordEncoding.encode("1234");					
			daCmUser.createCmUser(
					L_USER_NO
					,USER_NM
					,USER_ID
					,S_USER_PWD
					,EMAIL
					,"Y" /*USE_YN*/
					,RMK
					,SNS_GUBUN
					,SNS_ID
					,GNDR
					,0L
					);
		} else {
			CmUser c = al.get(0);
			long L_USER_NO = c.getUserNo();
			USE_YN = c.getUseYn();
			if(PjtUtil.isEmpty(USER_ID)) {
				throw new BizRuntimeException("사용자ID가 입력되지 않았습니다.");
			}

			if(PjtUtil.isEmpty(USER_NM)) {
				throw new BizRuntimeException("사용자명이 입력되지 않았습니다.");
			}
	
			if(PjtUtil.isEmpty(EMAIL)) {
				throw new BizRuntimeException("이메일주소가 입력되지 않았습니다.");
			}
			
			daCmUser.updateCmUser(
					L_USER_NO
					,USER_NM
					,USER_ID
					,EMAIL
					,USE_YN
					,RMK
					,0L
					);
		}	

		Long L_SEQ_NO = daCmSeq.increate("CM_OATH_LOG_SEQ_NO");

		daCmOauthLog.createCmOauthLog(
			 L_SEQ_NO
			, SNS_GUBUN
			, SNS_ID
			, USER_NM
			, PRF_IMG
			, THMB_IMG
			, EMAIL
			, BRTHDAY
			, GNDR);


		OUT_DS outDs = new OUT_DS(); 

		List<CmUser>  al2 = daCmUser.findCmUserBySnsId(SNS_GUBUN, SNS_ID);
		if(al2.size()==0){
			throw new BizRuntimeException("SNS 로그인이 처리되지 않았습니다.");
		}

		for(int i=0;i<al2.size();i++) {
			CmUser cm=al2.get(i);
			OUT_DATA_ROW  row = new OUT_DATA_ROW();
			List<String> auth_al = daLogin.findAuthByUserNo( cm.getUserNo());
			row.USER_NO=cm.getUserNo();
			row.USER_NM=cm.getUserNm();
			row.USER_ID=cm.getUserId();
			row.EMAIL=cm.getEmail();
			if(auth_al.size()>0){
				row.AUTH  = String.join(",", auth_al);
			}
			outDs.OUT_DATA.add(row);
		}
		return outDs;
	}
}
