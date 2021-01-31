package com.example.demo.db.domain.av;

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
@Table(name="tb_av_actr")
public class AvActr {
	@Id
	@Column(nullable = false,unique=true ,name="actr_seq")
	long actrSeq;
	
	@Column(nullable = true,unique=false, length = 50 ,name="actr_nm_jp")
	String actrNmJp;
	
	@Column(nullable = true,unique=false, length = 50 ,name="actr_nm_eng")
	String actrNmEng;
	
	@Column(nullable = false,unique=false, length = 50 ,name="actr_nm_kr")
	String actrNmKr;
	
	@Column(nullable = true,unique=false, length = 8 ,name="birth_dt")
	String birthDt;
		
	@Column(nullable = false, length = 1 ,name="sex")
	String sex;
	
	@Column(nullable = true, name="rnk")
	int rnk;
	
	@Column(nullable = true, length = 4000 ,name="rmk")
	String rmk;
	
	@Column(nullable = false, length = 1 ,name="msc_yn")
	String mscYn;
	
	@Column(nullable = false, name="ord")
	int ord;
	
	@Column(nullable = false, name = "CRT_USR_NO")
	long crtUsrNo;
	
	@Column(nullable = false, name = "UPDT_USR_NO")
	long updtUsrNo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false,name="crt_dtm")
	Date crtDtm;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false,name="updt_dtm")
	Date updtDtm;
}