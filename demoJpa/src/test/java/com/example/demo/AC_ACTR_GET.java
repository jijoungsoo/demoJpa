package com.example.demo;

import com.example.demo.utils.HttpUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

public class AC_ACTR_GET {
	@Test
    void download() {
        //String tmp =httpGet("https://www.avdbs.com/");
        HttpUtil h = new HttpUtil();
        //String tmp =h.httpGet("http://www.avdbs.com/menu/actor.php?actor_idx=6826");
        String tmp =h.httpGet2("http://www.avdbs.com/menu/dvd.php?dvd_idx=781756");
        
        Document doc = Jsoup.parseBodyFragment(tmp);
        String mv_nm =  doc.getElementsByClass("profile_view_top").select(".tomato").text(); //작품번호
        String dvd_img_src  = doc.getElementById("dvd_img_src").attr("src");  

        String title_kr  = doc.getElementById("title_kr").text();
        String main_actr_idx  = doc.getElementsByClass("inner_name_cn").select("a").attr("href");
        Elements profile_detail  = doc.getElementsByClass("profile_detail").select("p");
        
        System.out.println(profile_detail.text());
    }
}
