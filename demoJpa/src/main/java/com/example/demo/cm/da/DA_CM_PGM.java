package com.example.demo.cm.da;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.CmPgm;
import com.example.demo.domain.QCmPgm;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Service
public class DA_CM_PGM {
	
	@Autowired
	JPAQueryFactory qf;
	
	public List<CmPgm> findPgm() {
		List<CmPgm> al =  qf
	                .selectFrom(QCmPgm.cmPgm)
	                .orderBy(QCmPgm.cmPgm.pgmId.asc())
	                .fetch();
		 return al;
	}
}
