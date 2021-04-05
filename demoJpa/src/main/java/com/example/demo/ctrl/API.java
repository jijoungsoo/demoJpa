package com.example.demo.ctrl;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.example.demo.anotation.OpService;
import com.example.demo.exception.BizException;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Throwables;

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
			System.out.println(br);
			System.out.println(jsonInString);
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
			System.out.println(resMap.rm.method.getName());
			System.out.println(resMap.rm.method.getParameterTypes());
			System.out.println(resMap.rm.method.getReturnType());
			 Method method = bean.getClass().getMethod(resMap.rm.method.getName(),resMap.rm.method.getParameterTypes());  /*인풋타입은 무조건 정해졌다. */

			 Object tmp=PjtUtil.JsonStringToObject(jsonInString, resMap.rm.method.getParameterTypes()[0]);
			 
			 ret = method.invoke(bean, tmp);

			 String outString = PjtUtil.ObjectToJsonString(ret);/*리턴타입은 무조건 정해져있다.*/
			 System.out.println(outString);
			 return outString;
		} catch (BizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resMap.success="false";
			resMap.errorMessage=e.getMessage();
			//resMap.errorMessage=PjtUtil.convertExceptionToJSON(e);	
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
			
		} catch (java.lang.reflect.InvocationTargetException e) {
			e.printStackTrace();
			e.getTargetException().printStackTrace();
			//log.info("AAAAA");
			//log.info(e.getMessage());   e.getMessage로 가져올수가 없고
			//log.info("BBBBB");
			//log.info(e.getTargetException().getMessage());  이걸로 가져와야 값이 있다.
			resMap.success="false";
			resMap.errorMessage=e.getTargetException().getMessage();
			//resMap.errorMessage=PjtUtil.convertExceptionToJSON(e.getTargetException());

			return out;
		} catch (Exception e) {
			e.printStackTrace();
			resMap.success="false";
			resMap.errorMessage="알수없는 시스템 오류입니다.(Exception)";

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
		resMap.brRs=rs.get("brRs").toString();
		if(rs.get("uuid")!=null) {
			resMap.uuid=rs.get("uuid").toString();
		}
		if(rs.get("pgmId")!=null) {
			resMap.pgmId=rs.get("pgmId").toString();
		}
		String brRq = resMap.brRq;   /*요청데이터 테이블둘 IN_DATA,IN_DATA2등*/
		String brRs = resMap.brRs;   /*응답 테이블둘 OUT_DATA,OUT_DATA등*/
		String[] arrBrRq=brRq.split(",");
		String[] arrBrRs=brRs.split(",");
		
		for(int i =0; i<arrBrRq.length;i++) {
			String tmp  = arrBrRq[i];
			if(tmp !=null && tmp.length()>0) {
				if(!rs.containsKey(tmp)) {
					throw new BizException(tmp+"가 인풋 요청 파라미터 테이블이 존재하지 않습니다.");  
					/*사실 이건 이쪽에 안있어도된다.  왜냐면 클라이언트 체크니까.
					*/
				}
			}
		}
	}


	public static void init(String classAntPattern) throws Exception{
		API.opServiceMetaScan(classAntPattern);
	}
	public static ApiResultMethod getApiClass(String methodName){
		ApiResultMethod rm = alOpService.get(methodName);
		return rm;
	}
	
	static HashMap<String,ApiResultMethod> alOpService = new HashMap<String,ApiResultMethod>();
	public static void opServiceMetaScan(String classAntPattern) throws Exception {
		List<ClassMetadata> al= classMetaScan(classAntPattern,OpService.class);
		for(int i=0;i<al.size();i++) {
			ClassMetadata setM = al.get(i);
			String class_name = setM.getClassName();
			Class<?> cls = null;
			try {
				cls = Class.forName(class_name);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(cls==null ) {
				Exception e =new Exception("일어날일이 없는데 ["+class_name+"] 클래스를 찾을수없습니다.");	
			}

			String simple_class_name = cls.getSimpleName();
			System.out.println(class_name);
			if(alOpService.containsKey(simple_class_name)) {
				ApiResultMethod rm = alOpService.get(simple_class_name);
				System.out.println(simple_class_name);
				System.out.println(class_name);
				System.out.println(rm.className);
				Exception e =new Exception("API 서버의 OpService ["+simple_class_name+"]  는 class명이 이릉이 중복될수없습니다.");
				throw Throwables.propagate(e);
			} else {

				Method[] arrMethod = cls.getDeclaredMethods();
				Boolean existsRun = false;
				for(int j=0;j<arrMethod.length;j++) {
					Method method = arrMethod[j];
					if("run".equals(method.getName())) {
						if(method.getParameters().length!=1){
							Exception e =new Exception("API 서버의 OpService ["+simple_class_name+"] 는 run 함수는 한개의 input 파라미터를 가지고 있어야합니다.");
							throw Throwables.propagate(e);
						}
						existsRun=true;
						ApiResultMethod  tmp = new ApiResultMethod();
						tmp.className=class_name;
						tmp.method=method;
						alOpService.put(simple_class_name, tmp);		
					}
				}
				if(!existsRun){
					System.out.println(simple_class_name);
					System.out.println(class_name);
					Exception e =new Exception("API 서버의 OpService ["+simple_class_name+"] 는 run 함수를 가지고 있어야합니다.");
					throw Throwables.propagate(e);
				}

				
			}
		}
	}
	/*
	public static List<Set<MethodMetadata>> methodMetaScan(String classAntPattern, Class annotationType) throws Exception {
		*/
		/*https://okky.kr/article/272135
		 * 
		 * 이 아저시께 결국엔 다 맞았다.
		 * 사용자정의 anntation을 정의 할때 꼭 
		 * @Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
		 * 적어주자 이거 안적어주면 못찾는다.
		 * */

		 /*
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
		
	}*/
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


