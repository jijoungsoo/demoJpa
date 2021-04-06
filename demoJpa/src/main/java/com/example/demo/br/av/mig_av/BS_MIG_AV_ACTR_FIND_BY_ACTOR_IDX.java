package com.example.demo.br.av.mig_av;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.anotation.OpService;
import com.example.demo.db.da.mig_av.DA_MIG_AV_ACTR_IMG;
import com.example.demo.db.da.mig_av.DA_MIG_AV_MV;
import com.example.demo.db.domain.mig_av.MigAvActr;
import com.example.demo.db.domain.mig_av.MigAvActrImg;
import com.example.demo.db.domain.mig_av.MigAvMv;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
import com.example.demo.sa.mig.mig_av.SA_MIG_AV_ACTR_DTL_GET;
import com.example.demo.sa.mig.mig_av.SA_MIG_AV_MV_DTL_GET;
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
@OpService
@Service
public class BS_MIG_AV_ACTR_FIND_BY_ACTOR_IDX {
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BS_MIG_AV_ACTR_FIND_BY_ACTOR_IDX")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

		
		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BS_MIG_AV_ACTR_FIND_BY_ACTOR_IDX", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
	}

	@ApiModel(value="IN_DATA_ROW-BS_MIG_AV_ACTR_FIND_BY_ACTOR_IDX")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("ACTOR_IDX")
		@Schema(name = "ACTOR_IDX", example = "1", description = "ACTOR_IDX")
		String ACTOR_IDX = "";
	}

	@Autowired
    PjtUtil pjtU;
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BS_MIG_AV_ACTR_FIND_BY_ACTOR_IDX")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BS_MIG_AV_ACTR_FIND_BY_ACTOR_IDX", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();

		@JsonProperty("OUT_DATA_IMG")
		@Schema(name="OUT_DATA_IMG-BS_MIG_AV_ACTR_FIND_BY_ACTOR_IDX", description = "출력 데이터")
		ArrayList<OUT_DATA_IMG_ROW> OUT_DATA_IMG = new ArrayList<OUT_DATA_IMG_ROW>();

		@JsonProperty("OUT_DATA_BEST")
		@Schema(name="OUT_DATA_BEST-BS_MIG_AV_ACTR_FIND_BY_ACTOR_IDX", description = "출력 데이터")
		ArrayList<OUT_DATA_BEST_ROW> OUT_DATA_BEST = new ArrayList<OUT_DATA_BEST_ROW>();

		@JsonProperty("OUT_DATA_DVD")
		@Schema(name="OUT_DATA_DVD-BS_MIG_AV_ACTR_FIND_BY_ACTOR_IDX", description = "출력 데이터")
		ArrayList<OUT_DATA_DVD_ROW> OUT_DATA_DVD = new ArrayList<OUT_DATA_DVD_ROW>();
	}

	@ApiModel(value="OUT_DATA_ROW-BS_MIG_AV_ACTR_FIND_BY_ACTOR_IDX")
	@Data
	static class OUT_DATA_ROW {
		@JsonProperty("ACTOR_IDX")
		@Schema(name = "ACTOR_IDX", example = "1", description = "배우IDX")
		Long ACTOR_IDX = null;
		@JsonProperty("IMG")
		@Schema(name = "IMG", example = "adf.jpg", description = "프로필")
		String IMG = null;
		@JsonProperty("IMG_S")
		@Schema(name = "IMG_S", example = "adf.jpg", description = "작은프로필")
		String IMG_S = null;

		@JsonProperty("IMG_L")
		@Schema(name = "IMG_L", example = "adf.jpg", description = "IMG_L")
		String IMG_L = null;

		@JsonProperty("IMG_LS")
		@Schema(name = "IMG_LS", example = "adf.jpg", description = "IMG_LS")
		String IMG_LS = null;

		@JsonProperty("DEBUT_DT")
		@Schema(name = "DEBUT_DT", example = "20200302", description = "데뷔일")
		String DEBUT_DT = null;
		@JsonProperty("NAME_KR")
		@Schema(name = "NAME_KR", example = "마사카", description = "이름(한글)")
		String NAME_KR = null;
		@JsonProperty("NAME_EN")
		@Schema(name = "NAME_EN", example = "masaka", description = "이름(영어)")
		String NAME_EN = null;
		@JsonProperty("NAME_CN")
		@Schema(name = "NAME_CN", example = "중국어", description = "이름(중국어)")
		String NAME_CN = null;
		@JsonProperty("BIRTH")
		@Schema(name = "BIRTH", example = "0316", description = "월일")
		String BIRTH = null;
		@JsonProperty("HEIGHT")
		@Schema(name = "HEIGHT", example = "173", description = "키")
		String HEIGHT = null;
		@JsonProperty("SIZE")
		@Schema(name = "SIZE", example = "3 4 5", description = "쓰리사이즈")
		String SIZE = null;
		@JsonProperty("BRA_SIZE")
		@Schema(name = "BRA_SIZE", example = "F", description = "브라사이즈")
		String BRA_SIZE = null;
		@JsonProperty("O_NM")
		@Schema(name = "O_NM", example = "혼또", description = "다른이름")
		String O_NM = null;
		
		@JsonProperty("DSCR_TTL")
		@Schema(name = "DSCR_TTL", example = "야호", description = "프로필상세 타이틀")
		String DSCR_TTL = null;

		@JsonProperty("DSCR")
		@Schema(name = "DSCR", example = "야호", description = "프로필 상세")
		String DSCR = null;


		@JsonProperty("SYNC")
		@Schema(name = "SYNC", example = "Y", description = "SYNC")
		String SYNC = null;
		
		@JsonProperty("CRT_DTM")
		@Schema(name = "CRT_DTM", example = "202012311640", description = "생성일시")
		String CRT_DTM = null;
	}

	
	@ApiModel(value="OUT_DATA_IMG_ROW-BS_MIG_AV_ACTR_FIND_BY_ACTOR_IDX")
	@Data
	static class OUT_DATA_IMG_ROW {
		@JsonProperty("ACTOR_IDX")
		@Schema(name = "ACTOR_IDX", example = "1", description = "배우IDX")
		Long ACTOR_IDX = null;

		@JsonProperty("IMG_SEQ")
		@Schema(name = "IMG_SEQ", example = "1", description = "IMG_SEQ")
		Long IMG_SEQ = null;

		@JsonProperty("CRT_DTM")
		@Schema(name = "CRT_DTM", example = "202012311640", description = "생성일시")
		String CRT_DTM = null;
	}

	
	@ApiModel(value="OUT_DATA_BEST_ROW-BS_MIG_AV_ACTR_FIND_BY_ACTOR_IDX")
	@Data
	static class OUT_DATA_BEST_ROW {
		@JsonProperty("MAIN_ACTOR_IDX")
		@Schema(name = "MAIN_ACTOR_IDX", example = "1", description = "메인 배우IDX")
		Long MAIN_ACTOR_IDX = null;

		@JsonProperty("DVD_IDX")
		@Schema(name = "DVD_IDX", example = "1", description = "DVD_IDX")
		Long DVD_IDX = null;


		@JsonProperty("MV_NM")
		@Schema(name = "MV_NM", example = "AV111", description = "품번")
		String MV_NM = null;


		@JsonProperty("BEST_YN")
		@Schema(name = "BEST_YN", example = "Y", description = "베스트 작품")
		String BEST_YN = null;
		
		@JsonProperty("TTL_KR")
		@Schema(name = "TTL_KR", example = "야호", description = "작품 타이틀 설명")
		String TTL_KR = null;
	}


	@ApiModel(value="OUT_DATA_DVD_ROW-BS_MIG_AV_ACTR_FIND_BY_ACTOR_IDX")
	@Data
	static class OUT_DATA_DVD_ROW {
		@JsonProperty("MAIN_ACTOR_IDX")
		@Schema(name = "MAIN_ACTOR_IDX", example = "1", description = "메인 배우IDX")
		Long MAIN_ACTOR_IDX = null;

		@JsonProperty("DVD_IDX")
		@Schema(name = "DVD_IDX", example = "1", description = "DVD_IDX")
		Long DVD_IDX = null;


		@JsonProperty("MV_NM")
		@Schema(name = "MV_NM", example = "AV111", description = "품번")
		String MV_NM = null;


		@JsonProperty("BEST_YN")
		@Schema(name = "BEST_YN", example = "Y", description = "베스트 작품")
		String BEST_YN = null;
		
		@JsonProperty("TTL_KR")
		@Schema(name = "TTL_KR", example = "야호", description = "작품 타이틀 설명")
		String TTL_KR = null;

		@JsonProperty("OPEN_DT")
		@Schema(name = "OPEN_DT", example = "야호", description = "20201231")
		String OPEN_DT = null;		
	}
	
	@Autowired
	SA_MIG_AV_ACTR_DTL_GET saMigAvActrDtlGet;

	@Autowired
	SA_MIG_AV_MV_DTL_GET saMigAvMvDtlGet;

	@Autowired
	DA_MIG_AV_ACTR_IMG daMigAvActrImg;

	@Autowired
	DA_MIG_AV_MV daMigAvMv;
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"AV"}, value = "AV배우 하나를 조회한다.", notes = "페이징 처리")
	//@PostMapping(path= "/api/BS_MIG_AV_ACTR_FIND_BY_ACTOR_IDX", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.IN_DATA==null) {
			throw new BizRuntimeException("[IN_DATA]입력파라미터가 전달되지 않았습니다.");
		}
		if(inDS.IN_DATA.size()!=1) {
			throw new BizRuntimeException("[IN_DATA]입력파라미터의 ["+inDS.IN_DATA.size()+"]행수가 잘못되었습니다.");
		}
		
		IN_DATA_ROW  rs =inDS.IN_DATA.get(0);
		String  ACTOR_IDX 		= pjtU.str(rs.ACTOR_IDX);

		if(pjtU.isEmpty(ACTOR_IDX)){
			throw new BizRuntimeException("ACTOR_IDX가 전달되지 않았습니다.");
		}
		Long L_ACTOR_IDX  = Long.parseLong(ACTOR_IDX);
		MigAvActr c= saMigAvActrDtlGet.run(L_ACTOR_IDX,false);
		if(c==null){
			throw new BizRuntimeException("ACTOR_IDX에 해당하는 배우가 없습니다.");
		}
		OUT_DS outDs = new OUT_DS();
		OUT_DATA_ROW row = new OUT_DATA_ROW();
		row.ACTOR_IDX = c.getActrIdx();
		row.IMG = c.getImg();
		row.IMG_S = c.getImgS();
		row.IMG_L = c.getImgL();
		row.IMG_LS = c.getImgLs();
		
		row.DEBUT_DT = c.getDebutDt();
		row.NAME_KR = c.getNmKr();
		row.NAME_EN = c.getNmEn();
		row.NAME_CN = c.getNmCn();
		row.BIRTH = c.getBrth();
		row.HEIGHT = c.getHeight();
		row.SIZE = c.getSize();
		row.BRA_SIZE = c.getBrSize();
		row.O_NM = c.getONm();
		row.DSCR_TTL = c.getDscrTtl();
		row.DSCR = c.getDscr();
		row.SYNC = c.getSync();
		row.CRT_DTM = pjtU.getYyyy_MM_dd_HHMMSS(c.getCrtDtm());
		outDs.OUT_DATA.add(row);

		/*Img */
		List<MigAvActrImg> cImg= daMigAvActrImg.findByIdActorIdx(L_ACTOR_IDX);
		
		for(int i=0;i<cImg.size();i++){
			OUT_DATA_IMG_ROW rowImg = new OUT_DATA_IMG_ROW();
			MigAvActrImg m = cImg.get(i);
			rowImg.ACTOR_IDX =m.getActrIdx();

			if(pjtU.isEmpty(m.getImgL()) ||
				pjtU.isEmpty(m.getImgLs())
			
			){
				Long L_IMG_SEQ = m.getImgSeq();
				String IMG   =m.getImg();
				String IMG_S =m.getImgS();
				String IMG_L = pjtU.fileDwnld(IMG);
				String IMG_LS =pjtU.fileDwnld(IMG_S);
				daMigAvActrImg.updtMigAvActrImg(L_IMG_SEQ
				, IMG
				, IMG_S
				, IMG_L
				, IMG_LS    
				);
			} 
			rowImg.IMG_SEQ  =m.getImgSeq();
			rowImg.CRT_DTM = pjtU.getYyyy_MM_dd_HHMMSS(m.getCrtDtm());
			outDs.OUT_DATA_IMG.add(rowImg);
		}

		/*베스트 dbd */
		List<MigAvMv> al= daMigAvMv.findMigAvBestMvByActorIdx(L_ACTOR_IDX);
		for(int i=0;i<al.size();i++){
			MigAvMv m = al.get(i);
			OUT_DATA_BEST_ROW rowBest = new OUT_DATA_BEST_ROW();
			/* 상세정보를 읽는걸 주석처리 --DVD정보에서 읽는다..
			여거 있을 필요가 없음.
			MigAvMv c =saMigAvMvDtlGet.run(m.getDvdIdx());	
			*/
			rowBest.MAIN_ACTOR_IDX = m.getMnActrIdx();
			rowBest.BEST_YN = m.getBestYn();
			rowBest.TTL_KR = m.getTtlKr();
			rowBest.MV_NM = m.getMvNm();
			rowBest.DVD_IDX = m.getDvdIdx();
			outDs.OUT_DATA_BEST.add(rowBest);
		}

		List<MigAvMv> al2= daMigAvMv.findMigAvMvByActorIdx(L_ACTOR_IDX);
		for(int i=0;i<al2.size();i++){
			MigAvMv m = al2.get(i);
			MigAvMv c2 =saMigAvMvDtlGet.run(m.getDvdIdx());

			OUT_DATA_DVD_ROW rowDvd = new OUT_DATA_DVD_ROW();
			rowDvd.MAIN_ACTOR_IDX = c2.getMnActrIdx();
			rowDvd.BEST_YN = c2.getBestYn();
			rowDvd.TTL_KR = c2.getTtlKr();
			rowDvd.MV_NM = c2.getMvNm();
			rowDvd.DVD_IDX = c2.getDvdIdx();
			rowDvd.OPEN_DT = c2.getOpenDt();
			outDs.OUT_DATA_DVD.add(rowDvd);
		}

		return outDs;
	}
}
