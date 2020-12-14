package com.example.demo.kiw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.CmUser;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Service
public class DA_ST {
	
	@Autowired
	JPAQueryFactory qf;
		
	public CmUser kiw2100_findThemeCd() {
		 return null;
	}
}
