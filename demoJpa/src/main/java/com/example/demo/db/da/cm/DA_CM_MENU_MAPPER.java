package com.example.demo.db.da.cm;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DA_CM_MENU_MAPPER {
	public List<Map> findMenu(@Param("PRNT_MENU_CD") String PRNT_MENU_CD
	,@Param("MENU_KIND") String MENU_KIND
	) throws Exception;
}