package com.example.demo.ctrl;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.anotation.OpService;
import com.example.demo.exception.BizException;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Throwables;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@RestController 
public class API {
	public static void init(String classAntPattern) throws Exception{
		API.alOpServiceMethod = API.opServiceMethodMetaScan(classAntPattern);
	}
	public static ApiResultMethod getApiClass(String methodName){
		ApiResultMethod rm = alOpServiceMethod.get(methodName);
		return rm;
	}
	
	static HashMap<String,ApiResultMethod> alOpServiceMethod = new HashMap<String,ApiResultMethod>();
	public static HashMap<String, ApiResultMethod> opServiceMethodMetaScan(String classAntPattern) throws Exception {
		HashMap<String,ApiResultMethod> map = new HashMap<String,ApiResultMethod>();
		List<Set<MethodMetadata>> al= methodMetaScan(classAntPattern,OpService.class);
		for(int i=0;i<al.size();i++) {
			Set<MethodMetadata> setM = al.get(i);
			Iterator<MethodMetadata> iter=setM.iterator();
			while(iter.hasNext()) {
				MethodMetadata  key =iter.next();
				//System.out.println("key: " + key);
				//System.out.println("key.getMethodName(): " + key.getMethodName());
				//System.out.println("key.getDeclaringClassName(): " + key.getDeclaringClassName());
				//System.out.println("key.getReturnTypeName(): " + key.getReturnTypeName());
				//MergedAnnotations annotations =  key.getAnnotations();  이건 모르겠다.
	
				if(map.containsKey(key.getMethodName())) {
					// 스트림안에서 throw new Exception("API 서버의 함수는 중복될수없습니다."); 이되지 않아 아래와 같이 바꿨다.
					//https://qastack.kr/programming/23548589/java-8-how-do-i-work-with-exception-throwing-methods-in-streams
					ApiResultMethod rm = map.get(key.getMethodName());
					System.out.println(key.getDeclaringClassName() +"->"+key.getMethodName());
					System.out.println(rm.className +"->"+key.getMethodName());
					Exception e =new Exception("API 서버의 OpService 함수는 method 이릉이 중복될수없습니다.");
					throw Throwables.propagate(e);
				} else {
					Class<?> cls = null;
					try {
						cls = Class.forName(key.getDeclaringClassName());
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(cls==null ) {
						Exception e =new Exception("일어날일이 없는데 ["+key.getDeclaringClassName()+"] 클래스를 찾을수없습니다.");	
					}
					Method[] arrMethod = cls.getDeclaredMethods();
					for(int j=0;j<arrMethod.length;j++) {
						Method method = arrMethod[j];
						ApiResultMethod  tmp = new ApiResultMethod();
						tmp.className = key.getDeclaringClassName();
						tmp.methodName = key.getMethodName();
						map.put(key.getMethodName(), tmp);
						if(key.getMethodName().equals(method.getName())) {
						
							/*파라미터 IN_PUT은 IN_DS  아웃풋은 OUT_DS 무조건 정해져있다. 체크할 필요가 없다.*/
							Class<?> returnType = method.getReturnType();
							if(!returnType.getClass().getName().equals("OUT_DS")  ) {
								log.info("className=>"+key.getDeclaringClassName());
								log.info("methodName=>"+key.getMethodName());
								throw new BizException("opService를 구현함수는 반환값음 OUT_DS.class 형이여야합니다.");
							}
							
							if(method.getParameters().length!=1) {
								log.info("className=>"+key.getDeclaringClassName());
								log.info("methodName=>"+key.getMethodName());
								throw new BizException("opService를 구현함수의 입력파라미터는 IN_DS 하나여야합니다.");
							}
							
							for (Parameter p :method.getParameters()) {
								//tmp.parameters.put(p.getName(),p.getType());
								Class<?> argType = p.getType();
								if(!argType.getClass().getName().equals("IN_DS")  ) {
									log.info("className=>"+key.getDeclaringClassName());
									log.info("methodName=>"+key.getMethodName());
									throw new BizException("opService를 구현함수는 입력파라미터는 IN_DS.class 형이여야합니다.");
								}
							}
							map.put(key.getMethodName(), tmp);	

						}
					}
				}
			}
			/*람다식은 체크드 익세션을 잡아내기가 어렵다.
			setM.forEach(
				(key) -> {
				
				}
			);
			*/
		}
		return map;
	}
	
	public static List<Set<MethodMetadata>> methodMetaScan(String classAntPattern, Class annotationType) throws Exception {
		/*https://okky.kr/article/272135
		 * 
		 * 이 아저시께 결국엔 다 맞았다.
		 * 사용자정의 anntation을 정의 할때 꼭 
		 * @Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
		 * 적어주자 이거 안적어주면 못찾는다.
		 * */
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] rs;
		try {
			rs = resolver.getResources(classAntPattern);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		
		List<Set<MethodMetadata>> allMethodMetadatas = new ArrayList<Set<MethodMetadata>>();
		for(Resource r : rs) {
			MetadataReader mr;
			try {
				mr = new SimpleMetadataReaderFactory().getMetadataReader(r);
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
			Set<MethodMetadata> methodMetadatas = mr.getAnnotationMetadata().getAnnotatedMethods(annotationType.getTypeName());
			allMethodMetadatas.add(methodMetadatas);
			
		
		}
		return allMethodMetadatas;
		
	}
	public static List<ClassMetadata> classMetaScan(String classAntPattern,Class annotationType) throws Exception {
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] rs;
		try {
			rs = resolver.getResources(classAntPattern);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		List<ClassMetadata> classeMetas = new ArrayList<ClassMetadata>();
		for(Resource r : rs) {
			MetadataReader mr;
			try {
				mr = new SimpleMetadataReaderFactory().getMetadataReader(r);
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
			boolean hasAnnotation =mr.getAnnotationMetadata().hasAnnotation(annotationType.getName());  /*service  스트레오 메칭*/
			if (hasAnnotation) {
				classeMetas.add(mr.getClassMetadata());
			}
		}
		return classeMetas;
	}
}


