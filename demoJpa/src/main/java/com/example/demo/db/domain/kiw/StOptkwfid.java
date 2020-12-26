package com.example.demo.db.domain.kiw;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor /*org.springframework.orm.jpa.JpaSystemException: No default constructor for entity:*/
@AllArgsConstructor /*https://tzara.tistory.com/73*/
@org.hibernate.annotations.DynamicUpdate/*구분생성시 변경된 것만 한다.*/
@org.hibernate.annotations.DynamicInsert/*구분생성시 null인것은 보내지 않는다.*/
@Data
@Entity
@Table(name="TB_ST_OPTKWFID")  /*호가 */
public class StOptkwfid {
	/** 종목코드 */
	@Id
	@Column(nullable = false,length = 9,  name="STOCK_CD")
	String stockCd;
	
	/** 기준가(어제종가) */
	@Column(nullable = true,  name="YESTERDAY_AMT")
	Integer yesterdayAmt;
		
	/** 현재가 */
	@Column(nullable = true,  name="CURR_AMT")
	Integer currAmt;
	
	/** 전일대비기호 */	
	@Column(nullable = true, name="CONTRAST_YESTERDAY_SYMBOL")
	Integer contrastYesterdaySymbol;
	
	/** 등락율 */	
	@Column(nullable = true,  name="FLUCTUATION_RT")
	Double  fluctuationRt;
	
	/** 전일대비 */	
	@Column(nullable = true, name="CONTRAST_YESTERDAY")
	Integer contrastYesterday;
	
	/** 거래량 */	
	@Column(nullable = true, name="TRADE_QTY")
	Integer tradeQty;
	
	/** 거래대금 */	
	@Column(nullable = true, name="TRADE_AMT")
	Integer tradeAmt;
	
	/** 체결량 */	
	@Column(nullable = true, name="CONTRACT_QTY")
	Integer contractQty;
	
	/** 체결강도 */	
	@Column(nullable = true, name="CONTRACT_STRENGTH")
	Double contractStrength;
	
	/** 전일거래량대비(비율) */	
	@Column(nullable = true, name="YESTERDAY_CONTRAST_TRADE_RT")
	Double yesterdayContrastTradeRt;
		
	/** 매도호가 */	
	@Column(nullable = true,  name="OFFERED_AMT")
	Integer  offeredAmt;
	
	/** 매수호가 */	
	@Column(nullable = true, name="BID_AMT")
	Integer bidAmt;
	
	/** 매도호가 1 */	
	@Column(nullable = true,  name="OFFERED_AMT_ONE")
	Integer  offeredAmtOne;
	
	/** 매도호가 2 */	
	@Column(nullable = true,  name="OFFERED_AMT_TWO")
	Integer  offeredAmtTwo;
	
	/** 매도호가 3 */	
	@Column(nullable = true,  name="OFFERED_AMT_THREE")
	Integer  offeredAmtThree;
	
	/** 매도호가 4 */	
	@Column(nullable = true,  name="OFFERED_AMT_FOUR")
	Integer  offeredAmtFour;
	
	/** 매도호가 5 */	
	@Column(nullable = true,  name="OFFERED_AMT_FIVE")
	Integer  offeredAmtFive;
	
	/** 매수호가1 */	
	@Column(nullable = true, name="BID_AMT_ONE")
	Integer bidAmtOne;
	
	/** 매수호가2 */	
	@Column(nullable = true, name="BID_AMT_TWO")
	Integer bidAmtTwo;
	
	/** 매수호가3 */	
	@Column(nullable = true, name="BID_AMT_THREE")
	Integer bidAmtThree;
	
	/** 매수호가4 */	
	@Column(nullable = true, name="BID_AMT_FOUR")
	Integer bidAmtFour;
	
	/** 매수호가5 */	
	@Column(nullable = true, name="BID_AMT_FIVE")
	Integer bidAmtFive;
	
	@Column(nullable = true, name="UPPER_AMT_LMT")
	Integer upperAmtLmt;
	

	@Column(nullable = true, name="LOWER_AMT_LMT")
	Integer lowerAmtLmt;
	
	/** 시가 */	
	@Column(nullable = true, name="START_AMT")
	Integer startAmt;
	
	/** 고가 */	
	@Column(nullable = true, name="HIGH_AMT")
	Integer highAmt;
	
	/** 저가 */	
	@Column(nullable = true, name="LOW_AMT")
	Integer lowAmt;
	
	/**  종가 */	
	@Column(nullable = true, name="CLSG_AMT")
	Integer clsgAmt;
	
	/** 체결시간 */	
	@Column(nullable = true, length = 6, name="CONTRACT_TIME")
	String  contractTime;
	
	/**  예상체결가 */	
	@Column(nullable = true, name="EXPECTATION_CONTRACT_AMT")
	Integer expectationContractAmt;
	
	/**  예상체결수량 */	
	@Column(nullable = true, name="EXPECTATION_CONTRACT_QTY")
	Integer expectationContractQty;
	
	/**  자본금 */	
	@Column(nullable = true, name="CAPITAL_AMT")
	Integer capitalAmt;
	
	/**  액면가 */	
	@Column(nullable = true, name="FACE_AMT")
	Integer faceAmt;
	
	/**  시가총액 */	
	@Column(nullable = true, name="TOT_MARKET_AMT")
	Integer totMarketAmt;
	
	/**  상장주식수 */	
	@Column(nullable = true, name="STOCK_CNT")
	Integer stockCnt;
	
	/** 호가시간 */	
	@Column(nullable = true, length = 6, name="HOGA_TIME")
	String  hogaTime;
	
	/** 날짜 */	
	@Column(nullable = false, length = 8 ,name="STOCK_DT")
	String stockDt;
	
	/** 우선매도잔량 */	
	@Column(nullable = true,  name="FST_OFFERED_BALANCE")
	Integer fstOfferedBalance;
		
	/** 우선매수잔량 */	
	@Column(nullable = true, name="FST_BID_BALANCE")
	Integer fstBidBalance;
	
	/** 우선매도건수 */	
	@Column(nullable = true, name="FST_OFFERED_QTY")
	Integer fstOfferedQty;
	
	/** 우선매수건수 */	
	@Column(nullable = true, name="FST_BID_QTY")
	Integer fstBidQty;
	
	
	/** 총매도잔량 */	
	@Column(nullable = true, name="TOT_OFFERED_BALANCE")
	Integer totOfferedBalance;
	
	/** 총매수잔량 */	
	@Column(nullable = true, name="TOT_BID_BALANCE")
	Integer totBidBalance;
	
	/** 총매도건수 */	
	@Column(nullable = true, name="TOT_OFFERED_QTY")
	Integer totOfferedQty;
	
	/** 장구분 */	
	@Column(nullable = true, name="TOT_BID_QTY")
	Integer totBidQty;
	
	/** 패리티 */	
	@Column(nullable = true, name="PARITY_RT")
	Double parityRt;
	
	/** 기어링 */	
	@Column(nullable = true, name="GEARING")
	Double gearing;
	
	/** 손익분기 */	
	@Column(nullable = true, name="BREAK_EVEN_POINT")
	Double breakEvenPoint;
	
	/** ELW행사가 */	
	@Column(nullable = true, name="ELW_STRIKE_AMT")
	Integer elwStrikeAmt;
	
	/** 전환비율 */	
	@Column(nullable = true, name="CONVERSION_RT")
	Double conversionRt;
	
	/** ELW만기일 */	
	@Column(nullable = true, length = 8 , name="ELW_EXPIRY_DT")
	String elwExpiryDt;
	
	/** 미결제약정 */	
	@Column(nullable = true, name="OPEN_INTEREST")
	Integer openInterest;
	
	/** 미결제전일대비 */	
	@Column(nullable = true, name="CONTRAST_OPEN_INTEREST")
	Integer contrastOpenInterest;
	
	/** 이론가 */	
	@Column(nullable = true, name="THEORIST_AMT")
	Integer theoristAmt;
	
	/** 내재변동성 */	
	@Column(nullable = true, name="IMPLIED_VOLATILITY")
	Integer impliedVolatility;
	
	/** 델타 */	
	@Column(nullable = true, name="DELTA")
	Integer delta;
	
	/** 감마 */	
	@Column(nullable = true, name="GAMMA")
	Integer gamma;
		
	/** 세타 */	
	@Column(nullable = true, name="THETA")
	Integer theta;
	
	/** 베가 */	
	@Column(nullable = true, name="VEGA")
	Integer vega;
	
	/** 로 */	
	@Column(nullable = true, name="LO")
	Integer lo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false,name="CRT_DTM")
	Date crtDtm;
}
