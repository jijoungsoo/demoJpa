package com.example.demo.db.da.stck;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DA_STCK_BUY_MAPPER {
	 public int reSyncStBuy( @Param("BUY_SEQ") long l_BUY_SEQ,
			 @Param("UPDT_USR_NO") long UPDT_USR_NO			 
			 ) throws Exception;
	 

}