package com.example.demo;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@Transactional
@SpringBootTest
@ContextConfiguration(
		  loader = AnnotationConfigContextLoader.class)		
class DemoCmCdTest {
	
	@Autowired
	EntityManagerFactory emf;
	/*
	@Autowired
	EntityManager em;
	*/

	@Test
	void contextLoads() {	/*
		EntityManager em = emf.createEntityManager();
		EntityTransaction tr = em.getTransaction();
	
		tr.begin();
		CmGrpCd t =  CmGrpCd.builder()
				.grpCd("HOGA_GUBUN").grpNm("거래구분(호가구분)").useYn("Y").ord(2).updtDtm(new Date()).crtDtm(new Date()).build();
		
		CmCd c= CmCd.builder()
				.cd("00").cdNm("(00)지정가").useYn("Y").ord(1).updtDtm(new Date()).crtDtm(new Date()).build();
		System.out.println("aaaaa");
		System.out.println(t);
		System.out.println("aaaaa");
		c.setCmGrpCd(t);
		em.persist(t);
	
		t =  CmCdGrp.builder()
					.grpCd("ORDER_TYPE").grpCdNm("주문유형").useYn("Y").ord(1).updtDtm(new Date()).crtDtm(new Date()).build();
		em.persist(t);
		 t =  CmCdGrp.builder()
					.grpCd("STOCK_CD_TYPE").grpCdNm("주식코드 접두어(주문,잔고-체결)").useYn("Y").ord(3).updtDtm(new Date()).crtDtm(new Date()).build();
		em.persist(t);
		 t =  CmCdGrp.builder()
					.grpCd("MARKET").grpCdNm("장구분(마켓))").useYn("Y").ord(4).updtDtm(new Date()).crtDtm(new Date()).build();
	

		
		em.persist(t);
		tr.commit();	*/
		
	}

}
