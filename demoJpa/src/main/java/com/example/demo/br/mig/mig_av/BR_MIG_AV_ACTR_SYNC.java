package com.example.demo.br.mig.mig_av;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import com.example.demo.db.da.mig_av.DA_MIG_AV_ACTR;
import com.example.demo.db.da.mig_av.DA_MIG_AV_ACTR_IMG;
import com.example.demo.db.da.mig_av.DA_MIG_AV_MV;
import com.example.demo.db.domain.mig_av.MigAvActr;
import com.example.demo.db.domain.mig_av.MigAvActrImg;
import com.example.demo.db.domain.mig_av.MigAvMv;
import com.example.demo.exception.BizException;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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
@RestController
public class BR_MIG_AV_ACTR_SYNC {
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_MIG_AV_ACTR_SYNC")
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
	@ApiModel(value="OUT_DS-BR_MIG_AV_ACTR_SYNC")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_MIG_AV_ACTR_SYNC", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}

	@Autowired
	DA_MIG_AV_ACTR daMigAvActr;

    @Autowired
	DA_MIG_AV_ACTR_IMG daMigAvActrImg;

    @Autowired
	DA_MIG_AV_MV daMigAvMv;

    @Autowired
	EntityManager em;
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"AV"}, value = "AVDBS 배우 마이그", notes = "AVDBS 배우 마이그")
	@GetMapping(path= "/api/BR_MIG_AV_ACTR_SYNC")
	public OUT_DS run() throws BizException {

        
        //insertActor(2309L);
        List<MigAvActr> al = daMigAvActr.findBySyncN();
		for(var i=0;i<al.size();i++){
			MigAvActr m=al.get(i);

            if(m.getSync().equals("N")){
                //EntityManager  tm =  em.getEntityManagerFactory().createEntityManager();
                //tm.getTransaction().begin();
                insertActor(m.getActrIdx());
                //tm.flush();
                //tm.getTransaction().commit();
                //flush는 먹히지 않는다.
                //트랜잭션 단위를 넣고 싶은데.. 잘안된다...
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
    private void insertActor(Long L_ACTR_IDX) throws BizException{
        HashMap<String, Object> tmp =getActor(L_ACTR_IDX);
        String  NM_EN    = tmp.get("INNER_NAME_EN").toString();
        String  NM_CN    = tmp.get("INNER_NAME_CN").toString();
        String  PRF_IMG  = tmp.get("PRF_IMG").toString();
        String  BRTH     = tmp.get("BIRTH").toString();   //1993-08-16
        String  HEIGHT   = tmp.get("HEIGHT").toString();  //159
        String  SIZE     = tmp.get("SIZE").toString();  //B83 / W57 / H88
        String  BRA_SIZE = tmp.get("BRA_SIZE").toString();  //F
        String  ACTOR_ONM = tmp.get("ACTOR_ONM").toString();  //다른이름
        String  ACTOR_DSCR_TITLE = tmp.get("ACTOR_DSCR_TITLE").toString();  //배우설명 포인트
        String  ACTOR_DSCR_DSCR = tmp.get("ACTOR_DSCR_DSCR").toString();  //배우설명


        daMigAvActr.updtMigAvActr(L_ACTR_IDX, 
        NM_EN, 
        NM_CN, 
        PRF_IMG, 
        BRTH, 
        HEIGHT, 
        SIZE, 
        BRA_SIZE,
        ACTOR_ONM,
        ACTOR_DSCR_TITLE,
        ACTOR_DSCR_DSCR
        );
        ArrayList<String> arr_img = (ArrayList<String>) tmp.get("OTHER_PHOTO");
        if(arr_img!=null){
            for(var j=0;j<arr_img.size();j++){
                String img = arr_img.get(j);
                Optional<MigAvActrImg> tmp2 =daMigAvActrImg.findById(img);
                if (tmp2.isPresent()) {
                } else {
                    daMigAvActrImg.crtMigAvActrImg(img, L_ACTR_IDX);
                }
            }
        }

        ArrayList<String> arr_dvd = (ArrayList<String>) tmp.get("DVD_INFO");
        if(arr_dvd!=null){
            for(var j=0;j<arr_dvd.size();j++){
                String dvd_idx = arr_dvd.get(j);
                Long L_DVD_IDX = Long.parseLong(dvd_idx);
                Optional<MigAvMv> tmp2 =daMigAvMv.findById(L_DVD_IDX);
                if (tmp2.isPresent()) {
                } else {
                    daMigAvMv.crtMigAvMv(L_DVD_IDX, L_ACTR_IDX);
                }
            }
        }
        ArrayList<String> arr_best_dvd = (ArrayList<String>) tmp.get("BEST_DVD");
        if(arr_best_dvd!=null){
            for(var j=0;j<arr_best_dvd.size();j++){
                String dvd_idx = arr_best_dvd.get(j);
                Long L_DVD_IDX = Long.parseLong(dvd_idx);
                daMigAvMv.setBestYn(L_DVD_IDX, L_ACTR_IDX);
            }
        }
    }
    
    private HashMap<String, Object> getActor(Long ACTOR_IDX)  {
        HashMap<String, Object> result = new HashMap<String, Object>();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(10000); // 타임아웃 설정 5초
        factory.setReadTimeout(10000);// 타임아웃 설정 5초
        RestTemplate restTemplate = new RestTemplate(factory);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String url = "https://www.avdbs.com/menu/actor.php?actor_idx="+ACTOR_IDX;
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);

        String set_cookie = "adult_chk=1; user_nickname=dd; member_idx= 11;";
        headers.add("Cookie", set_cookie);

        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> resultMap = restTemplate.exchange(uriBuilder.build().toString(), HttpMethod.GET,entity, String.class);
        String tmp = resultMap.getBody();
        Document doc = Jsoup.parseBodyFragment(tmp);
        String prf_img  = doc.getElementsByClass("profile_img_view").attr("src");
        String inner_name_kr  = doc.getElementsByClass("inner_name_kr").text();
        String inner_name_en  = doc.getElementsByClass("inner_name_en").text();
        String inner_name_cn  = doc.getElementsByClass("inner_name_cn").text();
        String profile_birth  = doc.getElementsByClass("profile_birth").text();
        String profile_height  = doc.getElementsByClass("profile_height").text();
        String profile_size    = doc.getElementsByClass("profile_size").text();
        String profile_bra_size    = doc.getElementsByClass("profile_bra_size").text();
        String actor_onm    = doc.getElementsByClass("actor_onm").text();
        Elements other_photo_list =     doc.getElementsByClass("other_photo_list").select("img");
        Elements photo_boxs =     doc.getElementsByClass("photo_box");
        String  actor_dscr_title =     doc.getElementsByClass("actor-dscr").select(".title").text();
        String  actor_dscr_dscr =     doc.getElementsByClass("actor-dscr").select(".dscr").text();

        result.put("PRF_IMG", prf_img);  //프로필 이미지
        result.put("INNER_NAME_KR", inner_name_kr);  //한국어이름
        result.put("INNER_NAME_EN", inner_name_en);  //영어
        result.put("INNER_NAME_CN", inner_name_cn);  //중국어
        profile_birth=profile_birth.replaceAll("생년월일 :", "").trim();
        if(profile_birth.length()>8){
        profile_birth=profile_birth.substring(0,10);
        }
        result.put("BIRTH", profile_birth);  //생년월
        result.put("HEIGHT", profile_height.replaceAll("신장 :", "").replaceAll("cm", "").trim());  //키
        result.put("SIZE", profile_size.replaceAll("신체사이즈 :", "").trim());       //사이즈
        result.put("BRA_SIZE", profile_bra_size.replaceAll("컵사이즈 :", "").replaceAll("컵", "").trim());   //가슴 사이즈
        result.put("ACTOR_ONM", actor_onm);   //다른이름
        result.put("ACTOR_DSCR_TITLE", actor_dscr_title);   //다른이름
        result.put("ACTOR_DSCR_DSCR", actor_dscr_dscr);   //다른이름
        //다른 사진
        ArrayList<String> arr_img = new ArrayList<String>();
        for(Element img : other_photo_list) {
        arr_img.add(img.attr("src"));            
        }
        System.out.println("ACTOR_IDX");
        System.out.println(ACTOR_IDX);
        result.put("OTHER_PHOTO", arr_img);
        ArrayList<String> best_dvd = new ArrayList<String>();
        for(Element photo_box : photo_boxs) {
            String href = photo_box.select("a").attr("href");
            String dvd_idx=href.replaceAll("/menu/dvd.php\\?dvd_idx=","");

            System.out.println(dvd_idx);
            if(!PjtUtil.isEmpty(dvd_idx.trim())) {
                best_dvd.add(dvd_idx.trim());
            }
        }
        result.put("BEST_DVD", best_dvd);

        Elements lst =     doc.getElementsByClass("album_vw").select("ul.lst").select("li");
        int page_size = lst.size();

        String tot_cnt =     doc.getElementsByClass("page_navi shw-800-over").select(".cnt").text();
        tot_cnt=tot_cnt.replaceAll("건 조회","");
        tot_cnt=tot_cnt.replaceAll(",","");
        tot_cnt=tot_cnt.trim();

        if(PjtUtil.isEmpty(tot_cnt)==false) {
            int page_count = Integer.parseInt(tot_cnt)/page_size;
            if (Integer.parseInt(tot_cnt) % page_size > 0) {
                page_count++;	// 나머지가 있다면 1을 더해줌
            }
    
            ArrayList<String> dvd_info = new ArrayList<String>();
            for(Element p : lst) {
                String dvd_idx  = p.select(".photo").select(".detail").attr("href");
                dvd_idx=dvd_idx.replaceAll("/menu/dvd.php\\?dvd_idx=","");
    
                if(PjtUtil.isEmpty(dvd_idx.trim())==false) {
                    dvd_info.add(dvd_idx.trim());
                }
                
            }
            for(int i=2 ;i<=page_count;i++){
                try {
                    Thread.sleep(2000);
                } catch(Exception e){
                    e.printStackTrace();
                }
                
    
                HttpHeaders headers2 = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                String url2 = "https://www.avdbs.com/menu/actor.php?actor_idx="+ACTOR_IDX+"&_page="+i;
                UriComponentsBuilder uriBuilder2 = UriComponentsBuilder.fromHttpUrl(url2);
                headers2.add("x-pjax", "true");
                headers2.add("x-requested-with", "XMLHttpRequest");
    
                HttpEntity<?> entity2 = new HttpEntity<>(headers2);
                ResponseEntity<String> resultMap2 = restTemplate.exchange(uriBuilder2.build().toString(), HttpMethod.GET,entity2, String.class);
                String tmp2 = resultMap2.getBody();
                Document doc2 = Jsoup.parseBodyFragment(tmp2);
    
                Elements albums_li2 =     doc2.getElementsByClass("album_vw").select("ul.lst").select("li");
                System.out.println(albums_li2.size());
                for(Element p : albums_li2) {
                    String dvd_idx  = p.select(".photo").select(".detail").attr("href");
                    dvd_idx=dvd_idx.replaceAll("/menu/dvd.php\\?dvd_idx=","");
    
                    if(PjtUtil.isEmpty(dvd_idx.trim())==false) {
                        dvd_info.add(dvd_idx.trim());
                    }
                }
            }
            result.put("DVD_INFO", dvd_info);
        }
        
        return result;
    }
}
