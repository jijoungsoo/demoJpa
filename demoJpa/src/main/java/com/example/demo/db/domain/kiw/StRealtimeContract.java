package com.example.demo.db.domain.kiw;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
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
@Table(name="TB_ST_REALTIME_CONTRACT")  /*실시간체결 */
@IdClass(StRealtimeContractId.class)
public class StRealtimeContract {
	/** 날짜 */
	@Id
	@Column(nullable = false, length = 8 ,name="STOCK_DT")
	String stockDt;
	
	/** 현재가 */
	@Id
	@Column(nullable = true, length = 6,   name="CURR_TIME")
	String currTime;
	
	/** 종목코드 */
	@Id
	@Column(nullable = false,length = 9,  name="STOCK_CD")
	String stockCd;
	
	/** 현재가 */
	@Column(nullable = true,  name="CURR_AMT")
	Integer currAmt;
	
	/** 등락율 */	
	@Column(nullable = true,  name="FLUCTUATION_RT")
	Double  fluctuationRt;
	
	/** 매도호가 */	
	@Column(nullable = true,  name="OFFERED_AMT")
	Integer  offeredAmt;
	
	/** 매수호가 */	
	@Column(nullable = true, name="BID_AMT")
	Integer bidAmt;
	
	/** 거래량 */	
	@Column(nullable = true, name="TRADE_QTY")
	Integer tradeQty;
	
	/** 누적거래량 */	
	@Column(nullable = true,  name="ACCUMULATED_TRADE_QTY")
	Integer accumulatedTradeQty;
		
	/** 누적거래대금 */	
	@Column(nullable = true, name="ACCUMULATED_TRADE_AMT")
	Integer accumulatedTradeAmt;
	
	/** 시가 */	
	@Column(nullable = true, name="START_AMT")
	Integer startAmt;
	
	/** 고가 */	
	@Column(nullable = true, name="HIGH_AMT")
	Integer highAmt;
	
	/** 저가 */	
	@Column(nullable = true, name="LOW_AMT")
	Integer lowAmt;
	
	/** 전일대비기호 */	
	@Column(nullable = true, name="CONTRAST_YESTERDAY_SYMBOL")
	Integer contrastYesterdaySymbol;
	
	/** 전일거래량대비(계약,주) */	
	@Column(nullable = true, name="YESTERDAY_CONTRAST_TRADE_QTY")
	Integer yesterdayContrastTradeQty;
	
	/** 거래대금증감 */	
	@Column(nullable = true, name="TRADE_AMOUNT_VARIATION")
	Double tradeAmountVariation;
	
	/** 전일거래량대비(비율) */	
	@Column(nullable = true, name="YESTERDAY_CONTRAST_TRADE_RT")
	Double yesterdayContrastTradeRt;
	
	/** 거래회전율 */	
	@Column(nullable = true, name="TRADE_TURNOVER_RATIO")
	Double trade_turnover_ratio;
	
	/** 거래비용 */	
	@Column(nullable = true, name="TRADE_COST")
	Integer tradeCost;
	
	/** 체결강도 */	
	@Column(nullable = true, name="CONTRACT_STRENGTH")
	Double contractStrength;
	
	/** 시가총액(억) */	
	@Column(nullable = true, name="TOT_MARKET_AMT")
	Integer totMarketAmt;
	
	/** 장구분 */	
	@Column(nullable = true, name="MARKET_GUBUN")
	Integer marketGubun;
	
	/** KO접근도 */	
	@Column(nullable = true, name="KO_ACCESSIBILITY_RT")
	Double koAccessibilityRt;
	
	/** 상한가발생시간 */	
	@Column(nullable = true, length = 6, name="UPPER_AMT_LIMIT_TIME")
	String  upperAmtLimitTime;
	
	/** 하한가발생시간 */	
	@Column(nullable = true, length = 6, name="LOWER_AMT_LIMIT_TIME")
	String lowerAmtLimitTime;
	
	/** 전일대비 */	
	@Column(nullable = true, name="CONTRAST_YESTERDAY")
	Integer contrastYesterday;
	
	/** 주식체결 */	
	@Column(nullable = true, length = 50,name="REAL_NAME")
	String realName;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false,name="CRT_DTM")
	Date crtDtm;
}
