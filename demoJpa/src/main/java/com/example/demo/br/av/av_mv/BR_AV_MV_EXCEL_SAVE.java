package com.example.demo.br.av.av_mv;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.anotation.OpService;
import com.example.demo.ctrl.LSESSION_ROW;
import com.example.demo.db.da.av.DA_AV_MV;
import com.example.demo.db.da.cm.DA_CM_EXCEL_UPLD;
import com.example.demo.db.da.cm.DA_CM_SEQ;
import com.example.demo.db.domain.av.AvMv;
import com.example.demo.db.domain.cm.CmExcelUpld;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
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
@OpService
@Service
public class BR_AV_MV_EXCEL_SAVE {

	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_AV_MV_EXCEL_SAVE")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_AV_MV_EXCEL_SAVE", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
		
		@JsonProperty("LSESSION")
		LSESSION_ROW LSESSION;
	}
	
	@ApiModel(value="IN_DATA_ROW-BR_AV_MV_EXCEL_SAVE")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("FILE_ID")
		@Schema(name = "FILE_ID", example = "1", description = "파일 ID")
		String FILE_ID = null;
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_AV_MV_EXCEL_SAVE")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name = "OUT_DATA", title="OUT_DATA-BR_AV_MV_EXCEL_SAVE", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();
	}

	
	@ApiModel(value="OUT_DATA_ROW-BR_AV_MV_EXCEL_SAVE")
	@Data
	static class OUT_DATA_ROW {
		
		@JsonProperty("ROW_CNT")
		@Schema(name = "ROW_CNT", example = "1", description = "반영-수")
		Long ROW_CNT = null;
	}
	
	@Autowired
	DA_AV_MV daAvMv;

	@Autowired
	DA_CM_SEQ daCmSeq;

	@Autowired
	DA_CM_EXCEL_UPLD daCmExcelUpld;

	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"AV"},value = "AV작품을 EXCEL을 기반으로 저장한다.", notes = "")
	//@PostMapping(path= "/api/BR_AV_MV_EXCEL_SAVE", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.LSESSION==null) {
			throw new BizRuntimeException("세션값이 넘어오지 않았습니다1.");
		}
		String SESSION_USER_NO =inDS.LSESSION.getUSER_NO();
		if(PjtUtil.isEmpty(SESSION_USER_NO)) {
			throw new BizRuntimeException("사용자NO가 넘어오지 않았습니다2.");
		}
		Long L_SESSION_USER_NO = Long.parseLong(SESSION_USER_NO);
		
		if(inDS.IN_DATA.size()!=1) {
			throw new BizRuntimeException("잘못된 파라미터 입니다.");
		}
		String FILE_ID = inDS.IN_DATA.get(0).FILE_ID;
		
		List<CmExcelUpld>  al = daCmExcelUpld.findExcelUpld(FILE_ID);

		int ROW_CNT=0;
				
		for( int i=0;i<al.size();i++) {
			CmExcelUpld  rs =al.get(i);
			if(rs.getGbn().equals("D")) {  //상세 
				String  AV_NM 	= PjtUtil.str(rs.getCol00());
				String  TTL 	= PjtUtil.str(rs.getCol01());
				String  CNTNT	= PjtUtil.str(rs.getCol02());
				String  MSC_CD 	= PjtUtil.str(rs.getCol03());
				String  VR_YN  	= PjtUtil.str(rs.getCol04());  
				String  ORD 	= PjtUtil.str(rs.getCol05());
				String  RMK 	= PjtUtil.str(rs.getCol06());		
				String  CPTN_YN = PjtUtil.str(rs.getCol07());
				String  MK_DT 	= PjtUtil.str(rs.getCol08());
				
				if(PjtUtil.isEmpty(AV_NM)) {
					throw new BizRuntimeException("품번이 입력되지 않았습니다.");
				}
				if(PjtUtil.isEmpty(MSC_CD)) {
					throw new BizRuntimeException("모자이크코드가 입력되지 않았습니다.");
				}
				if(PjtUtil.isEmpty(CPTN_YN)) {
					throw new BizRuntimeException("자막 유무가 입력되지 않았습니다.");
				}
				
				/*품번이 중복 되지 않는지 조회 해야한다.*/
				List<AvMv> al2=  daAvMv.findByAvNm(AV_NM.toUpperCase().trim());
				if(al2.size()>0) {
					long L_AV_SEQ =al2.get(0).getAvSeq();
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
							ROW_CNT=ROW_CNT+1;
				} else {
					long L_AV_SEQ =daCmSeq.increate("AV_MV_AV_SEQ");
					daAvMv.createAvMv(L_AV_SEQ
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
							ROW_CNT=ROW_CNT+1;
				}
			}
		}
		OUT_DS outDs = new OUT_DS(); 

		OUT_DATA_ROW odr = new OUT_DATA_ROW();
		odr.ROW_CNT= (long) ROW_CNT;
	
		outDs.OUT_DATA.add(odr);
		return outDs;
	}
}
