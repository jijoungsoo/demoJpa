package com.example.demo;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.br.BR_PGM_INIT;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class DemoJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoJpaApplication.class, args);
		log.info("INFO");
		log.debug("DEBUG");
		log.warn("WARN");
		log.error("ERROR");
	}
	@Autowired
	BR_PGM_INIT t;
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
