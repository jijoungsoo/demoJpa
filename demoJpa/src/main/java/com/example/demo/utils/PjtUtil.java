package com.example.demo.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
		if (tmp.length() == 0) {
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

}
