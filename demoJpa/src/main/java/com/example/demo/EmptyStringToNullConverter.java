package com.example.demo;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true) 
public class EmptyStringToNullConverter implements AttributeConverter<String, String> 
{ 
	/*https://juntcom.tistory.com/94 
	 * 여기는 domain에 컬럼마다 지정해야하는 것처럼 나왔는데.
	 * https://stackoverflow.com/questions/47221538/empty-string-field-as-null-field
	 * 여기는 마치 자동으로 전부 적용되는 것 처럼 나왔다.
	 * 
	 * 오 따라 컬럼 위에 적지않아도 자동적용된다.!!
	 * */
	@Override 
	public String convertToDatabaseColumn(String string) { 
	// Use defaultIfEmpty to preserve Strings consisting only of whitespaces 
	return "".equals(string) ? null : string; 
	} 
	@Override 
	public String convertToEntityAttribute(String dbData) 
	{ 
	//If you want to keep it null otherwise transform to empty String 
		return dbData;
	} 
}