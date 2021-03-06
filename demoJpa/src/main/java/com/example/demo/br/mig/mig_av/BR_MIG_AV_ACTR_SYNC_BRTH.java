package com.example.demo.br.mig.mig_av;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;

import com.example.demo.anotation.OpService;
import com.example.demo.db.da.mig_av.DA_MIG_AV_ACTR;
import com.example.demo.db.da.mig_av.DA_MIG_AV_ACTR_IMG;
import com.example.demo.db.da.mig_av.DA_MIG_AV_MV;
import com.example.demo.db.domain.mig_av.MigAvActr;
import com.example.demo.exception.BizException;
import com.example.demo.utils.HttpUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
public class BR_MIG_AV_ACTR_SYNC_BRTH {
	
	@JsonRootName("IN_DS")
	@ApiModel(value="IN_DS-BR_MIG_AV_ACTR_SYNC_BRTH")
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
	@ApiModel(value="OUT_DS-BR_MIG_AV_ACTR_SYNC_BRTH")
	@Data
	static class OUT_DS {
		@JsonProperty("OUT_DATA")
		@Schema(name="OUT_DATA-BR_MIG_AV_ACTR_SYNC_BRTH", description = "출력 데이터")
		ArrayList<String> OUT_DATA = new ArrayList<String>();
	}
    @Autowired
    HttpUtil httpU;

	@Autowired
	DA_MIG_AV_ACTR daMigAvActr;

    @Autowired
	DA_MIG_AV_ACTR_IMG daMigAvActrImg;

    @Autowired
	DA_MIG_AV_MV daMigAvMv;

    @Autowired
	EntityManager em;
	
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "successful operation", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = OUT_DS.class)) }) 
	})
	@ApiOperation(tags={"AV"}, value = "AVDBS 배우 마이그", notes = "AVDBS 배우 마이그")
	//@GetMapping(path= "/api/BR_MIG_AV_ACTR_SYNC_BRTH")
	public OUT_DS run(IN_DS inDs) throws BizException {

        
        //insertActor(2309L);
        List<MigAvActr> al = daMigAvActr.findBySyncB();
		for(var i=0;i<al.size();i++){
			MigAvActr m=al.get(i);

            if(m.getSync().equals("B")){
                updtActor(m.getActrIdx());
                try {
                    Thread.sleep(2000);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }

		}
		OUT_DS outDs = new OUT_DS();
		return outDs;		
	}
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void updtActor(Long L_ACTR_IDX) throws BizException{
        HashMap<String, Object> tmp =getActorBirth(L_ACTR_IDX);
        String  BRTH     = tmp.get("BIRTH").toString();   //1993-08-16
        BRTH = BRTH.replaceAll("-","");
        String  BRA_SIZE     = tmp.get("BRA_SIZE").toString();   //1993-08-16
        daMigAvActr.updtMigAvActrBirth(L_ACTR_IDX,         
        BRTH,
        BRA_SIZE
        );       
    }
    
    private HashMap<String, Object> getActorBirth(Long ACTOR_IDX)  {
        HashMap<String, Object> result = new HashMap<String, Object>();
        //String url = "https://www.avdbs.com/menu/actor.php?actor_idx="+ACTOR_IDX;
        String url = "http://www.avdbs.com/menu/actor.php?actor_idx="+ACTOR_IDX;
        String tmp = httpU.httpGetAvdbs(url);
        
        Document doc = Jsoup.parseBodyFragment(tmp);
        String profile_birth  = doc.getElementsByClass("profile_birth").text();
        String profile_bra_size    = doc.getElementsByClass("profile_bra_size").text();
        profile_birth=profile_birth.replaceAll("생년월일 :", "").trim();
        if(profile_birth.length()>8){
        profile_birth=profile_birth.substring(0,10);
        }
        result.put("BIRTH", profile_birth);  //생년월
        result.put("BRA_SIZE", profile_bra_size.replaceAll("컵사이즈 :", "").replaceAll("컵", "").trim());   //가슴 사이즈
        return result;
    }
}
