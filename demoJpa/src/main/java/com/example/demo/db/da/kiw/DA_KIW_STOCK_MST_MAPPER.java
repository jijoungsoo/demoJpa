package com.example.demo.db.da.kiw;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DA_KIW_STOCK_MST_MAPPER {
	 public List<Map> findKiwMst( @Param("MARKET_CD")String MARKET_CD , @Param("STOCK_CD")String STOCK_CD ,@Param("STOCK_NM")String STOCK_NM,@Param("PAGE_NUM")int PAGE_NUM,@Param("PAGE_SIZE")int PAGE_SIZE ) throws Exception;
	
	 
	 public int findKiwMstTotalCnt( @Param("MARKET_CD")String MARKET_CD ,@Param("STOCK_CD") String STOCK_CD ,@Param("STOCK_NM") String STOCK_NM ) throws Exception;
	 

}