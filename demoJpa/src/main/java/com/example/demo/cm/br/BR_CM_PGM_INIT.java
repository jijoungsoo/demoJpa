package com.example.demo.cm.br;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.cm.da.DA_CM_PGM_INIT;
@Service
public class BR_CM_PGM_INIT {
	
	@Autowired
	DA_CM_PGM_INIT p;
	
	public void Init() {
		p.insertGrpCd();
		p.insertCd(); 
		p.insertMenu();
		p.insertPgm();
		p.insertDomain();
	}

}
