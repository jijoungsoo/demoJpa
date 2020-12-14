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
import com.example.demo.cm.da.DA_CM_LOGIN;
import com.example.demo.domain.CmUser;
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
public class API_TEST {
	//goddaehee.tistory.com/211 [갓대희의 작은공간]
	
	@Autowired 
	MockMvc mvc;
	
	@Autowired 
	private WebApplicationContext ctx;

	@Autowired
	JPAQueryFactory qf;

	@BeforeEach() 	//Junit4의 @Before 
	public void setup() { 
		this.mvc = MockMvcBuilders.webAppContextSetup(ctx)
				.addFilters(new CharacterEncodingFilter("UTF-8", true)) // 필터 추가 
				.alwaysDo(print()) 
				.build(); 
	}
	@Test
	void loadUserByusername_restTest() throws Exception {
		String userJson = "{\"userId\":\"admin\", \"userPwd\":\"admin\"}";
		mvc.perform(post("/api/loadUserByusername_restTest")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.content(userJson)		
				) 
		.andExpect(status().isOk()) 
		//.andExpect(content().contentType(MediaType.APPLICATION_JSON)) 
		//.andExpect(jsonPath("$.id", is("goddaehee"))) 
		.andDo(print());
	}
	

}



