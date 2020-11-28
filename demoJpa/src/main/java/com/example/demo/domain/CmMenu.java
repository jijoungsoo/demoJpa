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
@Table(name="TB_CM_MENU")
public class CmMenu {
	@Id
	@Column(nullable = false,unique=true, length = 50 ,name="MENU_CD")
	String menuCd;
	
	@Column(nullable = false,unique=true, length = 100 ,name="MENU_NM")
	String menuNm;
		
	@Column(nullable = true, length = 50 ,name="PRNT_MENU_CD")
	String prntMenuCd;
	
	@Column(nullable = true, length = 1 ,name="MENU_KIND")
	String menuKind;
	
	@Column(nullable = true, length = 4000 ,name="RMK")
	String rmk;
	
	@Column(nullable = true, length = 3 ,name="FST_ORD")
	String fstOrd;
	
	@Column(nullable = true, length = 3 ,name="SED_ORD")
	String sedOrd;
	
	@Column(nullable = true, length = 50 ,name="PGM_ID")
	String pgmId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false,name="CRT_DTM")
	Date crtDtm;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false,name="UPDT_DTM")
	Date updtDtm;
}
