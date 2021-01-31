package com.example.demo.br.cm.cm_domain;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.cm.DA_CM_DOMAIN;
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
@Tag(name = "CM_DOMAIN", description = "공통도메인")
public class BR_CM_DOMAIN_SAVE {

	@JsonRootName("IN_DS")
	@Schema(name="IN_DS",title="IN_DS-UPDT_DATA")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-UPDT_DATA", description = "입력 데이터")
		ArrayList<DATA_ROW> IN_DATA = new ArrayList<DATA_ROW>();
		
		@JsonProperty("UPDT_DATA")
		@Schema(name="IN_DATA-UPDT_DATA", description = "수정 데이터")
		ArrayList<DATA_ROW> UPDT_DATA = new ArrayList<DATA_ROW>();
		
		@JsonProperty("LSESSION")
		@Schema(name = "LSESSION",title="LSESSION-UPDT_DATA", description = "세션데이터")
		LSESSION_ROW LSESSION;
	}

	@Schema(name="DATA_ROW", title = "DATA_ROW-UPDT_DATA")
	@Data
	static class DATA_ROW {
		@JsonProperty("DMN_NO")
		@Schema(name = "DMN_NO", example = "1", description = "사용자NO")
		String DMN_NO = null;
		@JsonProperty("DMN_CD")
		@Schema(name = "DMN_CD", example = "1", description = "사용자NO")
		String DMN_CD = null;
		@JsonProperty("DMN_NM")
		@Schema(name = "DMN_NM", example = "jijs", description = "사용자ID")
		String DMN_NM = null;
		@JsonProperty("DATA_TYPE")
		@Schema(name = "DATA_TYPE", example = "****", description = "사용자패스워드")
		String DATA_TYPE = null;
		@JsonProperty("RMK")
		@Schema(name = "RMK", example = "****", description = "사용자패스워드")
		String RMK = null;
	
	}
	
	@JsonRootName("OUT_DS")
	@Schema(name="OUT_DS",title = "OUT_DS-UPDT_DATA")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA", title="OUT_DATA-UPDT_DATA", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}
	
	@Autowired
	DA_CM_DOMAIN daDmn;
	
	@Autowired
	DA_CM_SEQ daCmSeq;
	
	@Operation(summary = "도메인을 저장한다.", description = "")
	@PostMapping(path= "/api/BR_CM_DOMAIN_SAVE", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.LSESSION==null) {
			throw new BizRuntimeException("세션값이 넘어오지 않았습니다1.");
		}
		String USER_NO =inDS.LSESSION.getUSER_NO();
		if(PjtUtil.isEmpty(USER_NO)) {
			throw new BizRuntimeException("사용자NO가 넘어오지 않았습니다2.");
		}
		Long L_USER_NO = Long.parseLong(USER_NO);
		
		for( int i=0;i<inDS.IN_DATA.size();i++) {
			DATA_ROW  rs =inDS.IN_DATA.get(i);
			String  DMN_CD 		= PjtUtil.str(rs.DMN_CD);
			String  DMN_NM 		= PjtUtil.str(rs.DMN_NM);
			String  DATA_TYPE 	= PjtUtil.str(rs.DATA_TYPE);
			String  RMK 	= PjtUtil.str(rs.RMK);
			
			if(PjtUtil.isEmpty(DMN_CD)) {
				throw new BizRuntimeException("도메인코드 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(DMN_NM)) {
				throw new BizRuntimeException("도메인명 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(DATA_TYPE)) {
				throw new BizRuntimeException("데이터타입 입력되지 않았습니다.");
			}
			
			long L_DMN_NO =daCmSeq.increate("CM_DOMAIN_DMN_NO_SEQ");
						
			daDmn.createDomain(
					L_DMN_NO
					,DMN_CD
					,DMN_NM
					,DATA_TYPE
					,RMK
					);
		}
		
		for( int i=0;i<inDS.UPDT_DATA.size();i++) {
			DATA_ROW  rs =inDS.UPDT_DATA.get(i);
			String  DMN_NO 		= PjtUtil.str(rs.DMN_NO);
			String  DMN_CD 		= PjtUtil.str(rs.DMN_CD);
			String  DMN_NM 		= PjtUtil.str(rs.DMN_NM);
			String  DATA_TYPE 	= PjtUtil.str(rs.DATA_TYPE);
			String  RMK 	= PjtUtil.str(rs.RMK);
			
			if(PjtUtil.isEmpty(DMN_NO)) {
				throw new BizRuntimeException("도메인번호가 입력되지 않았습니다.");
			}
			
			if(PjtUtil.isEmpty(DMN_CD)) {
				throw new BizRuntimeException("도메인코드 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(DMN_NM)) {
				throw new BizRuntimeException("도메인명 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(DATA_TYPE)) {
				throw new BizRuntimeException("데이터타입 입력되지 않았습니다.");
			}
			long L_DMN_NO = Long.parseLong(DMN_NO);
			daDmn.updateDomain(
					 L_DMN_NO
					,DMN_CD
					,DMN_NM
					,DATA_TYPE
					,RMK
					);
		}

		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
}
