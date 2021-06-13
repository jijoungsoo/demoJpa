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
@IdClass(UpbitTradesTicksIdx.class)  
@Table(name="tb_upbit_trades_ticks")
public class UpbitTradesTicks {

	@Id
	//체결 번호(Unique)
	@Column(nullable = true,unique=false,name="sequential_id")
	Long sequentialId;

	@Id
	@Column(nullable = false,unique=true ,length = 1000 ,name="market")
	String market;

	//체결 시각(UTC 기준)
	@Id
	@Column(nullable = false,unique=false, length = 1000 ,name="trade_date_utc")
	String tradeDateUtc	;
	//체결 시각(UTC 기준)
	@Id
	@Column(nullable = false,unique=false, length = 1000 ,name="trade_time_utc")
	String tradeTimeUtc;

	
	//체결 타임스탬프
	@Id
	@Column(nullable = true,unique=false,name="timestamp")
	Long timestamp;

	//체결 가격
	@Column(nullable = true,unique=false,name="trade_price")
	Double tradePrice;


	//체결량
	@Column(nullable = true,unique=false,name="trade_volume")
	Double tradeVolume;


	//전일 종가
	@Column(nullable = true,unique=false,name="prev_closing_price")
	Double prevClosingPrice;

	//변화량
	@Column(nullable = true,unique=false,name="change_price")
	Double changePrice;

	//매도/매수
	@Column(nullable = true,unique=false,name="ask_bid")
	String askBid;


	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "crt_dtm")
	Date crtDtm;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "updt_dtm")
	Date updtDtm;
}