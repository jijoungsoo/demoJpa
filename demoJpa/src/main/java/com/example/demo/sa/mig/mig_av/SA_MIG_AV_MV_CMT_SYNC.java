package com.example.demo.sa.mig.mig_av;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.example.demo.YmlConfig;
import com.example.demo.db.da.mig_av.DA_MIG_AV_ACTR;
import com.example.demo.db.da.mig_av.DA_MIG_AV_MV_CMT;
import com.example.demo.db.domain.mig_av.MigAvActr;
import com.example.demo.db.domain.mig_av.MigAvMvCmt;
import com.example.demo.exception.BizException;
import com.example.demo.utils.HttpUtil;
import com.example.demo.utils.PjtUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SA_MIG_AV_MV_CMT_SYNC {

    @Autowired
    PjtUtil pjtU;

    @Autowired
	DA_MIG_AV_ACTR daMigAvActr;

    @Autowired
    YmlConfig yc;

    @Autowired
    HttpUtil httpU;

    
    @Autowired
	DA_MIG_AV_MV_CMT daMigAvMvCmt;
    
    public void run(Long L_ACTOR_IDX,String SYNC_YN) throws BizException  {
        Optional<MigAvActr> c = daMigAvActr.findById(L_ACTOR_IDX);
        if (c.isPresent()) { //있다.
            List<MigAvMvCmt> al= daMigAvMvCmt.findMigAvMvCmtByActorIdx(L_ACTOR_IDX);
            if(al.size()>0 && (SYNC_YN.equals("N"))){
                Long MAX_CMT_IDX = al.get(0).getCmtIdx();
                syncMvCmt( L_ACTOR_IDX,MAX_CMT_IDX);
            } else {
                syncMvCmt( L_ACTOR_IDX,0L);
            }
        }
    }

	public void  syncMvCmt(Long ACTOR_IDX,Long MAX_CMT_IDX)  {
        //String url = "https://www.avdbs.com/menu/actor.php?actor_idx="+ACTOR_IDX;
        String url = "http://www.avdbs.com/w2017/page/dvd/dvd_mention.php?actor_idx="+ACTOR_IDX;
        String tmp = httpU.httpGetAvdbs(url);
        Document doc = Jsoup.parseBodyFragment(tmp);
        //mention

        Elements  mention_cnt = doc.getElementsByClass("profile_view_grape_list web_view").select(".page_navi.shw-640-over").select("strong");
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
        //String url = "https://www.avdbs.com/w2017/page/actor/actor_mention.php?actor_idx="+ACTOR_IDX+"&page="+i;
        String url = "http://www.avdbs.com/w2017/page/dvd/dvd_mention.php?actor_idx="+ACTOR_IDX+"&page="+i;
        String tmp = httpU.httpGetAjax(url);

        long start = System.currentTimeMillis();
        Document doc = Jsoup.parseBodyFragment(tmp);
        Elements  mention_row = doc.getElementsByClass("mention").select(".row");

        Boolean endPage =false;
        for(Element m : mention_row) {
            HashMap<String,String> map = new HashMap<String,String>();
            String cmt_idx = m.attr("data-idx");
            String cmt = m.select(".comment").text();
            String dvd_idx  = m.select(".snum.default").attr("href");
            dvd_idx=dvd_idx.replaceAll("/menu/dvd.php\\?dvd_idx=","");

            String mv_nm  = m.select(".snum.default").text();
            cmt = cmt.replace(mv_nm, "");

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
                map.put("DVD_IDX", dvd_idx);
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
        updtMvCmt(mention);
        if(endPage==false) {
            long end = System.currentTimeMillis();
            pjtU.ActorCmtDelaySleep((int)(end - start));
        }
        return endPage;
    }

    private void updtMvCmt(ArrayList<HashMap<String,String>> arr_cmt) throws BizException{
   
        if(arr_cmt!=null){
            for(var j=0;j<arr_cmt.size();j++){

                try {
                    HashMap<String,String> m = arr_cmt.get(j);
                    String ACTOR_IDX  =   m.get("ACTOR_IDX");
                    String DVD_IDX  =   m.get("DVD_IDX");
                    String CMT_IDX  =   m.get("CMT_IDX");
                    String CMT      =   m.get("CMT");
                    String WRITER   =   m.get("WRITER");
                    String LK_CNT   =   m.get("LK_CNT");
                    String DSLK_CNT =   m.get("DSLK_CNT");
    
    
                    Long L_ACTR_IDX = Long.parseLong(ACTOR_IDX);
                    Long L_CMT_IDX = Long.parseLong(CMT_IDX);
                    Long L_DVD_IDX = Long.parseLong(DVD_IDX);
                    
                    Long L_LK_CNT =0L;
                    if(!pjtU.isEmpty(LK_CNT)){
                        //비추천 누적인경우 좋아여 데이터가 없다..
                        L_LK_CNT = Long.parseLong(LK_CNT);
                    }
                    
                    Long L_DSLK_CNT =0L;
                    if(!pjtU.isEmpty(DSLK_CNT)){
                        //이경우도 나왔다.
                        L_DSLK_CNT = Long.parseLong(DSLK_CNT);
                    }
    
                    daMigAvMvCmt.crtMigAvMvCmt(
                                    L_CMT_IDX
                                    , L_DVD_IDX
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
