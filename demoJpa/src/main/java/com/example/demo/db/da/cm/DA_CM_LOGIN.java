package com.example.demo.db.da.cm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.example.demo.db.domain.cm.CmUser;
import com.example.demo.db.domain.cm.QCmUser;
import com.example.demo.db.domain.cm.QCmUserRoleCd;
import com.querydsl.core.Tuple;
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

	public List<String> findAuthByUserNo(Long L_USER_NO) {
		List<String>  c = qf.select(QCmUserRoleCd.cmUserRoleCd.roleCd)
		.from(QCmUserRoleCd.cmUserRoleCd)
		.where(QCmUserRoleCd.cmUserRoleCd.useYn.eq("Y"))
		.where(QCmUserRoleCd.cmUserRoleCd.refCmRoleCd.useYn.eq("Y"))
		.where(QCmUserRoleCd.cmUserRoleCd.userNo.eq(L_USER_NO))
		.fetch();
		return c;
   }
}
