package com.example.demo.br.mig.mig_av;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import com.example.demo.anotation.OpService;
import com.example.demo.db.da.mig_av.DA_MIG_AV_GEN;
import com.example.demo.db.da.mig_av.DA_MIG_AV_MENU;
import com.example.demo.db.domain.mig_av.MigAvGen;
import com.example.demo.db.domain.mig_av.MigAvMenu;
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
public class BS_MIG_AV_GEN_SYNC {

	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BS_MIG_AV_GEN_SYNC")
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
	@ApiModel(value="OUT_DS-BS_MIG_AV_GEN_SYNC")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BS_MIG_AV_GEN_SYNC", description = "출력데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}
    @Autowired 
    HttpUtil httpU;
    
	@Autowired
	DA_MIG_AV_GEN daMigAvGen;

	@Autowired
	DA_MIG_AV_MENU daMigAvMenu;
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"AV"}, value = "장르")
	//@GetMapping(path= "/api/BS_MIG_AV_GEN_SYNC")
	public OUT_DS run(IN_DS inDs) throws BizException {
		ArrayList<HashMap<String,String>> al =this.getAvGen();

        for(int i=0;i<al.size();i++){
            HashMap<String,String> m = al.get(i);
            String CATE_NO =m.get("CATE_NO");
			String MENU_NO =m.get("MENU_NO");
            String CATE_NM =m.get("CATE_NM");
            String CATE_NM_JP =m.get("CATE_NM_JP");
            Long L_CATE_NO = Long.parseLong(CATE_NO);
			Long L_MENU_NO = Long.parseLong(MENU_NO);


            Optional<MigAvGen> c =daMigAvGen.findById(L_CATE_NO);
            if(c.isPresent()){
                daMigAvGen.updtMigAvGen(L_CATE_NO,L_MENU_NO, CATE_NM, CATE_NM_JP);
            } else {
                daMigAvGen.crtMigAvGen(L_CATE_NO,L_MENU_NO, CATE_NM, CATE_NM_JP);
            }
        }
		ArrayList<HashMap<String,String>> al2 = new ArrayList<HashMap<String,String>>();
		HashMap<String,String> m=new HashMap<String,String>();
		m.put("MENU_NO", "2");
		m.put("MENU_NM", "상황");
		al2.add(m);
		m=new HashMap<String,String>();
		m.put("MENU_NO", "3");
		m.put("MENU_NM", "배우유형");
		al2.add(m);
		m=new HashMap<String,String>();
		m.put("MENU_NO", "4");
		m.put("MENU_NM", "의상");
		al2.add(m);
		m=new HashMap<String,String>();
		m.put("MENU_NO", "5");
		m.put("MENU_NM", "세부장르");
		al2.add(m);
		m=new HashMap<String,String>();
		m.put("MENU_NO", "6");
		m.put("MENU_NM", "행위");
		al2.add(m);
		m=new HashMap<String,String>();
		m.put("MENU_NO", "1");
		m.put("MENU_NM", "기타");
		al2.add(m);
		for(int i=0;i<al2.size();i++){
            HashMap<String,String> m2 = al2.get(i);
			String MENU_NO =m2.get("MENU_NO");
            String MENU_NM =m2.get("MENU_NM");
            Long L_MENU_NO = Long.parseLong(MENU_NO);


            Optional<MigAvMenu> c =daMigAvMenu.findById(L_MENU_NO);
            if(c.isPresent()){
                daMigAvMenu.updtMigAvMenu(L_MENU_NO,MENU_NM);
            } else {
                daMigAvMenu.crtMigAvMenu(L_MENU_NO,MENU_NM);
            }
        }

		OUT_DS outDs = new OUT_DS();
		return outDs;		
	}

	
	public ArrayList<HashMap<String,String>>  getAvGen()  {
        //String url = "https://www.avdbs.com/genre-av";
        String url = "http://www.avdbs.com/genre-av";
        String tmp = httpU.httpGetAvdbs(url);

        Document doc = Jsoup.parseBodyFragment(tmp);
		//System.out.println(doc.text());
		//System.out.println(doc.getElementsByClass("genre_grp page_8").select(".lst").select("a").text());

        ArrayList<HashMap<String,String>> al_gen = new ArrayList<HashMap<String,String>>();
		Elements gens  = doc.getElementsByClass("genre_grp page_2").select(".lst").select("a");            
        for(Element g : gens) {
            HashMap<String,String> m = new HashMap<String,String>();
            String cate_no = g.attr("data-id");
            String cate_nm = g.text();
            String cate_nm_jp = g.attr("data-nmj");
			//System.out.println(cate_no);
			//System.out.println(cate_nm);
			//System.out.println(cate_nm_jp);
			m.put("MENU_NO", "2");  //상황
            m.put("CATE_NO", cate_no);
            m.put("CATE_NM", cate_nm);
            m.put("CATE_NM_JP", cate_nm_jp);
            al_gen.add(m);
        }
		gens  = doc.getElementsByClass("genre_grp page_3").select(".lst").select("a");            
        for(Element g : gens) {
            HashMap<String,String> m = new HashMap<String,String>();
            String cate_no = g.attr("data-id");
            String cate_nm = g.text();
            String cate_nm_jp = g.attr("data-nmj");
			//System.out.println(cate_no);
			//System.out.println(cate_nm);
			//System.out.println(cate_nm_jp);
			m.put("MENU_NO", "3");  //배우유형
            m.put("CATE_NO", cate_no);
            m.put("CATE_NM", cate_nm);
            m.put("CATE_NM_JP", cate_nm_jp);
            al_gen.add(m);
        }
		gens  = doc.getElementsByClass("genre_grp page_4").select(".lst").select("a");            
        for(Element g : gens) {
            HashMap<String,String> m = new HashMap<String,String>();
            String cate_no = g.attr("data-id");
            String cate_nm = g.text();
            String cate_nm_jp = g.attr("data-nmj");
			//System.out.println(cate_no);
			//System.out.println(cate_nm);
			//System.out.println(cate_nm_jp);
			m.put("MENU_NO", "4");  //의상
            m.put("CATE_NO", cate_no);
            m.put("CATE_NM", cate_nm);
            m.put("CATE_NM_JP", cate_nm_jp);
            al_gen.add(m);
        }
		gens  = doc.getElementsByClass("genre_grp page_5").select(".lst").select("a");            
        for(Element g : gens) {
            HashMap<String,String> m = new HashMap<String,String>();
            String cate_no = g.attr("data-id");
            String cate_nm = g.text();
            String cate_nm_jp = g.attr("data-nmj");
			//System.out.println(cate_no);
			//System.out.println(cate_nm);
			//System.out.println(cate_nm_jp);
			m.put("MENU_NO", "5");  //세부장르
            m.put("CATE_NO", cate_no);
            m.put("CATE_NM", cate_nm);
            m.put("CATE_NM_JP", cate_nm_jp);
            al_gen.add(m);
        }
		gens  = doc.getElementsByClass("genre_grp page_6").select(".lst").select("a");            
        for(Element g : gens) {
            HashMap<String,String> m = new HashMap<String,String>();
            String cate_no = g.attr("data-id");
            String cate_nm = g.text();
            String cate_nm_jp = g.attr("data-nmj");
			//System.out.println(cate_no);
			//System.out.println(cate_nm);
			//System.out.println(cate_nm_jp);
			m.put("MENU_NO", "6");  //행위
            m.put("CATE_NO", cate_no);
            m.put("CATE_NM", cate_nm);
            m.put("CATE_NM_JP", cate_nm_jp);
            al_gen.add(m);
        }
		gens  = doc.getElementsByClass("genre_grp page_1").select(".lst").select("a");            
        for(Element g : gens) {
            HashMap<String,String> m = new HashMap<String,String>();
            String cate_no = g.attr("data-id");
            String cate_nm = g.text();
            String cate_nm_jp = g.attr("data-nmj");
			//System.out.println(cate_no);
			//System.out.println(cate_nm);
			//System.out.println(cate_nm_jp);
			m.put("MENU_NO", "1");  //기타
            m.put("CATE_NO", cate_no);
            m.put("CATE_NM", cate_nm);
            m.put("CATE_NM_JP", cate_nm_jp);
            al_gen.add(m);
        }
        return al_gen;
    }
}
