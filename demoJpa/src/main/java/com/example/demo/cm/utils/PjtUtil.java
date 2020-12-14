package com.example.demo.cm.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.cm.anotation.OpService;
import com.example.demo.cm.ctrl.IN_DS;
import com.example.demo.cm.ctrl.OUT_DS;
import com.example.demo.cm.da.DA_CM_LOGIN;
import com.example.demo.domain.CmUser;
import com.example.demo.exception.BizException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


public class PjtUtil {
	public static String getYyyyMMddHHMMSS(java.util.Date inDate) {
		DateFormat dateformat = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateformat.format(inDate);
	}

	public static  <T> T JsonStringToObject(String JsonInString, Class<T> valueType) throws JsonMappingException, JsonProcessingException {
		ObjectMapper omOut = new ObjectMapper();
    	omOut.enable(SerializationFeature.INDENT_OUTPUT);
		return omOut.readValue(JsonInString,valueType);		
	}
	
	public static String ObjectToJsonString(Object value) throws JsonProcessingException {
		ObjectMapper om = new ObjectMapper();
		om.enable(SerializationFeature.INDENT_OUTPUT);
		return om.writeValueAsString(value);
	}
	
	public static boolean isEmpty(String tmp) {
		if(tmp == null) {
			return true;
		}
		if(tmp.length()==0) {
			return true;
		}
		return false;
	}
	
	public static String str(Object tmp) {
		if(tmp == null) {
			return "";
		}
		String tmp2 = tmp.toString();
		return tmp2;
	}
	
	public static String nvl(String first,String second) {
		if(isEmpty(first)) {
			return second;
		}
		
		return  first;
	}


	
	
}
