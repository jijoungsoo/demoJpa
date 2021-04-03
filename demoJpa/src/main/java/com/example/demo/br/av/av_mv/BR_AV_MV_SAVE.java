package com.example.demo.br.av.av_mv;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.av.DA_AV_MV;
import com.example.demo.db.da.cm.DA_CM_SEQ;
import com.example.demo.db.domain.av.AvMv;
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

@Tag(name = "AV", description = "AV정보")
@Slf4j
//@RestController
@OpService
@Service
public class BR_AV_MV_SAVE {

	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_AV_MV_SAVE")
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

	@ApiModel(value="DATA_ROW-BR_AV_MV_SAVE")
	@Data
	static class DATA_ROW {
		@JsonProperty("AV_SEQ")
		@Schema(name = "AV_SEQ", example = "1", description = "사용자NO")
		Long AV_SEQ = null;
		
		@JsonProperty("AV_NM")
		@Schema(name = "AV_NM", example = "jijs", description = "사용자ID")
		String AV_NM = null;
		
		@JsonProperty("TTL")
		@Schema(name = "TTL", example = "제목입니다.", description = "제목")
		String TTL = null;
		
		@JsonProperty("CNTNT")
		@Schema(name = "CNTNT", example = "내용입니다.", description = "내용")
		String CNTNT = null;
		
		@JsonProperty("LK_CNT")
		@Schema(name = "LK_CNT", example = "1", description = "좋아요 카운트")
		String LK_CNT = null;
		
		@JsonProperty("MSC_CD")
		@Schema(name = "MSC_CD", example = "(U-유출,M-모자이크,A-모자이크AI,N-노모)", description = "모자이크 코드")
		String MSC_CD = null;

		@JsonProperty("VR_YN")
		@Schema(name = "VR_YN", example = "(Y-VR,N-N-VR)", description = "VR여부")
		String VR_YN = null;
		
		@JsonProperty("ORD")
		@Schema(name = "ORD", example = "001", description = "정렬")
		String ORD = null;
		
		@JsonProperty("RMK")
		@Schema(name = "RMK", example = "비고", description = "비고")
		String RMK = null;
		
		@JsonProperty("CPTN_YN")
		@Schema(name = "CPTN_YN", example = "(Y-자막있음,N-자막없음)", description = "자막YN")
		String CPTN_YN = null;
		
		@JsonProperty("MK_DT")
		@Schema(name = "MK_DT", example = "20201231", description = "작품발매일")
		String MK_DT = null;
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_AV_MV_SAVE")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA", title="OUT_DATA-UPDT_DATA", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}
	
	@Autowired
	DA_AV_MV daAvMv;
	
	@Autowired
	DA_CM_SEQ daCmSeq;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"AV"},value = "AV작품을 저장한다.", notes = "")
	//@PostMapping(path= "/api/BR_AV_MV_SAVE", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.LSESSION==null) {
			throw new BizRuntimeException("세션값이 넘어오지 않았습니다1.");
		}
		String SESSION_USER_NO =inDS.LSESSION.getUSER_NO();
		if(PjtUtil.isEmpty(SESSION_USER_NO)) {
			throw new BizRuntimeException("사용자NO가 넘어오지 않았습니다2.");
		}
		Long L_SESSION_USER_NO = Long.parseLong(SESSION_USER_NO);
				
		for( int i=0;i<inDS.IN_DATA.size();i++) {
			DATA_ROW  rs =inDS.IN_DATA.get(i);
			String  AV_NM 	= PjtUtil.str(rs.AV_NM);
			String  TTL 	= PjtUtil.str(rs.TTL);
			String  CNTNT 	= PjtUtil.str(rs.CNTNT);
			String  MSC_CD 	= PjtUtil.str(rs.MSC_CD);
			String  VR_YN 	= PjtUtil.str(rs.VR_YN);
			String  ORD 	= PjtUtil.str(rs.ORD);
			String  RMK 	= PjtUtil.str(rs.RMK);
			String  CPTN_YN = PjtUtil.str(rs.CPTN_YN);
			String  MK_DT 	= PjtUtil.str(rs.MK_DT);
			
			if(PjtUtil.isEmpty(AV_NM)) {
				throw new BizRuntimeException("작품명이 입력되지 않았습니다.");
			}
			
			/*품번이 중복 되지 않는지 조회 해야한다.*/
			List<AvMv> al=  daAvMv.findByAvNm(AV_NM.toUpperCase().trim());
			if(al.size()>0) {
				throw new BizRuntimeException("작품명이 기존에 입력되어있던 작품과 중복됩니다.");
			}
			
			
			if(PjtUtil.isEmpty(MSC_CD)) {
				throw new BizRuntimeException("모자이크 유형이 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(CPTN_YN)) {
				throw new BizRuntimeException("자막유무가 입력되지 않았습니다.");
			}	
			if(PjtUtil.isEmpty(VR_YN)) {
				throw new BizRuntimeException("VR여부가 입력되지 않았습니다.");
			}	
			
			long L_AV_SEQ =daCmSeq.increate("AV_MV_AV_SEQ");
			
			daAvMv.createAvMv(
					L_AV_SEQ
					,AV_NM.toUpperCase().trim()
					,TTL
					,CNTNT
					,MSC_CD
					,VR_YN
					,ORD
					,RMK
					,CPTN_YN
					,MK_DT
					,L_SESSION_USER_NO
					);
		}
		
		for( int i=0;i<inDS.UPDT_DATA.size();i++) {
			DATA_ROW  rs =inDS.UPDT_DATA.get(i);
			String  AV_SEQ 	= PjtUtil.str(rs.AV_SEQ);
			String  AV_NM 	= PjtUtil.str(rs.AV_NM);
			String  TTL 	= PjtUtil.str(rs.TTL);
			String  CNTNT 	= PjtUtil.str(rs.CNTNT);
			String  MSC_CD 	= PjtUtil.str(rs.MSC_CD);
			String  VR_YN 	= PjtUtil.str(rs.VR_YN);
			String  ORD 	= PjtUtil.str(rs.ORD);		
			String  RMK 	= PjtUtil.str(rs.RMK);
			String  CPTN_YN = PjtUtil.str(rs.CPTN_YN);
			String  MK_DT	= PjtUtil.str(rs.MK_DT);
			
			if(PjtUtil.isEmpty(AV_SEQ)) {
				throw new BizRuntimeException("작품일련번호가 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(AV_NM)) {
				throw new BizRuntimeException("작품명이 입력되지 않았습니다.");
			}
			
			if(PjtUtil.isEmpty(MSC_CD)) {
				throw new BizRuntimeException("모자이크 유형이 입력되지 않았습니다.");
			}
			if(PjtUtil.isEmpty(CPTN_YN)) {
				throw new BizRuntimeException("자막유무가 입력되지 않았습니다.");
			}	
			if(PjtUtil.isEmpty(VR_YN)) {
				throw new BizRuntimeException("VR여부가 입력되지 않았습니다.");
			}	
			long L_AV_SEQ =Long.parseLong(AV_SEQ);
			daAvMv.updateAvMv(
					L_AV_SEQ
					,AV_NM.toUpperCase().trim()
					,TTL
					,CNTNT
					,MSC_CD
					,VR_YN
					,ORD
					,RMK
					,CPTN_YN
					,MK_DT
					,L_SESSION_USER_NO
					);
		}

		OUT_DS outDs = new OUT_DS(); 
		return outDs;
	}
}
