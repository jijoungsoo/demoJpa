package com.example.demo.db.da.cm;

import java.util.Date;
import java.util.List;

import com.example.demo.db.domain.cm.CmFavMenu;
import com.example.demo.db.domain.cm.QCmFavMenu;
import com.example.demo.db.domain.cm.QCmMenu;
import com.example.demo.db.domain.cm.QCmPgm;
import com.example.demo.db.domain.cm.QCmUser;
import com.example.demo.db.repository.cm.CmFavMneuRepository;
import com.example.demo.utils.PjtUtil;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DA_CM_FAV_MENU {

	@Autowired
    PjtUtil pjtU;

	@Autowired
	JPAQueryFactory qf;
	

	
	@Autowired
	CmFavMneuRepository cmFavMenuR;

	public List<com.querydsl.core.Tuple> findFavMenu() {
		List<com.querydsl.core.Tuple> tmp =qf.from(QCmFavMenu.cmFavMenu)
		.leftJoin(QCmMenu.cmMenu)
		.on(QCmFavMenu.cmFavMenu.menuNo.eq(QCmMenu.cmMenu.menuNo))
		.leftJoin(QCmPgm.cmPgm)
		.on(QCmPgm.cmPgm.pgmId.eq(QCmMenu.cmMenu.pgmId))
		.leftJoin(QCmUser.cmUser)
		.on(QCmUser.cmUser.userNo.eq(QCmFavMenu.cmFavMenu.userNo))
		.select(QCmFavMenu.cmFavMenu.favNo
				,QCmFavMenu.cmFavMenu.menuNo
				,QCmFavMenu.cmFavMenu.userNo
				,QCmUser.cmUser.userNm
				,QCmUser.cmUser.userId
				,QCmMenu.cmMenu.menuNm
				,QCmMenu.cmMenu.pgmId
				,QCmPgm.cmPgm.pgmNm
				,QCmPgm.cmPgm.pgmLink
				,QCmPgm.cmPgm.dirLink
				,QCmFavMenu.cmFavMenu.crtDtm
				,QCmFavMenu.cmFavMenu.updtDtm
				)
		.fetch();
	
		return tmp;
	}
	
	public CmFavMenu findFavMenuByMenuCdAndUserNo(long USER_NO,long MENU_NO){
		CmFavMenu c =  qf
	                .selectFrom(QCmFavMenu.cmFavMenu)
	                .where(QCmFavMenu.cmFavMenu.userNo.eq(USER_NO))
	                .where(QCmFavMenu.cmFavMenu.menuNo.eq(MENU_NO))
	                .fetchOne();
		 return c;
	}
	
	/**
	 * @param PGM_ID  프로그램 ID
	 * @param PGM_NM  프로그램명
	 * @param RMK      비고
	 * @param CATEGORY  카테고리
	 * @param PGM_LINK  프로그램링크
	 */
	public void createFavMenu(
			Long FAV_NO
			,Long MENU_NO
			,Long USER_NO
			) {
		
		cmFavMenuR.save(
				CmFavMenu.builder()
				.menuNo(MENU_NO)
				.favNo(FAV_NO)
				.userNo(USER_NO)
				.updtDtm(new Date())
				.crtDtm(new Date()).build());
	}
		
	public void rmFavMenu(
			Long FAV_NO
			) {
		cmFavMenuR.deleteById(FAV_NO);
	}

}
