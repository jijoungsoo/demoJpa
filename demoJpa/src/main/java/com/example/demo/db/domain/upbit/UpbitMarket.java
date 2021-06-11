package com.example.demo.db.domain.upbit;

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
@Table(name="tb_upbit_market")
public class UpbitMarket {

	@Id
	@Column(nullable = false,unique=true ,name="market")
	String market;

	@Column(nullable = true,unique=false ,name="market_cd")
	String marketCd;

	@Column(nullable = true,unique=false, length = 1000 ,name="kr_nm")
	String krNm;

	@Column(nullable = true,unique=false, length = 1000 ,name="en_nm")
	String enNm;

	@Column(nullable = true,unique=false, length = 1000 ,name="market_warning")
	String marketWarning;

	//최근 거래 일자(UTC)
	@Column(nullable = true,unique=false, length = 8 ,name="trade_date")
	String tradeDate;
	//최근 거래 시각(UTC)
	@Column(nullable = true,unique=false, length = 6 ,name="trade_time")
	String tradeTime;
	//최근 거래 일자(KST)
	@Column(nullable = true,unique=false, length = 8 ,name="trade_date_kst")
	String tradeDateKst;
	//최근 거래 시각(KST)
	@Column(nullable = true,unique=false, length = 8 ,name="trade_time_kst")
	String tradeTimeKst;
	//시가
	@Column(nullable = true,unique=false, name="opening_price")
	Double openingPrice;
	//고가
	@Column(nullable = true,unique=false, name="high_price")
	Double highPrice;
	//저가
	@Column(nullable = true,unique=false, name="low_price")
	Double lowPrice;
	//종가
	@Column(nullable = true,unique=false, name="trade_price")
	Double tradePrice;
	//전일 종가
	@Column(nullable = true,unique=false,name="prev_closing_price")
	Double prevClosingPrice;
	/*
	EVEN : 보합
	RISE : 상승
	FALL : 하락
	*/
	@Column(nullable = true,unique=false,length = 10 ,  name="change")
	String change;
	//변화액의 절대값
	@Column(nullable = true,unique=false, name="change_price")
	Double changePrice;
	//변화율의 절대값
	@Column(nullable = true,unique=false, name="change_rate")
	Double changeRate;
	//부호가 있는 변화액
	@Column(nullable = true,unique=false, name="signed_change_price")
	Double signedChangePrice;
	//부호가 있는 변화율
	@Column(nullable = true,unique=false, name="signed_change_rate")
	Double signedChangeRate;
	//가장 최근 거래량
	@Column(nullable = true,unique=false, name="trade_volume")
	Double tradeVolume;
	//누적 거래대금(UTC 0시 기준)
	@Column(nullable = true,unique=false, name="acc_trade_price")
	Double accTradePrice;
	//24시간 누적 거래대금
	@Column(nullable = true,unique=false, name="acc_trade_price_24h")
	Double accTradePrice24h;
	//누적 거래량(UTC 0시 기준)
	@Column(nullable = true,unique=false, name="acc_trade_volume")
	Double accTradeVolume;
	//24시간 누적 거래량
	@Column(nullable = true,unique=false, name="acc_trade_volume_24h")
	Double accTradeVolume24h;
	//52주 신고가
	@Column(nullable = true,unique=false, name="highest_52_week_price")
	Double highest52WeekPrice;
	//52주 신고가 달성일
	@Column(nullable = true,unique=false, name="highest_52_week_date")
	String highest52WeekDate;
	//52주 신저가
	@Column(nullable = true,unique=false, name="lowest_52_week_price")
	Double lowest52WeekPrice;
	//52주 신저가 달성일
	@Column(nullable = true,unique=false, name="lowest_52_week_date")
	String lowest52WeekDate;
	//타임스탬프
	@Column(nullable = true,unique=false, name="timestamp")
	Long timestamp;


	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "crt_dtm")
	Date crtDtm;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "updt_dtm")
	Date updtDtm;
}