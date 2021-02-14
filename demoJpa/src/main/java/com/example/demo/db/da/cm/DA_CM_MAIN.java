package com.example.demo.db.da.cm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.db.domain.cm.CmMenu;
import com.example.demo.db.domain.cm.CmPgm;
import com.example.demo.db.domain.cm.QCmMenu;
import com.example.demo.db.domain.cm.QCmMenuRoleCd;
import com.example.demo.db.domain.cm.QCmPgm;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Service
public class DA_CM_MAIN {
	
	@Autowired
	JPAQueryFactory qf;
		
	public List<CmMenu> findMainMenu() {
		List<CmMenu> al =  qf
	                .selectFrom(QCmMenu.cmMenu)
	                .where(QCmMenu.cmMenu.prntMenuCd.isNull())
	                .orderBy(QCmMenu.cmMenu.ord.asc())
	                .fetch();
	                
		 return al;
	}
	
	public List<CmMenu> findSubMenu(String menuCd) {
		List<CmMenu> al =  qf
	                .selectFrom(QCmMenu.cmMenu)
	                .where(QCmMenu.cmMenu.prntMenuCd.eq(menuCd))
	                .orderBy(QCmMenu.cmMenu.ord.asc())
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

	public List<CmPgm> findPgmByPgmId(String PGM_ID) {
		List<CmPgm> al =  qf
                .selectFrom(QCmPgm.cmPgm)
                .where(QCmPgm.cmPgm.pgmId.eq(PGM_ID))
                .orderBy(QCmPgm.cmPgm.pgmId.asc())
                .fetch();
		return al;
	}
}
