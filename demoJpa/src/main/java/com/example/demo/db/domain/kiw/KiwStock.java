package com.example.demo.db.domain.kiw;

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
@Table(name="tb_kiw_stock")  /*키움주식 */
public class KiwStock {
	@Id
	@Column(nullable = false, length = 9 ,name="stock_cd")
	String stockCd;
	
	@Column(nullable = false, length = 45 , name="stock_nm")
	String stockNm;
	
	@Column(nullable = false, length = 8 , name="stock_dt")
	String OpnDt;
	
	
	@Column(nullable = false, name="stock_cnt")
	BigDecimal stockCnt;
	
	@Column(nullable = true, name="lst_price")
	BigDecimal lstPrice;
	
	@Column(nullable = true,  length = 20 ,name="stock_state")
	String STOCK_STATE;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false,name="crt_dtm")
	Date crtDtm;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false,name="updt_dtm")
	Date updtDtm;
}
