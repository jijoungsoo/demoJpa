package com.example.demo.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name="TB_CM_PGM")
public class CmPgm {
	@Id
	@Column(nullable = false,unique=true, length = 50 ,name="PGM_ID")
	String pgmId;
	
	@Column(nullable = false,unique=true, length = 100 ,name="PGM_NM")
	String pgmNm;
		
	@Column(nullable = true, length = 4000 ,name="RMK")
	String rmk;
	
	@Column(nullable = true, length = 100 ,name="CATEGORY")
	String category;
	
	@Column(nullable = true, length = 200 ,name="PGM_LINK")
	String pgmLink;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false,name="CRT_DTM")
	Date crtDtm;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false,name="UPDT_DTM")
	Date updtDtm;
}