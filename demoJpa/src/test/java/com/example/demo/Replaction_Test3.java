package com.example.demo;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.MethodMetadata;
import com.example.demo.cm.anotation.OpService;
import com.example.demo.cm.br.BR_CM_LOGIN;
import com.example.demo.cm.ctrl.API;
import com.example.demo.cm.ctrl.ApiResultMethod;


public class Replaction_Test3 {
		
	@Test
	void restTest2() throws Exception {
		Class<?> cls = BR_CM_LOGIN.class;
		Method[] arrMethod = cls.getDeclaredMethods();
		for(int j=0;j<arrMethod.length;j++) {
			Method method = arrMethod[j];
			if("loadUserByusername".equals(method.getName())) {
				///List<String> al2 = reflections.getMethodParamNames(method);  
				/*이름이 얻어지기는 하는데.. local 변수도 얻어짐
				System.out.println(method);
				System.out.println(al2);
				System.out.println(al2.size());
				System.out.println(method.getParameters().length);
				*/
				for (Parameter p :method.getParameters()) {
					System.out.println(method.getName());
					System.out.println(p.getName());
					System.out.println(p.getType());
				}
			}
		}
	}
}



