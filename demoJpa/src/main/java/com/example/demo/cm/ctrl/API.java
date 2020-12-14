package com.example.demo.cm.ctrl;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.MethodParameterNamesScanner;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.DemoJpaApplication;
import com.example.demo.cm.anotation.OpService;
import com.example.demo.cm.utils.PjtUtil;
import com.example.demo.exception.BizException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Throwables;

import antlr.StringUtils;
import ch.qos.logback.classic.Logger;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@RestController 
public class API {
	@Autowired
	private ApplicationContext _appContext;
	
	@PostMapping(path= "/api/{br}", consumes = "application/json", produces = "application/json")
	public String callAPI(@PathVariable("br") String br
			,@RequestBody/*중요 이거 없으면 못읽어옴*/ String jsonInString
			)  {
		String out = null;
		ObjectMapper omOut = new ObjectMapper();
		omOut.enable(SerializationFeature.INDENT_OUTPUT);
		final ApiResultMap resMap = new ApiResultMap();
		resMap.brId			= br;
		resMap.jsonInString	= jsonInString;
		/*이쯤되면    input을 JSON으로 받는다는게 맞을까? 
		 *차라리 넘어올거라고 생각하는 BR param으로 보낼까 하다 범용성에json이 맞을 것 같다.
		 * */
		
		try {
			/*IN OUT 변수 감증 및 BR 존재하는지 검증, 아웃풋에 인풋까지 호출하는 정보까지 모두 담고있다.*/
			validOpService(br,jsonInString,resMap /*아웃 참조로 써보자.*/);   
			/*빌드 패턴이고   하나의 명령이 하나의 클래스면   java replaction으로   값을 보내기 좋은데 
		 	*하나의 클래스에 여러개의 br이라고 생각하니까. 복잡하다.
		 	*그래서 ejb가 클래스마다 명령이고 3개씩 만드나 보다. 
		 	*/
		    //Object bean = _appContext.getBean("com.content.partner.Movie");  <== 내입장에서는 이게 맞는데??  sprnig 컨트롤 네임이 중복되지 못하나??
			//Object bean = _appContext.getBean("Movie");  이렇게 해야한다고 한다.  <== 찾아보니까 service 이름이 중복되지 못해서 serivce(이름을 주거나 )     
			//http://itnp.kr/post/spring-annotation-driven-bean-name-conflicts
			//https://blog.woniper.net/318  변경해서 달았다. CustomBeanNameGenerator 고고
			Object ret = null;
			Object bean = _appContext.getBean(resMap.rm.className);
			 Method method = bean.getClass().getMethod(resMap.rm.methodName,IN_DS.class);  /*인풋타입은 무조건 정해졌다. */
			 ret = method.invoke(bean,resMap.IN_DS);
			 resMap.OUT_DS =(OUT_DS) ret;

			 resMap.jsonOutString =PjtUtil.ObjectToJsonString(resMap.OUT_DS);/*리턴타입은 무조건 정해져있다.*/
			 System.out.println("API==>"+resMap.jsonOutString);
			 resMap.success="true";
			 out = PjtUtil.ObjectToJsonString(resMap);
			 log.info(out);  //이건 나중에 서버에 저장하는 용도로 가지고 있자.
			 return out;
		} catch (BizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resMap.success="false";
			resMap.errorMessage=e.getMessage();			
			try {
				out = PjtUtil.ObjectToJsonString(resMap);
			} catch (JsonProcessingException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			return out;
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	
			/*여길 타는건 없다.*/
			resMap.success="false";
			resMap.errorMessage="알수없는 시스템 오류입니다.(JsonProcessingException)";
			try {
				out = PjtUtil.ObjectToJsonString(resMap);
			} catch (JsonProcessingException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			return out;
		} catch (Exception e) {
			e.printStackTrace();
			resMap.success="false";
			resMap.errorMessage="알수없는 시스템 오류입니다.(Exception)";
			try {
				out = PjtUtil.ObjectToJsonString(resMap);
			} catch (JsonProcessingException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			return out;
		}
	}
	
	public void validOpService(String br,String jsonInString,ApiResultMap resMap /*아웃*/) throws Exception {
		resMap.rm= API.getApiClass(br);
		resMap.brId=br;
		resMap.jsonInString=jsonInString;
		if(resMap.rm==null) {			
			BizException bE= new BizException(br+" opService가 없습니다.");
			throw bE;
		}
		resMap.rm.methodName = br;		
		HashMap<String, Object> rs = null;
		try {
			rs=PjtUtil.JsonStringToObject(jsonInString, HashMap.class);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BizException("valid 한 json 인풋 파라미터가 요청되지 않았습니다.");
		}
		
		if(rs.get("brRq")==null) {
			throw new BizException("baRq 요청 파라미터 테이블명이 존재하지않습니다. 인풋이 없다면 baRq: '' 라도 보내야합니다. ");
		}
		
		if(rs.get("brRs")==null) {
			throw new BizException("baRs 응답 파라미터 테이블명이 존재하지않습니다. 아웃풋이 없다면 baRs:'' 라도 보내야합니다. ");
		}
		
		//if(dataSet.size()>1)
		 
		resMap.brId=br;
		resMap.brRq=rs.get("brRq").toString();
		resMap.brRs=rs.get("brRq").toString();
		if(rs.get("uuid")!=null) {
			resMap.uuid=rs.get("uuid").toString();
		}
		if(rs.get("pgmId")!=null) {
			resMap.pgmId=rs.get("pgmId").toString();
		}
		String baRq = resMap.brRq;   /*요청데이터 테이블둘 IN_DATA,IN_DATA2등*/
		String baRs = resMap.brRs;   /*응답 테이블둘 OUT_DATA,OUT_DATA등*/
		String[] arrBaRq=baRq.split(",");
		String[] arrBaRs=baRs.split(",");
		
		for(int i =0; i<arrBaRq.length;i++) {
			String tmp  = arrBaRq[i];
			if(tmp !=null && tmp.length()>0) {
				if(!rs.containsKey(tmp)) {
					throw new BizException(tmp+"가 인풋 요청 파라미터 테이블이 존재하지 않습니다.");  
					/*사실 이건 이쪽에 안있어도된다.  왜냐면 클라이언트 체크니까.
					*/
				}
				if(rs.containsKey(tmp)) {
					resMap.IN_DS.put(tmp,  (ArrayList<HashMap<String, Object>>) rs.get(tmp)); 
				}	
			}
			
		}
	}
	
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
							if(!returnType.getClass().getName().equals(OUT_DS.class.getClass().getName())  ) {
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
								if(!argType.getClass().getName().equals(IN_DS.class.getClass().getName())  ) {
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

