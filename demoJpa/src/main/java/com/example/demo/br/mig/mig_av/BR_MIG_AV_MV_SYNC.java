package com.example.demo.br.mig.mig_av;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import com.example.demo.anotation.OpService;
import com.example.demo.db.da.mig_av.DA_MIG_AV_MV;
import com.example.demo.db.da.mig_av.DA_MIG_AV_MV_ACTR;
import com.example.demo.db.da.mig_av.DA_MIG_AV_MV_GEN;
import com.example.demo.db.domain.mig_av.MigAvMv;
import com.example.demo.db.domain.mig_av.MigAvMvActr;
import com.example.demo.db.domain.mig_av.MigAvMvActrIdx;
import com.example.demo.db.domain.mig_av.MigAvMvGen;
import com.example.demo.db.domain.mig_av.MigAvMvGenIdx;
import com.example.demo.exception.BizException;
import com.example.demo.utils.HttpUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
public class BR_MIG_AV_MV_SYNC {
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_MIG_AV_MV_SYNC")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;
	}

	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_MIG_AV_MV_SYNC")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_MIG_AV_MV_SYNC", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}

    @Autowired
    HttpUtil httpU;

    @Autowired
	DA_MIG_AV_MV daMigAvMv;

    @Autowired
    DA_MIG_AV_MV_ACTR daMigAvMvActr;

    @Autowired
    DA_MIG_AV_MV_GEN daMigAvMvGen;

    @Autowired
	EntityManager em;
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"AV"}, value = "AVDBS 배우 마이그", notes = "AVDBS 배우 마이그")
	//@GetMapping(path= "/api/BR_MIG_AV_MV_SYNC")
	public OUT_DS run(IN_DS inDs) throws BizException {

        List<MigAvMv> al = daMigAvMv.findMigAvMvN();
		for(var i=0;i<al.size();i++){
			MigAvMv m=al.get(i);

            if(m.getSync().equals("N")){
                updtMv(m.getDvdIdx());
               try {
                    Thread.sleep(2000);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }

		}
		OUT_DS outDs = new OUT_DS();
		return outDs;		
	}
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void updtMv(Long L_DVD_IDX) throws BizException{
        HashMap<String, Object> tmp =getMv(L_DVD_IDX);
        String  MV_NM    = tmp.get("MV_NM").toString();
        String  DVD_IMG_SRC  = tmp.get("DVD_IMG_SRC").toString();


        //모자이크 이미지가 나올지  아닐지 알수 없음.
        //일반이미지   https://s3.ap-northeast-2.amazonaws.com/img.avdbs.com/av/v0788/n_1412rebd535_a.jpg
        //작은일반이미지   https://s3.ap-northeast-2.amazonaws.com/img.avdbs.com/av/v0788/n_1412rebd535_as.jpg
        //모자이크이미지   https://s3.ap-northeast-2.amazonaws.com/img.avdbs.com/av/v0788/n_1412rebd535_n.jpg
        //작은모자이크이미지   https://s3.ap-northeast-2.amazonaws.com/img.avdbs.com/av/v0788/n_1412rebd535_ns.jpg


        String  TITLE_KR        = tmp.get("TITLE_KR").toString();
        String  MAIN_ACTR_IDX   = tmp.get("MAIN_ACTR_IDX").toString();
        String  OPEN_DT         = tmp.get("OPEN_DT").toString();
        String  ACTR_NM         = tmp.get("ACTR_NM").toString();
        String  COMP_NM         = "" ;

        String  LABEL           = "" ;
        String  SERIES          = "" ;
        String  DIRECTOR        = "" ;
        String  RUN_TIME        = "" ;
        String  STORY_KR        = tmp.get("STORY_KR").toString();

        if(tmp.get("COMP_NM")!=null){
            COMP_NM = tmp.get("COMP_NM").toString();
        }

        if(tmp.get("LABEL")!=null){
            LABEL = tmp.get("LABEL").toString();
        }

        if(tmp.get("SERIES")!=null){
            SERIES = tmp.get("SERIES").toString();
        }

        if(tmp.get("DIRECTOR")!=null){
            DIRECTOR = tmp.get("DIRECTOR").toString();
        }
        
        if(tmp.get("RUN_TIME")!=null){
            RUN_TIME = tmp.get("RUN_TIME").toString();
        }

        Long L_MAIN_ACTR_IDX  = Long.parseLong(MAIN_ACTR_IDX);

        daMigAvMv.updtMigAvMv(L_DVD_IDX, 
        MV_NM, 
         TITLE_KR,
         L_MAIN_ACTR_IDX,
         OPEN_DT,
         ACTR_NM,
         COMP_NM,
         LABEL,
         SERIES,
         DIRECTOR,
         RUN_TIME,
         STORY_KR
        );
        
        daMigAvMvActr.rmMigAvMvActr(L_DVD_IDX);
        ArrayList<String>  arr_actr = (ArrayList<String>)tmp.get("ACTOR_IDX");
        if(arr_actr!=null){
            for(var j=0;j<arr_actr.size();j++){
                String ACTR_IDX = arr_actr.get(j);
                Long L_ACTR_IDX = Long.parseLong(ACTR_IDX);
                /*                
                MigAvMvActrIdx  key = new MigAvMvActrIdx();
                key.setDvdIdx(L_DVD_IDX);
                key.setActrIdx(L_ACTR_IDX);
                
                Optional<MigAvMvActr> tmp2 =daMigAvMvActr.findById(key);
                if (tmp2.isPresent()) {
                } else {
                    daMigAvMvActr.crtMigAvMvActr(L_DVD_IDX, L_MAIN_ACTR_IDX);
                }
                */
                daMigAvMvActr.crtMigAvMvActr(L_DVD_IDX, L_ACTR_IDX);
            }
        }

        ArrayList<HashMap<String, Object>>   arr_gen = (ArrayList<HashMap<String, Object>>)tmp.get("GEN_LIST");
        if(arr_gen!=null){
            for(var j=0;j<arr_gen.size();j++){
                HashMap<String, Object> m = arr_gen.get(j);
                String MENU_NO = m.get("menu").toString();
                String CATE_NO = m.get("cate").toString();  //장르

                Long L_MENU_NO  = Long.parseLong(MENU_NO);
                Long L_CATE_NO  = Long.parseLong(CATE_NO);

                MigAvMvGenIdx  key = new MigAvMvGenIdx();
                key.setDvdIdx(L_DVD_IDX);
                key.setCateNo(L_CATE_NO);
                key.setMenuNo(L_MENU_NO);

                Optional<MigAvMvGen> tmp2 =daMigAvMvGen.findById(key);
                if (tmp2.isPresent()) {
                } else {
                    daMigAvMvGen.crtMigAvMvGen(L_DVD_IDX, L_CATE_NO,L_MENU_NO);
                }
            }
        }
    }
    
    private HashMap<String, Object> getMv(Long DVD_IDX)  {
        HashMap<String, Object> result = new HashMap<String, Object>();

        //String url = "https://www.avdbs.com/menu/dvd.php?dvd_idx="+DVD_IDX;
        String url = "http://www.avdbs.com/menu/dvd.php?dvd_idx="+DVD_IDX;
        String tmp = httpU.httpGetAvdbs(url);

        Document doc = Jsoup.parseBodyFragment(tmp);
        String mv_nm =  doc.getElementsByClass("profile_view_top").select(".tomato").text(); //작품번호
        String dvd_img_src  = doc.getElementById("dvd_img_src").attr("src");  

        String title_kr  = doc.getElementById("title_kr").text();
        String main_actr_idx  = doc.getElementsByClass("inner_name_cn").select("a").attr("href");
        Elements profile_detail  = doc.getElementsByClass("profile_detail").select("p");

        result.put("MV_NM", mv_nm);  //품번
        result.put("DVD_IMG_SRC", dvd_img_src);  //이미지
            //모자이크 이미지가 나올지  아닐지 알수 없음.
            //일반이미지   https://s3.ap-northeast-2.amazonaws.com/img.avdbs.com/av/v0788/n_1412rebd535_a.jpg
            //작은일반이미지   https://s3.ap-northeast-2.amazonaws.com/img.avdbs.com/av/v0788/n_1412rebd535_as.jpg
            //모자이크이미지   https://s3.ap-northeast-2.amazonaws.com/img.avdbs.com/av/v0788/n_1412rebd535_n.jpg
            //작은모자이크이미지   https://s3.ap-northeast-2.amazonaws.com/img.avdbs.com/av/v0788/n_1412rebd535_ns.jpg
            result.put("TITLE_KR", title_kr);  //작품하단 설명

            
            main_actr_idx = main_actr_idx.replaceAll("/menu/actor.php\\?actor_idx=", "");
            result.put("MAIN_ACTR_IDX", main_actr_idx);  //메인 배우 idx
            
            ArrayList<String>  arr_actr = new ArrayList<String>();
        for(Element pf : profile_detail) {

            String tmp2 =pf.text();
            System.out.println(tmp2);
            if(tmp2.indexOf("출시")>=0){
                tmp2 = tmp2.replaceAll("출시:", "");
                tmp2 = tmp2.replaceAll("\\.","");
                result.put("OPEN_DT", tmp2.trim());  //출시일
            }
            if(tmp2.indexOf("출연")>=0){
                tmp2 = tmp2.replaceAll("출연:", "");
                result.put("ACTR_NM", tmp2.trim());  //배우
                Elements actrs =  pf.select("a");
                for(Element actr : actrs) {
                //테이블을 따로 둬서 저장하자.
                String actor_idx = actr.attr("href");
                actor_idx = actor_idx.replaceAll("/menu/actor.php\\?actor_idx=", "");
                arr_actr.add(actor_idx);
                }
            }
            if(tmp2.indexOf("제작사")>=0){
                tmp2 = tmp2.replaceAll("제작사:","");
                result.put("COMP_NM", tmp2.trim());  //제작사
            }
            if(tmp2.indexOf("레이블")>=0){
                tmp2 = tmp2.replaceAll("레이블:", "");
                result.put("LABEL", tmp2.trim());  //레이블
            }
            if(tmp2.indexOf("시리즈")>=0){
                tmp2 = tmp2.replaceAll("시리즈:", "");
                result.put("SERIES", tmp2.trim());  //시리즈
            }
            if(tmp2.indexOf("감독")>=0){
                tmp2 = tmp2.replaceAll("감독:", "");
                result.put("DIRECTOR", tmp2.trim());  //감독
            }

            if(tmp2.indexOf("재생시간")>=0){
                tmp2 = tmp2.replaceAll("재생시간:", "");
                result.put("RUN_TIME", tmp2.trim());  //재생시간
            }
        }
        result.put("ACTOR_IDX", arr_actr);  //출연배우

        System.out.println(DVD_IDX);
        String story_kr  = "";
        if(doc.getElementById("story_kr")!=null) {
            story_kr = doc.getElementById("story_kr").text();
            
        }        
        result.put("STORY_KR", story_kr);  //작품상세

        ArrayList<HashMap<String, Object>> arr_gen = new ArrayList<HashMap<String, Object>>();
        Elements gen_list  = doc.getElementsByClass("gen_list");
        for(Element gen : gen_list) {
            HashMap<String, Object>  gem_map = new HashMap<String, Object>();
            String menu=gen.attr("data-menu");
            String cate=gen.attr("data-cate");
            String gen_nm=gen.text();
            gem_map.put("cate",cate);
            gem_map.put("menu",menu);
            gem_map.put("gen_nm",gen_nm);
            arr_gen.add(gem_map);
        }
        result.put("GEN_LIST", arr_gen);  //장르정보
        return result;
    }
}
