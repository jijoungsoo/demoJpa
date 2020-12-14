package com.example.demo.domain.kiw;

import java.math.BigDecimal;
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
@Table(name="TB_ST_STOCK")  /*키움주식 */
public class StStock {
	@Id
	@Column(nullable = false, length = 9 ,name="STOCK_CD")
	String stockCd;
	
	@Column(nullable = true,  length = 2 ,name="MARKET_CD")
	String marketCd;
	
	@Column(nullable = false, length = 45 , name="STOCK_NM")
	String stockNm;
	
	@Column(nullable = false, name="STOCK_CNT")
	BigDecimal stockCnt;
	
	@Column(nullable = true, name="LST_PRICE")
	BigDecimal lstPrice;
	
	@Column(nullable = true,  length = 20 ,name="STOCK_STATE")
	String STOCK_STATE;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false,name="CRT_DTM")
	Date crtDtm;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false,name="UPDT_DTM")
	Date updtDtm;
}
