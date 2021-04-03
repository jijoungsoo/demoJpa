package com.example.demo.db.domain.marcap;

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
@IdClass(StckMarcapId.class) 
@Entity
@Table(name="tb_st_marcap")  /*https://github.com/FinanceData/marcap   여기 데이터 */
public class StckMarcap {
	/*Code 종목코드  */
	@Id
	@Column(nullable = false, length = 9 ,name="stock_cd")
	String stockCd;
	
	/*date 날자 */
	@Id
	@Column(nullable = false, length = 8 , name="stock_dt")
	String stockDt;
	
	/*name 종목명 */
	@Column(nullable = true, length = 45 , name="stock_nm")
	String stockNm;
	
	/*close 종가 */
	@Column(nullable = true, name="cls_amt")
	Integer clsAmt;
	
	/*changes 전일대비 */
	@Column(nullable = true, name="changes_amt")
	Integer changesAmt;
	
	/*changes 전일대비 */
	@Column(nullable = true,  name="changes_rt")
	Double changesRt;
	
	/*Volume 거래량 */
	@Column(nullable = true,  name="trade_qty")
	Long tradeQty;
	
	
	/*Amount 거래대금 */
	@Column(nullable = true,  name="trade_amt")
	Long tradeAmt;
	
	/*open  시가 */
	@Column(nullable = true,  name="start_amt")
	Integer startAmt;
	
	/*high 고가 */
	@Column(nullable = true,  name="high_amt")
	Integer highAmt;
	
	/*low 저가 */
	@Column(nullable = true,  name="low_amt")
	Integer lowAmt;
	
	/*Marcap 시가총액(백만원) */
	@Column(nullable = true,  name="total_mrkt_amt")
	Long totalMrktAmt;
	
	/*MarcapRatio 시가총액비중(%) */
	@Column(nullable = true,  name="total_mrkt_amt_rt")
	Double totalMrktAmtRt;
	
	/*Stocks 상장주식수 */
	@Column(nullable = true,  name="stock_cnt")
	Double stockCnt;
	
	@Column(nullable = true, name="rnk")
	Integer rnk;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false,name="crt_dtm")
	Date crtDtm;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false,name="updt_dtm")
	Date updtDtm;
}
