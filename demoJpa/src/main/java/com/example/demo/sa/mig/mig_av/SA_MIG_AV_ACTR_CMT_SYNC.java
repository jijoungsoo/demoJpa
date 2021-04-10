package com.example.demo.sa.mig.mig_av;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.example.demo.YmlConfig;
import com.example.demo.db.da.mig_av.DA_MIG_AV_ACTR;
import com.example.demo.db.da.mig_av.DA_MIG_AV_ACTR_CMT;
import com.example.demo.db.domain.mig_av.MigAvActr;
import com.example.demo.db.domain.mig_av.MigAvActrCmt;
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
public class SA_MIG_AV_ACTR_CMT_SYNC {

    @Autowired
    PjtUtil pjtU;

    @Autowired
	DA_MIG_AV_ACTR daMigAvActr;

    @Autowired
    YmlConfig yc;

    
    @Autowired
	DA_MIG_AV_ACTR_CMT daMigAvActrCmt;
    
    public void run(Long L_ACTOR_IDX,String SYNC_YN) throws BizException  {
        Optional<MigAvActr> c = daMigAvActr.findById(L_ACTOR_IDX);
        if (c.isPresent()) { //있다.
            List<MigAvActrCmt> al= daMigAvActrCmt.findMigAvActrCmtByActorIdx(L_ACTOR_IDX);
            if(al.size()>0 && (SYNC_YN.equals("N"))){
                Long MAX_CMT_IDX = al.get(0).getCmtIdx();
                syncActorCmt( L_ACTOR_IDX,MAX_CMT_IDX);
            } else {
                syncActorCmt( L_ACTOR_IDX,0L);
            }
        }
    }

	public void  syncActorCmt(Long ACTOR_IDX,Long MAX_CMT_IDX)  {
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
        //mention

        Elements  mention_cnt = doc.getElementById("commlist").select(".page_navi.shw-640-over").select("strong");
        System.out.println("aaaaa");

        if(pjtU.isEmpty(mention_cnt.text())){
            return;
        }
        System.out.println(mention_cnt.text());
        System.out.println(mention_cnt.text().split("/")[1]);
        String page_cnt = mention_cnt.text().split("/")[1];
        int i_page_cnt = Integer.parseInt(page_cnt.trim());
        System.out.println("i_page_cnt=>"+i_page_cnt);

        for(int i=1;i<=i_page_cnt;i++){
            
            try {
                Boolean endPage =updateCmt( ACTOR_IDX, MAX_CMT_IDX, i);
                if(endPage==true){
                    break;
                }                    
            } catch (BizException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private Boolean updateCmt(Long ACTOR_IDX,Long MAX_CMT_IDX,Integer i) throws BizException{
        ArrayList<HashMap<String,String>> mention = new ArrayList<HashMap<String,String>>();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(10000); // 타임아웃 설정 5초
        factory.setReadTimeout(10000);// 타임아웃 설정 5초
        RestTemplate restTemplate = new RestTemplate(factory);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String url = "https://www.avdbs.com/w2017/page/actor/actor_mention.php?actor_idx="+ACTOR_IDX+"&page="+i;
        
        UriComponentsBuilder uriBuilder2 = UriComponentsBuilder.fromHttpUrl(url);
        headers.add("x-pjax", "true");
        headers.add("x-requested-with", "XMLHttpRequest");

        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<String> resultMap = restTemplate.exchange(uriBuilder2.build().toString(), HttpMethod.GET,entity, String.class);
        String tmp = resultMap.getBody();
        long start = System.currentTimeMillis();

        Document doc = Jsoup.parseBodyFragment(tmp);
        Elements  mention_row = doc.getElementsByClass("mention").select(".row");

        Boolean endPage =false;
        for(Element m : mention_row) {
            HashMap<String,String> map = new HashMap<String,String>();
            String cmt_idx = m.attr("data-idx");
            String cmt = m.select(".comment").text();
            String writer = m.select(".writer").text();
            String lk_cnt = m.select(".like").select(".cnt").text();
            String dslk_cnt = m.select(".dislike").select(".cnt").text();

            try{
                Long l_cmt_idx = Long.parseLong(cmt_idx);
                if(MAX_CMT_IDX>=l_cmt_idx){
                    endPage=true;
                    break;
                }                
                
                map.put("ACTOR_IDX", String.valueOf(ACTOR_IDX));
                map.put("CMT_IDX", cmt_idx);
                map.put("CMT", cmt);
                map.put("WRITER", writer);
                map.put("LK_CNT", lk_cnt);
                map.put("DSLK_CNT", dslk_cnt);
    
                mention.add(map);    
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        updtActorCmt(mention);
        if(endPage==false) {
            long end = System.currentTimeMillis();
            pjtU.ActorCmtDelaySleep((int)((end - start)/1000.0));
        }
        return endPage;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void updtActorCmt(ArrayList<HashMap<String,String>> arr_cmt) throws BizException{
   
        if(arr_cmt!=null){
            for(var j=0;j<arr_cmt.size();j++){

                try {
                    HashMap<String,String> m = arr_cmt.get(j);
                    String ACTOR_IDX  =   m.get("ACTOR_IDX");
                    String CMT_IDX  =   m.get("CMT_IDX");
                    String CMT      =   m.get("CMT");
                    String WRITER   =   m.get("WRITER");
                    String LK_CNT   =   m.get("LK_CNT");
                    String DSLK_CNT =   m.get("DSLK_CNT");
    
    
                    Long L_ACTR_IDX = Long.parseLong(ACTOR_IDX);
                    Long L_CMT_IDX = Long.parseLong(CMT_IDX);
                    
                    Long L_LK_CNT =0L;
                    if(!pjtU.isEmpty(LK_CNT)){
                        //비추천 누적인경우 좋아여 데이터가 없다..
                        L_LK_CNT = Long.parseLong(LK_CNT);
                    }
                    
    
                    
                    Long L_DSLK_CNT = Long.parseLong(DSLK_CNT);
    
                    daMigAvActrCmt.crtMigAvActrCmt(
                                    L_CMT_IDX
                                    , L_ACTR_IDX
                                    ,  CMT
                                    ,  WRITER
                                    ,  L_LK_CNT
                                    ,  L_DSLK_CNT
                                    ) ;
                } catch (Exception e){
                    e.printStackTrace();
                }
                
            }
        }
       
    }


}
