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
@IdClass(UpbitOrderBookIdx.class)  
@Table(name="tb_upbit_orderbook")
public class UpbitOrderBook {
	@Id
	@Column(nullable = false,unique=true ,length = 1000 ,name="market")
	String market;

	@Id
	//호가 생성 시각
	@Column(nullable = false,unique=false,name="timestamp")
	Long timestamp;

	//호가 매도 총 잔량
	@Column(nullable = false,unique=false, name="total_ask_size")
	Double totalAskSize	;
	
	//호가 매수 총 잔량
	@Column(nullable = false,unique=false, name="total_bid_size")
	Double totalBidSize	;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "crt_dtm")
	Date crtDtm;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "updt_dtm")
	Date updtDtm;
}