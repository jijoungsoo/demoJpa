package com.example.demo.db.da.cm;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface DA_CM_FAV_MENU_MAPPER {
	public List<Map> findFavMenuByUserNo(@Param("USER_NO") Long USER_NO) throws Exception;

}
