package com.example.demo.db.da.mig_av;

import java.util.Date;
import java.util.Optional;

import com.example.demo.db.domain.mig_av.MigAvGen;
import com.example.demo.db.domain.mig_av.MigAvMenu;
import com.example.demo.db.repository.mig_av.MigAvMenuRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DA_MIG_AV_MENU {
	
	@Autowired
	JPAQueryFactory qf;
		
	@Autowired
	MigAvMenuRepository migAvMenuR;
	
	public void crtMigAvMenu(Long L_MENU_NO
				,String MENU_NM
	) {
		
		migAvMenuR.save(
				MigAvMenu.builder()
				.menuNo(L_MENU_NO)
				.menuNm(MENU_NM)
				.crtDtm(new Date()).build());
	}
	
	public Optional<MigAvMenu> findById(Long L_MENU_NO){
		return migAvMenuR.findById(L_MENU_NO);
	}

	public void updtMigAvMenu(Long L_MENU_NO
		,String MENU_NM
	) {
		Optional<MigAvMenu> c =migAvMenuR.findById(L_MENU_NO);
		if(c.isPresent()){
			MigAvMenu m =c.get();
			m.setMenuNo(L_MENU_NO);
			m.setMenuNm(MENU_NM);
			migAvMenuR.save(m);
		}
	}
}


