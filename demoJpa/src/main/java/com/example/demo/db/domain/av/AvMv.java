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
@Table(name="tb_av_mv")
public class AvMv {
	@Id
	@Column(nullable = false,unique=true ,name="av_seq")
	long avSeq;
		
	@Column(nullable = true,unique=true, length = 50 ,name="av_nm")
	String avNm;
	
	@Column(nullable = true, length = 1000 ,name="ttl")
	String ttl;
	
	@Column(nullable = true, length = 4000 ,name="cntnt")
	String cntnt;
	
	@Column(nullable = true,unique=false, length = 8 ,name="mk_dt")
	String mkDt;
		
	@Column(nullable = true, name="lk_cnt")
	int lkCnt;

	@Column(nullable = true, name = "dslk_cnt")
	long dslkCnt;
	
	@Column(nullable = true, length = 5 ,name="ord")
	String ord;
	
	@Column(nullable = true, length = 4000 ,name="rmk")
	String rmk;
	
	@Column(nullable = false, length = 1 ,name="msc_cd")
	String mscCd;

	@Column(nullable = false, length = 1 ,name="vr_yn")
	String vrYn;
	
	@Column(nullable = false, length = 1 ,name="cptn_yn")
	String cptnYn;
		
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