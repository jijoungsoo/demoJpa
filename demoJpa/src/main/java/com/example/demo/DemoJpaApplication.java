package com.example.demo;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import com.example.demo.cm.br.BR_CM_PGM_INIT;
import com.example.demo.cm.ctrl.API;
import com.example.demo.exception.BizException;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class DemoJpaApplication {
	

	public static void main(String[] args) throws Exception {
		 CustomBeanNameGenerator beanNameGenerator = new CustomBeanNameGenerator();  /*bean이름을 패키지가 지정하는  */
	     beanNameGenerator.addBasePackages("com.example.demo.");   /*범위지정*/
	     ConfigurableApplicationContext  app = new SpringApplicationBuilder(DemoJpaApplication.class)
	                .beanNameGenerator(beanNameGenerator)
	                .run(args);		
		//SpringApplication.run(DemoJpaApplication.class, args);
		log.info("INFO");
		log.debug("DEBUG");
		log.warn("WARN");
		log.error("ERROR");
		try {
			API.init("classpath*:com/example/demo/cm/br/**/*.class");  /*OpService Api 세팅*/
		} catch(org.h2.jdbc.JdbcSQLNonTransientConnectionException e) {
			e.printStackTrace();
			//이건 종료 무시
		} catch (BizException e) {
			//이거 환경설정 관련되는거니까. 여기 에러가 났다면
			 //spring boot 가 멈췄으면 좋겠다.
			e.printStackTrace();
			app.close();
		}
	}
	
	@Autowired
	BR_CM_PGM_INIT t;
	@Bean
	InitializingBean sendDatabase() {
	    return () -> {
	    	
	    	//t.Init();
	    	/*
	        userRepository.save(new User("John"));
	        userRepository.save(new User("Rambo"));
	        */
	   };
	}
}
