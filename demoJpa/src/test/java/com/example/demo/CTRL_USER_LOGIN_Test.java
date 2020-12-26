package com.example.demo;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j; 
import org.junit.jupiter.api.Test; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate; 
import org.springframework.http.MediaType; 
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc; 
import org.springframework.test.web.servlet.setup.MockMvcBuilders; 
import org.springframework.web.context.WebApplicationContext; 
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print; 
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.cm.br.BR_CM_LOGIN;
import com.example.demo.db.da.cm.DA_CM_LOGIN;
import com.example.demo.db.domain.cm.CmUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.jpa.impl.JPAQueryFactory;

@SpringBootTest(properties = "classpath:/application.yml"
			,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
		)  /*https://velog.io/@hellozin/Spring-Boot-Test에서-Yaml-프로퍼티-적용하기*/
@Transactional
@AutoConfigureMockMvc
@Rollback(false)  /*롤백하지 않게 하자. */
@Slf4j
/*@ContextConfiguration(loader = AnnotationConfigContextLoader.class) */ /*이거하면 임시 디비가 만들어지는 듯하다. --위에 실db쓰는건 h2 특성상 2개를 못여는 듯한다. 서버 모드 안됨 */		
public class CTRL_USER_LOGIN_Test {
	//goddaehee.tistory.com/211 [갓대희의 작은공간]
	
	@Autowired 
	MockMvc mvc;
	
	@Autowired 
	private WebApplicationContext ctx;
	
	@Autowired 
	private TestRestTemplate restTemplate;

	@Autowired
	JPAQueryFactory qf;
	
	@Autowired
	DA_CM_LOGIN daLogin;
		
	@Autowired
	BR_CM_LOGIN BrLogin;

	@BeforeEach() 	//Junit4의 @Before 
	public void setup() { 
		this.mvc = MockMvcBuilders.webAppContextSetup(ctx)
				.addFilters(new CharacterEncodingFilter("UTF-8", true)) // 필터 추가 
				.alwaysDo(print()) 
				.build(); 
	}
	
	
	void DA_LOGIN_findByUserId() {
		 CmUser c = daLogin.findByUserId("admin");		
		 assertNotNull(c);
	}
	
	/*
	
	void BR_LOGIN_Login() throws BizException {
		String userId="admin";
		String userPwd="admin";
		CmUser c = BrLogin.Login(userId,userPwd);
		assertNotNull(c);
	}
	*/
	
	void CNTRL_USER_restTest1() throws Exception {
		mvc.perform(post("/restTest1")) 
		.andExpect(status().isOk()) 
		//.andExpect(content().contentType(MediaType.APPLICATION_JSON)) 
		//.andExpect(jsonPath("$.id", is("goddaehee"))) 
		.andDo(print());
	}
	void CNTRL_USER_restTest2() throws Exception {
		mvc.perform(post("/restTest2")
					.content("test--")		
				) 
		.andExpect(status().isOk()) 
		//.andExpect(content().contentType(MediaType.APPLICATION_JSON)) 
		//.andExpect(jsonPath("$.id", is("goddaehee"))) 
		.andDo(print());
	}
	
	void CNTRL_USER_restTest3() throws Exception {
		String userJson = "{\"userId\":\"admin\", \"userPwd\":\"admin\"}";
		mvc.perform(post("/restTest3")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.content(userJson)		
				) 
		.andExpect(status().isOk()) 
		//.andExpect(content().contentType(MediaType.APPLICATION_JSON)) 
		//.andExpect(jsonPath("$.id", is("goddaehee"))) 
		.andDo(print());
	}
	
	void CNTRL_USER_restTest4() throws Exception {
		String userJson = "{\"userId\":\"admin\", \"userPwd\":\"admin\"}";
		mvc.perform(post("/restTest4")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.content(userJson)		
				) 
		.andExpect(status().isOk()) 
		.andExpect(content().contentType(MediaType.APPLICATION_JSON)) 
		//.andExpect(jsonPath("$.id", is("goddaehee"))) 
		.andDo(print());
	}

	void CNTRL_USER_restTest5() throws Exception {

		//출처: https://engkimbs.tistory.com/770 [새로비]
		String userJson = "{\"userId\":\"admin\", \"userPwd\":\"admin\"}";
		mvc.perform(post("/restTest5")
							.contentType(MediaType.APPLICATION_JSON)
							.accept(MediaType.APPLICATION_JSON)
							.content(userJson)		
				)		
		.andExpect(status().isOk()) 
		.andExpect(content().contentType(MediaType.APPLICATION_JSON)) 
		//.andExpect(jsonPath("$.id", is("goddaehee"))) 
		.andDo(print());
	}

	void CNTRL_USER_restTest6() throws Exception {

		//출처: https://engkimbs.tistory.com/770 [새로비]
		String userJson = "{\"userId\":\"admin\", \"userPwd\":\"admin\"}";
		mvc.perform(post("/restTest6")
							.contentType(MediaType.APPLICATION_JSON)
							.accept(MediaType.APPLICATION_JSON)
							.content(userJson)		
				)		
		.andExpect(status().isOk()) 
		.andExpect(content().contentType(MediaType.APPLICATION_JSON)) 
		//.andExpect(jsonPath("$.id", is("goddaehee"))) 
		.andDo(print());
	}
	
	@Data 
	@NoArgsConstructor 
	@ToString 
	static class ReqData 
	{ 
		List<ReqData.InData> inData =new ArrayList<ReqData.InData>();		
		@Data 
		@NoArgsConstructor 
		@ToString 
		public static class InData { 
			String userId; 
			String userPwd; 
		}
	}	
	
	@Test
	@Order(6)
	void CNTRL_USER_login() throws Exception {
		ObjectMapper om = new ObjectMapper();
		ReqData r= new ReqData();
		ReqData.InData n =new ReqData.InData();
		n.userId="admin";
		n.userPwd="admin";
		r.getInData().add(n);
		
		String userJson=om.writeValueAsString(r);
		
		System.out.println(userJson);
		//String userJson = "[{\"userId\":\"admin\", \"userPwd\":\"admin\"}]";
		mvc.perform(post("/login")
							.contentType(MediaType.APPLICATION_JSON)
							.accept(MediaType.APPLICATION_JSON)
							.content(userJson)		
				)		
		.andExpect(status().isOk()) 
		.andExpect(content().contentType(MediaType.APPLICATION_JSON)) 
		//.andExpect(jsonPath("$.id", is("goddaehee"))) 
		.andDo(print());
	}
}



