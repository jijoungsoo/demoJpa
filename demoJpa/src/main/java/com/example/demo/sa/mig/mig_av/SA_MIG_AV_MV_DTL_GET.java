package com.example.demo.sa.mig.mig_av;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import javax.persistence.EntityManager;

import com.example.demo.YmlConfig;
import com.example.demo.db.da.mig_av.DA_MIG_AV_MV;
import com.example.demo.db.da.mig_av.DA_MIG_AV_MV_ACTR;
import com.example.demo.db.da.mig_av.DA_MIG_AV_MV_GEN;
import com.example.demo.db.domain.mig_av.MigAvMv;
import com.example.demo.db.domain.mig_av.MigAvMvActr;
import com.example.demo.db.domain.mig_av.MigAvMvActrIdx;
import com.example.demo.db.domain.mig_av.MigAvMvGen;
import com.example.demo.db.domain.mig_av.MigAvMvGenIdx;
import com.example.demo.db.repository.mig_av.MigAvMvRepository;
import com.example.demo.exception.BizException;
import com.example.demo.utils.HttpUtil;
import com.example.demo.utils.PjtUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SA_MIG_AV_MV_DTL_GET {    

    @Autowired
    PjtUtil pjtU;

    @Autowired
    HttpUtil httpU;


    @Autowired
	DA_MIG_AV_MV daMigAvMv;

    @Autowired
    DA_MIG_AV_MV_ACTR daMigAvMvActr;

    @Autowired
    DA_MIG_AV_MV_GEN daMigAvMvGen;


    @Autowired
    YmlConfig yc;

    @Autowired
	EntityManager em;

    	
	@Autowired
	MigAvMvRepository migAvMvR;

    public MigAvMv run(Long L_DVD_IDX) throws BizException {

        Optional<MigAvMv> c = daMigAvMv.findById(L_DVD_IDX);
        if(c.isPresent()){
            //여기면 있다는 거다.
            MigAvMv m = c.get();
            if(m.getSync().equals("N")){
                //여기하고 
                updtMv(m.getDvdIdx());              
            }
        }
        c = daMigAvMv.findById(L_DVD_IDX);
        MigAvMv m = c.get();
        return m;	
	}
    
    private void updtMv(Long L_DVD_IDX) throws BizException{
        HashMap<String, Object> tmp =getMv(L_DVD_IDX);
        long start = System.currentTimeMillis();
        String  MV_NM    = tmp.get("MV_NM").toString();
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

        System.out.println("1-MV_NM=>"+MV_NM);
        System.out.println("1-L_DVD_IDX=>"+L_DVD_IDX);
        System.out.println("1-MAIN_ACTR_IDX=>"+MAIN_ACTR_IDX);
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
        //em.flush();
        ArrayList<String>  arr_actr = (ArrayList<String>)tmp.get("ACTOR_IDX");
        if(arr_actr!=null){
            for(var j=0;j<arr_actr.size();j++){
                String ACTR_IDX = arr_actr.get(j);
                Long L_ACTR_IDX = Long.parseLong(ACTR_IDX);
                MigAvMvActrIdx  key = new MigAvMvActrIdx();
                key.setDvdIdx(L_DVD_IDX);
                key.setActrIdx(L_ACTR_IDX);
                System.out.println("AAAAAAAAAA=>"+L_DVD_IDX+"=>"+L_ACTR_IDX);
                Optional<MigAvMvActr> tmp2 =daMigAvMvActr.findById(key);
                if (tmp2.isPresent()) {
                } else {
                    daMigAvMvActr.crtMigAvMvActr(L_DVD_IDX, L_ACTR_IDX);  
                }
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

        long end = System.currentTimeMillis();
        pjtU.mvDelaySleep((int)(end - start));
    }
    
    private HashMap<String, Object> getMv(Long DVD_IDX)  {
        HashMap<String, Object> result = new HashMap<String, Object>();
        //String url = "https://www.avdbs.com/menu/dvd.php?dvd_idx="+DVD_IDX;
        String url = "http://www.avdbs.com/menu/dvd.php?dvd_idx="+DVD_IDX;
        System.out.println(url);
        String tmp = httpU.httpGet(url);

        
        Document doc = Jsoup.parseBodyFragment(tmp);
        String mv_nm =  doc.getElementsByClass("profile_view_top").select(".tomato").text(); //작품번호
        String dvd_img_src  = doc.getElementById("dvd_img_src").attr("src");  

        String title_kr  = doc.getElementById("title_kr").text();
        String main_actr_idx  = doc.getElementsByClass("inner_name_cn").select("a").attr("href");
        Elements profile_detail  = doc.getElementsByClass("profile_detail").select("p");

        result.put("MV_NM", mv_nm);  //품번

        /*  이미지 상세를 보니 이미지가 없으면 배우 프로필이 나옴
            이경우 이미지를 찾을 수가 없어서
            실제로 이미지는 배우의 작품 리스트에서 나오는 이미지를 넣어야 할것 같다.
            리스트에 나오는 이미지를 기반으로 하고 이것이 샘플이미지이면 샘플이미지를 나타내는 컬럼이 별도로 필요하다.
        */
        
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

        
        System.out.println("main_actr_idx=>"+main_actr_idx);
        System.out.println("mv_nm=>"+mv_nm);
        System.out.println("DVD_IDX=>"+DVD_IDX);
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
