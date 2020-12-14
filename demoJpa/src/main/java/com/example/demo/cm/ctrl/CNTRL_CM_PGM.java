package com.example.demo.cm.ctrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.cm.br.BR_CM_PGM;
import com.example.demo.domain.CmPgm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@RestController 
public class CNTRL_CM_PGM {
	@Autowired
	private BR_CM_PGM BrP;

	@Description("프로그램등록")
	@PostMapping(path= "/makePgm", consumes = "application/json", produces = "application/json")
	public String makePgm(String jsonInString) throws Exception {
		
		ObjectMapper om = new ObjectMapper();
		om.enable(SerializationFeature.INDENT_OUTPUT);
		String jsonOutString = null;
		
		
		
		
		
		
		return jsonOutString;
	}
	
	@Description("프로그램삭제")
	@PostMapping(path= "/rmPgm", consumes = "application/json", produces = "application/json")
	public String rmPgm(String jsonInString) throws Exception {
		ArrayList<HashMap<String, Object>> dataSet = new ArrayList<HashMap<String,Object>>();
		ObjectMapper om = new ObjectMapper();
		om.enable(SerializationFeature.INDENT_OUTPUT);
		dataSet=om.readValue(jsonInString,ArrayList.class);
		
		/*실제로 요청되지 않을수도있다.
		if(dataSet.size()==0) {
			throw new Exception("인풋 파라미터가 요청되지 않았습니다.");
		}
		*/		
		
		if(dataSet.size()>1) {  /*오히려 root 레벨이기에 1보타 크면 안된다.*/
			throw new Exception("인풋 파라미터가 요청되지 않았습니다.");
		}
		
		//if(dataSet.size()>1)
		String baRq = dataSet.get(0).get("baRq").toString();   /*요청데이터 테이블둘 IN_DATA,IN_DATA2등*/
		String baRs = dataSet.get(0).get("baRq").toString();   /*응답 테이블둘 OUT_DATA,OUT_DATA등*/
		String[] arrBaRq=baRq.split(",");
		String[] arrBaRs=baRs.split(",");
		
		for(int i =0; i<arrBaRq.length;i++) {
			String tmp  = arrBaRq[i];
			if(!dataSet.get(0).containsKey(tmp)) {
				throw new Exception(tmp+"가 인풋이 요청에 존재하지 않습니다.");  
				/*사실 이건 이쪽에 안있어도된다.  왜냐면 클라이언트 체크니까.
				*/
			}
			if(dataSet.get(0).containsKey(tmp)) {
				ArrayList<HashMap<String, Object>> rs = (ArrayList<HashMap<String, Object>>)dataSet.get(0).get(tmp);
				if(rs.size()==0) {
					
				}
				
				
			}
		}
		/*서버체크를 해야한다.*/
		/*데이터 타입음 개발자가 정했다.
		 * IN_DATA 
		 * [{ PGM_ID : 키   },{ PGM_ID : 키   },{ PGM_ID : 키   } ]
		 * 이런식이이어야한다.
		 * */
		
		
		
				
		
		
		for(int i =0; i<arrBaRs.length;i++) {
			String tmp  = arrBaRs[i];
			if(!dataSet.contains(tmp)) {
				throw new Exception(tmp+" 아웃풋에 응답에 존재하지 않습니다.");
			}
		}
		
		Class clazz = BR_CM_PGM.class;
		java.lang.reflect.Method mt = clazz.getDeclaredMethod("rmPgm");
		mt.getName();
		
		
		
		 
		
		
		String jsonOutString = null;
		return jsonOutString;
	}
	
	
}

