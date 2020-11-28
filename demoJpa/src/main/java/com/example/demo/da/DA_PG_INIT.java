package com.example.demo.da;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.da.service.CmCdRepository;
import com.example.demo.da.service.CmGrpCdRepository;
import com.example.demo.domain.CmCd;

@Service
public class DA_PG_INIT {
	@Autowired
	private CmGrpCdRepository cmGrpCdR;
	
	@Autowired
	private CmCdRepository cmCdR;
	
	@Autowired
	EntityManager em;

	public void init() {
		/*
		CmGrpCd t =cmGrpCdR.save(CmGrpCd.builder().grpCd("HOGA_GUBUN").grpNm("거래구분(호가구분)").useYn("Y").ord(2).updtDtm(new Date()).crtDtm(new Date()).build());
		em.persist(t);
		cmCdR.save(CmCd.builder().grpCd(t.getGrpCd()).cd("00").cdNm("(00)지정가").useYn("Y").ord(1).updtDtm(new Date()).crtDtm(new Date()).build());
		cmCdR.save(CmCd.builder().grpCd(t.getGrpCd()).cd("03").cdNm("(03)시장가").useYn("Y").ord(2).updtDtm(new Date()).crtDtm(new Date()).build());
		cmCdR.save(CmCd.builder().grpCd(t.getGrpCd()).cd("05").cdNm("(05)조건부지정가").useYn("Y").ord(3).updtDtm(new Date()).crtDtm(new Date()).build());
		cmCdR.save(CmCd.builder().grpCd(t.getGrpCd()).cd("06").cdNm("(06)최유리지정가").useYn("Y").ord(4).updtDtm(new Date()).crtDtm(new Date()).build());
		cmCdR.save(CmCd.builder().grpCd(t.getGrpCd()).cd("07").cdNm("(07)최우선지정가").useYn("Y").ord(5).updtDtm(new Date()).crtDtm(new Date()).build());
		cmCdR.save(CmCd.builder().grpCd(t.getGrpCd()).cd("10").cdNm("(10)지정가IOC").useYn("Y").ord(6).updtDtm(new Date()).crtDtm(new Date()).build());
		cmCdR.save(CmCd.builder().grpCd(t.getGrpCd()).cd("13").cdNm("(13)시장가IOC").useYn("Y").ord(7).updtDtm(new Date()).crtDtm(new Date()).build());
		cmCdR.save(CmCd.builder().grpCd(t.getGrpCd()).cd("16").cdNm("(16)최유리IOC").useYn("Y").ord(8).updtDtm(new Date()).crtDtm(new Date()).build());
		cmCdR.save(CmCd.builder().grpCd(t.getGrpCd()).cd("20").cdNm("(20)지정가FOK").useYn("Y").ord(9).updtDtm(new Date()).crtDtm(new Date()).build());
		cmCdR.save(CmCd.builder().grpCd(t.getGrpCd()).cd("23").cdNm("(23)시장가FOK").useYn("Y").ord(10).updtDtm(new Date()).crtDtm(new Date()).build());
		cmCdR.save(CmCd.builder().grpCd(t.getGrpCd()).cd("26").cdNm("(26)최유리FOK").useYn("Y").ord(11).updtDtm(new Date()).crtDtm(new Date()).build());
		cmCdR.save(CmCd.builder().grpCd(t.getGrpCd()).cd("61").cdNm("(61)장전시간외종가").useYn("Y").ord(12).updtDtm(new Date()).crtDtm(new Date()).build());
		cmCdR.save(CmCd.builder().grpCd(t.getGrpCd()).cd("62").cdNm("(62)시간외단일가").useYn("Y").ord(13).updtDtm(new Date()).crtDtm(new Date()).build());
		cmCdR.save(CmCd.builder().grpCd(t.getGrpCd()).cd("81").cdNm("(81)장후시간외종가").useYn("Y").ord(14).updtDtm(new Date()).crtDtm(new Date()).build());
		
		*/
		/*
		List<CmGrpCd> al=cmGrpCdR.findAll();
		CmGrpCd tt1 = al.get(0);
		System.out.println(tt1.getGrpNm());
		
		List<CmCd> al2= tt1.getCmCds();
		
		System.out.println(al2.get(0).getGrpCd());
		//System.out.println(al2.get(0).getCmGrpCd());
		for(int i=0;i<al2.size();i++) {
			System.out.println(al2.get(i).getCdNm());
		}
		*/
		List<CmCd> al= cmCdR.findAll();
		
		CmCd tt1 = al.get(0);
		//System.out.println(tt1.getCmGrpCd());
		
		
		
		

		//em.persist(c);
		/*
		
		
		em.persist(t);
		t.addCmCd(c);
		*/
		//
		
		/*
		
		
		t.addCmCd(c);
		em.persist(c);
		*/
		
		/*
		t.addCmCd(CmCd.builder().cd("03").cdNm("(03)시장가").useYn("Y").ord(2).updtDtm(new Date()).crtDtm(new Date()).build());
		t.addCmCd(CmCd.builder().cd("05").cdNm("(05)조건부지정가").useYn("Y").ord(3).updtDtm(new Date()).crtDtm(new Date()).build());
		t.addCmCd(CmCd.builder().cd("06").cdNm("(06)최유리지정가").useYn("Y").ord(4).updtDtm(new Date()).crtDtm(new Date()).build());
		t.addCmCd(CmCd.builder().cd("07").cdNm("(07)최우선지정가").useYn("Y").ord(5).updtDtm(new Date()).crtDtm(new Date()).build());
		t.addCmCd(CmCd.builder().cd("10").cdNm("(10)지정가IOC").useYn("Y").ord(6).updtDtm(new Date()).crtDtm(new Date()).build());
		t.addCmCd(CmCd.builder().cd("13").cdNm("(13)시장가IOC").useYn("Y").ord(7).updtDtm(new Date()).crtDtm(new Date()).build());
		t.addCmCd(CmCd.builder().cd("16").cdNm("(16)최유리IOC").useYn("Y").ord(8).updtDtm(new Date()).crtDtm(new Date()).build());
		t.addCmCd(CmCd.builder().cd("20").cdNm("(20)지정가FOK").useYn("Y").ord(9).updtDtm(new Date()).crtDtm(new Date()).build());
		t.addCmCd(CmCd.builder().cd("23").cdNm("(23)시장가FOK").useYn("Y").ord(10).updtDtm(new Date()).crtDtm(new Date()).build());
		t.addCmCd(CmCd.builder().cd("26").cdNm("(26)최유리FOK").useYn("Y").ord(11).updtDtm(new Date()).crtDtm(new Date()).build());
		t.addCmCd(CmCd.builder().cd("61").cdNm("(61)장전시간외종가").useYn("Y").ord(12).updtDtm(new Date()).crtDtm(new Date()).build());
		t.addCmCd(CmCd.builder().cd("62").cdNm("(62)시간외단일가").useYn("Y").ord(13).updtDtm(new Date()).crtDtm(new Date()).build());
		t.addCmCd(CmCd.builder().cd("81").cdNm("(81)장후시간외종가").useYn("Y").ord(14).updtDtm(new Date()).crtDtm(new Date()).build());
		
		
		t =cmGrpCdR.save(CmGrpCd.builder().grpCd("ORDER_TYPE").grpNm("주문유형").useYn("Y").ord(1).updtDtm(new Date()).crtDtm(new Date()).build());
		t.addCmCd(CmCd.builder().cd("1").cdNm("(1)신규매수").useYn("Y").ord(1).updtDtm(new Date()).crtDtm(new Date()).build());
		t.addCmCd(CmCd.builder().cd("2").cdNm("(2)신규매도").useYn("Y").ord(2).updtDtm(new Date()).crtDtm(new Date()).build());
		t.addCmCd(CmCd.builder().cd("3").cdNm("(3)매수취소").useYn("Y").ord(3).updtDtm(new Date()).crtDtm(new Date()).build());
		t.addCmCd(CmCd.builder().cd("4").cdNm("(4)매도취소").useYn("Y").ord(4).updtDtm(new Date()).crtDtm(new Date()).build());
		t.addCmCd(CmCd.builder().cd("5").cdNm("(5)매수정정").useYn("Y").ord(5).updtDtm(new Date()).crtDtm(new Date()).build());
		t.addCmCd(CmCd.builder().cd("6").cdNm("(6)매도정정").useYn("Y").ord(6).updtDtm(new Date()).crtDtm(new Date()).build());
		
		
		cmGrpCdR.save(CmGrpCd.builder().grpCd("STOCK_CD_TYPE").grpNm("주식코드 접두어(주문,잔고-체결)").useYn("Y").ord(3).updtDtm(new Date()).crtDtm(new Date()).build());
		t.addCmCd(CmCd.builder().cd("A").cdNm("장내주식").useYn("Y").ord(1).updtDtm(new Date()).crtDtm(new Date()).build());
		t.addCmCd(CmCd.builder().cd("J").cdNm("ELW종목").useYn("Y").ord(2).updtDtm(new Date()).crtDtm(new Date()).build());
		t.addCmCd(CmCd.builder().cd("Q").cdNm("ETN종목").useYn("Y").ord(3).updtDtm(new Date()).crtDtm(new Date()).build());
		
		cmGrpCdR.save(CmGrpCd.builder().grpCd("MARKET").grpNm("장구분(마켓)").useYn("Y").ord(4).updtDtm(new Date()).crtDtm(new Date()).build());
		t.addCmCd(CmCd.builder().cd("0").cdNm("장내").useYn("Y").ord(1).updtDtm(new Date()).crtDtm(new Date()).build());
		t.addCmCd(CmCd.builder().cd("3").cdNm("ELW").useYn("Y").ord(2).updtDtm(new Date()).crtDtm(new Date()).build());
		t.addCmCd(CmCd.builder().cd("4").cdNm("뮤추얼펀드").useYn("Y").ord(3).updtDtm(new Date()).crtDtm(new Date()).build());
		t.addCmCd(CmCd.builder().cd("5").cdNm("신주인수권").useYn("Y").ord(4).updtDtm(new Date()).crtDtm(new Date()).build());
		t.addCmCd(CmCd.builder().cd("6").cdNm("리츠").useYn("Y").ord(5).updtDtm(new Date()).crtDtm(new Date()).build());
		t.addCmCd(CmCd.builder().cd("8").cdNm("ETF").useYn("Y").ord(6).updtDtm(new Date()).crtDtm(new Date()).build());
		t.addCmCd(CmCd.builder().cd("9").cdNm("하이일드펀드").useYn("Y").ord(7).updtDtm(new Date()).crtDtm(new Date()).build());
		t.addCmCd(CmCd.builder().cd("10").cdNm("코스닥").useYn("Y").ord(8).updtDtm(new Date()).crtDtm(new Date()).build());
		t.addCmCd(CmCd.builder().cd("30").cdNm("K-OTC").useYn("Y").ord(9).updtDtm(new Date()).crtDtm(new Date()).build());
		t.addCmCd(CmCd.builder().cd("50").cdNm("코넥스(KONEX)").useYn("Y").ord(10).updtDtm(new Date()).crtDtm(new Date()).build());
		*/
	}
}
