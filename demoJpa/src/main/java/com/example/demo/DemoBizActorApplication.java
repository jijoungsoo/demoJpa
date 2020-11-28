package com.example.demo;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.br.BR_PG_INIT;

@SpringBootApplication
public class DemoBizActorApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoBizActorApplication.class, args);
	}
	
	@Bean
	InitializingBean sendDatabase() {
	    return () -> {
	    	BR_PG_INIT t = new BR_PG_INIT();
	    	t.Init();
	    	/*
	        userRepository.save(new User("John"));
	        userRepository.save(new User("Rambo"));
	        */
	   };
	}
}
