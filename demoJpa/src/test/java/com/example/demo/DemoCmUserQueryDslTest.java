package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.da.DA_PGM_INIT;
import com.example.demo.da.service.CmUserCustomRepository;
import com.example.demo.da.service.CmUserRepository;
import com.example.demo.da.util.QPredicates;
import com.example.demo.domain.CmUser;
import com.example.demo.domain.QCmUser;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

@SpringBootTest(properties = "classpath:/application.yml")  /*https://velog.io/@hellozin/Spring-Boot-Test에서-Yaml-프로퍼티-적용하기*/
@Transactional
@Rollback(false)  /*롤백하지 않게 하자. */
/*@ContextConfiguration(loader = AnnotationConfigContextLoader.class) */ /*이거하면 임시 디비가 만들어지는 듯하다. --위에 실db쓰는건 h2 특성상 2개를 못여는 듯한다. 서버 모드 안됨 */		
class DemoCmUserQueryDslTest {
	
	@Autowired
	EntityManager em;

	@Autowired
	JPAQueryFactory qf;
	
	@Autowired
	CmUserRepository cmUserR;

	@Test
	void JPAQueryTest() {
		/* 유투브보고 querydsl 설정했다.
		https://www.youtube.com/watch?v=hIw4EuhF9o8
		*/
		 String userId="admin";
		 CmUser c = new JPAQuery<CmUser>(em)
				 .select(QCmUser.cmUser)
				 .from(QCmUser.cmUser)
				 .where(QCmUser.cmUser.userId.eq(userId))
				 .fetchOne();
		 
		 System.out.println(c);
		 assertNotNull(c);
		 
	}
	
	@Test
	void JPAQueryFactoryTest() {
		/*https://jojoldu.tistory.com/372 원본은   bean으로 설정하는거다.*/
		 String userId="admin";
		qf.selectFrom(QCmUser.cmUser);
		
		 CmUser c =  qf
	                .selectFrom(QCmUser.cmUser)
	                .where(QCmUser.cmUser.userId.eq(userId))
	                .fetchOne();
		 System.out.println(c);
		 assertNotNull(c);
		 
	}
	
	@Test
	void loginBrTest() {
		/*https://jojoldu.tistory.com/372 원본은   bean으로 설정하는거다.*/
		/*
		 * https://www.youtube.com/watch?v=gBK2lar-Z7E
		 * https://www.youtube.com/watch?v=KMWrpI8an7o*/
		 CmUser filter = CmUser.builder()
				 .userId("admin")
				 .userPwd("admin")
				 .build();
		 
		 /*이렇게 하면  repository에 모든걸 만들지 않아도 되어서 편하네.
		  * 고민 해볼만하다. repository를 da 전의 layer 계층으로 보고 
		  *  da에서 predicate를  업무 단위별로 만들면 되겠다.
		  *  
		  *  da는 호출마다 만들고
		  *  그안에서 predicate를 쓰면 좋겠다.
		  *  그래도 처음 처럼 select 쓰고.
		  *  if문으로 막 조건을 붙여주는게 가독성이 더 좋을 것 같다.
		  * */
		 Predicate p = QPredicates.builder()
				 .add(filter.getUserId(), QCmUser.cmUser.userId::containsIgnoreCase)
				 .buildAnd();
		 Iterable<CmUser> r = cmUserR.findAll(p);
		 System.out.println(r);
		 assertTrue(r.iterator().hasNext());
	}
	
	@Test
	void testQuerydslPredicates() {
		
		
		BooleanExpression  p = QCmUser.cmUser.userId.eq("admin");
		Page<CmUser> al = cmUserR.findAll(p,Pageable.unpaged());
	}

}
