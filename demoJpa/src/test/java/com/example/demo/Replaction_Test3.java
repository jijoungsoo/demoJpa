package com.example.demo;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import org.junit.jupiter.api.Test;

import com.example.demo.br.cm.cm_login.BR_CM_LOGIN_LOAD_USER_BY_USER_NAME;


public class Replaction_Test3 {
		
	@Test
	void restTest2() throws Exception {
		Class<?> cls = BR_CM_LOGIN_LOAD_USER_BY_USER_NAME.class;
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



