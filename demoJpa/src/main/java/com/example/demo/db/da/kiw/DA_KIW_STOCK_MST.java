package com.example.demo.db.da.kiw;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import com.example.demo.db.domain.cm.CmMenu;
import com.example.demo.db.domain.cm.CmPgm;
import com.example.demo.db.domain.cm.QCmFavMenu;
import com.example.demo.db.domain.cm.QCmMenu;
import com.example.demo.db.repository.cm.CmPgmRepository;
import com.example.demo.db.repository.stck.StckMarcapRepository;
import com.example.demo.db.domain.cm.QCmPgm;
import com.example.demo.db.domain.cm.QCmUser;
import com.example.demo.db.domain.kiw.KiwStockMst;
import com.example.demo.db.domain.kiw.QKiwMarket;
import com.example.demo.db.domain.kiw.QKiwStockMst;
import com.example.demo.db.domain.marcap.QStckMarcap;
import com.example.demo.db.domain.marcap.StckMarcap;
import com.example.demo.exception.BizException;
import com.example.demo.utils.PjtUtil;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;


@Service
public class DA_KIW_STOCK_MST {
	
	@Autowired
	JPAQueryFactory qf;
	
	
	@Autowired
	StckMarcapRepository smR;
	
	public Page<KiwStockMst>  findKiwMst(
			String STOCK_CD
			,String STOCK_NM
			,Pageable p) {
		
			JPAQuery<KiwStockMst> c= qf.selectFrom(QKiwStockMst.kiwStockMst);
			if(!PjtUtil.isEmpty(STOCK_CD)) {
				c.where(QKiwStockMst.kiwStockMst.stockCd.contains(STOCK_CD));
			}
			
			if(!PjtUtil.isEmpty(STOCK_NM)) {
				c.where(QKiwStockMst.kiwStockMst.stockNm.contains(STOCK_NM));
			}
			c= c.orderBy(QKiwStockMst.kiwStockMst.OpenDt.desc());
			c= c.offset(p.getOffset()); // offset과
			c= c.limit(p.getPageSize()); // Limit 을 지정할 수 있고
			QueryResults<KiwStockMst> result= c.fetchResults();
	
				/*
				 * group by를 사용했는데 
				 * fetchResults를 사용했더니 에러가 발생되었다.
				 * 국내 인프런에 답변 달린게 있는데 queryDsl이 group by 를 쓰면 count를 못센다고 한다.
				 * https://www.inflearn.com/questions/23280
				 * 
				 * 그래서 이럴경우 다르게 구해야한다고 한다.
				 * countQuery의 쿼리를 별도로 구해야한다고 한다.
				 * return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetch().size());
				 * 
				 * 이거 대신에 ㅣ
				 * QueryResults<Tuple> result= c.fetchResults();
				 * */



		
		return new PageImpl<>(result.getResults(), p, result.getTotal());
	}
}
