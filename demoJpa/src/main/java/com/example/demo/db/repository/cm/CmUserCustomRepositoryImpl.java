package com.example.demo.db.repository.cm;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.db.domain.cm.CmUser;
import com.example.demo.db.domain.cm.QCmUser;
import com.querydsl.jpa.impl.JPAQuery;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CmUserCustomRepositoryImpl implements CmUserCustomRepository {
	@Autowired
	EntityManager em;
	
	/*
	private final JPAQueryFactory queryFactory;
	 */
	
	@Override
	public CmUser findByUserId(String userId) {
		/*
		queryFactory.selectFrom(QCmUser.cmUser);
		
		 return queryFactory
	                .selectFrom(QCmUser.cmUser)
	                .where(QCmUser.cmUser.userId.eq(userId))
	                .fetchOne();
		 */
		 return new JPAQuery<CmUser>(em)
				 .select(QCmUser.cmUser)
				 .from(QCmUser.cmUser)
				 .where(QCmUser.cmUser.userId.eq(userId))
				 .fetchOne();
	}


}


