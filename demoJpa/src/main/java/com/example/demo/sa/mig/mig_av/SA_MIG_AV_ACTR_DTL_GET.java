package com.example.demo.sa.mig.mig_av;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.example.demo.db.da.mig_av.DA_MIG_AV_ACTR;
import com.example.demo.db.da.mig_av.DA_MIG_AV_ACTR_CMT;
import com.example.demo.db.da.mig_av.DA_MIG_AV_ACTR_IMG;
import com.example.demo.db.da.mig_av.DA_MIG_AV_MV;
import com.example.demo.db.da.mig_av.DA_MIG_AV_MV_ACTR_MAIN;
import com.example.demo.db.domain.mig_av.MigAvActr;
import com.example.demo.db.domain.mig_av.MigAvActrImg;
import com.example.demo.db.domain.mig_av.MigAvMv;
import com.example.demo.db.domain.mig_av.MigAvMvActrMain;
import com.example.demo.db.domain.mig_av.MigAvMvActrMainIdx;
import com.example.demo.exception.BizException;
import com.example.demo.utils.PjtUtil;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class SA_MIG_AV_ACTR_DTL_GET {

    @Autowired
	DA_MIG_AV_ACTR daMigAvActr;

    
    @Autowired
	DA_MIG_AV_ACTR_CMT daMigAvActrCmt;

    @Autowired
	DA_MIG_AV_ACTR_IMG daMigAvActrImg;

    @Autowired
	DA_MIG_AV_MV daMigAvMv;

    @Autowired
	DA_MIG_AV_MV_ACTR_MAIN daMigAvMvActrMain;

    @Autowired
    SA_MIG_AV_ACTR_CMT_SYNC saMigAvActorCmtSync;
    
    public MigAvActr run(Long L_ACTOR_IDX,Boolean sync) throws BizException  {
        Optional<MigAvActr> c = daMigAvActr.findById(L_ACTOR_IDX);
        if (c.isPresent()) { //있다.
            MigAvActr m = c.get();
            List<MigAvMv> al =daMigAvMv.findMigAvMvByActorIdx(L_ACTOR_IDX);
            if(al.size()==0){
                sync=true;
            }
            if(al.size()>0 && PjtUtil.isEmpty(al.get(0).getImgLA())){
                sync=true;
            }

            List<MigAvActrImg> al2 =daMigAvActrImg.findByIdActorIdx(L_ACTOR_IDX);
            if(al2.size()>0 && PjtUtil.isEmpty(al2.get(0).getImgL())){
                sync=true;
            }

            
            if(m.getSync().equals("N")  || sync==true){
                //상세 update
                updtActor(m.getActrIdx());
            }
            
        }
        c = daMigAvActr.findById(L_ACTOR_IDX);
        MigAvActr m = c.get();
        if(m.getImgL()==null){
            //다운로드 이미지 local명시적으로 고고
            if(m.getImg()!=null){
                PjtUtil p =new PjtUtil();
                String IMG_L = p.fileDwnld(m.getImg());
                daMigAvActr.updtMigAvActrImgL(L_ACTOR_IDX, IMG_L);
                m.setImgL(IMG_L);
            }

        }

        if(m.getImgLs()==null){
            //다운로드 이미지 local명시적으로 고고
            if(m.getImgS()!=null){
                PjtUtil p =new PjtUtil();
                String IMG_LS = p.fileDwnld(m.getImgS());
                daMigAvActr.updtMigAvActrImgLS(L_ACTOR_IDX, IMG_LS);
                m.setImgLs(IMG_LS);
            }
        }
        return m;
    }

	public HashMap<String, Object> getActor(Long L_ACTOR_IDX) throws BizException  {
        HashMap<String, Object> result = new HashMap<String, Object>();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(10000); // 타임아웃 설정 5초
        factory.setReadTimeout(10000);// 타임아웃 설정 5초
        RestTemplate restTemplate = new RestTemplate(factory);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String url = "https://www.avdbs.com/menu/actor.php?actor_idx="+L_ACTOR_IDX;
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);

        headers.add("Cookie", "adult_chk=1; user_nickname=dd; member_idx= 11;");

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
            profile_birth.replaceAll("-", "");
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
        System.out.println("L_ACTOR_IDX");
        System.out.println(L_ACTOR_IDX);
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

        String tot_cnt =     doc.getElementsByClass("album_vw").select(".page_navi.shw-800-over").select(".cnt").text();
        tot_cnt=tot_cnt.replaceAll("건 조회","");
        tot_cnt=tot_cnt.replaceAll(",","");
        tot_cnt=tot_cnt.trim();

        if(PjtUtil.isEmpty(tot_cnt)==false) {
            int page_count = Integer.parseInt(tot_cnt)/page_size;
            if (Integer.parseInt(tot_cnt) % page_size > 0) {
                page_count++;	// 나머지가 있다면 1을 더해줌
            }
    
            ArrayList<HashMap<String,String>> dvd_info = new ArrayList<HashMap<String,String>>();
            for(Element p : lst) {
                String dvd_idx  = p.select(".photo").select(".detail").attr("href");
                dvd_idx=dvd_idx.replaceAll("/menu/dvd.php\\?dvd_idx=","");    
                String dvd_img_src  = p.select(".photo").select("img").attr("src");
                String MV_NM  = p.select(".dscr").select(".snum").text().trim();
                String TTL_KR  = p.select(".dscr").select(".title").text().trim();


                HashMap<String,String> m = new HashMap<String,String>();
                if(PjtUtil.isEmpty(dvd_idx.trim())==false) {
                    m.put("DVD_IDX", dvd_idx.trim());
                    m.put("IMG_S", dvd_img_src.trim());
                    m.put("MV_NM", MV_NM.trim());
                    m.put("TTL_KR", TTL_KR.trim());
                    dvd_info.add(m);
                }
                
            }
            updtMv( L_ACTOR_IDX, dvd_info);
            for(int i=2 ;i<=page_count;i++){
                try {
                    if(page_count>4){
                        Thread.sleep(3000);
                    } else {
                        Thread.sleep(1000);
                    }
                    
                } catch(Exception e){
                    e.printStackTrace();
                }
                getPageDvd(L_ACTOR_IDX , i);
            }
        }
        
        return result;
    }

    private void getPageDvd(Long L_ACTOR_IDX , int i) throws BizException{
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(10000); // 타임아웃 설정 5초
        factory.setReadTimeout(10000);// 타임아웃 설정 5초
        RestTemplate restTemplate = new RestTemplate(factory);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String url = "https://www.avdbs.com/menu/actor.php?actor_idx="+L_ACTOR_IDX+"&_page="+i;
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
        //headers2.add("x-pjax", "true");
        //headers2.add("x-requested-with", "XMLHttpRequest");
        headers.add("Cookie",  "adult_chk=1; user_nickname=dd; member_idx= 11;");

        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> resultMap = restTemplate.exchange(uriBuilder.build().toString(), HttpMethod.GET,entity, String.class);
        String tmp = resultMap.getBody();
        Document doc = Jsoup.parseBodyFragment(tmp);

        Elements albums_li =     doc.getElementsByClass("album_vw").select("ul.lst").select("li");
        ArrayList<HashMap<String,String>> dvd_info = new ArrayList<HashMap<String,String>>();
        System.out.println(albums_li.size());
        for(Element p : albums_li) {
            String dvd_idx  = p.select(".photo").select(".detail").attr("href");
            dvd_idx=dvd_idx.replaceAll("/menu/dvd.php\\?dvd_idx=","");
            String dvd_img_src  = p.select(".photo").select("img").attr("src");
            String MV_NM  = p.select(".dscr").select(".snum").text().trim();
            String TTL_KR  = p.select(".dscr").select(".title").text().trim();

            HashMap<String,String> m = new HashMap<String,String>();
            if(PjtUtil.isEmpty(dvd_idx.trim())==false) {
                m.put("DVD_IDX", dvd_idx.trim());
                m.put("IMG_S", dvd_img_src.trim());
                m.put("MV_NM", MV_NM.trim());
                m.put("TTL_KR", TTL_KR.trim());
                dvd_info.add(m);
            }
        }
        updtMv( L_ACTOR_IDX, dvd_info);
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void updtMv(Long L_ACTR_IDX, ArrayList<HashMap<String,String>> arr_dvd) throws BizException{
        if(arr_dvd!=null){
            for(var j=0;j<arr_dvd.size();j++){
                HashMap<String,String> m = arr_dvd.get(j);
                String dvd_idx = m.get("DVD_IDX");
                String IMG_S = m.get("IMG_S");
                String MV_NM = m.get("MV_NM");
                String TTL_KR = m.get("TTL_KR");
                String SAMPLE_YN="N";
                //https://i1.avdbs.com/sample/dcn/v001.jpg
                if(IMG_S.indexOf("sample")>0){
                    SAMPLE_YN="Y";
                }
                Long L_DVD_IDX = Long.parseLong(dvd_idx);
                Optional<MigAvMv> tmp2 =daMigAvMv.findById(L_DVD_IDX);


                MigAvMvActrMainIdx m2 =new MigAvMvActrMainIdx();
                m2.setActrIdx(L_ACTR_IDX);
                m2.setDvdIdx(L_DVD_IDX);
                Optional<MigAvMvActrMain> main_actr =daMigAvMvActrMain.findById(m2);
                if (!main_actr.isPresent()) {
                    daMigAvMvActrMain.crtMigAvMvActrMain(L_DVD_IDX, L_ACTR_IDX);
                }

                if (tmp2.isPresent()) {
                    if(SAMPLE_YN.equals("N")) {
                        // ns 파일이 있을수 있고
                        // s파일이 있을수 있고

                        String IMG_A = IMG_S.replace("s.jpg", ".jpg");
                        String IMG_AS = IMG_S;
                        String IMG_N = IMG_S.replace("as.jpg", "ns.jpg");  //as가 있다면 무조건 ns는 있다는 전제.
                        String IMG_NS = IMG_N.replace("s.jpg", ".jpg");

                        PjtUtil p =new PjtUtil();
                        String IMG_LA = p.fileDwnld(IMG_A);
                        String IMG_LAS = p.fileDwnld(IMG_AS);
                        String IMG_LN = p.fileDwnld(IMG_N);
                        String IMG_LNS = p.fileDwnld(IMG_NS);

                        daMigAvMv.updtMigAvMvImg(L_DVD_IDX
                            , MV_NM
                            , TTL_KR
                            , SAMPLE_YN
                            , IMG_A
                            , IMG_AS
                            , IMG_N
                            , IMG_NS
                            , IMG_LA
                            , IMG_LAS
                            , IMG_LN
                            , IMG_LNS                        
                        );

                    } else {
                        String IMG_A = IMG_S;
                        String IMG_AS =  IMG_S;
                        String IMG_N =  IMG_S;
                        String IMG_NS = IMG_S;

                        PjtUtil p = new PjtUtil();
                        String IMG_LA = p.fileDwnld(IMG_S);
                        String IMG_LAS = IMG_LA;
                        String IMG_LN =  IMG_LA;
                        String IMG_LNS = IMG_LA;

                        daMigAvMv.updtMigAvMvImg(L_DVD_IDX
                        , MV_NM
                        , TTL_KR
                        , SAMPLE_YN
                        , IMG_A
                        , IMG_AS
                        , IMG_N
                        , IMG_NS
                        , IMG_LA
                        , IMG_LAS
                        , IMG_LN
                        , IMG_LNS
                        
                        );

                    }

                } else {

          
                    /*
                    이미지는 성인용으로 다운 받았고 나머지 4개를 repalce를 통해서 만든다.
                    그런데 성인용 이미지가 아니면 ns 밖에 존재하지 않는다.
                    */

                    //모자이크      https://i2.avdbs.com/av/v0781/n_1412rebdb481_ns.jpg
                    //모자이크 아님 https://i2.avdbs.com/av/v0781/n_1412rebdb481_as.jpg  
                    //모자이크 아님이 기본이고 없을수도 있다. 그럼 모자이크가 된다.
                    //

                    if(SAMPLE_YN.equals("N")) {
                        String IMG_A = IMG_S.replace("s.jpg", ".jpg");
                        String IMG_AS = IMG_S;
                        String IMG_N = IMG_S.replace("as.jpg", "ns.jpg");  //as가 있다면 무조건 ns는 있다는 전제.
                        String IMG_NS = IMG_N.replace("s.jpg", ".jpg");
    
                        PjtUtil p = new PjtUtil();
                        String IMG_LA = p.fileDwnld(IMG_A);
                        String IMG_LAS = p.fileDwnld(IMG_AS);
                        String IMG_LN = p.fileDwnld(IMG_N);
                        String IMG_LNS = p.fileDwnld(IMG_NS);

                        daMigAvMv.crtMigAvMv(L_DVD_IDX, L_ACTR_IDX
                        , MV_NM
                        , TTL_KR
                        , SAMPLE_YN
                        , IMG_A
                        , IMG_AS
                        , IMG_N
                        , IMG_NS
                        , IMG_LA
                        , IMG_LAS
                        , IMG_LN
                        , IMG_LNS
                        
                        );

                    } else {
                        String IMG_A = IMG_S;
                        String IMG_AS =  IMG_S;
                        String IMG_N =  IMG_S;
                        String IMG_NS = IMG_S;
    
                        PjtUtil p = new PjtUtil();
                        String IMG_LA = p.fileDwnld(IMG_S);
                        String IMG_LAS = IMG_LA;
                        String IMG_LN =  IMG_LA;
                        String IMG_LNS = IMG_LA;

                        daMigAvMv.crtMigAvMv(L_DVD_IDX, L_ACTR_IDX
                        , MV_NM
                        , TTL_KR
                        , SAMPLE_YN
                        , IMG_A
                        , IMG_AS
                        , IMG_N
                        , IMG_NS
                        , IMG_LA
                        , IMG_LAS
                        , IMG_LN
                        , IMG_LNS
                        
                        );

                    }
                   
             
                }
            }
        }
    }
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void updtActor(Long L_ACTR_IDX) throws BizException{
        HashMap<String, Object> tmp = this.getActor(L_ACTR_IDX);
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
                String img_s = arr_img.get(j);
                List<MigAvActrImg> al =daMigAvActrImg.findByImgS(img_s);
                if (al.size()==0) {
                    String IMG   =img_s.replaceAll("s.jpg", "r.jpg"); //https://i2.avdbs.com/actor/a06/6756_003_r.jpg
				    String IMG_S =img_s;                            //https://i2.avdbs.com/actor/a06/6756_003_s.jpg
                    PjtUtil p = new PjtUtil();
				    String IMG_L =p.fileDwnld(IMG);
				    String IMG_LS =p.fileDwnld(IMG_S);
                    daMigAvActrImg.crtMigAvActrImg(L_ACTR_IDX
                    , IMG
                    , IMG_S
                    , IMG_L
                    , IMG_LS    
                    );
                }  else {
                    Long L_IMG_SEQ = al.get(0).getImgSeq();
                    String IMG   =img_s.replaceAll("s.jpg", "r.jpg"); //https://i2.avdbs.com/actor/a06/6756_003_r.jpg
				    String IMG_S =img_s;                            //https://i2.avdbs.com/actor/a06/6756_003_s.jpg
                    PjtUtil p = new PjtUtil();
				    String IMG_L =p.fileDwnld(IMG);
				    String IMG_LS =p.fileDwnld(IMG_S);
                    daMigAvActrImg.updtMigAvActrImg(L_IMG_SEQ
                    , IMG
                    , IMG_S
                    , IMG_L
                    , IMG_LS    
                    );
                }
            }
        }
        //ArrayList<HashMap<String,String>> arr_cmt = (ArrayList<HashMap<String,String>>) tmp.get("ACTOR_CMT");
        
        
        
        ArrayList<String> arr_best_dvd = (ArrayList<String>) tmp.get("BEST_DVD");
        if(arr_best_dvd!=null){
            for(var j=0;j<arr_best_dvd.size();j++){
                String dvd_idx = arr_best_dvd.get(j);
                Long L_DVD_IDX = Long.parseLong(dvd_idx);
                daMigAvMv.setBestYn(L_DVD_IDX, L_ACTR_IDX);
            }
        }

        saMigAvActorCmtSync.run(L_ACTR_IDX, "N");

    }


}
