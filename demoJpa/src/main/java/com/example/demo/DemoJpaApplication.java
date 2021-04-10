package com.example.demo;

import java.text.NumberFormat;

import com.example.demo.ctrl.API;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

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
			API.init("classpath*:com/example/demo/br/**/*.class");  /*OpService Api 세팅*/
		} catch(Exception e) {
			//이거 환경설정 관련되는거니까. 여기 에러가 났다면
			 //spring boot 가 멈췄으면 좋겠다.
			e.printStackTrace();
			app.close();
		}
	}
	
	@Autowired
	DataLoader t;
	@Bean
	InitializingBean sendDatabase() {
	    return () -> {
	    	
	    	//t.loadData();
	    	/*
	        userRepository.save(new User("John"));
	        userRepository.save(new User("Rambo"));
	        */

			Runtime runtime = Runtime.getRuntime();
			final NumberFormat format = NumberFormat.getInstance();

			final long maxMemory = runtime.maxMemory();
			final long allocatedMemory = runtime.totalMemory();
			final long freeMemory = runtime.freeMemory();
			final long mb = 1024 * 1024;
			final String mega = " MB";

			log.info("========================== Memory Info ==========================");
			log.info("Free memory: " + format.format(freeMemory / mb) + mega);
			log.info("Allocated memory: " + format.format(allocatedMemory / mb) + mega);
			log.info("Max memory: " + format.format(maxMemory / mb) + mega);
			log.info("Total free memory: " + format.format((freeMemory + (maxMemory - allocatedMemory)) / mb) + mega);
			log.info("=================================================================\n");
	   };
	}
}
