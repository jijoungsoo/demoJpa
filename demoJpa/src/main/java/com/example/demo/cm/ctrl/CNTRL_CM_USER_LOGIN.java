package com.example.demo.cm.ctrl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.cm.br.BR_CM_LOGIN;
import com.example.demo.domain.CmUser;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;




@RestController 
public class CNTRL_CM_USER_LOGIN {
	@Autowired
	private BR_CM_LOGIN BrL;
	
	@Data 
	@NoArgsConstructor 
	@ToString 
	static class ReqData 
	{ 
		List<InData> inData =new ArrayList<InData>();		
		@Data 
		@NoArgsConstructor 
		@ToString 
		public static class InData { 
			String userId; 
		}
	}
	
	
	@Data 
	@NoArgsConstructor 
	@ToString 
	static class ResData 
	{ 
		List<OutData> outData =new ArrayList<OutData>();
		
		@Data 
		@NoArgsConstructor 
		@ToString 
		public static class OutData { 
			String userId;
			String userPwd;
			String userNm;
			String email;
			String auth;
		}
	}
	
	/*BizActor의 구현으로 본다면 오히려  Map형태가 더 맞아보이기까지한다.
	 왜냐하면   C#의  DataSet이 오히려 듀플 같은 자료형이기 때문이다.
	 json을 맵으로 구현하는 방법을 찾아보자.
	 
	  그리고 IN_DATA하고 OUT_DATA도 오히려 Map에 키에서 찾는게 더 편해보인다.
	  넘어온 데이터셋 타입하고,   응답할 데이터셋 타입하고도 확인할때 map의 키로 확인하는게 더 빠르다.
	  DataSet처럼 오히려 커스트마이징된 자료형이 있으면 더 좋겠다.
	  인터넷에서 java용 dataSet을 찾아보자.
	 */
	/*
	 * https://velog.io/@hellozin/Spring-Security-Form-Login-%EA%B0%84%EB%8B%A8-%EC%82%AC%EC%9A%A9-%EC%84%A4%EB%AA%85%EC%84%9C-f2jzojj8bj
	 * 
	 * 암호화 때문에 걸었던 spring boot    security 때문에 로그인 페이지가 자동으로 생겼다.
	 * 이걸 제거 해야한다.
	 * 웹에서는 이 화면을 쓸수있게 만들자.
	 * 
	 * security가 filter를 구현하는 것 보다 좋은것 같다.
	 * 이미지나 그런 파일들을 필터를 걸어주지 않아도 되고 말이다.
	 * 저 매칭이 자동적으로 control의 url 만 찾는지는 관심가져볼만 한다.
	 * fillter는 그런게 안되었다.
	 * 
	 * */
/*
	@PostMapping(path= "/login", consumes = "application/json", produces = "application/json")
	public ResData Login(@RequestBody ReqData param) throws Exception {
		System.out.println(param.inData.size());
		if(param.inData.size()<=0) {
			ResData r=new ResData();
			return r; 
		}
		CmUser c=BrL.Login(param.inData.get(0).userId, param.inData.get(0).userPwd);
		
		
		ResData r=new ResData();
		ResData.OutData ro = new ResData.OutData();
		ro.userId=c.getUserId();
		ro.userNm=c.getUserNm();
		ro.Email=c.getEmail();
		r.getOutData().add(ro);
		
		return r;		
	}
	*/
	
	
/*spring security 기본구현 관련 */
	/*
	@PostMapping(path= "/loadUserByusername", consumes = "application/json", produces = "application/json")
	public ResData loadUserByusername(@RequestBody ReqData param) throws Exception {
		System.out.println(param.inData.size());
		if(param.inData.size()<=0) {
			ResData r=new ResData();
			return r; 
		}
		CmUser c=BrL.loadUserByusername(param.inData.get(0).userId);
		
		
		ResData r=new ResData();
		ResData.OutData ro = new ResData.OutData();
		ro.userId=c.getUserId();
		ro.userNm=c.getUserNm();
		ro.userPwd=c.getUserPwd();
		ro.email=c.getEmail();
		ro.auth="ADMIN";
		r.getOutData().add(ro);
		
		return r;		
	}*/
}

