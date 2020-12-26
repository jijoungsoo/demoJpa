package com.example.demo.db.da.cm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.db.domain.cm.CmUser;
import com.example.demo.db.domain.cm.QCmUser;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Service
public class DA_CM_LOGIN {
	
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
