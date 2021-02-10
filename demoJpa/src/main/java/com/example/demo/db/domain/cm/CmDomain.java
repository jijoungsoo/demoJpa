package com.example.demo.db.domain.cm;

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

@Data
@Builder
@NoArgsConstructor /*org.springframework.orm.jpa.JpaSystemException: No default constructor for entity:*/
@AllArgsConstructor /*https://tzara.tistory.com/73*/
@org.hibernate.annotations.DynamicUpdate/*구분생성시 변경된 것만 한다.*/
@org.hibernate.annotations.DynamicInsert/*구분생성시 null인것은 보내지 않는다.*/
@Entity
@Table(name="tb_cm_domain")
public class CmDomain {
	@Id
	@Column(nullable = false,unique=true,name="domain_no")
	long dmnNo;
	
	@Column(nullable = false,unique=true, length = 50 ,name="domain_cd")
	String dmnCd;
	
	@Column(nullable = false,unique=true, length = 100 ,name="domain_nm")
	String dmnNm;
		
	@Column(nullable = false, length = 50 ,name="data_type")
	String dataType;
	
	@Column(nullable = true, length = 4000 ,name="rmk")
	String rmk;	
	
	@Column(nullable = false, name = "crt_usr_no")
	long crtUsrNo;
	
	@Column(nullable = false, name = "updt_usr_no")
	long updtUsrNo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false,name="crt_dtm")
	Date crtDtm;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false,name="updt_dtm")
	Date updtDtm;
}
