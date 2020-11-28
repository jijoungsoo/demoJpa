package com.example.demo.domain;

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
@Table(name="TB_CM_DOMAIN")
public class CmDomain {
	@Id
	@Column(nullable = false,unique=true, length = 50 ,name="DOMAIN_CD")
	String dmnCd;
	
	@Column(nullable = false,unique=true, length = 100 ,name="DOMAIN_NM")
	String dmnNm;
		
	@Column(nullable = false, length = 50 ,name="DATA_TYPE")
	String dataType;
	
	@Column(nullable = true, length = 4000 ,name="RMK")
	String rmk;	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false,name="CRT_DTM")
	Date crtDtm;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false,name="UPDT_DTM")
	Date updtDtm;
}
