package com.example.demo;

import com.example.demo.br.mig.mig_stck.SA_MIG_STCK_SYNC;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;


@SpringBootTest(properties = "classpath:/application.yaml"
			,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
		)  /*https://velog.io/@hellozin/Spring-Boot-Test에서-Yaml-프로퍼티-적용하기*/
@AutoConfigureMockMvc
@Rollback(false)  /*롤백하지 않게 하자. */
public class Replaction_Test4 {
	@Autowired
	SA_MIG_STCK_SYNC s;
		
	@Test
	void restTest2() throws Exception {
		s.run2();
	}
}



