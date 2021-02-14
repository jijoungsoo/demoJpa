package com.example.demo.db.da.cm;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DA_CM_MENU_ROLE_CD_MAPPER {
	public List<Map> findMenuRoot(@Param("ROLE_CD") String ROLE_CD) throws Exception;

	public List<Map> findSubMenuRoot(@Param("ROLE_CD") String ROLE_CD,@Param("MENU_CD") String MENU_CD) throws Exception;


	public int reSyncMenuRoleCdParent( @Param("ROLE_CD") String ROLE_CD,
	@Param("UPDT_USR_NO") long UPDT_USR_NO			 
	) throws Exception;


	public List<Map> findMenuRoleCdParent( @Param("ROLE_CD") String ROLE_CD) throws Exception;
	

	public List<Map> findMainMenuRootByUserNo(@Param("USER_NO") Long USER_NO) throws Exception;

	public List<Map> findSubMenuByUserNo(@Param("USER_NO") Long USER_NO, 
	@Param("MENU_CD")  String MENU_CD)  throws Exception;
}