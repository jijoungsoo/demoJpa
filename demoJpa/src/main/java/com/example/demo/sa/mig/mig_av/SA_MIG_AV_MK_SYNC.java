package com.example.demo.sa.mig.mig_av;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import com.example.demo.db.da.mig_av.DA_MIG_AV_MK;
import com.example.demo.db.domain.mig_av.MigAvMk;
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

@Service
public class SA_MIG_AV_MK_SYNC {    
    @Autowired
	DA_MIG_AV_MK daMigAvMk;

    @Autowired
    PjtUtil pjtU;

    @Autowired
    HttpUtil httpU;

    public void run() throws BizException {
        ArrayList<HashMap<String, Object>>  al = getMk();
        for(int i=0;i<al.size();i++){
            HashMap<String, Object>  m = al.get(i);
            String mk_id = m.get("MK_ID").toString();
            Long L_MK_ID = Long.parseLong(mk_id);
            Optional<MigAvMk> c = daMigAvMk.findById(L_MK_ID);
            if(c.isPresent()==false){
                //false 니까 신규
                crtMk(m);
            }
        }
	}
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void crtMk(HashMap<String, Object> m) throws BizException{
        String  MK_ID      = m.get("MK_ID").toString();
        String  IMG        = m.get("IMG").toString();
        String  NM      = m.get("NM").toString();
        String  TTL        = m.get("TTL").toString();

        Long L_MK_ID = Long.parseLong(MK_ID);

        String IMG_L =pjtU.fileDwnld(IMG);

        daMigAvMk.crtMigAvMk(L_MK_ID, NM, IMG, IMG_L, TTL);
    }
    
    private ArrayList<HashMap<String, Object>> getMk()  {
        //String url = "https://www.avdbs.com/av-maker";
        String url = "http://www.avdbs.com/av-maker";
        String tmp = httpU.httpGetAvdbs(url);


        Document doc = Jsoup.parseBodyFragment(tmp);
        Elements maker_lst  = doc.getElementById("maker_lst").select("a");

        ArrayList<HashMap<String, Object>> al = new ArrayList<HashMap<String, Object>>();
        for(Element m2 : maker_lst) {
            String href = m2.attr("href");
            String cate = m2.text();  //카테고리
            System.out.println(href);
            System.out.println(cate);
    
             //url = "https://www.avdbs.com"+href;
             url = "http://www.avdbs.com"+href;
            tmp = httpU.httpGetAvdbs(url);
            doc = Jsoup.parseBodyFragment(tmp);


            Elements context  = doc.getElementsByClass("context").select(".ctx");
            for(Element m : context) {
                String img = m.select("img").attr("src");
                String mk_id = m.attr("data-maker_id");
                String nm     = m.select(".name").text();
                String ttl   = m.select(".dscr").select("a").text();


                HashMap<String, Object>  map = new HashMap<String, Object>();
                ttl = ttl.replace(nm,"").trim();
                //System.out.println(ttl);
                
                map.put("MK_ID", mk_id);
                map.put("IMG", img);
                map.put("NM", nm);
                map.put("TTL", ttl.trim());

                al.add(map);
            }
        


        }
        
       

        return al;
    }
}
