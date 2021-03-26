package com.example.demo.db.da.mig_av;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.Tuple;

import com.example.demo.db.domain.mig_av.MigAvGen;
import com.example.demo.db.repository.mig_av.MigAvGenRepository;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DA_MIG_AV_GEN {
	
	@Autowired
	JPAQueryFactory qf;
		
	@Autowired
	MigAvGenRepository migAvGenR;

	public List<Tuple> findGenGroupByActorIdx(Long L_ACTOR_IDX){
		/*
		SubQueryExpression<Tuple> sq1 =
		qf.query().from(employee).select(employee.id.max());
        SubQueryExpression<Tuple> sq2 = query().from(employee).select(employee.id.min());
        List<Tuple> list = query().unionAll(sq1, sq2).fetch();
		qf.select(QMigAvGen.)
		return null;
		*/
		return null;
		

	}
	
	public void crtMigAvGen(Long L_CATE_NO
				,Long L_MENU_NO
				,String CATE_NM
				,String CATE_NM_JP
	) {
		
		migAvGenR.save(
				MigAvGen.builder()
				.menuNo(L_MENU_NO)
				.cateNo(L_CATE_NO)
				.cateNm(CATE_NM)
				.cateNmJp(CATE_NM_JP)
				.crtDtm(new Date()).build());
	}
	
	public Optional<MigAvGen> findById(Long L_CATE_NO){
		return migAvGenR.findById(L_CATE_NO);
	}

	public void updtMigAvGen(Long L_CATE_NO
		,Long L_MENU_NO
		,String CATE_NM
		,String CATE_NM_JP
	) {
		Optional<MigAvGen> c =migAvGenR.findById(L_CATE_NO);
		if(c.isPresent()){
			MigAvGen m =c.get();
			m.setMenuNo(L_MENU_NO);
			m.setCateNm(CATE_NM);
			m.setCateNmJp(CATE_NM_JP);
			migAvGenR.save(m);
		}
	}
}


