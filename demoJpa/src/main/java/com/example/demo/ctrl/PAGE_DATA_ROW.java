package com.example.demo.ctrl;

import com.example.demo.exception.BizRuntimeException;
import com.example.demo.utils.PjtUtil;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@ApiModel(value = "PAGE_DATA_ROW")
public class PAGE_DATA_ROW {

	public PAGE_DATA_ROW(){
		
	}
	public PAGE_DATA_ROW(Page<?> p){
		this.PAGE_NUM=String.valueOf(p.getNumber());
		this.TOTAL_SIZE=String.valueOf(p.getTotalElements());
		this.TOTAL_PAGE=String.valueOf(p.getTotalPages());
		this.PAGE_SIZE=String.valueOf(p.getSize());
	}

	
	@JsonProperty("PAGE_NUM")
	@Schema(name = "PAGE_NUM", example = "1", description = "페이지 숫자(1부터 시작)")
	String PAGE_NUM = "";
	@JsonProperty("PAGE_SIZE")
	@Schema(name = "PAGE_SIZE", example = "10", description = "페이지 사이즈(화면에 보이는 row수)")
	String PAGE_SIZE = "";
	
	@JsonProperty("TOTAL_SIZE")
	@Schema(name = "TOTAL_SIZE", example = "100", description = "전체 count")
	String TOTAL_SIZE = "";
	
	@JsonProperty("TOTAL_PAGE")
	@Schema(name = "TOTAL_PAGE", example = "3", description = "전체 page 수")
	String TOTAL_PAGE = "";
	
	public void validPageData()  throws BizRuntimeException {
		if(PjtUtil.isEmpty(PAGE_NUM)) {
			throw new BizRuntimeException("페이지번호 잘못입력되었습니다.1");
		}
		
		if(PjtUtil.isEmpty(PAGE_SIZE)) {
			throw new BizRuntimeException("페이지사이즈가 잘못입력되었습니다.");
		}
	}
	
	private Integer getIntPAGE_NUM() {
		int I_PAGE_NUM = 1;
		try {
			I_PAGE_NUM = Integer.parseInt(PAGE_NUM);
		} catch (NumberFormatException e) {
			throw new BizRuntimeException("페이지번호 잘못된 타입이 입력되었습니다.");
		}	
		return I_PAGE_NUM;
	}
	private Integer getIntPAGE_SIZE() {
		int I_PAGE_SIZE = 10;
		try {
			I_PAGE_SIZE= Integer.parseInt(PAGE_SIZE);
		} catch (NumberFormatException e) {
			throw new BizRuntimeException("페이지사이즈가 잘못된 타입이 입력되었습니다.");
		}
		return I_PAGE_SIZE;
	}
	
	public Pageable getPageable() {
		this.validPageData();
		int I_PAGE_NUM = this.getIntPAGE_NUM();
		int I_PAGE_SIZE = this.getIntPAGE_SIZE();
		return PageRequest.of(I_PAGE_NUM, I_PAGE_SIZE);
	}
}