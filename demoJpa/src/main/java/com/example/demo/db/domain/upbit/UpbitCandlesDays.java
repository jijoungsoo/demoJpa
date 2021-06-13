package com.example.demo.db.domain.upbit;

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
@IdClass(UpbitCandlesDaysIdx.class)  
@Table(name="tb_upbit_candles_days")
public class UpbitCandlesDays {

	@Id
	@Column(nullable = false,unique=true ,length = 1000 ,name="market")
	String market;

	@Id
	@Column(nullable = false,unique=false, length = 1000 ,name="candle_date_time_utc")
	String candleDateTimeUtc	;

	@Column(nullable = true,unique=false, length = 1000 ,name="candle_date_time_kst")
	String candleDateTimeKst;

	@Column(nullable = true,unique=false,name="opening_price")
	Double openingPrice;

	@Column(nullable = true,unique=false,name="high_price")
	Double highPrice;

	@Column(nullable = true,unique=false,name="low_price")
	Double lowPrice;

	@Column(nullable = true,unique=false,name="trade_price")
	Double tradePrice;

	@Column(nullable = true,unique=false,name="timestamp")
	Long timestamp;

	@Column(nullable = true,unique=false,name="candle_acc_trade_price")
	Double candleAccTradePrice;

	@Column(nullable = true,unique=false,name="candle_acc_trade_volume")
	Double candleAccTradeVolume;

	@Column(nullable = true,unique=false,name="prev_closing_price")
	Double prevClosingPrice;

	@Column(nullable = true,unique=false,name="change_price")
	Double changePrice;

	@Column(nullable = true,unique=false,name="change_rate")
	Double changeRate;

	@Column(nullable = true,unique=false,name="converted_trade_price")
	Double convertedTradePrice;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "crt_dtm")
	Date crtDtm;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "updt_dtm")
	Date updtDtm;
}