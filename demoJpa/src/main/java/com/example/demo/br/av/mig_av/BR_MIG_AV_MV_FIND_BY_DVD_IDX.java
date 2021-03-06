package com.example.demo.br.av.mig_av;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.anotation.OpService;
import com.example.demo.db.da.mig_av.DA_MIG_AV_MV;
import com.example.demo.db.da.mig_av.DA_MIG_AV_MV_GEN;
import com.example.demo.db.domain.mig_av.MigAvMv;
import com.example.demo.db.domain.mig_av.QMigAvGen;
import com.example.demo.exception.BizException;
import com.example.demo.exception.BizRuntimeException;
import com.example.demo.sa.mig.mig_av.SA_MIG_AV_MV_DTL_GET;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;

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
public class BR_MIG_AV_MV_FIND_BY_DVD_IDX {
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_MIG_AV_MV_FIND_BY_DVD_IDX")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;
		
		@JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_MIG_AV_MV_FIND_BY_DVD_IDX", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
	}
	
	@ApiModel(value="IN_DATA_ROW-BR_MIG_AV_MV_FIND_BY_DVD_IDX")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("DVD_IDX")
		@Schema(name = "DVD_IDX", example = "1", description = "DVD_IDX")
		String DVD_IDX = "";
	}
	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_MIG_AV_MV_FIND_BY_DVD_IDX")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_MIG_AV_MV_FIND_BY_DVD_IDX", description = "출력 데이터")
		ArrayList<OUT_DATA_ROW> OUT_DATA = new ArrayList<OUT_DATA_ROW>();

		@JsonProperty("OUT_DATA_BEST")
		@Schema(name="OUT_DATA_BEST-BR_MIG_AV_MV_FIND_BY_DVD_IDX", description = "출력 데이터")
		ArrayList<OUT_DATA_BEST_ROW> OUT_DATA_BEST = new ArrayList<OUT_DATA_BEST_ROW>();

		@JsonProperty("OUT_DATA_DVD")
		@Schema(name="OUT_DATA_DVD-BR_MIG_AV_MV_FIND_BY_DVD_IDX", description = "출력 데이터")
		ArrayList<OUT_DATA_DVD_ROW> OUT_DATA_DVD = new ArrayList<OUT_DATA_DVD_ROW>();

		@JsonProperty("OUT_DATA_GEN")
		@Schema(name="OUT_DATA_GEN-BR_MIG_AV_MV_FIND_BY_DVD_IDX", description = "출력 데이터")
		ArrayList<OUT_DATA_GEN_ROW> OUT_DATA_GEN = new ArrayList<OUT_DATA_GEN_ROW>();
	}

	@ApiModel(value="OUT_DATA_ROW-BR_MIG_AV_MV_FIND_BY_DVD_IDX")
	@Data
	static class OUT_DATA_ROW {
		@JsonProperty("DVD_IDX")
		@Schema(name = "DVD_IDX", example = "1", description = "DVD IDX")
		Long DVD_IDX = null;
		@JsonProperty("IMG_A")
		@Schema(name = "IMG_A", example = "adf.jpg", description = "IMG_A")
		String IMG_A = null;
		@JsonProperty("IMG_AS")
		@Schema(name = "IMG_AS", example = "adf.jpg", description = "IMG_AS")
		String IMG_AS = null;
		@JsonProperty("IMG_N")
		@Schema(name = "IMG_N", example = "adf.jpg", description = "IMG_N")
		String IMG_N = null;
		@JsonProperty("IMG_NS")
		@Schema(name = "IMG_NS", example = "adf.jpg", description = "IMG_NS")
		String IMG_NS = null;





		@JsonProperty("IMG_LA")
		@Schema(name = "IMG_LA", example = "adf.jpg", description = "IMG_LA")
		String IMG_LA = null;
		@JsonProperty("IMG_LAS")
		@Schema(name = "IMG_LAS", example = "adf.jpg", description = "IMG_LAS")
		String IMG_LAS = null;
		@JsonProperty("IMG_LN")
		@Schema(name = "IMG_LN", example = "adf.jpg", description = "IMG_LN")
		String IMG_LN = null;
		@JsonProperty("IMG_LNS")
		@Schema(name = "IMG_LNS", example = "adf.jpg", description = "IMG_LNS")
		String IMG_LNS = null;














		
		@JsonProperty("MV_NM")
		@Schema(name = "MV_NM", example = "masaka", description = "MV_NM")
		String MV_NM = null;
		@JsonProperty("TITLE_KR")
		@Schema(name = "TITLE_KR", example = "타이틀", description = "TITLE_KR")
		String TITLE_KR = null;
		@JsonProperty("MAIN_ACTOR_NM")
		@Schema(name = "MAIN_ACTOR_NM", example = "마사카", description = "메인배우")
		String MAIN_ACTOR_NM = null;
		@JsonProperty("MAIN_ACTOR_IDX")
		@Schema(name = "MAIN_ACTOR_IDX", example = "1", description = "MAIN_ACTOR_IDX")
		String MAIN_ACTOR_IDX = null;

		@JsonProperty("ACTR_NM")
		@Schema(name = "ACTR_NM", example = "3 4 5", description = "배우들")
		String ACTR_NM = null;

		@JsonProperty("OPEN_DT")
		@Schema(name = "OPEN_DT", example = "20201231", description = "출시일")
		String OPEN_DT = null;
		@JsonProperty("COMP_NM")
		@Schema(name = "COMP_NM", example = "정말", description = "발매회사")
		String COMP_NM = null;
		
		@JsonProperty("LABEL")
		@Schema(name = "LABEL", example = "LABEL", description = "LABEL")
		String LABEL = null;

		@JsonProperty("SYNC")
		@Schema(name = "SYNC", example = "Y,N", description = "SYNC")
		String SYNC = null;


		@JsonProperty("BEST_YN")
		@Schema(name = "BEST_YN", example = "Y", description = "베스트 작품 여부")
		String BEST_YN = null;

		
		@JsonProperty("SERIES")
		@Schema(name = "SERIES", example = "가자", description = "시리즈")
		String SERIES = null;

		
		@JsonProperty("DIRECTOR")
		@Schema(name = "DIRECTOR", example = "김독", description = "감독")
		String DIRECTOR = null;

		
		@JsonProperty("RUN_TIME")
		@Schema(name = "RUN_TIME", example = "1시간30분", description = "런타임")
		String RUN_TIME = null;

		@JsonProperty("STORY_KR")
		@Schema(name = "STORY_KR", example = "그녀는 야이야", description = "스토리")
		String STORY_KR = null;

		@JsonProperty("GEN_LIST")
		@Schema(name = "GEN_LIST", example = "장르", description = "장르")
		String GEN_LIST = null;

		@JsonProperty("DEL_YN")
		@Schema(name = "DEL_YN", example = "(N,Y)", description = "로컬 파일 삭제여부")
		String DEL_YN = null;

		@JsonProperty("FILE_PATH")
		@Schema(name = "FILE_PATH", example = "e:av.avi", description = "로컬 파일 경로")
		String FILE_PATH = null;

		@JsonProperty("RMK")
		@Schema(name = "RMK", example = "RMK", description = "RMK")
		String RMK = null;
		
		@JsonProperty("CRT_DTM")
		@Schema(name = "CRT_DTM", example = "202012311640", description = "생성일시")
		String CRT_DTM = null;
	}
	
	
	@ApiModel(value="OUT_DATA_BEST_ROW-BR_MIG_AV_MV_FIND_BY_DVD_IDX")
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


	@ApiModel(value="OUT_DATA_DVD_ROW-BR_MIG_AV_MV_FIND_BY_DVD_IDX")
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

	@ApiModel(value="OUT_DATA_GEN_ROW-BS_MIG_AV_ACTR_FIND_BY_ACTOR_IDX")
	@Data
	static class OUT_DATA_GEN_ROW {
		@JsonProperty("CATE_NM")
		@Schema(name = "CATE_NM", example = "1", description = "메인 배우IDX")
		String CATE_NM = null;

		@JsonProperty("CATE_NO")
		@Schema(name = "CATE_NO", example = "1", description = "CATE_NO")
		Long CATE_NO = null;

		@JsonProperty("MENU_NO")
		@Schema(name = "MENU_NO", example = "1", description = "MENU_NO")
		Long MENU_NO = null;
	}

	@Autowired
    PjtUtil pjtU;
	
	@Autowired
	DA_MIG_AV_MV daMigAvMv;

	@Autowired
	SA_MIG_AV_MV_DTL_GET saMigAvMvDtlGet;

	
	@Autowired
	DA_MIG_AV_MV_GEN daMigAvMvGen;
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"AV"}, value = "AV작품을 조회한다.", notes = "페이징 처리")
	//@PostMapping(path= "/api/BR_MIG_AV_MV_FIND_BY_DVD_IDX", consumes = "application/json", produces = "application/json")
	public OUT_DS run(@RequestBody IN_DS inDS) throws BizException {
		if(inDS.IN_DATA==null) {
			throw new BizRuntimeException("[IN_DATA]입력파라미터가 전달되지 않았습니다.");
		}
		if(inDS.IN_DATA.size()!=1) {
			throw new BizRuntimeException("[IN_DATA]입력파라미터의 ["+inDS.IN_DATA.size()+"]행수가 잘못되었습니다.");
		}
		
		IN_DATA_ROW  rs =inDS.IN_DATA.get(0);
		String  DVD_IDX 		= pjtU.str(rs.DVD_IDX);

		if(pjtU.isEmpty(DVD_IDX)) {
			throw new BizRuntimeException("DVD_IDX가 넘어오지 않았습니다.");
		}
		Long L_DVD_IDX = Long.parseLong(DVD_IDX);	
		
		Optional<MigAvMv> c =daMigAvMv.findById(L_DVD_IDX);
		if(c.isPresent()==false) {
			throw new BizRuntimeException("DVD_IDX에 해당하는 작품이 없습니다.");
		}
		OUT_DS outDs = new OUT_DS();
		MigAvMv m = c.get();
		OUT_DATA_ROW row = new OUT_DATA_ROW();
		row.DVD_IDX = m.getDvdIdx();
		row.IMG_A = m.getImgA();
		row.IMG_AS = m.getImgAs();
		row.IMG_N = m.getImgN();
		row.IMG_NS = m.getImgNs();
		row.IMG_LA = m.getImgLA();
		row.IMG_LAS = m.getImgLAs();
		row.IMG_LN = m.getImgLN();
		row.IMG_LNS = m.getImgLNs();

		row.MV_NM = m.getMvNm();
		row.TITLE_KR = m.getTtlKr();
		row.MAIN_ACTOR_NM = m.getMnActrNm();
		row.MAIN_ACTOR_IDX = String.valueOf(m.getMnActrIdx()) ;
		row.ACTR_NM = m.getActrNm();
		row.OPEN_DT = m.getOpenDt();
		row.COMP_NM = m.getCmpNm();
		row.LABEL = m.getLbl();
		row.SYNC = m.getSync();
		row.BEST_YN = m.getBestYn();
		row.SERIES = m.getSeries();
		row.DIRECTOR = m.getDrctr();
		row.RUN_TIME = m.getRnTm();
		row.STORY_KR = m.getStryKr();
		row.GEN_LIST = m.getGenLst();
		row.DEL_YN = m.getDelYn();
		row.FILE_PATH = m.getFilePath();
		row.RMK		=m.getRmk();
		row.CRT_DTM = pjtU.getYyyy_MM_dd_HHMMSS(m.getCrtDtm());
		outDs.OUT_DATA.add(row);

		Long L_ACTOR_IDX = m.getMnActrIdx();
		
		/*베스트 dbd */
		List<MigAvMv> al= daMigAvMv.findMigAvBestMvByActorIdx(L_ACTOR_IDX);
		for(int i=0;i<al.size();i++){
			MigAvMv m2 = al.get(i);
			OUT_DATA_BEST_ROW rowBest = new OUT_DATA_BEST_ROW();
			rowBest.MAIN_ACTOR_IDX = m2.getMnActrIdx();
			rowBest.BEST_YN = m2.getBestYn();
			rowBest.TTL_KR = m2.getTtlKr();
			rowBest.MV_NM = m2.getMvNm();
			rowBest.DVD_IDX = m2.getDvdIdx();
			outDs.OUT_DATA_BEST.add(rowBest);
		}

		/*DVD 장르 */
		List<Tuple> al_gen= daMigAvMvGen.findGenByDvdIdx(L_DVD_IDX);
		for(int i=0;i<al_gen.size();i++){
			OUT_DATA_GEN_ROW row_gen = new OUT_DATA_GEN_ROW();
			Tuple t = al_gen.get(i);
			row_gen.CATE_NM=t.get(QMigAvGen.migAvGen.cateNm);
			row_gen.CATE_NO=t.get(QMigAvGen.migAvGen.cateNo);
			row_gen.MENU_NO=t.get(QMigAvGen.migAvGen.menuNo);
			outDs.OUT_DATA_GEN.add(row_gen);
		}

		List<MigAvMv> al2= daMigAvMv.findMigAvMvByActorIdx(L_ACTOR_IDX,null);
		for(int i=0;i<al2.size();i++){
			MigAvMv m2 = al2.get(i);
			MigAvMv c2 =saMigAvMvDtlGet.run(m2.getDvdIdx());

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
