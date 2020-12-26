package com.example.demo.db.domain.marcap;

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
@Table(name="tb_marcap_stock_day")  /*https://github.com/FinanceData/marcap   여기 데이터 */
public class MarcapStckDay {
	/*Code 종목코드  */
	@Id
	@Column(nullable = false, length = 9 ,name="stock_cd")
	String stockCd;
	
	/*name 종목명 */
	@Column(nullable = false, length = 45 , name="stock_nm")
	String stockNm;
	
	/*일자  */
	@Column(nullable = false, length = 8 , name="stock_dt")
	String stockDt;
	
	/*open  시가 */
	@Column(nullable = false, length = 8 , name="start_amt")
	int stAmt;
	
	/*low 저가 */
	@Column(nullable = false, length = 8 , name="low_amt")
	int lwAmt;
	

	/*close 종가 */
	@Column(nullable = false, length = 8 , name="cls_amt")
	int clsAmt;
	

	
	
	
	
	
	
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
