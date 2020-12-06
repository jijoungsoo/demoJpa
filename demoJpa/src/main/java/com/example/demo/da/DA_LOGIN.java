package com.example.demo.da;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.da.service.CmUserRepository;
import com.example.demo.domain.CmUser;
import com.example.demo.domain.QCmUser;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Service
public class DA_LOGIN {
	
	@Autowired
	JPAQueryFactory qf;
		
	public CmUser findByUserId(String userId) {
		 CmUser c =  qf
	                .selectFrom(QCmUser.cmUser)
	                .where(QCmUser.cmUser.userId.eq(userId))
	                .fetchOne();
		 return c;
	}
}
