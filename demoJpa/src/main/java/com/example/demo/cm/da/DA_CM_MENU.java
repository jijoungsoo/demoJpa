package com.example.demo.cm.da;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.da.service.CmMenuRepository;
import com.example.demo.domain.CmMenu;
import com.example.demo.domain.QCmMenu;
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
	                .orderBy(QCmMenu.cmMenu.menuCd.asc())
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
			String MENU_CD
			,String MENU_NM
			,String PRNT_MENU_CD
			,String FST_ORD
			,String SED_ORD
			,String PGM_ID
			,String MENU_KIND
			,String RMK
			) {
		cmMenuR.save(
				CmMenu.builder()
				.menuCd(MENU_CD)
				.menuNm(MENU_NM)
				.prntMenuCd(PRNT_MENU_CD)
				.fstOrd(FST_ORD)
				.sedOrd(SED_ORD)
				.pgmId(PGM_ID)
				.menuKind(MENU_KIND)
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
			String MENU_CD
			,String MENU_NM
			,String PRNT_MENU_CD
			,String FST_ORD
			,String SED_ORD
			,String PGM_ID
			,String MENU_KIND
			,String RMK
			) throws BizException {
		
		Optional<CmMenu>  c =  cmMenuR.findById(MENU_CD);
		if(c==null) {
			throw new BizException("["+MENU_CD+"] 메뉴코드가 존재하지 않습니다. ");
		}
		CmMenu tmp = c.get();
		tmp.setMenuNm(MENU_NM);
		tmp.setPrntMenuCd(PRNT_MENU_CD);
		tmp.setFstOrd(FST_ORD);
		tmp.setSedOrd(SED_ORD);
		tmp.setPgmId(PGM_ID);
		tmp.setMenuKind(MENU_KIND);
		tmp.setRmk(RMK);
		tmp.setUpdtDtm(new Date());
		cmMenuR.save(tmp);
	}
	
	public void rmMenu(
			String MENU_CD
			) {
		cmMenuR.deleteById(MENU_CD);
	}

	public List<CmMenu> findMenuRoot() {
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
	
}
