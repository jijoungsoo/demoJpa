package com.example.demo.domain.kiw;

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
@Table(name="TB_ST_RECEIVE_MSG")  /*전송 내용 */
public class StReceiveMsg {
	@Id
	@Column(nullable = false, length = 10 ,name="SCR_NO")
	String scrNo;
	
	@Column(nullable = false,length = 100,  name="RQ_NAME")
	String rqNm;
	
	@Column(nullable = true,  length = 30 ,name="TR_CODE")
	String trcd;
	
	@Column(nullable = true,  length = 9 ,name="STOCK_CD")
	String stockCd;
	
	@Column(nullable = true,  length = 9 ,name="MSG")
	String msg;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false,name="CRT_DTM")
	Date crtDtm;
}
