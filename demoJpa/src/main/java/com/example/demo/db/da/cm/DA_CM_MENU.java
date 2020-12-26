package com.example.demo.db.da.cm;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.db.domain.cm.CmMenu;
import com.example.demo.db.repository.cm.CmMenuRepository;
import com.example.demo.db.domain.cm.QCmMenu;
import com.example.demo.exception.BizException;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Service
public class DA_CM_MENU {
	
	@Autowired
	JPAQueryFactory qf;
	
	@Autowired
	CmMenuRepository cmMenuR;
	
	public List<CmMenu> findMenu() {
		List<CmMenu> al =  qf
	                .selectFrom(QCmMenu.cmMenu)
	                .orderBy(QCmMenu.cmMenu.ord.asc())
	                .fetch();
		 return al;
	}
	

	public List<CmMenu> findMenuRoot() {
			List<CmMenu> al =  qf
		                .selectFrom(QCmMenu.cmMenu)
		                .where(QCmMenu.cmMenu.prntMenuCd.isNull())
		                .orderBy(QCmMenu.cmMenu.ord.asc())
		                .fetch();
		                
			 return al;
		
	}
	
	public List<CmMenu> findSubMenuRoot(String menuCd) {
		List<CmMenu> al =  qf
	                .selectFrom(QCmMenu.cmMenu)
	                .where(QCmMenu.cmMenu.prntMenuCd.eq(menuCd))
	                .orderBy(QCmMenu.cmMenu.ord.asc())
	                .fetch();
	                
		 return al;
	}
	
	/**
	 * @param PGM_ID  프로그램 ID
	 * @param PGM_NM  프로그램명
	 * @param RMK      비고
	 * @param CATEGORY  카테고리
	 * @param PGM_LINK  프로그램링크
	 */
	public void createMenu(
			Long MENU_NO
			,String MENU_CD
			,String MENU_NM
			,String PRNT_MENU_CD
			,String ORD
			,String PGM_ID
			,String MENU_KIND
			,String MENU_LVL
			,String MENU_PATH
			,String RMK
			) {
		cmMenuR.save(
				CmMenu.builder()
				.menuNo(MENU_NO)
				.menuCd(MENU_CD)
				.menuNm(MENU_NM)
				.prntMenuCd(PRNT_MENU_CD)
				.ord(ORD)
				.pgmId(PGM_ID)
				.menuKind(MENU_KIND)
				.menuLvl(MENU_LVL)
				.menuPath(MENU_PATH)
				.rmk(RMK)
				.updtDtm(new Date())
				.crtDtm(new Date()).build());
	}
	

	/**
	 * @param PGM_ID  프로그램 ID
	 * @param PGM_NM  프로그램명
	 * @param RMK      비고
	 * @param CATEGORY  카테고리
	 * @param PGM_LINK  프로그램링크
	 * @throws BizException 
	 */
	public void updateMenu(
			Long MENU_NO
			,String MENU_CD
			,String MENU_NM
			,String PRNT_MENU_CD
			,String ORD
			,String PGM_ID
			,String MENU_KIND
			,String MENU_LVL
			,String MENU_PATH
			,String RMK
			) throws BizException {
		
		Optional<CmMenu>  c =  cmMenuR.findById(MENU_NO);
		if(c==null) {
			throw new BizException("["+MENU_NO+"]["+MENU_CD+"] 메뉴코드가 존재하지 않습니다. ");
		}
		CmMenu tmp = c.get();
		tmp.setMenuCd(MENU_CD);
		tmp.setMenuNm(MENU_NM);
		tmp.setPrntMenuCd(PRNT_MENU_CD);
		tmp.setOrd(ORD);
		tmp.setPgmId(PGM_ID);
		tmp.setMenuKind(MENU_KIND);
		tmp.setMenuLvl(MENU_LVL);
		tmp.setMenuPath(MENU_PATH);
		tmp.setRmk(RMK);
		tmp.setUpdtDtm(new Date());
		cmMenuR.save(tmp);
	}
	
	public void rmMenu(
			Long MENU_NO
			) {
		cmMenuR.deleteById(MENU_NO);
	}

	
}
