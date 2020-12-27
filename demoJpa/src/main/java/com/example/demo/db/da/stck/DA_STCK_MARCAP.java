package com.example.demo.db.da.stck;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.db.domain.cm.CmPgm;
import com.example.demo.db.repository.cm.CmPgmRepository;
import com.example.demo.db.repository.stck.StckMarcapRepository;
import com.example.demo.db.domain.cm.QCmPgm;
import com.example.demo.db.domain.marcap.QStckMarcap;
import com.example.demo.db.domain.marcap.StckMarcap;
import com.example.demo.exception.BizException;
import com.example.demo.utils.PjtUtil;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Service
public class DA_STCK_MARCAP {
	
	@Autowired
	JPAQueryFactory qf;
	
	
	@Autowired
	StckMarcapRepository smR;
	
	public Page<StckMarcap> findStckMarcap(String STOCK_DT_ST , String STOCK_DT_ED
			,String STOCK_CD
			,Pageable p) {
		JPAQuery<StckMarcap> c= qf.selectFrom(QStckMarcap.stckMarcap)
	                			  .where(QStckMarcap.stckMarcap.stockDt.between(STOCK_DT_ST, STOCK_DT_ED));
		if(!PjtUtil.isEmpty(STOCK_CD)) {
			c.where(QStckMarcap.stckMarcap.stockCd.eq(STOCK_CD));
		}
		c= c.offset(p.getOffset()); // offset과
		c= c.limit(p.getPageSize()); // Limit 을 지정할 수 있고
		c= c.orderBy(QStckMarcap.stckMarcap.stockDt.asc());
		QueryResults<StckMarcap> result= c.fetchResults();
		/*
		 * QueryResults<StckMarcap>
		 * JPAQuery<StckMarcap>
		 * https://joont92.github.io/jpa/QueryDSL/
		 * 여기서 보면 전체 조회수를 알아야할때 사용하는게 fetchResults라 말하고 있다.
		 * */
		 return new PageImpl<>(result.getResults(), p, result.getTotal());
	}
}
