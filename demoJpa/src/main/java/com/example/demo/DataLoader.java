package com.example.demo;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.db.domain.cm.CmCd;
import com.example.demo.db.domain.cm.CmDomain;
import com.example.demo.db.domain.cm.CmGrpCd;
import com.example.demo.db.domain.cm.CmMenu;
import com.example.demo.db.domain.cm.CmPgm;
import com.example.demo.db.domain.cm.CmSeq;
import com.example.demo.db.domain.cm.CmUser;
import com.example.demo.db.repository.cm.CmCdRepository;
import com.example.demo.db.repository.cm.CmDomainRepository;
import com.example.demo.db.repository.cm.CmGrpCdRepository;
import com.example.demo.db.repository.cm.CmMenuRepository;
import com.example.demo.db.repository.cm.CmPgmRepository;
import com.example.demo.db.repository.cm.CmSeqRepository;
import com.example.demo.db.repository.cm.CmUserRepository;

@Component
public class DataLoader {
	
	@Autowired
	private CmGrpCdRepository cmGrpCdR;
	
	@Autowired
	private CmCdRepository cmCdR;
	
	@Autowired
	CmMenuRepository cmMenuR;
	
	@Autowired
	CmPgmRepository cmPgmR;
	
	@Autowired
	CmDomainRepository cmDomainR;
	
	@Autowired
	CmUserRepository cmUserR;
	
	@Autowired
	CmSeqRepository cmSeqR;
	
		    //method invoked during the startup
	    /*@PostConstruct*/
	    public void loadData() {
	    	
	    	/*이걸로는 안되는 junit test로 실행하면 들어간다....
	    	 * 먼가 시점의 차이가 있는것 같다.
	    	 * */

	        insertUser();
	        insertTableSeq();
	        insertGrpCd();
	        insertCd();
	        insertMenu();
	        insertPgm();
	        insertDomain();
	    }

	    //method invoked during the shutdown
	    /*@PreDestroy*/
	    public void removeData() {

	    	cmCdR.deleteAll();
	    	cmGrpCdR.deleteAll();
	    	cmPgmR.deleteAll();
	    	cmMenuR.deleteAll();
	    	cmDomainR.deleteAll();
	    	cmUserR.deleteAll();;
	    	cmSeqR.deleteAll();;
	    	
	    }
	
	
	void insertUser() {
		BCryptPasswordEncoder passwordEncoding = new BCryptPasswordEncoder();
		String admin =passwordEncoding.encode("admin");
		//cmUserR.save(CmUser.builder().userNo(0).userNm("관리자").userId("jijs").userPwd(admin).email("admin@ji.co.kr").useYn("Y").lstAccDtm(new Date()).updtDtm(new Date()).crtDtm(new Date()).build());
	}
	
	void insertTableSeq() {
		cmSeqR.save(CmSeq.builder().seqNm("CM_USER_USER_NO_SEQ").seqNo(4).tbNm("TB_CM_USER").colNm("USER_NO").initVal(0).allocationSize(1).updtDtm(new Date()).crtDtm(new Date()).build());
		cmSeqR.save(CmSeq.builder().seqNm("CM_DOMAIN_DMN_NO_SEQ").seqNo(210).tbNm("TB_CM_DOMAIN").colNm("DMN_NO").initVal(0).allocationSize(1).updtDtm(new Date()).crtDtm(new Date()).build());
	}
	
	void insertGrpCd() {
		cmGrpCdR.save(CmGrpCd.builder().grpCd("ORDER_TYPE").grpNm("주문유형").useYn("Y").ord(1).updtDtm(new Date()).crtDtm(new Date()).build());
		cmGrpCdR.save(CmGrpCd.builder().grpCd("HOGA_GUBUN").grpNm("거래구분(호가구분)").useYn("Y").ord(2).updtDtm(new Date()).crtDtm(new Date()).build());
		cmGrpCdR.save(CmGrpCd.builder().grpCd("STOCK_CD_TYPE").grpNm("주식코드 접두어(주문,잔고-체결)").useYn("Y").ord(3).updtDtm(new Date()).crtDtm(new Date()).build());
		cmGrpCdR.save(CmGrpCd.builder().grpCd("MARKET").grpNm("장구분(마켓)").useYn("Y").ord(4).updtDtm(new Date()).crtDtm(new Date()).build());
		cmGrpCdR.save(CmGrpCd.builder().grpCd("USE_YN").grpNm("사용여부").useYn("Y").ord(5).updtDtm(new Date()).crtDtm(new Date()).build());
	}
	void insertCd() {
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
		
		t =cmGrpCdR.getOne("USE_YN");
		cmCdR.save(CmCd.builder().grpCd(t.getGrpCd()).cd("Y").cdNm("Y").useYn("Y").ord(0).updtDtm(new Date()).crtDtm(new Date()).build());
		cmCdR.save(CmCd.builder().grpCd(t.getGrpCd()).cd("N").cdNm("N").useYn("Y").ord(1).updtDtm(new Date()).crtDtm(new Date()).build());
		

	}

	void insertMenu() {
		/*시스템관리*/
		cmMenuR.save(CmMenu.builder().menuCd("MN_CM_0000").menuNm("시스템관리").prntMenuCd(null).menuKind("M").menuLvl("0").menuPath("시스템관리").ord("10000").pgmId("").updtDtm(new Date()).crtDtm(new Date()).build());
		cmMenuR.save(CmMenu.builder().menuCd("MN_CM_1000").menuNm("시스템").prntMenuCd("MN_CM_0000").menuKind("M").menuLvl("1").menuPath("시스템관리>시스템").ord("11000").pgmId("").updtDtm(new Date()).crtDtm(new Date()).build());
		cmMenuR.save(CmMenu.builder().menuCd("MN_CM_1100").menuNm("프로그램관리").prntMenuCd("MN_CM_1000").menuKind("S").menuLvl("2").menuPath("시스템관리>시스템>프로그램관리").ord("11100").pgmId("CM_1100").updtDtm(new Date()).crtDtm(new Date()).build());
		cmMenuR.save(CmMenu.builder().menuCd("MN_CM_1200").menuNm("테이블도메인").prntMenuCd("MN_CM_1000").menuKind("S").menuLvl("2").menuPath("시스템관리>시스템>테이블도메인").ord("11200").pgmId("CM_1200").updtDtm(new Date()).crtDtm(new Date()).build());
		cmMenuR.save(CmMenu.builder().menuCd("MN_CM_1300").menuNm("메뉴관리").prntMenuCd("MN_CM_1000").menuKind("S").menuLvl("2").menuPath("시스템관리>시스템>메뉴관리").ord("11300").pgmId("CM_1300").updtDtm(new Date()).crtDtm(new Date()).build());
		cmMenuR.save(CmMenu.builder().menuCd("MN_CM_1400").menuNm("공통코드관리").prntMenuCd("MN_CM_1000").menuKind("S").menuLvl("2").menuPath("시스템관리>시스템>공통코드관리").ord("11400").pgmId("CM_1400").updtDtm(new Date()).crtDtm(new Date()).build());
		cmMenuR.save(CmMenu.builder().menuCd("MN_CM_1500").menuNm("회원관리").prntMenuCd("MN_CM_1000").menuKind("S").menuLvl("2").menuPath("시스템관리>시스템>회원관리").ord("11500").pgmId("CM_1500").updtDtm(new Date()).crtDtm(new Date()).build());
		cmMenuR.save(CmMenu.builder().menuCd("MN_CM_1600").menuNm("테이블시퀀스").prntMenuCd("MN_CM_1000").menuKind("S").menuLvl("2").menuPath("시스템관리>시스템>테이블시퀀스").ord("11600").pgmId("CM_1600").updtDtm(new Date()).crtDtm(new Date()).build());
		
		
		/*키움*/
		cmMenuR.save(CmMenu.builder().menuCd("MN_KIW_0000").menuNm("키움").prntMenuCd(null).menuKind("M").menuLvl("0").menuPath("키움").ord("20000").pgmId("").updtDtm(new Date()).crtDtm(new Date()).build());
		cmMenuR.save(CmMenu.builder().menuCd("MN_KIW_1000").menuNm("키움조회").prntMenuCd("MN_KIW_0000").menuKind("M").menuLvl("1").menuPath("키움>키움조회").ord("21000").pgmId("").updtDtm(new Date()).crtDtm(new Date()).build());
		cmMenuR.save(CmMenu.builder().menuCd("MN_KIW_1100").menuNm("주식기본요청").prntMenuCd("MN_KIW_1000").menuKind("S").menuLvl("2").menuPath("키움>키움조회>주식기본요청").ord("21100").pgmId("KIW_1100").updtDtm(new Date()).crtDtm(new Date()).build());
		cmMenuR.save(CmMenu.builder().menuCd("MN_KIW_1200").menuNm("계좌수익률요청").prntMenuCd("MN_KIW_1000").menuKind("S").menuLvl("2").menuPath("키움>키움조회>계좌수익률요청").ord("21200").pgmId("KIW_1200").updtDtm(new Date()).crtDtm(new Date()).build());
		cmMenuR.save(CmMenu.builder().menuCd("MN_KIW_1300").menuNm("관심종목정보요청").prntMenuCd("MN_KIW_1000").menuKind("S").menuLvl("2").menuPath("키움>키움조회>관심종목정보요청").ord("21300").pgmId("KIW_1300").updtDtm(new Date()).crtDtm(new Date()).build());
		cmMenuR.save(CmMenu.builder().menuCd("MN_KIW_1400").menuNm("주식종목상세(매도,매수)").prntMenuCd("MN_KIW_1000").menuKind("S").menuLvl("2").menuPath("키움>키움조회>주식종목상세(매도,매수)").ord("21400").pgmId("KIW_1400").updtDtm(new Date()).crtDtm(new Date()).build());
		
		
		cmMenuR.save(CmMenu.builder().menuCd("MN_KIW_2000").menuNm("기준정보").prntMenuCd("MN_KIW_0000").menuKind("M").menuLvl("1").menuPath("키움>기준정보").ord("22000").pgmId("").updtDtm(new Date()).crtDtm(new Date()).build());
		cmMenuR.save(CmMenu.builder().menuCd("MN_KIW_2100").menuNm("테마그룹코드").prntMenuCd("MN_KIW_2000").menuKind("S").menuLvl("2").menuPath("키움>기준정보>테마그룹코드").ord("22100").pgmId("KIW_2100").updtDtm(new Date()).crtDtm(new Date()).build());
		
		
		cmMenuR.save(CmMenu.builder().menuCd("MN_KIW_3000").menuNm("키움실시간").prntMenuCd("MN_KIW_0000").menuKind("M").menuLvl("1").menuPath("키움>키움실시간").ord("23000").pgmId("").updtDtm(new Date()).crtDtm(new Date()).build());
		cmMenuR.save(CmMenu.builder().menuCd("MN_KIW_3100").menuNm("주문(접수,체결)").prntMenuCd("MN_KIW_3000").menuKind("S").menuLvl("2").menuPath("키움>키움실시간>주문(접수,체결)").ord("23100").pgmId("KIW_3100").updtDtm(new Date()).crtDtm(new Date()).build());
		cmMenuR.save(CmMenu.builder().menuCd("MN_KIW_3200").menuNm("잔고(체결)").prntMenuCd("MN_KIW_3000").menuKind("S").menuLvl("2").menuPath("키움>키움실시간>잔고(체결)").ord("23200").pgmId("KIW_3200").updtDtm(new Date()).crtDtm(new Date()).build());
		cmMenuR.save(CmMenu.builder().menuCd("MN_KIW_3300").menuNm("실시간체결").prntMenuCd("MN_KIW_3000").menuKind("S").menuLvl("2").menuPath("키움>키움실시간>실시간체결").ord("23300").pgmId("KIW_3300").updtDtm(new Date()).crtDtm(new Date()).build());
		
		cmMenuR.save(CmMenu.builder().menuCd("MN_KIW_4000").menuNm("내주식정보").prntMenuCd("MN_KIW_0000").menuKind("M").menuLvl("1").menuPath("키움>내주식정보").ord("24000").pgmId("").updtDtm(new Date()).crtDtm(new Date()).build());
		cmMenuR.save(CmMenu.builder().menuCd("MN_KIW_4100").menuNm("내가산주식").prntMenuCd("MN_KIW_4000").menuKind("S").menuLvl("2").menuPath("키움>내주식정보>내가산주식").ord("24100").pgmId("KIW_4100").updtDtm(new Date()).crtDtm(new Date()).build());
		cmMenuR.save(CmMenu.builder().menuCd("MN_KIW_4200").menuNm("내가판주식").prntMenuCd("MN_KIW_4000").menuKind("S").menuLvl("2").menuPath("키움>내주식정보>내가판주식").ord("24200").pgmId("KIW_4200").updtDtm(new Date()).crtDtm(new Date()).build());
	}
	void insertPgm() {
		/*공통*/
		cmPgmR.save(CmPgm.builder().pgmId("CM_1100").pgmNm("프로그램관리").rmk("CM_1100(프로그램관리)").category("CM").pgmLink("CM_1100(프로그램관리)").dirLink("CM").updtDtm(new Date()).crtDtm(new Date()).build());
		cmPgmR.save(CmPgm.builder().pgmId("CM_1200").pgmNm("테이블도메인관리").rmk("CM_1200(테이블도메인관리)").category("CM").pgmLink("CM_1200(테이블도메인관리)").dirLink("CM").updtDtm(new Date()).crtDtm(new Date()).build());
		cmPgmR.save(CmPgm.builder().pgmId("CM_1300").pgmNm("메뉴관리").rmk("CM_1300(메뉴관리)").category("CM").pgmLink("CM_1300(메뉴관리)").dirLink("CM").updtDtm(new Date()).crtDtm(new Date()).build());
		cmPgmR.save(CmPgm.builder().pgmId("CM_1400").pgmNm("공통코드관리").rmk("CM_1400(공통코드관리)").category("CM").pgmLink("CM_1400(공통코드관리)").dirLink("CM").updtDtm(new Date()).crtDtm(new Date()).build());
		cmPgmR.save(CmPgm.builder().pgmId("CM_1500").pgmNm("회원관리").rmk("CM_1500(회원관리)").category("CM").pgmLink("CM_1500(회원관리)").dirLink("CM").updtDtm(new Date()).crtDtm(new Date()).build());
		cmPgmR.save(CmPgm.builder().pgmId("CM_1600").pgmNm("테이블시퀀스").rmk("CM_1600(테이블시퀀스)").category("CM").pgmLink("CM_1600(테이블시퀀스)").dirLink("CM").updtDtm(new Date()).crtDtm(new Date()).build());
		/*키움실시간*/
		cmPgmR.save(CmPgm.builder().pgmId("KIW_3100").pgmNm("주문(접수,체결)").rmk("CHEJAN_ORDER(주문(접수,체결))").category("키움API").pgmLink("KIW_3100(주문(접수,체결))").updtDtm(new Date()).crtDtm(new Date()).build());
		cmPgmR.save(CmPgm.builder().pgmId("KIW_3200").pgmNm("잔고(체결)").rmk("CHEJAN_BALANCE(잔고(체결))").category("키움API").pgmLink("KIW_3200(잔고(체결))").updtDtm(new Date()).crtDtm(new Date()).build());
		cmPgmR.save(CmPgm.builder().pgmId("KIW_3300").pgmNm("실시간체결").rmk("REALTIME_CONTRACT(실시간체결) ").category("키움API").pgmLink("KIW_3300(실시간체결)").updtDtm(new Date()).crtDtm(new Date()).build());
		
		/*키움조회*/
		cmPgmR.save(CmPgm.builder().pgmId("KIW_1100").pgmNm("주식기본정보요청").rmk("OPT10001(주식기본정보요청)").category("키움API").pgmLink("KIW_1100(주식기본정보요청)").updtDtm(new Date()).crtDtm(new Date()).build());
		cmPgmR.save(CmPgm.builder().pgmId("KIW_1200").pgmNm("계좌수익률요청").rmk("OPT10085(계좌수익률요청)").category("키움API").pgmLink("KIW_1200(계좌수익률요청)").updtDtm(new Date()).crtDtm(new Date()).build());
		cmPgmR.save(CmPgm.builder().pgmId("KIW_1300").pgmNm("관심종목정보요청").rmk("OPTKWFID(관심종목정보요청)").category("키움API").pgmLink("KIW_1300(관심종목정보요청)").updtDtm(new Date()).crtDtm(new Date()).build());
		cmPgmR.save(CmPgm.builder().pgmId("KIW_1400").pgmNm("주식종목상세(매도,매수)").rmk("SEND_ORDER(주식종목상세(매도,매수))").category("키움API").pgmLink("KIW_1400(주식종목상세(매도,매수))").updtDtm(new Date()).crtDtm(new Date()).build());
		cmPgmR.save(CmPgm.builder().pgmId("KIW_2100").pgmNm("테마그룹코드").rmk("GetThemeGroupList(테마그룹코드)").category("키움API").pgmLink("KIW_2100(테마그룹코드)").updtDtm(new Date()).crtDtm(new Date()).build());
	}

	void insertDomain() {
		cmDomainR.save(CmDomain.builder().dmnNo(0).dmnCd("prev_next").dmnNm("0이면 종료  2이면 진행 2이면 진행").dataType("character varying(1)").rmk("tb_opt10015").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(1).dmnCd("top_offered_amt").dmnNm("(최우선)매도호가").dataType("integer").rmk("tb_chejan_balance,tb_chejan_order").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(2).dmnCd("top_bid_amt").dmnNm("(최우선)매수호가").dataType("integer").rmk("tb_chejan_balance,tb_chejan_order").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(3).dmnCd("amt_amount").dmnNm("1금액,  2 수량").dataType("character varying(1)").rmk("tb_opt10059").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(4).dmnCd("buy_sell").dmnNm("1매수, 2 매도   ==> 2개 합치면 순매수 확인 가능").dataType("character varying(1)").rmk("tb_opt10059").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(5).dmnCd("d250_high_amt").dmnNm("D250최고").dataType("integer").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(6).dmnCd("d250_high_rt").dmnNm("D250최고가대비율").dataType("double precision").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(7).dmnCd("d250_high_dt").dmnNm("D250최고가일").dataType("character varying(8)").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(8).dmnCd("d250_low_amt").dmnNm("D250최저").dataType("integer").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(9).dmnCd("d250_low_rt").dmnNm("D250최저가대비율").dataType("double precision").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(10).dmnCd("d250_low_dt").dmnNm("D250최저가일").dataType("character varying(8)").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(11).dmnCd("elw_expiry_dt").dmnNm("ELW만기일").dataType("character varying(8)").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(12).dmnCd("elw_strike_amt").dmnNm("ELW행사가").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(13).dmnCd("ko_accessibility_rt").dmnNm("KO접근도").dataType("double precision").rmk("tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(14).dmnCd("real_name").dmnNm("realname (주식체결").dataType("character varying(50)").rmk("tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(15).dmnCd("gamma").dmnNm("감마").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(16).dmnCd("domestic_investor").dmnNm("개인투자자").dataType("integer").rmk("tb_opt10059").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(17).dmnCd("trade_amt").dmnNm("거래대금").dataType("integer").rmk("tb_opt10015,tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(18).dmnCd("trade_amount_variation").dmnNm("거래대금증감").dataType("double precision").rmk("tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(19).dmnCd("trade_contrast").dmnNm("거래대비").dataType("double precision").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(20).dmnCd("trade_qty").dmnNm("거래량").dataType("integer").rmk("tb_opt10001,tb_opt10015,tb_optkwfid,tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(21).dmnCd("trade_cost").dmnNm("거래비용").dataType("integer").rmk("tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(22).dmnCd("trade_turnover_ratio").dmnNm("거래회전율").dataType("double precision").rmk("tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(23).dmnCd("settlement_mm").dmnNm("결산월(02) ").dataType("character varying(2)").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(24).dmnCd("payment_balance").dmnNm("결제잔고").dataType("integer").rmk("tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(25).dmnCd("acct_num").dmnNm("계좌번호").dataType("character varying(10)").rmk("tb_chejan_balance,tb_chejan_order,tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(26).dmnCd("high_amt").dmnNm("고가").dataType("integer").rmk("tb_opt10001,tb_optkwfid,tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(27).dmnCd("short_selling_trade_amt").dmnNm("공매도거래대금").dataType("integer").rmk("tb_opt10014").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(28).dmnCd("short_selling_qty").dmnNm("공매도량").dataType("integer").rmk("tb_opt10014").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(29).dmnCd("short_selling_average_amt").dmnNm("공매도평균가").dataType("integer").rmk("tb_opt10014").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(30).dmnCd("purchase_dt").dmnNm("구매일자").dataType("character varying(8)").rmk("tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(31).dmnCd("nation").dmnNm("국가").dataType("integer").rmk("tb_opt10059").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(32).dmnCd("grp_cd").dmnNm("그룹코드").dataType("character varying(30)").rmk("tb_cm_cd,tb_cm_cd_grp").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(33).dmnCd("grp_cd_nm").dmnNm("그룹코드명").dataType("character varying(50)").rmk("tb_cm_cd_grp").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(34).dmnCd("financial_investment").dmnNm("금융투자").dataType("integer").rmk("tb_opt10059").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(35).dmnCd("between_trade_qty").dmnNm("기간중거래량").dataType("integer").rmk("tb_opt10015").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(36).dmnCd("institution").dmnNm("기관계").dataType("integer").rmk("tb_opt10059").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(37).dmnCd("gearing").dmnNm("기어링").dataType("double precision").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(38).dmnCd("yesterday_amt").dmnNm("기준가(어제종가)").dataType("integer").rmk("tb_chejan_balance,tb_opt10001,tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(39).dmnCd("etc_financial").dmnNm("기타금융").dataType("integer").rmk("tb_opt10059").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(40).dmnCd("etc_corporation").dmnNm("기타법인").dataType("integer").rmk("tb_opt10059").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(41).dmnCd("stock_dt").dmnNm("날짜").dataType("character varying(8)").rmk("tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(42).dmnCd("foregin_investment_in_korea").dmnNm("내외국인").dataType("integer").rmk("tb_opt10059").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(43).dmnCd("implied_volatility").dmnNm("내재변동성").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(44).dmnCd("accumulated_trade_amt").dmnNm("누적거래대금").dataType("integer").rmk("tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(45).dmnCd("accumulated_trade_qty").dmnNm("누적거래량").dataType("integer").rmk("tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(46).dmnCd("contract_amt_unit").dmnNm("단위체결가").dataType("integer").rmk("tb_chejan_order").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(47).dmnCd("contract_amt_qty").dmnNm("단위체결량").dataType("integer").rmk("tb_chejan_order").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(48).dmnCd("loan_qty").dmnNm("담보대출수량").dataType("integer").rmk("tb_chejan_balance").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(49).dmnCd("today_tot_sell_profit_loss").dmnNm("당일 총 매도 손익").dataType("integer").rmk("tb_chejan_balance").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(50).dmnCd("today_sell_profit_loss").dmnNm("당일매도손익").dataType("integer").rmk("tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(51).dmnCd("today_tax").dmnNm("당일매매세금").dataType("integer").rmk("tb_chejan_order,tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(52).dmnCd("today_commission").dmnNm("당일매매수수료").dataType("integer").rmk("tb_chejan_order,tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(53).dmnCd("today_net_buy_qty").dmnNm("당일순매수량").dataType("integer").rmk("tb_chejan_balance").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(54).dmnCd("credit_today_profit_loss_amt").dmnNm("당일실현손익(신용)").dataType("integer").rmk("tb_chejan_balance").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(55).dmnCd("today_profit_loss_amt").dmnNm("당일실현손익(유가)").dataType("integer").rmk("tb_chejan_balance").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(56).dmnCd("credit_today_profit_loss_rt").dmnNm("당일실현손익률(신용)").dataType("double precision").rmk("tb_chejan_balance").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(57).dmnCd("today_profit_loss_rt").dmnNm("당일실현손익률(유가)").dataType("double precision").rmk("tb_chejan_balance").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(58).dmnCd("contrast_symbol").dmnNm("대비기호").dataType("integer").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(59).dmnCd("substitute_amt").dmnNm("대용가").dataType("integer").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(60).dmnCd("loan_dt").dmnNm("대출일").dataType("character varying(8)").rmk("tb_chejan_balance,tb_chejan_order,tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(61).dmnCd("delta").dmnNm("델타").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(62).dmnCd("domain_nm").dmnNm("도메인명").dataType("character varying(100)").rmk("tb_cm_domain").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(63).dmnCd("domain_cd").dmnNm("도메인코드").dataType("character varying(50)").rmk("tb_cm_domain").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(64).dmnCd("fluctuation_rt").dmnNm("등락율").dataType("double precision").rmk("tb_opt10001,tb_opt10015,tb_optkwfid,tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(67).dmnCd("lo").dmnNm("로").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(68).dmnCd("market_nm").dmnNm("마켓명").dataType("character varying(45)").rmk("tb_market").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(69).dmnCd("market_cd").dmnNm("마켓코드").dataType("character varying(2)").rmk("tb_market,tb_market_stock").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(70).dmnCd("expiry_dt").dmnNm("만기일").dataType("character varying(8)").rmk("tb_chejan_balance,tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(72).dmnCd("offered_amt_one").dmnNm("매도1차호가").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(73).dmnCd("offered_amt_two").dmnNm("매도2차호가").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(74).dmnCd("offered_amt_three").dmnNm("매도3차호가").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(75).dmnCd("offered_amt_four").dmnNm("매도4차호가").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(76).dmnCd("offered_amt_five").dmnNm("매도5차호가").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(77).dmnCd("selling_tax").dmnNm("매도세금").dataType("integer").rmk("tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(78).dmnCd("order_type").dmnNm("매도수구분(1:매도, 2:매수)").dataType("character varying(1)").rmk("tb_chejan_order").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(79).dmnCd("selling_commission").dmnNm("매도수수료").dataType("integer").rmk("tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(80).dmnCd("offered_amt").dmnNm("매도호가").dataType("integer").rmk("tb_optkwfid,tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(81).dmnCd("trade_gubun").dmnNm("매매구분(보통, 시장가…)").dataType("character varying(10)").rmk("tb_chejan_order").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(82).dmnCd("trade_rt").dmnNm("매매비중").dataType("double precision").rmk("tb_opt10014").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(83).dmnCd("bid_amt_one").dmnNm("매수1차호가").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(84).dmnCd("bid_amt_two").dmnNm("매수2차호가").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(85).dmnCd("bid_amt_three").dmnNm("매수3차호가").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(86).dmnCd("bid_amt_four").dmnNm("매수4차호가").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(87).dmnCd("bid_amt_five").dmnNm("매수5차호가").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(88).dmnCd("bid_amt").dmnNm("매수호가").dataType("integer").rmk("tb_optkwfid,tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(89).dmnCd("purchase_amt").dmnNm("매입가").dataType("integer").rmk("tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(91).dmnCd("purchase_unit_amt").dmnNm("매입단가").dataType("integer").rmk("tb_chejan_balance").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(92).dmnCd("buying_commission").dmnNm("매입수수료").dataType("integer").rmk("tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(93).dmnCd("sales").dmnNm("매출액").dataType("integer").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(94).dmnCd("open_interest").dmnNm("미결제약정").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(95).dmnCd("changes_open").dmnNm("미결제전일대비").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(96).dmnCd("not_contract_qty").dmnNm("미체결수량").dataType("integer").rmk("tb_chejan_order").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(97).dmnCd("vega").dmnNm("베가").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(98).dmnCd("possession_qty").dmnNm("보유수량").dataType("integer").rmk("tb_chejan_balance,tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(99).dmnCd("insurance").dmnNm("보험").dataType("integer").rmk("tb_opt10059").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(100).dmnCd("rmk").dmnNm("비고").dataType("character varying(4000)").rmk("tb_cm_domain").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(101).dmnCd("private_equity_fund").dmnNm("사모펀드").dataType("integer").rmk("tb_opt10059").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(102).dmnCd("use_yn").dmnNm("사용여부").dataType("character varying(1)").rmk("tb_cm_cd,tb_cm_cd_grp").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(103).dmnCd("stock_cnt").dmnNm("상장주식수").dataType("integer").rmk("tb_opt10001,tb_optkwfid,tb_stock").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(104).dmnCd("upper_amt_lmt").dmnNm("상한가").dataType("integer").rmk("tb_opt10001,tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(105).dmnCd("upper_amt_limit_time").dmnNm("상한가발생시간").dataType("character varying(6)").rmk("tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(107).dmnCd("crt_dtm").dmnNm("생성일자").dataType("date").rmk("tb_opt10001,tb_opt10014,tb_opt10015").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(108).dmnCd("theta").dmnNm("세타").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(109).dmnCd("not_commission_profit_loss").dmnNm("손익금액").dataType("integer").rmk("tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(110).dmnCd("break_even_point").dmnNm("손익분기").dataType("double precision").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(111).dmnCd("will_profit_amt").dmnNm("손익분기매입가").dataType("integer").rmk("tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(112).dmnCd("profit_loss_rt").dmnNm("손익율").dataType("double precision").rmk("tb_chejan_balance,tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(113).dmnCd("commission").dmnNm("수수료").dataType("integer").rmk("tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(114).dmnCd("earnings_rt").dmnNm("수익률").dataType("double precision").rmk("tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(115).dmnCd("updt_dtm").dmnNm("수정일자").dataType("timestamp without time zone").rmk("tb_cm_domain").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(116).dmnCd("ord").dmnNm("순서").dataType("integer").rmk("tb_cm_cd,tb_cm_cd_grp").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(117).dmnCd("start_amt").dmnNm("시가").dataType("integer").rmk("tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(118).dmnCd("total_mrkt_amt").dmnNm("시가총액").dataType("integer").rmk("tb_opt10001,tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(119).dmnCd("total_market_amt").dmnNm("시가총액(억)").dataType("integer").rmk("tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(120).dmnCd("total_mrkt_amt_rt").dmnNm("시가총액비중").dataType("double precision").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(123).dmnCd("credit_gubun").dmnNm("신용구분(00)").dataType("character varying(2)").rmk("tb_chejan_balance").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(124).dmnCd("credit_amt").dmnNm("신용금액").dataType("integer").rmk("tb_chejan_balance,tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(125).dmnCd("credit_rt").dmnNm("신용비율(credit_rate)").dataType("double precision").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(126).dmnCd("credit_interest").dmnNm("신용이자").dataType("integer").rmk("tb_chejan_balance,tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(127).dmnCd("face_amt").dmnNm("액면가").dataType("integer").rmk("tb_opt10001,tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(128).dmnCd("face_amt_unit").dmnNm("액면가단위").dataType("character varying(10)").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(129).dmnCd("pension_fund").dmnNm("연기금등").dataType("integer").rmk("tb_opt10059").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(130).dmnCd("year_high_amt").dmnNm("연중최고").dataType("integer").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(131).dmnCd("year_low_amt").dmnNm("연중최저").dataType("integer").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(132).dmnCd("business_profits").dmnNm("영업이익").dataType("integer").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(133).dmnCd("expectation_contract_amt").dmnNm("예상체결가").dataType("integer").rmk("tb_opt10001,tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(134).dmnCd("expectation_contract_qty").dmnNm("예상체결수량").dataType("integer").rmk("tb_opt10001,tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(135).dmnCd("deposit").dmnNm("예수금").dataType("integer").rmk("tb_chejan_balance").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(136).dmnCd("frgn_invstr").dmnNm("외국인투자자").dataType("integer").rmk("tb_opt10059").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(137).dmnCd("frgnr_ehstn_rt").dmnNm("외인소진률").dataType("double precision").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(138).dmnCd("fst_offered_qty").dmnNm("우선매도건수").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(139).dmnCd("fst_offered_balance").dmnNm("우선매도잔량").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(140).dmnCd("fst_bid_qty").dmnNm("우선매수건수").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(141).dmnCd("fst_bid_balance").dmnNm("우선매수잔량").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(142).dmnCd("ongn_order_num").dmnNm("원주문번호").dataType("character varying(10)").rmk("tb_chejan_order").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(143).dmnCd("bank").dmnNm("은행").dataType("integer").rmk("tb_opt10059").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(144).dmnCd("theorist_amt").dmnNm("이론가").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(145).dmnCd("ev").dmnNm("이자비용,법인세비용, 감가상각비용을 공제하기 전의 이익").dataType("double precision").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(147).dmnCd("roe").dmnNm("자기자본이익률").dataType("double precision").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(148).dmnCd("capital_amt").dmnNm("자본금").dataType("integer").rmk("tb_opt10001,tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(149).dmnCd("market_gubun").dmnNm("장구분").dataType("integer").rmk("tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(150).dmnCd("before_market_trade_qty").dmnNm("장전거래량").dataType("integer").rmk("tb_opt10015").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(151).dmnCd("before_market_trade_rt").dmnNm("장전거래비중").dataType("double precision").rmk("tb_opt10015").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(152).dmnCd("market_trade_qty").dmnNm("장중거래량").dataType("integer").rmk("tb_opt10015").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(153).dmnCd("market_trade_rt").dmnNm("장중거래비중").dataType("double precision").rmk("tb_opt10015").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(154).dmnCd("after_market_trade_qty").dmnNm("장후거래량").dataType("integer").rmk("tb_opt10015").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(155).dmnCd("after_market_trade_rt").dmnNm("장후거래비중").dataType("double precision").rmk("tb_opt10015").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(156).dmnCd("low_amt").dmnNm("저가").dataType("integer").rmk("tb_opt10001,tb_optkwfid,tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(157).dmnCd("last_price").dmnNm("전일가").dataType("integer").rmk("tb_stock").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(158).dmnCd("yesterday_contrast_trade_rt").dmnNm("전일거래량대비").dataType("double precision").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(159).dmnCd("yesterday_contrast_trade_qty").dmnNm("전일거래량대비(계약,주)").dataType("integer").rmk("tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(161).dmnCd("changes").dmnNm("전일대비").dataType("integer").rmk("tb_opt10001,tb_opt10015,tb_optkwfid,tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(162).dmnCd("changes_symbol").dmnNm("전일대비기호").dataType("integer").rmk("tb_opt10015,tb_optkwfid,tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(163).dmnCd("conversion_rt").dmnNm("전환비율").dataType("double precision").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(164).dmnCd("cls_amt").dmnNm("종가").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(165).dmnCd("stock_cd").dmnNm("종목코드").dataType("character varying(9)").rmk("tb_chejan_balance,tb_chejan_order,tb_market_stock,tb_opt10014,tb_opt10015,tb_opt10059,tb_opt10085,tb_optkwfid,tb_realtime_contract,tb_theme_stock").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(167).dmnCd("per").dmnNm("주가수익률").dataType("double precision").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(168).dmnCd("pbr").dmnNm("주가순자산비율").dataType("double precision").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(169).dmnCd("eps").dmnNm("주당순이익").dataType("double precision").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(170).dmnCd("bps").dmnNm("주당순자산가치").dataType("double precision").rmk("tb_opt10001").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(171).dmnCd("contract_time").dmnNm("주문/체결시간").dataType("character varying(6)").rmk("tb_chejan_order").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(172).dmnCd("order_amt").dmnNm("주문가격").dataType("integer").rmk("tb_chejan_order").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(173).dmnCd("order_possible_qty").dmnNm("주문가능수량").dataType("integer").rmk("tb_chejan_balance").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(174).dmnCd("order_gubun").dmnNm("주문구분(+현금내수, -현금매도…)").dataType("character varying(10)").rmk("tb_chejan_order").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(175).dmnCd("order_num").dmnNm("주문번호").dataType("character varying(10)").rmk("tb_chejan_order").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(176).dmnCd("order_status").dmnNm("주문상태 1 <=보유,2<=매도중,3<=매도완료").dataType("character varying(1)").rmk("tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(178).dmnCd("order_qty").dmnNm("주문수량").dataType("integer").rmk("tb_chejan_order").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(179).dmnCd("order_business_classification").dmnNm("주문업무분류").dataType("character varying(2)").rmk("tb_chejan_order").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(180).dmnCd("opn_dt").dmnNm("주식상장일").dataType("character varying(8)").rmk("tb_stock").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(181).dmnCd("sellable_qty").dmnNm("청산가능수량").dataType("integer").rmk("tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(182).dmnCd("contract_amt").dmnNm("체결가").dataType("integer").rmk("tb_chejan_order").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(183).dmnCd("contract_strength").dmnNm("체결강도").dataType("double precision").rmk("tb_optkwfid,tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(184).dmnCd("contract_tot_amt").dmnNm("체결누계금액").dataType("integer").rmk("tb_chejan_order").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(185).dmnCd("contract_qty").dmnNm("체결량").dataType("integer").rmk("tb_chejan_order,tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(186).dmnCd("contract_num").dmnNm("체결번호").dataType("character varying(10)").rmk("tb_chejan_order").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(188).dmnCd("tot_offered_qty").dmnNm("총매도건수").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(189).dmnCd("tot_offered_balance").dmnNm("총매도잔량").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(190).dmnCd("tot_bid_qty").dmnNm("총매수건수").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(191).dmnCd("tot_bid_balance").dmnNm("총매수잔량").dataType("integer").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(192).dmnCd("tot_purchase_amt").dmnNm("총매입가").dataType("integer").rmk("tb_chejan_balance").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(193).dmnCd("cd").dmnNm("코드").dataType("character varying(30)").rmk("tb_cm_cd").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(194).dmnCd("cd_nm").dmnNm("코드명").dataType("character varying(50)").rmk("tb_cm_cd").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(195).dmnCd("data_type").dmnNm("크기").dataType("character varying(50)").rmk("tb_cm_domain").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(196).dmnCd("terminal_num").dmnNm("터미널번호").dataType("character varying(8)").rmk("tb_chejan_order").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(197).dmnCd("theme_nm").dmnNm("테마명").dataType("character varying(45)").rmk("tb_theme").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(198).dmnCd("theme_cd").dmnNm("테마코드").dataType("character varying(3)").rmk("tb_theme,tb_theme_stock").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(199).dmnCd("investment_trust").dmnNm("투신").dataType("integer").rmk("tb_opt10059").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(200).dmnCd("parity_rt").dmnNm("패리티").dataType("double precision").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(201).dmnCd("evaluated_amt").dmnNm("평가금액").dataType("integer").rmk("tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(202).dmnCd("valuation_profit_loss").dmnNm("평가손익").dataType("integer").rmk("tb_opt10085").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(203).dmnCd("lower_amt_lmt").dmnNm("하한가").dataType("integer").rmk("tb_opt10001,tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(204).dmnCd("lower_amt_limit_time").dmnNm("하한가발생시간").dataType("character varying(6)").rmk("tb_realtime_contract").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(206).dmnCd("curr_amt").dmnNm("현재가, 체결가, 실시간종가").dataType("integer").rmk("tb_chejan_order,tb_opt10015").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(207).dmnCd("hoga_time").dmnNm("호가시간").dataType("character varying(6)").rmk("tb_optkwfid").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(208).dmnCd("screen_num").dmnNm("화면번호").dataType("character varying(4)").rmk("tb_chejan_order").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(209).dmnCd("stock_state").dmnNm("종목상태 (정상, 증거금100%, 거래정지, 관리종목, 감리종목, 투자유의종목, 담보대출, 액면분할, 신용가능)").dataType("character varying(20)").rmk("tb_stock").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(210).dmnCd("construction").dmnNm("감리구분 (정상,투자주의,투자경고,투자위험,투자주의환기종목)").dataType("character varying(20)").rmk("tb_stock").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(211).dmnCd("frgn_cnt").dmnNm("외국인주식보유수").dataType("integer").rmk("tb_marcap_stock").updtDtm(new Date()).crtDtm(new Date()).build());
		cmDomainR.save(CmDomain.builder().dmnNo(212).dmnCd("frgn_rt").dmnNm("외국인 지분율(%)").dataType("float").rmk("tb_marcap_stock").updtDtm(new Date()).crtDtm(new Date()).build());
	}
}
