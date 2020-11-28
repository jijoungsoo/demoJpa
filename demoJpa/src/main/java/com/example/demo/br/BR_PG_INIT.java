package com.example.demo.br;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.da.DA_PG_INIT;
@Service
public class BR_PG_INIT {
	
	@Autowired
	DA_PG_INIT p;
	
	public void Init() {
		p.init();
	}
}
