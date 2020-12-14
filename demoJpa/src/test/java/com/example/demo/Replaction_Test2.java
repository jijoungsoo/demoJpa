package com.example.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.MethodMetadata;
import com.example.demo.cm.anotation.OpService;
import com.example.demo.cm.ctrl.API;
import com.example.demo.cm.ctrl.ApiResultMethod;


public class Replaction_Test2 {
		
	@Test
	void restTest2() throws Exception {
		/*https://okky.kr/article/272135   
		 * 
		 * 이코드의 문제점은   스프링에서 설졍한 Service,Component 만 알아먹는거다.
		 * 
		 * */
		if(false) {			
			List<Set<MethodMetadata>>  allMethodMetadatas = API.methodMetaScan("classpath*:com/example/demo/cm/br/**/*.class",OpService.class);
				System.out.println("allMethodMetadatas.siz=>"+allMethodMetadatas.size());
				for(int i=0;i<allMethodMetadatas.size();i++) {
					Set<MethodMetadata> tmp  = allMethodMetadatas.get(i);
					tmp.forEach(
							(key) -> {
								System.out.println("key: " + key.getDeclaringClassName());
								System.out.println("key: " + key.getMethodName());
								System.out.println("key: " + key.getReturnTypeName());
							}
					);
				}
		}
		if(false) {
			 List<ClassMetadata>  classeMetas = API.classMetaScan("classpath*:com/example/demo/cm/br/**/*.class",OpService.class);
			System.out.println("classeMetas.siz=>"+classeMetas.size());
			for(int i=0;i<classeMetas.size();i++) {
				System.out.println(classeMetas.get(i).getClassName());
				
			}
			
		}
		
		if(true) {			
			HashMap<String, ApiResultMethod>  allMethodMetadatas = API.opServiceMethodMetaScan("classpath*:com/example/demo/cm/br/**/*.class");
			System.out.println("allMethodMetadatas.siz=>"+allMethodMetadatas.size());
			allMethodMetadatas.forEach(
					(key,value) -> {
						System.out.println("key: " + key);
						System.out.println("key: " + value);
					}
			);
		}
	}
}



