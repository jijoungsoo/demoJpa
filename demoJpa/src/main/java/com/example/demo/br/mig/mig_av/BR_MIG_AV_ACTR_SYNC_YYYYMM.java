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
import com.example.demo.exception.BizRuntimeException;
import com.example.demo.utils.HttpUtil;
import com.example.demo.utils.PjtUtil;
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
public class BR_MIG_AV_ACTR_SYNC_YYYYMM {
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_MIG_AV_ACTR_SYNC_YYYYMM")
	@Data
	static class IN_DS {
		@JsonProperty("brRq")
		@Schema(name = "brRq", example = "IN_DATA", description = "입력 데이터명")
		String brRq;

		@JsonProperty("brRs")
		@Schema(name = "brRs", example = "OUT_DATA", description = "출력 데이터명")
		String brRs;

        @JsonProperty("IN_DATA")
		@Schema(name="IN_DATA-BR_MIG_AV_ACTR_SYNC_YYYYMM", description = "입력 데이터")
		ArrayList<IN_DATA_ROW> IN_DATA = new ArrayList<IN_DATA_ROW>();
	}

    @ApiModel(value="IN_DATA_ROW-BR_MIG_AV_ACTR_SYNC_YYYYMM")
	@Data
	static class IN_DATA_ROW {
		@JsonProperty("DEBUT_YYMM")
		@Schema(name = "DEBUT_YYMM", example = "202104", description = "DEBUT_YYMM")
		String DEBUT_YYMM = "";
	}

	
	@JsonRootName("OUT_DS")
	@ApiModel(value="OUT_DS-BR_MIG_AV_ACTR_SYNC_YYYYMM")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_MIG_AV_ACTR_SYNC_YYYYMM", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}

	@Autowired
	DA_MIG_AV_ACTR daMigAvActr;

    @Autowired
	HttpUtil httpU;
    

    @Autowired
    YmlConfig yc;

    @Autowired
    PjtUtil pjtU;
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"AV"}, value = "AVDBS 배우 마이그", notes = "AVDBS 배우 마이그")
	//@GetMapping(path= "/api/BR_MIG_AV_ACTR_YYYYMM")
	public OUT_DS run(IN_DS inDS) throws BizException {
        if(inDS.IN_DATA==null) {
			throw new BizRuntimeException("[IN_DATA]입력파라미터가 전달되지 않았습니다.");
		}
		if(inDS.IN_DATA.size()!=1) {
			throw new BizRuntimeException("[IN_DATA]입력파라미터의 ["+inDS.IN_DATA.size()+"]행수가 잘못되었습니다.");
		}
		
		IN_DATA_ROW  rs =inDS.IN_DATA.get(0);
		String  DEBUT_YYMM 		= pjtU.str(rs.DEBUT_YYMM);

		if(pjtU.isEmpty(DEBUT_YYMM)){
			throw new BizRuntimeException("DEBUT_YYMM가 전달되지 않았습니다.");
		}
        //넘어오는 값은 YYYYMM이고
        //getActorList에 넘겨야하는 값은 YYMMDD이다.
        String tmp =DEBUT_YYMM.substring(2,6)+"01";
        ArrayList<HashMap<String, Object>>  al = getActorList(tmp);
        for(int j=0;j<al.size();j++){
            HashMap<String, Object> m =al.get(j);
            Long L_ACTR_IDX =Long.parseLong(m.get("ACTOR_IDX").toString());
            String IMG_S =m.get("PRF_IMG").toString();
            String DEBUT_DT =m.get("OPEN_DT").toString();
            String NM_KR =m.get("K_NAME").toString();
            Optional<MigAvActr> tmp2 =daMigAvActr.findById(L_ACTR_IDX);
            if(tmp2.isPresent()){

            } else {
                daMigAvActr.crtMigAvActr(L_ACTR_IDX, IMG_S, DEBUT_DT, NM_KR);
            } 
        }
         
		OUT_DS outDs = new OUT_DS();
        System.out.println(tmp);
		return outDs;		
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
