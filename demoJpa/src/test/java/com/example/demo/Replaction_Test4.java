package com.example.demo;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import org.junit.jupiter.api.Test;

import com.example.demo.br.cm.cm_login.BR_CM_LOGIN_LOAD_USER_BY_USER_NAME;
import com.example.demo.sa.mig.mig_stck.SA_MIG_STCK_SYNC;


public class Replaction_Test4 {
		
	@Test
	void restTest2() throws Exception {
		SA_MIG_STCK_SYNC t= new SA_MIG_STCK_SYNC();
		t.run();
	}
}



