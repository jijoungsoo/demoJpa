package com.example.demo;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.da.service.CmGrpCdRepository;
import com.example.demo.domain.CmGrpCd;

@SpringBootTest(properties = "classpath:/application.yml")  /*https://velog.io/@hellozin/Spring-Boot-Test에서-Yaml-프로퍼티-적용하기*/
@Transactional
@Rollback(false)  /*롤백하지 않게 하자. */
/*@ContextConfiguration(loader = AnnotationConfigContextLoader.class) */ /*이거하면 임시 디비가 만들어지는 듯하다. --위에 실db쓰는건 h2 특성상 2개를 못여는 듯한다. 서버 모드 안됨 */		
class DemoCmCdServiceTest {
	
	  @Autowired
	private CmGrpCdRepository r;

	@Test
	void contextLoads() {
		r.save( CmGrpCd.builder().grpCd("HOGA_GUBUN").grpNm("거래구분(호가구분)").useYn("Y").ord(2).updtDtm(new Date()).crtDtm(new Date()).build());
		r.save( CmGrpCd.builder().grpCd("ORDER_TYPE").grpNm("주문유형").useYn("Y").ord(1).updtDtm(new Date()).crtDtm(new Date()).build());
		r.save( CmGrpCd.builder().grpCd("STOCK_CD_TYPE").grpNm("주식코드 접두어(주문,잔고-체결)").useYn("Y").ord(3).updtDtm(new Date()).crtDtm(new Date()).build());
		r.save( CmGrpCd.builder().grpCd("MARKET").grpNm("장구분(마켓)").useYn("Y").ord(4).updtDtm(new Date()).crtDtm(new Date()).build());
		
	}

}
