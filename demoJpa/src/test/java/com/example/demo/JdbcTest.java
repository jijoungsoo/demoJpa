package com.example.demo;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.example.demo.br.kiw.BR_KIW_STOCK_MST;
import com.example.demo.ctrl.IN_DS;
import com.example.demo.ctrl.OUT_DS;
import com.example.demo.db.da.kiw.DA_KIW_STOCK_MST_MAPPER;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@SpringBootTest
@ContextConfiguration(
		  loader = AnnotationConfigContextLoader.class)		
class JdbcTest {
	   @Autowired
	    private BR_KIW_STOCK_MST daksm;

	  @Test
	    public void run() throws Exception {
		  ArrayList<HashMap<String,Object>> pg_al = new ArrayList<HashMap<String,Object>>();
		  ArrayList<HashMap<String,Object>> in_data = new ArrayList<HashMap<String,Object>>();
		  
		  IN_DS inDs = new IN_DS();
		  HashMap<String,Object> page_one = new HashMap<String,Object>(); 
		  page_one.put("PAGE_SIZE", "10");
		  page_one.put("PAGE_NUM", "0");
		  pg_al.add(page_one);
		  inDs.put("PAGE_DATA", pg_al);
		  
		  HashMap<String,Object> in_data_one = new HashMap<String,Object>();
		  in_data.add(in_data_one);
		  inDs.put("IN_DATA", in_data);	  
		  OUT_DS outDs = daksm.findKiwMst(inDs);
		   System.out.println(outDs);

	    }

}
