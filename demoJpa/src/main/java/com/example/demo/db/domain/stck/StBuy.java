package com.example.demo.db.domain.stck;

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
@Table(name = "tb_st_buy")
public class StBuy {
	@Id
	@Column(nullable = false, name = "buy_seq")
	long buySeq;

	@Column(nullable = false, length = 9, name = "stock_cd")
	String stockCd;
	
	@Column(nullable = false, length = 45 , name="stock_nm")
	String stockNm;

	/*단가 */
	@Column(nullable = false,  name = "amt")
	Integer amt;
	
	/*갯수*/
	@Column(nullable = false, name = "cnt")
	Integer cnt;
	
	/*잔고갯수*/
	@Column(nullable = false, name = "bal_cnt")
	Integer balCnt;
	
	/*수수료 단가당*/
	@Column(nullable = false, name = "fee")
	Integer fee;
	
	/*금액*수량   */
	@Column(nullable = false, name = "tot_amt")
	Integer totAmt;

	@Column(nullable = false, length = 14, name = "buy_date")
	String buyDate;
	
	@Column(nullable = false, length = 1, name = "del_yn")
	String delYn;
	
	@Column(nullable = true, length = 4000 ,name="rmk")
	String rmk;
	
	@Column(nullable = false, name = "crt_usr_no")
	long crtUsrNo;
	
	@Column(nullable = false, name = "updt_usr_no")
	long updtUsrNo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "crt_dtm")
	Date crtDtm;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "updt_dtm")
	Date updtDtm;
}
