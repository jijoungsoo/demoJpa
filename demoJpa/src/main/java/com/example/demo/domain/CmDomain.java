package com.example.demo.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@org.hibernate.annotations.DynamicUpdate
@org.hibernate.annotations.DynamicInsert
@Entity
@Table(name="TB_CM_DOMAIN")
public class CmDomain {
	@Id
	@Column(nullable = false,unique=true, length = 50 ,name="DOMAIN_CD")
	String domainCd;
	
	@Column(nullable = false,unique=true, length = 100 ,name="DOMAIN_NM")
	String domainNm;
		
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
