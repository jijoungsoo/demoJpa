package com.example.demo.db.domain.stck;

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
@NoArgsConstructor /*
					 * org.springframework.orm.jpa.JpaSystemException: No default constructor for
					 * entity:
					 */
@AllArgsConstructor /* https://tzara.tistory.com/73 */
@org.hibernate.annotations.DynamicUpdate /* 구분생성시 변경된 것만 한다. */
@org.hibernate.annotations.DynamicInsert /* 구분생성시 null인것은 보내지 않는다. */
@Data
@Entity
@Table(name = "TB_ST_SELL")
public class StSell {
	@Id
	@Column(nullable = false, name = "SELL_SEQ")
	long sellSeq;

	@Column(nullable = false, name = "USR_NO")
	long usrNo;

	@Column(nullable = false, length = 7, name = "STOCK_CD")
	String stockCd;

	@Column(nullable = false,  name = "AMT")
	Integer amt;
	
	/*갯수*/
	@Column(nullable = false, name = "CNT")
	Integer cnt;
	
	/*수수료*/
	@Column(nullable = false, name = "TOT_FEE")
	Integer totFee;
	
	/*금액*수량   */
	@Column(nullable = false, name = "TOT_AMT")
	Integer totAmt;


	@Column(nullable = true, length = 14, name = "SELL_DATE")
	String sellDate;
	
	@Column(nullable = false, name = "CRT_USR_NO")
	long crtUsrNo;
	
	@Column(nullable = false, name = "UPDT_USR_NO")
	long updtUsrNo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "CRT_DTM")
	Date crtDtm;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "UPDT_DTM")
	Date updtDtm;
}
