package com.example.demo.db.da.cm;

import java.util.Date;
import java.util.Optional;

import com.example.demo.db.domain.cm.CmMenuNoRoleCd;
import com.example.demo.db.domain.cm.CmMenuRoleCd;
import com.example.demo.db.domain.cm.QCmMenuRoleCd;
import com.example.demo.db.repository.cm.CmMenuRoleCdRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DA_CM_MENU_ROLE_CD {
	
	@Autowired
	JPAQueryFactory qf;
	
	@Autowired
	CmMenuRoleCdRepository cmMenuRoleR;
	public long rmCmMenuRoleCdAll(	
			String ROLE_CD) {
			return	qf.delete(QCmMenuRoleCd.cmMenuRoleCd)
				.where(QCmMenuRoleCd.cmMenuRoleCd.roleCd.eq(ROLE_CD))
				.execute();
	}
	public Optional<CmMenuRoleCd> findCmMenuRoleCd(
			String ROLE_CD
			,Long L_MENU_NO
	) {
		CmMenuNoRoleCd cmMenuNoRoleCd = new CmMenuNoRoleCd();
				cmMenuNoRoleCd.setRoleCd(ROLE_CD);
				cmMenuNoRoleCd.setMenuNo(L_MENU_NO);
				Optional<CmMenuRoleCd>  c=	cmMenuRoleR.findById(cmMenuNoRoleCd);
				return c;

	}
	public void createCmMenuRoleCd(
			String ROLE_CD
			,Long L_MENU_NO
			,Long L_LSESSION_USER_NO
	) {

		cmMenuRoleR.save(
			CmMenuRoleCd.builder()
			.roleCd(ROLE_CD)
			.menuNo(L_MENU_NO)
			.crtUsrNo(L_LSESSION_USER_NO)
			.crtDtm(new Date()).build());
	}
}
