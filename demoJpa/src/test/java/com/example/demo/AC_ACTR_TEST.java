package com.example.demo;

import javax.transaction.Transactional;

import com.example.demo.br.mig.mig_av.BR_MIG_AV_ACTR_SYNC;
import com.example.demo.exception.BizException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class AC_ACTR_TEST {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	BR_MIG_AV_ACTR_SYNC brMigavActrSync;
	/*
	@Autowired
	EntityManager em;
	*/

	@Test
	void contextLoads() {	
		
		try {
			mockMvc.perform(MockMvcRequestBuilders.get("/api/BR_MIG_AV_ACTR_SYNC"))
			.andExpect(MockMvcResultMatchers.status().isOk());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
