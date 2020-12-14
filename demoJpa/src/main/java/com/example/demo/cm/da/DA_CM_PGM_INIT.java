package com.example.demo.cm.da;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.da.service.CmCdRepository;
import com.example.demo.da.service.CmDomainRepository;
import com.example.demo.da.service.CmGrpCdRepository;
import com.example.demo.da.service.CmMenuRepository;
import com.example.demo.da.service.CmPgmRepository;
import com.example.demo.da.service.CmUserRepository;
import com.example.demo.domain.CmCd;
import com.example.demo.domain.CmDomain;
import com.example.demo.domain.CmGrpCd;
import com.example.demo.domain.CmMenu;
import com.example.demo.domain.CmPgm;

@Service
public class DA_CM_PGM_INIT {
	@Autowired
	private CmGrpCdRepository cmGrpCdR;
	
	@Autowired
	private CmCdRepository cmCdR;
	
	@Autowired
	CmMenuRepository cmMenuR;
	
	@Autowired
	CmPgmRepository cmPgmR;
	
	@Autowired
	CmDomainRepository CmDomainR;
	
	@Autowired
	CmUserRepository CmUserR;
	
	public void insertGrpCd() {
		cmGrpCdR.save(CmGrpCd.builder().grpCd("HOGA_GUBUN").grpNm("거래구분(호가구분)").useYn("Y").ord(2).updtDtm(new Date()).crtDtm(new Date()).build());
		cmGrpCdR.save(CmGrpCd.builder().grpCd("ORDER_TYPE").grpNm("주문유형").useYn("Y").ord(1).updtDtm(new Date()).crtDtm(new Date()).build());
		cmGrpCdR.save(CmGrpCd.builder().grpCd("STOCK_CD_TYPE").grpNm("주식코드 접두어(주문,잔고-체결)").useYn("Y").ord(3).updtDtm(new Date()).crtDtm(new Date()).build());
		cmGrpCdR.save(CmGrpCd.builder().grpCd("MARKET").grpNm("장구분(마켓)").useYn("Y").ord(4).updtDtm(new Date()).crtDtm(new Date()).build());
	}
	public void insertCd() {
		CmGrpCd t =cmGrpCdR.getOne("HOGA_GUBUN");
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

		t =cmGrpCdR.getOne("ORDER_TYPE");
		cmCdR.save(CmCd.builder().grpCd(t.getGrpCd()).cd("1").cdNm("(1)신규매수").useYn("Y").ord(1).updtDtm(new Date()).crtDtm(new Date()).build());
		cmCdR.save(CmCd.builder().grpCd(t.getGrpCd()).cd("2").cdNm("(2)신규매도").useYn("Y").ord(2).updtDtm(new Date()).crtDtm(new Date()).build());
		cmCdR.save(CmCd.builder().grpCd(t.getGrpCd()).cd("3").cdNm("(3)매수취소").useYn("Y").ord(3).updtDtm(new Date()).crtDtm(new Date()).build());
		cmCdR.save(CmCd.builder().grpCd(t.getGrpCd()).cd("4").cdNm("(4)매도취소").useYn("Y").ord(4).updtDtm(new Date()).crtDtm(new Date()).build());
		cmCdR.save(CmCd.builder().grpCd(t.getGrpCd()).cd("5").cdNm("(5)매수정정").useYn("Y").ord(5).updtDtm(new Date()).crtDtm(new Date()).build());
		cmCdR.save(CmCd.builder().grpCd(t.getGrpCd()).cd("6").cdNm("(6)매도정정").useYn("Y").ord(6).updtDtm(new Date()).crtDtm(new Date()).build());
		
		
		t =cmGrpCdR.getOne("STOCK_CD_TYPE");
		cmCdR.save(CmCd.builder().grpCd(t.getGrpCd()).cd("A").cdNm("장내주식").useYn("Y").ord(1).updtDtm(new Date()).crtDtm(new Date()).build());
		cmCdR.save(CmCd.builder().grpCd(t.getGrpCd()).cd("J").cdNm("ELW종목").useYn("Y").ord(2).updtDtm(new Date()).crtDtm(new Date()).build());
		cmCdR.save(CmCd.builder().grpCd(t.getGrpCd()).cd("Q").cdNm("ETN종목").useYn("Y").ord(3).updtDtm(new Date()).crtDtm(new Date()).build());
		
		t =cmGrpCdR.getOne("MARKET");
		cmCdR.save(CmCd.builder().grpCd(t.getGrpCd()).cd("0").cdNm("장내").useYn("Y").ord(1).updtDtm(new Date()).crtDtm(new Date()).build());
		cmCdR.save(CmCd.builder().grpCd(t.getGrpCd()).cd("3").cdNm("ELW").useYn("Y").ord(2).updtDtm(new Date()).crtDtm(new Date()).build());
		cmCdR.save(CmCd.builder().grpCd(t.getGrpCd()).cd("4").cdNm("뮤추얼펀드").useYn("Y").ord(3).updtDtm(new Date()).crtDtm(new Date()).build());
		cmCdR.save(CmCd.builder().grpCd(t.getGrpCd()).cd("5").cdNm("신주인수권").useYn("Y").ord(4).updtDtm(new Date()).crtDtm(new Date()).build());
		cmCdR.save(CmCd.builder().grpCd(t.getGrpCd()).cd("6").cdNm("리츠").useYn("Y").ord(5).updtDtm(new Date()).crtDtm(new Date()).build());
		cmCdR.save(CmCd.builder().grpCd(t.getGrpCd()).cd("8").cdNm("ETF").useYn("Y").ord(6).updtDtm(new Date()).crtDtm(new Date()).build());
		cmCdR.save(CmCd.builder().grpCd(t.getGrpCd()).cd("9").cdNm("하이일드펀드").useYn("Y").ord(7).updtDtm(new Date()).crtDtm(new Date()).build());
		cmCdR.save(CmCd.builder().grpCd(t.getGrpCd()).cd("10").cdNm("코스닥").useYn("Y").ord(8).updtDtm(new Date()).crtDtm(new Date()).build());
		cmCdR.save(CmCd.builder().grpCd(t.getGrpCd()).cd("30").cdNm("K-OTC").useYn("Y").ord(9).updtDtm(new Date()).crtDtm(new Date()).build());
		cmCdR.save(CmCd.builder().grpCd(t.getGrpCd()).cd("50").cdNm("코넥스(KONEX)").useYn("Y").ord(10).updtDtm(new Date()).crtDtm(new Date()).build());

	}
	
	public void insertMenu() {
		cmMenuR.save(CmMenu.builder().menuCd("MN_KIW_0000").menuNm("키움실시간").prntMenuCd("").menuKind("M").fstOrd("001").sedOrd("001").pgmId("").updtDtm(new Date()).crtDtm(new Date()).build());
		cmMenuR.save(CmMenu.builder().menuCd("MN_KIW_0100").menuNm("주문(접수,체결)").prntMenuCd("MN_KIW_0000").menuKind("S").fstOrd("001").sedOrd("002").pgmId("KIW_0100").updtDtm(new Date()).crtDtm(new Date()).build());
		cmMenuR.save(CmMenu.builder().menuCd("MN_KIW_0200").menuNm("잔고(체결)").prntMenuCd("MN_KIW_0000").menuKind("S").fstOrd("001").sedOrd("003").pgmId("KIW_0200").updtDtm(new Date()).crtDtm(new Date()).build());
		cmMenuR.save(CmMenu.builder().menuCd("MN_KIW_0300").menuNm("실시간체결").prntMenuCd("MN_KIW_0000").menuKind("S").fstOrd("001").sedOrd("004").pgmId("KIW_0300").updtDtm(new Date()).crtDtm(new Date()).build());
		cmMenuR.save(CmMenu.builder().menuCd("MN_KIW_1000").menuNm("키움조회").prntMenuCd("").menuKind("M").fstOrd("002").sedOrd("001").pgmId("").updtDtm(new Date()).crtDtm(new Date()).build());
		cmMenuR.save(CmMenu.builder().menuCd("MN_KIW_1100").menuNm("주식기본요청").prntMenuCd("MN_KIW_1000").menuKind("S").fstOrd("002").sedOrd("002").pgmId("KIW_1100").updtDtm(new Date()).crtDtm(new Date()).build());
		cmMenuR.save(CmMenu.builder().menuCd("MN_KIW_1200").menuNm("계좌수익률요청").prntMenuCd("MN_KIW_1000").menuKind("S").fstOrd("002").sedOrd("003").pgmId("KIW_1200").updtDtm(new Date()).crtDtm(new Date()).build());
		cmMenuR.save(CmMenu.builder().menuCd("MN_KIW_1300").menuNm("관심종목정보요청").prntMenuCd("MN_KIW_1000").menuKind("S").fstOrd("002").sedOrd("004").pgmId("KIW_1300").updtDtm(new Date()).crtDtm(new Date()).build());
		cmMenuR.save(CmMenu.builder().menuCd("MN_KIW_1400").menuNm("주식종목상세(매도,매수)").prntMenuCd("MN_KIW_1000").menuKind("S").fstOrd("002").sedOrd("005").pgmId("KIW_1400").updtDtm(new Date()).crtDtm(new Date()).build());
		cmMenuR.save(CmMenu.builder().menuCd("MN_KIW_2000").menuNm("기준정보").prntMenuCd("").menuKind("M").fstOrd("003").sedOrd("001").pgmId("").updtDtm(new Date()).crtDtm(new Date()).build());
		cmMenuR.save(CmMenu.builder().menuCd("MN_KIW_2100").menuNm("테마그룹코드").prntMenuCd("MN_KIW_2000").menuKind("S").fstOrd("003").sedOrd("002").pgmId("KIW_2100").updtDtm(new Date()).crtDtm(new Date()).build());
		cmMenuR.save(CmMenu.builder().menuCd("MN_CM_0000").menuNm("시스템관리").prntMenuCd("").menuKind("M").fstOrd("004").sedOrd("001").pgmId("").updtDtm(new Date()).crtDtm(new Date()).build());
		cmMenuR.save(CmMenu.builder().menuCd("MN_CM_0100").menuNm("프로그램관리").prntMenuCd("MN_CM_0000").menuKind("S").fstOrd("004").sedOrd("002").pgmId("CM_0100").updtDtm(new Date()).crtDtm(new Date()).build());
		cmMenuR.save(CmMenu.builder().menuCd("MN_CM_0200").menuNm("테이블도메인").prntMenuCd("MN_CM_0000").menuKind("S").fstOrd("004").sedOrd("003").pgmId("CM_0200").updtDtm(new Date()).crtDtm(new Date()).build());
		cmMenuR.save(CmMenu.builder().menuCd("MN_CM_0300").menuNm("메뉴관리").prntMenuCd("MN_CM_0000").menuKind("S").fstOrd("004").sedOrd("004").pgmId("CM_0300").updtDtm(new Date()).crtDtm(new Date()).build());
		cmMenuR.save(CmMenu.builder().menuCd("MN_CM_0400").menuNm("공통코드관리").prntMenuCd("MN_CM_0000").menuKind("S").fstOrd("004").sedOrd("005").pgmId("CM_0400").updtDtm(new Date()).crtDtm(new Date()).build());	
	}
	
	public void insertPgm() {
		cmPgmR.save(CmPgm.builder().pgmId("CM_0100").pgmNm("프로그램관리").rmk("CM_100(프로그램관리)").category("CM").pgmLink("CM_0100(프로그램관리)").updtDtm(new Date()).crtDtm(new Date()).build());
		cmPgmR.save(CmPgm.builder().pgmId("CM_0200").pgmNm("테이블도메인관리").rmk("CM_0200(테이블도메인관리)").category("CM").pgmLink("CM_0200(테이블도메인관리)").updtDtm(new Date()).crtDtm(new Date()).build());
		cmPgmR.save(CmPgm.builder().pgmId("CM_0300").pgmNm("메뉴관리").rmk("CM_0300(메뉴관리)").category("CM").pgmLink("CM_0300(메뉴관리)").updtDtm(new Date()).crtDtm(new Date()).build());
		cmPgmR.save(CmPgm.builder().pgmId("CM_0400").pgmNm("공통코드관리").rmk("CM_0400(공통코드관리)").category("CM").pgmLink("CM_0400(공통코드관리)").updtDtm(new Date()).crtDtm(new Date()).build());
		cmPgmR.save(CmPgm.builder().pgmId("KIW_0100").pgmNm("주문(접수,체결)").rmk("CHEJAN_ORDER(주문(접수,체결))").category("키움API").pgmLink("KIW_0100(주문(접수,체결))").updtDtm(new Date()).crtDtm(new Date()).build());
		cmPgmR.save(CmPgm.builder().pgmId("KIW_0200").pgmNm("잔고(체결)").rmk("CHEJAN_BALANCE(잔고(체결))").category("키움API").pgmLink("KIW_0200(잔고(체결))").updtDtm(new Date()).crtDtm(new Date()).build());
		cmPgmR.save(CmPgm.builder().pgmId("KIW_0300").pgmNm("실시간체결").rmk("REALTIME_CONTRACT(실시간체결) ").category("키움API").pgmLink("KIW_0300(실시간체결)").updtDtm(new Date()).crtDtm(new Date()).build());
		cmPgmR.save(CmPgm.builder().pgmId("KIW_1100").pgmNm("주식기본정보요청").rmk("OPT10001(주식기본정보요청)").category("키움API").pgmLink("KIW_1100(주식기본정보요청)").updtDtm(new Date()).crtDtm(new Date()).build());
		cmPgmR.save(CmPgm.builder().pgmId("KIW_1200").pgmNm("계좌수익률요청").rmk("OPT10085(계좌수익률요청)").category("키움API").pgmLink("KIW_1200(계좌수익률요청)").updtDtm(new Date()).crtDtm(new Date()).build());
		cmPgmR.save(CmPgm.builder().pgmId("KIW_1300").pgmNm("관심종목정보요청").rmk("OPTKWFID(관심종목정보요청)").category("키움API").pgmLink("KIW_1300(관심종목정보요청)").updtDtm(new Date()).crtDtm(new Date()).build());
		cmPgmR.save(CmPgm.builder().pgmId("KIW_1400").pgmNm("주식종목상세(매도,매수)").rmk("SEND_ORDER(주식종목상세(매도,매수))").category("키움API").pgmLink("KIW_1400(주식종목상세(매도,매수))").updtDtm(new Date()).crtDtm(new Date()).build());
		cmPgmR.save(CmPgm.builder().pgmId("KIW_2100").pgmNm("테마그룹코드").rmk("GetThemeGroupList(테마그룹코드)").category("키움API").pgmLink("KIW_2100(테마그룹코드)").updtDtm(new Date()).crtDtm(new Date()).build());
	}
	
	public void insertDomain() {
		CmDomainR.save(CmDomain.builder().dmnCd("prev_next").dmnNm("0이면 종료  2이면 진행 2이면 진행").dataType("character varying(1)").rmk("tb_opt10015").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("offered_amt").dmnNm("(최우선)매도호가").dataType("integer").rmk("tb_chejan_balance,tb_chejan_order").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("bid_amt").dmnNm("(최우선)매수호가").dataType("integer").rmk("tb_chejan_balance,tb_chejan_order").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("amt_amount").dmnNm("1금액,  2 수량").dataType("character varying(1)").rmk("tb_opt10059").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("buy_sell").dmnNm("1매수, 2 매도   ==> 2개 합치면 순매수 확인 가능").dataType("character varying(1)").rmk("tb_opt10059").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("d250_high_amt").dmnNm("D250최고").dataType("integer").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("d250_high_rt").dmnNm("D250최고가대비율").dataType("double precision").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("d250_high_dt").dmnNm("D250최고가일").dataType("character varying(8)").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("d250_low_amt").dmnNm("D250최저").dataType("integer").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("d250_low_rt").dmnNm("D250최저가대비율").dataType("double precision").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("d250_low_dt").dmnNm("D250최저가일").dataType("character varying(8)").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("elw_expiry_dt").dmnNm("ELW만기일").dataType("character varying(8)").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("elw_strike_amt").dmnNm("ELW행사가").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("ko_accessibility_rt").dmnNm("KO접근도").dataType("double precision").rmk("tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("real_name").dmnNm("realname (주식체결").dataType("character varying(50)").rmk("tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("gamma").dmnNm("감마").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("domestic_investor").dmnNm("개인투자자").dataType("integer").rmk("tb_opt10059").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("trade_amt").dmnNm("거래대금").dataType("integer").rmk("tb_opt10015,tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("trade_amount_variation").dmnNm("거래대금증감").dataType("double precision").rmk("tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("trade_contrast").dmnNm("거래대비").dataType("double precision").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("trade_qty").dmnNm("거래량").dataType("integer").rmk("tb_opt10001,tb_opt10015,tb_optkwfid,tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("trade_cost").dmnNm("거래비용").dataType("integer").rmk("tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("trade_turnover_ratio").dmnNm("거래회전율").dataType("double precision").rmk("tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("settlement_mm").dmnNm("결산월(02) ").dataType("character varying(2)").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("payment_balance").dmnNm("결제잔고").dataType("integer").rmk("tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("acct_num").dmnNm("계좌번호").dataType("character varying(10)").rmk("tb_chejan_balance,tb_chejan_order,tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("high_amt").dmnNm("고가").dataType("integer").rmk("tb_opt10001,tb_optkwfid,tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("short_selling_trade_amt").dmnNm("공매도거래대금").dataType("integer").rmk("tb_opt10014").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("short_selling_qty").dmnNm("공매도량").dataType("integer").rmk("tb_opt10014").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("short_selling_average_amt").dmnNm("공매도평균가").dataType("integer").rmk("tb_opt10014").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("purchase_dt").dmnNm("구매일자").dataType("character varying(8)").rmk("tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("nation").dmnNm("국가").dataType("integer").rmk("tb_opt10059").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("grp_cd").dmnNm("그룹코드").dataType("character varying(30)").rmk("tb_cm_cd,tb_cm_cd_grp").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("grp_cd_nm").dmnNm("그룹코드명").dataType("character varying(50)").rmk("tb_cm_cd_grp").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("financial_investment").dmnNm("금융투자").dataType("integer").rmk("tb_opt10059").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("between_trade_qty").dmnNm("기간중거래량").dataType("integer").rmk("tb_opt10015").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("institution").dmnNm("기관계").dataType("integer").rmk("tb_opt10059").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("gearing").dmnNm("기어링").dataType("double precision").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("yesterday_amt").dmnNm("기준가(어제종가)").dataType("integer").rmk("tb_chejan_balance,tb_opt10001,tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("etc_financial").dmnNm("기타금융").dataType("integer").rmk("tb_opt10059").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("etc_corporation").dmnNm("기타법인").dataType("integer").rmk("tb_opt10059").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("stock_dt").dmnNm("날짜").dataType("character varying(8)").rmk("tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("foregin_investment_in_korea").dmnNm("내외국인").dataType("integer").rmk("tb_opt10059").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("implied_volatility").dmnNm("내재변동성").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("accumulated_trade_amt").dmnNm("누적거래대금").dataType("integer").rmk("tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("accumulated_trade_qty").dmnNm("누적거래량").dataType("integer").rmk("tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("contract_amt_unit").dmnNm("단위체결가").dataType("integer").rmk("tb_chejan_order").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("contract_amt_qty").dmnNm("단위체결량").dataType("integer").rmk("tb_chejan_order").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("loan_qty").dmnNm("담보대출수량").dataType("integer").rmk("tb_chejan_balance").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("today_sell_profit_loss").dmnNm("당일 총 매도 손익").dataType("integer").rmk("tb_chejan_balance").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("today_sell_profit_loss").dmnNm("당일매도손익").dataType("integer").rmk("tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("today_tax").dmnNm("당일매매세금").dataType("integer").rmk("tb_chejan_order,tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("today_commission").dmnNm("당일매매수수료").dataType("integer").rmk("tb_chejan_order,tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("today_net_buy_qty").dmnNm("당일순매수량").dataType("integer").rmk("tb_chejan_balance").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("credit_today_profit_loss_amt").dmnNm("당일실현손익(신용)").dataType("integer").rmk("tb_chejan_balance").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("today_profit_loss_amt").dmnNm("당일실현손익(유가)").dataType("integer").rmk("tb_chejan_balance").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("credit_today_profit_loss_rt").dmnNm("당일실현손익률(신용)").dataType("double precision").rmk("tb_chejan_balance").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("today_profit_loss_rt").dmnNm("당일실현손익률(유가)").dataType("double precision").rmk("tb_chejan_balance").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("contrast_symbol").dmnNm("대비기호").dataType("integer").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("substitute_amt").dmnNm("대용가").dataType("integer").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("loan_dt").dmnNm("대출일").dataType("character varying(8)").rmk("tb_chejan_balance,tb_chejan_order,tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("delta").dmnNm("델타").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("domain_nm").dmnNm("도메인명").dataType("character varying(100)").rmk("tb_cm_domain").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("domain_cd").dmnNm("도메인코드").dataType("character varying(50)").rmk("tb_cm_domain").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("fluctuation_rt").dmnNm("등락율").dataType("double precision").rmk("tb_opt10001,tb_opt10015,tb_optkwfid,tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("crt_dtm").dmnNm("등록시간").dataType("timestamp without time zone").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("crt_dtm").dmnNm("등록일").dataType("timestamp without time zone").rmk("tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("lo").dmnNm("로").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("market_nm").dmnNm("마켓명").dataType("character varying(45)").rmk("tb_market").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("market_cd").dmnNm("마켓코드").dataType("character varying(2)").rmk("tb_market,tb_market_stock").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("expiry_dt").dmnNm("만기일").dataType("character varying(8)").rmk("tb_chejan_balance,tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("order_type").dmnNm("매도 / 매수구분").dataType("integer").rmk("tb_chejan_balance").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("offered_amt_one").dmnNm("매도1차호가").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("offered_amt_two").dmnNm("매도2차호가").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("offered_amt_three").dmnNm("매도3차호가").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("offered_amt_four").dmnNm("매도4차호가").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("offered_amt_five").dmnNm("매도5차호가").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("selling_tax").dmnNm("매도세금").dataType("integer").rmk("tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("order_type").dmnNm("매도수구분(1:매도, 2:매수)").dataType("character varying(1)").rmk("tb_chejan_order").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("selling_commission").dmnNm("매도수수료").dataType("integer").rmk("tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("offered_amt").dmnNm("매도호가").dataType("integer").rmk("tb_optkwfid,tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("trade_gubun").dmnNm("매매구분(보통, 시장가…)").dataType("character varying(10)").rmk("tb_chejan_order").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("trade_rt").dmnNm("매매비중").dataType("double precision").rmk("tb_opt10014").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("bid_amt_one").dmnNm("매수1차호가").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("bid_amt_two").dmnNm("매수2차호가").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("bid_amt_three").dmnNm("매수3차호가").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("bid_amt_four").dmnNm("매수4차호가").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("bid_amt_five").dmnNm("매수5차호가").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("bid_amt").dmnNm("매수호가").dataType("integer").rmk("tb_optkwfid,tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("purchase_amt").dmnNm("매입가").dataType("integer").rmk("tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("tot_purchase_amt").dmnNm("매입금액").dataType("integer").rmk("tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("purchase_amt").dmnNm("매입단가").dataType("integer").rmk("tb_chejan_balance").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("buying_commission").dmnNm("매입수수료").dataType("integer").rmk("tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("sales").dmnNm("매출액").dataType("integer").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("open_interest").dmnNm("미결제약정").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("contrast_open_interest").dmnNm("미결제전일대비").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("not_contract_qty").dmnNm("미체결수량").dataType("integer").rmk("tb_chejan_order").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("vega").dmnNm("베가").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("possession_qty").dmnNm("보유수량").dataType("integer").rmk("tb_chejan_balance,tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("insurance").dmnNm("보험").dataType("integer").rmk("tb_opt10059").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("rmk").dmnNm("비고").dataType("character varying(4000)").rmk("tb_cm_domain").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("private_equity_fund").dmnNm("사모펀드").dataType("integer").rmk("tb_opt10059").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("use_yn").dmnNm("사용여부").dataType("character varying(1)").rmk("tb_cm_cd,tb_cm_cd_grp").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("stock_cnt").dmnNm("상장주식수").dataType("integer").rmk("tb_opt10001,tb_optkwfid,tb_stock").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("upper_amt_lmt").dmnNm("상한가").dataType("integer").rmk("tb_opt10001,tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("upper_amt_limit_time").dmnNm("상한가발생시간").dataType("character varying(6)").rmk("tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("crt_dtm").dmnNm("생성일").dataType("timestamp without time zone").rmk("tb_cm_domain").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("crt_dtm").dmnNm("생성일자").dataType("date").rmk("tb_opt10001,tb_opt10014,tb_opt10015").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("theta").dmnNm("세타").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("not_commission_profit_loss").dmnNm("손익금액").dataType("integer").rmk("tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("break_even_point").dmnNm("손익분기").dataType("double precision").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("will_profit_amt").dmnNm("손익분기매입가").dataType("integer").rmk("tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("profit_loss_rt").dmnNm("손익율").dataType("double precision").rmk("tb_chejan_balance,tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("commission").dmnNm("수수료").dataType("integer").rmk("tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("earnings_rt").dmnNm("수익률").dataType("double precision").rmk("tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("updt_dtm").dmnNm("수정일자").dataType("timestamp without time zone").rmk("tb_cm_domain").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("ord").dmnNm("순서").dataType("integer").rmk("tb_cm_cd,tb_cm_cd_grp").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("start_amt").dmnNm("시가").dataType("integer").rmk("tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("total_mrkt_amt").dmnNm("시가총액").dataType("integer").rmk("tb_opt10001,tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("total_market_amt").dmnNm("시가총액(억)").dataType("integer").rmk("tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("total_mrkt_amt_rt").dmnNm("시가총액비중").dataType("double precision").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("start_amt").dmnNm("시작가").dataType("integer").rmk("tb_opt10001,tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("credit_gubun").dmnNm("신용구분").dataType("character varying(2)").rmk("tb_chejan_order,tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("credit_gubun").dmnNm("신용구분(00)").dataType("character varying(2)").rmk("tb_chejan_balance").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("credit_amt").dmnNm("신용금액").dataType("integer").rmk("tb_chejan_balance,tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("credit_rt").dmnNm("신용비율(credit_rate)").dataType("double precision").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("credit_interest").dmnNm("신용이자").dataType("integer").rmk("tb_chejan_balance,tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("face_amt").dmnNm("액면가").dataType("integer").rmk("tb_opt10001,tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("face_amt_unit").dmnNm("액면가단위").dataType("character varying(10)").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("pension_fund").dmnNm("연기금등").dataType("integer").rmk("tb_opt10059").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("year_high_amt").dmnNm("연중최고").dataType("integer").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("year_low_amt").dmnNm("연중최저").dataType("integer").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("business_profits").dmnNm("영업이익").dataType("integer").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("expectation_contract_amt").dmnNm("예상체결가").dataType("integer").rmk("tb_opt10001,tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("expectation_contract_qty").dmnNm("예상체결수량").dataType("integer").rmk("tb_opt10001,tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("deposit").dmnNm("예수금").dataType("integer").rmk("tb_chejan_balance").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("foreign_investor").dmnNm("외국인투자자").dataType("integer").rmk("tb_opt10059").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("foreigner_exhaustion_rt").dmnNm("외인소진률").dataType("double precision").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("fst_offered_qty").dmnNm("우선매도건수").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("fst_offered_balance").dmnNm("우선매도잔량").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("fst_bid_qty").dmnNm("우선매수건수").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("fst_bid_balance").dmnNm("우선매수잔량").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("ongn_order_num").dmnNm("원주문번호").dataType("character varying(10)").rmk("tb_chejan_order").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("bank").dmnNm("은행").dataType("integer").rmk("tb_opt10059").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("theorist_amt").dmnNm("이론가").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("ev").dmnNm("이자비용,법인세비용, 감가상각비용을 공제하기 전의 이익").dataType("double precision").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("stock_dt").dmnNm("일자").dataType("character varying(8)").rmk("tb_opt10014,tb_opt10015,tb_opt10059,tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("roe").dmnNm("자기자본이익률").dataType("double precision").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("capital_amt").dmnNm("자본금").dataType("integer").rmk("tb_opt10001,tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("market_gubun").dmnNm("장구분").dataType("integer").rmk("tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("before_market_trade_qty").dmnNm("장전거래량").dataType("integer").rmk("tb_opt10015").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("before_market_trade_rt").dmnNm("장전거래비중").dataType("double precision").rmk("tb_opt10015").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("market_trade_qty").dmnNm("장중거래량").dataType("integer").rmk("tb_opt10015").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("market_trade_rt").dmnNm("장중거래비중").dataType("double precision").rmk("tb_opt10015").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("after_market_trade_qty").dmnNm("장후거래량").dataType("integer").rmk("tb_opt10015").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("after_market_trade_rt").dmnNm("장후거래비중").dataType("double precision").rmk("tb_opt10015").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("low_amt").dmnNm("저가").dataType("integer").rmk("tb_opt10001,tb_optkwfid,tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("last_price").dmnNm("전일가").dataType("integer").rmk("tb_stock").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("yesterday_contrast_trade_rt").dmnNm("전일거래량대비").dataType("double precision").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("yesterday_contrast_trade_qty").dmnNm("전일거래량대비(계약,주)").dataType("integer").rmk("tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("yesterday_contrast_trade_rt").dmnNm("전일거래량대비(비율)").dataType("double precision").rmk("tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("contrast_yesterday").dmnNm("전일대비").dataType("integer").rmk("tb_opt10001,tb_opt10015,tb_optkwfid,tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("contrast_yesterday_symbol").dmnNm("전일대비기호").dataType("integer").rmk("tb_opt10015,tb_optkwfid,tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("conversion_rt").dmnNm("전환비율").dataType("double precision").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("clsg_amt").dmnNm("종가").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("stock_cd").dmnNm("종목코드").dataType("character varying(9)").rmk("tb_chejan_balance,tb_chejan_order,tb_market_stock,tb_opt10014,tb_opt10015,tb_opt10059,tb_opt10085,tb_optkwfid,tb_realtime_contract,tb_theme_stock").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("stock_cd").dmnNm("종목코드(stock_code)").dataType("character varying(9)").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("per").dmnNm("주가수익률").dataType("double precision").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("pbr").dmnNm("주가순자산비율").dataType("double precision").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("eps").dmnNm("주당순이익").dataType("double precision").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("bps").dmnNm("주당순자산가치").dataType("double precision").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("contract_time").dmnNm("주문/체결시간").dataType("character varying(6)").rmk("tb_chejan_order").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("order_amt").dmnNm("주문가격").dataType("integer").rmk("tb_chejan_order").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("order_possible_qty").dmnNm("주문가능수량").dataType("integer").rmk("tb_chejan_balance").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("order_gubun").dmnNm("주문구분(+현금내수, -현금매도…)").dataType("character varying(10)").rmk("tb_chejan_order").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("order_num").dmnNm("주문번호").dataType("character varying(10)").rmk("tb_chejan_order").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("order_status").dmnNm("주문상태 1 <=보유,2<=매도중,3<=매도완료").dataType("character varying(1)").rmk("tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("order_status").dmnNm("주문상태(체결,접수)").dataType("character varying(10)").rmk("tb_chejan_order").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("order_qty").dmnNm("주문수량").dataType("integer").rmk("tb_chejan_order").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("order_business_classification").dmnNm("주문업무분류").dataType("character varying(2)").rmk("tb_chejan_order").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("stock_dt").dmnNm("주식상장일").dataType("character varying(8)").rmk("tb_stock").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("sellable_qty").dmnNm("청산가능수량").dataType("integer").rmk("tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("contract_amt").dmnNm("체결가").dataType("integer").rmk("tb_chejan_order").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("contract_strength").dmnNm("체결강도").dataType("double precision").rmk("tb_optkwfid,tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("contract_tot_amt").dmnNm("체결누계금액").dataType("integer").rmk("tb_chejan_order").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("contract_qty").dmnNm("체결량").dataType("integer").rmk("tb_chejan_order,tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("contract_num").dmnNm("체결번호").dataType("character varying(10)").rmk("tb_chejan_order").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("contract_time").dmnNm("체결시간").dataType("character varying(6)").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("tot_offered_qty").dmnNm("총매도건수").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("tot_offered_balance").dmnNm("총매도잔량").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("tot_bid_qty").dmnNm("총매수건수").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("tot_bid_balance").dmnNm("총매수잔량").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("tot_purchase_amt").dmnNm("총매입가").dataType("integer").rmk("tb_chejan_balance").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("cd").dmnNm("코드").dataType("character varying(30)").rmk("tb_cm_cd").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("cd_nm").dmnNm("코드명").dataType("character varying(50)").rmk("tb_cm_cd").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("data_type").dmnNm("크기").dataType("character varying(50)").rmk("tb_cm_domain").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("terminal_num").dmnNm("터미널번호").dataType("character varying(8)").rmk("tb_chejan_order").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("theme_nm").dmnNm("테마명").dataType("character varying(45)").rmk("tb_theme").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("theme_cd").dmnNm("테마코드").dataType("character varying(3)").rmk("tb_theme,tb_theme_stock").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("investment_trust").dmnNm("투신").dataType("integer").rmk("tb_opt10059").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("parity_rt").dmnNm("패리티").dataType("double precision").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("evaluated_amt").dmnNm("평가금액").dataType("integer").rmk("tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("valuation_profit_loss").dmnNm("평가손익").dataType("integer").rmk("tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("lower_amt_lmt").dmnNm("하한가").dataType("integer").rmk("tb_opt10001,tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("lower_amt_limit_time").dmnNm("하한가발생시간").dataType("character varying(6)").rmk("tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("curr_amt").dmnNm("현재가").dataType("integer").rmk("tb_chejan_balance,tb_opt10001,tb_opt10085,tb_optkwfid,tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("curr_amt").dmnNm("현재가, 체결가, 실시간종가").dataType("integer").rmk("tb_chejan_order,tb_opt10015").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("hoga_time").dmnNm("호가시간").dataType("character varying(6)").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("screen_num").dmnNm("화면번호").dataType("character varying(4)").rmk("tb_chejan_order").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("stock_state").dmnNm("종목상태 (정상, 증거금100%, 거래정지, 관리종목, 감리종목, 투자유의종목, 담보대출, 액면분할, 신용가능)").dataType("character varying(20)").rmk("tb_stock").updtDtm(new Date()).crtDtm(new Date()).build());
		CmDomainR.save(CmDomain.builder().dmnCd("construction").dmnNm("감리구분 (정상,투자주의,투자경고,투자위험,투자주의환기종목)").dataType("character varying(20)").rmk("tb_stock").updtDtm(new Date()).crtDtm(new Date()).build());
	}

	public void insertUser() {
		/*
		BCryptPasswordEncoder passwordEncoding = new BCryptPasswordEncoder();
		String admin =passwordEncoding.encode("admin");
		CmUserR.save(CmUser.builder().userNo(0).userId("admin").userPwd(admin).userNm("관리자").email("admin@stock.com").useYn("Y").lstAccDtm(new Date()).updtDtm(new Date()).crtDtm(new Date()).build());
		*/
	}
}
