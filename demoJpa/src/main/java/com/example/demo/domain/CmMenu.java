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


@Builder
@NoArgsConstructor /*org.springframework.orm.jpa.JpaSystemException: No default constructor for entity:*/
@AllArgsConstructor /*https://tzara.tistory.com/73*/
@org.hibernate.annotations.DynamicUpdate/*구분생성시 변경된 것만 한다.*/
@org.hibernate.annotations.DynamicInsert/*구분생성시 null인것은 보내지 않는다.*/
@Data
@Entity
@Table(name="TB_CM_MENU")
public class CmMenu {
	@Id
	@Column(nullable = false,unique=true, length = 50 ,name="MENU_CD")
	String menuCd;
	
	@Column(nullable = false,unique=false, length = 100 ,name="MENU_NM")
	String menuNm;
		
	@Column(nullable = true, length = 50 ,name="PRNT_MENU_CD")
	String prntMenuCd;
	
	@Column(nullable = true, length = 50 ,name="MENU_LVL")
	String menuLvl;
	
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
