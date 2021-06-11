package com.example.demo.br.mig.mig_av;

import java.util.ArrayList;
import java.util.HashMap;
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
public class BR_MIG_AV_ACTR_PAGE {
    @Autowired
    HttpUtil httpU;
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_MIG_AV_ACTR_PAGE")
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
	@ApiModel(value="OUT_DS-BR_MIG_AV_ACTR_PAGE")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_MIG_AV_ACTR_PAGE", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}

	@Autowired
	DA_MIG_AV_ACTR daMigAvActr;

    @Autowired
    YmlConfig yc;	

    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
    })
    @ApiOperation(tags={"AV"}, value = "AVDBS 배우 마이그", notes = "AVDBS 배우 마이그")
   // @GetMapping(path= "/api/BR_MIG_AV_ACTR_PAGE")
    public OUT_DS run(IN_DS inDs) throws BizException {
        for(var i=357;i<=560;i++){
            ArrayList<HashMap<String, Object>>  al = getActorPage(String.valueOf(i));
            for(int j=0;j<al.size();j++){
                HashMap<String, Object> m =al.get(j);
                Long L_ACTR_IDX =Long.parseLong(m.get("ACTOR_IDX").toString());
                String IMG_S =m.get("PRF_IMG").toString();
                String DEBUT_DT =m.get("OPEN_DT").toString();
                String NM_KR =m.get("K_NAME").toString();
                System.out.println(L_ACTR_IDX);
                Optional<MigAvActr> tmp2 =daMigAvActr.findById(L_ACTR_IDX);

                System.out.println(tmp2);
                if (tmp2.isPresent()) {
                    //https://www.daleseo.com/java8-optional-after/
                    //예외를 발생시키므로 다음과 같이 객재 존재 여부를 bool 타입으로 반환하는 isPresent()라는 메소드를 통해 null 체크가 필요합니다.
                } else {
                    daMigAvActr.crtMigAvActr(L_ACTR_IDX, IMG_S, DEBUT_DT, NM_KR);
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
   
   public ArrayList<HashMap<String, Object>>  getActorPage(String PAGE_NUM){
        //String url = "https://www.avdbs.com/menu/actor_list.php?_sord=debutdate_d&_page="+PAGE_NUM;
        String url = "http://www.avdbs.com/menu/actor_list.php?_sord=debutdate_d&_page="+PAGE_NUM;
        String tmp = httpU.httpGetAvdbs(url);
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
        return actor_lst;
    }
}
