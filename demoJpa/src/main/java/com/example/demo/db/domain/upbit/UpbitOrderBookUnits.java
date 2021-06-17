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
@IdClass(UpbitOrderBookUnitsIdx.class)  
@Table(name="tb_upbit_order_book_units")
public class UpbitOrderBookUnits {
		/*
	orderbook_unit 리스트에는 15호가 정보가 들어가며 차례대로 1호가, 2호가 ... 15호가의 정보를 담고 있습니다.
	*/
	@Id
	@Column(nullable = false,unique=false ,length = 1000 ,name="market")
	String market;

	@Id
	//호가 생성 시각
	@Column(nullable = false,unique=false,name="timestamp")
	Long timestamp;

	@Id
	//1~15
	@Column(nullable = false,unique=false,name="seq")
	Integer seq;

	

	//매도호가
	@Column(nullable = false,unique=false, name="ask_price")
	Double askPrice	;
	
	//매수호가
	@Column(nullable = false,unique=false, name="bid_price")
	Double bidPrice	;

	//매도 잔량
	@Column(nullable = false,unique=false, name="ask_size")
	Double askSize	;
	
	//매수 잔량
	@Column(nullable = false,unique=false, name="bid_size	")
	Double bidSize		;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "crt_dtm")
	Date crtDtm;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "updt_dtm")
	Date updtDtm;

}