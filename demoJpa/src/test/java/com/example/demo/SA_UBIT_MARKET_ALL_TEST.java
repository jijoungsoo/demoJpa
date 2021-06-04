package com.example.demo;



import java.util.ArrayList;
import java.util.HashMap;

import com.example.demo.sa.upbit.api.SA_UBIT_MARKET_ALL;

import org.junit.jupiter.api.Test;

/*@ContextConfiguration(loader = AnnotationConfigContextLoader.class) */ /*이거하면 임시 디비가 만들어지는 듯하다. --위에 실db쓰는건 h2 특성상 2개를 못여는 듯한다. 서버 모드 안됨 */		
public class SA_UBIT_MARKET_ALL_TEST {

	@Test
	void test() throws Exception {
		SA_UBIT_MARKET_ALL sa = new SA_UBIT_MARKET_ALL();
		ArrayList<HashMap<String,String>> tmp  = sa.getMarketAll();
		System.out.println(tmp.size());
	}
	

}



