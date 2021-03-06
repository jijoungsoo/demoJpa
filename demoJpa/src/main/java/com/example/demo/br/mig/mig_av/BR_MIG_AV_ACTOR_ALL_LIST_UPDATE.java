package com.example.demo.br.mig.mig_av;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.example.demo.YmlConfig;
import com.example.demo.anotation.OpService;
import com.example.demo.db.da.mig_av.DA_MIG_AV_ACTR;
import com.example.demo.db.domain.mig_av.MigAvActr;
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
public class BR_MIG_AV_ACTOR_ALL_LIST_UPDATE {
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_MIG_AV_ACTOR_ALL_LIST_UPDATE")
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
	@ApiModel(value="OUT_DS-BR_MIG_AV_ACTOR_ALL_LIST_UPDATE")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_MIG_AV_ACTOR_ALL_LIST_UPDATE", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}

	@Autowired
	DA_MIG_AV_ACTR daMigAvActr;

    @Autowired
	HttpUtil httpU;
    

    @Autowired
    YmlConfig yc;
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"AV"}, value = "AVDBS 배우 마이그", notes = "AVDBS 배우 마이그")
	//@GetMapping(path= "/api/BR_MIG_AV_ACTR_YYYYMM")
	public OUT_DS run(IN_DS inDs) throws BizException {
		ArrayList<String> al_yymmdd= getActordebutYYMM();//셀렉트 박스의  데비월을 모두 가져온다.
		for(var i=0;i<al_yymmdd.size();i++){
			String yymmdd=al_yymmdd.get(i);

            List<MigAvActr> tmp =getMigDbActor(yymmdd);
            if(tmp.size()==0){
                ArrayList<HashMap<String, Object>>  al = getActorList(yymmdd);
                for(int j=0;j<al.size();j++){
                    HashMap<String, Object> m =al.get(j);
                    Long L_ACTR_IDX =Long.parseLong(m.get("ACTOR_IDX").toString());
                    String IMG_S =m.get("PRF_IMG").toString();
                    String DEBUT_DT =m.get("OPEN_DT").toString();
                    String NM_KR =m.get("K_NAME").toString();
                    Optional<MigAvActr> tmp2 =daMigAvActr.findById(L_ACTR_IDX);
                    if(tmp2.isPresent()){
                        //비어있지 않다.
                    } else {
                        //비어있다.
                        daMigAvActr.crtMigAvActr(L_ACTR_IDX, IMG_S, DEBUT_DT, NM_KR);
                    }
                }
            }

            
			
			try{
				Thread.sleep(yc.getDelaysleep());
			} catch(Exception e) {
				e.printStackTrace();
			} 
		}
		OUT_DS outDs = new OUT_DS();
		return outDs;		
	}
    
    public ArrayList<String> getActordebutYYMM(){
      HashMap<String, Object> result = new HashMap<String, Object>();
      //String url = "https://www.avdbs.com/menu/actor_list.php";
      String url = "http://www.avdbs.com/menu/actor_list.php";
      String tmp = httpU.httpGetAvdbs(url);
        
      Document doc =     Jsoup.parseBodyFragment(tmp);
      Elements els =     doc.getElementsByClass("selectbox for_pc").select("option");
      ArrayList<String> al = new ArrayList<String>();
      for(Element el : els) {
           String yymmdd = el.attr("value");
           if(yymmdd.equals("all")==false){
               al.add(yymmdd);
           }
       }
       return al;
   }
   public List<MigAvActr>  getMigDbActor(String YYMMDD){
       String YYYYMMDD="20"+YYMMDD;
       YYYYMMDD=YYYYMMDD.substring(0,6);
       return daMigAvActr.findByDebutYYYYMM(YYYYMMDD);
   }

   public ArrayList<HashMap<String, Object>>  getActorList(String YYMMDD){
        HashMap<String, Object> result = new HashMap<String, Object>();
        //String url = "https://www.avdbs.com/menu/actor_list.php?ymd="+YYMMDD+"&_page=1&_pjax=%23container";
        String url = "http://www.avdbs.com/menu/actor_list.php?ymd="+YYMMDD+"&_page=1&_pjax=%23container";
        String tmp = httpU.httpGetAjax(url);

        Document doc = Jsoup.parseBodyFragment(tmp);
        Elements lst =     doc.getElementsByClass("lst").select("li");
 
        ArrayList<HashMap<String, Object>>  actor_lst = new ArrayList<HashMap<String, Object>>();
        int page_size = lst.size();
        for(Element p : lst) {
            HashMap<String, Object>  actor = new HashMap<String, Object>();
            String actor_idx = p.attr("data-idx");
            String k_name = p.select(".k_name").text();
            String e_name  = p.select(".e_name").text();
            String open_dt  = p.select(".highlight").text();
            open_dt=open_dt.replaceAll("데뷔","");
            open_dt=open_dt.replaceAll("-","");
            open_dt=open_dt.trim();
            String prf_Img = p.getElementsByClass("photo").select("img").attr("src");
            
            actor.put("ACTOR_IDX", actor_idx);
            actor.put("K_NAME", k_name);
            actor.put("E_NAME", e_name);
            actor.put("OPEN_DT", "20"+open_dt);
            actor.put("PRF_IMG", prf_Img);
            actor_lst.add(actor);
        }
        String tot_cnt =     doc.getElementsByClass("page_navi").select(".cnt").text();
        tot_cnt=tot_cnt.replaceAll("건 조회","");
        tot_cnt=tot_cnt.replaceAll(",","");
        tot_cnt=tot_cnt.trim();
        //page_count=total_cnt/page_size
        int page_count = Integer.parseInt(tot_cnt)/page_size;
        if (Integer.parseInt(tot_cnt) % page_size > 0) {
          page_count++;	// 나머지가 있다면 1을 더해줌
        }
        for(int i=2 ;i<=page_count;i++){
           System.out.println(i);
           /*페이지 호출을 해서 작품리스트를 가져오자. */
 
           //String url2 = "https://www.avdbs.com/menu/actor_list.php?ymd="+YYMMDD+"&_page="+i+"&_pjax=%23container";
           String url2 = "http://www.avdbs.com/menu/actor_list.php?ymd="+YYMMDD+"&_page="+i+"&_pjax=%23container";
           String tmp2 = httpU.httpGetAjax(url2);
           Document doc2 = Jsoup.parseBodyFragment(tmp2);
 
           Elements lst2 =     doc2.getElementsByClass("lst").select("li");
           System.out.println(lst2.size());
           for(Element p : lst2) {
               HashMap<String, Object>  actor = new HashMap<String, Object>();
               String actor_idx = p.attr("data-idx");
               String k_name = p.select(".k_name").text();
               String e_name  = p.select(".e_name").text();
               String open_dt  = p.select(".highlight").text();
               open_dt=open_dt.replaceAll("데뷔","");
               open_dt=open_dt.replaceAll("-","");
               open_dt=open_dt.trim();
               String prf_Img = p.getElementsByClass("photo").select("img").attr("src");
               
               actor.put("ACTOR_IDX", actor_idx);
               actor.put("K_NAME", k_name);
               actor.put("E_NAME", e_name);
               actor.put("OPEN_DT", "20"+open_dt);
               actor.put("PRF_IMG", prf_Img);
               actor_lst.add(actor);
           }            
       }
     return actor_lst;
   }
}
