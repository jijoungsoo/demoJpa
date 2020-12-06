package com.example.demo.da;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.da.service.CmUserRepository;
import com.example.demo.domain.CmMenu;
import com.example.demo.domain.CmPgm;
import com.example.demo.domain.CmUser;
import com.example.demo.domain.QCmMenu;
import com.example.demo.domain.QCmPgm;
import com.example.demo.domain.QCmUser;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Service
public class DA_MAIN {
	
	@Autowired
	JPAQueryFactory qf;
		
	public List<CmMenu> findMainMenu() {
		List<CmMenu> al =  qf
	                .selectFrom(QCmMenu.cmMenu)
	                .where(QCmMenu.cmMenu.prntMenuCd.isNull())
	                .orderBy(QCmMenu.cmMenu.fstOrd.asc())
	                .fetch();
	                
		 return al;
	}
	
	public List<CmMenu> findSubMenu(String menuCd) {
		List<CmMenu> al =  qf
	                .selectFrom(QCmMenu.cmMenu)
	                .where(QCmMenu.cmMenu.prntMenuCd.eq(menuCd))
	                .orderBy(QCmMenu.cmMenu.sedOrd.asc())
	                .fetch();
	                
		 return al;
	}
	
	public List<CmPgm> findMainPgm() {
		List<CmPgm> al =  qf
	                .selectFrom(QCmPgm.cmPgm)
	                .orderBy(QCmPgm.cmPgm.pgmId.asc())
	                .fetch();
	                
		 return al;
	}
}
