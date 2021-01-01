package com.example.demo.db.da.cm;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.db.domain.cm.CmFavMenu;
import com.example.demo.db.domain.cm.CmMenu;
import com.example.demo.db.domain.cm.CmUser;
import com.example.demo.db.repository.cm.CmFavMneuRepository;
import com.example.demo.utils.PjtUtil;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.example.demo.db.domain.cm.QCmFavMenu;
import com.example.demo.db.domain.cm.QCmMenu;
import com.example.demo.db.domain.cm.QCmPgm;
import com.example.demo.db.domain.cm.QCmUser;

@Service
public class DA_CM_FAV_MENU {

	@Autowired
	JPAQueryFactory qf;
	

	
	@Autowired
	CmFavMneuRepository cmFavMenuR;

	public ArrayList<HashMap<String, Object>> findFavMenu() {
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
		ArrayList<HashMap<String, Object>> OUT_DATA = new ArrayList<HashMap<String,Object>>();
		 for (Tuple row : tmp) {
			 HashMap<String, Object>  OUT_DATA_ROW = new HashMap<String, Object>();
			 OUT_DATA_ROW.put("FAV_NO", row.get(QCmFavMenu.cmFavMenu.favNo));
			 OUT_DATA_ROW.put("MENU_NO", row.get(QCmFavMenu.cmFavMenu.menuNo));
			 OUT_DATA_ROW.put("USER_NO", row.get(QCmFavMenu.cmFavMenu.userNo));
			 OUT_DATA_ROW.put("USER_NM", row.get(QCmUser.cmUser.userNm));
			 OUT_DATA_ROW.put("USER_ID", row.get(QCmUser.cmUser.userId));
			 OUT_DATA_ROW.put("MENU_NM", row.get(QCmMenu.cmMenu.menuNm));
			 OUT_DATA_ROW.put("PGM_ID", row.get(QCmMenu.cmMenu.pgmId));
			 OUT_DATA_ROW.put("PGM_NM", row.get(QCmPgm.cmPgm.pgmNm));
			 OUT_DATA_ROW.put("PGM_LINK", row.get(QCmPgm.cmPgm.pgmLink));
			 OUT_DATA_ROW.put("DIR_LINK", row.get(QCmPgm.cmPgm.dirLink));
			 OUT_DATA_ROW.put("CRT_DTM", PjtUtil.getYyyy_MM_dd_HHMMSS(row.get(QCmFavMenu.cmFavMenu.crtDtm)));
			 OUT_DATA_ROW.put("UPDT_DTM", PjtUtil.getYyyy_MM_dd_HHMMSS(row.get(QCmFavMenu.cmFavMenu.updtDtm)));
			 OUT_DATA.add(OUT_DATA_ROW);
		 }
		return OUT_DATA;
	}

	public ArrayList<HashMap<String, Object>> findFavMenuByUserNo(long USER_NO) {
		List<com.querydsl.core.Tuple> tmp =qf.from(QCmFavMenu.cmFavMenu)
		.leftJoin(QCmMenu.cmMenu)
		.on(QCmFavMenu.cmFavMenu.menuNo.eq(QCmMenu.cmMenu.menuNo))
		.leftJoin(QCmPgm.cmPgm)
		.on(QCmPgm.cmPgm.pgmId.eq(QCmMenu.cmMenu.pgmId))
		.leftJoin(QCmUser.cmUser)
		.on(QCmUser.cmUser.userNo.eq(QCmFavMenu.cmFavMenu.userNo))
		.where(QCmFavMenu.cmFavMenu.userNo.eq(USER_NO))
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
		ArrayList<HashMap<String, Object>> OUT_DATA = new ArrayList<HashMap<String,Object>>();
		 for (Tuple row : tmp) {
			 HashMap<String, Object>  OUT_DATA_ROW = new HashMap<String, Object>();
			 OUT_DATA_ROW.put("FAV_NO", row.get(QCmFavMenu.cmFavMenu.favNo));
			 OUT_DATA_ROW.put("MENU_NO", row.get(QCmFavMenu.cmFavMenu.menuNo));
			 OUT_DATA_ROW.put("USER_NO", row.get(QCmFavMenu.cmFavMenu.userNo));
			 OUT_DATA_ROW.put("USER_NM", row.get(QCmUser.cmUser.userNm));
			 OUT_DATA_ROW.put("USER_ID", row.get(QCmUser.cmUser.userId));
			 OUT_DATA_ROW.put("MENU_NM", row.get(QCmMenu.cmMenu.menuNm));
			 OUT_DATA_ROW.put("PGM_ID", row.get(QCmMenu.cmMenu.pgmId));
			 OUT_DATA_ROW.put("PGM_NM", row.get(QCmPgm.cmPgm.pgmNm));
			 OUT_DATA_ROW.put("PGM_LINK", row.get(QCmPgm.cmPgm.pgmLink));
			 OUT_DATA_ROW.put("DIR_LINK", row.get(QCmPgm.cmPgm.dirLink));
			 OUT_DATA_ROW.put("CRT_DTM", PjtUtil.getYyyy_MM_dd_HHMMSS(row.get(QCmFavMenu.cmFavMenu.crtDtm)));
			 OUT_DATA_ROW.put("UPDT_DTM", PjtUtil.getYyyy_MM_dd_HHMMSS(row.get(QCmFavMenu.cmFavMenu.updtDtm)));
			 OUT_DATA.add(OUT_DATA_ROW);
		 }
		return OUT_DATA;
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
