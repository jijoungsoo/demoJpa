package com.example.demo.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

public class PjtUtil {

	static DateFormat dateformat1 = new SimpleDateFormat("yyyyMMddHHmmss");
	public static String getYyyyMMddHHMMSS(java.util.Date inDate) {
		
		return dateformat1.format(inDate);
	}
	static DateFormat dateformat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static String getYyyy_MM_dd_HHMMSS(java.util.Date inDate) {
		if(inDate==null){
			return null;
		}

		return dateformat2.format(inDate);
	}

	public static <T> T JsonStringToObject(String JsonInString, Class<T> valueType)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper omOut = new ObjectMapper();
		//느리니까 정리도 하지 말자 
		//omOut.enable(SerializationFeature.INDENT_OUTPUT);
		System.out.println(JsonInString);
		System.out.println(valueType);
		//https://answer-id.com/ko/52045806
		omOut.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);///변환활 클래스없는 필드는 json  필드 무시
		
		return omOut.readValue(JsonInString, valueType);
	}

	public static String ObjectToJsonString(Object value) throws JsonProcessingException {
		ObjectMapper om = new ObjectMapper();
		///느리니까 정리도 하지 말자 
		///om.enable(SerializationFeature.INDENT_OUTPUT);
		return om.writeValueAsString(value);
	}

	public static boolean isEmpty(String tmp) {
		if (tmp == null) {
			return true;
		}
		if (tmp.trim().length() == 0) {
			return true;
		}
		return false;
	}

	public static String str(Object tmp) {
		if (tmp == null) {
			return "";
		}
		String tmp2 = tmp.toString();
		return tmp2;
	}
	public static String strTrim(Object tmp) {
		if (tmp == null) {
			return "";
		}
		String tmp2 = tmp.toString().trim();
		return tmp2;
	}

	public static String nvl(String first, String second) {
		if (isEmpty(first)) {
			return second;
		}

		return first;
	}
	/* https://stackoverflow.com/questions/22271099/convert-exception-to-json */

	public static String convertExceptionToJSON(Throwable e) {
		JSONObject responseBody = new JSONObject();
		JSONObject errorTag = new JSONObject();
		try {
			responseBody.put("error", errorTag);
			JSONArray detailList = new JSONArray();
			errorTag.put("details", detailList);

			Throwable nextRunner = e;
			while (nextRunner != null) {
				Throwable runner = nextRunner;
				nextRunner = runner.getCause();

				JSONObject detailObj = new JSONObject();
				detailObj.put("code", runner.getClass().getName());
				String msg = runner.toString();
				detailObj.put("message", msg);

				detailList.put(detailObj);
			}

			JSONArray stackList = new JSONArray();
			for (StackTraceElement ste : e.getStackTrace()) {
				stackList.put(ste.getFileName() + ": " + ste.getMethodName() + ": " + ste.getLineNumber());
			}
			errorTag.put("stack", stackList);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return responseBody.toString();
	}

	public static String fileDwnld(String url){
		if(PjtUtil.isEmpty(url)){
			return null;
		}
		CloseableHttpClient client = HttpClientBuilder.create().build();
		String path = null;
		try {
            //String url = "https://i2.avdbs.com/actor/a02/2426_n.jpg";
            
            HttpGet request = new HttpGet(url);
			//String uri = request.getURI().toString();
			//System.out.println("uri=>"+uri);
			//String auth = request.getURI().getAuthority();
			//System.out.println("auth=>"+auth);
			//String fr = request.getURI().getFragment();
			//System.out.println("fr=>"+fr);
			//String host = request.getURI().getHost();
			//System.out.println("host=>"+host);
			path = request.getURI().getPath();
			//System.out.println("path=>"+path);  ////actor/a02/2426_n.jpg
			//String qu = request.getURI().getQuery();
			//System.out.println("qu=>"+qu);


			String filePath = "d:/avdbs.com"+path;
			String p = filePath.substring(0,filePath.lastIndexOf("/"));
			String OsDirPath = p.replace("/", Matcher.quoteReplacement(File.separator));
			File d = new File(OsDirPath);
			if(!d.exists()){	//폴더가 없으면 생성 상위 폴더 까지 전부
				System.out.println(OsDirPath);
				Boolean b =d.mkdirs();
				System.out.println(b);
				System.out.println(OsDirPath);
			}  			
			d=null;
			String OsFilePath = filePath.replace("/", Matcher.quoteReplacement(File.separator));
			//String lastPath = OsFilePath.replaceAll(Matcher.quoteReplacement(File.separator), "/");

			
			File f = new File(OsFilePath);
			if(f.exists()==false){
				System.out.println(OsFilePath);
				
				HttpResponse response = client.execute(request);
				HttpEntity entity = response.getEntity();
	 
				int responseCode = response.getStatusLine().getStatusCode();
	 
				System.out.println("Request Url: " + request.getURI());
				System.out.println("Response Code: " + responseCode);
	 
				//String filePath2 = "d:\\avdbs.com\\file.zip";

				System.out.println("fileDwnld=>"+url);
				InputStream is = entity.getContent();
				FileOutputStream fos = new FileOutputStream(f);
 
				int inByte;
				while ((inByte = is.read()) != -1) {
					fos.write(inByte);
				}
	 
				is.close();
				fos.close();
				System.out.println(OsFilePath);
				System.out.println("File Download Completed!!!");
			}
			f=null;
			client.close();


 
            


		
        } catch (ClientProtocolException e) {
            e.printStackTrace();
			try {
				client.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println("ccccccc");
				e1.printStackTrace();
			}
        } catch (UnsupportedOperationException e) {
			System.out.println("bbbbb");
            e.printStackTrace();
			try {
				client.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        } catch (IOException e) {
			System.out.println("aaaaaaaaa");
            e.printStackTrace();
			e.printStackTrace();
			try {
				client.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
		return path;
	}

}
